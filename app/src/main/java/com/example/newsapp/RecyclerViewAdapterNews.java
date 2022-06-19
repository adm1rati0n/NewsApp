package com.example.newsapp;

import android.app.AlertDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapterNews extends RecyclerView.Adapter<RecyclerViewAdapterNews.ViewHolder> {
    private final LayoutInflater inflater;
    private final List<com.example.newsapp.NewsModel> models;
    com.example.newsapp.DatabaseHelper databaseHelper;

    public RecyclerViewAdapterNews(Context context, List<com.example.newsapp.NewsModel> models) {
        this.inflater = LayoutInflater.from(context);
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.news_model, parent, false);
        databaseHelper = new com.example.newsapp.DatabaseHelper(parent.getContext());
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        com.example.newsapp.NewsModel model = models.get(position);

        holder.txtHeader.setText(model.getHeader());
        holder.txtMainText.setText(model.getMainText());
        holder.btnEditNews.setOnClickListener(view -> {

            LayoutInflater inflater = LayoutInflater.from(view.getContext());
            View dialog = inflater.inflate(R.layout.edit_news_dialog, null);

            EditText editHeader = dialog.findViewById(R.id.txtEditHeaderDialog);
            EditText editMainText = dialog.findViewById(R.id.txtMainNewsDialog);

            editHeader.setText(model.getHeader());
            editMainText.setText(model.getMainText());

            String txtHeader = editHeader.getText().toString().trim();
            String txtMain = editMainText.getText().toString().trim();

            AlertDialog.Builder editDialog = new AlertDialog.Builder(view.getContext());
            editDialog.setCancelable(true);
            editDialog.setTitle("Изменение новости");

            editDialog.setPositiveButton("Сохранить изменения", ((dialogInterface, i) -> {
                if (txtHeader.isEmpty())
                    editHeader.setError("Обязательное поле!");
                else if (txtMain.isEmpty())
                    editMainText.setError("Это обязательное поле!");
                else {
                    boolean checkUpdateNews = databaseHelper.update(
                            model.getID(),
                            editHeader.getText().toString().trim(),
                            editMainText.getText().toString().trim());
                    if (checkUpdateNews) {
                        Toast.makeText(view.getContext(),
                                "Новость изменена!", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(view.getContext(),
                                "Ошибка при обновлении данных!", Toast.LENGTH_SHORT).show();
                    dialogInterface.cancel();
                }
            }));

            editDialog.setNegativeButton("Отмена", ((dialogInterface, i) -> {
                dialogInterface.cancel();
            }));

            editDialog.setView(dialog);
            editDialog.show();
        });

        holder.btnDeleteNews.setOnClickListener(view -> {
            removeAt(view, position, model.getID());
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView txtHeader;
        final TextView txtMainText;
        final Button btnEditNews;
        final Button btnDeleteNews;

        ViewHolder(View view) {
            super(view);
            this.txtHeader = view.findViewById(R.id.txtHeaderNews);
            this.txtMainText = view.findViewById(R.id.txtMainNews);
            this.btnEditNews = view.findViewById(R.id.btnEditNews);
            this.btnDeleteNews = view.findViewById(R.id.btnDeleteNews);
        }
    }

    private void removeAt(View view, int position, int elementID) {
        SQLiteDatabase db = new com.example.newsapp.DatabaseHelper(view.getContext()).getWritableDatabase();
        db.delete(com.example.newsapp.NewsDBContract.News.TABLE_NAME, com.example.newsapp.NewsDBContract.News._ID + "=" + elementID, null);
        models.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, models.size());
    }
}
