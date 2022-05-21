package it.polimi.ingsw.NETWORK.CLIENT;
import it.polimi.ingsw.NETWORK.MESSAGES.*;

import java.io.*;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Client {

    private String ip;
    private int port;
    private ClientAction clientAction;
    private ClientModelCLI model;
    private String nick = null;
    private boolean active = true;

    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;
        this.clientAction = ClientAction.SEND_NICKNAME;
        this.model = new ClientModelCLI();
    }


    public synchronized boolean isActive(){
        return active;
    }
    public synchronized void setActive(boolean active){
        this.active = active;
    }


    public Thread asyncReadFromSocket(final ObjectInputStream socketIn, final ObjectOutputStream socketOut){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {

                        //leggo da inputStream
                        Object inputObject =  socketIn.readObject();

                        ServerMessage in2 = (ServerMessage) inputObject;
                        //CONTROLLO L'HEADER PER VERIFICARE IL TIPO DI MESSAGGIO
                            //HEASER DI SET UP
                        if (ServerAction.SET_UP_NICKNAME.equals(in2.getServerHeader().getServerAction())) {
                            System.out.println(in2.getPayload().getParameter("SET_UP_NICKNAME"));
                            clientAction = ClientAction.SEND_NICKNAME;
                        } else if (ServerAction.SET_UP_NUM_PLAYERS.equals(in2.getServerHeader().getServerAction())) {
                            System.out.println(in2.getPayload().getParameter("SET_UP_NUM_PLAYERS"));
                            clientAction = ClientAction.SEND_NUM_PLAYERS;
                        } else if (ServerAction.SET_UP_GAMEMODE.equals(in2.getServerHeader().getServerAction())) {
                            System.out.println(in2.getPayload().getParameter("SET_UP_GAMEMODE"));
                            clientAction = ClientAction.SEND_GAMEMODE;
                        }
                            //HEADER DI ERRORE DI SETUP
                        else if (ServerAction.ERROR_SETUP.equals(in2.getServerHeader().getServerAction())){
                            System.out.println(in2.getPayload().getParameter("ERROR_SETUP"));
                        }
                            //HEADER DI INVIO BOARD INIZIALE
                        else if (ServerAction.OK_START.equals(in2.getServerHeader().getServerAction())){
                            System.out.println(in2.getPayload().getParameter("OK_START"));
                            clientAction = ClientAction.SEND_GAMEMODE;
                        }
                            //HEADER DI UPDATE BOARD
                        else if(ServerAction.UPDATE_BOARD.equals(in2.getServerHeader().getServerAction())){
                            model.update(in2);
                            System.out.println("messaggio letto dal client");
                        }
                            //HEADER PER CHIUSURA CONNESSIONE
                        else if(ServerAction.PING.equals(in2.getServerHeader().getServerAction())){
                            System.out.println(in2.getServerHeader().getDescription());
                            setActive(false);
                        }
                            //HEADER DI FINE PARTITA
                        else if(ServerAction.END_GAME.equals(in2.getServerHeader().getServerAction())){
                            //model.endGame(in2, nick);
                        }
                    }

                } catch (Exception e) {
                    System.out.println("eccezione read: " + e);
                    setActive(false);
                }
            }
        });
        t.start();
        return t;
    }

    public Thread asyncWriteToSocket(final Scanner stdin, final ObjectOutputStream socketOut){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {

                        String inputLine = stdin.nextLine();
                        ClientHeader ch;
                        Payload pay;
                        ClientMessage cm;

                        if(clientAction.equals(ClientAction.SEND_NICKNAME)) {

                            ch = new ClientHeader(inputLine, ClientAction.SEND_NICKNAME);
                            pay = new Payload("nickname", inputLine);
                            cm = new ClientMessage(ch, pay);
                            nick = (String) pay.getParameter("nickname");
                            //System.out.println("send_nick" + nick);
                            send(socketOut, cm);

                        } else if(clientAction.equals(ClientAction.SEND_NUM_PLAYERS)){
                            ch = new ClientHeader(nick, ClientAction.SEND_NUM_PLAYERS);
                            int n = Integer.parseInt(inputLine);
                            pay = new Payload("numPlayer", n);
                            cm = new ClientMessage(ch, pay);
                            //System.out.println("send_num (nickname) " + nick);
                            //System.out.println("send_num " + n);
                            send(socketOut, cm);

                        } else if(clientAction.equals(ClientAction.SEND_GAMEMODE)){
                            ch = new ClientHeader(nick, ClientAction.SEND_GAMEMODE);
                            pay = new Payload("typeGame", inputLine);
                            cm = new ClientMessage(ch, pay);

                            send(socketOut, cm);
                        }

                    }
                }catch(Exception e){
                    System.out.println("eccezione scrittura: " + e);
                    setActive(false);
                }
            }
        });
        t.start();
        return t;
    }

    public void run() throws IOException{
        Socket socket = new Socket(ip, port);
        System.out.println("Connection estabilished");

        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream socketOut = new ObjectOutputStream(socket.getOutputStream());
        Scanner stdin = new Scanner(System.in);

        //PING
        Thread t = asyncPing(socketOut);
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.submit(t);


        //String socketLine;
        try{
            Thread t0 = asyncReadFromSocket(socketIn, socketOut);
            Thread t1 = asyncWriteToSocket(stdin, socketOut);
            t0.join();
            System.out.println("chiusura lettura");
            t1.join();
            System.out.println("chiusura scrittura");
        } catch(InterruptedException | NoSuchElementException e){
            System.out.println("Exception lato client");
        } finally {
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
            System.out.println("Connection closed from the client side");
        }

    }

    private Thread asyncPing(ObjectOutputStream socketOut) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isActive()) {
                    try {
                        ClientHeader ch;
                        Payload pay;
                        ClientMessage cm;
                        while (true) {
                            ch = new ClientHeader(nick, ClientAction.PING);
                            pay = new Payload();
                            cm = new ClientMessage(ch, pay);

                            send(socketOut, cm);

                            Thread.sleep(1000);
                        }
                    }catch(InterruptedException e ){}
                }
            }
        });
        return t;
    }

    private synchronized void send(ObjectOutputStream socketOut,ClientMessage cm){
        try {
            socketOut.writeObject(cm); //Write byte stream to file system.
            socketOut.flush();
            socketOut.reset();
        }catch(IOException e){}
    }

    //public void asyncRead(){}
    //public void makeAMove(){}
}