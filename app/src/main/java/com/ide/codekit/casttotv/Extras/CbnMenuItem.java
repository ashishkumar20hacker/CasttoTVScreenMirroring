package com.ide.codekit.casttotv.Extras;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.ide.codekit.casttotv.Model.CustomBottomNavModel;
import com.ide.codekit.casttotv.R;

public class CbnMenuItem extends RelativeLayout {
    public CbnMenuItem(Context context, CustomBottomNavModel model, boolean isSelected) {
        super(context);
        initialize(context, model, isSelected);
    }
    private void initialize(Context context, CustomBottomNavModel model, boolean isSelected) {
        LayoutInflater.from(context).inflate(R.layout.menu_item, this, true);
    }
}
