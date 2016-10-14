package com.onenation.oneworld.mahfuj75.searchperson.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.onenation.oneworld.mahfuj75.searchperson.R;

public class CreateNewAccountActivity extends AppCompatActivity {
    private ImageView imageViewNewAccount;

    private Button buttonNewAccountImage;
    private Button buttonCreateNewAccount;

    private RadioButton radioNewAccountButtonMale;
    private RadioButton radioButtonNewAccountFemale;

    private EditText editTextNewAccountName;
    private EditText editTextNewAccountEmail;
    private EditText editTextNewAccountPassword;
    private EditText editTextNewAccountRepeatPassword;
    private EditText editTextNewAccountPhoneNumber;
    private EditText editTextNewAccountLocation;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
   // private FirebaseAuth.AuthStateListener mAuthListner;

    private ProgressDialog mProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account);
        imageViewNewAccount = (ImageView) findViewById(R.id.new_account_imageView);
        buttonNewAccountImage = (Button) findViewById(R.id.new_account_image_button);
        buttonCreateNewAccount = (Button) findViewById(R.id.button_create_new_account);
        radioNewAccountButtonMale =(RadioButton) findViewById(R.id.radio_new_account_male);
        radioButtonNewAccountFemale =(RadioButton) findViewById(R.id.radio_new_account_female);
        editTextNewAccountName =(EditText) findViewById(R.id.editText_new_account_user_name);
        editTextNewAccountEmail =(EditText) findViewById(R.id.editText_new_account_user_email);
        editTextNewAccountPassword =(EditText) findViewById(R.id.editText_new_account_user_password);
        editTextNewAccountRepeatPassword=(EditText) findViewById(R.id.editText_new_account_user_password_repeat);
        editTextNewAccountPhoneNumber =(EditText) findViewById(R.id.editText_new_account_user_phone);
        editTextNewAccountLocation =(EditText) findViewById(R.id.editText_new_account_user__location);

        mAuth = FirebaseAuth.getInstance();
        mProgress = new ProgressDialog(this);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");


        /*mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()== null)
                {

                }
            }
        };*/

        buttonCreateNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegister();

            }
        });





    }

    private void startRegister()
    {

        String name ;
        String gander="";
        String email;
        String password;
        String phoneNumber;
        String location;

        if(editTextNewAccountName.length()==0)
        {
            AlertDialog.Builder myAlert = new AlertDialog.Builder(CreateNewAccountActivity.this);
            myAlert.setMessage("Fill the name field")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            myAlert.show();
        }
        else
        {

            name = editTextNewAccountName.getText().toString();


            if(editTextNewAccountEmail.length()==0)
            {
                AlertDialog.Builder myAlert = new AlertDialog.Builder(CreateNewAccountActivity.this);
                myAlert.setMessage("Fill the e-mail field")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                myAlert.show();
            }
            else {
                email = editTextNewAccountEmail.getText().toString();

                if(editTextNewAccountPassword.length()==0)
                {
                    AlertDialog.Builder myAlert = new AlertDialog.Builder(CreateNewAccountActivity.this);
                    myAlert.setMessage("Fill the password field")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create();
                    myAlert.show();
                }

                else if(editTextNewAccountPassword.length()<=7)
                {
                    AlertDialog.Builder myAlert = new AlertDialog.Builder(CreateNewAccountActivity.this);
                    myAlert.setMessage("Password must be eight characters long")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create();
                    myAlert.show();
                }
                else if(!editTextNewAccountPassword.getText().toString().equals(editTextNewAccountRepeatPassword.getText().toString()))
                {
                    AlertDialog.Builder myAlert = new AlertDialog.Builder(CreateNewAccountActivity.this);
                    myAlert.setMessage("Match password and repeat-password")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create();
                    myAlert.show();
                }
                else {
                    password=editTextNewAccountPassword.getText().toString();

                    if (!radioNewAccountButtonMale.isChecked()&&!radioButtonNewAccountFemale.isChecked()){
                        AlertDialog.Builder myAlert = new AlertDialog.Builder(CreateNewAccountActivity.this);
                        myAlert.setMessage("Please Click Gander")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .create();
                        myAlert.show();
                    }
                    else
                    {

                        if(radioNewAccountButtonMale.isChecked())
                        {
                            gander="Male";
                        }
                        else if(radioButtonNewAccountFemale.isChecked())
                        {
                            gander="Female";
                        }


                        if(editTextNewAccountPhoneNumber.length()==0)
                        {
                            AlertDialog.Builder myAlert = new AlertDialog.Builder(CreateNewAccountActivity.this);
                            myAlert.setMessage("Fill the phone-number field")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .create();
                            myAlert.show();
                        }
                        else
                        {
                            phoneNumber= editTextNewAccountPhoneNumber.getText().toString();
                            if(editTextNewAccountLocation.length()==0)
                            {
                                AlertDialog.Builder myAlert = new AlertDialog.Builder(CreateNewAccountActivity.this);
                                myAlert.setMessage("Fill the location field")
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .create();
                                myAlert.show();
                            }
                            else
                            {
                                location=editTextNewAccountLocation.getText().toString();

                                final String finalGander = gander;
                                final String finalPassword =password;
                                final String finalName = name;
                                final String finalEmail = email;
                                final String finalLocation = location;
                                final String finalPhoneNumber = phoneNumber;

                                endProcess(finalGander,finalPassword,finalName,finalEmail,finalLocation,finalPhoneNumber);




                            }
                        }

                    }


                }
            }

        }


    }

    private  void endProcess(String gander, String password, String name, String email , String location, String phoneNumber)
    {
        mProgress.setMessage("Singing up...");
        mProgress.show();
        final String finalGander = gander;
        final String finalPassword =password;
        final String finalName = name;
        final String finalEmail = email;
        final String finalLocation = location;
        final String finalPhoneNumber = phoneNumber;


        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    String userID= mAuth.getCurrentUser().getUid();
                    DatabaseReference currentUserDB = mDatabase.child(userID);

                    currentUserDB.child("name").setValue(finalName);
                    currentUserDB.child("image").setValue("URL");
                    currentUserDB.child("gander").setValue(finalGander);
                    currentUserDB.child("email").setValue(finalEmail);
                    currentUserDB.child("phone").setValue(finalPhoneNumber);
                    currentUserDB.child("location").setValue(finalLocation);
                    mProgress.dismiss();

                    Intent i = new Intent(getApplicationContext(),MissingPersonActivity.class);
                    mAuth.signInWithEmailAndPassword(finalEmail,finalPassword);

                    startActivity(i);
                    finish();

                }
            }
        });
    }
}
