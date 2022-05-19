package it.polimi.ingsw.MODEL;

import it.polimi.ingsw.MODEL.CharacterCards.*;
import it.polimi.ingsw.MODEL.Exception.*;
import it.polimi.ingsw.NETWORK.MESSAGES.Payload;
import it.polimi.ingsw.NETWORK.MESSAGES.ServerAction;
import it.polimi.ingsw.NETWORK.MESSAGES.ServerHeader;
import it.polimi.ingsw.NETWORK.MESSAGES.ServerMessage;
import it.polimi.ingsw.NETWORK.VIEW.RemoteView;
import it.polimi.ingsw.NETWORK.UTILS.Observable;
import java.util.*;


public class Game extends Observable {
    private int numPlayer;
    private String characterCardThrown;
    private Player lastFirstPlayer;
    private List<Cloud> listCloud;
    private List<Player> listPlayer;
    private List<Team> listTeam;
    private List<Island> listIsland;
    private Bag bag;
    private MotherNature motherNature;
    private List<Professor> professors;
    private List<ConcreteCharacterCard> characterCards;
    private String currentPlayer;

    public Game(String nickname1, String nickname2) {

        listPlayer = new ArrayList<>();
        listTeam = new ArrayList<>();
        listTeam.add(new Team(ColourTower.WHITE, 8));
        listTeam.add(new Team(ColourTower.BLACK, 8));

        listPlayer.add(new Player(nickname1, listTeam.get(0)));
        listPlayer.add(new Player(nickname2, listTeam.get(1)));

        listCloud = new ArrayList<>();
        listCloud.add(new Cloud());
        listCloud.add(new Cloud());

        bag = new Bag();

        listIsland = new ArrayList<>();
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

        professors = new ArrayList<>();
        professors.add(new Professor(Colour.BLUE));
        professors.add(new Professor(Colour.GREEN));
        professors.add(new Professor(Colour.PINK));
        professors.add(new Professor(Colour.RED));
        professors.add(new Professor(Colour.YELLOW));

        characterCards = new ArrayList<>();
        characterCardThrown = "";
        currentPlayer = listPlayer.get(0).getNicknameClient();
    }

    public Game(String nickname1, String nickname2, String nickname3) {

        listPlayer = new ArrayList<>();
        listTeam = new ArrayList<>();
        listTeam.add(new Team(ColourTower.WHITE, 6));
        listTeam.add(new Team(ColourTower.BLACK, 6));
        listTeam.add(new Team(ColourTower.GREY, 6));

        listPlayer.add(new Player(nickname1, listTeam.get(0)));
        listPlayer.add(new Player(nickname2, listTeam.get(1)));
        listPlayer.add(new Player(nickname3, listTeam.get(2)));

        listCloud = new ArrayList<>();
        listCloud.add(new Cloud());
        listCloud.add(new Cloud());
        listCloud.add(new Cloud());

        bag = new Bag();

        listIsland = new ArrayList<>();
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

        professors = new ArrayList<>();
        professors.add(new Professor(Colour.BLUE));
        professors.add(new Professor(Colour.GREEN));
        professors.add(new Professor(Colour.PINK));
        professors.add(new Professor(Colour.RED));
        professors.add(new Professor(Colour.YELLOW));

        characterCards = new ArrayList<>();

        characterCardThrown = "";
        currentPlayer = listPlayer.get(0).getNicknameClient();
    }

