package it.polimi.ingsw.MODEL;
import java.security.InvalidParameterException;
import java.util.*;

public class StudentGroup {

    private List<Student> listStudent;
    public int countBlueStudent(){

    }
    public int countGreenStudent(){

    }
    public int countPinkStudent(){

    }
    public int countRedStudent(){

    }
    public int countYellowStudent(){

    }

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



    public void addStudent(Colour colour){
        listStudent.add(new Student(colour));
    }
}
