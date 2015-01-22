package crm.gobelins.materialplaylist.artists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.echonest.api.v4.Artist;
import com.echonest.api.v4.EchoNestException;

import crm.gobelins.materialplaylist.R;

public class ArtistsAdapter extends ArrayAdapter<Artist> {
    private final LayoutInflater mInflater;

    public ArtistsAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_1);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (null == convertView) {
            convertView = mInflater.inflate(android.R.layout.simple_list_item_1, null, false);

            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(android.R.id.text1);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Artist artist = getItem(position);
        String name;

        try {
            name = artist.getName();
        } catch (EchoNestException e) {
            name = getContext().getString(R.string.artist_anonymous);
        }

        holder.name.setText(name);

        return convertView;
    }

    private static class ViewHolder {
        TextView name;
    }
}
