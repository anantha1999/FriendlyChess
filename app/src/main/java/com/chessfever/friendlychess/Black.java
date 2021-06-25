package com.chessfever.friendlychess;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Black extends AppCompatActivity {

    private ImageView board;

    private ChessPiece blackPawn1 = new ChessPiece();
    private ChessPiece blackPawn2 = new ChessPiece();
    private ChessPiece blackPawn3 = new ChessPiece();
    private ChessPiece blackPawn4 = new ChessPiece();
    private ChessPiece blackPawn5 = new ChessPiece();
    private ChessPiece blackPawn6 = new ChessPiece();
    private ChessPiece blackPawn7 = new ChessPiece();
    private ChessPiece blackPawn8 = new ChessPiece();
    private ChessPiece blackKing = new ChessPiece();
    private ChessPiece blackQueen = new ChessPiece();
    private ChessPiece blackBishop1 = new ChessPiece();
    private ChessPiece blackBishop2 = new ChessPiece();
    private ChessPiece blackKnight1 = new ChessPiece();
    private ChessPiece blackKnight2 = new ChessPiece();
    private ChessPiece blackRook1 = new ChessPiece();
    private ChessPiece blackRook2 = new ChessPiece();

    private ChessPiece whitePawn1 = new ChessPiece();
    private ChessPiece whitePawn2 = new ChessPiece();
    private ChessPiece whitePawn3 = new ChessPiece();
    private ChessPiece whitePawn4 = new ChessPiece();
    private ChessPiece whitePawn5 = new ChessPiece();
    private ChessPiece whitePawn6 = new ChessPiece();
    private ChessPiece whitePawn7 = new ChessPiece();
    private ChessPiece whitePawn8 = new ChessPiece();
    private ChessPiece whiteKing = new ChessPiece();
    private ChessPiece whiteQueen = new ChessPiece();
    private ChessPiece whiteBishop1 = new ChessPiece();
    private ChessPiece whiteBishop2 = new ChessPiece();
    private ChessPiece whiteKnight1 = new ChessPiece();
    private ChessPiece whiteKnight2 = new ChessPiece();
    private ChessPiece whiteRook1 = new ChessPiece();
    private ChessPiece whiteRook2 = new ChessPiece();



    private boolean black_turn = false;

    private ChessPiece selectedPiece = null;

    private float[] lastTouchDownXY = new float[2];

    private ChessPiece[] whitePawns = new ChessPiece[8];

    private ChessPiece[] blackPawns = new ChessPiece[8];

    private boolean underCheck = false;

    public int[][] boardLocations = new int[8][8];

    public int[][] attackedSquares = new int[8][8];

    public int[][] pieceLocations = new int[8][8];

    private Map<Integer, ChessPiece> id_piece = new HashMap<>();

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    private boolean flag = false;

    private Button whiteClock;

    private Button blackClock;

    private boolean addIncrementWhite = true;

    private boolean addIncrementBlack = true;

    private CountDownTimer whiteTimer;

    private CountDownTimer blackTimer;

    private Button resign;

    private Button leave;

    private DatabaseReference game;

    private Button draw;

    private boolean draw_flag = false;

    private boolean request_flag = false;

    private Map<Integer, ChessPiece> getId_piece = new HashMap<>();

    private int new_ids = 17;

    private int opponent_ids = 16;

    private Vector<View> capturedPieces = new Vector<>(16);

    private int size;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black);

        try {
            getSupportActionBar().hide();
        }
        catch (Exception e){

        }
        getWindow().setStatusBarColor(Color.parseColor("#1B1B1B"));

        whiteClock = findViewById(R.id.opponent_timer);
        blackClock = findViewById(R.id.player_timer);

        Common.playerKing.new_x = 3;
        Common.playerKing.new_y = 7;

        draw = findViewById(R.id.draw);
        board = findViewById(R.id.board);

        resign = findViewById(R.id.resign);
        leave = findViewById(R.id.leave);

        blackPawn1.piece = findViewById(R.id.blackPawn1);
        blackPawn2.piece = findViewById(R.id.blackPawn2);
        blackPawn3.piece = findViewById(R.id.blackPawn3);
        blackPawn4.piece = findViewById(R.id.blackPawn4);
        blackPawn5.piece = findViewById(R.id.blackPawn5);
        blackPawn6.piece = findViewById(R.id.blackPawn6);
        blackPawn7.piece = findViewById(R.id.blackPawn7);
        blackPawn8.piece = findViewById(R.id.blackPawn8);
        blackKing.piece = findViewById(R.id.blackKing);
        blackQueen.piece = findViewById(R.id.blackQueen);
        blackKnight1.piece = findViewById(R.id.blackKnight1);
        blackKnight2.piece = findViewById(R.id.blackKnight2);
        blackBishop1.piece = findViewById(R.id.blackBishop1);
        blackBishop2.piece = findViewById(R.id.blackBishop2);
        blackRook1.piece = findViewById(R.id.blackRook1);
        blackRook2.piece = findViewById(R.id.blackRook2);

        blackPawns[0] = blackPawn1;
        blackPawns[1] = blackPawn2;
        blackPawns[2] = blackPawn3;
        blackPawns[3] = blackPawn4;
        blackPawns[4] = blackPawn5;
        blackPawns[5] = blackPawn6;
        blackPawns[6] = blackPawn7;
        blackPawns[7] = blackPawn8;

        whitePawn1.piece = findViewById(R.id.whitePawn1);
        whitePawn2.piece = findViewById(R.id.whitePawn2);
        whitePawn3.piece = findViewById(R.id.whitePawn3);
        whitePawn4.piece = findViewById(R.id.whitePawn4);
        whitePawn5.piece = findViewById(R.id.whitePawn5);
        whitePawn6.piece = findViewById(R.id.whitePawn6);
        whitePawn7.piece = findViewById(R.id.whitePawn7);
        whitePawn8.piece = findViewById(R.id.whitePawn8);
