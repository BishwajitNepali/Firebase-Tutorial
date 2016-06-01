package com.thanglastudio.meroserofero.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.thanglastudio.meroserofero.Adapter.HealthNewsAdapter;
import com.thanglastudio.meroserofero.Model.HealthNews;
import com.thanglastudio.meroserofero.R;


public class NewsDetailActivity extends AppCompatActivity implements HealthNewsAdapter.Callback{
    TextView news_title;
    TextView news_content;
    TextView related_news;
    HealthNewsAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        //Data coming from MainActivity
        String title=getIntent().getStringExtra("title");
        String content=getIntent().getStringExtra("content");
        RecyclerView relNewsRecyclerview=(RecyclerView)findViewById(R.id.related_newsRecyclerView);

        news_title=(TextView)findViewById(R.id.news_title);
        news_content=(TextView)findViewById(R.id.news_content);
        related_news=(TextView)findViewById(R.id.related_news);
        mAdapter=new HealthNewsAdapter(this,this);

        relNewsRecyclerview.setLayoutManager(new GridLayoutManager(this,2));

        relNewsRecyclerview.setHasFixedSize(true);

        relNewsRecyclerview.setAdapter(mAdapter);
        relNewsRecyclerview.setNestedScrollingEnabled(false);

        relNewsRecyclerview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


//        Typeface face= Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/Astrud.ttf");
//        related_news.setTypeface(face);

        news_title.setText(title);
        news_content.setText(content);
    }

    @Override
    public void onEdit(HealthNews healthNews) {

    }

    @Override
    public void onItemClick(HealthNews healthNews) {

        Intent intent= new Intent(NewsDetailActivity.this,NewsDetailActivity.class);

        String title=healthNews.getNews_title();
        String content=healthNews.getNews_content();

        intent.putExtra("title",title);
        intent.putExtra("content",content);
        startActivity(intent);

    }
}
