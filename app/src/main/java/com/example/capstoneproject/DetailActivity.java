package com.example.capstoneproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class DetailActivity extends AppCompatActivity {

    private ImageView hotelDetail;
    private TextView title;
    private TextView alamatHotel;
    private TextView koordinat;
    private TextView noTelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        toolBarLayout.setTitle(getIntent().getStringExtra("title"));
        alamatHotel.setText(getIntent().getStringExtra("alamat_hotel"));
        koordinat.setText(getIntent().getStringExtra("koordinat"));
        noTelp.setText(getIntent().getStringExtra("no_telp"));
        Glide.with(getApplicationContext()).load(getIntent().getStringExtra("gambarURL")).error(R.drawable.ic_launcher_background)
                .override(512,512)
                .into(hotelDetail);//menampilkan gambar

    }

    private void initView() {
        hotelDetail = findViewById(R.id.hotel_detail);
        title = findViewById(R.id.title);
        alamatHotel = findViewById(R.id.alamat_hotel);
        koordinat = findViewById(R.id.koordinat);
        noTelp = findViewById(R.id.no_telp);
    }

    public void pindah(View view) {
        Intent intent = new Intent(DetailActivity.this, FormPemesanan.class);
        startActivity(intent);
    }
}