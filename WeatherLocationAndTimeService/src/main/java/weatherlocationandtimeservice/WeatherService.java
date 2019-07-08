/*
 * Licensed in Public Domain by IoTica Research Lab.
 * Foundation University Islamabad, Rawalpindi Campus (FURC)
 * Rawalpindi, Pakistan
 */
package weatherlocationandtimeservice;

/**
 *
 * @author Umar
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.StringTokenizer;

public final class WeatherService {

    private final String darkSkyKey = "----------";  //Get a free DarkSky licesnce key 
    private final String darkSkyURL;
    private BufferedReader readWeatherStream;
    private final GatewayIPService gatewayIP;
    private final String gatewayIPAddress;
    private StringTokenizer weatherInfo;
    private String[] tokens;
    private String weatherReport;
    private final LatLongService llsvc;
    private final double[] latLongCurrent;
    private final File weatherReportFile;
    private final String latLongCity;
    private PrintWriter weatherLocWriter;

    public WeatherService() {
        weatherReportFile = new File("Weather and Location Information.txt");
        try {
            weatherLocWriter = new PrintWriter(weatherReportFile);
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
        gatewayIP = new GatewayIPService();
        gatewayIPAddress = gatewayIP.getGatewayIP();
        llsvc = new LatLongService();
        latLongCurrent = llsvc.getLatLong(gatewayIPAddress);
        latLongCity = llsvc.getCity(gatewayIPAddress);
        darkSkyURL = "https://api.darksky.net/forecast/" + darkSkyKey + "/" + latLongCurrent[0] + "," + latLongCurrent[1];
        getWeatherReport(darkSkyURL);
        writeInFile();
        weatherLocWriter.close();
    }
    protected String getLatCurrent(){
        return Double.toString(latLongCurrent[0]);
    }
    protected String getLongCurrent(){
        return Double.toString(latLongCurrent[1]);
    }
    protected String getCurrentCity(){
        return latLongCity.substring((latLongCity.indexOf(":")+3), (latLongCity.length()-2));
    }
    protected String getCurrentSummary(){
        return tokens[4].substring((tokens[4].indexOf("\"summary\":")+11),(tokens[4].length()-1));
    }
    protected String getCurrentPrecipProb(){
        return tokens[7].substring((tokens[7].indexOf("\"precipProbability\":")+20),(tokens[7].length()));
    }
    protected String getCurrentTemp(){
        return tokens[8].substring((tokens[8].indexOf("\"temperature\":")+14),(tokens[8].length()));
    }
    protected String getCurrentDewPoint(){
        return tokens[10].substring((tokens[10].indexOf("\"dewPoint\":")+11),(tokens[10].length()));
    }
    protected String getCurrentHumidity(){
        return tokens[11].substring((tokens[11].indexOf("\"humidity\":")+11),(tokens[11].length()));
    }
    protected String getCurrentPressure(){
        return tokens[12].substring((tokens[12].indexOf("\"pressure\":")+11),(tokens[12].length()));
    }
    protected String getCurrentWindSpeed(){
        return tokens[13].substring((tokens[13].indexOf("\"windSpeed\":")+12),(tokens[13].length()));
    }
    protected String getCurrentWindGust(){
        return tokens[14].substring((tokens[14].indexOf("\"windGust\":")+11),(tokens[14].length()));
    }    
    protected String getCurrentWindBng(){
        return tokens[15].substring((tokens[15].indexOf("\"windBearing\":")+14),(tokens[15].length()));
    }
    protected String getCurrentCloudCover(){
        return tokens[16].substring((tokens[16].indexOf("\"cloudCover\":")+13),(tokens[16].length()));
    }
    protected String getCurrentUVIndex(){
        return tokens[17].substring((tokens[17].indexOf("\"uvIndex\":")+10),(tokens[17].length()));
    }
    protected String getCurrentVisibility(){
        return tokens[18].substring((tokens[18].indexOf("\"visibility\":")+14),(tokens[18].length()));
    }
    protected String getCurrentOzone(){
        return tokens[19].substring((tokens[19].indexOf("\"ozone\":")+9),(tokens[19].length()-1));
    }
    
    private void writeInFile() {
        weatherLocWriter.println("Weather and location service - IOTICA, FURC");
        weatherLocWriter.println("Powered by Dark Sky API: https://darksky.net/dev");
        weatherLocWriter.println("and  CHECK IP:  http://checkip.amazonaws.com/");
        weatherLocWriter.println("and IP INFO: https://ipinfo.io/");
        weatherLocWriter.println();
        weatherLocWriter.println("Lattitude  is: " + this.getLatCurrent());
        weatherLocWriter.println("Longitude  is: " + this.getLongCurrent());
        weatherLocWriter.println("City is: "+ this.getCurrentCity());
        weatherLocWriter.println("Current summary is: "+this.getCurrentSummary());
        weatherLocWriter.println("Current Precipitation Probability is: "+this.getCurrentPrecipProb());
        weatherLocWriter.println("Current temperature is: "+this.getCurrentTemp());
        weatherLocWriter.println("Current dew point is: "+this.getCurrentDewPoint());
        weatherLocWriter.println("Current humidity is: "+this.getCurrentHumidity());
        weatherLocWriter.println("Current pressure is: "+this.getCurrentPressure());
        weatherLocWriter.println("Current wind speed is: "+this.getCurrentWindSpeed());
        weatherLocWriter.println("Current wind gust is: "+this.getCurrentWindGust());
        weatherLocWriter.println("Current wind bearing is: "+this.getCurrentWindBng());
        weatherLocWriter.println("Current cloud cover is: "+this.getCurrentCloudCover());
        weatherLocWriter.println("Current UV index is: "+this.getCurrentUVIndex());
        weatherLocWriter.println("Current visibility is: "+this.getCurrentVisibility());
        weatherLocWriter.println("Current ozone is: "+this.getCurrentOzone());
        
    }

    private void getWeatherReport(String urlToAccess) {
        try {
            URL url = new URL(urlToAccess);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("GET");
            readWeatherStream = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
            weatherReport = readWeatherStream.readLine();
            weatherInfo = new StringTokenizer(weatherReport, ",");
            tokens = weatherReport.split(",");
            //System.out.println(weatherReport);
            readWeatherStream.close();
        } catch (MalformedURLException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
