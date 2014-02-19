/* _________________________________________________________ */
/* _________________________________________________________ */
/**
 * Fichier : ModelConverter.java
 * 
 * Créé le 5 févr. 2014 à 09:40:37
 * 
 * Auteur : Jerome POINAS
 */
package model;

import java.util.Observable;
import java.util.Observer;

import fr.iut.licpro.parcelleapp.R;
import android.app.Activity;

/* _________________________________________________________ */
/**
 * @author Jerome POINAS
 * 
 */
public class ModelOrder extends Observable
{
	/** The density */
	private int	pound=-1;
	/** The pmg */
	private int	surface=-1;
	/** The conditionment */
	private int	conditionment=-1;
	/** The activity */
	private Activity activity;
	/** The resultat */
	private int resultat;

	public ModelOrder(Activity activity)
	{
		this.activity = activity;
	}

	public void bagsNeeded()
	{
		resultat = Math.round((pound * surface)/conditionment);
		notifier(activity.getString(R.string.constante_resultat));
	}

	/* _________________________________________________________ */
	/**
	 * Method which permit to inform observers
	 * @param info the information to send
	 */
	private void notifier(String info)
	{
		setChanged();
		notifyObservers(info);
	}

	/* _________________________________________________________ */
	/**
	 * Method which permit to add an observer.
	 */
	public void sAbonner(final Observer obs)
	{
		addObserver(obs);
	}

	public void poundChanged(int pound) {
		this.pound = pound;
		if(isConvertible())
			bagsNeeded();
		else
		{
			notifier(activity.getString(R.string.constante_desactiver));
		}
	}

	public void surfaceChanged(int surface) {
		this.surface = surface ;
		if(isConvertible())
			bagsNeeded();

		else{
			notifier(activity.getString(R.string.constante_desactiver));
		}
	}
	
	public void conditionmentChanged(int conditionment) {
		this.conditionment = conditionment ;
		if(isConvertible())
			bagsNeeded();

		else{
			notifier(activity.getString(R.string.constante_desactiver));
		}
	}

	public boolean isConvertible()
	{
		if(pound!=-1 && surface !=-1 && conditionment!=-1)
		{
			return true;
		}
		return false;
	}

	public int getResultat() {
		return resultat;
	}
}
/* _________________________________________________________ */
/*
 * Fin du fichier ModelConverter.java.
 * /*_________________________________________________________
 */
