package com.todolist.reminder;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;



/**
 * Created by HP on 09-01-2018.
 */

public class   settingsdialog extends DialogFragment {
    private RadioGroup group;
    private RadioGroup vibration;
    private final int NO_SOUND=0;
    private final int SOUND=1;
    private final int RING_SOUND=2;
    private final int VI=1;
    private final int NOVI=0;
    public  int soundset=1;
    public  int vibrationset=1;
    private RadioButton soundyesbtn;
    private RadioButton soundnobtn;
    private RadioButton soundoffstate;
    private RadioButton vibrationyes;
    private RadioButton vibrationno;
    private Button setbtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.settingslayout,container,false);
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
         soundset=sharedPreferences.getInt("soundkey",1);
         vibrationset=sharedPreferences.getInt("vibrationkey",0);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);


        group=(RadioGroup)root.findViewById(R.id.nogroup);
        vibration=(RadioGroup)root.findViewById(R.id.vigroup);
        soundyesbtn=(RadioButton)group.findViewById(R.id.nosoon);
        soundnobtn=(RadioButton)group.findViewById(R.id.nosoof);
        soundoffstate=(RadioButton)group.findViewById(R.id.offstate);
        vibrationyes=(RadioButton)vibration.findViewById(R.id.vion);
        setbtn=(Button)root.findViewById(R.id.setbtnsettings);
        vibrationno=(RadioButton) vibration.findViewById(R.id.viof);
        if(soundset==1){
            soundyesbtn.setChecked(true);
            soundnobtn.setChecked(false);
            soundoffstate.setChecked(false);
        }else if(soundset==2){
            soundyesbtn.setChecked(false);
            soundnobtn.setChecked(true);
            soundoffstate.setChecked(false);
        }
        else{
            soundyesbtn.setChecked(false);
            soundnobtn.setChecked(false);
            soundoffstate.setChecked(true);
        }
        if(vibrationset==1){
            vibrationyes.setChecked(true);
            vibrationno.setChecked(false);

        }else{
            vibrationyes.setChecked(false);
            vibrationno.setChecked(true);
        }
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.nosoon){

                    SharedPreferences sp=getActivity().getSharedPreferences("settings", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putInt("soundkey",SOUND);
                    editor.commit();
                }else if(i==R.id.nosoof){

                    SharedPreferences sp=getActivity().getSharedPreferences("settings", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putInt("soundkey",RING_SOUND);
                    editor.commit();

                }
                else if(i==R.id.offstate){

                    SharedPreferences sp=getActivity().getSharedPreferences("settings",Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putInt("soundkey",NO_SOUND);
                    editor.commit();
                }
            }
        });

        vibration=(RadioGroup)root.findViewById(R.id.vigroup);
        vibration.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.vion){

                    SharedPreferences sp=getActivity().getSharedPreferences("settings", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putInt("vibrationkey",VI);
                    editor.commit();
                }
                else if(i==R.id.viof){

                    SharedPreferences sp=getActivity().getSharedPreferences("settings", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putInt("vibrationkey",NOVI);
                    editor.commit();

                }
            }
        });

        setbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });




        return root;
    }
}
