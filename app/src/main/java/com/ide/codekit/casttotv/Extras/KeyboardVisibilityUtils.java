package com.ide.codekit.casttotv.Extras;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

public class KeyboardVisibilityUtils {

    public interface KeyboardVisibilityListener {
        void onKeyboardVisibilityChanged(boolean keyboardVisible);
    }

    public static void setKeyboardVisibilityListener(Activity activity, final KeyboardVisibilityListener listener) {
        View contentView = activity.findViewById(android.R.id.content);
        contentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            private boolean wasKeyboardVisible;

            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                contentView.getWindowVisibleDisplayFrame(r);
                int screenHeight = contentView.getRootView().getHeight();

                // Calculate the height difference between the screen height and visible display frame
                int heightDiff = screenHeight - (r.bottom - r.top);

                // If the height difference is more than 200dp, the keyboard is likely visible
                boolean isKeyboardVisible = heightDiff > 200;

                // Check if the keyboard visibility has changed
                if (isKeyboardVisible != wasKeyboardVisible) {
                    wasKeyboardVisible = isKeyboardVisible;
                    listener.onKeyboardVisibilityChanged(isKeyboardVisible);
                }
            }
        });
    }
}
