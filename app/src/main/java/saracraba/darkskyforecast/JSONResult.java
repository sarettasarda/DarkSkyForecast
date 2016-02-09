package saracraba.darkskyforecast;

/**
 * Callback interface for JSONDownload AsyncTask
 */
public interface JSONResult {
    void onDownloadCompleted(String result);
}
