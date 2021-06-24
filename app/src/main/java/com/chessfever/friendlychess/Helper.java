package com.chessfever.friendlychess;

import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.Vector;

public class Helper {

    private static boolean flag = false;
    public static void updateBishopAttackSquares(ChessPiece piece, int[][] attackedSquares, int[][] boardLocations){
        int x = piece.location.x;
        int y = piece.location.y;

        int tempX = x;
        int tempY = y;
        ++tempX;
        ++tempY;
        while(tempX < 8 && tempY < 8){

            attackedSquares[tempY][tempX] = 1;
            if(Common.playerKing.new_x == tempX && Common.playerKing.new_y == tempY){
                Common.attackingPiece.new_x = x;
                Common.attackingPiece.new_y = y;
                Common.attackingPiece.new_name = piece.name;
            }
            if(boardLocations[tempY][tempX] != 0) break;
            ++tempX;
            ++tempY;
        }

        tempX = x;
        tempY = y;
        --tempX;
        ++tempY;
        while(tempX >= 0 && tempY < 8){
            attackedSquares[tempY][tempX] = 1;
            if(Common.playerKing.new_x == tempX && Common.playerKing.new_y == tempY){
                Common.attackingPiece.new_x = x;
                Common.attackingPiece.new_y = y;
                Common.attackingPiece.new_name = piece.name;
            }
            if(boardLocations[tempY][tempX] != 0) break;
            --tempX;
            ++tempY;
        }

        tempX = x;
        tempY = y;
        --tempX;
        --tempY;
        while(tempX >= 0 && tempY >= 0){
            attackedSquares[tempY][tempX] = 1;
            if(Common.playerKing.new_x == tempX && Common.playerKing.new_y == tempY){
                Common.attackingPiece.new_x = x;
                Common.attackingPiece.new_y = y;
                Common.attackingPiece.new_name = piece.name;
            }
            if(boardLocations[tempY][tempX] != 0) break;
            --tempX;
            --tempY;
        }

        tempX = x;
        tempY = y;
        ++tempX;
        --tempY;
        while(tempX < 8 && tempY >= 0){
            attackedSquares[tempY][tempX] = 1;
            if(Common.playerKing.new_x == tempX && Common.playerKing.new_y == tempY){
                Common.attackingPiece.new_x = x;
                Common.attackingPiece.new_y = y;
                Common.attackingPiece.new_name = piece.name;
            }
            if(boardLocations[tempY][tempX] != 0) break;
            ++tempX;
            --tempY;
        }
    }

    public static void updateQueenAndRookAttackSquares(ChessPiece piece, int[][] attackedSquares, int[][] boardLocations){
        int x = piece.location.x;
        int y = piece.location.y;

        int tempX = x;
        int tempY = y;

        ++tempX;
        while(tempX < 8){
            attackedSquares[tempY][tempX] = 1;
            if(Common.playerKing.new_x == tempX && Common.playerKing.new_y == tempY){
                Common.attackingPiece.new_x = x;
                Common.attackingPiece.new_y = y;
                Common.attackingPiece.new_name = piece.name;
            }
            if(boardLocations[tempY][tempX] != 0) break;
            ++tempX;
        }

        tempX = x;
        ++tempY;
        while(tempY < 8){
            attackedSquares[tempY][tempX] = 1;
            if(Common.playerKing.new_x == tempX && Common.playerKing.new_y == tempY){
                Common.attackingPiece.new_x = x;
                Common.attackingPiece.new_y = y;
                Common.attackingPiece.new_name = piece.name;
            }
            if(boardLocations[tempY][tempX] != 0) break;
            ++tempY;
        }

        tempY = y;
        --tempX;
        while(tempX >= 0){
            attackedSquares[tempY][tempX] = 1;
            if(Common.playerKing.new_x == tempX && Common.playerKing.new_y == tempY){
                Common.attackingPiece.new_x = x;
                Common.attackingPiece.new_y = y;
                Common.attackingPiece.new_name = piece.name;
            }
            if(boardLocations[tempY][tempX] != 0) break;
            --tempX;
        }

        tempX = x;
        --tempY;
        while(tempY >= 0){
            attackedSquares[tempY][tempX] = 1;
            if(Common.playerKing.new_x == tempX && Common.playerKing.new_y == tempY){
                Common.attackingPiece.new_x = x;
                Common.attackingPiece.new_y = y;
                Common.attackingPiece.new_name = piece.name;
            }
            if(boardLocations[tempY][tempX] != 0) break;
            --tempY;
        }

        if(piece.name.equals("Rook")) return;

        updateBishopAttackSquares(piece, attackedSquares, boardLocations);
    }

