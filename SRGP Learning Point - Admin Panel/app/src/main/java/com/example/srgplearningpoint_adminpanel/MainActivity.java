package com.example.srgplearningpoint_adminpanel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.card.MaterialCardView;

public class MainActivity extends AppCompatActivity {

    MaterialCardView addNotice, addImage, addPDF, addFaculty, deleteNotice;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addNotice = findViewById(R.id.addNotice);
        addImage = findViewById(R.id.uploadImage);
        addPDF = findViewById(R.id.addBooks);
        addFaculty = findViewById(R.id.addFaculty);
        deleteNotice = findViewById(R.id.deleteNotice);

        addNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UploadNotice.class);
                startActivity(intent);
            }
        });
        
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UploadImage.class);
                startActivity(intent);
            }
        });

        addPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UploadPDF.class);
                startActivity(intent);
            }
        });

        addFaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UpdateFaculty.class);
                startActivity(intent);
            }
        });

        deleteNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DeleteNotice.class);
                startActivity(intent);
            }
        });

    }
}

