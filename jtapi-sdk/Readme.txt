****************************************************************************
                Avaya Application Enablement (AE) Services
                             JTAPI SDK Readme
                               Release 6.3.3
****************************************************************************

This readme file includes the following topics:

o  What's new for JTAPI, Release 6.3.3?

o  Updates to the configuration mechanism of Avaya's JTAPI implementation

o  Updates to the Programmer's Reference (javadoc)

o  AE Services JTAPI supports the JTAPI 1.4 specification

o  AE Services JTAPI requires JDK 7 or higher

o  Avaya Private Data

o  About the JTAPI SDK

o  AE Services JTAPI documentation

o  SDK Online Support

==============================================================================
What's new for JTAPI, Release 6.3.3?
==============================================================================

o  JTAPI Release 6.3.3 is a feature pack release for JTAPI Release 6.3.1. It adds 
   support for private data version 12.
 
==============================================================================
Updates to the configuration mechanism of Avaya's JTAPI implementation
==============================================================================
Beginning with AE Services 5.2, new keys have been added to the configuration 
mechanism to allow greater control over the implementation's functioning.
The following behavior of the JTAPI client is now configurable using the keys 
specified ...

o propertyRefreshRate: controls the refresh rate after which TSAPI.PRO 
  properties and system properties are re-read.
o callCleanupRate: specifies the rate (in seconds) at which JTAPI will audit 
  calls to clean up any calls that should no longer exist.
o callCompletionTimeout: specifies the maximum number of seconds to wait for 
  postconditions to be met following a Call.connect(), 
  LucentCallEx2.fastConnect(), CallControlCall.consult() or 
  CallCenterCall.connectPredictive() operation.
o getServicesTimeout: specifies the maximum number of seconds to wait for AES 
  to respond with the set of TLinks that it supports.

The following logging related configuration parameters have been added as a 
result of log4j support being introduced.

o traceFileCount: specifies the maximum number of trace files. 
o traceFileSize: specifies the maximum size of trace files.
o errorFile: specifies the filename for error logs 
o errorCount: specifies the maximum number of error files.
o enableAuditDump: specifies whether the audit thread should dump state to the 
  log file. This new flag must be enabled to see the audit dumps regardless of 
  how the TSAPI.PRO debugLevel or log4j log levels are configured.

============================================================================== 
Updates to the Programmer's Reference (javadoc)
==============================================================================
Beginning with AE Services 5.2, the Programmer's Reference are now bundled in 
the javadoc directory of this distribution.
Changes include
o A new public package "com/avaya/jtapi/tsapi/adapters" is available. This
  package contains adapter equivalents for all listener interfaces in the 
  JTAPI specification.
o The description of the com/avaya/jtapi/tsapi package has now been moved to 
  the Avaya MultiVantage Application Enablement Services JTAPI Programmer's 
  Guide.
o Avaya specific implementation details of standard JTAPI API's are clearly 
  marked as "Implementation Notes". Please refer to these while designing your 
  application.
o A large portion of the javax/telepony/media package has been excluded from 
  the javadoc. This package is an optional part of the JTAPI specification 
  which Avaya does not support.
    
==============================================================================
AE Services JTAPI supports the JTAPI 1.4 specification
==============================================================================

The JTAPI SDK supports the JTAPI 1.4 specification.

IMPORTANT:
          All new applications must be developed with the new JTAPI jar file:
          ecsjtapia.jar (JTAPI client).

          AE Services JTAPI uses one consolidated jar file.

          All applications developed prior to AE Services
          Release 3.1.0 must be recompiled in order to use the
          new JTAPI jar file (3.1.0, or later).

Also, as of Release 5.2 of the JTAPI SDK, JTAPI SDK now supports the JTAPI 1.4 
javax.telephony.Listener mechanism.

The JTAPI 1.4 specification is a superset of the JTAPI 1.2 specification.
With JTAPI 1.4 specification, the support for observers has been deprecated.

With the introduction of log4j, TSProvider.debug and 
TSProvider.getTSEHandler() has been removed. 

The AE Services JTAPI 3.1.0, or later, implementation uses a private interface
to the RouteUsed event (LucentV7RouteUsed). This private RouteUsed event
returns an address (which is consistent with the JTAPI 1.2 specification),
instead of a terminal (which is specified by the 1.4 JTAPI specification).

