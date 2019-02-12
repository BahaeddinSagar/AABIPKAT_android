package ly.bsagar.aabip_kat;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class QuestionActivity extends AppCompatActivity {

    static int QuestionNumber;
    static int QuestionIndex;
    TextView QuestionTextView;
    TextView QuestionNumberView;
    Button ViewImage;
    ListAdapter AnswerList;
    ListView AnswerListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        // get the question number from previous activity
        Intent i = getIntent();
        QuestionIndex = i.getIntExtra("QuestionNumber", -2);
        QuestionNumber = QuestionIndex + 1;


    }

    @Override
    protected void onStart() {
        super.onStart();
        // get view refrences from view.
        QuestionTextView = findViewById(R.id.QuestionText);
        QuestionNumberView = findViewById(R.id.QuestionNumber);
        ViewImage = findViewById(R.id.ViewImage);
        // view image is always disabled until proven otherwise
        ViewImage.setEnabled(false);
        // set question text and number.
        QuestionNumberView.setText("Question " + QuestionNumber);
        if (QuestionIndex >= QuestionBank.Module1Questions.size()) {
            Toast.makeText(this, "End of questions", Toast.LENGTH_SHORT).show();
            this.finish();
        } else {
            QuestionTextView.setText(QuestionBank.Module1Questions.get(QuestionIndex).Question);


            // make the arrayadapter for the answerlist
            AnswerList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, QuestionBank.Module1Questions.get(QuestionIndex).options);
            AnswerListView = (ListView) findViewById(R.id.AnswerList);
            AnswerListView.setAdapter(AnswerList);

            // if the question has an image, try to display the image
            if (!QuestionBank.Module1Questions.get(QuestionIndex).imageURL.equals("")) {
                ViewImage.setEnabled(true);
                //File storagePath = new File(Environment.getExternalStorageDirectory(), "Module1");
                //final File localFile = new File(storagePath, "Question " + QuestionNumber + 1);
                Log.d("Image issues", "Image exists for question " + QuestionNumber);


            } else {
                Log.d("Image issues", "NO Image exists for question " + QuestionNumber);
            }


            AnswerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // the clicked answer is false until proven otherwise.
                    boolean isCorrect = false;
                    // check if the clicked answer is correct.
                    if (position == QuestionBank.Module1Questions.get(QuestionIndex).CorrectAnswer) {
                        isCorrect = true;
                    }
                    // show the answerActivity and pass the explanation and isCorrect variable.
                    Intent QuestionActivity = new Intent(AnswerListView.getContext(), AnswerActivity.class);
                    QuestionActivity.putExtra("isCorrect", isCorrect);
                    QuestionActivity.putExtra("Explination", QuestionBank.Module1Questions.get(QuestionIndex).Explination);
                    startActivity(QuestionActivity);


                }
            });

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!QuestionBank.Module1Questions.get(QuestionIndex).imageURL.equals("")) {
            ViewImage.setEnabled(true);
            //File storagePath = new File(Environment.getExternalStorageDirectory(), "Module1");
            //final File localFile = new File(storagePath, "Question " + QuestionNumber + 1);
            Log.d("Image issues", "Image exists for question " + QuestionNumber);


        } else {
            Log.d("Image issues", "NO Image exists for question " + QuestionNumber);
        }

    }

    // this is triggered when show image button is pressed
    public void ShowImage(View view) {
        Dialog builder = new Dialog(this);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //nothing;
            }
        });

        ImageView imageView = new ImageView(this);
        Picasso.get()
                .load("file://" + Environment.getExternalStorageDirectory() + "/AAP/Module1/Question " + QuestionNumber)
                .into(imageView);
        Log.d("Imge issues", "ShowImage: " + Environment.getExternalStorageDirectory());

        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();
    }
}
