package it.polimi.ingsw.MODEL;

public class Island {

    int numIsland;
    private int numSubIsland;
    private ColourTower colourTower;
    private StudentGroup students;
    private boolean hasMotherNature;


    /*Why don't we pass 2 attributes of type Island to the constructor? */
    public Island(int nB, int nG, int nR, int nP, int nY, int nSI) {

    }

    public Island(Island i1, Island i2) {

    }

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
