package com.todolist.reminder;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;



/**
 * Created by HP on 24-12-2017.
 */

public class drawingsurface extends DialogFragment {
    private Button setButton;

    private TextInputLayout textInputLayout;
    private String text;
    private ArrayList<Model> item;
    private int num;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.drawingsurface, container, false);
        textInputLayout=(TextInputLayout)v.findViewById(R.id.til);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        num=getArguments().getInt("value");
        Toast.makeText(getActivity(),"VALUE: "+num,Toast.LENGTH_LONG).show();
        setButton = (Button) v.findViewById(R.id.setBtn);
        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text=textInputLayout.getEditText().getText().toString();
                if(text.isEmpty()){
                    text="NO TASK SET";
                }
                FragmentManager fm=getFragmentManager();
                TimeSet timeSet=new TimeSet();
                Bundle values=new Bundle();
                timeSet.setArguments(values);
                values.putString("text",text);
                timeSet.setCancelable(false);
                timeSet.show(fm,"ENTER YOUR TASK");
                dismiss();



            }
        });
return v;
    }
}
