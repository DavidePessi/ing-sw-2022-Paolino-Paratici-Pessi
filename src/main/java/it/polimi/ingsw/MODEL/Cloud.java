package it.polimi.ingsw.MODEL;

public class Cloud {
    private StudentGroup students;
    public Cloud (){
        students = new StudentGroup();
    }

    //return group students of the cloud and delete every students from the list of the cloud
    public StudentGroup getStudents() {
        StudentGroup result = new StudentGroup(students);
        students.clear();
        return result;
    }

    public void addStudents (StudentGroup studentGroup){
        this.students.addStudents(studentGroup); //we have to check that we can only add 3 students when the cloud is empty or take 3 students, and we can have only 3 or 0 students on a cloud
    }
}
