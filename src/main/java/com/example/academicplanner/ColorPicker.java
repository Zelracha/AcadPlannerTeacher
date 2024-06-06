package com.example.academicplanner;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ColorPicker extends BottomSheetDialogFragment {

    private int[] colors = {
            0xFFFF6666, // Light Red (hex: #FF6666)
            0xFFFF0000, // Red (hex: #FF0000)
            0xFFCC0000, // Dark Red (hex: #CC0000)
            0xFFFFB84D, // Light Orange (hex: #FFB84D)
            0xFFFFA500, // Orange (hex: #FFA500)
            0xFFCC8400, // Dark Orange (hex: #CC8400)
            0xFFFFFF66, // Light Yellow (hex: #FFFF66)
            0xFFFFFF00, // Yellow (hex: #FFFF00)
            0xFFCCCC00, // Dark Yellow (hex: #CCCC00)
            0xFF66FF66, // Light Lime (hex: #66FF66)
            0xFF00FF00, // Lime (hex: #00FF00)
            0xFF00CC00, // Dark Lime (hex: #00CC00)
            0xFF66CC66, // Light Green (hex: #66CC66)
            0xFF008000, // Green (hex: #008000)
            0xFF006600, // Dark Green (hex: #006600)
            0xFF6666FF, // Light Blue (hex: #6666FF)
            0xFF0000FF, // Blue (hex: #0000FF)
            0xFF0000CC, // Dark Blue (hex: #0000CC)
            0xFF66CCCC, // Light Teal (hex: #66CCCC)
            0xFF008080, // Teal (hex: #008080)
            0xFF006666, // Dark Teal (hex: #006666)
            0xFF9B30FF, // Light Violet (hex: #9B30FF)
            0xFF8A2BE2, // Violet (hex: #8A2BE2)
            0xFF6820B0, // Dark Violet (hex: #6820B0)
            0xFFFF66FF, // Light Magenta (hex: #FF66FF)
            0xFFFF00FF, // Magenta (hex: #FF00FF)
            0xFFCC00CC, // Dark Magenta (hex: #CC00CC)
            0xFFFFD6E5, // Light Pink (hex: #FFD6E5)
            0xFFFFC0CB, // Pink (hex: #FFC0CB)
            0xFFCC99A2, // Dark Pink (hex: #CC99A2)
            0xFFCC7A7A,  // Light Brown (hex: #CC7A7A)
            0xFFA52A2A, // Brown (hex: #A52A2A)
            0xFF7A1E1E // Dark Brown (hex: #7A1E1E)
    };

    private OnColorSelectedListener colorSelectedListener;

    public interface OnColorSelectedListener {
        void onColorSelected(int color);
    }

    public void setOnColorSelectedListener(OnColorSelectedListener listener) {
        this.colorSelectedListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_color_picker, container, false);

        GridView colorGridView = view.findViewById(R.id.colorGridView);
        ColorAdapter colorAdapter = new ColorAdapter();
        colorGridView.setAdapter(colorAdapter);

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
                int margin = (int) (30 * getResources().getDisplayMetrics().density);
                bottomSheet.getLayoutParams().height = height - margin;
                BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(bottomSheet);
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                behavior.setHideable(true);
                behavior.setSkipCollapsed(true);

                bottomSheet.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_bottom_sheet));

                View tipView = bottomSheet.findViewById(R.id.bottom_sheet_tip);
                tipView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        int[] location = new int[2];
                        v.getLocationOnScreen(location);
                        int tipY = location[1]; // Y-coordinate of the tip view
                        float y = event.getRawY();
                        if (y < tipY) {
                            // Allow scrolling if the touch event is above the tip view
                            return false;
                        } else {
                            // Consume the touch event if it's on or below the tip view
                            return true;
                        }
                    }
                });
            }
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(dialogInterface -> {
            BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
            View bottomSheet = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            if (bottomSheet != null) {
                BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(bottomSheet);
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                ObjectAnimator slideUpAnimator = ObjectAnimator.ofFloat(bottomSheet, "translationY", bottomSheet.getHeight(), 0);
                slideUpAnimator.setDuration(300);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(slideUpAnimator);
                animatorSet.start();
            }
        });
        return dialog;
    }

    private class ColorAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return colors.length;
        }

        @Override
        public Object getItem(int position) {
            return colors[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.color_picker_item, parent, false);
            }

            ImageView colorImageView1 = view.findViewById(R.id.colorImageView1);
            ImageView colorImageView2 = view.findViewById(R.id.colorImageView2);
            ImageView colorImageView3 = view.findViewById(R.id.colorImageView3);

            int color = (int) getItem(position);
            colorImageView1.setBackgroundColor(color);
            colorImageView2.setBackgroundColor(color);
            colorImageView3.setBackgroundColor(color);

            view.setOnClickListener(v -> {
                if (colorSelectedListener != null) {
                    colorSelectedListener.onColorSelected(color);

                    if (getDialog() != null) {
                        View bottomSheet = getDialog().findViewById(com.google.android.material.R.id.design_bottom_sheet);
                        if (bottomSheet != null) {
                            ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(bottomSheet, View.ALPHA, 1f, 0f);
                            ObjectAnimator slideDownAnimator = ObjectAnimator.ofFloat(bottomSheet, "translationY", 0, bottomSheet.getHeight());
                            AnimatorSet animatorSet = new AnimatorSet();
                            animatorSet.playTogether(alphaAnimator, slideDownAnimator);
                            animatorSet.setDuration(300);
                            animatorSet.addListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    dismiss();
                                }
                            });
                            animatorSet.start();
                        }
                    }
                }
            });

            return view;
        }
    }
}