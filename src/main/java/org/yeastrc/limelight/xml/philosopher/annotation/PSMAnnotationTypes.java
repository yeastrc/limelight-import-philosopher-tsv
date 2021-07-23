package org.yeastrc.limelight.xml.philosopher.annotation;

import org.yeastrc.limelight.limelight_import.api.xml_dto.FilterDirectionType;
import org.yeastrc.limelight.limelight_import.api.xml_dto.FilterablePsmAnnotationType;
import org.yeastrc.limelight.xml.philosopher.constants.Constants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class PSMAnnotationTypes {

	public static final String PEPTIDEPROPHET_ANNOTATION_TYPE_PROBABILITY = "PeptideProphet Probability";
	public static final String PHILOSOPHER_ANNOTATION_TYPE_DELTAMASS = "Delta Mass";

	public static final String MSFRAGGER_ANNOTATION_TYPE_EVALUE = "Expectation";
	public static final String MSFRAGGER_ANNOTATION_TYPE_HYPERSCORE = "Hyperscore";
	public static final String MSFRAGGER_ANNOTATION_TYPE_NEXTSCORE = "Nextscore";

	public static final String COMET_ANNOTATION_TYPE_EVALUE = "Expectation";
	public static final String COMET_ANNOTATION_TYPE_XCORR = "XCorr";
	public static final String COMET_ANNOTATION_TYPE_DELTACN = "DeltaCN";
	public static final String COMET_ANNOTATION_TYPE_SPRANK = "SPRank";

	public static List<FilterablePsmAnnotationType> getFilterablePsmAnnotationTypes( String programName ) {
		List<FilterablePsmAnnotationType> types = new ArrayList<FilterablePsmAnnotationType>();

		if( programName.equals( Constants.PROGRAM_NAME_MSFRAGGER ) ) {

			{
				FilterablePsmAnnotationType type = new FilterablePsmAnnotationType();
				type.setName( MSFRAGGER_ANNOTATION_TYPE_EVALUE );
				type.setDescription( "MSFragger expect value" );
				type.setFilterDirection( FilterDirectionType.BELOW );

				types.add( type );
			}

			{
				FilterablePsmAnnotationType type = new FilterablePsmAnnotationType();
				type.setName( MSFRAGGER_ANNOTATION_TYPE_HYPERSCORE );
				type.setDescription( "Similar to scoring done in X! Tandem. See: https://www.ncbi.nlm.nih.gov/pmc/articles/PMC5409104/" );
				type.setFilterDirection( FilterDirectionType.ABOVE );

				types.add( type );
			}

			{
				FilterablePsmAnnotationType type = new FilterablePsmAnnotationType();
				type.setName( MSFRAGGER_ANNOTATION_TYPE_NEXTSCORE );
				type.setDescription( "Difference in hyperscore between top and next hit." );
				type.setFilterDirection( FilterDirectionType.ABOVE );

				types.add( type );
			}

			{
				FilterablePsmAnnotationType type = new FilterablePsmAnnotationType();
				type.setName( PHILOSOPHER_ANNOTATION_TYPE_DELTAMASS );
				type.setDescription( "Difference between expected and observed mass for peptide, as calculated by " + programName );
				type.setFilterDirection( FilterDirectionType.BELOW );

				types.add( type );
			}

		} else if( programName.equals( Constants.PROGRAM_NAME_COMET)) {

			{
				FilterablePsmAnnotationType type = new FilterablePsmAnnotationType();
				type.setName( COMET_ANNOTATION_TYPE_EVALUE );
				type.setDescription( "Comet expect value" );
				type.setFilterDirection( FilterDirectionType.BELOW );

				types.add( type );
			}

			{
				FilterablePsmAnnotationType type = new FilterablePsmAnnotationType();
				type.setName( COMET_ANNOTATION_TYPE_XCORR );
				type.setDescription( "Comet cross-correlation coefficient" );
				type.setFilterDirection( FilterDirectionType.ABOVE );

				types.add( type );
			}

			{
				FilterablePsmAnnotationType type = new FilterablePsmAnnotationType();
				type.setName( COMET_ANNOTATION_TYPE_DELTACN );
				type.setDescription( "Difference between this PSM XCorr and the next candidate XCorr" );
				type.setFilterDirection( FilterDirectionType.ABOVE );

				types.add( type );
			}

			{
				FilterablePsmAnnotationType type = new FilterablePsmAnnotationType();
				type.setName( COMET_ANNOTATION_TYPE_SPRANK );
				type.setDescription( "Rank of this PSMs Sp score among candidate peptides." );
				type.setFilterDirection( FilterDirectionType.BELOW );

				types.add( type );
			}

			{
				FilterablePsmAnnotationType type = new FilterablePsmAnnotationType();
				type.setName( PHILOSOPHER_ANNOTATION_TYPE_DELTAMASS );
				type.setDescription( "Difference between expected and observed mass for peptide, as calculated by " + programName );
				type.setFilterDirection( FilterDirectionType.BELOW );

				types.add( type );
			}

		} else if( programName.equals( Constants.PROGRAM_NAME_PEPTIDEPROPHET)) {

			{
				FilterablePsmAnnotationType type = new FilterablePsmAnnotationType();
				type.setName( PEPTIDEPROPHET_ANNOTATION_TYPE_PROBABILITY );
				type.setDescription( "Probability from PeptideProphet" );
				type.setFilterDirection( FilterDirectionType.ABOVE );

				types.add( type );
			}

		}

		return types;
	}
	
	
}
