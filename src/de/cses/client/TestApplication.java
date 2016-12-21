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

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.fx.client.Draggable;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.Viewport;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

import de.cses.client.bibliography.AuthorEditor;
import de.cses.client.bibliography.AuthorEditorListener;
import de.cses.client.caves.Antechamber;
import de.cses.client.caves.CaveType;
import de.cses.client.caves.CaveEditor;
import de.cses.client.caves.Cella;
import de.cses.client.caves.Districts;
import de.cses.client.caves.Niches;
import de.cses.client.depictions.DepictionEditor;
import de.cses.client.depictions.DepictionEditorListener;
import de.cses.client.images.ImageEditor;
import de.cses.client.images.ImageEditorListener;
import de.cses.client.images.ImageUploadListener;
import de.cses.client.images.ImageUploader;
import de.cses.client.images.PhotographerEditor;
import de.cses.client.ornamentic.Ornamentic;
import de.cses.client.walls.Walls;
import de.cses.shared.AuthorEntry;
import de.cses.shared.DepictionEntry;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class TestApplication implements EntryPoint, ImageEditorListener {

	static Ornamentic ornamentic = new Ornamentic();

	private TabLayoutPanel main;

	private PopupPanel depictionEditorPanel;

	private PopupPanel imageEditorPanel;

	private PopupPanel imageUploadPanel;

	private PopupPanel authorEditorPanel;

	private PopupPanel caveEditorPanel;

	/**
	 * This is the entry point method.
	 */

	public void onModuleLoad() {

		/*
		 * apparently, the viewport is important since it guarantees that the
		 * content of all tabs will be updated in the background and look nice and
		 * clean all the time
		 */
		main = new TabLayoutPanel(3.0, Unit.EM);
		Viewport v = new Viewport();
		v.add(main);
		RootPanel.get().add(v); // use RootPanel, not RootLayoutPanel here!

		Ornamentic co = new Ornamentic();
		CaveEditor caveEditor = new CaveEditor(0);
		Cella cella = new Cella();
		CaveType caveType = new CaveType();
		Niches niches = new Niches();
		Antechamber antechamber = new Antechamber();
		Districts districts = new Districts();

		// ImageUploader imageUploader = new ImageUploader(imgEditor);
		PhotographerEditor pEditor = new PhotographerEditor();

		main.add(co.asWidget(), "Ornamentic Editor");
		main.add(caveType.asWidget(), "Cave Type Editor");
		// main.add(niches.asWidget(), "Niches Editor");
		// main.add(antechamber.asWidget(), "Antechamber Editor");
		// main.add(districts.asWidget(), "District Editor");

		// we are using FlowLayoutContainer

		FlowLayoutContainer flowLCcella = new FlowLayoutContainer();
		flowLCcella.setScrollMode(ScrollMode.AUTOY);
		MarginData layoutDatacella = new MarginData(new Margins(0, 5, 0, 0));
		flowLCcella.add(cella, layoutDatacella);
		main.add(flowLCcella, "Cella Editor");

		main.add(cella, "Cella Editor");
		// main.add(pEditor, "Photographer Editor");
		 Walls wall = new Walls();
		 main.add(wall, "Wall Editor");

		TextButton imgEditorButton = new TextButton("Edit Image");
		imageEditorPanel = new PopupPanel(false);
		imageEditorPanel.add(new ImageEditor(this));
		new Draggable(imageEditorPanel);
		imgEditorButton.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				imageEditorPanel.setGlassEnabled(true);
				imageEditorPanel.center();
				imageEditorPanel.show();
			}
		});
//		main.add(imgEditorButton, "Image Editor");

		TextButton depictionButton = new TextButton("Edit Depiction");
		depictionEditorPanel = new PopupPanel(false);
		depictionEditorPanel.add(new DepictionEditor(0, new DepictionEditorListener() {

			@Override
			public void depictionSaved(DepictionEntry depictionEntry) {
				depictionEditorPanel.hide();
			}
		}));
		new Draggable(depictionEditorPanel);
		depictionButton.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				depictionEditorPanel.setGlassEnabled(true);
				// depictionEditorPanel.setModal(true);
				depictionEditorPanel.center();
				depictionEditorPanel.show();
			}
		});

		TextButton uploadButton = new TextButton("Image Uploader");
		imageUploadPanel = new PopupPanel(false);
		imageUploadPanel.add(new ImageUploader(new ImageUploadListener() {

			@Override
			public void uploadCompleted() {
				imageUploadPanel.hide();
			}
		}));
		new Draggable(imageUploadPanel);
		uploadButton.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				imageUploadPanel.setGlassEnabled(true);
				imageUploadPanel.center();
				imageUploadPanel.show();
			}
		});

		TextButton authorEditorButton = new TextButton("Author Editor");
		authorEditorPanel = new PopupPanel(false);
		authorEditorPanel.add(new AuthorEditor(0, new AuthorEditorListener() {

			@Override
			public void authorSaved(AuthorEntry entry) {
				authorEditorPanel.hide();
			}
		}));
		new Draggable(authorEditorPanel);
		authorEditorButton.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				authorEditorPanel.setGlassEnabled(true);
				authorEditorPanel.center();
				authorEditorPanel.show();
			}
		});
		
		TextButton caveEditorButton = new TextButton("Cave Editor");
		caveEditorPanel = new PopupPanel(false);
		caveEditorPanel.add(new CaveEditor(0));
		new Draggable(caveEditorPanel);
		caveEditorButton.addSelectHandler(new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				caveEditorPanel.setGlassEnabled(true);
				caveEditorPanel.center();
				caveEditorPanel.show();
			}
		});

		VerticalPanel vp = new VerticalPanel();
		vp.add(depictionButton);
		vp.add(uploadButton);
		vp.add(authorEditorButton);
		vp.add(imgEditorButton);
		vp.add(caveEditorButton);

		main.add(vp, "Editor testing");

	}

	/* (non-Javadoc)
	 * @see de.cses.client.images.ImageEditorListener#closeImageEditor()
	 */
	@Override
	public void closeImageEditor() {
		imageEditorPanel.hide();
	}

}