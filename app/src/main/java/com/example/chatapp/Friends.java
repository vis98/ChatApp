package com.example.chatapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Friends.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Friends#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Friends extends Fragment {

    private RecyclerView recyclerView;
    private Toolbar mtoolbar;
    private DatabaseReference ref;
    private String curruser;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        recyclerView = view.findViewById(R.id.usr_list);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        curruser = currentUser.getUid();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ref = FirebaseDatabase.getInstance().getReference().child("users");
        return view;

    }

    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(ref, User.class)
                        .setLifecycleOwner(this)
                        .build();


        FirebaseRecyclerAdapter<User, UserViewHolder> adapter = new FirebaseRecyclerAdapter<User, UserViewHolder>(options) {
            @Override
            public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_item, parent, false);
                UserViewHolder viewHolder = new UserViewHolder(view);

                return viewHolder;

            }

            @Override
            protected void onBindViewHolder(@NonNull final UserViewHolder viewHolder, int i, @NonNull final User user) {
                final String id = getRef(i).getKey();
                ref.child(id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("image")) {
                            Toast.makeText(getActivity(), "ss", Toast.LENGTH_LONG).show();
                            //String profileimg=dataSnapshot.child("image").getValue().toString();
                            //String userName = dataSnapshot.child("name").getValue().toString();
                            //String userStatus = dataSnapshot.child("status").getValue().toString();
                            viewHolder.setName(user.getName());
                            viewHolder.setStatus(user.getStatus());
                            viewHolder.setProfile_image(user.getImage(),getContext());


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(getActivity(),MessageActivity.class);
                        i.putExtra("userid",id);
                         startActivity(i);

                    }
                });


            }
        };


        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }


    public static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public TextView status;
        public CircleImageView profile_image;
        View view;


        public UserViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            username = itemView.findViewById(R.id.usr_name);
            profile_image = (CircleImageView) itemView.findViewById(R.id.profimg);
            status = itemView.findViewById(R.id.usr_status);


        }
        public void  setName(String name){
            username.setText(name);
        }
        public void setStatus(String stuts){
            status.setText(stuts);
        }
        public void  setProfile_image(String im,Context ctx){

            Picasso.with(ctx).load(im).placeholder(R.drawable.index).into(profile_image);

        }

    }

}










