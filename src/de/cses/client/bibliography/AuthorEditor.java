/*
 * Copyright 2016 - 2018
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

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.button.ToolButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.validator.MaxLengthValidator;
import com.sencha.gxt.widget.core.client.form.validator.RegExValidator;

import de.cses.client.DatabaseService;
import de.cses.client.DatabaseServiceAsync;
import de.cses.client.Util;
import de.cses.shared.AuthorEntry;

/**
 * @author alingnau
 *
 */
public class AuthorEditor implements IsWidget {

	private final DatabaseServiceAsync dbService = GWT.create(DatabaseService.class);
	private AuthorEntry authorEntry = null;
	private FramedPanel mainPanel = null;
	private AuthorEditorListener listener;
	
	public AuthorEditor(AuthorEditorListener listener) {
		this(new AuthorEntry(), listener);
	}

	/**
	 * 
	 */
	public AuthorEditor(AuthorEntry authorEntry, AuthorEditorListener listener) {
		this.listener = listener;
		this.authorEntry = authorEntry;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.user.client.ui.IsWidget#asWidget()
	 */
	@Override
	public Widget asWidget() {
		if (mainPanel == null) {
			initUI();
		}
		return mainPanel;
	}
	
	private void closeEditor(boolean saved) {
		if (saved) {
			listener.authorSaved(authorEntry);
		} else {
			listener.editorCanceled();
		}
	}

	/**
	 * 
	 */
	private void initUI() {
		mainPanel = new FramedPanel();

		if (authorEntry.getAuthorID() == 0) {
			mainPanel.setHeading("Add New Author/Editor");
		} else {
			mainPanel.setHeading("Edit Author/Editor");
		}
		
		TextField authorLastNameTF = new TextField();
		authorLastNameTF.setAllowBlank(false);
		authorLastNameTF.addValidator(new MaxLengthValidator(64));
		authorLastNameTF.setAutoValidate(true);
		authorLastNameTF.setWidth(300);
		authorLastNameTF.setValue(authorEntry.getLastname());
		authorLastNameTF.addValueChangeHandler(new ValueChangeHandler<String>() {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				authorEntry.setLastname(event.getValue());
			}
		});
		
		TextField authorFirstNameTF = new TextField();
		authorFirstNameTF.setAllowBlank(true);
		authorFirstNameTF.addValidator(new MaxLengthValidator(64));
		authorFirstNameTF.setAutoValidate(true);
		authorFirstNameTF.setValue(authorEntry.getFirstname());
		authorFirstNameTF.addValueChangeHandler(new ValueChangeHandler<String>() {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				authorEntry.setFirstname(event.getValue());
			}
		});

