package view.activity;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import model.ModelOrder;
import persistence.DaoUtils;
import view.adapter.ParcelleItemArrayAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import business.Plot;
import fr.iut.licpro.parcelleapp.R;

public class PlotOrderActivity extends Activity implements Observer,
		OnClickListener, android.content.DialogInterface.OnClickListener
{
	/**
	 * The button control
	 */
	private Button		btn_ctrl;
	/**
	 * The field pound
	 */
	private EditText	pound;
	/**
	 * The field surface
	 */
	private EditText	surface;
	/**
	 * The field conditionment
	 */
	private EditText	conditionment;
	/**
	 * The field result
	 */
	private TextView	unite_Result;
	/**
	 * The model
	 */
	private ModelOrder	model;
	/**
	 * The result
	 */
	private TextView	res;
	/**
	 * The ImageButton
	 */
	private ImageButton	img;
	/**
	 * The AlertDialog
	 */
	private AlertDialog	dialog;
	/**
	 * The density coming from screen1
	 */
	String				densityVal;

	@Override
	protected void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plot_order);
		model = new ModelOrder(this);
		model.sAbonner(this);
		btn_ctrl = (Button) findViewById(R.id.btn_ctrl);
		btn_ctrl.setEnabled(false);
		btn_ctrl.setOnClickListener(this);
		img = (ImageButton) findViewById(R.id.image_plot_context_menu);
		img.setOnClickListener(this);
		unite_Result = (TextView) findViewById(R.id.unite_sacs);
		unite_Result.setVisibility(View.INVISIBLE);
		res = (TextView) findViewById(R.id.resultat_sacs);
		pound = (EditText) findViewById(R.id.densityVal);
		/** On regarde si il existe un intent */
		final Intent intentReceive = getIntent();
		final String value = intentReceive
				.getStringExtra(getString(R.string.resultatconvertervalue));
		final String density = intentReceive
				.getStringExtra(getString(R.string.densiysemisvalue));
		if (value != null)
		{
			if (!value.equals(""))
			{
				pound.setText(value);
				final int poundVal = Integer.parseInt(pound.getText()
						.toString());
				model.poundChanged(poundVal);
			}
		}
		if (density != null)
		{
			if (!density.equals(""))
			{
				densityVal = density;
			}
		}
		pound.addTextChangedListener(poundWatcher);
		/**************************************/
		surface = (EditText) findViewById(R.id.ecartementVal);
		surface.addTextChangedListener(surfaceWatcher);
		conditionment = (EditText) findViewById(R.id.conditionment);
		conditionment.addTextChangedListener(conditionmentWatcher);
		createAlertDialog();
	}

	/*********************************************************************/
	TextWatcher	poundWatcher			= new TextWatcher()
										{
											@Override
											public void afterTextChanged(
													final Editable s)
											{
												if (!pound.getText().toString()
														.equals(""))
												{
													final int poundVal = Integer
															.parseInt(pound
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
													final int arg1,
													final int arg2,
													final int arg3)
											{
											}

											@Override
											public void onTextChanged(
													final CharSequence arg0,
													final int arg1,
													final int arg2,
													final int arg3)
											{
											}
										};
	/*********************************************************************/
	TextWatcher	surfaceWatcher			= new TextWatcher()
										{
											@Override
											public void afterTextChanged(
													final Editable s)
											{
												if (!surface.getText()
														.toString().equals(""))
												{
													final int surfaceVal = Integer
															.parseInt(surface
																	.getText()
																	.toString());
													model.surfaceChanged(surfaceVal);
												}
												else
												{
													model.surfaceChanged(-1);
												}
											}

											@Override
											public void beforeTextChanged(
													final CharSequence arg0,
													final int arg1,
													final int arg2,
													final int arg3)
											{
											}

											@Override
											public void onTextChanged(
													final CharSequence arg0,
													final int arg1,
													final int arg2,
													final int arg3)
											{
											}
										};
	/*********************************************************************/
	TextWatcher	conditionmentWatcher	= new TextWatcher()
										{
											@Override
											public void afterTextChanged(
													final Editable s)
											{
												if (!conditionment.getText()
														.toString().equals(""))
												{
													final int conditionmentVal = Integer
															.parseInt(conditionment
																	.getText()
																	.toString());
													model.conditionmentChanged(conditionmentVal);
												}
												else
												{
													model.conditionmentChanged(-1);
												}
											}

											@Override
											public void beforeTextChanged(
													final CharSequence arg0,
													final int arg1,
													final int arg2,
													final int arg3)
											{
											}

											@Override
											public void onTextChanged(
													final CharSequence arg0,
													final int arg1,
													final int arg2,
													final int arg3)
											{
											}
										};

	/*********************************************************************/
	@Override
	public boolean onCreateOptionsMenu(final Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.plot_order, menu);
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
			case R.id.action_controle:
				intent = new Intent(getBaseContext(), PlotControlActivity.class);
				startActivity(intent);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void update(final Observable source, final Object info)
	{
		if (source instanceof ModelOrder)
		{
			final String texte = info.toString();
			if (texte.equals(getString(R.string.constante_resultat)))
			{
				res.setText(String.valueOf(model.getResultat()));
				unite_Result.setVisibility(View.VISIBLE);
				btn_ctrl.setEnabled(true);
			}
			else if (texte.equals(getString(R.string.constante_desactiver)))
			{
				btn_ctrl.setEnabled(false);
				res.setText("");
				unite_Result.setVisibility(View.INVISIBLE);
			}
		}
	}

	public void createAlertDialog()
	{
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// Set the dialog characteristics
		builder.setTitle(getString(R.string.parcelles));
		final LayoutInflater inflater = getLayoutInflater();
		builder.setView(inflater.inflate(R.layout.activity_plot_list, null));
		final List<Plot> listePlots = DaoUtils.getAllData(
				getApplicationContext(), Plot.class);
		builder.setAdapter(
				new ParcelleItemArrayAdapter(this, listePlots, false), this);
		dialog = builder.create();
	}

	@Override
	public void onClick(final View view)
	{
		if (view.getId() == R.id.btn_ctrl)
		{
			final Intent intent = new Intent(getBaseContext(),
					PlotControlActivity.class);
			if (densityVal != null)
			{
				intent.putExtra(getString(R.string.densiysemisvalue),
						String.valueOf(densityVal));
			}
			startActivity(intent);
		}
		else if (view.getId() == R.id.image_plot_context_menu)
		{
			dialog.show();
		}
	}

	/* _________________________________________________________ */
	/**
	 * @param arg0
	 * @param arg1
	 * @see android.content.DialogInterface.OnClickListener#onClick(android.content.DialogInterface,
	 *      int)
	 */
	@Override
	public void onClick(final DialogInterface dialog, final int rang)
	{
		final List<Plot> listePlots = DaoUtils.getAllData(
				getApplicationContext(), Plot.class);
		surface.setText(String.valueOf(listePlots.get(rang).getSurface()));
	}
}
