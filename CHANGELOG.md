# Change Log/Revision History

= 1.6.5-SNAPSHOT =
======
Changes not yet assigned to a numbered release; **`pom.xml`** **`<version>`** is **`1.6.5-SNAPSHOT`** until release **1.6.5** is tagged.

- **Java 11 / Jakarta:** **`maven.compiler.release` 11**; Eclipse EE4J **`com.sun.xml.ws:jaxws-rt` 4.0.4**; **`maven-enforcer-plugin`** bans legacy Metro **`webservices-*`** and **`javax.xml.ws` / `javax.xml.bind` / `javax.xml.soap`** coordinates. Application SOAP code uses **`jakarta.xml.ws`** / **`jakarta.xml.bind`**; **`hi-wsdl`** at **`hi.wsdl.version`** = **`${project.version}`** (install matching **`hi-wsdl-java`** for unpublished SNAPSHOTs). Default lifecycle does **not** run **`wsimport`**.
- **Dependencies:** **`au.gov.nehta:smi-xsp` 1.2.1** plus **`hi-wsdl`** and **`jaxws-rt`** at compile/runtime scope. In-repo **`ArgumentUtils`**, **`KeystoreUtil`**, **`SimpleCertificateValidator`**, **`TimeUtility`**, **`WebServiceClientUtil`**, **`HiWsdlArtifactRoot`**, **`LoggingHandler`**, and **`hi_override` XMLDSig** types replace former transitive helpers from **`common-library`** / **`smi-common-utils`**.
- **Handlers / clients:** **`HISecurityHandler`**, **`HIHeaderHandler`**, and facade clients updated for Jakarta APIs and **`jaxws-rt` 4.x** **`Service`** metadata. Removed unused **`BaseClient_30`**. Batch **`SearchIHI`** types use **`au.net.electronichealth.ns.hi.consumermessages.searchihi._3`** (Jakarta **`hi-wsdl`** package layout).
- **Tests:** **`TestConfiguration`**, **`TestSslSupport`**, **`TestReflect`**; default Surefire runs offline-safe tests (**`TimeUtilityTest`**, **`TestConfigurationTest`**, **`HiWsdlArtifactRootTest`**, **`ConsumerSearchIHIClientArgumentValidatorTest`**); **`-Pintegration`** runs **`**/*Test.java`** with **`local.properties`** / **`HI_*`**. International-address test fixtures use **`CountryType`** from Jakarta **`hi-wsdl`**.
- **Docs / CI:** **`LICENSE.md`**, release-line tables, **`AGENTS.md`**, **`.cursor/rules/*`**. CI: Temurin **11**, **`mvn verify`** + **`sample`** compile. Licensed WSDL/XSD remain **out of Git** (**`.gitignore`**); runtime via **`HI_WSDL_ARTIFACT_ROOT`**.
- **Optional:** **`fat-jar`** profile (**`maven-shade-plugin`**, classifier **`all`**); **`dev-javadoc-off`** for faster local **`verify`**.

= 1.6.2 =
======
- Fixed HI WSDL ref and updated WS library

= 1.6.1 =
======
- Converted to Maven build with Maven dependencies.
- Cleaned up references to out-of-date libraries.

= 1.6.0 =
======
- Added Provider Read Provider Organisation client
- Added Provider Read Provider Administrative Individual client
- Added Provider Manage Provider Organisation client
- Added Provider Manage Provider Directory client
- Added Provider Manage Provider Administrative Individual client


= 1.5.0 =
======
- updated nehta-smi-xsp-1.2.0.jar - this includes a refactored CertificateVerifier,
  renamed to CertificateValidator the older CertificateVerifier will continue to work if used from
  nehta-vendorlibrary-java-common-1.1.0.

- updated nehta-vendorlibrary-java-common-1.1.0 to accommodate changes in xsp and new CertificateValidator
- changed the default CertificateValidator from the Null impl to one that does basic validations, time and trust
- HISecurity Handler no longer assigns its own xml:id to signed elements, this is left to xsp-api now.
  as a result, xml:id's are no longer prefixed with 'ts_' 'data_' etc.

- Clients now accept an optional CertificateValidator that will be applied by the HI security handler when processing
  signed SOAP responses,  if a null validator is supplied the client will still check the validity dates of the
  certificate used to sign the response SOAP message.

= 1.4.1 =
======
- updated Nehta common lib jar to 1.0.4
- changed client interface to use ProductType instead of Holder<ProductType>
- Added wrapped xml types for ProductType and QualifiedId for better reuse between clients
- Added new constructors to clients to accept the new wrapped xml types
- Added overloaded request methods that accept a QualifiedId, so the current user can be supplied per request.

= 1.4.0 =
======
- change to wsdl v.1.4.0 which overrode xmldsig (hi_override package) for downstream clients
- change to all clients to expose the underlying web service port via client.getPort()
- change to all clients to configure com.sun.xml.internal.ws as well as com.sun.xml.ws for TLS
- ConsumerSearchIHIBatchSyncClient.BatchSearch is no longer an inner type. ie: BatchSearch batch = new BatchSearch()

= 1.3.1 =
======
- Added support for JAX-WS > 2.1
  Medicare does not support <FaultTo>, therefore this header is removed before signing.
- JAX-WS reference implementation stack updated to 2.3 / JAX-WS 2.2.8

= 1.3.0 =
======
- Support Services:
  1.    ProviderSearchForProviderOrganisationClient
  2.   ProviderSearchForProviderIndividualClient


= 1.2.0 =
======
- Support for CSP organisation acting on behalf of organisation for:
  1. ProviderSearchHIProviderDirectoryForIndividualClient
  2. ProviderSearchHIProviderDirectoryForOrganisationClient
  3. ReadReferenceDataClient
