package it.polimi.ingsw.NETWORK.CLIENT;

import it.polimi.ingsw.MODEL.Island;
import it.polimi.ingsw.NETWORK.ClientAppGUI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;


public  class LoginController {

    //LABEL - INSERIMETO_DATI_GIOCATORI
    @FXML
    private TextField GAMEMODE_text_field;
    @FXML
    private TextField NICKNAME_field_text;
    @FXML
    private TextField NUMPLAYERS_text_field;


    //LABEL - CONNECTION
    @FXML
    private Label IP;
    @FXML
    private Label PORT;
    @FXML
    private TextField IP_text_field;
    @FXML
    private TextField PORT_text_field;

    @FXML
    private GridPane grid;

    private ImageView[] boxes;


    //INTERNAL VARIABLE
    private Boolean buttonIsClicked = false;
    private Stage stage = new Stage();
    private Scene scene;
    private ActionEvent event = new ActionEvent();
    private Parent root;

    //METODI

    public LoginController() throws IOException {
    }

    @FXML
    public void initialize() {
        ClientModelGUI.setController(this);

        if(GAMEMODE_text_field == null && IP_text_field == null){
            boxes = new ImageView[12];
            for(int i = 0; i < ClientModelGUI.listIsland.size(); i++){
                ImageView box = new ImageView(new Image("file:island1.png"));
                grid.add(box, i, 200);
                boxes[i] = box;
            }
        }
    }

    @FXML
    protected void onLoginButtonClick(ActionEvent event) throws IOException {
        this.event = event;
        System.out.println(IP_text_field.getText());
        System.out.println(PORT_text_field.getText());

        callClient();
    }

    private void callClient(){
        ClientCLI client = new ClientCLI("GUI");

        try {
            client.run(sendIP(), sendPort());
        }
        catch(Exception e){}
    }


    //INSERIMENTO_DATI_GIOCATORI

    @FXML
    protected void goToLobby(ActionEvent e) {
        this.event = e;
        setButtonIsClicked(true);
    }

    private void setButtonIsClicked(boolean b) {
        System.out.println("settato a" + b + "il buttone");
        buttonIsClicked = b;
    }

    //LETTURA DA LABEL
    public String sendIP()  {
        String ip = IP_text_field.getText();
        return ip;
    }

    public int sendPort(){
        int port = Integer.parseInt(PORT_text_field.getText());
        return port;
    }

    public String sendTypeGame()  {
        String typeGame = GAMEMODE_text_field.getText();
        return typeGame;
    }

    public String sendNumPlayers(){
        String numPlayers = NUMPLAYERS_text_field.getText();
        return numPlayers;
    }

    public String sendNickname()  {
        String nick = NICKNAME_field_text.getText();
        setButtonIsClicked(false);

        return nick;
    }

    public void changeToWaitingPage(){
        Platform.runLater(()-> {
            Parent root = null;
            try {
                root = FXMLLoader.load(ClientAppGUI.class.getResource("waitingroom.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
            }


        });
    }

    public void changeToBoard(){
        Platform.runLater(()->{
            Parent root = null;
            try {
                root = FXMLLoader.load(ClientAppGUI.class.getResource("board.fxml"));
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

    }

    public void changeToLoginPage(){
        Platform.runLater(()->{
            try {
                root = FXMLLoader.load(ClientAppGUI.class.getResource("inserimento_dati_giocatore.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }catch(Exception e){}



            });
    }

    public boolean getButtonIsClicked() {
        return buttonIsClicked;
    }


}