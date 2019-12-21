package com.onenation.oneworld.mahfuj75.searchperson.viewHolder;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.onenation.oneworld.mahfuj75.searchperson.R;
import com.squareup.picasso.Picasso;

/**
 * Created by mahfuj75 on 12/5/16.
 */

public class UserPostViewHolder extends RecyclerView.ViewHolder {

    ProgressBar progressBar;
    View mView;
    CardView cv;


    public UserPostViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        cv = (CardView) itemView.findViewById(R.id.cardView_userPost);
    }

    public void setImage(Context ctx, String imageUrl) {

        ImageView MissingPersonImage = (ImageView) mView.findViewById(R.id.userPostImage);
        //Picasso.with(ctx).load(image).into(MissingPersonImage);

        if (imageUrl == null) {
            Picasso.with(ctx).load(R.drawable.no_image)
                    .error(R.drawable.progress_animation)
                    .into(MissingPersonImage);
        } else {
            Picasso.with(ctx).load(imageUrl)
                    .error(R.drawable.progress_animation)
                    .into(MissingPersonImage);
        }


    }

    public void setName(String missingPersonName) {
        TextView MissingPersonName = (TextView) mView.findViewById(R.id.user_missing_post_name_textView);

        if (missingPersonName.trim().length() == 0) {
            MissingPersonName.setTextColor(Color.parseColor("#C62828"));
            MissingPersonName.setText("Unknown");
        } else {
            MissingPersonName.setText(missingPersonName);
        }


    }

    public void setDate(String postDate) {

        TextView MissingPersonDate = (TextView) mView.findViewById(R.id.user_missing_post_date_textView);
        MissingPersonDate.setText(postDate);

    }

    public void setAge(String age) {

        TextView MissingPersonAge = (TextView) mView.findViewById(R.id.age_textView_user_post);
        MissingPersonAge.setText(age);
    }

    public void setLastSeenLocation(String district, String subDistrict) {

        TextView MissingPersonLastSeenLocation = (TextView) mView.findViewById(R.id.textView_lastSeenLocation_user_post);
        int length = district.length() + subDistrict.length();
        if (subDistrict.equals("All") || length > 15) {
            MissingPersonLastSeenLocation.setText(district);
        } else {
            MissingPersonLastSeenLocation.setText(district + ", " + subDistrict);
        }

    }

    public void setLostFound(String lostFound) {


        TextView MissingPersonLostFound = (TextView) mView.findViewById(R.id.user_missing_post_FL_textView);
        MissingPersonLostFound.setText(lostFound);
        /*if (lostFound.matches("Found")) {
            cv.setCardBackgroundColor(Color.parseColor("#FAFAFA"));
        } else if (lostFound.matches("Lost")) {
            cv.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        }*/


    }

}
