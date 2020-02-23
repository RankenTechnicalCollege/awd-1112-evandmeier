package edu.ranken.emeier.codingchallenge52;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static final String TRANSITION_TYPE = "edu.ranken.emeier.TRANSITION_TYPE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if ("explode".equals(intent.getStringExtra(TRANSITION_TYPE))) {
            getWindow().setEnterTransition(new Explode().setDuration(1000));
        } else if ("fade".equals(intent.getStringExtra(TRANSITION_TYPE))) {
            getWindow().setEnterTransition(new Fade().setDuration(1000));
        }

        setContentView(R.layout.activity_main);
    }

    public void explode(View view) {
        getWindow().setExitTransition(new Explode().setDuration(1000));

        Intent intent = new Intent(this, this.getClass());
        intent.putExtra(TRANSITION_TYPE, "explode");
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    public void fade(View view) {
        getWindow().setExitTransition(new Fade().setDuration(1000));

        Intent intent = new Intent(this, this.getClass());
        intent.putExtra(TRANSITION_TYPE, "fade");
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    public void rotate(View view) {
        // create object animator
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, View.ROTATION, 0f, 360f);
        animator.setDuration(1500);

        animator.start();
    }

    public void swapTransition(View view) {
        // view that we are swapping with
        View otherImage = findViewById(R.id.yellowBlock);

        ActivityOptions options =
                ActivityOptions.makeSceneTransitionAnimation(
                        this,
                                Pair.create(view, "swapAndroid"),
                                Pair.create(otherImage, "swapBlock"));

        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent,options.toBundle());
    }
}
