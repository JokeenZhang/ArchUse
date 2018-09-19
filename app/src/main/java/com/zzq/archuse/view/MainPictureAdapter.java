package com.zzq.archuse.view;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzq.archuse.R;
import com.zzq.archuse.retrofit.bean.MeizhiBean;

public class MainPictureAdapter extends PagedListAdapter<MeizhiBean, MainPictureAdapter.MainPictureHolder> {

    public MainPictureAdapter() {
        super(DIFF_CALLBACK);
    }

    public static final DiffUtil.ItemCallback<MeizhiBean> DIFF_CALLBACK = new DiffUtil.ItemCallback<MeizhiBean>() {
        @Override
        public boolean areItemsTheSame(MeizhiBean oldItem, MeizhiBean newItem) {
            return oldItem.get_id().equals(newItem.get_id());
        }

        @Override
        public boolean areContentsTheSame(MeizhiBean oldItem, MeizhiBean newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public MainPictureHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_picture, parent, false);
        MainPictureHolder holder = new MainPictureHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull MainPictureHolder holder, int position) {
        holder.mImageView.setVisibility(View.GONE);
        holder.mTextView.setText(getItem(position).getUrl());
//        Glide.with(holder.itemView.getContext())
//                .load(data.get(position).getUrl())
//                .into(holder.mImageView);
    }

    public static class MainPictureHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextView;

        public MainPictureHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv_item_pict);
            mTextView = itemView.findViewById(R.id.tv_item_url);
        }
    }

}
