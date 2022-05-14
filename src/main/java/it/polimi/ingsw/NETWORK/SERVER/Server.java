package it.polimi.ingsw.NETWORK.SERVER;

import it.polimi.ingsw.CONTROLLER.ControllerAction;
import it.polimi.ingsw.CONTROLLER.ControllerTurn;
import it.polimi.ingsw.MODEL.Game;
import it.polimi.ingsw.NETWORK.VIEW.RemoteView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int PORT = 12345;
    private ServerSocket serverSocket;
    private ExecutorService executor = Executors.newFixedThreadPool(128);
    private Map<String, SocketClientConnection> waitingRoom = new HashMap<>();
    private Map<SocketClientConnection, SocketClientConnection> playingConnection = new HashMap<>();

    //Deregister connection
    public synchronized void deregisterConnection(ClientConnection c) {
        ClientConnection opponent = playingConnection.get(c);
        if(opponent != null) {
            opponent.closeConnection();
        }
        playingConnection.remove(c);
        playingConnection.remove(opponent);
        Iterator<String> iterator = waitingRoom.keySet().iterator();
        while(iterator.hasNext()){
            if(waitingRoom.get(iterator.next())==c){
                iterator.remove();
            }
        }
    }

    //Wait for another player
    public synchronized void lobby(SocketClientConnection c, String name){
        //public synchronized void lobby(Connection c, String name){
            waitingRoom.put(name, c);
            if(waitingRoom.size() == 2) {
                List<String> keys = new ArrayList<>(waitingRoom.keySet());
                SocketClientConnection c1 = waitingRoom.get(keys.get(0));
                SocketClientConnection c2 = waitingRoom.get(keys.get(1));
                RemoteView player1 = new RemoteView(c1);
                RemoteView player2 = new RemoteView(c2);
                Game model = new Game(keys.get(0), keys.get(1));

                ControllerAction controllerAction = new ControllerAction(model, keys);

                ControllerTurn controllerTurn = new ControllerTurn(controllerAction, model, keys);

                model.addObserver(player1);
                model.addObserver(player2);
                player1.addObserver(controllerTurn);
                player2.addObserver(controllerTurn);

                // TODO: 30/04/2022 opponent
                playingConnection.put(c1, c2);
                playingConnection.put(c2, c1);
                waitingRoom.clear();
                System.out.println("Game created");
            }
    }

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }

    public void run(){
        int connections = 0;
        System.out.println("Server is running");
        while(true){
            try {
                Socket newSocket = serverSocket.accept();
                connections++;
                System.out.println("Ready for the new connection - " + connections);
                SocketClientConnection socketConnection = new SocketClientConnection(newSocket, this);
                executor.submit(socketConnection);
            } catch (IOException e) {
                System.out.println("Connection Error!");
            }
        }
    }

}
