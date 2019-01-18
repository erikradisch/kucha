package de.cses.shared;

import java.util.ArrayList;

import com.google.gwt.user.client.Cookies;

import de.cses.client.user.UserLogin;

public class ImageSearchEntry extends AbstractSearchEntry {
	
	private String titleSearch = "";
	private String copyrightSearch = "";
	private String filenameSearch = "";
	private int daysSinceUploadSearch = 0;
	ArrayList<Integer> imageTypeIdList = new ArrayList<Integer>();

	public ImageSearchEntry(boolean orSearch, String sessionID, String username) {
		super(orSearch, sessionID, username);
	}

	public ImageSearchEntry(String sessionID, String username) {
		super(sessionID, username);
	}

	public ImageSearchEntry() { }

	public String getTitleSearch() {
		return titleSearch;
	}

	public void setTitleSearch(String titleSearch) {
		this.titleSearch = titleSearch;
	}

	public String getCopyrightSearch() {
		return copyrightSearch;
	}

	public void setCopyrightSearch(String copyrightSearch) {
		this.copyrightSearch = copyrightSearch;
	}

	public ArrayList<Integer> getImageTypeIdList() {
		return imageTypeIdList;
	}

	public void setImageTypeIdList(ArrayList<Integer> imageTypeIdList) {
		this.imageTypeIdList = imageTypeIdList;
	}

	public String getFilenameSearch() {
		return filenameSearch;
	}

	public void setFilenameSearch(String filenameSearch) {
		this.filenameSearch = filenameSearch;
	}

	public int getDaysSinceUploadSearch() {
		return daysSinceUploadSearch;
	}

	public void setDaysSinceUploadSearch(int daysSinceUploadSearch) {
		this.daysSinceUploadSearch = daysSinceUploadSearch;
	}

}
