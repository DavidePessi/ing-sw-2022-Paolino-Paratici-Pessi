package it.polimi.ingsw.NETWORK.CLIENT;

import it.polimi.ingsw.MODEL.Colour;
import it.polimi.ingsw.MODEL.Player;
import it.polimi.ingsw.NETWORK.ClientAppGUI;
import it.polimi.ingsw.NETWORK.MESSAGES.ClientAction;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
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
    private Label WAIT;
    @FXML
    private TextField IP_text_field;
    @FXML
    private TextField PORT_text_field;


    //INTERNAL VARIABLE for rendering
    private Stage stage = new Stage();
    private ActionEvent event = new ActionEvent();
    private Scene scene;
    private Parent root;

    //INTERNAL VARIABLE for computation
    private Boolean buttonIsClicked = false;
    private ClientAction action = ClientAction.STAI_FERMO;

    //METODI
    public LoginController() throws IOException {
    }
    @FXML
    public void initialize() {
        ClientModelGUI.setController(this);
    }

    private void callClient(){
        ClientCLI client = new ClientCLI("GUI");

        try {
            client.run(sendIP(), sendPort());
        }
        catch(Exception e){}
    }

    //GESTIONE EVENTI
    @FXML //bottone per andare in lobby (statico)
    protected void goToLobby(ActionEvent e) {
        this.event = e;
        setButtonIsClicked(true);
    }

    @FXML//bottone per andare alla pagina di login (statico)
    protected void onLoginButtonClick(ActionEvent event) throws IOException {
        this.event = event;
        System.out.println(IP_text_field.getText());
        System.out.println(PORT_text_field.getText());

        callClient();
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

    //CAMBIO SCHERMATE
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

            if(ClientModelGUI.boardCreated == true) {
                stage = ClientModelGUI.stage;
            } else{
                stage = (Stage) WAIT.getScene().getWindow();
                ClientModelGUI.boardCreated = true;
                ClientModelGUI.stage = stage;
            }

            BorderPane layout = new BorderPane();

            GridPane centerBoard;
            GridPane bottomBoard;
            GridPane rightBoard;
            VBox LeftBoard = new VBox();
            HBox UpperBoard = new HBox();

            //FUNZIONE DI DRAG HANDLE   (move mother nature)
            EventHandler<DragEvent> dragHandler = new EventHandler<DragEvent>() {
                @Override
                public void handle(DragEvent dragEvent) {
                    System.out.println("funziona");
                    if(dragEvent.getEventType().equals(dragEvent.DRAG_DONE)){
                        if(dragEvent.getSource() instanceof ImageView){
                            if(((ImageView) dragEvent.getSource()).getAccessibleText().equals("mothernature")){
                                System.out.println("funziona");
                            }
                        }
                    }
                }
            };

            //FUNZIONE DI EVEN HANDLE   (take cloud)
            EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {

                    if(e.getEventType().equals(MouseEvent.MOUSE_PRESSED)){
                        if(e.getTarget() instanceof GridPane){
                            if(((GridPane)(e.getTarget())).getAccessibleText().substring(0,5).equals("cloud")){
                                ClientModelGUI.setAction(ClientAction.PLAY_TAKE_CLOUD);
                                ClientModelGUI.setButtonIsClicked(true);

                                ClientModelGUI.setActionPlayed(((GridPane)(e.getTarget())).getAccessibleText());
                            }
                            else if(((GridPane)(e.getTarget())).getAccessibleText().substring(0,6).equals("island")) {
                                System.out.println("sei un fenomeno");
                            }
                        } else if(e.getTarget() instanceof ImageView){
                            if(((ImageView)(e.getTarget())).getAccessibleText().substring(0,4).equals("card")){
                                ClientModelGUI.setAction(ClientAction.PLAY_CARD);

                                ClientModelGUI.setButtonIsClicked(true);
                                ClientModelGUI.setActionPlayed(((ImageView)(e.getTarget())).getAccessibleText());
                            }
                            System.out.println("ok");
                        }
                    }
                }
            };


            //FUNZIONE DI EVENT HANDLE  (invio carte assistente)
            EventHandler<ActionEvent> actionHandler =
                    new EventHandler<ActionEvent>(){

                        public void handle(ActionEvent t) {
                            if(((Button)(t.getTarget())).getText().substring(0,4).equals("card")){
                                System.out.println(((Button)(t.getTarget())).getText());
                                ClientModelGUI.setAction(ClientAction.PLAY_CARD);
                                ClientModelGUI.setButtonIsClicked(true);

                                ClientModelGUI.setActionPlayed(((Button)(t.getTarget())).getText());
                            }
                            else{
                                System.out.println(((Button)(t.getTarget())).getText());
                            }
                            System.out.println(t.getEventType().getName());
                        }};


            //LAYOUT
            layout.setStyle("-fx-background-color:#267bf1");

            //CENTER BOARD
            centerBoard = showCenterBoard(eventHandler, dragHandler);
            //centerBoard.setStyle("-fx-background-color: #C0C0C0;");
            centerBoard.setAlignment(Pos.CENTER);

            //BOTTOM BOARD
            bottomBoard = showBottomBoard(eventHandler);
            bottomBoard.setAlignment(Pos.CENTER);

            //RIGHT BOARD
            rightBoard = showRightBoard(eventHandler);
            rightBoard.setAlignment(Pos.CENTER);

            //ADDING EVERYTHING TO THE BOARD
            layout.setTop(UpperBoard);
            layout.setLeft(LeftBoard);
            layout.setRight(rightBoard);
            layout.setBottom(bottomBoard);
            layout.setCenter(centerBoard);

            scene = new Scene(layout);
            stage.setScene(scene);
            stage.show();
    });
    }

    public GridPane showBottomBoard(EventHandler<? super MouseEvent> eventHandler){
        GridPane bottomBoard = new GridPane();
        int width;
        int heigth;
        Image img;
        Player p;
        try{
            p = ClientModelGUI.getPlayer(ClientModelGUI.nickname);

        //PLANCIA
        GridPane gpane = createDashBoard(2, ClientModelGUI.nickname);
        bottomBoard.add(gpane, 0,0);


        //CARTE (solo del giocatore corrente)
        GridPane cardsRow = new GridPane();
        cardsRow.getRowConstraints().add(new RowConstraints(105));
        cardsRow.getRowConstraints().add(new RowConstraints(40));

        width = 65;
        heigth = 75;

        for (int i = 1; i <= p.getDeck().size(); i++) {
            String address = getAddressCard(p.getDeck().getCardOfIndex(i - 1).getValue());
            img = new Image(address, width, heigth, false, true, true);
            ImageView view = new ImageView(img);
            view.setAccessibleText("card" + p.getDeck().getCardOfIndex(i - 1).getValue());
            view.addEventFilter(MouseEvent.MOUSE_PRESSED, eventHandler);

            cardsRow.add(view, i, 0);
        }


        //COINS
        if(ClientModelGUI.showCoins){
            String address = "it/polimi/ingsw/NETWORK/Images/Moneta_base.png";

            img = new Image(address, 40, 40, false, true, true);
            ImageView view = new ImageView(img);

            Text text = new Text(String.valueOf(p.getNumCoins()));
            text.setTabSize(20);
            cardsRow.add(view, 1,1);
            cardsRow.add(text, 2,1);
        }


        bottomBoard.add(cardsRow, 2,0);

        }catch(Exception e){}

        //LAYOUT CONSTRAINTS
        bottomBoard.getColumnConstraints().add(new ColumnConstraints(440));
        bottomBoard.getColumnConstraints().add(new ColumnConstraints(20));

        return bottomBoard;
    }

    public GridPane showCenterBoard(EventHandler<? super MouseEvent> eventHandler, EventHandler<? super DragEvent> dragHandler){
        GridPane centerBoard = new GridPane();
        Button button;

        //ISOLE
        int i, sub;
        GridPane pane;

        i = ClientModelGUI.listIsland.get(0).getNumIsland();
        sub = ClientModelGUI.listIsland.get(0).getNumSubIsland();


        pane = createIsland(i, eventHandler, dragHandler);
        centerBoard.add(pane, 1, 0);
        sub = sub -1;

        if(sub == 0){
            i = ClientModelGUI.listIsland.get(i+1).getNumIsland();
            sub = ClientModelGUI.listIsland.get(i).getNumSubIsland();
        }else{
            //stampo il ponte nella posizione indicata
        }

        pane = createIsland(i, eventHandler, dragHandler);
        centerBoard.add(pane, 3, 0);
        sub = sub -1;

        if(sub == 0){
            i = ClientModelGUI.listIsland.get(i+1).getNumIsland();
            sub = ClientModelGUI.listIsland.get(i).getNumSubIsland();
        }else{
            //stampo il ponte nella posizione indicata
        }

        pane = createIsland(i, eventHandler, dragHandler);
        centerBoard.add(pane, 5, 0);
        sub = sub -1;

        if(sub == 0){
            i = ClientModelGUI.listIsland.get(i+1).getNumIsland();
            sub = ClientModelGUI.listIsland.get(i).getNumSubIsland();
        }else{
            //stampo il ponte nella posizione indicata
        }

        pane = createIsland(i, eventHandler, dragHandler);
        centerBoard.add(pane, 7, 0);
        sub = sub -1;

        if(sub == 0){
            i = ClientModelGUI.listIsland.get(i+1).getNumIsland();
            sub = ClientModelGUI.listIsland.get(i).getNumSubIsland();
        }else{
            //stampo il ponte nella posizione indicata
        }

        pane = createIsland(i, eventHandler, dragHandler);
        centerBoard.add(pane, 8, 1);
        sub = sub -1;

        if(sub == 0){
            i = ClientModelGUI.listIsland.get(i+1).getNumIsland();
            sub = ClientModelGUI.listIsland.get(i).getNumSubIsland();
        }else{
            //stampo il ponte nella posizione indicata
        }

        pane = createIsland(i, eventHandler, dragHandler);
        centerBoard.add(pane, 8, 3);
        sub = sub -1;

        if(sub == 0){
            i = ClientModelGUI.listIsland.get(i+1).getNumIsland();
            sub = ClientModelGUI.listIsland.get(i).getNumSubIsland();
        }else{
            //stampo il ponte nella posizione indicata
        }

        pane = createIsland(i, eventHandler, dragHandler);
        centerBoard.add(pane, 7, 4);
        sub = sub -1;

        if(sub == 0){
            i = ClientModelGUI.listIsland.get(i+1).getNumIsland();
            sub = ClientModelGUI.listIsland.get(i).getNumSubIsland();
        }else{
            //stampo il ponte nella posizione indicata
        }

        pane = createIsland(i, eventHandler, dragHandler);
        centerBoard.add(pane, 5, 4);
        sub = sub -1;

        if(sub == 0){
            i = ClientModelGUI.listIsland.get(i+1).getNumIsland();
            sub = ClientModelGUI.listIsland.get(i).getNumSubIsland();
        }else{
            //stampo il ponte nella posizione indicata
        }

        pane = createIsland(i, eventHandler, dragHandler);
        centerBoard.add(pane, 3, 4);
        sub = sub -1;

        if(sub == 0){
            i = ClientModelGUI.listIsland.get(i+1).getNumIsland();
            sub = ClientModelGUI.listIsland.get(i).getNumSubIsland();
        }else{
            //stampo il ponte nella posizione indicata
        }

        pane = createIsland(i, eventHandler, dragHandler);
        centerBoard.add(pane, 1, 4);
        sub = sub -1;

        if(sub == 0){
            i = ClientModelGUI.listIsland.get(i+1).getNumIsland();
            sub = ClientModelGUI.listIsland.get(i).getNumSubIsland();
        }else{
            //stampo il ponte nella posizione indicata
        }

        pane = createIsland(i, eventHandler, dragHandler);
        centerBoard.add(pane, 0, 3);
        sub = sub -1;

        if(sub == 0){
            i = ClientModelGUI.listIsland.get(i+1).getNumIsland();
            sub = ClientModelGUI.listIsland.get(i).getNumSubIsland();
        }else{
            //stampo il ponte nella posizione indicata
        }

        pane = createIsland(i, eventHandler, dragHandler);
        centerBoard.add(pane, 0, 1);
        sub = sub -1;

        if(sub == 0){
        }else{
            //stampo il ponte nella posizione indicata
        }

        //NUVOLE

        GridPane gpane = createCloud(0, eventHandler);

        centerBoard.add(gpane, 1, 1);

        gpane = createCloud(1, eventHandler);
        centerBoard.add(gpane, 3, 1);

        if(ClientModelGUI.listCloud.size() >= 3){
            gpane = createCloud(2, eventHandler);
            centerBoard.add(gpane, 5, 1);

            if(ClientModelGUI.listCloud.size() == 4){
                gpane = createCloud(3, eventHandler);
                centerBoard.add(gpane, 7, 1);
            }
        }

        //CHARACTER CARD
        int x = 1;
        if(ClientModelGUI.characterCards.get(0) != null) {
            for (int j = 0; j < ClientModelGUI.characterCards.size(); j++) {
                Image img = new Image(getAddressCharacter(ClientModelGUI.characterCards.get(j).getNameCard()), 65, 75, false, true, true);
                ImageView view = new ImageView(img);

                view.addEventFilter(MouseEvent.MOUSE_PRESSED, eventHandler);
                centerBoard.add(view, x+j * 2, 3);
            }
        }

        //LAYOUT CONSTRAINTS
        centerBoard.getColumnConstraints().add(new ColumnConstraints(100));
        centerBoard.getColumnConstraints().add(new ColumnConstraints(100));
        centerBoard.getColumnConstraints().add(new ColumnConstraints(30));
        centerBoard.getColumnConstraints().add(new ColumnConstraints(100));
        centerBoard.getColumnConstraints().add(new ColumnConstraints(30));
        centerBoard.getColumnConstraints().add(new ColumnConstraints(100));
        centerBoard.getColumnConstraints().add(new ColumnConstraints(30));
        centerBoard.getColumnConstraints().add(new ColumnConstraints(100));
        centerBoard.getColumnConstraints().add(new ColumnConstraints(100));
        centerBoard.getColumnConstraints().add(new ColumnConstraints(30));
        centerBoard.getColumnConstraints().add(new ColumnConstraints(100));
        centerBoard.getColumnConstraints().add(new ColumnConstraints(30));
        centerBoard.getColumnConstraints().add(new ColumnConstraints(100));

        centerBoard.getRowConstraints().add(new RowConstraints(100));
        centerBoard.getRowConstraints().add(new RowConstraints(100));
        centerBoard.getRowConstraints().add(new RowConstraints(30));
        centerBoard.getRowConstraints().add(new RowConstraints(100));
        centerBoard.getRowConstraints().add(new RowConstraints(100));

        return centerBoard;
    }

    private GridPane showRightBoard(EventHandler<? super MouseEvent> eventHandler){
        GridPane rightBoard = new GridPane();
        int j = 0;
        for(Player p : ClientModelGUI.listPlayer){
            if(!p.getNicknameClient().equals(ClientModelGUI.nickname)){
                j++;

                Text t = new Text(p.getNicknameClient());
                GridPane dashboard = createDashBoard(1, p.getNicknameClient());

                rightBoard.add(t, 1, j*2);
                rightBoard.add(dashboard,1, 1+j*2);
            }
        }
        rightBoard.getColumnConstraints().add(new ColumnConstraints(10));
        rightBoard.getColumnConstraints().add(new ColumnConstraints(220));
        rightBoard.getColumnConstraints().add(new ColumnConstraints(10));

        rightBoard.getRowConstraints().add(new RowConstraints(10));
        rightBoard.getRowConstraints().add(new RowConstraints(110));
        rightBoard.getRowConstraints().add(new RowConstraints(10));
        rightBoard.getRowConstraints().add(new RowConstraints(110));
        rightBoard.getRowConstraints().add(new RowConstraints(10));
        rightBoard.getRowConstraints().add(new RowConstraints(110));

        return rightBoard;
    }

    private GridPane createIsland(int i, EventHandler<? super MouseEvent> eventHandler, EventHandler<? super DragEvent> dragHandler) {
        GridPane pane = new GridPane();
        pane.setMinSize(100,100);
        pane.setMaxSize(100,100);

        pane.setAccessibleText("island"+i);

        Image img = new Image("it/polimi/ingsw/NETWORK/Images/island1.png", pane.getWidth(), pane.getHeight(), false, true, true);

        BackgroundImage bimage = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(pane.getWidth(), pane.getHeight(), true, true, true, false));
        Background backGround = new Background(bimage);
        pane.setBackground(backGround);

        pane.addEventFilter(MouseEvent.MOUSE_PRESSED, eventHandler);

        pane.getColumnConstraints().add(new ColumnConstraints(15));
        pane.getRowConstraints().add(new RowConstraints(20));

        //SETTO GLI STUDENTI

        int width = 10;
        int heigth = 10;

        img = new Image("it/polimi/ingsw/NETWORK/Images/student_blue.png", width, heigth, false, true, true);
        ImageView view = new ImageView(img);
        pane.add(view, 1,1 );

        img = new Image("it/polimi/ingsw/NETWORK/Images/student_yellow.png", width, heigth, false, true, true);
        view = new ImageView(img);
        pane.add(view, 1,2 );

        img = new Image("it/polimi/ingsw/NETWORK/Images/student_red.png", width, heigth, false, true, true);
        view = new ImageView(img);
        pane.add(view, 1,3 );

        img = new Image("it/polimi/ingsw/NETWORK/Images/student_pink.png", width, heigth, false, true, true);
        view = new ImageView(img);
        pane.add(view, 3,1 );

        img = new Image("it/polimi/ingsw/NETWORK/Images/student_green.png", width, heigth, false, true, true);
        view = new ImageView(img);
        pane.add(view, 3,2 );

        if(ClientModelGUI.listIsland.get(i).getHasMotherNature()){
            img = new Image("it/polimi/ingsw/NETWORK/Images/mother_nature.png", width*2, heigth*2, false, true, true);
            view = new ImageView(img);
            view.setAccessibleText("mothernature");

            view.addEventFilter(DragEvent.DRAG_DONE, dragHandler);

            pane.add(view, 5,1 );
        }

        int j = 1;
        for(Colour c : Colour.values()){
            Text text = new Text();
            text.minHeight(10);
            text.minWidth(10);
            text.setText(String.valueOf(ClientModelGUI.listIsland.get(i).countStudentsOfColour(c)));
            if(j<4) pane.add(text, 2, j);
            else pane.add(text, 4, j-3);
            j++;
        }


        return pane;
    }

    private GridPane createCloud(int i, EventHandler<? super MouseEvent> eventHandler){
        GridPane gpane = new GridPane();

        gpane.setMinHeight(100);
        gpane.setMinWidth(100);

        gpane.addEventFilter(MouseEvent.MOUSE_PRESSED, eventHandler);

        gpane.setAccessibleText("cloud" + i);

        String address = "it/polimi/ingsw/NETWORK/Images/cloud_card_1.png";

        if(i == 1){
            address = "it/polimi/ingsw/NETWORK/Images/cloud_card_2.png";
        } else if(i == 2){
            address = "it/polimi/ingsw/NETWORK/Images/cloud_card_3.png";
        } else if(i == 3){
            address = "it/polimi/ingsw/NETWORK/Images/cloud_card_4.png";
        }

        Image img = new Image(address, gpane.getWidth(), gpane.getHeight(), false, true, true);

        BackgroundImage bimage = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(gpane.getWidth(), gpane.getHeight(), true, true, true, false));
        Background backGround = new Background(bimage);
        gpane.setBackground(backGround);

        //aggiungo gli studenti alla nuvola
        int width = 30;
        int height = 30;
        gpane.getRowConstraints().add(new RowConstraints(10));
        gpane.getRowConstraints().add(new RowConstraints(height-10));
        gpane.getRowConstraints().add(new RowConstraints(height-5));
        gpane.getColumnConstraints().add(new ColumnConstraints(5));
        gpane.getColumnConstraints().add(new ColumnConstraints(width+5));
        gpane.getColumnConstraints().add(new ColumnConstraints(width-25));

        if(!ClientModelGUI.listCloud.get(i).empty()) {
            for(int j = 0; j < 3 ; j++) {
                if (ClientModelGUI.listCloud.get(i).getStudent(j).getColour().equals(Colour.BLUE)){
                    address = "it/polimi/ingsw/NETWORK/Images/student_blue.png";
                }else if(ClientModelGUI.listCloud.get(i).getStudent(j).getColour().equals(Colour.RED)){
                    address = "it/polimi/ingsw/NETWORK/Images/student_red.png";
                }else if(ClientModelGUI.listCloud.get(i).getStudent(j).getColour().equals(Colour.YELLOW)){
                    address = "it/polimi/ingsw/NETWORK/Images/student_yellow.png";
                }else if(ClientModelGUI.listCloud.get(i).getStudent(j).getColour().equals(Colour.GREEN)){
                    address = "it/polimi/ingsw/NETWORK/Images/student_green.png";
                }else if(ClientModelGUI.listCloud.get(i).getStudent(j).getColour().equals(Colour.PINK)){
                    address = "it/polimi/ingsw/NETWORK/Images/student_pink.png";
                }

                img = new Image(address, width, height, false, true, true);
                ImageView view = new ImageView(img);

                int y = 2;
                if(j == 1){
                    y = 3;
                } else if (j == 2){
                    y = 1;
                }
                gpane.add(view, j+1,y);
            }
        }



        return gpane;
    }

    private GridPane createDashBoard(int factor, String client){

        //PLANCIA
        GridPane gpane = new GridPane();

        gpane.setMinSize(220*factor, 110*factor);
        gpane.setMaxSize(220*factor, 110*factor);

        Image img = new Image("it/polimi/ingsw/NETWORK/Images/PLANCIA GIOCO_2.png", gpane.getWidth(), gpane.getHeight(), false, true, true);
        BackgroundImage bimage = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(gpane.getWidth(), gpane.getHeight(), true, true, true, false));
        Background backGround = new Background(bimage);
        gpane.setBackground(backGround);

        gpane.getColumnConstraints().add(new ColumnConstraints(5*factor));
        gpane.getColumnConstraints().add(new ColumnConstraints(10*factor));
        gpane.getColumnConstraints().add(new ColumnConstraints(5*factor));
        gpane.getColumnConstraints().add(new ColumnConstraints(10*factor));

        gpane.getColumnConstraints().add(new ColumnConstraints(10*factor));
        gpane.getColumnConstraints().add(new ColumnConstraints(10*factor));
        gpane.getColumnConstraints().add(new ColumnConstraints(2.5*factor));
        gpane.getColumnConstraints().add(new ColumnConstraints(10*factor));
        gpane.getColumnConstraints().add(new ColumnConstraints(2.5*factor));
        gpane.getColumnConstraints().add(new ColumnConstraints(10*factor));
        gpane.getColumnConstraints().add(new ColumnConstraints(2.5*factor));
        gpane.getColumnConstraints().add(new ColumnConstraints(10*factor));
        gpane.getColumnConstraints().add(new ColumnConstraints(2.5*factor));
        gpane.getColumnConstraints().add(new ColumnConstraints(10*factor));
        gpane.getColumnConstraints().add(new ColumnConstraints(2.5*factor));
        gpane.getColumnConstraints().add(new ColumnConstraints(10*factor));
        gpane.getColumnConstraints().add(new ColumnConstraints(2.5*factor));
        gpane.getColumnConstraints().add(new ColumnConstraints(10*factor));
        gpane.getColumnConstraints().add(new ColumnConstraints(2.5*factor));
        gpane.getColumnConstraints().add(new ColumnConstraints(10*factor));
        gpane.getColumnConstraints().add(new ColumnConstraints(2.5*factor));
        gpane.getColumnConstraints().add(new ColumnConstraints(10*factor));
        gpane.getColumnConstraints().add(new ColumnConstraints(2.5*factor));
        gpane.getColumnConstraints().add(new ColumnConstraints(10*factor));

        gpane.getRowConstraints().add(new RowConstraints(20*factor));
        gpane.getRowConstraints().add(new RowConstraints(10*factor));
        gpane.getRowConstraints().add(new RowConstraints(5*factor));
        gpane.getRowConstraints().add(new RowConstraints(10*factor));
        gpane.getRowConstraints().add(new RowConstraints(5*factor));
        gpane.getRowConstraints().add(new RowConstraints(10*factor));
        gpane.getRowConstraints().add(new RowConstraints(5*factor));
        gpane.getRowConstraints().add(new RowConstraints(10*factor));
        gpane.getRowConstraints().add(new RowConstraints(5*factor));
        gpane.getRowConstraints().add(new RowConstraints(10*factor));


        int width = 10*factor;
        int heigth = 10*factor;

        for(int i = 0; i < ClientModelGUI.listPlayer.size(); i++){

            if(ClientModelGUI.listPlayer.get(i).getNicknameClient().equals(client)){
                //ENTRANCE
                int y = 1;
                int x = 3;
                for(int j = 0; j < ClientModelGUI.listPlayer.get(i).getEntrance().getStudentGroup().size(); j++) {

                    if(j % 2 != 0){
                        x = 1;
                        y = y + 2;
                    }else{
                        x = 3;
                    }

                    String address = getAddress(ClientModelGUI.listPlayer.get(i).getEntrance().getStudentGroup().get(j).getColour());
                    img = new Image(address, width, heigth, false, true, true);
                    ImageView view = new ImageView(img);
                    gpane.add(view, x, y);
                }

                //DINING ROOM
                y = 1;
                for(Colour c : Colour.values()) {
                    x = 5;
                    for (int j = 0; j < ClientModelGUI.listPlayer.get(i).numStudentsDiningRoom(c); j++) {
                        String address = getAddress(c);
                        img = new Image(address, width, heigth, false, true, true);
                        ImageView view = new ImageView(img);
                        view.setAccessibleText(ClientModelGUI.colourToString(c));
                        gpane.add(view, x, y);
                        x = x + 2;
                    }
                }
            }
        }

        return gpane;
    }

    //METODI UTILI
    private String getAddress(Colour c){
        String address = "";

        if (c.equals(Colour.BLUE)){
            address = "it/polimi/ingsw/NETWORK/Images/student_blue.png";
        }else if(c.equals(Colour.RED)){
            address = "it/polimi/ingsw/NETWORK/Images/student_red.png";
        }else if(c.equals(Colour.YELLOW)){
            address = "it/polimi/ingsw/NETWORK/Images/student_yellow.png";
        }else if(c.equals(Colour.GREEN)){
            address = "it/polimi/ingsw/NETWORK/Images/student_green.png";
        }else if(c.equals(Colour.PINK)){
            address = "it/polimi/ingsw/NETWORK/Images/student_pink.png";
        }

        return address;
    }
    private String getAddressCard(int i){
        String address = "";

        if(i == 1){
            address = "it/polimi/ingsw/NETWORK/Images/Assistente (1).png";
        }else if(i == 2){
            address = "it/polimi/ingsw/NETWORK/Images/Assistente (2).png";
        }else if(i == 3){
            address = "it/polimi/ingsw/NETWORK/Images/Assistente (3).png";
        }else if(i == 4){
            address = "it/polimi/ingsw/NETWORK/Images/Assistente (4).png";
        }else if(i == 5){
            address = "it/polimi/ingsw/NETWORK/Images/Assistente (5).png";
        }else if(i == 6){
            address = "it/polimi/ingsw/NETWORK/Images/Assistente (6).png";
        }else if(i == 7){
            address = "it/polimi/ingsw/NETWORK/Images/Assistente (7).png";
        }else if(i == 8){
            address = "it/polimi/ingsw/NETWORK/Images/Assistente (8).png";
        }else if(i == 9){
            address = "it/polimi/ingsw/NETWORK/Images/Assistente (9).png";
        }else if(i == 10){
            address = "it/polimi/ingsw/NETWORK/Images/Assistente (10).png";
        }

        return address;
    }
    private String getAddressCharacter(String s){
        String address = "";

        if(s.equals("Jester")){
            address = "it/polimi/ingsw/NETWORK/Images/Jester.jpg";
        }else if(s.equals("Knight")){
            address = "it/polimi/ingsw/NETWORK/Images/Knight.jpg";
        }else if(s.equals("Minstrell")){
            address = "it/polimi/ingsw/NETWORK/Images/Minstrell.jpg";
        }else if(s.equals("Pirate")){
            address = "it/polimi/ingsw/NETWORK/Images/Pirate.jpg";
        }else if(s.equals("PostMan")){
            address = "it/polimi/ingsw/NETWORK/Images/PostMan.jpg";
        }else if(s.equals("Priest")){
            address = "it/polimi/ingsw/NETWORK/Images/Priest.jpg";
        }else if(s.equals("Satyr")){
            address = "it/polimi/ingsw/NETWORK/Images/Satyr.jpg";
        }else if(s.equals("Woman")){
            address = "it/polimi/ingsw/NETWORK/Images/Woman.jpg";
        }

        return address;

    }
    //GET AND SET
    public boolean getButtonIsClicked() {
        return buttonIsClicked;
    }
    private void setButtonIsClicked(boolean b) {
        //System.out.println("settato a" + b + "il buttone");
        buttonIsClicked = b;
    }
}