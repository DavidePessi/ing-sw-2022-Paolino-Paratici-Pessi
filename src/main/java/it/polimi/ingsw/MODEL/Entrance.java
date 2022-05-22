package it.polimi.ingsw.MODEL;

import it.polimi.ingsw.MODEL.Exception.MissingStudentException;

import java.io.Serializable;

public class Entrance implements Serializable {

    private StudentGroup studentGroup;

    public Entrance(){
        this.studentGroup = new StudentGroup();
    }

    public void removeStudent(Colour colour) throws MissingStudentException{
        if(this.studentGroup.countStudentsOfColour(colour) > 0) {
            this.studentGroup.removeStudent(colour);
        } else {
            throw new MissingStudentException();
        }
    }

    public void addGroup(StudentGroup students) {
        this.studentGroup.addStudents(students);
    }

    public StudentGroup getStudentGroup(){
        return this.studentGroup;
    }
}
