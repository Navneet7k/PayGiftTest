package navneet.com.paygifttest;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import navneet.com.paygifttest.model.Article;
import navneet.com.paygifttest.model.NewsResponse;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private NewsAdapter newsAdapter;
    private RecyclerView newsRecyclerView;
    private ArrayList<Article> articles=new ArrayList<>();
    private ProgressBar progress_bar;
    private TextView error_text;
    RequestInterface requestInterface;
    private LinearLayoutManager linearLayoutManager;

    private int page_no=1;
    private int item_count=10;

    private boolean isLoadingTrue=true;
    private int pastVisibleItems,visibleItemCount,totalItemCount,previousTotal=0;
    private int viewThreshold=10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progress_bar=(ProgressBar)findViewById(R.id.progress_bar);
        error_text=(TextView) findViewById(R.id.error_text);
        newsRecyclerView=(RecyclerView)findViewById(R.id.news_list);
        linearLayoutManager=new LinearLayoutManager(this);
        newsRecyclerView.setLayoutManager(linearLayoutManager); //setting layout manager for recyclerview, we may use grid layout manager, staggered grid layout manger or even a custom layout manager also

        newsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount=linearLayoutManager.getChildCount();
                totalItemCount=linearLayoutManager.getItemCount();
                pastVisibleItems=linearLayoutManager.findFirstVisibleItemPosition();

                if (dy>0) {
                    if (isLoadingTrue) {
                        if (totalItemCount>previousTotal) {
                            isLoadingTrue=false;
                            previousTotal=totalItemCount;
                        }
                    }

                    if (!isLoadingTrue && (totalItemCount-visibleItemCount)<=(pastVisibleItems+viewThreshold)) {
                        page_no++;
                        performPaging();
                        isLoadingTrue=true;
                    }
                }
            }
        });

        getNewsFeed(); //fetches the json response from newsapi.org using retrofit
    }

    private void getNewsFeed() {

        requestInterface=ApiClient.getApiClient().create(RequestInterface.class); // request interface is where we define all our endpoints
        Call<NewsResponse> call=requestInterface.getNewsFeed(item_count,page_no);

        call.enqueue(new Callback<NewsResponse>() {  // calling enqueue for async operation
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                progress_bar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    articles = new ArrayList<Article>(response.body().getArticles());
                    newsAdapter=new NewsAdapter(MainActivity.this,articles);
                    newsRecyclerView.setAdapter(newsAdapter);
                } else {
                    error_text.setVisibility(View.VISIBLE); // display error message when response is not successfull
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsResponse> call,@NonNull Throwable t) {
                progress_bar.setVisibility(View.GONE);
                error_text.setVisibility(View.VISIBLE); // display error message when failure response
                Toast.makeText(MainActivity.this,"Failure!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void performPaging() {
        progress_bar.setVisibility(View.VISIBLE);
        Call<NewsResponse> call=requestInterface.getNewsFeed(item_count,page_no);
        call.enqueue(new Callback<NewsResponse>() {  // calling enqueue for async operation
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                if (response.body().getStatus().equals("ok")) {
                    ArrayList<Article> articleArrayList=new ArrayList<>(response.body().getArticles());
                    newsAdapter.addImages(articleArrayList);
                    Toast.makeText(MainActivity.this,"loaded page : "+page_no,Toast.LENGTH_SHORT).show();
                }
                progress_bar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<NewsResponse> call,@NonNull Throwable t) {
                progress_bar.setVisibility(View.GONE);
                error_text.setVisibility(View.VISIBLE); // display error message when failure response
                Toast.makeText(MainActivity.this,"Failure!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
