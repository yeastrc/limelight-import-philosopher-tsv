package org.yeastrc.limelight.xml.philosopher.objects;

import java.math.BigDecimal;
import java.util.Map;

public class PhilosopherReportedPeptide {
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((reportedPeptideString == null) ? 0 : reportedPeptideString.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PhilosopherReportedPeptide))
			return false;
		PhilosopherReportedPeptide other = (PhilosopherReportedPeptide) obj;
		if (reportedPeptideString == null) {
			if (other.reportedPeptideString != null)
				return false;
		} else if (!reportedPeptideString.equals(other.reportedPeptideString))
			return false;
		return true;
	}
	
	private String reportedPeptideString;
	private String nakedPeptide;
	private Map<Integer, BigDecimal> mods;
	
	/**
	 * @return the reportedPeptideString
	 */
	public String getReportedPeptideString() {
		return reportedPeptideString;
	}
	/**
	 * @param reportedPeptideString the reportedPeptideString to set
	 */
	public void setReportedPeptideString(String reportedPeptideString) {
		this.reportedPeptideString = reportedPeptideString;
	}
	/**
	 * @return the nakedPeptide
	 */
	public String getNakedPeptide() {
		return nakedPeptide;
	}
	/**
	 * @param nakedPeptide the nakedPeptide to set
	 */
	public void setNakedPeptide(String nakedPeptide) {
		this.nakedPeptide = nakedPeptide;
	}
	/**
	 * @return the mods
	 */
	public Map<Integer, BigDecimal> getMods() {
		return mods;
	}
	/**
	 * @param mods the mods to set
	 */
	public void setMods(Map<Integer, BigDecimal> mods) {
		this.mods = mods;
	}
	
	
	
}
