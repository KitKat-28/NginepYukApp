package com.example.capstoneproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.capstoneproject.DetailActivity;
import com.example.capstoneproject.MainActivity;
import com.example.capstoneproject.R;
import com.example.capstoneproject.model.HotelItem;

import java.util.ArrayList;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.ViewHolder> {
    private ArrayList<HotelItem> hotelItems;
    private Context context;


    public HotelAdapter(ArrayList<HotelItem> hotelItems, Context context) {
        this.hotelItems = hotelItems;
        this.context = context;
    }

    public void setData(ArrayList<HotelItem> data){
        this.hotelItems = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(hotelItems.get(position).getGambarUrl()).error(R.drawable.ic_launcher_background)
                .override(512,512)
                .into(holder.sportsImage);//menampilkan gambar


        holder.title.setText(hotelItems.get(position).getNama());
        holder.idHotel.setText(Integer.toString(hotelItems.get(position).getId()));
        holder.alamatHotel.setText(hotelItems.get(position).getAlamat());
        holder.koordinat.setText(hotelItems.get(position).getKordinat());
        holder.noTelp.setText(hotelItems.get(position).getNomorTelp());
        holder.cvKlik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("gambarURL",hotelItems.get(position).getGambarUrl());
                intent.putExtra("title",hotelItems.get(position).getNama());
                intent.putExtra("alamat_hotel",hotelItems.get(position).getAlamat());
                intent.putExtra("koordinat",hotelItems.get(position).getKordinat());
                intent.putExtra("no_telp",hotelItems.get(position).getNomorTelp());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return hotelItems.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView sportsImage;
        private TextView title;
        private TextView idHotel;
        private TextView alamatHotel;
        private TextView koordinat;
        private TextView noTelp;
        private CardView cvKlik;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sportsImage = itemView.findViewById(R.id.sportsImage);
            title = itemView.findViewById(R.id.title);
            idHotel = itemView.findViewById(R.id.id_hotel);
            alamatHotel = itemView.findViewById(R.id.alamat_hotel);
            koordinat = itemView.findViewById(R.id.koordinat);
            noTelp = itemView.findViewById(R.id.no_telp);
            cvKlik = itemView.findViewById(R.id.cv_klik);
        }
    }
}
