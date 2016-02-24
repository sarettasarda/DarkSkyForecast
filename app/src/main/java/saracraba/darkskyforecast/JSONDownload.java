package saracraba.darkskyforecast;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *  Establish a HTTP connection to download Json data from a given URL
 */
public class JSONDownload extends AsyncTask<String, String, String>
{
    //Http connection
    private HttpURLConnection urlConnection;


    @Override
    protected String  doInBackground(String... query)
    {

        StringBuilder result = new StringBuilder();

        try {
            //Establish a HTTP connection
            URL url = new URL(query[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

        }
        catch( Exception e) {
            e.printStackTrace();
        }
        finally {
            urlConnection.disconnect();
        }

        return result.toString();
    }

}
