package it.polimi.ingsw.NETWORK.SERVER;

import it.polimi.ingsw.NETWORK.MESSAGES.*;
import it.polimi.ingsw.NETWORK.UTILS.Observable;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import it.polimi.ingsw.NETWORK.UTILS.Observable;

import java.io.IOException;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

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
                send(message);
            }
        }).start();
    }

    @Override
    public void run() {
        String name;
        int numPlayers;
        String typeGame;
        try{
            out = new ObjectOutputStream(socket.getOutputStream());

            ServerHeader sh = new ServerHeader(ServerAction.SET_UP_NICKNAME, "");
            Payload pay = new Payload("SET_UP_NICKNAME", "Welcome! What is your name?");
            ServerMessage sm = new ServerMessage(sh, pay);

            out.writeObject(sm); //Write byte stream to file system.
            out.flush();

            in = new ObjectInputStream(socket.getInputStream());
            ClientMessage cm = (ClientMessage) in.readObject();

            name = (String)cm.getPayload().getParameter("nickname");
            //System.out.println("ciao " + name);

            sh = new ServerHeader(ServerAction.SET_UP_NUM_PLAYERS, "");
            pay = new Payload("SET_UP_NUM_PLAYERS", "Quanti giocatori siete?");
            sm = new ServerMessage(sh, pay);

            out.writeObject(sm); //Write byte stream to file system.
            out.flush();

            cm = (ClientMessage) in.readObject();

            Integer n = (Integer) cm.getPayload().getParameter("numPlayer");
            System.out.println("numplayers: "+n);
            numPlayers = n.intValue();

            sh = new ServerHeader(ServerAction.SET_UP_GAMEMODE, "");
            pay = new Payload("SET_UP_GAMEMODE", "tipo partita");
            sm = new ServerMessage(sh, pay);

            out.writeObject(sm); //Write byte stream to file system.
            out.flush();

            cm = (ClientMessage) in.readObject();

            typeGame = (String)cm.getPayload().getParameter("typeGame");

            System.out.println("ciao " + name + " hai scelto partita con " + numPlayers + " giocatori e tipo " + typeGame);
            server.lobby(this, name);

            while(isActive()){
                cm = (ClientMessage) in.readObject();
                typeGame = (String)cm.getPayload().getParameter("typeGame");
                notify(cm.toString());
            }
        } catch (IOException | NoSuchElementException | ClassNotFoundException e) {
            System.err.println("Error! " + e.getMessage());
        }finally{
            close();
        }
    }
}
