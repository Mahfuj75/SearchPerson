package com.onenation.oneworld.mahfuj75.searchperson.tabFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
 * {@link FoundFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FoundFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoundFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private DatabaseReference mDatabase ;
    private DatabaseReference mDatabase2 ;
    private Query mQuaryFound;
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private String postKey;
    private FloatingActionMenu floatingActionMenu;

    private FirebaseRecyclerAdapter<MissingPerson,FoundLostCrimeViewHolder> firebaseRecyclerAdapter;


    private TextView textViewFound;

    private OnFragmentInteractionListener mListener;

    private SharedPreferences sharedPreferences;
    private String district;
    private String subDistrict;

    public FoundFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FoundFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FoundFragment newInstance(String param1, String param2) {
        FoundFragment fragment = new FoundFragment();
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

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Post");
        mDatabase.keepSynced(true);


        sharedPreferences =  PreferenceManager.getDefaultSharedPreferences(getContext());
        district = sharedPreferences.getString("district","").trim();
        subDistrict = sharedPreferences.getString("sub_district","").trim();
        if(district.equals("All"))
        {
            mQuaryFound = mDatabase.orderByChild("lostFound").equalTo("Found");
        }
        else{
            if(subDistrict.equals("All"))
            {
                mQuaryFound = mDatabase.orderByChild("districtSelectionLF").equalTo("Found"+" , "+district);
            }
            else{
                mQuaryFound = mDatabase.orderByChild("subDistrictSelectionLF").equalTo("Found"+" , "+subDistrict);
            }
        }

        mQuaryFound.keepSynced(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_found, container, false);

        floatingActionMenu = (FloatingActionMenu) getActivity().findViewById(R.id.floatingActionMenu);
        recyclerView = (RecyclerView) layout.findViewById(R.id.missingPersonRecyclerViewFoundFragment);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(layout.getContext()));
        mAuth = FirebaseAuth.getInstance();



        textViewFound =(TextView) layout.findViewById(R.id.textView_fragmentFound);
        mQuaryFound.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() == null) {
                    textViewFound.setVisibility(View.VISIBLE);
                } else {
                    textViewFound.setVisibility(View.GONE);
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

        recyclerView.setAdapter(new FirebaseAdapterForPost().getPostAdapter(mQuaryFound,getActivity()));
        return layout;
    }

    public static FoundFragment getInstance(int position)
    {
        FoundFragment foundFragment = new FoundFragment();
        Bundle args = new Bundle();
        args.putInt("Position",position);
        foundFragment.setArguments(args);
        return foundFragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerView.setAdapter(new FirebaseAdapterForPost().getPostAdapter(mQuaryFound,getActivity()));
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
