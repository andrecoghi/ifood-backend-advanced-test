package br.com.ifood.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.com.ifood.config.AsyncConfiguration;
import br.com.ifood.gateway.OpenWheaterGateway;

@Service
public class OpenWeatherService {

	@Autowired
	private OpenWheaterGateway owGateway;

	@Async(AsyncConfiguration.TASK_EXECUTOR_SERVICE)
	@Cacheable("openWeatherCityName")
	public  CompletableFuture<String> getWeather(String cityName) {
		return CompletableFuture.completedFuture(owGateway.getWeather(cityName).getMain().getTemp());
	}

	@Async(AsyncConfiguration.TASK_EXECUTOR_SERVICE)
	@Cacheable("openWeatherCoordinates")
	public CompletableFuture<String> getWeather(Double lat, Double lon) {
		return CompletableFuture.completedFuture(owGateway.getWeather(lat, lon).getMain().getTemp());
	}
}
