package com.example.lbd.retrofit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lbd.retrofit.generator.ServiceGenerator;
import com.example.lbd.retrofit.services.ChuckService;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KategoriActivity extends AppCompatActivity {

    private ChuckService chuckService;
    private ListView listKategori;
    private ArrayAdapter arrayAdapter;
    private ArrayList<String> listCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);

        chuckService = ServiceGenerator.createService(ChuckService.class);

        Button btnClick = findViewById(R.id.btnRandom);

        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RandomQuoteActivity.class);

                getApplicationContext().startActivity(intent);
            }
        });

        this.listKategori = findViewById(R.id.listKategori);

        this.listCategories = new ArrayList<>();

        Call<List<String>> call = chuckService.getCategories();

        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if ( response.body().size() > 0 )
                {
                    for ( int i = 0 ; i < response.body().size(); i++)
                    {
                        String namaKategori = response.body().get(i);
                        String kategori = namaKategori.substring(0, 1).toUpperCase() + namaKategori.substring(1);

                        listCategories.add(kategori);
                    }
                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(KategoriActivity.this, "Error: " + t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1 , listCategories);
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
