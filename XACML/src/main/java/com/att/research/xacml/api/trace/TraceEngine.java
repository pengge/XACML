/*
 *                        AT&T - PROPRIETARY
 *          THIS FILE CONTAINS PROPRIETARY INFORMATION OF
 *        AT&T AND IS NOT TO BE DISCLOSED OR USED EXCEPT IN
 *             ACCORDANCE WITH APPLICABLE AGREEMENTS.
 *
 *          Copyright (c) 2013 AT&T Knowledge Ventures
 *              Unpublished and Not for Publication
 *                     All Rights Reserved
 */

package com.att.research.xacml.api.trace;

/**
 * Defines the API for objects that serve as handlers for {@link com.att.research.xacml.api.trace.TraceEvent}s.  <code>TraceEngine</code>s
 * are instantiated with {@link com.att.research.xacml.api.trace.TraceEngineFactory} objects.
 * 
 * @author Christopher A. Rath
 * @version $Revision$
 */
public interface TraceEngine {
	/**
	 * Processes the given {@link com.att.research.xacml.api.trace.TraceEvent}.
	 * 
	 * @param traceEvent the <code>TraceEvent</code> to process
	 */
	public void trace(TraceEvent<?> traceEvent);
	
	/**
	 * Returns true if this <code>TraceEngine</code> would actually process a {@link com.att.research.xacml.api.trace.TraceEvent}.  This
	 * is useful to avoid creating new <code>TraceEvent</code> objects that will just be ignored.
	 * 
	 * @return true if this <code>TraceEngine</code> would perform an action on a <code>TraceEvent</code>.
	 */
	public boolean isTracing();
}
