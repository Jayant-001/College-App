package com.example.srgplearningpoint_adminpanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class UpdateFaculty extends AppCompatActivity {

    FloatingActionButton fab;
    private RecyclerView csBranch, itBranch, ecBranch, icBranch;
    private LinearLayout csNoData, itNoData, ecNoData, icNoData;
    private List<TeacherData> list1, list2, list3, list4;
    private TeacherAdapter adapter;

    private DatabaseReference reference, dbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_faculty);

        csBranch = findViewById(R.id.cseBranch);
        itBranch = findViewById(R.id.itBranch);
        ecBranch = findViewById(R.id.ecBranch);
        icBranch = findViewById(R.id.icBranch);

        csNoData = findViewById(R.id.csNoData);
        itNoData = findViewById(R.id.itNoData);
        ecNoData = findViewById(R.id.ecNoData);
        icNoData = findViewById(R.id.icNoData);

        reference = FirebaseDatabase.getInstance().getReference().child("teacher");

        csBranch();
        itBranch();
        ecBranch();
        icBranch();

        fab = findViewById(R.id.floatingBtn);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UpdateFaculty.this, AddTeacher.class));
            }
        });
    }

    private void csBranch() {
        dbRef = reference.child("Computer Science");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1 = new ArrayList<>();
                if(!snapshot.exists()) {
                    csNoData.setVisibility(View.VISIBLE);
                    csBranch.setVisibility(View.GONE);
                } else {
                    csNoData.setVisibility(View.GONE);
                    csBranch.setVisibility(View.VISIBLE);

                    for (DataSnapshot ds: snapshot.getChildren()) {
                        TeacherData data = ds.getValue(TeacherData.class);
                        list1.add(data);
                    }
                    csBranch.setHasFixedSize(true);
                    csBranch.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    adapter = new TeacherAdapter(list1, UpdateFaculty.this, "Computer Science");
                    csBranch.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void itBranch() {
        dbRef = reference.child("Information Technology");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2 = new ArrayList<>();
                if(!snapshot.exists()) {
                    itNoData.setVisibility(View.VISIBLE);
                    itBranch.setVisibility(View.GONE);
                } else {
                    itNoData.setVisibility(View.GONE);
                    itBranch.setVisibility(View.VISIBLE);

                    for (DataSnapshot ds: snapshot.getChildren()) {
                        TeacherData data = ds.getValue(TeacherData.class);
                        list2.add(data);
                    }
                    itBranch.setHasFixedSize(true);
                    itBranch.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    adapter = new TeacherAdapter(list2, UpdateFaculty.this, "Information Technology");
                    itBranch.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ecBranch() {
        dbRef = reference.child("Electronics");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list3 = new ArrayList<>();
                if(!snapshot.exists()) {
                    ecNoData.setVisibility(View.VISIBLE);
                    ecBranch.setVisibility(View.GONE);
                } else {
                    ecNoData.setVisibility(View.GONE);
                    ecBranch.setVisibility(View.VISIBLE);

                    for (DataSnapshot ds: snapshot.getChildren()) {
                        TeacherData data = ds.getValue(TeacherData.class);
                        list3.add(data);
                    }
                    ecBranch.setHasFixedSize(true);
                    ecBranch.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    adapter = new TeacherAdapter(list3, UpdateFaculty.this, "Electronics");
                    ecBranch.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void icBranch() {
        dbRef = reference.child("Instrumentation and Control");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list4 = new ArrayList<>();
                if(!snapshot.exists()) {
                    icNoData.setVisibility(View.VISIBLE);
                    icBranch.setVisibility(View.GONE);
                } else {
                    icNoData.setVisibility(View.GONE);
                    icBranch.setVisibility(View.VISIBLE);

                    for (DataSnapshot ds: snapshot.getChildren()) {
                        TeacherData data = ds.getValue(TeacherData.class);
                        list4.add(data);
                    }
                    icBranch.setHasFixedSize(true);
                    icBranch.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    adapter = new TeacherAdapter(list4, UpdateFaculty.this, "Instrumentation and Control");
                    icBranch.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}