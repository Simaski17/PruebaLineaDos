package com.rinno.simaski.pruebalineados;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by simaski on 31-01-17.
 */

class DrawingPointView extends View {
    Path path;
    Paint paint;
    Paint paint3;
    float length;

    public String[] parts;
    public ArrayList<Float> coordx = new ArrayList<Float>();
    public ArrayList<Float> coordy = new ArrayList<Float>();

    public DrawingPointView(Context context)
    {
        super(context);
    }

    public DrawingPointView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public DrawingPointView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    class Pt{
        float x, y;
        Pt(float _x, float _y){
            x = _x;
            y = _y;
        }
    }

    Pt[] myPath = { new Pt(300, 100),

            new Pt(400, 200),

            new Pt(500, 100),

            new Pt(250, 350),

            new Pt(700, 200),

            new Pt(700, 100),

            new Pt(550, 350),

            new Pt(200, 200)

    };

    public void init(String partes)
    {

        parts =partes.split("->"); // escape .
        //parts = new String[]{"4", "3", "1", "0"};

        paint3 = new Paint();
        paint3.setColor(Color.MAGENTA);

        path = new Path();

        for(int i =0; i < parts.length; i++){

            coordx.add(myPath[Integer.parseInt(parts[i])].x);
            coordy.add(myPath[Integer.parseInt(parts[i])].y);
        }

        path.moveTo(coordx.get(0), coordy.get(0));

        PathMeasure measure = new PathMeasure(path, false);
        length = measure.getLength();

        float[] intervals = new float[]{length, length};


        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(this, "alpha",  1f, .3f);
        fadeOut.setDuration(500);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(this, "alpha", .3f, 1f);
        fadeIn.setDuration(500);

        final AnimatorSet mAnimationSet = new AnimatorSet();

        mAnimationSet.play(fadeIn).after(fadeOut);

        mAnimationSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mAnimationSet.start();
            }
        });
        mAnimationSet.start();



    }

    //is called by animtor object
    public void setPhase(float phase)
    {
        //Log.d("pathview","setPhase called with:" + String.valueOf(phase));
        paint.setPathEffect(createPathEffect(length, phase, 0.0f));
        invalidate();//will calll onDraw

    }

    private PathEffect createPathEffect(float pathLength, float phase, float offset)
    {
        return new DashPathEffect(new float[] {
                pathLength, pathLength
        },
                Math.max(phase * pathLength, offset));
    }

    @Override
    public void onDraw(Canvas c)
    {
        super.onDraw(c);
        c.drawPath(path, paint);
        c.drawCircle(coordx.get(0), coordy.get(0), 50, paint3);
    }
}
