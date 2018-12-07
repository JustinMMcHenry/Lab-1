package edu.haverford.cs.squirrelfacts;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

/**
 * Communicates between list view and squirrel list. Keeps things kosher.
 */
public class SquirrelListAdapter extends BaseAdapter implements ListAdapter {

    private Context mContext;
    private SquirrelList mList;

    public SquirrelListAdapter(Context context, SquirrelList sl) {
        super();
        mContext = context;
        mList = sl;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int i) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        mList.addObserver(dataSetObserver);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        mList.removeObserver(dataSetObserver);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Squirrel getItem(int i) {
        return mList.getItem(i);
    }

    @Override
    public long getItemId(int i) {
        return getItem(i).getId();//should be same as i
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * Systematically called by android to return elements of the list...
     * @param i
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //Careful

        final Squirrel s = mList.getItem(i);
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.squirrel_item_template, viewGroup, false);
        }
        TextView name = (TextView) view.findViewById(R.id.squirrelName);
        TextView location = (TextView) view.findViewById(R.id.squirrelLocation);
        name.setText(s.getName());
        location.setText(s.getLocation());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(mContext, SquirrelInfoActivity.class);
                it.putExtra("name", s.getName());
                it.putExtra("location", s.getLocation());
                it.putExtra("picture", s.getPicture());
                mContext.startActivity(it);
            }
        });
        return view;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return getCount()==0;
    }
}
