package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class White extends AppCompatActivity {

    private ImageView board;

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

    private boolean white_turn = true;

    private ChessPiece selectedPiece = null;

    private float[] lastTouchDownXY = new float[2];

    private ChessPiece[] blackPawns = new ChessPiece[8];

    private ChessPiece[] whitePawns = new ChessPiece[8];

    private int[][] boardLocations = new int[8][8];

    private int[][] attackedSquares = new int[8][8];

    private int[][] pieceLocations = new int[8][8];

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_white);

//        onBackPressed();
        //Initialise views
        board = findViewById(R.id.board);
        resign = findViewById(R.id.resign);
        leave = findViewById(R.id.leave);
        draw = findViewById(R.id.draw);
        whiteClock = findViewById(R.id.player_timer);
        blackClock = findViewById(R.id.opponent_timer);

        whitePawn1.piece = findViewById(R.id.whitePawn1);
        whitePawn2.piece = findViewById(R.id.whitePawn2);
        whitePawn3.piece = findViewById(R.id.whitePawn3);
        whitePawn4.piece = findViewById(R.id.whitePawn4);
        whitePawn5.piece = findViewById(R.id.whitePawn5);
        whitePawn6.piece = findViewById(R.id.whitePawn6);
        whitePawn7.piece = findViewById(R.id.whitePawn7);
        whitePawn8.piece = findViewById(R.id.whitePawn8);
        whiteKing.piece = findViewById(R.id.whiteKing);
        whiteQueen.piece = findViewById(R.id.whiteQueen);
        whiteKnight1.piece = findViewById(R.id.whiteKnight1);
        whiteKnight2.piece = findViewById(R.id.whiteKnight2);
        whiteBishop1.piece = findViewById(R.id.whiteBishop1);
        whiteBishop2.piece = findViewById(R.id.whiteBishop2);
        whiteRook1.piece = findViewById(R.id.whiteRook1);
        whiteRook2.piece = findViewById(R.id.whiteRook2);

        whitePawns[0] = whitePawn1;
        whitePawns[1] = whitePawn2;
        whitePawns[2] = whitePawn3;
        whitePawns[3] = whitePawn4;
        whitePawns[4] = whitePawn5;
        whitePawns[5] = whitePawn6;
        whitePawns[6] = whitePawn7;
        whitePawns[7] = whitePawn8;

        blackPawn1.piece = findViewById(R.id.blackPawn1);
        blackPawn2.piece = findViewById(R.id.blackPawn2);
        blackPawn3.piece = findViewById(R.id.blackPawn3);
        blackPawn4.piece = findViewById(R.id.blackPawn4);
        blackPawn5.piece = findViewById(R.id.blackPawn5);
        blackPawn6.piece = findViewById(R.id.blackPawn6);
        blackPawn7.piece = findViewById(R.id.blackPawn7);
        blackPawn8.piece = findViewById(R.id.blackPawn8);