    public static void removeBishopAttackSquares(ChessPiece piece, int[][] attackedSquares, int[][] boardLocations){
        int x = piece.location.x;
        int y = piece.location.y;

        int tempX = x;
        int tempY = y;
        ++tempX;
        ++tempY;
        while(tempX < 8 && tempY < 8){
            attackedSquares[tempY][tempX] = 0;
            if(boardLocations[tempY][tempX] != 0) break;
            ++tempX;
            ++tempY;
        }

        tempX = x;
        tempY = y;
        --tempX;
        ++tempY;
        while(tempX >= 0 && tempY < 8){
            attackedSquares[tempY][tempX] = 0;
            if(boardLocations[tempY][tempX] != 0) break;
            --tempX;
            ++tempY;
        }

        tempX = x;
        tempY = y;
        --tempX;
        --tempY;
        while(tempX >= 0 && tempY >= 0){
            attackedSquares[tempY][tempX] = 0;
            if(boardLocations[tempY][tempX] != 0) break;
            --tempX;
            --tempY;
        }

        tempX = x;
        tempY = y;
        ++tempX;
        --tempY;
        while(tempX < 8 && tempY >= 0){
            attackedSquares[tempY][tempX] = 0;
            if(boardLocations[tempY][tempX] != 0) break;
            ++tempX;
            --tempY;
        }
    }

    public static void removeQueenAndRookAttackSquares(ChessPiece piece, int[][] attackedSquares, int[][] boardLocations){
        int x = piece.location.x;
        int y = piece.location.y;

        int tempX = x;
        int tempY = y;
        ++tempX;
        while(tempX < 8){
            attackedSquares[tempY][tempX] = 0;
            if(boardLocations[tempY][tempX] != 0) break;
            ++tempX;
        }

        tempX = x;
        ++tempY;
        while(tempY < 8){
            attackedSquares[tempY][tempX] = 0;
            if(boardLocations[tempY][tempX] != 0) break;
            ++tempY;
        }

        tempY = y;
        --tempX;
        while(tempX >= 0){
            attackedSquares[tempY][tempX] = 0;
            if(boardLocations[tempY][tempX] != 0) break;
            --tempX;
        }

        tempX = x;
        --tempY;
        while(tempY >= 0){
            attackedSquares[tempY][tempX] = 0;
            if(boardLocations[tempY][tempX] != 0) break;
            --tempY;
        }

        if(piece.name.equals("Rook")) return;

        removeBishopAttackSquares(piece, attackedSquares, boardLocations);
    }

