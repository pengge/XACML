package com.att.research.xacml.std;

import static org.junit.Assert.*;
import org.junit.Test;
import com.att.research.xacml.api.Identifier;

public class IdentifierImplTest {

  @Test
  public void testHashCode() {
    Identifier id = new IdentifierImpl("com.test.");
    assertTrue(id.hashCode() != 0);
  }

  @Test
  public void testIdentifierImplURI() {
    //fail("Not yet implemented");
  }

  @Test
  public void testIdentifierImplString() {
    //fail("Not yet implemented");
  }

  @Test
  public void testIdentifierImplIdentifierString() {
    //fail("Not yet implemented");
  }

  @Test
  public void testGensymString() {
    //fail("Not yet implemented");
  }

  @Test
  public void testGensym() {
    //fail("Not yet implemented");
  }

  @Test
  public void testGetUri() {
    //fail("Not yet implemented");
  }

  @Test
  public void testToString() {
    //fail("Not yet implemented");
  }

  @Test
  public void testEqualsObject() {
    //fail("Not yet implemented");
  }

  @Test
  public void testStringValue() {
    //fail("Not yet implemented");
  }

}
