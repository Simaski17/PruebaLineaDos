package com.rinno.simaski.pruebalineados;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //custom drawing view
    private DrawingView drawView;
    private DrawingPointView drawViewPoint;

    int[][] m = new int[8][8];
    int[][] path = new int[8][8];
    int[][] shortpath;
    public String partes;

    public ImageView imagenPrueba;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get drawing view
        drawView = (DrawingView) findViewById(R.id.drawing);
        drawViewPoint = (DrawingPointView) findViewById(R.id.drawingPoint);


        m[0][0] = 0;
        m[0][1] = 3;
        m[0][2] = 5;
        m[0][3] = 2;
        m[0][4] = 10000;
        m[0][5] = 10000;
        m[0][6] = 10000;
        m[0][7] = 10;
        m[1][0] = 3;
        m[1][1] = 0;
        m[1][2] = 5;
        m[1][3] = 8;
        m[1][4] = 4;
        m[1][5] = 10000;
        m[1][6] = 6;
        m[1][7] = 6;
        m[2][0] = 5;
        m[2][1] = 5;
        m[2][2] = 0;
        m[2][3] = 10000;
        m[2][4] = 1;
        m[2][5] = 7;
        m[2][6] = 9;
        m[2][7] = 10000;
        m[3][0] = 2;
        m[3][1] = 6;
        m[3][2] = 10000;
        m[3][3] = 0;
        m[3][4] = 8;
        m[3][5] = 10000;
        m[3][6] = 10000;
        m[3][7] = 14;
        m[4][0] = 10000;
        m[4][1] = 4;
        m[4][2] = 1;
        m[4][3] = 8;
        m[4][4] = 0;
        m[4][5] = 10000;
        m[4][6] = 15;
        m[4][7] = 10000;
        m[5][0] = 10000;
        m[5][1] = 10000;
        m[5][2] = 7;
        m[5][3] = 10000;
        m[5][4] = 10000;
        m[5][5] = 0;
        m[5][6] = 10000;
        m[5][7] = 9;
        m[6][0] = 10000;
        m[6][1] = 6;
        m[6][2] = 9;
        m[6][3] = 10000;
        m[6][4] = 15;
        m[6][5] = 10000;
        m[6][6] = 0;
        m[6][7] = 3;
        m[7][0] = 10;
        m[7][1] = 6;
        m[7][2] = 10000;
        m[7][3] = 14;
        m[7][4] = 10000;
        m[7][5] = 9;
        m[7][6] = 3;
        m[7][7] = 0;

        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.length; j++) {
                if (m[i][j] == 10000) {
                    path[i][j] = -1;
                } else {
                    path[i][j] = i;
                }
            }
        }

        for (int i = 0; i < m.length; i++) {
            path[i][i] = i;
        }

        shortpath = shortestpath(m, path);

        int start = 6;
        int end = 4;

        String myPath = end + "";

        while (path[start][end] != start) {
            myPath = path[start][end] + "->" + myPath;
            end = path[start][end];
        }

        Log.e("TAG","PATH: "+myPath);

        myPath = start + "->" + myPath;
        Log.e("TAG","ESTE ES EL CAMINO: "+myPath);

        partes = myPath;
        //parts =partes.split("->"); // escape .

        drawView.init(partes);
        drawViewPoint.init(partes);
        /*Intent i = new Intent(this, DrawLineActivity.class);
        i.putExtra("partes", partes);
        startActivity(i);*/
        Log.e("TAG", "PARTES PARTES : " + partes);

    }

    public static int[][] shortestpath(int[][] adj, int[][] path) {

        int n = adj.length;
        int[][] ans = new int[n][n];

        // Implementar el algoritmo en una matriz de copia de modo que la adyacencia no es
        //destruido.
        copy(ans, adj);

        // Calcular rutas sucesivamente a través de una mejor k vértices.
        for (int k = 0; k < n; k++) {

            // Es así que entre cada par de puntos posibles.
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {


                    if (ans[i][k] + ans[k][j] < ans[i][j]) {
                        ans[i][j] = ans[i][k] + ans[k][j];
                        path[i][j] = path[k][j];
                    }
                }
            }
        }

        // Devuelva la matriz camino más corto.
        return ans;
    }

    public static void copy(int[][] a, int[][] b) {

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                a[i][j] = b[i][j];
            }
        }
    }

}
