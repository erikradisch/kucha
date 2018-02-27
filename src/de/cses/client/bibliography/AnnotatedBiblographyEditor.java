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
package de.cses.client.bibliography;

import java.util.ArrayList;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.text.shared.AbstractSafeHtmlRenderer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.core.client.util.DateWrapper;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.TabPanel;
import com.sencha.gxt.widget.core.client.button.ToolButton;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.DualListField;
import com.sencha.gxt.widget.core.client.form.NumberField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.validator.MaxNumberValidator;

import de.cses.client.DatabaseService;
import de.cses.client.DatabaseServiceAsync;
import de.cses.client.StaticTables;
import de.cses.client.ui.AbstractEditor;
import de.cses.shared.AnnotatedBiblographyEntry;
import de.cses.shared.AuthorAnnotatedRelation;
import de.cses.shared.AuthorEntry;
import de.cses.shared.EditorAnnotatedRelation;
import de.cses.shared.PublicationTypeEntry;
import de.cses.shared.PublisherEntry;

/**
 * @author Nina
 *
 */
public class AnnotatedBiblographyEditor extends AbstractEditor {
	private HorizontalLayoutContainer horizontBackground;
	private AnnotatedBiblographyEntry entry;
	private final DatabaseServiceAsync dbService = GWT.create(DatabaseService.class);

	private int publicationtype = 0;

	private FramedPanel frame;

	private DualListField<AuthorEntry, String> authorSelection;
	private DualListField<AuthorEntry, String> editorSelection;

	private ComboBox<PublisherEntry> publisherComboBox;
	private ComboBox<PublicationTypeEntry> publicationTypeComboBox;
	private ComboBox<AnnotatedBiblographyEntry> firstEditionComboBox;

	private ListStore<PublicationTypeEntry> publicationTypeListStore;
	private ListStore<PublisherEntry> publisherListStore;
	private ListStore<AnnotatedBiblographyEntry> AnnotatedBiblographyEntryListStore;

	private ListStore<AuthorEntry> authorListStore;
	private ListStore<AuthorEntry> selectedAuthorListStore;
	private ListStore<AuthorEntry> selectedEditorListStore;

	private PublisherProperties publisherProps;
	private AnnotatedBiblographyEntryProperties annotatedBiblographyEntryProps;
	private PublicationTypeProperties publicationTypeProps;
	private AuthorProperties authorProps;

	private TabPanel tabpanel = new TabPanel();
	private VerticalLayoutContainer firstTabVLC = new VerticalLayoutContainer();
	private VerticalLayoutContainer secoundTabVLC = new VerticalLayoutContainer();
	private VerticalLayoutContainer thirdTabVLC = new VerticalLayoutContainer();
	private VerticalLayoutContainer firstsecoundTabVLC = new VerticalLayoutContainer();

	// VerticalLayoutContainer mainInputVLC = new VerticalLayoutContainer();
	private FramedPanel framefirstedition;

	private FramedPanel original;
	private FramedPanel eng;
	private FramedPanel trans;

	private FramedPanel mainFP = null; // das oberste Framed Panel als Rahmen
	private VerticalLayoutContainer backgroundoverview = new VerticalLayoutContainer(); // verticaler background fuer die Lioteratur

	private TextField titelEN;
	private TextField titelORG;
	private TextField titelTR;

	private TextField procEN;
	private TextField procORG;
	private TextField procTR;

	private TextField chaptitEN;
	private TextField chaptitORG;
	private TextField chaptitTR;

	private TextField booktitelEN;
	private TextField booktitelORG;
	private TextField booktitelTR;

	private TextField uniEN;
	private TextField uniORG;
	private TextField uniTR;

	private TextField numberEN;
	private TextField numberORG;
	private TextField numberTR;

	private TextField accessEN;
	private TextField accessORG;
	private TextField accessTR;

	private TextField titeladdonEN;
	private TextField titeladdonORG;
	private TextField titeladdonTR;

	private TextField seriesEN;
	private TextField seriesORG;
	private TextField seriesTR;

	private TextField editionEN;
	private TextField editionORG;
	private TextField editionTR;

	private TextField volumeEN;
	private TextField volumeORG;
	private TextField volumeTR;

	NumberField<Integer> yearEN;
	private TextField yearORG;
	private TextField yearTR;

	private TextField monthEN;
	private TextField monthORG;
	private TextField monthTR;

	private TextField pagesEN;
	private TextField pagesORG;
	private TextField pagesTR;

	private TextArea comments;
	private TextArea notes;
	private TextField url;
	private TextField uri;
	private CheckBox unpublished;
	private CheckBox erstauflage;

	public AnnotatedBiblographyEditor(AnnotatedBiblographyEntry entry) {
		this.entry = entry;
	}

	public AnnotatedBiblographyEditor() {	}

	@Override
	public Widget asWidget() {
		if (mainFP == null) {
			init();
			createForm();
		}

		return mainFP;
	}

