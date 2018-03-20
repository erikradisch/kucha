/*
 * Copyright 2017 - 2018
 * Saxon Academy of Science in Leipzig, Germany
 * 
 * This is free software: you can redistribute it and/or modify it under the terms of the 
 * GNU General Public License version 3 (GPL v3) as published by the Free Software Foundation.
 * 
 * This software is distributed WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. Please read the GPL v3 for more details.
 * 
 * You should have received a copy of the GPL v3 along with the software. 
 * If not, you can access it from here: <https://www.gnu.org/licenses/gpl-3.0.txt>.
 */
package de.cses.shared;

/**
 * @author Nina
 *
 */
public class PublicationTypeEntry extends AbstractEntry {
	

	int publicationTypeID = 0;
	String name;
	boolean accessDateEnabled = false;
	boolean authorEnabled = false;
	boolean bookTitleEnabled = false;
	boolean conferenceNameEnabled = false;
	boolean editionEnabled = false;
	boolean editorEnabled = false;
	boolean monthEnabled = false;
	boolean numberEnabled = false;
	boolean pagesEnabled = false;
	boolean proceedingsTitleEnabled = false;
	boolean seriesEnabled = false;
	boolean titleAddonEnabled = false;
	boolean universityEnabled = false;
	boolean volumeEnabled = false;
	boolean issueEnabled = false;
	boolean yearEnabled = false;
	
	/**
	 * @return the publicationTypeID
	 */
	
	public PublicationTypeEntry() {	}

	public PublicationTypeEntry(int publicationTypeID, String name, boolean accessDateEnabled, boolean authorEnabled, boolean bookTitleEnabled, boolean conferenceNameEnabled,
			boolean editionEnabled, boolean editorEnabled, boolean monthEnabled, boolean numberEnabled, boolean pagesEnabled,
			boolean proceedingsTitleEnabled, boolean seriesEnabled, boolean titleAddonEnabled, boolean universityEnabled, boolean volumeEnabled, boolean issueEnabled,
			boolean yearEnabled) {
		super();
		this.publicationTypeID = publicationTypeID;
		this.name = name;
		this.accessDateEnabled = accessDateEnabled;
		this.authorEnabled = authorEnabled;
		this.bookTitleEnabled = bookTitleEnabled;
		this.conferenceNameEnabled = conferenceNameEnabled;
		this.editionEnabled = editionEnabled;
		this.editorEnabled = editorEnabled;
		this.monthEnabled = monthEnabled;
		this.numberEnabled = numberEnabled;
		this.pagesEnabled = pagesEnabled;
		this.proceedingsTitleEnabled = proceedingsTitleEnabled;
		this.seriesEnabled = seriesEnabled;
		this.titleAddonEnabled = titleAddonEnabled;
		this.universityEnabled = universityEnabled;
		this.volumeEnabled = volumeEnabled;
		this.issueEnabled = issueEnabled;
		this.yearEnabled = yearEnabled;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/* (non-Javadoc)
	 * @see de.cses.shared.AbstractEntry#getUniqueID()
	 */
	@Override
	public String getUniqueID() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * @return the publicationTypeID
	 */
	public int getPublicationTypeID() {
		return publicationTypeID;
	}
	/**
	 * @param publicationTypeID the publicationTypeID to set
	 */
	public void setPublicationTypeID(int publicationTypeID) {
		this.publicationTypeID = publicationTypeID;
	}

	public boolean isAccessDateEnabled() {
		return accessDateEnabled;
	}

	public void setAccessDateEnabled(boolean accessDateEnabled) {
		this.accessDateEnabled = accessDateEnabled;
	}

	public boolean isBookTitleEnabled() {
		return bookTitleEnabled;
	}

	public void setBookTitleEnabled(boolean bookTitleEnabled) {
		this.bookTitleEnabled = bookTitleEnabled;
	}

	public boolean isEditionEnabled() {
		return editionEnabled;
	}

	public void setEditionEnabled(boolean editionEnabled) {
		this.editionEnabled = editionEnabled;
	}

	public boolean isMonthEnabled() {
		return monthEnabled;
	}

	public void setMonthEnabled(boolean monthEnabled) {
		this.monthEnabled = monthEnabled;
	}

	public boolean isNumberEnabled() {
		return numberEnabled;
	}

	public void setNumberEnabled(boolean numberEnabled) {
		this.numberEnabled = numberEnabled;
	}

	public boolean isPagesEnabled() {
		return pagesEnabled;
	}

	public void setPagesEnabled(boolean pagesEnabled) {
		this.pagesEnabled = pagesEnabled;
	}

	public boolean isProceedingsTitleEnabled() {
		return proceedingsTitleEnabled;
	}

	public void setProceedingsTitleEnabled(boolean proceedingsTitleEnabled) {
		this.proceedingsTitleEnabled = proceedingsTitleEnabled;
	}

	public boolean isSeriesEnabled() {
		return seriesEnabled;
	}

	public void setSeriesEnabled(boolean seriesEnabled) {
		this.seriesEnabled = seriesEnabled;
	}

	public boolean isTitleAddonEnabled() {
		return titleAddonEnabled;
	}

	public void setTitleAddonEnabled(boolean titleAddonEnabled) {
		this.titleAddonEnabled = titleAddonEnabled;
	}

	public boolean isUniversityEnabled() {
		return universityEnabled;
	}

	public void setUniversityEnabled(boolean universityEnabled) {
		this.universityEnabled = universityEnabled;
	}

	public boolean isVolumeEnabled() {
		return volumeEnabled;
	}

	public void setVolumeEnabled(boolean volumeEnabled) {
		this.volumeEnabled = volumeEnabled;
	}

	public boolean isYearEnabled() {
		return yearEnabled;
	}

	public void setYearEnabled(boolean yearEnabled) {
		this.yearEnabled = yearEnabled;
	}

	public boolean isAuthorEnabled() {
		return authorEnabled;
	}

	public void setAuthorEnabled(boolean authorEnabled) {
		this.authorEnabled = authorEnabled;
	}

	public boolean isEditorEnabled() {
		return editorEnabled;
	}

	public void setEditorEnabled(boolean editorEnabled) {
		this.editorEnabled = editorEnabled;
	}

	public boolean isIssueEnabled() {
		return issueEnabled;
	}

	public void setIssueEnabled(boolean issueEnabled) {
		this.issueEnabled = issueEnabled;
	}

	public boolean isConferenceNameEnabled() {
		return conferenceNameEnabled;
	}

	public void setConferenceNameEnabled(boolean conferenceNameEnabled) {
		this.conferenceNameEnabled = conferenceNameEnabled;
	}
	
	
	

}
