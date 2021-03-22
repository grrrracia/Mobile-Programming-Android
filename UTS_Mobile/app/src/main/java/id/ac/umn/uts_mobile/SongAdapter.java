package id.ac.umn.uts_mobile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {
    Context mContext;
    MediaMetadataRetriever metaRetriver = new MediaMetadataRetriever();

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvTitle, tvArtist;
        ImageView albumArt;
        LinearLayout parentLayout;

        public ViewHolder(View itemView){
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvArtist = (TextView) itemView.findViewById(R.id.tvArtist);
//            tvAlbum = (TextView) itemView.findViewById(R.id.tvAlbum);
            parentLayout = itemView.findViewById(R.id.llParentLayout);
            albumArt = itemView.findViewById(R.id.albumArt);
        }
    }

    private List<SongModel> mSongs;

    public SongAdapter(Context context, List<SongModel> songList){
        mContext = context;
        mSongs = songList;
    }

    @Override
    public SongAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View songView = inflater.inflate(R.layout.item_song, parent, false);
        ViewHolder viewHolder = new ViewHolder(songView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SongAdapter.ViewHolder holder, int position){
        SongModel songModel = mSongs.get(position);
        TextView songTitle = holder.tvTitle;
        TextView songArtist = holder.tvArtist;
        ImageView songAlbumArt = holder.albumArt;
//        TextView songAlbum = holder.tvAlbum;

        String songPath = songModel.getmPath();
        songTitle.setText(songModel.getmTitle());
        songArtist.setText(songModel.getmArtist());
//        songAlbum.setText(songModel.getmAlbum());
        int songIndex = songModel.getmIndex();
        int max = mSongs.size();

        metaRetriver.setDataSource(songPath);
        byte[] art = metaRetriver.getEmbeddedPicture();
        if (art != null){
            Bitmap songImage = BitmapFactory
                    .decodeByteArray(art, 0, art.length);
            songAlbumArt.setImageBitmap(songImage);
        }else{
            songAlbumArt.setImageDrawable(songAlbumArt.getResources().getDrawable(R.drawable.cdimage));
        }

        holder.parentLayout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PlaySongActivity.class);
                intent.putExtra("songTitle", songTitle.getText().toString());
                intent.putExtra("songArtist", songArtist.getText().toString());
//                intent.putExtra("songAlbum", songAlbum.getText().toString());
                intent.putExtra("songPath", songPath);
                intent.putExtra("songIndex", songIndex);
                intent.putExtra("listSize", max);

                mContext.startActivity(intent);
            }
        });

    }


    public int getItemCount(){
        return mSongs.size();
    }
}
