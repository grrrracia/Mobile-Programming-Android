package id.ac.umn.week08_28059;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import id.ac.umn.week08_28059.TutorialSavedInstanceDanSharedPreference.TutorialSavedInstanceDanSharedPref;
import id.ac.umn.week08_28059.TutorialTextEditor.TutorialTextEditor;

public class MainActivity extends AppCompatActivity {

    Button btn8A, btn8B;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn8A = findViewById(R.id.button8A);
        btn8B = findViewById(R.id.button8B);

        btn8A.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    startActivity(new Intent(MainActivity.this, TutorialTextEditor.class));
        }
        });

        btn8B.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, TutorialSavedInstanceDanSharedPref.class));
            }
        });
    }
}