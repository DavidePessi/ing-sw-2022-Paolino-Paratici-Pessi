package it.polimi.ingsw.MODEL;

import it.polimi.ingsw.MODEL.Exception.MissingCloudException;

import java.io.Serializable;

public class Cloud implements Serializable {
    private StudentGroup students;

    public Cloud (){
        students = new StudentGroup();
    }

    /**
     * returns the student group of the cloud and delete every student from the list of the cloud
     * @return
     * @throws MissingCloudException
     */
    public StudentGroup getStudents() throws MissingCloudException {
        if(this.students.size() == 0){
            throw new MissingCloudException("Cloud empty");
        }
        else {
            StudentGroup result = new StudentGroup(students);
            students.clear();
            return result;
        }
    }

    /**
     * add a student group to cloud after the end of the turn
     * @param studentGroup
     */
    public void addStudents (StudentGroup studentGroup){
        this.students.addStudents(studentGroup); //we have to check that we can only add 3 students when the cloud is empty or take 3 students, and we can have only 3 or 0 students on a cloud
    }

    /**
     * returns the student to position i
     * @param i
     * @return
     */
    public Student getStudent(int i){
        return students.get(i);
    }

    /**
     * verify is the cloud is empty
     * @return
     */
    public boolean empty(){
        for(Colour colour : Colour.values()) {
            if(this.students.countStudentsOfColour(colour) != 0){
                return false;
            }
        }
        return true;
    }
}
