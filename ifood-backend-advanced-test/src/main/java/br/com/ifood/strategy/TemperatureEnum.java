package br.com.ifood.strategy;

public enum TemperatureEnum {
	HOT(30D), AVERAGE_MIN(15D), AVERAGE_MAX(30D), CHILLY_MIN(10D), CHILLY_MAX(14D);

	private final Double temperature;

	private TemperatureEnum(Double temperature) {
		this.temperature = temperature;
	}

	public Double value() {
		return this.temperature;
	}
}
