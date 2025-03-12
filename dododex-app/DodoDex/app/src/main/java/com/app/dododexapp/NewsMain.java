package com.app.dododexapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class NewsMain extends AppCompatActivity {
    private List<News> news;
    private NewsList newsList;
    private Activity activity;



    public void setNewsList(NewsList newsList) {
        this.newsList = newsList;
       // NewsAdapter myAdapter = new NewsAdapter(news,activity);
      // recyclerView.setAdapter(myAdapter);
      //  recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private RecyclerView recyclerView;
   // private NewsAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        ActionBar actionBar = getSupportActionBar();
        /**
         * @param actionBar es para la flecha del retun**/
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);




        recyclerView = findViewById(R.id.cell_recicler);
        List<News> news = new ArrayList<>();
        news.add(new News(1,R.drawable.museum,"Exelentes noticias!", "https://museobelasartescoruna.xunta.gal/gl"));
        news.add(new News(2,R.drawable.museo1,"Importante noticia.","https://nationaldinosaurmuseum.com.au/"));
        news.add(new News(3,R.drawable.museo2,"Museo Impresionante!!","https://fernbankmuseum.org/"));
        news.add(new News(4, R.drawable.museo3, "Un lugar para disfrutar","https://naturalhistory.si.edu/"));
        NewsAdapter myAdapter = new NewsAdapter(news, this);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


}