    public Game(String nickname1, String nickname2, String nickname3, String nickname4) {

        listPlayer = new ArrayList<>();
        listTeam = new ArrayList<>();
        listTeam.add(new Team(ColourTower.WHITE, 8));
        listTeam.add(new Team(ColourTower.BLACK, 8));

        listPlayer.add(new Player(nickname1, listTeam.get(0)));
        listPlayer.add(new Player(nickname2, listTeam.get(1)));
        listPlayer.add(new Player(nickname3, listTeam.get(0)));
        listPlayer.add(new Player(nickname4, listTeam.get(1)));

        listCloud = new ArrayList<>();
        listCloud.add(new Cloud());
        listCloud.add(new Cloud());
        listCloud.add(new Cloud());
        listCloud.add(new Cloud());


        bag = new Bag();

        listIsland = new ArrayList<>();
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

        professors = new ArrayList<>();
        professors.add(new Professor(Colour.BLUE));
        professors.add(new Professor(Colour.GREEN));
        professors.add(new Professor(Colour.PINK));
        professors.add(new Professor(Colour.RED));
        professors.add(new Professor(Colour.YELLOW));

        characterCards = new ArrayList<>();

        characterCardThrown = "";
        currentPlayer = listPlayer.get(0).getNicknameClient();
    }

    public void startGame() throws MissingStudentException {

        //estrarre casualmente 3 carte
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            boolean isPresent = false;
            int num = random.nextInt(8);

            if (num == 0) {
                for (CharacterCard c : characterCards) {
                    if (c.getNameCard().equals("Jester")) {
                        isPresent = true;
                    }
                }
                if (!isPresent) {
                    characterCards.add(new Jester(this));
                } else {
                    i--;
                }
            }

            if (num == 1) {
                for (CharacterCard c : characterCards) {
                    if (c.getNameCard().equals("Knight")) {
                        isPresent = true;
                    }
                }
                if (!isPresent) {
                    characterCards.add(new Knight(this));
                } else {
                    i--;
                }
            }

            if (num == 2) {
                for (CharacterCard c : characterCards) {
                    if (c.getNameCard().equals("Minstrell")) {
                        isPresent = true;
                    }
                }
                if (!isPresent) {
                    characterCards.add(new Minstrell(this));
                } else {
                    i--;
                }
            }

            if (num == 3) {
                for (CharacterCard c : characterCards) {
                    if (c.getNameCard().equals("Pirate")) {
                        isPresent = true;
                    }
                }
                if (!isPresent) {
                    characterCards.add(new Pirate(this));
                } else {
                    i--;
                }
            }

            if (num == 4) {
                for (CharacterCard c : characterCards) {
                    if (c.getNameCard().equals("PostMan")) {
                        isPresent = true;
                    }
                }
                if (!isPresent) {
                    characterCards.add(new PostMan(this));
                } else {
                    i--;
                }
            }

            if (num == 5) {
                for (CharacterCard c : characterCards) {
                    if (c.getNameCard().equals("Priest")) {
                        isPresent = true;
                    }
                }
                if (!isPresent) {
                    characterCards.add(new Priest(this));
                } else {
                    i--;
                }
            }

            if (num == 6) {
                for (CharacterCard c : characterCards) {
                    if (c.getNameCard().equals("Satyr")) {
                        isPresent = true;
                    }
                }
                if (!isPresent) {
                    characterCards.add(new Satyr(this));
                } else {
                    i--;
                }
            }

            if (num == 7) {
                for (CharacterCard c : characterCards) {
                    if (c.getNameCard().equals("Woman")) {
                        isPresent = true;
                    }
                }
                if (!isPresent) {
                    characterCards.add(new Woman(this));
                } else {
                    i--;
                }
            }
        }
        /*
            characterCards.add(0, new Jester(this));
            characterCards.add(1, new Knight(this));
            characterCards.add(2, new Minstrell(this));
            characterCards.add(3, new Pirate(this));
            characterCards.add(4, new PostMan(this));
            characterCards.add(5, new Priest(this));
            characterCards.add(6, new Satyr(this));
            characterCards.add(7, new Woman(this));
        */
        //characterCards.add(1, new ConcreteCharacterCard());
        //characterCards.add(2, new ConcreteCharacterCard());

        for (Cloud cloud : listCloud) { //per ogni nuvola aggiungo 3 studenti estratti casualmente dalla bag
            if (cloud != null) {
                StudentGroup studentGroup = new StudentGroup();
                for (int i = 0; i < 3; i++) {
                    studentGroup.addStudent(bag.pullOut());
                }
                cloud.addStudents(studentGroup);
            }
        }


