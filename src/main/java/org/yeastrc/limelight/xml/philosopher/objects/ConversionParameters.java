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

package org.yeastrc.limelight.xml.philosopher.objects;

import java.io.File;

public class ConversionParameters {
		
	/**
	 * @return the fastaFile
	 */
	public File getFastaFile() {
		return fastaFile;
	}
	/**
	 * @param fastaFile the fastaFile to set
	 */
	public void setFastaFile(File fastaFile) {
		this.fastaFile = fastaFile;
	}
	/**
	 * @return the msFraggerConfFile
	 */
	public File getParamsFile() {
		return paramsFile;
	}
	/**
	 * @param paramsFile the msFraggerConfFile to set
	 */
	public void setParamsFile(File paramsFile) {
		this.paramsFile = paramsFile;
	}
	/**
	 * @return the limelightXMLOutputFile
	 */
	public File getLimelightXMLOutputFile() {
		return limelightXMLOutputFile;
	}
	/**
	 * @param limelightXMLOutputFile the limelightXMLOutputFile to set
	 */
	public void setLimelightXMLOutputFile(File limelightXMLOutputFile) {
		this.limelightXMLOutputFile = limelightXMLOutputFile;
	}
	/**
	 * @return the conversionProgramInfo
	 */
	public ConversionProgramInfo getConversionProgramInfo() {
		return conversionProgramInfo;
	}
	/**
	 * @param conversionProgramInfo the conversionProgramInfo to set
	 */
	public void setConversionProgramInfo(ConversionProgramInfo conversionProgramInfo) {
		this.conversionProgramInfo = conversionProgramInfo;
	}

	public boolean isOpenMod() {
		return isOpenMod;
	}

	public void setOpenMod(boolean openMod) {
		isOpenMod = openMod;
	}

	public File getMsfraggerTSVFile() {
		return msfraggerTSVFile;
	}

	public void setMsfraggerTSVFile(File msfraggerTSVFile) {
		this.msfraggerTSVFile = msfraggerTSVFile;
	}

	public String getDecoyPrefix() {
		return decoyPrefix;
	}

	public void setDecoyPrefix(String decoyPrefix) {
		this.decoyPrefix = decoyPrefix;
	}

	private File fastaFile;
	private File paramsFile;
	private File limelightXMLOutputFile;
	private ConversionProgramInfo conversionProgramInfo;
	private boolean isOpenMod;
	private File msfraggerTSVFile;
	private String decoyPrefix;
}
