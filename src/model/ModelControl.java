/* _________________________________________________________ */
/* _________________________________________________________ */
/**
 * Fichier : ModelControl.java
 * 
 * Créé le 12 févr. 2014 à 08:19:01
 * 
 * Auteur : Jerome POINAS
 */
package model;

import java.util.Observable;
import java.util.Observer;
import android.app.Activity;
import fr.iut.licpro.parcelleapp.R;

/* _________________________________________________________ */
/**
 * @author Jerome POINAS
 * 
 */
public class ModelControl extends Observable
{
	/** The density */
	private int				pound		= -1;
	/** The ecartement */
	private int				ecartement	= -1;
	/** The activity */
	private final Activity	activity;
	/** The resultat grains */
	private int				resultatGrains;
	/** The resultat rang */
	private int				resultatRang;

	public ModelControl(final Activity activity)
	{
		this.activity = activity;
	}

	public void GrainsNeeded()
	{
		resultatGrains = Math.round(pound / ecartement);
		RangNeeded();
		notifier(activity.getString(R.string.constante_resultat));
	}

	public void RangNeeded()
	{
		if (resultatGrains != 0)
		{
			resultatRang = Math.round(pound / resultatGrains);
			notifier(activity.getString(R.string.constante_resultat));
		}
	}

	/* _________________________________________________________ */
	/**
	 * Method which permit to inform observers
	 * 
	 * @param info
	 *            the information to send
	 */
	private void notifier(final String info)
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

	public void poundChanged(final int pound)
	{
		this.pound = pound;
		if (isConvertible())
		{
			GrainsNeeded();
		}
		else
		{
			notifier(activity.getString(R.string.constante_desactiver));
		}
	}

	public void ecartementChanged(final int ecartement)
	{
		this.ecartement = ecartement;
		if (isConvertible())
		{
			GrainsNeeded();
		}
		else
		{
			notifier(activity.getString(R.string.constante_desactiver));
		}
	}

	public boolean isConvertible()
	{
		if ((pound != -1) && (ecartement != -1))
		{
			return true;
		}
		return false;
	}

	public int getResultatGrains()
	{
		return resultatGrains;
	}

	public int getResultatRang()
	{
		return resultatRang;
	}
}
/* _________________________________________________________ */
/*
 * Fin du fichier ModelControl.java.
 * /*_________________________________________________________
 */
