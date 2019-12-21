package com.onenation.oneworld.mahfuj75.searchperson.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.firebase.messaging.FirebaseMessaging;
import com.onenation.oneworld.mahfuj75.searchperson.R;
import com.onenation.oneworld.mahfuj75.searchperson.fragments.CommentPopFragment;
import com.onenation.oneworld.mahfuj75.searchperson.fragments.PostByPopFragment;
import com.onenation.oneworld.mahfuj75.searchperson.objectclass.Comment;
import com.onenation.oneworld.mahfuj75.searchperson.viewHolder.CommentViewHolder;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;


public class PostActivity extends AppCompatActivity {


    private ImageView imageViewPersonImage;
    private TextView textViewDate;
    private TextView textViewTime;
    private TextView textViewName;
    private TextView textViewLocation;
    private TextView textViewRelative;
    private TextView textViewAge;
    private TextView textViewHeight;
    private TextView textViewDescription;
    private TextView textViewFoundLost;
    private TextView textViewPostGiverName;
    private TextView textViewPostMissingDateName;
    private TextView textViewPostMissingDate;
    private TextView textViewCommentCounter;
    private Button buttonViewAllComment;

    private EditText editTextCommentBox;
    private Button buttonPostCall;
    private Button buttonPostMessage;
    private Button buttonPostComment;
    private LinearLayout postButtonLayout;
    private FirebaseAuth mAuth;
    private DatabaseReference mRefPost;
    private DatabaseReference mRefUser;
    private DatabaseReference mRefComment;
    private FirebaseMessaging mMsg;

    private String postKey;

    private String postGiverPhoneNumber;
    private String postGiverName;

    private String uid;

    private String commentImageUrl;
    private String currentUserUid;
    private String commentUserName;
    private RecyclerView recyclerView;
    private View postView;


    long commentCounter = 0;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        postKey = getIntent().getExtras().getString("keyOfPost");
        mAuth = FirebaseAuth.getInstance();

        mRefPost = FirebaseDatabase.getInstance().getReference().child("Post").child(postKey);
        mRefUser = FirebaseDatabase.getInstance().getReference().child("Users");

        textViewDate = (TextView) findViewById(R.id.postDate);
        textViewTime = (TextView) findViewById(R.id.postTime);
        imageViewPersonImage = (ImageView) findViewById(R.id.postImage);
        textViewName = (TextView) findViewById(R.id.postName);
        textViewLocation = (TextView) findViewById(R.id.postLocation);
        textViewRelative = (TextView) findViewById(R.id.postRelative);
        textViewAge = (TextView) findViewById(R.id.postAge);
        textViewHeight = (TextView) findViewById(R.id.postHeight);
        textViewDescription = (TextView) findViewById(R.id.postDescription);
        textViewFoundLost = (TextView) findViewById(R.id.postFoundLost);
        textViewCommentCounter = (TextView) findViewById(R.id.textViewCommentCounter);

        textViewPostGiverName = (TextView) findViewById(R.id.postGiverName);
        textViewPostMissingDate = (TextView) findViewById(R.id.postMissingDate);
        textViewPostMissingDateName = (TextView) findViewById(R.id.postMissingDateName);
        buttonViewAllComment = (Button) findViewById(R.id.button_viewAllComment);

        buttonPostCall = (Button) findViewById(R.id.buttonPostCall);
        buttonPostMessage = (Button) findViewById(R.id.buttonPostMessage);
        buttonPostComment = (Button) findViewById(R.id.postButtonComment);


