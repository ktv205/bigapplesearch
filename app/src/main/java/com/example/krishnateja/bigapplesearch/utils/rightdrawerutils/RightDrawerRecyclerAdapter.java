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

import com.example.krishnateja.bigapplesearch.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by krishnateja on 4/28/2015.
 */
public class RightDrawerRecyclerAdapter extends RecyclerView.Adapter<RightDrawerRecyclerAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mTextData;
    private ArrayList<Integer> mSpinnerData;
    private HashMap<Integer, Boolean> mFiltersToShow;
    private int mDataSize = 0;
    private static final String TAG = RightDrawerRecyclerAdapter.class.getSimpleName();
    private HashMap<String, Integer> mSpinnerSelection;
    private HashMap<String, Integer> mPreviousSelection;

    public HashMap<String, Integer> getmSpinnerSelection() {
        return mSpinnerSelection;
    }

    private SpinnerSelectionInterface mSpinnerSelectionInterface;

    public interface SpinnerSelectionInterface {
        void getSpinnerSelection(HashMap<String, Integer> spinnerSelection);
    }

    public void setmSpinnerSelection(HashMap<String, Integer> mSpinnerSelection) {
        mPreviousSelection = new HashMap<>();
        mPreviousSelection.putAll(mSpinnerSelection);
        this.mSpinnerSelection = mSpinnerSelection;
    }

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
                                      ArrayList<Integer> spinnerData, HashMap<Integer, Boolean> filtersToShow, Button button, final DrawerLayout drawerLayout) {
        mContext = context;
        mTextData = new ArrayList<>();
        mSpinnerData = new ArrayList<>();
        mFiltersToShow = filtersToShow;
        applyFiltersToData(textData, spinnerData);
        mDataSize = mTextData.size();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (changed()) {
                    Log.d(TAG, "changed");
                } else {
                    Log.d(TAG, "not changed");
                }
                mSpinnerSelectionInterface.getSpinnerSelection(mSpinnerSelection);
                mPreviousSelection.clear();
                mPreviousSelection.putAll(mSpinnerSelection);
                drawerLayout.closeDrawer(GravityCompat.END);
            }
        });

        mSpinnerSelectionInterface = (SpinnerSelectionInterface) context;


    }

    private void applyFiltersToData(ArrayList<String> textData, ArrayList<Integer> spinnerData) {
        for (Map.Entry<Integer, Boolean> entry : mFiltersToShow.entrySet()) {
            if (entry.getValue()) {
                Log.d(TAG, "in  get value");
                mTextData.add(textData.get(entry.getKey()));
                mSpinnerData.add(spinnerData.get(entry.getKey()));
            }
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
        Log.d(TAG, "true->" + mFiltersToShow.get(i));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext,
                mSpinnerData.get(i), android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewHolder.spinner.setAdapter(adapter);
        viewHolder.textView.setText(mTextData.get(i));
        if (mSpinnerSelection.containsKey(viewHolder.textView.getText().toString())) {
            viewHolder.spinner.setSelection(mSpinnerSelection.get(viewHolder.textView.getText().toString()));
        }
        viewHolder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSpinnerSelection.put(viewHolder.textView.getText().toString(), position);
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


    public void changeDataSet(ArrayList<Integer> integerData, ArrayList<String> stringData,
                              HashMap<Integer, Boolean> filtersToShow) {
        mTextData.clear();
        mSpinnerData.clear();
        mFiltersToShow.clear();
        mFiltersToShow = filtersToShow;
        applyFiltersToData(stringData, integerData);
        mDataSize = mTextData.size();
        Log.d(TAG, "size of mDataSize->" + mDataSize);
        notifyDataSetChanged();
    }

    public boolean changed() {
        return !mSpinnerSelection.equals(mPreviousSelection);
    }


}
