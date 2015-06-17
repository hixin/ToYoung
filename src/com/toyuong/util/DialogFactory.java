package com.toyuong.util;

import com.example.toyoung.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;



public class DialogFactory {

	public static Dialog creatRequestDialog(final Context context, String tip) {
		final Dialog dialog = new Dialog(context, R.style.dialog);
		dialog.setContentView(R.layout.dialog_load);
		Window window = dialog.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		int width = ScreenUtil.getScreenWidth(context);
		lp.width = (int) (0.6 * width);

		dialog.setCanceledOnTouchOutside(false);
		TextView titleTxtv = (TextView) dialog.findViewById(R.id.tvLoad);
		if (tip == null || tip.length() == 0) {
			titleTxtv.setText("正在加载...");
		} else {
			titleTxtv.setText(tip);
		}

		return dialog;
	}
	public static void ToastDialog(Context context, String title, String msg) {
		new AlertDialog.Builder(context).setIcon(null).setTitle(title).setMessage(msg)
				.setPositiveButton("确定", null).create().show();
	}
}
