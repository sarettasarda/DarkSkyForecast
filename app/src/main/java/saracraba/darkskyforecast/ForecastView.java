package saracraba.darkskyforecast;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

/**
 *
 * Create a custom view for a forecast
 */
class ForecastView extends LinearLayout
{
    private TextView day;
    private TextView summary;
    private TextView forecast;

    @Override
    public void findViewsWithText(ArrayList<View> outViews, CharSequence text, int flags) {
        super.findViewsWithText(outViews, text, flags);
    }

    ForecastView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.forecast_view,this);

        day= (TextView) findViewById(R.id.day);
        summary= (TextView) findViewById(R.id.summary);
        forecast = (TextView) findViewById(R.id.forecast);
    }

    /**
     *
     * @param day day of the forecast
     * @param summary summary of the forecast
     * @param forecast forecast details
     */
    void setForecastView(String day, String summary, String forecast)
    {
        this.day.setText(day);
        this.summary.setText(summary);
        this.forecast.setText(forecast);
    }
}
