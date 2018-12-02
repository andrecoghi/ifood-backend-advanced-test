package br.com.ifood.utils;

import java.text.Normalizer;

public final class StringUtils {
	
	public static String nomalize(String text) {
		if (text == null) {
			return "";
		}

		String normalizedText = text;
		normalizedText = Normalizer.normalize(text, Normalizer.Form.NFKD);
		
		String stripAccents = normalizedText.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
		
		String result = stripAccents.replaceAll("\\s","+");
		
		return result;
	}

}
