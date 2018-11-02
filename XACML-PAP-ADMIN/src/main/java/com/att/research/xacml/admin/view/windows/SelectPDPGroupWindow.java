/*
 * 
 * Copyright (C) 2013-2018 AT&T Intellectual Property.
 *
 * SPDX-License-Identifier: MIT
 *
 */
package com.att.research.xacml.admin.view.windows;

import java.util.Set;

import com.att.research.xacml.api.pap.PDPGroup;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class SelectPDPGroupWindow extends Window {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private Button buttonSave;
	@AutoGenerated
	private ListSelect listSelectPDPGroup;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final SelectPDPGroupWindow self = this;
	
	private boolean saved = false;
	
	private PDPGroup selectedGroup = null;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public SelectPDPGroupWindow(Set<PDPGroup> groups, String caption) {
		buildMainLayout();
		//setCompositionRoot(mainLayout);
		setContent(mainLayout);
		//
		// Setup the shortcuts
		//
		this.setCloseShortcut(KeyCode.ESCAPE);
		this.buttonSave.setClickShortcut(KeyCode.ENTER);
		//
		// initialize
		//
		this.initialize(groups);
		//
		// Focus
		//
		this.listSelectPDPGroup.focus();
		//
		// setup the button
		//
		this.setupButtons();
	}
	
	protected void initialize(Set<PDPGroup> groups) {
		//
		// Initialize the list
		//
		this.initializeSelect(groups);
		//
		// Buttons
		//
		this.initializeButtons();
	}
	
	protected void initializeButtons() {
		this.buttonSave.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				//
				// Get the selected value
				//
				self.selectedGroup = (PDPGroup) self.listSelectPDPGroup.getValue();
				assert (self.selectedGroup != null);
				//
				// Mark ourselves as saved
				//
				self.saved = true;
				//
				// Close window
				//
				self.close();
			}
		});
	}
	
	public boolean isSaved() {
		return saved;
	}
	
	public PDPGroup selectedGroup() {
		return this.selectedGroup;
	}
	
	private void initializeSelect(Set<PDPGroup> groups) {
		//
		// Initialize GUI properties
		//
		this.listSelectPDPGroup.setImmediate(true);
		this.listSelectPDPGroup.setItemCaptionMode(ItemCaptionMode.EXPLICIT);
		this.listSelectPDPGroup.setNullSelectionAllowed(false);
		this.listSelectPDPGroup.setNewItemsAllowed(false);
		this.listSelectPDPGroup.setMultiSelect(false);
		//
		// Add items
		//
		for (PDPGroup group : groups) {
			this.listSelectPDPGroup.addItem(group);
			this.listSelectPDPGroup.setItemCaption(group, group.getName());
		}
		//
		// Listen to events
		//
		this.listSelectPDPGroup.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				self.setupButtons();
			}
		});
	}
	
	protected void setupButtons() {
		if (self.listSelectPDPGroup.getValue() == null) {
			self.buttonSave.setEnabled(false);
		} else {
			self.buttonSave.setEnabled(true);
		}
	}

	@AutoGenerated
	private VerticalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("-1px");
		mainLayout.setHeight("-1px");
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);
		
		// top-level component properties
		setWidth("-1px");
		setHeight("-1px");
		
		// listSelectPDPGroup
		listSelectPDPGroup = new ListSelect();
		listSelectPDPGroup.setImmediate(false);
		listSelectPDPGroup.setWidth("-1px");
		listSelectPDPGroup.setHeight("-1px");
		listSelectPDPGroup.setInvalidAllowed(false);
		listSelectPDPGroup.setRequired(true);
		mainLayout.addComponent(listSelectPDPGroup);
		mainLayout.setExpandRatio(listSelectPDPGroup, 1.0f);
		
		// buttonSave
		buttonSave = new Button();
		buttonSave.setCaption("Save");
		buttonSave.setImmediate(true);
		buttonSave.setWidth("-1px");
		buttonSave.setHeight("-1px");
		mainLayout.addComponent(buttonSave);
		mainLayout.setComponentAlignment(buttonSave, new Alignment(48));
		
		return mainLayout;
	}

}
