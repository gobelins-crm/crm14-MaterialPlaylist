package crm.gobelins.materialplaylist.songs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.echonest.api.v4.Song;

public class SongsAdapter extends ArrayAdapter<Song> {

    private final LayoutInflater mInflater;

    public SongsAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_2);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (null == convertView) {
            convertView = mInflater.inflate(android.R.layout.simple_list_item_2, parent, false);

            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(android.R.id.text1);
            holder.artistName = (TextView) convertView.findViewById(android.R.id.text2);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Song song = getItem(position);

        holder.title.setText(song.getTitle());
        holder.artistName.setText(song.getArtistName());

        return convertView;
    }

    private static class ViewHolder {
        TextView title;
        TextView artistName;
    }
}
