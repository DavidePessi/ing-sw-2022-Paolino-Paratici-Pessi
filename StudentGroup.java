package it.polimi.ingsw.MODEL;
import java.util.*;

public class StudentGroup {

    private List<Student> listStudent;

    public StudentGroup(){
        
    }
    //create a new StudentGroup that is the copy of the one given
    public StudentGroup(StudentGroup copy){
        try {
            for (int i = 0; i < copy.size(); i++) {
                this.listStudent.add(copy.get(i));
            }
        }catch(NullPointerException e){}
    }

    public int countBlueStudent(){
        int numBlue=0;
        for (Student student : listStudent) {
            if (student.getColour().equals(Colour.BLUE)) {
                numBlue++;
            }
        }
        return numBlue;
    }
    public int countGreenStudent(){
        int numGreen=0;
        for (Student student : listStudent) {
            if (student.getColour().equals(Colour.GREEN)) {
                numGreen++;
            }
        }
        return numGreen;
    }
    public int countPinkStudent(){
        int numPink=0;
        for (Student student : listStudent) {
            if (student.getColour().equals(Colour.PINK)) {
                numPink++;
            }
        }
        return numPink;
    }
    public int countRedStudent(){
        int numRed=0;
        for (Student student : listStudent) {
            if (student.getColour().equals(Colour.RED)) {
                numRed++;
            }
        }
        return numRed;
    }
    public int countYellowStudent(){
        int numYellow=0;
        for (Student student : listStudent) {
            if (student.getColour().equals(Colour.YELLOW)) {
                numYellow++;
            }
        }
        return numYellow;
    }

    /*public void removeStudent(Student student){
        listStudent.remove(listStudent.indexOf(student));
        // it may writeen like this: listStudent.remove(student);
    }*/

    public void removeStudent(Colour colour) throws IllegalArgumentException {
        boolean removed = false;
        for(int i=0; i<listStudent.size() && removed==false; i++){
            if(listStudent.get(i).getColour().equals(colour)){
                listStudent.remove(listStudent.get(i));
                removed = true;
            }
        }
        if(!removed){
            throw new IllegalArgumentException() ;
        }
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

    public void addStudent(Colour colour){
        listStudent.add(new Student(colour));
    }

    public Colour pullOut(){
        /*da implementare:
         * deve estrarre uno studente casuale dalla lista, ritornarne il colore e rimuovere lo studente dalla lista*/
        return Colour.BLUE;
    }
}
