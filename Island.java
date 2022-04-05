package it.polimi.ingsw.MODEL;

import java.util.Objects;

// TODO: 21/03/2022 come facciamo il costruttore se non ci sono più i numColour?
// TODO: 04/04/2022 equals modificata senza: Objects.equals(students, island.students), se da problemi controlliamo questo
public class Island {



    private ColourTower colourTower;
    private boolean hasMotherNature;
    private int numSubisland;
    private int numIsland;
    private StudentGroup students;

    /*Why don't we pass 2 attributes of type Island to the constructor? */
    public Island(int numIsland) {
        this.numIsland = numIsland;
        numSubisland = 1;
        hasMotherNature = false;
        students = new StudentGroup();
    }
    public Island(Island i1, Island i2) {}

    public void setMotherNature(boolean hasMotherNature){
        if (hasMotherNature){
            this.hasMotherNature = true;
        }
        else this.hasMotherNature = false;
    }

    public boolean getHasMotherNature (){
        return this.hasMotherNature;
    }

    public int getNumIsland(){
        return numIsland;
    }

    public void addStudent(Colour colour){
        students.addStudent(colour);
    }


    public int countStudentsOfColour (Colour colour){
        return this.students.countStudentsOfColour(colour);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Island island = (Island) o;
        return hasMotherNature == island.hasMotherNature && numSubisland == island.numSubisland && numIsland == island.numIsland && colourTower == island.colourTower;
    }

    @Override
    public int hashCode() {
        return Objects.hash(colourTower, hasMotherNature, numSubisland, numIsland, students);
    }

    @Override
    public String toString() {
        return "Island{" +
                "colourTower=" + colourTower +
                ", hasMotherNature=" + hasMotherNature +
                ", numSubisland=" + numSubisland +
                ", numIsland=" + numIsland +
                ", students=" + students +
                '}';
    }
}
