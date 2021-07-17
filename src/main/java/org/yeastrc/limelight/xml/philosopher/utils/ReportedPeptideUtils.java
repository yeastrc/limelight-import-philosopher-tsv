package org.yeastrc.limelight.xml.philosopher.utils;

import org.yeastrc.limelight.xml.philosopher.objects.MSFraggerPSM;
import org.yeastrc.limelight.xml.philosopher.objects.PhilosopherPSM;
import org.yeastrc.limelight.xml.philosopher.objects.PhilosopherReportedPeptide;
import org.yeastrc.limelight.xml.philosopher.objects.PhilosopherResults;

public class ReportedPeptideUtils {

	public static PhilosopherReportedPeptide getReportedPeptideForPSM(PhilosopherPSM psm ) throws Exception {
		
		PhilosopherReportedPeptide rp = new PhilosopherReportedPeptide();
		
		rp.setNakedPeptide( psm.getPeptideSequence() );
		rp.setMods( psm.getMods() );
		rp.setReportedPeptideString( ModParsingUtils.getRoundedReportedPeptideString( psm.getPeptideSequence(), psm.getMods() ));

		return rp;
	}

	public static boolean reportedPeptideOnlyContainsDecoys(PhilosopherResults results, PhilosopherReportedPeptide reportedPeptide ) {

		for( int scanNumber : results.getPeptidePSMMap().get( reportedPeptide ).keySet() ) {

			PhilosopherPSM psm = results.getPeptidePSMMap().get( reportedPeptide ).get( scanNumber );
			if( !psm.isDecoy() ) {
				return false;
			}
		}

		return true;
	}

}
