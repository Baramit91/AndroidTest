package com.baramit.com.androidtest.adapters;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.baramit.com.androidtest.R;

public abstract class IntegerViewHolder extends RecyclerView.ViewHolder {
    private TextView integerTextView;
    private View.OnClickListener mOnIntegerClickListener;

    public IntegerViewHolder(View integerView, View.OnClickListener mOnIntegerClickListener) {
        //init ViewHolder with infrastructure to intercept onItemClickListener
        super(integerView);

        this.mOnIntegerClickListener = mOnIntegerClickListener;
        integerView.setTag(this);
        integerView.setOnClickListener(mOnIntegerClickListener);
    }

    abstract public void setData(final Integer number, int position);

}