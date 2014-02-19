package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import persistence.DaoUtils;
import business.Growing;
import business.Plot;
import view.activity.PlotAddActivity;
import view.activity.PlotListActivity;
import fr.iut.licpro.parcelleapp.R;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ModelPlot extends Observable{

	List<Plot> listPlot = new ArrayList<Plot>();


	/**
	 * The constructor of the model.
	 */
	public ModelPlot()
	{}


	/* _________________________________________________________ */
	/**
	 * Method which permit to inform observers
	 */
	private void notifier()
	{
		setChanged();
		notifyObservers();
	}

	/* _________________________________________________________ */
	/**
	 * Method which permit to add an observer.
	 */
	public void sAbonner(Observer obs)
	{
		addObserver(obs);
	}


	/**
	 * Methode qui permet d'ajouter un Plot dans la base de données
	 * @param L'activité parent.
	 */
	public void ajouter(PlotAddActivity plotAddActivity) {
		Spinner spinnerPlot = (Spinner) plotAddActivity.findViewById(R.id.Tv_addPlot);
		Spinner spinnerOldPlot = (Spinner) plotAddActivity.findViewById(R.id.Tv_addOldPlot);
		NumberPicker picker = (NumberPicker) plotAddActivity.findViewById(R.id.pickerSurface);
		TextView name = (TextView) plotAddActivity.findViewById(R.id.Tv_addName);
		ImageView img_preview = (ImageView)plotAddActivity.findViewById(R.id.preview);

		if(!name.getText().toString().equals(""))
		{
			String valuePlot = spinnerPlot.getSelectedItem().toString();
			String valueOldPlot = spinnerOldPlot.getSelectedItem().toString();
			//On peux initialiser a NULL car on est sure de trouver une valeur parmis les Growings grace aux Spinners
			Growing plot = null;
			Growing oldPlot = null;
			for(Growing g : Growing.values())
			{
				if(valuePlot.equals(g.toString()))
					plot=g;
				if(valueOldPlot.equals(g.toString()))
					oldPlot=g;
			}

			String pathImage = "" ;
			try{
				pathImage = String.valueOf(img_preview.getContentDescription());
			}catch(Exception e){
			}

			Plot newPlot = new Plot(name.getText().toString(),plot, oldPlot, picker.getValue(),pathImage);
			listPlot.add(newPlot);
			DaoUtils.storeSingleData(plotAddActivity.getApplicationContext(), newPlot);
			notifier();
			final Intent intent = new Intent(plotAddActivity.getBaseContext(),
					PlotListActivity.class);
			plotAddActivity.startActivity(intent);
		}
		else{
			Toast.makeText(plotAddActivity.getBaseContext(),R.string.le_nom_n_est_pas_renseign_, Toast.LENGTH_LONG).show();
		}

	}
}
