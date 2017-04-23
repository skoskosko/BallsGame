package com.example.skosko.ballsgame;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by skosko on 21/04/2017.
 */

public class ScoreItem {
    String points;
    String name;


    public static ArrayList<ScoreItem> getScoresFromString(String file, Context context){


        final ArrayList<ScoreItem> ScoreList = new ArrayList<>();

        try {
            JSONArray json = new JSONArray(file);

            for (int i = 0 ; i < json.length() ; i++){
                JSONObject data = json.getJSONObject(i);
                ScoreItem THIS = new ScoreItem();
                THIS.points = data.getString("score");
                THIS.name = data.getString("name");
                ScoreList.add(THIS);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ScoreList;




    }
}
