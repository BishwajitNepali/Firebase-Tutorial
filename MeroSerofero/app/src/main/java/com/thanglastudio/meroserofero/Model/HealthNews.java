package com.thanglastudio.meroserofero.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by admin on 5/26/16.
 */
public class HealthNews {
    private String news_title;
    private String news_content;
    private String news_image_url;

    @JsonIgnore
    private String keys;

    public HealthNews(String news_title, String news_content, String news_image_url) {
        this.news_title = news_title;
        this.news_content = news_content;
        this.news_image_url = news_image_url;
    }

    public HealthNews() {
        //required empty constructor for Firebase Jackson
    }

    public String getNews_image_url() {
        return news_image_url;
    }

    public void setNews_image_url(String news_image_url) {
        this.news_image_url = news_image_url;
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
        news_image_url=newHealthNews.news_image_url;
    }
}
