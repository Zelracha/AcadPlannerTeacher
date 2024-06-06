package com.example.academicplanner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TermsAdapter extends RecyclerView.Adapter<TermsAdapter.TermViewHolder> {

    private List<String> termList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class TermViewHolder extends RecyclerView.ViewHolder {
        TextView termNumber, termText;
        ImageView deleteButton;

        public TermViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            termNumber = itemView.findViewById(R.id.term_number);
            termText = itemView.findViewById(R.id.term_text);
            deleteButton = itemView.findViewById(R.id.term_delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }

    public TermsAdapter(List<String> termList) {
        this.termList = termList;
    }

    @NonNull
    @Override
    public TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.terms_list, parent, false);
        return new TermViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TermViewHolder holder, int position) {
        holder.termNumber.setText(String.valueOf(position + 1));
        holder.termText.setText(termList.get(position));
    }

    @Override
    public int getItemCount() {
        return termList.size();
    }
}
