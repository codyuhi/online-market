package com.example.onlinemarket;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class ApiUtil {

    private ApiUtil(){}

    public static final String PI_BASE_API_URL = "http://192.168.4.1:3000/";
    public static final String PC_BASE_API_URL = "http://localhost:3000/";
    public static final String PC_REMOTE_BASE_API_URL = "http://10.37.71.248:3000/";

    public static URL buildUrl(String title) {
        String fullUrl = PC_REMOTE_BASE_API_URL + title;
        URL url = null;

        try {
            url = new URL(fullUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getJson(URL url) throws IOException{
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            InputStream stream = connection.getInputStream();
            Scanner scanner = new Scanner(stream);

            boolean hasData = scanner.hasNext();
            if(hasData) {
                return scanner.next();
            } else {
                return null;
            }
        } catch (Exception e) {
            Log.d("Error: ", e.toString());
            return null;
        }
        finally {
            connection.disconnect();
        }
    }

    public static void createAccountPOST(URL createAccountUrl, String username, String password) {
        try {
            String createAccountJsonString = "{\"username\": \"" +
                    username +
                    "\",\n\"password\": \"" +
                    password + "\"}";
//            JSONObject createAccountObject = new JSONObject(createAccountJsonString);
            HttpURLConnection connection = (HttpURLConnection) createAccountUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            DataOutputStream write = new DataOutputStream(connection.getOutputStream());
            write.writeBytes(createAccountJsonString);
            write.flush();
            write.close();
//            Toast.makeText(getApplicationContext(), "Account creation failed at location 2", Toast.LENGTH_LONG).show();
            Log.d("Response: ", connection.getResponseMessage() + "");
        } catch (Exception e){
            Log.d("Error: ", e.getMessage());
        }
    }
}
