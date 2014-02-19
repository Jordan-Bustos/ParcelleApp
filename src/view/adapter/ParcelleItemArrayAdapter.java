/* _________________________________________________________ */
/* _________________________________________________________ */
/**
 * Fichier : ParcelleItemArrayAdapter.java
 * 
 * Créé le 29 janv. 2014 à 09:32:24
 * 
 * Auteur : Jerome POINAS
 */
package view.adapter;

import java.util.List;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import business.Plot;
import business.ViewHolder;
import fr.iut.licpro.parcelleapp.R;

/* _________________________________________________________ */
/**
 * @author Jerome POINAS
 * 
 */
public class ParcelleItemArrayAdapter extends ArrayAdapter<Plot>
{
	/**
	 * Le context.
	 */
	private final Context		mContext;
	private final List<Plot>	mValues;
	private final Boolean		isListActivity;

	/* _________________________________________________________ */
	/**
	 * @param context
	 *            Le context
	 * @param values
	 *            La liste de Plot
	 */
	public ParcelleItemArrayAdapter(final Context context,
			final List<Plot> values, final Boolean isListActivity)
	{
		super(context, R.layout.growing_item, values);
		mContext = context;
		mValues = values;
		this.isListActivity = isListActivity;
	}

	/* _________________________________________________________ */
	/**
	 * @return
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount()
	{
		return mValues.size();
	}

	/* _________________________________________________________ */
	/**
	 * @param location
	 * @return
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Plot getItem(final int location)
	{
		return mValues.get(location);
	}

	/* _________________________________________________________ */
	/**
	 * @param location
	 * @return
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(final int location)
	{
		return mValues.get(location).getId();
	}

	/* _________________________________________________________ */
	/**
	 * @param arg0
	 * @return
	 * @see android.widget.Adapter#getItemViewType(int)
	 */
	@Override
	public int getItemViewType(final int arg0)
	{
		return 0;
	}

	/* _________________________________________________________ */
	/**
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return convertView
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 *      android.view.ViewGroup)
	 */
	@Override
	public View getView(final int position, View convertView,
			final ViewGroup parent)
	{
		ViewHolder holder;
		if (convertView == null)
		{
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.growing_item, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.growing = (TextView) convertView.findViewById(R.id.growing);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		final Plot s = mValues.get(position);
		holder.name.setText(s.getName());
		if (isListActivity)
		{
			holder.growing.setText(s.getCulture().toString());
		}
		else
		{
			holder.growing.setText(s.getSurface() + " hectares");
		}
		return convertView;
	}

	/* _________________________________________________________ */
	/**
	 * @return
	 * @see android.widget.Adapter#getViewTypeCount()
	 */
	@Override
	public int getViewTypeCount()
	{
		return 1;
	}

	/* _________________________________________________________ */
	/**
	 * @return
	 * @see android.widget.Adapter#hasStableIds()
	 */
	@Override
	public boolean hasStableIds()
	{
		return true;
	}

	/* _________________________________________________________ */
	/**
	 * @return
	 * @see android.widget.Adapter#isEmpty()
	 */
	@Override
	public boolean isEmpty()
	{
		return mValues.isEmpty();
	}

	/* _________________________________________________________ */
	/**
	 * @param arg0
	 * @see android.widget.Adapter#registerDataSetObserver(android.database.DataSetObserver)
	 */
	@Override
	public void registerDataSetObserver(final DataSetObserver arg0)
	{
	}

	/* _________________________________________________________ */
	/**
	 * @param arg0
	 * @see android.widget.Adapter#unregisterDataSetObserver(android.database.DataSetObserver)
	 */
	@Override
	public void unregisterDataSetObserver(final DataSetObserver arg0)
	{
	}

	/* _________________________________________________________ */
	/**
	 * @return
	 * @see android.widget.ListAdapter#areAllItemsEnabled()
	 */
	@Override
	public boolean areAllItemsEnabled()
	{
		return true;
	}

	/* _________________________________________________________ */
	/**
	 * @param position
	 * @return
	 * @see android.widget.ListAdapter#isEnabled(int)
	 */
	@Override
	public boolean isEnabled(final int position)
	{
		return true;
	}
}
/* _________________________________________________________ */
/*
 * Fin du fichier ParcelleItemArrayAdapter.java.
 * /*_________________________________________________________
 */
