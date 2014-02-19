package view.activity;

import java.util.Observable;
import java.util.Observer;
import model.ModelPlot;
import utility.ReverseGeocodingTask;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import business.SurfacePicker;
import business.interfaces.TaskFinishedListener;
import fr.iut.licpro.parcelleapp.R;

public class PlotAddActivity extends Activity implements OnClickListener,
		Observer, LocationListener, TaskFinishedListener
{
	/**
	 * The model
	 */
	ModelPlot		myModel;
	/**
	 * The Location Manager
	 */
	LocationManager	mLocationManager;
	/**
	 * Latitude
	 */
	private double	latitudePlot;
	/**
	 * Longitude
	 */
	private double	longitudePlot;
	/**
	 * Address
	 */
	private String	adressePlot;
	/**
	 * Path to picture of the plot.
	 */
	private String pictureFile = "";

	@Override
	protected void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plot_add);
		myModel = new ModelPlot();
		myModel.sAbonner(this);
		final Button bt_add = (Button) findViewById(R.id.bt_add);
		bt_add.setOnClickListener(this);
		final ImageButton bt_geo = (ImageButton) findViewById(R.id.imageButtonLocation);
		bt_geo.setOnClickListener(this);
		final ImageButton bt_takePhoto = (ImageButton) findViewById(R.id.btn_takePhoto);
		bt_takePhoto.setOnClickListener(this);
		// ********** Parametrage du numberPicker ****************//
		final SurfacePicker pickerSetting = new SurfacePicker();
		final NumberPicker picker = (NumberPicker) findViewById(R.id.pickerSurface);
		picker.setMinValue(0);
		picker.setMaxValue(pickerSetting.getValues().length);
		// ******************************************************//
		// ********** Parametrage des Spinners ****************//
		final Spinner spinnerPlot = (Spinner) findViewById(R.id.Tv_addPlot);
		final Spinner spinnerOldPlot = (Spinner) findViewById(R.id.Tv_addOldPlot);
		final ArrayAdapter<CharSequence> adapter = ArrayAdapter
				.createFromResource(this, R.array.plots_array,
						android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerPlot.setAdapter(adapter);
		spinnerOldPlot.setAdapter(adapter);
		// ******************************************************//
		// Mise en place de l'apercu de la photo qui vient d'être prise.
		ImageView img_preview = (ImageView)findViewById(R.id.preview);		
		img_preview.setVisibility(ImageView.INVISIBLE);
		TextView libelleApercu = (TextView) findViewById(R.id.apercuLibelle);
		libelleApercu.setVisibility(TextView.INVISIBLE);
		final Intent intentReceive = getIntent();
		pictureFile = intentReceive
				.getStringExtra(getString(R.string.picturefile));
		if (pictureFile != null)
		{
			if (!pictureFile.equals(""))
			{
				try{					
		            final Bitmap bitmap = BitmapFactory.decodeFile(pictureFile);
		            img_preview.setImageBitmap(bitmap);
		            img_preview.setContentDescription(pictureFile);
		            img_preview.setVisibility(ImageView.VISIBLE);
		            libelleApercu.setVisibility(TextView.VISIBLE);
				}
				catch(Exception e){}
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.plot_add, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item)
	{
		final Intent intent;
		switch (item.getItemId())
		{
			case R.id.action_parcelles:
				intent = new Intent(getBaseContext(), PlotListActivity.class);
				startActivity(intent);
				return true;
			case R.id.action_convertisseur:
				intent = new Intent(getBaseContext(),
						PlotConverterActivity.class);
				startActivity(intent);
				return true;
			case R.id.action_commande:
				intent = new Intent(getBaseContext(), PlotOrderActivity.class);
				startActivity(intent);
				return true;
			case R.id.action_controle:
				intent = new Intent(getBaseContext(), PlotControlActivity.class);
				startActivity(intent);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void update(final Observable obs, final Object arg1)
	{
		if (obs instanceof ModelPlot)
		{
			Toast.makeText(getBaseContext(),
					R.string.ajout_de_la_nouvelle_parcelle, Toast.LENGTH_LONG)
					.show();
		}
	}

	@Override
	public void onClick(final View v)
	{
		switch (v.getId())
		{
			case R.id.bt_add:
				myModel.ajouter(this);
				break;
			case R.id.imageButtonLocation:
				// ********** Abonnement au Location Manager ***************//
				mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
				if (mLocationManager
						.isProviderEnabled(LocationManager.GPS_PROVIDER))
				{
					mLocationManager.requestLocationUpdates(
							LocationManager.GPS_PROVIDER, 3000, 0, this);
				}
				else
				{
					mLocationManager.requestLocationUpdates(
							LocationManager.NETWORK_PROVIDER, 3000, 0, this);
				}
				break;
			case R.id.btn_takePhoto:
				Intent intent = new Intent(getBaseContext(), PlotCameraActivity.class);
				startActivity(intent);
				break;
			default:
				break;
		}
	}

	/* _________________________________________________________ */
	/**
	 * @param arg0
	 * @see android.location.LocationListener#onLocationChanged(android.location.Location)
	 */
	@Override
	public void onLocationChanged(final Location location)
	{
		setLatitudePlot(location.getLatitude());
		setLongitudePlot(location.getLongitude());
		if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD)
				&& Geocoder.isPresent())
		{
			final ReverseGeocodingTask reverseGeocodingTask = new ReverseGeocodingTask(
					this, new Location[] { location }, this, this);
			reverseGeocodingTask.execute();
		}
		// ********** Désabonnement du Location Manager ***************//
		mLocationManager.removeUpdates(this);
	}

	/* _________________________________________________________ */
	/**
	 * @param arg0
	 * @see android.location.LocationListener#onProviderDisabled(java.lang.String)
	 */
	@Override
	public void onProviderDisabled(final String arg0)
	{
	}

	/* _________________________________________________________ */
	/**
	 * @param arg0
	 * @see android.location.LocationListener#onProviderEnabled(java.lang.String)
	 */
	@Override
	public void onProviderEnabled(final String arg0)
	{
	}

	/* _________________________________________________________ */
	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @see android.location.LocationListener#onStatusChanged(java.lang.String,
	 *      int, android.os.Bundle)
	 */
	@Override
	public void onStatusChanged(final String arg0, final int arg1,
			final Bundle arg2)
	{
	}

	/* _________________________________________________________ */
	/**
	 * @param value
	 * @see business.interfaces.TaskFinishedListener#onTaskCompleted(java.lang.String)
	 */
	@Override
	public void onTaskCompleted(final String value)
	{
	}

	public double getLatitudePlot() {
		return latitudePlot;
	}

	public void setLatitudePlot(double latitudePlot) {
		this.latitudePlot = latitudePlot;
	}

	public double getLongitudePlot() {
		return longitudePlot;
	}

	public void setLongitudePlot(double longitudePlot) {
		this.longitudePlot = longitudePlot;
	}

	public String getAdressePlot() {
		return adressePlot;
	}

	public void setAdressePlot(String adressePlot) {
		this.adressePlot = adressePlot;
	}
}
