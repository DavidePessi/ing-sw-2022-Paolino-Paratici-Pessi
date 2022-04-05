package it.polimi.ingsw.MODEL;

public class Entrance{

    private StudentGroup studentGroup;

    public Entrance(){
        studentGroup = new StudentGroup();
    }

    public void removeStudent(Colour colour){
        studentGroup.removeStudent(colour);
    }

    //aggiungo studenti alla nuvola
    public void addGroup(StudentGroup students) {
        studentGroup.addStudents(students);
    }
}
