/*
 * 
 * Copyright (C) 2013-2018 AT&T Intellectual Property.
 *
 * SPDX-License-Identifier: MIT
 *
 */
package com.att.research.xacml.admin.view.validators;

import java.text.ParseException;

import com.att.research.xacml.std.datatypes.ISO8601DateTime;
import com.vaadin.data.Validator;

public class DateTimeValidator implements Validator {
	private static final long serialVersionUID = 1L;

	public DateTimeValidator() {
	}

	@Override
	public void validate(Object value) throws InvalidValueException {
		if (value instanceof String) {
			try {
				ISO8601DateTime.fromISO8601DateTimeString((String) value);
			} catch (ParseException e) {
				throw new InvalidValueException(e.getLocalizedMessage());
			}
		} else
			throw new InvalidValueException("Unrecognized DateTime");
	}
}
