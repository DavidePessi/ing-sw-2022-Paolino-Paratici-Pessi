package it.polimi.ingsw.NETWORK.SERVER;


import it.polimi.ingsw.NETWORK.MESSAGES.*;
import it.polimi.ingsw.NETWORK.UTILS.Observable;

import java.io.*;
import java.net.Socket;

import it.polimi.ingsw.NETWORK.UTILS.Observable;
import it.polimi.ingsw.NETWORK.UTILS.Observer;

import java.io.IOException;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.ToDoubleBiFunction;

public class SocketClientConnection extends Observable<String> implements ClientConnection, Runnable {

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Server server;
    private int pingTime = 10;  //tempo di attesa massimo di un ping dal client in secondi
    private boolean socketClose = false;
    private boolean endGame;    //mi dice se il game a cui si sta partecipando è ancora in corso o meno
    private boolean active = true;

    public SocketClientConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    /**
     * returns if the socket is active
     * @return
     */
    private synchronized boolean isActive(){
        return active;
    }

    @Override
    public synchronized void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket!");
        }

        active = false;
    }

    /**
     * close the connection
     */
    private void close() {
        //chiudo effettivamente la connessione
        socketClose = true;
        closeConnection();
        System.out.println("Deregistering client...");
        server.disconnection(this);
        System.out.println("Done!");
    }

    @Override
    public void asyncSend(final Object message){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //System.out.println("ho ricevuto il messaggio: " + message);
                    //System.out.println("canale di uscita: " + out);

                    send((ServerMessage) message);
                }catch(Exception e){
                    //System.out.println("eccezione: " + e.getClass());
                    //e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void run() {
        String name = null;
        int numPlayers = 0;
        String typeGame = null;
        boolean okNickname = false;

        try {
            //inizializzo i canali di comunicazione
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            //dichiaro i messaggi da scambiare
            ClientMessage cm;
            Payload pay;
            ServerMessage sm;
            ServerHeader sh;

            //invio i messaggi di ping

            Thread t = asyncPingDecrease();
            ExecutorService executor = Executors.newFixedThreadPool(1);
            executor.submit(t);
            do{
                endGame = false;

                //--------------------------------------------------------------
                //CHIEDO IL TIPO DI PARTITA
                do {
                    sh = new ServerHeader(ServerAction.SET_UP_GAMEMODE, "");
                    pay = new Payload("SET_UP_GAMEMODE", "Select the gamemode");
                    sm = new ServerMessage(sh, pay);

                    send(sm);

                    cm = PingRead(in);
                    typeGame = (String) cm.getPayload().getParameter("typeGame");
                    System.out.println("ho ricevuto il tipo di partita");

                    //CASO DI ERRORE
                    if (!typeGame.equals("difficult") && !typeGame.equals("easy")) {
                        sh = new ServerHeader(ServerAction.ERROR_SETUP, "");
                        pay = new Payload("ERROR_SETUP", "Warning you have to choose between difficult and easy");
                        sm = new ServerMessage(sh, pay);

                        send(sm);
                    }
                } while (!typeGame.equals("difficult") && !typeGame.equals("easy"));

                //---------------------------------------------------------------
                //CHIEDO IL NUMERO DI GIOCATORI
                do {
                    sh = new ServerHeader(ServerAction.SET_UP_NUM_PLAYERS, "");
                    pay = new Payload("SET_UP_NUM_PLAYERS", "Choose the number of player");
                    sm = new ServerMessage(sh, pay);

                    send(sm);

                    cm = PingRead(in);
                    Integer n = (Integer) cm.getPayload().getParameter("numPlayer");
                    //System.out.println("numplayers: " + n);
                    numPlayers = n.intValue();
                    System.out.println("ho ricevuto il numero di giocatori");

                    //CASO DI ERRORE
                    if (numPlayers != 2 && numPlayers != 3 && numPlayers != 4) {
                        sh = new ServerHeader(ServerAction.ERROR_SETUP, "");
                        pay = new Payload("ERROR_SETUP", "Warning you can choose between 2,3 or 4 players");
                        sm = new ServerMessage(sh, pay);

                        send(sm);
                    }
                } while (numPlayers != 2 && numPlayers != 3 && numPlayers != 4);

                //----------------------------------------------------------------------
                //CHIEDO IL NICKNAME
                do {
                    sh = new ServerHeader(ServerAction.SET_UP_NICKNAME, "");
                    pay = new Payload("SET_UP_NICKNAME", "Choose the nickname");
                    sm = new ServerMessage(sh, pay);

                    send(sm);

                    cm = PingRead(in);
                    name = (String) cm.getPayload().getParameter("nickname");
                    System.out.println("ho ricevuto il nickname");

                    //VERIFICO LA VALIDITA' DEL NICKNAME CON 2E
                    if (numPlayers == 2 && typeGame.equals("easy")) {
                        try {
                            server.lobby2E(this, name);
                            okNickname = true;
                        } catch (Exception e) {
                            sh = new ServerHeader(ServerAction.ERROR_SETUP, "");
                            pay = new Payload("ERROR_SETUP", "Warning nickname is already in use");
                            sm = new ServerMessage(sh, pay);

                            send(sm);
                        }
                    } else //VERIFICO LA VALIDITA' DEL NICKNAME CON 2D
                        if (numPlayers == 2 && typeGame.equals("difficult")) {
                            try {
                                server.lobby2D(this, name);
                                okNickname = true;
                            } catch (Exception e) {
                                sh = new ServerHeader(ServerAction.ERROR_SETUP, "");
                                pay = new Payload("ERROR_SETUP", "Warning nickname is already in use");
                                sm = new ServerMessage(sh, pay);

                                send(sm);
                            }
                        } else
                            //VERIFICO LA VALIDITA' DEL NICKNAME CON 3E
                            if (numPlayers == 3 && typeGame.equals("easy")) {
                                try {
                                    server.lobby3E(this, name);
                                    okNickname = true;
                                } catch (Exception e) {
                                    sh = new ServerHeader(ServerAction.ERROR_SETUP, "");
                                    pay = new Payload("ERROR_SETUP", "Warning nickname is already in use");
                                    sm = new ServerMessage(sh, pay);

                                    send(sm);
                                }
                            } else
                                //VERIFICO LA VALIDITA' DEL NICKNAME CON 3D
                                if (numPlayers == 3 && typeGame.equals("difficult")) {
                                    try {
                                        server.lobby3D(this, name);
                                        okNickname = true;
                                    } catch (Exception e) {
                                        sh = new ServerHeader(ServerAction.ERROR_SETUP, "");
                                        pay = new Payload("ERROR_SETUP", "Warning nickname is already in use");
                                        sm = new ServerMessage(sh, pay);

                                        send(sm);
                                    }
                                } else
                                    //VERIFICO LA VALIDITA' DEL NICKNAME CON 4E
                                    if (numPlayers == 4 && typeGame.equals("easy")) {
                                        try {
                                            server.lobby4E(this, name);
                                            okNickname = true;
                                        } catch (Exception e) {
                                            sh = new ServerHeader(ServerAction.ERROR_SETUP, "");
                                            pay = new Payload("ERROR_SETUP", "Warning nickname is already in use");
                                            sm = new ServerMessage(sh, pay);

                                            send(sm);
                                        }
                                    } else //VERIFICO LA VALIDITA' DEL NICKNAME CON 4D
                                        if (numPlayers == 4 && typeGame.equals("difficult")) {
                                            try {
                                                server.lobby4D(this, name);
                                                okNickname = true;
                                            } catch (Exception e) {
                                                sh = new ServerHeader(ServerAction.ERROR_SETUP, "");
                                                pay = new Payload("ERROR_SETUP", "Warning nickname is already in use");
                                                sm = new ServerMessage(sh, pay);

                                                send(sm);
                                            }
                                        }

            } while (okNickname == false);

            //CONFERMO AL CLIENT DI ESSERE ENTRATO NELLA LOBBY
            sh = new ServerHeader(ServerAction.OK_START, "");
            pay = new Payload("OK_START", "You are in LOBBY");
            sm = new ServerMessage(sh, pay);

            send(sm);
                System.out.println("sei in lobby");

            //CICLO DI GIOCO
            //continuo ad inviare i messaggi al server finchè non trovo un messaggio di fine partita a quel
            //punto mi disconnetto se il player non vuole più giocare o mi collego ad un'altra lobby se
            //vuole continuare
            while (isActive() && !endGame) {
                cm = PingRead(in);

                if (ClientAction.END_GAME.equals(cm.getClientHeader().getClientAction())) {
                    setEndGame(true);
                    try {
                        server.deregisterConnection(this);
                    }catch(Exception e){}

                    if("0".equals((String)cm.getPayload().getParameter("endgame"))){
                        setActive(false);
                    }
                } else {
                    notify(cm);
                }
            }
        }while(isActive());

        } catch (IOException e) {
            //System.err.println("Error IOException! ");
        } finally{
            close();
        }
    }

    /**
     * if the type of client message is ping, I will read the ping, and call ping()
     * @param in
     * @return
     */
    private ClientMessage PingRead(ObjectInputStream in){
        ClientMessage cm = null;
        try {
            do {

                cm = (ClientMessage) in.readObject();
                if ((cm.getClientHeader().getClientAction()).equals(ClientAction.PING)) {
                    //System.out.println("qui");
                    //System.out.println("oggetto: " + cm.getClientHeader().getClientAction());
                    ping();
                }
            } while (cm.getClientHeader().getClientAction().equals(ClientAction.PING));

        }catch(IOException e){
            //System.out.println("IOexception nel pingRead");
            //System.out.println("oggetto: " + cm.getClientHeader().getClientAction());
        }catch(ClassNotFoundException e){
            //System.out.println("ClassNotFoundException nel pingCheck");
        }
        return cm;
    }

    /**
     * decrease the value of ping
     * @return
     */
    private Thread asyncPingDecrease(){

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while(pingTime > 0) {
                    //System.out.println("pingTime: " + pingTime);
                    pingTime--;

                    try {
                        Thread.sleep(1000);
                    }catch(Exception e){}
                }
                if(!socketClose) {
                    close();
                }
            }
        });
        return t;
    }

    /**
     * send the value of ping and send a message to server to say that connection is stable
     */
    private void ping() {
        pingTime = 10;

        ServerMessage sm;
        ServerHeader sh = new ServerHeader(ServerAction.PING, "Connection stable");
        Payload pay = new Payload();
        sm = new ServerMessage(sh, pay);
        send(sm);

    }

    /**
     * send a message
     * @param sm
     */
    private synchronized void send(ServerMessage sm){
        try {
            out.writeObject(sm); //Write byte stream to file system.
            out.flush();
            out.reset();
        }catch(IOException e){}
    }

    /**
     * set the variable endGame with the value of the parameter
     * @param bool
     */
    private void setEndGame(boolean bool){
        endGame = bool;
    }

    /**
     * set the activity of the socketClientConnection
     * @param bool
     */
    private void setActive(boolean bool){
        active = bool;
    }
}
