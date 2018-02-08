/*
 * Copyright 2016 
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
package de.cses.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.cses.shared.AnnotatedBiblographyEntry;
import de.cses.shared.AuthorEntry;
import de.cses.shared.CaveEntry;
import de.cses.shared.CaveGroupEntry;
import de.cses.shared.CavePart;
import de.cses.shared.CaveTypeEntry;
import de.cses.shared.CeilingTypeEntry;
import de.cses.shared.CurrentLocationEntry;
import de.cses.shared.DepictionEntry;
import de.cses.shared.DistrictEntry;
import de.cses.shared.ExpeditionEntry;
import de.cses.shared.IconographyEntry;
import de.cses.shared.ImageEntry;
import de.cses.shared.ImageTypeEntry;
import de.cses.shared.LocationEntry;
import de.cses.shared.MainTypologicalClass;
import de.cses.shared.ModeOfRepresentationEntry;
import de.cses.shared.OrientationEntry;
import de.cses.shared.OrnamentCaveType;
import de.cses.shared.OrnamentEntry;
import de.cses.shared.OrnamentFunctionEntry;
import de.cses.shared.OrnamentOfOtherCulturesEntry;
import de.cses.shared.OrnamentPositionEntry;
import de.cses.shared.PhotographerEntry;
import de.cses.shared.PictorialElementEntry;
import de.cses.shared.PreservationClassificationEntry;
import de.cses.shared.PublicationEntry;
import de.cses.shared.PublicationTypeEntry;
import de.cses.shared.PublisherEntry;
import de.cses.shared.RegionEntry;
import de.cses.shared.SiteEntry;
import de.cses.shared.StructureOrganization;
import de.cses.shared.StyleEntry;
import de.cses.shared.UserEntry;
import de.cses.shared.VendorEntry;
import de.cses.shared.WallEntry;
import de.cses.shared.WallLocationEntry;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("db")
public interface DatabaseService extends RemoteService {

//	String dbServer(String name) throws IllegalArgumentException;

	ArrayList<DistrictEntry> getDistricts() throws IllegalArgumentException;

	ArrayList<ImageEntry> getImages() throws IllegalArgumentException;
	ArrayList<AnnotatedBiblographyEntry> getAnnotatedBiblography() throws IllegalArgumentException;
	AnnotatedBiblographyEntry getAnnotatedBiblographybyID(int bibid) throws IllegalArgumentException;
	ArrayList<PublicationTypeEntry> getPublicationTypes() throws IllegalArgumentException;

	ArrayList<ImageEntry> getImages(String where) throws IllegalArgumentException;

	ArrayList<PhotographerEntry> getPhotographer() throws IllegalArgumentException;

	ImageEntry getImage(int imageID) throws IllegalArgumentException;

	ArrayList<CaveEntry> getCaves() throws IllegalArgumentException;

	ArrayList<CaveEntry> getCavesbyDistrictID(int DistrictID) throws IllegalArgumentException;

	ArrayList<OrnamentEntry> getOrnaments() throws IllegalArgumentException;

	ArrayList<OrnamentOfOtherCulturesEntry> getOrnamentsOfOtherCultures() throws IllegalArgumentException;

	ArrayList<DepictionEntry> getDepictions() throws IllegalArgumentException;

	DepictionEntry getDepictionEntry(int depictionID) throws IllegalArgumentException;

	boolean updateEntry(String sqlUpdate);

	boolean deleteEntry(String sqlDelete);

//	int insertEntry(String sqlInsert);

	ArrayList<IconographyEntry> getIconography() throws IllegalArgumentException;

	ArrayList<PictorialElementEntry> getPictorialElements() throws IllegalArgumentException;

	boolean saveOrnamentEntry(OrnamentEntry ornamentEntry) throws IllegalArgumentException;

	CaveTypeEntry getCaveTypebyID(int caveTypeID) throws IllegalArgumentException;

	ArrayList<CaveTypeEntry> getCaveTypes() throws IllegalArgumentException;

	ArrayList<VendorEntry> getVendors() throws IllegalArgumentException;

	ArrayList<StyleEntry> getStyles() throws IllegalArgumentException;

	ArrayList<ExpeditionEntry> getExpeditions() throws IllegalArgumentException;

	PublicationEntry getPublicationEntry(int id) throws IllegalArgumentException;

	AuthorEntry getAuthorEntry(int id) throws IllegalArgumentException;

	ImageEntry getMasterImageEntryForDepiction(int depictionID) throws IllegalArgumentException;

	CaveEntry getCaveEntry(int id) throws IllegalArgumentException;

//	AntechamberEntry getAntechamberEntry(int publicationTypeID) throws IllegalArgumentException;
//
//	MainChamberEntry getMainChamberEntry(int publicationTypeID) throws IllegalArgumentException;
//
//	RearAreaEntry getRearAreaEntry(int publicationTypeID) throws IllegalArgumentException;

	ArrayList<RegionEntry> getRegions() throws IllegalArgumentException;

	ArrayList<SiteEntry> getSites() throws IllegalArgumentException;

	SiteEntry getSite(int id)  throws IllegalArgumentException;

	ArrayList<CaveEntry> getCaves(String sqlWhere) throws IllegalArgumentException;

	ArrayList<DepictionEntry> getDepictions(String sqlWhere) throws IllegalArgumentException;
	
	ArrayList<DepictionEntry> getDepictionsbyWallID(int wallID) throws IllegalArgumentException;
	
	String saveDepiction(int depictionID, int absoluteLeft, int absoluteTop) throws IllegalArgumentException;

	ArrayList<ImageEntry> getRelatedImages(int depictionID) throws IllegalArgumentException;
	
	ArrayList<OrientationEntry> getOrientations() throws IllegalArgumentException;

	ArrayList<MainTypologicalClass> getMainTypologicalClasses() throws IllegalArgumentException;
	
	ArrayList<OrnamentCaveType> getOrnamentCaveTypes() throws IllegalArgumentException;
	
	ArrayList<WallEntry> getWalls() throws IllegalArgumentException;

	WallEntry getWall(int caveID, String locationLabel) throws IllegalArgumentException;

	ArrayList<WallEntry> getWalls(int caveID) throws IllegalArgumentException;

	ArrayList<OrnamentFunctionEntry> getOrnamentFunctions() throws IllegalArgumentException;
	
	ArrayList<OrnamentPositionEntry> getOrnamentPositions() throws IllegalArgumentException;
	
	ArrayList<StructureOrganization> getStructureOrganizations() throws IllegalArgumentException;
	
	ArrayList<CavePart> getCaveParts() throws IllegalArgumentException;
	
	ArrayList<OrnamentEntry> getOrnamentsWHERE(String sqlWhere) throws IllegalArgumentException;
	
	ArrayList<OrientationEntry> getOrientationInformation() throws IllegalArgumentException;
	
	IconographyEntry getIconographyEntry(int iconographyID) throws IllegalArgumentException;
	
	ArrayList<PictorialElementEntry> getRelatedPE(int depictionID) throws IllegalArgumentException;

	ArrayList<CeilingTypeEntry> getCeilingTypes() throws IllegalArgumentException;

	ArrayList<PreservationClassificationEntry> getPreservationClassifications();

	ArrayList<PictorialElementEntry> getPictorialElementsObjects() throws IllegalArgumentException;

	boolean updateCaveEntry(CaveEntry caveEntry) throws IllegalArgumentException;

	int insertCaveEntry(CaveEntry caveEntry) throws IllegalArgumentException;

	ArrayList<CaveGroupEntry> getCaveGroups() throws IllegalArgumentException;

	String userLogin(String username, String password) throws IllegalArgumentException;

	boolean updateImageEntry(ImageEntry imgEntry) throws IllegalArgumentException;

	ArrayList<ImageTypeEntry> getImageTypes() throws IllegalArgumentException;

	int insertDepictionEntry(DepictionEntry depictionEntry, ArrayList<ImageEntry> imgEntryList, ArrayList<PictorialElementEntry> peEntryList,
			ArrayList<IconographyEntry> iconographyList);

	boolean updateDepictionEntry(DepictionEntry correspondingDepictionEntry, ArrayList<ImageEntry> all,
			ArrayList<PictorialElementEntry> selectedPE, ArrayList<IconographyEntry> iconographyList);

	ArrayList<ModeOfRepresentationEntry> getModesOfRepresentation() throws IllegalArgumentException;

	ArrayList<WallLocationEntry> getWallLocations() throws IllegalArgumentException;

	boolean updateAuthorEntry(AuthorEntry currentAuthorEntry) throws IllegalArgumentException;
	
	boolean saveAnnotatedBiblographyEntry(AnnotatedBiblographyEntry bibEntry) throws IllegalArgumentException;
	ArrayList<AnnotatedBiblographyEntry> getAnnotatedBibliography() throws IllegalArgumentException;
	ArrayList<PublisherEntry> getPublisher() throws IllegalArgumentException;
	ArrayList<AuthorEntry> getAuthors() throws IllegalArgumentException;

	int insertPhotographerEntry(PhotographerEntry photographerEntry) throws IllegalArgumentException;

	int insertCaveGroupEntry(CaveGroupEntry cgEntry) throws IllegalArgumentException;

	int insertDistrictEntry(DistrictEntry de) throws IllegalArgumentException;

	int insertRegionEntry(RegionEntry re) throws IllegalArgumentException;

	int insertAuthorEntry(AuthorEntry currentAuthorEntry) throws IllegalArgumentException;

	int insertCeilingTypeEntry(CeilingTypeEntry ctEntry) throws IllegalArgumentException;

	@Deprecated
	ArrayList<CurrentLocationEntry> getCurrentLocations() throws IllegalArgumentException;

	ArrayList<IconographyEntry> getRelatedIconography(int depictionID) throws IllegalArgumentException;

	ArrayList<LocationEntry> getLocations() throws IllegalArgumentException;

	int insertVendorEntry(VendorEntry vEntry) throws IllegalArgumentException;

	int insertLocationEntry(LocationEntry lEntry) throws IllegalArgumentException;

}
