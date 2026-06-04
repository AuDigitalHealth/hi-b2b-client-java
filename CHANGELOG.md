# Change Log/Revision History

= 1.6.5-SNAPSHOT =
======
Changes not yet assigned to a numbered release; **`pom.xml`** **`<version>`** is **`1.6.5-SNAPSHOT`** until release **1.6.5** is tagged.

- **`hi-wsdl`** **1.6.2** (**`hi.wsdl.version`**) - current **`au.gov.nehta:hi-wsdl`** on **Maven Central**; **`javax.*`** / Java **8** bytecode in generated types; default lifecycle does not run **`wsimport`**.
- Java **8** (`maven.compiler.release` **8**) / **`javax.*`** application and generated types; JAX-WS runtime **`com.sun.xml.ws:jaxws-rt`** **2.3.7** with Metro default transitive API JARs (EE4J Maven coordinates; **`javax.*`** packages). No **`jaxws-rt`** exclusions or extra SOAP API dependencies in this **`pom.xml`** except **`webservices-rt`** excluded from **`common-library`**.
- Default **`skipTests`** **false**; Surefire runs **`TimeUtilityTest`**, **`TestConfigurationTest`**, and **`HiWsdlArtifactRootTest`** unless skipped; **`-Pintegration`** runs **`**/*Test.java`** with **`hi.require.live.config`** (mutual-TLS; requires **`local.properties`** / **`HI_*`**). Offline **`TimeUtilityTest`** matches **`TimeUtility`** from **`common-library`** at **`nehta.lib.version`**.
- Removed unused direct **`au.gov.nehta:smi-common-utils`**; added explicit **`au.gov.nehta:smi-xsp`** at **`nehta.lib.version`** for **`ArgumentUtils`** / **`CertificateValidator`** clarity. Removed **`jaxb-api`**, **`bcpkix-jdk15on`**, and **`spring-test`**; tests use **`ReflectionFieldSetter`** instead of Spring for field injection.
- Public **`au.gov.nehta.vendorlibrary.hi.*`** client surface matches the **`hi-wsdl`** artifact at **`hi.wsdl.version`** (no extra facade types such as async helpers or custom policy validators in this line).
- Documentation and CI: default **`mvn -B -Dgpg.skip=true clean verify`** (no project **`settings.xml`**; keeps **`~/.m2/settings.xml`** mirrors). **`settings.xml.example`** is optional; documents **`hi-wsdl-tree`** / **`mvn -s`** tradeoff. **`build.*`** match CI; optional **`MVN_SETTINGS`**. **README** / **CONTRIBUTING** updated accordingly. **README** prioritises library integrators; **MAINTAINERS.md** has **`target/`** cleanup; **CONTRIBUTING** has line-ending guidance.
- **Runtime WSDL tree:** **`au.gov.nehta.vendorlibrary.hi.wsdl.HiWsdlArtifactRoot`** resolves **`HI_WSDL_ARTIFACT_ROOT`** (env, **`local.properties`**, system property, or **`setRoot(Path)`**). **`au.gov.nehta.vendorlibrary.ws.WebServiceClientUtil`** in this artifact replaces **`common-library`**'s util for **`javax.xml.ws`** clients and consults **`HiWsdlArtifactRoot`** before the classpath. **`local.properties.example`** documents **`HI_WSDL_ARTIFACT_ROOT`**; **`fat-jar`** shading excludes **`WebServiceClientUtil.class`** from **`common-library`**.
- **`fat-jar`:** **`maven-shade-plugin`** filters (**`module-info.class`**, versioned **`module-info.class`**, dependency **`META-INF/MANIFEST.MF`**, **`META-INF/hk2-locator/default`** on **`ha-api`**) plus **`ApacheLicenseResourceTransformer`** and **`ApacheNoticeResourceTransformer`**. Shading also excludes duplicate **`ArgumentUtils`** from **`smi-xsp`** and **`common-library`** where this artifact supplies **`WebServiceClientUtil`** / **`ArgumentUtils`**. Overlapping activation implementation and API JARs may still log duplicate **`javax.activation`** class warnings at shade time; see **MAINTAINERS** if you need a stricter merge strategy.
- **Build:** **`maven-source-plugin`** **`jar-no-fork`** avoids forking **`generate-sources`** when attaching **`-sources`**. **`maven-clean-plugin`** **`retryOnError`** and **`force`**. **`wsdls/ee4j-jaxws-lib-pom.xml`** **`maven-dependency-plugin`** **3.10.0**. **README** / **MAINTAINERS** clarify **`jakarta.*`** Maven coordinates vs **`javax.*`** packages for **`jaxws-rt`** **2.3.x**. **`.cursor/rules/java-maven-defaults.mdc`** documents **`java-8-javax`** vs Java **11** branch stacks. **`maven-javadoc-plugin`** **3.12.0** uses **`jar`** (no **`jar-no-fork`** goal in this plugin line).

## 1.6.2

- Fixed HI WSDL reference and updated WS library.

## 1.6.1

- Maven build with Central dependencies.
- Removed references to obsolete libraries.

## 1.6.0

- Added Provider Read Provider Organisation client.
- Added Provider Read Provider Administrative Individual client.
- Added Provider Manage Provider Organisation client.
- Added Provider Manage Provider Directory client.
- Added Provider Manage Provider Administrative Individual client.

## 1.5.0

- **`smi-xsp`**: `CertificateVerifier` refactored to **`CertificateValidator`** (older **`CertificateVerifier`** remains usable when loaded from **nehta-vendorlibrary-java-common** **1.1.0**).
- **`common-library`** **1.1.0** updated for **`CertificateValidator`** integration.
- Default **`CertificateValidator`** is no longer a null implementation; it performs basic validation (time and trust).
- **HISecurityHandler** no longer assigns **`xml:id`** on signed elements (handled by **xsp-api**); **`xml:id`** values are no longer prefixed with **`ts_`**, **`data_`**, etc.
- Clients accept an optional **`CertificateValidator`** for signed SOAP responses; when **`null`**, clients still validate signing certificate validity dates.

## 1.4.1

- **common-library** updated to **1.0.4**.
- Client interfaces use **`ProductType`** instead of **`Holder<ProductType>`**.
- Wrapped XML types for **`ProductType`** and **`QualifiedId`** for reuse across clients; new constructors and **`QualifiedId`** overloads on request methods.

## 1.4.0

- WSDL **1.4.0** with **`xmldsig`** override (**`hi_override`** package) for downstream clients.
- Clients expose the underlying port via **`getPort()`**.
- TLS configuration for **`com.sun.xml.internal.ws`** and **`com.sun.xml.ws`**.
- **`ConsumerSearchIHIBatchSyncClient.BatchSearch`** is no longer an inner type (**`BatchSearch`** is a top-level class).

## 1.3.1

- JAX-WS **> 2.1** support; **`<FaultTo>`** header removed before signing (Medicare does not support it).
- JAX-WS runtime updated to **2.3** / **2.2.8** line.

## 1.3.0

- **`ProviderSearchForProviderOrganisationClient`**
- **`ProviderSearchForProviderIndividualClient`**

## 1.2.0

- CSP organisation acting on behalf of organisation for **`ProviderSearchHIProviderDirectoryForIndividualClient`**, **`ProviderSearchHIProviderDirectoryForOrganisationClient`**, and **`ReadReferenceDataClient`**.
