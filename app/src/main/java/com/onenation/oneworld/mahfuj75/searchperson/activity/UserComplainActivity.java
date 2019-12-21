package com.onenation.oneworld.mahfuj75.searchperson.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.onenation.oneworld.mahfuj75.searchperson.R;
import com.onenation.oneworld.mahfuj75.searchperson.fragments.ChatPopFragment;
import com.onenation.oneworld.mahfuj75.searchperson.objectclass.Message;
import com.onenation.oneworld.mahfuj75.searchperson.viewHolder.MessageViewHolder;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UserComplainActivity extends AppCompatActivity {


    private TextView textViewComDesSubject;
    private TextView textViewComDesDate;
    private TextView textViewComDesTime;
    private TextView textViewComDesDescription;

    private TextView textViewSpotDivision;
    private TextView textViewSpotDistrict;
    private TextView textViewSpotSubLocation;
    private TextView textViewSpotDescription;
    private TextView textViewStatusComplain;

    private Button buttonAllComment;


    private DatabaseReference mRef;
    private String complainKey;
    private String uid = "";
    private String userUid;


    private String incidentSpotDivision;
    private String incidentSpotDistrict;
    private String incidentSpotSubLocation;

    private String complainSubject;
    private String complainSubSubject;
    private EditText editTextCommentBox;

    private LinearLayout linearLayoutCommentComplainView;
    private FirebaseAuth mAuth;

    private RecyclerView commentRecycleView;
    private View viewGap;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_complain);

        complainKey = getIntent().getExtras().getString("keyOfComplain");

        //textViewComplainPostDate = (TextView) findViewById(R.id.textViewComplainPostDate);
        //textViewComplainPostTime = (TextView) findViewById(R.id.textViewComplainPostTime);


        textViewStatusComplain = (TextView) findViewById(R.id.statusComplain);

        textViewComDesSubject = (TextView) findViewById(R.id.complain_view_textViewComplainSubject);
        textViewComDesDate = (TextView) findViewById(R.id.complain_view_textViewIncidentDate);
        textViewComDesTime = (TextView) findViewById(R.id.complain_view_textViewIncidentTime);
        textViewComDesDescription = (TextView) findViewById(R.id.complain_view_textViewIncidentDescription);

        textViewSpotDivision = (TextView) findViewById(R.id.complain_view_textViewSpotDivision);
        textViewSpotDistrict = (TextView) findViewById(R.id.complain_view_textViewSpotDistrict);
        textViewSpotSubLocation = (TextView) findViewById(R.id.complain_view_textViewSpotSubDistrict);
        textViewSpotDescription = (TextView) findViewById(R.id.complain_view_textViewSpotDescription);

        commentRecycleView = (RecyclerView) findViewById(R.id.commentRecycleView);
        commentRecycleView.setHasFixedSize(true);
        commentRecycleView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        editTextCommentBox = (EditText) findViewById(R.id.editText_complain_commentBox);
        buttonAllComment = (Button) findViewById(R.id.button_viewAllComment);

        viewGap = findViewById(R.id.view_gap);


        linearLayoutCommentComplainView = (LinearLayout) findViewById(R.id.linearLayoutCommentComplainView);

        mRef = FirebaseDatabase.getInstance().getReference().child("Complain").child(complainKey);
        mAuth = FirebaseAuth.getInstance();
        userUid = mAuth.getCurrentUser().getUid();
        complainData();
        commentRecycle();

        buttonAllComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (savedInstanceState == null) {
                    //CommentPopFragment commentPopFragment = null;

                    ChatPopFragment.newInstance().show(getSupportFragmentManager(), complainKey);
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();


        editTextCommentBox.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if (Build.VERSION.SDK_INT >= 21) {

                    final int DRAWABLE_LEFT = 0;
                    final int DRAWABLE_TOP = 1;
                    final int DRAWABLE_RIGHT = 2;
                    final int DRAWABLE_BOTTOM = 3;

                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        if (motionEvent.getRawX() >= (editTextCommentBox.getRight() - editTextCommentBox.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            // your action here
                            Chat();
                            editTextCommentBox.setText("");
                            InputMethodManager inputManager = (InputMethodManager)
                                    getSystemService(Context.INPUT_METHOD_SERVICE);

                            if (getCurrentFocus() != null) {

                                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                        InputMethodManager.HIDE_NOT_ALWAYS);
                            }


                            editTextCommentBox.setCursorVisible(false);
                            return true;
                        }
                    }

                } else {

                }
                return false;
            }
        });




    }


    private void commentRecycle() {

        DatabaseReference mRefComment = mRef.child("message");

        Query mQuary;
        mQuary = mRefComment.limitToLast(5);


        FirebaseRecyclerAdapter<Message, MessageViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Message, MessageViewHolder>(
                Message.class,
                R.layout.chat_layout,
                MessageViewHolder.class,
                mQuary
        ) {

            @Override
            public int getItemCount() {

                return super.getItemCount();
            }

            protected void populateViewHolder(MessageViewHolder viewHolder, Message model, int position) {

                String chatUid = model.getUid().trim();
                if (position == 4) {
                    buttonAllComment.setVisibility(View.VISIBLE);
                    viewGap.setVisibility(View.GONE);
                }
                else {
                    buttonAllComment.setVisibility(View.GONE);
                    viewGap.setVisibility(View.VISIBLE);
                }

                if(chatUid.equals(userUid))
                {
                    viewHolder.setMessage(model.getMessage(),1);
                    if(getItemCount()-1==position)
                    {
                        viewHolder.setDateTime(model.getMessageDate(),model.getMessageTime(),1);
                    }
                    else if((position%3)==0 && position!= 0)
                    {
                        viewHolder.setDateTime(model.getMessageDate(),model.getMessageTime(),1);
                    }
                    else
                    {
                        viewHolder.setDateTime(model.getMessageDate(),model.getMessageTime(),0);
                    }
                }
                else{
                    viewHolder.setMessage(model.getMessage(),2);

                    if(getItemCount()-1==position)
                    {
                        viewHolder.setDateTime(model.getMessageDate(),model.getMessageTime(),1);
                    }
                    else if((position%3)==0 && position!= 0 )
                    {
                        viewHolder.setDateTime(model.getMessageDate(),model.getMessageTime(),1);
                    }
                    else
                    {
                        viewHolder.setDateTime(model.getMessageDate(),model.getMessageTime(),0);
                    }
                }



            }
        };
        //Toast.makeText(getApplicationContext(),firebaseRecyclerAdapter.getItem(1).toString(),Toast.LENGTH_LONG).show();
        commentRecycleView.setAdapter(firebaseRecyclerAdapter);
    }



    private void Chat() {

        String comment = editTextCommentBox.getText().toString().trim();
        Toast.makeText(getApplicationContext(), comment, Toast.LENGTH_LONG).show();
        DatabaseReference mRefComment = mRef.child("message").push();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mDateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
        SimpleDateFormat mTimeFormat = new SimpleDateFormat("HH:mm  aa");

        String commentDate = mDateFormat.format(calendar.getTime());
        String commentTime = mTimeFormat.format(calendar.getTime());


        if (!comment.equals("")) {



            if (mAuth.getCurrentUser() != null) {
                mRefComment.child("message").setValue(comment);
                mRefComment.child("uid").setValue(mAuth.getCurrentUser().getUid());
                mRefComment.child("messageDate").setValue(commentDate);
                mRefComment.child("messageTime").setValue(commentTime);

                commentRecycle();

            }


        } else {

            AlertDialog.Builder myAlert = new AlertDialog.Builder(UserComplainActivity.this);
            myAlert.setMessage("message box is empty")
                    .setCancelable(false)
                    .setTitle("message")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            myAlert.show();
        }
    }

    private void complainData() {

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                incidentSpotDivision = dataSnapshot.child("incidentSpotDivision").getValue().toString().trim();
                incidentSpotDistrict = dataSnapshot.child("incidentSpotDistrict").getValue().toString().trim();
                incidentSpotSubLocation = dataSnapshot.child("incidentSpotSubLocation").getValue().toString().trim();


                try{
                    uid = dataSnapshot.child("uid").getValue().toString().trim();
                }
                catch (NullPointerException e)
                {
                    //Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    uid = "";
                }


                complainSubSubject = dataSnapshot.child("complainSubSubject").getValue().toString().trim();
                complainSubject = dataSnapshot.child("complainSubject").getValue().toString().trim();

                if(dataSnapshot.child("process").getValue().toString().trim().equals("1"))
                {

                    textViewStatusComplain.setText("Validation");
                }
                else if(dataSnapshot.child("process").getValue().toString().trim().equals("2"))
                {
                   textViewStatusComplain.setText("Process");

                }
                else if(dataSnapshot.child("process").getValue().toString().trim().equals("3"))
                {
                    textViewStatusComplain.setText("Action");
                }
                else {
                    textViewStatusComplain.setTextColor(Color.parseColor("#B71C1C"));
                    textViewStatusComplain.setText("not seen yet");
                    linearLayoutCommentComplainView.setVisibility(View.GONE);
                }



                //textViewComplainPostDate.setText(dataSnapshot.child("postDate").getValue().toString().trim());
               // textViewComplainPostTime.setText(dataSnapshot.child("postTime").getValue().toString().trim());


                textViewComDesSubject.setText(complainSubSubject);
                textViewComDesDate.setText(dataSnapshot.child("incidentDate").getValue().toString().trim());
                textViewComDesTime.setText(dataSnapshot.child("incidentTime").getValue().toString().trim());
                textViewComDesDescription.setText(dataSnapshot.child("incidentDescription").getValue().toString().trim());


                textViewSpotDivision.setText(incidentSpotDivision);
                textViewSpotDistrict.setText(incidentSpotDistrict);
                textViewSpotSubLocation.setText(incidentSpotSubLocation);

                textViewSpotDescription.setText(dataSnapshot.child("incidentSpotDescription").getValue().toString().trim());



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
