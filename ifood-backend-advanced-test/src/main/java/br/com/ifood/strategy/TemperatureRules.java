package br.com.ifood.strategy;

import static br.com.ifood.strategy.TemperatureEnum.AVERAGE_MAX;
import static br.com.ifood.strategy.TemperatureEnum.AVERAGE_MIN;
import static br.com.ifood.strategy.TemperatureEnum.CHILLY_MAX;
import static br.com.ifood.strategy.TemperatureEnum.CHILLY_MIN;

public interface TemperatureRules {

	static String getSuggestion(Double temp) {
		if (isHot(temp))
			return new Hot().getSuggestion();

		else if (isAverage(temp))
			return new Average().getSuggestion();

		else if (isChilly(temp))
			return new Chilly().getSuggestion();

		else
			return new Freezing().getSuggestion();
	}

	static boolean isHot(Double temp) {
		return temp.compareTo(30D) >= 0;
	}

	static boolean isAverage(Double temp) {
		return between(temp, AVERAGE_MIN.value(), AVERAGE_MAX.value());
	}

	static boolean isChilly(Double temp) {
		return between(temp, CHILLY_MIN.value(), CHILLY_MAX.value());
	}

	static boolean between(double i, double minValueInclusive, double maxValueInclusive) {
		return (i >= minValueInclusive && i <= maxValueInclusive);
	}
}
