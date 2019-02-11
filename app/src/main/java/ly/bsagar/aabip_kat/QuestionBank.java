package ly.bsagar.aabip_kat;

import java.util.ArrayList;

public class QuestionBank {

    static ArrayList<Question> Module1Questions;
    static ArrayList<String> QuestionNumbers;
    // make string array of the question numbers
    static void makeStringArray(){
        QuestionNumbers = new ArrayList<String>();
        for (int i = 1 ; i <= Module1Questions.size(); i++){
            QuestionNumbers.add("Question "+i);
        }
    }
}
