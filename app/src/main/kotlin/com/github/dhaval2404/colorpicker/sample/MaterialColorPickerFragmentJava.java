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
import androidx.fragment.app.Fragment;

import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog;
import com.github.dhaval2404.colorpicker.listener.ColorListener;
import com.github.dhaval2404.colorpicker.listener.DismissListener;
import com.github.dhaval2404.colorpicker.model.ColorShape;
import com.github.dhaval2404.colorpicker.model.ColorSwatch;
import com.github.dhaval2404.colorpicker.util.ColorUtil;

import org.jetbrains.annotations.NotNull;

public class MaterialColorPickerFragmentJava extends Fragment {

    private String mMaterialColorSquare = "";
    private String mMaterialColorCircle = "";
    private String mMaterialColorBottomSheet = "";
    private String mMaterialPreDefinedColor = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_material_color_picker, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View rootView = getView();
        final AppCompatButton materialPreDefinedColorPickerBtn = rootView.findViewById(R.id.materialPreDefinedColorPickerBtn);
        final AppCompatButton materialDialogPickerCircleBtn = rootView.findViewById(R.id.materialDialogPickerCircleBtn);
        final AppCompatButton materialBottomSheetDialogBtn = rootView.findViewById(R.id.materialBottomSheetDialogBtn);
        final AppCompatButton materialDialogPickerSquareBtn = rootView.findViewById(R.id.materialDialogPickerSquareBtn);

        materialDialogPickerSquareBtn.setOnClickListener(view -> {
            new MaterialColorPickerDialog
                    .Builder(requireActivity())
                    .setColorShape(ColorShape.SQAURE) // Or ColorShape.CIRCLE
                    .setColorSwatch(ColorSwatch._300) // Default ColorSwatch._500
                    .setDefaultColor(mMaterialColorSquare) // Pass Default Color
                    .setColorListener(new ColorListener() {
                        @Override
                        public void onColorSelected(int color, @NotNull String colorHex) {
                            mMaterialColorSquare = colorHex;
                            setButtonBackground(materialDialogPickerSquareBtn, color);
                        }
                    })
                    .show();
        });

        materialDialogPickerCircleBtn.setOnClickListener(view -> {
            new MaterialColorPickerDialog
                    .Builder(requireActivity())
                    .setColorSwatch(ColorSwatch._500)
                    .setDefaultColor(mMaterialColorCircle)
                    .setColorListener(new ColorListener() {
                        @Override
                        public void onColorSelected(int color, @NotNull String colorHex) {
                            mMaterialColorCircle = colorHex;
                            setButtonBackground(materialDialogPickerCircleBtn, color);
                        }
                    })
                    .setDismissListener(new DismissListener() {
                        @Override
                        public void onDismiss() {
                            Log.d("ColorPickerDialog", "Dismiss");
                        }
                    })
                    .show();
        });

        materialBottomSheetDialogBtn.setOnClickListener(view -> {
            new MaterialColorPickerDialog
                    .Builder(requireActivity())
                    .setColorSwatch(ColorSwatch._300)
                    .setDefaultColor(mMaterialColorBottomSheet)
                    .setColorListener(new ColorListener() {
                        @Override
                        public void onColorSelected(int color, @NotNull String colorHex) {
                            mMaterialColorBottomSheet = colorHex;
                            setButtonBackground(materialBottomSheetDialogBtn, color);
                        }
                    })
                    .setDismissListener(new DismissListener() {
                        @Override
                        public void onDismiss() {
                            Log.d("BottomSheetDialog", "Dismiss");
                        }
                    })
                    .showBottomSheet(getChildFragmentManager());
        });

        materialPreDefinedColorPickerBtn.setOnClickListener(view -> {
            String[] colorArray = new String[]{"#f6e58d", "#ffbe76", "#ff7979", "#badc58", "#dff9fb",
                    "#7ed6df", "#e056fd", "#686de0", "#30336b", "#95afc0"};
            int[] colorResArray = getResources().getIntArray(R.array.themeColors);
            String[] colorHexArray = getResources().getStringArray(R.array.themeColorHex);

            new MaterialColorPickerDialog
                    .Builder(requireActivity())
                    .setColorRes(colorResArray)
                    .setColors(colorHexArray)
                    .setColors(colorArray)
                    .setDefaultColor(mMaterialPreDefinedColor)
                    .setColorListener(new ColorListener() {
                        @Override
                        public void onColorSelected(int color, @NotNull String colorHex) {
                            mMaterialPreDefinedColor = colorHex;
                            setButtonBackground(materialPreDefinedColorPickerBtn, color);
                        }
                    })
                    .showBottomSheet(getChildFragmentManager());
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
