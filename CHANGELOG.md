# Change Log/Revision History

# = 24.0.0 =

- Maven **`au.gov.nehta:hi-b2b-client`** **24.0.0** (Java **24** / **Jakarta**, **26** facade clients, full MCA). Pair with **`hi-wsdl`** **24.0.0**. The first number of both Maven versions is the targeted Java SE version.
- **`smi-xsp`** at **`nehta.xsp.lib.version`** = **`${project.version}`** (**24.0.0**).
- Runtime: Eclipse EE4J **`jaxws-rt` 4.0.5**; in-repo **`wsimport`** via **`jaxws-maven-plugin` 4.0.5** (JDK 17+).
- Messages `searchIHI`: `electronicCommunication` before `dateOfBirth` (international address before unstructured) so Consumer Search IHI (including batch) request XML matches the HI Service message schema.
- Interface `searchIHI`: optional `electronicCommunication` before `dateOfBirth` and `australianUnstructuredStreetAddress` before `internationalAddress` on **`HI_ConsumerSearchIHIInterface-3.0`**.
- For in-repo `wsimport`, keep that particle order in licensed `SearchIHIMessages.xsd` (see `wsdls/README.md`). With `-Phi-wsdl-artifact`, use matching **`au.gov.nehta:hi-wsdl`** **24.0.0**.
- **Tests:** **`HiRequestElementOrderParityTest`** asserts JAXB `propOrder` for Messages/Interface `searchIHI`, batch `SearchIHIRequestType`, HI31/HI33 `searchForProviderIndividual`, TDS search `providerIndividual`, and Australian/International address types (same sequences as **`hi-wsdl`**). **`HiFacadeCoverageTest`** asserts **26** MCA facades and generated **`Service`** types.
- Consumer Search IHI: **`australianUnstructuredStreetAddressSearch`** (suburb, state, postcode required). Structured Australian street search requires **`streetType`** when **`streetNumber`** is set; structured and unstructured addresses are mutually exclusive.

# = 21.0.0 =

- Maven **`au.gov.nehta:hi-b2b-client`** **21.0.0** (Java **21** / **Jakarta**, **26** facade clients, full MCA). Pair with **`hi-wsdl`** **21.0.0**. The first number of both Maven versions is the targeted Java SE version.
- **`smi-xsp`** at **`nehta.xsp.lib.version`** = **`${project.version}`** (**21.0.0**).
- Runtime: Eclipse EE4J **`jaxws-rt` 4.0.5**; in-repo **`wsimport`** via **`jaxws-maven-plugin` 4.0.5** (JDK 17+).
- Messages `searchIHI`: `electronicCommunication` before `dateOfBirth` (international address before unstructured) so Consumer Search IHI (including batch) request XML matches the HI Service message schema.
- Interface `searchIHI`: optional `electronicCommunication` before `dateOfBirth` and `australianUnstructuredStreetAddress` before `internationalAddress` on **`HI_ConsumerSearchIHIInterface-3.0`**.
- For in-repo `wsimport`, keep that particle order in licensed `SearchIHIMessages.xsd` (see `wsdls/README.md`). With `-Phi-wsdl-artifact`, use matching **`au.gov.nehta:hi-wsdl`** **21.0.0**.
- **Tests:** **`HiRequestElementOrderParityTest`** asserts JAXB `propOrder` for Messages/Interface `searchIHI`, batch `SearchIHIRequestType`, HI31/HI33 `searchForProviderIndividual`, TDS search `providerIndividual`, and Australian/International address types (same sequences as **`hi-wsdl`**). **`HiFacadeCoverageTest`** asserts **26** MCA facades and generated **`Service`** types.
- Consumer Search IHI: **`australianUnstructuredStreetAddressSearch`** (suburb, state, postcode required). Structured Australian street search requires **`streetType`** when **`streetNumber`** is set; structured and unstructured addresses are mutually exclusive.

# = 17.0.0 =

- Maven **`au.gov.nehta:hi-b2b-client`** **17.0.0** (Java **17** / **Jakarta**, **26** facade clients, full MCA). Pair with **`hi-wsdl`** **17.0.0**. The first number of both Maven versions is the targeted Java SE version.
- **`smi-xsp`** at **`nehta.xsp.lib.version`** = **`${project.version}`** (**17.0.0**).
- Runtime: Eclipse EE4J **`jaxws-rt` 4.0.5**; in-repo **`wsimport`** via **`jaxws-maven-plugin` 4.0.5** (JDK 17+).
- Messages `searchIHI`: `electronicCommunication` before `dateOfBirth` (international address before unstructured) so Consumer Search IHI (including batch) request XML matches the HI Service message schema.
- Interface `searchIHI`: optional `electronicCommunication` before `dateOfBirth` and `australianUnstructuredStreetAddress` before `internationalAddress` on **`HI_ConsumerSearchIHIInterface-3.0`**.
- For in-repo `wsimport`, keep that particle order in licensed `SearchIHIMessages.xsd` (see `wsdls/README.md`). With `-Phi-wsdl-artifact`, use matching **`au.gov.nehta:hi-wsdl`** **17.0.0**.
- **Tests:** **`HiRequestElementOrderParityTest`** asserts JAXB `propOrder` for Messages/Interface `searchIHI`, batch `SearchIHIRequestType`, HI31/HI33 `searchForProviderIndividual`, TDS search `providerIndividual`, and Australian/International address types (same sequences as **`hi-wsdl`**). **`HiFacadeCoverageTest`** asserts **26** MCA facades and generated **`Service`** types.
- Consumer Search IHI: **`australianUnstructuredStreetAddressSearch`** (suburb, state, postcode required). Structured Australian street search requires **`streetType`** when **`streetNumber`** is set; structured and unstructured addresses are mutually exclusive.

