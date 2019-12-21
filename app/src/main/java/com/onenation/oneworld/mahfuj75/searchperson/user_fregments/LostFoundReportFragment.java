package com.onenation.oneworld.mahfuj75.searchperson.user_fregments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.onenation.oneworld.mahfuj75.searchperson.activity.AddMissingPersonActivity;
import com.onenation.oneworld.mahfuj75.searchperson.activity.PostActivity;
import com.onenation.oneworld.mahfuj75.searchperson.objectclass.MissingPerson;
import com.onenation.oneworld.mahfuj75.searchperson.viewHolder.UserPostViewHolder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LostFoundReportFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LostFoundReportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LostFoundReportFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView textViewFoundLost;
    private DatabaseReference mDatabase;
    private DatabaseReference mRefUser;
    private Query mQuaryFoundLostReport;
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private String postKey;

    private Button buttonEdit;
    private Button buttonRemove;

    private OnFragmentInteractionListener mListener;
    private FirebaseRecyclerAdapter<MissingPerson, UserPostViewHolder> firebaseRecyclerAdapter;

    public LostFoundReportFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LostFoundReportFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LostFoundReportFragment newInstance(String param1, String param2) {
        LostFoundReportFragment fragment = new LostFoundReportFragment();
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
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Post");
        mRefUser = FirebaseDatabase.getInstance().getReference().child("Users").child(uId).child("DeletePost");
        mDatabase.keepSynced(true);
        mQuaryFoundLostReport = mDatabase.orderByChild("uid").equalTo(uId);
        mQuaryFoundLostReport.keepSynced(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_lost_found_report, container, false);

        recyclerView = (RecyclerView) layout.findViewById(R.id.userAccountRecyclerViewFoundLostFragment);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(layout.getContext()));
        textViewFoundLost = (TextView) layout.findViewById(R.id.textView_fragmentFoundLost_report);
        //buttonEdit =(Button) layout.findViewById(R.id.user_button_edit);
        mQuaryFoundLostReport.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() == null) {
                    textViewFoundLost.setVisibility(View.VISIBLE);
                } else {
                    textViewFoundLost.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<MissingPerson,
                UserPostViewHolder>(
                MissingPerson.class,
                R.layout.user_post_row,
                UserPostViewHolder.class,
                mQuaryFoundLostReport
        ) {


            @Override
            public MissingPerson getItem(int position) {

                return super.getItem(getItemCount() - (position + 1));
            }

            protected void populateViewHolder(UserPostViewHolder viewHolder, MissingPerson model, int position) {


                position = (getItemCount() - (position + 1));

                viewHolder.setImage(getActivity().getApplicationContext(), model.getImageUrl());
                viewHolder.setName(model.getMissingPersonName());
                viewHolder.setAge(model.getAge() + " years old");
                viewHolder.setLastSeenLocation(model.getDistrict(), model.getSubDistrict());
                viewHolder.setLostFound(model.getLostFound());
                viewHolder.setDate(model.getPostDate());


                buttonEdit = (Button) viewHolder.itemView.findViewById(R.id.user_button_edit);
                buttonRemove = (Button) viewHolder.itemView.findViewById(R.id.user_button_remove);


                final int finalPosition = position;
                buttonEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        postKey = getRef(finalPosition).getKey();
                        Intent i = new Intent(getActivity(), AddMissingPersonActivity.class);
                        i.putExtra("update", postKey);
                        startActivity(i);
                    }
                });

                buttonRemove.setOnClickListener(new View.OnClickListener() {
                     String key = getRef(finalPosition).getKey();
                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder myAlert = new AlertDialog.Builder(getActivity());
                        myAlert.setTitle("Remove")
                                .setMessage("Are you sure ?")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mDatabase.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {

                                                mRefUser.push().setValue(dataSnapshot.getValue(), new DatabaseReference.CompletionListener() {
                                                    @Override
                                                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                                        if (databaseError != null) {
                                                            AlertDialog.Builder myAlert = new AlertDialog.Builder(getActivity());
                                                            myAlert.setTitle("Failed")
                                                                    .setMessage("post is not removed")
                                                                    .setCancelable(false)
                                                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialog, int which) {
                                                                            dialog.dismiss();
                                                                        }
                                                                    })
                                                                    .create();
                                                            myAlert.show();
                                                        } else {
                                                            mDatabase.child(postKey).setValue(null);
                                                            AlertDialog.Builder myAlert = new AlertDialog.Builder(getActivity());
                                                            myAlert.setTitle("Successful")
                                                                    .setMessage("post is removed")
                                                                    .setCancelable(false)
                                                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialog, int which) {
                                                                            dialog.dismiss();
                                                                            onResume();
                                                                        }
                                                                    })
                                                                    .create();
                                                            myAlert.show();
                                                        }
                                                    }
                                                });
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).create();

                        myAlert.show();


                    }
                });

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        postKey = getRef(finalPosition).getKey();
                        //Toast.makeText(getContext(), postKey, Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getActivity(), PostActivity.class);
                        i.putExtra("keyOfPost", postKey);
                        startActivity(i);
                    }
                });
                viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {

                        AlertDialog.Builder myAlert = new AlertDialog.Builder(getActivity());
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
        recyclerView.setAdapter(firebaseRecyclerAdapter);
        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
        recyclerView.invalidate();
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
