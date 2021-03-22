package id.ac.umn.uts_mobile;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;

public class SongModel {
    private String mTitle, mArtist, mAlbum, mPath;
    private int mIndex;

    public SongModel(String title, String artist, String album,String path, int index){
        mTitle = title;
        mArtist = artist;
        mAlbum = album;
        mPath = path;
        mIndex = index;
    }

    public String getmTitle() {
        return mTitle;
    }
    public String getmArtist() {
        return mArtist;
    }
    public String getmAlbum() {
        return mAlbum;
    }
    public String getmPath() {
        return mPath;
    }
    public int getmIndex(){
        return mIndex;
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
}
