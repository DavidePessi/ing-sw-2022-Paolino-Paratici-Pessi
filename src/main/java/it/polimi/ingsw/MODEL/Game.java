package it.polimi.ingsw.MODEL;

import it.polimi.ingsw.MODEL.CharacterCards.*;
import it.polimi.ingsw.MODEL.Exception.*;
import it.polimi.ingsw.NETWORK.MESSAGES.Payload;
import it.polimi.ingsw.NETWORK.MESSAGES.ServerAction;
import it.polimi.ingsw.NETWORK.MESSAGES.ServerHeader;
import it.polimi.ingsw.NETWORK.MESSAGES.ServerMessage;
import it.polimi.ingsw.NETWORK.UTILS.Observable;


import java.util.*;


public class Game extends Observable {
    private String characterCardThrown;
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

    /**
     * with this method start the preparation phase of the game
     * @param easy
     * @throws MissingStudentException
     */
    public void startGame(boolean easy) throws MissingStudentException {

        if(easy == false){

            //da un coin ad ogni player
            for(Player player: listPlayer){
                player.receiveCoin();
            }

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

        }


        for (Cloud cloud : listCloud) { //per ogni nuvola aggiungo 3 studenti estratti casualmente dalla bag
            if (cloud != null) {
                StudentGroup studentGroup = new StudentGroup();
                if(listPlayer.size() == 3){
                    for (int i = 0; i < 4; i++) {
                        studentGroup.addStudent(bag.pullOut());
                    }
                    cloud.addStudents(studentGroup);
                }else {
                    for (int i = 0; i < 3; i++) {
                        studentGroup.addStudent(bag.pullOut());
                    }
                    cloud.addStudents(studentGroup);
                }
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

        for (Player player : listPlayer) { //per ogni player aggiungo nella entrance i 7 o i 9 studenti estratti casualmente dalla bag
            if (player != null) {
                if(listPlayer.size()==3) {
                    StudentGroup studentGroup1 = new StudentGroup();
                    for (int i = 0; i < 9; i++) {
                        studentGroup1.addStudent(bag.pullOut());
                    }
                    player.addStudentsToEntrance(studentGroup1);
                }
                else{
                    StudentGroup studentGroup = new StudentGroup();
                    for (int i = 0; i < 7; i++) {
                        studentGroup.addStudent(bag.pullOut());
                    }
                    player.addStudentsToEntrance(studentGroup);
                }
            }
        }
        sendBoard("STARTGAME");
    }

    /**
     * if the game is finished and there's a winner return true, else return false
     * @return
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

    /**
     * returns who won the game
     * @return
     */
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

    /**
     * move mother nature:
     * 1. The number of the island on which mother nature is found with the number of movements it must make is added
     * 2. Do the module with islands that are there, this is the number of the island on which mother nature must move
     * 3. This number is given to the getIsland function, which returns the island object with that number
     * @param numMovement
     * @throws Exception
     */
    public void doMoveMotherNature(int numMovement) throws Exception{

        if (characterCardThrown.equals("PostMan")) {
            try {
                //risetto la carta lanciata a nessuna
                characterCardThrown = "";

                //faccio il controllo del movimento di madre natura con +2
                if (numMovement <= this.getPlayer(currentPlayer).getLastPlayedCard().getMovement() + 2) {
                    motherNature.move(getIsland((numMovement + motherNature.getNumIsland()) % listIsland.size()));

                }
                else {
                    throw new Exception("madre natura non si può spostare di cosi tante isole");
                }
            } catch (MissingPlayerException | MissingCardException e) {
                e.printStackTrace();
            }
        } else {
            try {
                if (numMovement <= this.getPlayer(currentPlayer).getLastPlayedCard().getMovement()) {
                    motherNature.move(getIsland((numMovement + motherNature.getNumIsland()) % listIsland.size()));
                }
                else {
                    throw new Exception("madre natura non si può spostare di cosi tante isole");
                }
            } catch (MissingPlayerException | MissingCardException e) {
                e.printStackTrace();
            }
        }
        checkTowers(motherNature.getNumIsland());
        sendBoard("MoveMotherNature");
    }

    /**
     * move a student form entrance to dining room
     * @param nickname
     * @param colour
     * @throws Exception
     */
    public void doMoveStudentInDiningRoom(String nickname, Colour colour) throws Exception {
        try {
            this.getPlayer(nickname).moveStudentInDiningRoom(colour);
        } catch (MissingPlayerException e) {
        }
        sendBoard("MoveStudentInDiningRoom");
    }

    /**
     * move student from entrance to island
     * @param nickname
     * @param colour
     * @param numIsland
     * @throws MissingStudentException
     * @throws IllegalArgumentException
     */
    public void doMoveStudentInIsland(String nickname, Colour colour, int numIsland)throws MissingStudentException, IllegalArgumentException {
        for (Player player : listPlayer) {
            if (player.getNicknameClient().equals(nickname)) {
                player.moveStudentInIsland(colour, this.getIsland(numIsland));
            }
        }
        sendBoard("MoveStudentInIsland");
    }

    /**
     * search the id of the player in the player list
     * once is done it calls the method that add the students from the group to the entrance
     * passing as parameter the studentGroup in the cloud that we want to take
     * @param nickname
     * @param numCloud
     * @throws MissingCloudException
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

    /**
     * returns the island with number in parameter
     * @param numIsland
     * @return
     * @throws IllegalArgumentException
     */
    public Island getIsland(int numIsland) throws IllegalArgumentException {
        Island island = null;
        if (numIsland < 0 || numIsland > (listIsland.size() - 1)) {
            throw new IllegalArgumentException("isola selezionata non esistente");
        } else {
            for (Island value : listIsland) {
                if (value.getNumIsland() == numIsland) {
                    island = value;
                }
            }
        }
        return island;
    }

    /**
     * makes the player play the card
     * @param nickname
     * @param numCard
     * @throws MissingCardException
     */
    public void doPlayCard(String nickname, int numCard) throws MissingCardException{
        try {
            for (Player player : listPlayer) {
                if (player != null) {
                    if (player.getNicknameClient().equals(nickname)) {
                        player.playCard(numCard);
                    }
                }
            }
            sendBoard("PlayCard");

        }catch(PossibleWinException e){
            notifyWin();
        }
    }

    /**
     * The method has the function of verifying
     * after the movement of a student that the professors assigned to the correct player,
     * if this is not the case with the correct player the professors
     * @param colour
     */
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

    /**
     * The method has the function of calculating the influence of students on the island
     * and on the basis of those who have more influence replace the towers or not if there is a case of victory
     * is reported by its exception if the island is absent is reported by his exception
     * @param numIsland
     * @throws MissingIslandException
     * @throws MissingTowerException
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
            throw new MissingIslandException("Isola selezionata non esistente");
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
                    } else if(colourTower.equals(listTeam.get(1).getColourTower())){
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
                    if(island.getColourTower().equals(listTeam.get(1).getColourTower())){

                        listTeam.get(0).useTowers(island.getNumSubIsland());
                        listTeam.get(1).takeTowers(island.getNumSubIsland());
                    }else if(island.getColourTower().equals(ColourTower.NO_ONE)){
                        listTeam.get(0).useTowers(island.getNumSubIsland());
                    }

                    island.setColourTower(listTeam.get(0).getColourTower());
                    fusion(numIsland);
                }

                //caso di maggioranza team2
                //se il team2 ha finito le torri significa che ha vinto e lancio un'eccezione per
                //richiamare il metodo checkwin
                else {
                    if(island.getColourTower().equals(listTeam.get(0).getColourTower())){
                        listTeam.get(1).useTowers(island.getNumSubIsland());
                        listTeam.get(0).takeTowers(island.getNumSubIsland());
                    }else if(island.getColourTower().equals(ColourTower.NO_ONE)){
                        listTeam.get(1).useTowers(island.getNumSubIsland());
                    }

                    island.setColourTower(listTeam.get(1).getColourTower());
                    fusion(numIsland);
                }
            }

        }
        //sendBoard("CheckTowers");
    }

    /**
     * make a fusion between two or tree island
     * @param numIsland
     */
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
                        //System.out.println("isole" + iii.getNumIsland());
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

                //controllo isola destra
                if (listIsland.get((numIsland + 1) % listIsland.size()).getColourTower().equals(listIsland.get(numIsland).getColourTower())) {

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

        //verifico se ci sono almeno 3 isole altrimenti è finita la partita
        if(listIsland.size() <= 3){
            notifyWin();
        }
        //sendBoard("Fusion");
    }

    /**
     * returns the player the that nickanme
     * @param s
     * @return
     * @throws MissingPlayerException
     */
    public Player getPlayer(String s) throws MissingPlayerException {
        Player playerreturn = null;

        for (Player p : listPlayer) {
            if (p.getNicknameClient().equals(s)) {
                playerreturn = p;
            }
        }
        if (playerreturn == null) {
            throw new MissingPlayerException();
        } else {
            return playerreturn;
        }
    }

    /**
     * returns the cloud with that number
     * @param num
     * @return
     */
    public Cloud getCloud(int num) {
        return listCloud.get(num);
    }

    /**
     * returns the professor with that colour
     * @param col
     * @return
     * @throws MissingProfessorException
     */
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

    /**
     * returns the bag of the game
     * @return
     */
    public Bag getBag() {
        return this.bag;
    }

    /**
     * return the character cart that is thrown
     * @return
     */
    public String getCharacterCardThrown(){
        return this.characterCardThrown;
    }

    /**
     * reurn the character card with that index
     * @param index
     * @return
     * @throws Exception
     */
    public ConcreteCharacterCard getCharacterCard(int index) throws Exception {
        if (index < 0 || index > characterCards.size()) throw new Exception();
        else {
            return characterCards.get(index);
        }
    }

    /**
     * set the character chard that is thrown
     * @param characterCard
     */
    public void setCardThrown(String characterCard) {
        this.characterCardThrown = characterCard;
    }

    /**
     * play the character card with that parameters
     * @param charPar
     * @throws Exception
     */
    public void doPlayCharacterCard(CharacterParameters charPar) throws Exception {
        //per capire sia stata giocata o meno metto un booleano
        boolean cardplayed = false;

        //verifio che esiste tra le carte in gioco
        //e se esiste richiamo l'effetto
        for (ConcreteCharacterCard cha : characterCards) {
            if (cha.getNameCard().equals(charPar.getCharacterName())) {
                if (cha.getNameCard().equals("Pirate")) {
                    getPlayer(charPar.getPlayerName()).useCoins(cha.getPrice());
                    Pirate p = (Pirate) cha;
                    p.effect(charPar.getPlayerName(),charPar.getNum());
                    cardplayed = true;

                } else if (cha.getNameCard().equals("Satyr")) {
                    getPlayer(charPar.getPlayerName()).useCoins(cha.getPrice());
                    Satyr s = (Satyr) cha;
                    s.effect(charPar.getPlayerName());
                    cardplayed = true;

                } else if (cha.getNameCard().equals("Woman")) {
                    getPlayer(charPar.getPlayerName()).useCoins(cha.getPrice());
                    Woman w = (Woman) cha;
                    w.effect(charPar.getPlayerName(), charPar.getColour1());
                    cardplayed = true;

                } else if (cha.getNameCard().equals("Priest")) {
                    getPlayer(charPar.getPlayerName()).useCoins(cha.getPrice());
                    Priest p = (Priest) cha;
                    p.effect(charPar.getPlayerName(), charPar.getNum(), charPar.getColour1());
                    cardplayed = true;

                } else if (cha.getNameCard().equals("PostMan")) {
                    getPlayer(charPar.getPlayerName()).useCoins(cha.getPrice());
                    PostMan p = (PostMan) cha;
                    p.effect(charPar.getPlayerName());
                    cardplayed = true;

                } else if (cha.getNameCard().equals("Minstrell")) {
                    getPlayer(charPar.getPlayerName()).useCoins(cha.getPrice());
                    Minstrell m = (Minstrell) cha;
                    try {
                        m.effect(charPar.getPlayerName(), charPar.getColour1(), charPar.getColour2(), charPar.getColour3(), charPar.getColour4());
                        cardplayed = true;
                    } catch (Exception e1) {
                        m.effect(charPar.getPlayerName(), charPar.getColour1(), charPar.getColour2());
                        cardplayed = true;
                    }

                } else if (cha.getNameCard().equals("Knight")) {
                    getPlayer(charPar.getPlayerName()).useCoins(cha.getPrice());
                    Knight k = (Knight) cha;
                    k.effect(charPar.getPlayerName());
                    cardplayed = true;

                } else if (cha.getNameCard().equals("Jester")) {
                    getPlayer(charPar.getPlayerName()).useCoins(cha.getPrice());
                    Jester j = (Jester) cha;

                    try {
                        j.effect(charPar.getPlayerName(), charPar.getColour1(), charPar.getColour2(), charPar.getColour3(), charPar.getColour4(), charPar.getColour5(), charPar.getColour6());
                        cardplayed = true;
                    } catch (Exception e1) {
                        try {
                            j.effect(charPar.getPlayerName(), charPar.getColour1(), charPar.getColour2(), charPar.getColour3(), charPar.getColour4());
                            cardplayed = true;

                        } catch (Exception e2) {
                            j.effect(charPar.getPlayerName(), charPar.getColour1(), charPar.getColour2());
                            cardplayed = true;
                        }

                    }
                }
            }

        }
        if(cardplayed) {
            sendBoard("PlayCharacterCard");
        } else{
            throw new Exception("Character Card selected doesn't exist");
        }
    }

    /**
     * add character card
     * @param cc
     */
    public void addCharacterCard(ConcreteCharacterCard cc){
        characterCards.add(cc);
    }

    /**
     * fills the clouds with the students after the end of a turn
     */
    public void refillCloud(){
        for (Cloud cloud : listCloud) { //per ogni nuvola aggiungo 3 studenti estratti casualmente dalla bag
            if (cloud != null) {
                StudentGroup studentGroup = new StudentGroup();
                if(listPlayer.size() == 3){
                    for (int i = 0; i < 4; i++) {
                        try {
                            studentGroup.addStudent(bag.pullOut());
                        } catch (MissingStudentException e) {
                            //FINE GAME
                            notifyWin();
                        }
                    }
                    cloud.addStudents(studentGroup);
                }
                else{
                    for (int i = 0; i < 3; i++) {
                        try {
                            studentGroup.addStudent(bag.pullOut());
                        } catch (MissingStudentException e) {
                            //FINE GAME
                            notifyWin();
                        }
                    }
                    cloud.addStudents(studentGroup);
                }

            }
        }
        sendBoard("refillcloud");
    }

    /**
     * set current player with the nickname in the parameter
     * @param newCurrentPlayer
     */
    public void setCurrentPlayer(String newCurrentPlayer){
        currentPlayer = newCurrentPlayer;
    }

    //Method for network
    public void notifyError(String description, String nickname){
        ServerMessage sm;
        ServerHeader sh;
        Payload pay;

        //creo il messaggio dove specifico il player che ha sbagliato
        sh = new ServerHeader(ServerAction.CLIENT_ERROR, description);
        pay = new Payload();
        pay.addParameter("nickname", nickname);
        sm = new ServerMessage(sh, pay);

        //notifico tutti
        notify(sm);
    }

    public void sendBoard(String s){
        ServerMessage sm;
        ServerHeader sh = new ServerHeader(ServerAction.UPDATE_BOARD, s);
        Payload pay = new Payload();

        if(s.equals("PlayCard")){

            for(int i = 1; i <= listPlayer.size(); i++) {
                pay.addParameter("player" + i, listPlayer.get(i-1));
            }
        }

        else if(s.equals("MoveStudentInDiningRoom")){

            for(int i = 1; i <= professors.size(); i++) {
                pay.addParameter("professor" + i, professors.get(i-1));
            }
            for(int i = 1; i <= listPlayer.size(); i++) {
                pay.addParameter("player" + i, listPlayer.get(i-1));
            }
        }

        else if(s.equals("MoveStudentInIsland")){

            for(int i = 1; i <= listPlayer.size(); i++) {
                pay.addParameter("player" + i, listPlayer.get(i-1));
            }
            for(int i = 0; i < listIsland.size(); i++) {
                pay.addParameter("island" + i, listIsland.get(i));
            }
        }

        else if(s.equals("TakeCloud")){

            for(int i = 1; i <= listPlayer.size(); i++) {
                pay.addParameter("player" + i, listPlayer.get(i-1));
            }
            for(int i = 1; i <= listCloud.size(); i++) {
                pay.addParameter("cloud" + i, listCloud.get(i-1));
            }
        }

        else if(s.equals("MoveMotherNature")){

            for(int i = 0; i < listIsland.size(); i++) {
                pay.addParameter("island" + i, listIsland.get(i));
            }

            for(int i = 1; i <= listTeam.size(); i++) {
                pay.addParameter("team" + i, listTeam.get(i-1));
            }
            pay.addParameter("mothernature", motherNature);

            for(int i = 1; i <= listPlayer.size(); i++) {
                pay.addParameter("player" + i, listPlayer.get(i-1));
            }
        }

        else if(s.equals("Fusion")){

            for(int i = 0; i < listIsland.size(); i++) {
                pay.addParameter("island" + i, listIsland.get(i));
            }

            sm = new ServerMessage(sh, pay);
            notify(sm);

        }

        else if(s.equals("PlayCharacterCard")){

            for (int i = 0; i < characterCards.size(); i++) {
                pay.addParameter("charactercard" + (i+1), characterCards.get(i));
            }

            for(int i = 0; i < listIsland.size(); i++) {
                pay.addParameter("island"+i, listIsland.get(i));
            }

            for(int i = 1; i <= listPlayer.size(); i++) {
                pay.addParameter("player" + i, listPlayer.get(i-1));
            }

            pay.addParameter("cardthrown", characterCardThrown);
        }

        else if(s.equals("CheckProfessor")){

            for(int i = 1; i <= professors.size(); i++) {
                pay.addParameter("professor" + i, professors.get(i-1));
            }

            for(int i = 1; i <= listPlayer.size(); i++) {
                pay.addParameter("player" + i, listPlayer.get(i-1));
            }


        }

        else if(s.equals("CheckTowers")){

            for(int i = 0; i < listIsland.size(); i++) {
                pay.addParameter("island" + i, listIsland.get(i));
            }

            for(int i = 1; i <= listTeam.size(); i++) {
                pay.addParameter("team" + i, listTeam.get(i-1));
            }

        }

        else if(s.equals("STARTGAME")){
            //se sei nel difficult fai:
            if(!characterCards.isEmpty()) {
                for (int i = 0; i < characterCards.size(); i++) {
                    pay.addParameter("charactercard" + (i+1), characterCards.get(i));
                }

                pay.addParameter("showCoins", true);
            }
            else{
                pay.addParameter("showCoins", false);
            }

            for(int i = 0; i < listIsland.size(); i++) {
                pay.addParameter("island"+i, listIsland.get(i));
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

            pay.addParameter("cardthrown", characterCardThrown);

            pay.addParameter("currentClient", currentPlayer);

            for(int i = 1; i <= listCloud.size(); i++) {
                pay.addParameter("cloud" + i, listCloud.get(i-1));
            }

            //System.out.println("sto inviando il messaggio: " + sm);
        }

        else if(s.equals("refillcloud")){
            for(int i = 1; i <= listCloud.size(); i++) {
                pay.addParameter("cloud" + i, listCloud.get(i-1));
            }
        }

        else if(s.equals("EndTurn")){
            pay.addParameter("currentClient", currentPlayer);
        }

        sm = new ServerMessage(sh, pay);
        notify(sm);
    }

    public void notifyWin() {
        if(checkWin()) {
            ServerMessage sm;
            ServerHeader sh;
            Payload pay;

            //creo il messaggio dove specifico il player che ha sbagliato
            sh = new ServerHeader(ServerAction.END_GAME, "");

            pay = new Payload();
            pay.addParameter("team", theWinnerIs());
            sm = new ServerMessage(sh, pay);

            //notifico tutti
            notify(sm);
        } else{
            System.out.println("c'è stato una falsa chiamata di Possible win exception");
        }

    }
}