package view.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import business.Growing;
import fr.iut.licpro.parcelleapp.R;

public class PlotDetailsActivity extends Activity
{
	@Override
	protected void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plot_details);
		;
		final Bundle bundleReceive = getIntent().getExtras();
		if (bundleReceive != null)
		{
			final String plotName = bundleReceive.getString("plotName");
			final String plotGrowing = bundleReceive.getString("plotGrowing");
			final String plotLastGrowing = bundleReceive
					.getString("plotLastGrowing");
			final String plotPathImage = bundleReceive
					.getString("plotPathImage");
			final int plotSurface = bundleReceive.getInt("plotSurface");
			((TextView) findViewById(R.id.PlotName)).setText(plotName);
			((TextView) findViewById(R.id.PlotGrowingValue))
					.setText(plotGrowing);
			((TextView) findViewById(R.id.PlotLastGrowingValue))
					.setText(plotLastGrowing);
			((TextView) findViewById(R.id.PlotSurfaceValue))
					.setText(plotSurface + " hectares");
			if(plotPathImage!=null && !plotPathImage.equals(getString(R.string.apercu)))
			{
				setCustomImagePlot(plotPathImage);
			}
			else{
				setImagePlot(plotGrowing);
			}
		}
	}

	/* _________________________________________________________ */
	/**
	 * setCustomImagePot
	 * 
	 * @param plotName
	 *            Le nom du chemin jusqu'a l'image
	 */
	private void setCustomImagePlot(final String plotPathImage) {
		final ImageView plotImage = (ImageView) findViewById(R.id.ImagePlot);
		final Bitmap bitmap = BitmapFactory.decodeFile(plotPathImage);
		plotImage.setImageBitmap(bitmap);
		plotImage.setContentDescription(plotPathImage);
	}

	/* _________________________________________________________ */
	/**
	 * setImagePot
	 * 
	 * @param plotName
	 *            Le nom de l'image
	 */
	private void setImagePlot(final String plotGrowing)
	{
		final ImageView plotImage = (ImageView) findViewById(R.id.ImagePlot);
		if (plotGrowing.equals(Growing.Maïs.toString()))
		{
			plotImage.setImageResource(R.drawable.mais);
		}
		if (plotGrowing.equals(Growing.Colza.toString()))
		{
			plotImage.setImageResource(R.drawable.colza);
		}
		if (plotGrowing.equals(Growing.Orge.toString()))
		{
			plotImage.setImageResource(R.drawable.orge);
		}
		if (plotGrowing.equals(Growing.Tournesol.toString()))
		{
			plotImage.setImageResource(R.drawable.tournesol);
		}
		if (plotGrowing.equals(Growing.Prairie.toString()))
		{
			plotImage.setImageResource(R.drawable.prairie);
		}
		if (plotGrowing.equals(Growing.Blé.toString()))
		{
			plotImage.setImageResource(R.drawable.ble);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.plot_details, menu);
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
}
