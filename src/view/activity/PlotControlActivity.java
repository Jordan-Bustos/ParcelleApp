package view.activity;

import java.util.Observable;
import java.util.Observer;
import model.ModelControl;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import fr.iut.licpro.parcelleapp.R;

public class PlotControlActivity extends Activity implements Observer
{
	/**
	 * The field density
	 */
	private EditText		densite;
	/**
	 * The field ecartement
	 */
	private EditText		ecartement;
	/**
	 * Grains unit
	 */
	private TextView		nbGrainsUnit;
	/**
	 * Rang unit
	 */
	private TextView		nbRangUnit;
	/**
	 * Le nombre de grains
	 */
	private TextView		resNbGrains;
	/**
	 * Le nombre de rang
	 */
	private TextView		resNbRang;
	/**
	 * The model
	 */
	private ModelControl	model;

	@Override
	protected void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plot_control);
		densite = (EditText) findViewById(R.id.densityVal);
		ecartement = (EditText) findViewById(R.id.ecartementVal);
		densite.addTextChangedListener(densityWatcher);
		ecartement.addTextChangedListener(ecartementWatcher);
		nbGrainsUnit = (TextView) findViewById(R.id.nbGrainsUnit);
		nbGrainsUnit.setVisibility(View.INVISIBLE);
		nbRangUnit = (TextView) findViewById(R.id.nbRangUnit);
		nbRangUnit.setVisibility(View.INVISIBLE);
		resNbGrains = (TextView) findViewById(R.id.nbGrainsVal);
		resNbRang = (TextView) findViewById(R.id.nbRangVal);
		model = new ModelControl(this);
		model.sAbonner(this);
		final Intent intentReceive = getIntent();
		final String density = intentReceive
				.getStringExtra(getString(R.string.densiysemisvalue));
		if (density != null)
		{
			if (!density.equals(""))
			{
				densite.setText(density);
				final int poundVal = Integer.parseInt(densite.getText()
						.toString());
				model.poundChanged(poundVal);
			}
		}
	}

	/*********************************************************************/
	TextWatcher	densityWatcher		= new TextWatcher()
									{
										@Override
										public void afterTextChanged(
												final Editable s)
										{
											if (!densite.getText().toString()
													.equals(""))
											{
												final int poundVal = Integer
														.parseInt(densite
																.getText()
																.toString());
												model.poundChanged(poundVal);
											}
											else
											{
												model.poundChanged(-1);
											}
										}

										@Override
										public void beforeTextChanged(
												final CharSequence arg0,
												final int arg1, final int arg2,
												final int arg3)
										{
										}

										@Override
										public void onTextChanged(
												final CharSequence arg0,
												final int arg1, final int arg2,
												final int arg3)
										{
										}
									};
	/*********************************************************************/
	TextWatcher	ecartementWatcher	= new TextWatcher()
									{
										@Override
										public void afterTextChanged(
												final Editable s)
										{
											if (!ecartement.getText()
													.toString().equals(""))
											{
												final int ecartementVal = Integer
														.parseInt(ecartement
																.getText()
																.toString());
												model.ecartementChanged(ecartementVal);
											}
											else
											{
												model.ecartementChanged(-1);
											}
										}

										@Override
										public void beforeTextChanged(
												final CharSequence arg0,
												final int arg1, final int arg2,
												final int arg3)
										{
										}

										@Override
										public void onTextChanged(
												final CharSequence arg0,
												final int arg1, final int arg2,
												final int arg3)
										{
										}
									};

	/*********************************************************************/
	@Override
	public boolean onCreateOptionsMenu(final Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.plot_control, menu);
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
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	/* _________________________________________________________ */
	/**
	 * @param source
	 * @param info
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(final Observable source, final Object info)
	{
		if (source instanceof ModelControl)
		{
			final String texte = info.toString();
			if (texte.equals(getString(R.string.constante_resultat)))
			{
				resNbGrains.setText(String.valueOf(model.getResultatGrains()));
				resNbRang.setText(String.valueOf(model.getResultatRang()));
				nbGrainsUnit.setVisibility(View.VISIBLE);
				nbRangUnit.setVisibility(View.VISIBLE);
			}
			else if (texte.equals(getString(R.string.constante_desactiver)))
			{
				resNbGrains.setText("");
				resNbRang.setText("");
				nbGrainsUnit.setVisibility(View.INVISIBLE);
				nbRangUnit.setVisibility(View.INVISIBLE);
			}
		}
	}
}
