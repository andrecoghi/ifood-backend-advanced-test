package br.com.ifood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifood.gateway.OpenWheaterGateway;

@Service
public class OpenWeatherService {

	@Autowired
	private OpenWheaterGateway owGateway;

	public String getWeather(String cityName) {
		return owGateway.getWeather(cityName).getMain().getTemp();
	}

	public String getWeather(Double lat, Double lon) {
		return owGateway.getWeather(lat, lon).getMain().getTemp();
	}
}
