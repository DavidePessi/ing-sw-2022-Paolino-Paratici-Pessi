package it.polimi.ingsw.MODEL;

public class Entrance{

    private StudentGroup studentGroup;

    public Entrance(){
        this.studentGroup = new StudentGroup();
    }

    public void removeStudent(Colour colour){
        this.studentGroup.removeStudent(colour);
    }

    public void addGroup(StudentGroup students) {
        this.studentGroup.addStudents(students);
    }

    public StudentGroup getStudentGroup(){
        return this.studentGroup;
    }
}
