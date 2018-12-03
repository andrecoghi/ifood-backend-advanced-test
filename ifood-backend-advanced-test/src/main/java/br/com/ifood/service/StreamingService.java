package br.com.ifood.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.ifood.domain.ResponseModel;
import br.com.ifood.domain.spotify.Items;
import br.com.ifood.domain.spotify.Query;
import br.com.ifood.domain.spotify.Token;
import br.com.ifood.gateway.SpotifyGateway;
import br.com.ifood.repository.SpotifyRepository;
import br.com.ifood.strategy.TemperatureContext;
import br.com.ifood.utils.DateUtils;

@Component
public class StreamingService {

	@Autowired
	private Environment environment;

	@Autowired
	private SpotifyGateway sptfyGateway;

	@Autowired
	private SpotifyRepository sptfyRepository;

	protected final Logger logger = LoggerFactory.getLogger(SpotifyService.class);

	public String getToken() {
		Date currentTime = new Date();

		if (sptfyRepository.isTokenValid(currentTime)) {
			return sptfyRepository.getToken();
		}

		logger.info("retrieving a new token...");
		Token token = sptfyGateway.getToken();

		sptfyRepository.setToken(token.getAccess_token());
		sptfyRepository.setTimeLimit(DateUtils.addSecondsTo(currentTime, token.getExpires_in()));

		return token.getAccess_token();
	}

	@Cacheable("tracksByMusicStyle")
	protected List<String> getTracksBy(Double temperature) {
		List<String> result = new ArrayList<>();

		String musicStyle = new TemperatureContext(temperature).getMusicStyle();

		Query queryResult = sptfyGateway.getTracksBy(musicStyle, getToken());

		List<Items> items = Arrays.stream(queryResult.getTracks().getItems()).collect(Collectors.toList());

		items.stream().map(Items::getName).forEach(result::add);

		return result;
	}

	protected ResponseModel playlistByCityFallback(String cityName) {
		logger.error("Hystrix is calling fallback method playlistByCityFallback with param: " + cityName);
		return defaultResponse();
	}

	protected ResponseModel playlistByCoordinatesFallback(Double latitude, Double longitude) {
		logger.error("Hystrix is calling fallback method playlistByCoordinatesFallback with param: " + latitude + " "
				+ longitude);
		return defaultResponse();
	}

	private ResponseModel defaultResponse() {
		final ResponseModel response = new ResponseModel();
		final List<String> messagesList = new ArrayList<>(1);
		final List<String> tracksList = new ArrayList<>();

		Integer timeout = Integer.valueOf(
				environment.getProperty("hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds"));

		messagesList.add("This api is out of service");
		messagesList.add("Max timeout (seconds) : " + (timeout / 1000));
		messagesList.add("Returning default soundtracks");

		tracksList.add("Sweet Child O' Mine");
		tracksList.add("Livin' On a Prayer");
		tracksList.add("Back In Black");
		tracksList.add("Eye of the Tiger");
		tracksList.add("Welcome to the Jungle");
		tracksList.add("Tom Sawyer");
		tracksList.add("Beat It");
		tracksList.add("Don't Stop Believin'");

		response.setCode(HttpStatus.OK.value());
		response.setStatus(HttpStatus.OK.getReasonPhrase());
		response.setMessages(messagesList);
		response.setResult(tracksList);

		return response;
	}

}
