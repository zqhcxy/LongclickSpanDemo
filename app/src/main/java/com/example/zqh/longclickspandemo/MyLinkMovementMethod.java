package com.example.zqh.longclickspandemo;

import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * 通过LinkMovementMethod监听，把超链接和非超链接点击事件分别代理给GestureDetectorCompat，以此来区分各自的点击/长按事件
 * <p><说明/>
 * a、实现的是把文本的事件分别区分为超链接和非超链接，之所以这样是因为，ClickableSpan不支持长按事件。
 * b、这样实现有个不完美的地方，就是 超链接的ClickableSpan点击事件会在长按的时候也触发，所有需要添加一个标志位，在是长按的时候
 * 不执行ClickableSpan事件,这个标准在{@link GestureDetectorCompatHelper}需要自行维护，分别是超链接的长按时设置为true，触发
 * ClickableSpan 时候，判断为ture后再设置为false
 * c、使用这个类，同时又去使用textview本身的点击或长按事件，就需要修改下面的触摸监听，无非就是截取事件等操作，自行调整。
 *
 * @author zqhcxy 2019/11/04
 */
public class MyLinkMovementMethod extends LinkMovementMethod {


    private GestureDetectorCompatHelper mHelper;

    public MyLinkMovementMethod(GestureDetectorCompatHelper helper) {
        mHelper = helper;
    }

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {

        int action = event.getAction();

        if (action == MotionEvent.ACTION_UP ||
                action == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();

            x += widget.getScrollX();
            y += widget.getScrollY();

            Layout layout = widget.getLayout();
            int line = layout.getLineForVertical(y);
            int off = layout.getOffsetForHorizontal(line, x);
            ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);

            //当前点击的位置是否是超链接位置
            if (link.length != 0) {//有数据
                if (mHelper != null) {
                    mHelper.getLinkCompat().onTouchEvent(event);
                }
                //去掉选中状态
                Selection.removeSelection(buffer);
            } else {//点击非超链接位置
                if (mHelper != null) {
                    mHelper.getTextCompat().onTouchEvent(event);
                }
//                Selection.removeSelection(buffer);
            }
        } else if (action == MotionEvent.ACTION_CANCEL) {
            if (mHelper != null) {
                mHelper.getLinkCompat().onTouchEvent(event);
                mHelper.getTextCompat().onTouchEvent(event);
            }
        }


        return super.onTouchEvent(widget, buffer, event);
    }
}
