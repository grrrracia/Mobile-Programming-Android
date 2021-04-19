package id.ac.umn.week10_28059;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import id.ac.umn.week10_28059.TutorialA.TutorialAMainActivity;
import id.ac.umn.week10_28059.TutorialB.TutorialBMainActivity;
import id.ac.umn.week10_28059.TutorialC.TutorialCMainActivity;

public class MainActivity extends AppCompatActivity {
    Button tutorialA, tutorialB, tutorialC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tutorialA = findViewById(R.id.btnTutorialA);
        tutorialB = findViewById(R.id.btnTutorialB);
        tutorialC = findViewById(R.id.btnTutorialC);

        tutorialA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TutorialAMainActivity.class));
            }
        });
        tutorialB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TutorialBMainActivity.class));
            }
        });
        tutorialC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TutorialCMainActivity.class));
            }
        });
    }
}