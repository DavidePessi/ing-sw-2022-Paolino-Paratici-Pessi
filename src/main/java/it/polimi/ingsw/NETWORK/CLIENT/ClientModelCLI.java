package it.polimi.ingsw.NETWORK.CLIENT;

import it.polimi.ingsw.MODEL.*;
import it.polimi.ingsw.MODEL.CharacterCards.ConcreteCharacterCard;
import it.polimi.ingsw.MODEL.CharacterCards.Jester;
import it.polimi.ingsw.MODEL.CharacterCards.Knight;
import it.polimi.ingsw.MODEL.CharacterCards.Minstrell;
import it.polimi.ingsw.MODEL.Exception.MissingTowerException;
import it.polimi.ingsw.NETWORK.MESSAGES.ServerMessage;

import java.util.ArrayList;
import java.util.List;

public class ClientModelCLI {
    private String characterCardThrown;
    private List<Cloud> listCloud;
    private List<Player> listPlayer;
    private List<Team> listTeam;
    private List<Island> listIsland;
    private MotherNature motherNature;
    private List<Professor> professors;
    private List<ConcreteCharacterCard> characterCards;


    public ClientModelCLI(){

        //todo poi tutta questa inizializzazioneè precaria solo per testare
        listCloud = new ArrayList<>();
        listIsland = new ArrayList<>();
        listPlayer = new ArrayList<>();
        listTeam = new ArrayList<>();
        professors = new ArrayList<>();

        listTeam.add(new Team(ColourTower.WHITE, 8));
        listTeam.add(new Team(ColourTower.BLACK, 8));

        listPlayer.add(new Player("io", listTeam.get(0)));
        listPlayer.add(new Player("tu", listTeam.get(1)));

        listPlayer.get(0).addProfessor(new Professor(Colour.BLUE));

        listCloud.add(new Cloud());
        listCloud.add(new Cloud());

        StudentGroup s = new StudentGroup();
        s.addStudent(Colour.YELLOW);
        s.addStudent(Colour.YELLOW);
        s.addStudent(Colour.GREEN);
        listCloud.get(0).addStudents(s);

        listIsland.add(new Island(0));
        listIsland.add(new Island(1));
        listIsland.add(new Island(2));
        listIsland.add(new Island(3));
        listIsland.add(new Island(4));
        listIsland.add(new Island(5));
        listIsland.add(new Island(6));
        listIsland.add(new Island(7));
        listIsland.add(new Island(8));
        listIsland.add(new Island(9));
        listIsland.add(new Island(10));
        listIsland.add(new Island(11));

        motherNature = new MotherNature(listIsland.get(0));
        listIsland.get(0).setMotherNature(true);

        professors.add(new Professor(Colour.BLUE));
        professors.add(new Professor(Colour.GREEN));
        professors.add(new Professor(Colour.PINK));
        professors.add(new Professor(Colour.RED));
        professors.add(new Professor(Colour.YELLOW));

        characterCards = new ArrayList<>();
        characterCards.add(0, new Jester(new Game("io", "tu")));
        characterCards.add(1, new Knight(new Game("io", "tu")));
        characterCards.add(2, new Minstrell(new Game("io", "tu")));
        characterCardThrown = "Jester";

        StudentGroup s1 = new StudentGroup();
        s1.addStudent(Colour.PINK);
        s1.addStudent(Colour.PINK);
        s1.addStudent(Colour.BLUE);
        s1.addStudent(Colour.BLUE);
        s1.addStudent(Colour.RED);

        listPlayer.get(0).addStudentsToEntrance(s1);

        listIsland.get(3).addStudent(Colour.YELLOW);
        listIsland.get(2).addStudent(Colour.BLUE);
        listIsland.get(7).addStudent(Colour.RED);
        //todo se la stringa è vuota mettere nessuna carta lanciata
    }

