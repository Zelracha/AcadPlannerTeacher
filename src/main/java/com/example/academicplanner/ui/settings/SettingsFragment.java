package com.example.academicplanner.ui.settings;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.academicplanner.AboutActivity;
import com.example.academicplanner.AccountProfile;
import com.example.academicplanner.MainActivity;
import com.example.academicplanner.R;
import com.example.academicplanner.Terms;

public class SettingsFragment extends Fragment {

    TextView accountProfile;
    TextView Semester;
    TextView About;
    TextView Logout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        accountProfile = view.findViewById(R.id.accountProfile);
        Semester = view.findViewById(R.id.Terms);
        About = view.findViewById(R.id.About);
        Logout = view.findViewById(R.id.Logout);

        accountProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AccountProfile.class);
                startActivity(i);
            }
        });

        Semester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Terms.class);
                startActivity(i);
            }
        });

        About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AboutActivity.class);
                startActivity(i);            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutMenu(SettingsFragment.this);
            }
        });
        return view;
    }

    private void logoutMenu(SettingsFragment settingsFragment){
        AlertDialog.Builder builder = new AlertDialog.Builder(settingsFragment.getActivity());
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

}