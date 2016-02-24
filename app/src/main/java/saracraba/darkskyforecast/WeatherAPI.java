package saracraba.darkskyforecast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by saracraba on 2/22/16.
 */
public class WeatherAPI {

    private static final String KEY= "7486ee35981e6d909ef1b36ea31a17ec";
    private static final String FORECAST_URL="https://api.forecast.io/forecast/";

    static private JSONObject DownloadForecast(Double latitude, Double longitude) {
        //Create the HTTP address string
        final String forecastDownload= FORECAST_URL+KEY+'/'+latitude+','+longitude;

        String jsonObjectString= null;

        //Download Json
        try {
            jsonObjectString = new JSONDownload().execute(forecastDownload).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(jsonObjectString!= null) {
            return JsonParser.getJsonMainObject(jsonObjectString);
        }

        return null;
    }

    /**
     * Download, parse and return the current Forecast object for the location (latitude, longitude)
     * @param coordinates coordinates of the location
     * @return current Forecast object
     */
    static Forecast getCurrentForecast(Coordinates coordinates){
        JSONObject jsonMainObject= DownloadForecast(coordinates.latitude, coordinates.longitude);

        if(jsonMainObject== null){
            return null;
        }

        return Forecast.initialize(JsonParser.getJsonCurrentlyObject(jsonMainObject));
    }

    /**
     * Download, parse and return the day of the week Forecast object for the location (latitude, longitude)
     * @param coordinates coordinates of the location
     * @param day day of the week [0= Today, 1= tomorrow,...]
     * @return current Forecast object
     */
    static Forecast getDayForecast(Coordinates coordinates, int day){
        JSONObject jsonMainObject= DownloadForecast(coordinates.latitude, coordinates.longitude);

        if(jsonMainObject== null){
            return null;
        }

        return Forecast.initialize(JsonParser.getJsonWeekObject(jsonMainObject, day));
    }

    /**
     * Download, parse and return a Forecast object array for the location (latitude, longitude) where
     * Forecast[0] = current Forecast
     * Forecast[1] = today Forecast
     * Forecast[2] = tomorrow Forecast
     * ...
     * @param coordinates coordinates of the location
     * @return current Forecast object
     */
    static Forecast [] getAllForecast(Coordinates coordinates){
        JSONObject jsonMainObject= DownloadForecast(coordinates.latitude, coordinates.longitude);

        if(jsonMainObject== null){
            return null;
        }

        int forecastArraySize=JsonParser.DAY_AT_WEEK+1;
        Forecast[] forecastArray= new Forecast[forecastArraySize];

        //current forecast
        forecastArray[0]=  Forecast.initialize(JsonParser.getJsonCurrentlyObject(jsonMainObject));

        //week forecast
        JSONObject[] jsonObjectWeekArray= JsonParser.getJsonWeekOjectArray(jsonMainObject);
        for(int i=1; i<forecastArraySize; i++) {
            forecastArray[i] = Forecast.initialize(jsonObjectWeekArray[i-1]);
        }

        return forecastArray;
    }

    /**
     * Download the coordinates from a given city name
     * @param cityName name of the city to search for the coordinates
     * @return coordinates of the city
     */
     static Coordinates coordinatesFromCityName(String cityName){
        String cityFormatted= cityName.replaceAll(" ", "%20");
        String coordinatesDownload="http://maps.google.com/maps/api/geocode/json?address="+cityFormatted+"&sensor=false";
        Coordinates coordinates= null;

        String jsonObjectString= null;
        //Download Json
        try {
            jsonObjectString = new JSONDownload().execute(coordinatesDownload).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(jsonObjectString!= null) {
            try
            {
                //Parse Json
                JSONObject jsonGeolocalObject = new JSONObject(jsonObjectString);
                JSONArray jsonResultArray = jsonGeolocalObject.getJSONArray("results");
                JSONObject jsonInfoObject = jsonResultArray.getJSONObject(0);
                JSONObject jsonGeometryObject = jsonInfoObject.getJSONObject("geometry");
                JSONObject jsonCoordinates = jsonGeometryObject.getJSONObject("location");

                coordinates= new Coordinates(jsonCoordinates.getDouble("lat"), jsonCoordinates.getDouble("lng"));

            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return coordinates;
    }

    static final class Coordinates{
        Double latitude;
        Double longitude;

        private Coordinates(Double latitude, Double longitude){
            this.latitude= latitude;
            this.longitude =longitude;
        }

        static Coordinates initCoordinates(Double latitude, Double longitude){
            return new Coordinates(latitude, longitude);
        }
    }
}
