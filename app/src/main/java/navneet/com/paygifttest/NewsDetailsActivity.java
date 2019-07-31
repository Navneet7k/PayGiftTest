package navneet.com.paygifttest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsDetailsActivity extends AppCompatActivity {

    private ImageView news_image;
    private TextView news_desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsdetails);

        news_image=(ImageView)findViewById(R.id.news_image);
        news_desc=(TextView) findViewById(R.id.news_desc);

        Intent intent=getIntent();

        if (intent!=null) {
           String desc=intent.getStringExtra("desc");
           news_desc.setText(desc);
        }


    }
}
