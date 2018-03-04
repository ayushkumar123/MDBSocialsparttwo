package com.ayush.mdbsocials;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.CustomViewHolder> {

    private final Context context;
    private final ArrayList<Message> data;

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
        final Message socials = data.get(data.size() - position - 1);
        holder.msgView.setText(socials.name);
        holder.msgView2.setText(socials.date);
        holder.msgView3.setText(socials.email);
        holder.msgView4.setText(socials.num_interested + " people are interested");

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, NewSocial.class);
                i.putExtra("socials", socials);
                i.putExtra("social id", socials.id);
                context.startActivity(i);
            }
        });

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
        final TextView msgView;
        final TextView msgView2;
        final TextView msgView3;
        final TextView msgView4;
        final ImageView imageView;
        final ConstraintLayout card;

        public CustomViewHolder (View view) {
            super(view);
            this.msgView = view.findViewById(R.id.msgView);
            this.msgView2 = view.findViewById(R.id.msgView2);
            this.msgView3 = view.findViewById(R.id.msgView3);
            this.msgView4 = view.findViewById(R.id.msgView4);
            this.imageView = view.findViewById(R.id.imageView);
            this.card = view.findViewById(R.id.card);
        }

    }
}