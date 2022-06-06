package com.tuantran.requesapihttpconection_retrofit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {
    private ArrayList<Album> arrayList;
    private Context context;

    public AlbumAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<Album> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AlbumAdapter.AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_layout, parent, false);
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumAdapter.AlbumViewHolder holder, int position) {
        Album album = arrayList.get(position);
        String strID = String.valueOf(album.getId());
        holder.tvID.setText(strID);
        holder.tvTitle.setText(album.getTitle());
        Picasso.get().load(album.getUrl()).fit().into(holder.imgAnh);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class AlbumViewHolder extends RecyclerView.ViewHolder {
        private TextView tvID, tvTitle;
        private ImageView imgAnh;

        public AlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            tvID = itemView.findViewById(R.id.tv_id);
            tvTitle = itemView.findViewById(R.id.tv_title);
            imgAnh = itemView.findViewById(R.id.img_album);
        }
    }
}
