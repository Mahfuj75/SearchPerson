package com.onenation.oneworld.mahfuj75.searchperson.custom;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;
import com.onenation.oneworld.mahfuj75.searchperson.R;
import com.onenation.oneworld.mahfuj75.searchperson.activity.PostActivity;
import com.onenation.oneworld.mahfuj75.searchperson.objectclass.MissingPerson;
import com.onenation.oneworld.mahfuj75.searchperson.viewHolder.FoundLostCrimeViewHolder;

/**
 * Created by Mahfuj75 on 3/9/2017.
 */

public class FirebaseAdapterForPost {

    public FirebaseAdapterForPost() {
    }

    public FirebaseRecyclerAdapter getPostAdapter(Query query, final Activity activity)
    {
        FirebaseRecyclerAdapter firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<MissingPerson, FoundLostCrimeViewHolder>(
                MissingPerson.class,
                R.layout.missing_person_row,
                FoundLostCrimeViewHolder.class,
                query
        )  {


            @Override
            public MissingPerson getItem(int position) {

                return super.getItem(getItemCount() - (position + 1));
            }

            protected void populateViewHolder(FoundLostCrimeViewHolder viewHolder, MissingPerson model, int position) {


                position = (getItemCount() - (position + 1));

                viewHolder.setImageUrl(activity,model.getImageUrl());
                viewHolder.setMissingPersonName(model.getMissingPersonName());
                viewHolder.setAge(model.getAge()+ " years old");
                viewHolder.setLastSeenLocation(model.getLocation());
                viewHolder.setLostFound(model.getLostFound());
                viewHolder.setPostTime(model.getPostTime());
                viewHolder.setPostDate(model.getPostDate());
                viewHolder.setMissingDate(model.getMissingDate());


                final int finalPosition = position;
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //Toast.makeText(getContext(),postKey,Toast.LENGTH_LONG).show();
                        Intent i = new Intent(activity, PostActivity.class);
                        i.putExtra("keyOfPost",getRef(finalPosition).getKey());
                        activity.startActivity(i);
                    }
                });
                viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {

                        AlertDialog.Builder myAlert = new AlertDialog.Builder(activity);
                        myAlert.setMessage("Long Click")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .create();
                        myAlert.show();
                        return false;
                    }
                });

            }
        };
        return firebaseRecyclerAdapter;
    }

}
