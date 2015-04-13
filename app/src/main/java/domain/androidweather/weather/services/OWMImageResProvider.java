package domain.androidweather.weather.services;


import android.content.res.Resources;
import android.util.Log;

import java.util.Calendar;

import domain.androidweather.BuildConfig;
import domain.androidweather.weather.models.WeatherCondition;
import domain.androidweather.weather.services.IWeatherResourceProvider;

public class OWMImageResProvider implements IWeatherResourceProvider<WeatherCondition, Integer> {

    private Resources resources;

    public OWMImageResProvider(Resources resources) {
        this.resources = resources;
    }

    private boolean isDaylight() {
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        Log.i("WeatherCode", hourOfDay + "");
        return hourOfDay <= 19 && hourOfDay >= 7;
    }

    private int getResourceId(int weatherCode) {
        String code = weatherCode + "";

        int result = resources.getIdentifier("owm_" + code, "drawable", BuildConfig.APPLICATION_ID);

        if(result == 0) {
            code = String.valueOf(weatherCode).charAt(0) + "00";
            result = resources.getIdentifier("owm_" + code, "drawable", BuildConfig.APPLICATION_ID);
        }
        if(!isDaylight()) {
            int timeRes = resources.getIdentifier("owm_" + code+"n", "drawable", BuildConfig.APPLICATION_ID);
            result = (timeRes != 0) ? timeRes : result;
        }

        return result != 0 ? result : resources.getIdentifier("na", "drawable", BuildConfig.APPLICATION_ID);
    }

    @Override
    public Integer getResource(WeatherCondition code) {
        return getResourceId(code.id);
    }
}