    public static void updateKnightAttackSquares(ChessPiece piece, int[][] attackedSquares, int[][] boardLocations){
        int x = piece.location.x;
        int y = piece.location.y;

        if(x+2 < 8 && y+1 < 8){
            attackedSquares[y+1][x+2] = 1;
            if(Common.playerKing.new_x == x+2 && Common.playerKing.new_y == y+1){
                Common.attackingPiece.new_x = x;
                Common.attackingPiece.new_y = y;
                Common.attackingPiece.new_name = piece.name;
            }
        }

        if(x+2 < 8 && y-1 >= 0){
            attackedSquares[y-1][x+2] = 1;
            if(Common.playerKing.new_x == x+2 && Common.playerKing.new_y == y-1){
                Common.attackingPiece.new_x = x;
                Common.attackingPiece.new_y = y;
                Common.attackingPiece.new_name = piece.name;
            }
        }

        if(x-2 >= 0 && y+1 < 8){
            attackedSquares[y+1][x-2] = 1;
            if(Common.playerKing.new_x == x-2 && Common.playerKing.new_y == y+1){
                Common.attackingPiece.new_x = x;
                Common.attackingPiece.new_y = y;
                Common.attackingPiece.new_name = piece.name;
            }
        }

        if(x-2 >= 0 && y-1 >= 0){
            attackedSquares[y-1][x-2] = 1;
            if(Common.playerKing.new_x == x-2 && Common.playerKing.new_y == y-1){
                Common.attackingPiece.new_x = x;
                Common.attackingPiece.new_y = y;
                Common.attackingPiece.new_name = piece.name;
            }
        }

        if(x+1 < 8 && y+2 < 8){
            attackedSquares[y+2][x+1] = 1;
            if(Common.playerKing.new_x == x+1 && Common.playerKing.new_y == y+2){
                Common.attackingPiece.new_x = x;
                Common.attackingPiece.new_y = y;
                Common.attackingPiece.new_name = piece.name;
            }
        }

        if(x+1 < 8 && y-2 >= 0){
            attackedSquares[y-2][x+1] = 1;
            if(Common.playerKing.new_x == x+1 && Common.playerKing.new_y == y-2){
                Common.attackingPiece.new_x = x;
                Common.attackingPiece.new_y = y;
                Common.attackingPiece.new_name = piece.name;
            }
        }

        if(x-1 >= 0 && y+2 < 8){
            attackedSquares[y+2][x-1] = 1;
            if(Common.playerKing.new_x == x-1 && Common.playerKing.new_y == y+2){
                Common.attackingPiece.new_x = x;
                Common.attackingPiece.new_y = y;
                Common.attackingPiece.new_name = piece.name;
            }
        }

        if(x-1 >= 0 && y-2 >= 0){
            attackedSquares[y-2][x-1] = 1;
            if(Common.playerKing.new_x == x-1 && Common.playerKing.new_y == y-2){
                Common.attackingPiece.new_x = x;
                Common.attackingPiece.new_y = y;
                Common.attackingPiece.new_name = piece.name;
            }
        }

    }

    public static void removeKnightAttackSquares(ChessPiece piece, int[][] attackedSquares, int[][] boardLocations){
        int x = piece.location.x;
        int y = piece.location.y;

        if(x+2 < 8 && y+1 < 8){
            attackedSquares[y+1][x+2] = 0;
        }

        if(x+2 < 8 && y-1 >= 0){
            attackedSquares[y-1][x+2] = 0;
        }

        if(x-2 >= 0 && y+1 < 8){
            attackedSquares[y+1][x-2] = 0;
        }

        if(x-2 >= 0 && y-1 >= 0){
            attackedSquares[y-1][x-2] = 0;
        }

        if(x+1 < 8 && y+2 < 8){
            attackedSquares[y+2][x+1] = 0;
        }

        if(x+1 < 8 && y-2 >= 0){
            attackedSquares[y-2][x+1] = 0;
        }

        if(x-1 >= 0 && y+2 < 8){
            attackedSquares[y+2][x-1] = 0;
        }

        if(x-1 >= 0 && y-2 >= 0){
            attackedSquares[y-2][x-1] = 0;
        }

    }

    public static void updatePawnAttackSquares(ChessPiece piece, int[][] attackedSquares, int[][] boardLocations){
        int x = piece.location.x;
        int y = piece.location.y;

        if(x-1 >= 0 && y+1 < 8) attackedSquares[y+1][x-1] = 1;

        if(x+1 < 8 && y+1 < 8) attackedSquares[y+1][x+1] = 1;
    }

