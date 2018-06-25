/*
 * Copyright 2017 
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
package de.cses.client.ornamentic;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.text.shared.AbstractSafeHtmlRenderer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.button.ToolButton;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.TextArea;

import de.cses.client.DatabaseService;
import de.cses.client.DatabaseServiceAsync;
import de.cses.client.StaticTables;
import de.cses.client.walls.WallSelector;
import de.cses.shared.CaveAreaEntry;
import de.cses.shared.CaveEntry;
import de.cses.shared.CavePart;
import de.cses.shared.InnerSecondaryPatternsEntry;
import de.cses.shared.OrnamentFunctionEntry;
import de.cses.shared.OrnamentPositionEntry;
import de.cses.shared.WallEntry;
import de.cses.shared.WallOrnamentCaveRelation;

/**
 * @author nina
 *
 */
public class OrnamentWallAttributes extends PopupPanel {

	private CaveEntry caveEntry;
	// private PopupPanel popup;
	private OrnamentCaveAttributes ornamentCaveRelation;
	private OrnamentPositionProperties ornamentPositionProps;
	private OrnamentFunctionProperties ornamentFunctionProps;
	private ListStore<OrnamentPositionEntry> ornamentPositionEntryLS;
	private ComboBox<OrnamentPositionEntry> ornamentPositionComboBox;
	private ListStore<OrnamentFunctionEntry> ornamentFunctionEntryLS;
	private ComboBox<OrnamentFunctionEntry> ornamentfunctionComboBox;
	// private ComboBox<WallEntry> wallsComboBox;
	private WallOrnamentCaveRelation wallOrnamentCaveRelation;
	private final DatabaseServiceAsync dbService = GWT.create(DatabaseService.class);

	private TextArea notes;

	private WallSelector wallselector;

	public OrnamentWallAttributes(CaveEntry cave, WallOrnamentCaveRelation wallOrnamentCaveRelation) {
		super(false);
		this.wallOrnamentCaveRelation = wallOrnamentCaveRelation;
		this.caveEntry = cave;
		ornamentPositionProps = GWT.create(OrnamentPositionProperties.class);
		ornamentFunctionProps = GWT.create(OrnamentFunctionProperties.class);
		ornamentPositionEntryLS = new ListStore<OrnamentPositionEntry>(ornamentPositionProps.ornamentPositionID());
		ornamentFunctionEntryLS = new ListStore<OrnamentFunctionEntry>(ornamentFunctionProps.ornamentFunctionID());

		for (OrnamentPositionEntry ope : StaticTables.getInstance().getOrnamentPositionEntries().values()) {
			ornamentPositionEntryLS.add(ope);
		}
		for (OrnamentFunctionEntry ofe : StaticTables.getInstance().getOrmanemtFunctionEntries().values()) {
			ornamentFunctionEntryLS.add(ofe);
		}
		


		setWidget(createForm());
	}