//
        blackPawns[0] = blackPawn1;
        blackPawns[1] = blackPawn2;
        blackPawns[2] = blackPawn3;
        blackPawns[3] = blackPawn4;
        blackPawns[4] = blackPawn5;
        blackPawns[5] = blackPawn6;
        blackPawns[6] = blackPawn7;
        blackPawns[7] = blackPawn8;

        blackKing.piece = findViewById(R.id.blackKing);
        blackQueen.piece = findViewById(R.id.blackQueen);
        blackKnight1.piece = findViewById(R.id.blackKnight1);
        blackKnight2.piece = findViewById(R.id.blackKnight2);
        blackBishop1.piece = findViewById(R.id.blackBishop1);
        blackBishop2.piece = findViewById(R.id.blackBishop2);
        blackRook1.piece = findViewById(R.id.blackRook1);
        blackRook2.piece = findViewById(R.id.blackRook2);

        initialBoardLocations();
        updateLocations(); //Updates the location field in each chess piece
        setOnClickListeners(); //Sets onClick listeners
        updateAttackSquares(boardLocations, attackedSquares); //Updates initial attack squares



        game = database.child(Common.code);

        //Checks if the game has a timer
        game.child("timer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Common.isTimer = snapshot.getValue(Boolean.class);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

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
                                    game.child("white").child("isGameOver").setValue(-1);

                                    stopAllTimers();// stop the timers

                                    Common.gameOver = true;
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

                        stopAllTimers();// stop the timers

                        AlertDialog.Builder builder
                                = new AlertDialog
                                .Builder(White.this);

                        builder.setMessage("Opponent accepted the draw!");
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
                    else if(request_flag && draw == 0){
                        request_flag = false;
                        AlertDialog.Builder builder
                                = new AlertDialog
                                .Builder(White.this);

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
                                .Builder(White.this);

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

        leave.setOnClickListener(v -> {
            if(Common.gameOver){
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
                                    Common.gameOver = false;
                                    Common.isTimer = false;
                                    Common.time_increment = 0;
                                    Common.time.black = Common.time.white = 0;
                                    startActivity(intent);
                                });
                builder
                        .setNegativeButton(
                                "No",
                                (dialog, which) -> dialog.cancel());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
            else{
                AlertDialog.Builder builder
                        = new AlertDialog
                        .Builder(this);

                builder.setMessage("Match is still in progress");
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
        });

        //Listens for opponent moves
        game.child("black").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(flag){
                    WhiteBlack blackMove = snapshot.getValue(WhiteBlack.class);
                    if(blackMove.isGameOver != 0){ //Checks if the opponent resigned or got checkmated
                        Common.gameOver = true;

                        stopAllTimers();// stop the timers

                        if(blackMove.isGameOver == -1){
                            AlertDialog.Builder builder
                                    = new AlertDialog
                                    .Builder(White.this);
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
                        else {
                            AlertDialog.Builder builder
                                    = new AlertDialog
                                    .Builder(White.this);
                            builder.setMessage("You won!");
                            builder.setTitle("");
                            builder.setCancelable(false);
                            builder
                                    .setPositiveButton(
                                            "OK",
                                            (dialog, which) -> {
                                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                    }
                    else {
                        if (Common.isTimer) {
                            Common.time.black = blackMove.time;
                            blackClock.setText(Helper.convertTime(Common.time.black));
                        }

                        ChessPiece piece = getPieceBasedOnId(blackMove.id);
                        removeAttackSquares(boardLocations, attackedSquares);//removes the old attacked squares before updating with new ones
                        //Moves the opponent piece
                        Helper.moveOpponentPiece(blackMove.old_x, blackMove.old_y, blackMove.new_x, blackMove.new_y, boardLocations, attackedSquares, piece, pieceLocations);

                        //Checks if the opponent has captured a piece
                        if (blackMove.capturedPiece_id != 0) {
                            ChessPiece captured = getPieceBasedOnId(blackMove.capturedPiece_id);
                            Helper.removeAttackSquare(captured, attackedSquares, boardLocations);

                            captured.piece.setVisibility(View.GONE);

                            blackMove.capturedPiece_id = 0;
                        }

                        //Checks if the opponent has castled
                        if (blackMove.castle == 1) {
                            flag = false;
                            Extra rook = blackMove.rook;
                            game.child("black").child("castle").setValue(0);
                            piece = getPieceBasedOnId(rook.id);

                            Helper.moveOpponentPiece(rook.old_x, rook.old_y, rook.new_x, rook.new_y, boardLocations, attackedSquares, piece, pieceLocations);
                        }

                        //Finally updates the attack squares after the opponent piece is moved
                        updateAttackSquares(boardLocations, attackedSquares);

                        //Checks if king under check after opponent move
                        if (attackedSquares[whiteKing.location.y][whiteKing.location.x] == 1) {
                            Common.underCheck = true;
//                            print("King under check");
                            if (isCheckmate()) { //Checks if the player got checkmated
                                game.child("white").child("isGameOver").setValue(-1);
                                Common.gameOver = true;

                                stopAllTimers();

                                AlertDialog.Builder builder
                                        = new AlertDialog
                                        .Builder(White.this);
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

                        }

                        //If the game has timer then stop opponent timer and start player timer
                        if (Common.isTimer) {
                            stopBlackTimer();
                            startWhiteTimer();
                        }
                        white_turn = true;
                    }
                }
                else{
                    //When the opponent move listener is initialised in the beginning of the game, get all the time related details
                    if(Common.isTimer){
                        WhiteBlack timeDetails = snapshot.getValue(WhiteBlack.class);

                        Common.isTimer = timeDetails.isTimer;
                        Common.time.white = timeDetails.time;
                        Common.time.black = timeDetails.time;
                        Common.time_increment = timeDetails.bonus;

                        whiteClock.setText(Helper.convertTime((int)(Common.time.white)));
                        blackClock.setText(Helper.convertTime((int)(Common.time.black)));
                        startWhiteTimer();
                    }
                    flag = true;
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    //Overriding to prevent pressing back button to change the activity
    @Override
    public void onBackPressed(){

    }

    private void setOnClickListeners(){
        for(int i=0;i<8;++i){
            ChessPiece whitePawn = whitePawns[i];
            ChessPiece blackPawn = blackPawns[i];
            whitePawn.piece.setOnClickListener(v -> {
                if(!Common.gameOver && whitePawn.allowed && white_turn) {
                    if (selectedPiece == null) {
                        selectedPiece = whitePawn;
                        selectedPiece.piece.setBackgroundColor(Color.parseColor("#0A6224"));
                    } else {
                        if (selectedPiece != whitePawn) {
                            selectedPiece.piece.setBackgroundColor(Color.TRANSPARENT);
                            selectedPiece = whitePawn;
                            selectedPiece.piece.setBackgroundColor(Color.parseColor("#0A6224"));
                        } else {
                            selectedPiece.piece.setBackgroundColor(Color.TRANSPARENT);
                            selectedPiece = null;
                        }
                    }
                }
            });

            blackPawn.piece.setOnClickListener(v -> {
                if(selectedPiece != null){
                    capturePiece(blackPawn);
                }

            });
        }

        blackKing.piece.setOnClickListener(v -> { });

        blackQueen.piece.setOnClickListener(v -> {
            if(selectedPiece != null){

                capturePiece(blackQueen);
//                blackQueen.setVisibility(View.GONE);
            }

        });

        blackBishop1.piece.setOnClickListener(v -> {
            if(selectedPiece != null){

                capturePiece(blackBishop1);
//                blackBishop1.setVisibility(View.GONE);
            }

        });

        blackBishop2.piece.setOnClickListener(v -> {
            if(selectedPiece != null){

                capturePiece(blackBishop2);
//                blackBishop2.setVisibility(View.GONE);
            }

        });

        blackKnight1.piece.setOnClickListener(v -> {
            if(selectedPiece != null){

                capturePiece(blackKnight1);
//                blackKnight1.setVisibility(View.GONE);
            }

        });

        blackKnight2.piece.setOnClickListener(v -> {
            if(selectedPiece != null){

                capturePiece(blackKnight2);
//                blackKnight2.setVisibility(View.GONE);
            }

        });

        blackRook1.piece.setOnClickListener(v -> {
            if(selectedPiece != null){

                capturePiece(blackRook1);
//                blackRook1.setVisibility(View.GONE);
            }

        });

        blackRook2.piece.setOnClickListener(v -> {
            if(selectedPiece != null){

                capturePiece(blackRook2);
//                blackRook2.setVisibility(View.GONE);
            }

        });

        whiteKing.piece.setOnClickListener(v -> {
            if(!Common.gameOver && whiteKing.allowed && white_turn) {
                if (selectedPiece == null) {
                    selectedPiece = whiteKing;
                    selectedPiece.piece.setBackgroundColor(Color.parseColor("#0A6224"));
                } else {
                    if (selectedPiece != whiteKing) {
                        selectedPiece.piece.setBackgroundColor(Color.TRANSPARENT);
                        selectedPiece = whiteKing;
                        selectedPiece.piece.setBackgroundColor(Color.parseColor("#0A6224"));
                    } else {
                        selectedPiece.piece.setBackgroundColor(Color.TRANSPARENT);
                        selectedPiece = null;
                    }
                }
            }
        });

        whiteQueen.piece.setOnClickListener(v -> {
            if(!Common.gameOver && whiteQueen.allowed && white_turn) {
                if (selectedPiece == null) {
                    selectedPiece = whiteQueen;
                    selectedPiece.piece.setBackgroundColor(Color.parseColor("#0A6224"));
                } else {
                    if (selectedPiece != whiteQueen) {
                        selectedPiece.piece.setBackgroundColor(Color.TRANSPARENT);
                        selectedPiece = whiteQueen;
                        selectedPiece.piece.setBackgroundColor(Color.parseColor("#0A6224"));
                    } else {
                        selectedPiece.piece.setBackgroundColor(Color.TRANSPARENT);
                        selectedPiece = null;
                    }
                }
            }
        });

        whiteBishop1.piece.setOnClickListener(v -> {
            if(!Common.gameOver && whiteBishop1.allowed && white_turn) {
                if (selectedPiece == null) {
                    selectedPiece = whiteBishop1;
                    selectedPiece.piece.setBackgroundColor(Color.parseColor("#0A6224"));
                } else {
                    if (selectedPiece != whiteBishop1) {
                        selectedPiece.piece.setBackgroundColor(Color.TRANSPARENT);
                        selectedPiece = whiteBishop1;
                        selectedPiece.piece.setBackgroundColor(Color.parseColor("#0A6224"));
                    } else {
                        selectedPiece.piece.setBackgroundColor(Color.TRANSPARENT);
                        selectedPiece = null;
                    }
                }
            }
        });

        whiteBishop2.piece.setOnClickListener(v -> {
            if(!Common.gameOver && whiteBishop2.allowed && white_turn) {
                if (selectedPiece == null) {
                    selectedPiece = whiteBishop2;
                    selectedPiece.piece.setBackgroundColor(Color.parseColor("#0A6224"));
                } else {
                    if (selectedPiece != whiteBishop2) {
                        selectedPiece.piece.setBackgroundColor(Color.TRANSPARENT);
                        selectedPiece = whiteBishop2;
                        selectedPiece.piece.setBackgroundColor(Color.parseColor("#0A6224"));
                    } else {
                        selectedPiece.piece.setBackgroundColor(Color.TRANSPARENT);
                        selectedPiece = null;
                    }
                }
            }
        });

        whiteKnight1.piece.setOnClickListener(v -> {
            if(!Common.gameOver && whiteKnight1.allowed && white_turn) {
                if (selectedPiece == null) {
                    selectedPiece = whiteKnight1;
                    selectedPiece.piece.setBackgroundColor(Color.parseColor("#0A6224"));
                } else {
                    if (selectedPiece != whiteKnight1) {
                        selectedPiece.piece.setBackgroundColor(Color.TRANSPARENT);
                        selectedPiece = whiteKnight1;
                        selectedPiece.piece.setBackgroundColor(Color.parseColor("#0A6224"));
                    } else {
                        selectedPiece.piece.setBackgroundColor(Color.TRANSPARENT);
                        selectedPiece = null;
                    }
                }
            }
        });

        whiteKnight2.piece.setOnClickListener(v -> {
            if(!Common.gameOver && whiteKnight2.allowed && white_turn) {
                if (selectedPiece == null) {
                    selectedPiece = whiteKnight2;
                    selectedPiece.piece.setBackgroundColor(Color.parseColor("#0A6224"));
                } else {
                    if (selectedPiece != whiteKnight2) {
                        selectedPiece.piece.setBackgroundColor(Color.TRANSPARENT);
                        selectedPiece = whiteKnight2;
                        selectedPiece.piece.setBackgroundColor(Color.parseColor("#0A6224"));
                    } else {
                        selectedPiece.piece.setBackgroundColor(Color.TRANSPARENT);
                        selectedPiece = null;
                    }
                }
            }
        });

        whiteRook1.piece.setOnClickListener(v -> {
            if(!Common.gameOver && whiteRook1.allowed && white_turn) {
                if (selectedPiece == null) {
                    selectedPiece = whiteRook1;
                    selectedPiece.piece.setBackgroundColor(Color.parseColor("#0A6224"));
                } else {
                    if (selectedPiece != whiteRook1) {
                        selectedPiece.piece.setBackgroundColor(Color.TRANSPARENT);
                        selectedPiece = whiteRook1;
                        selectedPiece.piece.setBackgroundColor(Color.parseColor("#0A6224"));
                    } else {
                        selectedPiece.piece.setBackgroundColor(Color.TRANSPARENT);
                        selectedPiece = null;
                    }
                }
            }
        });

        whiteRook2.piece.setOnClickListener(v -> {
            if(!Common.gameOver && whiteRook2.allowed && white_turn) {
                if (selectedPiece == null) {
                    selectedPiece = whiteRook2;
                    selectedPiece.piece.setBackgroundColor(Color.parseColor("#0A6224"));
                } else {
                    if (selectedPiece != whiteRook2) {
                        selectedPiece.piece.setBackgroundColor(Color.TRANSPARENT);
                        selectedPiece = whiteRook2;
                        selectedPiece.piece.setBackgroundColor(Color.parseColor("#0A6224"));
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

        //approx 126 units is the length of each square
        board.setOnClickListener(v -> {

            if(selectedPiece != null){
                print("coordinates - "+lastTouchDownXY[0]+" "+lastTouchDownXY[1]);
                movePiece(lastTouchDownXY[0],lastTouchDownXY[1]);
            }

        });
    }

    private void movePiece(float x, float y){
        int x_mul = (int)(x/126);
        int y_mul = (int)(y/126);
        print(x_mul+" "+y_mul);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) selectedPiece.piece.getLayoutParams();
        if((isUnderCheckAfterMove(selectedPiece, selectedPiece.location.x, selectedPiece.location.y, x_mul, y_mul)) || !isMovePossible(selectedPiece, x_mul, y_mul)){
            return;
        }
        removeAttackSquares(boardLocations, attackedSquares);
        if(x_mul != selectedPiece.location.x) params.horizontalBias = (float)(x_mul*0.14285);

        if(y_mul != selectedPiece.location.y) params.verticalBias = (float)(y_mul*0.14285);

        selectedPiece.piece.setLayoutParams(params);
        selectedPiece.firstMove = false;
        selectedPiece.piece.setBackgroundColor(Color.TRANSPARENT);
        updateBoardLocations(selectedPiece, x_mul, y_mul);
        white_turn = false;
        selectedPiece = null;
//        allowAllMoves();
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
            Helper.print2D(boardLocations);
            Helper.print2D(attackedSquares);
            return;
        }

        if(x_mul != selectedPiece.location.x) selectedPieceParams.horizontalBias = (float)(x_mul*0.14285);

        if(y_mul != selectedPiece.location.y) selectedPieceParams.verticalBias = (float)(y_mul*0.14285);
        selectedPiece.piece.setLayoutParams(selectedPieceParams);
        selectedPiece.firstMove = false;
        selectedPiece.piece.setBackgroundColor(Color.TRANSPARENT);

        if(piece.name.equals("King") || piece.name.equals("Queen")) Common.whiteBlack.capturedPiece_id = piece.id;
        else if(piece.name.equals("Pawn")) Common.whiteBlack.capturedPiece_id = (piece.id > 0)? (9-piece.id):(-9-piece.id);
        else if(piece.id%2 == 0) Common.whiteBlack.capturedPiece_id = (piece.id > 0)?  piece.id-1: piece.id+1;
        else Common.whiteBlack.capturedPiece_id = (piece.id > 0)? piece.id+1:piece.id-1;

        updateBoardLocations(selectedPiece, piece.location.x, piece.location.y);
        selectedPiece = null;
        white_turn = false;
        piece.piece.setVisibility(View.GONE);
//        allowAllMoves();
//        Helper.print2D(boardLocations);
//        System.out.println("Attacked squares \n");
//        Helper.print2D(attackedSquares);
    }

    private void updateLocations(){
        for(int i=0;i<8;++i){
            Location whiteLocation = new Location();
            Location blackLocation = new Location();
            whiteLocation.x = i;
            whiteLocation.y = 6;
            blackLocation.x = i;
            blackLocation.y = 1;
            blackPawns[i].location = blackLocation;
            whitePawns[i].location = whiteLocation;
            whitePawns[i].name = blackPawns[i].name = "Pawn";
            whitePawns[i].id = i+1;
            blackPawns[i].id = -i-1;
            pieceLocations[1][i] = -i-1;
            pieceLocations[6][i] = i+1;
            getId_piece.put(-i-1,blackPawns[i]);
        }

        whiteKing.location = new Location();
        whiteKing.location.x = 4;
        whiteKing.location.y = 7;
        pieceLocations[7][4] = 9;

        whiteQueen.location = new Location();
        whiteQueen.location.x = 3;
        whiteQueen.location.y = 7;
        pieceLocations[7][3] = 10;

        whiteBishop1.location = new Location();
        whiteBishop1.location.x = 2;
        whiteBishop1.location.y = 7;
        pieceLocations[7][2] = 11;

        whiteBishop2.location = new Location();
        whiteBishop2.location.x = 5;
        whiteBishop2.location.y = 7;
        pieceLocations[7][5] = 12;

        whiteKnight1.location = new Location();
        whiteKnight1.location.x = 1;
        whiteKnight1.location.y = 7;
        pieceLocations[7][1] = 13;

        whiteKnight2.location = new Location();
        whiteKnight2.location.x = 6;
        whiteKnight2.location.y = 7;
        pieceLocations[7][6] = 14;

        whiteRook1.location = new Location();
        whiteRook1.location.x = 0;
        whiteRook1.location.y = 7;
        pieceLocations[7][0] = 15;

        whiteRook2.location = new Location();
        whiteRook2.location.x = 7;
        whiteRook2.location.y = 7;
        pieceLocations[7][7] = 16;

        blackKing.location = new Location();
        blackKing.location.x = 4;
        blackKing.location.y = 0;
        pieceLocations[0][4] = -9;

        blackQueen.location = new Location();
        blackQueen.location.x = 3;
        blackQueen.location.y = 0;
        pieceLocations[0][3] = -10;

        blackBishop1.location = new Location();
        blackBishop1.location.x = 2;
        blackBishop1.location.y = 0;
        pieceLocations[0][2] = -11;

        blackBishop2.location = new Location();
        blackBishop2.location.x = 5;
        blackBishop2.location.y = 0;
        pieceLocations[0][5] = -12;

        blackKnight1.location = new Location();
        blackKnight1.location.x = 1;
        blackKnight1.location.y = 0;
        pieceLocations[0][1] = -13;

        blackKnight2.location = new Location();
        blackKnight2.location.x = 6;
        blackKnight2.location.y = 0;
        pieceLocations[0][6] = -14;

        blackRook1.location = new Location();
        blackRook1.location.x = 0;
        blackRook1.location.y = 0;
        pieceLocations[0][0] = -15;

        blackRook2.location = new Location();
        blackRook2.location.x = 7;
        blackRook2.location.y = 0;
        pieceLocations[0][7] = -16;

        whiteKing.name = blackKing.name = "King";
        whiteQueen.name = blackQueen.name = "Queen";
        whiteBishop1.name = blackBishop1.name = "Bishop";
        whiteBishop2.name = blackBishop2.name = "Bishop";
        whiteKnight1.name = blackKnight1.name = "Knight";
        whiteKnight2.name = blackKnight2.name = "Knight";
        whiteRook1.name = blackRook1.name = "Rook";
        whiteRook2.name = blackRook2.name = "Rook";

        getId_piece.put(-9,blackKing);
        getId_piece.put(-10,blackQueen);
        getId_piece.put(-11,blackBishop1);
        getId_piece.put(-12,blackBishop2);
        getId_piece.put(-13,blackKnight1);
        getId_piece.put(-14,blackKnight2);
        getId_piece.put(-15,blackRook1);
        getId_piece.put(-16,blackRook2);

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
    }


    private void updateAttackSquares(int[][] boardLocations, int[][] attackedSquares){
        for(int i=0; i<8; ++i){
            if(!blackPawns[i].captured) Helper.updatePawnAttackSquares(blackPawns[i], attackedSquares, boardLocations);
        }
        if(!blackKing.captured) Helper.updateKingAttackSquares(blackKing, attackedSquares, boardLocations);
        if(!blackQueen.captured) Helper.updateQueenAndRookAttackSquares(blackQueen, attackedSquares, boardLocations);
        if(!blackBishop1.captured) Helper.updateBishopAttackSquares(blackBishop1, attackedSquares, boardLocations);
        if(!blackBishop2.captured) Helper.updateBishopAttackSquares(blackBishop2, attackedSquares, boardLocations);
        if(!blackKnight1.captured) Helper.updateKnightAttackSquares(blackKnight1, attackedSquares, boardLocations);
        if(!blackKnight2.captured) Helper.updateKnightAttackSquares(blackKnight2, attackedSquares, boardLocations);
        if(!blackRook1.captured) Helper.updateQueenAndRookAttackSquares(blackRook1, attackedSquares, boardLocations);
        if(!blackRook2.captured) Helper.updateQueenAndRookAttackSquares(blackRook2, attackedSquares, boardLocations);

    }


    private void removeAttackSquares(int[][] boardLocations, int[][] attackedSquares){
        for(int i=0; i<8; ++i){
            if(!blackPawns[i].captured) Helper.removePawnAttackSquares(blackPawns[i], attackedSquares, boardLocations);
        }
        if(!blackKing.captured) Helper.removeKingAttackSquares(blackKing, attackedSquares, boardLocations);
        if(!blackQueen.captured) Helper.removeQueenAndRookAttackSquares(blackQueen, attackedSquares, boardLocations);
        if(!blackBishop1.captured) Helper.removeBishopAttackSquares(blackBishop1, attackedSquares, boardLocations);
        if(!blackBishop2.captured) Helper.removeBishopAttackSquares(blackBishop2, attackedSquares, boardLocations);
        if(!blackKnight1.captured) Helper.removeKnightAttackSquares(blackKnight1, attackedSquares, boardLocations);
        if(!blackKnight2.captured) Helper.removeKnightAttackSquares(blackKnight2, attackedSquares, boardLocations);
        if(!blackRook1.captured) Helper.removeQueenAndRookAttackSquares(blackRook1, attackedSquares, boardLocations);
        if(!blackRook2.captured) Helper.removeQueenAndRookAttackSquares(blackRook2, attackedSquares, boardLocations);

    }

    private void initialBoardLocations(){
        for(int i=0;i<8;++i){
            for(int j=0;j<8;++j){
                boardLocations[i][j] = 0;
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
        //Updates player's moves on board
        DatabaseReference game = database.child(Common.code);
        Common.whiteBlack.old_x = (7-piece.location.x);
        Common.whiteBlack.old_y = (7-piece.location.y);
        boardLocations[piece.location.y][piece.location.x] = 0;
//        pieceLocations[new_y][new_x] = pieceLocations[piece.location.y][piece.location.x];
//        pieceLocations[piece.location.y][piece.location.x] = 0;
        boardLocations[new_y][new_x] = -1;

        piece.location.x = new_x;
        piece.location.y = new_y;

        Common.whiteBlack.new_x = (7-piece.location.x);
        Common.whiteBlack.new_y = (7-piece.location.y);

        if(piece.name.equals("King") || piece.name.equals("Queen")) Common.whiteBlack.id = piece.id;
        else if(piece.name.equals("Pawn")) Common.whiteBlack.id = (piece.id > 0)? (9-piece.id):(-9-piece.id);
        else if(piece.id%2 == 0) Common.whiteBlack.id = (piece.id > 0)?  piece.id-1: piece.id+1;
        else Common.whiteBlack.id = (piece.id > 0)? piece.id+1:piece.id-1;

        if(Common.isTimer) stopWhiteTimer();


        Common.whiteBlack.time = Common.time.white; //Update white time in firebase
        game.child("white").setValue(Common.whiteBlack);
        updateAttackSquares(boardLocations, attackedSquares);
        if(attackedSquares[whiteKing.location.y][whiteKing.location.x] == 0){
            Common.underCheck = false;
//            System.out.println("King not under check!");
        }

        if(Common.isTimer) startBlackTimer();
    }

    private boolean isMovePossible(ChessPiece piece, int new_x, int new_y){
        boolean possible = false;
        switch(piece.name){
            case "King": possible =  Helper.isPossibleKing(piece,whiteRook1, whiteRook2, boardLocations, attackedSquares, pieceLocations, new_x, new_y, "white"); break;
            case "Queen": possible = Helper.isPossibleQueenAndRook(piece, boardLocations, attackedSquares, new_x, new_y); break;
            case "Bishop": possible = Helper.isPossibleBishop(piece, boardLocations, attackedSquares, new_x, new_y); break;
            case "Knight": possible = Helper.isPossibleKnight(piece, boardLocations, attackedSquares, new_x, new_y); break;
            case "Rook" : possible = Helper.isPossibleRook(piece, boardLocations, attackedSquares, new_x, new_y); break;
            case "Pawn" : possible = Helper.isPossiblePawn(piece, boardLocations, attackedSquares, new_x, new_y); break;
        }
        return possible;
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
        if(!whiteKing.captured && checkMovesPossible(whiteKing)) checkmate = false;
        if(!whiteQueen.captured && checkMovesPossible(whiteQueen)) checkmate = false;
        if(!whiteBishop1.captured && checkMovesPossible(whiteBishop1)) checkmate = false;
        if(!whiteBishop2.captured && checkMovesPossible(whiteBishop2)) checkmate = false;
        if(!whiteKnight1.captured && checkMovesPossible(whiteKnight1)) checkmate = false;
        if(!whiteKnight2.captured && checkMovesPossible(whiteKnight2)) checkmate = false;
        if(!whiteRook1.captured && checkMovesPossible(whiteRook1)) checkmate = false;
        if(!whiteRook2.captured && checkMovesPossible(whiteRook2)) checkmate = false;
        for(int i=0; i<8; ++i){
            if(!whitePawns[i].captured && checkMovesPossible(whitePawns[i])) checkmate = false;
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
//            System.out.println("Bishop - NewX = "+tempX+" NewY = "+tempY+" x = "+x+" y = "+y+" boardLocation[x][y] = "+boardLocations[x][y]);

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
            if(boardLocations[tempY][tempX] != 0) break;
            if(!isUnderCheckAfterMove(piece, x,y,tempX, tempY)) return true;
            ++tempX;
        }

        tempX = x;
        ++tempY;
        while(tempY < 8){
            if(boardLocations[tempY][tempX] != 0) break;
            if(!isUnderCheckAfterMove(piece, x,y,tempX, tempY)) return true;
            ++tempY;
        }

        tempY = y;
        --tempX;
        while(tempX >= 0){
            if(boardLocations[tempY][tempX] != 0) break;
            if(!isUnderCheckAfterMove(piece, x,y,tempX, tempY)) return true;
            --tempX;
        }

        tempX = x;
        --tempY;
        while(tempY >= 0){
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
            if(boardLocations[y+1][x+2] != -1 && !isUnderCheckAfterMove(piece, x, y, x+2, y+1)) return true;
        }

        if(x+2 < 8 && y-1 >= 0){
            if(boardLocations[y-1][x+2] != -1 && !isUnderCheckAfterMove(piece, x, y, x+2, y-1)) return true;
        }

        if(x-2 >= 0 && y+1 < 8){
            if(boardLocations[y+1][x-2] != -1 && !isUnderCheckAfterMove(piece, x, y, x-2, y+1)) return true;
        }

        if(x-2 >= 0 && y-1 >= 0){
            if(boardLocations[y-1][x-2] != -1 && !isUnderCheckAfterMove(piece, x, y, x-2, y-1)) return true;
        }

        if(x+1 < 8 && y+2 < 8){
            if(boardLocations[y+2][x+1] != -1 && !isUnderCheckAfterMove(piece, x, y, x+1, y+2)) return true;
        }

        if(x+1 < 8 && y-2 >= 0){
            if(boardLocations[y-2][x+1] != -1 && !isUnderCheckAfterMove(piece, x, y, x+1, y-2)) return true;
        }

        if(x-1 >= 0 && y+2 < 8){
            if(boardLocations[y+2][x-1] != -1 && !isUnderCheckAfterMove(piece, x, y, x-1, y+2)) return true;
        }

        if(x-1 >= 0 && y-2 >= 0){
            if(boardLocations[y-2][x-1] != -1 && !isUnderCheckAfterMove(piece, x, y, x-1, y-2)) return true;
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
            if(!isUnderCheckAfterMove(piece,x,y,x-1,y-1)) return true;
        }
        if(y-1 >=0 && x+1 < 8 && boardLocations[y-1][x+1] == 1){
            if(!isUnderCheckAfterMove(piece,x,y,x+1,y-1)) return true;
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
        if(temp_attack[whiteKing.location.y][whiteKing.location.x] == 0){
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
            case "King": allowed  = checkKingPossible(piece); break;
            case "Queen":
            case "Rook":
                allowed  = checkQueenAndRookPossible(piece); break;
            case "Bishop": allowed = checkBishopPossible(piece); break;
            case "Knight": allowed = checkKnightPossible(piece); break;
        }
        return allowed ;
    }

    private void allowAllMoves(){
        for(int i=0; i<8; ++i){
            whitePawns[i].allowed = true;
        }
        whiteKing.allowed = true;
        whiteQueen.allowed = true;
        whiteBishop1.allowed = true;
        whiteBishop2.allowed = true;
        whiteKnight1.allowed = true;
        whiteKnight2.allowed = true;
        whiteRook1.allowed = true;
        whiteRook2.allowed = true;
    }

    private void print(Object text){
        System.out.println(text);
    }

    private void startWhiteTimer(){
        if(addIncrementWhite) Common.time.white += Common.time_increment;
        whiteTimer = new CountDownTimer(Common.time.white*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                    Common.time.white = (int)(millisUntilFinished/1000);
                    addIncrementWhite = true;
                    whiteClock.setText(Helper.convertTime((int)(millisUntilFinished/1000)));

            }

            @Override
            public void onFinish() {
                game.child("white").child("isGameOver").setValue(-1);
                Common.gameOver = true;
                AlertDialog.Builder builder
                        = new AlertDialog
                        .Builder(White.this);
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
        whiteTimer.start();
    }

    private void startBlackTimer(){
//        if(addIncrementBlack) Common.time.white += 2;
        blackTimer = new CountDownTimer(Common.time.black*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                    Common.time.black = (int)(millisUntilFinished/1000);
//                    addIncrementBlack = true;
                    blackClock.setText(Helper.convertTime((int)(millisUntilFinished/1000)));

            }

            @Override
            public void onFinish() {

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
                    // The 'which' argument contains the index position
                    // of the selected item
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
                    Common.whiteBlack.id = piece.id = new_ids++;


                    switch(which){
                        case 0: piece.piece.setImageResource(R.drawable.white_queen); piece.name = whiteQueen.name; break;
                        case 1: piece.piece.setImageResource(R.drawable.white_rook); piece.name = whiteRook1.name;break;
                        case 2: piece.piece.setImageResource(R.drawable.white_bishop); piece.name = whiteBishop1.name; break;
                        case 3: piece.piece.setImageResource(R.drawable.white_knight); piece.name = whiteKnight1.name; break;
                    }
                    Common.whiteBlack.new_name = piece.name;

                    if (Common.isTimer) stopWhiteTimer();


                    Common.whiteBlack.time = Common.time.white; //Update white time in firebase
                    game.child("white").setValue(Common.whiteBlack);
                    updateAttackSquares(boardLocations, attackedSquares);
                    if (attackedSquares[whiteKing.location.y][whiteKing.location.x] == 0) {
                        Common.underCheck = false;
//            System.out.println("King not under check!");
                    }

                    if (Common.isTimer) startBlackTimer();
                    dialog.cancel();
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}