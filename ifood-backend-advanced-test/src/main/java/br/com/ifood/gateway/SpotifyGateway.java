package br.com.ifood.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.ifood.domain.spotify.Query;
import br.com.ifood.domain.spotify.Token;
import br.com.ifood.config.SpotifyProperties;

@Component
public class SpotifyGateway {
	
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private SpotifyProperties spotifyProperties;

	public Token getToken() {

		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(spotifyProperties.getClientID(), spotifyProperties.getClientSecret()));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("grant_type", "client_credentials");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

		ResponseEntity<Token> response = restTemplate.exchange(spotifyProperties.getTokenUrl(), HttpMethod.POST, request, Token.class);

		return response.getBody();
	}

	public Query getTracksBy(String query, String accessToken) {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(spotifyProperties.getSearchUrl());

		builder.queryParam("q", query);
		builder.queryParam("type", spotifyProperties.getType());
		builder.queryParam("market", spotifyProperties.getMarket());
		builder.queryParam("limit", spotifyProperties.getLimit());

		ResponseEntity<Query> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, Query.class);

		return response.getBody();
	}

}
