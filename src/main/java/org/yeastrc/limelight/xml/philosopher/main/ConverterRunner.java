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

package org.yeastrc.limelight.xml.philosopher.main;

import org.yeastrc.limelight.xml.philosopher.builder.XMLBuilder;
import org.yeastrc.limelight.xml.philosopher.objects.*;
import org.yeastrc.limelight.xml.philosopher.reader.ParamsReader;
import org.yeastrc.limelight.xml.philosopher.reader.ResultsParser;

public class ConverterRunner {

	// quickly get a new instance of this class
	public static ConverterRunner createInstance() { return new ConverterRunner(); }
	
	
	public void convertPhilosopherTSVToLimelightXML(ConversionParameters conversionParameters ) throws Throwable {

		System.err.print( "Determining search program..." );
		final String searchProgram = ParamsReader.getSearchProgram(conversionParameters.getParamsFile());
		System.err.println( " Got: " + searchProgram );

		System.err.print( "Reading conf file into memory..." );
		SearchParameters searchParams = ParamsReader.getSearchParameters(conversionParameters.getParamsFile(), searchProgram );
		System.err.println( " Done." );
		
		System.err.print( "Reading search results (psm.tsv) into memory..." );
		PhilosopherResults philosopherResults = ResultsParser.getResults( conversionParameters.getMsfraggerTSVFile(), searchParams, conversionParameters.isOpenMod() );
		System.err.println( " Done." );

		System.err.print( "Writing out XML..." );
		(new XMLBuilder()).buildAndSaveXML( conversionParameters, philosopherResults, searchParams );
		System.err.println( " Done." );

		System.err.print( "Validating Limelight XML..." );
		LimelightXMLValidator.validateLimelightXML(conversionParameters.getLimelightXMLOutputFile());
		System.err.println( " Done." );
		
	}
}
