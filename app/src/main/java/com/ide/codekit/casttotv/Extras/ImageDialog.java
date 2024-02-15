package com.ide.codekit.casttotv.Extras;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.ide.codekit.casttotv.R;

public class ImageDialog extends Dialog {

    public ImageDialog(@NonNull Context context, String imageUrl) {
        super(context);
        setContentView(R.layout.dialog_image);

        // Make the dialog background transparent
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        ImageView imageView = findViewById(R.id.imageView);
        ImageView backbt = findViewById(R.id.back_bt);

        // Load the image from the URL using Glide
        Glide.with(context)
             .load(imageUrl)
//             .placeholder(R.drawable.placeholder) // Placeholder image while loading
//             .error(R.drawable.error_image) // Error image if loading fails
             .into(imageView);

        backbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
