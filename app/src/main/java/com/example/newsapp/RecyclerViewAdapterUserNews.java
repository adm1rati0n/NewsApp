package com.example.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapterUserNews extends RecyclerView.Adapter<RecyclerViewAdapterUserNews.ViewHolderUser> {
    private final LayoutInflater inflater;
    private final List<NewsModel> models;
    DatabaseHelper databaseHelper;
    public RecyclerViewAdapterUserNews(Context context, List<NewsModel> models){
        inflater = LayoutInflater.from(context);
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolderUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.news_view_model, parent, false);
        databaseHelper = new DatabaseHelper(parent.getContext());
        return new ViewHolderUser(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderUser holder, int position) {
        NewsModel model = models.get(position);

        holder.txtHeader.setText(model.getHeader());
        holder.txtMainText.setText(model.getMainText());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public static class ViewHolderUser extends RecyclerView.ViewHolder{
        final TextView txtHeader;
        final TextView txtMainText;

        ViewHolderUser(View view) {
            super(view);
            this.txtHeader = view.findViewById(R.id.txtHeaderNews2);
            this.txtMainText = view.findViewById(R.id.txtMainNews2);
        }
    }
}
