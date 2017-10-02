package com.example.android.fotoniciaassignment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.R.attr.data;
import static android.os.Build.VERSION_CODES.M;
import static com.example.android.fotoniciaassignment.R.id.view;


public class MainActivity extends AppCompatActivity {

    Button image1, image2, create, share;
    RelativeLayout relativeLayout;
    ImageView element1, baseLayout;
    CircleImageView element2;
    Bitmap b;
    public static int PICK_PHOTO_FIRST = 1;
    public static int PICK_PHOTO_SECOND = 2;
    private float relX, relY;
    private float elem1X, elem1Y, elem1Width, elem1Height;
    private float elem2X, elem2Y, elem2Width, elem2Height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relativeLayout = (RelativeLayout) findViewById(view);
        element1 = (ImageView) findViewById(R.id.element1);
        element2 = (CircleImageView) findViewById(R.id.element2);
        baseLayout = (ImageView) findViewById(R.id.baseImage);

        final float baseWidth = getResources().getDisplayMetrics().widthPixels;
        Log.i("TAG", "W: " + baseWidth);
        final float baseHeight = (baseWidth * 24 / 36);
        Log.i("TAG", "H: " + baseHeight);
        baseLayout.getLayoutParams().height = (int) baseHeight;
        Picasso.with(this).load(R.drawable.base).centerCrop().resize((int) baseWidth, (int) baseHeight).into(baseLayout);

        relX = relativeLayout.getX();
        relY = relativeLayout.getY();
        relativeLayout.getLayoutParams().height = (int) baseHeight;
        Log.i("TAG", "Height: " + baseHeight);

        elem1X = 471 * baseWidth / 3600;
        elem1Y = 282 * baseHeight / 2400;
        Log.i("TAG", "X: " + elem1X + " Y: " + elem1Y);
        element1.setX(elem1X + relX - 4f);
        element1.setY(elem1Y + relY - 2f);
        elem1Width = 1053 * baseWidth / 3600;
        elem1Height = 936 * baseHeight / 2400;
        element1.getLayoutParams().width = (int) (elem1Width + 7);
        element1.getLayoutParams().height = (int) (elem1Height + 5);
        Log.i("TAG", "1W: " + elem1Width + " 1H: " + elem1Height);

        elem2X = 1914 * baseWidth / 3600;
        elem2Y = 1137 * baseHeight / 2400;
        element2.setX(elem2X + relX - 5f);
        element2.setY(elem2Y + relY - 3.5f);
        Log.i("TAG", "X: " + element2.getX() + " Y: " + element2.getY());
        elem2Width = 1068 * baseWidth / 3600;
        elem2Height = 1077 * baseHeight / 2400;
        element2.getLayoutParams().height = (int) (elem2Height + 7.9);
        element2.getLayoutParams().width = (int) (elem2Width + 7.6);
        Log.i("TAG", "2W: " + elem2Width + " 2H: " + elem2Height);

        image1 = (Button) findViewById(R.id.choose1);
        image2 = (Button) findViewById(R.id.choose2);
        share = (Button) findViewById(R.id.share);
        share.setEnabled(false);
    }

    public void saveImage(View view) {
        try {
            // Create a new File instance to save the generated image;
            File file = new File(Environment.getExternalStorageDirectory()
                    .getPath() + "/saved.jpeg");
            // Check if File does not exist in the storage;
            if (!file.exists()) {
                // Create a physical File;
                file.createNewFile();
            }
            // Initialize a FileOutputStream to write to the File;
            FileOutputStream fos = new FileOutputStream(file);
            // Top edge Y coordinate of the top most child View;
            int boundTop = relativeLayout.getMeasuredHeight();
            // Left edge X coordinate of the left most child View;
            int boundLeft = relativeLayout.getMeasuredWidth();
            // Bottom edge Y coordinate of the bottom most child View;
            int boundBottom = 0;
            // Right edge X coordinate of the right most child View;
            int boundRight = 0;
            // Get the total number of child Views present in the Layout;
            int totalChildren = relativeLayout.getChildCount();
            // Value to add as padding to the final image;
            int padding = 20;
            // Iterate through all the child Views;
            for (int i = 0; i < totalChildren; i++) {
                // Get the current child View;
                View vw = relativeLayout.getChildAt(i);
                // Check if it is higher than the current top edge;
                if (vw.getTop() < boundTop) {
                    // Update the top edge value;
                    boundTop = vw.getTop();
                }
                // Check if it is more leftwards than the current left edge;
                if (vw.getLeft() < boundLeft) {
                    // Update the left edge value;
                    boundLeft = vw.getLeft();
                }
                // Check if it is lower than the current bottom edge;
                if (vw.getBottom() > boundBottom) {
                    // Update the bottom edge value;
                    boundBottom = vw.getBottom();
                }
                // Check if it is more rightwards than the current right edge;
                if (vw.getRight() > boundRight) {
                    // Update the right edge value;
                    boundRight = vw.getRight();
                }
            }
            // Calculate the final Bitmap width;
            int bWidth = padding + boundRight - boundLeft;
            // Calculate the final Bitmap height;
            int bHeight = padding + boundBottom - boundTop;
            // Create a Bitmap Object with the specified width and height;
            Bitmap bitmap = Bitmap.createBitmap(bWidth,
                    bHeight, Bitmap.Config.ARGB_8888);
            // Initialize a Canvas to draw to the Bitmap;
            Canvas canvas = new Canvas(bitmap);
            // Fill the Bitmap with transparency;
            canvas.drawColor(Color.TRANSPARENT);
    /*
     * Translate the Canvas towards top left direction to compensate for
     * the blank space between the extreme Views and the edges of the
     * Layout and also the extra padding.
     */
            canvas.translate(-boundLeft + padding / 2, -boundTop + padding / 2);
            // Make the Layout draw its child Views on the Canvas;
            relativeLayout.draw(canvas);
            // Save the Bitmap to the File instance;
            bitmap.compress(Bitmap.CompressFormat.JPEG,81, fos);
            // Flush(Clear) the FileOutputStream;
            fos.flush();
            // Close the FileOutputStream;
            fos.close();
            // Flag the Bitmap for garbage collection;
            bitmap.recycle();

            share.setEnabled(true);
        } catch (Exception e) {
            Toast.makeText(this, "ERROR WHILE SAVING " + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
            e.printStackTrace();
        }

    }

    public void share(View view) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "saved.jpeg")));
        shareIntent.setType("image/jpeg");
        startActivity(Intent.createChooser(shareIntent, "Share Image"));
    }

    public void pickImage(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if(view.getId() == R.id.choose1){
            startActivityForResult(intent, PICK_PHOTO_FIRST);
        } else if (view.getId() == R.id.choose2) {
            startActivityForResult(intent, PICK_PHOTO_SECOND);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO_FIRST || requestCode == PICK_PHOTO_SECOND && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                Toast.makeText(this, "Couldn't load image", Toast.LENGTH_SHORT).show();
                return;
            }
            Uri selectedImageUri = data.getData();
            if(requestCode == PICK_PHOTO_FIRST) {
                Picasso.with(this).load(selectedImageUri).into(element1);
            } else if (requestCode == PICK_PHOTO_SECOND) {
                Picasso.with(this).load(selectedImageUri).into(element2);
            }
        }
    }
}
