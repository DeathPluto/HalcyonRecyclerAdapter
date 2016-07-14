package com.halcyon.utils;

/**
 * ©2016-2017 kmhealthcloud.All Rights Reserved <p/>
 * Created by: L  <br/>
 * Description:
 */
public class ModelWrapper<T> {

    public T mData;
    public boolean isChecked;

    public ModelWrapper(T data, boolean isChecked) {
        mData = data;
        this.isChecked = isChecked;
    }

    @Override
    public String toString() {
        return "ModelWrapper{" +
                "mData=" + mData +
                ", isChecked=" + isChecked +
                '}';
    }
}
