package org.yeastrc.limelight.xml.philosopher.annotation;

import org.yeastrc.limelight.limelight_import.api.xml_dto.SearchAnnotation;
import org.yeastrc.limelight.xml.philosopher.constants.Constants;

import java.util.ArrayList;
import java.util.List;

public class PSMAnnotationTypeSortOrder {

	public static List<SearchAnnotation> getPSMAnnotationTypeSortOrder( String programName ) {
		List<SearchAnnotation> annotations = new ArrayList<SearchAnnotation>();

		{
			SearchAnnotation annotation = new SearchAnnotation();
			annotation.setAnnotationName( PSMAnnotationTypes.PEPTIDEPROPHET_ANNOTATION_TYPE_PROBABILITY );
			annotation.setSearchProgram( Constants.PROGRAM_NAME_PEPTIDEPROPHET );
			annotations.add( annotation );
		}

		if(programName.equals(Constants.PROGRAM_NAME_COMET)) {

			{
				SearchAnnotation annotation = new SearchAnnotation();
				annotation.setAnnotationName(PSMAnnotationTypes.COMET_ANNOTATION_TYPE_EVALUE);
				annotation.setSearchProgram(Constants.PROGRAM_NAME_COMET);
				annotations.add(annotation);
			}

		}

		if(programName.equals(Constants.PROGRAM_NAME_MSFRAGGER)) {

			{
				SearchAnnotation annotation = new SearchAnnotation();
				annotation.setAnnotationName(PSMAnnotationTypes.MSFRAGGER_ANNOTATION_TYPE_EVALUE);
				annotation.setSearchProgram(Constants.PROGRAM_NAME_MSFRAGGER);
				annotations.add(annotation);
			}

		}
		
		return annotations;
	}
}
