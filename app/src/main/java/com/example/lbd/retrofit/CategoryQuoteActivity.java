package com.example.lbd.retrofit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lbd.retrofit.generator.ServiceGenerator;
import com.example.lbd.retrofit.models.ChuckNorrisCategoryQuote;
import com.example.lbd.retrofit.models.ChuckNorrisQuotes;
import com.example.lbd.retrofit.services.ChuckService;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryQuoteActivity extends AppCompatActivity {

    ChuckService chuckService;
    TextView textQuote , textKategori;
    ImageView imageQuote;
    Button btnRandom;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_quote);

        chuckService = ServiceGenerator.createService(ChuckService.class);

        this.initComponents();
    }

    private void initComponents()
    {
        textQuote = findViewById(R.id.textResult);
        textKategori = findViewById(R.id.kategori);
        imageQuote = findViewById(R.id.imageResult);
        btnRandom = findViewById(R.id.reload);

        intent = getIntent();

        textQuote.setText("loading quote...");
        textKategori.setText("Kategori: " + intent.getStringExtra("kategori"));

        btnRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setButton("loading..." , false);
                onResume();
            }
        });

        /** Set Image Programmatically */
        android.view.ViewGroup.LayoutParams layoutParams = imageQuote.getLayoutParams();
        layoutParams.width = 180;
        layoutParams.height = 180;
        imageQuote.setLayoutParams(layoutParams);

    }

    @Override
    protected void onResume() {
        resultData();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    private void resultData()
    {
        String intentKategori = intent.getStringExtra("kategori").toLowerCase();
        Call<ChuckNorrisCategoryQuote> call = chuckService.getCategoryQuote("" + intentKategori);

        call.enqueue(new Callback<ChuckNorrisCategoryQuote>() {
            @Override
            public void onResponse(Call<ChuckNorrisCategoryQuote> call, Response<ChuckNorrisCategoryQuote> response) {
                setButton("" + getResources().getString(R.string.reload), true);
                textQuote.setText(response.body().getValue());
                Picasso.with(getApplicationContext())
                        .load(response.body().getIconUrl())
                        .placeholder(R.mipmap.ic_launcher)
                        .into(imageQuote);
            }

            @Override
            public void onFailure(Call<ChuckNorrisCategoryQuote> call, Throwable t) {
                textQuote.setText(t.getMessage());
            }
        });
    }

    private void setButton(String message , Boolean isEnabled)
    {
        btnRandom.setText(message);
        btnRandom.setEnabled(isEnabled);
    }
}
