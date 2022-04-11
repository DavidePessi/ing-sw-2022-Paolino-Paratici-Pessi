package it.polimi.ingsw.MODEL;

import java.util.*;
import java.util.function.ToDoubleBiFunction;

// TODO: 22/03/2022 aggiungere una variabile per fare il modulo
// TODO: 03/04/2022 GUARDARE LE DUE IMPLEMENTAZIONI DI "theWinnerIs" E VEDERE SE RIESCI A FINIRE LA SECONDA, ALTRIMENTI LA PRIMA VA BENE
// TODO: 05/04/2022 gestine delle influenze da fare
// TODO: 05/04/2022 decorator con ereditarietà
// TODO: 05/04/2022 dividere il controller

public class Game {
    private int numPlayer;
    private Player lastFirstPlayer;
    private List<Cloud> listCloud;
    private List<Player> listPlayer;
    private List<Team> listTeam;
    private List<Island> listIsland;
    private Bag bag;
    private MotherNature motherNature;
    private List<Professor> professors;
    //private int currentIsland;

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
    }

    public void startGame() throws MissingStudentException{

        for (Cloud cloud : listCloud) { //per ogni nuvola aggiungo 3 studenti estratti casualmente dalla bag
            if (cloud != null) {
                StudentGroup studentGroup = new StudentGroup();
                for(int i=0; i<3; i++) {
                    studentGroup.addStudent(bag.pullOut());
                }
                cloud.addStudents(studentGroup);
            }
        }

        /*devo tirar fuori dalla bag 2 studenti per colore e
        posizionarli a caso sulle isole tranne sull'isola di madre natura e la sua opposta */
        StudentGroup startingPullOut = bag.startingPullOut();
        for (Island island : listIsland){
            if (island != null && island.getNumIsland()!=motherNature.getNumIsland() && island.getNumIsland()!=((motherNature.getNumIsland()+6)%12)){
                //controlla che l'isola non sia quella di madre natura e nemmeno l'opposta
                island.addStudent(startingPullOut.pullOut());
            }
        }

        for (Player player : listPlayer) { //per ogni player aggiungo nella entrance i 7 studenti estratti casualmente dalla bag
            if (player != null) {
                StudentGroup studentGroup = new StudentGroup();
                for(int i=0; i<7; i++){
                    studentGroup.addStudent(bag.pullOut());
                }
                player.addStudentsToEntrances(studentGroup);
            }
        }
    }

    /*if the game is finished and there's a winner return True, else return False*/
    public boolean checkWin() {
        if(listIsland.size()<=3 || bag.size()==0){ //if there's 3 or less island or the bag is empty, the game ends
            return true;
        }
        else{
            for(Player p : listPlayer){
                if(p.getTeam().getNumberOfTower()==0 || p.getDeck().size()==0){ //if there's a  player with 0 towers or without card, the game ends
                    return true;
                }
            }
            return false;
        }
    }

    public Team theWinnerIs() {
            if(listPlayer.size()==2){
                Team team1 = listPlayer.get(0).getTeam();
                Team team2 = listPlayer.get(1).getTeam();

                if(team1.getNumberOfTower()<team2.getNumberOfTower()){ //returns number of tower in Entrance
                    return team1;
                }
                else if(team2.getNumberOfTower()<team1.getNumberOfTower()){
                    return team2;
                }
                else {
                    if (listPlayer.get(0).numProfessor() < listPlayer.get(1).numProfessor()) {
                        return team2;
                    }
                    else {
                        return team1;
                    }
                }
            }
            else if(listPlayer.size()==3)
            {
                Team team1 = listPlayer.get(0).getTeam();
                Team team2 = listPlayer.get(1).getTeam();
                Team team3 = listPlayer.get(2).getTeam();

                if(team1.getNumberOfTower()<team2.getNumberOfTower() && team1.getNumberOfTower()<team3.getNumberOfTower()){
                    return team1;
                }
                else if(team2.getNumberOfTower()<team1.getNumberOfTower() && team2.getNumberOfTower()<team3.getNumberOfTower()){
                    return team2;
                }
                else if(team3.getNumberOfTower()<team1.getNumberOfTower() && team3.getNumberOfTower()<team2.getNumberOfTower()){
                    return team3;
                }
                else {
                    if(listPlayer.get(0).numProfessor() > listPlayer.get(1).numProfessor() && listPlayer.get(0).numProfessor() > listPlayer.get(2).numProfessor()){
                        return team1;
                    }
                    else if(listPlayer.get(1).numProfessor() > listPlayer.get(0).numProfessor() && listPlayer.get(1).numProfessor() > listPlayer.get(2).numProfessor()){
                        return team2;
                    }
                    else {
                        return team3;
                    }
                }
            }
            else{ //4 players
                Team team1 = listPlayer.get(0).getTeam();
                Team team2 = listPlayer.get(1).getTeam();

                if(team1.getNumberOfTower()<team2.getNumberOfTower()){
                    return team1;
                }
                else if(team2.getNumberOfTower()<team1.getNumberOfTower()){
                    return team2;
                }
                else{
                    if(listPlayer.get(0).numProfessor()+listPlayer.get(2).numProfessor() > listPlayer.get(1).numProfessor()+listPlayer.get(3).numProfessor()){
                        return team1;
                    }
                    else {
                        return team2;
                    }
                }
            }
    }

    /*public Team theWinnerIs(){
            int MinTower = listTeam.get(0).getNumberOfTower();
            Team WinTeam = listTeam.get(0);

            for(Team t : listTeam){
                if(t.getNumberOfTower()<MinTower){
                    MinTower = t.getNumberOfTower();
                    WinTeam = t;
                }
            }

            for(Team t : listTeam){
                if(t.getNumberOfTower()==MinTower && !t.equals(WinTeam)){
                    for(Player p : listPlayer ){
                        int tot = 0;
                        if(p.getTeam().equals(MinTower)){
                            tot = tot + p.numProfessor();
                        }
                    }

                    boh

                    if(t.numProf() > WinTeam.numProf()){
                        WinTeam = t;
                        return WinTeam;
                    }
                }
            }

            return WinTeam;
    }*/

    public void doMoveMotherNature(int numMovement) { //metti eccezione
        /*
        1. viene sommato il numero dell'isola su cui si trova madre natura con il numero di spostamenti che deve fare
        2. si fa modulo 12, si trova il numero dell'isola su cui madre natura si deve spostare
        3. questo numero è dato alla funzione getIsland, che ritorna l'oggetto isola avente quel numero
         */
        motherNature.move(getIsland((numMovement+motherNature.getNumIsland())%12));
    }

    public void doMoveStudentInDiningRoom(String nickname, Colour colour) throws MissingStudentException {
        for (Player player : listPlayer) {
            if (player != null) {
                if (player.getNicknameClient().equals(nickname)) {
                    player.moveStudentInDiningRoom(colour);
                }

            } else {
                throw new MissingStudentException("Student not found");
            }
        }
    }

    //search the id of the player in the player list
    //once is done it calls the method that add the students from the group to the entrance
    //passing as parameter the studentGroup in the cloud that we want to take
    public void doTakeCloud(String nickname, int numCloud) {
        for (Player player : listPlayer) {
            if (player != null) {
                if (player.getNicknameClient().equals(nickname)) {
                    player.addStudentsToEntrances(listCloud.get(numCloud).getStudents());
                }
            }
        }
    }

    public void doMoveStudentInIsland(String nickname, Colour colour, int numIsland) {
        for (Player player : listPlayer) {
            if (player.getNicknameClient().equals(nickname)) {
                player.moveStudentInIsland(colour, this.getIsland(numIsland));
            }
        }
    }

    public Island getIsland(int numIsland) throws IllegalArgumentException {
        Island island = null;
        if (numIsland < 0 || numIsland > listIsland.size()) {
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

    public void doPlayCard(String nickname, int numCard){
        for(Player player : listPlayer){
            if(player != null){
                if(player.getNicknameClient().equals(nickname)){
                    player.playCard(numCard);
                }
            }
        }
    }

    public void checkProfessor(Colour colour) {

        Player MaxPlayer = listPlayer.get(0);

        //il primo ciclo mi dice chi è il player con più studenti del colore colour
        for (Player player : listPlayer) {
            if (player.NumStudentsDiningRoom(colour) > 0 &&
                    player.NumStudentsDiningRoom(colour) >= MaxPlayer.NumStudentsDiningRoom(colour)) {
                MaxPlayer = player;
            }
        }

        //cerco il professore dello stesso colore
        for (Professor professor : professors) {

            if (professor.getOwner() == null && professor.getColour() == colour) {

                //se il professore non ha owner e il player con più studenti ha almeno uno studente
                //assegno il professore
                if (professor.getOwner() == null && MaxPlayer.NumStudentsDiningRoom(colour) > 0) {
                    MaxPlayer.addProfessor(professor);
                    professor.changeOwner(MaxPlayer);

                }
            }

            //se il professore ha un owner e il player con più studenti ha più studenti dell'owner
            //assegno il professore
            else {
                if (professor.getColour() == colour && MaxPlayer.NumStudentsDiningRoom(colour) > professor.getOwner().NumStudentsDiningRoom(colour)) {
                    MaxPlayer.addProfessor(professor);
                    professor.getOwner().removeProfessor(professor);
                    professor.changeOwner(MaxPlayer);
                }
            }
        }
    }

    //il metodo ha la funzione di calcolare l'influenza degli studenti
    //sull'isola ed in base a chi ha più influenza sostituire le torri o meno
    //se c'è un caso di vittoria vien segnalato dalla sua eccezione
    //se l'isola è assente viene segnalato dalla sua eccezione
    void checkTowers(int numIsland)throws MissingIslandException, PossibleWinException{
        Island island = null;

        //cerco l'isola su cui effettuare il controllo
        for(Island island1 : listIsland){
            if(island1.getNumIsland() == numIsland) {
                island = island1;
            }
        }

        //se non esiste lancio un eccezione
        if(island == null){
            throw new MissingIslandException();
        }

        //altrimenti effettuo il conto degli studenti per le torri
        else{
            //inizializzo i punti per ogni team a 0
            int team1 = 0;
            int team2 = 0;

            //associo ai team i corrispettivi punti
            //(a seconda dei professori che controllano e degli studenti presenti sul terreno)
            for(Professor prof : professors){
                if(prof.getOwner() != null &&
                        prof.getOwner().getTeam() == listTeam.get(0)){

                    team1 = team1 +island.countStudentsOfColour(prof.getColour());
                }
                else if(prof.getOwner() != null &&
                        prof.getOwner().getTeam() == listTeam.get(1)){

                    team2 = team2 +island.countStudentsOfColour(prof.getColour());
                }
            }

            //se sono presenti torri aggiungo i punti alla squadra pari al numero di torri
            try{
                ColourTower colourTower = island.getColourTower();
                if(colourTower == listTeam.get(0).getColourTower()){
                    team1 = team1 + island.getNumSubIsland();
                }
                else{
                    team2 = team2 + island.getNumSubIsland();
                }
            }catch(MissingTowerException e){}
            finally {
                //a questo punto confronto il valore dei team
                //e se c'è una maggioranza riassegno le torri e controllo eventuali fusioni
                //altrimenti chiudo il metodo

                //caso di parità
                if(team1 == team2){
                    return;
                }

                //caso di maggioranza team1
                //se il team1 ha finito le torri significa che ha vinto e lancio un'eccezione per
                //richiamare il metodo checkwin
                else if(team1 > team2){
                    try {
                        listTeam.get(0).useTowers(island.getNumSubIsland());
                        listTeam.get(1).takeTowers(island.getNumSubIsland());
                        island.setColourTower(listTeam.get(0).getColourTower());
                    }
                    catch(MissingTowerException e){
                        throw new PossibleWinException();
                    }
                }
                //TODO:IL METODO PER UNIRE LE ISOLE CHE DEVE ESSERE RICHIAMATO NEI DUE RAMI
                //caso di maggioranza team2
                //se il team2 ha finito le torri significa che ha vinto e lancio un'eccezione per
                //richiamare il metodo checkwin
                else{
                    try {
                        listTeam.get(1).useTowers(island.getNumSubIsland());
                        listTeam.get(0).takeTowers(island.getNumSubIsland());
                        island.setColourTower(listTeam.get(1).getColourTower());
                    }
                    catch(MissingTowerException e){
                        throw new PossibleWinException();
                    }
                }
            }


        }
    }

    public Player getPlayer(String io){
        for (Player p : listPlayer){
            if(p.getNicknameClient().equals(io)){
                return p;
            }
        }
        return null;
    }

    public Cloud getCloud(int num){
        return listCloud.get(num);
    }

    public Professor getProfessor(Colour col) {
        for (Professor p : professors){
            if(p.getColour().equals(col)){
                return p;
            }
        }
        return null;
    }
}