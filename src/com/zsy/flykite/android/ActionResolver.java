package com.zsy.flykite.android;

import android.content.DialogInterface;

public interface ActionResolver {
	//有两个按钮
    interface IAlertDialogCallback{
    	public String getTitle();
    	public String getMessage();
    	public String getPositiveText();
    	public String getNegativeText();
    	public void positiveClick(DialogInterface dialog, int which);
        public void negativeClick(DialogInterface dialog, int which);
    }
    public void showAlertDialog(IAlertDialogCallback alertDialogCallback);

    //只有一个按钮
    interface IAlertDialogCallback2{
    	public String getTitle();
    	public String getMessage();
    	public String getPositiveText();
    	public void positiveClick(DialogInterface dialog, int which);
    }
    public void showAlertDialog(IAlertDialogCallback2 alertDialogCallback2);

}
