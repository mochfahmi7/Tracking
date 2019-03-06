package fahmi.tracking.com.tracking.apihelper;

public class UtilsApi {
    public static final String BASE_URL_API = "http://am-360.com/fik/";

    // Mendeklarasikan Interface BaseApiService
    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
