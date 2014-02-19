package view.activity;

import java.util.Observable;
import java.util.Observer;
import model.ModelConverter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import fr.iut.licpro.parcelleapp.R;

public class PlotConverterActivity extends Activity implements Observer,
		OnClickListener
{
	/**
	 * Density
	 */
	private EditText		densite;
	/**
	 * PMG
	 */
	private EditText		pmg;
	/**
	 * Text view of the unit of the result.
	 */
	private TextView		unite_Result;
	/**
	 * Button of the cmd.
	 */
	private Button			btn_order;
	/**
	 * The model
	 */
	private ModelConverter	model;
	/**
	 * The result
	 */
	private TextView		res;

	@Override
	protected void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plot_converter);
		btn_order = (Button) findViewById(R.id.btn_order);
		btn_order.setEnabled(false);
		btn_order.setOnClickListener(this);
		densite = (EditText) findViewById(R.id.densityVal);
		pmg = (EditText) findViewById(R.id.ecartementVal);
		densite.addTextChangedListener(densityWatcher);
		pmg.addTextChangedListener(pgmWatcher);
		unite_Result = (TextView) findViewById(R.id.unite_resultat);
		unite_Result.setVisibility(View.INVISIBLE);
		res = (TextView) findViewById(R.id.resultat);
		model = new ModelConverter(this);
		model.sAbonner(this);
	}

	/*********************************************************************/
	TextWatcher	densityWatcher	= new TextWatcher()
								{
									@Override
									public void afterTextChanged(
											final Editable s)
									{
										if (!densite.getText().toString()
												.equals(""))
										{
											final int density = Integer
													.parseInt(densite.getText()
															.toString());
											model.densityChanged(density);
										}
										else
										{
											model.densityChanged(-1);
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
	TextWatcher	pgmWatcher		= new TextWatcher()
								{
									@Override
									public void afterTextChanged(
											final Editable s)
									{
										if (!pmg.getText().toString()
												.equals(""))
										{
											final int pmgVal = Integer
													.parseInt(pmg.getText()
															.toString());
											model.pmgChanged(pmgVal);
										}
										else
										{
											model.pmgChanged(-1);
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
		getMenuInflater().inflate(R.menu.plot_converter, menu);
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
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(final Observable source, final Object info)
	{
		if (source instanceof ModelConverter)
		{
			final String texte = info.toString();
			if (texte.equals(getString(R.string.constante_resultat)))
			{
				res.setText(String.valueOf(model.getResultat()));
				unite_Result.setVisibility(View.VISIBLE);
				btn_order.setEnabled(true);
			}
			else if (texte.equals(getString(R.string.constante_desactiver)))
			{
				btn_order.setEnabled(false);
				res.setText("");
				unite_Result.setVisibility(View.INVISIBLE);
			}
		}
	}

	@Override
	public void onClick(final View view)
	{
		if (view.getId() == R.id.btn_order)
		{
			final Intent intent = new Intent(getBaseContext(),
					PlotOrderActivity.class);
			intent.putExtra(getString(R.string.resultatconvertervalue),
					String.valueOf(model.getResultat()));
			intent.putExtra(getString(R.string.densiysemisvalue),
					String.valueOf(densite.getText().toString()));
			startActivity(intent);
		}
	}
}
