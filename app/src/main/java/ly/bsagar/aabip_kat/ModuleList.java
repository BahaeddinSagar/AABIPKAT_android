package ly.bsagar.aabip_kat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ModuleList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_list);
        // number of modules,
        String[] Modules = {"Module 1", "Module 2", "Module 3", "Module 4" } ;
        // make the list adapter that will hold the modules
        final ListAdapter ModulesList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Modules);

        ListView ModulesListView = (ListView) findViewById(R.id.ModulesListView);
        ModulesListView.setAdapter(ModulesList);


        ModulesListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // module 1 only will show data, others show comming soon
                        if (position == 0) {
                            Intent Questions = new Intent(((ArrayAdapter) ModulesList).getContext(), QuestionsList.class);
                            startActivity(Questions);


                        } else {
                            Toast.makeText(ModuleList.this, "Comming Soon", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

    }



}
