package com.example.krishnateja.bigapplesearch.fragment;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.krishnateja.bigapplesearch.R;
import com.example.krishnateja.bigapplesearch.models.CitiBikeMainScreenModel;
import com.example.krishnateja.bigapplesearch.models.MTAMainScreenModel;
import com.example.krishnateja.bigapplesearch.models.RestaurantMainScreenModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Created by krishnateja on 5/1/2015.
 */
public class MapViewFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {
    private static final String TAG =MapViewFragment.class.getSimpleName() ;
    MapView mapView;
    GoogleMap myMap;
    private MapViewFragmentInstance mMapViewFragmentInstance;
    private ArrayList<Marker> mMarkers;
    private GoogleApiClient mGoogleApiClient;

    @Override
    public void onConnected(Bundle bundle) {
        Location location=null;
        if(mGoogleApiClient!=null) {
           location = LocationServices.FusedLocationApi
                    .getLastLocation(mGoogleApiClient);
        }
        if(location!=null) {
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 15));
            Marker marker = myMap
                    .addMarker(new MarkerOptions()
                            .position(
                                    new LatLng(lat, lng))
                            .title("you are here")
                            .icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            mMarkers.add(marker);
        }
        if(mGoogleApiClient!=null) {
            mGoogleApiClient.disconnect();
        }
        mGoogleApiClient=null;

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public interface MapViewFragmentInstance {
        void getMapViewFragmentInstance(MapViewFragment mapViewFragment);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mMapViewFragmentInstance = (MapViewFragmentInstance) activity;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMarkers = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = (MapView) view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myMap = mapView.getMap();
        mMapViewFragmentInstance.getMapViewFragmentInstance(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    public void getDataFromMain(ArrayList<MTAMainScreenModel> mtaMainScreenModelArrayList,
                                ArrayList<CitiBikeMainScreenModel> citiBikeMainScreenModelArrayList,
                                ArrayList<RestaurantMainScreenModel> restaurantMainScreenModelArrayList) {
        Log.d(TAG, "here");
        removeMarkers();
        if (mtaMainScreenModelArrayList != null) {
            for (MTAMainScreenModel mtaMainScreenModel : mtaMainScreenModelArrayList) {
                Marker marker = myMap
                        .addMarker(new MarkerOptions()
                                .position(
                                        new LatLng(mtaMainScreenModel.getStopLatitude(),
                                                mtaMainScreenModel.getStopLongitude()))
                                .title(mtaMainScreenModel.getStopName())
                                .icon(BitmapDescriptorFactory
                                        .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                mMarkers.add(marker);
            }

        }
        if (citiBikeMainScreenModelArrayList != null) {
            for (CitiBikeMainScreenModel citiBikeMainScreenModel : citiBikeMainScreenModelArrayList) {
                Marker marker = myMap
                        .addMarker(new MarkerOptions()
                                .position(
                                        new LatLng(citiBikeMainScreenModel.getLatitude(),
                                                citiBikeMainScreenModel.getLongitude()))
                                .title(citiBikeMainScreenModel.getStationName())
                                .icon(BitmapDescriptorFactory
                                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                mMarkers.add(marker);
            }
        }
        if (restaurantMainScreenModelArrayList != null) {
            for (RestaurantMainScreenModel restaurantMainScreenModel : restaurantMainScreenModelArrayList) {
                Marker marker = myMap
                        .addMarker(new MarkerOptions()
                                .position(
                                        new LatLng(restaurantMainScreenModel.getLat(),
                                                restaurantMainScreenModel.getLng()))
                                .title(restaurantMainScreenModel.getResName())
                                .icon(BitmapDescriptorFactory
                                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                mMarkers.add(marker);
            }

        }
        buildGoogleApi();

    }

    private void buildGoogleApi() {

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API).
                        addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        mGoogleApiClient.connect();
    }

    public void removeMarkers() {
        if (mMarkers!=null && mMarkers.size() != 0) {
            for (Marker marker : mMarkers) {
                marker.remove();
            }
        }else{
            mMarkers=new ArrayList<>();
        }
    }
}
