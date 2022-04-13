package it.polimi.ingsw.MODEL;

import java.util.List;
import java.util.Objects;

// equals modificata senza: Objects.equals(students, island.students), se da problemi controlliamo questo
public class Island {

    private ColourTower colourTower;
    private boolean hasMotherNature;
    private int numSubisland;
    private int numIsland;
    private StudentGroup students;
    private int numSubIsland;

    /*Why don't we pass 2 attributes of type Island to the constructor? */
    public Island(int numIsland) {
        this.numIsland = numIsland;
        this.numSubisland = 1;
        this.colourTower = null;
        this.hasMotherNature = false;
        this.students = new StudentGroup();
    }
    public static Island fusion(Island i1, Island i2, List<Island> li) {

        if (i1 != null && i2 !=null && li!=null){
            Island i = new Island(i1.getNumIsland()); //numero della isola1 < numero della isola2, passate in senso antiorario

            //mettiamo sulla nuova isola i tutti gli studenti delle due isole
            i.students.addStudents(i1.students);
            i.students.addStudents(i2.students);

            i.numSubisland = i1.getNumSubIsland() + i2.getNumSubIsland();

            li.remove(i2.getNumIsland());
            li.remove(i1.getNumIsland()); //le isole vanno da 0 a 11

            li.add(i1.getNumIsland(), i);

            for (Island x : li) {
                if (x.getNumIsland() > i.getNumIsland()) {
                    x.setNumIsland(x.getNumIsland() - 1);
                }
            }
            return i;
        }
        return null;
    }

    public void setMotherNature(boolean hasMotherNature){
        if(hasMotherNature){
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

    public int getNumSubIsland() {
        return this.numSubisland;
    }

    public ColourTower getColourTower() throws MissingTowerException {
        if(this.colourTower == null){
            throw new MissingTowerException();
        }
        else{
            return this.colourTower;
        }
    }

    //cambia il colore delle torri sull'isola
    public void setColourTower(ColourTower colourTower){
        this.colourTower = colourTower;
    }

     public void setNumIsland(int numIsland){
        this.numIsland = numIsland;
    }

    public void setNumSubIsland(int numSubIsland){
        this.numSubIsland = numSubIsland;
    }
}
