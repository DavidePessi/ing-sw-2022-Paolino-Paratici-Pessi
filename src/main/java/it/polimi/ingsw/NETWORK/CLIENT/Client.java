package it.polimi.ingsw.NETWORK.CLIENT;

import it.polimi.ingsw.MODEL.Game;
import it.polimi.ingsw.NETWORK.MESSAGES.*;
import it.polimi.ingsw.NETWORK.SERVER.ClientConnection;

import java.io.*;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {


    private String ip;
    private int port;
    private ClientAction clientAction;

    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;
        this.clientAction = ClientAction.SEND_NICKNAME;
    }
    private boolean active = true;


    public synchronized boolean isActive(){
        return active;
    }
    public synchronized void setActive(boolean active){
        this.active = active;
    }


    public Thread asyncReadFromSocket(final ObjectInputStream socketIn){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {

                        //leggo da inputStream
                        Object inputObject =  socketIn.readObject();

                        ServerMessage in2 = (ServerMessage) inputObject;
                        //verifico che è un messaggio di servizio e non un update quindi che il suo
                        //header contenga SET_UP
                            //sposto l'azione che può effettuare il client alla successiva
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




                    }

                } catch (Exception e) {
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
                String nick = null;
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
                            socketOut.writeObject(cm); //Write byte stream to file system.
                            socketOut.flush();

                        } else if(clientAction.equals(ClientAction.SEND_NUM_PLAYERS)){
                            ch = new ClientHeader(nick, ClientAction.SEND_NUM_PLAYERS);
                            int n = Integer.parseInt(inputLine);
                            pay = new Payload("numPlayer", n);
                            cm = new ClientMessage(ch, pay);
                            //System.out.println("send_num (nickname) " + nick);
                            //System.out.println("send_num " + n);
                            socketOut.writeObject(cm); //Write byte stream to file system.
                            socketOut.flush();

                        } else if(clientAction.equals(ClientAction.SEND_GAMEMODE)){
                            ch = new ClientHeader(nick, ClientAction.SEND_GAMEMODE);
                            pay = new Payload("typeGame", inputLine);
                            cm = new ClientMessage(ch, pay);

                            socketOut.writeObject(cm); //Write byte stream to file system.
                            socketOut.flush();
                        }

                    }
                }catch(Exception e){
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
        //Scanner socketIn = new Scanner(socket.getInputStream());
        //PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
        ObjectOutputStream socketOut = new ObjectOutputStream(socket.getOutputStream());
        Scanner stdin = new Scanner(System.in);
        //String socketLine;
        try{
            Thread t0 = asyncReadFromSocket(socketIn);
            Thread t1 = asyncWriteToSocket(stdin, socketOut);
            t0.join();
            t1.join();
        } catch(InterruptedException | NoSuchElementException e){
            System.out.println("Connection closed from the client side");
        } finally {
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }


    //crea la connessione
    //invio il nickname alla connessione
    //inviamo la modalità di gioco da fare alla connessione
    }

    //public void asyncRead(){}
    //public void makeAMove(){}
}