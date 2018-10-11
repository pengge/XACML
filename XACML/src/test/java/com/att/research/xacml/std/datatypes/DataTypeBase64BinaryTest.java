/*
 *                        AT&T - PROPRIETARY
 *          THIS FILE CONTAINS PROPRIETARY INFORMATION OF
 *        AT&T AND IS NOT TO BE DISCLOSED OR USED EXCEPT IN
 *             ACCORDANCE WITH APPLICABLE AGREEMENTS.
 *
 *          Copyright (c) 2018 AT&T Knowledge Ventures
 *              Unpublished and Not for Publication
 *                     All Rights Reserved
 */

package com.att.research.xacml.std.datatypes;

import static org.junit.Assert.*;

import java.util.Base64;

import org.junit.Test;

import com.att.research.xacml.api.DataTypeException;

public class DataTypeBase64BinaryTest {

	@Test
	public void test() throws DataTypeException {
		DataTypeBase64Binary datatype = DataTypeBase64Binary.newInstance();
		
		assertNull(datatype.convert(null));
		
		String test = "iamasecret";
		Base64Binary base64 = Base64Binary.newInstance(test);
		assertEquals(base64, datatype.convert(base64));
		assertEquals(base64, datatype.convert(base64.getData()));
		
		assertNull(datatype.toStringValue(null));
		assertEquals(base64.stringValue(), datatype.toStringValue(base64));
		
		String test64 = Base64.getEncoder().encodeToString(test.getBytes());
		
		System.out.println(test64);
		
		Base64Binary convertedObject = datatype.convert(test);
		
		System.out.println(convertedObject);
		
		System.out.println(convertedObject.getData());
		
	}
	
}
