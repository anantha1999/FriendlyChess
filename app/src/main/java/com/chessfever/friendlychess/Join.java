package com.chessfever.friendlychess;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Join extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private Button joinButton;

    private EditText codeValue;

    private boolean playerWhite = false;

    private Context context;

    private boolean join_flag = true;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        try {
            getSupportActionBar().hide();
        }
        catch (Exception e){

        }
        Common.gameOver = false;
        getWindow().setStatusBarColor(Color.parseColor("#1B1B1B"));
        context = getApplicationContext();
        joinButton = findViewById(R.id.join);
        codeValue = findViewById(R.id.codeInput);
        joinButton.setOnClickListener(v -> {
                Common.code = codeValue.getText().toString();
                System.out.println("Getting game reference");
                DatabaseReference game = database.getReference().child(codeValue.getText().toString());
                System.out.println("Done getting game reference");
                game.child("players").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if(snapshot.getValue(Integer.class) < 2) {
                            changeIntentListener(game);
                            join_flag = false;
                        }
                        else if(join_flag){
                            AlertDialog.Builder builder
                                    = new AlertDialog
                                    .Builder(Join.this);

                            builder.setMessage("Room is full");
                            builder.setTitle("");
                            builder.setCancelable(false);

                            builder
                                    .setPositiveButton(
                                            "Ok",
                                            (dialog, which) -> {
                                                dialog.cancel();
                                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                    }


                    @Override
                    public void onCancelled( DatabaseError error) {

                    }
                });


        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
        finish();
    }

    private void changeIntent(){
        if(playerWhite){
            System.out.println("Inside white");
            Intent intent = new Intent(this, White.class);
            startActivity(intent);
            finish();
        }
        else{
            System.out.println("Inside black");
            Intent intent = new Intent(this, Black.class);
            startActivity(intent);
            finish();
        }
    }

    private void changeIntentListener(DatabaseReference game){

        game.child("players").setValue(2);
        game.child("colour").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                try {
                    System.out.println("Reading reference");
                    if (snapshot.getValue(Integer.class) == 0) {
                        System.out.println("Inside if");
                        playerWhite = false;
                        changeIntent();
                    } else {
                        System.out.println("Inside else");
                        playerWhite = true;
                        changeIntent();
                    }
                    System.out.println("Done reading");
                }
                catch (Exception e){
                    CharSequence text = "Code is invalid";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        getActionBar().hide();
//    }


}