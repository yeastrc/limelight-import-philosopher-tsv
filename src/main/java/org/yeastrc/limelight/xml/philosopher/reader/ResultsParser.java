/*
 * Original author: Michael Riffle <mriffle .at. uw.edu>
 *
 * Copyright 2018 University of Washington - Seattle, WA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.yeastrc.limelight.xml.philosopher.reader;

import com.sun.xml.bind.v2.runtime.reflect.opt.Const;
import org.yeastrc.limelight.xml.philosopher.constants.Constants;
import org.yeastrc.limelight.xml.philosopher.objects.*;
import org.yeastrc.limelight.xml.philosopher.utils.PhilosopherParsingUtils;
import org.yeastrc.limelight.xml.philosopher.utils.ReportedPeptideUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Riffle
 *
 */
public class ResultsParser {

	public static PhilosopherResults getResults(File psmTSVFile, SearchParameters params, boolean isOpenMod, String searchProgram ) throws Throwable {

		PhilosopherResults results = new PhilosopherResults();
		Map<PhilosopherReportedPeptide,Map<Integer,PhilosopherPSM>> resultMap = new HashMap<>();
		results.setPeptidePSMMap(resultMap);

		try(BufferedReader br = new BufferedReader(new FileReader( psmTSVFile ))) {

			String headerLine = br.readLine();
			Map<String, Integer> columnMap = processHeaderLine(headerLine);

			for(String line = br.readLine(); line != null; line = br.readLine()) {
				PhilosopherPSM psm = getPSMFromLine(line, columnMap, params, isOpenMod, searchProgram);
				PhilosopherReportedPeptide reportedPeptide = ReportedPeptideUtils.getReportedPeptideForPSM( psm );

				if( !results.getPeptidePSMMap().containsKey( reportedPeptide ) )
					results.getPeptidePSMMap().put( reportedPeptide, new HashMap<>() );

				results.getPeptidePSMMap().get( reportedPeptide ).put( psm.getScanNumber(), psm );
			}
		}

		return results;
	}

	/**
	 * Get a map of column headers to the index of that column on the line
	 *
	 * @param headerLine
	 * @return
	 */
	private static Map<String, Integer> processHeaderLine(String headerLine) {
		Map<String, Integer> columnMap = new HashMap<>();

		String[] fields = headerLine.split("\\t", -1);
		for(int i = 0; i < fields.length; i++) {
			columnMap.put(fields[i], i);
		}

		return columnMap;
	}

