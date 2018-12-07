package edu.haverford.cs.squirrelfacts;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * An adapter class that allows us to take an ArrayList of Squirrels and display them into a nice
 * view we can render.
 */
public class SquirrelArrayAdapter extends ArrayAdapter<Squirrel> {
    private ArrayList<Squirrel> mSquirrels;

    public SquirrelArrayAdapter(@NonNull Context context, ArrayList<Squirrel> squirrels) {
        super(context, 0, squirrels);
        mSquirrels = squirrels;
    }

    /**
     * Called by the Android framework to populate a view with the data from the given index
     * @param position Position in the underlying arraylist {mSquirrels} for the data
     * @param view The view to be populated. This is an instance of the View corresponding to the
     *             squittel_item_template resource.
     * @param parent The parent viewgroup under which this view sits.
     * @return The view after having made the modifications to populate it with the data.
     */
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final Squirrel s = mSquirrels.get(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.squirrel_item_template, parent, false);
        }
        TextView name = (TextView) view.findViewById(R.id.squirrelName);
        TextView location = (TextView) view.findViewById(R.id.squirrelLocation);
        name.setText(s.getName());
        location.setText(s.getLocation());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), SquirrelInfoActivity.class);
                i.putExtra("name", s.getName());
                i.putExtra("location", s.getLocation());
                i.putExtra("picture", s.getPicture());
                getContext().startActivity(i);
            }
        });
        return view;
    }
}
