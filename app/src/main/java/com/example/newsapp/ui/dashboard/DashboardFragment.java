package com.example.newsapp.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.newsapp.DatabaseHelper;
import com.example.newsapp.R;
import com.example.newsapp.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    Button btnAddNews;
    EditText editHeader;
    EditText editMain;
    DatabaseHelper databaseHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btnAddNews = binding.btnAddNews;
        editHeader = binding.editHeader;
        editMain = binding.editMainText;
        databaseHelper = new DatabaseHelper(getContext());

        btnAddNews.setOnClickListener(view -> {
            if (isValid()) {
                boolean checkInsertNews = databaseHelper.insertNews(
                        editHeader.getText().toString().trim(),
                        editMain.getText().toString().trim());
                if (checkInsertNews) {
                    Toast.makeText(getContext(),
                            "Новость добавлена!", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getContext(),
                            "Ошибка при добавлении записи!", Toast.LENGTH_SHORT).show();

            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private boolean isValid() {
        String error = "Это обязательное поле!";
        boolean isValid = false;

        if (editMain.getText().toString().isEmpty())
            editMain.setError(error);
        else if (editHeader.getText().toString().isEmpty())
            editHeader.setError(error);
        else isValid = true;

        return isValid;
    }
}