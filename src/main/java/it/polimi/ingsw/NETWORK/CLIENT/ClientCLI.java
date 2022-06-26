package it.polimi.ingsw.NETWORK.CLIENT;
import it.polimi.ingsw.NETWORK.CLIENT.CLI.ClientModelCLI;
import it.polimi.ingsw.NETWORK.MESSAGES.*;

import java.io.*;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public final class ClientCLI {

    private static String ip;
    private static int port;
    private static ClientAction clientAction;
    private static UserInterface model;
    private static String nick = null;
    private static boolean active = true;
    private static int pingTime = 10;
    private static Scanner stdin;
    private static Socket socket;
    private static ObjectInputStream socketIn;
    private static ObjectOutputStream socketOut;

    public ClientCLI(String type){
        this.ip = null;
        this.clientAction = ClientAction.STAI_FERMO;
        stdin = new Scanner(System.in);

        if(type.equals("CLI")) {
            this.model = new ClientModelCLI(stdin);
        }
        else if(type.equals("GUI")){
            //TODO DA FARE PER LA GUI
            this.model = new ClientModelGUI();
        }
    }


    public synchronized static boolean isActive(){
        return active;
    }
    public synchronized static void setActive(boolean a){
        active = a;
    }


    public static Thread asyncReadFromSocket(final ObjectInputStream socketIn, final ObjectOutputStream socketOut){
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
                        }
                        else if (ServerAction.SET_UP_NUM_PLAYERS.equals(in2.getServerHeader().getServerAction())) {
                            System.out.println(in2.getPayload().getParameter("SET_UP_NUM_PLAYERS"));
                            setClientAction(ClientAction.SEND_NUM_PLAYERS);
                        }
                        else if (ServerAction.SET_UP_GAMEMODE.equals(in2.getServerHeader().getServerAction())) {
                            System.out.println(in2.getPayload().getParameter("SET_UP_GAMEMODE"));
                            setClientAction(ClientAction.SEND_GAMEMODE);
                            if(model instanceof ClientModelGUI) {
                                ClientModelGUI.changeToLoginPage();
                            }
                        }

                            //HEADER DI ERRORE DI SETUP
                        else if (ServerAction.ERROR_SETUP.equals(in2.getServerHeader().getServerAction())){
                            System.out.println(in2.getPayload().getParameter("ERROR_SETUP"));
                        }

                            //HEADER DI LOBBY CREATA
                        else if (ServerAction.OK_START.equals(in2.getServerHeader().getServerAction())){
                            System.out.println(in2.getPayload().getParameter("OK_START"));
                            setClientAction(ClientAction.PLAY_ACTION);
                            if(model instanceof ClientModelCLI) {
                                //ClientModelCLI.showMoves(nick);
                            }
                            else if(model instanceof ClientModelGUI) {
                                ClientModelGUI.changeToWaitingPage();
                            }
                        }

                            //HEADER DI UPDATE BOARD
                        else if(ServerAction.UPDATE_BOARD.equals(in2.getServerHeader().getServerAction())){
                            model.update(in2);
                            if(model instanceof ClientModelCLI) {
                                ClientModelCLI.showBoard();
                                ClientModelCLI.showMoves(nick);
                            }
                            else if(model instanceof ClientModelGUI){
                                System.out.println("update received");
                                ClientModelGUI.changeToBoard();
                                //ClientModelGUI.showMoves(nick);
                            }
                        }

                            //HEADER PER CHIUSURA CONNESSIONE
                        else if(ServerAction.PING.equals(in2.getServerHeader().getServerAction())){
                            ping();
                        }

                            //HEADER DI FINE PARTITA
                        else if(ServerAction.END_GAME.equals(in2.getServerHeader().getServerAction())){
                            setClientAction(ClientAction.END_GAME);
                            if(model instanceof ClientModelCLI) {
                                ClientModelCLI.endGame(in2);
                            }
                            else if(model instanceof ClientModelGUI){
                                ClientModelGUI.endGame(in2);
                            }

                        }

                            //HEADER DI ERRORE DI INSERIMENTO
                        else if(ServerAction.CLIENT_ERROR.equals(in2.getServerHeader().getServerAction())){
                            if(in2.getPayload().getParameter("nickname").equals(nick)) {
                                if(model instanceof ClientModelCLI) {
                                    ClientModelCLI.clientError(in2);
                                }
                                else if(model instanceof ClientModelGUI){
                                    System.out.println("errore di inserimento");
                                    ClientModelGUI.clientError(in2);
                                }
                                setClientAction(ClientAction.PLAY_ACTION);
                            }
                        }
                    }

                } catch (Exception e) {
                    System.out.println("eccezione read: " + e);
                    close();
                }
            }
        });
        t.start();
        return t;
    };

    public static Thread asyncWriteToSocket(final Scanner stdin, final ObjectOutputStream socketOut){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {

                        //SET UP MOVES
                        if(clientAction.equals(ClientAction.SEND_NICKNAME)) {
                            ClientMessage cm;
                            if(model instanceof ClientModelCLI) {
                                cm = ClientModelCLI.sendNickname();

                                nick = (String) cm.getPayload().getParameter("nickname");

                                send(socketOut, cm);
                            }
                            else if(model instanceof ClientModelGUI){
                                System.out.println("richiesta di messaggio per send nickname inviata");
                                cm = ClientModelGUI.sendNickname();
                                send(socketOut, cm);
                            }

                        }
                        else if(clientAction.equals(ClientAction.SEND_NUM_PLAYERS)){
                            ClientMessage cm;
                            if(model instanceof ClientModelCLI) {
                                cm = ClientModelCLI.sendNumPlayers();
                                send(socketOut, cm);
                            }
                            else if(model instanceof ClientModelGUI){
                                System.out.println("richiesta di messaggio per send num players inviata");
                                cm = ClientModelGUI.sendNumPlayers();
                                send(socketOut, cm);
                            }


                        }
                        else if(clientAction.equals(ClientAction.SEND_GAMEMODE)){
                            ClientMessage cm;
                            if(model instanceof ClientModelCLI) {
                                cm = ClientModelCLI.sendTypeGame();
                                send(socketOut, cm);
                            }
                            else if(model instanceof ClientModelGUI){
                                System.out.println("richiesta di messaggio per send gamemode inviata");
                                cm = ClientModelGUI.sendTypeGame();
                                send(socketOut, cm);
                            }
                        }

                        //PLAY ACTION MOVES
                        else if(clientAction.equals(ClientAction.PLAY_ACTION)){
                            if(model instanceof ClientModelCLI) {
                                setClientAction(ClientModelCLI.sendTypeAction());
                            }
                            else if(model instanceof ClientModelGUI){
                                System.out.println("richiesta di action inviata");
                                setClientAction(ClientModelGUI.sendTypeAction());
                            }
                        }

                        else if(clientAction.equals(ClientAction.PLAY_CARD)){
                            ClientMessage cm;
                            if(model instanceof ClientModelCLI) {
                                cm = ClientModelCLI.sendPlayCard();
                                send(socketOut, cm);
                                setClientAction(ClientAction.PLAY_ACTION);
                            }
                            else if(model instanceof ClientModelGUI){
                                System.out.println("richiesta di messaggio per play card inviata");
                                cm = ClientModelGUI.sendPlayCard();
                                send(socketOut, cm);
                                setClientAction(ClientAction.PLAY_ACTION);
                            }


                        }
                        else if(clientAction.equals(ClientAction.PLAY_CHARACTERCARD)){
                            ClientMessage cm;
                            if(model instanceof ClientModelCLI) {
                                cm = ClientModelCLI.sendPlayCharacterCard();
                                send(socketOut, cm);
                                setClientAction(ClientAction.PLAY_ACTION);
                            }
                            else if(model instanceof ClientModelGUI){
                                cm = ClientModelGUI.sendPlayCharacterCard();
                                send(socketOut, cm);
                                setClientAction(ClientAction.PLAY_ACTION);
                                System.out.println("richiesta di messaggio per play charactercard inviata");
                            }


                        }
                        else if(clientAction.equals(ClientAction.PLAY_MOVE_MOTHERNATURE)){
                            ClientMessage cm;
                            if(model instanceof ClientModelCLI) {
                                cm = ClientModelCLI.sendMoveMotherNature();
                                send(socketOut, cm);
                                setClientAction(ClientAction.PLAY_ACTION);
                            }
                            else if(model instanceof ClientModelGUI){
                                System.out.println("richiesta di messaggio per move mother nature inviata");
                                cm = ClientModelGUI.sendMoveMotherNature();
                                send(socketOut, cm);
                                setClientAction(ClientAction.PLAY_ACTION);
                            }



                        }
                        else if(clientAction.equals(ClientAction.PLAY_MOVE_STUDENT_IN_ISLAND)){
                            ClientMessage cm;
                            if(model instanceof ClientModelCLI) {
                                cm = ClientModelCLI.sendMoveStudentInIsland();
                                send(socketOut, cm);
                                setClientAction(ClientAction.PLAY_ACTION);
                            }
                            else if(model instanceof ClientModelGUI){
                                System.out.println("richiesta di messaggio per move student in island inviata");
                                cm = ClientModelGUI.sendMoveStudentInIsland();
                                send(socketOut, cm);
                                setClientAction(ClientAction.PLAY_ACTION);
                            }



                        }
                        else if(clientAction.equals(ClientAction.PLAY_MOVE_STUDENT_IN_DININGROOM)){
                            ClientMessage cm;
                            if(model instanceof ClientModelCLI) {
                                cm = ClientModelCLI.sendMoveStudentInDiningRoom();
                                send(socketOut, cm);
                                setClientAction(ClientAction.PLAY_ACTION);
                            }
                            else if(model instanceof ClientModelGUI){
                                System.out.println("richiesta di messaggio per move student in diningroom inviata");
                                cm = ClientModelGUI.sendMoveStudentInDiningRoom();
                                send(socketOut, cm);
                                setClientAction(ClientAction.PLAY_ACTION);
                            }



                        }
                        else if(clientAction.equals(ClientAction.PLAY_TAKE_CLOUD)){
                            ClientMessage cm;
                            if(model instanceof ClientModelCLI) {
                                cm = ClientModelCLI.sendTakeCloud();
                            }
                            else{
                                System.out.println("richiesta di messaggio per take cloud inviata");
                                cm = ClientModelGUI.sendTakeCloud();
                            }

                            send(socketOut, cm);
                            setClientAction(ClientAction.PLAY_ACTION);
                        }

                        //END GAMES MOVES
                        else if(clientAction.equals(ClientAction.END_GAME)){
                            String response;
                            if(model instanceof ClientModelCLI) {
                                response = ClientModelCLI.keepPlaying();
                            }
                            else if(model instanceof ClientModelGUI){
                                response = ClientModelGUI.keepPlaying();
                            }
                            else response = "no";
                            if(response.equals("yes")){
                                setClientAction(ClientAction.SEND_NICKNAME);

                                ClientHeader ch;
                                Payload pay;
                                ClientMessage cm;

                                ch = new ClientHeader(nick, ClientAction.END_GAME);
                                pay = new Payload();
                                pay.addParameter("endgame", "1");

                                cm = new ClientMessage(ch, pay);
                                send(socketOut, cm);

                            } else if(response.equals("no")){
                                ClientHeader ch;
                                Payload pay;
                                ClientMessage cm;

                                ch = new ClientHeader(nick, ClientAction.END_GAME);
                                pay = new Payload();
                                pay.addParameter("endgame", "0");

                                cm = new ClientMessage(ch, pay);
                                send(socketOut, cm);

                            } else {
                                System.out.println("please choose between yes and no");
                            }
                        }
                        Thread.sleep(100);
                    }
                }catch(Exception e){
                    System.out.println("eccezione scrittura: " + e);
                    close();
                }
            }
        });
        t.start();
        return t;
    }

    private static void close()  {
        setActive(false);
        try {
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }catch(IOException e){}
        System.out.println("Connection closed from the client side");
    }

    public static void run(String ip1, int port1) throws IOException{

        ip = ip1;
        port = port1;

        socket = new Socket(ip, port);
        System.out.println("Connection estabilished");

        socketIn = new ObjectInputStream(socket.getInputStream());
        socketOut = new ObjectOutputStream(socket.getOutputStream());

        //PING
        Thread p1 = asyncPing(socketOut);
        Thread p2 = asyncPingDecrease();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(p1);
        executor.submit(p2);


        //String socketLine;
        try{
            Thread t0 = asyncReadFromSocket(socketIn, socketOut);
            Thread t1 = asyncWriteToSocket(stdin, socketOut);

        } catch(NoSuchElementException e){
            System.out.println("Exception lato client");
        }
    }

    private static void setClientAction(ClientAction clientA){
        clientAction = clientA;
    }


    //METODI RELATIVI AL PING E AL NETWORK

    private static synchronized void send(ObjectOutputStream socketOut,ClientMessage cm){
        try {
            socketOut.writeObject(cm); //Write byte stream to file system.
            socketOut.flush();
            socketOut.reset();
        }catch(IOException e){}
    }

    private static Thread asyncPing(ObjectOutputStream socketOut) {
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

    private static Thread asyncPingDecrease(){

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
                if(!isActive()) {
                    pingTime = 0;
                }
            }
        });
        return t;
    }
    private static void ping() {
        pingTime = 10;
    }
}