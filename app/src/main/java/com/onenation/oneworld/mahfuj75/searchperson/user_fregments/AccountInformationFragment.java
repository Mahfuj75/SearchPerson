package com.onenation.oneworld.mahfuj75.searchperson.user_fregments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onenation.oneworld.mahfuj75.searchperson.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AccountInformationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AccountInformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountInformationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private String uId;
    private String imageURL;
    private String location;
    private CircleImageView circleImageView;
    private TextView profileName;
    private TextView profileEmail;
    private TextView profileGander;
    private TextView profilePhone;
    private TextView profileLocation;
    private TextView profileDateOfBirth;

    private OnFragmentInteractionListener mListener;

    public AccountInformationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountInformationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountInformationFragment newInstance(String param1, String param2) {
        AccountInformationFragment fragment = new AccountInformationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uId);
        mDatabase.keepSynced(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View layout = inflater.inflate(R.layout.fragment_account_information, container, false);
        circleImageView = (CircleImageView) layout.findViewById(R.id.profile_image);
        profileName = (TextView) layout.findViewById(R.id.profile_name);
        profileEmail = (TextView) layout.findViewById(R.id.profileEmail);
        profileGander = (TextView) layout.findViewById(R.id.profileGander);
        profilePhone = (TextView) layout.findViewById(R.id.profilePhone);
        profileLocation = (TextView) layout.findViewById(R.id.profileLocation);
        final Context c = getActivity().getApplicationContext();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                imageURL = (String) dataSnapshot.child("image").getValue();
                Picasso.with(c).load(imageURL)
                        .error(R.drawable.progress_animation)
                        .into(circleImageView);
                profileName.setText(dataSnapshot.child("name").getValue().toString());
                profileEmail.setText(dataSnapshot.child("email").getValue().toString());
                profileGander.setText(dataSnapshot.child("gender").getValue().toString());
                profilePhone.setText(dataSnapshot.child("phone").getValue().toString());
                if (dataSnapshot.child("subDistrict").getValue() == null) {
                    profileLocation.setText(dataSnapshot.child("district").getValue().toString());
                } else {
                    location = dataSnapshot.child("subDistrict").getValue().toString() + " ," + dataSnapshot.child("district").getValue().toString();
                    profileLocation.setText(location);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return layout;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
