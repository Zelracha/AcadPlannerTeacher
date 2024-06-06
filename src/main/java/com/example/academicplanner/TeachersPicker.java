package com.example.academicplanner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class TeachersPicker extends BottomSheetDialogFragment {

    private DBHelper dbHelper;
    private LinearLayout checkboxContainer;
    private List<String> selectedTeachers = new ArrayList<>();

    public interface TeachersSelectedListener {
        void onTeachersSelected(List<String> selectedTeachers);
    }

    private TeachersSelectedListener mListener;

    public static TeachersPicker newInstance(List<String> selectedTeachers) {
        TeachersPicker fragment = new TeachersPicker();
        Bundle args = new Bundle();
        args.putStringArrayList("selectedTeachers", new ArrayList<>(selectedTeachers));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (TeachersSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement TeachersSelectedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_picker, container, false);

        dbHelper = new DBHelper(requireContext());
        checkboxContainer = view.findViewById(R.id.checkboxContainer);

        List<String> teacherList = dbHelper.getAllTeachers();
        List<String> preSelectedTeachers = getArguments() != null ? getArguments().getStringArrayList("selectedTeachers") : new ArrayList<>();

        for (String teacher : teacherList) {
            CheckBox checkBox = new CheckBox(requireContext());
            checkBox.setPadding(25, 0, 0, 0);
            checkBox.setText(teacher);
            checkBox.setTypeface(ResourcesCompat.getFont(requireContext(), R.font.dm_sans));
            checkBox.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

            if (preSelectedTeachers.contains(teacher)) {
                checkBox.setChecked(true);
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 20);
            checkBox.setLayoutParams(params);

            checkboxContainer.addView(checkBox);
        }

        Button createButton = view.findViewById(R.id.teacher_create_button);
        createButton.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), TeachersAdd.class);
            startActivity(intent);
            dismiss();
        });

        Button selectButton = view.findViewById(R.id.teacher_select_button);
        selectButton.setOnClickListener(v -> {
            selectedTeachers.clear();
            for (int i = 0; i < checkboxContainer.getChildCount(); i++) {
                CheckBox checkBox = (CheckBox) checkboxContainer.getChildAt(i);
                if (checkBox.isChecked()) {
                    selectedTeachers.add(checkBox.getText().toString());
                }
            }
            mListener.onTeachersSelected(selectedTeachers);
            dismiss();
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
        if (dialog != null) {
            View bottomSheet = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            if (bottomSheet != null) {
                int height = getResources().getDisplayMetrics().heightPixels;
                int margin = (int) (400 * getResources().getDisplayMetrics().density);
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
