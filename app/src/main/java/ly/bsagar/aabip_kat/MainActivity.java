package ly.bsagar.aabip_kat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "FIREBASE DATABASE";
    ArrayList<Question> ListOfQuestions;
    private StorageReference mStorageRef;
    static boolean calledAlready = false;
    static FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //intilize firebase app and make storage refrence

        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);
        FirebaseAnalytics mFirebaseAnalytics;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mStorageRef = FirebaseStorage.getInstance().getReference();

    }

    @Override
    protected void onStart() {
        super.onStart();

        ConnectToFirebase();

    }

    void getPermission(View view) {

        isExternalStorageWritable();


    }

    void ConnectToFirebase() {
        if (!calledAlready) {

            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            // enable offline capabilities

            calledAlready = true;
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        // make an empty array list that will hold all of the questions
        ListOfQuestions = new ArrayList<Question>();
        // Read from the database
        myRef.child("Module1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                // making sure that the data exists
                // boolean exist = dataSnapshot.exists();
                ListOfQuestions.clear();
                // looping through every question in snapshot
                for (DataSnapshot question : dataSnapshot.getChildren()) {
                    // store question
                    Question q = question.getValue(Question.class);
                    // avoid null exception, make the "options" array which holds all of the answers
                    if (q != null) {
                        q.makeAnswers();
                    }
                    // add the question to the list of questions
                    ListOfQuestions.add(q);
                }
                // store the list of question in static variables so that it can be accessed anywhere in the app
                QuestionBank.Module1Questions = ListOfQuestions;
                // make array of questions numbers for questionlist activity
                QuestionBank.makeStringArray();
                Log.d(TAG, "Value is: " + QuestionBank.Module1Questions);


                // try to load all of the images into sd card

            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }





    public void Start(View view) {
        Intent Modules = new Intent(this, ModuleList.class);
        //DownloadImages();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                startActivity(Modules);
            } else {
                Toast.makeText(this, "Please give app permission first", Toast.LENGTH_SHORT).show();
            }
        } else {
            startActivity(Modules);
        }
        //startActivity(Modules);
    }


    public boolean isExternalStorageWritable() {
        //String state = Environment.getExternalStorageState();
        //if (Environment.MEDIA_MOUNTED.equals(state)) {
        //    return true;
        //}

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                Toast.makeText(this, "Permission is granted", Toast.LENGTH_LONG).show();
                return true;
            } else {

                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                Toast.makeText(this, "Permission is revoked", Toast.LENGTH_LONG).show();
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            Toast.makeText(this, "Permission is granted", Toast.LENGTH_LONG).show();
            return true;
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
            DownloadImages();
        }
    }


    void DownloadFile(int index, String imageURL) {
        // make the storage path
        StorageReference gsReference = FirebaseStorage.getInstance().getReferenceFromUrl(imageURL);
        File storagePath = new File(Environment.getExternalStorageDirectory(), "AAP//Module1");
        Log.d("file issues", "storagePath: " + storagePath);
        // Create direcorty if not exists
        Boolean DirExists = storagePath.exists();
        Log.d("file issues", "storagePath: " + storagePath.exists());
        if (!storagePath.exists()) {
            storagePath.mkdirs();
        }
        int QuestionNumber = index;
        final File localFile = new File(storagePath, "Question " + QuestionNumber);
        Boolean exists = localFile.exists();
        Log.d("file issues", "localFile: " + localFile.exists());
        if (!localFile.exists()) {
            final int finalI = index;
            gsReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    // Toast.makeText(MainActivity.this, "Question " + finalI + " image downloaded", Toast.LENGTH_SHORT).show();
                    Log.d("file issues", "localFile loaded successfully ");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Toast.makeText(MainActivity.this, "Error Downloading images", Toast.LENGTH_SHORT).show();
                    Log.d("file issues", "localFile NOT loaded successfully ");
                }
            });

        } else {
            // Toast.makeText(MainActivity.this, "File Already exists", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "localFile Already exists ");
        }
    }

    void DownloadImages() {
        for (Question question : ListOfQuestions) {
            if (!question.imageURL.equals("")) {
                StorageReference gsReference = FirebaseStorage.getInstance().getReferenceFromUrl(question.imageURL);
                // check permission for storage
                Log.d("file issues", "Permission: " + isExternalStorageWritable());
                //if (isExternalStorageWritable()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {

                        DownloadFile(question.Index, question.imageURL);
                    } else {
                        isExternalStorageWritable();
                    }
                } else {
                    // permission already granted, just go ahead with file download
                    DownloadFile(question.Index, question.imageURL);
                }
            } else {
                //Toast.makeText(MainActivity.this, "Error with permission", Toast.LENGTH_SHORT).show();
                // Log.d("file issues", "No image for this question ");

            }
        }

    }
}
