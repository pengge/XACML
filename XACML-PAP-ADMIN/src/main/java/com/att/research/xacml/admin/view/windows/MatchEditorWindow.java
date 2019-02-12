/*
 *
 *          Copyright (c) 2014,2019  AT&T Knowledge Ventures
 *                     SPDX-License-Identifier: MIT
 */
package com.att.research.xacml.admin.view.windows;

import oasis.names.tc.xacml._3_0.core.schema.wd_17.MatchType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.att.research.xacml.admin.XacmlAdminUI;
import com.att.research.xacml.admin.jpa.Attribute;
import com.att.research.xacml.admin.jpa.Datatype;
import com.att.research.xacml.admin.view.events.AttributeChangedEventListener;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Buffered.SourceException;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class MatchEditorWindow extends Window implements AttributeChangedEventListener {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */
	
	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private Button buttonSave;
	@AutoGenerated
	private Table tableFunctions;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Log logger	= LogFactory.getLog(MatchEditorWindow.class);
	private final MatchEditorWindow self = this;
	private final MatchType match;
	private final Datatype datatype;
	private boolean isSaved = false;
	private static SQLContainer matchFunctions = ((XacmlAdminUI) UI.getCurrent()).getMatchFunctionContainer();
		
	private static String PROPERTY_SHORTNAME = "shortname";
	private static String PROPERTY_XACMLID = "xacmlid";
	private static String PROPERTY_ARG2_DATATYPE = "arg2_datatype";
	
	static {
		//
		// H2 seems to insist on capitalizing, even with the no uppercase switch.
		//
		for (Object prop : MatchEditorWindow.matchFunctions.getContainerPropertyIds()) {
			logger.info("SQL Container Property Id: " + prop.toString());
			if (prop.toString().equalsIgnoreCase(PROPERTY_SHORTNAME)) {
				PROPERTY_SHORTNAME = prop.toString();
			} else if (prop.toString().equalsIgnoreCase(PROPERTY_XACMLID)) {
				PROPERTY_XACMLID = prop.toString();
			} else if (prop.toString().equalsIgnoreCase(PROPERTY_ARG2_DATATYPE)) {
				PROPERTY_ARG2_DATATYPE = prop.toString();
			}
		}
		
	}
	
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public MatchEditorWindow(MatchType match, Datatype datatype) {
		buildMainLayout();
		//setCompositionRoot(mainLayout);
		setContent(mainLayout);
		//
		// Save our data
		//
		this.match = match;
		this.datatype = datatype;
		//
		// Close shortcut
		//
		this.setCloseShortcut(KeyCode.ESCAPE);
		//
		// Initialize GUI
		//
		this.initializeFunctions();
		this.initializeButtons();
		//
		// Set our focus
		//
		this.tableFunctions.focus();
	}
	
	protected void initializeFunctions() {
		//
		// Setup datasource and GUI properties
		//
		this.tableFunctions.setContainerDataSource(MatchEditorWindow.matchFunctions);
		this.tableFunctions.setImmediate(true);
		this.tableFunctions.setNullSelectionAllowed(false);
		this.tableFunctions.setRequired(true);
		this.tableFunctions.setRequiredError("Please select a function.");
		this.tableFunctions.setSelectable(true);
		this.tableFunctions.setPageLength(15);
		this.tableFunctions.setVisibleColumns(PROPERTY_SHORTNAME, PROPERTY_XACMLID);
		this.tableFunctions.setColumnHeaders(new String[] {"Short Function Name", "Xacml ID"});
		//
		// Filter out functions where ARG2 is the given datatype. NOTE: The
		// AttributeDesignator/AttributeSelector is the 2nd argument.
		//
		MatchEditorWindow.matchFunctions.removeAllContainerFilters();
		MatchEditorWindow.matchFunctions.addContainerFilter(new Compare.Equal(PROPERTY_ARG2_DATATYPE, this.datatype.getId()));
		//
		// Respond to selection events
		//
		this.tableFunctions.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				Object id = self.tableFunctions.getValue();
				if (id != null) {
					Item item = MatchEditorWindow.matchFunctions.getItem(id);
					if (item == null) {
						return;
					}
					Property<?> property = item.getItemProperty(PROPERTY_XACMLID);
					if (property == null) {
						return;
					}
					self.match.setMatchId(property.getValue().toString());
					self.buttonSave.setEnabled(true);
				} else {
					self.buttonSave.setEnabled(false);
				}
			}			
		});
		//
		// Respond to double-click events
		//
		this.tableFunctions.addItemClickListener(new ItemClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void itemClick(ItemClickEvent event) {
				if (event.isDoubleClick()) {
					self.doSave();
				}
			}
		});
		//
		// Default selection
		//
		this.buttonSave.setEnabled(false);
		if (this.match.getMatchId() != null) {
			for (Object id : MatchEditorWindow.matchFunctions.getItemIds()) {
				Item item = MatchEditorWindow.matchFunctions.getItem(id);
				if (item != null) {
					Property<?> property = item.getItemProperty(PROPERTY_XACMLID);
					if (property != null) {
						if (property.getValue().toString().equals(this.match.getMatchId())) {
							this.tableFunctions.select(id);
							break;
						}
					}
				}
			}
		}
	}
	
	protected void initializeButtons() {
		this.buttonSave.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				self.doSave();
			}		
		});
	}
	
	protected void doSave() {
		try {
			//
			// Commit changes
			//
			self.tableFunctions.commit();
			//
			// We are saved
			//
			self.isSaved = true;
			//
			// Close the window
			//
			self.close();
		} catch (SourceException | InvalidValueException e) {
			return;
		}
	}
	
	@Override
	public void attributeChanged(Attribute attribute) {
		if (logger.isDebugEnabled()) {
			logger.debug("attributeChanged: " + attribute);
		}
		//
		// Remove all filters.
		//
		MatchEditorWindow.matchFunctions.removeAllContainerFilters();
		if (attribute == null) {
			return;
		}
		//
		// Get the datatype for the attribute
		//
		Datatype datatype = attribute.getDatatypeBean();
		if (logger.isDebugEnabled()) {
			logger.debug("datatype: " + datatype.getId());
		}
		//
		// Filter out functions where ARG2 is the datatype. The
		// AttributeDesignator/AttributeSelector is the 2nd arg.
		//
		MatchEditorWindow.matchFunctions.addContainerFilter(new Compare.Equal(PROPERTY_ARG2_DATATYPE, datatype.getId()));
	}
	
	public boolean isSaved() {
		return this.isSaved;
	}
	
	public MatchType getMatch() {
		return this.match;
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
		
		// tableFunctions
		tableFunctions = new Table();
		tableFunctions.setCaption("Function");
		tableFunctions.setImmediate(true);
		tableFunctions
				.setDescription("Select a function for matching the attribute.");
		tableFunctions.setWidth("100.0%");
		tableFunctions.setHeight("-1px");
		tableFunctions.setInvalidAllowed(false);
		tableFunctions.setRequired(true);
		mainLayout.addComponent(tableFunctions);
		mainLayout.setExpandRatio(tableFunctions, 1.0f);
		
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