# = 11.0.0 =

- Maven **`au.gov.nehta:hi-b2b-client`** **11.0.0** (Java **11** / **Jakarta**, **26** facade clients, full MCA). Pair with **`hi-wsdl`** **11.0.0**. The first number of both Maven versions is the targeted Java SE version.
- **`smi-xsp`** at **`nehta.xsp.lib.version`** = **`${project.version}`** (**11.0.0**).
- Messages `searchIHI`: `electronicCommunication` before `dateOfBirth` (international address before unstructured) so Consumer Search IHI (including batch) request XML matches the HI Service message schema.
- Interface `searchIHI`: optional `electronicCommunication` before `dateOfBirth` and `australianUnstructuredStreetAddress` before `internationalAddress` on **`HI_ConsumerSearchIHIInterface-3.0`**.
- For in-repo `wsimport`, keep that particle order in licensed `SearchIHIMessages.xsd` (see `wsdls/README.md`). With `-Phi-wsdl-artifact`, use matching **`au.gov.nehta:hi-wsdl`** **11.0.0**.
- **Tests:** **`HiRequestElementOrderParityTest`** asserts JAXB `propOrder` for Messages/Interface `searchIHI`, batch `SearchIHIRequestType`, HI31/HI33 `searchForProviderIndividual`, TDS search `providerIndividual`, and Australian/International address types (same sequences as **`hi-wsdl`**).
- Consumer Search IHI: **`australianUnstructuredStreetAddressSearch`** (suburb, state, postcode required). Structured Australian street search requires **`streetType`** when **`streetNumber`** is set; structured and unstructured addresses are mutually exclusive.
- Runtime: Eclipse EE4J **`jaxws-rt` 4.0.5**; **`smi-xsp`** at **`${project.version}`**.

# = 1.7.0 =

- Java **11**, Jakarta XML Web Services / JAXB (**`jaxws-rt` 4.x**). **26** full MCA facade clients over licensed **`HI_*`** WSDLs.
- In-repo **`wsimport`** (26 executions) with build-time root **`hi.wsdl.tree.root`** (default **`wsdls/xml`**). Optional profile **`hi-wsdl-artifact`** skips codegen when **`au.gov.nehta:hi-wsdl`** is on the classpath. Published JAR excludes **`*.wsdl`**; runtime WSDL via **`HiWsdlArtifactRoot`** / **`HI_WSDL_ARTIFACT_ROOT`**.
- Optional **`fat-jar`** profile and **`build.* shaded`** produce classifier **`all`** uber-JAR.
- Default Surefire runs offline unit tests; **`-Pintegration`** for mutual-TLS against cert endpoints with **`local.properties`** / **`HI_*`**.

# = 1.6.2 =

- Fixed HI WSDL ref and updated WS library

# = 1.6.1 =

- Converted to Maven build with Maven dependencies.
- Cleaned up references to out-of-date libraries.

# = 1.6.0 =

- Added Provider Read Provider Organisation client
- Added Provider Read Provider Administrative Individual client
- Added Provider Manage Provider Organisation client
- Added Provider Manage Provider Directory client
- Added Provider Manage Provider Administrative Individual client

# = 1.5.0 =

- updated nehta-smi-xsp-1.2.0.jar - this includes a refactored CertificateVerifier,
  renamed to CertificateValidator the older CertificateVerifier will continue to work if used from
  nehta-vendorlibrary-java-common-1.1.0.

- updated nehta-vendorlibrary-java-common-1.1.0 to accommodate changes in xsp and new CertificateValidator
- changed the default CertificateValidator from the Null impl to one that does basic validations, time and trust
- HISecurity Handler no longer assigns its own xml:id to signed elements, this is left to xsp-api now.
  as a result, xml:id's are no longer prefixed with 'ts*' 'data*' etc.

- Clients now accept an optional CertificateValidator that will be applied by the HI security handler when processing
  signed SOAP responses, if a null validator is supplied the client will still check the validity dates of the
  certificate used to sign the response SOAP message.

# = 1.4.1 =

- updated Nehta common lib jar to 1.0.4
- changed client interface to use ProductType instead of Holder<ProductType>
- Added wrapped xml types for ProductType and QualifiedId for better reuse between clients
- Added new constructors to clients to accept the new wrapped xml types
- Added overloaded request methods that accept a QualifiedId, so the current user can be supplied per request.

# = 1.4.0 =

- change to wsdl v.1.4.0 which overrode xmldsig (hi_override package) for downstream clients
- change to all clients to expose the underlying web service port via client.getPort()
- change to all clients to configure com.sun.xml.internal.ws as well as com.sun.xml.ws for TLS
- ConsumerSearchIHIBatchSyncClient.BatchSearch is no longer an inner type. ie: BatchSearch batch = new BatchSearch()

# = 1.3.1 =

- Added support for JAX-WS > 2.1
  Medicare does not support <FaultTo>, therefore this header is removed before signing.
- JAX-WS reference implementation stack updated to 2.3 / JAX-WS 2.2.8

# = 1.3.0 =

- Support Services:
  1.  ProviderSearchForProviderOrganisationClient
  2.  ProviderSearchForProviderIndividualClient

# = 1.2.0 =

- Support for CSP organisation acting on behalf of organisation for:
  1. ProviderSearchHIProviderDirectoryForIndividualClient
  2. ProviderSearchHIProviderDirectoryForOrganisationClient
  3. ReadReferenceDataClient
