package it.polimi.ingsw.NETWORK.SERVER;


import it.polimi.ingsw.NETWORK.MESSAGES.*;
import it.polimi.ingsw.NETWORK.UTILS.Observable;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import it.polimi.ingsw.NETWORK.UTILS.Observable;
import it.polimi.ingsw.NETWORK.UTILS.Observer;

import java.io.IOException;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.ToDoubleBiFunction;

public class SocketClientConnection extends Observable<String> implements ClientConnection, Runnable {

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Server server;
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
                    ServerHeader sh;
                    Payload pay;
                    ServerMessage sm;

                    sh = new ServerHeader(ServerAction.SET_UP_GAMEMODE, "");
                    pay = new Payload("SET_UP_GAMEMODE", "tipo partita");
                    sm = new ServerMessage(sh, pay);

                    out.writeObject(sm); //Write byte stream to file system.
                    out.flush();
                }catch(IOException e){}

            }
        }).start();
    }

    @Override
    public void run() {
        String name = null;
        int numPlayers=0;
        String typeGame = null;
        try{
            out = new ObjectOutputStream(socket.getOutputStream());

            ServerHeader sh = new ServerHeader(ServerAction.SET_UP_GAMEMODE, "");
            Payload pay = new Payload("SET_UP_GAMEMODE", "Scegli il tipo di parita");
            ServerMessage sm = new ServerMessage(sh, pay);

            out.writeObject(sm); //Write byte stream to file system.
            out.flush();

            in = new ObjectInputStream(socket.getInputStream());
            ClientMessage cm;

            //---------------------------------------------------------------
            //faccio inserire il tipo di partita e controllo
            boolean oktypeGame = false;
            while (oktypeGame == false) {
                cm = (ClientMessage) in.readObject();
                typeGame = (String) cm.getPayload().getParameter("typeGame");
                //System.out.println("ciao " + name + " hai scelto partita con " + numPlayers + " giocatori e tipo " + typeGame);

                if (typeGame.equals("difficult") || typeGame.equals("easy")) {

                    sh = new ServerHeader(ServerAction.SET_UP_NUM_PLAYERS, "");
                    pay = new Payload("SET_UP_NUM_PLAYERS", "numero di Players");
                    sm = new ServerMessage(sh, pay);

                    out.writeObject(sm); //Write byte stream to file system.
                    out.flush();

                    //faccio inserire il num di players e controllo
                    boolean okNumPlayers = false;
                    while (okNumPlayers == false) {

                        cm = (ClientMessage) in.readObject();
                        Integer n = (Integer) cm.getPayload().getParameter("numPlayer");
                        //System.out.println("numplayers: " + n);
                        numPlayers = n.intValue();
                        if (numPlayers == 2 || numPlayers == 3 || numPlayers == 4) {

                            boolean okNickname = false;
                            boolean oklobby = false;
                            while (okNickname == false) {


                                sh = new ServerHeader(ServerAction.SET_UP_NICKNAME, "");
                                pay = new Payload("SET_UP_NICKNAME", "Inserisci nickname");
                                sm = new ServerMessage(sh, pay);

                                out.writeObject(sm); //Write byte stream to file system.
                                out.flush();

                                cm = (ClientMessage) in.readObject();
                                name = (String) cm.getPayload().getParameter("nickname");

                                if (typeGame.equals("difficult") && numPlayers == 2) {
                                    try {
                                        server.lobby2D(this, name, typeGame);
                                        okNickname = true;
                                    } catch (Exception e) {
                                        okNickname = false;
                                        sh = new ServerHeader(ServerAction.ERROR_NUMPLAYERS, "");
                                        pay = new Payload("ERROR_NUMPLAYERS", "Attenzione nickname già in uso");
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
                                        sh = new ServerHeader(ServerAction.ERROR_NUMPLAYERS, "");
                                        pay = new Payload("ERROR_NUMPLAYERS", "Attenzione nickname già in uso");
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
                                        sh = new ServerHeader(ServerAction.ERROR_NUMPLAYERS, "");
                                        pay = new Payload("ERROR_NUMPLAYERS", "Attenzione nickname già in uso");
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
                                        sh = new ServerHeader(ServerAction.ERROR_NUMPLAYERS, "");
                                        pay = new Payload("ERROR_NUMPLAYERS", "Attenzione nickname già in uso");
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
                                        sh = new ServerHeader(ServerAction.ERROR_NUMPLAYERS, "");
                                        pay = new Payload("ERROR_NUMPLAYERS", "Attenzione nickname già in uso");
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
                                        sh = new ServerHeader(ServerAction.ERROR_NUMPLAYERS, "");
                                        pay = new Payload("ERROR_NUMPLAYERS", "Attenzione nickname già in uso");
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
                            sh = new ServerHeader(ServerAction.ERROR_NUMPLAYERS, "");
                            pay = new Payload("ERROR_NUMPLAYERS", "Attenzione puoi giocare con 2,3,4 giocatori");
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
                    sh = new ServerHeader(ServerAction.ERROR_GAMEMODE, "");
                    pay = new Payload("ERROR_GAMEMODE", "Attenzione devi scegliere tra difficult o easy");
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


            //TODO a cosa serve?
            while(isActive()){
                cm = (ClientMessage) in.readObject();
                //typeGame = (String)cm.getPayload().getParameter("typeGame");
                notify(cm);

            }
        } catch (IOException | NoSuchElementException | ClassNotFoundException e) {
            System.err.println("Error! " + e.getMessage());
        }finally{
            close();
        }
    }


}
