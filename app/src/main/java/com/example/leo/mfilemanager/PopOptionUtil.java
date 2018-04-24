package com.example.leo.mfilemanager;

/**
 * @Author:Leo
 * @Description
 * @Date: Created in 22:27 2018/2/28
 * @Modified
 */
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.example.leo.mfilemanager.bean.FileBean;
import com.example.leo.mfilemanager.bean.PopClickEvent;

import java.io.FileNotFoundException;


public class PopOptionUtil {

    private Context mContext;
    private int popupWidth;
    private int popupHeight;
    private PopupWindow popupWindow;
    private PopClickEvent mEvent;
    private TextView preBt;
    private TextView nextBt;
    private TextView DelBt;
    private TextView ShareButton;


    public PopOptionUtil(Context context) {
        mContext = context;
        View popupView = LayoutInflater.from(mContext).inflate(R.layout.layout_pop, null);
        preBt = (TextView) popupView.findViewById(R.id.txt_l);
        nextBt = (TextView) popupView.findViewById(R.id.txt_r);
        DelBt= (TextView) popupView.findViewById(R.id.txt_del);
        ShareButton= (TextView) popupView.findViewById(R.id.share);
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupWidth = popupView.getMeasuredWidth();
        popupHeight = popupView.getMeasuredHeight();
    }

    public void show(View view,FileBean fileBean) {
        initEvent(fileBean);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, (location[0] + view.getWidth() / 2) - popupWidth / 2,
                location[1] - popupHeight);
    }


    public void setOnPopClickEvent(PopClickEvent mEvent) {
        this.mEvent = mEvent;
    }

    private void initEvent(final FileBean fileBean) {
        if (mEvent != null) {
            preBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mEvent.onPreClick(fileBean);
                }
            });
            nextBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        mEvent.onNextClick(fileBean);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            DelBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mEvent.onDeleteClick(fileBean);
                }
            });
            ShareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mEvent.onShareClick(fileBean);
                }
            });
        }
    }

    public void hide(){
        popupWindow.dismiss();
    }
}