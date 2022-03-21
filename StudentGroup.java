package it.polimi.ingsw.MODEL;
import java.util.*;

public class StudentGroup {

    private List<Student> listStudent;
    public int countBlueStudent(){}
    public int countGreenStudent(){}
    public int countPinkStudent(){}
    public int countRedStudent(){}
    public int countYellowStudent(){}

    //create a new StudentGroup that is the copy of the one given
    public StudentGroup(StudentGroup copia){
        try {
            for (int i = 0; i < copia.size(); i++) {
                this.listStudent.add(copia.get(i));
            }
        }catch(NullPointerException e){}
    }

    public void removeStudent(Colour colour){

    }
    //delete every students from the list listStudent
    public void clear(){listStudent.clear();}

    //return the length of the list listStudent (the number of students)
    public int size(){
        return this.listStudent.size();
    }

    //return the student in position pos in the list listStudent
    public Student get(int pos){
        return this.listStudent.get(pos);
    }

    //add every students of the group given to the list listStudent
    public void addStudents(StudentGroup students){
        try {
            for (int i = 0; i < students.size(); i++) {
                this.listStudent.add(students.get(i));
            }
        }catch(NullPointerException e){}
    }
}