If you plan to use the RouteUsed event, you must make sure you recode the
RouteUsed event to the private from. For example:

 Change this:
    "Address ad: RouteUsed ev;
     ad= ev.getRouteUsed(); // JTAPI 1.2 API code"

 To this:
    ad= ((LucentV7RouteUsed)ev).getRouteUsedAddress();  // JTAPI 1.4 API code
                                                        // with Avaya private
                                                        // interface

==============================================================================
AE Services JTAPI requires JDK 7 or higher
==============================================================================

As of Release 6.2 of the JTAPI client, AE Services JTAPI requires that you
run the JTAPI application using Java SDK 7 or higher.

==============================================================================
Avaya Private Data
==============================================================================

As of Release 6.3.3, the JTAPI client supports private data version 12.
For more information, see the AE Services JTAPI for Communication Manager
Programmer's Reference.

==============================================================================
About the JTAPI SDK
==============================================================================

IMPORTANT: 
o There is no longer a separate JTAPI client download. You must extract 
  ecsjtapia.jar (JTAPI client) file from the SDK.
o There is no longer a windows installer available. As mentioned 
  below, ant should be used for building and running sample apps and the 
  JTAPI exerciser.
o It's necessary to include a log4j jar file in the classpath. 
  log4j-1.2.12.jar file under SDK/lib can be used.
   
o Starting AE Services 7.0, new AE Services server installs will not
  have Avaya provided default certificate installed on AE Services 
  server. Instead, AE Services server will have self-signed default
  certificate. If you need to use TLS connection when connecting to
  TSAPI service, you can export the AE Service server trust certificates 
  installed on AE Services server. This certificate can be obtained via
  AE Services server management Web console by going to 
  "Security -> Certificate Management -> CA Trusted Certificates" page
  and then check the certificate you want to export and then click on
  export button. This opens a new page with the certificate in a 
  window. Copy the entire text in the window and save it to a file 
  e.g., aesvcsCA.cer file.

  Copy the aesvcsCA.cer file to SDK/resources/aesvcsCA.cer and run
  the following command. The following command uses "password" as a 
  keystore password, please use the correct keystore password if it 
  was changed since SDK installation.

  keytool -alias aesvcs -import -file aesvcsCA.cer -keystore avayaprca.jks -storepass password

The following directories and files are created when the SDK is expanded.

    SDK/ -- the directory containing the SDK.
	
    SDK/Readme.txt -- this file.
	
    SDK/LICENSE.txt -- Avaya copyright notification.
	
    SDK/LICENSE.log4j -- log4j license file.
	
    SDK/.project -- An eclipse project file.
	
    SDK/ant -- ant 1.7.1 distribution
	
    SDK/resources/avayaprca.jks  -- a Java Key Store containing the 
                                    Avaya Product PKI Root CA Certificate.
		
    SDK/apps/ --  contains 5 sample applications under their respective
			source directories as mentioned below
			acdSrc - ACD sample app
			autoanswerSrc - AutoAnswer sample app
			calllogSrc - Call Log sample app
			routeSrc - Route Sample App 
			tstestSrc - TsTest Sample App
			click2callSrc - Click2Call Sample App.
 
	SDK/apps/bin -- contains buildscripts to build and run sample apps.
			      Instructions for building and running sample apps
			      are given below.		
	
	SDK/apps/build/tstest.war -- Deployable war file to demonstrate 
	                                   JTAPI in a J2EE environment.	
										
    SDK/tools/jtapiex -- contains the JTAPI exerciser.
	
	SDK/tools/bin -- contains build.xml to run Jtapi exerciser.
			 Instructions for running Jtapi exerciser are given below.		
	
	SDK/conf -- contains following configuration files
             	    TSAPI.PRO - used for configuring JTAPI
		    log4j.properties - used for configuring log4j logging.
		    click2call.properties - used for LDAP configuration
				to be used in the Click2Call sample app.
    
	SDK/lib/ -- contains following libraries
	            ecsjtapia.jar - JTAPI client library
		    log4j-1.2.12.jar - log4j library
		    servlet-api-2.5-6.1.1.jar - The Java servlet specification 
				
				
	SDK/javadoc/ -- The Programmer's Reference for the JTAPI spec and this 
	                JTAPI implementation.
				
