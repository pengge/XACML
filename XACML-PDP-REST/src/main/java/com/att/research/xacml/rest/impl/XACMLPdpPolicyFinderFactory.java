/*
 * 
 * Copyright (C) 2013-2018 AT&T Intellectual Property.
 *
 * SPDX-License-Identifier: MIT
 *
 */
package com.att.research.xacml.rest.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.att.research.xacml.std.StdStatusCode;
import com.att.research.xacml.std.dom.DOMStructureException;
import com.att.research.xacml.util.FactoryException;
import com.att.research.xacml.util.XACMLProperties;
import com.att.research.xacmlatt.pdp.policy.Policy;
import com.att.research.xacmlatt.pdp.policy.PolicyDef;
import com.att.research.xacmlatt.pdp.policy.PolicyFinder;
import com.att.research.xacmlatt.pdp.policy.PolicyFinderFactory;
import com.att.research.xacmlatt.pdp.policy.dom.DOMPolicyDef;
import com.att.research.xacmlatt.pdp.std.StdPolicyFinder;
import com.google.common.base.Splitter;

public class XACMLPdpPolicyFinderFactory extends PolicyFinderFactory {
	public static final String	PROP_FILE		= ".file";
	public static final String	PROP_URL		= ".url";
	
	private static Log logger							= LogFactory.getLog(XACMLPdpPolicyFinderFactory.class);
	private List<PolicyDef> rootPolicies;
	private List<PolicyDef> referencedPolicies;
	private boolean needsInit					= true;
	
	private Properties properties = null;
	
	public XACMLPdpPolicyFinderFactory() {
		//
		// Here we differ from the StdPolicyFinderFactory in that we initialize right away.
		// We do not wait for a policy request to happen to look for and load policies.
		//
		this.init();
	}

	public XACMLPdpPolicyFinderFactory(Properties properties) {
		//
		// Save our properties
		//
		this.properties = properties;
		//
		// Here we differ from the StdPolicyFinderFactory in that we initialize right away.
		// We do not wait for a policy request to happen to look for and load policies.
		//
		this.init();
	}

