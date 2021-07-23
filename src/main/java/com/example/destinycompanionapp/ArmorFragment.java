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
public class ArmorFragment extends Fragment {

    ImageView HeadImage;
    ImageView BodyImage;
    ImageView LegsImage;
    ImageView FeetImage;
    ImageView MarkImage;

    TextView HeadText;
    TextView BodyText;
    TextView LegsText;
    TextView FeetText;
    TextView MarkText;

    public ArmorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_armor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Bundle bundle = this.getArguments();
        if (bundle != null) {

        }

        HeadImage = getActivity().findViewById(R.id.id_headIMage);
        HeadText = getActivity().findViewById(R.id.id_headText);

        BodyImage = getActivity().findViewById(R.id.id_bodyImage);
        BodyText = getActivity().findViewById(R.id.id_bodyText);

        LegsImage = getActivity().findViewById(R.id.id_legsImage);
        LegsText = getActivity().findViewById(R.id.id_legsText);

        FeetImage = getActivity().findViewById(R.id.id_feetImage);
        FeetText = getActivity().findViewById(R.id.id_feetText);

        MarkImage = getActivity().findViewById(R.id.id_markImage);
        MarkText = getActivity().findViewById(R.id.id_markText);

        new ArmorFragment.DownloadImageTask(HeadImage).execute(bundle.getString("HeadURL"));
        new ArmorFragment.DownloadImageTask(BodyImage).execute(bundle.getString("BodyURL"));
        new ArmorFragment.DownloadImageTask(LegsImage).execute(bundle.getString("LegsURL"));
        new ArmorFragment.DownloadImageTask(FeetImage).execute(bundle.getString("FeetURL"));
        new ArmorFragment.DownloadImageTask(MarkImage).execute(bundle.getString("MarkURL"));

        HeadText.setText(bundle.getString("HeadName"));
        BodyText.setText(bundle.getString("BodyName"));
        LegsText.setText(bundle.getString("LegsName"));
        FeetText.setText(bundle.getString("FeetName"));
        MarkText.setText(bundle.getString("MarkName"));

        //Displaying values/img of head gear
        HeadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Head = new Intent(getActivity(), Pop.class);

                Head.putExtra("ImageURL",bundle.getString("HeadURL"));
                Head.putExtra("NAME",bundle.getString("HeadName"));
                Head.putExtra("TYPE",bundle.getString("HeadType"));
                Head.putExtra("RARITY",bundle.getString("HeadRarity"));
                Head.putExtra("DESC",bundle.getString("HeadDesc"));

                startActivity(Head);
            }
        });

        //Displaying values/img of body armor
        BodyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Body = new Intent(getActivity(), Pop.class);

                Body.putExtra("ImageURL",bundle.getString("BodyURL"));
                Body.putExtra("NAME",bundle.getString("BodyName"));
                Body.putExtra("TYPE",bundle.getString("BodyType"));
                Body.putExtra("RARITY",bundle.getString("BodyRarity"));
                Body.putExtra("DESC",bundle.getString("BodyDesc"));

                startActivity(Body);
            }
        });

        //Displaying values/img of leg gear
        LegsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Legs = new Intent(getActivity(), Pop.class);

                Legs.putExtra("ImageURL",bundle.getString("LegsURL"));
                Legs.putExtra("NAME",bundle.getString("LegsName"));
                Legs.putExtra("TYPE",bundle.getString("LegsType"));
                Legs.putExtra("RARITY",bundle.getString("LegsRarity"));
                Legs.putExtra("DESC",bundle.getString("LegsDesc"));

                startActivity(Legs);
            }
        });

        //Displaying values/img of boots
        FeetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Feet = new Intent(getActivity(), Pop.class);

                Feet.putExtra("ImageURL",bundle.getString("FeetURL"));
                Feet.putExtra("NAME",bundle.getString("FeetName"));
                Feet.putExtra("TYPE",bundle.getString("FeetType"));
                Feet.putExtra("RARITY",bundle.getString("FeetRarity"));
                Feet.putExtra("DESC",bundle.getString("FeetDesc"));

                startActivity(Feet);
            }
        });

        //Displaying values/img of Mark
        MarkImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Mark = new Intent(getActivity(), Pop.class);

                Mark.putExtra("ImageURL",bundle.getString("MarkURL"));
                Mark.putExtra("NAME",bundle.getString("MarkName"));
                Mark.putExtra("TYPE",bundle.getString("MarkType"));
                Mark.putExtra("RARITY",bundle.getString("MarkRarity"));
                Mark.putExtra("DESC",bundle.getString("MarkDesc"));

                startActivity(Mark);
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
