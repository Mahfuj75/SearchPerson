package com.onenation.oneworld.mahfuj75.searchperson.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.onenation.oneworld.mahfuj75.searchperson.R;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

public class AddMissingPersonActivity extends AppCompatActivity {




    private RadioButton radioButtonFound ;
    private RadioButton radioButtonLost ;

    private Button buttonMissingPersonSubmit;
    private Button buttonMissingPersonImage;

    private EditText editTextMissingPersonName;
    private EditText editTextMissingPersonRelative;
    private EditText editTextMissingPersonAge;
    private EditText editTextMissingPersonSkinColor;
    private EditText editTextMissingPersonHeight;
    private EditText editTextMissingPersonLastSeenLocation;
    private EditText editTextMissingPersonDescription;

    private ImageView imageViewAddmissingperson;

    private final int SELECT_PHOTO = 1;
    private Bitmap selectedImage;
    private StorageReference mStorage;
    private Uri imageUri;
    private String imageKey;


    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Post");
    private String s= myRef.push().getKey();




    //public static final String FIREBASE_URL = "https://missing-person-finder.firebaseio.com/";
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot().child("Post");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_missing_person);

        mStorage = FirebaseStorage.getInstance().getReference();



        radioButtonFound = (RadioButton) findViewById(R.id.radioButton_found);
        radioButtonLost =(RadioButton) findViewById(R.id.radioButton_lost);

        buttonMissingPersonSubmit =(Button) findViewById(R.id.button_missingPerson_submit);
        buttonMissingPersonImage =(Button) findViewById(R.id.addMissingPersonImageButton);

        editTextMissingPersonName =(EditText) findViewById(R.id.editText_missingPerson_name);
        editTextMissingPersonRelative =(EditText) findViewById(R.id.editText_missingPerson_relative);
        editTextMissingPersonAge =(EditText) findViewById(R.id.editText_missingPerson_age);
        editTextMissingPersonSkinColor =(EditText) findViewById(R.id.editText_missingPerson_skinColor);
        editTextMissingPersonHeight =(EditText) findViewById(R.id.editText_missingPerson_height);
        editTextMissingPersonLastSeenLocation =(EditText)findViewById(R.id.editText_missingPerson_lastSeenLocation);
        editTextMissingPersonDescription =(EditText) findViewById(R.id.editText_missingPerson_description);

        imageViewAddmissingperson =(ImageView) findViewById(R.id.addMissingPersonImageView);

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

                if(editTextMissingPersonName.getText().length()==0||editTextMissingPersonRelative.getText().length()==0||
                        editTextMissingPersonAge.getText().length()==0||editTextMissingPersonSkinColor.getText().length()==0||
                        editTextMissingPersonHeight.getText().length()==0||editTextMissingPersonLastSeenLocation.getText().length()==0)
                {

                   // Toast.makeText(getApplicationContext(),"Something Is Missing",Toast.LENGTH_LONG).show();

                    AlertDialog.Builder myAlert = new AlertDialog.Builder(AddMissingPersonActivity.this);
                    myAlert.setMessage("Something Is Missing")
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
                else if(radioButtonLost.isChecked()==true ||radioButtonFound.isChecked()==true)
                {


                    //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();

                    Calendar rightNow = Calendar.getInstance();

                    myRef.child(s).child("time").setValue(rightNow.toString());

                    myRef.child(s).child("userID").setValue("ruOlPhjd7ZZXhs3yyesdPXtStIL2");

                    if (radioButtonLost.isChecked()==true)
                    {
                        myRef.child(s).child("lostFound").setValue("Lost");
                    }
                    else if (radioButtonFound.isChecked()==true)
                    {
                        myRef.child(s).child("lostFound").setValue("Found");
                    }
                    myRef.child(s).child("name").setValue(editTextMissingPersonName.getText().toString());
                    myRef.child(s).child("relative").setValue(editTextMissingPersonRelative.getText().toString());
                    myRef.child(s).child("age").setValue(editTextMissingPersonAge.getText().toString());
                    myRef.child(s).child("skinColor").setValue(editTextMissingPersonSkinColor.getText().toString());
                    myRef.child(s).child("height").setValue(editTextMissingPersonHeight.getText().toString());
                    myRef.child(s).child("lastSeenLocation").setValue(editTextMissingPersonLastSeenLocation.getText().toString());
                    myRef.child(s).child("description").setValue(editTextMissingPersonDescription.getText().toString());

                    imageKey= myRef.child("Image").push().getKey();
                    StorageReference filePath = mStorage.child("MissingPerson").child(imageKey);


                    filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(getApplicationContext(),"Upload Complete",Toast.LENGTH_SHORT).show();
                            myRef.child(s).child("image").setValue(imageKey);

                        }
                    });






                    editTextMissingPersonName.setText("");
                    editTextMissingPersonRelative.setText("");
                    editTextMissingPersonAge.setText("");
                    editTextMissingPersonSkinColor.setText("");
                    editTextMissingPersonHeight.setText("");
                    editTextMissingPersonLastSeenLocation.setText("");
                    editTextMissingPersonDescription.setText("");
                    radioButtonFound.setChecked(false);
                    radioButtonLost.setChecked(false);

                    AlertDialog.Builder myAlert = new AlertDialog.Builder(AddMissingPersonActivity.this);
                    myAlert.setMessage("Missing Person Added")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(AddMissingPersonActivity.this,MissingPersonActivity.class);
                                    startActivity(i);
                                }
                            })
                            .create();
                    myAlert.show();
                }
                else
                {
                    AlertDialog.Builder myAlert = new AlertDialog.Builder(AddMissingPersonActivity.this);
                    myAlert.setMessage("Something Is Missing")
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);


        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    try {
                        imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        selectedImage = BitmapFactory.decodeStream(imageStream);
                        imageViewAddmissingperson.setImageBitmap(selectedImage);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
        }

    }
}
