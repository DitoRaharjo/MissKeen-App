package com.plbtw.misskeen_app;

/**
 * Created by Paulina on 5/22/2017.
 */
public class Recipe {
    private String recipeid;
    private String recipename;
    private String recipedescription;
    private String reciperating;
    private String recipethumbnail;

    public Recipe() {
    }

    public String getRecipeid() {
        return recipeid;
    }

    public void setRecipeid(String recipeid) {
        this.recipeid = recipeid;
    }

    public String getRecipename() {
        return recipename;
    }

    public void setRecipename(String recipename) {
        this.recipename = recipename;
    }

    public String getRecipedescription() {
        return recipedescription;
    }

    public void setRecipedescription(String recipedescription) {
        this.recipedescription = recipedescription;
    }

    public String getReciperating() {
        return reciperating;
    }

    public void setReciperating(String reciperating) {
        this.reciperating = reciperating;
    }

    public String getRecipethumbnail() {
        return recipethumbnail;
    }

    public void setRecipethumbnail(String recipethumbnail) {
        this.recipethumbnail = "http://ditoraharjo.co/misskeen/uploads/RecipePicture/"+recipethumbnail;
    }
}
