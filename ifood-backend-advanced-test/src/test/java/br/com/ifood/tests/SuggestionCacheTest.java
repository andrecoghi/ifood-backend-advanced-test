package br.com.ifood.tests;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.github.benmanes.caffeine.cache.Ticker;
import com.google.common.testing.FakeTicker;

import br.com.ifood.Application;
import br.com.ifood.domain.openweather.Clouds;
import br.com.ifood.domain.openweather.Coord;
import br.com.ifood.domain.openweather.Main;
import br.com.ifood.domain.openweather.OpenWeather;
import br.com.ifood.domain.openweather.Sys;
import br.com.ifood.domain.openweather.Weather;
import br.com.ifood.domain.openweather.Wind;
import br.com.ifood.service.OpenWeatherService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SuggestionCacheTest {

	@Configuration
	@Import(Application.class)
	public static class TestConfig {

		static FakeTicker fakeTicker = new FakeTicker();

		@Bean
		public Ticker ticker() {
			return fakeTicker::read;
		}

	}

	private static final String CITY_NAME = "London";

	@SpyBean
	private RestTemplate restTemplate;

	@Autowired
	private OpenWeatherService owService;

	@Before
	public void setUp() throws Exception {
		OpenWeather ow = stubWheather(CITY_NAME);
		doReturn(ow).when(restTemplate).getForObject(anyString(), eq(OpenWeather.class));
	}

	@Test
	public void shouldUseCachesWithDifferentTTL() throws Exception {
		// 0 minutes
		owService.getWeather(CITY_NAME);
		verify(restTemplate, times(1)).getForObject(anyString(), eq(OpenWeather.class));

		// after 5 minutes
		TestConfig.fakeTicker.advance(5, TimeUnit.MINUTES);
		owService.getWeather(CITY_NAME);
		verify(restTemplate, times(1)).getForObject(anyString(), eq(OpenWeather.class));

		// after 95 minutes we expect the cached 'openWeatherCityName'
		// is expired which we confirm by another call to the endpoint.
		TestConfig.fakeTicker.advance(90, TimeUnit.MINUTES);
		owService.getWeather(CITY_NAME);
		verify(restTemplate, times(2)).getForObject(anyString(), eq(OpenWeather.class));

	}

	private OpenWeather stubWheather(String cityName) {
		return new OpenWeather("", "", new Clouds(), new Coord(), new Wind(), "", "", new Sys(), cityName, "", new Weather[1], new Main());
	}

}
