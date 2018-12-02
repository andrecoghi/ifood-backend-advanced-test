package br.com.ifood.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.ifood.config.OpenWeatherProperties;
import br.com.ifood.domain.openweather.OpenWeather;

@Component
public class OpenWheaterGateway {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private OpenWeatherProperties properties;

	public OpenWeather getWeather(String cityName) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(properties.getUrl());
		builder.queryParam("q", cityName);
		builder.queryParam("units", properties.getUnits());
		builder.queryParam("APPID", properties.getAppId());
		builder.queryParam("fields", "name");

		return restTemplate.getForObject(builder.toUriString(), OpenWeather.class);
	}

	public OpenWeather getWeather(Double lat, Double lon) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(properties.getUrl());
		builder.queryParam("lat", lat);
		builder.queryParam("lon", lon);
		builder.queryParam("units", properties.getUnits());
		builder.queryParam("APPID", properties.getAppId());

		return restTemplate.getForObject(builder.toUriString(), OpenWeather.class);
	}

}
