package com.aa.thrivetrack.helpers;

import android.animation.ObjectAnimator;
import android.view.View;

public class AnimationHelper {
    public static void likeAnimation(View v){
        ObjectAnimator scaleUp = ObjectAnimator.ofFloat(v, "scaleX", 1.0f, 1.5f);
        scaleUp.setDuration(500); // Duration in milliseconds
        scaleUp.setRepeatCount(1); // Repeat once
        scaleUp.setRepeatMode(ObjectAnimator.REVERSE); // Reverse animation on repeat

        // Start the animation
        scaleUp.start();
    }
}
