package id.ac.umn.week06_28059;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import id.ac.umn.week06_28059.TutorialAnimasiDrawable.TutorialAnimasiDrawableActivity;
import id.ac.umn.week06_28059.TutorialAnimasiFisika.TutorialAnimasiFisikaActivity;
import id.ac.umn.week06_28059.TutorialAnimasiProperty.TutorialAnimasiPropertyActivity;


public class MainActivity extends AppCompatActivity {

    Button btnTutorialAnimasiProperti, btnTutorialAnimasiDrawable, btnTutorialAnimasiFisika ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTutorialAnimasiProperti = findViewById(R.id.btnTutorialAnimasiProperti);
        btnTutorialAnimasiDrawable = findViewById(R.id.btnTutorialAnimasiDrawable);
        btnTutorialAnimasiFisika = findViewById(R.id.btnTutorialAnimasiFisika);

        btnTutorialAnimasiProperti.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, TutorialAnimasiPropertyActivity.class));
            }
        });

        btnTutorialAnimasiDrawable.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, TutorialAnimasiDrawableActivity.class));
            }
        });

        btnTutorialAnimasiFisika.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, TutorialAnimasiFisikaActivity.class));
            }
        });
    }
}