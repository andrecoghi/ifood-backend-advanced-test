package br.com.ifood.strategy;

import static br.com.ifood.strategy.TemperatureEnum.AVERAGE_MAX;
import static br.com.ifood.strategy.TemperatureEnum.AVERAGE_MIN;
import static br.com.ifood.strategy.TemperatureEnum.CHILLY_MAX;
import static br.com.ifood.strategy.TemperatureEnum.CHILLY_MIN;

public interface TemperatureRules {

	static String getMusicStyle(Double temp) {
		if (isHot(temp))
			return new Hot().getMusicStyle();

		else if (isAverage(temp))
			return new Average().getMusicStyle();

		else if (isChilly(temp))
			return new Chilly().getMusicStyle();

		else
			return new Freezing().getMusicStyle();
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
