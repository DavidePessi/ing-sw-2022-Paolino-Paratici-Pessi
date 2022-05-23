package it.polimi.ingsw.NETWORK.CLIENT;
import it.polimi.ingsw.CONTROLLER.Action;
import it.polimi.ingsw.MODEL.CharacterParameters;
import it.polimi.ingsw.MODEL.Colour;
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
                            setClientAction(ClientAction.SEND_NICKNAME);
                        } else if (ServerAction.SET_UP_NUM_PLAYERS.equals(in2.getServerHeader().getServerAction())) {
                            System.out.println(in2.getPayload().getParameter("SET_UP_NUM_PLAYERS"));
                            setClientAction(ClientAction.SEND_NUM_PLAYERS);
                        } else if (ServerAction.SET_UP_GAMEMODE.equals(in2.getServerHeader().getServerAction())) {
                            System.out.println(in2.getPayload().getParameter("SET_UP_GAMEMODE"));
                            setClientAction(ClientAction.SEND_GAMEMODE);
                        }
                            //HEADER DI ERRORE DI SETUP
                        else if (ServerAction.ERROR_SETUP.equals(in2.getServerHeader().getServerAction())){
                            System.out.println(in2.getPayload().getParameter("ERROR_SETUP"));
                        }
                            //HEADER DI LOBBY CREATA
                        else if (ServerAction.OK_START.equals(in2.getServerHeader().getServerAction())){
                            System.out.println(in2.getPayload().getParameter("OK_START"));
                            setClientAction(ClientAction.PLAY_ACTION);
                        }
                            //HEADER DI UPDATE BOARD
                        else if(ServerAction.UPDATE_BOARD.equals(in2.getServerHeader().getServerAction())){
                            System.out.println("prima di fare l'update");
                            model.update(in2);
                            //System.out.println("messaggio letto dal client");
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
                            //HEADER DI ERRORE DI INSERIMENTO
                        else if(ServerAction.CLIENT_ERROR.equals(in2.getServerHeader().getServerAction())){
                            if(in2.getPayload().getParameter("nickname").equals(nick)) {
                                model.clientError(in2);
                                setClientAction(ClientAction.PLAY_ACTION);
                            }
                        }
                        else if(ServerAction.PING.equals(in2.getServerHeader().getServerAction())){
                            System.out.println(in2.getServerHeader().getDescription());
                            setActive(false);
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
                        String inputLine;
                        ClientHeader ch;
                        Payload pay;
                        ClientMessage cm;

                        inputLine = stdin.nextLine();


                        if(clientAction.equals(ClientAction.SEND_NICKNAME)) {
                            ch = new ClientHeader(inputLine, ClientAction.SEND_NICKNAME);
                            pay = new Payload("nickname", inputLine);
                            cm = new ClientMessage(ch, pay);
                            nick = (String) pay.getParameter("nickname");
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
                        } else if(clientAction.equals(ClientAction.PLAY_ACTION)){
                            if(inputLine.equals("play card")){
                                setClientAction(ClientAction.PLAY_CARD);
                                System.out.println("hai selezionato mossa play card");
                                System.out.println("inserisci il numero della carta da giocare");
                            } else if(inputLine.equals("move mother nature")){
                                setClientAction(ClientAction.PLAY_MOVE_MOTHERNATURE);
                                System.out.println("hai selezionato mossa move mother nature");
                                System.out.println("inserisci il numero di passi che madre natura deve fare");
                            } else if(inputLine.equals("move student in dining room")){
                                setClientAction(ClientAction.PLAY_MOVE_STUDENT_IN_DININGROOM);
                                System.out.println("hai selezionato mossa move student in dining room");
                                System.out.println("inserisci il colore dello studente da spostare");
                            } else if(inputLine.equals("move student in island")){
                                setClientAction(ClientAction.PLAY_MOVE_STUDENT_IN_ISLAND);
                                System.out.println("hai selezionato mossa move student in island");
                                System.out.println("inserisci il colore dello studente da spostare");
                            } else if(inputLine.equals("take cloud")){
                                setClientAction(ClientAction.PLAY_TAKE_CLOUD);
                                System.out.println("hai selezionato mossa take cloud");
                                System.out.println("inserisci il numero della nuvola da spostare");
                            } else if(inputLine.equals("play character card")){
                                setClientAction(ClientAction.PLAY_CHARACTERCARD);
                                System.out.println("hai selezionato mossa play character card");
                            } else{
                                System.out.println("mossa selezionata inesistente");
                            }
                        } else if(clientAction.equals(ClientAction.PLAY_CARD)){
                            //INSERISCO I PARAMETRI PER RICONOSCERE L'AZIONE
                            pay = new Payload();
                            pay.addParameter("nickname", nick);
                            pay.addParameter("Action", Action.PlayCard);

                            //CHIEDO I PARAMETRI PER L'AZIONE
                            int n = Integer.parseInt(inputLine);
                            pay.addParameter("num", n);

                            //INSERISCO I PARAMETRI NON UTILIZZATI
                            pay.addParameter("Colour", null);
                            pay.addParameter("CharacterParameters", null);

                            //COSTRUISCO E INVIO IL MESSAGGIO
                            ch = new ClientHeader(nick, ClientAction.PLAY_CARD);
                            cm = new ClientMessage(ch, pay);
                            send(socketOut, cm);

                            setClientAction(ClientAction.PLAY_ACTION);

                        } else if(clientAction.equals(ClientAction.PLAY_CHARACTERCARD)){

                            setClientAction(ClientAction.PLAY_ACTION);
                        } else if(clientAction.equals(ClientAction.PLAY_MOVE_MOTHERNATURE)){
                            //INSERISCO I PARAMETRI PER RICONOSCERE L'AZIONE
                            pay = new Payload("nickname", nick);
                            pay.addParameter("Action", Action.MoveMotherNature);

                            //CHIEDO I PARAMETRI PER L'AZIONE
                            int n = Integer.parseInt(inputLine);
                            pay.addParameter("num", n);

                            //INSERISCO I PARAMTRI NON UTILIZZATI
                            pay.addParameter("Colour", null);
                            pay.addParameter("CharacterParameters", null);

                            //COSTRUISCO E INVIO IL MESSAGGIO
                            ch = new ClientHeader(nick, ClientAction.PLAY_MOVE_MOTHERNATURE);
                            cm = new ClientMessage(ch, pay);
                            send(socketOut, cm);

                            setClientAction(ClientAction.PLAY_ACTION);

                        } else if(clientAction.equals(ClientAction.PLAY_MOVE_STUDENT_IN_ISLAND)){
                            //INSERISCO I PARAMETRI PER RICONOSCERE L'AZIONE
                            pay = new Payload("nickname", nick);
                            pay.addParameter("Action", Action.MoveStudentInIsland);

                            //CHIEDO I PARAMETRI PER L'AZIONE
                            Colour colour = stringToColour(inputLine, stdin);
                            pay.addParameter("Colour", colour);

                            System.out.println("inserisci il numero dell'isola dove inserire lo studente");
                            inputLine = stdin.nextLine();
                            int n = Integer.parseInt(inputLine);
                            pay.addParameter("num", n-1);

                            //INSERISCO I PARAMTRI NON UTILIZZATI
                            pay.addParameter("CharacterParameters", null);

                            //COSTRUISCO E INVIO IL MESSAGGIO
                            ch = new ClientHeader(nick, ClientAction.PLAY_MOVE_STUDENT_IN_ISLAND);
                            cm = new ClientMessage(ch, pay);
                            send(socketOut, cm);

                            setClientAction(ClientAction.PLAY_ACTION);

                        } else if(clientAction.equals(ClientAction.PLAY_MOVE_STUDENT_IN_DININGROOM)){
                            //INSERISCO I PARAMETRI PER RICONOSCERE L'AZIONE
                            pay = new Payload("nickname", nick);
                            pay.addParameter("Action", Action.MoveStudentInDiningRoom);

                            //CHIEDO I PARAMETRI PER L'AZIONE
                            Colour colour = stringToColour(inputLine, stdin);
                            pay.addParameter("Colour", colour);

                            //INSERISCO I PARAMTRI NON UTILIZZATI
                            pay.addParameter("CharacterParameters", null);
                            pay.addParameter("num", 0);

                            //COSTRUISCO E INVIO IL MESSAGGIO
                            ch = new ClientHeader(nick, ClientAction.PLAY_MOVE_STUDENT_IN_DININGROOM);
                            cm = new ClientMessage(ch, pay);
                            send(socketOut, cm);

                            setClientAction(ClientAction.PLAY_ACTION);

                        } else if(clientAction.equals(ClientAction.PLAY_TAKE_CLOUD)){
                            //INSERISCO I PARAMETRI PER RICONOSCERE L'AZIONE
                            pay = new Payload("nickname", nick);
                            pay.addParameter("Action", Action.TakeCloud);

                            //CHIEDO I PARAMETRI PER L'AZIONE
                            int n = Integer.parseInt(inputLine);
                            pay.addParameter("num", n);

                            //INSERISCO I PARAMTRI NON UTILIZZATI
                            pay.addParameter("CharacterParameters", null);
                            pay.addParameter("Colour", null);

                            //COSTRUISCO E INVIO IL MESSAGGIO
                            ch = new ClientHeader(nick, ClientAction.PLAY_MOVE_STUDENT_IN_DININGROOM);
                            cm = new ClientMessage(ch, pay);
                            send(socketOut, cm);

                            setClientAction(ClientAction.PLAY_ACTION);

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

    private void setClientAction(ClientAction clientAction){
        this.clientAction = clientAction;
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

    private Colour stringToColour(String inputLine, Scanner stdin){

        while(!inputLine.equals("blue") && !inputLine.equals("yellow") && !inputLine.equals("red") && !inputLine.equals("green") && !inputLine.equals("pink")){
            System.out.println("Colore selezionato: " + inputLine);
            System.out.println("Colore non esistente riprova");
            inputLine = stdin.nextLine();
        }

        if(inputLine.equals("blue")){
            return Colour.BLUE;
        } else if(inputLine.equals("red")){
            return Colour.RED;
        } else if(inputLine.equals("yellow")){
            return Colour.YELLOW;
        } else if(inputLine.equals("green")){
            return Colour.GREEN;
        } else {
            return Colour.PINK;
        }
    }
    //public void asyncRead(){}
    //public void makeAMove(){}
}