package de.cses.shared;

import java.util.ArrayList;

import com.google.gwt.user.client.Cookies;

import de.cses.client.user.UserLogin;

public class DepictionSearchEntry extends AbstractSearchEntry {
	
	private String shortName="";
	private ArrayList<Integer> caveIdList = new ArrayList<Integer>();
	private ArrayList<Integer> locationIdList = new ArrayList<Integer>();
	private ArrayList<Integer> iconographyIdList = new ArrayList<Integer>();
	private ArrayList<Integer> bibIdList = new ArrayList<Integer>();
	private int correlationFactor = 0;

	public DepictionSearchEntry(boolean orSearch, String sessionID) {
		super(orSearch, sessionID);
	}

	public DepictionSearchEntry(String sessionID) {
		super(sessionID);
	}

	public DepictionSearchEntry() {	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public ArrayList<Integer> getCaveIdList() {
		return caveIdList;
	}

	public void setCaveIdList(ArrayList<Integer> caveIdList) {
		this.caveIdList = caveIdList;
	}

	public ArrayList<Integer> getLocationIdList() {
		return locationIdList;
	}

	public void setLocationIdList(ArrayList<Integer> locationIdList) {
		this.locationIdList = locationIdList;
	}

	public ArrayList<Integer> getIconographyIdList() {
		return iconographyIdList;
	}

	public void setIconographyIdList(ArrayList<Integer> iconographyIdList) {
		this.iconographyIdList = iconographyIdList;
	}

	public int getCorrelationFactor() {
		return correlationFactor;
	}

	public void setCorrelationFactor(int correlationFactor) {
		this.correlationFactor = correlationFactor;
	}

	public ArrayList<Integer> getBibIdList() {
		return bibIdList;
	}

	public void setBibIdList(ArrayList<Integer> bibIdList) {
		this.bibIdList = bibIdList;
	}

}
