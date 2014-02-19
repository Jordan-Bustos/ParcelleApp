package view.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import business.CameraPreview;
import fr.iut.licpro.parcelleapp.R;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class PlotCameraActivity extends Activity {

	/**
	 * The camera
	 */
	private static Camera mCamera;
	/**
	 * The camera preview
	 */
	private static CameraPreview mPreview;
	/**
	 * The picture.
	 */
	private PictureCallback mPicture = new PictureCallback() {
		
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			File pictureFile = getOutputMediaFile();
			if(pictureFile == null)
			{
				return;
			}
			try{
				FileOutputStream fos = new FileOutputStream(pictureFile);
				fos.write(data);
				fos.close();
				Toast.makeText(getApplicationContext(), getString(R.string.image_saved) + pictureFile.getPath(), Toast.LENGTH_LONG).show();
			}
			catch(FileNotFoundException e){
				Toast.makeText(getApplicationContext(), getString(R.string.file_not_found), Toast.LENGTH_SHORT).show();
			}
			catch(IOException e){
				Toast.makeText(getApplicationContext(), getString(R.string.error_accessing_file), Toast.LENGTH_SHORT).show();
			}	
			Intent intent = new Intent(getBaseContext(), PlotAddActivity.class);
			intent.putExtra(getString(R.string.picturefile), pictureFile.getPath());
			startActivity(intent);
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plot_camera);
		
		if(checkCameraHardware(getBaseContext())){
			mCamera = getCameraInstance();
			if(mCamera!=null)
			{
				mPreview = new CameraPreview(this,mCamera);
				mPreview.setActivity(this);
				FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
				preview.addView(mPreview);
				
				Button captureButton = (Button)findViewById(R.id.button_capture);
				captureButton.setOnClickListener(
						new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								mCamera.takePicture(null, null, mPicture);
							}
						});
			}
			else{
				Toast.makeText(getBaseContext(), getString(R.string.camera_unavailable), Toast.LENGTH_SHORT).show();
			}
		}
		else{
			Toast.makeText(getBaseContext(), getString(R.string.camera_not_found), Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.plot_camera, menu);
		return true;
	}
	
	/**
	 * Method checking if a camera exist on the device.
	 * @param context The context
	 * @return <true> if the device has a camera, <false> if not.
	 */
	private boolean checkCameraHardware(Context context){
		if(context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Methode attempting to return the camera instance.
	 * @return The camera instance.
	 */
	public static Camera getCameraInstance(){
		Camera c = null;
		try{
			releaseCameraAndPreview();
			c=Camera.open();
		}
		catch (Exception e){
			return null;
		}
		return c;
	}
	
	/**
	 * Relase the camera.
	 */
	private static void releaseCameraAndPreview() {
	    if (mCamera != null) {
	        mCamera.release();
	        mCamera = null;
	    }
	}
	
	/**
	 * Create dicrectory and the image.
	 * @return The file created.
	 */
    public static File getOutputMediaFile(){
 
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                  Environment.DIRECTORY_PICTURES), "PlotApp");
 
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
               return null;
            }
        }
 
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
            "IMG_"+ timeStamp + ".jpg");
        return mediaFile;
    }

}
