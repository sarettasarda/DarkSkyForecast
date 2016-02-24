package saracraba.darkskyforecast;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 *
 * Create a custom view for a forecast
 */
class ForecastView extends View
{
    private TextView day;
    private TextView summary;
    private TextView forecast;
    private Context context;

    ForecastView(Context context, AttributeSet attrs){
        super(context, attrs);

        this.context=context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.forecast_view, null);

        day= (TextView) findViewById(R.id.day);
        summary= (TextView) findViewById(R.id.summary);
        forecast = (TextView) findViewById(R.id.forecast);
    }

    /**
     * @param day day of the forecast
     * @param summary summary of the forecast
     * @param forecast forecast details
     */
    void setForecastView(String day, String summary, String forecast) {
        //this.day= new TextView(context);
        this.day.setText(day);
        //this.summary.setText(summary);
        //this.forecast.setText(forecast);
    }
}
