package com.ide.codekit.casttotv.Extras;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.ide.codekit.casttotv.Model.CustomBottomNavModel;
import com.ide.codekit.casttotv.R;

import java.util.List;

public class CustomBottomNav extends LinearLayout {
    int selectedItemIconColor, selectedItemBackgroundColor, backgroundColor, itemIconColor, itemTextColor;
    Drawable backgroundDrawable, selectedItemBackgroundDrawable;
    List<CustomBottomNavModel> modelList;
    Context context;

    public CustomBottomNav(Context context) {
        super(context);
    }

    public CustomBottomNav(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomBottomNav, 0, 0);

        try {
            selectedItemIconColor = array.getColor(R.styleable.CustomBottomNav_selectedItemIconColor, Color.GREEN);
            selectedItemBackgroundColor = array.getColor(R.styleable.CustomBottomNav_selectedItemBackgroundColor, Color.BLUE);
            backgroundColor = array.getColor(R.styleable.CustomBottomNav_backgroundColor, Color.LTGRAY);
            itemIconColor = array.getColor(R.styleable.CustomBottomNav_itemIconColor, Color.GRAY);
            itemTextColor = array.getColor(R.styleable.CustomBottomNav_itemTextColor, Color.GRAY);
            backgroundDrawable = array.getDrawable(R.styleable.CustomBottomNav_backgroundDrawable);
            selectedItemBackgroundDrawable = array.getDrawable(R.styleable.CustomBottomNav_selectedItemBackgroundDrawable);
        } finally {
            array.recycle();
        }

        this.setOrientation(HORIZONTAL);


    }

    public void populate(List<CustomBottomNavModel> modelList) {
        this.modelList = modelList;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context)
                .getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels / modelList.size();
        createMenuItem(width);
    }

    private void createMenuItem(int width) {
        boolean isSelected = true;
        for (CustomBottomNavModel model : modelList) {
            CbnMenuItemModel itemModel = new CbnMenuItemModel(model, selectedItemIconColor, itemIconColor, itemTextColor);
            if (backgroundDrawable != null) {
                itemModel.setBackgroundDrawable(backgroundDrawable);
            } else {
                itemModel.setBackgroundColor(backgroundColor);
            }
            if (selectedItemBackgroundDrawable != null) {
                itemModel.setSelectedItemBackgroundDrawable(selectedItemBackgroundDrawable);
            } else {
                itemModel.setSelectedItemBackgroundColor(selectedItemBackgroundColor);
            }
            CbnMenuItem menuItem = new CbnMenuItem(context, itemModel, isSelected);
            menuItem.setMinimumWidth(width);
            this.addView(menuItem);
            menuItem.getLayoutParams().width = width;
            menuItem.requestLayout();
            if (isSelected) {
                isSelected = false;
            }
        }
    }

}
