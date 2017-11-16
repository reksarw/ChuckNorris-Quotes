package com.example.lbd.retrofit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class KategoriActivity extends AppCompatActivity {

    private ListView listKategori;
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);

        Button btnClick = findViewById(R.id.btnRandom);

        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RandomQuoteActivity.class);

                getApplicationContext().startActivity(intent);
            }
        });

        listKategori = findViewById(R.id.listKategori);

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.list_categories));
        listKategori.setAdapter(arrayAdapter);

        listKategori.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(KategoriActivity.this, CategoryQuoteActivity.class);
                intent.putExtra("kategori", getResources().getStringArray(R.array.list_categories)[position]);

                getApplicationContext().startActivity(intent);
            }
        });
    }
}
