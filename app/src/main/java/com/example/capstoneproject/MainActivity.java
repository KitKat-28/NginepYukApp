package com.example.capstoneproject;

import android.app.NotificationManager;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneproject.adapter.HotelAdapter;
import com.example.capstoneproject.model.HotelItem;
import com.example.capstoneproject.model.RootHotelModel;
import com.example.capstoneproject.rest.ApiConfig;
import com.example.capstoneproject.rest.ApiService;
import com.example.capstoneproject.room.HotelViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    private RecyclerView hotel;
    private ArrayList<HotelItem> hotelItems;
    private HotelAdapter hotelAdapter;
    private HotelViewModel mHotelViewModel;
    public static final String EXTRA_MESSAGE =
            "com.example.android.capstoneproject.extra.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Create an instance of the tab layout from the view.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        hotelItems = new ArrayList<>();
        if (haveNetwork()) {
            getData();
        } else if (!haveNetwork()) {
            hotelAdapter = new HotelAdapter(hotelItems, getApplicationContext()); // model memasukkan ke adapter untuk ditampilkan di Recycle vIEW
            hotel.setAdapter(hotelAdapter); //recycler view menghubungkan ke adapter dengan "SET ADAPTER"
            hotel.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            mHotelViewModel.getAllData().observe(this, new Observer<List<HotelItem>>() {
                @Override
                public void onChanged(@Nullable final List<HotelItem> data) {
                    // Update the cached copy of the words in the adapter.
                    hotelAdapter.setData((ArrayList<HotelItem>) data);
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_alarm:
                Intent intent = new Intent(MainActivity.this,
                        AlarmActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_voucer:
                displayToast(getString(R.string.action_status_message));
                return true;
            default:
                // Do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private boolean haveNetwork() {
        boolean have_WIFI = false;
        boolean have_MobileData = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
        for (NetworkInfo info : networkInfos) {
            if (info.getTypeName().equalsIgnoreCase("WIFI"))
                if (info.isConnected()) have_WIFI = true;

            if (info.getTypeName().equalsIgnoreCase("MOBILE"))
                if (info.isConnected()) have_MobileData = true;
        }
        return have_WIFI || have_MobileData;
    }

    private void initView() {
        hotel = findViewById(R.id.hotel);
        mHotelViewModel = ViewModelProviders.of(this).get(HotelViewModel.class);
    }


    private void getData() {
        ApiService apiService = ApiConfig.getApiService();
        apiService.getData()
                .enqueue(new Callback<RootHotelModel>() {
                    @Override
                    public void onResponse(Call<RootHotelModel> call, Response<RootHotelModel> response) {
                        if(response.isSuccessful()){
                            hotelItems = response.body().getHotel(); //ini untuk mengambil data dari JSON lalu ditampung ke model


                            mHotelViewModel.deleteAll();

                            // Menyimpan data ke database || Save data
                            for (int i = 0; i < hotelItems.size(); i++) {
                                Integer id = hotelItems.get(i).getId();
                                Integer kode_hotel = hotelItems.get(i).getKode_hotel();
                                String nama = hotelItems.get(i).getNama();
                                String alamat = hotelItems.get(i).getAlamat();
                                String kordinat = hotelItems.get(i).getKordinat();
                                String no_telp = hotelItems.get(i).getNomorTelp();
                                String gambar_url = hotelItems.get(i).getGambarUrl();


                                HotelItem nginepyuk= new HotelItem();
                                nginepyuk.setId(id);
                                nginepyuk.setKode_hotel(kode_hotel);
                                nginepyuk.setNama(nama);
                                nginepyuk.setAlamat(alamat);
                                nginepyuk.setKordinat(kordinat);
                                nginepyuk.setNomorTelp(no_telp);
                                nginepyuk.setGambarUrl(gambar_url);
                                mHotelViewModel.insert(nginepyuk);
                            }

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
}