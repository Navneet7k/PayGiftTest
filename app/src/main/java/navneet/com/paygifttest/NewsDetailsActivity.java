package navneet.com.paygifttest;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewsDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView news_image;
    private TextView news_desc,news_title,news_date,read_more;
    private String more_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsdetails);

        news_image=(ImageView)findViewById(R.id.news_image);
        news_title=(TextView) findViewById(R.id.news_title);
        news_date=(TextView) findViewById(R.id.news_date);
        news_desc=(TextView) findViewById(R.id.news_desc);
        read_more=(TextView) findViewById(R.id.read_more);

        read_more.setOnClickListener(this);


        Intent intent=getIntent();

        if (intent!=null) {
            String title=intent.getStringExtra("title");
            String date=intent.getStringExtra("date");
            String desc=intent.getStringExtra("desc");
            String image=intent.getStringExtra("image");
            more_url=intent.getStringExtra("more_url");
            news_date.setText(date);
            news_title.setText(title);
            news_desc.setText(desc);
            Picasso.get().load(image).into(news_image);
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.read_more) {
            if (more_url!=null) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(more_url));
                startActivity(browserIntent);
            }
        }
    }
}
