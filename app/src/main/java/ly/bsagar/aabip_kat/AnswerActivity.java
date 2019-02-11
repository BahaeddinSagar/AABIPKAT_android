package ly.bsagar.aabip_kat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AnswerActivity extends AppCompatActivity {

    TextView isCorrectTextView;
    TextView ExplinationTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        // get the explanation and isCorrect variable from previous activity
        Intent i = getIntent();
        boolean isCorrect =  i.getBooleanExtra("isCorrect",false);
        String Explination = i.getStringExtra("Explination");

        isCorrectTextView = findViewById(R.id.isCorrect);
        ExplinationTextView = findViewById(R.id.Explaination);
        // display the correct and explanation views
        if (isCorrect) {
            isCorrectTextView.setText("Correct");
        } else {
            isCorrectTextView.setText("Incorrect");
        }
        ExplinationTextView.setText(Explination);

    }


    void NextQuestion(View view){
        QuestionActivity.QuestionNumber +=1;
        this.finish();


    }
}
