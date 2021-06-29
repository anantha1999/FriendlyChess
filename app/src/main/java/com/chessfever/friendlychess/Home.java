package com.chessfever.friendlychess;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {
    private Button button1;
    private Button button2;

    private boolean flag = true;

    private int id;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        try {
            getSupportActionBar().hide();
        }
        catch (Exception e){

        }
        getWindow().setStatusBarColor(Color.parseColor("#1B1B1B"));
        button1 = findViewById(R.id.button1);

        button2 = findViewById(R.id.button2);
        button1.setOnClickListener(v -> {
            try {
                goToCreate();
                System.out.println("ID constant " + Common.id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        button2.setOnClickListener(v -> {
            goToJoin();
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void goToCreate(){
        Intent intent = new Intent(this, Create.class);
        startActivity(intent);
        finish();

    }

    private void goToJoin(){
        Intent intent = new Intent(this, Join.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

    }
}