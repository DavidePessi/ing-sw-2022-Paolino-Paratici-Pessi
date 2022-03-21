package it.polimi.ingsw.MODEL;

public class Entrance{

    private StudentGroup studentGroup;

    /*public void removeStudent(Student student){
        studentGroup.removeStudent(student);
    }*/

    public void removeStudent(Colour colour){
        studentGroup.removeStudent(colour);
    }

    //aggiungo studenti alla nuvola
    public void addGroup(StudentGroup students) {
        studentGroup.addStudents(students);
    }
}
