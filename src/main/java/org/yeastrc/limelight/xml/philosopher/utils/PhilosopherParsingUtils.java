package org.yeastrc.limelight.xml.philosopher.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhilosopherParsingUtils {

    /**
     * Parse the spectrum id (Spectrum field in psm.tsv) to get the scan number for this PSM
     *
     * @param spectrumId
     * @return
     * @throws Exception
     */
    public static int getScanNumberFromSpectrumId(String spectrumId) throws Exception {
        Pattern p = Pattern.compile("^.+\\.\\d+\\.(\\d+)\\.\\d+$");
        Matcher m = p.matcher(spectrumId);

        if(m.matches()) {
            return Integer.parseInt(m.group(1));
        }

        throw new Exception("Unable to find scan number in spectrum id: " + spectrumId);
    }

}