    public void showBoard(){
        String Board = "";

        //-------------------------------STAMPA PLAYER------------------------------
        for(Player player : listPlayer){
            Board = Board +("player: " + player.getNicknameClient());
            Board = Board +("\nteam: " + player.getTeam().getColourTower() + "{" + player.getTeam().getNumberOfTower() + "}\n");

            //metto i professori
            Board = Board + "professors: ";

            if(player.professorPresent(Colour.RED))Board = Board + (char) 27 + "[31m" + "◊" + "\u001B[0m";
            if(player.professorPresent(Colour.YELLOW))Board = Board + (char) 27 + "[33m" + "◊" + "\u001B[0m";
            if(player.professorPresent(Colour.GREEN))Board = Board + (char) 27 + "[32m" + "◊" + "\u001B[0m";
            if(player.professorPresent(Colour.BLUE))Board = Board + (char) 27 + "[34m" + "◊" + "\u001B[0m";
            if(player.professorPresent(Colour.PINK))Board = Board + (char) 27 + "[35m" + "◊" + "\u001B[0m";

            Board = Board +( "\nentrance : \n[");

            for (int i = 0; i < player.getEntrance().getStudentGroup().countStudentsOfColour(Colour.RED); i++) {
                Board = Board +((char) 27 + "[31m" + "▪" + "\u001B[0m");
            }

            Board = Board +( "]\n[");
            for (int i = 0; i < player.getEntrance().getStudentGroup().countStudentsOfColour(Colour.YELLOW); i++) {
                Board = Board +((char) 27 + "[33m" + "▪" + "\u001B[0m");
            }

            Board = Board +( "]\n[");
            for (int i = 0; i < player.getEntrance().getStudentGroup().countStudentsOfColour(Colour.GREEN); i++) {
                Board = Board +((char) 27 + "[32m" + "▪" + "\u001B[0m");
            }

            Board = Board +( "]\n[");
            for (int i = 0; i < player.getEntrance().getStudentGroup().countStudentsOfColour(Colour.BLUE); i++) {
                Board = Board +((char) 27 + "[34m" + "▪" + "\u001B[0m");
            }

            Board = Board +( "]\n[");
            for (int i = 0; i < player.getEntrance().getStudentGroup().countStudentsOfColour(Colour.PINK); i++) {
                Board = Board +((char) 27 + "[35m" + "▪" + "\u001B[0m");
            }

            Board = Board +( "]\n");


            Board = Board +( "DinnerRoom : \n[");
            for (int i = 0; i < player.numStudentsDiningRoom(Colour.RED); i++) {
                Board = Board +((char) 27 + "[31m" + "▪" + "\u001B[0m");
            }
            Board = Board +( "]\n[");
            for (int i = 0; i < player.numStudentsDiningRoom(Colour.YELLOW); i++) {
                Board = Board +((char) 27 + "[33m" + "▪" + "\u001B[0m");
            }
            Board = Board +( "]\n[");
            for (int i = 0; i < player.numStudentsDiningRoom(Colour.GREEN); i++) {
                Board = Board +((char) 27 + "[32m" + "▪" + "\u001B[0m");
            }
            Board = Board +( "]\n[");
            for (int i = 0; i < player.numStudentsDiningRoom(Colour.BLUE); i++) {
                Board = Board +((char) 27 + "[34m" + "▪" + "\u001B[0m");
            }
            Board = Board +( "]\n[");
            for (int i = 0; i < player.numStudentsDiningRoom(Colour.PINK); i++) {
                Board = Board +((char) 27 + "[35m" + "▪" + "\u001B[0m");
            }
            Board = Board +( "]\n");

        }


        //-------------------------STAMPA CARTE PERSONAGGIO-------------------------
        Board = Board +( "Character Cards :\n");
        for(ConcreteCharacterCard card : characterCards){
            Board = Board +("\t" + card.getNameCard() + " cost: " + card.getPrice() +"\n");
        }


        //-------------------------------STAMPA ISOLE-------------------------------
        //setto una variabile colore
        String colore = "";
        int max = 6;

        Board = Board +( "Islands :\n\t");

        //voglio tornare a capo se ci sono più di 6 isole quindi metto due casi
        //setto quanto è lunga la prima fila
        if(max > listIsland.size()){
            max = listIsland.size();
        }

        /////////////////////////////////////////////////////////////////////////PRIMA FILA DI ISOLE
        //scrivo i numeri sopra
        for(int i = 0; i < max; i++){
            Board = Board + listIsland.get(i).getNumIsland() + "\t\t\t\t\t\t";
        }

        //scrivo il confine delle isole
        Board = Board +("\n");
        for(int i = 0; i < max; i++) {
            Board = Board +("\t|-------------------|");
        }
        Board = Board +("\n");

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
                Board = Board + ("\t|");
                for (int j = 0; j < listIsland.get(i).countStudentsOfColour(colour); j++) {

                    Board = Board + ((char) 27 + colore + "▪" + "\u001B[0m");
                }
                if (listIsland.get(i).countStudentsOfColour(colour) > 7) {
                    Board = Board + ("\t\t|");
                } else {
                    Board = Board + ("\t\t\t\t\t|");
                }
            }

