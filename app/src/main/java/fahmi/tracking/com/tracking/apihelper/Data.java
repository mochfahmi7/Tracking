package fahmi.tracking.com.tracking.apihelper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Data {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("no1")
    @Expose
    private String no1;
    @SerializedName("no2")
    @Expose
    private String no2;
    @SerializedName("no3")
    @Expose
    private String no3;
    @SerializedName("no4")
    @Expose
    private String no4;
    @SerializedName("no5")
    @Expose
    private String no5;
    @SerializedName("no6")
    @Expose
    private String no6;
    @SerializedName("no7")
    @Expose
    private String no7;
    @SerializedName("pesan")
    @Expose
    private String pesan;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNo1() {
        return no1;
    }

    public void setNo1(String no1) {
        this.no1 = no1;
    }

    public String getNo2() {
        return no2;
    }

    public void setNo2(String no2) {
        this.no2 = no2;
    }

    public String getNo3() {
        return no3;
    }

    public void setNo3(String no3) {
        this.no3 = no3;
    }

    public String getNo4() {
        return no4;
    }

    public void setNo4(String no4) {
        this.no4 = no4;
    }

    public String getNo5() {
        return no5;
    }

    public void setNo5(String no5) {
        this.no5 = no5;
    }

    public String getNo6() {
        return no6;
    }

    public void setNo6(String no6) {
        this.no6 = no6;
    }

    public String getNo7() {
        return no7;
    }

    public void setNo7(String no7) {
        this.no7 = no7;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

}