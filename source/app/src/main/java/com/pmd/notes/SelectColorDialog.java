package com.pmd.notes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;

public class SelectColorDialog extends DialogFragment {
    private String color = null;
    public Dialog onCreateDialog(Bundle savedInstanceFragment){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.select_color,null);
        builder.setView(view);
        view.findViewById(R.id.YellowBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = Note.getConstColor("yellow");
                dismiss();
            }
        });
        view.findViewById(R.id.GreenBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = Note.getConstColor("green");
                dismiss();
            }
        });
        view.findViewById(R.id.BlueBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = Note.getConstColor("blue");
                dismiss();
            }
        });
        view.findViewById(R.id.RedBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = Note.getConstColor("red");
                dismiss();
            }
        });
        return builder.create();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (color != null){
            getActivity().findViewById(R.id.lbg).setBackgroundColor(Color.parseColor(color));

        }
    }
}
