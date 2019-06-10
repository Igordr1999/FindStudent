package ru.findstudent;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TechParser {
    public static List<TechItem> parse(String data) throws JSONException {
        if (data == null) {
            return new ArrayList<>(0);
        }

        JSONObject techJson = new JSONObject(data);
        List<TechItem> result = new ArrayList<>();
        JSONArray arr = techJson.getJSONArray("results");
        for (int i = 0; i < arr.length(); i++) {
            TechItem techItem = new TechItem();
            techItem.code = arr.getJSONObject(i).getString("code");
            techItem.record_create_date = arr.getJSONObject(i).getString("record_create_date");
            techItem.record_update_date = arr.getJSONObject(i).getString("record_update_date");
            techItem.photo = arr.getJSONObject(i).getString("photo");

            JSONArray identified_faces = (JSONArray) arr.getJSONObject(i).opt("identified_faces");
            if(identified_faces.length() < 1) continue;

            JSONObject first_identified_face = identified_faces.getJSONObject(0);
            techItem.last_name = first_identified_face.getJSONObject("student").getString("last_name");
            techItem.first_name = first_identified_face.getJSONObject("student").getString("first_name");
            techItem.user_id = first_identified_face.getJSONObject("student").getString("user_id");
            techItem.similarity = first_identified_face.optString("similarity");
            result.add(techItem);
        }

        return result;
    }
}
