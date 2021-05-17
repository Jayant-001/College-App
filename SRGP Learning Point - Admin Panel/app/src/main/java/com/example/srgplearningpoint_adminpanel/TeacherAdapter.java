package com.example.srgplearningpoint_adminpanel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewAdapter> {

    private List<TeacherData> list;
    private Context context;
    private String category;

    public TeacherAdapter(List<TeacherData> list, Context context, String category) {
        this.list = list;
        this.context = context;
        this.category = category;
    }

    @NonNull
    @Override
    public TeacherViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.teacher_data, parent, false);
        return new TeacherViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewAdapter holder, int position) {
        TeacherData item = list.get(position);
        holder.name.setText(item.getName());
        holder.email.setText(item.getEmail());
        holder.contact.setText(item.getContact());
        holder.qualification.setText(item.getQualification());
        try {
            Picasso.get().load(item.getImage()).into(holder.teacherImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Update Teacher", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, UpdateTeacher.class);
                intent.putExtra("name", item.getName());
                intent.putExtra("email", item.getEmail());
                intent.putExtra("contact", item.getContact());
                intent.putExtra("qualification", item.getQualification());
                intent.putExtra("image", item.getImage());
                intent.putExtra("key", item.getKey());
                intent.putExtra("category", category);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class TeacherViewAdapter extends RecyclerView.ViewHolder {

        private TextView name, email, contact, qualification;
        private Button updateBtn;
        private ImageView teacherImage;

        public TeacherViewAdapter(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.teacherName);
            email = itemView.findViewById(R.id.teacherEmail);
            contact = itemView.findViewById(R.id.teacherContact);
            qualification = itemView.findViewById(R.id.teacherQualification);

            updateBtn = itemView.findViewById(R.id.updateBtn2);
            teacherImage = itemView.findViewById(R.id.teacherImage);


        }
    }
}
