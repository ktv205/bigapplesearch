package com.example.krishnateja.bigapplesearch.utils.rightdrawerutils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krishnateja.bigapplesearch.R;

import java.util.ArrayList;

/**
 * Created by krishnateja on 4/28/2015.
 */
public class RightDrawerRecyclerAdapter extends RecyclerView.Adapter<RightDrawerRecyclerAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mTextData;
    private ArrayList<Integer> mSpinnerData;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Spinner spinner;
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            spinner=(Spinner)itemView.findViewById(R.id.item_right_list_spinner);
            textView=(TextView)itemView.findViewById(R.id.item_right_list_textview);
        }
    }

    public RightDrawerRecyclerAdapter(Context context, ArrayList<String> textData,ArrayList<Integer> spinnerData) {
        mContext = context;
        mTextData=textData;
        mSpinnerData=spinnerData;
    }

    @Override
    public RightDrawerRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_right_drawer_list,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RightDrawerRecyclerAdapter.ViewHolder viewHolder, int i) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext,
                mSpinnerData.get(i), android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewHolder.spinner.setAdapter(adapter);
        viewHolder.textView.setText(mTextData.get(i));
        viewHolder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext,parent.getItemIdAtPosition(position)+"",Toast.LENGTH_SHORT).show();
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
