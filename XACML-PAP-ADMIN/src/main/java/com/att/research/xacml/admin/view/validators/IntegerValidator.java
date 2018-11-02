/*
 * 
 * Copyright (C) 2013-2018 AT&T Intellectual Property.
 *
 * SPDX-License-Identifier: MIT
 *
 */
package com.att.research.xacml.admin.view.validators;

import com.vaadin.data.Validator;

public class IntegerValidator implements Validator {
	private static final long serialVersionUID = 1L;

	public IntegerValidator() {
	}

	@Override
	public void validate(Object value) throws InvalidValueException {
		if (value instanceof Integer) {
			return;
		}
		try {
			Integer.parseInt(value.toString());
		} catch (NumberFormatException e) {
			throw new InvalidValueException(e.getLocalizedMessage());
		}
	}
}
