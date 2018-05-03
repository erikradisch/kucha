/*
 * Copyright 2018 
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
package de.cses.client.bibliography;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.GXT;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.state.client.CookieProvider;
import com.sencha.gxt.state.client.GridFilterStateHandler;
import com.sencha.gxt.state.client.StateManager;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GridView;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;
import com.sencha.gxt.widget.core.client.grid.filters.StringFilter;

import de.cses.client.DatabaseService;
import de.cses.client.DatabaseServiceAsync;
import de.cses.client.StaticTables;
import de.cses.shared.AnnotatedBiblographyEntry;
import de.cses.shared.AuthorEntry;

/**
 * @author alingnau
 *
 */
public class BibliographySelector implements IsWidget {
	
	interface BibliographyProperties extends PropertyAccess<AnnotatedBiblographyEntry> {
		@Path("annotatedBiblographyID")
		ModelKeyProvider<AnnotatedBiblographyEntry> key();

		ValueProvider<AnnotatedBiblographyEntry, String> titleORG();
		
		ValueProvider<AnnotatedBiblographyEntry, String> titleEN();
		
		ValueProvider<AnnotatedBiblographyEntry, String> authors();
		
		ValueProvider<AnnotatedBiblographyEntry, String> editors();
		
		@Path("yearORG")
		ValueProvider<AnnotatedBiblographyEntry, String> year();
		
//		@Path("publicationType.name")
//		ValueProvider<AnnotatedBiblographyEntry, String> publicationType();
	}
	
	private ContentPanel mainPanel = null;
	private BibliographyProperties bibProps = GWT.create(BibliographyProperties.class);
	private final DatabaseServiceAsync dbService = GWT.create(DatabaseService.class);
	private Grid<AnnotatedBiblographyEntry> grid;

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.IsWidget#asWidget()
	 */
	@Override
	public Widget asWidget() {
		if (mainPanel == null) {
			createUI();
		}
		return mainPanel;
	}

	/**
	 * 
	 */
	private void createUI() {
		ColumnConfig<AnnotatedBiblographyEntry, String> titleOrgCol = new ColumnConfig<AnnotatedBiblographyEntry, String>(bibProps.titleORG(), 250, "Original Title");
		ColumnConfig<AnnotatedBiblographyEntry, String> titleEnCol = new ColumnConfig<AnnotatedBiblographyEntry, String>(bibProps.titleEN(), 250, "English Translation");
		ColumnConfig<AnnotatedBiblographyEntry, String> authorsCol = new ColumnConfig<AnnotatedBiblographyEntry, String>(bibProps.authors(), 200, "Authors");
		ColumnConfig<AnnotatedBiblographyEntry, String> editorsCol = new ColumnConfig<AnnotatedBiblographyEntry, String>(bibProps.authors(), 200, "Editors");
		ColumnConfig<AnnotatedBiblographyEntry, String> yearColumn = new ColumnConfig<AnnotatedBiblographyEntry, String>(bibProps.year(), 50, "Year");
		
    // we don't want all columns visible at the beginning
		editorsCol.setHidden(true);
		titleEnCol.setHidden(true);
		titleOrgCol.setMenuDisabled(false);
		
    List<ColumnConfig<AnnotatedBiblographyEntry, ?>> columns = new ArrayList<ColumnConfig<AnnotatedBiblographyEntry, ?>>();
    columns.add(titleOrgCol);
    columns.add(titleEnCol);
    columns.add(authorsCol);
    columns.add(editorsCol);
    columns.add(yearColumn);

    ColumnModel<AnnotatedBiblographyEntry> cm = new ColumnModel<AnnotatedBiblographyEntry>(columns);
    
    ListStore<AnnotatedBiblographyEntry> store = new ListStore<AnnotatedBiblographyEntry>(bibProps.key());
    for (AnnotatedBiblographyEntry abe : StaticTables.getInstance().getBibliographyEntries().values()) {
    	store.add(abe);
    }
    
//		dbService.getAnnotatedBibliography(new AsyncCallback<ArrayList<AnnotatedBiblographyEntry>>() {
//
//			@Override
//			public void onFailure(Throwable caught) {
//				caught.printStackTrace();
//			}
//
//			@Override
//			public void onSuccess(ArrayList<AnnotatedBiblographyEntry> result) {
//				for (AnnotatedBiblographyEntry abe : result) {
//				}
//			}
//		});
		
		GridView<AnnotatedBiblographyEntry> bibGridView = new GridView<AnnotatedBiblographyEntry>();
		bibGridView.setAutoExpandColumn(titleOrgCol);
		bibGridView.setColumnLines(true);
		
    grid = new Grid<AnnotatedBiblographyEntry>(store, cm, bibGridView);
    grid.setAllowTextSelection(true);
    grid.setBorders(true);
    grid.setColumnReordering(true);
    
    // State manager, make this grid stateful
    grid.setStateful(true);
    grid.setStateId("bibSelector");

    StringFilter<AnnotatedBiblographyEntry> titleFilter = new StringFilter<AnnotatedBiblographyEntry>(bibProps.titleORG());
    StringFilter<AnnotatedBiblographyEntry> authorFilter = new StringFilter<AnnotatedBiblographyEntry>(bibProps.authors());
    StringFilter<AnnotatedBiblographyEntry> editorFilter = new StringFilter<AnnotatedBiblographyEntry>(bibProps.editors());

    GridFilters<AnnotatedBiblographyEntry> filters = new GridFilters<AnnotatedBiblographyEntry>();
    filters.initPlugin(grid);
    filters.setLocal(true);
    filters.addFilter(titleFilter);
    filters.addFilter(authorFilter);
    filters.addFilter(editorFilter);
    
//    titleFilter.setActive(true, false);
//    authorFilter.setActive(true, false);
//    editorFilter.setActive(true, false);
    
    // Stage manager, load the previous state
//    GridFilterStateHandler<AnnotatedBiblographyEntry> handler = new GridFilterStateHandler<AnnotatedBiblographyEntry>(grid, filters);
//    handler.loadState();
    
    BorderLayoutContainer bibSelectorBLC = new BorderLayoutContainer();
    bibSelectorBLC.setCenterWidget(grid, new MarginData(5));

    mainPanel = new ContentPanel();
		mainPanel.setHeaderVisible(false);
		mainPanel.add(bibSelectorBLC);
	}

}
