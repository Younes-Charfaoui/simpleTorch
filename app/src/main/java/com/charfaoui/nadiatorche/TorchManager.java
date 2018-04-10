package com.charfaoui.nadiatorche;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

/**
 * @definition this class helps to use the Torch built in
 * the phone by providing interface to turn on and off
 * the light.
 */
public class TorchManager {

    private static final String TAG = TorchManager.class.getSimpleName();

    private Context mContext;
    private Camera mCamera;
    private Camera.Parameters mParams;
    private CameraManager mCameraManager;

    TorchManager(Context mContext) {
        this.mContext = mContext;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M && mCamera == null) {
            mCamera = Camera.open();
        }
    }

    /**
     * this static method will check if the phone
     * of the user has the feature of the  Flash.
     *
     * @return boolean indicating the this phone has
     * or has not this feature.
     */
    public static boolean isFlashAvailable(Context context) {
        if (!context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            Log.i(TAG, "The flash is not available on this device.");
            return false;
        }
        return true;
    }

    /**
     * this method will try to put the torch in the ON state ,
     * by doing that , it will change the variable and the text view.
     */
    public void turnOn() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mCameraManager = (CameraManager) mContext.getSystemService(Context.CAMERA_SERVICE);
            if (mCameraManager != null) {
                try {
                    String cameraId = mCameraManager.getCameraIdList()[0];
                    mCameraManager.setTorchMode(cameraId, true);
                } catch (CameraAccessException e) {
                    Log.e(TAG, "turnOn: " + e.getMessage(), e);
                }
            }
        } else {
            getCamera();
            mParams = mCamera.getParameters();
            mParams.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            mCamera.setParameters(mParams);
            mCamera.startPreview();
        }
    }

    /**
     * this method will tru to put the torch in the OFF state ,
     * by doing that , it will change the variable and the text view.
     */
    public void turnOff() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mCameraManager = (CameraManager) mContext.getSystemService(Context.CAMERA_SERVICE);
            if (mCameraManager != null) {
                try {
                    String cameraId = mCameraManager.getCameraIdList()[0];
                    mCameraManager.setTorchMode(cameraId, false);
                } catch (CameraAccessException e) {
                    Log.e(TAG, "turnOff: " + e.getMessage(), e);
                }
            }
        } else {
            getCamera();
            mParams = mCamera.getParameters();
            mParams.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            mCamera.setParameters(mParams);
            mCamera.stopPreview();
        }
    }

    /**
     * this method will try to get the camera if
     * it was available.
     */
    public void getCamera() {
        if (mCamera == null) {
            try {
                mCamera = Camera.open();
            } catch (Exception e) {
                Toast.makeText(mContext, R.string.another_app, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * this method like it's name said that
     * will release the camera for future use.
     */
    public void release() {
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }
}
