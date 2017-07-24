package client_android.rutemaps;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.NetworkOnMainThreadException;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.AvoidType;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import client_android.rutemaps.model.DataTersimpanModel;
import client_android.rutemaps.model.DefaultResponseModel;
import client_android.rutemaps.model.DoneResponseModel;
import client_android.rutemaps.service.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMarkerDragListener, GoogleMap.OnMarkerClickListener{
    private int index_rute = 0;
    private boolean status_button_done = true;
    private GoogleMap mMap;
    private DoneResponseModel data_rute;
    private Polyline rute;
    Button addGudang,addTaman, done;
    HashMap<String, Boolean> data_marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        addGudang = (Button) findViewById(R.id.addgudang);
        addTaman = (Button) findViewById(R.id.addtaman);
        done = (Button)findViewById(R.id.done);

        data_marker = new HashMap<String, Boolean>();

        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);



        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(status_button_done){
                    try {
                        Response<DoneResponseModel> response = ApiService.service_get.getHasil().execute();
                        data_rute = response.body();
                        done.setText("Show Route");
                        status_button_done = false;

                    }catch (IOException e){
                        Toast.makeText(MapsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }catch (NetworkOnMainThreadException f){
                        Toast.makeText(MapsActivity.this, f.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
//                    index_rute = index_rute+1;
                    if(index_rute<data_rute.getDetail().size()){
                        nextRute(index_rute++); ;
                    }else{
                        Toast.makeText(MapsActivity.this, "Wes entek bro", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });



        ApiService.service_get.getData().enqueue(new Callback<DataTersimpanModel>() {
            @Override
            public void onResponse(Call<DataTersimpanModel> call, Response<DataTersimpanModel> response) {
                for(int i = 0; i< response.body().getGudang().size();i++){
                    LatLng lokasi = new LatLng(Double.parseDouble(response.body().getGudang().get(i).getLat()), Double.parseDouble(response.body().getGudang().get(i).getLng()));
                    MarkerOptions titik_gudang = new MarkerOptions()
                            .position(lokasi)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.pabrik))
                            .title(response.body().getGudang().get(i).getNama())
                            .draggable(true)
                            .snippet(response.body().getGudang().get(i).getStock());

                    Marker marker = mMap.addMarker(titik_gudang);

                    data_marker.put(marker.getId(), false);
                }

                for(int i = 0; i< response.body().getTaman().size();i++){
                    LatLng lokasi = new LatLng(Double.parseDouble(response.body().getTaman().get(i).getLat()), Double.parseDouble(response.body().getTaman().get(i).getLng()));
                    MarkerOptions titik_taman = new MarkerOptions()
                            .position(lokasi)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.taman))
                            .title(response.body().getTaman().get(i).getNama())
                            .draggable(true)
                            .snippet(response.body().getTaman().get(i).getStock());

                    Marker marker = mMap.addMarker(titik_taman);
                    data_marker.put(marker.getId(), true);
                }
            }

            @Override
            public void onFailure(Call<DataTersimpanModel> call, Throwable t) {

            }
        });


        addGudang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater = getLayoutInflater();
                final AlertDialog alert_gudang = new AlertDialog.Builder(MapsActivity.this).create();
                alert_gudang.setView(inflater.inflate(R.layout.alert_add_gudang, null));
                alert_gudang.show();

                Button tambahkan = (Button) alert_gudang.findViewById(R.id.bt_tambahkan_gudang);
                final EditText nama = (EditText) alert_gudang.findViewById(R.id.nama_gudang);
                final EditText stock = (EditText) alert_gudang.findViewById(R.id.stock_gudang);
                tambahkan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alert_gudang.dismiss();
                        LatLng lokasi = new LatLng(-7.2767636, 112.7927149);

                        MarkerOptions titik_gudang = new MarkerOptions()
                                .position(lokasi)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                                .title(String.valueOf(nama.getText()))
                                .draggable(true)
                                .snippet(String.valueOf(stock.getText())
                                );

                        Marker marker = mMap.addMarker(titik_gudang);

                        data_marker.put(marker.getId(), false);

                    }
                });

            }
        });

        addTaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getLayoutInflater();
                final AlertDialog alert_taman = new AlertDialog.Builder(MapsActivity.this).create();
                alert_taman.setView(inflater.inflate(R.layout.alert_add_taman, null));
                alert_taman.show();

                Button tambahkan = (Button) alert_taman.findViewById(R.id.bt_tambahkan_taman);
                final EditText nama = (EditText) alert_taman.findViewById(R.id.nama_taman);
                final EditText kebutuhan = (EditText) alert_taman.findViewById(R.id.kebutuhan_taman);
                tambahkan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alert_taman.dismiss();
                        LatLng lokasi = new LatLng(-7.2767636, 112.7927149);

                        MarkerOptions titik_taman = new MarkerOptions()
                                .position(lokasi)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                                .title(String.valueOf(nama.getText()))
                                .draggable(true)
                                .snippet(String.valueOf(kebutuhan.getText())
                                );

                        Marker marker = mMap.addMarker(titik_taman);

                        data_marker.put(marker.getId(), true);

                    }
                });

            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng pens = new LatLng(-7.2767636, 112.7927149);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(pens));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
        mMap.setOnMarkerDragListener(this);
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
    }

    @Override
    public void onMarkerDrag(Marker marker) {
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        final ProgressDialog loading = new ProgressDialog(MapsActivity.this);
        loading.setMessage("Please wait..");
        loading.show();

        double latitude = marker.getPosition().latitude;
        double longitude = marker.getPosition().longitude;

        if(data_marker.get(marker.getId())){
            ApiService.service_post.postTujuan(marker.getTitle(), marker.getSnippet(), latitude, longitude).enqueue(new Callback<DefaultResponseModel>() {
                @Override
                public void onResponse(Call<DefaultResponseModel> call, Response<DefaultResponseModel> response) {
                    Toast.makeText(MapsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                }

                @Override
                public void onFailure(Call<DefaultResponseModel> call, Throwable t) {

                }
            });
        }else{
            ApiService.service_post.postSumber(marker.getTitle(), marker.getSnippet(), latitude, longitude).enqueue(new Callback<DefaultResponseModel>() {
                @Override
                public void onResponse(Call<DefaultResponseModel> call, Response<DefaultResponseModel> response) {
                    Toast.makeText(MapsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                }

                @Override
                public void onFailure(Call<DefaultResponseModel> call, Throwable t) {

                }
            });
        }



    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        return false;
    }

    private void nextRute(final int k){
        final LatLng lokasi_gudang = new LatLng(data_rute.getDetail().get(k).getLat_gudang(), data_rute.getDetail().get(k).getLng_gudang());
        final LatLng lokasi_taman = new LatLng(data_rute.getDetail().get(k).getLat_taman(), data_rute.getDetail().get(k).getLng_taman());
        GoogleDirection.withServerKey("AIzaSyDc2sbxL_YfPujGZ-hTWU3dkPv8XqWACFs")
                .from(lokasi_gudang)
                .to(lokasi_taman)
                .avoid(AvoidType.FERRIES)
                .avoid(AvoidType.HIGHWAYS)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
//                                Snackbar.make(done, "Success with status : " + direction.getStatus(), Snackbar.LENGTH_SHORT).show();
                        if (direction.isOK()) {
                            mMap.addMarker(new MarkerOptions().position(lokasi_gudang).icon(BitmapDescriptorFactory.fromResource(R.drawable.pabrik)));
                            mMap.addMarker(new MarkerOptions().position(lokasi_taman).icon(BitmapDescriptorFactory.fromResource(R.drawable.taman)));
                            Toast.makeText(MapsActivity.this, Integer.toString(k), Toast.LENGTH_SHORT).show();
                            ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();
                            rute  = mMap.addPolyline(DirectionConverter.createPolyline(MapsActivity.this, directionPositionList, 5, Color.BLUE));

                            done.setText("Next Route");



                        }

                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {
                        // Do something
                    }
                });
    }
}
