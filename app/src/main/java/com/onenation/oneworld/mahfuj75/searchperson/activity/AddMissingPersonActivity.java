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
import android.icu.text.DateFormat;
import android.net.Uri;
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
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.onenation.oneworld.mahfuj75.searchperson.R;
import com.onenation.oneworld.mahfuj75.searchperson.dialog.DatePickerFragment;
import com.onenation.oneworld.mahfuj75.searchperson.objectclass.SubDistrictSelector;
import com.onenation.oneworld.mahfuj75.searchperson.custom.SpinnerCustomAdapter;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Objects;

public class AddMissingPersonActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {


    private String UPDATE_CODE = null;


    //AIzaSyAiZG2tvi9GyM78YcE3Y2C6OOgJrbx1wmQ

    private RadioButton radioButtonFound ;
    private RadioButton radioButtonLost ;
    private RadioGroup radioGroup;

    private Button buttonMissingPersonSubmit;
    private Button buttonMissingPersonImage;
    private Button buttonMissingPersonMissingDate;

    private EditText editTextMissingPersonName;
    private EditText editTextMissingPersonRelative;
    private EditText editTextMissingPersonAge;
    private EditText editTextMissingPersonDescription;


    private TextView textViewMissingPersonDate;


    private ImageView imageViewAddMissingPerson;
    private Spinner spinnerFoot;
    private Spinner spinnerInch;
    private Spinner spinnerAddLocation;
    private Spinner spinnerAddSubLocation;
    private String subDistrict;
    private String[] subLocationList;
    private String[] locationList;

    private final int SELECT_PHOTO = 1;
    private StorageReference mStorage;
    private Uri imageUri;
    private Uri cropImageUri;
    private SubDistrictSelector subDistrictSelector;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Post");
    private DatabaseReference myRefUser = database.getReference("Users");
    private ProgressDialog mProgress;
    private FirebaseAuth mAuth;

