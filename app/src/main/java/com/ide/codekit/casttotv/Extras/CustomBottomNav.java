package com.ide.codekit.casttotv.Extras;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.ide.codekit.casttotv.Model.CustomBottomNavModel;
import com.ide.codekit.casttotv.R;

import java.util.List;

public class CustomBottomNav extends LinearLayout {
    int selectedItemIconColor, selectedItemBackgroundColor, backgroundColor, itemIconColor, itemTextColor;
    Drawable backgroundDrawable, selectedItemBackgroundDrawable;
    List<CustomBottomNavModel> modelList;
    CbnMenuItemModel itemModel;

    public CustomBottomNav(Context context) {
        super(context);
    }

    public CustomBottomNav(Context context, List<CustomBottomNavModel> modelList) {
        super(context);
        this.modelList = modelList;
    }

    public CustomBottomNav(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
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

//        if (backgroundDrawable != null) {
//            this.setBackground(backgroundDrawable);
//        } else {
//            this.setBackgroundColor(backgroundColor);
//        }

        createMenuItem(context);


//        itemModel = new CbnMenuItemModel()

    }

    private void createMenuItem(Context context) {
        boolean isSelected = true;
        for (CustomBottomNavModel model : modelList) {
            CbnMenuItem menuItem = new CbnMenuItem(context, model, isSelected);
            this.addView(menuItem);
            if (isSelected){
                isSelected = false;
            }
        }
    }

}
