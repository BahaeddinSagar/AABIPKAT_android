package ly.bsagar.aabip_kat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class QuestionsList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_list);
        // get the question numbers as a string array
        String [] QuestionNumbers =  QuestionBank.QuestionNumbers.toArray(new String [QuestionBank.QuestionNumbers.size()]);
        // make the list adapter to be held in the list view
        final ListAdapter QuestionList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2,  android.R.id.text1, QuestionNumbers){

            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(QuestionBank.QuestionNumbers.get(position));


                // this should display if the question is answered correctly or not

                //text2.setText(persons.get(position).getAge());

                return view;
            }
        };
        final ListView QuestionsListView = (ListView) findViewById(R.id.QuestionsListView);

        QuestionsListView.setAdapter(QuestionList);

        // when a question is clicked, we go to questionActivity and pass the question number to that activity
        QuestionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent QuestionActivity = new Intent(((ArrayAdapter) QuestionList).getContext(), QuestionActivity.class);
                QuestionActivity.putExtra("QuestionNumber", position);
                startActivity(QuestionActivity);
            }
        });


    }
}
