package it.polimi.ingsw.MODEL;

import it.polimi.ingsw.MODEL.Exception.MissingStudentException;

import java.io.Serializable;

public class Entrance implements Serializable {
    private StudentGroup studentGroup;

    public Entrance(){
        this.studentGroup = new StudentGroup();
    }

    /**
     * remove the student with the colour of the parameter
     * @param colour
     * @throws MissingStudentException
     */
    public void removeStudent(Colour colour) throws MissingStudentException{
        if(this.studentGroup.countStudentsOfColour(colour) > 0) {
            this.studentGroup.removeStudent(colour);
        } else {
            throw new MissingStudentException();
        }
    }

    /**
     * add student group to the entrance
     * @param students
     */
    public void addGroup(StudentGroup students) {
        this.studentGroup.addStudents(students);
    }

    /**
     * returns the student group of the entrance
     * @return
     */
    public StudentGroup getStudentGroup(){
        return this.studentGroup;
    }
}
