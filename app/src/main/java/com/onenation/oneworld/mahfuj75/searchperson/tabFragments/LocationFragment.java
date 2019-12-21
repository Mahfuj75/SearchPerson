package com.onenation.oneworld.mahfuj75.searchperson.tabFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.onenation.oneworld.mahfuj75.searchperson.R;
import com.onenation.oneworld.mahfuj75.searchperson.custom.FirebaseAdapterForPost;
import com.onenation.oneworld.mahfuj75.searchperson.objectclass.MissingPerson;
import com.onenation.oneworld.mahfuj75.searchperson.viewHolder.FoundLostCrimeViewHolder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LocationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LocationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int PLACE_PICKER_REQUEST = 1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    private DatabaseReference mDatabase ;
    private DatabaseReference mCurrentUserDatabase ;
    private Query mQuaryLocation;
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private String location;
    private String subLocation;
    private String loc;
    private FirebaseRecyclerAdapter<MissingPerson,FoundLostCrimeViewHolder> firebaseRecyclerAdapter;
    private FloatingActionMenu floatingActionMenu;
    private TextView textViewFragmentLocation;

    public LocationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LocationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LocationFragment newInstance(String param1, String param2) {
        LocationFragment fragment = new LocationFragment();
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
        mAuth = FirebaseAuth.getInstance();
        //PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Post");
        mDatabase.keepSynced(true);
        if (mAuth.getCurrentUser()!=null)
        {
            mCurrentUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
            //mCurrentUserDatabase.child(mAuth.getCurrentUser().getUid());
            mCurrentUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    location =dataSnapshot.child("location").getValue().toString().trim();
                    mQuaryLocation = mDatabase.orderByChild("location").equalTo(location);
                    mQuaryLocation.keepSynced(true);


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }






    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_location, container, false);
        floatingActionMenu = (FloatingActionMenu) getActivity().findViewById(R.id.floatingActionMenu);
        recyclerView = (RecyclerView) layout.findViewById(R.id.missingPersonRecyclerViewLocationFragment);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(layout.getContext()));
        textViewFragmentLocation = (TextView) layout.findViewById(R.id.textView_fragmentLocation);



        //Toast.makeText(getContext(),location,Toast.LENGTH_LONG).show();
        if(mAuth.getCurrentUser()==null)
        {
            mQuaryLocation = mDatabase.orderByChild("location").equalTo(null);
            mQuaryLocation.keepSynced(true);
        }
        else if(mAuth.getCurrentUser()!=null)
        {
            mQuaryLocation = mDatabase.orderByChild("location").equalTo(location);
            mQuaryLocation.keepSynced(true);
        }

        mQuaryLocation.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()==null)
                {
                    textViewFragmentLocation.setVisibility(View.VISIBLE);
                }
                else {
                    textViewFragmentLocation.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if (dy > 0 && floatingActionMenu.isShown())
                {
                    floatingActionMenu.hideMenu(true);
                }
                else if (dy < 0 && floatingActionMenu.isMenuHidden())
                {
                    floatingActionMenu.showMenu(true);
                }




            }

        });
        recyclerView.setAdapter(new FirebaseAdapterForPost().getPostAdapter(mQuaryLocation,getActivity()));
        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerView.setAdapter(new FirebaseAdapterForPost().getPostAdapter(mQuaryLocation,getActivity()));
        recyclerView.invalidate();
        if(floatingActionMenu.isMenuHidden())
        {
            floatingActionMenu.showMenu(true);
        }
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
