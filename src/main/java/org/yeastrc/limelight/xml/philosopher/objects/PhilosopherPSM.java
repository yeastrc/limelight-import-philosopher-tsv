package org.yeastrc.limelight.xml.philosopher.objects;

import java.math.BigDecimal;
import java.util.Map;

public class PhilosopherPSM {

	private BigDecimal massDiff;
	private OpenModification openModification;
	private int scanNumber;
	private BigDecimal precursorMZ;
	private int charge;
	private BigDecimal retentionTime;
	private String peptideSequence;
	private boolean isDecoy;
	private Map<Integer, BigDecimal> mods;
	private BigDecimal peptideProphetProbability;

	public BigDecimal getPeptideProphetProbability() {
		return peptideProphetProbability;
	}

	public void setPeptideProphetProbability(BigDecimal peptideProphetProbability) {
		this.peptideProphetProbability = peptideProphetProbability;
	}

	public Map<Integer, BigDecimal> getMods() {
		return mods;
	}

	public void setMods(Map<Integer, BigDecimal> mods) {
		this.mods = mods;
	}

	public boolean isDecoy() {
		return isDecoy;
	}

	public void setDecoy(boolean decoy) {
		isDecoy = decoy;
	}

	public BigDecimal getMassDiff() {
		return massDiff;
	}

	public void setMassDiff(BigDecimal massDiff) {
		this.massDiff = massDiff;
	}

	public OpenModification getOpenModification() {
		return openModification;
	}

	public void setOpenModification(OpenModification openModification) {
		this.openModification = openModification;
	}

	public int getScanNumber() {
		return scanNumber;
	}

	public void setScanNumber(int scanNumber) {
		this.scanNumber = scanNumber;
	}

	public BigDecimal getPrecursorMZ() {
		return precursorMZ;
	}

	public void setPrecursorMZ(BigDecimal precursorMZ) {
		this.precursorMZ = precursorMZ;
	}

	public int getCharge() {
		return charge;
	}

	public void setCharge(int charge) {
		this.charge = charge;
	}

	public BigDecimal getRetentionTime() {
		return retentionTime;
	}

	public void setRetentionTime(BigDecimal retentionTime) {
		this.retentionTime = retentionTime;
	}

	public String getPeptideSequence() {
		return peptideSequence;
	}

	public void setPeptideSequence(String peptideSequence) {
		this.peptideSequence = peptideSequence;
	}
}
