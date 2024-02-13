package com.ide.codekit.casttotv.Model;

import java.io.Serializable;

public class ObDataModel implements Serializable {

    private String name, value, imgPath, number;
    private int icon;
    private int gifImage;
    private boolean isPremium, isSelected;

    public ObDataModel(String name, String value, int icon, int gifImage) {
        this.name = name;
        this.value = value;
        this.icon = icon;
        this.gifImage = gifImage;
    }

    public ObDataModel(String name, int icon, String value) {
        this.name = name;
        this.value = value;
        this.icon = icon;
    }

    public ObDataModel(String name, int icon, boolean isSelected) {
        this.name = name;
        this.icon = icon;
        this.isSelected = isSelected;
    }

    public ObDataModel(String name, String number, int icon) {
        this.name = name;
        this.number = number;
        this.icon = icon;
    }

    public ObDataModel(String name, String number, String imgPath) {
        this.name = name;
        this.imgPath = imgPath;
        this.number = number;
    }

    public int getGifImage() {
        return gifImage;
    }

    public void setGifImage(int gifImage) {
        this.gifImage = gifImage;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public void setPremium(boolean premium) {
        isPremium = premium;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}