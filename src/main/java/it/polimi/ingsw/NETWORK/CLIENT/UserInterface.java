package it.polimi.ingsw.NETWORK.CLIENT;

import it.polimi.ingsw.MODEL.*;
import it.polimi.ingsw.MODEL.CharacterCards.*;
import it.polimi.ingsw.NETWORK.CLIENT.CLI.ClientModelCLI;
import it.polimi.ingsw.NETWORK.MESSAGES.*;

import java.util.ArrayList;
import java.util.List;

public class UserInterface {

    protected static String characterCardThrown;
    protected static List<Cloud> listCloud;
    protected static List<Player> listPlayer;
    protected static List<Team> listTeam;
    protected static List<Island> listIsland;
    protected static MotherNature motherNature;
    protected static List<Professor> professors;
    protected static List<ConcreteCharacterCard> characterCards;
    protected static String currentPlayer;
    protected static boolean showCoins;

    protected static LoginController controller;

    public UserInterface(){
        listCloud = new ArrayList<>();
        listIsland = new ArrayList<>();
        listPlayer = new ArrayList<>();
        listTeam = new ArrayList<>();
        professors = new ArrayList<>();
        characterCards = new ArrayList<>();
    }

    //METODI CHE EFFETTUANO OPERAZIONI
    public static void update(ServerMessage message){

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
                if(message.getPayload().containsParameter("island"+i)){
                    listIsland.set(i, (Island) message.getPayload().getParameter("island" +i));
                }
                else {
                    listIsland.remove(i);
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

            if(message.getPayload().getParameter("charactercard1") instanceof Knight){
                characterCards.set(0, (Knight) message.getPayload().getParameter("charactercard1"));
            }else if(message.getPayload().getParameter("charactercard1") instanceof Priest){
                characterCards.set(0, (Priest) message.getPayload().getParameter("charactercard1"));
            }else if(message.getPayload().getParameter("charactercard1") instanceof Jester){
                characterCards.set(0, (Jester) message.getPayload().getParameter("charactercard1"));
            }else if(message.getPayload().getParameter("charactercard1") instanceof Minstrell){
                characterCards.set(0, (Minstrell) message.getPayload().getParameter("charactercard1"));
            }else if(message.getPayload().getParameter("charactercard1") instanceof Woman){
                characterCards.set(0, (Woman) message.getPayload().getParameter("charactercard1"));
            }else if(message.getPayload().getParameter("charactercard1") instanceof PostMan){
                characterCards.set(0, (PostMan) message.getPayload().getParameter("charactercard1"));
            }else if(message.getPayload().getParameter("charactercard1") instanceof Satyr){
                characterCards.set(0, (Satyr) message.getPayload().getParameter("charactercard1"));
            }else if(message.getPayload().getParameter("charactercard1") instanceof Pirate){
                characterCards.set(0, (Pirate) message.getPayload().getParameter("charactercard1"));
            }
            if(message.getPayload().getParameter("charactercard2") instanceof Knight){
                characterCards.set(1, (Knight) message.getPayload().getParameter("charactercard2"));
            }else if(message.getPayload().getParameter("charactercard2") instanceof Priest){
                characterCards.set(1, (Priest) message.getPayload().getParameter("charactercard2"));
            }else if(message.getPayload().getParameter("charactercard2") instanceof Jester){
                characterCards.set(1, (Jester) message.getPayload().getParameter("charactercard2"));
            }else if(message.getPayload().getParameter("charactercard2") instanceof Minstrell){
                characterCards.set(1, (Minstrell) message.getPayload().getParameter("charactercard2"));
            }else if(message.getPayload().getParameter("charactercard2") instanceof Woman){
                characterCards.set(1, (Woman) message.getPayload().getParameter("charactercard2"));
            }else if(message.getPayload().getParameter("charactercard2") instanceof PostMan){
                characterCards.set(1, (PostMan) message.getPayload().getParameter("charactercard2"));
            }else if(message.getPayload().getParameter("charactercard2") instanceof Satyr){
                characterCards.set(1, (Satyr) message.getPayload().getParameter("charactercard2"));
            }else if(message.getPayload().getParameter("charactercard2") instanceof Pirate){
                characterCards.set(1, (Pirate) message.getPayload().getParameter("charactercard2"));
            }
            if(message.getPayload().getParameter("charactercard3") instanceof Knight){
                characterCards.set(2, (Knight) message.getPayload().getParameter("charactercard3"));
            }else if(message.getPayload().getParameter("charactercard3") instanceof Priest){
                characterCards.set(2, (Priest) message.getPayload().getParameter("charactercard3"));
            }else if(message.getPayload().getParameter("charactercard3") instanceof Jester){
                characterCards.set(2, (Jester) message.getPayload().getParameter("charactercard3"));
            }else if(message.getPayload().getParameter("charactercard3") instanceof Minstrell){
                characterCards.set(2, (Minstrell) message.getPayload().getParameter("charactercard3"));
            }else if(message.getPayload().getParameter("charactercard3") instanceof Woman){
                characterCards.set(2, (Woman) message.getPayload().getParameter("charactercard3"));
            }else if(message.getPayload().getParameter("charactercard3") instanceof PostMan){
                characterCards.set(2, (PostMan) message.getPayload().getParameter("charactercard3"));
            }else if(message.getPayload().getParameter("charactercard3") instanceof Satyr){
                characterCards.set(2, (Satyr) message.getPayload().getParameter("charactercard3"));
            }else if(message.getPayload().getParameter("charactercard3") instanceof Pirate){
                characterCards.set(2, (Pirate) message.getPayload().getParameter("charactercard3"));
            }
            //characterCards.set(0, (ConcreteCharacterCard) message.getPayload().getParameter("charactercard1"));
            //characterCards.set(1, (ConcreteCharacterCard) message.getPayload().getParameter("charactercard2"));
            //characterCards.set(2, (ConcreteCharacterCard) message.getPayload().getParameter("charactercard3"));

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
            if(message.getPayload().getParameter("charactercard1") instanceof Knight){
                characterCards.add(0, (Knight) message.getPayload().getParameter("charactercard1"));
            }else if(message.getPayload().getParameter("charactercard1") instanceof Priest){
                characterCards.add(0, (Priest) message.getPayload().getParameter("charactercard1"));
            }else if(message.getPayload().getParameter("charactercard1") instanceof Jester){
                characterCards.add(0, (Jester) message.getPayload().getParameter("charactercard1"));
            }else if(message.getPayload().getParameter("charactercard1") instanceof Minstrell){
                characterCards.add(0, (Minstrell) message.getPayload().getParameter("charactercard1"));
            }else if(message.getPayload().getParameter("charactercard1") instanceof Woman){
                characterCards.add(0, (Woman) message.getPayload().getParameter("charactercard1"));
            }else if(message.getPayload().getParameter("charactercard1") instanceof PostMan){
                characterCards.add(0, (PostMan) message.getPayload().getParameter("charactercard1"));
            }else if(message.getPayload().getParameter("charactercard1") instanceof Satyr){
                characterCards.add(0, (Satyr) message.getPayload().getParameter("charactercard1"));
            }else if(message.getPayload().getParameter("charactercard1") instanceof Pirate){
                characterCards.add(0, (Pirate) message.getPayload().getParameter("charactercard1"));
            }
            if(message.getPayload().getParameter("charactercard2") instanceof Knight){
                characterCards.add(1, (Knight) message.getPayload().getParameter("charactercard2"));
            }else if(message.getPayload().getParameter("charactercard2") instanceof Priest){
                characterCards.add(1, (Priest) message.getPayload().getParameter("charactercard2"));
            }else if(message.getPayload().getParameter("charactercard2") instanceof Jester){
                characterCards.add(1, (Jester) message.getPayload().getParameter("charactercard2"));
            }else if(message.getPayload().getParameter("charactercard2") instanceof Minstrell){
                characterCards.add(1, (Minstrell) message.getPayload().getParameter("charactercard2"));
            }else if(message.getPayload().getParameter("charactercard2") instanceof Woman){
                characterCards.add(1, (Woman) message.getPayload().getParameter("charactercard2"));
            }else if(message.getPayload().getParameter("charactercard2") instanceof PostMan){
                characterCards.add(1, (PostMan) message.getPayload().getParameter("charactercard2"));
            }else if(message.getPayload().getParameter("charactercard2") instanceof Satyr){
                characterCards.add(1, (Satyr) message.getPayload().getParameter("charactercard2"));
            }else if(message.getPayload().getParameter("charactercard2") instanceof Pirate){
                characterCards.add(1, (Pirate) message.getPayload().getParameter("charactercard2"));
            }
            if(message.getPayload().getParameter("charactercard3") instanceof Knight){
                characterCards.add(2, (Knight) message.getPayload().getParameter("charactercard3"));
            }else if(message.getPayload().getParameter("charactercard3") instanceof Priest){
                characterCards.add(2, (Priest) message.getPayload().getParameter("charactercard3"));
            }else if(message.getPayload().getParameter("charactercard3") instanceof Jester){
                characterCards.add(2, (Jester) message.getPayload().getParameter("charactercard3"));
            }else if(message.getPayload().getParameter("charactercard3") instanceof Minstrell){
                characterCards.add(2, (Minstrell) message.getPayload().getParameter("charactercard3"));
            }else if(message.getPayload().getParameter("charactercard3") instanceof Woman){
                characterCards.add(2, (Woman) message.getPayload().getParameter("charactercard3"));
            }else if(message.getPayload().getParameter("charactercard3") instanceof PostMan){
                characterCards.add(2, (PostMan) message.getPayload().getParameter("charactercard3"));
            }else if(message.getPayload().getParameter("charactercard3") instanceof Satyr){
                characterCards.add(2, (Satyr) message.getPayload().getParameter("charactercard3"));
            }else if(message.getPayload().getParameter("charactercard3") instanceof Pirate){
                characterCards.add(2, (Pirate) message.getPayload().getParameter("charactercard3"));
            }

            //characterCards.add((ConcreteCharacterCard) message.getPayload().getParameter("charactercard1"));
            //characterCards.add((ConcreteCharacterCard) message.getPayload().getParameter("charactercard2"));
            //characterCards.add((ConcreteCharacterCard) message.getPayload().getParameter("charactercard3"));

            //setto il player corrente
            ClientModelCLI.currentPlayer = (String)message.getPayload().getParameter("currentClient");

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
            ClientModelCLI.currentPlayer = (String)message.getPayload().getParameter("currentClient");
        }
    }

    public static boolean verifyClient(String nickname){
        if(nickname.equals(currentPlayer)) return true;
        else return false;
    }
}
