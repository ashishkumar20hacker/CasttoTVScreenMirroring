package com.ide.codekit.casttotv.Extras;

import android.graphics.drawable.Drawable;

import com.ide.codekit.casttotv.Model.CustomBottomNavModel;

public class CbnMenuItemModel {
    private CustomBottomNavModel model;
    private int selectedItemIconColor, selectedItemBackgroundColor, backgroundColor, itemIconColor, itemTextColor;
    private Drawable backgroundDrawable, selectedItemBackgroundDrawable;

    public CbnMenuItemModel(CustomBottomNavModel model, int selectedItemIconColor, int itemIconColor, int itemTextColor, int selectedItemBackgroundColor, int backgroundColor) {
        this.model = model;
        this.selectedItemIconColor = selectedItemIconColor;
        this.selectedItemBackgroundColor = selectedItemBackgroundColor;
        this.backgroundColor = backgroundColor;
        this.itemIconColor = itemIconColor;
        this.itemTextColor = itemTextColor;
    }

    public CbnMenuItemModel(CustomBottomNavModel model, int selectedItemIconColor, int itemIconColor, int itemTextColor, Drawable backgroundDrawable, Drawable selectedItemBackgroundDrawable) {
        this.model = model;
        this.selectedItemIconColor = selectedItemIconColor;
        this.itemIconColor = itemIconColor;
        this.itemTextColor = itemTextColor;
        this.backgroundDrawable = backgroundDrawable;
        this.selectedItemBackgroundDrawable = selectedItemBackgroundDrawable;
    }

    public CbnMenuItemModel(CustomBottomNavModel model, int selectedItemIconColor, int itemIconColor, int itemTextColor, int backgroundColor, Drawable selectedItemBackgroundDrawable) {
        this.model = model;
        this.selectedItemIconColor = selectedItemIconColor;
        this.backgroundColor = backgroundColor;
        this.itemIconColor = itemIconColor;
        this.itemTextColor = itemTextColor;
        this.selectedItemBackgroundDrawable = selectedItemBackgroundDrawable;
    }
    public CbnMenuItemModel(CustomBottomNavModel model, int selectedItemIconColor, int itemIconColor, int itemTextColor, Drawable backgroundDrawable, int selectedItemBackgroundColor) {
        this.model = model;
        this.selectedItemIconColor = selectedItemIconColor;
        this.backgroundDrawable = backgroundDrawable;
        this.itemIconColor = itemIconColor;
        this.itemTextColor = itemTextColor;
        this.selectedItemBackgroundColor = selectedItemBackgroundColor;
    }


}
