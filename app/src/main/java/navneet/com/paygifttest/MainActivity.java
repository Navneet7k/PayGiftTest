package navneet.com.paygifttest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

import navneet.com.paygifttest.model.Article;
import navneet.com.paygifttest.model.NewsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private NewsAdapter newsAdapter;
    private RecyclerView newsRecyclerView;
    private ArrayList<Article> articles=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsRecyclerView=(RecyclerView)findViewById(R.id.news_list);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        getNewsFeed(); //fetches the json response from newsapi.org using retrofit
    }

    private void getNewsFeed() {

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();  //creating a retrofit object and loading it with gson converter factory for json to pojo conversions easy

        RequestInterface requestInterface=retrofit.create(RequestInterface.class); // request interface is where we define all our endpoints
        Call<NewsResponse> call=requestInterface.getNewsFeed();

        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful()) {
                    articles = new ArrayList<Article>(response.body().getArticles());
                    newsAdapter=new NewsAdapter(MainActivity.this,articles);
                    newsRecyclerView.setAdapter(newsAdapter);
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Failure!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
