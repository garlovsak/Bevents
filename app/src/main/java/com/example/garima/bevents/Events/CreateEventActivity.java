package com.example.garima.bevents.Events;

import android.app.DatePickerDialog;
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
import android.widget.ImageView;
import android.widget.TextView;
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

public class CreateEventActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int PICK_IMAGE_REQUEST = 234;

    private Button uploadchoose,uploadbutton;
    EditText desc;
    ImageView uploadimage;
    TextView viewupload,filename;
    DatePickerDialog datePickerDialog;



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


        storagerefrence= FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        mref = FirebaseDatabase.getInstance().getReference(FirebaseConstants.DATABASE_PATH_UPLOADS);
        FirebaseUser user = firebaseAuth.getCurrentUser();
         currentuid = user.getUid();
       // System.out.println("user key="+ user.getUid());

        uploadchoose = (Button)findViewById(R.id.choose);
        desc = (EditText)findViewById(R.id.description);
        uploadbutton  = (Button)findViewById(R.id.buttonUpload);
        filename = (TextView) findViewById(R.id.filename);
        uploadimage = (ImageView)findViewById(R.id.imageshow);
        viewupload = (TextView)findViewById(R.id.textViewShow);



        uploadbutton.setOnClickListener(this);
        uploadchoose.setOnClickListener(this);
        viewupload.setOnClickListener(this);
        filename.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        if (view == uploadchoose ) {
            showFileChooser();
        }
        else
            if (view == uploadbutton) {
                uploadfile();

        }
        else
            if (view == viewupload) {

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

                uploadimage.setImageBitmap(bitmap);
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

    public void uploadfile(){
        if(filepath != null){

            StorageReference sRef = storagerefrence.child(FirebaseConstants.STORAGE_PATH_UPLOADS +
                    System.currentTimeMillis() + "." + getFileExtension(filepath));


            sRef.putFile(filepath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //dismissing the progress dialog

                            //displaying success toast
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();

                            //creating the upload object to store uploaded image details

                            String date = filename.getText().toString().trim();
                            String description = desc.getText().toString().trim();
                            String snapshot = taskSnapshot.getDownloadUrl().toString();
                            uploadFirebase uploadfirebase = new uploadFirebase(currentuid,date,description,snapshot);

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
    }


}
