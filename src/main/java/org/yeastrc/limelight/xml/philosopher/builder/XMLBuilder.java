package org.yeastrc.limelight.xml.philosopher.builder;

import org.yeastrc.limelight.limelight_import.api.xml_dto.*;
import org.yeastrc.limelight.limelight_import.api.xml_dto.ReportedPeptide.ReportedPeptideAnnotations;
import org.yeastrc.limelight.limelight_import.api.xml_dto.SearchProgram.PsmAnnotationTypes;
import org.yeastrc.limelight.limelight_import.create_import_file_from_java_objects.main.CreateImportFileFromJavaObjectsMain;
import org.yeastrc.limelight.xml.philosopher.annotation.PSMAnnotationTypeSortOrder;
import org.yeastrc.limelight.xml.philosopher.annotation.PSMAnnotationTypes;
import org.yeastrc.limelight.xml.philosopher.annotation.PSMDefaultVisibleAnnotationTypes;
import org.yeastrc.limelight.xml.philosopher.constants.Constants;
import org.yeastrc.limelight.xml.philosopher.objects.*;
import org.yeastrc.limelight.xml.philosopher.utils.MassUtils;
import org.yeastrc.limelight.xml.philosopher.utils.ReportedPeptideUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.text.DecimalFormat;

public class XMLBuilder {

