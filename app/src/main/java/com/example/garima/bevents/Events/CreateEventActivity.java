package com.example.garima.bevents.Events;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.garima.bevents.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class CreateEventActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int PICK_IMAGE_REQUEST = 234;

    private Button uploadchoose,uploadbutton;
    EditText filename;
    ImageView uploadimage;
    TextView viewupload;


    private Uri filepath;

    private StorageReference storagerefrence;
    private DatabaseReference mref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__event);


        storagerefrence= FirebaseStorage.getInstance().getReference();
        mref = FirebaseDatabase.getInstance().getReference(FirebaseConstants.DATABASE_PATH_UPLOADS);

        uploadchoose = (Button)findViewById(R.id.choose);
        uploadbutton  = (Button)findViewById(R.id.buttonUpload);
        filename = (EditText)findViewById(R.id.filename);
        uploadimage = (ImageView)findViewById(R.id.imageshow);
        viewupload = (TextView)findViewById(R.id.textViewShow);



        uploadbutton.setOnClickListener(this);
        uploadchoose.setOnClickListener(this);
        viewupload.setOnClickListener(this);


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
                            uploadFirebase upload = new uploadFirebase(filename.getText().toString().trim(),taskSnapshot.getDownloadUrl().toString());

                            //adding an upload to firebase database
                            String uploadId = mref.push().getKey();
                            mref.child(uploadId).setValue(upload);
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
