package it.polimi.ingsw.NETWORK.CLIENT.CLI;

import it.polimi.ingsw.CONTROLLER.Action;
import it.polimi.ingsw.MODEL.*;
import it.polimi.ingsw.MODEL.CharacterCards.ConcreteCharacterCard;
import it.polimi.ingsw.MODEL.CharacterCards.Jester;
import it.polimi.ingsw.MODEL.CharacterCards.Priest;
import it.polimi.ingsw.MODEL.CharacterCards.Woman;
import it.polimi.ingsw.MODEL.Exception.MissingCardException;
import it.polimi.ingsw.MODEL.Exception.MissingTowerException;
import it.polimi.ingsw.NETWORK.CLIENT.UserInterface;
import it.polimi.ingsw.NETWORK.MESSAGES.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class ClientModelCLI extends UserInterface{

    private static Scanner stdin;
    private static String nick;

    public ClientModelCLI(Scanner std){
        super();

        stdin = std;
    }

    /**
     * shows the board at the client
     */
    public static void showBoard(){

        String board = "";

        //-------------------------------STAMPA PLAYER------------------------------
        board = board + showPlayer();

        //-------------------------STAMPA CARTE PERSONAGGIO-------------------------
        if(characterCards != null) {
            board = board + ("Character Cards :\n");
            for (ConcreteCharacterCard card : characterCards) {
                board = board + ("\t" + card.getNameCard() + " cost: " + card.getPrice());
                if(card instanceof Jester){
                    Jester j = (Jester) card;
                    String colore = "";
                    board = board + "[";

                    for(int k = 0; k < j.getPool().size(); k++){

                        //setto il colore della stringa
                        if(j.getPool().get(k).getColour().equals(Colour.BLUE)){
                            colore = "Blue";
                        } else if(j.getPool().get(k).getColour().equals(Colour.RED)){
                            colore = "Red";
                        } else if(j.getPool().get(k).getColour().equals(Colour.PINK)){
                            colore = "Pink";
                        } else if(j.getPool().get(k).getColour().equals(Colour.GREEN)){
                            colore = "Green";
                        } else if(j.getPool().get(k).getColour().equals(Colour.YELLOW)){
                            colore = "Yellow";
                        }

                        board = board + colore + " ";
                    }
                    board = board + "]\n";
                } else if(card instanceof Priest){
                    Priest j = (Priest) card;
                    String colore = "";
                    board = board + "[";

                    for(int k = 0; k < j.getPool().size(); k++){

                        //setto il colore della stringa
                        if(j.getPool().get(k).getColour().equals(Colour.BLUE)){
                            colore = "Blue";
                        } else if(j.getPool().get(k).getColour().equals(Colour.RED)){
                            colore = "Red";
                        } else if(j.getPool().get(k).getColour().equals(Colour.PINK)){
                            colore = "Pink";
                        } else if(j.getPool().get(k).getColour().equals(Colour.GREEN)){
                            colore = "Green";
                        } else if(j.getPool().get(k).getColour().equals(Colour.YELLOW)){
                            colore = "Yellow";
                        }

                        board = board + colore + " ";
                    }
                    board = board + "]\n";
                } else if(card instanceof Woman){
                    Woman j = (Woman) card;
                    String colore = "";
                    board = board + "[";

                    for(int k = 0; k < j.getPool().size(); k++){

                        //setto il colore della stringa
                        if(j.getPool().get(k).getColour().equals(Colour.BLUE)){
                            colore = "Blue";
                        } else if(j.getPool().get(k).getColour().equals(Colour.RED)){
                            colore = "Red";
                        } else if(j.getPool().get(k).getColour().equals(Colour.PINK)){
                            colore = "Pink";
                        } else if(j.getPool().get(k).getColour().equals(Colour.GREEN)){
                            colore = "Green";
                        } else if(j.getPool().get(k).getColour().equals(Colour.YELLOW)){
                            colore = "Yellow";
                        }

                        board = board + colore + " ";
                    }
                    board = board + "]\n";
                } else{
                    board = board + "\n";
                }
            }
        }


        //-------------------------------STAMPA ISOLE-------------------------------
        board = board + showIsland();


        //------------------------------STAMPA NUVOLE-------------------------------
        board = board + showCloud();

        System.out.println(board);
    }

    /**
     * shows the cloud at the client
     * @return
     */
    private static String showCloud(){
        String cloud = "";
        String colore = "";

        for(int j = 0; j < listCloud.size(); j++){
            cloud = cloud + "cloud " + j + ": {";

            if(!listCloud.get(j).empty()){
                if(listPlayer.size() == 3) {
                    for (int i = 0; i < 4; i++) {
                        if (listCloud.get(j).getStudent(i).getColour().equals(Colour.BLUE)) {
                            colore = "Blue";
                        } else if (listCloud.get(j).getStudent(i).getColour().equals(Colour.RED)) {
                            colore = "Red";
                        } else if (listCloud.get(j).getStudent(i).getColour().equals(Colour.PINK)) {
                            colore = "Pink";
                        } else if (listCloud.get(j).getStudent(i).getColour().equals(Colour.GREEN)) {
                            colore = "Green";
                        } else if (listCloud.get(j).getStudent(i).getColour().equals(Colour.YELLOW)) {
                            colore = "Yellow";
                        }
                        cloud = cloud + colore + " ";
                    }
                }
                else{
                    for (int i = 0; i < 3; i++) {
                        if (listCloud.get(j).getStudent(i).getColour().equals(Colour.BLUE)) {
                            colore = "Blue";
                        } else if (listCloud.get(j).getStudent(i).getColour().equals(Colour.RED)) {
                            colore = "Red";
                        } else if (listCloud.get(j).getStudent(i).getColour().equals(Colour.PINK)) {
                            colore = "Pink";
                        } else if (listCloud.get(j).getStudent(i).getColour().equals(Colour.GREEN)) {
                            colore = "Green";
                        } else if (listCloud.get(j).getStudent(i).getColour().equals(Colour.YELLOW)) {
                            colore = "Yellow";
                        }
                        cloud = cloud + colore + " ";
                    }
                }
            }
            cloud = cloud + "}\n";
        }
        return cloud;
    }

    /**
     * shows the Players at the client
     * @return
     */
    private static String showPlayer(){
        String players = "";

        for(Player player : listPlayer){
            //stampa nickname
            players = players +("player: " + player.getNicknameClient());

            //stampa torri e team di appartenenza
            players = players +("\nteam: " + player.getTeam().getColourTower() + "{" + player.getTeam().getNumberOfTower() + "}");

            //stampa carte assistente
            players = players + "\nAssistant Cards: ";
            for(int i = 0; i < player.getDeck().size(); i++){
                try {
                    players = players + "(" + player.getDeck().getCardOfIndex(i).getValue() + ",";
                    players = players + player.getDeck().getCardOfIndex(i).getMovement() + ") ";
                }catch(MissingCardException e){
                    System.out.println(e.getMessage());
                }
            }

            //numero di coins
            if(showCoins) {
                players = players + ("\nnum coins: " + player.getNumCoins());
            }

            //metto i professori
            players = players + "\nprofessors: ";

            if(player.professorPresent(Colour.RED))players = players + "RedProf ";
            if(player.professorPresent(Colour.YELLOW))players = players + "YellowProf";
            if(player.professorPresent(Colour.GREEN))players = players + (char) 27 + "GreenProf";
            if(player.professorPresent(Colour.BLUE))players = players + (char) 27 + "BlueProf";
            if(player.professorPresent(Colour.PINK))players = players + (char) 27 + "PinkProf";

            players = players +( "\nentrance : \n");

            players = players + "Red: " + player.getEntrance().getStudentGroup().countStudentsOfColour(Colour.RED) + "\n";

            players = players + "Blue: " + player.getEntrance().getStudentGroup().countStudentsOfColour(Colour.BLUE) + "\n";

            players = players + "Green: " + player.getEntrance().getStudentGroup().countStudentsOfColour(Colour.GREEN) + "\n";

            players = players + "Yellow: " + player.getEntrance().getStudentGroup().countStudentsOfColour(Colour.YELLOW) + "\n";

            players = players + "Pink: " + player.getEntrance().getStudentGroup().countStudentsOfColour(Colour.PINK) + "\n";



            players = players +( "DiningRoom : \n");

            players = players + "Red: " + player.numStudentsDiningRoom(Colour.RED) + "\n";

            players = players + "Blue: " + player.numStudentsDiningRoom(Colour.BLUE) + "\n";

            players = players + "Green: " + player.numStudentsDiningRoom(Colour.GREEN) + "\n";

            players = players + "Yellow: " + player.numStudentsDiningRoom(Colour.YELLOW) + "\n";

            players = players + "Pink: " + player.numStudentsDiningRoom(Colour.PINK) + "\n";


        }
        return players;
    }

    /**
     * shows the Islands at the client
     * @return
     */
    private static String showIsland(){
        String island = "";
        String colore = "";
        int max = 6;

        island = island +( "Islands :\n\t");



        /////////////////////////////////////////////////////////////////////////SECONDA FILA DI ISOLE
        island = island +( "\t");
        //scrivo i numeri sopra
        for(int i = 0; i < listIsland.size(); i++){
            island = island +"\n------isola " + (listIsland.get(i).getNumIsland()+1)+ "-------\n";


            //stampo gli studenti per colore sulle isole
            for(Colour colour : Colour.values()){

                //setto il colore della stringa
                if(colour.equals(Colour.BLUE)){
                    colore = "Blue";

                } else if(colour.equals(Colour.RED)){
                    colore = "Red";

                } else if(colour.equals(Colour.PINK)){
                    colore = "Pink";

                } else if(colour.equals(Colour.GREEN)){
                    colore = "Green";

                } else if(colour.equals(Colour.YELLOW)){
                    colore = "Yellow";

                }

                island = island + (colore +": " + listIsland.get(i).countStudentsOfColour(colour));
                island = island +("\n");
            }

            try{
                if(listIsland.get(i).getColourTower().equals(ColourTower.BLACK)){
                    island = island + "BlackTower: " + listIsland.get(i).getNumSubIsland()+ "\n";

                } else if(listIsland.get(i).getColourTower().equals(ColourTower.WHITE)){
                    island = island + "WhiteTower: " + listIsland.get(i).getNumSubIsland()+ "\n";

                } else if(listIsland.get(i).getColourTower().equals(ColourTower.GREY)){
                    island = island + "GreyTower: " + listIsland.get(i).getNumSubIsland()+ "\n";
                }
            }catch(MissingTowerException e){}
            if(listIsland.get(i).getHasMotherNature()) {
                island = island + "֍mothernature֍" + "\n";
            }
        }

        island = island + "\n";

        return island;
    }

    /**
     * show the moves that the client chose
     * @param nickname
     */
    public static void showMoves(String nickname){
        if(ClientModelCLI.currentPlayer.equals(nickname)){
            System.out.println("it's your turn!");
            System.out.println("Select the number of the move you want to make: ");
            System.out.println("1) Play Card");
            System.out.println("2) Move Student in Dining Room");
            System.out.println("3) Move Student in Island");
            System.out.println("4) Move Mother Nature");
            System.out.println("5) Take Cloud");

            if(showCoins){
                System.out.println("6) Play Character Card");
            }
        }
    }

    /**
     * print the error that the client has committed
     * @param message
     */
    public static void clientError(ServerMessage message){
        System.out.println(message.getServerHeader().getDescription());
    }

    /**
     * set the winner team and send a message
     * @param message
     */
    public static void endGame(ServerMessage message){
        Team t = (Team) message.getPayload().getParameter("team");
        System.out.println("il team vincitore è il: " + t.getColourTower());
        System.out.println("vuoi giocare un'altra partita? ");
    }

    /**
     * allows to send a nickname to the server
     * @return
     */
    public static ClientMessage sendNickname(){
        String inputLine;
        ClientHeader ch;
        Payload pay;
        ClientMessage cm;

        inputLine = stdin.nextLine();

        ch = new ClientHeader(inputLine, ClientAction.SEND_NICKNAME);
        pay = new Payload("nickname", inputLine);
        cm = new ClientMessage(ch, pay);
        nick = (String) pay.getParameter("nickname");

        return cm;
    }

    /**
     * allows to send the type of game to the server
     * @return
     */
    public static ClientMessage sendTypeGame(){
        String inputLine;
        ClientHeader ch;
        Payload pay;
        ClientMessage cm;

        inputLine = stdin.nextLine();

        ch = new ClientHeader(nick, ClientAction.SEND_GAMEMODE);
        pay = new Payload("typeGame", inputLine);
        cm = new ClientMessage(ch, pay);

        return cm;
    }

    /**
     * allows to send the number of the player to the server
     * @return
     */
    public static ClientMessage sendNumPlayers(){
        String inputLine;
        ClientHeader ch;
        Payload pay;
        ClientMessage cm;

        inputLine = stdin.nextLine();

        ch = new ClientHeader(nick, ClientAction.SEND_NUM_PLAYERS);
        int n = stringToInt(inputLine);
        pay = new Payload("numPlayer", n);
        cm = new ClientMessage(ch, pay);

        return cm;
    }

    /**
     * allows to send the type of action chose by the client
     * @return
     * @throws Exception
     */
    public static ClientAction sendTypeAction()throws Exception{
        String inputLine;
        ClientHeader ch;
        Payload pay;
        ClientMessage cm;

        inputLine = stdin.nextLine();


        if (inputLine.equals("1")) {
            System.out.println("you selected play card");
            System.out.println("insert the number of the Assistant card to play");

            return ClientAction.PLAY_CARD;

        } else if (inputLine.equals("2")) {
            System.out.println("you selected move student in dining room");
            System.out.println("insert the colour of the student to move");

            return ClientAction.PLAY_MOVE_STUDENT_IN_DININGROOM;

        } else if (inputLine.equals("3")) {
            System.out.println("you selected move student in island");
            System.out.println("insert the colour of the student to move");

            return ClientAction.PLAY_MOVE_STUDENT_IN_ISLAND;

        } else if (inputLine.equals("4")) {
            System.out.println("you selected Move Mother Nature");
            System.out.println("insert the number of steps mother nature have to do");

            return ClientAction.PLAY_MOVE_MOTHERNATURE;

        } else if (inputLine.equals("5")) {
            System.out.println("you selected Take Cloud");
            System.out.println("insert the number of the cloud you want to take");

            return ClientAction.PLAY_TAKE_CLOUD;

        } else if (inputLine.equals("6")) {
            System.out.println("you selected Play Character Card");
            System.out.println("insert the name of the character card you want to play");

            return ClientAction.PLAY_CHARACTERCARD;

        } else {
            System.out.println("Selected move doesn't exist");

            return ClientAction.PLAY_ACTION;
        }
    }

    /**
     * allows to send the played card chose by the client
     * @return
     */
    public static ClientMessage sendPlayCard(){
        String inputLine;
        ClientHeader ch;
        Payload pay;
        ClientMessage cm;

        inputLine = stdin.nextLine();

        //INSERISCO I PARAMETRI PER RICONOSCERE L'AZIONE
        pay = new Payload();
        pay.addParameter("nickname", nick);
        pay.addParameter("Action", Action.PlayCard);

        //CHIEDO I PARAMETRI PER L'AZIONE
        int n = stringToInt(inputLine);
        pay.addParameter("num", n);

        //INSERISCO I PARAMETRI NON UTILIZZATI
        pay.addParameter("Colour", null);
        pay.addParameter("CharacterParameters", null);

        //COSTRUISCO E INVIO IL MESSAGGIO
        ch = new ClientHeader(nick, ClientAction.PLAY_CARD);
        cm = new ClientMessage(ch, pay);

        return cm;
    }

    /**
     * allows to send the character card played chose by the client
     * @return
     */
    public static ClientMessage sendPlayCharacterCard(){
        String inputLine;
        ClientHeader ch;
        Payload pay;
        ClientMessage cm;

        inputLine = stdin.nextLine();

        //INSERISCO I PARAMETRI PER RICONOSCERE L'AZIONE
        pay = new Payload();
        pay.addParameter("nickname", nick);
        pay.addParameter("Action", Action.UseCharacter);

        //CHIEDO I PARAMETRI PER L'AZIONE
        CharacterParameters charPar;

        if(inputLine.equals("Jester")){
            List<Colour> list = new ArrayList<>();
            int n;
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
                n = 1;
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
                n = 2;
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
                n = 3;
            }

            //compongo i parametri della carta
            charPar = new CharacterParameters(nick, "Jester", list, n);
        }
        else if(inputLine.equals("Knight")){
            charPar = new CharacterParameters(nick, "Knight");
        }
        else if(inputLine.equals("Minstrell")){
            List<Colour> list = new ArrayList<>();
            int n;
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
                n = 1;
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
                n = 2;
            }

            //compongo i parametri della carta
            charPar = new CharacterParameters(nick, "Minstrell", list, n);
        }
        else if(inputLine.equals("Pirate")){
            System.out.println("Insert the number of the island: ");
            inputLine = stdin.nextLine();
            int n = stringToInt(inputLine);

            charPar = new CharacterParameters(nick, "Pirate", n);
        }
        else if(inputLine.equals("PostMan")){
            charPar = new CharacterParameters(nick, "PostMan");
        }
        else if(inputLine.equals("Priest")){
            System.out.println("Insert the number of the island: ");
            inputLine = stdin.nextLine();
            int n = stringToInt(inputLine);

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

        return cm;
    }

    /**
     * allows to send the num of movement of mother nature
     * @return
     */
    public static ClientMessage sendMoveMotherNature(){
        String inputLine;
        ClientHeader ch;
        Payload pay;
        ClientMessage cm;

        inputLine = stdin.nextLine();

        //INSERISCO I PARAMETRI PER RICONOSCERE L'AZIONE
        pay = new Payload("nickname", nick);
        pay.addParameter("Action", Action.MoveMotherNature);

        //CHIEDO I PARAMETRI PER L'AZIONE
        int n = stringToInt(inputLine);
        pay.addParameter("num", n);

        //INSERISCO I PARAMTRI NON UTILIZZATI
        pay.addParameter("Colour", null);
        pay.addParameter("CharacterParameters", null);

        //COSTRUISCO E INVIO IL MESSAGGIO
        ch = new ClientHeader(nick, ClientAction.PLAY_MOVE_MOTHERNATURE);
        cm = new ClientMessage(ch, pay);

        return cm;
    }

    /**
     * allows you to send the color that the client wishes to move to the dining room to the server
     * @return
     */
    public static ClientMessage sendMoveStudentInDiningRoom(){
        String inputLine;
        ClientHeader ch;
        Payload pay;
        ClientMessage cm;

        inputLine = stdin.nextLine();

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

        return cm;
    }

    /**
     *  allows you to send the color that the client wishes to move to the island to the server
     * @return
     */
    public static ClientMessage sendMoveStudentInIsland(){
        String inputLine;
        ClientHeader ch;
        Payload pay;
        ClientMessage cm;

        inputLine = stdin.nextLine();

        //INSERISCO I PARAMETRI PER RICONOSCERE L'AZIONE
        pay = new Payload("nickname", nick);
        pay.addParameter("Action", Action.MoveStudentInIsland);

        //CHIEDO I PARAMETRI PER L'AZIONE
        Colour colour = stringToColour(inputLine, stdin);
        pay.addParameter("Colour", colour);

        System.out.println("inserisci il numero dell'isola dove inserire lo studente");
        inputLine = stdin.nextLine();
        int n = stringToInt(inputLine);
        pay.addParameter("num", n-1);

        //INSERISCO I PARAMTRI NON UTILIZZATI
        pay.addParameter("CharacterParameters", null);

        //COSTRUISCO E INVIO IL MESSAGGIO
        ch = new ClientHeader(nick, ClientAction.PLAY_MOVE_STUDENT_IN_ISLAND);
        cm = new ClientMessage(ch, pay);

        return cm;
    }

    /**
     * allows to take the cloud chosen by the client
     * @return
     */
    public static ClientMessage sendTakeCloud(){
        String inputLine;
        ClientHeader ch;
        Payload pay;
        ClientMessage cm;

        inputLine = stdin.nextLine();

        //INSERISCO I PARAMETRI PER RICONOSCERE L'AZIONE
        pay = new Payload("nickname", nick);
        pay.addParameter("Action", Action.TakeCloud);

        //CHIEDO I PARAMETRI PER L'AZIONE

        int n = stringToInt(inputLine);
        pay.addParameter("num", n);

        //INSERISCO I PARAMTRI NON UTILIZZATI
        pay.addParameter("CharacterParameters", null);
        pay.addParameter("Colour", null);

        //COSTRUISCO E INVIO IL MESSAGGIO
        ch = new ClientHeader(nick, ClientAction.PLAY_TAKE_CLOUD);
        cm = new ClientMessage(ch, pay);

        return cm;
    }

    /**
     * allows you to continue playing
     * @return
     */
    public static String keepPlaying(){
        String inputLine;
        inputLine = stdin.nextLine();

        return inputLine;
    }

    /**
     * converts from string to colour
     * @param inputLine
     * @param stdin
     * @return
     */
    private static Colour stringToColour(String inputLine, Scanner stdin){

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

    /**
     * converts from string to int
     * @param inputLine
     * @return
     */
    private static int stringToInt(String inputLine){
        int n;
        if(inputLine.equals("12345")){
            n = 12345;
        }
        if(inputLine.equals("0")){
            n = 0;
        }else if(inputLine.equals("1")){
            n = 1;
        }else if(inputLine.equals("2")){
            n = 2;
        }else if(inputLine.equals("3")){
            n = 3;
        }else if(inputLine.equals("4")){
            n = 4;
        }else if(inputLine.equals("5")){
            n = 5;
        }else if(inputLine.equals("6")){
            n = 6;
        }else if(inputLine.equals("7")){
            n = 7;
        }else if(inputLine.equals("8")){
            n = 8;
        }else if(inputLine.equals("9")){
            n = 9;
        }else if(inputLine.equals("10")){
            n = 10;
        }else if(inputLine.equals("11")){
            n = 11;
        }else if(inputLine.equals("12")){
            n = 12;
        }else{
            n = 100;
        }

        return n;
    }

}