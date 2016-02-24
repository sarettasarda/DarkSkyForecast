package saracraba.darkskyforecast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by saracraba on 2/22/16.
 */
class ListViewAdapter  extends ArrayAdapter<Forecast>{

    LayoutInflater inflater;
    ArrayList<Forecast>  objects= null;

    ListViewAdapter (Context context, int layoutResourceId, ArrayList<Forecast> objects){
        super(context, layoutResourceId, objects);
        /* We get the inflator in the constructor */
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.objects= objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        WeatherHolder holder = null;

       if(row == null)
        {
            row = inflater.inflate(R.layout.forecast_view, parent, false);

            holder = new WeatherHolder();
            holder.day = (TextView)row.findViewById(R.id.day);
            holder.summary = (TextView)row.findViewById(R.id.summary);
            holder.forecast = (TextView)row.findViewById(R.id.forecast);

            row.setTag(holder);
        }
        else
        {
            holder = (WeatherHolder)row.getTag();
        }

        Forecast weather = objects.get(position);

        String date= weather.printDate();
        if(position==0){
            date= "Current at " + weather.printHour();
        }
        else if(position== 1){
            date= "Today "+date;
        }
        else if(position ==2){
            date = "Tomorrow "+ date;
        }
        holder.day.setText(date);
        holder.summary.setText(weather.printSummary());
        holder.forecast.setText(weather.printForecast());

        return row;

    }

    static class WeatherHolder
    {
        TextView day;
        TextView summary;
        TextView forecast;
    }

}
