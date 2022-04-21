package com.darktornado.library;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;


public class SnackBar {

    private static final PopupWindow window = new PopupWindow();
    private static final Handler handler = new Handler(Looper.getMainLooper());

    private final TextView txt;
    private View parent;
    private long timeout;

    public SnackBar(Context ctx) {
        txt = new TextView(ctx);
        txt.setTextColor(Color.WHITE);
        txt.setTextSize(17);
        timeout = 3000;
        window.setWidth(-1);
        window.setHeight(-2);
        int pad = dip2px(ctx, 16);
        txt.setPadding(pad, pad, pad, pad);
        window.setAnimationStyle(android.R.style.Animation_InputMethod);
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#323232")));
    }

    public static SnackBar makeText(View parent, Context ctx, String text, long timeout) {
        SnackBar bar = new SnackBar(ctx);
        bar.setText(text);
        bar.parent = parent;
        bar.timeout = timeout;
        return bar;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public void updateText(String text) {
        TextView txt = (TextView) window.getContentView();
        txt.setText(text);
        window.setHeight(-2);
        window.update();
    }

    public void setText(String text) {
        txt.setText(text);
    }

    public void setTextSize(float size) {
        txt.setTextSize(size);
    }

    public void setTextColor(int color) {
        txt.setTextColor(color);
    }

    public void setParentView(View parent) {
        this.parent = parent;
    }

    public void setBackground(Drawable drawable) {
        window.setBackgroundDrawable(drawable);
    }

    public void setBackgroundColor(int color) {
        window.setBackgroundDrawable(new ColorDrawable(color));
    }

    public void show() {
        if (window.isShowing()) {
            handler.removeCallbacksAndMessages(null);
            updateText(this.txt.getText().toString());
        } else {
            window.setContentView(txt);
            window.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        }
        if (timeout > 0) handler.postDelayed(() -> {
            if (window.isShowing()) window.dismiss();
        }, timeout);
    }

    public void dismiss(int delay) {
        if (window.isShowing()) handler.removeCallbacksAndMessages(null);
        if (delay > 0) handler.postDelayed(() -> {
            if (window.isShowing()) window.dismiss();
        }, delay);
    }

    public void dismiss() {
        if (window.isShowing()) window.dismiss();
    }


    private int dip2px(Context ctx, int dips) {
        return (int) Math.ceil(dips * ctx.getResources().getDisplayMetrics().density);
    }

}
