package it.polimi.ingsw.NETWORK.SERVER;

import it.polimi.ingsw.CONTROLLER.ControllerAction;
import it.polimi.ingsw.CONTROLLER.ControllerTurn;
import it.polimi.ingsw.MODEL.Game;
import it.polimi.ingsw.NETWORK.VIEW.RemoteView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int PORT = 12345;
    private ServerSocket serverSocket;
    private ExecutorService executor = Executors.newFixedThreadPool(128);

    private List<Connection> connections;
    private Map<String, Connection> waitingRoom;

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }

    private synchronized void registerConnection(Connection c){
        connections.add(c);
    }

    public void run(){

        int connections = 0;
        System.out.println("Server listening on port: " + PORT);
        while(true){
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Connection number: " + connections);
                connections++;
                Connection connection = new Connection(socket, this);
                registerConnection(connection);
                executor.submit(connection);
            } catch (IOException e){
                System.err.println("Connection error!");
            }
        }
    //server si mette in ascolto
    //crea una socket
    //crea una connessione
    }

    public synchronized void lobby(Connection c, String name){
        waitingRoom.put(name, c);
        if(waitingRoom.size() == 2){
            List<String> keys = new ArrayList<>(waitingRoom.keySet());
            Connection c1 = waitingRoom.get(keys.get(0));
            Connection c2 = waitingRoom.get(keys.get(1));
            RemoteView player1 = new RemoteView(c1);
            RemoteView player2 = new RemoteView(c2);
            Game model = new Game(keys.get(0),keys.get(1));

            ControllerAction controllerAction = new ControllerAction(model, keys);
            ControllerTurn controllerTurn = new ControllerTurn(controllerAction, model, keys);

            model.addObserver(player1);
            model.addObserver(player2);
            player1.addObserver(controllerTurn);
            player2.addObserver(controllerTurn);

            // TODO: 30/04/2022 opponent come li gestiamo, se li gestiamo
            //playingConnection.put(c1, c2);
            //playingConnection.put(c2, c1);
            waitingRoom.clear();
        }

    }

    public void lobby2(){
//viene aggiunta la connesione a connections
//aggiunge la connessione a waitingRoom
//se la grandezza di waitingRoom è tale da giocare avvia la partita
//nel creare la partita creo tutto compreso remote view, model, controller e basta
//altrimenti niente
    }

    public void lobby3(){}

    public void lobby4(){}

    public void disconnect(){}
}