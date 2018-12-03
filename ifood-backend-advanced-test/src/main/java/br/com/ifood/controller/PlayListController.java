package br.com.ifood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifood.domain.ResponseModel;
import br.com.ifood.service.SpotifyService;
import br.com.ifood.utils.StringUtils;

@RestController
@RequestMapping("/playlist")
public class PlayListController {

	@Autowired
	SpotifyService sptfyService;

	@GetMapping("/city/{name}")
	public ResponseModel findByCityName(@PathVariable("name") String cityName) {
		return sptfyService.getPlaylistBy(StringUtils.nomalize(cityName));
	}

	@GetMapping("/lat/{lat}/lon/{lon}")
	public ResponseModel findByCoordinates(@PathVariable(value = "lat") Double lat,
			@PathVariable(value = "lon") Double lon) {
		return sptfyService.getPlaylistBy(lat, lon);
	}

}
