package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        context = getApplicationContext();
        joinButton = findViewById(R.id.join);
        codeValue = findViewById(R.id.codeInput);
        joinButton.setOnClickListener(v -> {
                Common.code = codeValue.getText().toString();
                System.out.println("Getting game reference");
                DatabaseReference game = database.getReference().child(codeValue.getText().toString());
                System.out.println("Done getting game reference");
                changeIntentListener(game);

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
}