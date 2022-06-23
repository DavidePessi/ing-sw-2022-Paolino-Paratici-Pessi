package it.polimi.ingsw.MODEL;

import it.polimi.ingsw.MODEL.Exception.MissingTowerException;

import java.io.Serializable;
import java.util.Objects;

public class Island implements Serializable {

    private ColourTower colourTower;
    private boolean hasMotherNature;
    private int numIsland;
    private StudentGroup students;
    private int numSubIsland;

    public Island(int numIsland) {
        this.numIsland = numIsland;
        this.numSubIsland = 1;
        this.colourTower = ColourTower.NO_ONE;
        this.hasMotherNature = false;
        this.students = new StudentGroup();
    }
    public Island (Island i1, Island i2){
        if(i1.getNumIsland() < i2.getNumIsland()){
            this.numIsland = i1.getNumIsland();
            this.colourTower = i1.colourTower;
            this.numSubIsland = i1.numSubIsland + i2.numSubIsland;
            this.students = new StudentGroup();
            this.students.addStudents(i1.students);
            this.students.addStudents(i2.students);
            if(i1.hasMotherNature || i2.hasMotherNature){
                this.hasMotherNature = true;
            }
            else this.hasMotherNature = false;
        }
        else{
            this.numIsland = i2.getNumIsland();
            this.colourTower = i2.colourTower;
            this.numSubIsland = i1.numSubIsland + i2.numSubIsland;
            this.students = new StudentGroup();
            this.students.addStudents(i1.students);
            this.students.addStudents(i2.students);
            if(i1.hasMotherNature || i2.hasMotherNature){
                this.hasMotherNature = true;
            }
            else this.hasMotherNature = false;
        }
    }

    /**
     * set if mother nature is present or not into the island
     * @param hasMotherNature
     */
    public void setMotherNature(boolean hasMotherNature){
        if(hasMotherNature){
            this.hasMotherNature = true;
        }
        else this.hasMotherNature = false;
    }

    /**
     * returns if mother nature is on that island
     * @return
     */
    public boolean getHasMotherNature (){
        return this.hasMotherNature;
    }

    /**
     * return the number of the island
     * @return
     */
    public int getNumIsland(){
        return numIsland;
    }

    /**
     * add a student to island with the colour of the parameter
     * @param colour
     */
    public void addStudent(Colour colour){
        students.addStudent(colour);
    }

    /**
     * count students with the colour of the parameter
     * @param colour
     * @return
     */
    public int countStudentsOfColour (Colour colour){
        return this.students.countStudentsOfColour(colour);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Island island = (Island) o;
        return hasMotherNature == island.hasMotherNature && numSubIsland == island.numSubIsland && numIsland == island.numIsland && colourTower == island.colourTower;
    }

    @Override
    public int hashCode() {
        return Objects.hash(colourTower, hasMotherNature, numSubIsland, numIsland, students);
    }

    @Override
    public String toString() {
        return "Island{" +
                "colourTower=" + colourTower +
                ", hasMotherNature=" + hasMotherNature +
                ", numSubisland=" + numSubIsland +
                ", numIsland=" + numIsland +
                ", students=" + students +
                '}';
    }

    /**
     * returns the number of sub-islands that the island have
     * @return
     */
    public int getNumSubIsland() {
        return this.numSubIsland;
    }

    /**
     * return which colour dominate that island
     * @return
     * @throws MissingTowerException
     */
    public ColourTower getColourTower() throws MissingTowerException {
        if(this.colourTower.equals(null)){
            throw new MissingTowerException();
        }
        else{
            return this.colourTower;
        }
    }

    /**
     * change the colour of the tower on the island
     * @param colourTower
     */
    public void setColourTower(ColourTower colourTower){
        this.colourTower = colourTower;
    }

    /**
     * set the num of the island
     * @param numIsland
     */
     public void setNumIsland(int numIsland){
        this.numIsland = numIsland;
    }

}