    public static void updateKingAttackSquares(ChessPiece piece, int[][] attackedSquares, int[][] boardLocations){
        int x = piece.location.x;
        int y = piece.location.y;

        if(x-1 >= 0 && y+1 < 8) attackedSquares[y+1][x-1] = 1;
        if(x+1 < 8 && y+1 < 8) attackedSquares[y+1][x+1] = 1;
        if(x-1 >= 0 && y-1 >= 0) attackedSquares[y-1][x-1] = 1;
        if(x+1 < 8 && y-1 >= 0) attackedSquares[y-1][x+1] = 1;
        if(y-1 >= 0) attackedSquares[y-1][x] = 1;
        if(y+1 < 8) attackedSquares[y+1][x] = 1;
        if(x-1 >= 0) attackedSquares[y][x-1] = 1;
        if(x+1 < 8) attackedSquares[y][x+1] = 1;

    }

    public static void removeKingAttackSquares(ChessPiece piece, int[][] attackedSquares, int[][] boardLocations){
        int x = piece.location.x;
        int y = piece.location.y;

        if(x-1 >= 0 && y+1 < 8) attackedSquares[y+1][x-1] = 0;
        if(x+1 < 8 && y+1 < 8) attackedSquares[y+1][x+1] = 0;
        if(x-1 >= 0 && y-1 >= 0) attackedSquares[y-1][x-1] = 0;
        if(x+1 < 8 && y-1 >= 0) attackedSquares[y-1][x+1] = 0;
        if(y-1 >= 0) attackedSquares[y-1][x] = 0;
        if(y+1 < 8) attackedSquares[y+1][x] = 0;
        if(x-1 >= 0) attackedSquares[y][x-1] = 0;
        if(x+1 < 8) attackedSquares[y][x+1] = 0;
    }

    public static void removePawnAttackSquares(ChessPiece piece, int[][] attackedSquares, int[][] boardLocations){
        int x = piece.location.x;
        int y = piece.location.y;

        if(x-1 >= 0 && y+1 < 8) attackedSquares[y+1][x-1] = 0;
        if(x+1 < 8 && y+1 < 8) attackedSquares[y+1][x+1] = 0;
    }

