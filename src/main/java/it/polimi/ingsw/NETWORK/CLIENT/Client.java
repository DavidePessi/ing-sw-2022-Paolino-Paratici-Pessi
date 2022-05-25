package it.polimi.ingsw.NETWORK.CLIENT;
import it.polimi.ingsw.CONTROLLER.Action;
import it.polimi.ingsw.MODEL.CharacterParameters;
import it.polimi.ingsw.MODEL.Colour;
import it.polimi.ingsw.NETWORK.MESSAGES.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
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

    public Client(){
        this.ip = null;
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
                            model.update(in2);
                            model.showMoves(nick);
                        }
                            //HEADER PER CHIUSURA CONNESSIONE
                        else if(ServerAction.PING.equals(in2.getServerHeader().getServerAction())){
                            System.out.println(in2.getServerHeader().getDescription());
                            setActive(false);
                        }
                            //HEADER DI FINE PARTITA
                        else if(ServerAction.END_GAME.equals(in2.getServerHeader().getServerAction())){
                            setClientAction(ClientAction.END_GAME);
                            model.endGame(in2);
                        }
                            //HEADER DI ERRORE DI INSERIMENTO
                        else if(ServerAction.CLIENT_ERROR.equals(in2.getServerHeader().getServerAction())){
                            if(in2.getPayload().getParameter("nickname").equals(nick)) {
                                model.clientError(in2);
                                setClientAction(ClientAction.PLAY_ACTION);
                            }
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

                        //SET UP MOVES
                        if(clientAction.equals(ClientAction.SEND_NICKNAME)) {
                            ch = new ClientHeader(inputLine, ClientAction.SEND_NICKNAME);
                            pay = new Payload("nickname", inputLine);
                            cm = new ClientMessage(ch, pay);
                            nick = (String) pay.getParameter("nickname");
                            send(socketOut, cm);

                        }
                        else if(clientAction.equals(ClientAction.SEND_NUM_PLAYERS)){
                            ch = new ClientHeader(nick, ClientAction.SEND_NUM_PLAYERS);
                            int n = Integer.parseInt(inputLine);
                            pay = new Payload("numPlayer", n);
                            cm = new ClientMessage(ch, pay);
                            send(socketOut, cm);

                        }
                        else if(clientAction.equals(ClientAction.SEND_GAMEMODE)){
                            ch = new ClientHeader(nick, ClientAction.SEND_GAMEMODE);
                            pay = new Payload("typeGame", inputLine);
                            cm = new ClientMessage(ch, pay);

                            send(socketOut, cm);
                        }

                        //PLAY ACTION MOVES
                        else if(clientAction.equals(ClientAction.PLAY_ACTION)){
                            if(model.verifyClient(nick)) {
                                if (inputLine.equals("1")) {
                                    setClientAction(ClientAction.PLAY_CARD);
                                    System.out.println("you selected play card");
                                    System.out.println("insert the number of the Assistant card to play");

                                } else if (inputLine.equals("2")) {
                                    setClientAction(ClientAction.PLAY_MOVE_STUDENT_IN_DININGROOM);
                                    System.out.println("you selected move student in dining room");
                                    System.out.println("insert the colour of the student to move");

                                } else if (inputLine.equals("3")) {
                                    setClientAction(ClientAction.PLAY_MOVE_STUDENT_IN_ISLAND);
                                    System.out.println("you selected move student in island");
                                    System.out.println("insert the colour of the student to move");

                                } else if (inputLine.equals("4")) {
                                    setClientAction(ClientAction.PLAY_MOVE_MOTHERNATURE);
                                    System.out.println("you selected Move Mother Nature");
                                    System.out.println("insert the number of steps mother nature have to do");

                                } else if (inputLine.equals("5")) {
                                    setClientAction(ClientAction.PLAY_TAKE_CLOUD);
                                    System.out.println("you selected Take Cloud");
                                    System.out.println("insert the number of the cloud you want to take");

                                } else if (inputLine.equals("6")) {
                                    setClientAction(ClientAction.PLAY_CHARACTERCARD);
                                    System.out.println("you selected Play Character Card");
                                    System.out.println("insert the name of the character card you want to play");

                                } else {
                                    System.out.println("Selected move doesn't exist");
                                }
                            } else{
                                System.out.println("It's not your turn! be patient");
                            }

                        }
                        else if(clientAction.equals(ClientAction.PLAY_CARD)){
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

                        }
                        else if(clientAction.equals(ClientAction.PLAY_CHARACTERCARD)){

                            //INSERISCO I PARAMETRI PER RICONOSCERE L'AZIONE
                            pay = new Payload();
                            pay.addParameter("nickname", nick);
                            pay.addParameter("Action", Action.UseCharacter);

                            //CHIEDO I PARAMETRI PER L'AZIONE
                            CharacterParameters charPar;

                            if(inputLine.equals("Jester")){
                                List<Colour> list = new ArrayList<>();

                                System.out.println("Select how many students you want to move: 1\n2\n3\n");
                                do {
                                    inputLine = stdin.nextLine();
                                    if(!inputLine.equals("1") && !inputLine.equals("2") && !inputLine.equals("3")){
                                        System.out.println("retry: ");
                                    }
                                }while(!inputLine.equals("1") && !inputLine.equals("2") && !inputLine.equals("3"));

                                if(inputLine.equals("1")){
                                    System.out.println("select the colour from the card");
                                    inputLine = stdin.nextLine();
                                    list.add(stringToColour(inputLine, stdin));

                                    System.out.println("select the colour from the entrance");
                                    inputLine = stdin.nextLine();
                                    list.add(stringToColour(inputLine, stdin));
                                }
                                else if(inputLine.equals("2")){
                                    System.out.println("select the first colour from the card");
                                    inputLine = stdin.nextLine();
                                    list.add(stringToColour(inputLine, stdin));

                                    System.out.println("select the first colour from the entrance");
                                    inputLine = stdin.nextLine();
                                    list.add(stringToColour(inputLine, stdin));

                                    System.out.println("select the second colour from the card");
                                    inputLine = stdin.nextLine();
                                    list.add(stringToColour(inputLine, stdin));

                                    System.out.println("select the second colour from the entrance");
                                    inputLine = stdin.nextLine();
                                    list.add(stringToColour(inputLine, stdin));
                                }
                                else {
                                    System.out.println("select the first colour from the card");
                                    inputLine = stdin.nextLine();
                                    list.add(stringToColour(inputLine, stdin));

                                    System.out.println("select the first colour from the entrance");
                                    inputLine = stdin.nextLine();
                                    list.add(stringToColour(inputLine, stdin));

                                    System.out.println("select the second colour from the card");
                                    inputLine = stdin.nextLine();
                                    list.add(stringToColour(inputLine, stdin));

                                    System.out.println("select the second colour from the entrance");
                                    inputLine = stdin.nextLine();
                                    list.add(stringToColour(inputLine, stdin));

                                    System.out.println("select the third colour from the card");
                                    inputLine = stdin.nextLine();
                                    list.add(stringToColour(inputLine, stdin));

                                    System.out.println("select the third colour from the entrance");
                                    inputLine = stdin.nextLine();
                                    list.add(stringToColour(inputLine, stdin));
                                }

                                //compongo i parametri della carta
                                charPar = new CharacterParameters(nick, "Jester", list);
                            }
                            else if(inputLine.equals("Knight")){
                                charPar = new CharacterParameters(nick, "Knight");
                            }
                            else if(inputLine.equals("Minstrell")){
                                List<Colour> list = new ArrayList<>();
                                System.out.println("Select how many students you want to move: 1\n2\n");
                                do {
                                    inputLine = stdin.nextLine();
                                    if(!inputLine.equals("1") && !inputLine.equals("2")){
                                        System.out.println("retry: ");
                                    }
                                }while(!inputLine.equals("1") && !inputLine.equals("2"));

                                if(inputLine.equals("1")){
                                    System.out.println("select the colour from the entrance");
                                    inputLine = stdin.nextLine();
                                    list.add(stringToColour(inputLine, stdin));

                                    System.out.println("select the colour from the dining room");
                                    inputLine = stdin.nextLine();
                                    list.add(stringToColour(inputLine, stdin));
                                }
                                else {
                                    System.out.println("select the first colour from the entrance");
                                    inputLine = stdin.nextLine();
                                    list.add(stringToColour(inputLine, stdin));

                                    System.out.println("select the first colour from the dining room");
                                    inputLine = stdin.nextLine();
                                    list.add(stringToColour(inputLine, stdin));

                                    System.out.println("select the second colour from the entrance");
                                    inputLine = stdin.nextLine();
                                    list.add(stringToColour(inputLine, stdin));

                                    System.out.println("select the second colour from the dining room");
                                    inputLine = stdin.nextLine();
                                    list.add(stringToColour(inputLine, stdin));
                                }

                                //compongo i parametri della carta
                                charPar = new CharacterParameters(nick, "Minstrell", list);
                            }
                            else if(inputLine.equals("Pirate")){
                                System.out.println("Insert the number of the island: ");
                                inputLine = stdin.nextLine();
                                int n = Integer.parseInt(inputLine);

                                charPar = new CharacterParameters(nick, "Pirate", n);
                            }
                            else if(inputLine.equals("PostMan")){
                                charPar = new CharacterParameters(nick, "PostMan");
                            }
                            else if(inputLine.equals("Priest")){
                                System.out.println("Insert the number of the island: ");
                                inputLine = stdin.nextLine();
                                int n = Integer.parseInt(inputLine);

                                System.out.println("Insert the colour of the student: ");
                                inputLine = stdin.nextLine();

                                charPar = new CharacterParameters(nick, "Priest", n, stringToColour(inputLine, stdin));
                            }
                            else if(inputLine.equals("Satyr")){
                                charPar = new CharacterParameters(nick, "Satyr");
                            }
                            else if(inputLine.equals("Woman")){
                                System.out.println("Insert the colour of the student: ");
                                inputLine = stdin.nextLine();

                                charPar = new CharacterParameters(nick, "Woman", stringToColour(inputLine, stdin));
                            }
                            else {
                                charPar = new CharacterParameters(nick, "");
                            }//CASO ERRORE

                            pay.addParameter("CharacterParameters", charPar);

                            //INSERISCO I PARAMETRI NON UTILIZZATI
                            pay.addParameter("Colour", null);
                            pay.addParameter("num", 0);

                            //COSTRUISCO E INVIO IL MESSAGGIO
                            ch = new ClientHeader(nick, ClientAction.PLAY_CHARACTERCARD);
                            cm = new ClientMessage(ch, pay);
                            send(socketOut, cm);

                            setClientAction(ClientAction.PLAY_ACTION);


                        }
                        else if(clientAction.equals(ClientAction.PLAY_MOVE_MOTHERNATURE)){
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

                        }
                        else if(clientAction.equals(ClientAction.PLAY_MOVE_STUDENT_IN_ISLAND)){
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

                        }
                        else if(clientAction.equals(ClientAction.PLAY_MOVE_STUDENT_IN_DININGROOM)){
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

                        }
                        else if(clientAction.equals(ClientAction.PLAY_TAKE_CLOUD)){
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
                            ch = new ClientHeader(nick, ClientAction.PLAY_TAKE_CLOUD);
                            cm = new ClientMessage(ch, pay);
                            send(socketOut, cm);

                            setClientAction(ClientAction.PLAY_ACTION);
                        }

                        //END GAMES MOVES
                        else if(clientAction.equals(ClientAction.END_GAME)){
                            if(inputLine.equals("yes")){
                                setClientAction(ClientAction.SEND_NICKNAME);

                                ch = new ClientHeader(nick, ClientAction.END_GAME);
                                pay = new Payload();

                                pay.addParameter("endgame", "1");

                                cm = new ClientMessage(ch, pay);
                                send(socketOut, cm);
                            } else if(inputLine.equals("no")){
                                ch = new ClientHeader(nick, ClientAction.END_GAME);
                                pay = new Payload();

                                pay.addParameter("endgame", "0");

                                cm = new ClientMessage(ch, pay);
                                send(socketOut, cm);
                            } else {
                                System.out.println("please choose between yes and no");
                            }
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
        Scanner stdin = new Scanner(System.in);
        System.out.println("inserisci indirizzo ip: ");
        ip = stdin.nextLine();//"127.0.0.1"
        System.out.println("inserisci porta");
        port = Integer.parseInt(stdin.nextLine());//12345

        Socket socket = new Socket(ip, port);
        System.out.println("Connection estabilished");

        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream socketOut = new ObjectOutputStream(socket.getOutputStream());


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
}