	/**
	 * Loads the <code>PolicyDef</code> for the given <code>String</code> identifier by looking first
	 * for a ".file" property associated with the ID and using that to load from a <code>File</code> and
	 * looking for a ".url" property associated with the ID and using that to load from a <code>URL</code>.
	 * 
	 * @param policyId the <code>String</code> identifier for the policy
	 * @return a <code>PolicyDef</code> loaded from the given identifier
	 */
	protected PolicyDef loadPolicyDef(String policyId) {
		String propLocation = null;
		if (this.properties == null) {
			propLocation	= XACMLProperties.getProperty(policyId + PROP_FILE);
		} else {
			propLocation	= this.properties.getProperty(policyId + PROP_FILE);
		}
		if (propLocation != null) {
			File fileLocation	= new File(propLocation);
			if (!fileLocation.exists()) {
				XACMLPdpPolicyFinderFactory.logger.error("Policy file " + fileLocation.getAbsolutePath() + " does not exist.");
			} else if (!fileLocation.canRead()) {
				XACMLPdpPolicyFinderFactory.logger.error("Policy file " + fileLocation.getAbsolutePath() + " cannot be read.");
			} else {
				try {
					XACMLPdpPolicyFinderFactory.logger.info("Loading policy file " + fileLocation);
					PolicyDef policyDef	= DOMPolicyDef.load(fileLocation);
					if (policyDef != null) {
						return policyDef;
					}
				} catch (DOMStructureException ex) {
					XACMLPdpPolicyFinderFactory.logger.error("Error loading policy file " + fileLocation.getAbsolutePath() + ": " + ex.getMessage(), ex);
					return new Policy(StdStatusCode.STATUS_CODE_SYNTAX_ERROR, ex.getMessage());
				}
			}
		}
		if (this.properties == null) {
			propLocation = XACMLProperties.getProperty(policyId + PROP_URL);
		} else {
			propLocation = this.properties.getProperty(policyId + PROP_URL);
		}
		if (propLocation != null) {
			 InputStream is = null;
			try {
				URL url						= new URL(propLocation);
				URLConnection urlConnection	= url.openConnection();
				XACMLPdpPolicyFinderFactory.logger.info("Loading policy file " + url.toString());
				is = urlConnection.getInputStream();
				PolicyDef policyDef			= DOMPolicyDef.load(is);
				if (policyDef != null) {
					return policyDef;
				}
			} catch (MalformedURLException ex) {
				XACMLPdpPolicyFinderFactory.logger.error("Invalid URL " + propLocation + ": " + ex.getMessage(), ex);
			} catch (IOException ex) {
				XACMLPdpPolicyFinderFactory.logger.error("IOException opening URL " + propLocation + ": " + ex.getMessage(), ex);
			} catch (DOMStructureException ex) {
				XACMLPdpPolicyFinderFactory.logger.error("Invalid Policy " + propLocation + ": " + ex.getMessage(), ex);
				return new Policy(StdStatusCode.STATUS_CODE_SYNTAX_ERROR, ex.getMessage());
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						XACMLPdpPolicyFinderFactory.logger.error("Exception closing InputStream for GET of url " + propLocation + " : " + e.getMessage() + "  (May be memory leak)", e);
					}
				}
			}
		}
		
		XACMLPdpPolicyFinderFactory.logger.error("No known location for Policy " + policyId);
		return null;
	}
	
	/**
	 * Finds the identifiers for all of the policies referenced by the given property name in the
	 * <code>XACMLProperties</code> and loads them using the requested loading method.
	 * 
	 * @param propertyName the <code>String</code> name of the property containing the list of policy identifiers
	 * @return a <code>List</code> of <code>PolicyDef</code>s loaded from the given property name
	 */
	protected List<PolicyDef> getPolicyDefs(String propertyName) {
		String policyIds	= XACMLProperties.getProperty(propertyName);
		if (policyIds == null || policyIds.length() == 0) {
			return null;
		}
		
		Iterable<String> policyIdArray	= Splitter.on(',').trimResults().omitEmptyStrings().split(policyIds);
		if (policyIdArray == null) {
			return null;
		}
		
		List<PolicyDef> listPolicyDefs	= new ArrayList<PolicyDef>();
		for (String policyId : policyIdArray) {
			PolicyDef policyDef	= this.loadPolicyDef(policyId);	
			if (policyDef != null) {
				listPolicyDefs.add(policyDef);
			}
		}
		return listPolicyDefs;
	}
	
	protected synchronized void init() {
		if (this.needsInit) {
			if (XACMLPdpPolicyFinderFactory.logger.isDebugEnabled()) {
				XACMLPdpPolicyFinderFactory.logger.debug("Initializing");
			}
			this.rootPolicies		= this.getPolicyDefs(XACMLProperties.PROP_ROOTPOLICIES);
			this.referencedPolicies	= this.getPolicyDefs(XACMLProperties.PROP_REFERENCEDPOLICIES);
			if (XACMLPdpPolicyFinderFactory.logger.isDebugEnabled()) {
				XACMLPdpPolicyFinderFactory.logger.debug("Root Policies: " + this.rootPolicies);
				XACMLPdpPolicyFinderFactory.logger.debug("Referenced Policies: " + this.referencedPolicies);
			}
			this.needsInit	= false;
		}
	}
	
	@Override
	public PolicyFinder getPolicyFinder() throws FactoryException {
		//
		// Force using any properties that were passed upon construction
		//
		return new StdPolicyFinder(this.rootPolicies, this.referencedPolicies, this.properties);
	}

	@Override
	public PolicyFinder getPolicyFinder(Properties properties) throws FactoryException {
		return new StdPolicyFinder(this.rootPolicies, this.referencedPolicies, properties);
	}

}