		CheckBox kuchaVisitorCB = new CheckBox();
		kuchaVisitorCB.setBoxLabel("has visited Kucha");
		kuchaVisitorCB.setValue(false);
		
		
		TextField authorAffiliation = new TextField();
		authorAffiliation.setValue(authorEntry.getAffiliation());
		authorAffiliation.addValueChangeHandler(new ValueChangeHandler<String>() {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				authorEntry.setAffiliation(event.getValue());
			}
		});

		TextField authorEmailTF = new TextField();
		authorEmailTF.addValidator(new RegExValidator(Util.REGEX_EMAIL_PATTERN, "please enter valid email address"));
		authorEmailTF.setAutoValidate(true);
		authorEmailTF.setValue(authorEntry.getEmail());
		authorEmailTF.addValueChangeHandler(new ValueChangeHandler<String>() {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				authorEntry.setEmail(event.getValue());
			}
		});
		
		TextField authorHomepageTF = new TextField();
		authorHomepageTF.addValidator(new RegExValidator(Util.REGEX_URL_PATTERN, "please enter valid URL"));
		authorHomepageTF.setAutoValidate(true);
		authorHomepageTF.setValue(authorEntry.getHomepage());
		authorHomepageTF.addValueChangeHandler(new ValueChangeHandler<String>() {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				authorEntry.setHomepage(event.getValue());
			}
		});

		TextField institutionTF = new TextField();
		institutionTF.setAllowBlank(false);
		institutionTF.addValidator(new MaxLengthValidator(256));
		institutionTF.setAutoValidate(true);
		if (authorEntry.getInstitution() != null) {
			authorLastNameTF.reset();
			authorLastNameTF.setEnabled(false);
			authorFirstNameTF.reset();
			authorFirstNameTF.setEnabled(false);
			authorAffiliation.reset();
			authorAffiliation.setEnabled(false);
			authorEmailTF.reset();
			authorEmailTF.setEnabled(false);
			institutionTF.setEnabled(true);
			institutionTF.setValue(authorEntry.getInstitution());
		} else {
			institutionTF.setEnabled(false);
		}
		institutionTF.addValueChangeHandler(new ValueChangeHandler<String>() {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				authorEntry.setInstitution(event.getValue());
			}
		});		
		
		CheckBox institutionCB = new CheckBox();
		institutionCB.setBoxLabel("is institution");
		institutionCB.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				if (event.getValue()) {
					authorLastNameTF.reset();
					authorLastNameTF.setEnabled(false);
					authorFirstNameTF.reset();
					authorFirstNameTF.setEnabled(false);
					authorAffiliation.reset();
					authorAffiliation.setEnabled(false);
					authorEmailTF.reset();
					authorEmailTF.setEnabled(false);
					institutionTF.setEnabled(true);
				} else {
					authorLastNameTF.setEnabled(true);
					authorFirstNameTF.setEnabled(true);
					authorAffiliation.setEnabled(true);
					authorEmailTF.setEnabled(true);
					institutionTF.reset();
					institutionTF.setEnabled(false);
				}
			}
		});
		VerticalLayoutContainer newAuthorVLC = new VerticalLayoutContainer();
		newAuthorVLC.add(new FieldLabel(authorLastNameTF, "Family name"), new VerticalLayoutData(1.0, 1.0 / 8));
		newAuthorVLC.add(new FieldLabel(authorFirstNameTF, "Given Name"), new VerticalLayoutData(1.0, 1.0 / 8));
		newAuthorVLC.add(new FieldLabel(authorAffiliation, "Affiliation"), new VerticalLayoutData(1.0, 1.0 / 8));
		newAuthorVLC.add(new FieldLabel(authorEmailTF, "E-mail"), new VerticalLayoutData(1.0, 1.0 / 8));
		newAuthorVLC.add(institutionCB, new VerticalLayoutData(1.0, 1.0 / 8));
		newAuthorVLC.add(new FieldLabel(institutionTF, "Institution"), new VerticalLayoutData(1.0, 1.0 / 8));
		newAuthorVLC.add(new FieldLabel(authorHomepageTF, "Homepage"), new VerticalLayoutData(1.0, 1.0 / 8));
		newAuthorVLC.add(kuchaVisitorCB, new VerticalLayoutData(1.0, 1.0 / 8));
		
		mainPanel.add(newAuthorVLC);

		ToolButton saveToolButton = new ToolButton(ToolButton.SAVE);
		saveToolButton.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				if ((institutionCB.getValue() && institutionTF.validate() && authorHomepageTF.validate()) || (authorLastNameTF.validate()
						&& authorFirstNameTF.validate() && authorEmailTF.validate() && authorHomepageTF.validate())) {
					if (authorEntry.getAuthorID()==0) {
						dbService.insertAuthorEntry(authorEntry, new AsyncCallback<Integer>() {
							
							@Override
							public void onFailure(Throwable caught) {
								Util.showWarning("Add New Author", "Error while saving!");
							}
							
							@Override
							public void onSuccess(Integer result) {
								if (result > 0) {
									authorEntry.setAuthorID(result);
									closeEditor(true);
								} else {
									Util.showWarning("Add New Author", "Error while saving!");
								}
							}
						});
					} else {
						dbService.updateAuthorEntry(authorEntry, new AsyncCallback<Boolean>() {

							@Override
							public void onFailure(Throwable caught) {
								Util.showWarning("Update Author", "Error while saving!");
							}

							@Override
							public void onSuccess(Boolean result) {
								closeEditor(true);
							}
						});
					}
				}
			}
		});
		mainPanel.addTool(saveToolButton);
		
		ToolButton cancelToolButton = new ToolButton(ToolButton.CLOSE);
		cancelToolButton.addSelectHandler(new SelectHandler() {

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
						closeEditor(true);
					}
				});
				d.getButton(PredefinedButton.NO).addSelectHandler(new SelectHandler() {

					@Override
					public void onSelect(SelectEvent event) {
						closeEditor(false);
					}
				});
			}
		});
		mainPanel.addTool(cancelToolButton);
		
		mainPanel.setWidth(450);
	}

}
