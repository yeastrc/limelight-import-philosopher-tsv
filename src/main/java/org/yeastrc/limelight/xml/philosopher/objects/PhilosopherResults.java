package org.yeastrc.limelight.xml.philosopher.objects;

import java.util.Map;

public class PhilosopherResults {

	private Map<PhilosopherReportedPeptide, Map<Integer, PhilosopherPSM>> peptidePSMMap;

	/**
	 * @return the peptidePSMMap
	 */
	public Map<PhilosopherReportedPeptide, Map<Integer, PhilosopherPSM>> getPeptidePSMMap() {
		return peptidePSMMap;
	}
	/**
	 * @param peptidePSMMap the peptidePSMMap to set
	 */
	public void setPeptidePSMMap(Map<PhilosopherReportedPeptide, Map<Integer, PhilosopherPSM>> peptidePSMMap) {
		this.peptidePSMMap = peptidePSMMap;
	}

}
