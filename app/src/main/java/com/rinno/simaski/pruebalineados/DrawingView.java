package com.rinno.simaski.pruebalineados;

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
import android.view.animation.Animation;

import java.util.ArrayList;

/**
 * Created by simaski on 31-01-17.
 */

class DrawingView extends View {
    Path path;
    Paint paint;
    Paint paint2;
    float length;

    public String[] parts;
    public ArrayList<Float> coordx = new ArrayList<Float>();
    public ArrayList<Float> coordy = new ArrayList<Float>();

    public DrawingView(Context context)
    {
        super(context);
    }

    public DrawingView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public DrawingView(Context context, AttributeSet attrs, int defStyleAttr)
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

        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(15);
        paint.setStyle(Paint.Style.STROKE);

        paint2 = new Paint();
        paint2.setColor(Color.BLUE);

        path = new Path();

        for(int i =0; i < parts.length; i++){
            Log.e("TAG","RECIBIDO: "+parts[i]);

            coordx.add(myPath[Integer.parseInt(parts[i])].x);
            coordy.add(myPath[Integer.parseInt(parts[i])].y);
        }

        path.moveTo(coordx.get(0), coordy.get(0));

        for (int i = 1; i < coordx.size(); i++){

            path.lineTo(coordx.get(i), coordy.get(i));

        }

        // Measure the path
        PathMeasure measure = new PathMeasure(path, false);
        length = measure.getLength();

        float[] intervals = new float[]{length, length};

        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "phase", 1.0f, 0.0f);
        animator.setDuration(5000);
        animator.start();


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
        c.drawCircle(coordx.get(0), coordy.get(0), 30, paint2);
    }
}