	/**
	 * Get a PSM object for a given line in MSFragger TSV output
	 *
	 * @param line
	 * @param params
	 * @param isOpenMod
	 * @return
	 * @throws Exception
	 */
	private static PhilosopherPSM getPSMFromLine(String line, Map<String, Integer> columnMap, SearchParameters params, boolean isOpenMod, String searchProgram) throws Exception {

		String decoyPrefix = params.getDecoyPrefix();

		String[] fields = line.split("\\t", -1);

		final int scanNumber = PhilosopherParsingUtils.getScanNumberFromSpectrumId(fields[columnMap.get("Spectrum")]);
		final int charge = Integer.parseInt(fields[columnMap.get("Charge")]);
		final String sequence = fields[columnMap.get("Peptide")];
		final BigDecimal retentionTime = new BigDecimal(fields[columnMap.get("Retention")]);
		final BigDecimal observedMz = new BigDecimal(fields[columnMap.get("Observed M/Z")]);
		final BigDecimal deltaMass = new BigDecimal(fields[columnMap.get("Delta Mass")]);
		final BigDecimal peptideProphetProbability = new BigDecimal(fields[columnMap.get("PeptideProphet Probability")]);
		final String assignedModifications = fields[columnMap.get("Assigned Modifications")];
		final String protein = fields[columnMap.get("Protein")];
		final String mappedProteins = fields[columnMap.get("Mapped Proteins")];

		PhilosopherPSM psm = null;
		if(searchProgram.equals(Constants.PROGRAM_NAME_COMET)) {
			psm = new CometPSM();
		} else if(searchProgram.equals(Constants.PROGRAM_NAME_MSFRAGGER)) {
			psm = new MSFraggerPSM();
		} else {
			throw new Exception("Unknown search program: " + searchProgram);
		}

		psm.setScanNumber(scanNumber);
		psm.setCharge(charge);
		psm.setMassDiff(deltaMass);
		psm.setPeptideSequence(sequence);
		psm.setPrecursorMZ(observedMz);
		psm.setRetentionTime(retentionTime);
		psm.setPeptideProphetProbability(peptideProphetProbability);

		if(isOpenMod) {
			psm.setOpenModification( new OpenModification(deltaMass, new HashSet<>()));
		}

		if(searchProgram.equals(Constants.PROGRAM_NAME_COMET)) {
			// handle comet scores

			final BigDecimal xcorr = new BigDecimal(fields[columnMap.get("XCorr")]);
			final BigDecimal deltaCn = new BigDecimal(fields[columnMap.get("DeltaCN")]);
			final int spRank = Integer.parseInt(fields[columnMap.get("SPRank")]);
			final BigDecimal expectScore = new BigDecimal(fields[columnMap.get("Expectation")]);

			((CometPSM) psm).setXCorr(xcorr);
			((CometPSM) psm).setDeltaCN(deltaCn);
			((CometPSM) psm).setSpRank(spRank);
			((CometPSM) psm).setExpectationValue(expectScore);
		} else {
			// handle msfragger scores

			final BigDecimal expectScore = new BigDecimal(fields[columnMap.get("Expectation")]);
			final BigDecimal hyperScore = new BigDecimal(fields[columnMap.get("Hyperscore")]);
			final BigDecimal nextScore = new BigDecimal(fields[columnMap.get("Nextscore")]);

			((MSFraggerPSM) psm).setExpectationValue(expectScore);
			((MSFraggerPSM) psm).setHyperscore(hyperScore);
			((MSFraggerPSM) psm).setNextscore(nextScore);
		}


		// handle var mods
		psm.setModifications(getDynamicModsFromString(modString, sequence, params));

		// handle proteins


		// set whether or not this is a decoy hit
		psm.setDecoy(isDecoyHit(decoyPrefix, protein, mappedProteins));

		return psm;
	}

	/**
	 * Returns true if the PSM only matches decoy proteins, false otherwise.
	 *
	 * @param decoyPrefix
	 * @param proteinName
	 * @param altProteinNames
	 * @return
	 */
	private static boolean isDecoyHit(String decoyPrefix, String proteinName, String altProteinNames) {

		if(decoyPrefix == null || decoyPrefix.length() < 1) { return false; }

		if(!proteinName.startsWith(decoyPrefix)) { return false; }

		if(altProteinNames != null && altProteinNames.length() > 0) {
			String[] altProteins = altProteinNames.split(", ");
			for (String altProtein : altProteins) {
				if (!altProtein.startsWith(decoyPrefix)) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Example of string: "10C(57.02146), 17C(57.02146), 38C(57.02146)"
	 * @param modString
	 * @return
	 */
	private static Map<Integer, BigDecimal> getDynamicModsFromString(String modString, String peptide, MSFraggerParameters params) throws Exception {

		Map<Integer, BigDecimal> modMap = new HashMap<>();

		if(modString == null || modString.length() < 1) {
			return modMap;
		}

		String[] modChunks = modString.split(", ");
		for(String modChunk : modChunks ) {
			Matcher m = modPattern.matcher(modChunk);
			if(m.matches()) {

				int position = Integer.parseInt(m.group(1));
				String aminoAcid = String.valueOf( peptide.charAt( position - 1 ) );
				BigDecimal mass = new BigDecimal(m.group(2));

				if(!isModStaticMod(aminoAcid, mass, params))
					modMap.put(position, mass);

			} else {

				m = modTermPattern.matcher(modChunk);

				if( m.matches()) {

					int position = 0;
					if(m.group(1).equals("C")) {
						position = peptide.length();
					}

					BigDecimal mass = new BigDecimal(m.group(2));

					modMap.put(position, mass);

				} else {
					throw new Exception("Did not understand reported modification: " + modChunk);
				}
			}
		}

		return modMap;
	}

	private static Pattern modPattern = Pattern.compile("^(\\d+)[A-Z]\\((.+)\\)$");
	private static Pattern modTermPattern = Pattern.compile("^([NC])\\-term\\((.+)\\)$");

}
