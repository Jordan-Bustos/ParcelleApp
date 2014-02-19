package persistence;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import fr.iut.licpro.parcelleapp.R;

public class DaoUtils {
	/* _________________________________________________________ */
	/**
	 * @param pContext
	 *            Le context
	 * @param pClass
	 *            Le type de la classe concerné.
	 * @return La liste de données.
	 */
	public static <Plot> List<Plot> getAllData(final Context pContext,
			final Class<Plot> pClass)
			{
		List<Plot> ret = null;
		final SQLiteOpenHelper db = DatabaseHelper.getInstance(pContext);
		final ConnectionSource connectionSource = new AndroidConnectionSource(
				db);
		try
		{
			@SuppressWarnings("unchecked")
			final Dao<Plot, Integer> dao = (Dao<Plot, Integer>) DaoManager
			.createDao(connectionSource, pClass);
			if (dao != null)
			{
				ret = dao.queryForAll();
			}
		}
		catch (final SQLException e)
		{
			Toast.makeText(pContext,
					R.string.erreur_de_connexion_avec_la_base_de_donn_es,
					Toast.LENGTH_SHORT).show();
		}
		finally
		{
			connectionSource.closeQuietly();
		}
		return ret;
			}


	@SuppressWarnings({ "unchecked"})
	public static <Plot> void storeSingleData(final Context pContext, final Plot pData) {
		Class<?> type = pData.getClass();
		SQLiteOpenHelper db = DatabaseHelper.getInstance(pContext);
		ConnectionSource connectionSource = new AndroidConnectionSource(db);
		Dao<Plot, Integer> dao = null;
		try 
		{
			dao = (Dao<Plot, Integer>) DaoManager.createDao(connectionSource, type);
			if (dao != null)
			{
				dao.create(pData);
			}
		} 
		catch (SQLException e)  { }
		finally 
		{
			connectionSource.closeQuietly();
		}
	}
}
