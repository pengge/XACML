/*
 * 
 * Copyright (C) 2013-2018 AT&T Intellectual Property.
 *
 * SPDX-License-Identifier: MIT
 *
 */
package com.att.research.xacml.admin.view.validators;

import com.vaadin.data.Validator;

public class BooleanValidator implements Validator {
	private static final long serialVersionUID = 1L;

	public BooleanValidator() {
	}

	@Override
	public void validate(Object value) throws InvalidValueException {
		if (value instanceof Boolean) {
			return;
		}
		if (value.toString().equalsIgnoreCase("true") ||
			value.toString().equalsIgnoreCase("false") ||
			value.toString().equalsIgnoreCase("0") ||
			value.toString().equalsIgnoreCase("1")) {
			return;
		}
		throw new InvalidValueException("Unrecognized boolean");
	}

}
