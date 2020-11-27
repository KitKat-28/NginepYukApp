package com.example.capstoneproject.rest;


import com.example.capstoneproject.model.RootHotelModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("purwakarta/hotel")
    Call<RootHotelModel> getData();


}
