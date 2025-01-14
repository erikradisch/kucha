/*
 * Copyright 2016-2017 
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
package de.cses.client.images;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.text.shared.AbstractSafeHtmlRenderer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.SimpleSafeHtmlCell;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.XTemplates.XTemplate;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.ListViewSelectionModel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.button.ToolButton;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.ListField;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;

import de.cses.client.DatabaseService;
import de.cses.client.DatabaseServiceAsync;
import de.cses.client.Util;
import de.cses.client.user.UserLogin;
import de.cses.shared.AbstractEntry;
import de.cses.shared.ImageEntry;
import de.cses.shared.ImageSearchEntry;

public class ImageSelector implements IsWidget {

	private ListStore<ImageEntry> imageEntryList;
	private ImageProperties properties;
	private ListView<ImageEntry, ImageEntry> imageListView;
	private ArrayList<ImageSelectorListener> selectorListener;

	/**
	 * Create a remote service proxy to talk to the server-side service.
	 */
	private final DatabaseServiceAsync dbService = GWT.create(DatabaseService.class);
	private FlowLayoutContainer imageContainer;
	private ImageFilter imgFilter;
	private FramedPanel mainPanel = null;
//	private PopupPanel zoomPanel;
//	protected String zoomImageUri;
//	protected Window loadZoomInfoWindow;
//	private Image zoomImage;

	interface ImageProperties extends PropertyAccess<ImageEntry> {
		ModelKeyProvider<ImageEntry> imageID();
		LabelProvider<ImageEntry> title();
	}

	/**
	 * Creates the view how a thumbnail of an image entry will be shown currently
	 * we are relying on the url of the image until we have user management
	 * implemented and protect images from being viewed from the outside without
	 * permission
	 * 
	 * @author alingnau
	 *
	 */
	interface ImageViewTemplates extends XTemplates {
//		@XTemplate("<div style='border-style: solid; border-color: #99ff66; border-width: 3px;'><img src=\"{imageUri}\" style=\"width: 400px; height: auto; align-content: center; margin: 10px;\"><br>{title}<br> {shortName}")
		@XTemplate("<figure style='border-style: solid; border-color: #99ff66; border-width: 3px; margin: 0;'>"
				+ "<img src='{imageUri}' style='position: relative; padding: 5px; width: 400px; background: white;'>"
				+ "<figcaption style='font-size:12px; padding: 10px; text-align: center;'>{shortName} ({imageFormat})<br><div style='font-size:10px;'>{title}</div></figcaption></figure>")
		SafeHtml publicImage(SafeUri imageUri, String title, String shortName, String imageFormat);

//		@XTemplate("<div style='border-style: solid; border-color: #ff1a1a; border-width: 3px;'><img src=\"{imageUri}\" style=\"width: 400px; height: auto; align-content: center; margin: 10px;\"><br>{title}<br> {shortName}")
		@XTemplate("<figure style='border-style: solid; border-color: #ff1a1a; border-width: 3px; margin: 0;'>"
				+ "<img src='{imageUri}' style='position: relative; padding: 5px; width: 400px; background: white;'>"
				+ "<figcaption style='font-size:12px; padding: 10px; text-align: center;'>{shortName} ({imageFormat})<br><div style='font-size:10px;'>{title}</div></figcaption></figure>")
		SafeHtml nonPublicImage(SafeUri imageUri, String title, String shortName, String imageFormat);
	}

	/**
	 * 
	 * @param listener
	 */
	public ImageSelector(ImageSelectorListener listener) {
		selectorListener = new ArrayList<ImageSelectorListener>();
		selectorListener.add(listener);
		properties = GWT.create(ImageProperties.class);
		imageEntryList = new ListStore<ImageEntry>(properties.imageID());
	}

	@Override
	public Widget asWidget() {
		if (mainPanel == null) {
			initPanel();
		}
		return mainPanel;
	}

	private void initPanel() {

		mainPanel = new FramedPanel();
		mainPanel.setHeading("Image Selector");
		mainPanel.setSize("750", "500");
		
		imageContainer = new FlowLayoutContainer();
		imageContainer.setSize("600px", "600px");
		imageContainer.setScrollMode(ScrollMode.AUTO);

//		zoomPanel = new PopupPanel(true);
//		zoomPanel.add(imageContainer);
//		loadZoomInfoWindow = new Window();

		
		imgFilter = new ImageFilter("Selector Filter");
		
		imageListView = new ListView<ImageEntry, ImageEntry>(imageEntryList, new IdentityValueProvider<ImageEntry>(), new SimpleSafeHtmlCell<ImageEntry>(new AbstractSafeHtmlRenderer<ImageEntry>() {
			final ImageViewTemplates imageViewTemplates = GWT.create(ImageViewTemplates.class);

			public SafeHtml render(ImageEntry item) {
				SafeUri imageUri = UriUtils.fromString("resource?imageID=" + item.getImageID() + "&thumb=700" + UserLogin.getInstance().getUsernameSessionIDParameterForUri());
				if (item.getAccessLevel() == AbstractEntry.ACCESS_LEVEL_PUBLIC) {
					return imageViewTemplates.publicImage(imageUri, item.getTitle(), item.getShortName(), item.getFilename().substring(item.getFilename().lastIndexOf(".")+1).toUpperCase());
				} else {
					return imageViewTemplates.nonPublicImage(imageUri, item.getTitle(), item.getShortName(), item.getFilename().substring(item.getFilename().lastIndexOf(".")+1).toUpperCase());
				}
			}

		}));
		imageListView.getSelectionModel().setSelectionMode(SelectionMode.SIMPLE);

		// we need to remove this due to multiple seleciton!!
//		imageListView.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<ImageEntry>() {
//
//			@Override
//			public void onSelectionChanged(SelectionChangedEvent<ImageEntry> event) {
//				ImageEntry item = event.getSelection().get(0);
//				zoomImageUri = "/resource?imageID=" + item.getImageID() + UserLogin.getInstance().getUsernameSessionIDParameterForUri();
//			}
//		});
//
		imageListView.setSize("1.0", "1.0");

		ListField<ImageEntry, ImageEntry> lf = new ListField<ImageEntry, ImageEntry>(imageListView);
		
		lf.setSize("1.0", "1.0");

		
//		zoomImage = Image.wrap( Document.get().createImageElement() );
//		zoomImage.addLoadHandler(new LoadHandler() {
//			
//			@Override
//			public void onLoad(LoadEvent event) {
//				loadZoomInfoWindow.hide();
//			}
//		});
//		imageContainer.add(zoomImage);

		TextButton selectButton = new TextButton("Select");

		selectButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				ArrayList<ImageEntry> selectedEntries = new ArrayList<ImageEntry>();
				for (ImageEntry ie : imageListView.getSelectionModel().getSelectedItems()) {
					selectedEntries.add(ie);
				}
				for (ImageSelectorListener listener : selectorListener) {
					listener.imageSelected(selectedEntries);
				}
			}
		});

		TextButton cancelButton = new TextButton("Cancel");
		cancelButton.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				for (ImageSelectorListener listener : selectorListener) {
					listener.imageSelected(null);
				}
			}
		});

		/**
		 * here we add the search for image titles
		 */
		TextButton searchButton = new TextButton("search");
		searchButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				refreshImages();
			}
		});
		TextButton resetButton = new TextButton("reset");
		resetButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				imageEntryList.clear();
			}
		});

		HorizontalLayoutContainer hlc = new HorizontalLayoutContainer();

		ContentPanel cp = new ContentPanel();
		cp.setHeaderVisible(false);
		cp.add(imgFilter);
		cp.addButton(searchButton);
		cp.addButton(resetButton);
		hlc.add(cp, new HorizontalLayoutData(.4, 1.0));
		
		ToolButton infoTB = new ToolButton(ToolButton.QUESTION);
		infoTB.addSelectHandler(new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				PopupPanel dialog = new PopupPanel();
				FramedPanel infoDialogFP = new FramedPanel();
				infoDialogFP.setHeading("Colour schema");
				VerticalPanel infoVP = new VerticalPanel();
				infoVP.add(new HTML("<div><label style='font-size: 12px; color: #004d00;'>Open Access Image</label></div>"));
				infoVP.add(new HTML("<div><label style='font-size: 12px; color: #ff1a1a;'>Non Open Access Image</label></div>"));
				infoDialogFP.add(infoVP);
				TextButton okButton = new TextButton("OK");
				okButton.addSelectHandler(new SelectHandler() {
					
					@Override
					public void onSelect(SelectEvent event) {
						dialog.hide();
					}
				});
				infoDialogFP.addButton(okButton);
				dialog.add(infoDialogFP);
				dialog.setModal(true);
				dialog.setGlassEnabled(true);
				dialog.center();
			}
		});

