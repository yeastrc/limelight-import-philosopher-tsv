package org.yeastrc.limelight.xml.philosopher.objects;

import java.math.BigDecimal;
import java.util.Map;

public class SearchParameters {

	@Override
	public String toString() {
		return "SearchParameters{" +
				"staticMods=" + staticMods +
				", decoyPrefix='" + decoyPrefix + '\'' +
				'}';
	}

	/**
	 * @return the staticMods
	 */
	public Map<Character, BigDecimal> getStaticMods() {
		return staticMods;
	}
	/**
	 * @param staticMods the staticMods to set
	 */
	public void setStaticMods(Map<Character, BigDecimal> staticMods) {
		this.staticMods = staticMods;
	}

	public String getDecoyPrefix() {
		return decoyPrefix;
	}

	public void setDecoyPrefix(String decoyPrefix) {
		this.decoyPrefix = decoyPrefix;
	}

	private Map<Character, BigDecimal> staticMods;
	private String decoyPrefix;

}
