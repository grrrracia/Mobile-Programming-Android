package id.ac.umn.uts_mobile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class PlaySongActivity extends AppCompatActivity implements Serializable {

    TextView title, artist, album, path, totalTime, currentTime, songPos;
    Button pausePlay, prev, next;
    SeekBar songBar;
    MediaPlayer mp = new MediaPlayer();
    double current_pos, total_duration;
    Context context = PlaySongActivity.this;
    int songIndex, maxListSize;
    ImageView albumArt;
//    Drawable pause = (Drawable)getResources().getDrawable(R.drawable.pause);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);

        title = findViewById(R.id.tvSongTitle);
        artist = findViewById(R.id.tvSongArtist);
//        album = findViewById(R.id.tvSongAlbum);
//        path = findViewById(R.id.tvPath);
        pausePlay = findViewById(R.id.btnPausePlay);
        prev = findViewById(R.id.btnPrev);
        next = findViewById(R.id.btnNext);
//        songPos = findViewById(R.id.tvSongPosition);
        songBar = findViewById(R.id.sbSongBar);
        albumArt = findViewById(R.id.albumImage);

        getIncomingIntent();
    }

    private void getIncomingIntent(){
        if (getIntent().hasExtra("songTitle")){
            Intent intent = getIntent();
            String songTitle = intent.getStringExtra("songTitle");
            String songArtist = intent.getStringExtra("songArtist");
            String songAlbum= intent.getStringExtra("songAlbum");
            String songPath = intent.getStringExtra("songPath");

            songIndex = intent.getIntExtra("songIndex", 0);
            maxListSize = intent.getIntExtra("listSize", 0);
            System.out.println("Current Index: " + songIndex);
            System.out.println("Max amount of Songs: "+maxListSize);

            MediaMetadataRetriever metaRetriver = new MediaMetadataRetriever();
            metaRetriver.setDataSource(songPath);
            byte[] art = metaRetriver.getEmbeddedPicture();
            if (art != null){
                Bitmap songImage = BitmapFactory
                        .decodeByteArray(art, 0, art.length);
                albumArt.setImageBitmap(songImage);
            }else{
                albumArt.setImageDrawable(getResources().getDrawable(R.drawable.cdimage));
            }

            String ssIndex = Integer.toString(songIndex+1);
            title.setText(songTitle);
            artist.setText(songArtist);
//            album.setText(songAlbum);
//            path.setText(songPath);
//            songPos.setText(ssIndex);

            playSong(songPath);

        }else {
            Context context = getApplicationContext();
            CharSequence text = "Not received";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        }
    }

    private void playSong(String path){
        try{
            mp.reset();
            mp.setDataSource(path);
            mp.prepare();
            int duration = mp.getDuration();
            songBar.setMax(duration);
            mp.start();
//            pausePlay.setText("Pause");
            pausePlay.setBackground(ContextCompat.getDrawable(context, R.drawable.pause));

            setAudioProgress();

        }catch (Exception e){
            e.printStackTrace();
        }

        songBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                current_pos = songBar.getProgress();
                mp.seekTo((int) current_pos);
            }
        });

        pausePlay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (!mp.isPlaying()) {
                    mp.start();
                    setAudioProgress();
//                    pausePlay.setText("Pause");
                    pausePlay.setBackground(ContextCompat.getDrawable(context, R.drawable.pause));
                }else{
                    mp.pause();
//                    pausePlay.setText("Play");
                    pausePlay.setBackground(ContextCompat.getDrawable(context, R.drawable.play));
                }
            }
        });

        prev.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (songIndex < 1){
                    Context context = getApplicationContext();
                    CharSequence text = "First Song in Queue";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }else{
                    mp.stop();
                    finish();
                    newIntent(songIndex - 1);
                }

            }
        });

        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (songIndex == maxListSize-1){
                    Context context = getApplicationContext();
                    CharSequence text = "Last Song in Queue";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }else {
                    mp.stop();
                    finish();
                    newIntent(songIndex + 1);
                }
            }
        });
    }

    private void newIntent(int index){
        ArrayList newSongList = createSongList(context);
        SongModel song = (SongModel) newSongList.get(index);
        String sTitle = song.getmTitle();
        String sArtist = song.getmArtist();
//        String sAlbum = song.getmAlbum();
        String sPath = song.getmPath();
        int sIndex = song.getmIndex();
        int sMax = newSongList.size();

        Intent nextIntent = new Intent(getBaseContext(), PlaySongActivity.class);
        nextIntent.putExtra("songTitle", sTitle.toString());
        nextIntent.putExtra("songArtist", sArtist.toString());
//        nextIntent.putExtra("songAlbum", sAlbum.toString());
        nextIntent.putExtra("songPath", sPath);
        nextIntent.putExtra("songIndex", sIndex);
        nextIntent.putExtra("listSize", sMax);

        startActivity(nextIntent);
        this.finish();
    }

    public static ArrayList<SongModel> createSongList(Context context){
        ArrayList<SongModel> songList = new ArrayList<>();
        ContentResolver contentResolver = context.getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri, null, null, null, null);

        if (songCursor != null && songCursor.moveToFirst()){
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int songAlbum = songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            int songPath = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            do{
                String currentTitle = songCursor.getString(songTitle);
                String currentArtist = songCursor.getString(songArtist);
                String currentAlbum = songCursor.getString(songAlbum);
                String currentPath = songCursor.getString(songPath);
                int currentIndex = songList.size();
                songList.add(new SongModel(currentTitle,currentArtist, currentAlbum, currentPath, currentIndex));
            }while (songCursor.moveToNext());
        }
        return songList;
    }

    public void setAudioProgress(){
        totalTime = findViewById(R.id.tvTotalTime);
        currentTime = findViewById(R.id.tvCurrentTime);
        current_pos = mp.getCurrentPosition();
        total_duration = mp.getDuration();
        totalTime.setText(timerConversion((long) total_duration));
        currentTime.setText(timerConversion((long) current_pos));
        songBar.setMax((int) total_duration);
        final Handler handler = new Handler();

        Runnable runnable = new Runnable(){
            @Override
            public void run(){
                try{
                    current_pos = mp.getCurrentPosition();
                    currentTime.setText(timerConversion((long) current_pos));
                    songBar.setProgress((int) current_pos);
                    handler.postDelayed(this, 1000);
                }catch (IllegalStateException ed){
                    ed.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1000);

    }
    public String timerConversion(long value) {
        String audioTime;
        int dur = (int) value;
        int hrs = (dur / 3600000);
        int mns = (dur / 60000) % 60000;
        int scs = dur % 60000 / 1000;

        if (hrs > 0) {
            audioTime = String.format("%02d:%02d:%02d", hrs, mns, scs);
        } else {
            audioTime = String.format("%02d:%02d", mns, scs);
        }
        return audioTime;
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (mp != null){
            mp.release();
        }
    }


    @Override
    public void onBackPressed(){
        super.onBackPressed();
        mp.stop();
        this.finish();
//        Intent homeIntent = new Intent(this, HomeActivity.class);
//        startActivity(homeIntent);
    }

}