/*
 * 
 * Copyright (C) 2013-2018 AT&T Intellectual Property.
 *
 * SPDX-License-Identifier: MIT
 *
 */
package com.att.research.xacml.admin.view.windows;

import java.util.Map;

import oasis.names.tc.xacml._3_0.core.schema.wd_17.PolicyType;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.VariableDefinitionType;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.VariableReferenceType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Buffered.SourceException;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class VariableReferenceEditorWindow extends Window {
	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private Button buttonSave;
	@AutoGenerated
	private TextField textFieldVariable;
	@AutoGenerated
	private ListSelect listSelectVariables;
	/*
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Log logger	= LogFactory.getLog(VariableReferenceEditorWindow.class);
	private final VariableReferenceEditorWindow self = this;
	private final VariableReferenceType variable;
	private boolean isSaved = false;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public VariableReferenceEditorWindow(VariableReferenceType variable, Map<VariableDefinitionType, PolicyType> variables) {
		buildMainLayout();
		//setCompositionRoot(mainLayout);
		setContent(mainLayout);
		//
		// Save parameters
		//
		this.variable = variable;
		//
		// Set our shortcuts
		//
		this.setCloseShortcut(KeyCode.ESCAPE);
		//
		// Initialize
		//
		this.initializeTextField();
		this.initializeSelect(variables);
		this.initializeButtons();
		//
		// Focus
		//
		this.textFieldVariable.focus();
	}
	
	protected void initializeTextField() {
		this.textFieldVariable.setImmediate(true);
		this.textFieldVariable.setNullRepresentation("");
		this.textFieldVariable.addTextChangeListener(new TextChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void textChange(TextChangeEvent event) {
				if (event.getText() != null && event.getText().isEmpty() == false) {
					self.buttonSave.setEnabled(true);
				} else {
					self.buttonSave.setEnabled(false);
				}
			}
			
		});
		this.textFieldVariable.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				String value = self.textFieldVariable.getValue();
				if (value == null || value.isEmpty()) {
					self.buttonSave.setEnabled(false);
				} else {
					self.buttonSave.setEnabled(true);
				}
			}
		});
		if (this.variable != null) {
			this.textFieldVariable.setValue(this.variable.getVariableId());
		}
	}

	protected void initializeSelect(Map<VariableDefinitionType, PolicyType> vars) {
		//
		// Add existing variables into the table
		//
		if (vars != null) {
			for (VariableDefinitionType var : vars.keySet()) {
				this.listSelectVariables.addItem(var.getVariableId());
			}
		}
		//
		// Respond to changes
		//
		this.listSelectVariables.setImmediate(true);
		this.listSelectVariables.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				Object value = self.listSelectVariables.getValue();
				if (value != null) {
					self.textFieldVariable.setValue(value.toString());
				}
			}			
		});
	}
	
	protected void initializeButtons() {
		this.buttonSave.setClickShortcut(KeyCode.ENTER);
		this.buttonSave.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					//
					// Commit
					//
					self.textFieldVariable.commit();
					//
					// Now we can save
					//
					self.isSaved = true;
					self.variable.setVariableId(self.textFieldVariable.getValue());
					//
					// And close the window
					//
					self.close();
				} catch (SourceException | InvalidValueException e) {
					logger.error("Commit variable id: " + e);
				}
			}			
		});
	}
	
	public boolean isSaved() {
		return this.isSaved;
	}
	
	public VariableReferenceType getVariableReference() {
		return this.variable;
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
		
		// listSelectVariables
		listSelectVariables = new ListSelect();
		listSelectVariables.setCaption("Defined Variables");
		listSelectVariables.setImmediate(false);
		listSelectVariables.setWidth("100.0%");
		listSelectVariables.setHeight("-1px");
		mainLayout.addComponent(listSelectVariables);
		mainLayout.setExpandRatio(listSelectVariables, 1.0f);
		
		// textFieldVariable
		textFieldVariable = new TextField();
		textFieldVariable.setCaption("Variable");
		textFieldVariable.setImmediate(false);
		textFieldVariable.setWidth("-1px");
		textFieldVariable.setHeight("-1px");
		textFieldVariable.setInvalidAllowed(false);
		textFieldVariable.setRequired(true);
		textFieldVariable.setInputPrompt("Eg. \"12345\" or \"myVariable1\"");
		mainLayout.addComponent(textFieldVariable);
		mainLayout.setExpandRatio(textFieldVariable, 1.0f);
		
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
