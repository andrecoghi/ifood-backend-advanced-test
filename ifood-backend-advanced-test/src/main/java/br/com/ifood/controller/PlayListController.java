package br.com.ifood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifood.domain.ResponseModel;
import br.com.ifood.service.SpotifyService;
import br.com.ifood.utils.StringUtils;

@RestController
public class PlayListController {

	@Autowired
	SpotifyService sptfyService;

	@RequestMapping(value = "/playlist", params = "city")
	public ResponseModel findByCityName(@RequestParam("city") String cityName) {
		return sptfyService.getPlaylistBy(StringUtils.nomalize(cityName));
	}

	@RequestMapping(value = "/playlist", params = { "lat", "lon" })
	public ResponseModel findByCoordinates(@RequestParam(value = "lat") Double lat,
			@RequestParam(value = "lon") Double lon) {
		return sptfyService.getPlaylistBy(lat, lon);
	}

}
