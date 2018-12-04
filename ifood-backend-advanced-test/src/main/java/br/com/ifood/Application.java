package br.com.ifood;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Ticker;

import br.com.ifood.interceptor.RequestResponseLoggingInterceptor;

@EnableCircuitBreaker
@EnableCaching
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CacheManager cacheManager(Ticker ticker) {
		CaffeineCache cityNameCache = buildCache("playlistToCityName", ticker, 90);
		CaffeineCache coordinatesCache = buildCache("playlistToCoordinates", ticker, 30);

		SimpleCacheManager manager = new SimpleCacheManager();
		manager.setCaches(Arrays.asList(cityNameCache, coordinatesCache));
		return manager;
	}

	private CaffeineCache buildCache(String name, Ticker ticker, int minutesToExpire) {
		return new CaffeineCache(name, Caffeine.newBuilder().expireAfterWrite(minutesToExpire, TimeUnit.MINUTES)
				.maximumSize(100).ticker(ticker).build());
	}

	@Bean
	public Ticker ticker() {
		return Ticker.systemTicker();
	}

	@Bean
	public RestTemplate restTemplate() {
		ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
		RestTemplate restTemplate = new RestTemplate(factory);
		restTemplate.setInterceptors(Collections.singletonList(new RequestResponseLoggingInterceptor()));
		return restTemplate;
	}
}
