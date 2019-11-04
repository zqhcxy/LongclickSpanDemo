package com.example.zqh.longclickspandemo;

import android.support.v4.view.GestureDetectorCompat;

/**
 * 配合{@link MyLinkMovementMethod}使用的帮助类
 * <p> 主要是 封装了需要一些数据，做统一管理，更便于维护。如果超链接点击事件需要传递数据，可以在这里添加。
 * @author zqhcxy 2019/11/04
 */
public class GestureDetectorCompatHelper {


    private GestureDetectorCompat linkCompat;
    private GestureDetectorCompat textCompat;

    /**
     * 是否是超链接的长按
     */
    private boolean isLinkLongClick;


    public GestureDetectorCompatHelper(GestureDetectorCompat textCompat,GestureDetectorCompat linkCompat){
        this.linkCompat =linkCompat;
        this.textCompat = textCompat;
    }



    public GestureDetectorCompat getLinkCompat() {
        return linkCompat;
    }

    public void setLinkCompat(GestureDetectorCompat linkCompat) {
        this.linkCompat = linkCompat;
    }

    public GestureDetectorCompat getTextCompat() {
        return textCompat;
    }

    public void setTextCompat(GestureDetectorCompat textCompat) {
        this.textCompat = textCompat;
    }

    public boolean isLinkLongClick() {
        return isLinkLongClick;
    }

    public void setLinkLongClick(boolean linkLongClick) {
        isLinkLongClick = linkLongClick;
    }
}
