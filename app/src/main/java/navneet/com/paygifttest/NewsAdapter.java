package navneet.com.paygifttest;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import navneet.com.paygifttest.model.Article;

/**
 * Created by Navneet Krishna on 30/07/19.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Article> articles;

    public NewsAdapter(Context mContext, ArrayList<Article> articles) {
        this.mContext=mContext;
        this.articles=articles;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_list_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder viewHolder, int i) {

        final Article article=articles.get(i);

        final String date=splitDate(article.getPublishedAt());

        viewHolder.title.setText(article.getTitle());
        viewHolder.description.setText(article.getTitle());
        viewHolder.date_time.setText(date);



        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,NewsDetailsActivity.class);
                intent.putExtra("title",article.getTitle());
                intent.putExtra("desc",article.getDescription());
                intent.putExtra("date",date);
                intent.putExtra("image",article.getUrlToImage());
                intent.putExtra("more_url",article.getUrl());
                mContext.startActivity(intent);
            }
        });
    }

    private String splitDate(String date) {
        String dateSplit[]=date.split("T");
        return dateSplit[0];
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title,description,date_time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.title);
            description=(TextView)itemView.findViewById(R.id.desc);
            date_time=(TextView)itemView.findViewById(R.id.date_time);
        }
    }

    public void addImages(List<Article> articles_list) {
        for (Article article:articles_list) {
            articles.add(article);
        }
        notifyDataSetChanged();
    }
}