	private FramedPanel createForm() {
		wallselector = new WallSelector();
		wallselector.setCave(caveEntry);

		FramedPanel selectWallFP = new FramedPanel();
		selectWallFP.setHeading("Select Wall");
		selectWallFP.add(wallselector);
		if (wallOrnamentCaveRelation != null) {
			wallselector.setWallEntry(wallOrnamentCaveRelation.getWall());
		}
		ValueChangeHandler<WallEntry> wallSelectionHandler = new ValueChangeHandler<WallEntry>() {

			@Override
			public void onValueChange(ValueChangeEvent<WallEntry> event) {
				ornamentPositionComboBox.clear();
				ornamentfunctionComboBox.clear();
				ornamentPositionComboBox.disable();
				ornamentfunctionComboBox.disable();
				ornamentPositionEntryLS.clear();
				filterPositionbyCaveArea();

				ornamentPositionComboBox.setEnabled(true);
			}

		};
		wallselector.getWallSelectorCB().addValueChangeHandler(wallSelectionHandler);

		ornamentPositionComboBox = new ComboBox<OrnamentPositionEntry>(ornamentPositionEntryLS, ornamentPositionProps.name(),
				new AbstractSafeHtmlRenderer<OrnamentPositionEntry>() {

					@Override
					public SafeHtml render(OrnamentPositionEntry item) {
						final OrnamentPositionViewTemplates pvTemplates = GWT.create(OrnamentPositionViewTemplates.class);
						return pvTemplates.ornamentPosition(item.getName());
					}
				});
		ornamentPositionComboBox.setTypeAhead(false);
		ornamentPositionComboBox.setEditable(false);
		ornamentPositionComboBox.setTriggerAction(TriggerAction.ALL);
		if (wallOrnamentCaveRelation != null) {
			ornamentPositionComboBox.setValue(ornamentPositionEntryLS.findModelWithKey(Integer.toString(wallOrnamentCaveRelation.getOrnamenticPositionID())));
		}
		FramedPanel ornamentPositionFP = new FramedPanel();
		ornamentPositionFP.setHeading("Select ornament position");
		ornamentPositionFP.add(ornamentPositionComboBox);

		ornamentfunctionComboBox = new ComboBox<OrnamentFunctionEntry>(ornamentFunctionEntryLS, ornamentFunctionProps.name(),
				new AbstractSafeHtmlRenderer<OrnamentFunctionEntry>() {

					@Override
					public SafeHtml render(OrnamentFunctionEntry item) {
						final OrnamentFunctionViewTemplates pvTemplates = GWT.create(OrnamentFunctionViewTemplates.class);
						return pvTemplates.ornamentFunction(item.getName());
					}
				});
		ornamentfunctionComboBox.setTypeAhead(false);
		ornamentfunctionComboBox.setEditable(false);
		ornamentfunctionComboBox.setTriggerAction(TriggerAction.ALL);
		if (wallOrnamentCaveRelation != null) {
			ornamentfunctionComboBox.setValue(ornamentFunctionEntryLS.findModelWithKey(Integer.toString(wallOrnamentCaveRelation.getOrnamenticFunctionID())));
		}
		FramedPanel ornamentFunctionFP = new FramedPanel();
		ornamentFunctionFP.setHeading("Select the ornament function");
		ornamentFunctionFP.add(ornamentfunctionComboBox);

		
		
		ValueChangeHandler<OrnamentPositionEntry> positionSelectionHandler = new ValueChangeHandler<OrnamentPositionEntry>() {

			@Override
			public void onValueChange(ValueChangeEvent<OrnamentPositionEntry> event) {
				ornamentFunctionEntryLS.clear();
				
				dbService.getFunctionbyPosition(event.getValue(), new AsyncCallback<ArrayList<OrnamentFunctionEntry>>() {

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}

					@Override
					public void onSuccess(ArrayList<OrnamentFunctionEntry> result) {
						for (OrnamentFunctionEntry pe : result) {
							ornamentFunctionEntryLS.add(pe);
						}
						if (wallOrnamentCaveRelation != null) {
							ornamentfunctionComboBox.setValue(ornamentFunctionEntryLS.findModelWithKey(Integer.toString(wallOrnamentCaveRelation.getOrnamenticFunctionID())));
						}
					}
				});
				ornamentfunctionComboBox.setEnabled(true);
			}

		};
		ornamentPositionComboBox.addValueChangeHandler(positionSelectionHandler);
		
		
		notes = new TextArea();
		notes.setAllowBlank(true);
		FramedPanel notesFP = new FramedPanel();
		if (wallOrnamentCaveRelation != null) {
			notes.setText(wallOrnamentCaveRelation.getNotes());
		}
		notesFP.setHeading("Notes");
		notesFP.add(notes);

		VerticalLayoutContainer vlcWalls = new VerticalLayoutContainer();
//		vlcWalls.add(selectWallFP, new VerticalLayoutData(1.0, .5));
		vlcWalls.add(ornamentPositionFP, new VerticalLayoutData(1.0, .15));
		vlcWalls.add(ornamentFunctionFP, new VerticalLayoutData(1.0, .15));
		vlcWalls.add(notesFP, new VerticalLayoutData(1.0, .7));
		
