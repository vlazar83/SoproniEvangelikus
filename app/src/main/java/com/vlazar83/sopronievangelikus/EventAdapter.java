package com.vlazar83.sopronievangelikus;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class EventAdapter extends ArrayAdapter<Event> {

    public EventAdapter(Activity context, ArrayList<Event> repositories) {

        super(context, 0, repositories);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.eventlist_item, parent, false);
        }

        Event currentEvent = getItem(position);

        TextView repositoryTextView = (TextView) listItemView.findViewById(R.id.event_name);

        repositoryTextView.setText(currentEvent.getName());

        TextView fullNameTextView = (TextView) listItemView.findViewById(R.id.event_full_name);

        fullNameTextView.setText(currentEvent.getFullName());

        ImageView imageViewOfEvent = (ImageView) listItemView.findViewById(R.id.imageViewOfEventListItem);
        imageViewOfEvent.setImageURI(null);
        imageViewOfEvent.setImageURI(Utils.createUriForImages(currentEvent.getEventImage()));

        ImageView imageViewOfLanguage = (ImageView) listItemView.findViewById(R.id.imageViewOfLanguage);
        imageViewOfLanguage.setImageURI(null);
        String language = currentEvent.getLanguage();
        if(language.equals("Hungarian")){
            imageViewOfLanguage.setImageURI(Utils.createUriForImages("/drawable/hungary_flag"));
        } else if (language.equals("German")){
            imageViewOfLanguage.setImageURI(Utils.createUriForImages("/drawable/germany_flag"));
        }

        return listItemView;
    }
}
