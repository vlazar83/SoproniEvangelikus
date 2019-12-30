package com.vlazar83.sopronievangelikus;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ImagePickerActiviy extends AppCompatActivity implements View.OnClickListener{

    private static ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8, imageView9, imageView10, imageView11, imageView12, imageView13, imageView14, imageView15, imageView16, imageView17, imageView18;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_picker_activity);

        imageView1 = (ImageView)findViewById(R.id.imageView1);
        imageView1.setOnClickListener(this);
        imageView2 = (ImageView)findViewById(R.id.imageView2);
        imageView2.setOnClickListener(this);
        imageView3 = (ImageView)findViewById(R.id.imageView3);
        imageView3.setOnClickListener(this);
        imageView4 = (ImageView)findViewById(R.id.imageView4);
        imageView4.setOnClickListener(this);
        imageView5 = (ImageView)findViewById(R.id.imageView5);
        imageView5.setOnClickListener(this);
        imageView6 = (ImageView)findViewById(R.id.imageView6);
        imageView6.setOnClickListener(this);
        imageView7 = (ImageView)findViewById(R.id.imageView7);
        imageView7.setOnClickListener(this);
        imageView8 = (ImageView)findViewById(R.id.imageView8);
        imageView8.setOnClickListener(this);
        imageView9 = (ImageView)findViewById(R.id.imageView9);
        imageView9.setOnClickListener(this);
        imageView10 = (ImageView)findViewById(R.id.imageView10);
        imageView10.setOnClickListener(this);
        imageView11 = (ImageView)findViewById(R.id.imageView11);
        imageView11.setOnClickListener(this);
        imageView12 = (ImageView)findViewById(R.id.imageView12);
        imageView12.setOnClickListener(this);
        imageView13 = (ImageView)findViewById(R.id.imageView13);
        imageView13.setOnClickListener(this);
        imageView14 = (ImageView)findViewById(R.id.imageView14);
        imageView14.setOnClickListener(this);
        imageView15 = (ImageView)findViewById(R.id.imageView15);
        imageView15.setOnClickListener(this);
        imageView16 = (ImageView)findViewById(R.id.imageView16);
        imageView16.setOnClickListener(this);
        imageView17 = (ImageView)findViewById(R.id.imageView17);
        imageView17.setOnClickListener(this);
        imageView18 = (ImageView)findViewById(R.id.imageView18);
        imageView18.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent imagePickerIntent = new Intent(ImagePickerActiviy.this, CreateEventActivity.class);
        String imageName;

        switch (v.getId()) {

            case R.id.imageView1:
                imageName = String.valueOf(imageView1.getTag());
                imagePickerIntent.putExtra("choosenImage", imageName);
                // Start the new activity
                startActivity(imagePickerIntent);
                break;

            case R.id.imageView2:
                imageName = String.valueOf(imageView2.getTag());
                imagePickerIntent.putExtra("choosenImage", imageName);
                // Start the new activity
                startActivity(imagePickerIntent);
                break;

            case R.id.imageView3:
                imageName = String.valueOf(imageView3.getTag());
                imagePickerIntent.putExtra("choosenImage", imageName);
                // Start the new activity
                startActivity(imagePickerIntent);
                break;

            case R.id.imageView4:
                imageName = String.valueOf(imageView4.getTag());
                imagePickerIntent.putExtra("choosenImage", imageName);
                // Start the new activity
                startActivity(imagePickerIntent);
                break;

            case R.id.imageView5:
                imageName = String.valueOf(imageView5.getTag());
                imagePickerIntent.putExtra("choosenImage", imageName);
                // Start the new activity
                startActivity(imagePickerIntent);
                break;

            case R.id.imageView6:
                imageName = String.valueOf(imageView6.getTag());
                imagePickerIntent.putExtra("choosenImage", imageName);
                // Start the new activity
                startActivity(imagePickerIntent);
                break;

            case R.id.imageView7:
                imageName = String.valueOf(imageView7.getTag());
                imagePickerIntent.putExtra("choosenImage", imageName);
                // Start the new activity
                startActivity(imagePickerIntent);
                break;

            case R.id.imageView8:
                imageName = String.valueOf(imageView8.getTag());
                imagePickerIntent.putExtra("choosenImage", imageName);
                // Start the new activity
                startActivity(imagePickerIntent);
                break;

            case R.id.imageView9:
                imageName = String.valueOf(imageView9.getTag());
                imagePickerIntent.putExtra("choosenImage", imageName);
                // Start the new activity
                startActivity(imagePickerIntent);
                break;

            case R.id.imageView10:
                imageName = String.valueOf(imageView10.getTag());
                imagePickerIntent.putExtra("choosenImage", imageName);
                // Start the new activity
                startActivity(imagePickerIntent);
                break;

            case R.id.imageView11:
                imageName = String.valueOf(imageView11.getTag());
                imagePickerIntent.putExtra("choosenImage", imageName);
                // Start the new activity
                startActivity(imagePickerIntent);
                break;

            case R.id.imageView12:
                imageName = String.valueOf(imageView12.getTag());
                imagePickerIntent.putExtra("choosenImage", imageName);
                // Start the new activity
                startActivity(imagePickerIntent);
                break;

            case R.id.imageView13:
                imageName = String.valueOf(imageView13.getTag());
                imagePickerIntent.putExtra("choosenImage", imageName);
                // Start the new activity
                startActivity(imagePickerIntent);
                break;

            case R.id.imageView14:
                imageName = String.valueOf(imageView14.getTag());
                imagePickerIntent.putExtra("choosenImage", imageName);
                // Start the new activity
                startActivity(imagePickerIntent);
                break;

            case R.id.imageView15:
                imageName = String.valueOf(imageView15.getTag());
                imagePickerIntent.putExtra("choosenImage", imageName);
                // Start the new activity
                startActivity(imagePickerIntent);
                break;

            case R.id.imageView16:
                imageName = String.valueOf(imageView16.getTag());
                imagePickerIntent.putExtra("choosenImage", imageName);
                // Start the new activity
                startActivity(imagePickerIntent);
                break;

            case R.id.imageView17:
                imageName = String.valueOf(imageView17.getTag());
                imagePickerIntent.putExtra("choosenImage", imageName);
                // Start the new activity
                startActivity(imagePickerIntent);
                break;

            case R.id.imageView18:
                imageName = String.valueOf(imageView18.getTag());
                imagePickerIntent.putExtra("choosenImage", imageName);
                // Start the new activity
                startActivity(imagePickerIntent);
                break;

            default:
                break;
        }
    }
}
