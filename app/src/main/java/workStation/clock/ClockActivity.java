package workStation.clock;

import android.app.Activity; // Required to create an activity.
import android.os.Bundle; // A mapping from String values to various Parcelable types.
import android.widget.AnalogClock;  // Required to create an analog clock
import android.widget.DigitalClock;  // Required to create an digital clock
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;


import saracraba.darkskyforecast.R;

public class ClockActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        AnalogClock analog = (AnalogClock) findViewById(R.id.analog_clock);        //analog clock
        DigitalClock digital = (DigitalClock) findViewById(R.id.digital_clock);        //digital clock

    }

}
