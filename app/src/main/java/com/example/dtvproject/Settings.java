package com.example.dtvproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    ImageButton b1;
    LinearLayout l1;
    Switch s1,s2;
    Dialog d;
    TextView t_quality,t_subtitle,t_autoplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        b1=findViewById(R.id.b_back);
        l1=findViewById(R.id.ll_video_quality);
        s1=findViewById(R.id.switch1);
        s2=findViewById(R.id.switch2);
        t_autoplay=findViewById(R.id.textview_autoplay);
        t_quality=findViewById(R.id.textview_video_quality);
        t_subtitle=findViewById(R.id.textview_subtitles);

        s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (isChecked){
                   t_subtitle.setText("Auto");
                }else{
                    t_subtitle.setText("Off");
                }
            }
        });

        s2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (isChecked){
                   t_autoplay.setText("Auto");
                }else{
                    t_autoplay.setText("Off");
                }
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(Settings.this,MainActivity.class);
                startActivity(in);
            }
        });
        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                d=new Dialog(Settings.this);
                d.setContentView(R.layout.dialog_video_quality);
                final RadioGroup selectquality=d.findViewById(R.id.select_quality);
                RadioButton qualityauto=d.findViewById(R.id.quality_auto);
                RadioButton quality360=d.findViewById(R.id.quality_360p);
                RadioButton quality720=d.findViewById(R.id.quality_720p);
                RadioButton quality1080=d.findViewById(R.id.quality_1080p);

                selectquality.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        switch (i){
                            case R.id.quality_auto:
                               t_quality.setText("Auto");
                               d.dismiss();
                               break;
                            case R.id.quality_360p:
                                t_quality.setText("360p");
                                d.dismiss();
                                break;
                            case R.id.quality_720p:
                                t_quality.setText("720p");
                                d.dismiss();
                                break;
                            case R.id.quality_1080p:
                                t_quality.setText("1080p");
                                d.dismiss();
                                break;
                        }

                    }
                });

                Button cancel=d.findViewById(R.id.b_qality_dialog);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.dismiss();
                    }
                });
                d.show();
            }
        });
    }
}
