package com.elazizi.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.elazizi.myapplication.R;
import com.elazizi.myapplication.in.Employe.ListEmploye;
import com.elazizi.myapplication.in.Service.ListService;

public class MainActivity extends AppCompatActivity {

    private Button Prof, special;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Prof = findViewById(R.id.prof);
        Prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListEmploye.class);
                startActivity(intent);
            }
        });
        special = findViewById(R.id.special);
        special.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListService.class);
                startActivity(intent);
            }
        });


    }}