package com.example.destinycompanionapp;

import android.content.Intent;
import android.os.AsyncTask;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;



public class MainActivity extends AppCompatActivity {


    int membershipType = 0;
    //1 is Xbox 2 is PS
    String displayName = "";

    Spinner charSlect;
    ConstraintLayout layout;
    Button go;

    RadioGroup rg;
    RadioButton xbox;
    RadioButton psn;
    EditText username;
    boolean error = false;
    int charecter = -1;


    JSONObject jsonUserID;
    JSONObject jsonUserSummary;
    JSONObject jsonUserRace;
    JSONObject jsonUserClass;
    JSONObject jsonInventorySummary;

    Intent intent;
    Intent intentWeapons;
    Intent intentHome;
    Intent intentArmor;

    int Class = 3;
    int InventorItem = 6;
    int Race = 8;
    String characterID;
    int raceHash;
    long genderHash;
    long classHash;
    long subclassHash;
    long primaryWHash;
    long secondaryWHash;
    long tertiaryWHas;
    long headArmorHash;
    long bodyArmorHash;
    long legsArmorHash;
    long feetArmorHash;
    long markArmorHash;


    BigInteger female = new BigInteger("2204441813");
    BigInteger male = new BigInteger("3111576190");
    Boolean nameError = false;

    String race ="default";
    String classString = "default";


    Bundle bundleHOME = new Bundle();
    Bundle bundleWEAPONS = new Bundle();
    Bundle bundleARMOR = new Bundle();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = findViewById(R.id.layout);

        // Picking a random backgroud image from set of images for opening screen
        int rand = (int) (Math.random() * 5) + 1;
        if (rand == 1) {
            layout.setBackground(ContextCompat.getDrawable(this, R.drawable.openbg1));
        }
        if (rand == 2) {
            layout.setBackground(ContextCompat.getDrawable(this, R.drawable.openbg2));
        }
        if (rand == 3) {
            layout.setBackground(ContextCompat.getDrawable(this, R.drawable.openbg3));
        }
        if (rand == 4) {
            layout.setBackground(ContextCompat.getDrawable(this, R.drawable.openbg4));
        }
        if (rand == 5) {
            layout.setBackground(ContextCompat.getDrawable(this, R.drawable.openbg5));
        }


        charSlect = findViewById(R.id.id_charecterSpinner);
        String[] array = this.getResources().getStringArray(R.array.charecter_array);
        Spanned[] spannedStrings = new Spanned[3];
        for (int i = 0; i < array.length; i++) {
            spannedStrings[i] = Html.fromHtml(array[i]);
        }
        charSlect.setAdapter(new ArrayAdapter<CharSequence>(this, R.layout.support_simple_spinner_dropdown_item, spannedStrings));

