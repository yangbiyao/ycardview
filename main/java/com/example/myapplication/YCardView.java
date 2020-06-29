package com.example.myapplication;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * author:yby
 */
public class YCardView extends FrameLayout {
    private Paint mCornerShadowPaint;
    private Paint mEdgeShadowPaint;
    private float mCornerRadius = 5;
    private float mShadowSize = 20;
    private float inset = 20;

    private RectF mCardBounds;
    private Path mCornerShadowPath;

    private int mShadowStartColor = 0xff000000;
    private int mShadowEndColor = 0x00000000;

    public YCardView(@NonNull Context context) {
        super( context, null );
    }

    public YCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super( context, attrs );
        TypedArray a = getContext().obtainStyledAttributes( attrs, R.styleable.YCardView, 0, 0 );
        mShadowStartColor = a.getColor( R.styleable.YCardView_YCardView_ShadowStartColor, 0x00ffffff );
        mShadowEndColor = a.getColor( R.styleable.YCardView_YCardView_ShadowEndColor, 0xffffffff );
        mCornerRadius = a.getDimension( R.styleable.YCardView_YCardView_ConnerRadius, 5 );
        mShadowSize = a.getDimension( R.styleable.YCardView_YCardView_ShadowWith, 10 );
//        inset = mShadowSize;
        a.recycle();
        init();
    }


    public YCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super( context, attrs );
    }

    private void init() {
        setPadding( (int)mShadowSize/2, (int)mShadowSize/2, (int)mShadowSize/2, (int)mShadowSize/2 );
        mCornerShadowPaint = new Paint( Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG );
        mCornerShadowPaint.setStyle( Paint.Style.FILL );
        mEdgeShadowPaint = new Paint( mCornerShadowPaint );
        mEdgeShadowPaint.setAntiAlias( false );
        buildShadowCorners();
        setWillNotDraw( false );

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw( canvas );
        mCardBounds = new RectF( 0, 0, getWidth(), getHeight() );
        drawShadow( canvas );
    }
    private void drawShadow(Canvas canvas) {
        final float edgeShadowTop = -mCornerRadius - mShadowSize;
        final float inset = mCornerRadius +  mShadowSize / 2;
        final boolean drawHorizontalEdges = mCardBounds.width() - 2 * inset > 0;
        final boolean drawVerticalEdges = mCardBounds.height() - 2 * inset > 0;
        // LT
        int saved = canvas.save();
        canvas.translate( mCardBounds.left + inset, mCardBounds.top + inset );
        canvas.drawPath( mCornerShadowPath, mCornerShadowPaint );
        if (drawHorizontalEdges) {
            canvas.drawRect( 0, edgeShadowTop,
                    mCardBounds.width() - 2 * inset, -mCornerRadius,
                    mEdgeShadowPaint );
        }
        canvas.restoreToCount( saved );
        // RB
        saved = canvas.save();
        canvas.translate( mCardBounds.right - inset, mCardBounds.bottom - inset );
        canvas.rotate( 180f );
        canvas.drawPath( mCornerShadowPath, mCornerShadowPaint );
        if (drawHorizontalEdges) {
            canvas.drawRect( 0, edgeShadowTop,
                    mCardBounds.width() - 2 * inset, -mCornerRadius,
                    mEdgeShadowPaint );
        }
        canvas.restoreToCount( saved );
//        // LB
        saved = canvas.save();
        canvas.translate( mCardBounds.left + inset, mCardBounds.bottom - inset );
        canvas.rotate( 270f );
        canvas.drawPath( mCornerShadowPath, mCornerShadowPaint );
        if (drawVerticalEdges) {
            canvas.drawRect( 0, edgeShadowTop,
                    mCardBounds.height() - 2 * inset, -mCornerRadius, mEdgeShadowPaint );
        }
        canvas.restoreToCount( saved );
        // RT
        saved = canvas.save();
        canvas.translate( mCardBounds.right - inset, mCardBounds.top + inset );
        canvas.rotate( 90f );
        canvas.drawPath( mCornerShadowPath, mCornerShadowPaint );
        if (drawVerticalEdges) {
            canvas.drawRect( 0, edgeShadowTop,
                    mCardBounds.height() - 2 * inset, -mCornerRadius, mEdgeShadowPaint );
        }
        canvas.restoreToCount( saved );
    }

