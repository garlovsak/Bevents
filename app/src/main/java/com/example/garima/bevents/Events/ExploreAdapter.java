package com.example.garima.bevents.Events;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.garima.bevents.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Garima on 06-11-2017.
 */

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.ViewHolder> {

    private Context context;
    private List<UploadFirebase> uploads;
    // UserNameForImageLayout userNameForImageLayout ;
    // String email;
    // private String currentUserId;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    private String key1;


    public ExploreAdapter(Context context, List<UploadFirebase> uploads) {
        this.uploads = uploads;
        this.context = context;
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_card_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        databaseReference = FirebaseDatabase.getInstance().getReference("uploads");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final UploadFirebase upload = uploads.get(position);
        //DatabaseReference dRef = databaseReference.child(upload).getRef();

        Glide.with(context).load(upload.getImageurl()).into(holder.ImageViewShow);
        holder.DateEventSet.setText(upload.getStartdate());
        holder.TitleEvent.setText(upload.getTitle());
        holder.InterestPeople.setText(String.format(context.getString(R.string.people_interested),
                upload.getInterestedUserIds().size()));
        holder.CommentButtom.setText("edit it also");

        final String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (upload.getInterestedUserIds().contains(currentUserId)) {
            holder.borderSet.setBackgroundResource(R.drawable.ic_star_green_700_24dp);
            holder.starTextView.setTextColor(context.getResources().getColor(R.color.green_700));
        } else {
            holder.borderSet.setBackgroundResource(R.drawable.ic_star_border_green_700_24dp);
            holder.starTextView.setTextColor(context.getResources().getColor(R.color.cardview_dark_background));
        }

        final DatabaseReference mref = databaseReference.child(upload.getSnapShotId());
        holder.InterestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> interestedUserIds = new ArrayList<>();
                interestedUserIds.addAll(upload.getInterestedUserIds());
                if (interestedUserIds.contains(currentUserId)) {
                    interestedUserIds.remove(currentUserId);
                } else {
                    interestedUserIds.add(currentUserId);
                }
                mref.child("interestedUserIds").setValue(interestedUserIds);
            }
        });
    }


    // if (upload.getUserKey() != null)
    //     email = userNameForImageLayout.UserNameFunction(upload.getUserKey());
    //holder.textViewName.setText(email);
    //holder.listdesc.setText(upload.getDesc());

    //

    //  holder.imageView.setOnClickListener(new View.OnClickListener() {
    // @Override
    //public void onClick(View v) {
    //Intent intent = new Intent(context,PostDetailsActivity.class);
    //intent.putExtra(PostDetailsActivity.EXTRA_POST_KEY,upload.getUrl() );
    //Bundle b = new Bundle();
    // b.putString("key1",upload.getUrl());
    //intent.putExtras(b);
    //  context.startActivity(intent);


    // };

    // holder.PostComment.setOnClickListener(new View.OnClickListener() {
    // @Override
    // public void onClick(View v) {
    //   Intent i = new Intent(context,CommentPopupWindow.class);
    //  i.putExtra(PostDetailsActivity.EXTRA_POST_KEY,upload.getUrl());
    // context.startActivity(i);

    //}
    //});

    //}

    @Override
    public int getItemCount() {
        return uploads.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ImageViewShow, borderSet;
        TextView DateEventSet, TitleEvent, InterestPeople, CommentButtom;
        LinearLayout InterestButton, JoinButton;
        TextView starTextView;

        ViewHolder(View itemView) {
            super(itemView);

            DateEventSet = (TextView) itemView.findViewById(R.id.dateOfEventTextView);
            TitleEvent = (TextView) itemView.findViewById(R.id.eventTitle);
            ImageViewShow = (ImageView) itemView.findViewById(R.id.imageViewshow);
            InterestPeople = (TextView) itemView.findViewById(R.id.people_interested);
            CommentButtom = (TextView) itemView.findViewById(R.id.comments_view);
            InterestButton = (LinearLayout) itemView.findViewById(R.id.interestedButton);
            JoinButton = (LinearLayout) itemView.findViewById(R.id.joinButton);
            borderSet = (ImageView) itemView.findViewById(R.id.star_border_id);
            starTextView = (TextView) itemView.findViewById(R.id.star_text_view);
            // listdesc = (TextView) itemView.findViewById(R.id.listdesc);
            // ImageViewShow = (ImageView) itemView.findViewById(R.id.imageViewshow);
            // PostComment = (Button)itemView.findViewById(R.id.commentButton);

        }
    }
}