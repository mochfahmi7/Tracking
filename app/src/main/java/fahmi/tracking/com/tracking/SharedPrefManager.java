package fahmi.tracking.com.tracking;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    public static final String SP_MAHASISWA_APP = "spMahasiswaApp";

    public static final String SP_NAMA = "spNama";
    public static final String SP_EMAIL = "spEmail";
    public static final String SP_PESAN = "spPesan";
    public static final String SP_NO1 = "spNo1";
    public static final String SP_NO2 = "spNo2";
    public static final String SP_NO3 = "spNo3";
    public static final String SP_NO4 = "spNo4";
    public static final String SP_NO5 = "spNo5";
    public static final String SP_NO6 = "spNo6";
    public static final String SP_NO7 = "spNo7";



    public static final String SP_SUDAH_LOGIN = "spSudahLogin";
    public static final String SP_SUDAH_DATA = "spSudahData";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharedPrefManager(Context context){
        sp = context.getSharedPreferences(SP_MAHASISWA_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public String getSPNama(){
        return sp.getString(SP_NAMA, "");
    }

    public String getSPEmail(){
        return sp.getString(SP_EMAIL, "");
    }

    public String getSPPesan(){
        return sp.getString(SP_PESAN, "");
    }

    public String getSpNo1(){
        return sp.getString(SP_NO1, "");
    }

    public String getSpNo2(){
        return sp.getString(SP_NO2, "");
    }

    public String getSpNo3(){
        return sp.getString(SP_NO3, "");
    }

    public String getSpNo4(){
        return sp.getString(SP_NO4, "");
    }

    public String getSpNo5(){
        return sp.getString(SP_NO5, "");
    }

    public String getSpNo6(){
        return sp.getString(SP_NO6, "");
    }

    public String getSpNo7(){
        return sp.getString(SP_NO7, "");
    }

    public Boolean getSPSudahLogin(){
        return sp.getBoolean(SP_SUDAH_LOGIN, false);
    }

    public Boolean getSPSudahData(){
        return sp.getBoolean(SP_SUDAH_DATA, false);
    }
}
