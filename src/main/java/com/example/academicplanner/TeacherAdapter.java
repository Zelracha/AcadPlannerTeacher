package com.example.academicplanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList<String> teacher_id, teacher_name, teacher_surname, teacher_phone, teacher_email, teacher_pic_blob;

    public TeacherAdapter(Activity activity, Context context, ArrayList<String> teacher_id, ArrayList<String> teacher_name,
                          ArrayList<String> teacher_surname, ArrayList<String> teacher_phone, ArrayList<String> teacher_email,
                          ArrayList<String> teacher_pic_blob){
        this.activity = activity;
        this.context = context;
        this.teacher_id = teacher_id;
        this.teacher_name = teacher_name;
        this.teacher_surname = teacher_surname;
        this.teacher_phone = teacher_phone;
        this.teacher_email = teacher_email;
        this.teacher_pic_blob = teacher_pic_blob;
    }

    @NonNull
    @Override
    public TeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.teachers_list, parent, false);
        return new TeacherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewHolder holder, final int position) {
        holder.teacher_name_txt.setText(String.valueOf(teacher_name.get(position)));
        holder.teacher_surname_txt.setText(String.valueOf(teacher_surname.get(position)));

        String selectedImageUri = (String) teacher_pic_blob.get(position);
        if (selectedImageUri != null && !selectedImageUri.isEmpty()) {
            holder.teacher_pic_img.setImageURI(Uri.parse(selectedImageUri));
        } else {
            holder.teacher_pic_img.setImageResource(R.drawable.user);
        }

        holder.teacherLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Intent i = new Intent(context, TeachersUpdate.class);
                i.putExtra("id", String.valueOf(teacher_id.get(position)));
                i.putExtra("name", String.valueOf(teacher_name.get(position)));
                i.putExtra("surname", String.valueOf(teacher_surname.get(position)));
                String phone = teacher_phone.get(position) == null ? null : String.valueOf(teacher_phone.get(position));
                i.putExtra("phone", phone);
                String email = teacher_email.get(position) == null ? null : String.valueOf(teacher_email.get(position));
                i.putExtra("email", email);
                i.putExtra("image", (String) teacher_pic_blob.get(position));
                activity.startActivityForResult(i,1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return teacher_id.size();
    }

    class TeacherViewHolder extends RecyclerView.ViewHolder{

        ImageView teacher_pic_img;
        TextView teacher_name_txt, teacher_surname_txt;
        CardView teacherLayout;

        TeacherViewHolder(@NonNull View itemView) {
            super(itemView);
            teacher_name_txt = itemView.findViewById(R.id.teacher_name_txt);
            teacher_surname_txt = itemView.findViewById(R.id.teacher_surname_txt);
            teacher_pic_img = itemView.findViewById(R.id.teacher_pic_blob);
            teacherLayout = itemView.findViewById(R.id.teacherLayout);
        }
    }
}