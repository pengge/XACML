/*
 * 
 * Copyright (C) 2013-2018 AT&T Intellectual Property.
 *
 * SPDX-License-Identifier: MIT
 *
 */
package com.att.research.xacml.admin.view.validators;

import java.text.ParseException;

import com.att.research.xacml.std.datatypes.XPathYearMonthDuration;
import com.vaadin.data.Validator;

public class YearMonthDurationValidator implements Validator {
	private static final long serialVersionUID = 1L;

	public YearMonthDurationValidator() {
	}

	@Override
	public void validate(Object value) throws InvalidValueException {
		if (value instanceof String) {
			try {
				XPathYearMonthDuration.newInstance((String) value);
			} catch (ParseException e) {
				throw new InvalidValueException(e.getLocalizedMessage());
			}
		} else
			throw new InvalidValueException("Unrecognized YearMonthDuration");
	}
}
