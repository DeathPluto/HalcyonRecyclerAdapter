package com.halcyon;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.TextWatcher;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TextView.BufferType;

/**
 * ©2016-2017 kmhealthcloud.All Rights Reserved <p/>
 * Created by: L  <br/>
 * Description:
 */
public class HalcyonViewHolder extends RecyclerView.ViewHolder implements Checkable{
    /**
     * key:view的id
     * value: view对象
     */
    private SparseArray<View> mViews;
    private Context mContext;
    private View mItemView;
    private int mCheckableId = -1;

    public HalcyonViewHolder(View itemView) {
        super(itemView);
        mItemView = itemView;
        mContext = itemView.getContext();
        mViews = new SparseArray<>();
    }

    public HalcyonViewHolder setCheckableId(int checkableId) {
        mCheckableId = checkableId;
        return this;
    }

    @Override
    public void setChecked(boolean checked) {
        if(mCheckableId != -1){
            setChecked(mCheckableId,checked);
        }
    }

    @Override
    public boolean isChecked() {
        if(mCheckableId != -1){
            Checkable checkable = getView(mCheckableId);
            return checkable.isChecked();
        }
        return false;
    }

    @Override
    public void toggle() {
        if(mCheckableId != -1){
            Checkable checkable = getView(mCheckableId);
            checkable.setChecked(!checkable.isChecked());
        }
    }

    public static HalcyonViewHolder createViewHolder(View itemView) {
        HalcyonViewHolder holder = new HalcyonViewHolder(itemView);
        return holder;
    }

    public static HalcyonViewHolder createViewHolder(Context context,
                                                     ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        HalcyonViewHolder holder = new HalcyonViewHolder(itemView);
        return holder;
    }

    private <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 设置TextView的值
     *
     * @param viewId
     * @param text
     * @return
     */
    public HalcyonViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public HalcyonViewHolder setText(int viewId, String text, BufferType type) {
        TextView tv = getView(viewId);
        tv.setText(text, type);
        return this;
    }

    public HalcyonViewHolder setEditable(int viewId, boolean editable) {
        EditText et = getView(viewId);
        et.setEnabled(editable);
        return this;
    }

    public HalcyonViewHolder setTextSize(int viewId, float textSize) {
        TextView tv = getView(viewId);
        tv.setTextSize(textSize);
        return this;
    }

    public HalcyonViewHolder setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    public HalcyonViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public HalcyonViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    public HalcyonViewHolder setBackgroundColor(int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public HalcyonViewHolder setBackgroundRes(int viewId, int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public HalcyonViewHolder setTextColor(int viewId, int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    public HalcyonViewHolder setTextColorRes(int viewId, int textColorRes) {
        TextView view = getView(viewId);
        view.setTextColor(mContext.getResources().getColor(textColorRes));
        return this;
    }

    @SuppressLint("NewApi")
    public HalcyonViewHolder setAlpha(int viewId, float value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView(viewId).setAlpha(value);
        } else {
            // Pre-honeycomb hack to set Alpha value
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            getView(viewId).startAnimation(alpha);
        }
        return this;
    }

    public HalcyonViewHolder setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public HalcyonViewHolder linkify(int viewId) {
        TextView view = getView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    public HalcyonViewHolder setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = getView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    public HalcyonViewHolder setProgress(int viewId, int progress) {
        ProgressBar view = getView(viewId);
        view.setProgress(progress);
        return this;
    }

    public HalcyonViewHolder setProgress(int viewId, int progress, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    public HalcyonViewHolder setMax(int viewId, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        return this;
    }

    public HalcyonViewHolder setRating(int viewId, float rating) {
        RatingBar view = getView(viewId);
        view.setRating(rating);
        return this;
    }

    public HalcyonViewHolder setRating(int viewId, float rating, int max) {
        RatingBar view = getView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    public HalcyonViewHolder setTag(int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    public HalcyonViewHolder setTag(int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public HalcyonViewHolder setChecked(int viewId, boolean checked) {
        Checkable view = (Checkable) getView(viewId);
        view.setChecked(checked);
        return this;
    }

    /**
     * 关于事件的
     */
    public HalcyonViewHolder setOnClickListener(int viewId,
                                                View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public HalcyonViewHolder setOnTouchListener(int viewId,
                                                View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public HalcyonViewHolder setOnLongClickListener(int viewId,
                                                    View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

    public HalcyonViewHolder addTextChangedListener(int viewId, TextWatcher watcher) {
        EditText editText = getView(viewId);
        editText.addTextChangedListener(watcher);
        return this;
    }
}
