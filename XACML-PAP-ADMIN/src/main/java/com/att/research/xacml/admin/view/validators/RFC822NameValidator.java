/*
 * 
 * Copyright (C) 2013-2018 AT&T Intellectual Property.
 *
 * SPDX-License-Identifier: MIT
 *
 */
package com.att.research.xacml.admin.view.validators;

import java.text.ParseException;

import com.att.research.xacml.std.datatypes.RFC822Name;
import com.vaadin.data.Validator;

public class RFC822NameValidator implements Validator {
	private static final long serialVersionUID = 1L;

	public RFC822NameValidator() {
	}

	@Override
	public void validate(Object value) throws InvalidValueException {
		if (value instanceof String) {
			try {
				RFC822Name.newInstance((String) value);
			} catch (ParseException e) {
				throw new InvalidValueException(e.getLocalizedMessage());
			}
		} else
			throw new InvalidValueException("Unrecognized RFC822 Name");
	}
}
