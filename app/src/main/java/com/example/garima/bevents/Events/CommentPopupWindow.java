package com.example.garima.bevents.Events;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.garima.bevents.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.example.garima.bevents.Events.PostDetailsActivity.EXTRA_POST_KEY;

public class CommentPopupWindow extends Activity {

    private EditText CommentText;
    private Button Commentbutton,newcommentbutton;
    private DatabaseReference mCommentsReference;
    private DatabaseReference mPostReference;
    private String postkey;
    private CommentAdapter mAdapter;
    private FirebaseAuth firebaseAuth;

    private RecyclerView mCommentsRecycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_popup_window);
        String mPostKey = getIntent().getStringExtra(EXTRA_POST_KEY);

       // if (mPostKey == null) {
         //   throw new IllegalArgumentException("Must pass EXTRA_POST_KEY");
        //}

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        CommentText = (EditText)findViewById(R.id.field_comment_text_popup) ;
        Commentbutton  = (Button)findViewById(R.id.button_post_comment_popup_now);
        newcommentbutton = (Button)findViewById(R.id.newbuttoncomment);


        Toast.makeText(CommentPopupWindow.this,"inital comment button",Toast.LENGTH_SHORT).show();
        getWindow().setLayout((int)(width*0.8) , (int)(height*0.6));



        mPostReference = FirebaseDatabase.getInstance().getReference();
        mPostReference.child("uploads").orderByChild("url").equalTo(mPostKey).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        postkey = dataSnapshot.getKey();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
        mCommentsReference = FirebaseDatabase.getInstance().getReference();
        mCommentsRecycler = findViewById(R.id.recycler_comments_popup);


        mCommentsRecycler.setLayoutManager(new LinearLayoutManager(this));
        Toast.makeText(CommentPopupWindow.this,"calling post button",Toast.LENGTH_SHORT).show();

        Commentbutton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                postCommentPopup();

            }
        });
        newcommentbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CommentPopupWindow.this,"new button post",Toast.LENGTH_SHORT).show();
                postCommentPopup();
            }
        });

    }
   // @Override
    //public void onStart(){
      //  super.onStart();

       // mAdapter = new CommentAdapter(this, mCommentsReference, postkey);
       // mCommentsRecycler.setAdapter(mAdapter);

   // }
   /* @Override
    public void onStop(){
        super.onStop();
        mAdapter.cleanupListener();
    }*/




        private void postCommentPopup(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        final String uid = user.getUid();
        mPostReference.child("users").orderByKey().equalTo(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String authorName = dataSnapshot.child("email").getValue(String.class);

                        String commentText = CommentText.getText().toString();
                        CommentPost comment;
                        comment = new CommentPost(authorName,commentText);


                        // Push the comment, it will appear in the list

                        String uploadId = mCommentsReference.child("comments").child(postkey).push().getKey();
                        mCommentsReference.child("comments").child(postkey).child(uploadId).setValue(comment);
                       //mCommentsReference.push().setValue(comment);

                        // Clear the field
                        CommentText.setText(null);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private static class CommentViewHolder extends RecyclerView.ViewHolder {

        public TextView authorView;
        public TextView bodyView;

        public CommentViewHolder(View itemView) {
            super(itemView);

            authorView = itemView.findViewById(R.id.comment_author);
            bodyView = itemView.findViewById(R.id.comment_body);
        }
    }

    private static class CommentAdapter extends RecyclerView.Adapter<CommentPopupWindow.CommentViewHolder> {

        private Context mContext;
        private DatabaseReference mDatabaseReference;
        private ChildEventListener mChildEventListener;

        private List<String> mCommentIds = new ArrayList<>();
        private List<CommentPost> mComments = new ArrayList<>();

        public CommentAdapter(final Context context, DatabaseReference ref, String postKey) {
            mContext = context;
            mDatabaseReference = ref.child("comment").child(postKey);

            // Create child event listener
            // [START child_event_listener_recycler]
            ChildEventListener childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                    Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                    // A new comment has been added, add it to the displayed list
                    CommentPost comment = dataSnapshot.getValue(CommentPost.class);

                    // [START_EXCLUDE]
                    // Update RecyclerView
                    mCommentIds.add(dataSnapshot.getKey());
                    mComments.add(comment);
                    notifyItemInserted(mComments.size() - 1);
                    // [END_EXCLUDE]
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                    Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                    // A comment has changed, use the key to determine if we are displaying this
                    // comment and if so displayed the changed comment.
                    CommentPost newComment = dataSnapshot.getValue(CommentPost.class);
                    String commentKey = dataSnapshot.getKey();

                    // [START_EXCLUDE]
                    int commentIndex = mCommentIds.indexOf(commentKey);
                    if (commentIndex > -1) {
                        // Replace with the new data
                        mComments.set(commentIndex, newComment);

                        // Update the RecyclerView
                        notifyItemChanged(commentIndex);
                    } else {
                        Log.w(TAG, "onChildChanged:unknown_child:" + commentKey);
                    }
                    // [END_EXCLUDE]
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

                    // A comment has changed, use the key to determine if we are displaying this
                    // comment and if so remove it.
                    String commentKey = dataSnapshot.getKey();

                    // [START_EXCLUDE]
                    int commentIndex = mCommentIds.indexOf(commentKey);
                    if (commentIndex > -1) {
                        // Remove data from the list
                        mCommentIds.remove(commentIndex);
                        mComments.remove(commentIndex);

                        // Update the RecyclerView
                        notifyItemRemoved(commentIndex);
                    } else {
                        Log.w(TAG, "onChildRemoved:unknown_child:" + commentKey);
                    }
                    // [END_EXCLUDE]
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                    Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                    // A comment has changed position, use the key to determine if we are
                    // displaying this comment and if so move it.
                    CommentPost movedComment = dataSnapshot.getValue(CommentPost.class);
                    String commentKey = dataSnapshot.getKey();

                    // ...
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                    Toast.makeText(mContext, "Failed to load comments.",
                            Toast.LENGTH_SHORT).show();
                }
            };
            ref.addChildEventListener(childEventListener);
            // [END child_event_listener_recycler]

            // Store reference to listener so it can be removed on app stop
            mChildEventListener = childEventListener;
        }

        @Override
        public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View view = inflater.inflate(R.layout.item_comment, parent, false);
            return new CommentViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CommentPopupWindow.CommentViewHolder holder, int position) {
            CommentPost comment = mComments.get(position);
            holder.authorView.setText(comment.author);
            holder.bodyView.setText(comment.text);
        }

        @Override
        public int getItemCount() {
            return mComments.size();
        }

        public void cleanupListener() {
            if (mChildEventListener != null) {
                mDatabaseReference.removeEventListener(mChildEventListener);
            }
        }

    }

    }

