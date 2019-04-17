package com.allen.pad.Database;

import android.arch.persistence.room.TypeConverter;
import com.allen.pad.Model.Label;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class DataConverter implements Serializable {
 
 @TypeConverter // note this annotation
    public String fromOptionValuesList(ArrayList<Label> optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Label>>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }
 
    @TypeConverter // note this annotation
    public ArrayList<Label> toOptionValuesList(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Label>>() {
        }.getType();
        ArrayList<Label> productCategoriesList = gson.fromJson(optionValuesString, type);
        return productCategoriesList;
    }
 
}
