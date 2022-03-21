package it.polimi.ingsw.MODEL;

public class Cloud {
    private StudentGroup students;
    public Cloud (){}

    //return group students of the cloud and delete every students from the list of the cloud
    public StudentGroup getStudents() {
        StudentGroup result = new StudentGroup(students);
        students.clear();
        return result;
    }
}
