/*
 * Licensed in Public Domain by IoTica Research Lab.
 * Foundation University Islamabad, Rawalpindi Campus (FURC)
 * Rawalpindi, Pakistan
 */
package weatherlocationandtimeservice;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import java.util.Date;
import java.sql.Timestamp;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 *
 * @author Umar
 */
public class CurrentContextXML {

    private DocumentBuilderFactory docBuildFactory;
    private DocumentBuilder docBuilder;
    private Document currentDocument;
    private Element rootElement;
    private Element location;
    private Element latitude;
    private Element longitude;
    private Element city;
    private Element weather;
    private Element summary;
    private Element precipProb;
    private Element temp;
    private Element dewPoint;
    private Element humidity;
    private Element pressure;
    private Element windSpeed;
    private Element windGust;
    private Element windBng;
    private Element cloudCover;
    private Element uvIndex;
    private Element visibility;
    private Element ozone;
    private Element currentTime;
    private Element currentSystemTime;
    private Element currentTimeLag;
    private Attr attrLat;
    private Attr attrLong;
    private Attr attrCity;
    private Attr attrSummary;
    private Attr attrPrecipProb;
    private Attr attrTemp;
    private Attr attrDewPoint;
    private Attr attrHumidity;
    private Attr attrPressure;
    private Attr attrWindSpeed;
    private Attr attrWindGust;
    private Attr attrWindBng;
    private Attr attrCloudCover;
    private Attr attrUVIndex;
    private Attr attrVisibility;
    private Attr attrOzone;
    private Attr attrSystemTime;
    private TransformerFactory transformerFactory;
    private Transformer transformer;
    private DOMSource source;
    private StreamResult result;
    private StreamResult consoleResult;
    private WeatherService weatherSvc;
    private Date currentSystemDate;
    private long currentSystemTimeValue;
    private Timestamp currentSystemTS;

    public CurrentContextXML() {
        try {
            docBuildFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docBuildFactory.newDocumentBuilder();
            currentDocument = docBuilder.newDocument();
            rootElement = currentDocument.createElement("WeatherLocationandTime");
            currentDocument.appendChild(rootElement);
            location = currentDocument.createElement("Location");
            rootElement.appendChild(location);
            latitude = currentDocument.createElement("Latitude");
            location.appendChild(latitude);
            longitude = currentDocument.createElement("Longitude");
            location.appendChild(longitude);
            city = currentDocument.createElement("City");
            location.appendChild(city);
            weather = currentDocument.createElement("CurrentWeather");
            rootElement.appendChild(weather);
            summary = currentDocument.createElement("Suumary");
            weather.appendChild(summary);
            precipProb = currentDocument.createElement("PrecitipationProbability");
            weather.appendChild(precipProb);
            temp = currentDocument.createElement("Temperature");
            weather.appendChild(temp);
            dewPoint = currentDocument.createElement("DewPoint");
            weather.appendChild(dewPoint);
            humidity = currentDocument.createElement("Humidity");
            weather.appendChild(humidity);
            pressure = currentDocument.createElement("Pressure");
            weather.appendChild(pressure);
            windSpeed = currentDocument.createElement("WindSpeed");
            weather.appendChild(windSpeed);
            windGust = currentDocument.createElement("WindGust");
            weather.appendChild(windGust);
            windBng = currentDocument.createElement("WindBearing");
            weather.appendChild(windBng);
            cloudCover = currentDocument.createElement("CloudCover");
            weather.appendChild(cloudCover);
            uvIndex = currentDocument.createElement("UVIndex");
            weather.appendChild(uvIndex);
            visibility = currentDocument.createElement("Visibility");
            weather.appendChild(visibility);
            ozone = currentDocument.createElement("Ozone");
            weather.appendChild(ozone);
            currentSystemTime = currentDocument.createElement("CurrentSystemTime");
            rootElement.appendChild(currentSystemTime);
            transformerFactory = TransformerFactory.newInstance();
            transformer = transformerFactory.newTransformer();
            weatherSvc = new WeatherService();
            currentSystemDate = new Date();
            currentLocation();
            currentWeather();
            currentTime();
            createXML();
        } catch (ParserConfigurationException | TransformerException ex) {
            System.err.println(ex);
        }
    }

