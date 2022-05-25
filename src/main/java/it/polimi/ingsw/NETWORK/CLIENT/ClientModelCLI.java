package it.polimi.ingsw.NETWORK.CLIENT;

import it.polimi.ingsw.MODEL.*;
import it.polimi.ingsw.MODEL.CharacterCards.ConcreteCharacterCard;
import it.polimi.ingsw.MODEL.Exception.MissingCardException;
import it.polimi.ingsw.MODEL.Exception.MissingTowerException;
import it.polimi.ingsw.NETWORK.MESSAGES.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientModelCLI implements UserInterface{
    private String characterCardThrown;
    private List<Cloud> listCloud;
    private List<Player> listPlayer;
    private List<Team> listTeam;
    private List<Island> listIsland;
    private MotherNature motherNature;
    private List<Professor> professors;
    private List<ConcreteCharacterCard> characterCards;
    private String currentPlayer;
    private boolean showCoins;


    public ClientModelCLI(){

        //todo poi tutta questa inizializzazioneè precaria solo per testare
        listCloud = new ArrayList<>();
        listIsland = new ArrayList<>();
        listPlayer = new ArrayList<>();
        listTeam = new ArrayList<>();
        professors = new ArrayList<>();
        characterCards = new ArrayList<>();

    }

    public void showBoard(){

        String board = "";

        //-------------------------------STAMPA PLAYER------------------------------
        board = board + showPlayer();

        //-------------------------STAMPA CARTE PERSONAGGIO-------------------------
        if(characterCards.get(0)!=null) {
            board = board + ("Character Cards :\n");
            for (ConcreteCharacterCard card : characterCards) {
                board = board + ("\t" + card.getNameCard() + " cost: " + card.getPrice() + "\n");
            }
        }


        //-------------------------------STAMPA ISOLE-------------------------------
        board = board + showIsland();


        //------------------------------STAMPA NUVOLE-------------------------------
        board = board + showCloud();

        System.out.println(board);
    }

    private String showCloud(){
        String cloud = "";
        String colore = "";

        for(int j = 0; j < listCloud.size(); j++){
            cloud = cloud + "cloud " + j + ": {";

            if(!listCloud.get(j).empty()){

                for(int i = 0; i < 3; i++) {
                    if(listCloud.get(j).getStudent(i).getColour().equals(Colour.BLUE)){
                        colore = "[34m";
                    } else if(listCloud.get(j).getStudent(i).getColour().equals(Colour.RED)){
                        colore = "[31m";
                    } else if(listCloud.get(j).getStudent(i).getColour().equals(Colour.PINK)){
                        colore = "[35m";
                    } else if(listCloud.get(j).getStudent(i).getColour().equals(Colour.GREEN)){
                        colore = "[32m";
                    } else if(listCloud.get(j).getStudent(i).getColour().equals(Colour.YELLOW)){
                        colore = "[33m";
                    }
                    cloud = cloud + (char) 27 + colore + "▪" + "\u001B[0m";
                }
            }
            cloud = cloud + "}\n";
        }
        return cloud;
    }

    private String showPlayer(){
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

            if(player.professorPresent(Colour.RED))players = players + (char) 27 + "[31m" + "◊" + "\u001B[0m";
            if(player.professorPresent(Colour.YELLOW))players = players + (char) 27 + "[33m" + "◊" + "\u001B[0m";
            if(player.professorPresent(Colour.GREEN))players = players + (char) 27 + "[32m" + "◊" + "\u001B[0m";
            if(player.professorPresent(Colour.BLUE))players = players + (char) 27 + "[34m" + "◊" + "\u001B[0m";
            if(player.professorPresent(Colour.PINK))players = players + (char) 27 + "[35m" + "◊" + "\u001B[0m";

            players = players +( "\nentrance : \n[");

            for (int i = 0; i < player.getEntrance().getStudentGroup().countStudentsOfColour(Colour.RED); i++) {
                players = players +((char) 27 + "[31m" + "▪" + "\u001B[0m");
            }

            players = players +( "]\n[");
            for (int i = 0; i < player.getEntrance().getStudentGroup().countStudentsOfColour(Colour.YELLOW); i++) {
                players = players +((char) 27 + "[33m" + "▪" + "\u001B[0m");
            }

            players = players +( "]\n[");
            for (int i = 0; i < player.getEntrance().getStudentGroup().countStudentsOfColour(Colour.GREEN); i++) {
                players = players +((char) 27 + "[32m" + "▪" + "\u001B[0m");
            }

            players = players +( "]\n[");
            for (int i = 0; i < player.getEntrance().getStudentGroup().countStudentsOfColour(Colour.BLUE); i++) {
                players = players +((char) 27 + "[34m" + "▪" + "\u001B[0m");
            }

            players = players +( "]\n[");
            for (int i = 0; i < player.getEntrance().getStudentGroup().countStudentsOfColour(Colour.PINK); i++) {
                players = players +((char) 27 + "[35m" + "▪" + "\u001B[0m");
            }

            players = players +( "]\n");


            players = players +( "DiningRoom : \n[");
            for (int i = 0; i < player.numStudentsDiningRoom(Colour.RED); i++) {
                players = players +((char) 27 + "[31m" + "▪" + "\u001B[0m");
            }
            players = players +( "]\n[");
            for (int i = 0; i < player.numStudentsDiningRoom(Colour.YELLOW); i++) {
                players = players +((char) 27 + "[33m" + "▪" + "\u001B[0m");
            }
            players = players +( "]\n[");
            for (int i = 0; i < player.numStudentsDiningRoom(Colour.GREEN); i++) {
                players = players +((char) 27 + "[32m" + "▪" + "\u001B[0m");
            }
            players = players +( "]\n[");
            for (int i = 0; i < player.numStudentsDiningRoom(Colour.BLUE); i++) {
                players = players +((char) 27 + "[34m" + "▪" + "\u001B[0m");
            }
            players = players +( "]\n[");
            for (int i = 0; i < player.numStudentsDiningRoom(Colour.PINK); i++) {
                players = players +((char) 27 + "[35m" + "▪" + "\u001B[0m");
            }
            players = players +( "]\n");

        }
        return players;
    }

    private String showIsland(){
        String island = "";
        String colore = "";
        int max = 6;

        island = island +( "Islands :\n\t");

        //voglio tornare a capo se ci sono più di 6 isole quindi metto due casi
        //setto quanto è lunga la prima fila
        if(max > listIsland.size()){
            max = listIsland.size();
        }

        /////////////////////////////////////////////////////////////////////////PRIMA FILA DI ISOLE
        //scrivo i numeri sopra
        for(int i = 0; i < max; i++){
            island = island + ( listIsland.get(i).getNumIsland()+1) + "\t\t\t\t\t\t";
        }

        //scrivo il confine delle isole
        island = island +("\n");
        for(int i = 0; i < max; i++) {
            island = island +("\t|-------------------|");
        }
        island = island +("\n");

        //stampo gli studenti per colore sulle isole
        for(Colour colour : Colour.values()){

            //setto il colore della stringa
            if(colour.equals(Colour.BLUE)){
                colore = "[34m";
            } else if(colour.equals(Colour.RED)){
                colore = "[31m";
            } else if(colour.equals(Colour.PINK)){
                colore = "[35m";
            } else if(colour.equals(Colour.GREEN)){
                colore = "[32m";
            } else if(colour.equals(Colour.YELLOW)){
                colore = "[33m";
            }

            for (int i = 0; i < max; i++) {
                island = island + ("\t|");
                for (int j = 0; j < listIsland.get(i).countStudentsOfColour(colour); j++) {

                    island = island + ((char) 27 + colore + "▪" + "\u001B[0m");
                }
                if (listIsland.get(i).countStudentsOfColour(colour) > 7) {
                    island = island + ("\t\t|");
                } else {
                    island = island + ("\t\t\t\t\t|");
                }
            }

            island = island +("\n");
        }

        //metto le torri
        for(int i = 0; i < max; i++){
            island = island + "\t|";
            try{
                if(listIsland.get(i).getColourTower().equals(ColourTower.BLACK)){
                    for(int j = 0; j < listIsland.get(i).getNumSubIsland(); j++){

                        island = island + ((char) 27 + "[30m" + "○" + "\u001B[0m");
                    }
                } else if(listIsland.get(i).getColourTower().equals(ColourTower.WHITE)){
                    for(int j = 0; j < listIsland.get(i).getNumSubIsland(); j++){
                        island = island + ((char) 27 + "[38m" + "○" + "\u001B[0m");
                    }
                }
            }catch(MissingTowerException e){}
            if(listIsland.get(i).getNumSubIsland() < 2) {
                island = island + ("\t\t\t\t\t|");
            } else{
                island = island + ("\t\t\t\t|");
            }
        }

        island = island + "\n";

        //metto madre natura dove serve
        for(int i = 0; i < max; i++){
            island = island + "\t|";

            if(listIsland.get(i).getHasMotherNature()) {
                island = island + "\t" + ((char) 27 + "[36m" + "֍mothernature֍" + "\u001B[0m") + "\t|";
            }
            else{
                island = island + ("\t\t\t\t\t|");
            }
        }

        island = island + "\n";

        //stampo il confine in fondo dell'isola
        for(int i = 0; i < max; i++) {
            island = island +("\t|-------------------|");
        }
        island = island +("\n\n");

        /////////////////////////////////////////////////////////////////////////SECONDA FILA DI ISOLE
        island = island +( "\t");
        //scrivo i numeri sopra
        for(int i = 6; i < listIsland.size(); i++){
            island = island + (listIsland.get(i).getNumIsland()+1) + "\t\t\t\t\t\t";
        }

        //scrivo il confine delle isole
        island = island +("\n");
        for(int i = 6; i < listIsland.size(); i++) {
            island = island +("\t|-------------------|");
        }
        island = island +("\n");

        //stampo gli studenti per colore sulle isole
        for(Colour colour : Colour.values()){

            //setto il colore della stringa
            if(colour.equals(Colour.BLUE)){
                colore = "[34m";
            } else if(colour.equals(Colour.RED)){
                colore = "[31m";
            } else if(colour.equals(Colour.PINK)){
                colore = "[35m";
            } else if(colour.equals(Colour.GREEN)){
                colore = "[32m";
            } else if(colour.equals(Colour.YELLOW)){
                colore = "[33m";
            }

            for (int i = 6; i < listIsland.size(); i++) {
                island = island + ("\t|");
                for (int j = 0; j < listIsland.get(i).countStudentsOfColour(colour); j++) {

                    island = island + ((char) 27 + colore + "▪" + "\u001B[0m");
                }
                if (listIsland.get(i).countStudentsOfColour(colour) > 7) {
                    island = island + ("\t\t|");
                } else {
                    island = island + ("\t\t\t\t\t|");
                }
            }

            island = island +("\n");
        }

        //metto le torri
        for(int i = 6; i < listIsland.size(); i++){
            island = island + "\t|";
            try{
                if(listIsland.get(i).getColourTower().equals(ColourTower.BLACK)){
                    for(int j = 0; j < listIsland.get(i).getNumSubIsland(); j++){
                        island = island + ((char) 27 + "[30m" + "○" + "\u001B[0m");
                    }
                } else if(listIsland.get(i).getColourTower().equals(ColourTower.WHITE)){
                    for(int j = 0; j < listIsland.get(i).getNumSubIsland(); j++){
                        island = island + ((char) 27 + "[38m" + "○" + "\u001B[0m");
                    }
                }
            }catch(MissingTowerException e){}

            if(listIsland.get(i).getNumSubIsland() < 2) {
                island = island + ("\t\t\t\t\t|");
            } else{
                island = island + ("\t\t\t\t|");
            }
        }

        island = island + "\n";

        //metto madre natura dove serve
        for(int i = 6; i < listIsland.size(); i++){
            island = island + "\t|";

            if(listIsland.get(i).getHasMotherNature()) {
                island = island + "\t" + ((char) 27 + "[36m" + "֍mothernature֍" + "\u001B[0m") + "\t|";
            }
            else{
                island = island + ("\t\t\t\t\t|");
            }
        }

        island = island + "\n";

        //stampo il confine in fondo dell'isola
        for(int i = 6; i < listIsland.size(); i++) {
            island = island +("\t|-------------------|");
        }
        island = island +("\n");

        return island;
    }

    public void showMoves(String nickname){
        if(this.currentPlayer.equals(nickname)){
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

    public void update(ServerMessage message){

        // --> players
        if(message.getServerHeader().getDescription().equals("MoveStudentInDiningRoom")){

            listPlayer.set(0, (Player) message.getPayload().getParameter("player1"));
            listPlayer.set(1, (Player) message.getPayload().getParameter("player2"));
            if(message.getPayload().containsParameter("player3")){
                listPlayer.set(2, (Player) message.getPayload().getParameter("player3"));
                if(message.getPayload().containsParameter("player4")){
                    listPlayer.set(3, (Player) message.getPayload().getParameter("player4"));
                }
            }
        }

        // --> players, islands
        else if(message.getServerHeader().getDescription().equals("MoveStudentInIsland")){

            for(int i = 0; i < listIsland.size(); i++){
                listIsland.set(i, (Island)message.getPayload().getParameter("island"+i));
            }

            listPlayer.set(0, (Player) message.getPayload().getParameter("player1"));
            listPlayer.set(1, (Player) message.getPayload().getParameter("player2"));
            if(message.getPayload().containsParameter("player3")){
                listPlayer.set(2, (Player) message.getPayload().getParameter("player3"));
                if(message.getPayload().containsParameter("player4")){
                    listPlayer.set(3, (Player) message.getPayload().getParameter("player4"));
                }
            }
        }

        // --> players, clouds
        else if(message.getServerHeader().getDescription().equals("TakeCloud")){
            listCloud.set(0, (Cloud) message.getPayload().getParameter("cloud1"));
            listCloud.set(1, (Cloud) message.getPayload().getParameter("cloud2"));
            if(message.getPayload().containsParameter("cloud3")){
                listCloud.set(2, (Cloud) message.getPayload().getParameter("cloud3"));
                if(message.getPayload().containsParameter("cloud4")){
                    listCloud.set(3, (Cloud) message.getPayload().getParameter("cloud4"));
                }
            }


            listPlayer.set(0, (Player) message.getPayload().getParameter("player1"));
            listPlayer.set(1, (Player) message.getPayload().getParameter("player2"));
            if(message.getPayload().containsParameter("player3")){
                listPlayer.set(2, (Player) message.getPayload().getParameter("player3"));
                if(message.getPayload().containsParameter("player4")){
                    listPlayer.set(3, (Player) message.getPayload().getParameter("player4"));
                }
            }
        }

        // --> mother nature, islands, players, teams
        else if(message.getServerHeader().getDescription().equals("MoveMotherNature")){
            for(int i = 0; i < listIsland.size(); i++){
                listIsland.set(i, (Island)message.getPayload().getParameter("island"+i));
            }

            listPlayer.set(0, (Player) message.getPayload().getParameter("player1"));
            listPlayer.set(1, (Player) message.getPayload().getParameter("player2"));
            if(message.getPayload().containsParameter("player3")){
                listPlayer.set(2, (Player) message.getPayload().getParameter("player3"));
                if(message.getPayload().containsParameter("player4")){
                    listPlayer.set(3, (Player) message.getPayload().getParameter("player4"));
                }
            }

            listTeam.set(0, (Team) message.getPayload().getParameter("team1"));
            listTeam.set(1, (Team) message.getPayload().getParameter("team2"));

            if(message.getPayload().containsParameter("team3")){
                listTeam.set(2, (Team) message.getPayload().getParameter("team3"));
            }

            motherNature = (MotherNature) message.getPayload().getParameter("mothernature");
        }

        // --> players
        else if(message.getServerHeader().getDescription().equals("PlayCard")){

            listPlayer.set(0, (Player) message.getPayload().getParameter("player1"));
            listPlayer.set(1, (Player) message.getPayload().getParameter("player2"));
            if(message.getPayload().containsParameter("player3")){
                listPlayer.set(2, (Player) message.getPayload().getParameter("player3"));
                if(message.getPayload().containsParameter("player4")){
                    listPlayer.set(3, (Player) message.getPayload().getParameter("player4"));
                }
            }
        }

        // --> islands
        else if(message.getServerHeader().getDescription().equals("Fusion")){
            for(int i = 0; i < listIsland.size(); i++){
                if(message.getPayload().containsParameter("island"+i)) {
                    listIsland.set(i, (Island) message.getPayload().getParameter("island" + i));
                } else {
                    listIsland.remove(i);
                }
            }
        }

        // --> characterCardThrown, charactercards, players,
        else if(message.getServerHeader().getDescription().equals("PlayCharacterCard")){

            characterCards.set(0, (ConcreteCharacterCard) message.getPayload().getParameter("charactercard1"));
            characterCards.set(1, (ConcreteCharacterCard) message.getPayload().getParameter("charactercard2"));
            characterCards.set(2, (ConcreteCharacterCard) message.getPayload().getParameter("charactercard3"));

            characterCardThrown = (String) message.getPayload().getParameter("cardthrown");

            listPlayer.set(0, (Player) message.getPayload().getParameter("player1"));
            listPlayer.set(1, (Player) message.getPayload().getParameter("player2"));
            if(message.getPayload().containsParameter("player3")){
                listPlayer.set(2, (Player) message.getPayload().getParameter("player3"));
                if(message.getPayload().containsParameter("player4")){
                    listPlayer.set(3, (Player) message.getPayload().getParameter("player4"));
                }
            }

            for(int i = 0; i < listIsland.size(); i++){
                listIsland.set(i, (Island)message.getPayload().getParameter("island"+i));
            }
        }

        // -->professors, players
        else if(message.getServerHeader().getDescription().equals("CheckProfessor")){

            professors.set(0, (Professor) message.getPayload().getParameter("professor1"));
            professors.set(1, (Professor) message.getPayload().getParameter("professor2"));
            professors.set(2, (Professor) message.getPayload().getParameter("professor3"));
            professors.set(3, (Professor) message.getPayload().getParameter("professor4"));
            professors.set(4, (Professor) message.getPayload().getParameter("professor5"));

            listPlayer.set(0, (Player) message.getPayload().getParameter("player1"));
            listPlayer.set(1, (Player) message.getPayload().getParameter("player2"));
            if(message.getPayload().containsParameter("player3")){
                listPlayer.set(2, (Player) message.getPayload().getParameter("player3"));
                if(message.getPayload().containsParameter("player4")){
                    listPlayer.set(3, (Player) message.getPayload().getParameter("player4"));
                }
            }
        }

        //--> teams, islands, players
        else if(message.getServerHeader().getDescription().equals("CheckTowers")){

            for(int i = 0; i < listIsland.size(); i++){
                listIsland.set(i, (Island)message.getPayload().getParameter("island"+i));
            }

            listTeam.set(0, (Team) message.getPayload().getParameter("team1"));
            listTeam.set(1, (Team) message.getPayload().getParameter("team2"));

            if(message.getPayload().containsParameter("team3")){
                listTeam.set(2, (Team) message.getPayload().getParameter("team3"));
            }

            listPlayer.set(0, (Player) message.getPayload().getParameter("player1"));
            listPlayer.set(1, (Player) message.getPayload().getParameter("player2"));
            if(message.getPayload().containsParameter("player3")){
                listPlayer.set(2, (Player) message.getPayload().getParameter("player3"));
                if(message.getPayload().containsParameter("player4")){
                    listPlayer.set(3, (Player) message.getPayload().getParameter("player4"));
                }
            }
        }

        //--> teams, players, clouds, current player, mother nature, islands, professors, cahractercards, ccharacterCardThrown
        else if(message.getServerHeader().getDescription().equals("STARTGAME")){

            //setto le isole
            //for(int i = 0; i < 12; i++){
                listIsland.add((Island) message.getPayload().getParameter("island0"));
                listIsland.add((Island) message.getPayload().getParameter("island1"));
                listIsland.add((Island) message.getPayload().getParameter("island2"));
                listIsland.add((Island) message.getPayload().getParameter("island3"));
                listIsland.add((Island) message.getPayload().getParameter("island4"));
                listIsland.add((Island) message.getPayload().getParameter("island5"));
                listIsland.add((Island) message.getPayload().getParameter("island6"));
                listIsland.add((Island) message.getPayload().getParameter("island7"));
                listIsland.add((Island) message.getPayload().getParameter("island8"));
                listIsland.add((Island) message.getPayload().getParameter("island9"));
                listIsland.add((Island) message.getPayload().getParameter("island10"));
                listIsland.add((Island) message.getPayload().getParameter("island11"));
            //}

            //setto i player
            listPlayer.add( (Player) message.getPayload().getParameter("player1"));
            listPlayer.add( (Player) message.getPayload().getParameter("player2"));
            if(message.getPayload().containsParameter("player3")){
                listPlayer.add( (Player) message.getPayload().getParameter("player3"));
                if(message.getPayload().containsParameter("player4")){
                    listPlayer.add( (Player) message.getPayload().getParameter("player4"));
                }
            }

            //setto le nuvole
            listCloud.add( (Cloud) message.getPayload().getParameter("cloud1"));
            listCloud.add( (Cloud) message.getPayload().getParameter("cloud2"));
            if(message.getPayload().containsParameter("cloud3")){
                listCloud.add( (Cloud) message.getPayload().getParameter("cloud3"));
                if(message.getPayload().containsParameter("cloud4")){
                    listCloud.add( (Cloud) message.getPayload().getParameter("cloud4"));
                }
            }

            //setto i professori
            professors.add( (Professor) message.getPayload().getParameter("professor1"));
            professors.add( (Professor) message.getPayload().getParameter("professor2"));
            professors.add( (Professor) message.getPayload().getParameter("professor3"));
            professors.add( (Professor) message.getPayload().getParameter("professor4"));
            professors.add( (Professor) message.getPayload().getParameter("professor5"));

            //setto i team
            listTeam.add( (Team) message.getPayload().getParameter("team1"));
            listTeam.add( (Team) message.getPayload().getParameter("team2"));

            if(message.getPayload().containsParameter("team3")){
                listTeam.add( (Team) message.getPayload().getParameter("team3"));
            }

            //setto carta lanciata
            //todo da tenere sott'occhio
            characterCardThrown = (String)message.getPayload().getParameter("charactercardthrown");

            //setto le carte personaggio


            characterCards.add((ConcreteCharacterCard) message.getPayload().getParameter("charactercard1"));
            characterCards.add((ConcreteCharacterCard) message.getPayload().getParameter("charactercard2"));
            characterCards.add((ConcreteCharacterCard) message.getPayload().getParameter("charactercard3"));

            //setto il player corrente
            this.currentPlayer = (String)message.getPayload().getParameter("currentClient");

            //setto mothernature
            motherNature = (MotherNature) message.getPayload().getParameter("mothernature");

            showCoins = (boolean) message.getPayload().getParameter("showCoins");

        }

        //-->clouds
        else if(message.getServerHeader().getDescription().equals("refillcloud")){

            listCloud.set(0, (Cloud) message.getPayload().getParameter("cloud1"));
            listCloud.set(1, (Cloud) message.getPayload().getParameter("cloud2"));
            if(message.getPayload().containsParameter("cloud3")){
                listCloud.set(2, (Cloud) message.getPayload().getParameter("cloud3"));
                if(message.getPayload().containsParameter("cloud4")){
                    listCloud.set(3, (Cloud) message.getPayload().getParameter("cloud4"));
                }
            }
        }

        //--> current player
        else if(message.getServerHeader().getDescription().equals("EndTurn")){
            this.currentPlayer = (String)message.getPayload().getParameter("currentClient");
        }

        showBoard();
    }

    public boolean verifyClient(String nickname){
        if(nickname.equals(currentPlayer)) return true;
        else return false;
    }

    public void endGame(ServerMessage message){
        Team t = (Team) message.getPayload().getParameter("team");
        System.out.println("il team vincitore è il: " + t.getColourTower());
        System.out.println("vuoi giocare un'altra partita? ");
    }

    public void clientError(ServerMessage message){
        System.out.println(message.getServerHeader().getDescription());
    }

}