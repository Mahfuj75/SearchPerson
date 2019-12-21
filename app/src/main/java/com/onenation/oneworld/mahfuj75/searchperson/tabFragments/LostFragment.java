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
 * {@link LostFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LostFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView textViewFragmentLost;

    private OnFragmentInteractionListener mListener;



    private DatabaseReference mDatabase ;
    private Query mQuaryLost;
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private String postKey;

    private FloatingActionMenu floatingActionMenu;

    private FirebaseRecyclerAdapter<MissingPerson,FoundLostCrimeViewHolder> firebaseRecyclerAdapter;

    private SharedPreferences sharedPreferences;
    private String district;
    private String subDistrict;


    public LostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LostFragment newInstance(String param1, String param2) {
        LostFragment fragment = new LostFragment();
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
        mQuaryLost = mDatabase.orderByChild("lostFound").equalTo("Lost");
        sharedPreferences =  PreferenceManager.getDefaultSharedPreferences(getContext());
        district = sharedPreferences.getString("district","").trim();
        subDistrict = sharedPreferences.getString("sub_district","").trim();
        if(district.equals("All"))
        {
            mQuaryLost = mDatabase.orderByChild("lostFound").equalTo("Lost");
        }
        else{
            if(subDistrict.equals("All"))
            {
                mQuaryLost = mDatabase.orderByChild("districtSelectionLF").equalTo("Lost"+" , "+district);
            }
            else{
                mQuaryLost = mDatabase.orderByChild("subDistrictSelectionLF").equalTo("Lost"+" , "+subDistrict);
            }
        }

        mQuaryLost.keepSynced(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_lost, container, false);
        floatingActionMenu = (FloatingActionMenu) getActivity().findViewById(R.id.floatingActionMenu);
        recyclerView = (RecyclerView) layout.findViewById(R.id.missingPersonRecyclerViewLostFragment);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(layout.getContext()));
        mAuth = FirebaseAuth.getInstance();

        textViewFragmentLost =(TextView) layout.findViewById(R.id.textView_fragmentLost);

        mQuaryLost.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() == null) {
                    textViewFragmentLost.setVisibility(View.VISIBLE);
                    floatingActionMenu.showMenu(true);
                } else {
                    textViewFragmentLost.setVisibility(View.GONE);
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

        recyclerView.setAdapter(new FirebaseAdapterForPost().getPostAdapter(mQuaryLost,getActivity()));
        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerView.setAdapter(new FirebaseAdapterForPost().getPostAdapter(mQuaryLost,getActivity()));
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

    public static LostFragment getInstance(int position) {

        LostFragment lostFragment = new LostFragment();
        Bundle args = new Bundle();
        args.putInt("Position",position);
        lostFragment.setArguments(args);
        return lostFragment;
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
