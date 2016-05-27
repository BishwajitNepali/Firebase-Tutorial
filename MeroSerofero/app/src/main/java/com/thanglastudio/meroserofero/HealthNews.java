package com.thanglastudio.meroserofero;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by admin on 5/26/16.
 */
public class HealthNews {
    private String news_title;
    private String news_content;

    @JsonIgnore
    private String keys;

    public HealthNews(String news_title, String news_content) {
        this.news_title = news_title;
        this.news_content = news_content;
    }

    public HealthNews() {
        //required empty constructor for Firebase Jackson
    }

    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public String getNews_title() {
        return news_title;
    }

    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }

    public String getNews_content() {
        return news_content;
    }

    public void setNews_content(String news_content) {
        this.news_content = news_content;
    }

    public void setValues(HealthNews newHealthNews) {
        news_content=newHealthNews.news_content;
        news_title=newHealthNews.news_title;
    }
}
