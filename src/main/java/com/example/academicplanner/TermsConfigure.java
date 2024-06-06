package com.example.academicplanner;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class TermsConfigure extends BottomSheetDialogFragment {

    private static final String ARG_POSITION = "position";
    private static final String ARG_TERM_TEXT = "term_text";

    private int position;
    private String termText;

    public TermsConfigure() {
    }

    public static TermsConfigure newInstance(int position, String termText) {
        TermsConfigure fragment = new TermsConfigure();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        args.putString(ARG_TERM_TEXT, termText);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_POSITION);
            termText = getArguments().getString(ARG_TERM_TEXT);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_terms_configure, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText termTitleEditText = view.findViewById(R.id.term_title_text);
        termTitleEditText.setText(termText);

        Button cancelButton = view.findViewById(R.id.term_cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Button editButton = view.findViewById(R.id.term_edit_button);
        if (position == -1) {
            editButton.setText("CREATE");
        } else {
            editButton.setText("EDIT");
        }
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedTermText = termTitleEditText.getText().toString();
                if (!TextUtils.isEmpty(updatedTermText)) {
                    ((Terms) requireActivity()).updateTerm(position, updatedTermText);
                    dismiss();
                } else {
                    Toast.makeText(getContext(), "Title cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
        if (dialog != null) {
            View bottomSheet = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            if (bottomSheet != null) {
                int height = getResources().getDisplayMetrics().heightPixels;
                int margin = (int) (600 * getResources().getDisplayMetrics().density);
                bottomSheet.getLayoutParams().height = height - margin;
                BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(bottomSheet);
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                behavior.setHideable(true);
                behavior.setSkipCollapsed(true);

                bottomSheet.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_bottom_sheet));
            }
        }
    }
}
