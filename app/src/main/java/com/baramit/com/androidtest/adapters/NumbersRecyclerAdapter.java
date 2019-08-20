package com.baramit.com.androidtest.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.baramit.com.androidtest.R;

import java.util.List;

public class NumbersRecyclerAdapter extends RecyclerView.Adapter<IntegerViewHolder> {

    private static int viewsNum = 0;

    private List<Integer> mNumbers;
    private View.OnClickListener mOnIntegerListener;

    public NumbersRecyclerAdapter(List<Integer> numbers) {
        mNumbers = numbers;
    }

    @NonNull
    @Override
    public IntegerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View numberView;

        //Note: started with single ViewHolder & single XML card file, and then determined, and set the card's height through
        //Layout Params programmatically, until I saw the request for different ViewTypes.

        switch (viewType) {
            case 0:
                numberView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.small_integer_card, viewGroup, false);
                return new IntegerViewHolderSmall(numberView, mOnIntegerListener);
            case 1:
                numberView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.big_integer_card, viewGroup, false);
                return new IntegerViewHolderBig(numberView, mOnIntegerListener);
            default:
                numberView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.small_integer_card, viewGroup, false);
                return new IntegerViewHolderSmall(numberView, mOnIntegerListener);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull IntegerViewHolder viewHolder, int position) {
        viewHolder.setData(mNumbers.get(position), position);
    }

    @Override
    public int getItemCount() {
        if (mNumbers != null) {
            return mNumbers.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        int mType = isZeroPaired(mNumbers.get(position), position) ? 1 : 0;
        return mType;
    }

    public void setNumbers(List<Integer> numbers) {
        mNumbers = numbers;
        notifyDataSetChanged();
    }

    public Integer getSelectedNumber(int position) {
        if (mNumbers != null) {
            if (mNumbers.size() > 0) {
                return mNumbers.get(position);
            }
        }
        return null;
    }

    public List<Integer> getNumbers() {
        if (mNumbers != null)
            return mNumbers;
        return null;
    }

    private boolean isZeroPaired(Integer mNumber, int position) {
        //Assumption: the List<Integer> is an ordered list.
        //Note: dropped the option of "contains" method due to better run time & additional checks.
        for (int i = 0; i < mNumbers.size(); i++) {
            Integer mValue = mNumbers.get(i);
            if (mNumber + mValue > 0)
                break;
            if (mNumber + mValue == 0 && position != i)
                return true;
        }

        return false;
    }

    public void setmOnIntegerListener(View.OnClickListener mOnIntegerListener) {
        this.mOnIntegerListener = mOnIntegerListener;
    }

}