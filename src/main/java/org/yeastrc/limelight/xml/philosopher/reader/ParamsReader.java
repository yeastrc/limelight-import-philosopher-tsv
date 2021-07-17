package org.yeastrc.limelight.xml.philosopher.reader;

import org.yeastrc.limelight.xml.philosopher.constants.Constants;
import org.yeastrc.limelight.xml.philosopher.objects.SearchParameters;

import java.io.*;

public class ParamsReader {

    public static String getSearchProgram(File paramsFile) throws Exception {
        String line = getFirstLine(paramsFile);
        return getSearchProgramFromParamsFirstLine(line);
    }

    public static SearchParameters getSearchParameters(File paramsFile, String searchProgram) throws Exception {
        if(searchProgram.equals(Constants.PROGRAM_NAME_MSFRAGGER)) {
            return MSFraggerParamsReader.getMSFraggerParameters(paramsFile);
        }
        if(searchProgram.equals(Constants.PROGRAM_NAME_COMET)) {
            return CometParamsReader.getCometParameters(paramsFile);
        }

        throw new Exception("Unable to get search params. Search program was not comet or MSFragger.");
    }

    public static String getSearchProgramFromParamsFirstLine(String line) throws Exception {
        if(line.contains("comet")) { return Constants.PROGRAM_NAME_COMET; }
        if(line.contains("MSFragger")) { return Constants.PROGRAM_NAME_MSFRAGGER; }
        throw new Exception("Unable to determine from params file if search program was comet or MSFragger.");
    }


    private static String getFirstLine(File paramsFile) throws IOException {
        try(BufferedReader br = new BufferedReader( new FileReader( paramsFile))) {
            return br.readLine();
        }
    }

}
