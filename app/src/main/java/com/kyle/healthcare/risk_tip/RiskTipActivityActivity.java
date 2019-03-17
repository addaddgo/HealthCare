package com.kyle.healthcare.risk_tip;

import android.content.Intent;
import android.media.Image;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.kyle.healthcare.R;

import java.util.ArrayList;

public class RiskTipActivityActivity extends AppCompatActivity {

    private ImageView imageView;
    private ArrayList<Integer> images;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.risk_tip_activity);
        imageView = findViewById(R.id.risk_image_view);
        this.images = new ArrayList<>();
        linkResource();
        setListener();
        vibrating();
    }
    //vibrator
    private Vibrator vibrator;
    private boolean isOpening = true;
    private void vibrating(){
        this.vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
        //sleeping at the first of time,start vibrating at the second placing,and then sleeping the time of the third.next,from second number vibrating
        this.vibrator.vibrate(new long[]{0,1000,500},1);
    }


    //get image resource
    private void linkResource(){
        int all = getIntent().getIntExtra("TIP",0);
        switch (all){
            case 3:this.images.add(R.mipmap.fatigue_unusual);
                    this.images.add(R.mipmap.driving_unusual);
                    break;
            case 2:this.images.add(R.mipmap.fatigue_unusual);
                    break;
            case 1:this.images.add(R.mipmap.driving_unusual);
                    break;
        }
    }

    //listener
    private void setListener(){
        this.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOpening){
                    isOpening = false;
                    vibrator.cancel();
                }
                if(images.size() == 0){
                    finish();
                }else {
                    int resource = images.get(0);
                    images.remove(0);
                    imageView.setImageResource(resource);
                }
            }
        });
    }
}
