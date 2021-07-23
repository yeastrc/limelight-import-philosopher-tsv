package org.yeastrc.limelight.xml.philosopher.reader;

import org.yeastrc.limelight.xml.philosopher.objects.SearchParameters;

import java.io.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CometParamsReader {

	/**
	 * Get the relevant parameters from the magnum params file
	 * 
	 * @param paramsFile
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static SearchParameters getCometParameters(File paramsFile ) throws FileNotFoundException, IOException {
		
		SearchParameters magParams = new SearchParameters();
		
		try ( InputStream is = new FileInputStream( paramsFile ) ) {
			magParams.setStaticMods( getStaticModsFromParamsFile( is ) );
		}

		try ( InputStream is = new FileInputStream( paramsFile ) ) {
			magParams.setDecoyPrefix( getDecoyPrefix( is ) );
		}

		return magParams;
	}
	
	public static Map<String, BigDecimal> getStaticModsFromParamsFile(InputStream paramsInputStream ) throws IOException {
		
		Map<String, BigDecimal> staticMods = new HashMap<>();
		
		Pattern p = Pattern.compile( "^add_(\\w+)_.+=\\s+([0-9]+(\\.[0-9]+)?).*$" );
		
	    try (BufferedReader br = new BufferedReader( new InputStreamReader( paramsInputStream ) ) ) {
	    	
			for ( String line = br.readLine(); line != null; line = br.readLine() ) {		
				

				// skip immediately if it's not a line we want
				if( !line.startsWith( "add_" ) )
						continue;
				
				Matcher m = p.matcher( line );
				if( m.matches() ) {
					double d = Double.valueOf( m.group( 2 ) );
					
					if( d >= 0.000001 )
						staticMods.put( m.group( 1 ), new BigDecimal( m.group( 2 ) ) );
				}
			}
	    	
	    }
		
		return staticMods;
	}

	public static String getDecoyPrefix( InputStream paramsInputStream) throws IOException {

		try (BufferedReader br = new BufferedReader( new InputStreamReader( paramsInputStream ) ) ) {

			for ( String line = br.readLine(); line != null; line = br.readLine() ) {

				// skip immediately if it's not a line we want
				if( !line.startsWith( "decoy_prefix" ) )
					continue;

				Pattern p = Pattern.compile( "^decoy_prefix\\s+=\\s+(\\S+).*$" );

				Matcher m = p.matcher( line );
				if( m.matches() ) {
					return m.group( 1 );
				}

			}

		}

		return null;
	}

	
}