        /*
         * creo una lista di studenti (2 per ogni tipo)
         * ne metto 2 in ogni isola eccetto per l'isola dove c'è madre natura e quella opposta
         */
        StudentGroup startingPullOut = bag.startingPullOut();
        for (Island island : listIsland) {
            if (island != null && island.getNumIsland() != 0 && island.getNumIsland() != 6) {
                //controlla che l'isola non sia quella di madre natura e nemmeno l'opposta
                island.addStudent(startingPullOut.pullOut());

            }
        }

        for (Player player : listPlayer) { //per ogni player aggiungo nella entrance i 7 studenti estratti casualmente dalla bag
            if (player != null) {
                StudentGroup studentGroup = new StudentGroup();
                for (int i = 0; i < 7; i++) {
                    studentGroup.addStudent(bag.pullOut());
                }
                player.addStudentsToEntrance(studentGroup);
            }
        }
    }

    /*
    * if the game is finished and there's a winner return True, else return False
    */
    public boolean checkWin() {
        if (listIsland.size() <= 3 || bag.size() == 0) { //if there's 3 or less island or the bag is empty, the game ends
            return true;
        } else {
            for (Player p : listPlayer) {
                if (p.getTeam().getNumberOfTower() == 0 || p.getDeck().size() == 0) { //if there's a  player with 0 towers or without card, the game ends
                    return true;
                }
            }
            return false;
        }
    }

    public Team theWinnerIs() {
        int MinTower = listTeam.get(0).getNumberOfTower();
        int numProfteam1 = 0;
        int numProfteam2 = 0;
        Team WinTeam = listTeam.get(0);

        //conto chi ha meno torri
        for (Team t : listTeam) {
            if (t.getNumberOfTower() < MinTower) {
                MinTower = t.getNumberOfTower();
                WinTeam = t;
            }
        }

        //verifico che se ci sono pareggi il vincitore è quello con più professori
        for (Team t : listTeam) {
            if (t.getNumberOfTower() == MinTower && !t.equals(WinTeam)) {
                numProfteam1 = 0;
                numProfteam2 = 0;

                for (Player player : listPlayer) {
                    if (player.getTeam() == WinTeam) numProfteam1 = numProfteam1 + player.numProfessor();
                    else if (player.getTeam() == t) numProfteam2 = numProfteam2 + player.numProfessor();
                }
                if (numProfteam2 > numProfteam1) {
                    WinTeam = t;
                }
            }
        }
        return WinTeam;
    }


    public void doMoveMotherNature(int numMovement) { //metti eccezione
        /*
        1. viene sommato il numero dell'isola su cui si trova madre natura con il numero di spostamenti che deve fare
        2. si fa modulo num isole che ci sono, si trova il numero dell'isola su cui madre natura si deve spostare
        3. questo numero è dato alla funzione getIsland, che ritorna l'oggetto isola avente quel numero
         */
        if (characterCardThrown.equals("PostMan")) {
            try {
                //risetto la carta lanciata a nessuna
                characterCardThrown = "";

                //faccio il controllo del movimento di madre natura con +2
                if (numMovement <= this.getPlayer(currentPlayer).getLastPlayedCard().getMovement() + 2) {
                    motherNature.move(getIsland((numMovement + motherNature.getNumIsland()) % listIsland.size()));
                }
            } catch (MissingPlayerException | MissingCardException e) {
                e.printStackTrace();
            }
        } else {
            try {
                if (numMovement <= this.getPlayer(currentPlayer).getLastPlayedCard().getMovement()) {
                    motherNature.move(getIsland((numMovement + motherNature.getNumIsland()) % listIsland.size()));
                }
            } catch (MissingPlayerException | MissingCardException e) {
                e.printStackTrace();
            }
        }
        sendBoard("MoveMotherNature");
    }


    public void doMoveStudentInDiningRoom(String nickname, Colour colour) {
        try {
            this.getPlayer(nickname).moveStudentInDiningRoom(colour);
        } catch (MissingPlayerException e) {
        }
        sendBoard("MoveStudentInDiningRoom");
    }

    /*
    * search the id of the player in the player list
    * once is done it calls the method that add the students from the group to the entrance
    * passing as parameter the studentGroup in the cloud that we want to take
    */
    public void doTakeCloud(String nickname, int numCloud) throws MissingCloudException {

        if (numCloud < listCloud.size() && numCloud >= 0) {
            for (Player player : listPlayer) {
                if (player != null) {
                    if (player.getNicknameClient().equals(nickname)) {
                        player.addStudentsToEntrance(listCloud.get(numCloud).getStudents());
                    }
                }
            }
        } else throw new MissingCloudException("Error");
        sendBoard("TakeCloud");
    }


    public void doMoveStudentInIsland(String nickname, Colour colour, int numIsland) {
        for (Player player : listPlayer) {
            if (player.getNicknameClient().equals(nickname)) {
                player.moveStudentInIsland(colour, this.getIsland(numIsland));
            }
        }
        sendBoard("MoveStudentInIsland");
    }

    public Island getIsland(int numIsland) throws IllegalArgumentException {
        Island island = null;
        if (numIsland < 0 || numIsland > (listIsland.size() - 1)) {
            throw new IllegalArgumentException();
        } else {
            for (Island value : listIsland) {
                if (value.getNumIsland() == numIsland) {
                    island = value;
                }
            }
        }
        return island;
    }


    public void doPlayCard(String nickname, int numCard) {
        for (Player player : listPlayer) {
            if (player != null) {
                if (player.getNicknameClient().equals(nickname)) {
                    player.playCard(numCard);
                }
            }
        }
        sendBoard("PlayCard");
    }

    public void sendBoard(String s){
        ServerMessage sm;
        ServerHeader sh = new ServerHeader(ServerAction.UPDATE_BOARD, s);
        Payload pay = new Payload();

        if(s.equals("PlayCard")){

            for(int i = 1; i <= listPlayer.size(); i++) {
                pay.addParameter("player" + i, listPlayer.get(i-1));
            }
            sm = new ServerMessage(sh, pay);
            notify(sm);
        } else if(s.equals("MoveStudentInDiningRoom")){

            for(int i = 1; i <= professors.size(); i++) {
                pay.addParameter("professor" + i, professors.get(i-1));
            }
            for(int i = 1; i <= listPlayer.size(); i++) {
                pay.addParameter("player" + i, listPlayer.get(i-1));
            }

            sm = new ServerMessage(sh, pay);
            notify(sm);

        } else if(s.equals("MoveStudentInIsland")){

            for(int i = 1; i <= listPlayer.size(); i++) {
                pay.addParameter("player" + i, listPlayer.get(i-1));
            }
            for(int i = 1; i <= listIsland.size(); i++) {
                pay.addParameter("island" + i, listIsland.get(i-1));
            }

            sm = new ServerMessage(sh, pay);
            notify(sm);


        } else if(s.equals("TakeCloud")){

            for(int i = 1; i <= listPlayer.size(); i++) {
                pay.addParameter("player" + i, listPlayer.get(i-1));
            }
            for(int i = 1; i <= listCloud.size(); i++) {
                pay.addParameter("cloud" + i, listCloud.get(i-1));
            }

            sm = new ServerMessage(sh, pay);
            notify(sm);


        } else if(s.equals("MoveMotherNature")){

            for(int i = 1; i <= listIsland.size(); i++) {
                pay.addParameter("island" + i, listIsland.get(i-1));
            }

            for(int i = 1; i <= listTeam.size(); i++) {
                pay.addParameter("team" + i, listTeam.get(i-1));
            }

            pay.addParameter("mothernature", motherNature);

            sm = new ServerMessage(sh, pay);
            notify(sm);

        } else if(s.equals("Fusion")){

            for(int i = 1; i <= listIsland.size(); i++) {
                pay.addParameter("island" + i, listIsland.get(i-1));
            }

            sm = new ServerMessage(sh, pay);
            notify(sm);

        } else if(s.equals("PlayCharacterCard")){

            for(int i = 1; i <= characterCards.size(); i++) {
                pay.addParameter("charactercard" + i, characterCards.get(i-1));
            }

            pay.addParameter("cardthrown", characterCardThrown);

            for(int i = 1; i <= listPlayer.size(); i++) {
                pay.addParameter("player" + i, listPlayer.get(i-1));
            }
        } else if(s.equals("CheckProfessor")){

            for(int i = 1; i <= professors.size(); i++) {
                pay.addParameter("professor" + i, professors.get(i-1));
            }

            for(int i = 1; i <= listPlayer.size(); i++) {
                pay.addParameter("player" + i, listPlayer.get(i-1));
            }
        } else if(s.equals("CheckTowers")){

            for(int i = 1; i <= listIsland.size(); i++) {
                pay.addParameter("island" + i, listIsland.get(i-1));
            }

            for(int i = 1; i <= listTeam.size(); i++) {
                pay.addParameter("team" + i, listTeam.get(i-1));
            }


        }else if(s.equals("STRATGAME")){

            for(int i = 1; i <= listIsland.size(); i++) {
                pay.addParameter("island" + i, listIsland.get(i-1));
            }

            for(int i = 1; i <= listTeam.size(); i++) {
                pay.addParameter("team" + i, listTeam.get(i-1));
            }

            for(int i = 1; i <= professors.size(); i++) {
                pay.addParameter("professor" + i, professors.get(i-1));
            }

            for(int i = 1; i <= listPlayer.size(); i++) {
                pay.addParameter("player" + i, listPlayer.get(i-1));
            }

            pay.addParameter("mothernature", motherNature);

            for(int i = 1; i <= characterCards.size(); i++) {
                pay.addParameter("charactercard" + i, characterCards.get(i-1));
            }

            pay.addParameter("cardthrown", characterCardThrown);

            for(int i = 1; i <= listCloud.size(); i++) {
                pay.addParameter("cloud" + i, listCloud.get(i-1));
            }
        }

    }


    public void checkProfessor(Colour colour) {

        Player MaxPlayer = listPlayer.get(0);

        //il primo ciclo mi dice chi è il player con più studenti del colore colour
        for (int i = 0; i < listPlayer.size(); i++) {
            if (listPlayer.get(i).numStudentsDiningRoom(colour) > 0 &&
                    listPlayer.get(i).numStudentsDiningRoom(colour) >= MaxPlayer.numStudentsDiningRoom(colour)) {
                MaxPlayer = listPlayer.get(i);
            }
        }

        //cerco il professore dello stesso colore
        for (int j = 0; j < professors.size(); j++) {

            if (professors.get(j).getOwner() == null && professors.get(j).getColour() == colour) {

                //se il professore non ha owner e il player con più studenti ha almeno uno studente
                //assegno il professore
                if (professors.get(j).getOwner() == null && MaxPlayer.numStudentsDiningRoom(colour) > 0) {
                    MaxPlayer.addProfessor(professors.get(j));
                    professors.get(j).changeOwner(MaxPlayer);

                }
            }

            //se il professore ha un owner e il player con più studenti ha più studenti dell'owner
            //assegno il professore
            else {
                if (professors.get(j).getColour() == colour && MaxPlayer.numStudentsDiningRoom(colour) > professors.get(j).getOwner().numStudentsDiningRoom(colour)) {
                    MaxPlayer.addProfessor(professors.get(j));
                    professors.get(j).getOwner().removeProfessor(professors.get(j));
                    professors.get(j).changeOwner(MaxPlayer);
                }
            }
        }
        sendBoard("CheckProfessor");
    }



    /*
    * il metodo ha la funzione di calcolare l'influenza degli studenti
    * sull'isola ed in base a chi ha più influenza sostituire le torri o meno
    * se c'è un caso di vittoria vien segnalato dalla sua eccezione
    * se l'isola è assente viene segnalato dalla sua eccezione
    */
    public void checkTowers(int numIsland) throws MissingIslandException, MissingTowerException {

        Island island = null;

        //cerco l'isola su cui effettuare il controllo
        for (Island island1 : listIsland) {
            if (island1.getNumIsland() == numIsland) {
                island = island1;
            }
        }

        //se non esiste lancio un eccezione
        if (island == null) {
            characterCardThrown = "";
            throw new MissingIslandException();
        }

        //altrimenti effettuo il conto degli studenti per le torri
        else {
            //inizializzo i punti per ogni team a 0
            int team1 = 0;
            int team2 = 0;

            //associo ai team i corrispettivi punti
            //(a seconda dei professori che controllano e degli studenti presenti sul terreno)
            for (Professor prof : professors) {
                if (prof.getOwner() != null &&
                        prof.getOwner().getTeam() == listTeam.get(0)) {

                    team1 = team1 + island.countStudentsOfColour(prof.getColour());
                } else if (prof.getOwner() != null &&
                        prof.getOwner().getTeam() == listTeam.get(1)) {

                    team2 = team2 + island.countStudentsOfColour(prof.getColour());
                }
            }

            //se sono presenti torri aggiungo i punti alla squadra pari al numero di torri

            try {
                if (characterCardThrown != "Satyr") {
                    ColourTower colourTower = island.getColourTower();
                    if (colourTower.equals(listTeam.get(0).getColourTower())) {
                        team1 = team1 + island.getNumSubIsland();
                    } else {
                        team2 = team2 + island.getNumSubIsland();
                    }
                }
                if (characterCardThrown == "Knight") {
                    if (getPlayer(currentPlayer).getTeam() == listTeam.get(0))
                        team1 = team1 + 2;
                    else {
                        team2 = team2 + 2;
                    }
                }
                characterCardThrown = "";

            } catch (MissingTowerException e) {
            } catch (MissingPlayerException e) {
            } finally {
                //a questo punto confronto il valore dei team
                //e se c'è una maggioranza riassegno le torri e controllo eventuali fusioni
                //altrimenti chiudo il metodo

                //caso di parità
                if (team1 == team2) {
                    return;
                }

                //caso di maggioranza team1
                //se il team1 ha finito le torri significa che ha vinto e lancio un'eccezione per
                //richiamare il metodo checkwin
                else if (team1 > team2) {
                    listTeam.get(0).useTowers(island.getNumSubIsland());
                    listTeam.get(1).takeTowers(island.getNumSubIsland());
                    island.setColourTower(listTeam.get(0).getColourTower());
                    fusion(numIsland);
                }
                // IL METODO PER UNIRE LE ISOLE CHE DEVE ESSERE RICHIAMATO NEI DUE RAMI --> fatto
                //caso di maggioranza team2
                //se il team2 ha finito le torri significa che ha vinto e lancio un'eccezione per
                //richiamare il metodo checkwin
                else {

                    listTeam.get(1).useTowers(island.getNumSubIsland());
                    listTeam.get(0).takeTowers(island.getNumSubIsland());
                    island.setColourTower(listTeam.get(1).getColourTower());
                    fusion(numIsland);
                }
            }

        }
        sendBoard("CheckTowers");
    }


    public void fusion(int numIsland) {
        if (numIsland == 0) {
            try {
                //controllo isola sinistra
                if (listIsland.get(listIsland.size() - 1).getColourTower().equals(listIsland.get(numIsland).getColourTower())) {
                    //fondo isole
                    Island i = new Island(listIsland.get(numIsland), listIsland.get(listIsland.size() - 1));

                    //rimuovo, aggiungo e riordino lista
                    motherNature.move(i);

                    listIsland.set(numIsland, i);
                    listIsland.remove(listIsland.size() - 1);

                    //non serve perché dato che numIsland è 0 allora 1 c'è ancora ma non devo farlo arretrare a 0 ecc.
                    /*
                    for(Island island: listIsland){
                        if (island.getNumIsland() > i.getNumIsland()) {
                            island.setNumIsland(island.getNumIsland() - 1);
                        }
                    }*/

                }
            } catch (MissingTowerException e) {
                e.printStackTrace();
            }
            try {
                //controllo isola destra
                if (listIsland.get(numIsland + 1).getColourTower().equals(listIsland.get(numIsland).getColourTower())) {
                    //fondo isole
                    Island i = new Island(listIsland.get(numIsland), listIsland.get(numIsland + 1));

                    //rimuovo, aggiungo e riordino lista
                    motherNature.move(i);

                    listIsland.set(numIsland, i);
                    listIsland.remove(numIsland + 1);


                    for (Island island : listIsland) {
                        if (island.getNumIsland() > i.getNumIsland()) {
                            island.setNumIsland(island.getNumIsland() - 1);
                        }
                    }
                }
            } catch (MissingTowerException e) {
            }
        } else {
            try {
                //controllo isola sinistra
                if (listIsland.get(numIsland - 1).getColourTower().equals(listIsland.get(numIsland).getColourTower())) {
                    System.out.println("bla bla1");

                    //fondo isole
                    Island i = new Island(listIsland.get(numIsland), listIsland.get(numIsland - 1));

                    //rimuovo, aggiungo e riordino lista
                    motherNature.move(i);

                    listIsland.set(numIsland - 1, i);
                    listIsland.remove(numIsland);


                    for (Island island : listIsland) {
                        if (island.getNumIsland() > i.getNumIsland()) {
                            island.setNumIsland(island.getNumIsland() - 1);
                        }
                    }

                    for (Island iii : listIsland) {
                        System.out.println("isole" + iii.getNumIsland());
                    }
                    //System.out.println("isole" + );

                    //devo decrementare il numero dell'isola che considero nel caso in cui la fondo
                    //perchè se fondo 2 con 1 l'isola fusa avrà numero 1 quindi faccio il controllo
                    //il secondo controllo con 1 non con 2
                    numIsland = numIsland - 1;
                }

            } catch (MissingTowerException e) {
            }
            try {
                //System.out.println("bla bla2" + listIsland.get(numIsland).getColourTower());
                //System.out.println("bla bla3" + (numIsland + 1)%listIsland.size());

                //controllo isola destra
                if (listIsland.get((numIsland + 1) % listIsland.size()).getColourTower().equals(listIsland.get(numIsland).getColourTower())) {
                    //System.out.println("bla bla4");
                    //System.out.println("bla bla" + listIsland.get(numIsland).getNumSubIsland());

                    //fondo isole
                    Island i = new Island(listIsland.get(numIsland), listIsland.get((numIsland + 1) % listIsland.size()));

                    //rimuovo, aggiungo e riordino lista

                    motherNature.move(i);

                    if ((numIsland + 1) % listIsland.size() < numIsland) {
                        listIsland.set((numIsland + 1) % listIsland.size(), i);
                        listIsland.remove(numIsland);
                    } else {
                        listIsland.set(numIsland, i);
                        listIsland.remove((numIsland + 1) % listIsland.size());
                    }

                    if (numIsland != listIsland.size() + 1) {
                        for (Island island : listIsland) {
                            if (island.getNumIsland() > i.getNumIsland()) {
                                island.setNumIsland(island.getNumIsland() - 1);
                            }
                        }
                    }
                }
            } catch (MissingTowerException e) {
            }
        }

        sendBoard("Fusion");
    }

    public Player getPlayer(String io) throws MissingPlayerException {
        Player playerreturn = null;

        for (Player p : listPlayer) {
            if (p.getNicknameClient().equals(io)) {
                playerreturn = p;
            }
        }
        if (playerreturn == null) {
            throw new MissingPlayerException();
        } else {
            return playerreturn;
        }
    }

    public Cloud getCloud(int num) {
        return listCloud.get(num);
    }

    public Professor getProfessor(Colour col) throws MissingProfessorException {
        Professor professorreturn = null;

        for (Professor p : professors) {
            if (p.getColour().equals(col)) {
                professorreturn = p;
            }
        }
        if (professorreturn == null) {
            throw new MissingProfessorException();
        } else {
            return professorreturn;
        }
    }

    public Bag getBag() {
        return this.bag;
    }

    public String getCharacterCardThrown(){
        return this.characterCardThrown;
    }

    public ConcreteCharacterCard getCharacterCard(int index) throws Exception {
        if (index < 0 || index > characterCards.size()) throw new Exception();
        else {
            return characterCards.get(index);
        }
    }


    public void setCardThrown(String characterCard) {
        this.characterCardThrown = characterCard;
    }


    public void doPlayCharacterCard(CharacterParameters charPar) throws MissingCardException, Exception {

        //verifio che esiste tra le carte in gioco
        for (ConcreteCharacterCard cha : characterCards) {
            if (cha.getNameCard().equals(charPar.getCharacterName())) {

                if (cha.getNameCard().equals("Pirate")) {
                    getPlayer(charPar.getPlayerName()).useCoins(cha.getPrice());
                    Pirate p = (Pirate) cha;
                    p.effect(charPar.getPlayerName(),charPar.getNum());

                } else if (cha.getNameCard().equals("Satyr")) {
                    getPlayer(charPar.getPlayerName()).useCoins(cha.getPrice());
                    Satyr s = (Satyr) cha;
                    s.effect(charPar.getPlayerName());

                } else if (cha.getNameCard().equals("Woman")) {
                    getPlayer(charPar.getPlayerName()).useCoins(cha.getPrice());
                    Woman w = (Woman) cha;
                    w.effect(charPar.getPlayerName(), charPar.getColour1());

                } else if (cha.getNameCard().equals("Priest")) {
                    getPlayer(charPar.getPlayerName()).useCoins(cha.getPrice());
                    Priest p = (Priest) cha;
                    p.effect(charPar.getPlayerName(), charPar.getNum(), charPar.getColour1());


                } else if (cha.getNameCard().equals("PostMan")) {
                    getPlayer(charPar.getPlayerName()).useCoins(cha.getPrice());
                    PostMan p = (PostMan) cha;
                    p.effect(charPar.getPlayerName());

                } else if (cha.getNameCard().equals("Minstrell")) {
                    getPlayer(charPar.getPlayerName()).useCoins(cha.getPrice());
                    Minstrell m = (Minstrell) cha;
                    try {
                        m.effect(charPar.getPlayerName(), charPar.getColour1(), charPar.getColour2(), charPar.getColour3(), charPar.getColour4());
                    } catch (Exception e1) {
                        m.effect(charPar.getPlayerName(), charPar.getColour1(), charPar.getColour2());
                    }

                } else if (cha.getNameCard().equals("Knight")) {
                    getPlayer(charPar.getPlayerName()).useCoins(cha.getPrice());
                    Knight k = (Knight) cha;
                    k.effect(charPar.getPlayerName());

                } else if (cha.getNameCard().equals("Jester")) {
                    getPlayer(charPar.getPlayerName()).useCoins(cha.getPrice());
                    Jester j = (Jester) cha;

                    try {
                        j.effect(charPar.getPlayerName(), charPar.getColour1(), charPar.getColour2(), charPar.getColour3(), charPar.getColour4(), charPar.getColour5(), charPar.getColour6());
                    } catch (Exception e1) {
                        try {
                            j.effect(charPar.getPlayerName(), charPar.getColour1(), charPar.getColour2(), charPar.getColour3(), charPar.getColour4());

                        } catch (Exception e2) {
                            j.effect(charPar.getPlayerName(), charPar.getColour1(), charPar.getColour2());
                        }

                    }
                }
            }

        }
        sendBoard("PlayCharacterCard");
    }

    //per testing
    public void addCharacterCard(ConcreteCharacterCard cc){
        characterCards.add(cc);
    }
}