package fahmi.tracking.com.tracking;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import fahmi.tracking.com.tracking.apihelper.ApiClient;
import fahmi.tracking.com.tracking.apihelper.BaseApiService;
import fahmi.tracking.com.tracking.apihelper.Data;
import fahmi.tracking.com.tracking.apihelper.UtilsApi;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements LocationListener {
    boolean notNull = true;
    String email, nama;
    public static String nomor1,nomor2,nomor3,nomor4,nomor5,nomor6,nomor7,pesan,emailkirim;
    public static String alamat;
    public String[] phoneNumber=new String[8];
    @BindView(R.id.tvResultNama)
    TextView tvResultNama;


    @BindView(R.id.sendSMS)
    Button sendSMS;

    LocationManager locationManager;

    ProgressDialog dialog;
    public static String kondisi="";

    SharedPrefManager sharedPrefManager;
    Context mContext;

    BaseApiService mApiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        mContext = this;
        mApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(this);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            kondisi = b.getString("kondisi");
        }





        tvResultNama.setText(sharedPrefManager.getSPNama());
        email = sharedPrefManager.getSPEmail();
        nama = sharedPrefManager.getSPNama();
        getLocation(alamat);
        getAllData(email);
//        btnLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
//                startActivity(new Intent(MainActivity.this, LoginActivity.class)
//                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
//                finish();
//            }
//        });

//        btnData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getAllData(email);
//                    startActivity(new Intent(mContext, DataActivity.class));
//            }
//        });


        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    new String[]{Manifest.permission.SEND_SMS},
                    101
            );
        }




        sendSMS.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                if (notNull) {
                    getLocation(alamat);
                    getAllData(email);

                    String tesAlamat = alamat;
                    String pesanKondisi= "Kondisi Pasien: "+kondisi;
                    String message = pesanKondisi+"\n\n" + tesAlamat+ "\n\n"+ sharedPrefManager.getSPPesan() ;

                    sendSMS(phoneNumber, message);
                }else {
                    Toast.makeText(MainActivity.this,"Isi Nomor Dulu Yaaa", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.MyDialogTheme);
        builder.setTitle(Html.fromHtml("<font color='#ffffff'>Apakah Anda Yakin Keluar?</font>"));
        builder.setPositiveButton(Html.fromHtml("<font color='#ffffff'>Yes</font>"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNegativeButton(Html.fromHtml("<font color='#ffffff'>No</font>"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog quit = builder.create();
        quit.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.btnLogout){
            sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
            startActivity(new Intent(MainActivity.this, LoginActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }else if(item.getItemId()==R.id.btnData){
            getAllData(email);
            Intent intent = new Intent(MainActivity.this, DataActivity.class);
            MainActivity.this.startActivity(intent);
        }

        return true;
    }


    void getLocation(String alamat) {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        alamat = "Link Lokasi \n"+"http://maps.google.com/maps?q=" + location.getLatitude() + "," + location.getLongitude();
        //locationText.setText("Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude() + "\n Alamat: " + alamat);
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            //locationText.setText(locationText.getText() + "\n" + addresses.get(0).getAddressLine(0));
        } catch (Exception e) {

        }

    }


    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(MainActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    private void sendSMS(String[] phoneNumber, String message) {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);

        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

                for (String s : phoneNumber) {
                    SmsManager sms = SmsManager.getDefault();
                    sms.sendTextMessage(s, null, message, sentPI, deliveredPI);
                }

    }

    private void getAllData(String type) {
        mApiService=ApiClient.getApiClient().create(BaseApiService.class);
        Call< List< Data>> call = mApiService.getData(type);

        call.enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                List<Data> dataAll=response.body();
                emailkirim=dataAll.get(0).getEmail();
                nomor1=dataAll.get(0).getNo1();
                nomor2=dataAll.get(0).getNo2();
                nomor3=dataAll.get(0).getNo3();
                nomor4=dataAll.get(0).getNo4();
                nomor5=dataAll.get(0).getNo5();
                nomor6=dataAll.get(0).getNo6();
                nomor7=dataAll.get(0).getNo7();
                pesan=dataAll.get(0).getPesan();
                nomor7=dataAll.get(0).getNo7();

                phoneNumber = new String[]{nomor1,nomor2,nomor3,nomor4,nomor5,nomor6,nomor7};
                for (String tes : phoneNumber) {
                    notNull= notNull && (tes != null && !tes.equals(""));
                }

                sharedPrefManager.saveSPString(SharedPrefManager.SP_PESAN, pesan);
                sharedPrefManager.saveSPString(SharedPrefManager.SP_EMAIL, email);
                sharedPrefManager.saveSPString(SharedPrefManager.SP_NO1, nomor1);
                sharedPrefManager.saveSPString(SharedPrefManager.SP_NO2, nomor2);
                sharedPrefManager.saveSPString(SharedPrefManager.SP_NO3, nomor3);
                sharedPrefManager.saveSPString(SharedPrefManager.SP_NO4, nomor4);
                sharedPrefManager.saveSPString(SharedPrefManager.SP_NO5, nomor5);
                sharedPrefManager.saveSPString(SharedPrefManager.SP_NO6, nomor6);
                sharedPrefManager.saveSPString(SharedPrefManager.SP_NO7, nomor7);
                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error \n"+t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
