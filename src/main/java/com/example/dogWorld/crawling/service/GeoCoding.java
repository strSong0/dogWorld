package com.example.dogWorld.crawling.service;

import com.example.dogWorld.crawling.service.Coordinate;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class GeoCoding {

    public static Coordinate geocode(String address) {
        try {
            String encodedAddress = URLEncoder.encode(address, "UTF-8");

            String apiURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + encodedAddress;
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", "2c2y74faqu");
            con.setRequestProperty("X-NCP-APIGW-API-KEY", "HPnLjbQPo47UzJAbOrbwH63C7neU0ODSMzDrU0w3");

            int responseCode = con.getResponseCode();

            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            JSONTokener tokener = new JSONTokener(response.toString());
            JSONObject object = new JSONObject(tokener);
            JSONArray arr = object.getJSONArray("addresses");

            if (arr.length() > 0) {
                JSONObject temp = arr.getJSONObject(0);
                double latitude = temp.getDouble("y");
                double longitude = temp.getDouble("x");
                return new Coordinate(latitude, longitude);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

