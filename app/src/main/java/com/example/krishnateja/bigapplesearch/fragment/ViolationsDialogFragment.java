package com.example.krishnateja.bigapplesearch.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.krishnateja.bigapplesearch.R;
import com.example.krishnateja.bigapplesearch.models.AppConstants;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by krishnateja on 5/7/2015.
 */
public class ViolationsDialogFragment extends DialogFragment {

    private static final String TAG =ViolationsDialogFragment.class.getSimpleName() ;
    View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_dialog_violations, container, false);
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        ArrayList<String> violationCodes = bundle.getStringArrayList(AppConstants.BundleExtras.VIOLATION_CODES);
        ArrayList<String> violationText = getViolationText(violationCodes);
        ListView listView = (ListView) mView.findViewById(R.id.fragment_dialog_violations_list_view);
        MyBaseAdapter adapter = new MyBaseAdapter(violationCodes, violationText);
        TextView textView=(TextView)mView.findViewById(R.id.fragment_dialog_violations_score);
        int score=bundle.getInt(AppConstants.BundleExtras.VIOLATION_SCORE);
        textView.setText("Total violation score "+score);
        if(score<=30){
            textView.setTextColor(getActivity().getResources().getColor(R.color.violationGreen));
        }else if(score>=70){
            textView.setTextColor(getActivity().getResources().getColor(R.color.red));
        }else{
            textView.setTextColor(getActivity().getResources().getColor(R.color.colorAccent));
        }

        Button button=(Button)mView.findViewById(R.id.fragment_dialog_violations_ok_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }
        });
        listView.setAdapter(adapter);

    }

    public ArrayList<String> getViolationText(ArrayList<String> codes) {
        HashMap<String, String> hashMap = getViolationHashMap();
        ArrayList<String> text = new ArrayList<>();
        for (String code : codes) {
            Log.d(TAG, code);
            if (hashMap.containsKey(code.replaceAll("\\s", ""))) {
                text.add(hashMap.get(code.replaceAll("\\s", "")));
            } else {
                text.add("No description available");
            }
        }
        for(Map.Entry<String,String> entry : hashMap.entrySet()){
            Log.d(TAG,entry.getKey());
        }
        return text;
    }

    public HashMap<String, String> getViolationHashMap() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("04H", "Critical Violations - Food Protection");
        hashMap.put("10F", "General Violations Conditions - Facility Maintenance");
        hashMap.put("10J", "General Violations Conditions - Facility Maintenance");
        hashMap.put("04C", "Critical Violations - Food Protection");
        hashMap.put("06C", "Critical Violations - Personal Hygiene & Other Food Protection");
        hashMap.put("06E", "Critical Violations - Personal Hygiene & Other Food Protection");
        hashMap.put("02G", "Critical Violations - Food Temperature");
        hashMap.put("04N", "Critical Violations - Food Protection");
        hashMap.put("06D", "Critical Violations - Personal Hygiene & Other Food Protection");
        hashMap.put("08A", "General Violations Conditions - Vermin/Garbage");
        hashMap.put("10B", "General Violations Conditions - Facility Maintenance");
        hashMap.put("10E", "General Violations Conditions - Facility Maintenance");
        hashMap.put("04J", "Critical Violations - Food Protection");
        return hashMap;


    }

    public class MyBaseAdapter extends BaseAdapter {

        ArrayList<String> mCode;
        ArrayList<String> mText;

        public MyBaseAdapter(ArrayList<String> code, ArrayList<String> text) {
            mCode = code;
            mText = text;

        }

        @Override
        public int getCount() {
            return mCode.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            Holder holder;
            if (view == null) {
                view = LayoutInflater.from(getActivity()).inflate(android.R.layout.simple_list_item_2, parent, false);
                holder = new Holder(view);
                view.setTag(holder);
            } else {
                holder = (Holder) view.getTag();
            }
            holder.textView1.setText(mCode.get(position));
            holder.textView2.setText(mText.get(position));
            return view;
        }

        public class Holder {
            TextView textView1, textView2;

            public Holder(View view) {
                textView1 = (TextView) view.findViewById(android.R.id.text1);
                textView2 = (TextView) view.findViewById(android.R.id.text2);
            }

        }
    }


}
