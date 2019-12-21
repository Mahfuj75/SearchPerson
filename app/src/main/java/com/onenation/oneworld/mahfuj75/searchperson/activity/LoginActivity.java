package com.onenation.oneworld.mahfuj75.searchperson.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.onenation.oneworld.mahfuj75.searchperson.R;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


    public static Context java;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    private Button mEmailSignInButton;
    private Button signUpButton;
    private Button forgetPassButton;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    private GoogleSignInAccount googleSignInAccount;

    private ProgressDialog mProgress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        mProgress = new ProgressDialog(this);

        mPasswordView = (EditText) findViewById(R.id.password);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);


        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        signUpButton = (Button) findViewById(R.id.email_sign_up_button);
        forgetPassButton = (Button) findViewById(R.id.email_forget_pass_button);



        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });


        signUpButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CreateNewAccountActivity.class);
                startActivity(i);
                finish();
            }
        });
        forgetPassButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEmailView.length() != 0) {
                    mProgress.setMessage("Reset mail is sending");
                    mProgress.show();
                    forgetMailSend(mEmailView.getText().toString().trim());
                } else {
                    AlertDialog.Builder myAlert = new AlertDialog.Builder(LoginActivity.this);
                    myAlert.setMessage("fill your E-mail field")
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
            }
        });

    }

    private void forgetMailSend(String email) {


        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    mProgress.dismiss();
                    AlertDialog.Builder myAlert = new AlertDialog.Builder(LoginActivity.this);
                    myAlert.setMessage("Check your email")
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
                    mProgress.dismiss();
                    AlertDialog.Builder myAlert = new AlertDialog.Builder(LoginActivity.this);
                    myAlert.setMessage("E-mail not matching")
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

            }
        });

    }


    private void checkLogin() {
        String email = mEmailView.getText().toString().trim();
        String password = mPasswordView.getText().toString().trim();
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

            mProgress.setMessage("Signing in...");
            mProgress.show();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {


                    if (task.isSuccessful()) {

                        if (mAuth.getCurrentUser().isEmailVerified()) {

                            mProgress.dismiss();

                            AlertDialog.Builder myAlert = new AlertDialog.Builder(LoginActivity.this);
                            myAlert.setMessage("press ok")
                                    .setCancelable(false)
                                    .setTitle("Login Successful")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            Intent i = new Intent(getApplicationContext(), TabViewActivity.class);
                                            startActivity(i);
                                            finish();
                                        }
                                    })
                                    .create();
                            myAlert.show();

                        } else {

                            mProgress.dismiss();
                            AlertDialog.Builder myAlert = new AlertDialog.Builder(LoginActivity.this);
                            myAlert.setMessage("email not verified , resend verification mail by pressing \"OK\" else \"Cancel\"")
                                    .setCancelable(false)
                                    .setTitle("Verify")
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            mAuth.signOut();
                                            Intent i = new Intent(getApplicationContext(), TabViewActivity.class);
                                            startActivity(i);
                                            finish();
                                        }
                                    })
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            mAuth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                    AlertDialog.Builder myAlert = new AlertDialog.Builder(LoginActivity.this);
                                                    myAlert.setMessage("Your verification mail is send please check")
                                                            .setCancelable(false)
                                                            .setTitle("Verification")
                                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    dialog.dismiss();
                                                                    mAuth.signOut();
                                                                    Intent i = new Intent(getApplicationContext(), TabViewActivity.class);
                                                                    startActivity(i);
                                                                    finish();

                                                                }
                                                            })
                                                            .create();
                                                    myAlert.show();

                                                }
                                            });
                                        }
                                    })
                                    .create();
                            myAlert.show();

                        }


                    } else {
                        mProgress.dismiss();
                        AlertDialog.Builder myAlert = new AlertDialog.Builder(LoginActivity.this);
                        myAlert.setMessage("Please try again")
                                .setCancelable(false)
                                .setTitle("Login Error")
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
            });

        } else {
            if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
                AlertDialog.Builder myAlert = new AlertDialog.Builder(LoginActivity.this);
                myAlert.setMessage("e-mail & password field is empty")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                myAlert.show();
            } else if (TextUtils.isEmpty(email)) {
                AlertDialog.Builder myAlert = new AlertDialog.Builder(LoginActivity.this);
                myAlert.setMessage("e-mail field is empty")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                myAlert.show();
            } else if (TextUtils.isEmpty(password)) {
                AlertDialog.Builder myAlert = new AlertDialog.Builder(LoginActivity.this);
                myAlert.setMessage("password field is empty")
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
    }


}

