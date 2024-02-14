package com.ide.codekit.casttotv.Extras;

import android.graphics.drawable.Drawable;

import com.ide.codekit.casttotv.Model.CustomBottomNavModel;

public class CbnMenuItemModel {
    private CustomBottomNavModel model;
    private int selectedItemIconColor, selectedItemBackgroundColor, backgroundColor, itemIconColor, itemTextColor;
    private Drawable backgroundDrawable, selectedItemBackgroundDrawable;

    public CbnMenuItemModel(CustomBottomNavModel model, int selectedItemIconColor, int itemIconColor, int itemTextColor) {
        this.model = model;
        this.selectedItemIconColor = selectedItemIconColor;
        this.itemIconColor = itemIconColor;
        this.itemTextColor = itemTextColor;
    }

    public CustomBottomNavModel getModel() {
        return model;
    }

    public void setModel(CustomBottomNavModel model) {
        this.model = model;
    }

    public int getSelectedItemIconColor() {
        return selectedItemIconColor;
    }

    public void setSelectedItemIconColor(int selectedItemIconColor) {
        this.selectedItemIconColor = selectedItemIconColor;
    }

    public int getSelectedItemBackgroundColor() {
        return selectedItemBackgroundColor;
    }

    public void setSelectedItemBackgroundColor(int selectedItemBackgroundColor) {
        this.selectedItemBackgroundColor = selectedItemBackgroundColor;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getItemIconColor() {
        return itemIconColor;
    }

    public void setItemIconColor(int itemIconColor) {
        this.itemIconColor = itemIconColor;
    }

    public int getItemTextColor() {
        return itemTextColor;
    }

    public void setItemTextColor(int itemTextColor) {
        this.itemTextColor = itemTextColor;
    }

    public Drawable getBackgroundDrawable() {
        return backgroundDrawable;
    }

    public void setBackgroundDrawable(Drawable backgroundDrawable) {
        this.backgroundDrawable = backgroundDrawable;
    }

    public Drawable getSelectedItemBackgroundDrawable() {
        return selectedItemBackgroundDrawable;
    }

    public void setSelectedItemBackgroundDrawable(Drawable selectedItemBackgroundDrawable) {
        this.selectedItemBackgroundDrawable = selectedItemBackgroundDrawable;
    }
}
