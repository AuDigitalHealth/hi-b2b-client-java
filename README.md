# HI B2B Client

This is a software library that provides example client implementations of the
NEHTA Healthcare Identifiers (HI) Service (as currently operated by Medicare)
using Java.

More information about the HI Service (and the specifications implemented by
this library) is available from the following URL:
http://www.medicareaustralia.gov.au/provider/health-identifier/index.jsp

Installation
============

To use the distributable package, the following must be installed:

This distribution
-----------------
1. Unpack the hi-b2b-client-<VERSION>.zip file to a
   desired directory location. <HI_CLIENT_HOME> will be used in this
   document to refer to this directory.

2. Contents:

2.1 The <HI_CLIENT_HOME>\hi-b2b-client-<VERSION>.jar
file provides the class library.

2.2 The <HI_CLIENT_HOME>\hi-b2b-client-<VERSION>-sources.jar
file contains the source code for the library.

2.3 The <HI_CLIENT_HOME>\hi-b2b-client-<VERSION>-javadocs.jar
file contains the Javadoc documentation for the library.

2.4 The license agreement for use of this library is in the
<HI_CLIENT_HOME>\LICENSE.txt file.

2.5 This readme file is <HI_CLIENT_HOME>\README.md

Java Development Kit (JDK)
--------------------------
1. Download and install JDK 8 Update 271 or later.
   URL: http://java.sun.com/javase/downloads/index.jsp

   IMPORTANT NOTE: If you use a JDK version less than 1.7 you must override the JAXB RI implementation
   by providing a JAXB RI greater than 2.2.

   You can download the JAXB jars from http://jaxb.java.net/
   You must then place jaxb-api.jar in the <JDK_HOME>\jre\lib\endorsed dir

2. Unpack the JDK distribution into a directory of your choice.

   This directory will be your <JDK_HOME> and will be used in this document
   to refer to the root directory of the JDK installation.

   <JRE_HOME> will be used in this document to refer to <JDK_HOME>\jre.

3. Create a JAVA_HOME environment variable pointing to the <JDK_HOME>
   directory in 2.

4. Add <JDK_HOME>\bin to your system path.

JCE Policy Files
----------------
The Java Cryptography Extension (JCE) provides cryptography services in the JDK.
The JCE policy files in the JDK download are limited in strength due to the
import control restrictions for some countries. The "unlimited strength"
capabilities are enabled by installing certain policy files into the JRE.

1. Download the JCE Unlimited Strength Jurisdiction Policy Files for the
   installed JDK version.
   URL: http://java.sun.com/javase/downloads/index.jsp

2. Unpack the downloaded ZIP file.

3. Copy the two JAR files (local_policy.jar and US_export_policy.jar) to the
   <JRE_HOME>\lib\security directories. Overwrite the existing JAR files in the directory.

Metro Files
-----------
The HI Client library requires the Metro Web Services toolkit and its associated
libraries to be setup correctly within your Java Runtime Environment. The following
steps will assist you in setting up Metro.

1. Download the Metro 2.1 distribution file (metro-standalone-2.1.zip) from http://metro.java.net/2.1/

2. Unpack the downloaded ZIP file.

3. Copy the following files from the ZIP file:

   lib\webservices-api.jar
   lib\webservices-rt.jar

   to your <JDK_HOME>\lib\endorsed and <JRE_HOME>\lib\endorsed directories.

Client instantiation
=====================
The HI client library consists of the following five distinct Web Service clients:

1. ConsumerSearchIHIClient                                - For individual (IHI) searches
2. ConsumerSearchIHIBatchSyncClient                       - For synchronous batch individual (IHI) searches
3. ProviderSearchHIProviderDirectoryForIndividualClient   - For health provider (HPI-I) searches
4. ProviderSearchHIProviderDirectoryForOrganisationClient - For health organisation (HPI-O) searches
5. ProviderReadReferenceDataClient                        - For Provider Reference Element's lookup.

1. Requirements:

   a) A Transport Layer Security (TLS) public/private key pair and its associated public certificate
   These are used to authenticate the client to the HI Service server instance being used during the Transport Layer
   Security (TLS) handshake. They are typically stored in a Java key store file.

   b) A signing public/private key pair and its associated public certificate
   These are used by the client to sign all Web Service requests to the HI Service server. The associated public
   certificate is always an organisation certificate provided by a recognized Certificate Authority. These are also
   typically stored in a Java key store file which may be the same as the one used for the key pair in (a).

   c) The certificate of the Certificate Authority (CA) which signed the HI Service server's TLS certificate.
   This certificate is used to authenticate the HI Service server during the TLS handshake. This certificate is typically
   stored in a Java trust store file.

   d) Medicare authentication details
   These will be provided by Medicare, and include a Qualified Identifier identifying you to Medicare. These details should
   be instantiated as a Java QualifiedId object.

   e) Client product information details (PCIN)
   These include a Qualified ID for the product, the product name and version, and the product platform. These should
   all be instantiated in a Java Holder<ProductType> object.

   f) Based on the key pair in (a), a Java SSLSocketFactory object needs to be instantiated and provided as an argument
   to instantiate all of the clients above.

   g) The endpoint URLs for the HI Service.

Notes
=========
Currently, all consumer operations have been modified, removing the soapAction=https://ns.electronichealth.net.au" attribute
in the operation element in the WSDL files. In place of this, a soapActionRequired="false" attribute has been specified.
This removes issues that present when using JAX-WS code generation.

Licensing
=========
Copyright 2011 NEHTA
Copyright 2021 ADHA

Licensed under the NEHTA/ADHA Open Source (Apache) License; you may not use this
file except in compliance with the License. A copy of the License is in the
'license.txt' file, which should be provided with this work.

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
License for the specific language governing permissions and limitations
under the License.