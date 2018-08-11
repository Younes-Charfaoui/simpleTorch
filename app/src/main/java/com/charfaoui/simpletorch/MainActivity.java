package com.charfaoui.simpletorch;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @definition this activity will show to the user a view to tap on with
 * the String of {ON , OFF} , as the user interact with this view , this
 * class will handle this interaction by turning off and on the Torch.
 */

public class MainActivity extends AppCompatActivity {

    //this variable will hold the current state of the TORCH.
    private static boolean isOn = false;

    // text view to display the state {ON , OFF} for the user.
    private TextView mText;
    private TorchManager mTorchManager;
    private ImageView mImage;

    /**
     * this method is overridden for the purpose of getting
     * reference the camera object.
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (mTorchManager != null) mTorchManager.getCamera();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mText = findViewById(R.id.on_off_text_view);
        mImage = findViewById(R.id.button_image_view);
        //testing if the phone has a flash to use
        if (!TorchManager.isFlashAvailable(this)) {
            AvailabilityDialog dialog = new AvailabilityDialog();
            dialog.show(getSupportFragmentManager(), null);
        } else {
            mTorchManager = new TorchManager(this);
        }

        findViewById(R.id.turnLight).setOnClickListener(v -> {
                    if (isOn) {
                        /*
                          if the boolean is true that means that the flash
                          is the on state , we need to set the boolean to false
                          and turn it off.
                         */
                        isOn = false;
                        mTorchManager.turnOff();
                        mImage.animate().rotationBy(90f)
                                .setDuration(300)
                                .setInterpolator(new FastOutSlowInInterpolator())
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {
                                        super.onAnimationStart(animation);
                                        findViewById(R.id.turnLight).setEnabled(false);
                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        mImage.setColorFilter(getResources().getColor(R.color.colorAccentTwo));
                                        mText.setText(R.string.off);
                                        findViewById(R.id.turnLight).setEnabled(true);
                                    }
                                });

                    } else {
                        /*
                          if the boolean is false that means that the flash
                          is the off state , we need to set the boolean to true
                          and turn it on.
                         */
                        isOn = true;
                        mTorchManager.turnOn();
                        mImage.animate().rotationBy(90f)
                                .setDuration(300)
                                .setInterpolator(new FastOutSlowInInterpolator())
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {
                                        super.onAnimationStart(animation);
                                        findViewById(R.id.turnLight).setEnabled(false);
                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        mImage.setColorFilter(getResources().getColor(R.color.colorAccent));
                                        mText.setText(R.string.on);
                                        findViewById(R.id.turnLight).setEnabled(true);
                                    }
                                });

                    }
                }
        );
    }

    /**
     * this method is overridden for the purpose of releasing
     * the camera object.
     */
    @Override
    protected void onStop() {
        super.onStop();
        isOn = false;
        mText.setText(R.string.off);
        if (mTorchManager != null) {
            mTorchManager.turnOff();
            mTorchManager.release();
        }
    }

    /**
     * this method is overridden for the purpose of releasing
     * the camera object.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTorchManager != null) mTorchManager.release();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_us_menu:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}