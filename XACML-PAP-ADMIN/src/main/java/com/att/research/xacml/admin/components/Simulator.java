/*
 * 
 * Copyright (C) 2013-2018 AT&T Intellectual Property.
 *
 * SPDX-License-Identifier: MIT
 *
 */
package com.att.research.xacml.admin.components;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.UI;

public class Simulator extends CustomComponent {

	@AutoGenerated
	private AbsoluteLayout mainLayout;

	private static final long serialVersionUID = 1L;

	@Theme("xacml_pap_admin")
	public static class SimulatorUI extends UI {
		private static final long serialVersionUID = 1L;

		@Override
		protected void init(VaadinRequest request) {
			// TODO Auto-generated method stub

		}

	}
	
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public Simulator() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
	}

	@AutoGenerated
	private void buildMainLayout() {
		// the main layout and components will be created here
		mainLayout = new AbsoluteLayout();
	}

}
