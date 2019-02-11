package ly.bsagar.aabip_kat;



import java.util.ArrayList;

public class Question {

    String imageURL;
    String Question;
    String AnswerA;
    String AnswerB;
    String AnswerC;
    String AnswerD;
    String AnswerE;
    int CorrectAnswer;
    String Explination ;
    ArrayList<String> options;
    int Index;

    void Question(int Index, String imageURL, String Question, String AnswerA,String AnswerB,String AnswerC,String AnswerD,String AnswerE, int CorrectAnswer, String Explination ) {
        this.imageURL = imageURL;
        this.Question = Question;
        this.AnswerA = AnswerA;
        this.AnswerB = AnswerB;
        this.AnswerC = AnswerC;
        this.AnswerD = AnswerD;
        this.AnswerE = AnswerE;
        this.CorrectAnswer = CorrectAnswer;
        this.Explination = Explination;
        this.Index = Index;
    }

    void makeAnswers(){
        options = new ArrayList<String>();
        options.add(AnswerA);
        options.add(AnswerB);
        options.add(AnswerC);
        options.add(AnswerD);
        options.add(AnswerE);
    }


}
