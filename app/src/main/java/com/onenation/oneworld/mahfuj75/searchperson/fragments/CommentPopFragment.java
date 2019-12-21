package com.onenation.oneworld.mahfuj75.searchperson.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.onenation.oneworld.mahfuj75.searchperson.R;
import com.onenation.oneworld.mahfuj75.searchperson.objectclass.Comment;
import com.onenation.oneworld.mahfuj75.searchperson.viewHolder.CommentViewHolder;

/**
 * Created by mahfu on 12/17/2016.
 */

public class CommentPopFragment extends DialogFragment {

    String post;
    RecyclerView recyclerView;
    static CommentPopFragment f1;
    FirebaseAuth mauth;
    String uid;

    public static CommentPopFragment newInstance() {
        f1 = new CommentPopFragment();
        f1.setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_DeviceDefault_Dialog);
        return f1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Remove the default background


        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        // Inflate the new view with margins and background
        View v = inflater.inflate(R.layout.popup_layout, container, false);

        post= f1.getTag().toString();
        recyclerView = (RecyclerView) v.findViewById(R.id.CommentPopRecycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        commentPopRecycle();


        // Set up a click listener to dismiss the popup if they click outside
        // of the background view
        v.findViewById(R.id.popup_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return v;
    }

    private void commentPopRecycle()
    {
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("Post").child(post).child("Comments");

        FirebaseRecyclerAdapter<Comment,CommentViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Comment, CommentViewHolder>(
                Comment.class,
                R.layout.comment_row,
                CommentViewHolder.class,
                mRef
        ) {

            @Override
            public Comment getItem(int position) {
                return super.getItem(getItemCount() - (position + 1));
            }


            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected void populateViewHolder(CommentViewHolder viewHolder, Comment model, int position) {

                position = (getItemCount() - (position + 1));

                viewHolder.setImageUrl(getActivity().getApplicationContext(),model.getImageUrl());
                viewHolder.setUserName(model.getUserName());
                viewHolder.setComment(model.getComment());
                viewHolder.setTime(model.getCommentDate(),model.getCommentTime());

            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

}