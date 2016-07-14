package com.halcyon;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
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
public abstract class HalcyonRecyclerAdapter<T> extends RecyclerView.Adapter<HalcyonViewHolder> implements Checkable{

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

    /**
     * Running state of which positions are currently checked
     */
    SparseBooleanArray mCheckStates;

    protected int mChoiceMode = CHOICE_MODE_NONE;

    protected List<T> mDataList;
    protected List<ModelWrapper<T>> mWrapperList = new ArrayList<>();
    protected int mLayoutId;
    protected int mCheckableId;
    protected HalcyonViewHolder mViewHolder;

    public HalcyonRecyclerAdapter(@NonNull List<T> dataList, int layoutId) {
        mDataList = dataList;
        mLayoutId = layoutId;
        initCheckStates();
    }



    public HalcyonRecyclerAdapter(List<T> dataList, int layoutId, int checkableId) {
        mDataList = dataList;
        mLayoutId = layoutId;
        mCheckableId = checkableId;
        initCheckStates();
    }

    private void initCheckStates() {
        mCheckStates = new SparseBooleanArray();
        for (int i = 0; i < mDataList.size(); i++) {
            mCheckStates.append(i,initItemChecked(i));
        }
    }

    protected boolean initItemChecked(int position){
        return false;
    }

    @Override
    public void setChecked(boolean checked) {
        mViewHolder.setChecked(checked);
    }

    @Override
    public boolean isChecked() {
        return mViewHolder.isChecked();
    }

    @Override
    public void toggle() {
        mViewHolder.toggle();
    }

    public void setCheckableId(int checkableId) {
        mCheckableId = checkableId;
    }

    @Override
    public HalcyonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mViewHolder = HalcyonViewHolder.createViewHolder(parent.getContext(),parent,mLayoutId).setCheckableId(mCheckableId);
    }

    @Override
    public  void onBindViewHolder(HalcyonViewHolder holder, int position){
        holder.setChecked(isItemChecked(position));
        convert(holder,position);
    }

    public boolean isItemChecked(int position){
        return mCheckStates.get(position);
    }



    public abstract void convert(HalcyonViewHolder holder, int position);

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public T getItem(int position){
        return mDataList.get(position);
    }
}
