package saracraba.darkskyforecast;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {

    private static final String FORECAST_OBJ = "forecastObjForIntent";
    private static final int DAY_FORECAST=6;
    private static final String KEY= "7486ee35981e6d909ef1b36ea31a17ec";
    private static final String FORECAST_URL="https://api.forecast.io/forecast/";

    private static EditText latitudeTextView;
    private static EditText longitudeTextView;
    private static EditText cityTextView;
    private static TextView errorMessage;
    private static LinearLayout forecastListView;
    private Button weekForecastButton;

    private static JsonParser forecastObject = null;
    private String forecastJsonString = null;
    private Double latitude = null;
    private Double longitude = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latitudeTextView = (EditText) findViewById(R.id.latitude);
        latitudeTextView.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);

        longitudeTextView = (EditText) findViewById(R.id.longitude);
        longitudeTextView.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);

        cityTextView= (EditText) findViewById(R.id.city);
        errorMessage=(TextView) findViewById(R.id.error_message);
        forecastListView= (LinearLayout) findViewById(R.id.forecast_list_view);
        weekForecastButton= (Button) findViewById(R.id.week_forecast_button);
    }

    /**
     *
     * Clean all UI fields when Clean Button is pressed
     */
    public void onCleanBtnClick(View v)
    {
        CleanFields();
    }

    /**
     *
     * Get coordinates or city from UI when CheckWeather Button is pressed
     */
    public void onUpdateCoordinatesBtnClick(View v)
    {
        //Hide soft keyboard
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        String latitudeStr= latitudeTextView.getText().toString();
        String longitudeStr= longitudeTextView.getText().toString();
        String cityStr= cityTextView.getText().toString();

        if(latitudeStr.isEmpty() || longitudeStr.isEmpty())
        {
            if(cityStr.isEmpty())
            {
                errorMessage.setText("Error: City or Coordinates are required.");
                return;
            }

            //City name found, download coordinates
            DownloadCoordinates();
        }
        else {
            // Coordinates
            latitude= Double.parseDouble(latitudeStr);
            longitude= Double.parseDouble(longitudeStr);
            cityTextView.setText("");

            DownloadForecast();
        }
    }

    /**
     * When a city name is inserted, download and set the coordinates for the given city
     */
    private void DownloadCoordinates()
    {
        //Create the HTTP address string
        String cityFormatted= cityTextView.getText().toString().replaceAll(" ", "%20");
        String coordinatesDownload="http://maps.google.com/maps/api/geocode/json?address="+cityFormatted+"&sensor=false";

        //Download Json
        new JSONDownload(this, new JSONResult()
        {
            @Override
            public void onDownloadCompleted(String result)
            {
                try
                {
                    //Parse Json
                    JSONObject jsonGeolocalObject = new JSONObject(result);
                    JSONArray jsonResultArray = jsonGeolocalObject.getJSONArray("results");
                    JSONObject jsonInfoObject = jsonResultArray.getJSONObject(0);
                    JSONObject jsonGeometryObject = jsonInfoObject.getJSONObject("geometry");
                    JSONObject jsonCoordinates = jsonGeometryObject.getJSONObject("location");

                    latitude = jsonCoordinates.getDouble("lat");
                    latitudeTextView.setHint(Double.toString(latitude));
                    longitude = jsonCoordinates.getDouble("lng");
                    longitudeTextView.setHint(Double.toString((longitude)));

                    //Download forecast with coordinates
                    DownloadForecast();
                }
                catch (JSONException e) {
                    CleanFields();
                    errorMessage.setText("Error: City not found.");
                }
            }
        }).execute(coordinatesDownload);
    }

    /**
     * Download forecast
     */
    private void DownloadForecast()
    {
        if(latitude==null || longitude== null)
        {
            errorMessage.setText("Error: Invalid Coordinates.");
            return;
        }

        //Create the HTTP address string
        String forecastDownload= FORECAST_URL+KEY+'/'+latitude+','+longitude;

        //Download Json
        new JSONDownload(this, new JSONResult()
        {
            @Override
            public void onDownloadCompleted(String result)
            {
                try
                {
                    //Parse Json
                    forecastObject = new JsonParser(result);
                    forecastJsonString= result;

                    ShowCurrentForecast();
                }
                catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    errorMessage.setText("Error: The given location is invalid");
                }
            }
        }).execute(forecastDownload);
    }

    /**
     * Show current forecast in the UI
     */
    private void ShowCurrentForecast()
    {
        if (forecastObject == null)
        {
            errorMessage.setText("Error: Problem parsing coordinates");
            return;
        }

        //Clean forecast view
        if(forecastListView.getChildCount() > 0)
            forecastListView.removeAllViews();

        //Current forecast
        Forecast currentForecast = new Forecast.initialize(forecastObject.getCurrentForecastObject(), forecastObject);
        ForecastView currentForecastView = new ForecastView(this, null);
        currentForecastView.setForecastView("Current: "+ currentForecast.printHour(), currentForecast.printSummary(), currentForecast.printForecast());
        forecastListView.addView(currentForecastView);

        //Today forecast
        Forecast dayForecast = new Forecast(forecastObject.getWeekForecastObject()[0]);
        ForecastView dayForecastView = new ForecastView(this, null);
        dayForecastView.setForecastView("Today: " + dayForecast.printDate(), dayForecast.printSummary(), dayForecast.printForecast());
        forecastListView.addView(dayForecastView);

        //Show week forecast button
        weekForecastButton.setVisibility(Button.VISIBLE);

        //clean error message
        errorMessage.setText("");
    }

    /**
     * Clean UI fields
     */
    private void CleanFields()
    {
        latitude=null;
        longitude= null;
        latitudeTextView.setText("");
        longitudeTextView.setText("");
        latitudeTextView.setHint("");
        longitudeTextView.setHint("");
        cityTextView.setText("");

        //clean forecast view
        if(forecastListView.getChildCount() > 0)
            forecastListView.removeAllViews();

        //Hide week forecast button
        weekForecastButton.setVisibility(Button.INVISIBLE);
    }

    /**
     *
     * Start new activity to show the week forecast
     */
    public void onWeekForecastBtnClick(View v) {
        Intent intent = new Intent(this, WeekForecastActivity.class);
        intent.putExtra(FORECAST_OBJ, forecastJsonString);
        startActivity(intent);
    }
}
