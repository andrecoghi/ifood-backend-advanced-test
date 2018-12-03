package br.com.ifood.strategy;

public class TemperatureContext implements TemperatureRules{

	private Double temperature;

	public TemperatureContext(Double temperature) {
		if (temperature == null) {
			this.temperature = 0D;
			return;
		}
		this.temperature = temperature;
	}

	public String getMusicStyle() {
		return TemperatureRules.getMusicStyle(temperature);
	}
}
