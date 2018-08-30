package com.example.android.sandwichclub.Utils;

import android.text.TextUtils;
import android.util.Log;
import com.example.android.sandwichclub.Model.Sandwich;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private JsonUtils() { }
    public static Sandwich parseSandwichJson(String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        Sandwich sandwichObject = null;
        try {
            JSONObject base = new JSONObject(json);
            JSONObject name = base.getJSONObject("name");
            String mainName = name.getString("mainName");
            List<String> alsoKnownAsList = new ArrayList<>();
            JSONArray alsoKnownAsArray = name.getJSONArray("alsoKnownAs");
            for (int i = 0; i < alsoKnownAsArray.length(); i++)
                alsoKnownAsList.add(alsoKnownAsArray.getString(i));
            String placeOfOrigin = base.getString("placeOfOrigin");
            String description = base.getString("description");
            String image = base.getString("image");
            List<String> ingredientsList = new ArrayList<>();
            JSONArray ingredientsArray = base.getJSONArray("ingredients");
            for (int j = 0; j < ingredientsArray.length(); j++)
                ingredientsList.add(ingredientsArray.getString(j));
            sandwichObject = new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, image, ingredientsList);
        } catch (JSONException e) {
            Log.e("JsonUtils", "Error parsing Sandwich JSON Object", e);
            e.printStackTrace();
        }
        return sandwichObject;
    }
}
