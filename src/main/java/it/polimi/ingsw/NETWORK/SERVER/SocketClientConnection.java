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
    private boolean endGame;    //mi dice se il game a cui si sta partecipando è ancora in corso o meno
    private boolean active = true;

    public SocketClientConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    private synchronized boolean isActive(){
        return active;
    }

    public synchronized void send(Object message) {
        try {
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch(IOException e){
            System.err.println(e.getMessage());
        }

    }

    @Override
    public synchronized void closeConnection() {
        send("Connection closed!");
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket!");
        }
        active = false;
    }

    private void close() {
        //invio il messaggio di connessione persa
        ServerHeader sh = new ServerHeader(ServerAction.PING, "connessione persa");
        Payload pay = new Payload();
        ServerMessage sm = new ServerMessage(sh, pay);
        try {
            out.writeObject(sm);
            out.flush();
        }catch(IOException e){}

        //chiudo effettivamente la connessione
        closeConnection();
        System.out.println("Deregistering client...");
        server.deregisterConnection(this);
        System.out.println("Done!");
    }

    @Override
    public void asyncSend(final Object message){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ServerMessage sm = (ServerMessage) message;
                    if(sm.getServerHeader().getServerAction().equals(ServerAction.END_GAME)){

                        out.writeObject(message);
                        out.flush();

                        setEndGame(true);
                    }

                }catch(IOException e){}

            }
        }).start();
    }

    @Override
    public void run() {
        String name = null;
        int numPlayers=0;
        String typeGame = null;
        try {
                out = new ObjectOutputStream(socket.getOutputStream());

                //invio i messaggi di ping
                Thread t = asyncPingDecrease(out);
                ExecutorService executor = Executors.newFixedThreadPool(1);
                executor.submit(t);

            do{
                //--------------------------------------------------------------
                //CHIEDO IL TIPO DI PARTITA
                ServerHeader sh = new ServerHeader(ServerAction.SET_UP_GAMEMODE, "");
                Payload pay = new Payload("SET_UP_GAMEMODE", "Scegli il tipo di parita");
                ServerMessage sm = new ServerMessage(sh, pay);

                out.writeObject(sm); //Write byte stream to file system.
                out.flush();

                in = new ObjectInputStream(socket.getInputStream());
                ClientMessage cm;

                //---------------------------------------------------------------
                //VEDO SE IL TIPO DI PARTIA E' VALIDO
                boolean oktypeGame = false;
                while (oktypeGame == false) {
                    cm = pingCheck(in);
                    typeGame = (String) cm.getPayload().getParameter("typeGame");
                    //System.out.println("ciao " + name + " hai scelto partita con " + numPlayers + " giocatori e tipo " + typeGame);

                    if (typeGame.equals("difficult") || typeGame.equals("easy")) {

                        sh = new ServerHeader(ServerAction.SET_UP_NUM_PLAYERS, "");
                        pay = new Payload("SET_UP_NUM_PLAYERS", "numero di Players");
                        sm = new ServerMessage(sh, pay);

                        out.writeObject(sm); //Write byte stream to file system.
                        out.flush();

                        //------------------------------------------------------------
                        //CHIEDO IL NUMERO DI PLAYER
                        boolean okNumPlayers = false;
                        while (okNumPlayers == false) {

                            cm = pingCheck(in);
                            Integer n = (Integer) cm.getPayload().getParameter("numPlayer");
                            //System.out.println("numplayers: " + n);
                            numPlayers = n.intValue();

                            //VERIFICO SE IL NUMERO DI PLAYER E' VALIDO
                            if (numPlayers == 2 || numPlayers == 3 || numPlayers == 4) {

                                boolean okNickname = false;
                                boolean oklobby = false;
                                while (okNickname == false) {

                                    sh = new ServerHeader(ServerAction.SET_UP_NICKNAME, "");
                                    pay = new Payload("SET_UP_NICKNAME", "Inserisci nickname");
                                    sm = new ServerMessage(sh, pay);

                                    out.writeObject(sm);
                                    out.flush();

                                    cm = pingCheck(in);
                                    name = (String) cm.getPayload().getParameter("nickname");

                                    if (typeGame.equals("difficult") && numPlayers == 2) {
                                        try {
                                            server.lobby2D(this, name, typeGame);
                                            okNickname = true;
                                        } catch (Exception e) {
                                            okNickname = false;
                                            sh = new ServerHeader(ServerAction.ERROR_SETUP, "");
                                            pay = new Payload("ERROR_SETUP", "Attenzione nickname già in uso");
                                            sm = new ServerMessage(sh, pay);

                                            out.writeObject(sm); //Write byte stream to file system.
                                            out.flush();
                                        }
                                    } else if (typeGame.equals("easy") && numPlayers == 2) {
                                        try {
                                            server.lobby2E(this, name, typeGame);
                                            okNickname = true;
                                        } catch (Exception e) {
                                            okNickname = false;
                                            sh = new ServerHeader(ServerAction.ERROR_SETUP, "");
                                            pay = new Payload("ERROR_SETUP", "Attenzione nickname già in uso");
                                            sm = new ServerMessage(sh, pay);

                                            out.writeObject(sm); //Write byte stream to file system.
                                            out.flush();
                                        }
                                    } else if (typeGame.equals("difficult") && numPlayers == 3) {
                                        try {
                                            server.lobby3D(this, name, typeGame);
                                            okNickname = true;
                                        } catch (Exception e) {
                                            okNickname = false;
                                            sh = new ServerHeader(ServerAction.ERROR_SETUP, "");
                                            pay = new Payload("ERROR_SETUP", "Attenzione nickname già in uso");
                                            sm = new ServerMessage(sh, pay);

                                            out.writeObject(sm); //Write byte stream to file system.
                                            out.flush();
                                        }
                                    } else if (typeGame.equals("easy") && numPlayers == 3) {
                                        try {
                                            server.lobby3E(this, name, typeGame);
                                            okNickname = true;
                                        } catch (Exception e) {
                                            okNickname = false;
                                            sh = new ServerHeader(ServerAction.ERROR_SETUP, "");
                                            pay = new Payload("ERROR_SETUP", "Attenzione nickname già in uso");
                                            sm = new ServerMessage(sh, pay);

                                            out.writeObject(sm); //Write byte stream to file system.
                                            out.flush();

                                        }
                                    } else if (typeGame.equals("difficult") && numPlayers == 4) {
                                        try {
                                            server.lobby4D(this, name, typeGame);
                                            okNickname = true;
                                        } catch (Exception e) {
                                            okNickname = false;
                                            sh = new ServerHeader(ServerAction.ERROR_SETUP, "");
                                            pay = new Payload("ERROR_SETUP", "Attenzione nickname già in uso");
                                            sm = new ServerMessage(sh, pay);

                                            out.writeObject(sm); //Write byte stream to file system.
                                            out.flush();
                                        }
                                    } else if (typeGame.equals("easy") && numPlayers == 4) {
                                        try {
                                            server.lobby4E(this, name, typeGame);
                                            okNickname = true;
                                        } catch (Exception e) {
                                            okNickname = false;
                                            sh = new ServerHeader(ServerAction.ERROR_SETUP, "");
                                            pay = new Payload("ERROR_SETUP", "Attenzione nickname già in uso");
                                            sm = new ServerMessage(sh, pay);

                                            out.writeObject(sm); //Write byte stream to file system.
                                            out.flush();
                                        }
                                    }

                                }

                                System.out.println("ciao " + name + " hai scelto partita con " + numPlayers + " giocatori e tipo " + typeGame);

                                sh = new ServerHeader(ServerAction.OK_START, "");
                                pay = new Payload("OK_START", "Parametri corretti, sei in LOBBY");
                                sm = new ServerMessage(sh, pay);

                                out.writeObject(sm); //Write byte stream to file system.
                                out.flush();

                                okNumPlayers = true;
                            } else {
                                sh = new ServerHeader(ServerAction.ERROR_SETUP, "");
                                pay = new Payload("ERROR_SETUP", "Attenzione puoi giocare con 2,3,4 giocatori");
                                sm = new ServerMessage(sh, pay);

                                out.writeObject(sm); //Write byte stream to file system.
                                out.flush();

                                sh = new ServerHeader(ServerAction.SET_UP_NUM_PLAYERS, "");
                                pay = new Payload("SET_UP_NUM_PLAYERS", "scegli numero di giocatori");
                                sm = new ServerMessage(sh, pay);

                                out.writeObject(sm); //Write byte stream to file system.
                                out.flush();
                            }
                        }

                        oktypeGame = true;
                    } else {
                        sh = new ServerHeader(ServerAction.ERROR_SETUP, "");
                        pay = new Payload("ERROR_SETUP", "Attenzione devi scegliere tra difficult o easy");
                        sm = new ServerMessage(sh, pay);

                        out.writeObject(sm); //Write byte stream to file system.
                        out.flush();

                        sh = new ServerHeader(ServerAction.SET_UP_GAMEMODE, "");
                        pay = new Payload("SET_UP_GAMEMODE", "scegli tipo di partita");
                        sm = new ServerMessage(sh, pay);

                        out.writeObject(sm); //Write byte stream to file system.
                        out.flush();
                    }
                }
                //---------------------------------------------------------------

               /* ClientMessage cm = (ClientMessage) in.readObject();
                name = (String) cm.getPayload().getParameter("nickname");
                        //System.out.println("ciao " + name);

                        sh = new ServerHeader(ServerAction.SET_UP_NUM_PLAYERS, "");
                        pay = new Payload("SET_UP_NUM_PLAYERS", "Quanti giocatori siete?");
                        sm = new ServerMessage(sh, pay);

                        out.writeObject(sm); //Write byte stream to file system.
                        out.flush();*/


                setEndGame(false);
                while (isActive() && !endGame) {
                    cm = pingCheck(in);

                    //typeGame = (String)cm.getPayload().getParameter("typeGame");
                    notify(cm);
                }
            }while(isActive());

        } catch (IOException | NoSuchElementException e) {
            System.err.println("Error! " + e.getMessage());
        }finally{
            close();
        }
    }

    private void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }

    //PING RELATED METHODS
    private ClientMessage pingCheck(ObjectInputStream in){
        ClientMessage cm = null;
        try {
            do {
                cm = (ClientMessage) in.readObject();
                if (cm.getClientHeader().getClientAction().equals(ClientAction.PING)) {
                    ping();
                }
            } while (cm.getClientHeader().getClientAction().equals(ClientAction.PING));
        }catch(IOException | ClassNotFoundException e){}
        return cm;
    }
    private Thread asyncPingDecrease(ObjectOutputStream out){

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
                close();
            }
        });
        return t;
    }
    private void ping() {
        pingTime = 10;
    }


}