    private void createXML() {
        try {

            source = new DOMSource(currentDocument);
            result = new StreamResult(new File("Current Weather Time and Location.xml"));
            transformer.transform(source, result);
            consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);
        } catch (TransformerException ex) {
            System.err.println(ex);
        }
    }

    private void currentTime() {
        attrSystemTime = currentDocument.createAttribute("Current-System-Time-nsec");
        currentSystemTimeValue = currentSystemDate.getTime();
        currentSystemTS = new Timestamp(currentSystemTimeValue);
        
        attrSystemTime.setValue("" + currentSystemTS);
        currentSystemTime.setAttributeNode(attrSystemTime);
    }

    private void currentLocation() {
        attrLat = currentDocument.createAttribute("Current-Latitude");
        attrLat.setValue(weatherSvc.getLatCurrent());
        attrLong = currentDocument.createAttribute("Current-Longitude");
        attrLong.setValue(weatherSvc.getLongCurrent());
        attrCity = currentDocument.createAttribute("Current-City");
        attrCity.setValue(weatherSvc.getCurrentCity());

        latitude.setAttributeNode(attrLat);
        longitude.setAttributeNode(attrLong);
        city.setAttributeNode(attrCity);
    }

    protected void currentWeather() {
        attrSummary = currentDocument.createAttribute("Summary");
        attrSummary.setValue(weatherSvc.getCurrentSummary());
        attrPrecipProb = currentDocument.createAttribute("Current-Precipitation-Probability");
        attrPrecipProb.setValue(weatherSvc.getCurrentPrecipProb());
        attrTemp = currentDocument.createAttribute("Current-Temperature");
        attrTemp.setValue(weatherSvc.getCurrentTemp());
        attrDewPoint = currentDocument.createAttribute("Current-Dew-Point");
        attrDewPoint.setValue(weatherSvc.getCurrentDewPoint());
        attrHumidity = currentDocument.createAttribute("Current-Humidity");
        attrHumidity.setValue(weatherSvc.getCurrentHumidity());
        attrPressure = currentDocument.createAttribute("Current-Pressure");
        attrPressure.setValue(weatherSvc.getCurrentPressure());
        attrWindGust = currentDocument.createAttribute("Current-WindGust");
        attrWindGust.setValue(weatherSvc.getCurrentWindGust());
        attrWindSpeed = currentDocument.createAttribute("Current-Wind-Speed");
        attrWindSpeed.setValue(weatherSvc.getCurrentWindSpeed());
        attrWindBng = currentDocument.createAttribute("Current-Wind-Bearing");
        attrWindBng.setValue(weatherSvc.getCurrentWindBng());
        attrCloudCover = currentDocument.createAttribute("Current-Cloud-Cover");
        attrCloudCover.setValue(weatherSvc.getCurrentCloudCover());
        attrUVIndex = currentDocument.createAttribute("Current-UV-Index");
        attrUVIndex.setValue(weatherSvc.getCurrentUVIndex());
        attrVisibility = currentDocument.createAttribute("Current-Visbility");
        attrVisibility.setValue(weatherSvc.getCurrentVisibility());
        attrOzone = currentDocument.createAttribute("Current-Ozone");
        attrOzone.setValue(weatherSvc.getCurrentOzone());
        summary.setAttributeNode(attrSummary);
        precipProb.setAttributeNode(attrPrecipProb);
        temp.setAttributeNode(attrTemp);
        dewPoint.setAttributeNode(attrDewPoint);
        humidity.setAttributeNode(attrHumidity);
        pressure.setAttributeNode(attrPressure);
        windSpeed.setAttributeNode(attrWindSpeed);
        windGust.setAttributeNode(attrWindGust);
        windBng.setAttributeNode(attrWindBng);
        cloudCover.setAttributeNode(attrCloudCover);
        uvIndex.setAttributeNode(attrUVIndex);
        visibility.setAttributeNode(attrVisibility);
        ozone.setAttributeNode(attrOzone);
    }
}
