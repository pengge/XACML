/*
 * 
 * Copyright (C) 2013-2018 AT&T Intellectual Property.
 *
 * SPDX-License-Identifier: MIT
 *
 */
package com.att.research.xacml.admin.view.events;

import com.att.research.xacml.admin.jpa.Attribute;

public interface AttributeChangedEventListener {
	public void	attributeChanged(Attribute attribute);
}
