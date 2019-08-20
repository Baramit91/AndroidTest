package com.baramit.com.androidtest.adapters;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baramit.com.androidtest.R;

public class IntegerViewHolderBig extends IntegerViewHolder {
    private TextView integerTextView;

    public IntegerViewHolderBig(View integerView, View.OnClickListener mOnIntegerClickListener) {
        //init ViewHolder with infrastructure to intercept onItemClickListener
        super(integerView, mOnIntegerClickListener);
        integerTextView = integerView.findViewById(R.id.value);
    }

    public void setData(final Integer number, int position) {
        String stringVal = String.valueOf(number);
        integerTextView.setTextColor(Color.BLUE);
        integerTextView.setTypeface(Typeface.DEFAULT_BOLD);
        integerTextView.setText(stringVal);
    }

}