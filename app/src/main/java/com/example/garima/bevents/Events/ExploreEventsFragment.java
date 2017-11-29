package com.example.garima.bevents.Events;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.garima.bevents.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.garima.bevents.Events.FirebaseConstants.DATABASE_PATH_UPLOADS;


public class ExploreEventsFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;


    private DatabaseReference mDatabase;
    private LinearLayoutManager mManager;

    private List<UploadFirebase> uploads = new ArrayList<>();

    //public ExploreEventsFragment(){};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.explore, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable final Bundle savedInstanceState) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(mManager);

        //creating adapter
        adapter = new ExploreAdapter(this.getActivity(), uploads);
        //adding adapter to recyclerview
        recyclerView.setAdapter(adapter);

        //changes getContext from this if needed check this out
        mDatabase = FirebaseDatabase.getInstance().getReference(DATABASE_PATH_UPLOADS);

        //adding an event listener to fetch values
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //dismissing the progress dialog
                uploads.clear();
                //iterating through all the values in database
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    UploadFirebase upload = postSnapshot.getValue(UploadFirebase.class);
                    upload.setSnapShotId(postSnapshot.getKey());
                    uploads.add(upload);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}











