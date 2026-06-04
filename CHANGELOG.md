# Change Log/Revision History

= 1.7.0-SNAPSHOT =
======
- **Docs / build:** **README**, **CONTRIBUTING**, **AGENTS**, **MAINTAINERS**, **`settings.xml.example`**, and **`.github/workflows/ci.yml`** state that **WSDL/XSD under `wsdls/xml/`** are **tracked in Git** on this branch; **`hi.wsdl.tree.root`** overrides remain for parity with other branches. **MAINTAINERS** adds **branch scope (Java 11 migration)**, **fast builds**, **contributors vs release publisher** (`pom.xml`), and **changelog** header rules (**`= version =`**).
- **POM:** Profile **`dev-javadoc-off`** skips the Javadoc JAR for faster local **`verify`**; **`maven-enforcer-plugin`** execution id **`require-wsdl-tree`** with an updated sentinel message.
- **Fat JAR (optional):** Maven profile **`fat-jar`** (**`maven-shade-plugin`**, classifier **`all`**) merges runtime dependencies; root **`build.*`** scripts accept **`shaded`** to activate it. **`ArgumentUtils`** from **`smi-xsp`** is excluded from the shaded artifact so the in-repo implementation is used. Shade uses **`ApacheLicenseResourceTransformer`** and **`ApacheNoticeResourceTransformer`**, drops **`module-info.class`**, dependency **`META-INF/MANIFEST.MF`**, and **`META-INF/hk2-locator/default`** from **`org.glassfish.ha:ha-api`** to avoid duplicate-resource warnings.
- **WSDL tree location:** Build-time **`hi.wsdl.tree.root`** (default **`wsdls/xml`**) resolved in **`initialize`** via **`-Dhi.wsdl.tree.root`**, **`HI_WSDL_TREE_ROOT`**, or an active Maven user-settings profile; default checkout uses plain **`mvn`** (no **`-s`**). **`maven-enforcer-plugin`** requires the sentinel consumer WSDL unless **`-Dhi.wsdl.codegen.skip=true`**. Runtime: **`HiWsdlArtifactRoot`** + **`HI_WSDL_ARTIFACT_ROOT`** / **`local.properties`** / **`setRoot(Path)`** before classpath in **`WebServiceClientUtil`**; **`maven-jar-plugin`** excludes **`*.wsdl`** from the published JAR. Removed copying WSDLs into **`target/classes`**.
- Target Java 11 (`--release 11`) with Jakarta XML Web Services / JAXB (Eclipse EE4J `jaxws-rt` 4.x). **`jaxws-rt`** and plugin **`jaxws-tools`** override at **4.0.4** (current Central **`jaxws-rt`** release); **`jaxws-maven-plugin`** stays **4.0.2** because **4.0.4** declares required Java **17** for the plugin on the JDK **11** build. **`jakarta.xml.ws-api`**, **`jakarta.xml.bind-api`**, and **`jakarta.xml.soap-api`** are direct compile dependencies (versions aligned with **`jaxws-rt`**). **`au.gov.nehta:smi-xsp`** **1.2.1** (sole Central release). **`org.apache.groovy`** **4.0.32** (latest stable **4.0.x** for **`gmavenplus-plugin`**). **`maven-enforcer-plugin`** **3.6.2**, **`maven-jar-plugin`** **3.5.0**, **`maven-shade-plugin`** **3.6.2**; other pinned plugins already current for Maven **3.6.3+** per **`versions-maven-plugin`** / Central metadata. **`jakarta.xml.bind-api`** stays **4.0.4** GA (not **4.1.0-M1**).
- Vendored HI WSDLs under `wsdls/xml/` and in-repo `wsimport`; removed Maven dependencies on `hi-wsdl`, `common-library`, and `smi-common-utils` in favour of generated types and helpers. **`wsdls/xml/binding`** headers match **`origin/master`**; branch-only **`wsdls/`** deltas: **`ee4j-jaxws-lib-pom.xml`** (EE4J JAR sync for optional Ant; **`ee4j.jaxws.version`** aligned with root **`jaxws.rt.version`**), **`readme.txt`** (current download URL + Maven build note), **`build.xml` / `build.properties` / `.classpath`** for **`local-lib/ee4j-jaxws-from-maven`**, **`src/sample/resources/buildInterface.xml`** JAR glob, vendored Metro JARs removed from Git, **`wsdls/local-lib/`** gitignored.
- `ArgumentUtils`: `isNullOrBlank` for `smi-xsp`; validation messages for null/blank fields match legacy expectations.
- `WebServiceClientUtil`: instantiate the generated `Service` subclass for EE4J `jaxws-rt` 4.x metadata; WSDL resolution uses `HiWsdlArtifactRoot` (filesystem) before the thread context classloader and the util class loader.
- Integration tests: TLS uses project truststore plus default JVM CA anchors (`TestSslSupport`). `local.properties` and/or `HI_*` for keystore, endpoints, and identifiers (`TestConfiguration`; environment overrides file). `HI_KEY_ALIAS_MEDICARE_IHI` and `HI_KEY_ALIAS_MEDICARE_HPIO` with fallback to `HI_KEY_ALIAS_MEDICARE` when suites need different Medicare key aliases.
- Maven Surefire: **`skipTests`** defaults to **`false`** so **`mvn test`** / **`verify`** run tests; the default **`<includes>`** list is **`TimeUtilityTest`**, **`TestConfigurationTest`**, **`JdbcTransactionTestRuleTest`**, and **`HiWsdlArtifactRootTest`**, so **`mvn verify`** passes without HI keystores (JVM **`-Xmx1024m`**). Full mutual-TLS coverage: **`mvn -Pintegration test`** (or **`verify`**) with **`local.properties`** / **`HI_*`** as documented.
- Root **`.md`** documentation updated for Java 11, Jakarta, Maven **`wsimport`**, and repository hygiene (Java/XML headers -> **`LICENSE.txt`**; **`wsdls/readme.txt`** still references **`wsdls/license.txt`**; **`.gitignore`** includes **`/*.pdf`**, **`README.md.bak*`**, **`wsdls/local-lib/`**).
- **Maven `verify`:** **`maven-source-plugin`** **`jar-no-fork`** replaces **`jar`** so attaching **`-sources`** does not fork **`generate-sources`** (avoids running **`wsimport`** / **`gmavenplus-plugin`** merge twice on every **`verify`**, including **`build.* shaded`**). **`maven-javadoc-plugin`** **`verbose`** **`false`** trims Javadoc diagnostic I/O; **`detectOfflineLinks`** **`false`** skips offline link checks. **`jaxws-maven-plugin`** **`quiet`** **`true`** suppresses repetitive **`wsimport`** output during **`generate-sources`**.
- **Build time:** **`gmavenplus-plugin`** **`initialize`** runs **`configure-hi-wsdl-tree-root`** and clears prior **`wsimport`** output in one execution (two scripts, one Groovy startup). **`maven-compiler-plugin`** **`proc`** **`none`** skips annotation processing (none is used in this tree).

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
