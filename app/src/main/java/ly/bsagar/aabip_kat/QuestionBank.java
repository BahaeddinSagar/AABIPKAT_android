package ly.bsagar.aabip_kat;

import java.util.ArrayList;

public class QuestionBank {

    public static ArrayList<Question> Module1Questions;
    public static ArrayList<String> QuestionNumbers;
    // make string array of the question numbers
    public static void makeStringArray(){
        QuestionNumbers = new ArrayList<String>();
        for (int i = 1 ; i <= Module1Questions.size(); i++){
            QuestionNumbers.add("Question "+i);
        }
    }
}
