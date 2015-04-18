package com.example.bonusgame;

import java.util.ArrayList;

import android.graphics.PointF;
import android.media.SoundPool;

public class Ball {
	public PointF p;
	public int ballVelocityX;
	public int ballVelocityY;
	public int radius;
	
	public Ball(){
          p=new PointF();
		  p.x=540;
	      p.y=1601;
	      ballVelocityX=15;
	      ballVelocityY=-15;
	      radius=20;
		
	}
	public void move(int wid,int height,PointF f, ArrayList<Brick> b, SoundPool sp, int sid){
		p.x=p.x+ballVelocityX;
		p.y=p.y+ballVelocityY;
		
		if(p.x-radius<=0||p.x+radius>=wid)
			ballVelocityX=ballVelocityX*-1;
		if(p.y-radius<=0)
			ballVelocityY=ballVelocityY*-1;
		if(p.y+radius>=f.y-60)
		{
			if(p.x>f.x-200&&p.x<f.x+200)
				ballVelocityY=ballVelocityY*-1;
		}
		
		for(int i=0;i<b.size();i++)
		{
			if(b.get(i).show)
			{
				
				  if(p.y-radius<b.get(i).p.y&&p.y+radius>b.get(i).p.y-b.get(i).height)
				 {
					
					if(p.x>b.get(i).p.x&&p.x<b.get(i).p.x+b.get(i).width)
					{	ballVelocityY=ballVelocityY*-1;
					    b.get(i).show=false;
					    //p.y=b.get(i).p.y+radius+1;
					    sp.play(sid, 1, 1, 0, 0, 1f);
					    break;
					}
					if(p.x+radius>b.get(i).p.x&&p.x<b.get(i).p.x)
					{	ballVelocityY=ballVelocityY*-1;
					    ballVelocityX=ballVelocityY*-1;
					    b.get(i).show=false;
					    //p.y=b.get(i).p.y+radius+1;
					    sp.play(sid, 1, 1, 0, 0, 1f);
					    break;
					}
					if(p.x-radius<b.get(i).p.x+b.get(i).width&&p.x>b.get(i).p.x+b.get(i).width)
					{	ballVelocityY=ballVelocityY*-1;
					    ballVelocityX=ballVelocityY*-1;
					    b.get(i).show=false;
					    //p.y=b.get(i).p.y+radius+1;
					    sp.play(sid, 1, 1, 0, 0, 1f);
					    break;
					}
				 }
				
			
//				if(ballVelocityY>0)
//				{
//				if(p.y+radius>b.get(i).p.y-b.get(i).height)
//				 {
//					
//					if(p.x>b.get(i).p.x&&p.x<b.get(i).p.x+b.get(i).width)
//					{	ballVelocityY=ballVelocityY*-1;
//					    p.y=b.get(i).p.y-radius-1;
//					    b.get(i).show=false;
//					    break;
//					}
//				 }
//				
//				}
			}
		}
	}
	

}
