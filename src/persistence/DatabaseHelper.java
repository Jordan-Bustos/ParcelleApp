/* _________________________________________________________ */
/* _________________________________________________________ */
/**
 * Fichier : DatabaseHelper.java
 * 
 * Créé le 29 janv. 2014 à 08:48:13
 * 
 * Auteur : Jerome POINAS
 */
package persistence;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import business.Plot;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import fr.iut.licpro.parcelleapp.R;

/* _________________________________________________________ */
/**
 * @author Jerome POINAS
 *         La classe DatabaseHelper.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper
{
	/**
	 * L'instance unique de la DatabaseHelper
	 */
	private static DatabaseHelper	instance;
	/**
	 * Le context
	 */
	private final Context			context;

	/* _________________________________________________________ */
	/**
	 * @param context
	 *            Le context
	 * @return Instance unique
	 */
	public static DatabaseHelper getInstance(final Context context)
	{
		if (instance == null)
		{
			instance = new DatabaseHelper(context);
		}
		return instance;
	}

	/* _________________________________________________________ */
	/**
	 * @param pContext
	 *            Le context.
	 */
	private DatabaseHelper(final Context pContext)
	{
		super(pContext, "PLOT_DATABASE", null, 3);
		context = pContext;
		if (instance == null)
		{
			instance = this;
		}
	}

	/* _________________________________________________________ */
	/**
	 * @param pDb
	 *            La base de données.
	 * @param pConnectionSource
	 *            La connection source.
	 * @see com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase,
	 *      com.j256.ormlite.support.ConnectionSource)
	 */
	@Override
	public void onCreate(final SQLiteDatabase pDb,
			final ConnectionSource pConnectionSource)
	{
		try
		{
			TableUtils.createTableIfNotExists(pConnectionSource, Plot.class);
		}
		catch (final SQLException e)
		{
			Toast.makeText(context,
					R.string.erreur_de_creation_dans_la_base_de_donn_es,
					Toast.LENGTH_SHORT).show();
		}
	}

	/* _________________________________________________________ */
	/**
	 * @param pDb
	 *            La base de données
	 * @param pConnectionSource
	 *            La connection source
	 * @param pOldVersion
	 *            La version anterieur
	 * @param pNewVersion
	 *            La nouvelle version
	 * @see com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase,
	 *      com.j256.ormlite.support.ConnectionSource, int, int)
	 */
	@Override
	public void onUpgrade(final SQLiteDatabase pDb,
			final ConnectionSource pConnectionSource, final int pOldVersion,
			final int pNewVersion)
	{
		try
		{
			TableUtils.dropTable(pConnectionSource, Plot.class, true);
			onCreate(pDb, pConnectionSource);
		}
		catch (final SQLException e)
		{
			Toast.makeText(
					context,
					R.string.erreur_de_suppression_de_table_dans_la_base_de_donn_es,
					Toast.LENGTH_SHORT).show();
		}
	}
}
/* _________________________________________________________ */
/*
 * Fin du fichier DatabaseHelper.java.
 * /*_________________________________________________________
 */
