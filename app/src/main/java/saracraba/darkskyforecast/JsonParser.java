package saracraba.darkskyforecast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sara Craba
 *
 * Parse a Json object from string to java Json object
 */
public class JsonParser
{
    private JSONObject jsonMainObject;
    protected JSONObject jsonCurrentlyObject;
    protected JSONObject [] jsonWeekObject;

    JsonParser (String jsonRawString) throws IllegalArgumentException
    {
        if (jsonRawString==null || jsonRawString.isEmpty()) {
            throw new IllegalArgumentException("JSON String must be not null and not empty");
        }

        ParseJsonString(jsonRawString);
    }

    /**
     * Parse the Json string and saves the current json object and the daily json array into java json objects
     *
     * @param jsonRawString the downloaded raw Json string
     */
    private void ParseJsonString(String jsonRawString)
    {
        try
        {
            //getting main json object
            jsonMainObject = new JSONObject(jsonRawString);

            //getting current json object
            jsonCurrentlyObject = jsonMainObject.getJSONObject("currently");

            //getting data of the daily json object
            JSONObject jsonDailyObject= jsonMainObject.getJSONObject("daily");
            JSONArray jsonDailyDataObject= jsonDailyObject.getJSONArray("data");

            jsonWeekObject = new JSONObject[MainActivity.DAY_FORECAST];

            for (int i=0; i< MainActivity.DAY_FORECAST; i++)
            {
                jsonWeekObject[i]=jsonDailyDataObject.getJSONObject(i);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    /**
     *  The UNIX time (that is, seconds since midnight GMT on 1 Jan 1970) at which this data point occurs
     *
     * @return timestamp of the forecast
     */

    static Long getTime(JSONObject jsonObject)
    {
        try {
            //parsing time
            return  jsonObject.getLong("time");

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @return  A human-readable text summary of this data point.
     */
     static String getSummary(JSONObject jsonObject)
    {
        try {
            //parsing summary
            return jsonObject.getString("summary");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @return  A machine-readable text summary of this data point, suitable for selecting an icon for display. If defined, this property will have one of the following values: clear-day, clear-night, rain, snow, sleet, wind, fog, cloudy, partly-cloudy-day, or partly-cloudy-night
     */
    static String getIcon(JSONObject jsonObject)
    {
        try {
            //parsing icon
            return jsonObject.getString("icon");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @return  A numerical value representing the distance to the nearest storm in miles. (This value is very approximate and should not be used in scenarios requiring accurate results. In particular, a storm distance of zero doesn’t necessarily refer to a storm at the requested location, but rather a storm in the vicinity of that location.)
     */
    static Double getNearestStormDistance(JSONObject jsonObject)
    {
        try {
            //parsing nearestStormDistance
            return jsonObject.getDouble("nearestStormDistance");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @return A numerical value representing the direction of the nearest storm in degrees, with true north at 0° and progressing clockwise. (If nearestStormDistance is zero, then this value will not be defined. The caveats that apply to nearestStormDistance also apply to this value.)
     */
    static Double getNearestStormBearing(JSONObject jsonObject)
    {
        try {
            //parsing nearestStormBearing
            return jsonObject.getDouble("nearestStormBearing");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * @return A numerical value representing the average expected intensity (in inches of liquid water per hour) of precipitation occurring at the given time conditional on probability (that is, assuming any precipitation occurs at all). A very rough guide is that a value of 0 in./hr. corresponds to no precipitation, 0.002 in./hr. corresponds to very light precipitation, 0.017 in./hr. corresponds to light precipitation, 0.1 in./hr. corresponds to moderate precipitation, and 0.4 in./hr. corresponds to heavy precipitation.
     */
    static Double getPrecipIntensity(JSONObject jsonObject)
    {
        try {
            //parsing precipIntensity
            return jsonObject.getDouble("precipIntensity");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @return A numerical value between 0 and 1 (inclusive) representing the probability of precipitation occuring at the given time.
     */
    static Double getPrecipProbability(JSONObject jsonObject)
    {
        try {
            //parsing precipProbability
            return jsonObject.getDouble("precipProbability");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @return  A numerical value representing the temperature at the given time in degrees Fahrenheit.
     */
    static Double getTemperature(JSONObject jsonObject)
    {
        try {
            //parsing temperature
            return jsonObject.getDouble("temperature");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @return A numerical value representing the apparent (or “feels like”) temperature at the given time in degrees Fahrenheit.
     */
    static Double getApparentTemperature(JSONObject jsonObject)
    {
        try {
            //parsing apparentTemperature
            return jsonObject.getDouble("apparentTemperature");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @return A numerical value representing the dew point at the given time in degrees Fahrenheit.
     */
    static Double getDewPoint(JSONObject jsonObject)
    {
        try {
            //parsing dewPoint
            return jsonObject.getDouble("dewPoint");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * @return A numerical value between 0 and 1 (inclusive) representing the relative humidity.
     */
    static Double getHumidity(JSONObject jsonObject)
    {
        try {
            //parsing humidity
            return jsonObject.getDouble("humidity");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @return A numerical value representing the wind speed in miles per hour.
     */
    static  Double getWindSpeed(JSONObject jsonObject)
    {
        try {
            //parsing windSpeed
            return jsonObject.getDouble("windSpeed");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @return A numerical value representing the direction that the wind is coming from in degrees, with true north at 0° and progressing clockwise
     */
    static Double getWindBearing(JSONObject jsonObject)
    {
        try {
            //parsing windBearing
            return jsonObject.getDouble("windBearing");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @return A numerical value representing the average visibility in miles, capped at 10 miles.
     */
    static Double getVisibility(JSONObject jsonObject)
    {
        try {
            //parsing visibility
            return jsonObject.getDouble("visibility");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * @return A numerical value between 0 and 1 (inclusive) representing the percentage of sky occluded by clouds. A value of 0 corresponds to clear sky, 0.4 to scattered clouds, 0.75 to broken cloud cover, and 1 to completely overcast skies.
     */
    static Double getCloudCover(JSONObject jsonObject)
    {
        try {
            //parsing cloudCover
            return jsonObject.getDouble("cloudCover");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @return A numerical value representing the sea-level air pressure in millibars.
     */
    static Double getPressure(JSONObject jsonObject)
    {
        try {
            //parsing pressure
            return jsonObject.getDouble("pressure");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @return  A numerical value representing the columnar density of total atmospheric ozone at the given time in Dobson units.
     */
    static Double getOzone(JSONObject jsonObject)
    {
        try {
            //parsing ozone
            return jsonObject.getDouble("ozone");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @return numerical values representing the minimum temperature on the given day in degrees Fahrenheit.
     */
    static Double getTemperatureMin(JSONObject jsonObject)
    {
        try {
            //parsing temperature min
            return jsonObject.getDouble("temperatureMin");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * @return numerical values representing the maximum temperature on the given day in degrees Fahrenheit.
     */
    static Double getTemperatureMax(JSONObject jsonObject)
    {
        try {
            //parsing temperature min
            return jsonObject.getDouble("temperatureMax");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