		HorizontalLayoutContainer wallRelationHLC = new HorizontalLayoutContainer();
		wallRelationHLC.add(selectWallFP, new HorizontalLayoutData(.5, 1.0));
		wallRelationHLC.add(vlcWalls, new HorizontalLayoutData(.5, 1.0));

		FramedPanel wallrelationFramedPanel = new FramedPanel();
		wallrelationFramedPanel.setHeading("Select Walls");
		wallrelationFramedPanel.setSize("600px", "450px");
		wallrelationFramedPanel.add(wallRelationHLC);
		
		ToolButton cancelTB = new ToolButton(ToolButton.CLOSE);
		cancelTB.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				hide();
			}
		});

		ToolButton saveTB = new ToolButton(ToolButton.SAVE);
		saveTB.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				save();
				hide();
			}
		});
		wallrelationFramedPanel.addTool(saveTB);
		wallrelationFramedPanel.addTool(cancelTB);

//		ToolButton closeTB = new ToolButton(ToolButton.CLOSE);
//		closeTB.addSelectHandler(new SelectHandler() {
//
//			@Override
//			public void onSelect(SelectEvent event) {
//				Dialog d = new Dialog();
//				d.setHeading("Exit Warning!");
//				d.setWidget(new HTML("Do you wish to save before exiting?"));
//				d.setBodyStyle("fontWeight:bold;padding:13px;");
//				d.setPixelSize(300, 100);
//				d.setHideOnButtonClick(true);
//				d.setPredefinedButtons(PredefinedButton.YES, PredefinedButton.NO, PredefinedButton.CANCEL);
//				d.setModal(true);
//				d.center();
//				d.show();
//				d.getButton(PredefinedButton.YES).addSelectHandler(new SelectHandler() {
//
//					@Override
//					public void onSelect(SelectEvent event) {
//						save();
//						hide();
//					}
//				});
//				d.getButton(PredefinedButton.NO).addSelectHandler(new SelectHandler() {
//
//					@Override
//					public void onSelect(SelectEvent event) {
//						hide();
//					}
//				});
//			}
//		});
//		wallrelationFramedPanel.addTool(closeTB);

		return wallrelationFramedPanel;
	}

	/**
	 * 
	 */
	protected void save() {
		WallOrnamentCaveRelation caveWallOrnamentRelation = new WallOrnamentCaveRelation(caveEntry.getCaveID(), wallselector.getSelectedWallEntry());
		if (ornamentfunctionComboBox.getValue() == null) {
			caveWallOrnamentRelation.setOrnamenticFunctionID(18); // 18 = unknown
		} else {
			caveWallOrnamentRelation.setOrnamenticFunctionID(ornamentfunctionComboBox.getValue().getOrnamentFunctionID());
		}
		if (ornamentPositionComboBox.getValue() == null) {
			caveWallOrnamentRelation.setOrnamenticPositionID(19); // 19 = unknwon
		} else {
			caveWallOrnamentRelation.setOrnamenticPositionID(ornamentPositionComboBox.getValue().getOrnamentPositionID());
		}
		caveWallOrnamentRelation.setNotes(notes.getText());
		
		if(wallOrnamentCaveRelation != null) {
			ornamentCaveRelation.getWallsListStore().remove(wallOrnamentCaveRelation);
		}
		
		ornamentCaveRelation.getWallsListStore().add(caveWallOrnamentRelation);
	}

	public CaveEntry getCave() {
		return caveEntry;
	}

	public void setCave(CaveEntry cave) {
		this.caveEntry = cave;
	}

	public OrnamentCaveAttributes getOrnamentCaveRelation() {
		return ornamentCaveRelation;
	}

	public void setOrnamentCaveRelation(OrnamentCaveAttributes ornamentCaveRelation) {
		this.ornamentCaveRelation = ornamentCaveRelation;
	}

	interface CavePartProperties extends PropertyAccess<CavePart> {
		ModelKeyProvider<CavePart> cavePartID();

		LabelProvider<CavePart> name();
	}

	interface OrnamentPositionProperties extends PropertyAccess<OrnamentPositionEntry> {
		ModelKeyProvider<OrnamentPositionEntry> ornamentPositionID();

		LabelProvider<OrnamentPositionEntry> name();
	}

	interface OrnamentFunctionProperties extends PropertyAccess<OrnamentFunctionEntry> {
		ModelKeyProvider<OrnamentFunctionEntry> ornamentFunctionID();

		LabelProvider<OrnamentFunctionEntry> name();
	}

	interface WallProperties extends PropertyAccess<WallEntry> {
		ModelKeyProvider<WallEntry> wallID();

		LabelProvider<WallEntry> wallIDLabel();
	}

	interface OrnamentCavePartViewTemplates extends XTemplates {
		@XTemplate("<div>{name}</div>")
		SafeHtml ornamentCavePart(String name);
	}

	interface OrnamentPositionViewTemplates extends XTemplates {
		@XTemplate("<div>{name}</div>")
		SafeHtml ornamentPosition(String name);
	}

	interface OrnamentFunctionViewTemplates extends XTemplates {
		@XTemplate("<div>{name}</div>")
		SafeHtml ornamentFunction(String name);
	}

	interface WallsViewTemplates extends XTemplates {
		@XTemplate("<div>{wallID}</div>")
		SafeHtml walls(int wallID);
	}
	
	public void filterPositionbyCaveArea() {
		String wallOrCeiling = StaticTables.getInstance().getWallLocationEntries().get(wallselector.getSelectedWallEntry().getWallLocationID()).getLabel();
	
		if( wallOrCeiling.contains("wall")) {
	
			dbService.getPositionbyWall(wallselector.getSelectedWallEntry(), new AsyncCallback<ArrayList<OrnamentPositionEntry>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
				}

				@Override
				public void onSuccess(ArrayList<OrnamentPositionEntry> result) {
		
					for (OrnamentPositionEntry pe : result) {
						ornamentPositionEntryLS.add(pe);
					}
					if (wallOrnamentCaveRelation != null) {
						ornamentPositionComboBox.setValue(ornamentPositionEntryLS.findModelWithKey(Integer.toString(wallOrnamentCaveRelation.getOrnamenticPositionID())));
					}
				}
			});
		}
		else if( wallOrCeiling.contains("ceiling"))  {
			;
			ArrayList<CaveAreaEntry> result = caveEntry.getCaveAreaList();
			String cavearealabel = StaticTables.getInstance().getWallLocationEntries().get(wallselector.getSelectedWallEntry().getWallLocationID()).getCaveAreaLabel();
			for(int i = 0; i < result.size(); i++){
				if(result.get(i).getCaveAreaLabel().contains(cavearealabel)) {
					CaveAreaEntry cavearea = result.get(i);
					int ceiling1 = cavearea.getCeilingTypeID1();
					int ceiling2 = cavearea.getCeilingTypeID2();
					if(ceiling1 == 0 && ceiling2 == 0) {
						ceiling1 = 11;
					}
					dbService.getPositionbyCeiling(ceiling1, ceiling2, new AsyncCallback<ArrayList<OrnamentPositionEntry>>() {

						@Override
						public void onFailure(Throwable caught) {
							caught.printStackTrace();
						}

						@Override
						public void onSuccess(ArrayList<OrnamentPositionEntry> result) {
							for (OrnamentPositionEntry pe : result) {
								ornamentPositionEntryLS.add(pe);
							}
							if (wallOrnamentCaveRelation != null) {
								ornamentPositionComboBox.setValue(ornamentPositionEntryLS.findModelWithKey(Integer.toString(wallOrnamentCaveRelation.getOrnamenticPositionID())));
							
				}
			}
				
	});
				
			}
			}
		}
		else {
		}
		}
}