Instructions for building and running the sample apps
-----------------------------------------------------
o Locate TSAPI.PRO file under SDK/conf directory. Edit it to specify an 
  IP address / DNS name of your telephony server along with default port of 450
  For eg, if the IP address of your telephony server is 10.1.70.23 
  then the entry in TSAPI.PRO should be 10.1.70.23=450
o Please find build.sh, build.bat and build.xml placed under 
  SDK/apps/bin.
o There are 6 ant targets in build.xml for building sample apps (one for
  each sample app). Similarly there are 6 ant targets for running sample apps
  (one for each sample app). For eg, if you need to build ACD sample app,
  you should use the ant target "buildAcdSampleApp". Similarly for running 
  ACD sample app, use the ant target "runAcdSampleApp". 
o Please use the "distTsTestWebApp" target to create the web version of the 
  TSTest application, which creates a WAR file, that can be deployed in any 
  compliant J2EE container.
o If you have created a eclipse project, then you can simply right click on the
  ant target in build.xml to execute it.
o If not, then please follow the steps below
  	o In a command shell, please change your current directory to 
  	  SDK/apps/bin.
  	o Pass the ant target as a parameter to ant.sh (if you are in a linux 
      environment) or to ant.bat (if you are in a windows environment). Please
      run the ant "help" target for more information regarding these various 
      targets.
    
Instructions for running the JTAPI exerciser
--------------------------------------------
o Locate TSAPI.PRO file under SDK/conf directory. Edit it to specify an 
  ip address / DNS name of your telephony server along with default port of 450
  For eg, if the IP address of your telephony server is 10.1.70.23 
  then the entry in TSAPI.PRO should be 10.1.70.23=450
o As mentioned above, a separate build.xml is placed under SDK/tools/bin.
o If you have created a eclipse project, right click on the ant target 
  "runJtapiExerciser" in build.xml to run the JTAPI exerciser,
o If not, then please follow the steps below
  	o In a command shell, please change your current directory to SDK/tools/bin.
  	o Please execute the file 'ant.sh' (if you are in a linux environment) or 
  	  'ant.bat' (if you are in a windows environment). This will execute the 
  	  default ant target i.e. "runJtapiExerciser" in build.xml.  
  
For information about using the SDK and developing JTAPI applications,
please see the Avaya MultiVantage Application Enablement Services JTAPI 
Programmer's Guide.
This document contains
o A guide to the JTAPI spec in general and Avaya JTAPI architecture in 
  particular.
o A detailed description of the SDK contents.
o A guide to using the JTAPI exerciser and samples.
o A guide to quickly writing a JTAPI client application.
o A description of the sections of the JTAPI spec unsupported by this JTAPI 
  implementation and other implementation level details you should be aware of
  while writing a non-trivial application.
o A description of the non-standard Avaya specific additions to the JTAPI API 
  created to make Communication Manager features, TSAPI service extensions and
  Avaya private data extensions available to users.
o Mapping of TSAPI and JTAPI constructs
o Hints to debug a JTAPI client application

==============================================================================
AE Services JTAPI documentation
==============================================================================

The AE Services JTAPI Programmer's Guide is available on the
Avaya Support Web site.

1. Go to http://www.avaya.com/support

2. From the "Support" page, click the "Find Documentation and Technical
   Information by Product Name" link.

3. From the "Find Documentation and Downloads by Product Name" page,
   click the "Application Enablement Services" link.

4. From the "Application Enablement Services" page,
   click the "View all documents" link.

5. From the "Application Enablement Services: All Documents" page,
   select "Application Enablement Services JTAPI Programmer's Guide."

==============================================================================
SDK Online Support
==============================================================================

Up to date information, including patches and Frequently Asked
Questions are available on the Web. Here are instructions for accessing
JTAPI SDK Support.

1. Go to http://www.avaya.com/support

2. From the "Support" page, click the "Find Documentation and Technical
   Information by Product Name" link.

3. From the "Find Documentation and Downloads by Product Name" page,
   click the "Computer Telephony SDK" link.

4. From the "Computer Telephony SDK" page, click the appropriate link.

****************************************************************************
****************************************************************************
