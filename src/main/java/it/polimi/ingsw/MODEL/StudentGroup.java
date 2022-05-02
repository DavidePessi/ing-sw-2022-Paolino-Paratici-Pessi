package it.polimi.ingsw.MODEL;
import it.polimi.ingsw.MODEL.Exception.MissingStudentException;

import java.util.*;

public class StudentGroup {

    private List<Student> listStudent;

    public StudentGroup() {
        listStudent = new ArrayList<>();
    }

    //create a new StudentGroup that is the copy of the one given
    public StudentGroup(StudentGroup copy) {
        listStudent = new ArrayList<>();
        try {
            for (int i = 0; i < copy.size(); i++) {
                this.listStudent.add(copy.get(i));
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public int countStudentsOfColour(Colour colour) {
        int numC = 0;
        for (Student student : listStudent) {
            if (student.getColour().equals(colour)) {
                numC++;
            }
        }
        return numC;
    }

    /*public int countBlueStudent(){
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
                numGreen += 1;
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
    }*/

    /*public void removeStudent(Student student){
        listStudent.remove(listStudent.indexOf(student));
        // it may writeen like this: listStudent.remove(student);
    }*/

    public void removeStudent(Colour colour) throws IllegalArgumentException {
        boolean removed = false;
        for (int i = 0; i < listStudent.size() && !removed; i++) {
            if (listStudent.get(i).getColour().equals(colour)) {
                listStudent.remove(listStudent.get(i));
                removed = true;
            }
        }
        if (!removed) throw new IllegalArgumentException();
    }

    //delete every students from the list listStudent
    public void clear() {
        listStudent.clear();
    }

    //return the length of the list listStudent (the number of students)
    public int size() {
        return this.listStudent.size();
    }

    //return the student in position pos in the list listStudent
    public Student get(int pos) {
        return this.listStudent.get(pos);
    }

    //add every students of the group given to the list listStudent
    public void addStudents(StudentGroup students) {
        try {
            for (int i = 0; i < students.size(); i++) {
                this.listStudent.add(students.get(i));
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void addStudent(Colour colour) {
        listStudent.add(new Student(colour));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentGroup that = (StudentGroup) o;

        boolean returnvalue = true;

        for (Colour col : Colour.values()) {
            if (((StudentGroup) o).countStudentsOfColour(col) != this.countStudentsOfColour(col)) {
                returnvalue = false;
                break;
            }
        }
        return returnvalue;
    }

    public Colour pullOut()throws MissingStudentException {
        /*
         * il metodo calcola uno studente di colore randomico basato sui colori rimasti nelLO StudentGroup e lo estrae
         * se non ci sono più studenti nella bag viene lanciata una missingstudentexception per avvisare
         */
        int NumBlue = this.countStudentsOfColour(Colour.BLUE);
        int NumRed = this.countStudentsOfColour(Colour.RED);
        int NumGreen = this.countStudentsOfColour(Colour.GREEN);
        int NumYellow = this.countStudentsOfColour(Colour.YELLOW);
        int NumPink = this.countStudentsOfColour(Colour.PINK);

        int numstudenti = NumBlue + NumPink +NumGreen + NumRed + NumYellow;

        Random random = new Random();

        //verifico che ci sia almeno uno studente
        if(numstudenti == 0) throw new MissingStudentException("studenti finiti nella bag");

        else {
            int randomNum = random.nextInt(numstudenti) + 1;

            //caso estrazione blue
            if (NumBlue >= randomNum) {
                this.removeStudent(Colour.BLUE);
                return Colour.BLUE;

                //caso estrazione verde
            } else if (NumBlue + NumGreen >= randomNum) {
                this.removeStudent(Colour.GREEN);
                return Colour.GREEN;

                //caso estrazione rosa
            } else if (NumBlue + NumGreen + NumPink >= randomNum) {
                this.removeStudent(Colour.PINK);
                return Colour.PINK;

                //caso estrazione rosso
            } else if (NumBlue + NumGreen + NumPink + NumRed >= randomNum) {
                this.removeStudent(Colour.RED);
                return Colour.RED;

                //caso estrazione giallo
            } else {
                this.removeStudent(Colour.YELLOW);
                return Colour.YELLOW;
            }
        }
    }
}
