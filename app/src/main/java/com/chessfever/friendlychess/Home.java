package com.chessfever.friendlychess;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {
    private Button button1;
    private Button button2;

    private boolean flag = true;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Intent intent = new Intent(this, Black.class);
//        startActivity(intent);
        setContentView(R.layout.activity_home);
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
//
    private void goToCreate(){
        Intent intent = new Intent(this, Create.class);
        startActivity(intent);
        finish();
//        System.out.println(Constants.whiteBlack);
    }

    private void goToJoin(){
        Intent intent = new Intent(this, Join.class);
        startActivity(intent);
        finish();
    }

    private void changeButtonColour(){
        button1.setBackgroundColor(getResources().getColor(R.color.black));
    }
}