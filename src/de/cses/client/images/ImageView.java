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
package de.cses.client.images;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.safehtml.shared.UriUtils;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.dnd.core.client.DndDragStartEvent;
import com.sencha.gxt.dnd.core.client.DragSource;

import de.cses.client.ui.AbstractEditor;
import de.cses.client.ui.AbstractView;
import de.cses.client.user.UserLogin;
import de.cses.shared.AbstractEntry;
import de.cses.shared.ImageEntry;

/**
 * @author alingnau
 *
 */
public class ImageView extends AbstractView {

	interface ImageViewResources extends ClientBundle {
		@Source("lock-protection.png")
		ImageResource locked();

		@Source("lock-semiprotection.png")
		ImageResource semi();

		@Source("photo.png")
		ImageResource open();
}

	interface ImageViewTemplates extends XTemplates {
		@XTemplate("<div><center><img src='{imgUri}'></img></center></div>")
		SafeHtml view(SafeUri imgUri);
		
		@XTemplate("<div style='text-align: left;'><figure style='float: right; margin: 0;'><img src='{lockUri}' style='position: relative; width: 16px; height: 16px;'></figure>"
				+ "Title: {title}<br> Shortname: {shortName}<br> Author: {author}<br> Orig. filename: {filename}"
				+ "<figure style='float: left; margin: 0;'><img src='{imgUri}' style='position: relative;'></figure></div>")
		SafeHtml view(SafeUri imgUri, String title, String shortName, String author, String filename, SafeUri lockUri);
	}
	
	private ImageEntry imgEntry;
	private ImageViewTemplates ivTemplates;
	private ImageViewResources res;

	/**
	 * @param text
	 */
	public ImageView(ImageEntry imgEntry) {
		super();
		ivTemplates = GWT.create(ImageViewTemplates.class);
		res = GWT.create(ImageViewResources.class);
		this.imgEntry = imgEntry;
		
		setHTML(ivTemplates.view(
				UriUtils.fromString("resource?imageID=" + imgEntry.getImageID() + "&thumb=120" + UserLogin.getInstance().getUsernameSessionIDParameterForUri()), 
				imgEntry.getTitle() != null ? imgEntry.getTitle() : "n/a", 
				imgEntry.getShortName() != null ? imgEntry.getShortName() : "n/a", 
				imgEntry.getImageAuthor() != null ? imgEntry.getImageAuthor().getLabel() : "n/a",
				imgEntry.getFilename() != null ? imgEntry.getFilename() : "n/a", 
				imgEntry.isOpenAccess() ? res.open().getSafeUri() : res.locked().getSafeUri()));
		setSize("95%", "auto");

		new DragSource(this) {

			@Override
			protected void onDragStart(DndDragStartEvent event) {
				super.onDragStart(event);
				event.setData(imgEntry);
				event.getStatusProxy().update(ivTemplates.view(UriUtils.fromString("resource?imageID=" + imgEntry.getImageID() + "&thumb=80" + UserLogin.getInstance().getUsernameSessionIDParameterForUri())));
			}
			
		};
	}

	/* (non-Javadoc)
	 * @see de.cses.client.ui.AbstractView#getEditor()
	 */
	@Override
	protected AbstractEditor getEditor() {
		return new SingleImageEditor(imgEntry.clone());
	}

	@Override
	public void closeRequest(AbstractEntry entry) {
		super.closeRequest(entry);
		if (entry != null && entry instanceof ImageEntry) {
			imgEntry = (ImageEntry) entry;
		}
		setHTML(ivTemplates.view(
				UriUtils.fromString("resource?imageID=" + imgEntry.getImageID() + "&thumb=120" + UserLogin.getInstance().getUsernameSessionIDParameterForUri()), 
				imgEntry.getTitle() != null ? imgEntry.getTitle() : "n/a", 
				imgEntry.getShortName() != null ? imgEntry.getShortName() : "n/a", 
				imgEntry.getImageAuthor() != null ? imgEntry.getImageAuthor().getLabel() : "n/a",
				imgEntry.getFilename() != null ? imgEntry.getFilename() : "n/a", 
				imgEntry.isOpenAccess() ? res.open().getSafeUri() : res.locked().getSafeUri()));
	}

	/* (non-Javadoc)
	 * @see de.cses.client.ui.AbstractView#getEntry()
	 */
	@Override
	protected AbstractEntry getEntry() {
		return imgEntry;
	}

	/* (non-Javadoc)
	 * @see de.cses.client.ui.AbstractView#getPermalink()
	 */
	@Override
	protected String getPermalink() {
		// TODO Auto-generated method stub
		return null;
	}

}