	public void save() {
		Window.alert("save begins");
		AnnotatedBiblographyEntry bib = new AnnotatedBiblographyEntry();

		bib.setTitleaddonEN(titeladdonEN.getText());
		bib.setTitleaddonORG(titeladdonORG.getText());
		bib.setTitleaddonTR(titeladdonTR.getText());

		bib.setTitleEN(titelEN.getText());
		bib.setTitleORG(titelORG.getText());
		bib.setTitleTR(titelORG.getText());

		if (publicationtype == 7) {
			bib.setAccessdateEN(accessEN.getText());
			bib.setAccessdateORG(accessORG.getText());
			bib.setAccessdateTR(accessTR.getText());
		}

		if (publicationtype == 1) {
			bib.setBookTitleEN(booktitelEN.getText());
			bib.setBookTitleORG(booktitelORG.getText());
			bib.setBookTitleTR(booktitelTR.getText());
		}

		if (publicationtype == 5) {
			bib.setChapTitleEN(chaptitEN.getText());
			bib.setChapTitleORG(chaptitORG.getText());
			bib.setChapTitleTR(chaptitTR.getText());
		}

		bib.setComments(comments.getText());

		if (publicationtype == 1 || publicationtype == 5) {
			bib.setEditionEN(editionEN.getText());
			bib.setEditionORG(editionORG.getText());
			bib.setEditionTR(editionTR.getText());
		}

		bib.setFirstEdition(erstauflage.getValue());

		if (publicationtype == 8) { // bleiben
			bib.setMonthEN(monthEN.getText());
			bib.setMonthORG(monthORG.getText());
			bib.setMonthTR(monthTR.getText());
		}

		if (firstEditionComboBox.getValue() != null) {
			bib.setFirstEditionEntry(firstEditionComboBox.getValue());
		}

		bib.setNotes(notes.getText());

		if (publicationtype == 8) {
			bib.setNumberEN(numberEN.getText());
			bib.setNumberORG(numberORG.getText());
			bib.setNumberTR(numberTR.getText());
		}

		bib.setPagesEN(pagesEN.getText());
		bib.setPagesORG(pagesORG.getText());
		bib.setPagesTR(pagesTR.getText());

		if (publicationtype == 6) {
			bib.setProcTitleEN(procEN.getText());
			bib.setProcTitleORG(procORG.getText());
			bib.setProcTitleTR(procTR.getText());
		}

		if (publisherComboBox.getValue() != null) {
			bib.setPublisher(publisherComboBox.getValue());
		}

		if (publicationtype == 8) {
			bib.setSerieEN(seriesEN.getText());
			bib.setSerieORG(seriesORG.getText());
			bib.setSerieTR(seriesTR.getText());
		}

		if (publicationtype == 3) {
			bib.setUniversityEN(uniEN.getText());
			bib.setUniversityORG(uniORG.getText());
			bib.setUniversityTR(uniTR.getText());
		}

		bib.setUnpublished(unpublished.getValue());

		bib.setUri(uri.getText());
		bib.setUrl(url.getText());

		if (publicationtype == 8) {
			bib.setVolumeEN(volumeEN.getText());
			bib.setVolumeORG(volumeORG.getText());
			bib.setVolumeTR(volumeTR.getText());
		}

		bib.setYearEN(yearEN.getValue());
		bib.setYearORG(yearORG.getText());
		bib.setYearTR(yearTR.getText());

		if (publicationtype != 6) {
			for (int i = 0; i < selectedAuthorListStore.size(); i++) {
				AuthorEntry author = selectedAuthorListStore.get(i);
				AuthorAnnotatedRelation relation = new AuthorAnnotatedRelation(author, bib);
				bib.getAuthorAnnotatedList().add(relation);
			}

		}
		for (int i = 0; i < selectedEditorListStore.size(); i++) {
			AuthorEntry editor = selectedEditorListStore.get(i);
			EditorAnnotatedRelation relation = new EditorAnnotatedRelation(bib, editor);
			bib.getEditorAnnotatedList().add(relation);
		}

		dbService.saveAnnotatedBiblographyEntry(bib, new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("fail");
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Boolean result) {
				// TODO Auto-generated method stub
				Window.alert("Entry saved!");
			}
		});

	}

	public void init() {

		authorProps = GWT.create(AuthorProperties.class);
		annotatedBiblographyEntryProps = GWT.create(AnnotatedBiblographyEntryProperties.class);
		publisherProps = GWT.create(PublisherProperties.class);
		publicationTypeProps = GWT.create(PublicationTypeProperties.class);
		authorListStore = new ListStore<AuthorEntry>(authorProps.authorID());
		AnnotatedBiblographyEntryListStore = new ListStore<AnnotatedBiblographyEntry>(annotatedBiblographyEntryProps.annotatedBiblographyID());
		selectedAuthorListStore = new ListStore<AuthorEntry>(authorProps.authorID());
		publicationTypeListStore = new ListStore<PublicationTypeEntry>(publicationTypeProps.publicationTypeID());
		selectedEditorListStore = new ListStore<AuthorEntry>(authorProps.authorID());
		for (PublicationTypeEntry pe : StaticTables.getInstance().getPublicationTypes().values()) {
			publicationTypeListStore.add(pe);
		}

		dbService.getAuthors(new AsyncCallback<ArrayList<AuthorEntry>>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(ArrayList<AuthorEntry> result) {
				authorListStore.clear();
				for (AuthorEntry pe : result) {
					authorListStore.add(pe);
				}
			}
		});

		dbService.getPublisher(new AsyncCallback<ArrayList<PublisherEntry>>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(ArrayList<PublisherEntry> result) {
				publisherListStore.clear();
				for (PublisherEntry pe : result) {
					publisherListStore.add(pe);
				}
			}
		});
	}

	public void createForm() {

		// Overview FramedPanel

		publicationTypeComboBox = new ComboBox<PublicationTypeEntry>(publicationTypeListStore, publicationTypeProps.name(),
				new AbstractSafeHtmlRenderer<PublicationTypeEntry>() {

					@Override
					public SafeHtml render(PublicationTypeEntry item) {
						final PublicationTypeViewTemplates pvTemplates = GWT.create(PublicationTypeViewTemplates.class);
						return pvTemplates.publicationType(item.getName());
					}
				});
		publicationTypeComboBox.setTriggerAction(TriggerAction.ALL);
		publicationTypeComboBox.setEditable(false);
		publicationTypeComboBox.setTypeAhead(false);
		SelectionHandler<PublicationTypeEntry> publicationTypeSelectionHandler = new SelectionHandler<PublicationTypeEntry>() {

			@Override
			public void onSelection(SelectionEvent<PublicationTypeEntry> event) {
				publicationtype = event.getSelectedItem().getPublicationTypeID();
				rebuildMainInput(publicationtype);
			}
		};
		publicationTypeComboBox.addSelectionHandler(publicationTypeSelectionHandler);

		FramedPanel puplicationTypeFP = new FramedPanel();
		puplicationTypeFP.setHeading("Publication Type");
		puplicationTypeFP.add(publicationTypeComboBox);

		if (entry != null) {
			publicationTypeComboBox.setValue(entry.getPublicationType());
		}

		// FramedPanel mainInputFP = new FramedPanel();
		// mainInputFP.setHeading("Literatur");
		// mainInputFP.add(mainInputVLC);

		backgroundoverview.add(puplicationTypeFP, new VerticalLayoutData(1.0, .1));

		ToolButton closeToolButton = new ToolButton(ToolButton.CLOSE);
		closeToolButton.setToolTip("close");
		closeToolButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				Dialog d = new Dialog();
				d.setHeading("Exit Warning!");
				d.setWidget(new HTML("Do you wish to save before exiting?"));
				d.setBodyStyle("fontWeight:bold;padding:13px;");
				d.setPixelSize(300, 100);
				d.setHideOnButtonClick(true);
				d.setPredefinedButtons(PredefinedButton.YES, PredefinedButton.NO, PredefinedButton.CANCEL);
				d.setModal(true);
				d.center();
				d.show();
				d.getButton(PredefinedButton.YES).addSelectHandler(new SelectHandler() {

					@Override
					public void onSelect(SelectEvent event) {
						save();
					}
				});
				d.getButton(PredefinedButton.NO).addSelectHandler(new SelectHandler() {

					@Override
					public void onSelect(SelectEvent event) {
						closeEditor();
					}
				});
			}
		});

		ToolButton saveToolButton = new ToolButton(ToolButton.SAVE);
		saveToolButton.setToolTip("save");
		saveToolButton.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				save();

			}

		});

		mainFP = new FramedPanel();
		mainFP.setHeading("Annotated Biblography");
		mainFP.setSize("900px", "730px"); // here we set the size of the panel
		mainFP.add(backgroundoverview, new VerticalLayoutData(1.0, 1.0));
		mainFP.addTool(saveToolButton);
		mainFP.addTool(closeToolButton);

	}

	public void rebuildMainInput(int publicationtype) {
		// mainInputVLC.clear();
		backgroundoverview.remove(tabpanel);
		tabpanel = new TabPanel();
		backgroundoverview.add(tabpanel, new VerticalLayoutData(1.0, 0.9));

		firstTabVLC = new VerticalLayoutContainer();
		firstsecoundTabVLC = new VerticalLayoutContainer();
		secoundTabVLC = new VerticalLayoutContainer();
		thirdTabVLC = new VerticalLayoutContainer();
		// mainInputVLC.add(tabpanel, new VerticalLayoutData(1.0, 1.0));

		tabpanel.add(firstTabVLC, "1. Basics");
		tabpanel.add(firstsecoundTabVLC, "2. Basics");
		tabpanel.add(secoundTabVLC, "Authors and Editors");
		tabpanel.add(thirdTabVLC, "Others");

		tabpanel.setTabScroll(true);

		firstTabVLC.setWidth("890px");
		firstTabVLC.setHeight("595px");
		firstsecoundTabVLC.setWidth("890px");
		firstsecoundTabVLC.setHeight("595px");
		secoundTabVLC.setWidth("890px");
		secoundTabVLC.setHeight("595px");
		thirdTabVLC.setWidth("890px");
		thirdTabVLC.setHeight("595px");

		horizontBackground = new HorizontalLayoutContainer();

		frame = new FramedPanel();
		frame.setHeading("Titel");
		frame.add(horizontBackground);
		firstTabVLC.add(frame, new VerticalLayoutData(1.0, 1.0 / 6));

		titelEN = new TextField();
		titelORG = new TextField();
		titelTR = new TextField();

		original = new FramedPanel();
		original.setHeading("Original");
		trans = new FramedPanel();
		trans.setHeading("Transkription");
		eng = new FramedPanel();
		eng.setHeading("English");

		original.add(titelORG);
		trans.add(titelTR);
		eng.add(titelEN);

		horizontBackground.add(eng, new HorizontalLayoutData(1.0 / 3, 1.0));
		horizontBackground.add(original, new HorizontalLayoutData(1.0 / 3, 1.0));
		horizontBackground.add(trans, new HorizontalLayoutData(1.0 / 3, 1.0));

		if (entry != null) {
			titelEN.setText(entry.getTitleEN());
			titelORG.setText(entry.getTitleORG());
			titelTR.setText(entry.getTitleTR());
		}

		if (publicationtype == 6) {
			horizontBackground = new HorizontalLayoutContainer();
			procEN = new TextField();
			procORG = new TextField();
			procTR = new TextField();

			original = new FramedPanel();
			original.setHeading("Original");
			trans = new FramedPanel();
			trans.setHeading("Transkription");
			eng = new FramedPanel();
			eng.setHeading("English");

			original.add(procORG);
			trans.add(procTR);
			eng.add(procEN);

			horizontBackground.add(eng, new HorizontalLayoutData(1.0 / 3, 1.0));
			horizontBackground.add(original, new HorizontalLayoutData(1.0 / 3, 1.0));
			horizontBackground.add(trans, new HorizontalLayoutData(1.0 / 3, 1.0));

			frame = new FramedPanel();
			frame.setHeading("Proceedings Title");
			frame.add(horizontBackground);
			firstTabVLC.add(frame, new VerticalLayoutData(1.0, 1.0 / 6));

			if (entry != null) {
				procEN.setText(entry.getProcTitleEN());
				procORG.setText(entry.getProcTitleORG());
				procTR.setText(entry.getProcTitleTR());
			}
		}

		if (publicationtype == 5) {
			horizontBackground = new HorizontalLayoutContainer();
			chaptitEN = new TextField();
			chaptitORG = new TextField();
			chaptitTR = new TextField();

			original = new FramedPanel();
			original.setHeading("Original");
			trans = new FramedPanel();
			trans.setHeading("Transkription");
			eng = new FramedPanel();
			eng.setHeading("English");

			original.add(chaptitORG);
			trans.add(chaptitTR);
			eng.add(chaptitEN);

			horizontBackground.add(eng, new HorizontalLayoutData(1.0 / 3, 1.0));
			horizontBackground.add(original, new HorizontalLayoutData(1.0 / 3, 1.0));
			horizontBackground.add(trans, new HorizontalLayoutData(1.0 / 3, 1.0));

			frame = new FramedPanel();
			frame.setHeading("Chapter Title");
			frame.add(horizontBackground);
			firstTabVLC.add(frame, new VerticalLayoutData(1.0, 1.0 / 6));

			if (entry != null) {
				chaptitEN.setText(entry.getChapTitleEN());
				chaptitORG.setText(entry.getChapTitleORG());
				chaptitTR.setText(entry.getChapTitleTR());
			}
		}

		if (publicationtype == 1) {

			horizontBackground = new HorizontalLayoutContainer();
			booktitelEN = new TextField();
			booktitelORG = new TextField();
			booktitelTR = new TextField();

			original = new FramedPanel();
			original.setHeading("Original");
			trans = new FramedPanel();
			trans.setHeading("Transkription");
			eng = new FramedPanel();
			eng.setHeading("English");

			original.add(booktitelORG);
			trans.add(booktitelTR);
			eng.add(booktitelEN);

			horizontBackground.add(eng, new HorizontalLayoutData(1.0 / 3, 1.0));
			horizontBackground.add(original, new HorizontalLayoutData(1.0 / 3, 1.0));
			horizontBackground.add(trans, new HorizontalLayoutData(1.0 / 3, 1.0));

			frame = new FramedPanel();
			frame.setHeading("Booktitle");
			frame.add(horizontBackground);
			firstTabVLC.add(frame, new VerticalLayoutData(1.0, 1.0 / 6));

			if (entry != null) {
				booktitelEN.setText(entry.getBookTitleEN());
				booktitelORG.setText(entry.getBookTitleORG());
				booktitelTR.setText(entry.getBookTitleTR());
			}
		}

		if (publicationtype == 3) {
			horizontBackground = new HorizontalLayoutContainer();
			uniEN = new TextField();
			uniORG = new TextField();
			uniTR = new TextField();

			original = new FramedPanel();
			original.setHeading("Original");
			trans = new FramedPanel();
			trans.setHeading("Transkription");
			eng = new FramedPanel();
			eng.setHeading("English");

			original.add(uniORG);
			trans.add(uniTR);
			eng.add(uniEN);

			horizontBackground.add(eng, new HorizontalLayoutData(1.0 / 3, 1.0));
			horizontBackground.add(original, new HorizontalLayoutData(1.0 / 3, 1.0));
			horizontBackground.add(trans, new HorizontalLayoutData(1.0 / 3, 1.0));

			frame = new FramedPanel();
			frame.setHeading("University");
			frame.add(horizontBackground);
			firstTabVLC.add(frame, new VerticalLayoutData(1.0, 1.0 / 6));

			if (entry != null) {
				uniEN.setText(entry.getUniversityEN());
				uniORG.setText(entry.getUniversityORG());
				uniTR.setText(entry.getUniversityTR());
			}

		}

		if (publicationtype == 8) { // achtung hier muss sie bleiben

			horizontBackground = new HorizontalLayoutContainer();
			numberEN = new TextField();
			numberORG = new TextField();
			numberTR = new TextField();

			original = new FramedPanel();
			original.setHeading("Original");
			trans = new FramedPanel();
			trans.setHeading("Transkription");
			eng = new FramedPanel();
			eng.setHeading("English");

			original.add(numberORG);
			trans.add(numberTR);
			eng.add(numberEN);

			horizontBackground.add(eng, new HorizontalLayoutData(1.0 / 3, 1.0));
			horizontBackground.add(original, new HorizontalLayoutData(1.0 / 3, 1.0));
			horizontBackground.add(trans, new HorizontalLayoutData(1.0 / 3, 1.0));

			frame = new FramedPanel();
			frame.setHeading("Number");
			frame.add(horizontBackground);
			firstTabVLC.add(frame, new VerticalLayoutData(1.0, 1.0 / 6));

			if (entry != null) {
				numberEN.setText(entry.getNumberEN());
				numberORG.setText(entry.getNumberORG());
				numberTR.setText(entry.getNumberTR());
			}
		}

		if (publicationtype == 7) {
			horizontBackground = new HorizontalLayoutContainer();
			accessEN = new TextField();
			accessORG = new TextField();
			accessTR = new TextField();

			original = new FramedPanel();
			original.setHeading("Original");
			trans = new FramedPanel();
			trans.setHeading("Transkription");
			eng = new FramedPanel();
			eng.setHeading("English");

			original.add(accessORG);
			trans.add(accessTR);
			eng.add(accessEN);

			horizontBackground.add(eng, new HorizontalLayoutData(1.0 / 3, 1.0));
			horizontBackground.add(original, new HorizontalLayoutData(1.0 / 3, 1.0));
			horizontBackground.add(trans, new HorizontalLayoutData(1.0 / 3, 1.0));

			frame = new FramedPanel();
			frame.setHeading("Access Date");
			frame.add(horizontBackground, new HorizontalLayoutData(1.0, 1.0));
			thirdTabVLC.add(frame, new VerticalLayoutData(1.0, 1.0 / 6));

			if (entry != null) {
				accessEN.setText(entry.getAccessdateEN());
				accessORG.setText(entry.getAccessdateORG());
				accessTR.setText(entry.getAccessdateTR());
			}
		}

		horizontBackground = new HorizontalLayoutContainer();
		titeladdonEN = new TextField();
		titeladdonORG = new TextField();
		titeladdonTR = new TextField();

		original = new FramedPanel();
		original.setHeading("Original");
		trans = new FramedPanel();
		trans.setHeading("Transkription");
		eng = new FramedPanel();
		eng.setHeading("English");

		original.add(titeladdonORG);
		trans.add(titeladdonTR);
		eng.add(titeladdonEN);

		horizontBackground.add(eng, new HorizontalLayoutData(1.0 / 3, 1.0));
		horizontBackground.add(original, new HorizontalLayoutData(1.0 / 3, 1.0));
		horizontBackground.add(trans, new HorizontalLayoutData(1.0 / 3, 1.0));

		frame = new FramedPanel();
		frame.setHeading("Titleaddon");
		frame.add(horizontBackground);
		firstTabVLC.add(frame, new VerticalLayoutData(1.0, 1.0 / 6));

		if (entry != null) {
			titeladdonEN.setText(entry.getTitleaddonEN());
			titeladdonORG.setText(entry.getTitleaddonORG());
			titeladdonTR.setText(entry.getTitleaddonTR());
		}

		publisherComboBox = new ComboBox<PublisherEntry>(publisherListStore, publisherProps.name(),
				new AbstractSafeHtmlRenderer<PublisherEntry>() {

					@Override
					public SafeHtml render(PublisherEntry item) {
						final PublisherViewTemplates pvTemplates = GWT.create(PublisherViewTemplates.class);
						return pvTemplates.publisher(item.getName());
					}
				});
		horizontBackground = new HorizontalLayoutContainer();
		horizontBackground.add(publisherComboBox, new HorizontalLayoutData(1.0, 1.0));
		frame = new FramedPanel();
		frame.setHeading("Publisher");
		frame.add(horizontBackground, new VerticalLayoutData(1.0, 1.0));
		secoundTabVLC.add(frame, new VerticalLayoutData(1.0, 1.0 / 6));

		if (entry != null) {
			publisherComboBox.setValue(entry.getPublisher());
		}

		authorSelection = new DualListField<AuthorEntry, String>(authorListStore, selectedAuthorListStore, authorProps.name(), new TextCell());

		editorSelection = new DualListField<AuthorEntry, String>(authorListStore, selectedEditorListStore, authorProps.name(), new TextCell());

		if (publicationtype != 6) {
			horizontBackground = new HorizontalLayoutContainer();
			horizontBackground.add(authorSelection, new HorizontalLayoutData(1.0, 1.0));
			frame = new FramedPanel();
			frame.setHeading("Author");
			frame.add(horizontBackground, new VerticalLayoutData(1.0, 1.0));
			secoundTabVLC.add(frame, new VerticalLayoutData(1.0, 1.0 / 3));
		}

		horizontBackground = new HorizontalLayoutContainer();
		horizontBackground.add(editorSelection, new HorizontalLayoutData(1.0, 1.0));
		frame = new FramedPanel();
		frame.setHeading("Editor");
		frame.add(horizontBackground, new VerticalLayoutData(1.0, 1.0));
		secoundTabVLC.add(frame, new VerticalLayoutData(1.0, 1.0 / 3));

		if (publicationtype == 8) { // hier muss sie bleiben
			horizontBackground = new HorizontalLayoutContainer();
			seriesEN = new TextField();
			seriesORG = new TextField();
			seriesTR = new TextField();

			original = new FramedPanel();
			original.setHeading("Original");
			trans = new FramedPanel();
			trans.setHeading("Transkription");
			eng = new FramedPanel();
			eng.setHeading("English");

			original.add(seriesORG);
			trans.add(seriesTR);
			eng.add(seriesEN);

			horizontBackground.add(eng, new HorizontalLayoutData(1.0 / 3, 1.0));
			horizontBackground.add(original, new HorizontalLayoutData(1.0 / 3, 1.0));
			horizontBackground.add(trans, new HorizontalLayoutData(1.0 / 3, 1.0));

			frame = new FramedPanel();
			frame.setHeading("Serie");
			frame.add(horizontBackground);
			firstTabVLC.add(frame, new VerticalLayoutData(1.0, 1.0 / 6));

			if (entry != null) {
				seriesEN.setText(entry.getSerieEN());
				seriesORG.setText(entry.getSerieORG());
				seriesTR.setText(entry.getSerieTR());
			}
		}

		if (publicationtype == 1 || publicationtype == 5) {

			horizontBackground = new HorizontalLayoutContainer();
			editionEN = new TextField();
			editionORG = new TextField();
			editionTR = new TextField();

			original = new FramedPanel();
			original.setHeading("Original");
			trans = new FramedPanel();
			trans.setHeading("Transkription");
			eng = new FramedPanel();
			eng.setHeading("English");

			original.add(editionORG);
			trans.add(editionTR);
			eng.add(editionEN);

			horizontBackground.add(eng, new HorizontalLayoutData(1.0 / 3, 1.0));
			horizontBackground.add(original, new HorizontalLayoutData(1.0 / 3, 1.0));
			horizontBackground.add(trans, new HorizontalLayoutData(1.0 / 3, 1.0));

			frame = new FramedPanel();
			frame.setHeading("Edition");
			frame.add(horizontBackground);
			firstTabVLC.add(frame, new VerticalLayoutData(1.0, 1.0 / 6));

			if (entry != null) {
				editionEN.setText(entry.getEditionEN());
				editionORG.setText(entry.getEditionORG());
				editionTR.setText(entry.getEditionTR());
			}

		}
		if (publicationtype == 8) {
			horizontBackground = new HorizontalLayoutContainer();
			volumeEN = new TextField();
			volumeORG = new TextField();
			volumeTR = new TextField();

			original = new FramedPanel();
			original.setHeading("Original");
			trans = new FramedPanel();
			trans.setHeading("Transkription");
			eng = new FramedPanel();
			eng.setHeading("English");

			original.add(volumeORG);
			trans.add(volumeTR);
			eng.add(volumeEN);

			horizontBackground.add(eng, new HorizontalLayoutData(1.0 / 3, 1.0));
			horizontBackground.add(original, new HorizontalLayoutData(1.0 / 3, 1.0));
			horizontBackground.add(trans, new HorizontalLayoutData(1.0 / 3, 1.0));

			frame = new FramedPanel();
			frame.setHeading("Volume");
			frame.add(horizontBackground);
			firstTabVLC.add(frame, new VerticalLayoutData(1.0, 1.0 / 6));

			if (entry != null) {
				volumeEN.setText(entry.getVolumeEN());
				volumeORG.setText(entry.getVolumeORG());
				volumeTR.setText(entry.getVolumeTR());
			}

		}
		horizontBackground = new HorizontalLayoutContainer();
		yearEN = new NumberField<Integer>(new NumberPropertyEditor.IntegerPropertyEditor());
		DateWrapper dw = new DateWrapper(); // we always want to use the current year as max year
		yearEN.addValidator(new MaxNumberValidator<Integer>(dw.getFullYear()));
		yearEN.setAllowNegative(false);

		yearORG = new TextField();
		yearTR = new TextField();

		original = new FramedPanel();
		original.setHeading("Original");
		trans = new FramedPanel();
		trans.setHeading("Transkription");
		eng = new FramedPanel();
		eng.setHeading("English");

		original.add(yearORG);
		trans.add(yearTR);
		eng.add(yearEN);

		horizontBackground.add(eng, new HorizontalLayoutData(1.0 / 3, 1.0));
		horizontBackground.add(original, new HorizontalLayoutData(1.0 / 3, 1.0));
		horizontBackground.add(trans, new HorizontalLayoutData(1.0 / 3, 1.0));

		frame = new FramedPanel();
		frame.setHeading("Year");
		frame.add(horizontBackground);
		firstsecoundTabVLC.add(frame, new VerticalLayoutData(1.0, 1.0 / 8));

		if (entry != null) {
			yearEN.setValue(entry.getYearEN());
			yearORG.setText(entry.getYearORG());
			yearTR.setText(entry.getYearTR());
		}

		if (publicationtype == 8) { // bleiben
			horizontBackground = new HorizontalLayoutContainer();
			monthEN = new TextField();
			monthORG = new TextField();
			monthTR = new TextField();

			original = new FramedPanel();
			original.setHeading("Original");
			trans = new FramedPanel();
			trans.setHeading("Transkription");
			eng = new FramedPanel();
			eng.setHeading("English");

			original.add(monthORG);
			trans.add(monthTR);
			eng.add(monthEN);

			horizontBackground.add(eng, new HorizontalLayoutData(1.0 / 3, 1.0));
			horizontBackground.add(original, new HorizontalLayoutData(1.0 / 3, 1.0));
			horizontBackground.add(trans, new HorizontalLayoutData(1.0 / 3, 1.0));

			frame = new FramedPanel();
			frame.setHeading("Month");
			frame.add(horizontBackground);
			firstsecoundTabVLC.add(frame, new VerticalLayoutData(1.0, 1.0 / 8));

			if (entry != null) {
				monthEN.setText(entry.getMonthEN());
				monthORG.setText(entry.getMonthORG());
				monthTR.setText(entry.getMonthTR());
			}

		}

		horizontBackground = new HorizontalLayoutContainer();
		pagesEN = new TextField();
		pagesORG = new TextField();
		pagesTR = new TextField();

		original = new FramedPanel();
		original.setHeading("Original");
		trans = new FramedPanel();
		trans.setHeading("Transkription");
		eng = new FramedPanel();
		eng.setHeading("English");

		original.add(pagesORG);
		trans.add(pagesTR);
		eng.add(pagesEN);

		horizontBackground.add(eng, new HorizontalLayoutData(1.0 / 3, 1.0));
		horizontBackground.add(original, new HorizontalLayoutData(1.0 / 3, 1.0));
		horizontBackground.add(trans, new HorizontalLayoutData(1.0 / 3, 1.0));
		frame = new FramedPanel();
		frame.setHeading("Pages");
		frame.add(horizontBackground);
		firstTabVLC.add(frame, new VerticalLayoutData(1.0, 1.0 / 6));

		if (entry != null) {
			pagesEN.setText(entry.getPagesEN());
			pagesORG.setText(entry.getPagesORG());
			pagesTR.setText(entry.getPagesTR());
		}

		comments = new TextArea();
		horizontBackground = new HorizontalLayoutContainer();
		horizontBackground.add(comments, new HorizontalLayoutData(1.0, 1.0));
		frame = new FramedPanel();
		frame.setHeading("Comments");
		frame.add(horizontBackground, new HorizontalLayoutData(1.0, 1.0));
		thirdTabVLC.add(frame, new VerticalLayoutData(1.0, 1.0 / 7));

		if (entry != null) {
			comments.setText(entry.getComments());
		}

		notes = new TextArea();
		horizontBackground = new HorizontalLayoutContainer();
		horizontBackground.add(notes, new HorizontalLayoutData(1.0, 1.0));
		frame = new FramedPanel();
		frame.setHeading("Notes");
		frame.add(horizontBackground, new HorizontalLayoutData(1.0, 1.0));
		thirdTabVLC.add(frame, new VerticalLayoutData(1.0, 1.0 / 7));
		if (entry != null) {
			notes.setText(entry.getNotes());
		}

		url = new TextField();
		horizontBackground = new HorizontalLayoutContainer();
		horizontBackground.add(url, new HorizontalLayoutData(1.0, 1.0));
		frame = new FramedPanel();
		frame.setHeading("URL");
		frame.add(horizontBackground, new HorizontalLayoutData(1.0, 1.0));
		thirdTabVLC.add(frame, new VerticalLayoutData(1.0, 1.0 / 16));
		if (entry != null) {
			url.setText(entry.getUrl());
		}

		uri = new TextField();
		horizontBackground = new HorizontalLayoutContainer();
		horizontBackground.add(uri, new HorizontalLayoutData(1.0, 1.0));
		frame = new FramedPanel();
		frame.setHeading("URI");
		frame.add(horizontBackground, new HorizontalLayoutData(1.0, 1.0));
		thirdTabVLC.add(frame, new VerticalLayoutData(1.0, 1.0 / 16));

		if (entry != null) {
			uri.setText(entry.getUri());
		}

		unpublished = new CheckBox();
		horizontBackground = new HorizontalLayoutContainer();
		horizontBackground.add(unpublished, new HorizontalLayoutData(1.0, 1.0));
		frame = new FramedPanel();
		frame.setHeading("Unpublished");
		frame.add(horizontBackground, new HorizontalLayoutData(1.0, 1.0));
		thirdTabVLC.add(frame, new VerticalLayoutData(1.0, 1.0 / 16));
		if (entry != null) {
			unpublished.setValue(entry.isUnpublished());
		}

		erstauflage = new CheckBox();
		horizontBackground = new HorizontalLayoutContainer();
		horizontBackground.add(erstauflage, new HorizontalLayoutData(1.0, 1.0));
		erstauflage.setValue(true);
		frame = new FramedPanel();
		frame.setHeading("FirstEdition");
		frame.add(horizontBackground, new HorizontalLayoutData(1.0, 1.0));
		thirdTabVLC.add(frame, new VerticalLayoutData(1.0, 1.0 / 16));

		if (entry != null) {
			erstauflage.setValue(entry.isFirstEdition());
		}

		firstEditionComboBox = new ComboBox<AnnotatedBiblographyEntry>(AnnotatedBiblographyEntryListStore,
				annotatedBiblographyEntryProps.titleEN(), new AbstractSafeHtmlRenderer<AnnotatedBiblographyEntry>() {

					@Override
					public SafeHtml render(AnnotatedBiblographyEntry item) {
						final AnnotatedBiblographyEntryViewTemplates pvTemplates = GWT.create(AnnotatedBiblographyEntryViewTemplates.class);
						return pvTemplates.AnnotatedBiblographyEntry(item.getTitleEN());
					}
				});

		if (entry != null) {
			firstEditionComboBox.setValue(entry.getFirstEditionEntry());
		}

		ValueChangeHandler<Boolean> checkBoxHandler = new ValueChangeHandler<Boolean>() {

			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				if (event.getValue() == false) {
					framefirstedition = new FramedPanel();
					framefirstedition.setHeading("Choose First Edition");
					framefirstedition.add(firstEditionComboBox);
					thirdTabVLC.add(framefirstedition);
				} else {
					thirdTabVLC.remove(framefirstedition);
				}

			}
		};

		erstauflage.addValueChangeHandler(checkBoxHandler);
	}

}

