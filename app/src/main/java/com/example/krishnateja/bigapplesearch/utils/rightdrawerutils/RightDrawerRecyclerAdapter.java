package com.example.krishnateja.bigapplesearch.utils.rightdrawerutils;

import android.content.Context;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krishnateja.bigapplesearch.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by krishnateja on 4/28/2015.
 */
public class RightDrawerRecyclerAdapter extends RecyclerView.Adapter<RightDrawerRecyclerAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mTextData;
    private ArrayList<Integer> mSpinnerData;
    private int mDataSize = 0;
    private static final String TAG = RightDrawerRecyclerAdapter.class.getSimpleName();
    private int[] mFilterList;
    private int[] mSendingList;

    public interface Filters {
        public void getFilters(int[] filters);
    }

    private Filters mFilters;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        Spinner spinner;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            spinner = (Spinner) itemView.findViewById(R.id.item_right_list_spinner);
            textView = (TextView) itemView.findViewById(R.id.item_right_list_textview);
        }
    }

    public RightDrawerRecyclerAdapter(Context context, ArrayList<String> textData,
                                      ArrayList<Integer> spinnerData, Button button, final DrawerLayout drawerLayout) {
        mContext = context;
        mTextData = textData;
        mSpinnerData = spinnerData;
        mDataSize = mTextData.size();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean changed = false;
                for (int i = 0; i < mDataSize; i++) {
                    if (mFilterList[i] != mSendingList[i]) {
                        changed = true;
                    }
                    Log.d(TAG, "mFilterList->" + mFilterList[i] + "mSendingList->" + mSendingList[i]);

                }
                mSendingList = Arrays.copyOf(mFilterList, mFilterList.length);;
                if (changed) {
                    mFilters.getFilters(mSendingList);
                } else {
                    Log.d(TAG, "nothing changed");
                }
                drawerLayout.closeDrawer(GravityCompat.END);
            }
        });
        try {
            mFilters = (Filters) mContext;
        } catch (ClassCastException e) {
            throw new ClassCastException(mContext.toString() + " must implement Filters");
        }
        mFilterList = new int[mDataSize];
        mSendingList = new int[mDataSize];
        for (int i = 0; i < mFilterList.length; i++) {
            mFilterList[i] = 0;
            mSendingList[i] = 0;
        }
    }

    @Override
    public RightDrawerRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_right_drawer_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RightDrawerRecyclerAdapter.ViewHolder viewHolder, int i) {
        final int pos = i;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext,
                mSpinnerData.get(i), android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewHolder.spinner.setAdapter(adapter);
        viewHolder.textView.setText(mTextData.get(i));
        viewHolder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "in menu->" + viewHolder.spinner.getSelectedItemPosition());
                mFilterList[pos] = viewHolder.spinner.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return mTextData.size();
    }
}
