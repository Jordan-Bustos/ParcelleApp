package business;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback{
	/**
	 * The surface holder.
	 */
	private SurfaceHolder mHolder;
	/**
	 * The Camera.
	 */
	private Camera mCamera;
	/**
	 * The activity.
	 */
	private Activity activity;

	/**
	 * Constructor of a CameraPreview.
	 * @param context The context.
	 * @param camera The Camera.
	 */
	public CameraPreview(Context context, Camera camera){
		super(context);
		mCamera = camera;
		mHolder = getHolder();
		mHolder.addCallback(this);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		if(mHolder.getSurface() == null) {
			return;
		}
		try{
			mCamera.stopPreview();
		}
		catch ( Exception e){}

		//Starting the preview with new settings
		try{
			mCamera.setPreviewDisplay(mHolder);
			updateCameraDisplay();
			mCamera.startPreview();
		}
		catch ( Exception e){}
	}
	
	/**
	 * Method allowing to display the preview with a correct orientation.
	 */
	public void updateCameraDisplay() {
     
        Display display = getActivity().getWindowManager()
                .getDefaultDisplay();

        int rotation = getActivity().getResources().getConfiguration().orientation;
        if (display.getRotation() == Surface.ROTATION_0) {

            if (rotation == Configuration.ORIENTATION_LANDSCAPE) {
            	mCamera.setDisplayOrientation(0);
            } else {
                mCamera.setDisplayOrientation(90);
            }
        }

        else if (display.getRotation() == Surface.ROTATION_90) {
            if (rotation == Configuration.ORIENTATION_PORTRAIT) {
                mCamera.setDisplayOrientation(270);
            }
        }

        else if (display.getRotation() == Surface.ROTATION_180) {
            if (rotation == Configuration.ORIENTATION_LANDSCAPE) {
            	mCamera.setDisplayOrientation(180);
            }
        }

        else if (display.getRotation() == Surface.ROTATION_270) {
            if (rotation == Configuration.ORIENTATION_PORTRAIT) {
                mCamera.setDisplayOrientation(90);
            }
        }
    }

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try{
			mCamera.setPreviewDisplay(holder);
			mCamera.startPreview();
		}
		catch(IOException e){

		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		if (mCamera != null) {
			mCamera.stopPreview();
			
		}
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}
}
