package com.example.garima.bevents.Events;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.garima.bevents.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.List;

/**
 * Created by Garima on 06-11-2017.
 */

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.ViewHolder>  {

    private Context context;
    private List<uploadFirebase> uploads ;
    UserNameForImageLayout userNameForImageLayout ;
    String email;
    private String currentUserId;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser user;

    public ExploreAdapter(Context context, List<uploadFirebase> uploads) {
        this.uploads = uploads;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_images, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        userNameForImageLayout= new UserNameForImageLayout();

//

        databaseReference = FirebaseDatabase.getInstance().getReference();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final uploadFirebase upload = uploads.get(position);

       /* databaseReference.child("uploads").orderByChild("url").equalTo(upload.getUrl()).
                addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        currentUserId = user.getUid();
        */
        if (upload.getUserKey() != null)
            email = userNameForImageLayout.UserNameFunction(upload.getUserKey());
        holder.textViewName.setText(email);
        holder.listdesc.setText(upload.getDesc());

        Glide.with(context).load(upload.getUrl()).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,PostDetailsActivity.class);
               intent.putExtra(PostDetailsActivity.EXTRA_POST_KEY,upload.getUrl() );
                //Bundle b = new Bundle();
               // b.putString("key1",upload.getUrl());
                //intent.putExtras(b);
                context.startActivity(intent);



            }
        });
        holder.PostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,CommentPopupWindow.class);
                i.putExtra(PostDetailsActivity.EXTRA_POST_KEY,upload.getUrl());
                context.startActivity(i);

            }
        });

    }




    @Override
    public int getItemCount() {
        return uploads.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName,listdesc;
        public ImageView imageView;
        public Button LikePost,PostComment;



        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            listdesc = (TextView) itemView.findViewById(R.id.listdesc);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            PostComment = (Button)itemView.findViewById(R.id.commentButton);

        }
    }
}


