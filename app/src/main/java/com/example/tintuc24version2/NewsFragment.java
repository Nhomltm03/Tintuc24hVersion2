package com.example.tintuc24version2;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;

import com.example.tintuc24version2.Models.Article;
import com.example.tintuc24version2.Models.News;
import com.example.tintuc24version2.NewsApdapter.NewsAdapter;
import com.example.tintuc24version2.NewsApdapter.OnItemClickListener;
import com.example.tintuc24version2.api.ApiClient;
import com.example.tintuc24version2.api.ApiInterface;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {

    private static final String API_KEY = "7c988e9136a0403e923f294a123f2995";
    private static final String API_QUERY_SORT_BY = "publishedAt";
    private static final String QUERY_HINT = "Search Latest News...";
    private static final String EXTRA_URL = "url";
    private static final String EXTRA_TITLE = "title";
    private static final String EXTRA_IMG = "img";
    private static final String EXTRA_DATE = "date";
    private static final String EXTRA_SOURCE = "source";
    private static final String EXTRA_AUTHOR = "author";
    private static final String API_QUERY_COUNTRY = "us";
    private RecyclerView recyclerView;
    private List<Article> articles = new ArrayList<>();
    private NewsAdapter newsAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_news, container, false);
        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadJSon("");
            }
        });
        recyclerView = rootView.findViewById(R.id.recycleView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        setHasOptionsMenu(true);
        onLoadingSwipeRefresh("");

        return rootView;
    }
    private void LoadJSon(final String keyword) {
        ApiInterface apiInterface = ApiClient.getAPIClient().create(ApiInterface.class);
        String language = Utils.getLanguage();

        swipeRefreshLayout.setRefreshing(true);

        Call<News> call;

        if (keyword.length() > 0) {
            call = apiInterface.getNewsSearch(keyword, language, API_QUERY_SORT_BY, API_KEY);
        } else {
            call = apiInterface.getNews(API_QUERY_COUNTRY, API_KEY); // because some country code Api have no result

            // set default country us
        }

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(@NotNull Call<News> call, @NotNull Response<News> response) {
                assert response.body() != null;
                if (response.isSuccessful() && response.body().getArticle() != null) {
                    if (!articles.isEmpty()) {
                        articles.clear();
                    }
                    articles = response.body().getArticle();
                    newsAdapter = new NewsAdapter(articles, getActivity());
                    recyclerView.setAdapter(newsAdapter);
                    newsAdapter.notifyDataSetChanged();
                    initListener();
                    swipeRefreshLayout.setRefreshing(false);
                } else {

                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(@NotNull Call<News> call, @NotNull Throwable t) {

                swipeRefreshLayout.setRefreshing(false);

            }
        });
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_seach:
                SearchManager searchManager = (SearchManager) Objects.requireNonNull(getActivity()).getSystemService(Context.SEARCH_SERVICE);
                final SearchView searchView = (SearchView) item.getActionView();
                assert searchManager != null;
                searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
                searchView.setQueryHint(QUERY_HINT);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        if (s.length() > 2) {
                            onLoadingSwipeRefresh(s);
                        }
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        onLoadingSwipeRefresh(newText); // always load when input text change
                        return false;
                    }
                });
                item.getIcon().setVisible(false, false);
                return true;
            case R.id.action_setting:
                Toast.makeText(getActivity(), "Clear call log", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void onLoadingSwipeRefresh(final String keyword) {
        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        LoadJSon(keyword);
                    }
                }
        );
    }

    private void initListener() {
        newsAdapter.setItemOnclickListener(new OnItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {

                Intent intent = new Intent(getActivity(), DetailNewsActivity.class);
                Article article = articles.get(position);
                intent.putExtra(EXTRA_URL, article.getUrl());
                intent.putExtra(EXTRA_TITLE, article.getTitle());
                intent.putExtra(EXTRA_IMG, article.getUrlToImage());
                intent.putExtra(EXTRA_DATE, article.getPublishedAt());
                intent.putExtra(EXTRA_SOURCE, article.getSource().getName());
                intent.putExtra(EXTRA_AUTHOR, article.getAuthor());
                startActivity(intent);

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

}
