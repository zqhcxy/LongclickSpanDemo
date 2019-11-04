package com.example.zqh.longclickspandemo;

import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    GestureDetectorCompat mLinkDetectorCompat;

    private GestureDetectorCompat mNormalDetectorCompat;
    private GestureDetectorCompatHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView text_tv = findViewById(R.id.text_tv);
        LinearLayout linearLayout = findViewById(R.id.test_ly);
        linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Log.i("spanclick", "ly onLongClick");
                return false;
            }
        });

        mLinkDetectorCompat = new GestureDetectorCompat(this, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.i("spanclick", "span onSingleTapUp");
                return super.onSingleTapUp(e);
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Log.i("spanclick", "span onLongClick");
                if(helper!=null){
                    helper.setLinkLongClick(true);
                }
                super.onLongPress(e);
            }
        });

        mNormalDetectorCompat = new GestureDetectorCompat(this, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.i("spanclick", "text onSingleTapUp");
                return super.onSingleTapUp(e);
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Log.i("spanclick", "text onLongClick");
                super.onLongPress(e);
            }
        });


        SpannableString spannableString = new SpannableString("nihao,3322221,wqnihaonihaonihaonihaonihaonihao");
        spannableString.setSpan(new ClickableSpan() {

            @Override
            public void onClick(View widget) {
                if(helper!=null&&helper.isLinkLongClick()){
                    Log.i("spanclick", "Skip span onSpanClick");
                    helper.setLinkLongClick(false);
                    return;
                }
                Log.i("spanclick", "span onSpanClick");

            }
        }, 0, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        text_tv.setText(spannableString);
        helper=new GestureDetectorCompatHelper(mNormalDetectorCompat,mLinkDetectorCompat);
        text_tv.setMovementMethod(new MyLinkMovementMethod(helper));//超链接要可点击，需要这句
    }
}

