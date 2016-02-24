package saracraba.darkskyforecast;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import workStation.clock.ClockActivity;

public class MainActivity extends Activity {

    private static EditText latitudeTextView;
    private static EditText longitudeTextView;
    private static EditText cityTextView;
    private static TextView errorMessage;
    private static ListView forecastListView;
    private ListViewAdapter adapter=null;
    private WeatherAPI.Coordinates coordinates= null;

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
        weekForecastButton= (Button) findViewById(R.id.week_forecast_button);

        final Button clockButton = (Button) findViewById(R.id.clock_button);
        clockButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(MainActivity.this, ClockActivity.class );
                startActivity(i);

            }
        });
        forecastListView= (ListView) findViewById(R.id.forecast_list_view);
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
            coordinates = WeatherAPI.coordinatesFromCityName(cityStr);
        }
        else {
            // Coordinates found
            coordinates = WeatherAPI.Coordinates.initCoordinates(Double.parseDouble(latitudeStr), Double.parseDouble(longitudeStr));
            cityTextView.setText("");
        }

        ShowCurrentForecast();
    }

    /**
     * Show current forecast in the UI
     */
    private void ShowCurrentForecast()
    {
        if (coordinates == null)
        {
            errorMessage.setText("Error: Problem parsing coordinates");
            return;
        }

        Forecast[] completeForecast= WeatherAPI.getAllForecast(coordinates);

        adapter = new ListViewAdapter(this, R.layout.forecast_view,
                new ArrayList<Forecast>(Arrays.asList(completeForecast)));
        View header = (View)getLayoutInflater().inflate(R.layout.forecast_view, null);

        forecastListView.setAdapter(adapter);

        //clean error message
        errorMessage.setText("");
    }

    /**
     * Clean UI fields
     */
    private void CleanFields()
    {
        coordinates= null;
        latitudeTextView.setText("");
        longitudeTextView.setText("");
        latitudeTextView.setHint("");
        longitudeTextView.setHint("");
        cityTextView.setText("");

        //clean forecast view
        if(adapter!= null) {
            adapter.clear();
        }
    }
}
