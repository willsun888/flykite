package com.zsy.flykite.android;

import android.content.DialogInterface;

public interface ActionResolver {
	//��������ť
    interface IAlertDialogCallback{
    	public String getTitle();
    	public String getMessage();
    	public String getPositiveText();
    	public String getNegativeText();
    	public void positiveClick(DialogInterface dialog, int which);
        public void negativeClick(DialogInterface dialog, int which);
    }
    public void showAlertDialog(IAlertDialogCallback alertDialogCallback);

    //ֻ��һ����ť
    interface IAlertDialogCallback2{
    	public String getTitle();
    	public String getMessage();
    	public String getPositiveText();
    	public void positiveClick(DialogInterface dialog, int which);
    }
    public void showAlertDialog(IAlertDialogCallback2 alertDialogCallback2);

}
