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
import com.zzq.archuse.retrofit.bean.FuliDataBean;

import java.util.ArrayList;
import java.util.List;

public class MainPictureAdapter extends PagedListAdapter<FuliDataBean.ResultsBean, MainPictureAdapter.MainPictureHolder> {

    private List<FuliDataBean.ResultsBean> data = new ArrayList<>();

    public MainPictureAdapter() {
        super(DIFF_CALLBACK);
    }

    public static final DiffUtil.ItemCallback<FuliDataBean.ResultsBean> DIFF_CALLBACK = new DiffUtil.ItemCallback<FuliDataBean.ResultsBean>() {
        @Override
        public boolean areItemsTheSame(FuliDataBean.ResultsBean oldItem, FuliDataBean.ResultsBean newItem) {
            return oldItem.get_id().equals(newItem.get_id());
        }

        @Override
        public boolean areContentsTheSame(FuliDataBean.ResultsBean oldItem, FuliDataBean.ResultsBean newItem) {
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
        holder.mTextView.setText(data.get(position).getUrl());
//        Glide.with(holder.itemView.getContext())
//                .load(data.get(position).getUrl())
//                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
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
