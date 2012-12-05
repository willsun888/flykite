package com.zsy.flykite.android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;

public class ActionResolverAndroid implements ActionResolver{
	private Handler uiThread;
    private Context appContext;

    public ActionResolverAndroid(Context appContext) {
            uiThread = new Handler();
            this.appContext = appContext;
    }
    
	@Override
	public void showAlertDialog(final IAlertDialogCallback alertDialog) {
		uiThread.post(new Runnable() {
            public void run() {
                    new AlertDialog.Builder(appContext)
                                    .setTitle(alertDialog.getTitle())
                                    .setMessage(alertDialog.getMessage())
                                    .setPositiveButton(alertDialog.getPositiveText(),new OnClickListener() {
										
										@Override
										public void onClick(DialogInterface dialog, int which) {
											alertDialog.positiveClick(dialog,which);
										}
									})
                                    .setNegativeButton(alertDialog.getNegativeText(), new OnClickListener() {
										
										@Override
										public void onClick(DialogInterface dialog, int which) {
											alertDialog.negativeClick(dialog,which);
										}
									})
                                    .create().show();
            }
    });
	}

	@Override
	public void showAlertDialog(final IAlertDialogCallback2 alertDialog) {
		uiThread.post(new Runnable() {
            public void run() {
                    new AlertDialog.Builder(appContext)
                                    .setTitle(alertDialog.getTitle())
                                    .setMessage(alertDialog.getMessage())
                                    .setPositiveButton(alertDialog.getPositiveText(),new OnClickListener() {
										
										@Override
										public void onClick(DialogInterface dialog, int which) {
											alertDialog.positiveClick(dialog,which);
										}
									})
                                    .create().show();
            }
    });
	}
	
	
}
