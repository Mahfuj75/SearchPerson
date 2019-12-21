package com.onenation.oneworld.mahfuj75.searchperson.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.onenation.oneworld.mahfuj75.searchperson.R;
import com.onenation.oneworld.mahfuj75.searchperson.custom.SpinnerCustomAdapter;
import com.onenation.oneworld.mahfuj75.searchperson.dialog.DatePickerFragment;
import com.onenation.oneworld.mahfuj75.searchperson.objectclass.SubDistrictSelector;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;


public class CreateNewAccountActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private ImageView imageViewNewAccount;

    private Button buttonNewAccountImage;
    private Button buttonCreateNewAccount;
    private Button buttonDatePicker;

    private RadioButton radioNewAccountButtonMale;
    private RadioButton radioButtonNewAccountFemale;

    private EditText editTextNewAccountName;
    private EditText editTextNewAccountEmail;
    private EditText editTextNewAccountPassword;
    private EditText editTextNewAccountRepeatPassword;
    private EditText editTextNewAccountPhoneNumber;
    private EditText editTextNewAccountLocation;
    private TextView textViewDateOfBirth;


    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Query mQuary;
    private DatabaseReference mDatabaseUser;
   // private FirebaseAuth.AuthStateListener mAuthListner;
   private StorageReference mStorage;


    private ProgressDialog mProgress;
    private Uri imageUri;


    private int mYear;
    private int mMonth;
    private int mDay;


    private final int SELECT_PHOTO = 1;

    private String name;
    private String gander = "";
    private String email;
    private String password;
    private String phoneNumber;
    private String district;
    private String subDistrict;
    private String location;

    private Spinner spinnerAddLocation;
    private Spinner spinnerAddSubLocation;


    private String[] subLocationList;
    private String[] locationList;

    private SubDistrictSelector subDistrictSelector;


    private DatePickerFragment datePickerFragment = new DatePickerFragment();

    private byte[] byteArray;

    private int emailValidity = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account);
        imageViewNewAccount = (ImageView) findViewById(R.id.new_account_imageView);
        buttonNewAccountImage = (Button) findViewById(R.id.new_account_image_button);
        buttonCreateNewAccount = (Button) findViewById(R.id.button_create_new_account);
        buttonDatePicker = (Button) findViewById(R.id.button_new_account_user_date_of_birth);
        radioNewAccountButtonMale =(RadioButton) findViewById(R.id.radio_new_account_male);
        radioButtonNewAccountFemale =(RadioButton) findViewById(R.id.radio_new_account_female);
        editTextNewAccountName =(EditText) findViewById(R.id.editText_new_account_user_name);
        editTextNewAccountEmail =(EditText) findViewById(R.id.editText_new_account_user_email);
        editTextNewAccountPassword =(EditText) findViewById(R.id.editText_new_account_user_password);
        editTextNewAccountRepeatPassword=(EditText) findViewById(R.id.editText_new_account_user_password_repeat);
        editTextNewAccountPhoneNumber =(EditText) findViewById(R.id.editText_new_account_user_phone);
        textViewDateOfBirth = (TextView) findViewById(R.id.textView_new_account_date_of_birth);
        spinnerAddLocation = (Spinner) findViewById(R.id.spinnerAddLocationAccount);
        spinnerAddSubLocation = (Spinner) findViewById(R.id.spinnerAddSubLocationAccount);


        locationList = getResources().getStringArray(R.array.district_bangladesh);
        subDistrictSelector = new SubDistrictSelector();


        mAuth = FirebaseAuth.getInstance();
        mProgress = new ProgressDialog(this);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mStorage = FirebaseStorage.getInstance().getReference();


        buttonDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                datePickerFragment.show(getSupportFragmentManager(), "datePicker");

            }
        });


        buttonNewAccountImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_PICK);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, SELECT_PHOTO);
            }
        });




        buttonCreateNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegister();

            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.spinner_item, locationList) {

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                if (position % 2 == 0) { // we're on an even row
                    view.setBackgroundColor(Color.parseColor("#EEEEEE"));
                } else {
                    view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                }
                return view;

            }

        };

        spinnerAddLocation.setAdapter(new SpinnerCustomAdapter().arrayListAdapterForSpinner(
                getResources().getStringArray(R.array.district_bangladesh)
                , getApplicationContext()));

        spinnerAddLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                spinnerAddSubLocation.setAdapter(new SpinnerCustomAdapter().arrayListAdapterForSpinner(
                        subDistrictSelector.GetLocation(getApplicationContext()
                                , spinnerAddLocation.getSelectedItem().toString().trim())
                        ,getApplicationContext()));


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


        mYear = year;
        mMonth = month;
        mDay = dayOfMonth;
        updateDate();

    }

    private void updateDate() {
        textViewDateOfBirth.setText(new StringBuilder()
                // Month is 0 based so add 1
                .append(mDay).append("-")
                .append(mMonth + 1).append("-")
                .append(mYear).append(" "));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {

                    imageUri = data.getData();
                    CropImage.activity(imageUri)
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .setAspectRatio(1, 1)
                            .setActivityTitle("Crop Image")
                            .start(this);


                }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                imageViewNewAccount.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }


    }

    private void startRegister()
    {


        if (imageUri == null)
        {

            AlertDialog.Builder myAlert = new AlertDialog.Builder(CreateNewAccountActivity.this);
            myAlert.setMessage("profile picture is missing")
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

            InputStream imageStream = null;
            try {
                imageStream = getContentResolver().openInputStream(
                        imageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            Bitmap bmp = BitmapFactory.decodeStream(imageStream);
            int hoi = bmp.getHeight();
            int woi = bmp.getWidth();
            bmp = getResizedBitmap(bmp, woi, hoi);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);


            byteArray = stream.toByteArray();

            try {
                stream.close();
                stream = null;
            } catch (IOException e) {

                e.printStackTrace();
            }


            if (editTextNewAccountName.length() == 0)
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
            } else {

                name = editTextNewAccountName.getText().toString().trim();

                String emailCheck = editTextNewAccountEmail.getText().toString().trim();


                if (emailCheck.length() == 0)
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
                } else if (!emailCheck.contains("@"))
                {
                    AlertDialog.Builder myAlert = new AlertDialog.Builder(CreateNewAccountActivity.this);
                    myAlert.setMessage("e-mail is not valid")
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


                    mQuary = mDatabase.orderByChild("email").equalTo(editTextNewAccountEmail.getText().toString().trim());
                    mQuary.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (dataSnapshot.getValue() == null) {
                                email = editTextNewAccountEmail.getText().toString().trim();
                                emailValidity = 0;
                            } else {
                                emailValidity = 1;
                                AlertDialog.Builder myAlert = new AlertDialog.Builder(CreateNewAccountActivity.this);
                                myAlert.setMessage("e-mail already exist")
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

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                    if (emailValidity == 0) {
                        if (editTextNewAccountPassword.length() == 0)
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
                        } else if (editTextNewAccountPassword.length() <= 7)
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
                        } else if (!editTextNewAccountPassword.getText().toString().equals(editTextNewAccountRepeatPassword.getText().toString())) {
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
                        } else {
                            password = editTextNewAccountPassword.getText().toString().trim();

                            if (!radioNewAccountButtonMale.isChecked() && !radioButtonNewAccountFemale.isChecked()) {
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

                                if (radioNewAccountButtonMale.isChecked()) {
                                    gander = "Male";
                                } else if (radioButtonNewAccountFemale.isChecked()) {
                                    gander = "Female";
                                }

                                String phoneCheck = editTextNewAccountPhoneNumber.getText().toString().trim();
                                int validPhone = 0;

                                if (phoneCheck.length() == 0) {
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
                                } else if (phoneCheck.length() == 11 || phoneCheck.length() == 14) {

                                    if (phoneCheck.length() == 11) {
                                        if (phoneCheck.startsWith("017") ||
                                                phoneCheck.startsWith("018") ||
                                                phoneCheck.startsWith("019") ||
                                                phoneCheck.startsWith("015") ||
                                                phoneCheck.startsWith("011")) {
                                            phoneNumber = phoneCheck;
                                            validPhone = 1;
                                        } else {
                                            AlertDialog.Builder myAlert = new AlertDialog.Builder(CreateNewAccountActivity.this);
                                            myAlert.setMessage("phone number is not valid")
                                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.dismiss();
                                                        }
                                                    })
                                                    .create();
                                            myAlert.show();
                                        }
                                    } else if (phoneCheck.length() == 14) {
                                        if (phoneCheck.startsWith("+88017") ||
                                                phoneCheck.startsWith("+88018") ||
                                                phoneCheck.startsWith("+88019") ||
                                                phoneCheck.startsWith("+88015") ||
                                                phoneCheck.startsWith("+88011")) {
                                            phoneNumber = phoneCheck;
                                            validPhone = 1;
                                        }
                                    }

                                    if (validPhone == 1) {

                                        phoneNumber = phoneCheck;
                                        if (spinnerAddLocation.getSelectedItem().equals("All") || spinnerAddSubLocation.getSelectedItem().equals("All")) {
                                            AlertDialog.Builder myAlert = new AlertDialog.Builder(CreateNewAccountActivity.this);
                                            myAlert.setTitle("Location")
                                                    .setMessage("fill location field")
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
                                            district = spinnerAddLocation.getSelectedItem().toString();
                                            subDistrict = spinnerAddSubLocation.getSelectedItem().toString();

                                            location = district + " , " + subDistrict;

                                            createAccount();


                                        }
                                    }


                                } else {

                                    AlertDialog.Builder myAlert = new AlertDialog.Builder(CreateNewAccountActivity.this);
                                    myAlert.setMessage("phone Number is not valid")
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


                        }
                    }


                }
            }


        }


    }

    private void createAccount() {


        mProgress.setMessage("Uploading...");
        mProgress.show();

        StorageReference filePath = mStorage.child("ProfilePicture").child(mDatabase.child("profile_picture").push().getKey());

        filePath.putBytes(byteArray).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests")
                final Uri downloadUri = Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getMetadata()).getReference()).getDownloadUrl().getResult();

                mProgress.setMessage("Singing up...");
                mProgress.show();

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String userID = mAuth.getCurrentUser().getUid();
                            DatabaseReference currentUserDB = mDatabase.child(userID);

                            currentUserDB.child("name").setValue(name);
                            currentUserDB.child("image").setValue(downloadUri.toString());
                            currentUserDB.child("gander").setValue(gander);
                            currentUserDB.child("email").setValue(email);
                            currentUserDB.child("phone").setValue(phoneNumber);
                            currentUserDB.child("district").setValue(district);
                            currentUserDB.child("subDistrict").setValue(subDistrict);
                            currentUserDB.child("location").setValue(location);
                            currentUserDB.child("crimeLocation").setValue("");
                            mProgress.dismiss();


                            mAuth.signInWithEmailAndPassword(email, password);
                            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        AlertDialog.Builder myAlert = new AlertDialog.Builder(CreateNewAccountActivity.this);
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
                                }
                            });

                        }
                    }
                });




            }
        });
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        while (newWidth > 450 || newHeight > 450) {
            newHeight = newHeight / 2;
            newWidth = newWidth / 2;
        }
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }




}
