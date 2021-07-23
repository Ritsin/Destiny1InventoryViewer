package com.example.destinycompanionapp;


import android.content.Intent;
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
public class WeaponsFragment extends Fragment {

    TextView primaryText;
    TextView secondaryText;
    TextView tertieryText;

    ImageView primaryImage;
    ImageView secondaryImage;
    ImageView terieryImage;

    public WeaponsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weapons, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Bundle bundle = this.getArguments();
        if (bundle != null) {

        }

        primaryText = getActivity().findViewById(R.id.id_PrimaryText);
        secondaryText = getActivity().findViewById(R.id.id_SecondaryText);
        tertieryText = getActivity().findViewById(R.id.id_TertieryText);

        primaryImage = getActivity().findViewById(R.id.id_PrimaryImage);
        secondaryImage = getActivity().findViewById(R.id.id_SecondaryImage);
        terieryImage = getActivity().findViewById(R.id.id_TertieryImage);

        new WeaponsFragment.DownloadImageTask(primaryImage).execute(bundle.getString("PrimaryURL"));
        new WeaponsFragment.DownloadImageTask(secondaryImage).execute(bundle.getString("SecondaryURL"));
        new WeaponsFragment.DownloadImageTask(terieryImage).execute(bundle.getString("TertiaryURL"));

        primaryText.setText(bundle.getString("PrimaryName"));
        secondaryText.setText(bundle.getString("SecondaryName"));
        tertieryText.setText(bundle.getString("TertiaryName"));

        //Displaying values/img of primary weapon
        primaryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent PrimaryW = new Intent(getActivity(), Pop.class);

                PrimaryW.putExtra("ImageURL",bundle.getString("PrimaryURL"));
                PrimaryW.putExtra("NAME",bundle.getString("PrimaryName"));
                PrimaryW.putExtra("TYPE",bundle.getString("PrimaryType"));
                PrimaryW.putExtra("RARITY",bundle.getString("PrimaryRarity"));
                PrimaryW.putExtra("DESC",bundle.getString("PrimaryDesc"));

                startActivity(PrimaryW);
            }
        });

        //Displaying values/img of secondary weapon
        secondaryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent SecondaryW = new Intent(getActivity(), Pop.class);

                SecondaryW.putExtra("ImageURL",bundle.getString("SecondaryURL"));
                SecondaryW.putExtra("NAME",bundle.getString("SecondaryName"));
                SecondaryW.putExtra("TYPE",bundle.getString("SecondaryType"));
                SecondaryW.putExtra("RARITY",bundle.getString("SecondaryRarity"));
                SecondaryW.putExtra("DESC",bundle.getString("SecondaryDesc"));

                startActivity(SecondaryW);
            }
        });

        //Displaying values/img of tertiery weapon
        terieryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent TertiaryW = new Intent(getActivity(), Pop.class);

                TertiaryW.putExtra("ImageURL",bundle.getString("TertiaryURL"));
                TertiaryW.putExtra("NAME",bundle.getString("TertiaryName"));
                TertiaryW.putExtra("TYPE",bundle.getString("TertiaryType"));
                TertiaryW.putExtra("RARITY",bundle.getString("TertiaryRarity"));
                TertiaryW.putExtra("DESC",bundle.getString("TertiaryDesc"));

                startActivity(TertiaryW);
            }
        });

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
