package saracraba.darkskyforecast;

import org.json.JSONObject;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sara Craba
 *
 * Forecast class contains all fields af a single forecast
 */
public class Forecast {
    private final Long Timestamp;
    private final Time forecastTime;
    private final String Summary;
    private final Double NearestStormDistance;
    private final Double NearestStormBearing;
    private final Double PrecipIntensity;
    private final Double PrecipProbability;
    private final Double TemperatureMin;
    private final Double TemperatureMax;
    private final Double Temperature;
    private final Double ApparentTemperature;
    private final Double DewPoint;
    private final Double Humidity;
    private final Double WindSpeed;
    private final Double WindBearing;
    private final Double Visibility;
    private final Double CloudCover;
    private final Double Pressure;
    private final Double Ozone;

    Forecast(JSONObject jsonObject)
    {
        Timestamp= JsonParser.getTime(jsonObject);
        forecastTime= new Time(Timestamp);

        Summary= JsonParser.getSummary(jsonObject);
        NearestStormDistance= JsonParser.getNearestStormDistance(jsonObject);
        NearestStormBearing= JsonParser.getNearestStormBearing(jsonObject);
        PrecipIntensity = JsonParser.getPrecipIntensity(jsonObject);
        PrecipProbability = JsonParser.getPrecipProbability(jsonObject);
        Temperature = JsonParser.getTemperature(jsonObject);
        ApparentTemperature = JsonParser.getApparentTemperature(jsonObject);
        DewPoint = JsonParser.getDewPoint(jsonObject);
        Humidity = JsonParser.getHumidity(jsonObject);
        WindSpeed = JsonParser.getWindSpeed(jsonObject);
        WindBearing = JsonParser.getWindBearing(jsonObject);
        Visibility = JsonParser.getVisibility(jsonObject);
        CloudCover = JsonParser.getCloudCover(jsonObject);
        Pressure = JsonParser.getPressure(jsonObject);
        Ozone = JsonParser.getOzone(jsonObject);
        TemperatureMin = JsonParser.getTemperatureMin(jsonObject);
        TemperatureMax = JsonParser.getTemperatureMax(jsonObject);
    }

    /**
     *
     * @return a formatted text with all not-null forecast values except time and summary
     */
    String printForecast()
    {
        StringBuilder forecastStringBuilder= new StringBuilder();

        //Nearest Storm Distance
        if(NearestStormDistance!=null)
            forecastStringBuilder.append("Nearest Storm Distance: " + NearestStormDistance.toString() + " miles\n");

        //Nearest Storm Bearing
        if(NearestStormBearing!=null)
            forecastStringBuilder.append("Nearest Storm Bearing: " + NearestStormBearing.toString() + " degrees\n");

        //Precipitation Intensity
        if(PrecipIntensity!=null)
            forecastStringBuilder.append("Precipitation Intensity: " + PrecipIntensity.toString() + " in./hr\n");

        //Precipitation Probability
        if(PrecipProbability!=null)
            forecastStringBuilder.append("PrecipitationProbability: " + PrecipProbability.toString() + "%\n");

        //Temperature
        if(Temperature!=null)
            forecastStringBuilder.append("Temperature: " + Temperature.toString() + " °F\n");

        //Apparent Temperature
        if(ApparentTemperature!=null)
            forecastStringBuilder.append("Apparent Temperature: " + ApparentTemperature.toString() + " °F\n");

        //Dew Point
        if(DewPoint!=null)
            forecastStringBuilder.append("Dew Point: " + DewPoint.toString() + " °F\n");

        //Humidity
        if(Humidity!=null)
        {
            Double humidityPerc = Humidity * 100d;
            forecastStringBuilder.append("Relative Humidity: " + humidityPerc.toString() + "%\n");
        }

        //Wind Speed
        if(WindSpeed!=null)
            forecastStringBuilder.append("Wind Speed: " + WindSpeed.toString() + "  mph \n");

        //Wind Bearing
        if(WindBearing!=null)
            forecastStringBuilder.append("Wind Bearing: " + WindBearing.toString() + " degrees\n");

        //Visibility
        if(Visibility!=null)
            forecastStringBuilder.append("Visibility: " + Visibility.toString() + " miles \n");

        //Cloud Cover
        if(CloudCover!=null) {
            Double cloudCoverPer = CloudCover * 100d;
            forecastStringBuilder.append("Cloud Cover: " + cloudCoverPer.toString() + "%\n");
        }

        //Pressure
        if(Pressure!=null)
            forecastStringBuilder.append("Pressure: " + Pressure.toString() + " millibars\n");

        //Ozone
        if(Ozone!=null)
            forecastStringBuilder.append("Ozone: " + Ozone.toString() + " Dobson\n");

        //Temperature Min
        if(TemperatureMin!=null)
            forecastStringBuilder.append("Temperature Minimum: " + TemperatureMin.toString() + " °F\n");

        //TemperatureMax
        if(TemperatureMax!=null)
            forecastStringBuilder.append("Temperature Maximum: " + TemperatureMax.toString() + " °F\n");

        return forecastStringBuilder.toString();
    }

    /**
     *
     * @return a formatted text of the forecast date --> MONTH/DAY/YEAR
     */
    String printDate()
    {
        if(forecastTime==null) {
            return "";
        }

        String month;
        switch (forecastTime.month)
        {
            case 0: month="JAN";
                break ;
            case 1: month="FEB";
                break;
            case 2: month="MAR";
                break;
            case 3: month="APR";
                break;
            case 4: month="MAY";
                break;
            case 5: month="JUN";
                break;
            case 6: month="JUL";
                break;
            case 7: month="AUG";
                break;
            case 8: month="SEP";
                break;
            case 9: month="OCT";
                break;
            case 10:month="NOV";
                break;
            default:month="DEC";
        }

        return month+ "/" +forecastTime.day +"/" + forecastTime.year;

    }

    /**
     *
     * @return a formatted text of the forecast hour --> hh:mm AM/PM
     */
    String printHour()
    {
        if(forecastTime==null) {
            return "";
        }

        return forecastTime.hour+ ":" +forecastTime.minute+ ((forecastTime.am_pm==0)? " AM": " PM");
    }

    /**
     *
     * @return a formatted text of the forecast summary
     */
    String printSummary()
    {
        //Summary
        return  Summary;
    }

    /**
     * Convert Unix timestamp
     */
   private class Time{
        int hour; //in AM/PM
        int minute;
        int am_pm;
        int day;
        int month;
        int year;

        Time(long timestamp) {

            Long javaTimestamp = timestamp * 1000L;
            Date date = new Date(javaTimestamp);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            hour= calendar.get(Calendar.HOUR);
            minute= calendar.get(Calendar.MINUTE);
            am_pm=calendar.get(Calendar.AM_PM);
            day= calendar.get(Calendar.DATE);
            month= calendar.get(Calendar.MONTH);
            year=calendar.get(Calendar.YEAR);
        }
    }
}
