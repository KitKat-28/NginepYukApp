package com.example.capstoneproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class FormPemesanan extends AppCompatActivity {
    TextView tglCheckin, tglCheckout;
    DatePickerDialog pickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pemesanan);

        tglCheckin = findViewById(R.id.tanggalCheckin);
        tglCheckout = findViewById(R.id.tanggalCheckout);

        tglCheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                pickerDialog = new DatePickerDialog(FormPemesanan.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int years, int months, int days) {
                        tglCheckin.setText(days + "/" + (months+1) + "/" + years);
                    }
                }, year, month, day);
                pickerDialog.show();
            }
        });

        tglCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                pickerDialog = new DatePickerDialog(FormPemesanan.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int years, int months, int days) {
                        tglCheckout.setText(days + "/" + (months+1) + "/" + years);
                    }
                }, year, month, day);
                pickerDialog.show();
            }
        });
    }

    public void Pesan(View view) {
        Toast toast = Toast.makeText(this,R.string.toast_message,Toast.LENGTH_SHORT);
        toast.show();
    }

    public void Kembali(View view) {
        Intent intent = new Intent(FormPemesanan.this, MainActivity.class);
        startActivity(intent);
    }
}