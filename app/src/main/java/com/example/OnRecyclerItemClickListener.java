package com.example;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.halcyon.RecyclerAdapterWrapper;

/**
 * Created by Administrator on 2016/4/14.
 */
public class OnRecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    private GestureDetectorCompat mGestureDetector;
    private RecyclerView recyclerView;

    public OnRecyclerItemClickListener(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        mGestureDetector = new GestureDetectorCompat(recyclerView.getContext(), new ItemTouchHelperGestureListener());
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (child != null) {
                RecyclerView.ViewHolder vh = recyclerView.getChildViewHolder(child);
                int position = vh.getLayoutPosition();
                if(recyclerView.getAdapter() instanceof RecyclerAdapterWrapper){
                    RecyclerAdapterWrapper wrapper = (RecyclerAdapterWrapper) recyclerView.getAdapter();
                    if(wrapper.isInnerAdapterPosition(position)){
                        wrapper.onItemCheckStateChange(position-wrapper.getHeadersCount());
                        onItemClick(vh,position-wrapper.getHeadersCount());
                    }
                }else{
                    onItemClick(vh,position);
                }

            }
            return true;
        }
    }




    public void onItemClick(RecyclerView.ViewHolder vh,int position) {
    }
}
