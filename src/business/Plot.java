/* _________________________________________________________ */
/* _________________________________________________________ */
/**
 * Fichier : Plot.java
 * 
 * Créé le 29 janv. 2014 à 08:21:10
 * 
 * Auteur : Jerome POINAS
 */
package business;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/* _________________________________________________________ */
/**
 * @author Jerome POINAS
 * 
 */
@DatabaseTable(tableName = "plot")
public class Plot
{
	/**
	 * L'id de la parcelle.
	 */
	@DatabaseField(generatedId = true)
	private long	id;
	/**
	 * Le nom de la parcelle.
	 */
	@DatabaseField
	private String	Name;
	/**
	 * La growing de la parcelle.
	 */
	@DatabaseField
	private Growing	growing;
	/**
	 * La growing précédente de la parcelle.
	 */
	@DatabaseField
	private Growing	last_growing;
	/**
	 * La surface de la growing.
	 */
	@DatabaseField
	private int		surface;
	/**
	 * L'URL de l'image.
	 */
	@DatabaseField
	private String	image;
	/**
	 * La latitude du Plot.
	 */
	private double	latitude;
	/**
	 * La longitude du Plot.
	 */
	private double	longitude;
	/**
	 * L'adresse du Plot.
	 */
	private String	address;

	/**
	 * Constructeur complet d'une parcelle.
	 * 
	 * @param name
	 *            Le nom de la parcelle.
	 * @param growing
	 *            La growing de la parcelle.
	 * @param last_growing
	 *            La growing précendente de la parcelle.
	 * @param surface
	 *            La surface de la parcelle.
	 */
	public Plot(final String name, final Growing growing,
			final Growing last_growing, final int surface, final String pathImage)
	{
		setName(name);
		setGrowing(growing);
		setLast_growing(last_growing);
		setSurface(surface);
		setImage(pathImage);
	}

	/* _________________________________________________________ */
	/**
	 * Constructeur par défaut d'une parcelle.
	 */
	public Plot()
	{
	}

	/* _________________________________________________________ */
	/**
	 * Retourne la valeur du champ name.
	 * 
	 * @return la valeur du champ name.
	 */
	public String getName()
	{
		return Name;
	}

	/* _________________________________________________________ */
	/**
	 * Modifie la valeur du cmap name.
	 * 
	 * @param name
	 *            la valeur à placer dans le champ name.
	 */
	private void setName(final String name)
	{
		if (name != null)
		{
			Name = name;
		}
	}

	/* _________________________________________________________ */
	/**
	 * Retourne la valeur du champ growing.
	 * 
	 * @return la valeur du champ growing.
	 */
	public Growing getCulture()
	{
		return growing;
	}

	/* _________________________________________________________ */
	/**
	 * Modifie la valeur du cmap growing.
	 * 
	 * @param growing
	 *            la valeur à placer dans le champ growing.
	 */
	private void setGrowing(final Growing growing)
	{
		if (growing != null)
		{
			this.growing = growing;
		}
	}

	/* _________________________________________________________ */
	/**
	 * Retourne la valeur du champ last_growing.
	 * 
	 * @return la valeur du champ last_growing.
	 */
	public Growing getLast_growing()
	{
		return last_growing;
	}

	/* _________________________________________________________ */
	/**
	 * Modifie la valeur du cmap last_growing.
	 * 
	 * @param last_growing
	 *            la valeur à placer dans le champ last_growing.
	 */
	private void setLast_growing(final Growing last_growing)
	{
		if (last_growing != null)
		{
			this.last_growing = last_growing;
		}
	}

	/* _________________________________________________________ */
	/**
	 * Retourne la valeur du champ surface.
	 * 
	 * @return la valeur du champ surface.
	 */
	public int getSurface()
	{
		return surface;
	}

	/* _________________________________________________________ */
	/**
	 * Modifie la valeur du cmap surface.
	 * 
	 * @param surface
	 *            la valeur à placer dans le champ surface.
	 */
	private void setSurface(final int surface)
	{
		if (surface >= 0)
		{
			this.surface = surface;
		}
	}

	/* _________________________________________________________ */
	/**
	 * Retourne la valeur du champ id.
	 * 
	 * @return la valeur du champ id.
	 */
	public long getId()
	{
		return id;
	}

	/* _________________________________________________________ */
	/**
	 * Retourne la valeur du champ image.
	 * 
	 * @return la valeur du champ image.
	 */
	public String getImage()
	{
		return image;
	}

	/* _________________________________________________________ */
	/**
	 * Modifie la valeur du cmap image.
	 * 
	 * @param image
	 *            la valeur à placer dans le champ image.
	 */
	public void setImage(final String image)
	{
		this.image = image;
	}

	/* _________________________________________________________ */
	/**
	 * Retourne la valeur du champ latitude.
	 * 
	 * @return la valeur du champ latitude.
	 */
	public double getLatitude()
	{
		return latitude;
	}

	/* _________________________________________________________ */
	/**
	 * Modifie la valeur du cmap latitude.
	 * 
	 * @param latitude
	 *            la valeur à placer dans le champ latitude.
	 */
	public void setLatitude(final double latitude)
	{
		this.latitude = latitude;
	}

	/* _________________________________________________________ */
	/**
	 * Retourne la valeur du champ longitude.
	 * 
	 * @return la valeur du champ longitude.
	 */
	public double getLongitude()
	{
		return longitude;
	}

	/* _________________________________________________________ */
	/**
	 * Modifie la valeur du cmap longitude.
	 * 
	 * @param longitude
	 *            la valeur à placer dans le champ longitude.
	 */
	public void setLongitude(final double longitude)
	{
		this.longitude = longitude;
	}

	/* _________________________________________________________ */
	/**
	 * Retourne la valeur du champ address.
	 * 
	 * @return la valeur du champ address.
	 */
	public String getAddress()
	{
		return address;
	}

	/* _________________________________________________________ */
	/**
	 * Modifie la valeur du cmap address.
	 * 
	 * @param address
	 *            la valeur à placer dans le champ address.
	 */
	public void setAddress(final String address)
	{
		this.address = address;
	}

	@Override
	public boolean equals(final Object other)
	{
		Plot otherPlot = null;
		try
		{
			otherPlot = (Plot) other;
		}
		catch (final Exception e)
		{
		}
		return id == otherPlot.getId();
	}
}
/* _________________________________________________________ */
/*
 * Fin du fichier Plot.java.
 * /*_________________________________________________________
 */