//    private void drawShadow(Canvas canvas) {
//        final float edgeShadowTop = -mCornerRadius - mShadowSize;
////        final float inset = mCornerRadius + mInsetShadow + mRawShadowSize / 2;
//        final boolean drawHorizontalEdges = mCardBounds.width() - 2 * inset > 0;
//        final boolean drawVerticalEdges = mCardBounds.height() - 2 * inset > 0;
//        // LT
//        int saved = canvas.save();
//        canvas.translate( mCardBounds.left + inset, mCardBounds.top + inset );
//        canvas.drawPath( mCornerShadowPath, mCornerShadowPaint );
//        if (drawHorizontalEdges) {
//            canvas.drawRect( 0, edgeShadowTop,
//                    mCardBounds.width() - 2 * inset, -mCornerRadius,
//                    mEdgeShadowPaint );
//        }
//        canvas.restoreToCount( saved );
//        // RB
//        saved = canvas.save();
//        canvas.translate( mCardBounds.right - inset, mCardBounds.bottom - inset );
//        canvas.rotate( 180f );
//        canvas.drawPath( mCornerShadowPath, mCornerShadowPaint );
//        if (drawHorizontalEdges) {
//            canvas.drawRect( 0, edgeShadowTop,
//                    mCardBounds.width() - 2 * inset, -mCornerRadius,
//                    mEdgeShadowPaint );
//        }
//        canvas.restoreToCount( saved );
////        // LB
//        saved = canvas.save();
//        canvas.translate( mCardBounds.left + inset, mCardBounds.bottom - inset );
//        canvas.rotate( 270f );
//        canvas.drawPath( mCornerShadowPath, mCornerShadowPaint );
//        if (drawVerticalEdges) {
//            canvas.drawRect( 0, edgeShadowTop,
//                    mCardBounds.height() - 2 * inset, -mCornerRadius, mEdgeShadowPaint );
//        }
//        canvas.restoreToCount( saved );
//        // RT
//        saved = canvas.save();
//        canvas.translate( mCardBounds.right - inset, mCardBounds.top + inset );
//        canvas.rotate( 90f );
//        canvas.drawPath( mCornerShadowPath, mCornerShadowPaint );
//        if (drawVerticalEdges) {
//            canvas.drawRect( 0, edgeShadowTop,
//                    mCardBounds.height() - 2 * inset, -mCornerRadius, mEdgeShadowPaint );
//        }
//        canvas.restoreToCount( saved );
//    }

    private void buildShadowCorners() {
        RectF innerBounds = new RectF( -mCornerRadius, -mCornerRadius, mCornerRadius, mCornerRadius );
        RectF outerBounds = new RectF( innerBounds );
        outerBounds.inset( -mShadowSize, -mShadowSize );

        if (mCornerShadowPath == null) {
            mCornerShadowPath = new Path();
        } else {
            mCornerShadowPath.reset();
        }
        mCornerShadowPath.setFillType( Path.FillType.EVEN_ODD );
        mCornerShadowPath.moveTo( -mCornerRadius, 0 );
        mCornerShadowPath.rLineTo( -mShadowSize, 0 );
        // outer arc
        mCornerShadowPath.arcTo( outerBounds, 180f, 90f, false );
        // inner arc
        mCornerShadowPath.arcTo( innerBounds, 270f, -90f, false );
        mCornerShadowPath.close();
        float startRatio = mCornerRadius / (mCornerRadius + mShadowSize);
        mCornerShadowPaint.setShader( new RadialGradient( 0, 0, mCornerRadius + mShadowSize,
                new int[]{mShadowStartColor, mShadowStartColor,mShadowEndColor, mShadowEndColor},
                new float[]{0f, startRatio,(1+startRatio)/2.143f, 1f},
                Shader.TileMode.CLAMP ) );

        // we offset the content shadowSize/2 pixels up to make it more realistic.
        // this is why edge shadow shader has some extra space
        // When drawing bottom edge shadow, we use that extra space.
        mEdgeShadowPaint.setShader( new LinearGradient( 0, -mCornerRadius + mShadowSize, 0,
                -mCornerRadius - mShadowSize,
                new int[]{mShadowStartColor, mShadowStartColor, mShadowEndColor,mShadowEndColor},
                new float[]{0f, .5f,.7f, 1f}, Shader.TileMode.CLAMP ) );
        mEdgeShadowPaint.setAntiAlias( false );
    }

}
