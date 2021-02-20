package com.example.quotesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    TextView tv_id, tv_author, tv_content, tv_tags;
    Button btnViewData;
    ImageView btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_id = findViewById(R.id.tv_id);
        tv_author = findViewById(R.id.tv_author);
        tv_content = findViewById(R.id.tv_content);
        tv_tags = findViewById(R.id.tv_tags);
        btnAdd = findViewById(R.id.btnAdd);
        btnViewData = findViewById(R.id.btnView);

        findViewById(R.id.btn_generate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkTask nt = new NetworkTask();
                nt.execute();
                onRestart();
            }
        });
    }

    class NetworkTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                URL url = new URL("https://api.quotable.io/random");
                InputStream is = url.openStream();
                byte[] buffer = new byte[4096];
                StringBuilder sb = new StringBuilder("");


                while (is.read(buffer) != -1) {
                    sb.append(new String(buffer));
                }

                Log.i("apiresponse", sb.toString());

                try {
                    JSONObject obj = new JSONObject(sb.toString());

                    String _id = obj.getString("_id");
                    publishProgress(_id, 0);

                    String content = obj.getString("content");
                    publishProgress(content, 2);

                    String author = obj.getString("author");
                    publishProgress(author, 1);

                    String tags = obj.getString("tags");
                    publishProgress(tags, 3);


                } catch (JSONException ex) {
                    ex.printStackTrace();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            switch (Integer.parseInt(values[1] + "")) {
                case 0:
                    tv_id.setText("ID: " + values[0].toString());
                    break;
                case 1:
                    tv_author.setText("~ " + values[0].toString());
                    break;
                case 2:
                    tv_content.setText(values[0].toString());
                    break;
                case 3:
                    tv_tags.setText("Tags: " + values[0].toString());
            }


            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String stringName = tv_author.getText().toString();
                    String stringQuotes = tv_content.getText().toString();

                    if (stringName.length() <= 0 || stringQuotes.length() <= 0) {
                        Toast.makeText(MainActivity.this, "Enter All Data", Toast.LENGTH_SHORT).show();
                    } else {
                        DatabaseHelperClass databaseHelperClass = new DatabaseHelperClass(MainActivity.this);
                        QuotesModelClass quotesModelClass = new QuotesModelClass(stringName, stringQuotes);
                        databaseHelperClass.addQuotes(quotesModelClass);
                        Toast.makeText(MainActivity.this, "Add Quotes Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(getIntent());
                    }
                }
            });


            btnViewData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, ViewQuotesActivity.class);
                    startActivity(intent);

                    overridePendingTransition(R.anim.slide_up_in, R.anim.slide_up_out);
                }
            });


        }
    }
}