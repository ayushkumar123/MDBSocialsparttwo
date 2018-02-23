package com.ayush.mdbsocials;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by hp on 2/17/2017.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.CustomViewHolder> {

    private Context context;
    private ArrayList<Message> data;

    public ListAdapter(Context context, ArrayList<Message> data) {
        this.context = context;
        this.data = data;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view, parent, false);
        return new CustomViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final CustomViewHolder holder, int position) {
        final Message socials = data.get(position);
        holder.msgView.setText(socials.name);
        holder.msgView2.setText(socials.date);
        holder.msgView3.setText(socials.email);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, NewSocial.class);
                i.putExtra("socials", socials);
//                i.putExtra("imageURL", current_social.getImageURL());
                context.startActivity(i);
            }
        });

        //haven't taught this yet but essentially it runs separately from the UI
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(socials.firebaseImageUrl + ".png");
        Glide.with(context).load(socials.getFirebaseImageUrl()).into(holder.imageView);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * A card displayed in the RecyclerView
     */
    class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView msgView;
        TextView msgView2;
        TextView msgView3;
        ImageView imageView;
        ConstraintLayout card;

        public CustomViewHolder (View view) {
            super(view);
            this.msgView = (TextView) view.findViewById(R.id.msgView);
            this.msgView2 = (TextView) view.findViewById(R.id.msgView2);
            this.msgView3 = (TextView) view.findViewById(R.id.msgView3);
            this.imageView = (ImageView) view.findViewById(R.id.imageView);
            this.card = (ConstraintLayout) view.findViewById(R.id.card);
        }

    }
    // Write some data with a timestamp
//    ref.push({
//        date: Firebase.ServerValue.TIMESTAMP;
//    });
//
//    // Later, retrieve the data by ordered date
//    ref.orderByChild('date').on('child_added', function(snapshot) {
//        //Do something with ordered children
//    });

}