    private String[] spinnerListFoot = {"0", "1", "2", "3", "4", "5", "6", "7"};
    private String[] spinnerListInch = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};

    private String uid;
    private String userName;
    private String userEmail;
    private String userPhoneNumber;

    private String imageURL;

    private int RUNTIME = 1;


    private int mYear;
    private int mMonth;
    private int mDay;


    private String missingDateDay;
    private String missingDateMonth;
    private String missingDateYear;

    private DatePickerFragment datePickerFragment = new DatePickerFragment();

    private FirebaseStorage storage = FirebaseStorage.getInstance();

    private ArrayAdapter<String> subAdapter;
    private int selection = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_missing_person);
        imageUri = null;
        cropImageUri = null;


        if (getIntent().hasExtra("update")) {
            UPDATE_CODE = getIntent().getExtras().getString("update");
            //Toast.makeText(getApplicationContext(), UPDATE_CODE, Toast.LENGTH_LONG).show();
        }


        locationList = getResources().getStringArray(R.array.district_bangladesh);
        subDistrictSelector = new SubDistrictSelector();
        mStorage = FirebaseStorage.getInstance().getReference();
        mProgress = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();


        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        myRefUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userPhoneNumber = dataSnapshot.child(uid).child("phone").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        subDistrict = "";
        radioButtonFound = (RadioButton) findViewById(R.id.radioButton_found);
        radioButtonLost =(RadioButton) findViewById(R.id.radioButton_lost);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroupAddMissingPerson);

        buttonMissingPersonSubmit =(Button) findViewById(R.id.button_missingPerson_submit);
        buttonMissingPersonImage =(Button) findViewById(R.id.addMissingPersonImageButton);

        spinnerFoot = (Spinner) findViewById(R.id.spinnerFoot);
        spinnerInch = (Spinner) findViewById(R.id.spinnerInch);
        spinnerAddLocation = (Spinner) findViewById(R.id.spinnerAddLocation);
        spinnerAddSubLocation = (Spinner) findViewById(R.id.spinnerAddSubLocation);

        editTextMissingPersonName =(EditText) findViewById(R.id.editText_missingPerson_name);
        editTextMissingPersonRelative =(EditText) findViewById(R.id.editText_missingPerson_relative);
        editTextMissingPersonAge =(EditText) findViewById(R.id.editText_missingPerson_age);
        editTextMissingPersonDescription =(EditText) findViewById(R.id.editText_missingPerson_description);

        textViewMissingPersonDate = (TextView) findViewById(R.id.textView_missingPerson_missingDate);


        buttonMissingPersonMissingDate = (Button) findViewById(R.id.button_missingPerson_missingDate);

        imageViewAddMissingPerson = (ImageView) findViewById(R.id.addMissingPersonImageView);


        buttonMissingPersonMissingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        spinnerFoot.setAdapter(new SpinnerCustomAdapter().arrayListAdapterForSpinner(spinnerListFoot,getApplicationContext()));

        spinnerInch.setAdapter(new SpinnerCustomAdapter().arrayListAdapterForSpinner(spinnerListInch,getApplicationContext()));

        spinnerAddLocation.setAdapter(new SpinnerCustomAdapter().arrayListAdapterForSpinner(
                getResources().getStringArray(R.array.district_bangladesh)
                ,getApplicationContext()));


        if (UPDATE_CODE != null && UPDATE_CODE.length() != 0) {

            buttonMissingPersonSubmit.setText("Update");
            setData();

        } else {
            subLocationSelect();
        }

        buttonMissingPersonImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);

            }
        });


        buttonMissingPersonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (radioButtonLost.isChecked())
                {

                    if (UPDATE_CODE == null || UPDATE_CODE.isEmpty()) {
                        lostPerson();
                    } else {
                        updateLost();
                    }

                } else if (radioButtonFound.isChecked()) {
                    if (UPDATE_CODE == null || UPDATE_CODE.isEmpty()) {
                        foundPerson();
                    } else {
                        updateFound();
                    }

                } else {
                    AlertDialog.Builder myAlert = new AlertDialog.Builder(AddMissingPersonActivity.this);
                    myAlert.setTitle("Missing")
                            .setMessage("Please check found or lost")
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


    private void setData() {
        DatabaseReference mUpdate = myRef.child(UPDATE_CODE);
        mUpdate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    String imageUrl = dataSnapshot.child("imageUrl").getValue().toString();
                    imageURL = imageUrl;

                    String lostFound = dataSnapshot.child("lostFound").getValue().toString();
                    String name = dataSnapshot.child("missingPersonName").getValue().toString();
                    String parentsName = dataSnapshot.child("relativeName").getValue().toString();
                    String age = dataSnapshot.child("age").getValue().toString();
                    String missingDate = dataSnapshot.child("missingDate").getValue().toString();

                    String heightFoot = dataSnapshot.child("heightFoot").getValue().toString();
                    String heightInch = dataSnapshot.child("heightInch").getValue().toString();
                    String description = dataSnapshot.child("description").getValue().toString();

                    String district = dataSnapshot.child("district").getValue().toString();
                    subDistrict = dataSnapshot.child("subDistrict").getValue().toString();


                    missingDateDay = dataSnapshot.child("missingDateDay").getValue().toString();
                    missingDateMonth = dataSnapshot.child("missingDateMonth").getValue().toString();
                    missingDateYear = dataSnapshot.child("missingDateYear").getValue().toString();

                    if (imageUrl == null) {
                        imageViewAddMissingPerson.setImageResource(R.drawable.anonymous);
                    } else {
                        Picasso.with(getApplicationContext()).load(imageUrl)
                                .error(R.drawable.progress_animation)
                                .into(imageViewAddMissingPerson);
                    }
                    if (lostFound.equals("Lost")) {
                        radioButtonLost.setChecked(true);


                    } else if (lostFound.equals("Found")) {
                        radioButtonFound.setChecked(true);
                    }


                    if (!name.isEmpty()) {
                        editTextMissingPersonName.setText(name);
                    }

                    if (!parentsName.isEmpty()) {
                        editTextMissingPersonRelative.setText(parentsName);
                    }

                    if (!age.isEmpty()) {
                        editTextMissingPersonAge.setText(age);
                    }
                    if (!missingDate.isEmpty()) {
                        textViewMissingPersonDate.setText(missingDate);
                    }

                    if (!description.isEmpty()) {
                        editTextMissingPersonDescription.setText(description);
                    }


                    if (!heightFoot.isEmpty()) {
                        spinnerFoot.setSelection(Integer.parseInt(heightFoot));
                        if (!heightInch.isEmpty()) {
                            spinnerInch.setSelection(Integer.parseInt(heightInch));
                        }
                    }

                    if (!district.isEmpty()) {


                        for (int i = 0; i < locationList.length; i++) {
                            if (locationList[i].equals(district)) {
                                spinnerAddLocation.setSelection(i);

                                subLocationList = subDistrictSelector.GetLocation(getApplicationContext(), district.trim());
                                subLocationSelect();


                            }
                        }


                    }
                } catch (Exception e)
                {
                    finish();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void subLocationSelect() {


        //Toast.makeText(getApplicationContext(),selection,Toast.LENGTH_LONG).show();


        spinnerAddLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                String location = spinnerAddLocation.getSelectedItem().toString().trim();
                //Toast.makeText(getApplicationContext(), location,Toast.LENGTH_LONG).show();

                subLocationList = subDistrictSelector.GetLocation(getApplicationContext(), location);

                if (subDistrict.length() != 0) {
                    for (int j = 0; j < subLocationList.length; j++)
                    {
                        if (subLocationList[j].equals(subDistrict)) {

                            selection = j;
                            subDistrict = "";
                            // Toast.makeText(getApplicationContext(),selection+subDistrict,Toast.LENGTH_LONG).show();
                        }
                    }


                } else {
                    selection = 0;
                }


                subAdapter = new ArrayAdapter<String>(getApplicationContext(),
                        R.layout.spinner_item, subLocationList) {

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
                spinnerAddSubLocation.setAdapter(subAdapter);
                spinnerAddSubLocation.setSelection(selection);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });
        if (subDistrict.length() == 0) {
            spinnerAddSubLocation.setSelection(0);
        }


    }


    private void updateLost() {

        StorageReference filePath = mStorage.child("MissingPerson").child(myRef.child("Image").push().getKey());

        final DatabaseReference mUpdate = myRef.child(UPDATE_CODE);


        if (imageUri == null) {
            if (editTextMissingPersonName.getText().length() == 0 || editTextMissingPersonAge.getText().length() == 0 ||
                    editTextMissingPersonRelative.getText().length() == 0 ||
                    (spinnerFoot.getSelectedItem().toString() == "0" && spinnerInch.getSelectedItem().toString() == "0") ||
                    textViewMissingPersonDate.getText().toString().equals("DD-MM-YYYY") ||
                    spinnerAddLocation.getSelectedItem().toString().equals("All")) {
                mProgress.dismiss();

                AlertDialog.Builder myAlert = new AlertDialog.Builder(AddMissingPersonActivity.this);
                myAlert.setMessage("Something is missing")
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


                String missingPersonName;
                String relativeName;
                String age = editTextMissingPersonAge.getText().toString().trim();

                String missingDate = textViewMissingPersonDate.getText().toString().trim();

                String heightFoot = spinnerFoot.getSelectedItem().toString().trim();
                String heightInch = spinnerInch.getSelectedItem().toString().trim();
                String height = spinnerFoot.getSelectedItem().toString() + "' " + spinnerInch.getSelectedItem().toString().trim() + "\"";
                String district = spinnerAddLocation.getSelectedItem().toString().trim();
                String subDistrict = spinnerAddSubLocation.getSelectedItem().toString().trim();
                String description = editTextMissingPersonDescription.getText().toString();
                String location;


                if (subDistrict.equals("All")) {
                    location = district;
                } else {
                    location = district + ", " + subDistrict;
                }


                missingPersonName = editTextMissingPersonName.getText().toString().trim();
                relativeName = editTextMissingPersonRelative.getText().toString().trim();


                mUpdate.child("uid").setValue(uid);
                mUpdate.child("userName").setValue(userName);
                mUpdate.child("userPhoneNumber").setValue(userPhoneNumber);
                mUpdate.child("userEmail").setValue(userEmail);

                mUpdate.child("missingPersonName").setValue(missingPersonName);
                mUpdate.child("relativeName").setValue(relativeName);
                mUpdate.child("age").setValue(age);

                mUpdate.child("missingDateDay").setValue(missingDateDay);
                mUpdate.child("missingDateMonth").setValue(missingDateMonth);
                mUpdate.child("missingDateYear").setValue(missingDateYear);
                mUpdate.child("missingDate").setValue(missingDate);

                mUpdate.child("heightFoot").setValue(heightFoot);
                mUpdate.child("heightInch").setValue(heightInch);
                mUpdate.child("height").setValue(height);

                mUpdate.child("district").setValue(district);
                mUpdate.child("subDistrict").setValue(subDistrict);
                mUpdate.child("description").setValue(description);
                mUpdate.child("location").setValue(location);
                mUpdate.child("lostFound").setValue("Lost");
                mUpdate.child("districtSelectionLF").setValue("Lost" + " , " + district);
                mUpdate.child("subDistrictSelectionLF").setValue("Lost" + " , " + subDistrict);

                mProgress.dismiss();

                AlertDialog.Builder myAlert = new AlertDialog.Builder(AddMissingPersonActivity.this);
                myAlert.setMessage("Update Complete")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .create();
                myAlert.show();


            }


        } else if (imageUri != null) {


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


            byte[] byteArray = stream.toByteArray();
            try {
                stream.close();
                stream = null;
            } catch (IOException e) {

                e.printStackTrace();
            }

            mProgress.setMessage("Updating...");
            mProgress.show();
            filePath.putBytes(byteArray).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                    if (editTextMissingPersonName.getText().length() == 0 || editTextMissingPersonAge.getText().length() == 0 ||
                            editTextMissingPersonRelative.getText().length() == 0 ||
                            (spinnerFoot.getSelectedItem().toString() == "0" && spinnerInch.getSelectedItem().toString() == "0") ||
                            textViewMissingPersonDate.getText().toString().equals("DD-MM-YYYY") ||
                            spinnerAddLocation.getSelectedItem().toString().equals("All"))
                    {
                        mProgress.dismiss();

                        AlertDialog.Builder myAlert = new AlertDialog.Builder(AddMissingPersonActivity.this);
                        myAlert.setMessage("Something is missing")
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


                        String missingPersonName;
                        String relativeName;
                        String age = editTextMissingPersonAge.getText().toString().trim();

                        String missingDate = textViewMissingPersonDate.getText().toString().trim();

                        String heightFoot = spinnerFoot.getSelectedItem().toString().trim();
                        String heightInch = spinnerInch.getSelectedItem().toString().trim();
                        String height = spinnerFoot.getSelectedItem().toString() + "' " + spinnerInch.getSelectedItem().toString().trim() + "\"";
                        String district = spinnerAddLocation.getSelectedItem().toString().trim();
                        String subDistrict = spinnerAddSubLocation.getSelectedItem().toString().trim();
                        String description = editTextMissingPersonDescription.getText().toString();
                        String location;


                        if (subDistrict.equals("All")) {
                            location = district;
                        } else {
                            location = district + ", " + subDistrict;
                        }


                        missingPersonName = editTextMissingPersonName.getText().toString().trim();
                        relativeName = editTextMissingPersonRelative.getText().toString().trim();

                        Uri downloadUri = Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getMetadata()).getReference()).getDownloadUrl().getResult();
                        mUpdate.child("imageUrl").setValue(downloadUri.toString());


                        mUpdate.child("missingPersonName").setValue(missingPersonName);
                        mUpdate.child("relativeName").setValue(relativeName);
                        mUpdate.child("age").setValue(age);

                        mUpdate.child("missingDateDay").setValue(missingDateDay);
                        mUpdate.child("missingDateMonth").setValue(missingDateMonth);
                        mUpdate.child("missingDateYear").setValue(missingDateYear);
                        mUpdate.child("missingDate").setValue(missingDate);

                        mUpdate.child("heightFoot").setValue(heightFoot);
                        mUpdate.child("heightInch").setValue(heightInch);
                        mUpdate.child("height").setValue(height);

                        mUpdate.child("district").setValue(district);
                        mUpdate.child("subDistrict").setValue(subDistrict);
                        mUpdate.child("description").setValue(description);
                        mUpdate.child("location").setValue(location);
                        mUpdate.child("lostFound").setValue("Lost");
                        mUpdate.child("districtSelectionLF").setValue("Lost" + " , " + district);
                        mUpdate.child("subDistrictSelectionLF").setValue("Lost" + " , " + subDistrict);


                    }


                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mProgress.dismiss();
                    // Create a storage reference from our app
                    StorageReference storageRef = storage.getReferenceFromUrl(imageURL);

                    // Create a reference to the file to delete
                    //StorageReference desertRef = storageRef.child("images/desert.jpg");

                    storageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            AlertDialog.Builder myAlert = new AlertDialog.Builder(AddMissingPersonActivity.this);
                            myAlert.setMessage("Update Complete")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    })
                                    .create();
                            myAlert.show();
                        }
                    });

                }
            });

        }

    }


    public void updateFound() {
        StorageReference filePath = mStorage.child("MissingPerson").child(myRef.child("Image").push().getKey());
        final DatabaseReference mUpdate = myRef.child(UPDATE_CODE);


        if (imageUri != null) {


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


            byte[] byteArray = stream.toByteArray();
            try {
                stream.close();
                stream = null;
            } catch (IOException e) {

                e.printStackTrace();
            }

            mProgress.setMessage("Updating...");
            mProgress.show();
            filePath.putBytes(byteArray).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                    if (editTextMissingPersonAge.getText().length() == 0 || (spinnerFoot.getSelectedItem().toString() == "0" && spinnerInch.getSelectedItem().toString() == "0") ||
                            textViewMissingPersonDate.getText().toString().equals("DD-MM-YYYY") ||
                            spinnerAddLocation.getSelectedItem().toString().equals("All")) {
                        mProgress.dismiss();

                        AlertDialog.Builder myAlert = new AlertDialog.Builder(AddMissingPersonActivity.this);
                        myAlert.setTitle("Missing")
                                .setMessage("Age, Found-Date, Location & Height is missing")
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


                        String missingPersonName;
                        String relativeName;
                        String age = editTextMissingPersonAge.getText().toString().trim();

                        String missingDate = textViewMissingPersonDate.getText().toString().trim();

                        String heightFoot = spinnerFoot.getSelectedItem().toString().trim();
                        String heightInch = spinnerInch.getSelectedItem().toString().trim();
                        String location;
                        String height = spinnerFoot.getSelectedItem().toString() + "' " + spinnerInch.getSelectedItem().toString().trim() + "\"";
                        String district = spinnerAddLocation.getSelectedItem().toString().trim();
                        String subDistrict = spinnerAddSubLocation.getSelectedItem().toString().trim();
                        String description = editTextMissingPersonDescription.getText().toString();


                        missingPersonName = editTextMissingPersonName.getText().toString().trim();
                        relativeName = editTextMissingPersonRelative.getText().toString().trim();

                        if (missingPersonName.length() == 0) {
                            missingPersonName = " ";
                        }


                        if (relativeName.length() == 0) {
                            relativeName = " ";
                        }
                        if (subDistrict.equals("All")) {
                            location = district;
                        } else {
                            location = district + ", " + subDistrict;
                        }

                        Uri downloadUri = Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getMetadata()).getReference()).getDownloadUrl().getResult();
                        mUpdate.child("imageUrl").setValue(downloadUri.toString());

                        mUpdate.child("uid").setValue(uid);
                        mUpdate.child("userName").setValue(userName);
                        mUpdate.child("userPhoneNumber").setValue(userPhoneNumber);
                        mUpdate.child("userEmail").setValue(userEmail);

                        mUpdate.child("missingPersonName").setValue(missingPersonName);
                        mUpdate.child("relativeName").setValue(relativeName);
                        mUpdate.child("age").setValue(age);

                        mUpdate.child("missingDateDay").setValue(missingDateDay);
                        mUpdate.child("missingDateMonth").setValue(missingDateMonth);
                        mUpdate.child("missingDateYear").setValue(missingDateYear);
                        mUpdate.child("missingDate").setValue(missingDate);

                        mUpdate.child("heightFoot").setValue(heightFoot);
                        mUpdate.child("heightInch").setValue(heightInch);
                        mUpdate.child("height").setValue(height);

                        mUpdate.child("district").setValue(district);
                        mUpdate.child("subDistrict").setValue(subDistrict);
                        mUpdate.child("description").setValue(description);
                        mUpdate.child("location").setValue(location);
                        mUpdate.child("lostFound").setValue("Found");
                        mUpdate.child("districtSelectionLF").setValue("Found" + " , " + district);
                        mUpdate.child("subDistrictSelectionLF").setValue("Found" + " , " + subDistrict);

                    }


                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mProgress.dismiss();
                    // Create a storage reference from our app
                    StorageReference storageRef = storage.getReferenceFromUrl(imageURL);

                    // Create a reference to the file to delete
                    //StorageReference desertRef = storageRef.child("images/desert.jpg");

                    storageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            AlertDialog.Builder myAlert = new AlertDialog.Builder(AddMissingPersonActivity.this);
                            myAlert.setMessage("Update Complete")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    })
                                    .create();
                            myAlert.show();
                        }
                    });


                }
            });

        } else if (imageUri == null) {
            if (editTextMissingPersonAge.getText().length() == 0 || (spinnerFoot.getSelectedItem().toString() == "0" && spinnerInch.getSelectedItem().toString() == "0") ||
                    textViewMissingPersonDate.getText().toString().equals("DD-MM-YYYY") ||
                    spinnerAddLocation.getSelectedItem().toString().equals("All")) {
                mProgress.dismiss();

                AlertDialog.Builder myAlert = new AlertDialog.Builder(AddMissingPersonActivity.this);
                myAlert.setTitle("Missing")
                        .setMessage("Age, Found-Date, Location & Height is missing")
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

                mProgress.setMessage("Updating...");
                mProgress.show();

                String missingPersonName;
                String relativeName;
                String age = editTextMissingPersonAge.getText().toString().trim();

                String missingDate = textViewMissingPersonDate.getText().toString().trim();

                String heightFoot = spinnerFoot.getSelectedItem().toString().trim();
                String heightInch = spinnerInch.getSelectedItem().toString().trim();
                String location;
                String height = spinnerFoot.getSelectedItem().toString() + "' " + spinnerInch.getSelectedItem().toString().trim() + "\"";
                String district = spinnerAddLocation.getSelectedItem().toString().trim();
                String subDistrict = spinnerAddSubLocation.getSelectedItem().toString().trim();
                String description = editTextMissingPersonDescription.getText().toString();


                missingPersonName = editTextMissingPersonName.getText().toString().trim();
                relativeName = editTextMissingPersonRelative.getText().toString().trim();

                if (missingPersonName.length() == 0) {
                    missingPersonName = " ";
                }


                if (relativeName.length() == 0) {
                    relativeName = " ";
                }
                if (subDistrict.equals("All")) {
                    location = district;
                } else {
                    location = district + ", " + subDistrict;
                }


                mUpdate.child("uid").setValue(uid);
                mUpdate.child("userName").setValue(userName);
                mUpdate.child("userPhoneNumber").setValue(userPhoneNumber);
                mUpdate.child("userEmail").setValue(userEmail);

                mUpdate.child("missingPersonName").setValue(missingPersonName);
                mUpdate.child("relativeName").setValue(relativeName);
                mUpdate.child("age").setValue(age);

                mUpdate.child("missingDateDay").setValue(missingDateDay);
                mUpdate.child("missingDateMonth").setValue(missingDateMonth);
                mUpdate.child("missingDateYear").setValue(missingDateYear);
                mUpdate.child("missingDate").setValue(missingDate);

                mUpdate.child("heightFoot").setValue(heightFoot);
                mUpdate.child("heightInch").setValue(heightInch);
                mUpdate.child("height").setValue(height);

                mUpdate.child("district").setValue(district);
                mUpdate.child("subDistrict").setValue(subDistrict);
                mUpdate.child("description").setValue(description);
                mUpdate.child("location").setValue(location);
                mUpdate.child("lostFound").setValue("Found");
                mUpdate.child("districtSelectionLF").setValue("Found" + " , " + district);
                mUpdate.child("subDistrictSelectionLF").setValue("Found" + " , " + subDistrict);


                mProgress.dismiss();

                AlertDialog.Builder myAlert = new AlertDialog.Builder(AddMissingPersonActivity.this);
                myAlert.setMessage("Update Complete")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .create();
                myAlert.show();

            }


        }

    }

    public void foundPerson() {

        StorageReference filePath = mStorage.child("MissingPerson").child(myRef.child("Image").push().getKey());

        if (imageUri == null) {
            AlertDialog.Builder myAlert = new AlertDialog.Builder(AddMissingPersonActivity.this);
            myAlert.setTitle("Missing")
                    .setMessage("image is missing")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            myAlert.show();
        } else if (imageUri != null) {


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


            byte[] byteArray = stream.toByteArray();
            try {
                stream.close();
                stream = null;
            } catch (IOException e) {

                e.printStackTrace();
            }

            mProgress.setMessage("Uploading...");
            mProgress.show();
            filePath.putBytes(byteArray).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                    if (editTextMissingPersonAge.getText().length() == 0 || (spinnerFoot.getSelectedItem().toString() == "0" && spinnerInch.getSelectedItem().toString() == "0") ||
                            textViewMissingPersonDate.getText().toString().equals("DD-MM-YYYY") ||
                            spinnerAddLocation.getSelectedItem().toString().equals("All")) {
                        mProgress.dismiss();

                        AlertDialog.Builder myAlert = new AlertDialog.Builder(AddMissingPersonActivity.this);
                        myAlert.setTitle("Missing")
                                .setMessage("Age, Found-Date, Location & Height is missing")
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


                        myRef = myRef.push();

                        String postDate = java.text.DateFormat.getDateInstance(DateFormat.MEDIUM).format(Calendar.getInstance().getTime());
                        String postTime = java.text.DateFormat.getTimeInstance(DateFormat.MEDIUM).format(Calendar.getInstance().getTime());

                        String missingPersonName;
                        String relativeName;
                        String age = editTextMissingPersonAge.getText().toString().trim();

                        String missingDate = textViewMissingPersonDate.getText().toString().trim();

                        String heightFoot = spinnerFoot.getSelectedItem().toString().trim();
                        String heightInch = spinnerInch.getSelectedItem().toString().trim();
                        String location;
                        String height = spinnerFoot.getSelectedItem().toString() + "' " + spinnerInch.getSelectedItem().toString().trim() + "\"";
                        String district = spinnerAddLocation.getSelectedItem().toString().trim();
                        String subDistrict = spinnerAddSubLocation.getSelectedItem().toString().trim();
                        String description = editTextMissingPersonDescription.getText().toString();


                        missingPersonName = editTextMissingPersonName.getText().toString().trim();
                        relativeName = editTextMissingPersonRelative.getText().toString().trim();

                        if (missingPersonName.length() == 0) {
                            missingPersonName = " ";
                        }


                        if (relativeName.length() == 0) {
                            relativeName = " ";
                        }
                        if (subDistrict.equals("All")) {
                            location = district;
                        } else {
                            location = district + ", " + subDistrict;
                        }

                        Uri downloadUri = Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getMetadata()).getReference()).getDownloadUrl().getResult();
                        myRef.child("imageUrl").setValue(downloadUri.toString());

                        myRef.child("postDate").setValue(postDate);
                        myRef.child("postTime").setValue(postTime);

                        myRef.child("uid").setValue(uid);
                        myRef.child("userName").setValue(userName);
                        myRef.child("userPhoneNumber").setValue(userPhoneNumber);
                        myRef.child("userEmail").setValue(userEmail);

                        myRef.child("missingPersonName").setValue(missingPersonName);
                        myRef.child("relativeName").setValue(relativeName);
                        myRef.child("age").setValue(age);

                        myRef.child("missingDateDay").setValue(missingDateDay);
                        myRef.child("missingDateMonth").setValue(missingDateMonth);
                        myRef.child("missingDateYear").setValue(missingDateYear);
                        myRef.child("missingDate").setValue(missingDate);

                        myRef.child("heightFoot").setValue(heightFoot);
                        myRef.child("heightInch").setValue(heightInch);
                        myRef.child("height").setValue(height);

                        myRef.child("district").setValue(district);
                        myRef.child("subDistrict").setValue(subDistrict);
                        myRef.child("description").setValue(description);
                        myRef.child("location").setValue(location);
                        myRef.child("lostFound").setValue("Found");
                        myRef.child("districtSelectionLF").setValue("Found" + " , " + district);
                        myRef.child("subDistrictSelectionLF").setValue("Found" + " , " + subDistrict);

                    }


                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mProgress.dismiss();

                    AlertDialog.Builder myAlert = new AlertDialog.Builder(AddMissingPersonActivity.this);
                    myAlert.setMessage("Missing Person Added")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(AddMissingPersonActivity.this, TabViewActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            })
                            .create();
                    myAlert.show();
                }
            });

        }


    }

    private void lostPerson() {
        StorageReference filePath = mStorage.child("MissingPerson").child(Objects.requireNonNull(myRef.child("Image").push().getKey()));

        if (imageUri == null) {
            AlertDialog.Builder myAlert = new AlertDialog.Builder(AddMissingPersonActivity.this);
            myAlert.setTitle("Missing")
                    .setMessage("image is missing")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            myAlert.show();
        } else if (imageUri != null) {


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


            byte[] byteArray = stream.toByteArray();
            try {
                stream.close();
                stream = null;
            } catch (IOException e) {

                e.printStackTrace();
            }

            mProgress.setMessage("Uploading...");
            mProgress.show();
            filePath.putBytes(byteArray).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                    if (editTextMissingPersonName.getText().length() == 0 || editTextMissingPersonAge.getText().length() == 0 ||
                            editTextMissingPersonRelative.getText().length() == 0 ||
                            (spinnerFoot.getSelectedItem().toString() == "0" && spinnerInch.getSelectedItem().toString() == "0") ||
                            textViewMissingPersonDate.getText().toString().equals("DD-MM-YYYY") ||
                            spinnerAddLocation.getSelectedItem().toString().equals("All")) {
                        mProgress.dismiss();

                        AlertDialog.Builder myAlert = new AlertDialog.Builder(AddMissingPersonActivity.this);
                        myAlert.setMessage("Something is missing")
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


                        myRef = myRef.push();

                        String postDate = java.text.DateFormat.getDateInstance(DateFormat.MEDIUM).format(Calendar.getInstance().getTime());
                        String postTime = java.text.DateFormat.getTimeInstance(DateFormat.MEDIUM).format(Calendar.getInstance().getTime());

                        String missingPersonName;
                        String relativeName;
                        String age = editTextMissingPersonAge.getText().toString().trim();

                        String missingDate = textViewMissingPersonDate.getText().toString().trim();

                        String heightFoot = spinnerFoot.getSelectedItem().toString().trim();
                        String heightInch = spinnerInch.getSelectedItem().toString().trim();
                        String height = spinnerFoot.getSelectedItem().toString() + "' " + spinnerInch.getSelectedItem().toString().trim() + "\"";
                        String district = spinnerAddLocation.getSelectedItem().toString().trim();
                        String subDistrict = spinnerAddSubLocation.getSelectedItem().toString().trim();
                        String description = editTextMissingPersonDescription.getText().toString();
                        String location;


                        if (subDistrict.equals("All")) {
                            location = district;
                        } else {
                            location = district + ", " + subDistrict;
                        }


                        missingPersonName = editTextMissingPersonName.getText().toString().trim();
                        relativeName = editTextMissingPersonRelative.getText().toString().trim();
                        @SuppressWarnings("VisibleForTests")
                        Uri downloadUri = Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getMetadata()).getReference()).getDownloadUrl().getResult();
                        myRef.child("imageUrl").setValue(downloadUri.toString());

                        myRef.child("postDate").setValue(postDate);
                        myRef.child("postTime").setValue(postTime);

                        myRef.child("uid").setValue(uid);
                        myRef.child("userName").setValue(userName);
                        myRef.child("userPhoneNumber").setValue(userPhoneNumber);
                        myRef.child("userEmail").setValue(userEmail);

                        myRef.child("missingPersonName").setValue(missingPersonName);
                        myRef.child("relativeName").setValue(relativeName);
                        myRef.child("age").setValue(age);

                        myRef.child("missingDateDay").setValue(missingDateDay);
                        myRef.child("missingDateMonth").setValue(missingDateMonth);
                        myRef.child("missingDateYear").setValue(missingDateYear);
                        myRef.child("missingDate").setValue(missingDate);

                        myRef.child("heightFoot").setValue(heightFoot);
                        myRef.child("heightInch").setValue(heightInch);
                        myRef.child("height").setValue(height);

                        myRef.child("district").setValue(district);
                        myRef.child("subDistrict").setValue(subDistrict);
                        myRef.child("description").setValue(description);
                        myRef.child("location").setValue(location);
                        myRef.child("lostFound").setValue("Lost");
                        myRef.child("districtSelectionLF").setValue("Lost" + " , " + district);
                        myRef.child("subDistrictSelectionLF").setValue("Lost" + " , " + subDistrict);


                    }


                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mProgress.dismiss();

                    AlertDialog.Builder myAlert = new AlertDialog.Builder(AddMissingPersonActivity.this);
                    myAlert.setMessage("Missing Person Added")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(AddMissingPersonActivity.this, TabViewActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            })
                            .create();
                    myAlert.show();
                }
            });

        }
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        mYear = year;
        mMonth = month;
        mDay = dayOfMonth;

        missingDateDay = String.valueOf(dayOfMonth);
        missingDateMonth = String.valueOf(month);
        missingDateYear = String.valueOf(year);
        updateDate();

    }

    private void updateDate() {
        textViewMissingPersonDate.setText(new StringBuilder()
                // Month is 0 based so add 1
                .append(mDay).append(" - ")
                .append(mMonth + 1).append(" - ")
                .append(mYear).append(" "));
    }


    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        while (newWidth > 650) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);


        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){

                    cropImageUri = imageReturnedIntent.getData();

                    CropImage.activity(cropImageUri)
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .setAspectRatio(16, 9)
                            .setActivityTitle("Crop Image")
                            .start(this);
                    //imageViewAddMissingPerson.setImageURI(imageUri);

                }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(imageReturnedIntent);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                imageUri = resultUri;
                imageViewAddMissingPerson.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }
}
