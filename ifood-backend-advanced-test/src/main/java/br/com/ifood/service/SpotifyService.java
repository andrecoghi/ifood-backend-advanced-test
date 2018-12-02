package br.com.ifood.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import br.com.ifood.domain.ResponseModel;

@Service
public class SpotifyService extends StreamingService {

	@Autowired
	private OpenWeatherService openWeatherService;

	@HystrixCommand(fallbackMethod = "playlistByCityFallback")
	public ResponseModel getPlaylistBy(String cityName) {

		CompletableFuture<String> tempFuture = openWeatherService.getWeather(cityName);

		Double temp = null;
		List<String> tracks = null;
		try {
			temp = Double.parseDouble(tempFuture.get());
			CompletableFuture<List<String>> tracksFuture = super.getTracksBy(temp);
			tracks = tracksFuture.get();
		} catch (InterruptedException e) {
			super.logger.info(e.getMessage());
			e.printStackTrace();
		} catch (ExecutionException e) {
			super.logger.info(e.getMessage());
			e.printStackTrace();
		}

		final List<String> msgs = new ArrayList<>(1);
		msgs.add("Temperatura em Celsius: " + temp);

		return new ResponseModel(temp, HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), msgs, tracks);

	}

	@HystrixCommand(fallbackMethod = "playlistByCoordinatesFallback")
	public ResponseModel getPlaylistBy(Double latitude, Double longitude) {

		CompletableFuture<String> tempFuture = openWeatherService.getWeather(latitude, longitude);

		Double temp = null;
		List<String> tracks = null;
		try {
			temp = Double.parseDouble(tempFuture.get());
			CompletableFuture<List<String>> tracksFuture = super.getTracksBy(temp);
			tracks = tracksFuture.get();
		} catch (InterruptedException e) {
			super.logger.info(e.getMessage());
			e.printStackTrace();
		} catch (ExecutionException e) {
			super.logger.info(e.getMessage());
			e.printStackTrace();
		}

		final List<String> msgs = new ArrayList<>(1);
		msgs.add("Temperatura em Celsius: " + temp);

		return new ResponseModel(temp, HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), msgs, tracks);
	}

}
