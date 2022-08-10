package com.pmd.notes;

import android.graphics.Color;

import java.util.HashMap;
import java.util.Map;

public class Note {
    private String theme;
    private String Text;
    private String color;
    private Integer id;
    public Note(String Theme, String SomeText, String Color, Integer Id){
        this.theme = Theme;
        this.Text = SomeText;
        this.color = Color;
        this.id = Id;
    }

    public String getColor() {
        return color;
    }

    public String getText() {
        return Text;
    }

    public String getTheme() {
        return theme;
    }

    public Integer getId() { return id; }

    static public String getConstColor(String name){
        switch (name){
            case("red"):return "#FFC4C4";
            case("blue"):return "#aed4fc";
            case("yellow"):return "#fcf7ae";
            case("green"):return "#c2fcae";
        }
        return "";
    }
}
