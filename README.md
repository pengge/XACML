
# AT&T XACML

AT&amp;T's reference implementation of the OASIS XACML 3.0 Standard. The AT&T framework represents the entire XACML 3.0 object set as a collection of Java interfaces and standard implementations of those interfaces.  The AT&T PDP engine is built on top of this framework and represents a complete implementation of a XACML 3.0 PDP, including all of the multi-decision profiles. In addition, the framework also contains an implementation of the OASIS XACML 3.0 RESTful API v1.0 and XACML JSON Profile v1.0 WD 14. The PEP API includes annotation functionality, allowing application developers to simply annotate a Java class to provide attributes for a request. The annotation support removes the need for application developers to learn much of the API.

The AT&T framework also includes interfaces and implementations to standardize development of PIP engines that are used by the AT&T PDP implementation, and can be used by other implementations built on top of the AT&T framework. The framework also includes interfaces and implementations for a PAP distributed cloud infrastructure of PDP nodes that includes support for policy distribution and pip configurations. This PAP infrastructure includes a web application administrative console that contains a XACML 3.0 policy editor, attribute dictionary support, and management of PDP RESTful node instances. In addition, there are tools available for policy simulation.

The [wiki] (https://github.com/att/XACML/wiki) has a lot of information on the organization of the software. 

Click here to view the [JavaDoc] (http://att.github.io/XACML/javadocs/index.html).

Tutorials and other useful information is also located on our [Github Pages site] (http://att.github.io/XACML). TBD - working on this 10/14.

# Requirements

* Java JDK 1.7, you will need to ensure Eclipse has that JDK installed in your development environment.



*  Apache Ivy to bring external dependencies into the build environment. You will need to install the Apache IvyDE Eclipse plug-in for these dependencies to be resolved.

# Building the source code

The AT&T Laboratories development team uses Eclipse to build the software. Simply import each project into an Eclipse workspace.

## Eclipse Luna 4.4.1 Problems

### Ivy - Able to retrieve dependency jars but is unable to resolve, thus resulting in compilation errors.
Ivy resolution management seems to have a bug in it starting with Luna v4.4.1. v4.0.1 seems to work fine. See https://issues.apache.org/jira/browse/IVY-1487 for more information.

To resolve it, upgrade to the latest release of Ivy:

https://builds.apache.org/job/ivyDE-updatesite/lastSuccessfulBuild/artifact/trunk/build/

After Eclipse restarts, you may have to still do an Ivy refresh or resolve for each project. Or possibly "Clean All Projects" could do the trick.

### JPA Problems in XACML-PAP-ADMIN project
In luna 4.4.1, JPA may show errors that a class listed in the persistence.xml is not annotated. Clearly, they are annotated but it seems that the Eclipse Project needs some additional setup.

1. Bring up the Project's Properties: right-click the project and select "Properties" menu item.
2. Select JPA from the left-side menu.
3. Under the "JPA Implementation" box on the right-side, click the "Download Library" icon on the farthest right-side.
4. Select EclipseLink 2.5.2 and then "Next", accept the terms and click "Finish".
5. You may still have to clean the project and/or resolve ivy dependencies for Eclipse to resolve any remaining compilation problems.

# Milestones

6/10/2014 - Initial Project loaded into Github.

