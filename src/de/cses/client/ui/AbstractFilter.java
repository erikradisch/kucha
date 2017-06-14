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
package de.cses.client.ui;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.Style.HideMode;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.Portlet;

/**
 * This class shall be extended to implement a new filter. To make sure all filters
 * follow a common structure, a <code>FramedPanel</code> is provided where the Widget from {@link #getFilterUI()} is 
 * placed. 
 * 
 * @author alingnau
 */
public abstract class AbstractFilter implements IsWidget {

	private Portlet panel = null;
	private String filterName;

	/**
	 * 
	 */
	public AbstractFilter(String filterName) {
		this.filterName = filterName;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.IsWidget#asWidget()
	 */
	@Override
	public Widget asWidget() {
		if (panel == null) {
			panel = new Portlet();
			panel.setWidth("100%");
			panel.setCollapsible(true);
			panel.setHeading(filterName);
			panel.add(getFilterUI());
		}
		return panel;
	}

	/**
	 * 
	 * @return 
	 * A Widget representing the individual filter UI. This UI is placed inside the panel provided in {@link #asWidget()}. 
	 * Never try to call this method directly!
	 */
	protected abstract Widget getFilterUI();
	
	/**
	 * 
	 * @return
	 * An <code>ArrayList</code> of type <code>String</code> that can be used to create the <code>WHERE</code> clause for
	 * the SQL request.
	 * @see de.cses.client.caves.CaveFilter#getSqlWhereClause()
	 */
	public abstract ArrayList<String> getSqlWhereClause();

}