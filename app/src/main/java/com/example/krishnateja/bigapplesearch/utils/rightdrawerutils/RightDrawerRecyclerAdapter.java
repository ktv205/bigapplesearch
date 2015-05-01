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
import com.example.krishnateja.bigapplesearch.models.AppConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by krishnateja on 4/28/2015.
 */
public class RightDrawerRecyclerAdapter extends RecyclerView.Adapter<RightDrawerRecyclerAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mTextData;
    private ArrayList<Integer> mSpinnerData;
    private int mDataSize = 0;
    private static final String TAG = RightDrawerRecyclerAdapter.class.getSimpleName();
    private HashMap<Integer, Integer> mFilterList;
    private HashMap<Integer, Integer> mSendingList;

    public interface Filters {
        public void getFilters(HashMap<Integer, Integer> filters);
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
        mFilterList = new HashMap<>();
        mSendingList = new HashMap<>();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean changed = changed();
                mSendingList.clear();
                mSendingList.putAll(mFilterList);

                if (changed) {
                    Log.d(TAG, "changed");
                    mFilters.getFilters(mSendingList);
                }
                drawerLayout.closeDrawer(GravityCompat.END);
            }
        });
        try {
            mFilters = (Filters) mContext;
        } catch (ClassCastException e) {
            throw new ClassCastException(mContext.toString() + " must implement Filters");
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
                int positionInsideSpinner = viewHolder.spinner.getSelectedItemPosition();
                String text = viewHolder.textView.getText().toString();
                if (text.equals(AppConstants.InAppConstants.SHOW_TEXT)) {
                    mFilterList.put(AppConstants.InAppConstants.SHOW, positionInsideSpinner);
                } else if (text.equals(AppConstants.InAppConstants.CUISINE_TEXT)) {
                    mFilterList.put(AppConstants.InAppConstants.CUISINE, positionInsideSpinner);
                } else if (text.equals(AppConstants.InAppConstants.DISTANCE_TEXT)) {
                    mFilterList.put(AppConstants.InAppConstants.DISTANCE, positionInsideSpinner);
                } else if (text.equals(AppConstants.InAppConstants.PRICE_TEXT)) {
                    mFilterList.put(AppConstants.InAppConstants.PRICE, positionInsideSpinner);
                } else {
                    mFilterList.put(AppConstants.InAppConstants.RATING, positionInsideSpinner);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataSize;
    }

    public boolean changed() {
        return !mFilterList.equals(mSendingList);
    }

    public void changeDataSet(ArrayList<Integer> integerData, ArrayList<String> stringData) {
        mTextData = stringData;
        mSpinnerData = integerData;
        mDataSize = mTextData.size();
        notifyDataSetChanged();
        mFilterList.clear();
        mSendingList.clear();
    }
}