//
        whitePawns[0] = whitePawn1;
        whitePawns[1] = whitePawn2;
        whitePawns[2] = whitePawn3;
        whitePawns[3] = whitePawn4;
        whitePawns[4] = whitePawn5;
        whitePawns[5] = whitePawn6;
        whitePawns[6] = whitePawn7;
        whitePawns[7] = whitePawn8;

        whiteKing.piece = findViewById(R.id.whiteKing);
        whiteQueen.piece = findViewById(R.id.whiteQueen);
        whiteKnight1.piece = findViewById(R.id.whiteKnight1);
        whiteKnight2.piece = findViewById(R.id.whiteKnight2);
        whiteBishop1.piece = findViewById(R.id.whiteBishop1);
        whiteBishop2.piece = findViewById(R.id.whiteBishop2);
        whiteRook1.piece = findViewById(R.id.whiteRook1);
        whiteRook2.piece = findViewById(R.id.whiteRook2);

        initialBoardLocations();
        updateLocations(); //Updates the location field in each chess piece
        setOnClickListeners(); //Sets onClick listeners
        updateAttackSquares(boardLocations, attackedSquares); //Updates initial attack squares

        game = database.child(Common.code);



        //On click listener for resign, draw and leave buttons
        resign.setOnClickListener(v -> {
            if(!Common.gameOver) {
                AlertDialog.Builder builder
                        = new AlertDialog
                        .Builder(this);

                builder.setMessage("Are you sure you want to resign?");
                builder.setTitle("");
                builder.setCancelable(false);

                builder
                        .setPositiveButton(
                                "Yes",
                                (dialog, which) -> {
                                    game.child("black").child("isGameOver").setValue(1);
                                    Common.gameOver = true;

                                    stopAllTimers();
                                    Intent intent = new Intent(Black.this, Home.class);
                                    Common.gameOver = false;
                                    Common.isTimer = false;
                                    Common.time_increment = 0;
                                    Common.time.black = Common.time.white = 0;
                                    startActivity(intent);
                                    Helper.restoreViews(capturedPieces);
                                    finish();
                                });
                builder
                        .setNegativeButton(
                                "No",
                                (dialog, which) -> dialog.cancel());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        draw.setOnClickListener(v -> {
            if(!Common.gameOver) {
                request_flag = true;
                game.child("draw").setValue(-1);
                Context context = getApplicationContext();
                CharSequence text = "Draw requested!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });

        //listens for whether requested draw has been accepted or declined, or if there are any draw requests
        game.child("draw").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(draw_flag){
                    int draw = snapshot.getValue(Integer.class);
                    if(request_flag && draw == 1){
                        Common.gameOver = true;

                        stopAllTimers(); // stop the timers

                        AlertDialog.Builder builder
                                = new AlertDialog
                                .Builder(Black.this);

                        builder.setMessage("Opponent accepted the draw!");
                        builder.setTitle("");
                        builder.setCancelable(false);

                        builder
                                .setPositiveButton(
                                        "Ok",
                                        (dialog, which) -> {
                                            Intent intent = new Intent(Black.this, Home.class);
                                            Common.gameOver = false;
                                            Common.isTimer = false;
                                            Common.time_increment = 0;
                                            Common.time.black = Common.time.white = 0;
                                            startActivity(intent);
                                            Helper.restoreViews(capturedPieces);
                                            finish();
                                            dialog.cancel();
                                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                    else if(request_flag && draw == 0){
                        request_flag = false;
                        AlertDialog.Builder builder
                                = new AlertDialog
                                .Builder(Black.this);

                        builder.setMessage("Draw was declined.");
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
                    else if(!request_flag && draw == -1){
                        AlertDialog.Builder builder
                                = new AlertDialog
                                .Builder(Black.this);

                        builder.setMessage("Opponent is requesting a draw");
                        builder.setTitle("");
                        builder.setCancelable(false);

                        builder
                                .setPositiveButton(
                                        "Accept",
                                        (dialog, which) -> {
                                            game.child("draw").setValue(1);
                                            Common.gameOver = true;
                                            stopAllTimers(); // stop the timers
                                            Intent intent = new Intent(Black.this, Home.class);
                                            Common.gameOver = false;
                                            Common.isTimer = false;
                                            Common.time_increment = 0;
                                            Common.time.black = Common.time.white = 0;
                                            startActivity(intent);
                                            Helper.restoreViews(capturedPieces);
                                            finish();
                                        });
                        builder
                                .setNegativeButton(
                                        "Decline",
                                        (dialog, which) -> {
                                            game.child("draw").setValue(0);
                                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }

                }
                else{
                    draw_flag = true;
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        //Checks if the game has a timer
        game.child("timer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Common.isTimer = snapshot.getValue(Boolean.class);
                if(Common.isTimer) {
                    whiteClock.setVisibility(View.VISIBLE);
                    blackClock.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        leave.setOnClickListener(v -> {

                AlertDialog.Builder builder
                        = new AlertDialog
                        .Builder(this);

                builder.setMessage("Are you sure you want to leave the game?");
                builder.setTitle("");
                builder.setCancelable(false);

                builder
                        .setPositiveButton(
                                "Yes",
                                (dialog, which) -> {
                                    Intent intent = new Intent(this, Home.class);
                                    game.child("black").child("leave").setValue(true);
                                    Common.gameOver = false;
                                    Common.isTimer = false;
                                    Common.time_increment = 0;
                                    Common.time.black = Common.time.white = 0;
                                    Helper.restoreViews(capturedPieces);
                                    startActivity(intent);
                                    Helper.restoreViews(capturedPieces);
                                    finish();
                                });
                builder
                        .setNegativeButton(
                                "No",
                                (dialog, which) -> dialog.cancel());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

        });

        //Listens for opponent moves
        game.child("white").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(flag){
                    WhiteBlack whiteMove = snapshot.getValue(WhiteBlack.class);
                    if(whiteMove.leave){
                        AlertDialog.Builder builder
                                = new AlertDialog
                                .Builder(Black.this);
                        builder.setMessage("Your opponent has left the game");
                        builder.setTitle("");
                        builder.setCancelable(false);
                        builder
                                .setPositiveButton(
                                        "OK",
                                        (dialog, which) -> {
                                            Intent intent = new Intent(Black.this, Home.class);
                                            Common.gameOver = false;
                                            Common.isTimer = false;
                                            Common.time_increment = 0;
                                            Common.time.black = Common.time.white = 0;
                                            startActivity(intent);
                                            Helper.restoreViews(capturedPieces);
                                            finish();
                                            dialog.cancel();
                                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                    else if(whiteMove.isGameOver != 0){ //Checks if the opponent resigned or got checkmated
                        Common.gameOver = true;

                        stopAllTimers();// stop the timers

                        if(whiteMove.isGameOver == 1){
                            AlertDialog.Builder builder
                                    = new AlertDialog
                                    .Builder(Black.this);
                            builder.setMessage("You lost.");
                            builder.setTitle("");
                            builder.setCancelable(false);
                            builder
                                    .setPositiveButton(
                                            "OK",
                                            (dialog, which) -> {
                                                Intent intent = new Intent(Black.this, Home.class);
                                                Common.gameOver = false;
                                                Common.isTimer = false;
                                                Common.time_increment = 0;
                                                Common.time.black = Common.time.white = 0;
                                                startActivity(intent);
                                                finish();
                                                Helper.restoreViews(capturedPieces);
                                                dialog.cancel();
                                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                        else {
                            AlertDialog.Builder builder
                                    = new AlertDialog
                                    .Builder(Black.this);
                            builder.setMessage("You won!");
                            builder.setTitle("");
                            builder.setCancelable(false);
                            builder
                                    .setPositiveButton(
                                            "OK",
                                            (dialog, which) -> {
                                                Intent intent = new Intent(Black.this, Home.class);
                                                Common.gameOver = false;
                                                Common.isTimer = false;
                                                Common.time_increment = 0;
                                                Common.time.black = Common.time.white = 0;
                                                startActivity(intent);
                                                Helper.restoreViews(capturedPieces);
                                                finish();

                                                dialog.cancel();
                                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                    }
                    else {
                        if (Common.isTimer) {
                            Common.time.white = whiteMove.time;
                            whiteClock.setText(Helper.convertTime(Common.time.white));
                        }
                        ChessPiece piece = getPiece(whiteMove.id, whiteMove.old_id);
                        removeAttackSquares(boardLocations, attackedSquares);//removes the old attacked squares before updating with new ones

                        if(Math.abs(whiteMove.id) > opponent_ids){ //checking if pawn has been promoted by checking if there is a new piece id
                            opponent_ids++;
                            piece.id = whiteMove.id;
                            switch(whiteMove.new_name){
                                case "Queen": piece.piece.setImageResource(R.drawable.white_queen); piece.name = whiteQueen.name; break;
                                case "Rook": piece.piece.setImageResource(R.drawable.white_rook); piece.name = whiteRook1.name;break;
                                case "Bishop": piece.piece.setImageResource(R.drawable.white_bishop); piece.name = whiteBishop1.name; break;
                                case "Knight": piece.piece.setImageResource(R.drawable.white_knight); piece.name = whiteKnight1.name; break;
                            }
                            getId_piece.put(whiteMove.id, piece);
                        }

                        //Moves the opponent piece
                        Helper.moveOpponentPiece(whiteMove.old_x, whiteMove.old_y, whiteMove.new_x, whiteMove.new_y, boardLocations, attackedSquares, piece, pieceLocations);

                        //Checks if the opponent has captured a piece
                        if (whiteMove.capturedPiece_id != 0) {
                            ChessPiece captured = getId_piece.get(whiteMove.capturedPiece_id);

                            captured.captured = true;
                            captured.piece.setVisibility(View.GONE);
                            capturedPieces.add(captured.piece);
                            whiteMove.capturedPiece_id = 0;
                        }

                        //Checks if the opponent has castled
                        if (whiteMove.castle == 1) {
                            flag = false;
                            Extra rook = whiteMove.rook;
                            game.child("white").child("castle").setValue(0);
                            piece = getPieceBasedOnId(rook.id);
                            removeAttackSquares(pieceLocations, attackedSquares);
                            Helper.moveOpponentPiece(rook.old_x, rook.old_y, rook.new_x, rook.new_y, boardLocations, attackedSquares, piece, pieceLocations);
                        }

                        Common.previousMove.id = piece.id;
                        Common.previousMove.old_x = whiteMove.old_x;
                        Common.previousMove.old_y = whiteMove.old_y;
                        Common.previousMove.new_x = whiteMove.new_x;
                        Common.previousMove.new_y = whiteMove.new_y;

                        //Finally updates the attack squares after the opponent piece is moved
                        updateAttackSquares(boardLocations, attackedSquares);

                        //Checks if king under check after opponent move
                        if (attackedSquares[blackKing.location.y][blackKing.location.x] == 1) {
                            Common.underCheck = true;
                            if (isCheckmate()) { //Checks if the player got checkmated
                                print("Inside checkmate black "+ isCheckmate());
                                game.child("black").child(("isGameOver")).setValue(1);
                                Common.gameOver = true;
                                stopAllTimers();
                                AlertDialog.Builder builder
                                        = new AlertDialog
                                        .Builder(Black.this);
                                builder.setMessage("You lost.");
                                builder.setTitle("");
                                builder.setCancelable(false);
                                builder
                                        .setPositiveButton(
                                                "OK",
                                                (dialog, which) -> {
                                                    Intent intent = new Intent(Black.this, Home.class);
                                                    Common.gameOver = false;
                                                    Common.isTimer = false;
                                                    Common.time_increment = 0;
                                                    Common.time.black = Common.time.white = 0;
                                                    startActivity(intent);
                                                    Helper.restoreViews(capturedPieces);
                                                    finish();

                                                    dialog.cancel();
                                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            }


                        }

                        //If the game has timer then stop opponent timer and start player timer
                        if (Common.isTimer) {
                            stopWhiteTimer();
                            startBlackTimer();
                        }

                        black_turn = true;
                    }
                }
                else{
                    //When the opponent move listener is initialised in the beginning of the game, get all the time related details
                    if(Common.isTimer) {
                        WhiteBlack timeDetails = snapshot.getValue(WhiteBlack.class);
                        Common.isTimer = timeDetails.isTimer;
                        Common.time.white = timeDetails.time;
                        Common.time.black = timeDetails.time;
                        Common.time_increment = timeDetails.bonus;

                        whiteClock.setText(Helper.convertTime((int) (Common.time.white)));
                        blackClock.setText(Helper.convertTime((int) (Common.time.black)));
                        startWhiteTimer();
                    }
                    flag = true;
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("Failed");
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        size = board.getWidth()/8;

    }

    @Override
    public void onBackPressed(){

    }

    private void setOnClickListeners(){
        for(int i=0;i<8;++i){
            ChessPiece blackPawn = blackPawns[i];
            ChessPiece whitePawn = whitePawns[i];
            blackPawn.piece.setOnClickListener(v -> {
                if(!Common.gameOver && blackPawn.allowed && black_turn) {
                    if (selectedPiece == null) {
                        selectedPiece = blackPawn;
                        selectedPiece.piece.setBackgroundColor(Color.parseColor("#680000"));
                    } else {
                        if (selectedPiece != blackPawn) {
                            selectedPiece.piece.setBackgroundColor(Color.TRANSPARENT);
                            selectedPiece = blackPawn;
                            selectedPiece.piece.setBackgroundColor(Color.parseColor("#680000"));
                        } else {
                            selectedPiece.piece.setBackgroundColor(Color.TRANSPARENT);
                            selectedPiece = null;
                        }
                    }
                }
            });

            whitePawn.piece.setOnClickListener(v -> {
                if(selectedPiece != null){
                    capturePiece(whitePawn);
                }

            });
        }

        whiteKing.piece.setOnClickListener(v -> { });

        whiteQueen.piece.setOnClickListener(v -> {
            if(selectedPiece != null){

                capturePiece(whiteQueen);

            }

        });

        whiteBishop1.piece.setOnClickListener(v -> {
            if(selectedPiece != null){

                capturePiece(whiteBishop1);

            }

        });

        whiteBishop2.piece.setOnClickListener(v -> {
            if(selectedPiece != null){

                capturePiece(whiteBishop2);

            }

        });

        whiteKnight1.piece.setOnClickListener(v -> {
            if(selectedPiece != null){

                capturePiece(whiteKnight1);

            }

        });

        whiteKnight2.piece.setOnClickListener(v -> {
            if(selectedPiece != null){

                capturePiece(whiteKnight2);

            }

        });

        whiteRook1.piece.setOnClickListener(v -> {
            if(selectedPiece != null){

                capturePiece(whiteRook1);

            }

        });

        whiteRook2.piece.setOnClickListener(v -> {
            if(selectedPiece != null){

                capturePiece(whiteRook2);

            }

        });

        blackKing.piece.setOnClickListener(v -> {
            if(!Common.gameOver && blackKing.allowed && black_turn) {
                if (selectedPiece == null) {
                    selectedPiece = blackKing;
                    selectedPiece.piece.setBackgroundColor(Color.parseColor("#680000"));
                } else {
                    if (selectedPiece != blackKing) {
                        selectedPiece.piece.setBackgroundColor(Color.TRANSPARENT);
                        selectedPiece = blackKing;
                        selectedPiece.piece.setBackgroundColor(Color.parseColor("#680000"));
                    } else {
                        selectedPiece.piece.setBackgroundColor(Color.TRANSPARENT);
                        selectedPiece = null;
                    }
                }
            }
        });

        blackQueen.piece.setOnClickListener(v -> {
            if(!Common.gameOver && blackQueen.allowed && black_turn) {
                if (selectedPiece == null) {
                    selectedPiece = blackQueen;
                    selectedPiece.piece.setBackgroundColor(Color.parseColor("#680000"));
                } else {
                    if (selectedPiece != blackQueen) {
                        selectedPiece.piece.setBackgroundColor(Color.TRANSPARENT);
                        selectedPiece = blackQueen;
                        selectedPiece.piece.setBackgroundColor(Color.parseColor("#680000"));
                    } else {
                        selectedPiece.piece.setBackgroundColor(Color.TRANSPARENT);
                        selectedPiece = null;
                    }
                }
            }
        });

        blackBishop1.piece.setOnClickListener(v -> {
            if(!Common.gameOver && blackBishop1.allowed && black_turn) {
                if (selectedPiece == null) {
                    selectedPiece = blackBishop1;
                    selectedPiece.piece.setBackgroundColor(Color.parseColor("#680000"));
                } else {
                    if (selectedPiece != blackBishop1) {
                        selectedPiece.piece.setBackgroundColor(Color.TRANSPARENT);
                        selectedPiece = blackBishop1;
                        selectedPiece.piece.setBackgroundColor(Color.parseColor("#680000"));
                    } else {
                        selectedPiece.piece.setBackgroundColor(Color.TRANSPARENT);
                        selectedPiece = null;
                    }
                }
            }
        });

        blackBishop2.piece.setOnClickListener(v -> {
            if(!Common.gameOver && blackBishop2.allowed && black_turn) {
                if (selectedPiece == null) {
                    selectedPiece = blackBishop2;
                    selectedPiece.piece.setBackgroundColor(Color.parseColor("#680000"));
                } else {
                    if (selectedPiece != blackBishop2) {
                        selectedPiece.piece.setBackgroundColor(Color.TRANSPARENT);
                        selectedPiece = blackBishop2;
                        selectedPiece.piece.setBackgroundColor(Color.parseColor("#680000"));
                    } else {
                        selectedPiece.piece.setBackgroundColor(Color.TRANSPARENT);
                        selectedPiece = null;
                    }
                }
            }
        });

        blackKnight1.piece.setOnClickListener(v -> {
            if(!Common.gameOver && blackKnight1.allowed && black_turn) {
                if (selectedPiece == null) {
                    selectedPiece = blackKnight1;
                    selectedPiece.piece.setBackgroundColor(Color.parseColor("#680000"));
                } else {
                    if (selectedPiece != blackKnight1) {
                        selectedPiece.piece.setBackgroundColor(Color.TRANSPARENT);
                        selectedPiece = blackKnight1;
                        selectedPiece.piece.setBackgroundColor(Color.parseColor("#680000"));
                    } else {
                        selectedPiece.piece.setBackgroundColor(Color.TRANSPARENT);
                        selectedPiece = null;
                    }
                }
            }
        });

        blackKnight2.piece.setOnClickListener(v -> {
            if(!Common.gameOver && blackKnight2.allowed && black_turn) {
                if (selectedPiece == null) {
                    selectedPiece = blackKnight2;
                    selectedPiece.piece.setBackgroundColor(Color.parseColor("#680000"));
                } else {
                    if (selectedPiece != blackKnight2) {
                        selectedPiece.piece.setBackgroundColor(Color.TRANSPARENT);
                        selectedPiece = blackKnight2;
                        selectedPiece.piece.setBackgroundColor(Color.parseColor("#680000"));
                    } else {
                        selectedPiece.piece.setBackgroundColor(Color.TRANSPARENT);
                        selectedPiece = null;
                    }
                }
            }
        });

        blackRook1.piece.setOnClickListener(v -> {
            if(!Common.gameOver && blackRook1.allowed && black_turn) {
                if (selectedPiece == null) {
                    selectedPiece = blackRook1;
                    selectedPiece.piece.setBackgroundColor(Color.parseColor("#680000"));
                } else {
                    if (selectedPiece != blackRook1) {
                        selectedPiece.piece.setBackgroundColor(Color.TRANSPARENT);
                        selectedPiece = blackRook1;
                        selectedPiece.piece.setBackgroundColor(Color.parseColor("#680000"));
                    } else {
                        selectedPiece.piece.setBackgroundColor(Color.TRANSPARENT);
                        selectedPiece = null;
                    }
                }
            }
        });

        blackRook2.piece.setOnClickListener(v -> {
            if(!Common.gameOver && blackRook2.allowed && black_turn) {
                if (selectedPiece == null) {
                    selectedPiece = blackRook2;
                    selectedPiece.piece.setBackgroundColor(Color.parseColor("#680000"));
                } else {
                    if (selectedPiece != blackRook2) {
                        selectedPiece.piece.setBackgroundColor(Color.TRANSPARENT);
                        selectedPiece = blackRook2;
                        selectedPiece.piece.setBackgroundColor(Color.parseColor("#680000"));
                    } else {
                        selectedPiece.piece.setBackgroundColor(Color.TRANSPARENT);
                        selectedPiece = null;
                    }
                }
            }
        });


        board.setOnTouchListener((v, event) -> {

            // save the X,Y coordinates
            if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                lastTouchDownXY[0] = event.getX();
                lastTouchDownXY[1] = event.getY();
            }

            // let the touch event pass on to whoever needs it
            return false;
        });


        board.setOnClickListener(v -> {

            if(selectedPiece != null){
                print("coordinates - "+lastTouchDownXY[0]+" "+lastTouchDownXY[1]);
                movePiece(lastTouchDownXY[0],lastTouchDownXY[1]);
            }

        });
    }

    private void movePiece(float x, float y){
        int x_mul = ((int)(x/size)) == 8? 7:((int)(x/size));
        int y_mul = ((int)(y/size)) == 8? 7:((int)(y/size));
        print(x_mul+" "+y_mul);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) selectedPiece.piece.getLayoutParams();
        if((isUnderCheckAfterMove(selectedPiece, selectedPiece.location.x, selectedPiece.location.y, x_mul, y_mul)) || !isMovePossible(selectedPiece, x_mul, y_mul)){
            return;
        }
        removeAttackSquares(boardLocations, attackedSquares);
        if(x_mul != selectedPiece.location.x){
            params.horizontalBias = (float)(x_mul*0.14285);

        }

        if(y_mul != selectedPiece.location.y){
            params.verticalBias = (float)(y_mul*0.14285);

        }

        selectedPiece.piece.setLayoutParams(params);
        selectedPiece.firstMove = false;
        selectedPiece.piece.setBackgroundColor(Color.TRANSPARENT);
        Common.whiteBlack.capturedPiece_id = 0;
        updateBoardLocations(selectedPiece, x_mul, y_mul);
        black_turn = false;
        selectedPiece = null;



    }

    private void capturePiece(ChessPiece piece){
        ConstraintLayout.LayoutParams selectedPieceParams = (ConstraintLayout.LayoutParams) selectedPiece.piece.getLayoutParams();
        int x_mul = piece.location.x;
        int y_mul = piece.location.y;
        removeAttackSquares(boardLocations, attackedSquares);
        piece.captured = true;
        if((isUnderCheckAfterMove(selectedPiece, selectedPiece.location.x, selectedPiece.location.y, x_mul, y_mul)) || !isMovePossible(selectedPiece, x_mul, y_mul)){
            piece.captured = false;
            updateAttackSquares(boardLocations, attackedSquares);
            return;
        }

        if(x_mul != selectedPiece.location.x){
            selectedPieceParams.horizontalBias = (float)(x_mul*0.14285);

        }

        if(y_mul != selectedPiece.location.y){
            selectedPieceParams.verticalBias = (float)(y_mul*0.14285);

        }
        selectedPiece.piece.setLayoutParams(selectedPieceParams);
        selectedPiece.firstMove = false;
        selectedPiece.piece.setBackgroundColor(Color.TRANSPARENT);

        if(piece.name.equals("King") || piece.name.equals("Queen")) Common.whiteBlack.capturedPiece_id = piece.id;
        else if(piece.name.equals("Pawn")) Common.whiteBlack.capturedPiece_id = (piece.id > 0)? (9-piece.id):(-9-piece.id);
        else if(piece.id%2 == 0) Common.whiteBlack.capturedPiece_id = (piece.id > 0)?  piece.id-1: piece.id+1;
        else Common.whiteBlack.capturedPiece_id = (piece.id > 0)? piece.id+1:piece.id-1;

        boardLocations[piece.location.y][piece.location.x] = 0;

        updateBoardLocations(selectedPiece, piece.location.x, piece.location.y);
        selectedPiece = null;
        black_turn = false;
        piece.piece.setVisibility(View.GONE);
        capturedPieces.add(piece.piece);




    }

    private void updateLocations(){
        for(int i=0;i<8;++i){
            Location blackLocation = new Location();
            Location whiteLocation = new Location();
            blackLocation.x = i;
            blackLocation.y = 6;
            whiteLocation.x = i;
            whiteLocation.y = 1;
            whitePawns[i].id = i+1;
            blackPawns[i].id = -i-1;
            whitePawns[i].location = whiteLocation;
            blackPawns[i].location = blackLocation;
            blackPawns[i].name = whitePawns[i].name = "Pawn";
            pieceLocations[6][i] = -i-1;
            pieceLocations[1][i] = 9-(i+1);
            getId_piece.put(i+1, whitePawns[i]);
            getId_piece.put(-i-1, blackPawns[i]);
        }

        blackKing.location = new Location();
        blackKing.location.x = 3;
        blackKing.location.y = 7;
        pieceLocations[7][3] = -9;

        blackQueen.location = new Location();
        blackQueen.location.x = 4;
        blackQueen.location.y = 7;
        pieceLocations[7][4] = -10;

        blackBishop1.location = new Location();
        blackBishop1.location.x = 2;
        blackBishop1.location.y = 7;
        pieceLocations[7][2] = -11;

        blackBishop2.location = new Location();
        blackBishop2.location.x = 5;
        blackBishop2.location.y = 7;
        pieceLocations[7][5] = -12;

        blackKnight1.location = new Location();
        blackKnight1.location.x = 1;
        blackKnight1.location.y = 7;
        pieceLocations[7][1] = -13;

        blackKnight2.location = new Location();
        blackKnight2.location.x = 6;
        blackKnight2.location.y = 7;
        pieceLocations[7][6] = -14;

        blackRook1.location = new Location();
        blackRook1.location.x = 0;
        blackRook1.location.y = 7;
        pieceLocations[7][0] = -15;

        blackRook2.location = new Location();
        blackRook2.location.x = 7;
        blackRook2.location.y = 7;
        pieceLocations[7][7] = -16;

        whiteKing.location = new Location();
        whiteKing.location.x = 3;
        whiteKing.location.y = 0;
        pieceLocations[0][3] = 9;

        whiteQueen.location = new Location();
        whiteQueen.location.x = 4;
        whiteQueen.location.y = 0;
        pieceLocations[0][4] = 10;

        whiteBishop1.location = new Location();
        whiteBishop1.location.x = 2;
        whiteBishop1.location.y = 0;
        pieceLocations[0][2] = 11;

        whiteBishop2.location = new Location();
        whiteBishop2.location.x = 5;
        whiteBishop2.location.y = 0;
        pieceLocations[0][5] = 12;

        whiteKnight1.location = new Location();
        whiteKnight1.location.x = 1;
        whiteKnight1.location.y = 0;
        pieceLocations[0][1] = 13;

        whiteKnight2.location = new Location();
        whiteKnight2.location.x = 6;
        whiteKnight2.location.y = 0;
        pieceLocations[0][6] = 14;

        whiteRook1.location = new Location();
        whiteRook1.location.x = 0;
        whiteRook1.location.y = 0;
        pieceLocations[0][0] = 15;

        whiteRook2.location = new Location();
        whiteRook2.location.x = 7;
        whiteRook2.location.y = 0;
        pieceLocations[0][7] = 16;

        blackKing.name = whiteKing.name = "King";
        blackQueen.name = whiteQueen.name = "Queen";
        blackBishop1.name = whiteBishop1.name = "Bishop";
        blackBishop2.name = whiteBishop2.name = "Bishop";
        blackKnight1.name = whiteKnight1.name = "Knight";
        blackKnight2.name = whiteKnight2.name = "Knight";
        blackRook1.name = whiteRook1.name = "Rook";
        blackRook2.name = whiteRook2.name = "Rook";

        whiteKing.id = 9;
        blackKing.id = -9;
        whiteQueen.id = 10;
        blackQueen.id = -10;
        whiteBishop1.id = 11;
        blackBishop1.id = -11;
        whiteBishop2.id = 12;
        blackBishop2.id = -12;
        whiteKnight1.id = 13;
        blackKnight1.id = -13;
        whiteKnight2.id = 14;
        blackKnight2.id = -14;
        whiteRook1.id = 15;
        blackRook1.id = -15;
        whiteRook2.id = 16;
        blackRook2.id = -16;

        getId_piece.put(9,whiteKing);
        getId_piece.put(10,whiteQueen);
        getId_piece.put(11,whiteBishop1);
        getId_piece.put(12,whiteBishop2);
        getId_piece.put(13,whiteKnight1);
        getId_piece.put(14,whiteKnight2);
        getId_piece.put(15,whiteRook1);
        getId_piece.put(16,whiteRook2);
        getId_piece.put(-9,blackKing);
        getId_piece.put(-10,blackQueen);
        getId_piece.put(-11,blackBishop1);
        getId_piece.put(-12,blackBishop2);
        getId_piece.put(-13,blackKnight1);
        getId_piece.put(-14,blackKnight2);
        getId_piece.put(-15,blackRook1);
        getId_piece.put(-16,blackRook2);
    }

    private void updateAllAttackSquares(ChessPiece piece, int[][] boardLocations, int[][] attackedSquares){
        switch (piece.name){
            case "King": Helper.updateKingAttackSquares(piece, attackedSquares, boardLocations); break;
            case "Queen":
            case "Rook": Helper.updateQueenAndRookAttackSquares(piece, attackedSquares, boardLocations); break;
            case "Bishop": Helper.updateBishopAttackSquares(piece, attackedSquares, boardLocations); break;
            case "Knight": Helper.updateKnightAttackSquares(piece, attackedSquares, boardLocations); break;
            case "Pawn": Helper.updatePawnAttackSquares(piece, attackedSquares, boardLocations); break;
        }
    }

    private void removeAllAttackSquares(ChessPiece piece, int[][] boardLocations, int[][] attackedSquares){
        switch (piece.name){
            case "King": Helper.removeKingAttackSquares(piece, attackedSquares, boardLocations); break;
            case "Queen":
            case "Rook": Helper.removeQueenAndRookAttackSquares(piece, attackedSquares, boardLocations); break;
            case "Bishop": Helper.removeBishopAttackSquares(piece, attackedSquares, boardLocations); break;
            case "Knight": Helper.removeKnightAttackSquares(piece, attackedSquares, boardLocations); break;
            case "Pawn": Helper.removePawnAttackSquares(piece, attackedSquares, boardLocations); break;
        }
    }


    private void updateAttackSquares(int[][] boardLocations, int[][] attackedSquares){
        for(ChessPiece piece: getId_piece.values()){

            if(piece.id > 0 && !piece.captured) updateAllAttackSquares(piece, boardLocations, attackedSquares);
        }
    }

    private void removeAttackSquares(int[][] boardLocations, int[][] attackedSquares){
        for(ChessPiece piece: getId_piece.values()){

            if(piece.id > 0 && !piece.captured) removeAllAttackSquares(piece, boardLocations, attackedSquares);
        }

    }

    private void initialBoardLocations(){
        for(int i=0;i<8;++i){
            for(int j=0;j<8;++j){
                boardLocations[i][j] = 0;
                pieceLocations[i][j] = 0;
            }
        }

        for(int i=0;i<2;++i){
            for(int j=0; j<8; ++j){
                boardLocations[i][j] = 1;
                boardLocations[7-i][j] = -1;
            }
        }

    }

    private void updateBoardLocations(ChessPiece piece, int new_x, int new_y){
        if(new_y == 0 && piece.name.equals("Pawn")){
            pawnPromotion(piece, new_x, new_y);

            updateAttackSquares(boardLocations, attackedSquares);
        }
        else {
            DatabaseReference game = database.child(Common.code);
            Common.whiteBlack.old_x = (7 - piece.location.x);
            Common.whiteBlack.old_y = (7 - piece.location.y);

            boardLocations[piece.location.y][piece.location.x] = 0;
            boardLocations[new_y][new_x] = -1;

            int x = piece.location.x;
            int y = piece.location.y;

            piece.location.x = new_x;
            piece.location.y = new_y;

            Common.whiteBlack.new_x = (7 - piece.location.x);
            Common.whiteBlack.new_y = (7 - piece.location.y);

            if (piece.name.equals("King") || piece.name.equals("Queen")){
                Common.whiteBlack.id = piece.id;
                Common.playerKing.new_x = new_x;
                Common.playerKing.new_y = new_y;
            }
            else if (piece.name.equals("Pawn"))
                Common.whiteBlack.id = (piece.id > 0) ? (9 - piece.id) : (-9 - piece.id);
            else if (piece.id % 2 == 0)
                Common.whiteBlack.id = (piece.id > 0) ? piece.id - 1 : piece.id + 1;
            else Common.whiteBlack.id = (piece.id > 0) ? piece.id + 1 : piece.id - 1;

            if (Common.isTimer) stopBlackTimer();

            Common.whiteBlack.time = Common.time.black;

            if(piece.name.equals("Pawn")){
                if(getId_piece.containsKey(Common.previousMove.id) && getId_piece.get(Common.previousMove.id).name.equals("Pawn")){
                    if(new_x == Common.previousMove.new_x && (new_x == x-1|| new_x == x+1)){
                        if(Common.previousMove.old_y == 1 && Common.previousMove.new_y == 3){
                            ChessPiece enpassentPiece = getId_piece.get(Common.previousMove.id);
                            boardLocations[enpassentPiece.location.y][enpassentPiece.location.x] = 0;
                            enpassentPiece.piece.setVisibility(View.GONE);
                            enpassentPiece.captured = true;
                            Common.whiteBlack.capturedPiece_id = (Common.previousMove.id > 0)? (9-Common.previousMove.id):(-9-Common.previousMove.id);
                            capturedPieces.add(enpassentPiece.piece);
                        }
                    }
                }
            }

            game.child("black").setValue(Common.whiteBlack);
            updateAttackSquares(boardLocations, attackedSquares);
            if (attackedSquares[blackKing.location.y][blackKing.location.x] == 0) {
                Common.underCheck = false;

            }
            if (Common.isTimer) startWhiteTimer();
        }
    }

    private boolean isMovePossible(ChessPiece piece, int new_x, int new_y){
        boolean possible = false;
        switch(piece.name){
            case "King": possible =  Helper.isPossibleKing(piece,blackRook1, blackRook2, boardLocations, attackedSquares, pieceLocations, new_x, new_y, "black"); break;
            case "Queen": possible = Helper.isPossibleQueenAndRook(piece, boardLocations, attackedSquares, new_x, new_y); break;
            case "Bishop": possible = Helper.isPossibleBishop(piece, boardLocations, attackedSquares, new_x, new_y); break;
            case "Knight": possible = Helper.isPossibleKnight(piece, boardLocations, attackedSquares, new_x, new_y); break;
            case "Rook" : possible = Helper.isPossibleRook(piece, boardLocations, attackedSquares, new_x, new_y); break;
            case "Pawn" : possible = Helper.isPossiblePawn(piece, boardLocations, attackedSquares, new_x, new_y, getId_piece); break;
        }
        return possible;
    }

    private void updateAttackSquare(ChessPiece piece){
        switch (piece.name){
            case "Pawn": Helper.updatePawnAttackSquares(piece, attackedSquares, boardLocations); break;
            case "King": Helper.updateKingAttackSquares(piece, attackedSquares, boardLocations); break;
            case "Queen":
            case "Rook":
                Helper.updateQueenAndRookAttackSquares(piece, attackedSquares, boardLocations); break;
            case "Bishop": Helper.updateBishopAttackSquares(piece, attackedSquares, boardLocations); break;
            case "Knight": Helper.updateKnightAttackSquares(piece, attackedSquares, boardLocations); break;
        }
    }

    private ChessPiece getPieceBasedOnId(int id){
        ChessPiece piece = null;
        switch (id){
            case -1: piece = blackPawn1; break;
            case -2: piece = blackPawn2; break;
            case -3: piece = blackPawn3; break;
            case -4: piece = blackPawn4; break;
            case -5: piece = blackPawn5; break;
            case -6: piece = blackPawn6; break;
            case -7: piece = blackPawn7; break;
            case -8: piece = blackPawn8; break;
            case -9: piece = blackKing; break;
            case -10: piece = blackQueen; break;
            case -11: piece = blackBishop1; break;
            case -12: piece = blackBishop2; break;
            case -13: piece = blackKnight1; break;
            case -14: piece = blackKnight2; break;
            case -15: piece = blackRook1; break;
            case -16: piece = blackRook2; break;
            case 1: piece = whitePawn1; break;
            case 2: piece = whitePawn2; break;
            case 3: piece = whitePawn3; break;
            case 4: piece = whitePawn4; break;
            case 5: piece = whitePawn5; break;
            case 6: piece = whitePawn6; break;
            case 7: piece = whitePawn7; break;
            case 8: piece = whitePawn8; break;
            case 9: piece = whiteKing; break;
            case 10: piece = whiteQueen; break;
            case 11: piece = whiteBishop1; break;
            case 12: piece = whiteBishop2; break;
            case 13: piece = whiteKnight1; break;
            case 14: piece = whiteKnight2; break;
            case 15: piece = whiteRook1; break;
            case 16: piece = whiteRook2; break;
        }
        return piece;
    }

    private boolean isCheckmate(){
        boolean checkmate = true;
        for(ChessPiece piece:getId_piece.values()){
            if(piece.id < 0 && !piece.captured) checkmate = !checkMovesPossible(piece);
            if(!checkmate) return checkmate;
        }
        return checkmate;
    }

    private boolean checkBishopPossible(ChessPiece piece){
        int x = piece.location.x;
        int y = piece.location.y;

        int tempX = x;
        int tempY = y;
        ++tempX;
        ++tempY;
        while(tempX < 8 && tempY < 8){
            if((Common.attackingPiece.new_x == tempX && Common.attackingPiece.new_y == tempY)) return true;
            if(boardLocations[tempY][tempX] != 0) break;
            if(!isUnderCheckAfterMove(piece, x,y,tempX, tempY)) return true;
            ++tempX;
            ++tempY;
        }

        tempX = x;
        tempY = y;
        --tempX;
        ++tempY;
        while(tempX >= 0 && tempY < 8){
            if((Common.attackingPiece.new_x == tempX && Common.attackingPiece.new_y == tempY)) return true;
            if(boardLocations[tempY][tempX] != 0) break;
            if(!isUnderCheckAfterMove(piece, x,y,tempX, tempY)) return true;
            --tempX;
            ++tempY;
        }

        tempX = x;
        tempY = y;
        --tempX;
        --tempY;
        while(tempX >= 0 && tempY >= 0){
            if((Common.attackingPiece.new_x == tempX && Common.attackingPiece.new_y == tempY)) return true;
            if(boardLocations[tempY][tempX] != 0) break;
            if(!isUnderCheckAfterMove(piece, x,y,tempX, tempY)) return true;
            --tempX;
            --tempY;
        }

        tempX = x;
        tempY = y;
        ++tempX;
        --tempY;
        while(tempX < 8 && tempY >= 0){

            if((Common.attackingPiece.new_x == tempX && Common.attackingPiece.new_y == tempY)) return true;
            if(boardLocations[tempY][tempX] != 0) break;
            if(!isUnderCheckAfterMove(piece, x,y,tempX, tempY)) return true;
            ++tempX;
            --tempY;
        }
        return false;
    }

    private boolean checkQueenAndRookPossible(ChessPiece piece){
        int x = piece.location.x;
        int y = piece.location.y;

        int tempX = x;
        int tempY = y;

        while(tempX < 8){
            if((Common.attackingPiece.new_x == tempX && Common.attackingPiece.new_y == tempY)) return true;
            if(boardLocations[tempY][tempX] != 0) break;
            if(!isUnderCheckAfterMove(piece, x,y,tempX, tempY)) return true;
            ++tempX;
        }

        tempX = x;
        ++tempY;
        while(tempY < 8){
            if((Common.attackingPiece.new_x == tempX && Common.attackingPiece.new_y == tempY)) return true;
            if(boardLocations[tempY][tempX] != 0) break;
            if(!isUnderCheckAfterMove(piece, x,y,tempX, tempY)) return true;
            ++tempY;
        }

        tempY = y;
        --tempX;
        while(tempX >= 0){
            if((Common.attackingPiece.new_x == tempX && Common.attackingPiece.new_y == tempY)) return true;
            if(boardLocations[tempY][tempX] != 0) break;
            if(!isUnderCheckAfterMove(piece, x,y,tempX, tempY)) return true;
            --tempX;
        }

        tempX = x;
        --tempY;
        while(tempY >= 0){
            if((Common.attackingPiece.new_x == tempX && Common.attackingPiece.new_y == tempY)) return true;
            if(boardLocations[tempY][tempX] != 0) break;
            if(!isUnderCheckAfterMove(piece, x,y,tempX, tempY)) return true;
            --tempY;
        }

        if(piece.name.equals("Rook")) return false;

        return checkBishopPossible(piece);
    }

    private boolean checkKnightPossible(ChessPiece piece){
        int x = piece.location.x;
        int y = piece.location.y;

        if(x+2 < 8 && y+1 < 8){
            if((boardLocations[y+1][x+2] != -1 && !isUnderCheckAfterMove(piece, x, y, x+2, y+1)) || (Common.attackingPiece.new_x == x+2 && Common.attackingPiece.new_y == y+1)) return true;
        }

        if(x+2 < 8 && y-1 >= 0){
            if((boardLocations[y-1][x+2] != -1 && !isUnderCheckAfterMove(piece, x, y, x+2, y-1)) || (Common.attackingPiece.new_x == x+2 && Common.attackingPiece.new_y == y-1)) return true;
        }

        if(x-2 >= 0 && y+1 < 8){
            if((boardLocations[y+1][x-2] != -1 && !isUnderCheckAfterMove(piece, x, y, x-2, y+1))|| (Common.attackingPiece.new_x == x-2 && Common.attackingPiece.new_y == y+1)) return true;
        }

        if(x-2 >= 0 && y-1 >= 0){
            if((boardLocations[y-1][x-2] != -1 && !isUnderCheckAfterMove(piece, x, y, x-2, y-1)) || (Common.attackingPiece.new_x == x-2 && Common.attackingPiece.new_y == y-1)) return true;
        }

        if(x+1 < 8 && y+2 < 8){
            if((boardLocations[y+2][x+1] != -1 && !isUnderCheckAfterMove(piece, x, y, x+1, y+2)) || (Common.attackingPiece.new_x == x+1 && Common.attackingPiece.new_y == y+2)) return true;
        }

        if(x+1 < 8 && y-2 >= 0){
            if((boardLocations[y-2][x+1] != -1 && !isUnderCheckAfterMove(piece, x, y, x+1, y-2)) || (Common.attackingPiece.new_x == x+1 && Common.attackingPiece.new_y == y-2)) return true;
        }

        if(x-1 >= 0 && y+2 < 8){
            if((boardLocations[y+2][x-1] != -1 && !isUnderCheckAfterMove(piece, x, y, x-1, y+2)) || (Common.attackingPiece.new_x == x-1 && Common.attackingPiece.new_y == y+2)) return true;
        }

        if(x-1 >= 0 && y-2 >= 0){
            if((boardLocations[y-2][x-1] != -1 && !isUnderCheckAfterMove(piece, x, y, x-1, y-2)) || (Common.attackingPiece.new_x == x-1 && Common.attackingPiece.new_y == y-2)) return true;
        }
        return false;
    }

    private boolean checkPawnPossible(ChessPiece piece){
        int x = piece.location.x;
        int y = piece.location.y;

        if(piece.firstMove){
            if(y-2 >= 0 && boardLocations[y-2][x] == 0 && !isUnderCheckAfterMove(piece, x, y, x, y-2)) return true;
        }
        if(y-1 >= 0 && boardLocations[y-1][x] == 0 && !isUnderCheckAfterMove(piece, x, y, x, y-1)) return true;
        if(y-1 >= 0 && x-1 >= 0 && boardLocations[y-1][x-1] == 1){
            if(!isUnderCheckAfterMove(piece,x,y,x-1,y-1) || (Common.attackingPiece.new_x == x-1 && Common.attackingPiece.new_y == y-1)) return true;
        }
        if(y-1 >=0 && x+1 < 8 && boardLocations[y-1][x+1] == 1){
            if(!isUnderCheckAfterMove(piece,x,y,x+1,y-1) || (Common.attackingPiece.new_x == x+1 && Common.attackingPiece.new_y == y-1)) return true;
        }
        return false;
    }

    private boolean isUnderCheckAfterMove(ChessPiece piece, int old_x, int old_y, int new_x, int new_y){
        int[][] temp_board = new int[8][8];
        int[][] temp_attack = new int[8][8];
        for(int i=0;i<8;++i){
            for(int j=0;j<8;++j){
                temp_board[i][j] = boardLocations[i][j];
                temp_attack[i][j] = attackedSquares[i][j];
            }
        }
        removeAttackSquares(temp_board, temp_attack);
        temp_board[old_y][old_x] = 0;
        temp_board[new_y][new_x] = -1;
        updateAttackSquares(temp_board, temp_attack);

        if(piece.name.equals("King") && temp_attack[new_y][new_x] == 0) return false;
        if(temp_attack[blackKing.location.y][blackKing.location.x] == 0){
            return false;
        }
        return true;
    }

    private boolean checkKingPossible(ChessPiece piece){
        int x = piece.location.x;
        int y = piece.location.y;

        if(piece.firstMove){
            if(y == 7){
                if(x+2 < 8 && boardLocations[y][x+1] == 0 && boardLocations[y][x+2] == 0 && whiteRook2.firstMove && attackedSquares[y][x+1] == 0 && attackedSquares[y][x+2] == 0){
                    if(!isUnderCheckAfterMove(piece, x, y, x+2, y)) return true;
                }
                else if(x-2 >= 0 && boardLocations[y][x-1] == 0 && boardLocations[y][x-2]== 0 && whiteRook1.firstMove && attackedSquares[y][x-1] == 0 && attackedSquares[y][x-2] == 0){
                    if(!isUnderCheckAfterMove(piece, x, y, x-2, y)) return true;
                }
            }
        }
        if(x-1 >= 0 && y+1 < 8 && boardLocations[y+1][x-1] != -1 && attackedSquares[y+1][x-1] == 0) if(!isUnderCheckAfterMove(piece, x, y, x-1, y+1)) return true;
        if(x+1 < 8 && y+1 < 8 && boardLocations[y+1][x+1] != -1 && attackedSquares[y+1][x+1] == 0) if(!isUnderCheckAfterMove(piece, x, y, x+1, y+1)) return true;
        if(x-1 >= 0 && y-1 >= 0 && boardLocations[y-1][x-1] != -1 && attackedSquares[y-1][x-1] == 0) if(!isUnderCheckAfterMove(piece, x, y, x-1, y-1)) return true;
        if(x+1 < 8 && y-1 >= 0 && boardLocations[y-1][x+1] != -1 && attackedSquares[y-1][x+1] == 0) if(!isUnderCheckAfterMove(piece, x, y, x+1, y-1)) return true;
        if(y-1 >= 0 && boardLocations[y-1][x] != -1 && attackedSquares[y-1][x] == 0) if(!isUnderCheckAfterMove(piece, x, y, x, y-1)) return true;
        if(y+1 < 8 && boardLocations[y+1][x] != -1 && attackedSquares[y+1][x] == 0) if(!isUnderCheckAfterMove(piece, x, y, x, y+1)) return true;
        if(x-1 >= 0 && boardLocations[y][x-1] != -1 && attackedSquares[y][x-1] == 0) if(!isUnderCheckAfterMove(piece, x, y, x-1, y)) return true;
        if(x+1 < 8 && boardLocations[y][x+1] != -1 && attackedSquares[y][x+1] == 0) if(!isUnderCheckAfterMove(piece, x, y, x+1, y)) return true;
        return false;
    }

    private boolean checkMovesPossible(ChessPiece piece){
        boolean allowed = true;
        switch (piece.name){
            case "Pawn": allowed = checkPawnPossible(piece); break;
            case "King": allowed = checkKingPossible(piece); break;
            case "Queen":
            case "Rook":
                allowed = checkQueenAndRookPossible(piece); break;
            case "Bishop": allowed = checkBishopPossible(piece); break;
            case "Knight": allowed = checkKnightPossible(piece); break;
        }
        return allowed;
    }

    private void allowAllMoves(){
        for(int i=0; i<8; ++i){
            blackPawns[i].allowed = true;
        }
        blackKing.allowed = true;
        blackQueen.allowed = true;
        blackBishop1.allowed = true;
        blackBishop2.allowed = true;
        blackKnight1.allowed = true;
        blackKnight2.allowed = true;
        blackRook1.allowed = true;
        blackRook2.allowed = true;
    }

    private void print(Object text){
        System.out.println(text);
    }

    private void startWhiteTimer(){

        whiteTimer = new CountDownTimer(Common.time.white*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                Common.time.white = (int)(millisUntilFinished/1000);

                whiteClock.setText(Helper.convertTime((int)(millisUntilFinished/1000)));

            }

            @Override
            public void onFinish() {

            }
        };
        whiteTimer.start();
    }

    private void startBlackTimer(){
        if(addIncrementBlack) Common.time.black += Common.time_increment;
        blackTimer = new CountDownTimer(Common.time.black*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                Common.time.black = (int)(millisUntilFinished/1000);
                addIncrementBlack = true;
                blackClock.setText(Helper.convertTime((int)(millisUntilFinished/1000)));

            }

            @Override
            public void onFinish() {
                game.child("black").child("isGameOver").setValue(-1);
                Common.gameOver = true;
                AlertDialog.Builder builder
                        = new AlertDialog
                        .Builder(Black.this);
                builder.setMessage("You lost.");
                builder.setTitle("");
                builder.setCancelable(false);
                builder
                        .setPositiveButton(
                                "OK",
                                (dialog, which) -> {
                                    dialog.cancel();
                                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        };
        blackTimer.start();
    }

    private void stopWhiteTimer(){
        whiteTimer.cancel();
    }

    private void stopBlackTimer(){
        blackTimer.cancel();
    }

    private void stopAllTimers(){
        try{
            stopWhiteTimer();
        }
        catch (Exception e){

        }
        try{
            stopBlackTimer();
        }
        catch (Exception e){

        }
    }

    private void pawnPromotion(ChessPiece piece, int new_x, int new_y){
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(this);

        builder.setTitle("Promote to");
        builder.setCancelable(false);
        builder
                .setItems(R.array.promote, (dialog, which) -> {


                    DatabaseReference game = database.child(Common.code);
                    Common.whiteBlack.old_x = (7 - piece.location.x);
                    Common.whiteBlack.old_y = (7 - piece.location.y);
                    boardLocations[piece.location.y][piece.location.x] = 0;
                    boardLocations[new_y][new_x] = -1;

                    piece.location.x = new_x;
                    piece.location.y = new_y;

                    Common.whiteBlack.new_x = (7 - piece.location.x);
                    Common.whiteBlack.new_y = (7 - piece.location.y);

                    if (piece.name.equals("King") || piece.name.equals("Queen"))
                        Common.whiteBlack.id = piece.id;
                    else if (piece.name.equals("Pawn"))
                        Common.whiteBlack.id = (piece.id > 0) ? (9 - piece.id) : (-9 - piece.id);
                    else if (piece.id % 2 == 0)
                        Common.whiteBlack.id = (piece.id > 0) ? piece.id - 1 : piece.id + 1;
                    else Common.whiteBlack.id = (piece.id > 0) ? piece.id + 1 : piece.id - 1;

                    Common.whiteBlack.old_id = Common.whiteBlack.id;
                    Common.whiteBlack.id = piece.id = -new_ids;
                    new_ids++;


                    switch(which){
                        case 0: piece.piece.setImageResource(R.drawable.black_queen); piece.name = blackQueen.name; break;
                        case 1: piece.piece.setImageResource(R.drawable.black_rook); piece.name = blackRook1.name;break;
                        case 2: piece.piece.setImageResource(R.drawable.black_bishop); piece.name = blackBishop1.name; break;
                        case 3: piece.piece.setImageResource(R.drawable.black_knight); piece.name = blackKnight1.name; break;
                    }
                    Common.whiteBlack.new_name = piece.name;
                    getId_piece.put(piece.id, piece);

                    if (Common.isTimer) stopBlackTimer();


                    Common.whiteBlack.time = Common.time.white;
                    game.child("black").setValue(Common.whiteBlack);
                    updateAttackSquares(boardLocations, attackedSquares);
                    if (attackedSquares[whiteKing.location.y][whiteKing.location.x] == 0) {
                        Common.underCheck = false;

                    }

                    if (Common.isTimer) startWhiteTimer();
                    dialog.cancel();
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private ChessPiece getPiece(int id, int old_id){
        if(getId_piece.containsKey(id)){
            return getId_piece.get(id);
        }
        return getId_piece.get(old_id);
    }

    private void deleteCode(){
        game.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                snapshot.getRef().removeValue();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

}