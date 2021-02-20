package com.example.quotesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class ViewQuotesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_quotes);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        DatabaseHelperClass databaseHelperClass = new DatabaseHelperClass(this);
        List<QuotesModelClass> quotesModelClasses = databaseHelperClass.getQuotesList();

        if (quotesModelClasses.size() > 0){
            QuotesAdapterClass quotesadapterclass = new QuotesAdapterClass(quotesModelClasses, ViewQuotesActivity.this);
            recyclerView.setAdapter(quotesadapterclass);
        }else {
            Toast.makeText(this, "There is no quotes in the database", Toast.LENGTH_SHORT).show();
        }




    }
}
