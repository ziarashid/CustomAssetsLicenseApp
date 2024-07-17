package com.paksoftwares.trialy;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         String myLicenseKey = "ID_83jdjsfd9230dee_licenca_1";
           parseJsonFromAssets(this, myLicenseKey);

    }
    public void parseJsonFromAssets(Context context, String myLicenseKey) {
        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open("license.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String jsonString = stringBuilder.toString();

            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray licensesArray = jsonObject.getJSONArray("licenses");
            if (licensesArray.length() == 0) {
                Log.e("License", "No License Found");
                // display toast message
                Toast.makeText(context, "No License Found", Toast.LENGTH_LONG).show();
                // close the app
                finishAffinity(); // Finish all activities in the task and their affinity
                System.exit(0);
                return;
            } else if (licensesArray.length() > 0) {
                // check if mylicense key is present in the jsonarray
                if (licensesArray.toString().contains(myLicenseKey)) {
                    Log.e("License", "License Found");
                    for (int i = 0; i < licensesArray.length(); i++) {
                        JSONObject license = licensesArray.getJSONObject(i);
                        String licenseId = license.getString("license_id");
                        String expiredDate = license.getString("expiry_date");
                        String startTime = license.getString("start_time");
                        String lastLaunchTime = license.getString("last_launch_time");
                        int launchCount = license.getInt("launch_count");
                        boolean isActive = license.getBoolean("active");
                        // Use the extracted data as needed
                        System.out.println("License ID: " + licenseId);
                        // Continue printing or using other data

                        if (licenseId.equals(myLicenseKey)) {
                            Log.e("License", "License Found");
                            Log.e("License", "License ID: " + licenseId);
                            Log.e("License", "Expiry Date: " + expiredDate);
                            Log.e("License", "Start Time: " + startTime);
                            Log.e("License", "Last Launch Time: " + lastLaunchTime);
                            Log.e("License", "Launch Count: " + launchCount);
                            Log.e("License", "Is Active: " + isActive);
                            if (isActive) {
                                Log.e("License", "License is Active");
                                // check if expiryDate is greater than current date
                                try {
                                    String expiryDateString = expiredDate;
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date expiryDate = sdf.parse(expiryDateString);
                                    Date currentDate = new Date();

                                    if (expiryDate.after(currentDate)) {
                                        System.out.println("The expiry date is greater than the current date.");
                                        // display toast message
                                        Toast.makeText(context, "The expiry date is greater than the current date.", Toast.LENGTH_LONG).show();
                                    } else {

                                        System.out.println("The expiry date is not greater than the current date.");
                                        // display toast message
                                        Toast.makeText(context, "The expiry date is not greater than the current date.", Toast.LENGTH_LONG).show();
                                        // close the app
                                        finishAffinity(); // Finish all activities in the task and their affinity
                                        System.exit(0);
                                    }
                                } catch (Exception e) {
                                    // display toast message
                                    Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                    e.printStackTrace();
                                }

                            } else {
                                Log.e("License", "License is Inactive");
                                // display toast message
                                Toast.makeText(context, "License is Inactive", Toast.LENGTH_LONG).show();
                                // close the app
                                finishAffinity(); // Finish all activities in the task and their affinity
                                System.exit(0);
                            }
                            // break the loop if license found
                            break;

                        }
                    }
                } else {
                    Log.e("License", "License Not Found");
                    // display toast message
                    Toast.makeText(context, "License Not Found", Toast.LENGTH_LONG).show();
                    // close the app
                    finishAffinity(); // Finish all activities in the task and their affinity
                    System.exit(0);

                }
            } else {
                Log.e("License", "No License Found");
                // display toast message
                Toast.makeText(context, "No License Found", Toast.LENGTH_LONG).show();
                // close the app
                finishAffinity(); // Finish all activities in the task and their affinity
                System.exit(0);
            }


        } catch (Exception e) {
            // display toast message
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
 }