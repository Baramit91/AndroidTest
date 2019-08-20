package com.baramit.com.androidtest;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.baramit.com.androidtest.adapters.NumbersRecyclerAdapter;
import com.baramit.com.androidtest.view_models.MainActivityViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel mainActivityViewModel;
    private RecyclerView recyclerView;
    private NumbersRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.mRecView);

        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        mainActivityViewModel.init();

        mainActivityViewModel.getMyNumbers().observe(this, new Observer<List<Integer>>() {
            @Override
            public void onChanged(@Nullable List<Integer> numbers) {
                if (adapter.getNumbers() == null) {
                    if (numbers != null) {
                        adapter.setNumbers(numbers);
                    }
                }

                adapter.notifyDataSetChanged();

            }
        });

        initRecyclerView();

        mainActivityViewModel.makeNumbersRequest();
    }


    private void initRecyclerView() {
        adapter = new NumbersRecyclerAdapter(mainActivityViewModel.getMyNumbers().getValue());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

}
