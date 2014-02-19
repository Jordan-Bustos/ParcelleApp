package view.activity;

import java.util.List;
import persistence.DaoUtils;
import view.adapter.ParcelleItemArrayAdapter;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import business.Plot;
import fr.iut.licpro.parcelleapp.R;

public class PlotListActivity extends Activity implements OnItemClickListener
{
	/** The list view of plot. */
	private ListView	lvPLots;

	@Override
	protected void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plot_list);
		final List<Plot> listePlots = DaoUtils.getAllData(
				getApplicationContext(), Plot.class);
		if (listePlots == null)
		{
			Toast.makeText(getBaseContext(),
					R.string.erreur_pendant_le_chargement, Toast.LENGTH_SHORT)
					.show();
		}
		else if (listePlots.isEmpty())
		{
			Toast.makeText(getBaseContext(),
					R.string.la_base_de_donnees_est_vide, Toast.LENGTH_SHORT)
					.show();
		}
		else
		{
			lvPLots = (ListView) findViewById(R.id.PlotList);
			lvPLots.setOnItemClickListener(this);
			lvPLots.setAdapter(new ParcelleItemArrayAdapter(this, listePlots,
					true));
		}
		final ActionBar actionBar = getActionBar();
		actionBar.show();
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.plot_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item)
	{
		final Intent intent;
		switch (item.getItemId())
		{
			case R.id.menu_add:
				intent = new Intent(getBaseContext(), PlotAddActivity.class);
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

	/* _________________________________________________________ */
	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView,
	 *      android.view.View, int, long)
	 */
	@Override
	public void onItemClick(final AdapterView<?> arg0, final View v,
			final int position, final long arg3)
	{
		final Plot selectedPlot = (Plot) lvPLots.getItemAtPosition(position);
		final Intent intent = new Intent(getBaseContext(),
				PlotDetailsActivity.class);
		final Bundle bundleToSend = new Bundle();
		bundleToSend.putString("plotName", selectedPlot.getName());
		bundleToSend.putString("plotGrowing", selectedPlot.getCulture()
				.toString());
		bundleToSend.putString("plotLastGrowing", selectedPlot
				.getLast_growing().toString());
		bundleToSend.putString("plotPathImage", selectedPlot.getImage());
		bundleToSend.putInt("plotSurface", selectedPlot.getSurface());
		intent.putExtras(bundleToSend);
		startActivity(intent);
	}
}
