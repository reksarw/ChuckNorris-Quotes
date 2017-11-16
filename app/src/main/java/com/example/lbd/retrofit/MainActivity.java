package com.example.lbd.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lbd.retrofit.generator.ServiceGenerator;
import com.example.lbd.retrofit.models.ChuckNorrisQuotes;
import com.example.lbd.retrofit.services.ChuckService;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ChuckService chuckService;
    TextView textResult;
    Button btnReload;
    ImageView imageResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chuckService = ServiceGenerator.createService(ChuckService.class);

        textResult = findViewById(R.id.textResult);
        imageResult = findViewById(R.id.imageResult);
        btnReload = findViewById(R.id.reload);

        android.view.ViewGroup.LayoutParams layoutParams = imageResult.getLayoutParams();
        layoutParams.width = 180;
        layoutParams.height = 180;
        imageResult.setLayoutParams(layoutParams);

        textResult.setText("loading quote...");

        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onResume();
            }
        });
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
        Call<ChuckNorrisQuotes> call = chuckService.getQuote();

        call.enqueue(new Callback<ChuckNorrisQuotes>() {
            @Override
            public void onResponse(Call<ChuckNorrisQuotes> call, Response<ChuckNorrisQuotes> response) {
                textResult.setText(response.body().getValue());
                Picasso.with(getApplicationContext())
                        .load(response.body().getIconUrl())
                        .placeholder(R.mipmap.ic_launcher)
                        .into(imageResult);
            }

            @Override
            public void onFailure(Call<ChuckNorrisQuotes> call, Throwable t) {
                textResult.setText(t.getMessage());
            }
        });
    }
}
