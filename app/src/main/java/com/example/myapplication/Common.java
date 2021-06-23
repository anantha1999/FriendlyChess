package com.example.myapplication;

public class Common {
    public static String code;
    public static int id;
    public static Time time = new Time();
    public static int time_increment = 0;
    public static WhiteBlack whiteBlack = new WhiteBlack();
    public static boolean underCheck = false;
    public static boolean isTimer = false;
    public static boolean gameOver = false;
    public static WhiteBlack previousMove = new WhiteBlack();
    public static WhiteBlack attackingPiece = new WhiteBlack();
    public static WhiteBlack playerKing = new WhiteBlack();
}
