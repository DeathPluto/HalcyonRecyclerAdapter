package com.halcyon;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * ©2016-2017 kmhealthcloud.All Rights Reserved <p/>
 * Created by: L  <br/>
 * Description:
 */
public abstract class HalcyonRecyclerAdapter<T> extends RecyclerView.Adapter<HalcyonViewHolder> {

    protected List<T> mDataList;
    protected View mItemView;

    public HalcyonRecyclerAdapter(@NonNull List<T> dataList, View itemView) {
        mDataList = dataList;
        mItemView = itemView;
    }

    @Override
    public HalcyonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return HalcyonViewHolder.createViewHolder(mItemView);
    }

    @Override
    public abstract void onBindViewHolder(HalcyonViewHolder holder, int position);

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public T getItem(int position){
        return mDataList.get(position);
    }
}
