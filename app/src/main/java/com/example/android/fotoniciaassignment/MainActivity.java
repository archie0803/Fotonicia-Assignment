package com.example.android.fotoniciaassignment;

import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {

    Button image1, image2, baseSelect, create, share;
    //CardView cardView;
    RelativeLayout relativeLayout;
    ImageView element1, baseLayout;
    CircleImageView element2;
    //private int baseWidth, baseHeight, ratio;
    private float relX, relY;
    private float elem1X, elem1Y, elem1Width, elem1Height;
    private float elem2X, elem2Y, elem2Width, elem2Height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relativeLayout = (RelativeLayout) findViewById(R.id.view);
        //cardView = (CardView) findViewById(R.id.card_view);
        element1 = (ImageView) findViewById(R.id.element1);
        element2 = (CircleImageView) findViewById(R.id.element2);
        baseLayout = (ImageView) findViewById(R.id.baseImage);

        final int baseWidth = getResources().getDisplayMetrics().widthPixels;
        Log.i("TAG", "W: " + baseWidth);
        final int baseHeight = (baseWidth * 24 / 36);
        Log.i("TAG", "H: " + baseHeight);
        baseLayout.getLayoutParams().height = baseHeight;
        baseLayout.setImageResource(R.drawable.base);
        baseLayout.setImageBitmap(null);
        //baseLayout.setLayoutParams(new RelativeLayout.LayoutParams(baseWidth, baseHeight));

        relX = relativeLayout.getX();
        relY = (int) relativeLayout.getY();
        relativeLayout.getLayoutParams().height = baseHeight;
        Log.i("TAG", "Height: " + baseHeight);

        elem1X = 471 * baseWidth / 3600;
        elem1Y = 282 * baseHeight / 2400;
        Log.i("TAG", "X: " + elem1X + " Y: " + elem1Y);
        element1.setX(elem1X + relX);
        element1.setY(elem1Y + relY);
        elem1Width = 1053 * baseWidth / 3600;
        elem1Height = 936 * baseHeight / 2400;
        element1.getLayoutParams().width = (int) elem1Width;
        element1.getLayoutParams().height = (int) elem1Height;
        Log.i("TAG", "1W: "+ elem1Width + " 1H: " + elem1Height);

        elem2X = 1914 * baseWidth / 3600;
        elem2Y = 1137 * baseHeight / 2400;
        element2.setX(elem2X + relX);
        element2.setY(elem2Y + relY);
        Log.i("TAG", "X: " + element2.getX() + " Y: " + element2.getY());
        elem2Width = 1068 * baseWidth/ 3600;
        elem2Height = 1077 * baseHeight/ 2400;
        element2.getLayoutParams().height = (int) elem2Height;
        element2.getLayoutParams().width = (int) elem2Width;
        Log.i("TAG", "2W: "+ elem2Width + " 2H: " + elem2Height);

        //https://github.com/archie0803/Fotonicia-Assignment

    }
}
