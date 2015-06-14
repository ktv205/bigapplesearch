package com.example.krishnateja.bigapplesearch.activities;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.krishnateja.bigapplesearch.R;
import com.example.krishnateja.bigapplesearch.models.AppConstants;
import com.example.krishnateja.bigapplesearch.services.FetchAddressIntentService;
import com.example.krishnateja.bigapplesearch.utils.CommonAsyncTask;
import com.example.krishnateja.bigapplesearch.utils.CommonFunctions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by krishnateja on 5/26/2015.
 */
public class RouteFinderActivity extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private AutoCompleteTextView mFromAutoCompleteTextView, mToAutoCompleteTextView;
    private Button mRouteFinderButton;
    private GoogleApiClient mGoogleApiClient;
    private Toolbar mToolbar;
    String mFromAddress, mToAddress;
    private AddressResultReceiver mResultReceiver;
    private boolean mIsSetFromCurrentAddress = true;

    private String TAG = RouteFinderActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_finder);
        referenceXmlElements();
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_route_finder_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        if (savedInstanceState != null) {
            mToAutoCompleteTextView.setText(savedInstanceState.getString(AppConstants.BundleExtras.TO_ADDRESS));
            mFromAutoCompleteTextView.setText(savedInstanceState.getString(AppConstants.BundleExtras.FROM_ADDRESS));
        }


    }

    public void referenceXmlElements() {
        mFromAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.from_auto_complete_text);
        mToAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.to_auto_complete_text);
        mRouteFinderButton = (Button) findViewById(R.id.find_route_button);
        ImageButton toCancelButton, fromCancelButton, toCurrentButton, fromCurrentButton;
        toCancelButton = (ImageButton) findViewById(R.id.to_cancel_image_button);
        fromCancelButton = (ImageButton) findViewById(R.id.from_cancel_image_button);
        toCurrentButton = (ImageButton) findViewById(R.id.to_current_location_image_button);
        fromCurrentButton = (ImageButton) findViewById(R.id.from_current_location_image_button);
        toCancelButton.setOnClickListener(this);
        fromCancelButton.setOnClickListener(this);
        toCurrentButton.setOnClickListener(this);
        fromCurrentButton.setOnClickListener(this);
        mRouteFinderButton.setOnClickListener(this);
        mFromAutoCompleteTextView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                if (textView != null) {
                    Log.d(TAG, "textView from->" + textView.getText().toString());
                    mFromAddress = textView.getText().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mToAutoCompleteTextView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                if (textView != null) {
                    mToAddress = textView.getText().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();

    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(AppConstants.BundleExtras.FROM_ADDRESS, mFromAddress);
        outState.putString(AppConstants.BundleExtras.TO_ADDRESS, mToAddress);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onConnected(Bundle bundle) {
        AutoCompleteArrayAdapter adapter = new AutoCompleteArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, android.R.id.text1, new ArrayList<String>());
        mFromAutoCompleteTextView.setAdapter(adapter);
        mFromAutoCompleteTextView.setThreshold(0);
        adapter.getGoogleApiClient(mGoogleApiClient);
        mToAutoCompleteTextView.setAdapter(adapter);
        mToAutoCompleteTextView.setThreshold(0);
        adapter.getGoogleApiClient(mGoogleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.from_cancel_image_button) {
            mFromAutoCompleteTextView.setText("");
        } else if (id == R.id.to_cancel_image_button) {
            mToAutoCompleteTextView.setText("");
        } else if (id == R.id.to_current_location_image_button) {
            mIsSetFromCurrentAddress = false;
            setCurrentAddress();

        } else if (id == R.id.from_current_location_image_button) {
            mIsSetFromCurrentAddress = true;
            setCurrentAddress();
        } else if (id == R.id.find_route_button) {
            if (!mFromAddress.isEmpty() && !mToAddress.isEmpty()) {
                   sendAddressesToServer(mFromAddress,mToAddress);
            }
        }

    }

    public class AutoCompleteArrayAdapter extends ArrayAdapter<String> {
        public GoogleApiClient mGoogleApiClient;
        Context mContext;
        ArrayList<String> arrayList;

        public AutoCompleteArrayAdapter(Context context, int resource, int textViewResourceId, ArrayList<String> arrayList) {
            super(context, resource, textViewResourceId, arrayList);
            this.arrayList = arrayList;

        }


        @Override
        public Filter getFilter() {

            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint.length() > 3) {
                        displayPredictiveResults(constraint.toString());
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {

                }
            };
        }


        public class ViewHolder {
            TextView textView;

            public ViewHolder(View view) {
                textView = (TextView) view.findViewById(android.R.id.text1);
            }
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            ViewHolder holder;
            if (view == null) {
                view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.textView.setText(arrayList.get(position));
            return view;
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        public void getGoogleApiClient(GoogleApiClient googleApiClient) {
            mGoogleApiClient = googleApiClient;
        }

        public void displayPredictiveResults(String query) {

            LatLngBounds newyorkLatLngBounds = new LatLngBounds(new LatLng(40.477399, -73.700272), new LatLng(40.917577, -74.25909));
            Places.GeoDataApi.getAutocompletePredictions(mGoogleApiClient, query, newyorkLatLngBounds, null).setResultCallback(
                    new ResultCallback<AutocompletePredictionBuffer>() {

                        @Override
                        public void onResult(AutocompletePredictionBuffer autocompletePredictions) {

                            if (autocompletePredictions == null) {

                            } else if (autocompletePredictions.getStatus().isSuccess()) {

                                arrayList.clear();
                                for (AutocompletePrediction prediction : autocompletePredictions) {
                                    Log.d(TAG, prediction.getDescription());
                                    arrayList.add(prediction.getDescription());
                                }
                            } else if (autocompletePredictions.getStatus().isCanceled()) {

                            } else if (autocompletePredictions.getStatus().isInterrupted()) {

                            } else {

                            }
                            autocompletePredictions.release();
                            notifyDataSetChanged();
                        }
                    }, 60, TimeUnit.SECONDS
            );
        }
    }

    public void setCurrentAddress() {
        if (mGoogleApiClient != null) {
            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if (location != null) {
                startIntentService(location);
            }
        }
    }

    protected void startIntentService(Location location) {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        mResultReceiver = new AddressResultReceiver(new Handler(Looper.getMainLooper()));
        intent.putExtra(AppConstants.Constants.RECEIVER, mResultReceiver);
        intent.putExtra(AppConstants.Constants.LOCATION_DATA_EXTRA, location);
        startService(intent);
    }

    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            Log.d(TAG, "here in onReceive result");
            if (mIsSetFromCurrentAddress) {
                mFromAutoCompleteTextView.setText(resultData.getString(AppConstants.Constants.RESULT_DATA_KEY));
            } else {
                mToAutoCompleteTextView.setText(resultData.getString(AppConstants.Constants.RESULT_DATA_KEY));
            }

            // Show a toast message if an address was found.
            if (resultCode == AppConstants.Constants.SUCCESS_RESULT) {
                //showToast(getString(R.string.address_found));
            }

        }
    }

    public void sendAddressesToServer(String fromAddress,String toAddress){
        Log.d(TAG,"fromAddress->"+fromAddress);
        Log.d(TAG,"toAddress->"+toAddress);
        String[] paths = new String[2];
        paths[0] = AppConstants.ServerVariables.PATH;
        paths[1] = AppConstants.ServerVariables.ALERTS_FILE;
        HashMap<String, String> getVariables = new HashMap<>();
        getVariables.put(AppConstants.ServerVariables.FROM_ADD_GET_VARIABLE,fromAddress);
        getVariables.put(AppConstants.ServerVariables.TO_ADD_GET_VARIABLE,toAddress);
        //new CommonAsyncTask(this, AppConstants.InAppConstants.FIND_ROUTE_CODE).execute(CommonFunctions.buildParams(paths, getVariables, this));
    }


}
