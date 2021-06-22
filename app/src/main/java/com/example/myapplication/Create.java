package com.example.myapplication;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.slider.RangeSlider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class Create extends AppCompatActivity {

    private AnimationDrawable loadingAnimation;

    private ImageView imageView;

    private boolean white = false;

    private boolean black = false;

    private ImageView whiteButton;

    private ImageView blackButton;

    private TextView whiteText;

    private TextView blackText;

    private ImageView copyButton;

    private TextView code;

    private boolean playerWhite = true;

    private boolean flag = false;

    private Button createButton;

    private RangeSlider minutes;

    private RangeSlider bonus;

    private TextView minutesValue;

    private TextView bonusValue;

    private ProgressBar progressBar;

    private TextView waiting;

    private Switch timerSwitch;

    private TextView timerText;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Common.isTimer = false;
        timerText = findViewById(R.id.timerText);
        timerSwitch = findViewById(R.id.timerSwitch);
        createButton = findViewById(R.id.create);
        progressBar = findViewById(R.id.progressBar);
        waiting = findViewById(R.id.waiting);
        progressBar.setVisibility(View.GONE);
        waiting.setVisibility(View.GONE);

        minutes = findViewById(R.id.minutes);
        bonus = findViewById(R.id.bonus);
        minutesValue = findViewById(R.id.minutesValue);
        bonusValue = findViewById(R.id.bonusValue);

        timerSwitch.setOnClickListener(v -> {
            if(Common.isTimer) {
                Common.isTimer = false;
                timerText.setText("Timer : OFF");
            }
            else{
                Common.isTimer = true;
                timerText.setText("Timer : ON");
            }
        });

        minutes.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(RangeSlider slider, float value, boolean fromUser) {
                minutesValue.setText(String.valueOf(value));
            }
        });

        bonus.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(RangeSlider slider, float value, boolean fromUser) {
                bonusValue.setText(String.valueOf(value));
            }
        });

        createButton.setOnClickListener(v -> {
            if(!flag) {
                progressBar.setVisibility(View.VISIBLE);
                waiting.setVisibility(View.VISIBLE);
                Random random = new Random();
                Common.code = generateCode()+random.nextInt(10000);
                code = findViewById(R.id.code);
                code.setText(Common.code);
                DatabaseReference dbRef = database.getReference();
                DatabaseReference game = dbRef.child(Common.code);
                WhiteBlack white = new WhiteBlack();
                WhiteBlack black = new WhiteBlack();
                if(Common.isTimer) {
                    white.time = (int) (Float.parseFloat(minutesValue.getText().toString()) * 60);
                    white.bonus = (int) (Float.parseFloat(bonusValue.getText().toString()));
                    black.time = (int) (Float.parseFloat(minutesValue.getText().toString())*60);
                    black.bonus = (int) (Float.parseFloat(bonusValue.getText().toString()));
                }
                game.child("white").setValue(white);
                game.child("black").setValue(black);
                game.child("whitedraw").setValue(0);
                game.child("blackdraw").setValue(0);
                game.child("players").setValue(1);
                game.child("timer").setValue(Common.isTimer);
                game.child("colour").setValue((this.playerWhite)? 0:1);
                game.child("players").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (flag) { //added flag because it runs once as soon as the listener is created as this is how values are read too.
                            changeIntent();
                        } else {
                            flag = true;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
            }
        });


        whiteButton = findViewById(R.id.white);
        blackButton = findViewById(R.id.black);
        whiteText = findViewById(R.id.whiteText);
        blackText = findViewById(R.id.blackText);
        copyButton = findViewById(R.id.copy);

        whiteButton.setOnClickListener(v -> {
            changeWhiteButtonColour();
        });
        whiteText.setOnClickListener(v -> {
            changeWhiteButtonColour();
        });
        blackButton.setOnClickListener(v -> {
            changeBlackButtonColour();
        });
        blackText.setOnClickListener(v -> {
            changeBlackButtonColour();
        });
        copyButton.setOnClickListener(v -> {
            copyCodeToClipBoard();
        });
    }

    private void changeIntent(){
        if(playerWhite){
            Intent intent = new Intent(this, White.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(this, Black.class);
            startActivity(intent);
        }
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void changeWhiteButtonColour(){
        playerWhite = true;
        if(white){
            return;
        }
        else if(!white && !black){
            white = true;
            black = false;
            whiteButton.setBackground(getDrawable(R.drawable.circleshadowon));

        }
        else if(!white && black){
            black = false;
            white = true;
            whiteButton.setBackground(getDrawable(R.drawable.circleshadowon));

            blackButton.setBackground(getDrawable(R.drawable.circleshadowoff));

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void changeBlackButtonColour(){
        playerWhite = false;
        if(black){
            return;
        }
        else if(!white && !black){
            black = true;
            white = false;
            blackButton.setBackground(getDrawable(R.drawable.circleshadowon));

        }
        else if(!black && white){
            white = false;
            black = true;
            blackButton.setBackground(getDrawable(R.drawable.circleshadowon));

            whiteButton.setBackground(getDrawable(R.drawable.circleshadowoff));

        }
    }

    private void copyCodeToClipBoard(){
        ClipboardManager cm = (ClipboardManager)getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText("Code",code.getText().toString()));
        Context context = getApplicationContext();
        CharSequence text = "Copied!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private String generateCode() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 7;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();

        return(generatedString);
    }

}