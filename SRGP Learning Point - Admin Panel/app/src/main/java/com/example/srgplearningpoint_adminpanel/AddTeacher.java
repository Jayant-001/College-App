package com.example.srgplearningpoint_adminpanel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddTeacher extends AppCompatActivity {

    private ImageView teacherImage;
    private EditText teacherName,  teacherEmail,  teacherContact,  teacherQualification;
    private Spinner teacherCategory;
    private Button addTeacherButton;

    private ProgressDialog pd;

    private StorageReference storageReference;
    private DatabaseReference reference, dbRef;


    private final int REQ = 1;
    private Bitmap bitmap = null;
    private String category;

    private String name, email, number, qualification, downloadUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);

        teacherImage = findViewById(R.id.teacherImage);
        teacherName = findViewById(R.id.teacher_name);
        teacherEmail = findViewById(R.id.teacher_email);
        teacherContact = findViewById(R.id.teacher_contact);
        teacherQualification = findViewById(R.id.teacher_qualification);
        teacherCategory = findViewById(R.id.teacher_categoty);
        addTeacherButton = findViewById(R.id.add_teacher_button);

        pd = new ProgressDialog(this);
        reference = FirebaseDatabase.getInstance().getReference().child("teacher");
        storageReference = FirebaseStorage.getInstance().getReference();

        teacherImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        String[] items = new String[]{"Select Category", "Computer Science", "Information Technology", "Electronics", "Instrumentation and Control",  "Other Branch"};
        teacherCategory.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items));

        teacherCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category = teacherCategory.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        addTeacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidation();
                Log.d("Details", "" + name + email + number + qualification + category);
            }
        });



    }

    private void checkValidation() {
        name = teacherName.getText().toString();
        email = teacherEmail.getText().toString();
        number = teacherContact.getText().toString();
        qualification = teacherQualification.getText().toString();

        if(name.isEmpty()) {
            teacherName.setError("Empty");
            teacherName.requestFocus();
        } else if(email.isEmpty()) {
            teacherEmail.setError("Empty");
            teacherEmail.requestFocus();
        } else if(number.isEmpty()) {
            teacherContact.setError("Empty");
            teacherContact.requestFocus();
        } else if(qualification.isEmpty()) {
            teacherQualification.setError("Empty");
            teacherQualification.requestFocus();
        } else if(category.equals("Select Category")) {
            Toast.makeText(this, "Please Select Category", Toast.LENGTH_SHORT).show();
        } else if(bitmap == null) {
            insertData();
        } else {
            pd.setMessage("Uploading...");
            pd.show();
            uploadImage();
        }
    }

    private void uploadImage() {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] finalImage = baos.toByteArray();
        final StorageReference filePath;
        filePath = storageReference.child("Teachers").child(finalImage+"jpg");
        final UploadTask uploadTask = filePath.putBytes(finalImage);
        uploadTask.addOnCompleteListener(AddTeacher.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()) {
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl = String.valueOf(uri);
                                    insertData();
                                }
                            });
                        }
                    });
                } else {
                    pd.dismiss();
                    Toast.makeText(AddTeacher.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void insertData() {
        dbRef = reference.child(category);
        final String uniqueKey = dbRef.push().getKey();

        TeacherData teacherData = new TeacherData(name, email, number, qualification, downloadUrl, uniqueKey);

        dbRef.child(uniqueKey).setValue(teacherData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                pd.dismiss();
                Toast.makeText(AddTeacher.this, "Teacher Added", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(AddTeacher.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openGallery() {
        Intent selectImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(selectImage, REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            teacherImage.setImageBitmap(bitmap);
        }
    }
}