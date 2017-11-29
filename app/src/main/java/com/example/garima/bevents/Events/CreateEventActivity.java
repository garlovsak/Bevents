package com.example.garima.bevents.Events;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.garima.bevents.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Calendar;

public class CreateEventActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PICK_IMAGE_REQUEST = 234;

    FrameLayout UploadImage;
    private Button uploadbutton;
    EditText EventDescription, EventTitle, Location;
    TextView StartDate, EndDate, StartTime, EndTime, filename;
    ImageView ImageShow;

        private String Title,locatn,Desc;
        DatePickerDialog datePickerDialog;
        TimePickerDialog timePickerDialog;

    private Uri filepath;
    private String url;
    private String currentuid;
    private StorageReference storagerefrence;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__event);

        storagerefrence = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        mref = FirebaseDatabase.getInstance().getReference(FirebaseConstants.DATABASE_PATH_UPLOADS);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        currentuid = user.getUid();

        ImageShow = (ImageView)findViewById(R.id.imageshow);
        EventTitle = (EditText)findViewById(R.id.event_title_edit_text);
        UploadImage = (FrameLayout) findViewById(R.id.choose);
        uploadbutton = (Button) findViewById(R.id.buttonUpload);
        StartDate = (TextView) findViewById(R.id.start_date_tv);
        StartTime = (TextView) findViewById(R.id.start_time_tv);
        EndDate = (TextView) findViewById(R.id.end_date_tv);
        EndTime = (TextView) findViewById(R.id.end_time_tv);
        EventDescription = (EditText) findViewById(R.id.description_edit_text);
        Location = (EditText) findViewById(R.id.location_edit_text);

        UploadImage.setOnClickListener(this);
        StartTime.setOnClickListener(this);
        StartDate.setOnClickListener(this);
        EndTime.setOnClickListener(this);
        EndDate.setOnClickListener(this);
        uploadbutton.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        if (view == UploadImage) {
            showFileChooser();
        } else if (view == uploadbutton) {
            uploadfile();

        } else if (view == StartDate) {
            StartFixDate();
        } else if (view == EndDate) {
            EndFixDate();

        } else if (view == StartTime) {
            StartFixTime();
        } else if (view == EndTime) {
            EndFixTime();

        }
        else
            if (view == filename) {
                fixdate();
            }

    }

    private void fixdate() {
         Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        datePickerDialog = new DatePickerDialog(CreateEventActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        filename.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void StartFixTime() {
        Calendar mTimeset = Calendar.getInstance();
        int hour = mTimeset.get(Calendar.HOUR_OF_DAY);
        int minute = mTimeset.get(Calendar.MINUTE);
        timePickerDialog = new TimePickerDialog(CreateEventActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        StartTime.setText(hourOfDay + ":" + minute);
                    }
                },
                hour, minute, false);
        timePickerDialog.show();

    }


    private void EndFixTime() {
        Calendar mTimeset = Calendar.getInstance();
        int hour = mTimeset.get(Calendar.HOUR_OF_DAY);
        int minute = mTimeset.get(Calendar.MINUTE);
        timePickerDialog = new TimePickerDialog(CreateEventActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        StartTime.setText(hourOfDay + ":" + minute);
                    }
                },
                hour, minute, false);
        timePickerDialog.show();

    }


    private void StartFixDate() {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        datePickerDialog = new DatePickerDialog(CreateEventActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        StartDate.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }



    private void EndFixDate() {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        datePickerDialog = new DatePickerDialog(CreateEventActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        EndDate.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filepath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);

                ImageShow.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void uploadfile() {
        if (filepath != null) {


                        StorageReference sRef = storagerefrence.child(FirebaseConstants.STORAGE_PATH_UPLOADS +
                                System.currentTimeMillis() + "." + getFileExtension(filepath));


                        sRef.putFile(filepath)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        //dismissing the progress dialog

                                        //displaying success toast
                                        Toast.makeText(getApplicationContext(), "File Uploaded... ", Toast.LENGTH_LONG).show();

                                        //creating the upload object to store uploaded image details
                                        Title = EventTitle.getText().toString().trim();
                                        if (Title == null){
                                            Toast.makeText(CreateEventActivity.this, "Enter Title First...", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        locatn = Location.getText().toString().trim();
                                        Desc = EventDescription.getText().toString().trim();

                                        String startdate = StartDate.getText().toString().trim();
                                        String starttime = StartTime.getText().toString().trim();
                                        String enddate = EndDate.getText().toString().trim();
                                        String endtime = EndTime.getText().toString().trim();

                                        //String date = filename.getText().toString().trim();
                                        //String description = desc.getText().toString().trim();
                                        String snapshot = taskSnapshot.getDownloadUrl().toString().trim();
                                        UploadFirebase uploadfirebase = new UploadFirebase(currentuid,Title,startdate,starttime,enddate,endtime,locatn,Desc, snapshot);

                                        //adding an upload to firebase database
                                        String uploadId = mref.push().getKey();
                                        mref.child(uploadId).setValue(uploadfirebase);

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                })
                                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                        //displaying the upload progress
                                        //double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                        // progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                                    }
                                });



        }
        else
            Toast.makeText(CreateEventActivity.this, "Enter Image First...", Toast.LENGTH_SHORT).show();
    }


}
