package com.frankfancode.recyclerviewentrance;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by frank on 17-1-14.
 */

public class Mydapter extends RecyclerView.Adapter {

    public List<IconEntity> list;
    private Context mContext;
    private RecyclerViewReboundAnimator mReboundAnimator;
    private int mColumn =1;

    public Mydapter(List<IconEntity> list, RecyclerView recyclerView) {
        this.list = list;
        mReboundAnimator = new RecyclerViewReboundAnimator(recyclerView);
        RecyclerView.LayoutManager layoutManager=recyclerView.getLayoutManager();
        if (layoutManager.getClass().equals(LinearLayoutManager.class)){
            mColumn =1;
        }else if (layoutManager.getClass().equals(GridLayoutManager.class)){
            GridLayoutManager glm= (GridLayoutManager) layoutManager;
            mColumn =glm.getSpanCount();
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_icon, parent, false);
        mReboundAnimator.onCreateViewHolder(viewGroup, mColumn);
        return new ViewHolder(viewGroup);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        IconEntity item = list.get(position);
        mContext = holder.itemView.getContext().getApplicationContext();
        ViewHolder vh = (ViewHolder) holder;
        Glide.with(mContext).load("file:///android_asset/" + item.imagePath).into(vh.iv);
        vh.tv.setText(item.imageName);
        mReboundAnimator.onBindViewHolder(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return list == null || list.isEmpty() ? 0 : list.size();
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv;
        public TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv_grid);
            tv = (TextView) itemView.findViewById(R.id.tv_grid);
        }
    }
}
