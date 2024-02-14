package com.ide.codekit.casttotv.Extras;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.ide.codekit.casttotv.Model.CustomBottomNavModel;
import com.ide.codekit.casttotv.R;

public class CbnMenuItem extends RelativeLayout {
    private Context context;
    private RelativeLayout relativeLayout, selectedRl;
    private CardView cardView;
    private TextView textView;
    private ImageView imageView;
    private int itemId;
    private boolean isSelected;
    CustomBottomNav.OnMenuItemSelectedListener onMenuItemSelectedListener;

    public CbnMenuItem(Context context, CbnMenuItemModel model, boolean isSelected) {
        super(context);
        initialize(context, model, isSelected);
    }

    @SuppressLint("UseCompatTextViewDrawableApis")
    private void initialize(Context context, CbnMenuItemModel model, boolean isSelected) {
        LayoutInflater.from(context).inflate(R.layout.menu_item, this, true);
        this.itemId = model.getModel().getItemId();
        this.isSelected = isSelected;
        relativeLayout = findViewById(R.id.item_rl);
        cardView = findViewById(R.id.selected_card);
        selectedRl = findViewById(R.id.selected_rl);
        textView = findViewById(R.id.unselected_tv);
        imageView = findViewById(R.id.selected_iv);

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
                onSelector();
                onMenuItemSelectedListener.onMenuItemSelected(getItemId());
            }
        });
    }

    public void setOnMItemSelectedListener(CustomBottomNav.OnMenuItemSelectedListener onMenuItemSelectedListener) {
        this.onMenuItemSelectedListener = onMenuItemSelectedListener;
    }

    private void onSelector() {
        if (!isSelected) {
            isSelected = true;
            textView.setVisibility(INVISIBLE);
            selectedRl.setVisibility(VISIBLE);
        } else {
            isSelected = false;
            selectedRl.setVisibility(INVISIBLE);
            textView.setVisibility(VISIBLE);
        }
    }

    public int getItemId() {
        return itemId;
    }
}
