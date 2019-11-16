package com.example.tintuc24version2.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class News {

    @SerializedName("articles")
    @Expose
    private List<Article> article;

    public News(List<Article> article) {
        this.article = article;
    }

    public List<Article> getArticle() {
        return article;
    }

}
