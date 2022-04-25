package it.polimi.ingsw.MODEL;

import it.polimi.ingsw.MODEL.Exception.MissingStudentException;

import java.util.Random;

public class Bag {

    private int numBlue;
    private int numGreen;
    private int numRed;
    private int numPink;
    private int numYellow;

    public Bag(){
        numBlue = 26;
        numGreen = 26;
        numPink = 26;
        numRed = 26;
        numYellow = 26;
    }

    public int size(){
        return numBlue+numGreen+numPink+numRed+numYellow;
    }

    public StudentGroup startingPullOut(){
        StudentGroup studentGroup = new StudentGroup();
        studentGroup.addStudent(Colour.BLUE);
        studentGroup.addStudent(Colour.BLUE);
        studentGroup.addStudent(Colour.GREEN);
        studentGroup.addStudent(Colour.GREEN);
        studentGroup.addStudent(Colour.PINK);
        studentGroup.addStudent(Colour.PINK);
        studentGroup.addStudent(Colour.RED);
        studentGroup.addStudent(Colour.RED);
        studentGroup.addStudent(Colour.YELLOW);
        studentGroup.addStudent(Colour.YELLOW);
        this.numBlue = this.numBlue - 2;
        this.numGreen = this.numGreen - 2;
        this.numPink = this.numPink - 2;
        this.numRed = this.numRed - 2;
        this.numYellow = this.numYellow - 2;

        return studentGroup;
    }

    public Colour pullOut()throws MissingStudentException {
        /*
        * il metodo calcola uno studente di colore randomico basato sui colori rimasti nel sacchetto e lo estrae
        * se non ci sono più studenti nella bag viene lanciata una missingstudentexception per avvisare
        */
        int numstudenti = this.numBlue + this.numGreen + this.numPink + this.numYellow + this.numRed;
        Random random = new Random();

        //verifico che ci sia almeno uno studente
        if(numstudenti == 0) throw new MissingStudentException("studenti finiti nella bag");

        else {
            int randomNum = random.nextInt(numstudenti) + 1;

                //caso estrazione blue
            if (this.numBlue >= randomNum) {
                this.numBlue--;
                return Colour.BLUE;

                //caso estrazione verde
            } else if (this.numBlue + this.numGreen >= randomNum) {
                this.numGreen--;
                return Colour.GREEN;

                //caso estrazione rosa
            } else if (this.numBlue + this.numGreen + this.numPink >= randomNum) {
                this.numPink--;
                return Colour.PINK;

                //caso estrazione rosso
            } else if (this.numBlue + this.numGreen + this.numPink + this.numRed >= randomNum) {
                this.numRed--;
                return Colour.RED;

                //caso estrazione giallo
            } else {
                this.numYellow--;
                return Colour.YELLOW;
            }
        }
    }
}
