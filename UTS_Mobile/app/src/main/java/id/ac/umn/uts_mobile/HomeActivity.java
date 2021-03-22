package id.ac.umn.uts_mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private static final int MY_PERMISSION_RESULT = 1;
    ArrayList<SongModel> songList;
    Context context = HomeActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        setSupportActionBar(myToolbar);

        welcomeMessage();

        doStuff();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menuProfile:
                startActivity(new Intent(HomeActivity.this, ProfilePage.class));
                return(true);
            case R.id.menuLogout:
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }

    public void welcomeMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Welcome");
        builder.setMessage("Gracia Angelica N \n 00000028059");

        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void doStuff(){
        RecyclerView rvSongList = (RecyclerView) findViewById(R.id.rvSongList);
        Context context = HomeActivity.this;

        songList = SongModel.createSongList(context);
        SongAdapter adapter = new SongAdapter(context, songList);
        rvSongList.setAdapter(adapter);
        rvSongList.setLayoutManager(new LinearLayoutManager(this));
    }

}