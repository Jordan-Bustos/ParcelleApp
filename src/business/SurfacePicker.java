/* _________________________________________________________ */
/* _________________________________________________________ */
/**
 * Fichier : DialogDistance.java
 * 
 * Créé le 9 déc. 2013 à 15:32:00
 * 
 * Auteur : Jerome POINAS
 */
package business;

/* _________________________________________________________ */
/**
 * The Class DialogDistance.
 * 
 * @author Jerome POINAS
 */
public class SurfacePicker
{
	/** The tab valeurs. */
	private final String[]	values	= new String[20];

	/* _________________________________________________________ */
	/**
	 * Instantiates a new SurfacePicker.
	 */
	public SurfacePicker()
	{
		for (int i = 0; i < 20; i++)
		{
			values[i] = String.valueOf(i);
		}
	}

	/* _________________________________________________________ */
	/**
	 * Gets the value at specified index.
	 * 
	 * @param index
	 *            the index
	 * @return the string value
	 */
	public String get(final int index)
	{
		return values[index];
	}

	/* _________________________________________________________ */
	/**
	 * Gets the values.
	 * 
	 * @return data in values tab.
	 */
	public String[] getValues()
	{
		return values;
	}

}
/* _________________________________________________________ */
/*
 * Fin du fichier DialogDistance.java.
 * /*_________________________________________________________
 */
