package com.example.destinycompanionapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

public class Pop extends Activity{

    ImageView imageView;

    TextView itemName;
    TextView itemType;
    TextView itemRarity;
    TextView itemDesc;

    ConstraintLayout layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popwindow);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.8));

        imageView = findViewById(R.id.id_image);

        itemName = findViewById(R.id.id_NAME);
        itemType = findViewById(R.id.id_TYPE);
        itemRarity = findViewById(R.id.id_RARITY);
        itemDesc = findViewById(R.id.id_DESC);

        //Getting values of item
        itemName.setText(getIntent().getStringExtra("NAME"));
        itemType.setText(getIntent().getStringExtra("TYPE"));
        itemRarity.setText(getIntent().getStringExtra("RARITY"));
        itemDesc.setText(getIntent().getStringExtra("DESC"));

        new Pop.DownloadImageTask(imageView).execute(getIntent().getStringExtra("ImageURL"));

        layout = findViewById(R.id.POPlayout);

        //UI changes to BG color based on obj rarity
        if(getIntent().getStringExtra("RARITY").equals("Common")){
            layout.setBackgroundColor(ContextCompat.getColor(this, R.color.Basic));
        }
        if(getIntent().getStringExtra("RARITY").equals("Uncommon")){
            layout.setBackgroundColor(ContextCompat.getColor(this, R.color.Uncommon));
        }
        if(getIntent().getStringExtra("RARITY").equals("Rare")){
            layout.setBackgroundColor(ContextCompat.getColor(this, R.color.Rare));
        }
        if(getIntent().getStringExtra("RARITY").equals("Legendary")){
            layout.setBackgroundColor(ContextCompat.getColor(this, R.color.Legendary));
        }
        if(getIntent().getStringExtra("RARITY").equals("Exotic")){
            layout.setBackgroundColor(ContextCompat.getColor(this, R.color.Exotic));
        }

    }

    //Getting images to display
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
