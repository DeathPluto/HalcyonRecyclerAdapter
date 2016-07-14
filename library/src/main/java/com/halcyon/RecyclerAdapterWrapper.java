package com.halcyon;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;

import com.halcyon.utils.WrapperUtils;

/**
 * ©2016-2017 kmhealthcloud.All Rights Reserved <p/>
 * Created by: L  <br/>
 * Description:
 */
public class RecyclerAdapterWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;

    public static final int ITEM_TYPE_LOAD_MORE = Integer.MAX_VALUE - 2;

    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFooterViews = new SparseArrayCompat<>();
    private View mLoadMoreView;
    private int mLoadMoreLayoutId;
    private OnLoadMoreListener mOnLoadMoreListener;
    private RecyclerView.Adapter mInnerAdapter;

    public RecyclerAdapterWrapper(RecyclerView.Adapter adapter) {
        mInnerAdapter = adapter;
    }

    private boolean hasLoadMore() {
        return mLoadMoreView != null || mLoadMoreLayoutId != 0;
    }



    public interface OnLoadMoreListener {
        void onLoadMoreRequested();
    }


    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPosition(position)) {
            return mHeaderViews.keyAt(position);
        } else if (isFooterViewPosition(position)) {
            return mFooterViews.keyAt(position - getHeadersCount() - getRealItemCount());
        } else if ( isShowLoadMore(position)){
            return ITEM_TYPE_LOAD_MORE;
        }
        return mInnerAdapter.getItemViewType(position - getHeadersCount());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderViews.get(viewType) != null) {
            ViewHolder holder = HalcyonViewHolder.createViewHolder(mHeaderViews.get(viewType));
            return holder;

        } else if (mFooterViews.get(viewType) != null) {
            ViewHolder holder = HalcyonViewHolder.createViewHolder(mFooterViews.get(viewType));
            return holder;
        } else if (viewType == ITEM_TYPE_LOAD_MORE){
            ViewHolder holder;
            if (mLoadMoreView != null) {
                holder = HalcyonViewHolder.createViewHolder(mLoadMoreView);
            } else {
                holder = HalcyonViewHolder.createViewHolder(parent.getContext(), parent, mLoadMoreLayoutId);
            }
            return holder;
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (isHeaderViewPosition(position)) {
            return;
        }
        if (isFooterViewPosition(position)) {
            return;
        }

        if(isShowLoadMore(position)){
            //todo refresh stuff
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position - getHeadersCount());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback() {
            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                int viewType = getItemViewType(position);
                if (mHeaderViews.get(viewType) != null) {
                    return layoutManager.getSpanCount();
                } else if (mFooterViews.get(viewType) != null) {
                    return layoutManager.getSpanCount();
                }
                if (oldLookup != null)
                    return oldLookup.getSpanSize(position);
                return 1;
            }
        });
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        if (isHeaderViewPosition(position) || isFooterViewPosition(position)) {
            WrapperUtils.setFullSpan(holder);
        }
    }

    @Override
    public int getItemCount() {
        return getHeadersCount() + getRealItemCount() + getFootersCount() + (hasLoadMore() ? 1 : 0);
    }


    public int getRealItemCount() {
        return mInnerAdapter.getItemCount();
    }

    public int getHeadersCount() {
        return mHeaderViews.size();
    }

    public int getFootersCount() {
        return mFooterViews.size();
    }

    public boolean isHeaderViewPosition(int position) {
        return position < getHeadersCount();
    }

    /**
     *
     * @param position current position
     * @return if position is in [headerSize,headerSize+realItemCount)
     */
    public boolean isFooterViewPosition(int position) {
        return position >= getHeadersCount() + getRealItemCount() && position < getHeadersCount()+getRealItemCount();
    }

    public boolean isShowLoadMore(int position) {
        return hasLoadMore() && (position >= getItemCount());
    }

    public boolean isInnerAdapterPosition(int position){
        return position >= getHeadersCount() && position < getRealItemCount()+getHeadersCount();
    }

    public void addHeaderView(View view) {
        mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, view);
    }

    public void addFooterView(View view) {
        mFooterViews.put(mFooterViews.size() + BASE_ITEM_TYPE_FOOTER, view);
    }

    public void removeHeaderView(View view){
        mHeaderViews.remove(mHeaderViews.indexOfValue(view)+BASE_ITEM_TYPE_HEADER);
        notifyDataSetChanged();
    }

    public void removeFooterView(View view){
        mFooterViews.remove(mFooterViews.indexOfValue(view));
        notifyDataSetChanged();
    }

    public RecyclerAdapterWrapper setLoadMoreView(View loadMoreView) {
        mLoadMoreView = loadMoreView;
        return this;
    }

    public RecyclerAdapterWrapper setLoadMoreView(int layoutId) {
        mLoadMoreLayoutId = layoutId;
        return this;
    }

}