    public static boolean isPossibleKing(ChessPiece piece, ChessPiece rook1, ChessPiece rook2, int[][]boardLocations, int[][] attackedSquares, int[][] pieceLocations, int new_x, int new_y, String player){
        int x = piece.location.x;
        int y = piece.location.y;

        if(piece.firstMove && player.equals("white")){
            if(y == new_y){
                if(new_x == x+2 && boardLocations[y][x+1] != 1 && boardLocations[y][x+2] != 1 && rook2.firstMove && attackedSquares[y][x+1] != 1 && attackedSquares[y][x+2] != 1){
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) rook2.piece.getLayoutParams();
                    params.horizontalBias = (float)(5*0.14285);
                    rook2.piece.setLayoutParams(params);
                    boardLocations[rook2.location.y][rook2.location.x] = 0;
                    pieceLocations[rook2.location.y][rook2.location.x] = 0;
                    rook2.location.x = 5;
                    boardLocations[rook2.location.y][rook2.location.x] = 1;
                    pieceLocations[rook2.location.y][rook2.location.x] = 16;
                    Common.whiteBlack.rook.id = 15;
                    Common.whiteBlack.rook.old_x = 0;
                    Common.whiteBlack.rook.old_y = (7-rook2.location.y);
                    Common.whiteBlack.rook.new_x = 2;
                    Common.whiteBlack.rook.new_y = (7-rook2.location.y);
                    Common.whiteBlack.castle = 1;
                    return true;
                }
                else if(new_x == x-2 && boardLocations[y][x-1] != 1 && boardLocations[y][x-2]!= 1 && rook1.firstMove && attackedSquares[y][x-1] != 1 && attackedSquares[y][x-2] != 1){
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) rook1.piece.getLayoutParams();
                    params.horizontalBias = (float)(3*0.14285);
                    rook1.piece.setLayoutParams(params);
                    boardLocations[rook1.location.y][rook1.location.x] = 0;
                    pieceLocations[rook1.location.y][rook1.location.x] = 0;
                    rook1.location.x = 3;
                    boardLocations[rook1.location.y][rook1.location.x] = 1;
                    pieceLocations[rook1.location.y][rook1.location.x] = 15;
                    Common.whiteBlack.rook.id = 16;
                    Common.whiteBlack.rook.old_x = (7);
                    Common.whiteBlack.rook.old_y = (7-rook1.location.y);
                    Common.whiteBlack.rook.new_x = (4);
                    Common.whiteBlack.rook.new_y = (7-rook1.location.y);
                    Common.whiteBlack.castle = (1);
                    return true;
                }
            }
        }
        else if(piece.firstMove && player.equals("black")){
            if(y == new_y){
                DatabaseReference game = FirebaseDatabase.getInstance().getReference().child(Common.code);
                if(new_x == x+2 && boardLocations[y][x+1] != 1 && boardLocations[y][x+2] != 1 && rook2.firstMove && attackedSquares[y][x+1] != 1 && attackedSquares[y][x+2] != 1){
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) rook2.piece.getLayoutParams();
                    params.horizontalBias = (float)(4*0.14285);
                    rook2.piece.setLayoutParams(params);
                    boardLocations[rook2.location.y][rook2.location.x] = 0;
                    pieceLocations[rook2.location.y][rook2.location.x] = 0;
                    rook2.location.x = 4;
                    boardLocations[rook2.location.y][rook2.location.x] = 1;
                    pieceLocations[rook2.location.y][rook2.location.x] = -16;

                    Common.whiteBlack.rook.id = -15;
                    Common.whiteBlack.rook.old_x = (0);
                    Common.whiteBlack.rook.old_y = (7-rook2.location.y);
                    Common.whiteBlack.rook.new_x = (3);
                    Common.whiteBlack.rook.new_y = (7-rook2.location.y);
                    Common.whiteBlack.castle = (1);
                    return true;
                }
                else if(new_x == x-2 && boardLocations[y][x-1] != 1 && boardLocations[y][x-2] != 1 && rook1.firstMove && attackedSquares[y][x-1] != 1 && attackedSquares[y][x-2] != 1){
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) rook1.piece.getLayoutParams();
                    params.horizontalBias = (float)(2*0.14285);
                    rook1.piece.setLayoutParams(params);
                    boardLocations[rook1.location.y][rook1.location.x] = 0;
                    pieceLocations[rook1.location.y][rook1.location.x] = 0;
                    rook1.location.x = 2;
                    boardLocations[rook1.location.y][rook1.location.x] = 1;
                    pieceLocations[rook1.location.y][rook1.location.x] = -15;

                    Common.whiteBlack.rook.id = -16;
                    Common.whiteBlack.rook.old_x = (7);
                    Common.whiteBlack.rook.old_y = (7-rook1.location.y);
                    Common.whiteBlack.rook.new_x = (5);
                    Common.whiteBlack.rook.new_y = (7-rook1.location.y);
                    Common.whiteBlack.castle = (1);
                    return true;
                }
            }
        }

        if(Math.abs((x-new_x)) <= 1 && Math.abs(y-new_y) <= 1){
            if((new_x >= 0 && new_x < 8) && (new_y >= 0 && new_y < 8) && attackedSquares[new_y][new_x] != 1) return true;
        }
        return false;
    }

    public static boolean isPossibleBishop(ChessPiece piece, int[][]boardLocations, int[][] attackedSquares, int new_x, int new_y){
        int x = piece.location.x;
        int y = piece.location.y;

        int tempX = x;
        int tempY = y;
        ++tempX;
        ++tempY;
        while(tempX < 8 && tempY < 8){

            if(new_x == tempX && new_y == tempY) return true;
            if(boardLocations[tempY][tempX] != 0) break;
            ++tempX;
            ++tempY;
        }

        tempX = x;
        tempY = y;
        --tempX;
        ++tempY;
        while(tempX >= 0 && tempY < 8){

            if(new_x == tempX && new_y == tempY) return true;
            if(boardLocations[tempY][tempX] != 0) break;
            --tempX;
            ++tempY;
        }

        tempX = x;
        tempY = y;
        --tempX;
        --tempY;
        while(tempX >= 0 && tempY >= 0){

            if(new_x == tempX && new_y == tempY) return true;
            if(boardLocations[tempY][tempX] != 0) break;
            --tempX;
            --tempY;
        }

        tempX = x;
        tempY = y;
        ++tempX;
        --tempY;
        while(tempX < 8 && tempY >= 0){

            if(new_x == tempX && new_y == tempY) return true;
            if(boardLocations[tempY][tempX] != 0) break;
            ++tempX;
            --tempY;
        }
        return false;
    }

    public static boolean isPossibleQueenAndRook(ChessPiece piece, int[][]boardLocations, int[][] attackedSquares, int new_x, int new_y){
        int x = piece.location.x;
        int y = piece.location.y;

        int tempX = x;
        int tempY = y;
        ++tempX;
        while(tempX < 8){

            if(new_x == tempX && new_y == tempY) return true;
            if(boardLocations[tempY][tempX] != 0) break;
            ++tempX;
        }

        tempX = x;
        ++tempY;
        while(tempY < 8){

            if(new_x == tempX && new_y == tempY) return true;
            if(boardLocations[tempY][tempX] != 0) break;
            ++tempY;
        }

        tempY = y;
        --tempX;
        while(tempX >= 0){

            if(new_x == tempX && new_y == tempY) return true;
            if(boardLocations[tempY][tempX] != 0) break;
            --tempX;
        }

        tempX = x;
        --tempY;
        while(tempY >= 0){


            if(new_x == tempX && new_y == tempY) return true;
            if(boardLocations[tempY][tempX] != 0) break;
            --tempY;
        }

        if(piece.name.equals("Rook")) {
            return false;
        }
        
        return isPossibleBishop(piece, boardLocations, attackedSquares, new_x, new_y) || false;
    }
    

    public static boolean isPossibleKnight(ChessPiece piece, int[][]boardLocations, int[][] attackedSquares, int new_x, int new_y){
        int x = piece.location.x;
        int y = piece.location.y;


        if(x+2 < 8 && y+1 < 8){
            if(new_x == x+2 && new_y == y+1) return true;
        }

        if(x+2 < 8 && y-1 >= 0){
            if(new_x == x+2 && new_y == y-1) return true;
        }

        if(x-2 >= 0 && y+1 < 8){

            if(new_x == x-2 && new_y == y+1) return true;
        }

        if(x-2 >= 0 && y-1 >= 0){
            if(new_x == x-2 && new_y == y-1) return true;
        }

        if(x+1 < 8 && y+2 < 8){
            if(new_x == x+1 && new_y == y+2) return true;
        }

        if(x+1 < 8 && y-2 >= 0){
            if(new_x == x+1 && new_y == y-2) return true;
        }

        if(x-1 >= 0 && y+2 < 8){
            if(new_x == x-1 && new_y == y+2) return true;
        }

        if(x-1 >= 0 && y-2 >= 0){
            if(new_x == x-1 && new_y == y-2) return true;
        }
        return false;
    }

    public static boolean isPossibleRook(ChessPiece piece, int[][]boardLocations, int[][] attackedSquares, int new_x, int new_y){
        return isPossibleQueenAndRook(piece, boardLocations, attackedSquares, new_x, new_y);
    }

    public static boolean isPossiblePawn(ChessPiece piece, int[][]boardLocations, int[][] attackedSquares, int new_x, int new_y, Map<Integer, ChessPiece> getId_piece){
        int x = piece.location.x;
        int y = piece.location.y;

        if(piece.firstMove){

            if(new_x == x && new_y == y-2 && boardLocations[y-2][x] != 1) return true;
        }
        if(new_x == x && new_y == y-1 && boardLocations[y-1][x] != 1) return true;
        if(new_x == x-1 && new_y == y-1){
            if(boardLocations[new_y][new_x] != 0) return true;
            if(getId_piece.containsKey(Common.previousMove.id) && getId_piece.get(Common.previousMove.id).name == "Pawn"){
                if(Common.previousMove.old_x == new_x && (new_x == x-1|| new_x == x+1)){
                    if(Common.previousMove.old_y == 1 && Common.previousMove.new_y == 3) return true;
                }
            }
        }
        if(new_x == x+1 && new_y == y-1){
            if(boardLocations[new_y][new_x] != 0) return true;
            if(getId_piece.containsKey(Common.previousMove.id) && getId_piece.get(Common.previousMove.id).name == "Pawn"){
                if(Common.previousMove.old_x == new_x){
                    if(Common.previousMove.old_y == 1 && Common.previousMove.new_y == 3) return true;
                }
            }
        }
        return false;
    }

    public static void moveOpponentPiece(int old_x, int old_y, int new_x, int new_y, int[][] boardLocations, int[][] attackedSquares,  ChessPiece piece, int[][] pieceLocations){

        boardLocations[old_y][old_x] = 0;
        pieceLocations[old_y][old_x] = 0;
        pieceLocations[new_y][new_x] = piece.id;
        boardLocations[new_y][new_x] = 1;
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)piece.piece.getLayoutParams();
        params.horizontalBias = (float)(new_x*0.14285);
        params.verticalBias = (float)(new_y*0.14285);
        piece.piece.setLayoutParams(params);
        piece.location.x = new_x;
        piece.location.y = new_y;

    }


    public static void updateAttackSquare(ChessPiece piece, int[][] attackedSquares, int[][] boardLocations){
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

    public static void removeAttackSquare(ChessPiece piece, int[][] attackedSquares, int[][] boardLocations){
        switch (piece.name){
            case "Pawn": Helper.removePawnAttackSquares(piece, attackedSquares, boardLocations); break;
            case "King": Helper.removeKingAttackSquares(piece, attackedSquares, boardLocations); break;
            case "Queen":
            case "Rook":
                Helper.removeQueenAndRookAttackSquares(piece, attackedSquares, boardLocations); break;
            case "Bishop": Helper.removeBishopAttackSquares(piece, attackedSquares, boardLocations); break;
            case "Knight": Helper.removeKnightAttackSquares(piece, attackedSquares, boardLocations); break;
        }
    }

    public static int getValue(DatabaseReference dbRef){
        final int[] value = {0};

        System.out.println("In DATABASE REFERENCE");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(flag){
                    value[0] = snapshot.getValue(Integer.class).intValue();
                }
                else{

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        if(flag){
            flag = false;
        }
        else{
            flag = true;
        }

        return value[0];
    }

    public static void print2D(int[][] board){
        String text = "";
        for(int i=0;i<8;++i){
            for(int j=0;j<8;++j){
                text += board[i][j]+" ";
            }
           text += "\n";
        }
        System.out.println(text);
    }

    public static String convertTime(int seconds){
        int h, m, s;
        h = (int)(seconds/3600);
        m = (int)(seconds/60);
        s = seconds%60;
        return h+":"+m+":"+s;
    }

    public static void  restoreViews(Vector<View> capturedPieces){
        for(View piece: capturedPieces){
            piece.setVisibility(View.VISIBLE);
        }
    }

}
