package br.com.ifood.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import br.com.ifood.domain.ResponseModel;

@Service
public class SpotifyService extends StreamingService {

	@Autowired
	private OpenWeatherService openWeatherService;

	@HystrixCommand(fallbackMethod = "playlistByCityFallback")
	@Cacheable("playlistToCityName")
	public ResponseModel getPlaylistBy(String cityName) {

		final Double temp = Double.parseDouble(openWeatherService.getWeather(cityName));

		final List<String> msgs = new ArrayList<>(1);
		msgs.add("Temperatura em Celsius: " + temp);

		final List<String> tracks = super.getTracksBy(temp);

		return new ResponseModel(temp, HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), msgs, tracks);

	}

	@HystrixCommand(fallbackMethod = "playlistByCoordinatesFallback")
	@Cacheable("playlistToCoordinates")
	public ResponseModel getPlaylistBy(Double latitude, Double longitude) {

		final Double temp = Double.parseDouble(openWeatherService.getWeather(latitude, longitude));

		final List<String> msgs = new ArrayList<>(1);
		msgs.add("Temperatura em Celsius: " + temp);

		final List<String> tracks = super.getTracksBy(temp);

		return new ResponseModel(temp, HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), msgs, tracks);
	}

}
