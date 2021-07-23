package com.example.destinycompanionapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    TextView subclassRarity;
    TextView subclassType;
    TextView subclassName;
    TextView subclassDesc;
    ImageView subclassPic;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {

        }
        Log.d("BUNDLETEST","IMAGE URL: "+bundle.getString("SubclassImageURL"));

        subclassName = getView().findViewById(R.id.id_subclassName);
        subclassPic = getView().findViewById(R.id.id_subclassImage);
        subclassRarity = getView().findViewById(R.id.id_subclassRarity);
        subclassType = getView().findViewById(R.id.id_subclassType);
        subclassDesc = getView().findViewById(R.id.id_subclassDesc);

        subclassName.setText(bundle.getString("SubclassName"));
        subclassRarity.setText(bundle.getString("SubclassRarity"));
        subclassType.setText(bundle.getString("SubclassType"));
        subclassDesc.setText(bundle.getString("SubclassDesc"));

        new DownloadImageTask(subclassPic).execute(bundle.getString("SubclassImageURL"));

    }

    //Downloads images
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
