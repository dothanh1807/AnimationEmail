package com.vllenin.animationemail;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class EMail extends View {

    private Context mContext;

    private Paint mPaint;

    private Path mPathLid;

    private Path mPathTop;

    private Path mPathBottom;

    private Path mPathLeft;

    private Path mPathRight;

    private int openProgress;

    private Point cLid;
    private Point aLid;
    private Point bLid;
    int layout_width;
    int layout_height;

    public EMail(Context context) {
        super(context);
    }

    public EMail(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs){
        int[] attrsArray = new int[] {android.R.attr.layout_width, android.R.attr.layout_height};
        TypedArray ta = mContext.obtainStyledAttributes(attrs, attrsArray);
        layout_width = ta.getLayoutDimension(0, ViewGroup.LayoutParams.MATCH_PARENT);
        layout_height = ta.getLayoutDimension(1, ViewGroup.LayoutParams.MATCH_PARENT);
        ta.recycle();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mPaint.setStrokeWidth(6);// Corner goc khi ve bang Path
//        CornerPathEffect corEffect = new CornerPathEffect(20f);
//        mPaint.setPathEffect(corEffect);

        openProgress = layout_height*2/3;
        aLid = new Point(0, layout_height/3);
        bLid = new Point(layout_width, layout_height/3);
        cLid = new Point(layout_width/2, openProgress);
        mPathLid = new Path();
        mPathLid.moveTo(aLid.x, aLid.y);
        mPathLid.lineTo(aLid.x, aLid.y);
        mPathLid.lineTo(bLid.x, bLid.y);
        mPathLid.lineTo(cLid.x, cLid.y);
        mPathLid.close();

        Point a = new Point(0, layout_height/3);
        Point b = new Point(layout_width, layout_height/3);
        Point c = new Point(layout_width/2, openProgress);
        mPathTop = new Path();
        mPathLid.moveTo(a.x, a.y);
        mPathTop.lineTo(a.x, a.y);
        mPathTop.lineTo(b.x, b.y);
        mPathTop.lineTo(c.x, c.y);
        mPathTop.close();

        Point a1 = new Point(0, layout_height/3);
        Point b1 = new Point(layout_width/2, layout_height*2/3);
        Point c1 = new Point(0, layout_height);
        mPathLeft = new Path();
        mPathLid.moveTo(a1.x, a1.y);
        mPathLeft.lineTo(a1.x, a1.y);
        mPathLeft.lineTo(b1.x, b1.y);
        mPathLeft.lineTo(c1.x, c1.y);
        mPathLeft.close();

        Point a2 = new Point(layout_width, layout_height/3);
        Point b2 = new Point(layout_width/2, layout_height*2/3);
        Point c2 = new Point(layout_width, layout_height);
        mPathRight = new Path();
        mPathRight.moveTo(a2.x, a2.y);
        mPathRight.lineTo(a2.x, a2.y);
        mPathRight.lineTo(b2.x, b2.y);
        mPathRight.lineTo(c2.x, c2.y);
        mPathRight.close();

        Point a3 = new Point(layout_width/2, layout_height*2/3);
        Point b3 = new Point(0, layout_height);
        Point c3 = new Point(layout_width, layout_height);
        mPathBottom = new Path();
        mPathBottom.moveTo(a3.x, a3.y);
        mPathBottom.lineTo(a3.x, a3.y);
        mPathBottom.lineTo(b3.x, b3.y);
        mPathBottom.lineTo(c3.x, c3.y);
        mPathBottom.close();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {


        }


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        Log.d("XXX", "X: " + getX());
        Log.d("XXX", "Y: " + getTranslationY());
        mPaint.setColor(mContext.getResources().getColor(R.color.color3));
        canvas.drawPath(mPathLid, mPaint);

//        mPaint.setColor(mContext.getResources().getColor(R.color.color3));
//        canvas.drawPath(mPathTop, mPaint);

        mPaint.setColor(mContext.getResources().getColor(R.color.color2));
        canvas.drawPath(mPathLeft, mPaint);

        mPaint.setColor(mContext.getResources().getColor(R.color.color2));
        canvas.drawPath(mPathRight, mPaint);

        mPaint.setColor(mContext.getResources().getColor(R.color.color1));
        canvas.drawPath(mPathBottom, mPaint);
    }

    public void setOpenProgress(int open) {
        openProgress = open;
        aLid = new Point(0, layout_height/3);
        bLid = new Point(layout_width, layout_height/3);
        cLid = new Point(layout_width/2, openProgress);
        mPathLid = new Path();
        mPathLid.moveTo(aLid.x, aLid.y);
        mPathLid.lineTo(aLid.x, aLid.y);
        mPathLid.lineTo(bLid.x, bLid.y);
        mPathLid.lineTo(cLid.x, cLid.y);
        mPathLid.close();

        invalidate();
    }

    public int getOpenProgress() {
        return openProgress;
    }

}
