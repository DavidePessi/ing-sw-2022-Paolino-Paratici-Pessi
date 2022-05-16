package it.polimi.ingsw.MODEL;

import it.polimi.ingsw.MODEL.Exception.MissingCloudException;

public class Cloud {
    private StudentGroup students;
    public Cloud (){
        students = new StudentGroup();
    }

    //return group students of the cloud and delete every students from the list of the cloud
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

    public void addStudents (StudentGroup studentGroup){
        this.students.addStudents(studentGroup); //we have to check that we can only add 3 students when the cloud is empty or take 3 students, and we can have only 3 or 0 students on a cloud
    }

    public Student getStudent(int i){
        return students.get(i);
    }

    public boolean empty(){
        for(Colour colour : Colour.values()) {
            if(this.students.countStudentsOfColour(colour) != 0){
                return false;
            }
        }
        return true;
    }
}
