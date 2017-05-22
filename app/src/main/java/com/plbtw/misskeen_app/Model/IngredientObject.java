package com.plbtw.misskeen_app.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Paulina on 5/22/2017.
 */
public class IngredientObject
{
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String nama;
    @SerializedName("amount")
    private String amount;
    @SerializedName("unit")
    private String unit;
    @SerializedName("description")
    private String description;

    public IngredientObject(String name, String amount, String unit, String description)
    {
        this.nama = name;
        this.amount = amount;
        this.unit = unit;
        this.description = description;
    }
    public String getNama() {
        return nama;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public String getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }
}
