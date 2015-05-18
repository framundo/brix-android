import android.app.Application;
import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.wall.android.service.interceptor.SecuredRequestInterceptor;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class WoloxApplication extends Application {

    private static Context sContext;
    private static RequestInterceptor sSecureRequestInterceptor;

    public static final String STAGE_ENDPOINT = "http://nightowl-stage.herokuapp.com/api/v1";
    public static final String APIARY_ENDPOINT = "http://private-1211b-lightsout1.apiary-mock.com/api/v1";

    static {
        buildRestServices();
    }

    public static void buildRestServices() {
        sSecureRequestInterceptor = new SecuredRequestInterceptor();
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .create();
        RestAdapter apiaryAdapter = new RestAdapter.Builder()
                .setEndpoint(APIARY_ENDPOINT)
                .setConverter(new GsonConverter(gson))
                .setRequestInterceptor(sSecureRequestInterceptor)
                .build();
        RestAdapter stageAdapter = new RestAdapter.Builder()
                .setEndpoint(STAGE_ENDPOINT)
                .setConverter(new GsonConverter(gson))
                .setRequestInterceptor(sSecureRequestInterceptor)
                .build();
    }

    public static class DateDeserializer implements JsonDeserializer<Date> {
        @Override
        public Date deserialize(JsonElement element, Type arg1, JsonDeserializationContext arg2)
                throws JsonParseException {
            String originalDate = element.toString();
            char[] dateChar = originalDate.toCharArray();
            char[] dateCharConverted = Arrays.copyOfRange(dateChar, 1, dateChar.length - 1);
            String date = new String(dateCharConverted);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            try {
                return formatter.parse(date);
            } catch (ParseException e3) {
                formatter = new SimpleDateFormat("yyyy-MM-dd");
                formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                try {
                    originalDate = originalDate.substring(1, originalDate.length() - 2);
                    return formatter.parse(originalDate);
                } catch (ParseException e4) {
                    throw new JsonParseException(e4);
                }
            }
        }
    }

    public static Context context() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }
}
