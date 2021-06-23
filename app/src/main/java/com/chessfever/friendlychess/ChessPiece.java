package com.chessfever.friendlychess;

import android.widget.ImageView;

public class ChessPiece {
    public int id;
    public String name;
    public boolean firstMove = true;
    public ImageView piece;
    public Location location;
    public boolean allowed = true;
    public boolean captured = false;
}
