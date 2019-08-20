package com.baramit.com.androidtest.adapters;

import android.view.View;
import android.widget.TextView;

import com.baramit.com.androidtest.R;

public class IntegerViewHolderSmall extends IntegerViewHolder {
    private TextView integerTextView;

    public IntegerViewHolderSmall(View integerView, View.OnClickListener mOnIntegerClickListener) {
        //init ViewHolder with infrastructure to intercept onItemClickListener
        super(integerView, mOnIntegerClickListener);
        integerTextView = integerView.findViewById(R.id.value);
    }

    public void setData(final Integer number, int position) {
        String stringVal = String.valueOf(number);
        integerTextView.setText(stringVal);
    }

}