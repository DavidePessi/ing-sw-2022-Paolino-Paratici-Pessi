package it.polimi.ingsw.MODEL;

// TODO: 21/03/2022 come facciamo il costruttore se non ci sono più i numColour?
public class Island {

    private ColourTower colourTower;
    private boolean hasMotherNature;
    private int numSubisland;
    int numIsland;
    private StudentGroup students;

    /*Why don't we pass 2 attributes of type Island to the constructor? */
    public Island(int nB, int nG, int nR, int nP, int nY, int nSI) {}
    public Island(Island i1, Island i2) {}

    public void setMotherNature(boolean hasMotherNature){
        if (hasMotherNature){
            this.hasMotherNature = true;
        }
        else this.hasMotherNature = false;
    }

    public void addStudent(Colour colour){
        students.addStudent(colour);
    }
}
