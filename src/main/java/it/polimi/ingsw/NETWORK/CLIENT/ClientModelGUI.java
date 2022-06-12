package it.polimi.ingsw.NETWORK.CLIENT;

import it.polimi.ingsw.NETWORK.CLIENT.LoginController;
import it.polimi.ingsw.NETWORK.CLIENT.UserInterface;
import it.polimi.ingsw.NETWORK.MESSAGES.*;

import java.io.IOException;

public final class ClientModelGUI extends UserInterface {

    private static LoginController controller;

    static {
        try {
            controller = new LoginController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static String nickname = "";

    public ClientModelGUI(){

        super();
    }

    //todo non e' detto che sia necessario
    public static void showMoves(String nickname){
    }

    //todo potrebbe anche essere vuoto
    public static void showBoard(){}

    //todo : deve mostrare che cosa non va bene
    public static void clientError(ServerMessage message){}

    //todo : deve mostrare il vincitore
    public static void endGame(ServerMessage message){}

    //METODI CHE RICHIEDONO DATI
    public static ClientMessage sendNickname(){

        ClientHeader ch;
        Payload pay;
        ClientMessage cm;


        while(!controller.getButtonIsClicked()){}
        System.out.println("e' stato premuto il pulsante");
        String nick = controller.sendNickname();
        nickname = nick;

        ch = new ClientHeader(nickname, ClientAction.SEND_NICKNAME);
        pay = new Payload("nickname", nick);
        cm = new ClientMessage(ch, pay);

        return cm;
    }

    public static ClientMessage sendTypeGame(){

        ClientHeader ch;
        Payload pay;
        ClientMessage cm;

        while(!controller.getButtonIsClicked()){try{Thread.sleep(100);}catch(Exception e){}
        }

        String typeGame = controller.sendTypeGame();


        ch = new ClientHeader(nickname, ClientAction.SEND_GAMEMODE);
        pay = new Payload("typeGame", typeGame);
        cm = new ClientMessage(ch, pay);

        return cm;
    }

    public static ClientMessage sendNumPlayers(){

        ClientHeader ch;
        Payload pay;
        ClientMessage cm;

        while(!controller.getButtonIsClicked()){}
        System.out.println("e' stato premuto il pulsante");
        String numPlayers = controller.sendNumPlayers();

        ch = new ClientHeader(nickname, ClientAction.SEND_NUM_PLAYERS);
        int n = Integer.parseInt(numPlayers);
        pay = new Payload("numPlayer", n);
        cm = new ClientMessage(ch, pay);

        return cm;
    }

    //TODO : TUTTI I METODI SOTTO QUESTO TODO
    public static ClientAction sendTypeAction(){
        return ClientAction.PLAY_ACTION;
    }

    public static ClientMessage sendPlayCard(){
        String inputLine;
        ClientHeader ch;
        Payload pay;
        ClientMessage cm;

        ch = new ClientHeader("", ClientAction.SEND_NICKNAME);
        pay = new Payload("nickname", "");
        cm = new ClientMessage(ch, pay);

        return cm;
    }

    public static ClientMessage sendPlayCharacterCard(){
        String inputLine;
        ClientHeader ch;
        Payload pay;
        ClientMessage cm;


        ch = new ClientHeader("", ClientAction.SEND_NICKNAME);
        pay = new Payload("nickname", "");
        cm = new ClientMessage(ch, pay);

        return cm;
    }

    public static ClientMessage sendMoveMotherNature(){
        String inputLine;
        ClientHeader ch;
        Payload pay;
        ClientMessage cm;


        ch = new ClientHeader("", ClientAction.SEND_NICKNAME);
        pay = new Payload("nickname", "");
        cm = new ClientMessage(ch, pay);

        return cm;
    }

    public static ClientMessage sendMoveStudentInDiningRoom(){
        String inputLine;
        ClientHeader ch;
        Payload pay;
        ClientMessage cm;


        ch = new ClientHeader("", ClientAction.SEND_NICKNAME);
        pay = new Payload("nickname", "");
        cm = new ClientMessage(ch, pay);

        return cm;
    }

    public static ClientMessage sendMoveStudentInIsland(){
        String inputLine;
        ClientHeader ch;
        Payload pay;
        ClientMessage cm;


        ch = new ClientHeader("", ClientAction.SEND_NICKNAME);
        pay = new Payload("nickname", "");
        cm = new ClientMessage(ch, pay);

        return cm;
    }

    public static ClientMessage sendTakeCloud(){
        String inputLine;
        ClientHeader ch;
        Payload pay;
        ClientMessage cm;


        ch = new ClientHeader("", ClientAction.SEND_NICKNAME);
        pay = new Payload("nickname", "");
        cm = new ClientMessage(ch, pay);

        return cm;
    }

    public static String keepPlaying(){
        return "no";
    }

    //METODI UTILI
    public static void setController(LoginController control){
        System.out.println("ho cambiato il controller");
        controller = control;

    }


    //CAMBI PAGINA
    public static void changeToLoginPage() {
        controller.changeToLoginPage();
    }

    public static void changeToWaitingPage() {
        controller.changeToWaitingPage();
    }

    public static void changeToBoard(){controller.changeToBoard();}
}
