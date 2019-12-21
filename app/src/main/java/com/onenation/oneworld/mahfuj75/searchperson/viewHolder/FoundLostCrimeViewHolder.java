package com.onenation.oneworld.mahfuj75.searchperson.viewHolder;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.onenation.oneworld.mahfuj75.searchperson.R;
import com.squareup.picasso.Picasso;

import java.util.Objects;

/**
 * Created by mahfu on 11/18/2016.
 */

public class FoundLostCrimeViewHolder extends RecyclerView.ViewHolder {


    ProgressBar progressBar;
    View mView;
    CardView cv;


    public FoundLostCrimeViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        cv = (CardView) itemView.findViewById(R.id.cardView_missing_person);
    }

    public void setImageUrl(Context ctx, String imageUrl) {

        ImageView missingPersonImage = (ImageView) mView.findViewById(R.id.missing_person_image);
        missingPersonImage.setMinimumHeight(mView.getHeight());
        missingPersonImage.setMinimumHeight(mView.getHeight());
        if (imageUrl == null) {
            Picasso.with(ctx).load(R.drawable.no_image)
                    .error(R.drawable.image_error)
                    .into(missingPersonImage);
            missingPersonImage.setVisibility(View.GONE);
        } else {
            Picasso.with(ctx).load(imageUrl)
                    .error(R.drawable.image_error)
                    .into(missingPersonImage);
        }


    }

    public void setMissingPersonName(String missingPersonName) {

        TextView MissingPersonName = (TextView) mView.findViewById(R.id.missing_person_name);


        if (Objects.equals(missingPersonName.trim(), "")) {
            MissingPersonName.setText("Unknown");
            MissingPersonName.setTextColor(Color.parseColor("#B71C1C"));
        } else {
            MissingPersonName.setText(missingPersonName);
        }
    }

    public void setPostTime(String postTime) {

        TextView MissingPersonTime = (TextView) mView.findViewById(R.id.missing_person_time);
        MissingPersonTime.setText(postTime);

    }

    public void setPostDate(String postDate) {

        TextView MissingPersonDate = (TextView) mView.findViewById(R.id.missing_person_date);
        MissingPersonDate.setText(postDate);

    }

    public void setAge(String age) {

        TextView MissingPersonAge = (TextView) mView.findViewById(R.id.age_textView);
        MissingPersonAge.setText(age);
    }

    public void setLastSeenLocation(String location) {

        TextView MissingPersonLastSeenLocation = (TextView) mView.findViewById(R.id.textView_lastSeenLocation);

        MissingPersonLastSeenLocation.setText(location);


    }

    public void setLostFound(String lostFound) {


        TextView MissingPersonLostFound = (TextView) mView.findViewById(R.id.lost_found_textView);
        TextView MissingPersonLastSeanDateName = (TextView) mView.findViewById(R.id.textView_lastSeenDateName);
        MissingPersonLostFound.setText(lostFound);
        if (lostFound.matches("Found")) {
            cv.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            MissingPersonLastSeanDateName.setText("Found Date :");

        } else if (lostFound.matches("Lost")) {
            cv.setCardBackgroundColor(Color.parseColor("#FAFAFA"));
            MissingPersonLastSeanDateName.setText("Lost Date :");
        }


    }

    public void setMissingDate(String missingDate) {

        TextView MissingPersonName = (TextView) mView.findViewById(R.id.textView_lastSeenDate);
        MissingPersonName.setText(missingDate);
    }


}