	public void buildAndSaveXML( ConversionParameters conversionParameters,
			                     PhilosopherResults philosopherResults,
			                     SearchParameters searchParameters,
								 String searchProgramName)
    throws Exception {

		LimelightInput limelightInputRoot = new LimelightInput();

		limelightInputRoot.setFastaFilename( conversionParameters.getFastaFile().getName() );
		
		// add in the conversion program (this program) information
		ConversionProgramBuilder.createInstance().buildConversionProgramSection( limelightInputRoot, conversionParameters);
		
		SearchProgramInfo searchProgramInfo = new SearchProgramInfo();
		limelightInputRoot.setSearchProgramInfo( searchProgramInfo );
		
		SearchPrograms searchPrograms = new SearchPrograms();
		searchProgramInfo.setSearchPrograms( searchPrograms );

		{
			SearchProgram searchProgram = new SearchProgram();
			searchPrograms.getSearchProgram().add( searchProgram );

			searchProgram.setName( Constants.PROGRAM_NAME_PHILOSOPHER);
			searchProgram.setDisplayName( Constants.PROGRAM_NAME_PHILOSOPHER );
			searchProgram.setVersion( "Unknown" );
		}

		{
			SearchProgram searchProgram = new SearchProgram();
			searchPrograms.getSearchProgram().add( searchProgram );
				
			searchProgram.setName( Constants.PROGRAM_NAME_PEPTIDEPROPHET );
			searchProgram.setDisplayName( Constants.PROGRAM_NAME_PEPTIDEPROPHET );
			searchProgram.setVersion( "Unknown" );
			
			//
			// Define the annotation types present in magnum data
			//
			PsmAnnotationTypes psmAnnotationTypes = new PsmAnnotationTypes();
			searchProgram.setPsmAnnotationTypes( psmAnnotationTypes );
			
			FilterablePsmAnnotationTypes filterablePsmAnnotationTypes = new FilterablePsmAnnotationTypes();
			psmAnnotationTypes.setFilterablePsmAnnotationTypes( filterablePsmAnnotationTypes );
			
			for( FilterablePsmAnnotationType annoType : PSMAnnotationTypes.getFilterablePsmAnnotationTypes( Constants.PROGRAM_NAME_PEPTIDEPROPHET ) ) {
				filterablePsmAnnotationTypes.getFilterablePsmAnnotationType().add( annoType );
			}
		}

		if(searchProgramName.equals(Constants.PROGRAM_NAME_MSFRAGGER)) {
			SearchProgram searchProgram = new SearchProgram();
			searchPrograms.getSearchProgram().add( searchProgram );

			searchProgram.setName( Constants.PROGRAM_NAME_MSFRAGGER );
			searchProgram.setDisplayName( Constants.PROGRAM_NAME_MSFRAGGER );
			searchProgram.setVersion( "Unknown" );

			//
			// Define the annotation types present in magnum data
			//
			PsmAnnotationTypes psmAnnotationTypes = new PsmAnnotationTypes();
			searchProgram.setPsmAnnotationTypes( psmAnnotationTypes );

			FilterablePsmAnnotationTypes filterablePsmAnnotationTypes = new FilterablePsmAnnotationTypes();
			psmAnnotationTypes.setFilterablePsmAnnotationTypes( filterablePsmAnnotationTypes );

			for( FilterablePsmAnnotationType annoType : PSMAnnotationTypes.getFilterablePsmAnnotationTypes( Constants.PROGRAM_NAME_MSFRAGGER ) ) {
				filterablePsmAnnotationTypes.getFilterablePsmAnnotationType().add( annoType );
			}
		} else if(searchProgramName.equals(Constants.PROGRAM_NAME_COMET)) {
			SearchProgram searchProgram = new SearchProgram();
			searchPrograms.getSearchProgram().add( searchProgram );

			searchProgram.setName( Constants.PROGRAM_NAME_COMET );
			searchProgram.setDisplayName( Constants.PROGRAM_NAME_COMET );
			searchProgram.setVersion( "Unknown" );

			//
			// Define the annotation types present in magnum data
			//
			PsmAnnotationTypes psmAnnotationTypes = new PsmAnnotationTypes();
			searchProgram.setPsmAnnotationTypes( psmAnnotationTypes );

			FilterablePsmAnnotationTypes filterablePsmAnnotationTypes = new FilterablePsmAnnotationTypes();
			psmAnnotationTypes.setFilterablePsmAnnotationTypes( filterablePsmAnnotationTypes );

			for( FilterablePsmAnnotationType annoType : PSMAnnotationTypes.getFilterablePsmAnnotationTypes( Constants.PROGRAM_NAME_COMET ) ) {
				filterablePsmAnnotationTypes.getFilterablePsmAnnotationType().add( annoType );
			}
		}
		
		//
		// Define which annotation types are visible by default
		//
		DefaultVisibleAnnotations xmlDefaultVisibleAnnotations = new DefaultVisibleAnnotations();
		searchProgramInfo.setDefaultVisibleAnnotations( xmlDefaultVisibleAnnotations );
		
		VisiblePsmAnnotations xmlVisiblePsmAnnotations = new VisiblePsmAnnotations();
		xmlDefaultVisibleAnnotations.setVisiblePsmAnnotations( xmlVisiblePsmAnnotations );

		for( SearchAnnotation sa : PSMDefaultVisibleAnnotationTypes.getDefaultVisibleAnnotationTypes(searchProgramName) ) {
			xmlVisiblePsmAnnotations.getSearchAnnotation().add( sa );
		}
		
		//
		// Define the default display order in limelight
		//
		AnnotationSortOrder xmlAnnotationSortOrder = new AnnotationSortOrder();
		searchProgramInfo.setAnnotationSortOrder( xmlAnnotationSortOrder );
		
		PsmAnnotationSortOrder xmlPsmAnnotationSortOrder = new PsmAnnotationSortOrder();
		xmlAnnotationSortOrder.setPsmAnnotationSortOrder( xmlPsmAnnotationSortOrder );
		
		for( SearchAnnotation xmlSearchAnnotation : PSMAnnotationTypeSortOrder.getPSMAnnotationTypeSortOrder(searchProgramName) ) {
			xmlPsmAnnotationSortOrder.getSearchAnnotation().add( xmlSearchAnnotation );
		}
		
		//
		// Define the static mods
		//
		if( searchParameters.getStaticMods() != null && searchParameters.getStaticMods().keySet().size() > 0 ) {
			StaticModifications smods = new StaticModifications();
			limelightInputRoot.setStaticModifications( smods );
			
			
			for( String residue : searchParameters.getStaticMods().keySet() ) {
				
				StaticModification xmlSmod = new StaticModification();
				xmlSmod.setAminoAcid( String.valueOf( residue ) );
				xmlSmod.setMassChange( searchParameters.getStaticMods().get( residue ) );
				
				smods.getStaticModification().add( xmlSmod );
			}
		}

		//
		// Define the peptide and PSM data
		//
		ReportedPeptides reportedPeptides = new ReportedPeptides();
		limelightInputRoot.setReportedPeptides( reportedPeptides );
		
		// iterate over each distinct reported peptide
		for( PhilosopherReportedPeptide philosopherReportedPeptide : philosopherResults.getPeptidePSMMap().keySet() ) {

			// skip this reported peptide if it only contains decoys
			if(ReportedPeptideUtils.reportedPeptideOnlyContainsDecoys(philosopherResults, philosopherReportedPeptide) ) {
				continue;
			}

			ReportedPeptide xmlReportedPeptide = new ReportedPeptide();
			reportedPeptides.getReportedPeptide().add( xmlReportedPeptide );
			
			xmlReportedPeptide.setReportedPeptideString( philosopherReportedPeptide.getReportedPeptideString() );
			xmlReportedPeptide.setSequence( philosopherReportedPeptide.getNakedPeptide() );
			
			// add in the filterable peptide annotations (e.g., q-value)
			ReportedPeptideAnnotations xmlReportedPeptideAnnotations = new ReportedPeptideAnnotations();
			xmlReportedPeptide.setReportedPeptideAnnotations( xmlReportedPeptideAnnotations );

			// add in the mods for this peptide
			if( philosopherReportedPeptide.getMods() != null && philosopherReportedPeptide.getMods().keySet().size() > 0 ) {

				PeptideModifications xmlModifications = new PeptideModifications();
				xmlReportedPeptide.setPeptideModifications( xmlModifications );

				for( int position : philosopherReportedPeptide.getMods().keySet() ) {
					PeptideModification xmlModification = new PeptideModification();
					xmlModifications.getPeptideModification().add( xmlModification );

					xmlModification.setMass( philosopherReportedPeptide.getMods().get( position ).stripTrailingZeros().setScale( 4, RoundingMode.HALF_UP ) );

					if(position == 0)
						xmlModification.setIsNTerminal(true);

					else if(position == philosopherReportedPeptide.getNakedPeptide().length())
						xmlModification.setIsCTerminal(true);

					else
						xmlModification.setPosition( new BigInteger( String.valueOf( position ) ) );

				}
			}

			
			// add in the PSMs and annotations
			Psms xmlPsms = new Psms();
			xmlReportedPeptide.setPsms( xmlPsms );

			// iterate over all PSMs for this reported peptide

			for( int scanNumber : philosopherResults.getPeptidePSMMap().get(philosopherReportedPeptide).keySet() ) {

				PhilosopherPSM psm = philosopherResults.getPeptidePSMMap().get(philosopherReportedPeptide).get( scanNumber );

				if(psm.isDecoy()) {
					continue;
				}

				Psm xmlPsm = new Psm();
				xmlPsms.getPsm().add( xmlPsm );

				xmlPsm.setScanNumber( new BigInteger( String.valueOf( scanNumber ) ) );
				xmlPsm.setPrecursorCharge( new BigInteger( String.valueOf( psm.getCharge() ) ) );
				xmlPsm.setPrecursorMZ(psm.getPrecursorMZ());
				xmlPsm.setPrecursorRetentionTime(psm.getRetentionTime());

				// add in the filterable PSM annotations (e.g., score)
				FilterablePsmAnnotations xmlFilterablePsmAnnotations = new FilterablePsmAnnotations();
				xmlPsm.setFilterablePsmAnnotations( xmlFilterablePsmAnnotations );

				// handle peptideprophet
				{
					FilterablePsmAnnotation xmlFilterablePsmAnnotation = new FilterablePsmAnnotation();
					xmlFilterablePsmAnnotations.getFilterablePsmAnnotation().add( xmlFilterablePsmAnnotation );

					xmlFilterablePsmAnnotation.setAnnotationName( PSMAnnotationTypes.PEPTIDEPROPHET_ANNOTATION_TYPE_PROBABILITY );
					xmlFilterablePsmAnnotation.setSearchProgram( Constants.PROGRAM_NAME_PEPTIDEPROPHET );

					xmlFilterablePsmAnnotation.setValue( psm.getPeptideProphetProbability() );
				}

				if(searchProgramName.equals(Constants.PROGRAM_NAME_MSFRAGGER)) {
					// handle msfragger scores

					{
						FilterablePsmAnnotation xmlFilterablePsmAnnotation = new FilterablePsmAnnotation();
						xmlFilterablePsmAnnotations.getFilterablePsmAnnotation().add(xmlFilterablePsmAnnotation);

						xmlFilterablePsmAnnotation.setAnnotationName(PSMAnnotationTypes.MSFRAGGER_ANNOTATION_TYPE_EVALUE);
						xmlFilterablePsmAnnotation.setSearchProgram(Constants.PROGRAM_NAME_MSFRAGGER);
						xmlFilterablePsmAnnotation.setValue(((MSFraggerPSM)psm).getExpectationValue());
					}

					{
						FilterablePsmAnnotation xmlFilterablePsmAnnotation = new FilterablePsmAnnotation();
						xmlFilterablePsmAnnotations.getFilterablePsmAnnotation().add(xmlFilterablePsmAnnotation);

						xmlFilterablePsmAnnotation.setAnnotationName(PSMAnnotationTypes.MSFRAGGER_ANNOTATION_TYPE_HYPERSCORE);
						xmlFilterablePsmAnnotation.setSearchProgram(Constants.PROGRAM_NAME_MSFRAGGER);
						xmlFilterablePsmAnnotation.setValue(((MSFraggerPSM)psm).getHyperscore());
					}

					{
						FilterablePsmAnnotation xmlFilterablePsmAnnotation = new FilterablePsmAnnotation();
						xmlFilterablePsmAnnotations.getFilterablePsmAnnotation().add(xmlFilterablePsmAnnotation);

						xmlFilterablePsmAnnotation.setAnnotationName(PSMAnnotationTypes.MSFRAGGER_ANNOTATION_TYPE_NEXTSCORE);
						xmlFilterablePsmAnnotation.setSearchProgram(Constants.PROGRAM_NAME_MSFRAGGER);
						xmlFilterablePsmAnnotation.setValue(((MSFraggerPSM)psm).getNextscore());
					}

					{
						FilterablePsmAnnotation xmlFilterablePsmAnnotation = new FilterablePsmAnnotation();
						xmlFilterablePsmAnnotations.getFilterablePsmAnnotation().add(xmlFilterablePsmAnnotation);

						xmlFilterablePsmAnnotation.setAnnotationName(PSMAnnotationTypes.PHILOSOPHER_ANNOTATION_TYPE_DELTAMASS);
						xmlFilterablePsmAnnotation.setSearchProgram(Constants.PROGRAM_NAME_MSFRAGGER);
						xmlFilterablePsmAnnotation.setValue(psm.getMassDiff());
					}

				} else if(searchProgramName.equals(Constants.PROGRAM_NAME_COMET)) {
					// handle comet scores

					{
						FilterablePsmAnnotation xmlFilterablePsmAnnotation = new FilterablePsmAnnotation();
						xmlFilterablePsmAnnotations.getFilterablePsmAnnotation().add(xmlFilterablePsmAnnotation);

						xmlFilterablePsmAnnotation.setAnnotationName(PSMAnnotationTypes.COMET_ANNOTATION_TYPE_EVALUE);
						xmlFilterablePsmAnnotation.setSearchProgram(Constants.PROGRAM_NAME_COMET);
						xmlFilterablePsmAnnotation.setValue(((CometPSM)psm).getExpectationValue());
					}

					{
						FilterablePsmAnnotation xmlFilterablePsmAnnotation = new FilterablePsmAnnotation();
						xmlFilterablePsmAnnotations.getFilterablePsmAnnotation().add(xmlFilterablePsmAnnotation);

						xmlFilterablePsmAnnotation.setAnnotationName(PSMAnnotationTypes.COMET_ANNOTATION_TYPE_XCORR);
						xmlFilterablePsmAnnotation.setSearchProgram(Constants.PROGRAM_NAME_COMET);
						xmlFilterablePsmAnnotation.setValue(((CometPSM)psm).getXCorr());
					}

					{
						FilterablePsmAnnotation xmlFilterablePsmAnnotation = new FilterablePsmAnnotation();
						xmlFilterablePsmAnnotations.getFilterablePsmAnnotation().add(xmlFilterablePsmAnnotation);

						xmlFilterablePsmAnnotation.setAnnotationName(PSMAnnotationTypes.COMET_ANNOTATION_TYPE_DELTACN);
						xmlFilterablePsmAnnotation.setSearchProgram(Constants.PROGRAM_NAME_COMET);
						xmlFilterablePsmAnnotation.setValue(((CometPSM)psm).getDeltaCN());
					}

					{
						FilterablePsmAnnotation xmlFilterablePsmAnnotation = new FilterablePsmAnnotation();
						xmlFilterablePsmAnnotations.getFilterablePsmAnnotation().add(xmlFilterablePsmAnnotation);

						xmlFilterablePsmAnnotation.setAnnotationName(PSMAnnotationTypes.COMET_ANNOTATION_TYPE_SPRANK);
						xmlFilterablePsmAnnotation.setSearchProgram(Constants.PROGRAM_NAME_COMET);
						xmlFilterablePsmAnnotation.setValue(BigDecimal.valueOf(((CometPSM)psm).getSpRank()));
					}

					{
						FilterablePsmAnnotation xmlFilterablePsmAnnotation = new FilterablePsmAnnotation();
						xmlFilterablePsmAnnotations.getFilterablePsmAnnotation().add(xmlFilterablePsmAnnotation);

						xmlFilterablePsmAnnotation.setAnnotationName(PSMAnnotationTypes.PHILOSOPHER_ANNOTATION_TYPE_DELTAMASS);
						xmlFilterablePsmAnnotation.setSearchProgram(Constants.PROGRAM_NAME_COMET);
						xmlFilterablePsmAnnotation.setValue(psm.getMassDiff());
					}

				}

				// add in the open mod mass if this is an open mod search
				if(conversionParameters.isOpenMod()) {
					PsmOpenModification xmlPsmOpenMod = new PsmOpenModification();
					xmlPsmOpenMod.setMass(psm.getOpenModification().getMass());
					xmlPsm.setPsmOpenModification(xmlPsmOpenMod);

					if(psm.getOpenModification().getPositions() != null && psm.getOpenModification().getPositions().size() > 0) {
						for(int position : psm.getOpenModification().getPositions()) {
							PsmOpenModificationPosition xmlPsmOpenModPosition = new PsmOpenModificationPosition();
							xmlPsmOpenModPosition.setPosition(BigInteger.valueOf(position));
							xmlPsmOpenMod.getPsmOpenModificationPosition().add(xmlPsmOpenModPosition);
						}
					}
				}
				
				
			}// end iterating over psms for a reported peptide
		
		}//end iterating over reported peptides


		
		
		// add in the matched proteins section
		MatchedProteinsBuilder.getInstance().buildMatchedProteins(
				                                                   limelightInputRoot,
				                                                   conversionParameters.getFastaFile(),
				                                                   philosopherResults.getPeptidePSMMap().keySet()
				                                                  );
		
		
		// add in the config file(s)
		ConfigurationFiles xmlConfigurationFiles = new ConfigurationFiles();
		limelightInputRoot.setConfigurationFiles( xmlConfigurationFiles );
		
		ConfigurationFile xmlConfigurationFile = new ConfigurationFile();
		xmlConfigurationFiles.getConfigurationFile().add( xmlConfigurationFile );
		
		xmlConfigurationFile.setSearchProgram( searchProgramName );
		xmlConfigurationFile.setFileName( conversionParameters.getParamsFile().getName() );
		xmlConfigurationFile.setFileContent( Files.readAllBytes( FileSystems.getDefault().getPath( conversionParameters.getParamsFile().getAbsolutePath() ) ) );

		//make the xml file
		CreateImportFileFromJavaObjectsMain.getInstance().createImportFileFromJavaObjectsMain( conversionParameters.getLimelightXMLOutputFile(), limelightInputRoot);
		
	}
	
	
}