        editTextCommentBox = (EditText) findViewById(R.id.editText_complain_commentBox);
        recyclerView = (RecyclerView) findViewById(R.id.commentRecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        postButtonLayout = (LinearLayout) findViewById(R.id.postButtonLayout);
        postView = findViewById(R.id.postView);

        textViewPostGiverName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PostByPopFragment postByPopFragment = null;
                PostByPopFragment.newInstance().show(getSupportFragmentManager(), uid);

            }
        });


        //Toast.makeText(getApplicationContext(),postKey,Toast.LENGTH_LONG).show();

        postData();

        if (mAuth.getCurrentUser() != null) {
            validUser();

        }

        buttonPostCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + postGiverPhoneNumber));
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);

            }
        });

        buttonPostMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("smsto:" + postGiverPhoneNumber));


                try {
                    startActivity(intent);
                } catch (android.content.ActivityNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

        if (Build.VERSION.SDK_INT < 21) {
            buttonPostComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    comment();
                    editTextCommentBox.setText("");
                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);

                    if (getCurrentFocus() != null) {

                        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                    }


                    editTextCommentBox.setCursorVisible(false);
                }
            });
        }


        editTextCommentBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextCommentBox.setCursorVisible(true);
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                if (getCurrentFocus() == null) {

                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.SHOW_IMPLICIT);
                }

            }
        });
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
                            comment();
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

        buttonViewAllComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (savedInstanceState == null) {
                    //CommentPopFragment commentPopFragment = null;

                    CommentPopFragment.newInstance().show(getSupportFragmentManager(), postKey);
                }
            }
        });


        commentRecycle();


    }

    private void postData() {


        mRefPost.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    uid = dataSnapshot.child("uid").getValue().toString();

                    if (mAuth.getCurrentUser() != null) {
                        if (currentUserUid.equals(uid)) {
                            postButtonLayout.setVisibility(View.GONE);
                            postView.setVisibility(View.GONE);

                        } else {
                            postButtonLayout.setVisibility(View.VISIBLE);
                            postView.setVisibility(View.VISIBLE);
                        }
                    } else {
                        postButtonLayout.setVisibility(View.VISIBLE);
                        postView.setVisibility(View.VISIBLE);
                    }
                    postGiverInformation(uid);


                    textViewDate.setText(dataSnapshot.child("postDate").getValue().toString());
                    textViewTime.setText(dataSnapshot.child("postTime").getValue().toString());
                    textViewName.setText(dataSnapshot.child("missingPersonName").getValue().toString());
                    if (Objects.equals(dataSnapshot.child("missingPersonName").getValue().toString().trim(), "")) {
                        textViewName.setText("Unknown");
                        textViewName.setTextColor(Color.parseColor("#B71C1C"));
                    }
                    String location;
                    if (dataSnapshot.child("subDistrict").getValue().equals("All")) {
                        location = dataSnapshot.child("district").getValue().toString();
                    } else {
                        location = dataSnapshot.child("district").getValue().toString() + " ," +
                                dataSnapshot.child("subDistrict").getValue().toString();
                    }


                    if (dataSnapshot.child("imageUrl").getValue().toString() == null) {
                        Picasso.with(getApplicationContext()).load(R.drawable.no_image)
                                .error(R.drawable.progress_animation)
                                .into(imageViewPersonImage);
                    } else {
                        Picasso.with(getApplicationContext()).load(dataSnapshot.child("imageUrl").getValue().toString())
                                .error(R.drawable.progress_animation)
                                .into(imageViewPersonImage);

                        textViewLocation.setText(location);
                        textViewRelative.setText(dataSnapshot.child("relativeName").getValue().toString());
                        if (Objects.equals(dataSnapshot.child("relativeName").getValue().toString().trim(), "")) {
                            textViewRelative.setText("Unknown");
                            textViewRelative.setTextColor(Color.parseColor("#B71C1C"));
                        }
                        textViewAge.setText(dataSnapshot.child("age").getValue().toString() + " years old");
                        textViewHeight.setText(dataSnapshot.child("height").getValue().toString());
                        textViewDescription.setText(dataSnapshot.child("description").getValue().toString());
                        textViewFoundLost.setText(dataSnapshot.child("lostFound").getValue().toString());
                        if (dataSnapshot.child("lostFound").getValue().toString().equals("Found")) {
                            textViewPostMissingDateName.setText("Found Date");
                        } else if (dataSnapshot.child("lostFound").getValue().toString().equals("Lost")) {
                            textViewPostMissingDateName.setText("Lost Date");
                        }

                        textViewPostMissingDate.setText(dataSnapshot.child("missingDate").getValue().toString());

                    }
                } catch (Exception e) {
                    finish();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void validUser() {


        commentUserName = mAuth.getCurrentUser().getDisplayName();
        currentUserUid = mAuth.getCurrentUser().getUid();


        mRefUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                commentImageUrl = dataSnapshot.child(currentUserUid).child("image").getValue().toString();
                commentUserName = dataSnapshot.child(currentUserUid).child("name").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void commentRecycle() {

        mRefComment = mRefPost.child("Comments");

        mRefComment.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                commentCounter = dataSnapshot.getChildrenCount();
                //Toast.makeText(getApplicationContext(), String.valueOf(commentCounter) ,Toast.LENGTH_LONG).show();
                textViewCommentCounter.setText(commentCounter + " says about this post");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Query mQuary;
        mQuary = mRefComment.limitToLast(3);

        FirebaseRecyclerAdapter<Comment, CommentViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Comment, CommentViewHolder>(
                Comment.class,
                R.layout.comment_row,
                CommentViewHolder.class,
                mQuary
        ) {

            @Override
            public int getItemCount() {

                return super.getItemCount();
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected void populateViewHolder(CommentViewHolder viewHolder, Comment model, int position) {


                if (position == 2) {
                    buttonViewAllComment.setVisibility(View.VISIBLE);
                }
                viewHolder.setImageUrl(getApplicationContext(), model.getImageUrl());

                viewHolder.setUserName(model.getUserName());
                viewHolder.setComment(model.getComment());
                viewHolder.setTime(model.getCommentDate(), model.getCommentTime());

            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }


    private void comment() {

        String comment = editTextCommentBox.getText().toString().trim();
        Toast.makeText(getApplicationContext(), comment, Toast.LENGTH_LONG).show();
        DatabaseReference mRef = mRefPost.child("Comments").push();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mDateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
        SimpleDateFormat mTimeFormat = new SimpleDateFormat("HH:mm  aa");

        String commentDate = mDateFormat.format(calendar.getTime());
        String commentTime = mTimeFormat.format(calendar.getTime());


        if (!comment.equals("")) {

            if (mAuth.getCurrentUser() != null) {
                mRef.child("comment").setValue(comment);
                mRef.child("userName").setValue(commentUserName);
                mRef.child("uid").setValue(uid);
                mRef.child("imageUrl").setValue(commentImageUrl);
                mRef.child("commentDate").setValue(commentDate);
                mRef.child("commentTime").setValue(commentTime);

            } else {
                mRef.child("comment").setValue(comment);
                mRef.child("commentDate").setValue(commentDate);
                mRef.child("commentTime").setValue(commentTime);
            }


        } else {

            AlertDialog.Builder myAlert = new AlertDialog.Builder(PostActivity.this);
            myAlert.setMessage("Comment is empty")
                    .setCancelable(false)
                    .setTitle("Comment")
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


    public void postGiverInformation(String uid) {
        mRefUser.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                postGiverName = dataSnapshot.child("name").getValue().toString();
                textViewPostGiverName.setText(postGiverName);
                postGiverPhoneNumber = dataSnapshot.child("phone").getValue().toString();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
