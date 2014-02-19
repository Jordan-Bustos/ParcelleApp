/* _________________________________________________________ */
/* _________________________________________________________ */
/**
 * Fichier : ReverseGeocodingTask.java
 * 
 * Créé le 12 févr. 2014 à 09:25:25
 * 
 * Auteur : Jerome POINAS
 */
package utility;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;
import business.interfaces.TaskFinishedListener;
import fr.iut.licpro.parcelleapp.R;

/* _________________________________________________________ */
/**
 * @author Jordan Bustos
 * 
 */
public class ReverseGeocodingTask extends AsyncTask<String, String, String>
{
	private final Context				mContext;
	private String						mAddress;
	private final Location				mParams[];
	private final TaskFinishedListener	mListener;
	private final Activity				mActivity;

	/* _________________________________________________________ */
	/**
	 * @param mContext
	 * @param mParams
	 * @param mListener
	 */
	public ReverseGeocodingTask(final Context mContext,
			final Location[] mParams, final TaskFinishedListener mListener,
			final Activity activity)
	{
		super();
		this.mContext = mContext;
		this.mParams = mParams;
		this.mListener = mListener;
		mActivity = activity;
	}

	/* _________________________________________________________ */
	/**
	 * @param params
	 * @return
	 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
	 */
	@Override
	protected String doInBackground(final String... params)
	{
		final Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
		final Location loc = mParams[0];
		List<Address> addresses = null;
		try
		{
			addresses = geocoder.getFromLocation(loc.getLatitude(),
					loc.getLongitude(), 1);
		}
		catch (final IOException e)
		{
			Toast.makeText(mContext,
					mActivity.getString(R.string.impossible_de_geolocaliser),
					Toast.LENGTH_SHORT).show();
			final LocationManager manager = (LocationManager) mActivity
					.getSystemService(Context.LOCATION_SERVICE);
			manager.removeUpdates((LocationListener) mActivity);
		}
		if ((addresses != null) && (addresses.size() > 0))
		{
			final Address address = addresses.get(0);
			mAddress = String.format("%s, %s, %s", address
					.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0)
					: "", address.getLocality(), address.getCountryName());
		}
		return mAddress;
	}

	@Override
	protected void onPostExecute(final String adr)
	{
		final EditText addresse = (EditText) mActivity
				.findViewById(R.id.fieldAddresse);
		addresse.setText(adr);
	}
}
/* _________________________________________________________ */
/*
 * Fin du fichier ReverseGeocodingTask.java.
 * /*_________________________________________________________
 */
