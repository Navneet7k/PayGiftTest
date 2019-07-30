package navneet.com.paygifttest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import navneet.com.paygifttest.model.Article;

public class MainActivity extends AppCompatActivity {

    private NewsAdapter newsAdapter;
    private RecyclerView newsRecyclerView;
    private ArrayList<Article> articles=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
