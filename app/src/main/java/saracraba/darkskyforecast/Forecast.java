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


    public static class Builder{
        //Required parameters
        private final Long Timestamp;
        private final String Summary;

        //Optional parameters
        private Double NearestStormDistance=null;
        private Double NearestStormBearing=null;
        private Double PrecipIntensity=null;
        private Double PrecipProbability=null;
        private Double TemperatureMin=null;
        private Double TemperatureMax=null;
        private Double Temperature=null;
        private Double ApparentTemperature=null;
        private Double DewPoint=null;
        private Double Humidity=null;
        private Double WindSpeed=null;
        private Double WindBearing=null;
        private Double Visibility=null;
        private Double CloudCover=null;
        private Double Pressure=null;
        private Double Ozone=null;

        public Builder(Long Timestamp, String Summary){
            this.Timestamp=Timestamp;
            this.Summary=Summary;
        }

        public Builder NearestStormDistance(Double value){
            NearestStormDistance= value; return this;
        }
        public Builder NearestStormBearing(Double value){
            NearestStormBearing= value; return this;
        }
        public Builder PrecipIntensity(Double value){
            PrecipIntensity= value; return this;
        }
        public Builder PrecipProbability(Double value){
            PrecipProbability= value; return this;
        }
        public Builder TemperatureMin(Double value){
            TemperatureMin= value; return this;
        }
        public Builder TemperatureMax(Double value){
            TemperatureMax= value; return this;
        }
        public Builder Temperature(Double value){
            Temperature= value; return this;
        }
        public Builder ApparentTemperature(Double value){
            ApparentTemperature= value; return this;
        }
        public Builder DewPoint(Double value){
            DewPoint= value; return this;
        }
        public Builder Humidity(Double value){
            Humidity= value; return this;
        }
        public Builder WindSpeed(Double value){
            WindSpeed= value; return this;
        }
        public Builder WindBearing(Double value){
            WindBearing= value; return this;
        }
        public Builder Visibility(Double value){
            Visibility= value; return this;
        }
        public Builder CloudCover(Double value){
            CloudCover= value; return this;
        }
        public Builder Pressure(Double value){
            Pressure= value; return this;
        }
        public Builder Ozone(Double value){
            Ozone= value; return this;
        }

        public Forecast build(){
            return new Forecast(this);
        }

    }

    private Forecast (Builder builder){
        Timestamp= builder.Timestamp;
        forecastTime= new Time(Timestamp);
        Summary = builder.Summary;
        NearestStormDistance=builder.NearestStormDistance;
        NearestStormBearing=builder.NearestStormBearing;
        PrecipIntensity= builder.PrecipIntensity;
        PrecipProbability= builder.PrecipProbability;
        TemperatureMin= builder.TemperatureMin;
        TemperatureMax= builder.TemperatureMax;
        Temperature= builder.Temperature;
        ApparentTemperature= builder.ApparentTemperature;
        DewPoint= builder.DewPoint;
        Humidity= builder.Humidity;
        WindSpeed= builder.WindSpeed;
        WindBearing= builder.WindBearing;
        Visibility= builder.Visibility;
        CloudCover= builder.CloudCover;
        Pressure= builder.Pressure;
        Ozone= builder.Ozone;
    }

    /**
     * Build a Forecast object from a JsonObject using a JsonParser
     */
     static Forecast initialize(JSONObject jsonObject, JsonParser jsonParser)
     {
        return new Forecast.Builder(jsonParser.getTime(jsonObject),  jsonParser.getSummary(jsonObject))
                .NearestStormDistance(jsonParser.getNearestStormDistance(jsonObject))
                .NearestStormBearing(jsonParser.getNearestStormBearing(jsonObject))
                .PrecipIntensity( jsonParser.getPrecipIntensity(jsonObject))
                .PrecipProbability (jsonParser.getPrecipProbability(jsonObject))
                .Temperature ( jsonParser.getTemperature(jsonObject))
                .ApparentTemperature (jsonParser.getApparentTemperature(jsonObject))
                .DewPoint (jsonParser.getDewPoint(jsonObject))
                .Humidity (jsonParser.getHumidity(jsonObject))
                .WindSpeed(jsonParser.getWindSpeed(jsonObject))
                .WindBearing (jsonParser.getWindBearing(jsonObject))
                .Visibility (jsonParser.getVisibility(jsonObject))
                .CloudCover (jsonParser.getCloudCover(jsonObject))
                .Pressure (jsonParser.getPressure(jsonObject))
                .Ozone (jsonParser.getOzone(jsonObject))
                .TemperatureMin (jsonParser.getTemperatureMin(jsonObject))
                .TemperatureMax (jsonParser.getTemperatureMax(jsonObject))
                .build();
    }

    /**
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
