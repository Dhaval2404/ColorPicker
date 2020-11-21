package com.github.dhaval2404.colorpicker.sample;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.dhaval2404.colorpicker.ColorPickerDialog;
import com.github.dhaval2404.colorpicker.ColorPickerView;
import com.github.dhaval2404.colorpicker.listener.ColorListener;
import com.github.dhaval2404.colorpicker.listener.DismissListener;
import com.github.dhaval2404.colorpicker.model.ColorShape;
import com.github.dhaval2404.colorpicker.util.ColorUtil;
import com.github.dhaval2404.colorpicker.util.SharedPref;

import org.jetbrains.annotations.NotNull;

public class ColorPickerFragmentJava extends Fragment {

    private int mColor = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_color_picker, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View rootView = getView();
        final AppCompatButton colorPickerBtn = rootView.findViewById(R.id.colorPickerBtn);
        final ColorPickerView colorPickerView = rootView.findViewById(R.id.colorPickerView);

        int primaryColor = ContextCompat.getColor(requireContext(), R.color.colorPrimary);
        mColor = new SharedPref(requireContext()).getRecentColor(primaryColor);
        getView().findViewById(R.id.colorPickerBtn).setOnClickListener(view -> {
            new ColorPickerDialog.Builder(requireActivity())
                    .setColorShape(ColorShape.SQAURE)
                    .setDefaultColor(mColor)
                    .setColorListener(new ColorListener() {
                        @Override
                        public void onColorSelected(int color, @NotNull String colorHex) {
                            mColor = color;
                            colorPickerView.setColor(color);
                            setButtonBackground(colorPickerBtn, color);
                        }
                    })
                    .setDismissListener(new DismissListener() {
                        @Override
                        public void onDismiss() {
                            Log.d("ColorPickerDialog", "Handle dismiss event");
                        }
                    })
                    .show();
        });
    }

    private void setButtonBackground(AppCompatButton btn, int color) {
        if (ColorUtil.isDarkColor(color)) {
            btn.setTextColor(Color.WHITE);
        } else {
            btn.setTextColor(Color.BLACK);
        }
        btn.setBackgroundTintList(ColorStateList.valueOf(color));
    }

}
