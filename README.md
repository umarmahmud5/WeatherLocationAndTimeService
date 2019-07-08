# WeatherLocationAndTimeService
This service gets the weather information based on Lat Long of a Desktop
This Service uses DarkSkyApi to get weather informatoin using Lat Long
Since there are no GPS modules in a desktop, the service gets the IP infor of the ISP using CheckIP.amazonwsI. This is then sent to IP Info API to get the Lat Long of server (ISP) IP.
