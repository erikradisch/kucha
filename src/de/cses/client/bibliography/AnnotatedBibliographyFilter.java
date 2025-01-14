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

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.NumberField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.validator.RegExValidator;

import de.cses.client.Util;
import de.cses.client.ui.AbstractFilter;
import de.cses.client.user.UserLogin;
import de.cses.shared.AbstractSearchEntry;
import de.cses.shared.AnnotatedBibliographySearchEntry;

/**
 * @author alingnau
 *
 */
public class AnnotatedBibliographyFilter extends AbstractFilter {
	
	public TextField authorNameTF;
	public TextField titleTF;
	private TextField publisherTF;
	private NumberField<Integer> yearSearch;

	/**
	 * @param filterName
	 */
	public AnnotatedBibliographyFilter(String filterName) {
		super(filterName);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see de.cses.client.ui.AbstractFilter#getFilterUI()
	 */
	@Override
	protected Widget getFilterUI() {
		authorNameTF = new TextField();
		authorNameTF.setEmptyText("search author / editor / institute");
		authorNameTF.setToolTip(Util.createToolTip("searches all authors / editors / institutes"));
		
		titleTF = new TextField();
		titleTF.setEmptyText("search title (orig./eng./trans.)");
		titleTF.setToolTip(Util.createToolTip("Searches in all sorts of titles.", "Includes Parent Title, Subtitle, Collection Title, Titleaddon, Book Title, etc."));
		
		publisherTF = new TextField();
		publisherTF.setEmptyText("search publisher & address");
		publisherTF.setToolTip(Util.createToolTip("Search for publishers & addresses.", "Please note: publisher is a free text field. Due to diffent spelling or typos, searing can become difficult."));
		
		yearSearch = new NumberField<Integer>(new NumberPropertyEditor.IntegerPropertyEditor());
		yearSearch.setAllowNegative(false);
		yearSearch.setEmptyText("year");
		yearSearch.setToolTip(Util.createToolTip("search year", "will be extended into searching for range of years in next version"));
 
		VerticalLayoutContainer bibFilterVLC = new VerticalLayoutContainer();
		bibFilterVLC.add(authorNameTF, new VerticalLayoutData(1.0, .25));
		bibFilterVLC.add(titleTF, new VerticalLayoutData(1.0, .25));
		bibFilterVLC.add(publisherTF, new VerticalLayoutData(1.0, .25));
		bibFilterVLC.add(yearSearch, new VerticalLayoutData(1.0, .25));
		bibFilterVLC.setHeight("120px");
		return bibFilterVLC;
	}

	@Override
	public AbstractSearchEntry getSearchEntry() {
		AnnotatedBibliographySearchEntry searchEntry;
		if (UserLogin.isLoggedIn()) {
			searchEntry = new AnnotatedBibliographySearchEntry(UserLogin.getInstance().getSessionID());
		} else {
			searchEntry = new AnnotatedBibliographySearchEntry();
		}
		
		if (authorNameTF.getValue() != null && !authorNameTF.getValue().isEmpty()) {
			searchEntry.setAuthorSearch(authorNameTF.getValue());
		}
		
		if (titleTF.getValue() != null && !titleTF.getValue().isEmpty()) {
			searchEntry.setTitleSearch(titleTF.getValue());
		}
		
		if (publisherTF.getValue() != null && !publisherTF.getValue().isEmpty()) {
			searchEntry.setPublisherSearch(publisherTF.getValue());
		}
		
		if (yearSearch.getValue() != null && yearSearch.getValue() > 0) {
			searchEntry.setYearSearch(yearSearch.getValue());
		}
		
		return searchEntry;
	}

}
