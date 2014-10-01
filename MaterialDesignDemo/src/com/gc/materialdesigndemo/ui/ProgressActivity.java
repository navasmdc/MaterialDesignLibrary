package com.gc.materialdesigndemo.ui;

import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.views.CheckBox;
import com.gc.materialdesign.views.ProgressBarDetermininate;
import com.gc.materialdesign.views.ProgressBarIndeterminateDeterminate;
import com.gc.materialdesign.views.Switch;
import com.gc.materialdesign.widgets.SnackBar;
import com.gc.materialdesigndemo.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;


public class ProgressActivity extends Activity {
	
	int backgroundColor = Color.parseColor("#1E88E5");

    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        
        int color = getIntent().getIntExtra("BACKGROUND", Color.BLACK);
        findViewById(R.id.progressBarCircularIndetermininate).setBackgroundColor(color);
        findViewById(R.id.progressBarIndeterminate).setBackgroundColor(color);
        findViewById(R.id.progressBarIndeterminateDeterminate).setBackgroundColor(color);
        findViewById(R.id.progressDeterminate).setBackgroundColor(color);
        findViewById(R.id.slider).setBackgroundColor(color);
        findViewById(R.id.sliderNumber).setBackgroundColor(color);
        
        
        progreesBarDetermininate = (ProgressBarDetermininate) findViewById(R.id.progressDeterminate);
		progressTimer.start();
		progressBarIndeterminateDeterminate = (ProgressBarIndeterminateDeterminate) findViewById(R.id.progressBarIndeterminateDeterminate);
		progressTimer2.start();
    } 
    
    
    
ProgressBarDetermininate progreesBarDetermininate;
    
    Thread progressTimer = new Thread(new Runnable() {
		
		@Override
		public void run() {
			for(int i = 0; i <= 100; i++){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				handler.sendMessage(new Message());
			}
		}
	});
	
	Handler handler = new Handler(new Handler.Callback() {
		int progress = 0;
		@Override
		public boolean handleMessage(Message msg) {
			progreesBarDetermininate.setProgress(progress++);
			return false;
		}
	});
	
	ProgressBarIndeterminateDeterminate progressBarIndeterminateDeterminate;
	
	Thread progressTimer2 = new Thread(new Runnable() {
		
		@Override
		public void run() {
			try {
			Thread.sleep(4000);
			for(int i = 0; i <= 100; i++){
				
					Thread.sleep(100);
				
				handler2.sendMessage(new Message());
			}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	});
	
	Handler handler2 = new Handler(new Handler.Callback() {
		int progress = 0;
		@Override
		public boolean handleMessage(Message msg) {
			progressBarIndeterminateDeterminate.setProgress(progress++);
			return false;
		}
	});
    

}
