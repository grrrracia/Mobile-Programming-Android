package id.ac.umn.week11_28059;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ApiInterface mApiInterface;
    private RecyclerView mRecyclerView;
    private PostsAdapter postsAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static MainActivity ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mApiInterface = APIClient.getClient().create(ApiInterface.class);
//        ma = this;
        refresh();
    }

    public void refresh(){
        Call<List<PostsModel>> postsCall = mApiInterface.getPosts();
        postsCall.enqueue(new Callback<List<PostsModel>>() {
            @Override
            public void onResponse(Call<List<PostsModel>> call, Response<List<PostsModel>> response) {
                List<PostsModel> postsModelList = response.body();
                Log.d("Retrofit Get", "Jumlah data Posts: " + String.valueOf(postsModelList.size()));
                postsAdapter = new PostsAdapter(postsModelList);
                mRecyclerView.setAdapter(postsAdapter);
            }

            @Override
            public void onFailure(Call<List<PostsModel>> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
            }
        });
    }
}