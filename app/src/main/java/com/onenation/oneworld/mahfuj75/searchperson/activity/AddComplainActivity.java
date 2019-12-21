package com.onenation.oneworld.mahfuj75.searchperson.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onenation.oneworld.mahfuj75.searchperson.R;
import com.onenation.oneworld.mahfuj75.searchperson.custom.SpinnerCustomAdapter;
import com.onenation.oneworld.mahfuj75.searchperson.dialog.DatePickerFragment;
import com.onenation.oneworld.mahfuj75.searchperson.dialog.TimePickerFragment;
import com.onenation.oneworld.mahfuj75.searchperson.objectclass.CityCorporationSelector;
import com.onenation.oneworld.mahfuj75.searchperson.objectclass.DistrictSelector;
import com.onenation.oneworld.mahfuj75.searchperson.objectclass.SubDistrictSelector;
import com.onenation.oneworld.mahfuj75.searchperson.objectclass.SubjectSelector;

import java.net.URI;
import java.util.Calendar;

public class AddComplainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private RadioGroup radioGroupComplain;
    private RadioButton radioButtonMale;
    private RadioButton radioButtonFemale;
    private EditText editTextComplainGiverName;
    private EditText editTextComplainGiverPhoneNumber;
    private EditText editTextComplainGiverNationalIDNumber;
    private EditText editTextComplainGiverPresentAdderss;

    private Spinner spinnerComplainSubject;
    private TextView textViewIncidentDate;
    private Button buttonIncidentDate;
    private TextView textViewIncidentTime;
    private Button buttonIncidentTime;
    private TextView textViewIncidentProof;
    private TextView textViewComplainDistrict;
    private Button buttonIncidentProof;
    private EditText editTextIncidentDescription;
    private TextView textViewSubLocationComplain;
    private Spinner spinnerDivision;
    private Spinner spinnerDistrict;
    private RadioButton radioButtonSubDistrict;
    private RadioButton radioButtonMetropolitan;
    private RadioButton radioButtonCityCorporation;
    private Spinner spinnerSubLocation;
    private Spinner spinnerSubComplainSubject;
    private EditText editTextIncidentSpotDescription;

    private Button buttonComplainSubmit;

    private DatabaseReference mRefPost;
    private DatabaseReference mRefUser;
    private DatabaseReference mRefComplain;
    private FirebaseAuth mAuth;

    private String complainGiverUID;
    private String complainGiverName;
    private String complainGiverGender;
    private String complainGiverPhoneNumber = "";
    private String complainGiverNationalID;
    private String complainGiverPresentAddress;
    private String[] complainList;
    private String[] subComplainList;
    private String[] divisionList;
    private String[] districtList;
    private String[] subLocationList;
    private SubjectSelector subjectSelector;

    private DistrictSelector districtSelector;
    private SubDistrictSelector subDistrictSelector;
    private CityCorporationSelector cityCorporationSelector;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mRef = database.getReference("Complain");


    private String complainSubject;
    private String complainSubSubject;

    private String incidentDate = "";
    private String incidentTime = "";
    private String incidentDescription = "";
    private String incidentSpotType = "Sub-Location";
    private String incidentSpotDivision = "";
    private String incidentSpotDistrict = "";
    private String incidentSpotSubLocation = "";
    private String incidentSpotDescription = "";

    private String postDate;
    private String postTime;

    private URI imageUri;

    private DatePickerFragment datePickerFragment = new DatePickerFragment();
    private DialogFragment timePickerFragment = new TimePickerFragment();

    private final int PICK_FILE = 1;


    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;


    private String complainDateDay;
    private String complainDateMonth;
    private String complainDateYear;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_complain);

        mAuth = FirebaseAuth.getInstance();

        subjectSelector = new SubjectSelector();

        radioGroupComplain = (RadioGroup) findViewById(R.id.radioGroupComplain);
        radioButtonMale = (RadioButton) findViewById(R.id.radioButton_complain_male);
        radioButtonFemale = (RadioButton) findViewById(R.id.radioButton_complain_female);
        editTextComplainGiverName = (EditText) findViewById(R.id.editText_complainPerson_name);
        editTextComplainGiverPhoneNumber = (EditText) findViewById(R.id.editText_complainPerson_phoneNumber);
        editTextComplainGiverNationalIDNumber = (EditText) findViewById(R.id.editText_complainPerson_nationalId);
        editTextComplainGiverPresentAdderss = (EditText) findViewById(R.id.editText_complainPerson_address);


        spinnerComplainSubject = (Spinner) findViewById(R.id.spinnerComplainSubject);
        textViewIncidentDate = (TextView) findViewById(R.id.textView_complain_incidentDate);
        buttonIncidentDate = (Button) findViewById(R.id.button_complain_incidentDate);
        textViewIncidentTime = (TextView) findViewById(R.id.textView_complain_incidentTime);
        buttonIncidentTime = (Button) findViewById(R.id.button_complain_incidentTime);
        textViewIncidentProof = (TextView) findViewById(R.id.textView_complain_proof);
        buttonIncidentProof = (Button) findViewById(R.id.button_complain_proof);
        editTextIncidentDescription = (EditText) findViewById(R.id.editText_complain_description);


        spinnerDivision = (Spinner) findViewById(R.id.spinner_complain_division);
        spinnerDistrict = (Spinner) findViewById(R.id.spinner_complain_district);
        radioButtonSubDistrict = (RadioButton) findViewById(R.id.radioButton_complain_subDistrict);
        radioButtonMetropolitan = (RadioButton) findViewById(R.id.radioButton_complain_metropolitan);
        radioButtonCityCorporation = (RadioButton) findViewById(R.id.radioButton_complain_cityCorporation);
        spinnerSubLocation = (Spinner) findViewById(R.id.spinner_complain_subDistrict);
        spinnerSubComplainSubject = (Spinner) findViewById(R.id.spinnerComplainSubSubject);
        editTextIncidentSpotDescription = (EditText) findViewById(R.id.editText_complain_spot_description);
        textViewSubLocationComplain = (TextView) findViewById(R.id.textViewSubLocationComplain);

        textViewComplainDistrict = (TextView) findViewById(R.id.textView_complain_district);

        buttonComplainSubmit = (Button) findViewById(R.id.button_complain_submit);
        complainList = getResources().getStringArray(R.array.complain_subject_short);
        divisionList = getResources().getStringArray(R.array.division_bangladesh);

        districtSelector = new DistrictSelector();
        subDistrictSelector = new SubDistrictSelector();
        cityCorporationSelector = new CityCorporationSelector();

        if (mAuth.getCurrentUser() != null) {

            mRefUser = FirebaseDatabase.getInstance().getReference().child("Users");
            complainGiverUID = mAuth.getCurrentUser().getUid();

            radioGroupComplain.setVisibility(View.GONE);
            editTextComplainGiverName.setVisibility(View.GONE);
            editTextComplainGiverPhoneNumber.setVisibility(View.GONE);
        }
        spinnerComplainSubject.setAdapter(new SpinnerCustomAdapter().arrayListAdapterForSpinner(complainList, getApplicationContext()));


        spinnerComplainSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                spinnerSubComplainSubject.setAdapter(new SpinnerCustomAdapter().arrayListAdapterForSpinner(
                        subjectSelector.GetSubject(getApplicationContext()
                                , spinnerComplainSubject.getSelectedItem().toString().trim())
                        , getApplicationContext()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerDivision.setAdapter(new SpinnerCustomAdapter().arrayListAdapterForSpinner(
                getResources().getStringArray(R.array.division_bangladesh)
                , getApplicationContext()));

        spinnerDivision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                spinnerDistrict.setAdapter(new SpinnerCustomAdapter().arrayListAdapterForSpinner(
                        districtSelector.GetLocation(getApplicationContext()
                                , spinnerDivision.getSelectedItem().toString().trim())
                        , getApplicationContext()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (radioButtonSubDistrict.isChecked()) {

                    textViewSubLocationComplain.setText("Sub-Location");
                    String location = spinnerDistrict.getSelectedItem().toString().trim();
                    //Toast.makeText(getApplicationContext(),location,Toast.LENGTH_LONG).show();
                    subLocationList = subDistrictSelector.GetLocation(getApplicationContext(), location);
                    subLocationList[0] = "select";

                    spinnerSubLocation.setAdapter(new SpinnerCustomAdapter().arrayListAdapterForSpinner(subLocationList
                            , getApplicationContext()));

                } else if (radioButtonCityCorporation.isChecked()) {
                    textViewSubLocationComplain.setText("City-Corporation");

                    spinnerSubLocation.setAdapter(new SpinnerCustomAdapter().arrayListAdapterForSpinner(
                            cityCorporationSelector.GetLocation(getApplicationContext()
                                    , spinnerDistrict.getSelectedItem().toString().trim())
                            , getApplicationContext()));

                } else if (radioButtonMetropolitan.isChecked()) {
                    textViewSubLocationComplain.setText("Metropolitan");
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        radioButtonSubDistrict.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    textViewSubLocationComplain.setText("Sub-Location");
                    spinnerDistrict.setSelection(0);
                    spinnerDivision.setSelection(0);
                    spinnerSubLocation.setSelection(0);
                    incidentSpotType = "Sub-Location";
                }
            }
        });
        radioButtonCityCorporation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    textViewSubLocationComplain.setText("City-Corporation");
                    spinnerDistrict.setSelection(0);
                    spinnerDivision.setSelection(0);
                    spinnerSubLocation.setSelection(0);
                    incidentSpotType = "City-Corporation";
                }
            }
        });
        radioButtonMetropolitan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    textViewSubLocationComplain.setText("Metropolitan");
                    spinnerDistrict.setSelection(0);
                    spinnerDivision.setSelection(0);
                    spinnerSubLocation.setSelection(0);
                }
            }
        });


        buttonIncidentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        buttonIncidentTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerFragment.show(getFragmentManager(), "timePicker");
            }
        });

        buttonComplainSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInfoGet();
            }
        });

        buttonIncidentProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent filePickerIntent = new Intent(Intent.ACTION_PICK);
                filePickerIntent.setType("image/*");
                startActivityForResult(filePickerIntent, PICK_FILE);
            }
        });


    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        mHour = hourOfDay;
        mMinute = minute;
        updateTime();
    }

    private void updateTime() {

        if (mHour == 0) {

            if (mMinute < 10) {
                textViewIncidentTime.setText(new StringBuilder()
                        // Month is 0 based so add 1
                        .append(12).append(" : ")
                        .append("0")
                        .append(mMinute).append("  ")
                        .append("AM").append(" "));
            } else {
                textViewIncidentTime.setText(new StringBuilder()
                        // Month is 0 based so add 1
                        .append(12).append(" : ")
                        .append(mMinute).append("  ")
                        .append("AM").append(" "));
            }

        } else if (mHour < 12) {

            if (mMinute < 10) {
                textViewIncidentTime.setText(new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mHour).append(" : ")
                        .append("0")
                        .append(mMinute).append("  ")
                        .append("AM").append(" "));
            } else {
                textViewIncidentTime.setText(new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mHour).append(" : ")
                        .append(mMinute).append("  ")
                        .append("AM").append(" "));
            }

        } else if (mHour == 12) {
            if (mMinute < 10) {
                textViewIncidentTime.setText(new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mHour).append(" : ")
                        .append("0")
                        .append(mMinute).append("  ")
                        .append("PM").append(" "));
            } else {
                textViewIncidentTime.setText(new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mHour).append(" : ")
                        .append(mMinute).append("  ")
                        .append("PM").append(" "));
            }


        } else {
            if (mMinute < 10) {
                textViewIncidentTime.setText(new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mHour - 12).append(" : ")
                        .append("0")
                        .append(mMinute).append("  ")
                        .append("PM").append(" "));
            } else {
                textViewIncidentTime.setText(new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mHour - 12).append(" : ")
                        .append(mMinute).append("  ")
                        .append("PM").append(" "));
            }

        }


    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        mYear = year;
        mMonth = month;
        mDay = dayOfMonth;
        complainDateDay = String.valueOf(dayOfMonth);
        complainDateMonth = String.valueOf(month);
        complainDateYear = String.valueOf(year);
        updateDate();

    }

    private void updateDate() {
        textViewIncidentDate.setText(new StringBuilder()
                // Month is 0 based so add 1
                .append(mDay).append(" - ")
                .append(mMonth + 1).append(" - ")
                .append(mYear).append(" "));


    }


    private void userInfoGet() {

        if (mAuth.getCurrentUser() != null) {

            mRefUser.child(complainGiverUID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    complainGiverName = dataSnapshot.child("name").getValue().toString();
                    complainGiverGender = dataSnapshot.child("gender").getValue().toString();
                    complainGiverPhoneNumber = dataSnapshot.child("phone").getValue().toString();
                    personalInfoForUser();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        } else {

            personalInfo();


        }

    }

    private void personalInfoForUser() {

        if (editTextComplainGiverNationalIDNumber.getText().toString().trim().length() != 0) {
            complainGiverNationalID = editTextComplainGiverNationalIDNumber.getText().toString().trim();

            if (editTextComplainGiverPresentAdderss.getText().toString().trim().length() != 0) {
                complainGiverPresentAddress = editTextComplainGiverPresentAdderss.getText().toString().trim();
                crimeDescription();

            } else {
                AlertDialog.Builder myAlert = new AlertDialog.Builder(AddComplainActivity.this);
                myAlert.setMessage("present address field is empty")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                myAlert.show();
            }
        } else {
            AlertDialog.Builder myAlert = new AlertDialog.Builder(AddComplainActivity.this);
            myAlert.setMessage("national id field is empty")
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

    private void personalInfo() {

        if (radioButtonFemale.isChecked() || radioButtonMale.isChecked()) {
            if (radioButtonFemale.isChecked()) {
                complainGiverGender = "Female";
            } else {
                complainGiverGender = "Male";
            }

            if (editTextComplainGiverName.getText().toString().length() == 0) {
                AlertDialog.Builder myAlert = new AlertDialog.Builder(AddComplainActivity.this);
                myAlert.setMessage("please fill the name field")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                myAlert.show();
            } else {
                complainGiverName = editTextComplainGiverName.getText().toString();

                String phoneNumber = editTextComplainGiverPhoneNumber.getText().toString().trim();

                if (phoneNumber.length() == 11 ||
                        phoneNumber.length() == 14) {
                    if (phoneNumber.length() == 11) {
                        if (phoneNumber.startsWith("017") ||
                                phoneNumber.startsWith("018") ||
                                phoneNumber.startsWith("019") ||
                                phoneNumber.startsWith("015") ||
                                phoneNumber.startsWith("011")) {
                            complainGiverPhoneNumber = phoneNumber;
                        } else {
                            AlertDialog.Builder myAlert = new AlertDialog.Builder(AddComplainActivity.this);
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
                    } else if (phoneNumber.length() == 14) {
                        if (phoneNumber.startsWith("+88017") ||
                                phoneNumber.startsWith("+88018") ||
                                phoneNumber.startsWith("+88019") ||
                                phoneNumber.startsWith("+88015") ||
                                phoneNumber.startsWith("+88011")) {
                            complainGiverPhoneNumber = phoneNumber;
                        } else {
                            AlertDialog.Builder myAlert = new AlertDialog.Builder(AddComplainActivity.this);
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
                    }

                    if (complainGiverPhoneNumber.length() != 0) {

                        if (editTextComplainGiverNationalIDNumber.getText().toString().trim().length() != 0) {
                            complainGiverNationalID = editTextComplainGiverNationalIDNumber.getText().toString().trim();

                            if (editTextComplainGiverPresentAdderss.getText().toString().trim().length() != 0) {
                                complainGiverPresentAddress = editTextComplainGiverPresentAdderss.getText().toString().trim();

                                crimeDescription();

                            } else {
                                AlertDialog.Builder myAlert = new AlertDialog.Builder(AddComplainActivity.this);
                                myAlert.setMessage("present address field is empty")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .create();
                                myAlert.show();
                            }
                        } else {
                            AlertDialog.Builder myAlert = new AlertDialog.Builder(AddComplainActivity.this);
                            myAlert.setMessage("national id field is empty")
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
                } else if (phoneNumber.trim().length() == 0) {
                    AlertDialog.Builder myAlert = new AlertDialog.Builder(AddComplainActivity.this);
                    myAlert.setMessage("phone number field is empty")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create();
                    myAlert.show();
                } else {
                    AlertDialog.Builder myAlert = new AlertDialog.Builder(AddComplainActivity.this);
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
            }
        } else {
            AlertDialog.Builder myAlert = new AlertDialog.Builder(AddComplainActivity.this);
            myAlert.setMessage("select male or female")
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

    private void crimeDescription() {
        if (spinnerComplainSubject.getSelectedItem().toString().trim().equals("Select Subject") ||
                spinnerSubComplainSubject.getSelectedItem().toString().trim().equals("Select") ||
                spinnerSubComplainSubject.getSelectedItem().toString().trim().equals("সকল")) {
            AlertDialog.Builder myAlert = new AlertDialog.Builder(AddComplainActivity.this);
            myAlert.setMessage("subject is missing")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            myAlert.show();
        } else {

            complainSubject = spinnerComplainSubject.getSelectedItem().toString().trim();
            complainSubSubject = spinnerSubComplainSubject.getSelectedItem().toString().trim();


            if (textViewIncidentDate.getText().toString().trim().equals("DD-MM-YYYY")) {
                AlertDialog.Builder myAlert = new AlertDialog.Builder(AddComplainActivity.this);
                myAlert.setMessage("incident date is missing")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                myAlert.show();
            } else {

                incidentDate = textViewIncidentDate.getText().toString().trim();

                if (textViewIncidentTime.getText().toString().trim().equals("HH : MM a/pm")) {
                    AlertDialog.Builder myAlert = new AlertDialog.Builder(AddComplainActivity.this);
                    myAlert.setMessage("incident time is missing")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create();
                    myAlert.show();
                } else {
                    incidentTime = textViewIncidentTime.getText().toString().trim();

                    if (editTextIncidentDescription.getText().toString().trim().equals("")) {
                        AlertDialog.Builder myAlert = new AlertDialog.Builder(AddComplainActivity.this);
                        myAlert.setMessage("incident description is missing")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .create();
                        myAlert.show();
                    } else {

                        incidentDescription = editTextIncidentDescription.getText().toString().trim();

                        if (spinnerSubLocation.getSelectedItem().equals("Select") ||
                                spinnerDistrict.getSelectedItem().equals("Select") ||
                                spinnerDivision.getSelectedItem().equals("Select")) {
                            AlertDialog.Builder myAlert = new AlertDialog.Builder(AddComplainActivity.this);
                            myAlert.setMessage("incident location is missing")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .create();
                            myAlert.show();
                        } else {

                            incidentSpotDivision = spinnerDivision.getSelectedItem().toString().trim();
                            incidentSpotDistrict = spinnerDistrict.getSelectedItem().toString().trim();
                            incidentSpotSubLocation = spinnerSubLocation.getSelectedItem().toString().trim();

                            postDate = java.text.DateFormat.getDateInstance(DateFormat.MEDIUM).format(Calendar.getInstance().getTime());
                            postTime = java.text.DateFormat.getTimeInstance(DateFormat.MEDIUM).format(Calendar.getInstance().getTime());

                            if (editTextIncidentSpotDescription.getText().toString().trim().equals("")) {
                                AlertDialog.Builder myAlert = new AlertDialog.Builder(AddComplainActivity.this);
                                myAlert.setMessage("incident spot description is missing")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .create();
                                myAlert.show();
                            } else {
                                incidentSpotDescription = editTextIncidentSpotDescription.getText().toString().trim();
                                mRef = mRef.push();
                                progressDialog = new ProgressDialog(this);
                                progressDialog.setMessage("Updating...");
                                progressDialog.show();
                                complainUpload();

                            }
                        }
                    }

                }

            }

        }

    }

    private void complainUpload() {

        if (mAuth.getCurrentUser() != null) {
            mRef.child("uid").setValue(complainGiverUID);
        }


        mRef.child("name").setValue(complainGiverName);
        mRef.child("gender").setValue(complainGiverGender);
        mRef.child("phoneNumber").setValue(complainGiverPhoneNumber);
        mRef.child("nationalID").setValue(complainGiverNationalID);
        mRef.child("presentAddress").setValue(complainGiverPresentAddress);


        mRef.child("complainSubject").setValue(complainSubject);
        mRef.child("complainSubSubject").setValue(complainSubSubject);


        mRef.child("divisionProcess").setValue(incidentSpotDivision + "_0");
        mRef.child("divisionSubjectProcess").setValue(incidentSpotDivision + "_" + complainSubject + "_0");
        mRef.child("divisionSubSubjectProcess").setValue(incidentSpotDivision + "_" + complainSubSubject + "_0");


        mRef.child("districtProcess").setValue(incidentSpotDistrict + "_0");
        mRef.child("districtSubjectProcess").setValue(incidentSpotDistrict + "_" + complainSubject + "_0");
        mRef.child("districtSubSubjectProcess").setValue(incidentSpotDistrict + "_" + complainSubSubject + "_0");

        mRef.child("subLocationProcess").setValue(incidentSpotSubLocation + "_0");
        mRef.child("subLocationSubjectProcess").setValue(incidentSpotSubLocation + "_" + complainSubject + "_0");
        mRef.child("subLocationSubSubjectProcess").setValue(incidentSpotSubLocation + "_" + complainSubSubject + "_0");


        mRef.child("incidentDate").setValue(incidentDate);
        mRef.child("incidentDateDay").setValue(complainDateDay);
        mRef.child("incidentDateMonth").setValue(complainDateMonth);
        mRef.child("incidentDateYear").setValue(complainDateYear);

        mRef.child("incidentTime").setValue(incidentTime);
        mRef.child("nationalID").setValue(complainGiverNationalID);
        mRef.child("incidentDescription").setValue(incidentDescription);

        mRef.child("incidentSpotType").setValue(incidentSpotType);
        mRef.child("incidentSpotDivision").setValue(incidentSpotDivision);
        mRef.child("incidentSpotDistrict").setValue(incidentSpotDistrict);
        mRef.child("incidentSpotSubLocation").setValue(incidentSpotSubLocation);


        mRef.child("incidentSpotDescription").setValue(incidentSpotDescription);
        mRef.child("postDate").setValue(postDate);
        mRef.child("postTime").setValue(postTime);
        mRef.child("process").setValue("0");

        progressDialog.dismiss();

        AlertDialog.Builder myAlert = new AlertDialog.Builder(AddComplainActivity.this);
        myAlert.setTitle("Successful")
                .setMessage("complain submitted successfully")
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