            Board = Board +("\n");
        }

        //metto le torri
        for(int i = 0; i < max; i++){
            Board = Board + "\t|";
            try{
                if(listIsland.get(i).getColourTower().equals(ColourTower.BLACK)){
                    for(int j = 0; j < listIsland.get(i).getNumSubIsland(); j++){
                        Board = Board + ((char) 27 + "[30m" + "○" + "\u001B[0m");
                    }
                } else if(listIsland.get(i).getColourTower().equals(ColourTower.WHITE)){
                    for(int j = 0; j < listIsland.get(i).getNumSubIsland(); j++){
                        Board = Board + ((char) 27 + "[38m" + "○" + "\u001B[0m");
                    }
                }
            }catch(MissingTowerException e){}
            Board = Board + ("\t\t\t\t\t|");
        }

        Board = Board + "\n";

        //metto madre natura dove serve
        for(int i = 0; i < max; i++){
            Board = Board + "\t|";

            if(listIsland.get(i).getHasMotherNature()) {
                Board = Board + "\t" + ((char) 27 + "[36m" + "֍mothernature֍" + "\u001B[0m") + "\t|";
            }
            else{
                Board = Board + ("\t\t\t\t\t|");
            }
        }

        Board = Board + "\n";

        //stampo il confine in fondo dell'isola
        for(int i = 0; i < max; i++) {
            Board = Board +("\t|-------------------|");
        }
        Board = Board +("\n\n");

        /////////////////////////////////////////////////////////////////////////SECONDA FILA DI ISOLE
        Board = Board +( "\t");
        //scrivo i numeri sopra
        for(int i = 6; i < listIsland.size(); i++){
            Board = Board + listIsland.get(i).getNumIsland() + "\t\t\t\t\t\t";
        }

        //scrivo il confine delle isole
        Board = Board +("\n");
        for(int i = 6; i < listIsland.size(); i++) {
            Board = Board +("\t|-------------------|");
        }
        Board = Board +("\n");

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
                Board = Board + ("\t|");
                for (int j = 0; j < listIsland.get(i).countStudentsOfColour(colour); j++) {

                    Board = Board + ((char) 27 + colore + "▪" + "\u001B[0m");
                }
                if (listIsland.get(i).countStudentsOfColour(colour) > 7) {
                    Board = Board + ("\t\t|");
                } else {
                    Board = Board + ("\t\t\t\t\t|");
                }
            }

            Board = Board +("\n");
        }

        //metto le torri
        for(int i = 6; i < listIsland.size(); i++){
            Board = Board + "\t|";
            try{
                if(listIsland.get(i).getColourTower().equals(ColourTower.BLACK)){
                    for(int j = 0; j < listIsland.get(i).getNumSubIsland(); j++){
                        Board = Board + ((char) 27 + "[30m" + "○" + "\u001B[0m");
                    }
                } else if(listIsland.get(i).getColourTower().equals(ColourTower.WHITE)){
                    for(int j = 0; j < listIsland.get(i).getNumSubIsland(); j++){
                        Board = Board + ((char) 27 + "[38m" + "○" + "\u001B[0m");
                    }
                }
            }catch(MissingTowerException e){}
            Board = Board + ("\t\t\t\t\t|");
        }

        Board = Board + "\n";

        //metto madre natura dove serve
        for(int i = 6; i < listIsland.size(); i++){
            Board = Board + "\t|";

            if(listIsland.get(i).getHasMotherNature()) {
                Board = Board + "\t" + ((char) 27 + "[36m" + "֍mothernature֍" + "\u001B[0m") + "\t|";
            }
            else{
                Board = Board + ("\t\t\t\t\t|");
            }
        }

        Board = Board + "\n";

        //stampo il confine in fondo dell'isola
        for(int i = 6; i < listIsland.size(); i++) {
            Board = Board +("\t|-------------------|");
        }
        Board = Board +("\n");

        //------------------------------STAMPA NUVOLE-------------------------------
        for(int j = 0; j < listCloud.size(); j++){
            Board = Board + "cloud " + j + ": {";

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
                    Board = Board + (char) 27 + colore + "▪" + "\u001B[0m";
                }
            }
            Board = Board + "}\n";
        }

        System.out.println(Board);
    }

    public void update(ServerMessage message){

        //sposto uno studente da entrance a dining room
        // --> professors e players(entrance e diningroom e professori)
        if(message.getPayload().containsParameter("MoveStudentInDiningRoom")){

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
        //sposto uno studente da entrance a island
        // --> players(entrance) e islands
        else if(message.getPayload().containsParameter("MoveStudentInIsland")){
            //todo problema con liste che cambiano il numero di oggetti perchè le stringhe non sono ciclabili

            listPlayer.set(0, (Player) message.getPayload().getParameter("player1"));
            listPlayer.set(1, (Player) message.getPayload().getParameter("player2"));
            if(message.getPayload().containsParameter("player3")){
                listPlayer.set(2, (Player) message.getPayload().getParameter("player3"));
                if(message.getPayload().containsParameter("player4")){
                    listPlayer.set(3, (Player) message.getPayload().getParameter("player4"));
                }
            }
        }
        //prendo una nuvola
        // --> players(entrance) e nuvole
        else if(message.getPayload().containsParameter("TakeCloud")){
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
        //sposto madre natura
        // --> teams, madre natura e isole
        else if(message.getPayload().containsParameter("MoveMotherNature")){
            //todo stesso problema delle isole di prima
            listTeam.set(0, (Team) message.getPayload().getParameter("team1"));
            listTeam.set(1, (Team) message.getPayload().getParameter("team2"));

            if(message.getPayload().containsParameter("team3")){
                listTeam.set(2, (Team) message.getPayload().getParameter("team3"));
            }

            motherNature = (MotherNature) message.getPayload().getParameter("mothernature");
        }
        //gioco una carta
        // --> players(deck)
        else if(message.getPayload().containsParameter("UseCard")){

            listPlayer.set(0, (Player) message.getPayload().getParameter("player1"));
            listPlayer.set(1, (Player) message.getPayload().getParameter("player2"));
            if(message.getPayload().containsParameter("player3")){
                listPlayer.set(2, (Player) message.getPayload().getParameter("player3"));
                if(message.getPayload().containsParameter("player4")){
                    listPlayer.set(3, (Player) message.getPayload().getParameter("player4"));
                }
            }
        }
        //fusione isole
        // --> isole
        else if(message.getPayload().containsParameter("Fusion")){
            //todo stesso problema delle isole di prima
        }
        //gioco una carta personaggio
        // --> carta players(monete), carta lanciata
        else if(message.getPayload().containsParameter("PlayCard")){

            characterCards.set(0, (ConcreteCharacterCard) message.getPayload().getParameter("card1"));
            characterCards.set(1, (ConcreteCharacterCard) message.getPayload().getParameter("card2"));
            characterCards.set(2, (ConcreteCharacterCard) message.getPayload().getParameter("card3"));

            characterCardThrown = (String) message.getPayload().getParameter("cardthrown");

            listPlayer.set(0, (Player) message.getPayload().getParameter("player1"));
            listPlayer.set(1, (Player) message.getPayload().getParameter("player2"));
            if(message.getPayload().containsParameter("player3")){
                listPlayer.set(2, (Player) message.getPayload().getParameter("player3"));
                if(message.getPayload().containsParameter("player4")){
                    listPlayer.set(3, (Player) message.getPayload().getParameter("player4"));
                }
            }
        }

    }

}