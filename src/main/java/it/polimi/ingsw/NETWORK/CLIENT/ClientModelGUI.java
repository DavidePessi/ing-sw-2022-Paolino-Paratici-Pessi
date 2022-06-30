package it.polimi.ingsw.NETWORK.CLIENT;

import it.polimi.ingsw.CONTROLLER.Action;
import it.polimi.ingsw.MODEL.*;
import it.polimi.ingsw.NETWORK.CLIENT.LoginController;
import it.polimi.ingsw.NETWORK.CLIENT.UserInterface;
import it.polimi.ingsw.NETWORK.MESSAGES.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class ClientModelGUI extends UserInterface {

    private static boolean buttonIsClicked = false;

    private static ClientAction action;

    private static String actionPlayed;
    private static String actionPlayed2;
    private static String actionPlayed3;
    private static String actionPlayed4;
    private static String actionPlayed5;
    private static String actionPlayed6;
    private static String actionPlayed7;
    private static String actionPlayed8;

    private static int colors1 = 0;
    private static int colors2 = 0;

    protected static String nickname = "";
    private static String cardThrown = "";
    protected static boolean boardCreated = false;
    protected static Stage stage = new Stage();
    private static LoginController controller;
    public static ColourTower winner = ColourTower.NO_ONE;

    static {
        try {
            controller = new LoginController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ClientModelGUI(){

        super();
    }

    /**
     * it show if something is wrong
     * @param message
     */
    public static void clientError(ServerMessage message){}

    /**
     * allows to send a nickname to the server
     * @return
     */
    public static ClientMessage sendNickname()throws Exception{

        ClientHeader ch;
        Payload pay;
        ClientMessage cm;


        while(!controller.getButtonIsClicked() && !ClientCLI.getClose()){
            try{Thread.sleep(100);}catch(Exception e){}
        }

        if(ClientCLI.getClose()){throw new Exception();}

        String nick = controller.sendNickname();
        nickname = nick;

        ch = new ClientHeader(nickname, ClientAction.SEND_NICKNAME);
        pay = new Payload("nickname", nick);
        cm = new ClientMessage(ch, pay);

        return cm;
    }

    /**
     * allows to send the type of game to the server
     * @return
     */
    public static ClientMessage sendTypeGame()throws Exception{

        ClientHeader ch;
        Payload pay;
        ClientMessage cm;

        while(!controller.getButtonIsClicked() && !ClientCLI.getClose()){try{Thread.sleep(100);}catch(Exception e){}
        }
        if(ClientCLI.getClose()){throw new Exception();}
        String typeGame = controller.sendTypeGame();


        ch = new ClientHeader(nickname, ClientAction.SEND_GAMEMODE);
        pay = new Payload("typeGame", typeGame);
        cm = new ClientMessage(ch, pay);

        return cm;
    }

    /**
     * allows to send the number of the player to the server
     * @return
     */
    public static ClientMessage sendNumPlayers()throws Exception{

        ClientHeader ch;
        Payload pay;
        ClientMessage cm;

        while(!controller.getButtonIsClicked() && !ClientCLI.getClose()){try{Thread.sleep(100);}catch(Exception e){}}

        if(ClientCLI.getClose()){throw new Exception();}

        String numPlayers = controller.sendNumPlayers();

        ch = new ClientHeader(nickname, ClientAction.SEND_NUM_PLAYERS);
        int n = stringToInt(numPlayers);
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
        while(!getButtonIsClicked() && !ClientCLI.getClose()){try{Thread.sleep(100);}catch(Exception e){}}

        if(ClientCLI.getClose()){throw new Exception();}

        return getAction();
    }

    /**
     * allows to send the played card chose by the client
     * @return
     */
    public static ClientMessage sendPlayCard(){
        ClientHeader ch;
        Payload pay;
        ClientMessage cm;

        ch = new ClientHeader(nickname, ClientAction.PLAY_CARD);
        pay = new Payload();

        //aggiungo i parametri per riconoscere l'azione
        pay.addParameter("nickname", nickname);
        pay.addParameter("Action", Action.PlayCard);
        int n;
        //richiedo il numero della carta
        String actionPlayed = getActionPlayed();

        if(actionPlayed.length() == 5) {
            //System.out.println("la lunghezza e' 5");
            n = Integer.parseInt(actionPlayed.substring(4, 5));
        } else{
            //System.out.println("la lunghezza e' 6");
            n = Integer.parseInt(actionPlayed.substring(4, 6));
        }

        pay.addParameter("num", n);

        //aggiungo parametri nulli
        pay.addParameter("Colour", null);
        pay.addParameter("CharacterParameters", null);

        cm = new ClientMessage(ch, pay);

        return cm;
    }

    /**
     * allows to take the cloud chosen by the client
     * @return
     */
    public static ClientMessage sendTakeCloud(){
        ClientHeader ch;
        Payload pay;
        ClientMessage cm;


        //INSERISCO I PARAMETRI PER RICONOSCERE L'AZIONE
        pay = new Payload("nickname", nickname);
        pay.addParameter("Action", Action.TakeCloud);

        //CHIEDO I PARAMETRI PER L'AZIONE
        //richiedo il numero della carta
        String actionPlayed = getActionPlayed();
        int n = Integer.parseInt(actionPlayed.substring(5, 6));
        pay.addParameter("num", n);

        //INSERISCO I PARAMTRI NON UTILIZZATI
        pay.addParameter("CharacterParameters", null);
        pay.addParameter("Colour", null);

        //COSTRUISCO E INVIO IL MESSAGGIO
        ch = new ClientHeader(nickname, ClientAction.PLAY_TAKE_CLOUD);
        cm = new ClientMessage(ch, pay);

        return cm;
    }

    /**
     * allows to send the num of movement of mother nature
     * @return
     */
    public static ClientMessage sendMoveMotherNature(){
        ClientHeader ch;
        Payload pay;
        ClientMessage cm;


        //INSERISCO I PARAMETRI PER RICONOSCERE L'AZIONE
        pay = new Payload("nickname", nickname);
        pay.addParameter("Action", Action.MoveMotherNature);

        //CHIEDO I PARAMETRI PER L'AZIONE
        int n;
        if(actionPlayed.length() == 7){
            n = stringToInt(getActionPlayed().substring(6,7));
        } else{
            n = stringToInt(getActionPlayed().substring(6,8));
        }
        int temp = n - motherNature.getNumIsland();

        if(temp < 0){
            n = listIsland.size() - motherNature.getNumIsland() + n;
        }else {
            n = temp;
        }
        pay.addParameter("num", n);

        //INSERISCO I PARAMTRI NON UTILIZZATI
        pay.addParameter("Colour", null);
        pay.addParameter("CharacterParameters", null);

        //COSTRUISCO E INVIO IL MESSAGGIO
        ch = new ClientHeader(nickname, ClientAction.PLAY_MOVE_MOTHERNATURE);
        cm = new ClientMessage(ch, pay);

        return cm;
    }

    /**
     * allows you to send the color that the client wishes to move to the dining room to the server
     * @return
     */
    public static ClientMessage sendMoveStudentInDiningRoom(){
        ClientHeader ch;
        Payload pay;
        ClientMessage cm;

        //INSERISCO I PARAMETRI PER RICONOSCERE L'AZIONE
        pay = new Payload("nickname", nickname);
        pay.addParameter("Action", Action.MoveStudentInDiningRoom);

        //CHIEDO I PARAMETRI PER L'AZIONE
        Colour colour = stringToColour(getActionPlayed());
        pay.addParameter("Colour", colour);

        //INSERISCO I PARAMTRI NON UTILIZZATI
        pay.addParameter("CharacterParameters", null);
        pay.addParameter("num", 0);

        //COSTRUISCO E INVIO IL MESSAGGIO
        ch = new ClientHeader(nickname, ClientAction.PLAY_MOVE_STUDENT_IN_DININGROOM);
        cm = new ClientMessage(ch, pay);

        return cm;
    }

    /**
     *  allows you to send the color that the client wishes to move to the island to the server
     * @return
     */
    public static ClientMessage sendMoveStudentInIsland(){
        ClientHeader ch;
        Payload pay;
        ClientMessage cm;

        //INSERISCO I PARAMETRI PER RICONOSCERE L'AZIONE
        pay = new Payload("nickname", nickname);
        pay.addParameter("Action", Action.MoveStudentInIsland);

        //CHIEDO I PARAMETRI PER L'AZIONE
        Colour colour = stringToColour(getActionPlayed());
        pay.addParameter("Colour", colour);


        int n = stringToInt(getActionPlayed2());
        pay.addParameter("num", n);

        //INSERISCO I PARAMTRI NON UTILIZZATI
        pay.addParameter("CharacterParameters", null);

        //COSTRUISCO E INVIO IL MESSAGGIO
        ch = new ClientHeader(nickname, ClientAction.PLAY_MOVE_STUDENT_IN_ISLAND);
        cm = new ClientMessage(ch, pay);

        return cm;
    }

    /**
     * allows to send the character card played chose by the client
     * @return
     */
    public static ClientMessage sendPlayCharacterCard(){
        ClientHeader ch;
        Payload pay;
        ClientMessage cm;

        //INSERISCO I PARAMETRI PER RICONOSCERE L'AZIONE
        pay = new Payload();
        pay.addParameter("nickname", nickname);
        pay.addParameter("Action", Action.UseCharacter);

        //CHIEDO I PARAMETRI PER L'AZIONE
        CharacterParameters charPar;

        if(getActionPlayed().equals("Knight")){
            charPar = new CharacterParameters(nickname, "Knight");
        }
        else if(getActionPlayed().equals("PostMan")){
            charPar = new CharacterParameters(nickname, "PostMan");
        }
        else if(getActionPlayed().equals("Satyr")){
            charPar = new CharacterParameters(nickname, "Satyr");
        }
        else if(getActionPlayed().equals("Pirate")){
            int n = stringToInt(getActionPlayed2());
            charPar = new CharacterParameters(nickname, "Pirate", n);

        }
        else if(getActionPlayed().equals("Priest")){
            int n = stringToInt(getActionPlayed3());
            charPar = new CharacterParameters(nickname, "Priest", n, stringToColour(getActionPlayed2()));
        }
        else if(getActionPlayed().equals("Woman")){
            charPar = new CharacterParameters(nickname, "Woman", stringToColour(getActionPlayed2()));
        }
        else if(getActionPlayed().equals("Jester")){
            List<Colour> list = new ArrayList<>();
            if(actionPlayed8.equals("1")){

                list.add(stringToColour(actionPlayed2));
                list.add(stringToColour(actionPlayed3));
            }else if(actionPlayed8.equals("2")){

                list.add(stringToColour(actionPlayed2));
                list.add(stringToColour(actionPlayed3));
                list.add(stringToColour(actionPlayed4));
                list.add(stringToColour(actionPlayed5));
            }else if(actionPlayed8.equals("3")){

                list.add(stringToColour(actionPlayed2));
                list.add(stringToColour(actionPlayed3));
                list.add(stringToColour(actionPlayed4));
                list.add(stringToColour(actionPlayed5));
                list.add(stringToColour(actionPlayed6));
                list.add(stringToColour(actionPlayed7));
            }
            charPar = new CharacterParameters(nickname, "Jester", list, stringToInt(actionPlayed8));
        }
        else if(getActionPlayed().equals("Minstrell")){
            List<Colour> list = new ArrayList<>();
            if(actionPlayed8.equals("1")){

                list.add(stringToColour(actionPlayed2));
                list.add(stringToColour(actionPlayed3));
            }else if(actionPlayed8.equals("2")){

                list.add(stringToColour(actionPlayed2));
                list.add(stringToColour(actionPlayed3));
                list.add(stringToColour(actionPlayed4));
                list.add(stringToColour(actionPlayed5));
            }
            charPar = new CharacterParameters(nickname, "Minstrell", list, stringToInt(actionPlayed8));
        }
        else{
            charPar = new CharacterParameters(nickname, "");
        }//CASO ERRORE

        pay.addParameter("CharacterParameters", charPar);

        //INSERISCO I PARAMETRI NON UTILIZZATI
        pay.addParameter("Colour", null);
        pay.addParameter("num", 0);

        //COSTRUISCO E INVIO IL MESSAGGIO
        ch = new ClientHeader(nickname, ClientAction.PLAY_CHARACTERCARD);
        cm = new ClientMessage(ch, pay);

        return cm;
    }

    /**
     * allows you to continue playing
     * @return
     */
    public static String keepPlaying(){

        while(!getButtonIsClicked()){
            try{Thread.sleep(100);}catch(Exception e){}
        }

        return getActionPlayed();
    }

    /**
     * set the controller
     * @param control
     */
    public static void setController(LoginController control){
        //System.out.println("ho cambiato il controller");
        controller = control;

    }

    /**
     * returns the player with that nickname
     * @param s
     * @return
     * @throws Exception
     */
    public static Player getPlayer(String s)throws Exception{
        Player player= null;
        for(Player p : listPlayer){
            if(p.getNicknameClient().equals(s)){
                player = p;
            }
        }
        if(player.equals(null)){
            throw new Exception();
        }else{return player;}
    }

    /**
     * returns the current action
     * @return
     */
    public static ClientAction getAction(){
        return action;
    }

    /**
     * set the new action
     * @param newAction
     */
    protected static void setAction(ClientAction newAction){action = newAction;}

    /**
     * set the action that the client has played
     * @param s
     */
    protected static void setActionPlayed(String s){
        actionPlayed = s;
    }

    /**
     * returns the action that the client played
     * @return
     */
    public static String getActionPlayed(){
        setButtonIsClicked(false);
        return actionPlayed;
    }

    /**
     * set the action that the client has played
     * @param s
     */
    protected static void setActionPlayed2(String s){
        actionPlayed2 = s;
    }

    /**
     * returns the action that the client played
     * @return
     */
    public static String getActionPlayed2(){
        setButtonIsClicked(false);
        return actionPlayed2;
    }

    /**
     * returns if the button has been clicked
     * @return
     */
    public static boolean getButtonIsClicked() {
        return buttonIsClicked;
    }

    /**
     * set if the button has been clicked
     * @return
     */
    protected static void setButtonIsClicked(boolean b) {
        buttonIsClicked = b;
    }

    /**
     * set the card that has been thrown
     * @param s
     */
    protected static void setCardThrown(String s){
        cardThrown = s;
    }

    /**
     * returns the card that has been thrown
     * @return
     */
    protected static String getCardThrown(){return cardThrown;}

    /**
     * set the action that the client has played
     * @param actionPlayed3
     */
    public static void setActionPlayed3(String actionPlayed3) {
        ClientModelGUI.actionPlayed3 = actionPlayed3;
    }
    public static String getActionPlayed3(){
        return actionPlayed3;
    }

    /**
     * set the action that the client has played
     * @param actionPlayed4
     */
    public static void setActionPlayed4(String actionPlayed4) {
        ClientModelGUI.actionPlayed4 = actionPlayed4;
    }
    public static String getActionPlayed4(){
        return actionPlayed4;
    }

    /**
     * set the action that the client has played
     * @param actionPlayed5
     */
    public static void setActionPlayed5(String actionPlayed5) {
        ClientModelGUI.actionPlayed5 = actionPlayed5;
    }
    public static String getActionPlayed5(){
        return actionPlayed5;
    }

    /**
     * set the action that the client has played
     * @param actionPlayed6
     */
    public static void setActionPlayed6(String actionPlayed6) {
        ClientModelGUI.actionPlayed6 = actionPlayed6;
    }
    public static String getActionPlayed6(){
        return actionPlayed6;
    }

    /**
     * set the action that the client has played
     * @param actionPlayed7
     */
    public static void setActionPlayed7(String actionPlayed7) {
        ClientModelGUI.actionPlayed7 = actionPlayed7;
    }
    public static String getActionPlayed7(){
        return actionPlayed7;
    }

    /**
     * set the action that the client has played
     * @param actionPlayed8
     */
    public static void setActionPlayed8(String actionPlayed8) {
        ClientModelGUI.actionPlayed8 = actionPlayed8;
    }
    public static String getActionPlayed8(){
        return actionPlayed8;
    }

    /**
     * set the colour1
     * @param num
     */
    public static void setColors1(int num){ClientModelGUI.colors1 = num;}

    /**
     * returns the colour1
     * @return
     */
    public static int getColors1(){return colors1;}

    /**
     * set the colour2
     * @param num
     */
    public static void setColors2(int num){ClientModelGUI.colors2 = num;}

    /**
     * returns the colour2
     * @return
     */
    public static int getColors2(){return colors2;}

    /**
     * converts from string to int
     * @param inputLine
     * @return
     */
    private static int stringToInt(String inputLine){
        int n;
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

    /**
     * converts from string to colour
     * @param inputLine
     * @return
     */
    private static Colour stringToColour(String inputLine) {
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
     * converts from colour to string
     * @param inputLine
     * @return
     */
    protected static String colourToString(Colour inputLine){

        if(inputLine.equals(Colour.BLUE)){
            return "blue";
        } else if(inputLine.equals(Colour.RED)){
            return "red";
        } else if(inputLine.equals(Colour.YELLOW)){
            return "yellow";
        } else if(inputLine.equals(Colour.GREEN)){
            return "green";
        } else {
            return "pink";
        }
    }

    /**
     * change the page to login page
     */
    public static void changeToLoginPage() {
        controller.changeToLoginPage();
    }

    /**
     * change the page to waiting page
     */
    public static void changeToWaitingPage() {
        controller.changeToWaitingPage();
    }

    /**
     * change to board page
     */
    public static void changeToBoard(){
        controller.changeToBoard();
    }

    /**
     * change to disconnecition page
     */
    public static void changeToDisconnectedPage(){controller.changeToDisconnectedPage();}

    /**
     * change to keep playing page
     * @param sm
     */
    public static void changeToKeepPlayingPage(ServerMessage sm){
        ClientModelGUI.winner = ((Team) (sm.getPayload().getParameter("team"))).getColourTower();
        controller.changeToKeepPlayingPage();
    }
}
