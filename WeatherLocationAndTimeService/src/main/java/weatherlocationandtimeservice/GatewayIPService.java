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
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public final class GatewayIPService {
    private final String urlName ="http://checkip.amazonaws.com/";
    private BufferedReader gatewayIPStream;
    private String gatewayIP;
    private URL url;
    private HttpURLConnection httpConn;
    
    protected String getGatewayIP() {
        try {
            url = new URL(urlName);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("GET");
            gatewayIPStream = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
            gatewayIP = gatewayIPStream.readLine();
            gatewayIPStream.close();            
        } catch (MalformedURLException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }
        finally{
            return gatewayIP;
        }
    }
}
