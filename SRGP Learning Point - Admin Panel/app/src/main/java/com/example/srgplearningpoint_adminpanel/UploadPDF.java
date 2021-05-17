package com.example.srgplearningpoint_adminpanel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class UploadPDF extends AppCompatActivity {

    MaterialCardView addPdf;
    EditText pdf_title;
    MaterialButton uploadPdfButton;

    private final int REQ = 1;
    private Uri pdfData;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private ProgressDialog pd;
    private TextView pdfNameTextView;
    private String pdfName, title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_p_d_f);


        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        pd = new ProgressDialog(this);

        addPdf = findViewById(R.id.addPdf);
        pdf_title = findViewById(R.id.pdf_title);
        uploadPdfButton = findViewById(R.id.upload_pdf_button);
        pdfNameTextView = findViewById(R.id.pdfName);

        addPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("application/pdf"); // if not working type '*' in double quote instead of pdf/docs/ppt
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select pdf"), REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ && resultCode == RESULT_OK) {
            pdfData = data.getData();

            if(pdfData.toString().startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = UploadPDF.this.getContentResolver().query(pdfData, null, null, null, null);
                    if(cursor != null && cursor.moveToFirst()) {
                        pdfName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if(pdfData.toString().startsWith("file://")) {
                pdfName = new File(pdfData.toString()).getName();
            }

            pdfNameTextView.setText(pdfName);
//            Toast.makeText(this, "" + pdfData, Toast.LENGTH_SHORT).show();
        }

    uploadPdfButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
             title = pdf_title.getText().toString();
//             if(title.isEmpty()) {
            if(pdf_title.getText().toString().isEmpty()) {
                 pdf_title.setError("Empty");
                 pdf_title.requestFocus();
             } else if (pdfData == null ) {
                 Toast.makeText(UploadPDF.this, "Please select a PDF file", Toast.LENGTH_SHORT).show();
             } else {
                 uploadPDF();
             }
        }
    });


    }

    private void uploadPDF() {
        pd.setTitle("Please wait...");
        pd.setMessage("Uploading Pdf...");
        pd.show();
        StorageReference reference = storageReference.child("pdf/" + pdfName + " : " + System.currentTimeMillis() + ".pdf");
        reference.putFile(pdfData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri uri = uriTask.getResult();
                uploadData(String.valueOf(uri));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadPDF.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadData(String valueOf) {
        String uniqueKey  = databaseReference.child("pdf").push().getKey();

        HashMap data = new HashMap();
        data.put("pdfTitle", title);
        data.put("pdfUrl", valueOf);

        databaseReference.child("pdf").child(uniqueKey).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                Toast.makeText(UploadPDF.this, "Pdf uploaded successfully", Toast.LENGTH_SHORT).show();
                pdf_title.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadPDF.this, "Failed to upload pdf", Toast.LENGTH_SHORT).show();
            }
        });
    }
}