        //Seeing if user selected xbox or playstation
        rg = findViewById(R.id.id_rg);
        xbox = findViewById(R.id.id_radioXbox);
        psn = findViewById(R.id.id_radioPSN);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (xbox.isChecked()) {
                    membershipType = 1;
                }
                if (psn.isChecked()) {
                    membershipType = 2;
                }
                Log.d("RadioButton", "" + membershipType);
            }
        });

        //Getting the username from user input
        username = findViewById(R.id.id_username);
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                displayName = charSequence.toString();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                displayName = charSequence.toString();
                error = false;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username.setText("");
            }
        });

        //Seeing which chanrecter the user wants to view
        charSlect = findViewById(R.id.id_charecterSpinner);
        charSlect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected_item = adapterView.getItemAtPosition(i).toString();

                if(selected_item.equals("Character 1")){
                    charecter = 0;
                }
                if(selected_item.equals("Character 2")){
                    charecter = 1;
                }
                if(selected_item.equals("Character 3")){
                    charecter = 2;
                }
                Log.d("SpinnerTest",""+charecter);
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //When go button is clicked
        go = findViewById(R.id.id_go);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Username", displayName);
                Log.d("STRINGGANG",username.getText().toString());

                //Checks of inout validity
                if (membershipType != 0 && !(username.getText().toString().equals("Username"))) {
                    AsyncThread thread = new AsyncThread();
                    thread.execute();
                    if(!nameError){
                        intent = new Intent(MainActivity.this, OverviewItems.class);
                        intent.putExtra("Character",charecter);
                        intent.putExtra("Username",displayName);
                        if(membershipType == 1) {
                            intent.putExtra("Console", "Xbox");
                        }
                        if(membershipType == 2) {
                            intent.putExtra("Console", "Playstation");
                        }
                        intentWeapons = new Intent(MainActivity.this, WeaponsFragment.class);
                        intentHome = new Intent(MainActivity.this, HomeFragment.class);
                        intentArmor = new Intent(MainActivity.this, ArmorFragment.class);
                        intent.putExtra("name", displayName);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    }
                }
                if (membershipType == 0 || username.getText().toString().equals("Username") || nameError) {
                    Toast.makeText(MainActivity.this, "Enter ALL Values", Toast.LENGTH_LONG).show();
                }


            }
        });

    }

    //Getting information from API
    public class AsyncThread extends AsyncTask<Void, Void, Void> {

        String membID;

        @Override
        protected Void doInBackground(Void... voids) {

            String HOST = "https://www.bungie.net/d1/Platform/Destiny/";

                String urlForID = HOST + membershipType + "/Stats/GetMembershipIdByDisplayName/" + displayName + "/";
                jsonUserID = makeJSON(urlForID);

            try {
                if(jsonUserID.getString("Response").equals("0")){
                    Log.d("JSONERRORTEST","ERROR");

                }else{
                    //Membership ID
                    try {
                        membID = jsonUserID.getString("Response");
                        Log.d("JSON",jsonUserID.toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                        System.out.println("Catch");
                    }
                    // End Membership ID

                    //Char Summary
                    String urlForSummary = HOST + membershipType + "/Account/" + membID + "/Summary/";
                    try {
                        Log.d("JSONTestSum", urlForSummary);

                        jsonUserSummary = makeJSON(urlForSummary);
                        Log.d("JsonTesting",jsonUserSummary.toString());

                        intent.putExtra("jsonUserSummary",jsonUserSummary.toString());

                        characterID = jsonUserSummary.getJSONObject("Response").getJSONObject("data").getJSONArray("characters").getJSONObject(charecter).getJSONObject("characterBase").getString("characterId");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //End Char Summary

                    //Manifest (Race/Gender)
                    //https://www.bungie.net/d1/Platform/Destiny/Manifest/{type}/{id}/
                    try {
                        raceHash = jsonUserSummary.getJSONObject("Response").getJSONObject("data").getJSONArray("characters").getJSONObject(charecter).getJSONObject("characterBase").getInt("raceHash");
                        genderHash = jsonUserSummary.getJSONObject("Response").getJSONObject("data").getJSONArray("characters").getJSONObject(charecter).getJSONObject("characterBase").getLong("genderHash");

                        Log.d("RaceJSON",HOST+"/Manifest/"+Race+"/"+raceHash+"/");
                        Log.d("RaceJSON",""+raceHash);
                        jsonUserRace = makeJSON(HOST+"/Manifest/"+Race+"/"+raceHash+"/");
                        Log.d("BigINt","gender"+BigInteger.valueOf(genderHash));
                        Log.d("BigINt","fem"+female);
                        Log.d("BigINt","male"+male);
                        Log.d("BigINt","comp"+BigInteger.valueOf(raceHash).equals(male));
                        Log.d("BigINt","compFem"+BigInteger.valueOf(raceHash).compareTo(female));
                        Log.d("BigINt",race);

                        if(BigInteger.valueOf(raceHash).compareTo(male) == -1){
                            race = jsonUserRace.getJSONObject("Response").getJSONObject("data").getJSONObject("race").getString("raceNameMale");
                        }else{
                            race = jsonUserRace.getJSONObject("Response").getJSONObject("data").getJSONObject("race").getString("raceNameFemale");
                        }
                        Log.d("RaceJSON",race);
                        intent.putExtra("race",race);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //End Manifest (Race)

                    //Manifest (Class)
                    try {
                        classHash = jsonUserSummary.getJSONObject("Response").getJSONObject("data").getJSONArray("characters").getJSONObject(charecter).getJSONObject("characterBase").getLong("classHash");
                        Log.d("ClassJSON",HOST+"Manifest/"+Class+"/"+classHash+"/");
                        Log.d("ClassJSON",""+classHash);
                        jsonUserClass = makeJSON(HOST+"Manifest/"+Class+"/"+classHash+"/");
                        classString = jsonUserClass.getJSONObject("Response").getJSONObject("data").getJSONObject("classDefinition").getString("className");
                        Log.d("ClassJSON",classString);
                        intent.putExtra("Class",classString);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //End Manifest (Class)

                    //Inventory Summary
                    //http://www.bungie.net/Platform/Destiny/2/Account/{MembershipID}/Character/{CharacterID}/Inventory/Summary/
                    //Getting images and data for inventory
                    jsonInventorySummary = makeJSON(HOST+membershipType+"/Account/"+membID+"/Character/"+characterID+"/Inventory/Summary/");
                    try {
                        subclassHash = jsonInventorySummary.getJSONObject("Response").getJSONObject("data").getJSONArray("items").getJSONObject(0).getLong("itemHash");
                        JSONObject subclass = makeJSON(HOST+"Manifest/6/"+subclassHash+"/");

                        bundleHOME.putString("SubclassImageURL","https://www.bungie.net"+subclass.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("icon"));
                        bundleHOME.putString("SubclassName",subclass.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("itemName"));
                        bundleHOME.putString("SubclassDesc",subclass.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("itemDescription"));
                        bundleHOME.putString("SubclassRarity",subclass.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("tierTypeName"));
                        bundleHOME.putString("SubclassType",subclass.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("itemTypeName"));

                        primaryWHash = jsonInventorySummary.getJSONObject("Response").getJSONObject("data").getJSONArray("items").getJSONObject(1).getLong("itemHash");
                        secondaryWHash = jsonInventorySummary.getJSONObject("Response").getJSONObject("data").getJSONArray("items").getJSONObject(2).getLong("itemHash");
                        tertiaryWHas = jsonInventorySummary.getJSONObject("Response").getJSONObject("data").getJSONArray("items").getJSONObject(3).getLong("itemHash");

                        JSONObject primaryWeapon = makeJSON(HOST+"Manifest/6/"+primaryWHash+"/");
                        JSONObject secondaryWeapon = makeJSON(HOST+"Manifest/6/"+secondaryWHash+"/");
                        JSONObject tertiaryWeapon = makeJSON(HOST+"Manifest/6/"+tertiaryWHas+"/");

                        bundleWEAPONS.putString("PrimaryURL","https://www.bungie.net"+primaryWeapon.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("icon"));
                        bundleWEAPONS.putString("SecondaryURL","https://www.bungie.net"+secondaryWeapon.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("icon"));
                        bundleWEAPONS.putString("TertiaryURL","https://www.bungie.net"+tertiaryWeapon.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("icon"));

                        bundleWEAPONS.putString("PrimaryName",primaryWeapon.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("itemName"));
                        bundleWEAPONS.putString("SecondaryName",secondaryWeapon.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("itemName"));
                        bundleWEAPONS.putString("TertiaryName",tertiaryWeapon.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("itemName"));

                        bundleWEAPONS.putString("PrimaryType",primaryWeapon.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("itemTypeName"));
                        bundleWEAPONS.putString("PrimaryDesc",primaryWeapon.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("itemDescription"));
                        bundleWEAPONS.putString("PrimaryRarity",primaryWeapon.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("tierTypeName"));

                        bundleWEAPONS.putString("SecondaryType",secondaryWeapon.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("itemTypeName"));
                        bundleWEAPONS.putString("SecondaryDesc",secondaryWeapon.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("itemDescription"));
                        bundleWEAPONS.putString("SecondaryRarity",secondaryWeapon.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("tierTypeName"));

                        bundleWEAPONS.putString("TertiaryType",tertiaryWeapon.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("itemTypeName"));
                        bundleWEAPONS.putString("TertiaryDesc",tertiaryWeapon.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("itemDescription"));
                        bundleWEAPONS.putString("TertiaryRarity",tertiaryWeapon.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("tierTypeName"));


                        headArmorHash = jsonInventorySummary.getJSONObject("Response").getJSONObject("data").getJSONArray("items").getJSONObject(5).getLong("itemHash");
                        bodyArmorHash = jsonInventorySummary.getJSONObject("Response").getJSONObject("data").getJSONArray("items").getJSONObject(6).getLong("itemHash");
                        legsArmorHash = jsonInventorySummary.getJSONObject("Response").getJSONObject("data").getJSONArray("items").getJSONObject(7).getLong("itemHash");
                        feetArmorHash = jsonInventorySummary.getJSONObject("Response").getJSONObject("data").getJSONArray("items").getJSONObject(8).getLong("itemHash");
                        markArmorHash = jsonInventorySummary.getJSONObject("Response").getJSONObject("data").getJSONArray("items").getJSONObject(9).getLong("itemHash");


                        JSONObject headArmor = makeJSON(HOST+"Manifest/6/"+headArmorHash+"/");
                        JSONObject bodyArmor = makeJSON(HOST+"Manifest/6/"+bodyArmorHash+"/");
                        JSONObject legsArmor = makeJSON(HOST+"Manifest/6/"+legsArmorHash+"/");
                        JSONObject feetArmor = makeJSON(HOST+"Manifest/6/"+feetArmorHash+"/");
                        JSONObject markArmor = makeJSON(HOST+"Manifest/6/"+markArmorHash+"/");

                        bundleARMOR.putString("HeadURL","https://www.bungie.net"+headArmor.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("icon"));
                        bundleARMOR.putString("HeadName",headArmor.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("itemName"));
                        bundleARMOR.putString("HeadType",headArmor.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("itemTypeName"));
                        bundleARMOR.putString("HeadDesc",headArmor.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("itemDescription"));
                        bundleARMOR.putString("HeadRarity",headArmor.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("tierTypeName"));

                        bundleARMOR.putString("BodyURL","https://www.bungie.net"+bodyArmor.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("icon"));
                        bundleARMOR.putString("BodyName",bodyArmor.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("itemName"));
                        bundleARMOR.putString("BodyType",bodyArmor.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("itemTypeName"));
                        bundleARMOR.putString("BodyDesc",bodyArmor.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("itemDescription"));
                        bundleARMOR.putString("BodyRarity",bodyArmor.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("tierTypeName"));


                        bundleARMOR.putString("LegsURL","https://www.bungie.net"+legsArmor.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("icon"));
                        bundleARMOR.putString("LegsName",legsArmor.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("itemName"));
                        bundleARMOR.putString("LegsType",legsArmor.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("itemTypeName"));
                        bundleARMOR.putString("LegsDesc",legsArmor.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("itemDescription"));
                        bundleARMOR.putString("LegsRarity",legsArmor.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("tierTypeName"));

                        bundleARMOR.putString("FeetURL","https://www.bungie.net"+feetArmor.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("icon"));
                        bundleARMOR.putString("FeetName",feetArmor.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("itemName"));
                        bundleARMOR.putString("FeetType",feetArmor.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("itemTypeName"));
                        bundleARMOR.putString("FeetDesc",feetArmor.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("itemDescription"));
                        bundleARMOR.putString("FeetRarity",feetArmor.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("tierTypeName"));

                        bundleARMOR.putString("MarkURL","https://www.bungie.net"+markArmor.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("icon"));
                        bundleARMOR.putString("MarkName",markArmor.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("itemName"));
                        bundleARMOR.putString("MarkType",markArmor.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("itemTypeName"));
                        bundleARMOR.putString("MarkDesc",markArmor.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("itemDescription"));
                        bundleARMOR.putString("MarkRarity",markArmor.getJSONObject("Response").getJSONObject("data").getJSONObject("inventoryItem").getString("tierTypeName"));




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.d("BUNDLETEST","MainActivity HomeBundle"+bundleHOME.toString() );
                    intent.putExtra("HomeBundle",bundleHOME);
                    intent.putExtra("ArmorBundle",bundleARMOR);
                    intent.putExtra("WeaponBundle",bundleWEAPONS);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if(membID!=null && membID.equals("0")){
                Log.d("JSONTest","URLError");
                Toast.makeText(MainActivity.this, "Invalid Username", Toast.LENGTH_SHORT).show();

            }

            try {
                if(jsonUserSummary!=null) {
                    intent.putExtra("discipline", jsonUserSummary.getJSONObject("Response").getJSONObject("data").getJSONArray("characters").getJSONObject(charecter).getJSONObject("characterBase").getJSONObject("stats").getJSONObject("STAT_DISCIPLINE").getInt("value"));
                    intent.putExtra("intellect", jsonUserSummary.getJSONObject("Response").getJSONObject("data").getJSONArray("characters").getJSONObject(charecter).getJSONObject("characterBase").getJSONObject("stats").getJSONObject("STAT_INTELLECT").getInt("value"));
                    intent.putExtra("strength", jsonUserSummary.getJSONObject("Response").getJSONObject("data").getJSONArray("characters").getJSONObject(charecter).getJSONObject("characterBase").getJSONObject("stats").getJSONObject("STAT_STRENGTH").getInt("value"));
                    intent.putExtra("light", jsonUserSummary.getJSONObject("Response").getJSONObject("data").getJSONArray("characters").getJSONObject(charecter).getJSONObject("characterBase").getJSONObject("stats").getJSONObject("STAT_LIGHT").getInt("value"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(jsonUserSummary!=null) {
                startActivity(intent);
            }else{
                Toast.makeText(MainActivity.this, "Username Not Found", Toast.LENGTH_LONG).show();
            }

        }

        //Connects to API and returns a JSON Object
        public JSONObject makeJSON(String url){
            JSONObject json = null;
            String apiKey = "36c346318fa54fc6bc659ad6321a6d41";
            try {
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                con.setRequestMethod("GET");
                con.setRequestProperty("X-API-KEY", apiKey);

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                String response = "";

                while ((inputLine = in.readLine()) != null) {
                    response += inputLine;
                }

                in.close();

                JsonParser parser = new JsonParser();
                JsonObject gson = (JsonObject) parser.parse(response);
                json = new JSONObject(gson.toString());


            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {

                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return json;
        }
    }




}

