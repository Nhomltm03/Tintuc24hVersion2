package com.example.tintuc24version2.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class News {
    @SerializedName("status")
    @Expose
    private  String status;

    @SerializedName("totalResult")
    @Expose
    private  String totalResult;

    @SerializedName("articles")
    @Expose
    private List<Article> aritcle;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(String totalResult) {
        this.totalResult = totalResult;
    }

    public List<Article> getAritcle() {
        return aritcle;
    }

    public void setAritcle(List<Article> aritcle) {
        this.aritcle = aritcle;
    }
}
