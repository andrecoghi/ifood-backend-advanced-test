package br.com.ifood.domain;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ResponseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResponseModel() {
		super();
	}

	public ResponseModel(Double temperature, String status, int code, List<String> messages, List<String> result) {
		this();
		this.temperature = temperature;
		this.status = status;
		this.code = code;
		this.messages = messages;
		this.result = result;
	}

	@JsonIgnore
	private Double temperature;

	private String status;
	private Integer code;
	private List<String> messages;
	private Object result;

	// GETTERS AND SETTERS
	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

}
