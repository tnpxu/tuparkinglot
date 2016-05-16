package com.tnpxu.tuparkinglot.customview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.tnpxu.tuparkinglot.ParkingDetailActivity;
import com.tnpxu.tuparkinglot.R;
import com.tnpxu.tuparkinglot.api.responsedata.SlotStatus;

import java.util.List;

/**
 * Created by tnpxu on 4/21/16 AD.
 */
public class ParkingView extends View {

    private int widthMeasure;
    private int heightMeasure;
    public List<SlotStatus> slotStatusList;
    public float realWidth;
    public float realHeight;

    public float widthDrawingBox;
    public float heightDrawingBox;

    private static float MIN_ZOOM = 1f;
    private static float MAX_ZOOM = 5f;

    private float scaleFactor = 1.f;
    private ScaleGestureDetector detector;


    public ParkingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        detector = new ScaleGestureDetector(getContext(), new ScaleListener());

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Canvas mCanvas = canvas;
        //1st must set drawingbox
        setDrawingBox();

        canvas.save();
        canvas.scale(scaleFactor, scaleFactor);

        if(slotStatusList != null) {

            for(SlotStatus slotStatus : slotStatusList) {
                float xPercent = Float.parseFloat(slotStatus.getX());
                float yPercent = Float.parseFloat(slotStatus.getY());
                float widthPercent = Float.parseFloat(slotStatus.getWidth());
                float heightPercent = Float.parseFloat(slotStatus.getHeight());
                boolean isAvailable = slotStatus.isAvailable();

                drawingCarSlot(mCanvas,xPercent,yPercent,widthPercent,heightPercent,isAvailable);
            }
        }

//        drawingCarSlot(mCanvas,0.93f,4.95f,7.38f,57.95f,false);
//        drawingCarSlot(mCanvas,8.39f,3.89f,7.53f,59.72f,true);
//        drawingCarSlot(mCanvas,15.93f,2.12f,7.77f,61.84f,false);
//        drawingCarSlot(mCanvas,23.15f,2.12f,8.16f,61.84f,true);
//        drawingCarSlot(mCanvas,30.92f,3.18f,7.54f,60.78f,false);
//        drawingCarSlot(mCanvas,38.38f,3.18f,7.54f,60.78f,false);
//        drawingCarSlot(mCanvas,45.77f,2.12f,7.93f,61.84f,true);
//        drawingCarSlot(mCanvas,53.38f,2.12f,7.77f,62.54f,false);
//        drawingCarSlot(mCanvas,60.76f,2.12f,7.93f,61.84f,true);
//        drawingCarSlot(mCanvas,68.53f,2.12f,7.77f,62.54f,false);
//        drawingCarSlot(mCanvas,75.99f,2.12f,7.77f,61.84f,true);
//        drawingCarSlot(mCanvas,83.76f,3.18f,7.38f,61.84f,true);
//        drawingCarSlot(mCanvas,90.99f,2.12f,8.39f,62.54f,false);
//        drawingCarSlot(mCanvas,74.52f,62.19f,15.54f,34.28f,false);
//        drawingCarSlot(mCanvas,60.53f,62.19f,15.54f,34.28f,false);
//        drawingCarSlot(mCanvas,45.54f,62.19f,15.38f,34.28f,true);
//        drawingCarSlot(mCanvas,30.77f,62.19f,15.15f,34.28f,true);
//        drawingCarSlot(mCanvas,15.54f,62.19f,15.38f,33.57f,false);
//        drawingCarSlot(mCanvas,0.93f,62.19f,15f,33.57f,true);

