package com.onenation.oneworld.mahfuj75.searchperson.user_fregments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.onenation.oneworld.mahfuj75.searchperson.R;
import com.onenation.oneworld.mahfuj75.searchperson.activity.UserComplainActivity;
import com.onenation.oneworld.mahfuj75.searchperson.objectclass.Complain;
import com.onenation.oneworld.mahfuj75.searchperson.viewHolder.ComplainViewHolder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ComplainReportFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ComplainReportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComplainReportFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FirebaseRecyclerAdapter<Complain,ComplainViewHolder> firebaseRecycleComplainAdapter;
    private TextView textViewComplain;
    private DatabaseReference mDatabase;
    private DatabaseReference mRefUser;
    private Query mQuaryComplainReport;
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private String postKey;

    private Button buttonEdit;
    private Button buttonRemove;

    private OnFragmentInteractionListener mListener;

    public ComplainReportFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ComplainReportFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ComplainReportFragment newInstance(String param1, String param2) {
        ComplainReportFragment fragment = new ComplainReportFragment();
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

        String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Complain");
        mRefUser = FirebaseDatabase.getInstance().getReference().child("Users").child(uId).child("DeletePost");
        mDatabase.keepSynced(true);
        mQuaryComplainReport = mDatabase.orderByChild("uid").equalTo(uId);
        mQuaryComplainReport.keepSynced(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_complain_report, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.missingPersonRecyclerViewComplainFragmentFragment);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        textViewComplain = (TextView) view.findViewById(R.id.textView_fragmentComplain_report);
        //buttonEdit =(Button) layout.findViewById(R.id.user_button_edit);
        mQuaryComplainReport.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() == null) {
                    textViewComplain.setVisibility(View.VISIBLE);
                } else {
                    textViewComplain.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        firebaseRecycleComplainAdapter = new FirebaseRecyclerAdapter<Complain, ComplainViewHolder>(
                Complain.class,
                R.layout.complain_card_view,
                ComplainViewHolder.class,
                mQuaryComplainReport

        ) {
            @Override
            protected void populateViewHolder(ComplainViewHolder viewHolder, Complain model, int position) {
                viewHolder.setPostDate(model.getPostDate());
                viewHolder.setPostTime(model.getPostTime());
                viewHolder.setSubject(model.getComplainSubSubject());
                viewHolder.setIncidentDate(model.getIncidentDate());
                viewHolder.setIncidentLocation(model.getIncidentSpotSubLocation());
                final int finalPosition = position;
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String complainKey = getRef(finalPosition).getKey();
                        //Toast.makeText(getContext(),postKey,Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getContext(), UserComplainActivity.class);
                        i.putExtra("keyOfComplain",complainKey);
                        startActivity(i);
                    }
                });
            }
        };


        recyclerView.setAdapter(firebaseRecycleComplainAdapter);
        return view;
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
