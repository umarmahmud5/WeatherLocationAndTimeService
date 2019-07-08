# WeatherLocationAndTimeService
This service gets the weather information based on Lat Long of a Desktop
This Service uses DarkSkyAPI to get weather information using Lat Long values
Since there are no GPS modules in a desktop, the service gets the IP info of the ISP using CheckIP.amazonws. This is then sent to IPInfoAPI to get the Lat Long of server (ISP) IP.
