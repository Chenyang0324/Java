package com.example.bonusgame;

import java.util.ArrayList;





import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.*;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable.Callback;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.*;

public class MainActivity extends ActionBarActivity {
	
	//PointF f;
	//float h;
    PointF f1;
    int screenwidth;
    int screenhight;
    int boardWidth;
    Ball b;
    Bitmap tempB;
    ArrayList<Brick> bri;
    SurfaceView mv;
    SoundPool sp;
    int counter;
  final int id1 = Menu.FIRST;
 final int id2 = Menu.FIRST+1;
    int soundid;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        sp=new SoundPool(1,AudioManager.STREAM_MUSIC,0);
        soundid=sp.load(getBaseContext(), R.raw.hit,1);
         f1=new PointF();
         f1.x=540;
         f1.y=1701;
         counter=0;
         b=new Ball();
         boardWidth=200;
         bri=new ArrayList<Brick>();
         int tempX=140;
         int tempY=1701/2-400;
         Resources res = getResources();
         tempB=BitmapFactory.decodeResource(res, R.drawable.ic_brick);
//         for(int i=0;i<18;i++)
//         {
//        	 Brick t=new Brick(tempX,tempY);
//        	 bri.add(t);
//        	 tempX=tempX+t.width;
//        	 tempY=tempY+t.height;
//         }
         for(int i=0; i<4;i++)
         {
        	 tempY=tempY-40;
        	 tempX=540-400+50*i;
         	for(int j=0; j<6-i;j++)
         	{
         		
         		Brick t=new Brick(tempX,tempY);
         		t.bit=tempB;
         		bri.add(t);
         		 tempX=tempX+t.width;
         	}
         	
         }
         tempB=BitmapFactory.decodeResource(res, R.drawable.ic_sky);
//        SurfaceView  surfaceView = new SurfaceView(this) ;        
         mv = new T3(this);  
        setContentView(mv); 
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
    	super.onCreateOptionsMenu(menu); 
    	  menu.add(Menu.NONE, id1, Menu.NONE, "Easy");             
          menu.add(Menu.NONE, id2, Menu.NONE,"Hard");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == id1) {
        	boardWidth=200;
 
            T3 t=(T3)mv;
            t.newGame();
            return true;
        } if (id == id2) {
        	boardWidth=150;

        	  T3 t=(T3)mv;
              t.newGame();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    

    public boolean onTouchEvent(MotionEvent event){
    	
    	int action=event.getAction();

    	
    	if(action==MotionEvent.ACTION_DOWN||action==MotionEvent.ACTION_MOVE)
    	{
    		if((int)(event.getX())>(int)(f1.x-200)&&(int)(event.getX())<(int)(f1.x+200)
    				&&(int)(event.getY())>(int)(screenhight-100)&&(int)(event.getY())<(int)(screenhight+300))
    		if((int)(event.getX()-200)<0||(int)(event.getX()+200)>screenwidth)
    		{
    			if((int)(event.getX()-250)<0)
    				f1.x=200;
    			if((int)(event.getX()+250)>screenwidth)
    				f1.x=screenwidth-200;
    		}
    		else
    		{
    			f1.x=event.getX();
    			f1.y=f1.y;
    		}
    	}
    	
    	return true;
    	
    }

    
    public class T3 extends SurfaceView implements Runnable, Callback, android.view.SurfaceHolder.Callback {    
        private SurfaceHolder mHolder;   
        private Thread t;     
        private volatile boolean flag;    
        private Canvas mCanvas; 
        Activity a;
        private Paint p;   
        float m_circle_r = 10;
     	int i=0;
        
        public T3(Context context) {    
            super(context);    
            a=(Activity)context;
            mHolder = getHolder(); 
            mHolder.addCallback(this);  
            p = new Paint(); 
            p.setColor(Color.WHITE); 
            setFocusable(true);    
        }    
        
         
        @Override    
        public void surfaceCreated(SurfaceHolder holder) {    
            if(t == null) {  
            t = new Thread(this); 
            flag = true;      
            t.start(); 
            }  
            else{
            	flag = true;
            	t.start();
            }
           }    
              
      
        @Override    
        public void surfaceChanged(SurfaceHolder holder, int format, int width,    
                int height) {    
        }    
        
      
        @Override    
        public void surfaceDestroyed(SurfaceHolder holder) {    
            mHolder.removeCallback(this);    
            flag = false;  
        }    
        
        @Override    
        public void run() {    
            while (flag) {    
                try {    
                    synchronized (mHolder) {    
                        Thread.sleep(40); 
                        
                        draw(); 
                    }    
                } catch (InterruptedException e) {    
                    e.printStackTrace();    
                } finally {    
                    if (mCanvas != null) {    
                      
        
                    }    
                }    
            }    
        }    
        
      
//        private void draw() {    
//                mCanvas = mHolder.lockCanvas();  
//                f=new PointF((int)mCanvas.getWidth()/2,(int)mCanvas.getHeight()-100);
//                h=(int)mCanvas.getHeight();
//          
//                //mCanvas.drawColor(Color.TRANSPARENT,PorterDuff.Mode.CLEAR);  
//                Paint paint = new Paint();    
//                paint.setColor(Color.RED);    
//                mCanvas.drawRect(0, 0, mCanvas.getWidth(), mCanvas.getHeight(), paint);
//                paint.setColor(Color.BLACK);
//                mCanvas.drawRect((int)(f.x-200), (int)mCanvas.getHeight()-60, (int)(f.x+200),(int)mCanvas.getHeight()-20, paint);
//                int oldlx,oldly;
//                oldly=(int)mCanvas.getHeight()/2-400;
//                oldlx=(int)mCanvas.getWidth()/2-400;
//                for(int i=0; i<4;i++)
//                {
//                	oldly=oldly-20;
//                	oldlx=mCanvas.getWidth()/2-400+50*i;
//                	for(int j=0; j<6-i;j++)
//                	{
//                		
//                		if(j%2==0)
//                			paint.setColor(Color.GREEN);
//                		else
//                			paint.setColor(Color.CYAN);
//                		mCanvas.drawRect(oldlx, oldly, oldlx+150,oldly-20, paint);
//                		oldlx=oldlx+150;
//                	}
//                	
//                }
//                paint.setColor(Color.GRAY);
//                mCanvas.drawCircle((int)mCanvas.getWidth()/2, (int)mCanvas.getHeight()-100, 40, paint);
//                mHolder.unlockCanvasAndPost(mCanvas);
//           
//       
//    }  
        public void newGame(){
        	for (int i = 0; i < bri.size(); i++)
				bri.get(i).show = true;
			b = new Ball();
			f1.x = 540;
			f1.y = 1701;
			flag = true;
			counter=0;
        	t = new Thread(this); 
            flag = true;      
            t.start();       	
        }
        public void showGameOverDialog(){
          	 final AlertDialog.Builder dialogBuilder=
          			new AlertDialog.Builder(getContext());
          	dialogBuilder.setTitle("Gamer Over");
          	dialogBuilder.setCancelable(false);
          	dialogBuilder.setPositiveButton("New Game", new DialogInterface.OnClickListener() {
       			
       			@Override
						public void onClick(DialogInterface dialog, int which) {
						
							newGame();
							
							

						}
					});
          	a.runOnUiThread(new Runnable(){

       			@Override
       			public void run() {
       				    flag=false;
       					dialogBuilder.show(); 
                            
                           
                       
       				
       			}
          		
          	});
          	
          }
        private void draw() {   

        	
     
            mCanvas = mHolder.lockCanvas(); 
           // f=new PointF((int)mCanvas.getWidth()/2,(int)mCanvas.getHeight()-100);
            screenhight=(int)mCanvas.getHeight();
            screenwidth=(int)mCanvas.getWidth();
            b.move(screenwidth, screenhight,f1,bri,sp,soundid);
            if(b.p.y>screenhight)
            {
            flag=false;
          	showGameOverDialog();
            }
            if (mCanvas != null) {    
             
    
              //mCanvas.drawColor(Color.TRANSPARENT,PorterDuff.Mode.CLEAR);  
              Paint paint = new Paint();    
              paint.setColor(Color.WHITE);    
             // mCanvas.drawRect(0, 0, mCanvas.getWidth(), mCanvas.getHeight(), paint);
              Rect temp1=new Rect();
              temp1.top=0;
              temp1.left=0;
              temp1.bottom=screenhight;
              temp1.right=screenwidth;
              mCanvas.drawBitmap(tempB, null, temp1, paint);
              paint.setColor(Color.BLACK);
              mCanvas.drawRect((int)(f1.x-boardWidth), (int)mCanvas.getHeight()-60, (int)(f1.x+boardWidth),(int)mCanvas.getHeight()-20, paint);
              //int oldlx,oldly;
             // oldly=(int)mCanvas.getHeight()/2-400;
             // oldlx=(int)mCanvas.getWidth()/2-400;
              for(int i=0; i<bri.size();i++)
              {
              	    if(bri.get(i).show)
              	    {  	Paint paint2 = new Paint();
              	    	paint.setColor(bri.get(i).c);
              	    	Rect temp=new Rect();
              	    	temp.left=(int)(bri.get(i).p.x);
              	    	temp.right=(int)(bri.get(i).p.x+bri.get(i).width);
              	    	temp.top=(int)(bri.get(i).p.y-bri.get(i).height);
              	    	temp.bottom=(int)(bri.get(i).p.y);
              		   // mCanvas.drawRect(bri.get(i).p.x, bri.get(i).p.y, bri.get(i).p.x+bri.get(i).width,bri.get(i).p.y-bri.get(i).height, paint);
              		    mCanvas.drawBitmap(bri.get(i).bit, null, temp, paint2);
              	    }
              	
              }
              paint.setColor(Color.GRAY);
              mCanvas.drawCircle(b.p.x, b.p.y, b.radius, paint);
              for(int i=0;i<bri.size();i++)
              {
            	  if(bri.get(i).show==false)
            	  {
            		  counter=counter+1;
            	  }
            	
              }
              if(counter==bri.size())
              {
            	  flag=false;
                	showGameOverDialog();
              }
              else{
            	  counter=0;
              }
            
              mHolder.unlockCanvasAndPost(mCanvas);   
            }    
            
        } 
        
    }

}
