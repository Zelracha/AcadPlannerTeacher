package com.example.academicplanner;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Terms extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TermsAdapter adapter;
    private List<String> termList;
    private int newlyAddedPosition = -1;
    private Button termback_button;
    private Button applyButton;
    private Button addTermButton;
    private TermsDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        dbHelper = new TermsDBHelper(this);

        recyclerView = findViewById(R.id.terms_recyclerView);
        termback_button = findViewById(R.id.termback_button);
        applyButton = findViewById(R.id.apply_button);
        addTermButton = findViewById(R.id.term_add);

        termList = new ArrayList<>();
        termList.addAll(dbHelper.getAllTerms());

        adapter = new TermsAdapter(termList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        updateApplyButtonState();

        termback_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adapter.setOnItemClickListener(new TermsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String termText = termList.get(position);
                TermsConfigure.newInstance(position, termText).show(getSupportFragmentManager(), "TermsConfigure");
            }

            @Override
            public void onDeleteClick(int position) {
                String termName = termList.get(position);
                dbHelper.showDeleteConfirmationDialog(termName, new DeleteConfirmationCallback() {
                    @Override
                    public void onDeleteConfirmed() {
                        String termId = dbHelper.getTermId(termName);
                        dbHelper.deleteTerm(termId);
                        termList.remove(position);
                        adapter.notifyItemRemoved(position);
                        updateApplyButtonState();
                    }
                });
            }
        });

        addTermButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open TermsConfigure fragment with position as -1 (indicating new term)
                TermsConfigure.newInstance(-1, "").show(getSupportFragmentManager(), "TermsConfigure");
            }
        });

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (termList.isEmpty()) {
                    // Just do nothing
                } else {
                    // Perform your action here when apply button is clicked
                    Toast.makeText(Terms.this, "Applied Successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private void updateApplyButtonState() {
        Drawable background = applyButton.getBackground();
        if (termList.isEmpty()) {
            applyButton.setEnabled(false);
            background.setAlpha(128);
        } else {
            applyButton.setEnabled(true);
            background.setAlpha(255);
        }
    }

    public void updateTerm(int position, String newTerm) {
        String termId;
        if (position == -1) {
            // Add new term
            termId = dbHelper.addTerm(newTerm);
            termList.add(newTerm);
            adapter.notifyItemInserted(termList.size() - 1);
        } else {
            // Update existing term
            termId = dbHelper.getTermId(termList.get(position));
            dbHelper.updateTerm(termId, newTerm);
            termList.set(position, newTerm);
            adapter.notifyItemChanged(position);
        }
        updateApplyButtonState();
    }

    interface DeleteConfirmationCallback {
        void onDeleteConfirmed();
    }
}
