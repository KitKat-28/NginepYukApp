package com.example.capstoneproject;

import android.os.Bundle;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneproject.adapter.HotelAdapter;
import com.example.capstoneproject.model.HotelItem;
import com.example.capstoneproject.model.RootHotelModel;
import com.example.capstoneproject.rest.ApiConfig;
import com.example.capstoneproject.rest.ApiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView hotel;
    private ArrayList<HotelItem> hotelItems;
    private HotelAdapter hotelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Create an instance of the tab layout from the view.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        hotelItems = new ArrayList<>();
        getData();

    }

    private void getData() {
        ApiService apiService = ApiConfig.getApiService();
        apiService.getData()
                .enqueue(new Callback<RootHotelModel>() {
                    @Override
                    public void onResponse(Call<RootHotelModel> call, Response<RootHotelModel> response) {
                        if(response.isSuccessful()){
                            hotelItems = response.body().getHotel(); //ini untuk mengambil data dari JSON lalu ditampung ke model
                            hotelAdapter = new HotelAdapter(hotelItems, getApplicationContext()); // model memasukkan ke adapter untuk ditampilkan di Recycle vIEW
                            hotelAdapter.notifyDataSetChanged(); //adapter akan mengetahui apabila ada data baru
                            hotel.setAdapter(hotelAdapter); //recycler view menghubungkan ke adapter dengan "SET ADAPTER"
                            hotel.setLayoutManager(new LinearLayoutManager(getApplicationContext())); //recycler view dapat berubah layoutnya bisa berubah grid maupun stargredd
                        }

                    }

                    @Override
                    public void onFailure(Call<RootHotelModel> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void initView() {
        hotel = findViewById(R.id.hotel);
    }
}