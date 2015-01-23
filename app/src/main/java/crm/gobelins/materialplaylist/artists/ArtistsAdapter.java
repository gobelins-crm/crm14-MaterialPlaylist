package crm.gobelins.materialplaylist.artists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.echonest.api.v4.Artist;
import com.echonest.api.v4.EchoNestException;
import com.squareup.picasso.Picasso;

import crm.gobelins.materialplaylist.R;

public class ArtistsAdapter extends ArrayAdapter<Artist> {
    private final LayoutInflater mInflater;

    public ArtistsAdapter(Context context) {
        super(context, R.layout.list_item_artist);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.list_item_artist, parent, false);

            holder = new ViewHolder();
            holder.artistName = (TextView) convertView.findViewById(R.id.list_item_artist_name);
            holder.image = (ImageView) convertView.findViewById(R.id.list_item_artist_image);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Artist artist = getItem(position);
        String name;
        String imageUrl;

        try {
            imageUrl = artist.getImages().get(0).getURL();
        } catch (EchoNestException e) {
            e.printStackTrace();
            imageUrl = null;
        }

        try {
            name = artist.getName();
        } catch (EchoNestException e) {
            e.printStackTrace();
            name = getContext().getString(R.string.artist_anonymous);
        }

        holder.artistName.setText(name);
        if (null != imageUrl) {
            Picasso.with(getContext())
                    .load(imageUrl)
                    .resize(100, 100)
                    .centerInside()
                    .into(holder.image);
        } else {
            holder.image.setImageBitmap(null);
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView artistName;
        ImageView image;
    }
}
