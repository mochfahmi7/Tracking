package fahmi.tracking.com.tracking;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import fahmi.tracking.com.tracking.apihelper.BaseApiService;
import fahmi.tracking.com.tracking.apihelper.UtilsApi;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataActivity extends AppCompatActivity {

    String dataEmail,dataPesan,dataNo1,dataNo2,dataNo3,dataNo4,dataNo5,dataNo6,dataNo7;
    @BindView(R.id.et_email) EditText etEmail;
    @BindView(R.id.et_no1) EditText etNo1;
    @BindView(R.id.et_no2) EditText etNo2;
    @BindView(R.id.et_no3) EditText etNo3;
    @BindView(R.id.et_no4) EditText etNo4;
    @BindView(R.id.et_no5) EditText etNo5;
    @BindView(R.id.et_no6) EditText etNo6;
    @BindView(R.id.et_no7) EditText etNo7;
    @BindView(R.id.et_pesan) EditText etPesan;
    @BindView(R.id.btn_submit) Button btnSubmit;
    ProgressDialog loading;
    SharedPrefManager sharedPrefManager;

    Context mContext;
    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_data);
        ButterKnife.bind(this);
        mContext = this;
        mApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(this);

        dataEmail=sharedPrefManager.getSPEmail();
        dataPesan=sharedPrefManager.getSPPesan();
        dataNo1=sharedPrefManager.getSpNo1();
        dataNo2=sharedPrefManager.getSpNo2();
        dataNo3=sharedPrefManager.getSpNo3();
        dataNo4=sharedPrefManager.getSpNo4();
        dataNo5=sharedPrefManager.getSpNo5();
        dataNo6=sharedPrefManager.getSpNo6();
        dataNo7=sharedPrefManager.getSpNo7();

        etPesan.setText(dataPesan);
        etEmail.setText(dataEmail);
        etNo1.setText(dataNo1);
        etNo2.setText(dataNo2);
        etNo3.setText(dataNo3);
        etNo4.setText(dataNo4);
        etNo5.setText(dataNo5);
        etNo6.setText(dataNo6);
        etNo7.setText(dataNo7);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                requestInsert();
            }
        });
    }

    private void requestInsert(){
        mApiService.insertRequest(etEmail.getText().toString(), etNo1.getText().toString(),etNo2.getText().toString(),etNo3.getText().toString(),etNo4.getText().toString(),etNo5.getText().toString(),etNo6.getText().toString(),etNo7.getText().toString(),etPesan.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Log.i("debug", "onResponse: BERHASIL");
                            loading.dismiss();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("error").equals("false")){
                                    Toast.makeText(mContext, "BERHASIL INSERT", Toast.LENGTH_SHORT).show();
                                    sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_DATA, true);
                                    startActivity(new Intent(mContext, LoginActivity.class));
                                } else {
                                    String error_message = jsonRESULTS.getString("error_msg");
                                    Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.i("debug", "onResponse: GA BERHASIL");
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
