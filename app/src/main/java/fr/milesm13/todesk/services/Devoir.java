package fr.milesm13.todesk.services;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Devoir {

    public String description;
    public String hasFile;

    @SerializedName("titre")
    public String title;

    @SerializedName("date_rendu")
    public String dateRendu;


    public Devoir(String title, String desc, String date, String hasFile){
        this.title = title;
        this.description = desc;
        this.dateRendu = date;
        this.hasFile = hasFile;

    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription(){
        return this.description;
    }

    public String getDate() {
        return this.dateRendu;
    }
}