        canvas.restore();
    }

    public void setParkingSlot(List<SlotStatus> slotStatusList,float realWidth,float realHeight){
        this.slotStatusList = slotStatusList;
        this.realWidth = realWidth;
        this.realHeight = realHeight;
    }

    //this method resizing box side to symmetry box that each side equal minimum side of origin size
    public void setDrawingBox() {

        float widthRatio = 1f;
        float heightRatio = 1f;
        float baseRatio = 1f;

        float widthSymmetryBox = 1f;
        float heightSymmetryBox = 1f;

        // Symmetry box
        if(getWidth() > getHeight()) {
            widthSymmetryBox = getHeight();
            heightSymmetryBox = getHeight();
        }

        if(getWidth() < getHeight()) {
            widthSymmetryBox = getWidth();
            heightSymmetryBox = getWidth();
        }

        if(getHeight() == getWidth()) {
            widthSymmetryBox = getWidth();
            heightSymmetryBox = getWidth();
        }

        // find base ratio of real width,height
        if(realHeight >= realWidth) {
            baseRatio = realHeight;
        } else {
            baseRatio = realWidth;
        }

        //find ratio
        widthRatio = realWidth / baseRatio;
        heightRatio = realHeight / baseRatio;

        //find width height drawing box
        widthDrawingBox = widthSymmetryBox * widthRatio;
        heightDrawingBox = heightSymmetryBox * heightRatio;
    }

    public void drawingCarSlot(Canvas mCanvas, float xPercent,float yPercent,float widthPercent,float heightPercent,boolean isAvailable) {

        Bitmap mBitmapParkingSlot;
        Matrix coordinateMatrix = new Matrix();
        //vertical slot
        //widthPercent  <= heightPercent
        if(widthPercentToRealSize(widthPercent,widthDrawingBox,false)  <= heightPercentToRealSize(heightPercent,heightDrawingBox,false)) {

            //car slot
            if(!isAvailable) {
               mBitmapParkingSlot = BitmapFactory.decodeResource(getResources(),R.drawable.ic_car_vertical);

                //check width , height to shrink if height > width more than 3times shirnk it
//                if(heightPercent / widthPercent >= 3.0) {
//
//                    mBitmapParkingSlot = getResizedBitmap(
//                            mBitmapParkingSlot,
//                            widthPercentToRealSize(widthPercent,getWidth(),false),
//                            heightPercentToRealSize(heightPercent,getHeight(),true)
//                            );
//                    //shrink height then offset y coordinate with 1.5 too
//                    coordinateMatrix.postTranslate(
//                            widthPercentToRealSize(xPercent, getWidth(), false),
//                            heightPercentToRealSize(yPercent + (heightPercent - (heightPercentToRealSize(heightPercent, getHeight(), true) * 100 / getHeight())), getHeight(), false)
//                    );
//
//                    mCanvas.drawBitmap(mBitmapParkingSlot,coordinateMatrix,null);
//
//
//
//                } else {
//
//                    mBitmapParkingSlot = getResizedBitmap(
//                            mBitmapParkingSlot,
//                            widthPercentToRealSize(widthPercent,getWidth(),false),
//                            heightPercentToRealSize(heightPercent,getHeight(),false)
//                    );
//
//                    coordinateMatrix.postTranslate(
//                            widthPercentToRealSize(xPercent,getWidth(),false),
//                            heightPercentToRealSize(yPercent,getHeight(),false)
//                    );
//
//                    mCanvas.drawBitmap(mBitmapParkingSlot,coordinateMatrix,null);
//
//                }

                mBitmapParkingSlot = getResizedBitmap(
                        mBitmapParkingSlot,
                        widthPercentToRealSize(widthPercent,widthDrawingBox,false),
                        heightPercentToRealSize(heightPercent,heightDrawingBox,false)
                );

                coordinateMatrix.postTranslate(
                        widthPercentToRealSize(xPercent,widthDrawingBox,false),
                        heightPercentToRealSize(yPercent,heightDrawingBox,false)
                );

                mCanvas.drawBitmap(mBitmapParkingSlot,coordinateMatrix,null);

            }

            //empty slot
            if(isAvailable) {
                mBitmapParkingSlot = BitmapFactory.decodeResource(getResources(),R.drawable.ic_empty_vertical);

                //check width , height to shrink if height > width more than 3times shirnk it
//                if(heightPercent / widthPercent >= 3.0) {
//
//                    mBitmapParkingSlot = getResizedBitmap(
//                            mBitmapParkingSlot,
//                            widthPercentToRealSize(widthPercent,getWidth(),false),
//                            heightPercentToRealSize(heightPercent,getHeight(),true)
//                    );
//
//                    //shrink height then offset y coordinate with 1.5 too
//                    coordinateMatrix.postTranslate(
//                            widthPercentToRealSize(xPercent,getWidth(),false),
//                            heightPercentToRealSize(yPercent + (heightPercent - (heightPercentToRealSize(heightPercent, getHeight(), true) * 100 / getHeight())), getHeight(), false)
//                    );
//
//                    mCanvas.drawBitmap(mBitmapParkingSlot,coordinateMatrix,null);
//
//
//                } else {
//
//                    mBitmapParkingSlot = getResizedBitmap(
//                            mBitmapParkingSlot,
//                            widthPercentToRealSize(widthPercent,getWidth(),false),
//                            heightPercentToRealSize(heightPercent,getHeight(),false)
//                    );
//
//                    coordinateMatrix.postTranslate(
//                            widthPercentToRealSize(xPercent,getWidth(),false),
//                            heightPercentToRealSize(yPercent,getHeight(),false)
//                    );
//
//                    mCanvas.drawBitmap(mBitmapParkingSlot,coordinateMatrix,null);
//
//                }

                mBitmapParkingSlot = getResizedBitmap(
                        mBitmapParkingSlot,
                        widthPercentToRealSize(widthPercent,widthDrawingBox,false),
                        heightPercentToRealSize(heightPercent,heightDrawingBox,false)
                );

                coordinateMatrix.postTranslate(
                        widthPercentToRealSize(xPercent,widthDrawingBox,false),
                        heightPercentToRealSize(yPercent,heightDrawingBox,false)
                );

                mCanvas.drawBitmap(mBitmapParkingSlot,coordinateMatrix,null);
            }
        }

        //horizontal slot
        //widthReal  > heightReal
        if(widthPercentToRealSize(widthPercent,widthDrawingBox,false)  > heightPercentToRealSize(heightPercent,heightDrawingBox,false)) {

            //car slot
            if(!isAvailable) {
                mBitmapParkingSlot = BitmapFactory.decodeResource(getResources(),R.drawable.ic_car_horizontal);

                //check width , height to shrink if width > height more than 3times shirnk it
//                if(widthPercent / heightPercent >= 3.0) {
//
//                    mBitmapParkingSlot = getResizedBitmap(
//                            mBitmapParkingSlot,
//                            widthPercentToRealSize(widthPercent,getWidth(),true),
//                            heightPercentToRealSize(heightPercent,getHeight(),false)
//                    );
//
//                    //shrink width then offset x coordinate with 1.5 too
//                    coordinateMatrix.postTranslate(
//                            widthPercentToRealSize(xPercent,getWidth(),true),
//                            heightPercentToRealSize(yPercent,getHeight(),false)
//                    );
//
//                    mCanvas.drawBitmap(mBitmapParkingSlot,coordinateMatrix,null);
//
//
//                } else {
//
//                    mBitmapParkingSlot = getResizedBitmap(
//                            mBitmapParkingSlot,
//                            widthPercentToRealSize(widthPercent,getWidth(),false),
//                            heightPercentToRealSize(heightPercent,getHeight(),false)
//                    );
//
//                    coordinateMatrix.postTranslate(
//                            widthPercentToRealSize(xPercent,getWidth(),false),
//                            heightPercentToRealSize(yPercent,getHeight(),false)
//                    );
//
//                    mCanvas.drawBitmap(mBitmapParkingSlot,coordinateMatrix,null);
//
//                }

                mBitmapParkingSlot = getResizedBitmap(
                        mBitmapParkingSlot,
                        widthPercentToRealSize(widthPercent,widthDrawingBox,false),
                        heightPercentToRealSize(heightPercent,heightDrawingBox,false)
                );

                coordinateMatrix.postTranslate(
                        widthPercentToRealSize(xPercent,widthDrawingBox,false),
                        heightPercentToRealSize(yPercent,heightDrawingBox,false)
                );

                mCanvas.drawBitmap(mBitmapParkingSlot,coordinateMatrix,null);
            }

            //empty slot
            if(isAvailable) {
                mBitmapParkingSlot = BitmapFactory.decodeResource(getResources(),R.drawable.ic_empty_horizontal);

                //check width , height to shrink if height > width more than 3times shirnk it
//                if(widthPercent / heightPercent >= 3.0) {
//
//                    mBitmapParkingSlot = getResizedBitmap(
//                            mBitmapParkingSlot,
//                            widthPercentToRealSize(widthPercent,getWidth(),true),
//                            heightPercentToRealSize(heightPercent,getHeight(),false)
//                    );
//
//                    //shrink width then offset x coordinate with 1.5 too
//                    coordinateMatrix.postTranslate(
//                            widthPercentToRealSize(xPercent,getWidth(),true),
//                            heightPercentToRealSize(yPercent,getHeight(),false)
//                    );
//
//                    mCanvas.drawBitmap(mBitmapParkingSlot,coordinateMatrix,null);
//
//
//                } else {
//
//                    mBitmapParkingSlot = getResizedBitmap(
//                            mBitmapParkingSlot,
//                            widthPercentToRealSize(widthPercent,getWidth(),false),
//                            heightPercentToRealSize(heightPercent,getHeight(),false)
//                    );
//
//                    coordinateMatrix.postTranslate(
//                            widthPercentToRealSize(xPercent,getWidth(),false),
//                            heightPercentToRealSize(yPercent,getHeight(),false)
//                    );
//
//                    mCanvas.drawBitmap(mBitmapParkingSlot,coordinateMatrix,null);
//
//                }

                mBitmapParkingSlot = getResizedBitmap(
                        mBitmapParkingSlot,
                        widthPercentToRealSize(widthPercent,widthDrawingBox,false),
                        heightPercentToRealSize(heightPercent,heightDrawingBox,false)
                );

                coordinateMatrix.postTranslate(
                        widthPercentToRealSize(xPercent,widthDrawingBox,false),
                        heightPercentToRealSize(yPercent,heightDrawingBox,false)
                );

                mCanvas.drawBitmap(mBitmapParkingSlot,coordinateMatrix,null);
            }

        }
    }


    public float widthPercentToRealSize(float percentWidth,float areaWidth,boolean shrinkMode) {
        float realSize = 0f;
//        if(shrinkMode) {
//            realSize = (percentWidth / 1.5f * areaWidth) / 100;
//        } else {
//            realSize = (percentWidth  * areaWidth) / 100;
//        }

        realSize = (percentWidth  * areaWidth) / 100;

        return realSize;

    }

    public float heightPercentToRealSize(float percentHeight,float areaHeight,boolean shrinkMode) {

        float realSize = 0f;
//        if(shrinkMode) {
//            realSize = (percentHeight / 1.5f * areaHeight) / 100;
//        } else {
//            realSize = (percentHeight * areaHeight) / 100;
//        }

        realSize = (percentHeight * areaHeight) / 100;

        return realSize;

    }

    public Bitmap getResizedBitmap(Bitmap bm, float newWidth, float newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth =  newWidth / width;
        float scaleHeight =  newHeight / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        widthMeasure = getMeasuredWidth();
        heightMeasure = getMeasuredHeight();

        setMeasuredDimension(widthMeasure, heightMeasure);
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= detector.getScaleFactor();
            scaleFactor = Math.max(MIN_ZOOM, Math.min(scaleFactor, MAX_ZOOM));
            invalidate();
            return true;
        }

    }
}
