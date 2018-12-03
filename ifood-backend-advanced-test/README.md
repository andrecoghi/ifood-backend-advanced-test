
# iFood Backend Advanced Test

This repository contains my solution for the iFood Backend Advanced Test.
<br/>

**Proposed problem:**
<br/>
Create a micro-service capable to receive RESTful requests receiving as parameter either city name or latitude and longitude coordinates and that returns soundtracks according to the current temperature.
<br/>

For the temperature data I used [OpenWeatherMaps API](https://openweathermap.org/) and [Spotify](https://developer.spotify.com/) for the song tracks.
<br/>

**To Development this project I used the following technologies and frameworks:**
 - Maven
 - Spring Boot
 - Java 8
 - jUnit
 - Caffeine Cache
 - Hystrix
 
 <br/>
 
**Endpoints to test:**
<br/>
(http://localhost:8080/ifood/playlist?city=campinas)

(http://localhost:8080/ifood/playlist?lat=35&lon=139)