package org.yeastrc.limelight.xml.philosopher.objects;

import java.math.BigDecimal;

public class MSFraggerPSM extends PhilosopherPSM {

	@Override
	public String toString() {
		return "MSFraggerPSM{" +
				"expectationValue=" + expectationValue +
				", hyperscore=" + hyperscore +
				", nextscore=" + nextscore +
				'}';
	}

	private BigDecimal expectationValue;
	private BigDecimal hyperscore;
	private BigDecimal nextscore;

	public BigDecimal getExpectationValue() {
		return expectationValue;
	}

	public void setExpectationValue(BigDecimal expectationValue) {
		this.expectationValue = expectationValue;
	}

	public BigDecimal getHyperscore() {
		return hyperscore;
	}

	public void setHyperscore(BigDecimal hyperscore) {
		this.hyperscore = hyperscore;
	}

	public BigDecimal getNextscore() {
		return nextscore;
	}

	public void setNextscore(BigDecimal nextscore) {
		this.nextscore = nextscore;
	}
}
