package com.example.tintuc24version2.NewsApdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.tintuc24version2.Common.ISO8601Parse;
import com.example.tintuc24version2.Models.Article;
import com.example.tintuc24version2.R;
import com.example.tintuc24version2.Utils;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.github.curioustechizen.ago.RelativeTimeTextView;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<Article>  articles;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public NewsAdapter(List<Article> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_news,parent,false);

        return new NewsViewHolder(view, onItemClickListener);

    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holders, int position) {

        final NewsViewHolder holder = holders;
        Article  model = articles.get(position);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawableColor());
        requestOptions.error(Utils.getRandomDrawableColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();

        Glide.with(context).load(model.getUrlToImage())
                           .apply(requestOptions)
                           .listener(new RequestListener<Drawable>() {
                               @Override
                               public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                   holder.progressBar.setVisibility(View.GONE);

                                   return false;
                               }

                               @Override
                               public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                   holder.progressBar.setVisibility(View.GONE);
                                   return false;
                               }
                           })
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(holder.imageSource);

        holder.title.setText(model.getTitle());
        holder.desc.setText(model.getDescription());
        holder.source.setText(model.getSource().getName());
        holder.publishedAd.setText(Utils.DateFormat(model.getPublishedAt()));
        if(model.getPublishedAt() != null) {
            Date date = null;
            try {
                date = ISO8601Parse.parse(model.getPublishedAt());
            } catch (ParseException ex) {
                ex.printStackTrace();

            }

            assert date != null;
            holder.time.setReferenceTime(date.getTime());
        }
        holder.author.setText(model.getAuthor());
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void setItemOnclickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title, desc,author, publishedAd, source;
        KenBurnsView imageSource;
        ProgressBar progressBar;
        RelativeTimeTextView time;
        OnItemClickListener onItemClickListener;

        NewsViewHolder(@NonNull View itemView, OnItemClickListener mOnItemClickListener) {
            super(itemView);

            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            author = itemView.findViewById(R.id.author);
            publishedAd = itemView.findViewById(R.id.publishedAt);
            source = itemView.findViewById(R.id.source);
            time = itemView.findViewById(R.id.time);
            imageSource = itemView.findViewById(R.id.source_image);
            progressBar = itemView.findViewById(R.id.progress_load_image);
            this.onItemClickListener = mOnItemClickListener;

        }

        @Override
        public void onClick(View view) {

            onItemClickListener.onClick(view,getAdapterPosition(),false);

        }
    }


}