//		ToolButton zoomTB = new ToolButton(ToolButton.MAXIMIZE);
//		zoomTB.setToolTip(Util.createToolTip("View selected image in full.", "A new tab will be opened."));
//		zoomTB.addSelectHandler(new SelectHandler() {
//			@Override
//			public void onSelect(SelectEvent event) {
//				com.google.gwt.user.client.Window.open(zoomImageUri,"_blank",null);
//			}
//		});

		FramedPanel imageListViewFP = new FramedPanel();
		imageListViewFP.setHeading("Images");
		imageListViewFP.add(lf);
//		imageListViewFP.addTool(zoomTB);
		imageListViewFP.addTool(infoTB);
		hlc.add(imageListViewFP, new HorizontalLayoutData(.6, 1.0));

		mainPanel.add(hlc);
		mainPanel.addButton(selectButton);
		mainPanel.addButton(cancelButton);
	}

	/**
	 * refreshes the list of images which will automatically update the view of
	 * the thumbnails
	 * 
	 * @see imageEntryList
	 */
	private void refreshImages() {
		ImageSearchEntry searchEntry = (ImageSearchEntry) imgFilter.getSearchEntry();
		
		dbService.searchImages(searchEntry, new AsyncCallback<ArrayList<ImageEntry>>() {

			@Override
			public void onFailure(Throwable caught) {
				Info.display("Refresh Image List", "failed");
			}

			@Override
			public void onSuccess(ArrayList<ImageEntry> result) {
				imageEntryList.clear();
				for (ImageEntry ie : result) {
					imageEntryList.add(ie);
				}
				imageListView.getSelectionModel().setSelectionMode(SelectionMode.SIMPLE);
			}
		});
	}

}
