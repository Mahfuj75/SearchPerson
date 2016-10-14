package com.onenation.oneworld.mahfuj75.searchperson.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.onenation.oneworld.mahfuj75.searchperson.R;
import com.onenation.oneworld.mahfuj75.searchperson.objectclass.MissingPerson;

public class MissingPersonActivity extends AppCompatActivity {


    private LinearLayout mLinearLayout;
    //private LinearLayout cardLinearLayout;
    private ScrollView noMissingPersonScrollView;

    private  RecyclerView recyclerView;
    private DatabaseReference mDatabase ;
    private CardView cardViewMissingPerson;

    private ProgressDialog mProgress;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    int signUser=0;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missing_person);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Post");

        mLinearLayout= (LinearLayout) findViewById(R.id.missing_parson_search);
        cardViewMissingPerson =(CardView) findViewById(R.id.cardView_missing_person);


        recyclerView = (RecyclerView) findViewById(R.id.missingPersonRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgress = new ProgressDialog(this);



        noMissingPersonScrollView = (ScrollView) findViewById(R.id.no_missing_person_scrollView);
        mAuth = FirebaseAuth.getInstance();
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mAuth.getCurrentUser()==null)
                {
                    signUser=0;
                }
                else
                {
                    signUser=1;
                }

            }
        };




    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);

        FirebaseRecyclerAdapter<MissingPerson,MissingPersonViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<MissingPerson, MissingPersonViewHolder>(
                MissingPerson.class,
                R.layout.missing_person_row,
                MissingPersonViewHolder.class,
                mDatabase
        ) {




            @Override
            public MissingPerson getItem(int position) {
                return super.getItem(getItemCount() - (position + 1));

            }
            @Override
            protected void populateViewHolder(MissingPersonViewHolder viewHolder, MissingPerson model, int position) {


                //Toast.makeText(getApplicationContext(),position+"",Toast.LENGTH_SHORT).show();

                //viewHolder.setImage(model.getImage());
                viewHolder.setName(model.getName());
                viewHolder.setAge("Age : "+model.getAge());
                viewHolder.setLastSeenLocation("Last Seen : "+model.getLastSeenLocation());
                viewHolder.setLostFound("Condition : "+model.getLostFound());

            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }




    public  static  class  MissingPersonViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public MissingPersonViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }
        /*public  void  setImage (String image){

            ImageView MissingPersonImage = (ImageView) mView.findViewById(R.id.missing_person_image);
            MissingPersonImage.setImageURI(Uri.parse(image));
        }*/

        public  void  setName (String name){

            TextView MissingPersonName = (TextView) mView.findViewById(R.id.missing_person_name);
            MissingPersonName.setText(name);
        }
        public  void  setAge (String age){

            TextView MissingPersonAge = (TextView) mView.findViewById(R.id.age_textView);
            MissingPersonAge.setText(age);
        }
        public  void  setLastSeenLocation (String lastSeenLocation){

            TextView MissingPersonLastSeenLocation = (TextView) mView.findViewById(R.id.textView_lastSeenLocation);
            MissingPersonLastSeenLocation.setText(lastSeenLocation);
        }
        public  void  setLostFound (String lostFound){

            TextView MissingPersonLostFound = (TextView) mView.findViewById(R.id.lost_found_textView);
            MissingPersonLostFound.setText(lostFound);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_missing_person, menu);

        if (mAuth.getCurrentUser()==null)
        {
            MenuItem item  = menu.findItem(R.id.action_logout);
            item.setVisible(false);
        }
        else
        {
            MenuItem item  = menu.findItem(R.id.action_logout);
            item.setVisible(true);
        }


        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id==R.id.action_create_newAccount)
        {
            Intent i = new Intent(getApplicationContext(),CreateNewAccountActivity.class);
            startActivity(i);
        }
        else if (id==R.id.action_login)
        {
            Intent i = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(i);
        }
        else if (id==R.id.action_missing)
        {
            Intent i = new Intent(getApplicationContext(),AddMissingPersonActivity.class);
            startActivity(i);
        }
        else if (id==R.id.action_search)
        {
            if (mLinearLayout.getVisibility()==View.VISIBLE)
            {
                mLinearLayout.setVisibility(View.GONE);
            }
            else
            {
                mLinearLayout.setVisibility(View.VISIBLE);
            }

        }
        else if(id== R.id.action_logout)
        {

            mAuth.signOut();
            Intent i= new Intent(this,MissingPersonActivity.class);
            startActivity(i);
            finish();



        }

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }
}
