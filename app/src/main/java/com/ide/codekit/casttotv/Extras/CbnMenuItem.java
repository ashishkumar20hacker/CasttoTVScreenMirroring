package com.ide.codekit.casttotv.Extras;

import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.ide.codekit.casttotv.Model.CbnMenuItemModel;
import com.ide.codekit.casttotv.R;

public class CbnMenuItem extends RelativeLayout {
    private Context context;
    private RelativeLayout relativeLayout, selectedRl, mainRl;
    private CardView cardView;
    private TextView textView;
    private ImageView imageView;
    private int itemId;
    private boolean isSelectedMain;
    CustomBottomNav.OnMenuItemSelectedListener onMenuItemSelectedListener,onMenuItemSelectedListener2;

    public CbnMenuItem(Context context, CbnMenuItemModel model, boolean isSelected) {
        super(context);
        initialize(context, model, isSelected);
    }

    @SuppressLint("UseCompatTextViewDrawableApis")
    private void initialize(Context context, CbnMenuItemModel model, boolean isSelected) {
        LayoutInflater.from(context).inflate(R.layout.menu_item, this, true);
        this.itemId = model.getModel().getItemId();
        this.isSelectedMain = isSelected;
        relativeLayout = findViewById(R.id.item_rl);
        cardView = findViewById(R.id.selected_card);
        selectedRl = findViewById(R.id.selected_rl);
        textView = findViewById(R.id.unselected_tv);
        imageView = findViewById(R.id.selected_iv);
        mainRl = findViewById(R.id.rl_main);
        mainRl.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        relativeLayout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        if (model.getBackgroundDrawable() != null) {
            relativeLayout.setBackground(model.getBackgroundDrawable());
        } else {
            relativeLayout.setBackgroundColor(model.getBackgroundColor());
        }
        if (model.getSelectedItemBackgroundDrawable() != null) {
            cardView.setForeground(model.getSelectedItemBackgroundDrawable());
        } else {
            cardView.setCardBackgroundColor(model.getSelectedItemBackgroundColor());
        }
        textView.setText(model.getModel().getItemName());
        textView.setTextColor(model.getItemTextColor());
        textView.setCompoundDrawablesWithIntrinsicBounds(0, model.getModel().getItemIcon(), 0, 0);
        textView.setCompoundDrawableTintList(ColorStateList.valueOf(model.getItemIconColor()));
        imageView.setImageResource(model.getModel().getItemIcon());
        imageView.setImageTintList(ColorStateList.valueOf(model.getSelectedItemIconColor()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            cardView.setOutlineAmbientShadowColor(model.getSelectedItemIconColor());
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            cardView.setOutlineSpotShadowColor(model.getSelectedItemIconColor());
        }
        if (isSelected) {
            textView.setVisibility(INVISIBLE);
            selectedRl.setVisibility(VISIBLE);
        } else {
            selectedRl.setVisibility(INVISIBLE);
            textView.setVisibility(VISIBLE);
        }
        relativeLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isSelectedMain) {
                    onMenuItemSelectedListener.onMenuItemSelected(getItemId());
                    onMenuItemSelectedListener2.onMenuItemSelected(getItemId());
                }
            }
        });
    }

    public void setOnMItemSelectedListener(CustomBottomNav.OnMenuItemSelectedListener onMenuItemSelectedListener) {
        this.onMenuItemSelectedListener = onMenuItemSelectedListener;
    }
    public void setOnMItemSelectedListener2(CustomBottomNav.OnMenuItemSelectedListener onMenuItemSelectedListener) {
        this.onMenuItemSelectedListener2 = onMenuItemSelectedListener;
    }

    public void onSelector(boolean b) {
        if (b) {
            isSelectedMain = true;
            textView.setVisibility(INVISIBLE);
            selectedRl.setVisibility(VISIBLE);
        } else {
            isSelectedMain = false;
            selectedRl.setVisibility(INVISIBLE);
            textView.setVisibility(VISIBLE);
        }
    }

    public int getItemId() {
        return itemId;
    }
}
