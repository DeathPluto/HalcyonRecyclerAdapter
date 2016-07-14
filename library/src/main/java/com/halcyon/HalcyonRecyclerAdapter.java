package com.halcyon;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Checkable;

import com.halcyon.utils.ModelWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * ©2016-2017 kmhealthcloud.All Rights Reserved <p/>
 * Created by: L  <br/>
 * Description:
 */
public abstract class HalcyonRecyclerAdapter<T> extends RecyclerView.Adapter<HalcyonViewHolder> {
    public interface OnCheckStateChangeListener {
        void onCheckedChange(int position, boolean checked);
    }


    /**
     * Normal list that does not indicate choices
     */
    public static final int CHOICE_MODE_NONE = 0;

    /**
     * The list allows up to one choice
     */
    public static final int CHOICE_MODE_SINGLE = 1;

    /**
     * The list allows multiple choices
     */
    public static final int CHOICE_MODE_MULTIPLE = 2;


    protected int mChoiceMode = CHOICE_MODE_NONE;

    protected List<T> mDataList;
    protected List<ModelWrapper<T>> mWrapperList = new ArrayList<>();
    protected int mLayoutId;
    protected int mCheckableId = -1;

    public void setCheckableId(int checkableId) {
        mCheckableId = checkableId;
    }

    public HalcyonRecyclerAdapter(@NonNull List<T> dataList, int layoutId) {
        mDataList = dataList;
        mLayoutId = layoutId;
        initWrapper();
    }

    private void initWrapper() {
        if (mDataList == null || mDataList.size() == 0) {
            return;
        }
        if (mDataList.get(0) instanceof Checkable) {
            for (T t : mDataList) {
                mWrapperList.add(new ModelWrapper<>(t, ((Checkable) t).isChecked()));

            }
        }

    }

    public void onItemCheckStateChange(int position){
        mWrapperList.get(position).isChecked = !mWrapperList.get(position).isChecked;
    }


    @Override
    public HalcyonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return HalcyonViewHolder
                .createViewHolder(parent.getContext(), parent, mLayoutId)
                .setCheckableId(mCheckableId)
                ;
    }

    @Override
    public void onBindViewHolder(HalcyonViewHolder holder, int position) {
        ModelWrapper<T> item = getWrapperItem(position);
        Log.i("Data:", item.isChecked + "");
        holder.setChecked(item.isChecked);
        convert(holder, position);
    }


    public abstract void convert(HalcyonViewHolder holder, int position);

    @Override
    public int getItemCount() {
        return mWrapperList.size();
    }

    public ModelWrapper<T> getWrapperItem(int position) {
        return mWrapperList.get(position);
    }

    public T getItem(int position) {
        return mDataList.get(position);
    }
}
