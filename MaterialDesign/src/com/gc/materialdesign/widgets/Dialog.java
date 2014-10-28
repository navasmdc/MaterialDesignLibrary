package com.gc.materialdesign.widgets;

import com.gc.materialdesign.R;
import com.gc.materialdesign.views.ButtonFlat;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class Dialog extends android.app.Dialog{
	
	String message;
	TextView messageTextView;
	String title;
	TextView titleTextView;
	
	ButtonFlat buttonAccept;
	ButtonFlat buttonCancel;
	
	
	View.OnClickListener onAcceptButtonClickListener;
	View.OnClickListener onCancelButtonClickListener;
	

	public Dialog(Context context,String title, String message) {
		super(context, android.R.style.Theme_Translucent);
		this.message = message;
		this.title = title;
	}
	
	@Override
	  protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.dialog);
	    
	    this.titleTextView = (TextView) findViewById(R.id.title);
	    setTitle(title);
	    
	    this.messageTextView = (TextView) findViewById(R.id.message);
	    setMessage(message);
	    
	    this.buttonAccept = (ButtonFlat) findViewById(R.id.button_accept);
	    buttonAccept.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
				if(onAcceptButtonClickListener != null)
			    	onAcceptButtonClickListener.onClick(v);
			}
		});
	    this.buttonCancel = (ButtonFlat) findViewById(R.id.button_cancel);
    	buttonCancel.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dismiss();
					if(onCancelButtonClickListener != null)
				    	onCancelButtonClickListener.onClick(v);
				}
			});
	}
	
	// GETERS & SETTERS

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
		messageTextView.setText(message);
	}

	public TextView getMessageTextView() {
		return messageTextView;
	}

	public void setMessageTextView(TextView messageTextView) {
		this.messageTextView = messageTextView;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		if(title == null)
			titleTextView.setVisibility(View.GONE);
		else{
			titleTextView.setVisibility(View.VISIBLE);
			titleTextView.setText(title);
		}
	}

	public TextView getTitleTextView() {
		return titleTextView;
	}

	public void setTitleTextView(TextView titleTextView) {
		this.titleTextView = titleTextView;
	}

	public ButtonFlat getButtonAccept() {
		return buttonAccept;
	}

	public void setButtonAccept(ButtonFlat buttonAccept) {
		this.buttonAccept = buttonAccept;
	}

	public ButtonFlat getButtonCancel() {
		return buttonCancel;
	}

	public void setButtonCancel(ButtonFlat buttonCancel) {
		this.buttonCancel = buttonCancel;
	}

	public void setOnAcceptButtonClickListener(
			View.OnClickListener onAcceptButtonClickListener) {
		this.onAcceptButtonClickListener = onAcceptButtonClickListener;
		if(buttonAccept != null)
			buttonAccept.setOnClickListener(onAcceptButtonClickListener);
	}

	public void setOnCancelButtonClickListener(
			View.OnClickListener onCancelButtonClickListener) {
		this.onCancelButtonClickListener = onCancelButtonClickListener;
		if(buttonCancel != null)
			buttonCancel.setOnClickListener(onAcceptButtonClickListener);
	}
	
	
	
	

}
