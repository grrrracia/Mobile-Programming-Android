package id.ac.umn.week11_28059;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.MyViewHolder> {
    List<PostsModel> mPostsList;

    public PostsAdapter(List <PostsModel> postsModelList){
        this.mPostsList = postsModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.posts_list, parent, false);
        MyViewHolder mViewHolder = new MyViewHolder(mView);
        return mViewHolder;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        holder.tvTitle.setText(mPostsList.get(position).getTitle());
        holder.tvBody.setText(mPostsList.get(position).getBody());
    }
    @Override
    public int getItemCount(){
        return mPostsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView tvTitle, tvBody;

        public MyViewHolder(View mView){
            super(mView);
            tvTitle = mView.findViewById(R.id.tvTitle);
            tvBody = mView.findViewById(R.id.tvBody);
        }
    }

}
