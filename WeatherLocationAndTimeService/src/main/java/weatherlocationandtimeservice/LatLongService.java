/*
 * Licensed in Public Domain by IoTica Research Lab.
 * Foundation University Islamabad, Rawalpindi Campus (FURC)
 * Rawalpindi, Pakistan
 */
package weatherlocationandtimeservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author Umar
 */
public final class LatLongService {

    private BufferedReader latLongStream;
    private URL url;
    private HttpURLConnection httpConn;
    private String gatewayIPAddress;
    private String latLong;
    private final double[] latLongValues;
    private String urlQuery;
    private String cityInfo;

    public LatLongService() {
        latLongValues = new double[2];
    }

    protected double[] getLatLong(String IPAddress) {
        urlQuery = "https://ipinfo.io/" + IPAddress;
        String line;
        try {
            url = new URL(urlQuery);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("GET");
            latLongStream = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
            while ((line = latLongStream.readLine()) != null) {
                if (line.contains("\"country\":")) {
                    latLong = latLongStream.readLine();
                }
            }
            latLongValues[0] = Double.parseDouble(latLong.substring(10, 17));
            latLongValues[1] = Double.parseDouble(latLong.substring(18, 25));
            latLongStream.close();
        } catch (MalformedURLException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        } finally {
            return latLongValues;
        }
    }
    protected String getCity(String IPAddress) {
        urlQuery = "https://ipinfo.io/" + IPAddress;
        String line;
        try {
            url = new URL(urlQuery);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("GET");
            latLongStream = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
            while ((line = latLongStream.readLine()) != null) {
                if (line.contains("\"hostname\":")) {
                    cityInfo = latLongStream.readLine();
                }
            }
            latLongStream.close();
        } catch (MalformedURLException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        } finally {;
            return cityInfo;
        }
    }
}
