package id.ac.umn.week04b_28059;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText etNama;
    private Button btnChange1, btnChange2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNama = findViewById(R.id.nama);
        btnChange1 = findViewById(R.id.main_button_change_1);
        btnChange2 = findViewById(R.id.main_button_change_2);

        btnChange1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent1 = new Intent(MainActivity.this, SecondActivity.class);
                //blm
                String nama = etNama.getText().toString();
                intent1.putExtra("MessageFromMain", nama);
                startActivityForResult(intent1, 1);
            }
        });

        btnChange2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent2 = new Intent(MainActivity.this, ThirdActivity.class);
                //blm
                String nama = etNama.getText().toString();
                intent2.putExtra("MessageFromMain", nama);
                startActivityForResult(intent2, 1);
            }
        });
    }

}