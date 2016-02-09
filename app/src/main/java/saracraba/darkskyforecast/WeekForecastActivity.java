package saracraba.darkskyforecast;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

/**
 * Second Activity show the list of the forecasts of the next 5 days
 */
public class WeekForecastActivity extends Activity
{
    private static LinearLayout forecastListView;
    private JsonParser forecastObject = null;
    private  String forecastJsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_forecast);

        forecastListView= (LinearLayout) findViewById(R.id.forecast_list_view);
        forecastJsonString= getIntent().getStringExtra(MainActivity.FORECAST_OBJ);

        try
        {
            forecastObject = new JsonParser(forecastJsonString);
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onStart()
    {
        super.onStart();

        // Next 5 days forecast
        // Note: forecastObject.jsonWeekObject[0] is current day forecast

        for(int i=1; i<MainActivity.DAY_FORECAST; i++)
        {
            //Create new forecast
            Forecast dayForecast = new Forecast(forecastObject.jsonWeekObject[i]);
            //Create new forecast view
            ForecastView dayForecastView = new ForecastView(this, null);

            String dateStr=dayForecast.printDate();
            if(i==1)
                dateStr="Tomorrow: "+ dateStr;

            dayForecastView.setForecastView(dateStr, dayForecast.printSummary(), dayForecast.printForecast());

            //add forecast view to listview
            forecastListView.addView(dayForecastView);
        }
    }
}
