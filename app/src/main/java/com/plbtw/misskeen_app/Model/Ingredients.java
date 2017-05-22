package com.plbtw.misskeen_app.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paulina on 5/22/2017.
 */
public class Ingredients {
    @SerializedName("ingredients")
    private List<IngredientObject> ingredientObject = new ArrayList<>();

    public List<IngredientObject> getIngredientObject() {
        return ingredientObject;
    }

    public void setIngredientObject(List<IngredientObject> ingredientObject) {
        this.ingredientObject = ingredientObject;
    }
}


