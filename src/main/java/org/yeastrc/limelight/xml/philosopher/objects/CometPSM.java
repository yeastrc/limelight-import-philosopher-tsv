package org.yeastrc.limelight.xml.philosopher.objects;

import java.math.BigDecimal;

public class CometPSM extends PhilosopherPSM {

	@Override
	public String toString() {
		return "CometPSM{" +
				"expectationValue=" + expectationValue +
				", XCorr=" + XCorr +
				", DeltaCN=" + DeltaCN +
				", spRank=" + spRank +
				'}';
	}

	private BigDecimal expectationValue;
	private BigDecimal XCorr;
	private BigDecimal DeltaCN;
	private int spRank;

	public BigDecimal getExpectationValue() {
		return expectationValue;
	}

	public void setExpectationValue(BigDecimal expectationValue) {
		this.expectationValue = expectationValue;
	}

	public BigDecimal getXCorr() {
		return XCorr;
	}

	public void setXCorr(BigDecimal XCorr) {
		this.XCorr = XCorr;
	}

	public BigDecimal getDeltaCN() {
		return DeltaCN;
	}

	public void setDeltaCN(BigDecimal deltaCN) {
		DeltaCN = deltaCN;
	}

	public int getSpRank() {
		return spRank;
	}

	public void setSpRank(int spRank) {
		this.spRank = spRank;
	}
}
