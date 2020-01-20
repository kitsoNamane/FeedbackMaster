package sdk.kitso.feedbackmaster.repository;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import sdk.kitso.feedbackmaster.model.DataItem;

class SurveyDeserializer implements JsonDeserializer<List<DataItem>> {
    @Override
    public List<DataItem> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        JsonObject jsonObject = json.getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonObject("data").getAsJsonArray("data");
        Log.d("FMDIGILAB 5", jsonArray.getAsString());
        Type dataItemListType = new TypeToken<ArrayList<DataItem>>(){}.getType();
        ArrayList<DataItem> dataItemsArray = gson.fromJson(jsonArray, dataItemListType);
        for(DataItem item: dataItemsArray) {
            Log.d("FMDIGILAB 6", item.getName());
        }
        return dataItemsArray;
    }
}
