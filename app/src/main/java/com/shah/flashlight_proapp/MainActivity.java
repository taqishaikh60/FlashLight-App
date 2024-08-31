package com.shah.flashlight_proapp;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private boolean isFlashlightOn = false;
    private CameraManager cameraManager;
    private String cameraId;
    private View lightBeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView flashlightImage = findViewById(R.id.flimage);
        ImageButton flashlightButton = findViewById(R.id.imageView);
        lightBeam = findViewById(R.id.light_beam);

        cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);

        try {
            cameraId = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        flashlightButton.setOnClickListener(v -> {
            try {
                if (isFlashlightOn) {
                    turnOffFlashlight();

                    flashlightImage.setImageResource(R.drawable.image2);
                    moveButton(flashlightButton, 0, 50);
                    lightBeam.setVisibility(View.GONE);
                } else {
                    turnOnFlashlight();

                    flashlightImage.setImageResource(R.drawable.image2);
                    moveButton(flashlightButton, 0, -20);
                    lightBeam.setVisibility(View.VISIBLE);
                }
            } catch (CameraAccessException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void turnOnFlashlight() throws CameraAccessException {
        cameraManager.setTorchMode(cameraId, true);
        isFlashlightOn = true;
    }

    private void turnOffFlashlight() throws CameraAccessException {
        cameraManager.setTorchMode(cameraId, false);
        isFlashlightOn = false;
    }

    private void moveButton(ImageButton button, int x, int y) {
        TranslateAnimation animation = new TranslateAnimation(0, x, 0, y);
        animation.setDuration(100);
        animation.setFillAfter(true);
        button.startAnimation(animation);
    }
}
