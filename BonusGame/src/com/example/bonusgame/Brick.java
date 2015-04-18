package com.example.bonusgame;

import java.util.Random;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;

public class Brick {
    public PointF p;
    boolean show;
    int width;
    int height;
    int c;
    Bitmap bit;
    
    public Brick(int w,int h){
    	p=new PointF();
    	show=true;
    	width=150;
    	height=40;
    	p.x=w;
    	p.y=h;
    	int number = new Random().nextInt(1);
    	if(number==1)
    	c=Color.CYAN;
    	else
    	c=Color.GREEN;
    	
    	
    	
    }
    
}