interface PublisherViewTemplates extends XTemplates {
	@XTemplate("<div>{name}</div>")
	SafeHtml publisher(String name);
}

interface AnnotatedBiblographyEntryViewTemplates extends XTemplates {
	@XTemplate("<div>{name}</div>")
	SafeHtml AnnotatedBiblographyEntry(String name);
}

interface PublicationTypeViewTemplates extends XTemplates {
	@XTemplate("<div>{name}</div>")
	SafeHtml publicationType(String name);
}

interface PublisherProperties extends PropertyAccess<PublisherEntry> {
	ModelKeyProvider<PublisherEntry> publisherID();

	LabelProvider<PublisherEntry> name();
}

interface AnnotatedBiblographyEntryProperties extends PropertyAccess<AnnotatedBiblographyEntry> {
	ModelKeyProvider<AnnotatedBiblographyEntry> annotatedBiblographyID();

	LabelProvider<AnnotatedBiblographyEntry> titleEN();
}

interface PublicationTypeProperties extends PropertyAccess<PublicationTypeEntry> {
	ModelKeyProvider<PublicationTypeEntry> publicationTypeID();

	LabelProvider<PublicationTypeEntry> name();
}

interface AuthorProperties extends PropertyAccess<AuthorEntry> {
	ModelKeyProvider<AuthorEntry> authorID();

	ValueProvider<AuthorEntry, String> name();
}
