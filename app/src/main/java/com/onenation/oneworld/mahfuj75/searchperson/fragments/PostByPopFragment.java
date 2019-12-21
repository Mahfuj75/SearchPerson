package com.onenation.oneworld.mahfuj75.searchperson.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onenation.oneworld.mahfuj75.searchperson.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mahfu on 12/29/2016.
 */

public class PostByPopFragment extends DialogFragment {

    String uID;
    static PostByPopFragment f1;


    CircleImageView circleImageViewPostByImage;
    TextView textViewPostByName;
    TextView textViewPostByEmail;
    TextView textViewPostByGander;
    TextView textViewPostByLocation;
    DatabaseReference mRef;


    public static PostByPopFragment newInstance() {
        f1 = new PostByPopFragment();
        f1.setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_DeviceDefault_Dialog);
        return f1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View view = inflater.inflate(R.layout.popup_post_by, container, false);
        uID= f1.getTag().toString();
        circleImageViewPostByImage = (CircleImageView) view.findViewById(R.id.postBy_image);
        textViewPostByName = (TextView) view.findViewById(R.id.postBy_name);
        textViewPostByEmail = (TextView) view.findViewById(R.id.postBy_Email);
        textViewPostByGander = (TextView) view.findViewById(R.id.postBY_Gander);
        textViewPostByLocation = (TextView) view.findViewById(R.id.postBy_Location);

        mRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uID);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String image = dataSnapshot.child("image").getValue().toString();
                String name = dataSnapshot.child("name").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();
                String gander = dataSnapshot.child("gender").getValue().toString();
                String location = dataSnapshot.child("district").getValue().toString()+ " , "+dataSnapshot.child("subDistrict").getValue().toString();


                if(image==null || image.isEmpty())
                {
                    Picasso.with(getActivity()).load(R.drawable.no_image)
                            .error(R.drawable.progress_animation)
                            .into(circleImageViewPostByImage);
                }
                else{
                    Picasso.with(getActivity()).load(image)
                            .error(R.drawable.progress_animation)
                            .into(circleImageViewPostByImage);
                }
                textViewPostByName.setText(name);
                textViewPostByEmail.setText(email);
                textViewPostByGander.setText(gander);
                textViewPostByLocation.setText(location);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        view.findViewById(R.id.popup_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }
}
