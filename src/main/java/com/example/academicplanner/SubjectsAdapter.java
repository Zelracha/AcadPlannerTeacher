package com.example.academicplanner;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.academicplanner.ui.subjects.SubjectsFragment;
import java.util.List;

public class SubjectsAdapter extends RecyclerView.Adapter<SubjectsAdapter.SubjectViewHolder> {

    private List<SubjectsFragment.Subject> subjectsList;

    public SubjectsAdapter(List<SubjectsFragment.Subject> subjectsList) {
        this.subjectsList = subjectsList;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subjects_list, parent, false);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        SubjectsFragment.Subject subject = subjectsList.get(position);
        holder.subjectNameTxt.setText(subject.getName());

        holder.subjectLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), SubjectsUpdate.class);
                i.putExtra("subjectId", subject.getId());
                i.putExtra("subjectName", subject.getName());
                i.putExtra("subjectNote", subject.getNote());
                i.putExtra("subjectColor", String.valueOf(subject.getColor()));
                i.putExtra("selectedTeachers", subject.getTeachers());
                i.putExtra("selectedTerms", subject.getTerms());
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjectsList.size();
    }

    public static class SubjectViewHolder extends RecyclerView.ViewHolder {

        TextView subjectNameTxt;
        CardView subjectLayout;

        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectNameTxt = itemView.findViewById(R.id.subject_name_txt);
            subjectLayout = itemView.findViewById(R.id.subjectLayout);
        }
    }
}