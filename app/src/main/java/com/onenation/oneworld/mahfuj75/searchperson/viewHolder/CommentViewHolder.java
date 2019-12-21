package com.onenation.oneworld.mahfuj75.searchperson.viewHolder;

import android.content.Context;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.onenation.oneworld.mahfuj75.searchperson.R;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mahfu on 12/14/2016.
 */

public class CommentViewHolder  extends RecyclerView.ViewHolder{

    ProgressBar progressBar;
    View mView;
    CardView cv;


    public CommentViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        cv = (CardView)itemView.findViewById(R.id.cardView_comment);
        cv.setCardBackgroundColor(Color.parseColor("#FFFFFF"));

    }



    public  void  setImageUrl (Context ctx, String imageUrl){

        CircleImageView imageViewImageUrl = (CircleImageView) mView.findViewById(R.id.comment_imageView);
        if(imageUrl==null)
        {

            imageViewImageUrl.setImageResource(R.drawable.anonymous);

        }
        else{
            Picasso.with(ctx).load(imageUrl)
                    .error(R.drawable.progress_animation)
                    .into(imageViewImageUrl);
        }





    }

    public  void  setUserName (String userName){

        TextView textViewUserName = (TextView) mView.findViewById(R.id.comment_userNameTextView );
        if(userName==null)
        {
            textViewUserName.setText("anonymous");
        }
        else {

            textViewUserName.setText(userName);
        }


    }
    public  void  setComment (String comment){

        TextView textViewComment = (TextView) mView.findViewById(R.id.comment_textViewComment );
        textViewComment.setText(comment);

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public  void  setTime (String commentDate, String commentTime){

        TextView textViewTime = (TextView) mView.findViewById(R.id.textViewCommentTime );
        String date = commentDate + " , " + commentTime;
        textViewTime.setText(date);

    }
}
