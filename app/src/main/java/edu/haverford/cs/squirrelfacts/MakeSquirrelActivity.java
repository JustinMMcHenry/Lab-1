package edu.haverford.cs.squirrelfacts;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MakeSquirrelActivity extends AppCompatActivity {

    private int requestedCode;


    /**
     * Sets up screen where user can enter info to add a new squirrel.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_squirrel);
        Button button = findViewById(R.id.finishedMaking);

        final EditText newName = findViewById(R.id.editName);
        final EditText newLoc = findViewById(R.id.editLocation);
        final EditText newPic = findViewById(R.id.editPicture);


        final Context context = this;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nam = (newName.getText().length()!=0 ? newName.getText().toString(): "Sad Nameless Squirrel");
                String loc = (newLoc.getText().length()!=0 ? newLoc.getText().toString(): "Sad Aimless Squirrel");
                String pic = newPic.getText().toString();

                Intent it = new Intent(context, MainActivity.class);

                it.putExtra("name", nam);
                it.putExtra("location",loc);
                it.putExtra("picture",pic);

                setResult(RESULT_OK,it);
                setIntent(it);
                finish();
            }
        });

        return;
    }
}


