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
public class ModelConverter extends Observable
{
	/** The density */
	private int	density=-1;
	/** The pmg */
	private int	pmg=-1;
	/** The activity */
	private Activity activity;
	/** The resultat */
	private int resultat;

	public ModelConverter(Activity activity)
	{
		this.activity = activity;
	}

	public void semenceNeeded()
	{
		resultat = Math.round(density * (pmg / 100));
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

	public void densityChanged(int density) {
		this.density = density;
		if(isConvertible())
			semenceNeeded();
		else
		{
			notifier(activity.getString(R.string.constante_desactiver));
		}
	}

	public void pmgChanged(int pmg) {
		this.pmg = pmg ;
		if(isConvertible())
			semenceNeeded();

		else{
			notifier(activity.getString(R.string.constante_desactiver));
		}

	}

	public boolean isConvertible()
	{
		if(density!=-1 && pmg !=-1)
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
