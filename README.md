# HI B2B Client

Maven library for Australia's **Healthcare Identifiers (HI) Service** over **JAX-WS (SOAP)**. It provides Java clients for **IHI** (individual), **HPI-I** (provider person), and **HPI-O** (provider organisation) operations.

**Audience:** applications that depend on **`au.gov.nehta:hi-b2b-client`**, supply mutual-TLS credentials and ADHA-registered product metadata, and resolve the licensed WSDL/XSD tree at runtime. To **build or change this repository**, see **`CONTRIBUTING.md`**, **`MAINTAINERS.md`**, and **`SECURITY.md`**.

Traffic uses **HTTPS with mutual TLS** and **signed** SOAP. You need ADHA / Services Australia **registration**, test or production **certificates**, and **endpoint URLs** before live calls succeed.

---

## Terms

| Term | Meaning |
| ---- | ------- |
| ADHA | Australian Digital Health Agency (specs, registration). |
| HI Service | National SOAP services for healthcare identifiers. |
| IHI | Individual Healthcare Identifier. |
| HPI-I | Healthcare Provider Identifier - Individual. |
| HPI-O | Healthcare Provider Identifier - Organisation. |
| NEHTA | Historical agency name; package namespaces may still use `nehta`. |

Registration: https://implementer.digitalhealth.gov.au/resources/hi-service-registration-and-certificates

---

## Dependency

Use the **`<version>`** from [Maven Central](https://central.sonatype.com/) or from the **`pom.xml`** of the release you build locally:

```xml
<dependency>
  <groupId>au.gov.nehta</groupId>
  <artifactId>hi-b2b-client</artifactId>
  <version>VERSION</version>
</dependency>
```

**Runtime:** JDK **11+** (see **`maven.compiler.release`** in **`pom.xml`**). Maven pulls **Jakarta XML Web Services** and JAXB via **`jaxws-rt`**; no separate JDK SOAP install.

SOAP types use **`jakarta.xml.ws`**, **`jakarta.xml.bind`**, and related Jakarta APIs. Java SE types such as **`javax.net.ssl`** and **`javax.xml.datatype.XMLGregorianCalendar`** are unchanged.

---

## Licensed WSDL and XSD (runtime)

The published JAR does **not** contain HI WSDL or XSD files. You must obtain the ADHA/Services Australia bundle under your licence and make it available at runtime.

1. Download from https://healthsoftware.humanservices.gov.au/claiming/ext-vnd/ (see **`wsdls/readme.txt`**).
2. Install so one directory has **immediate** children **`wsdl/`** and **`schema/`** (lowercase).

Point the library at that directory using **`au.gov.nehta.vendorlibrary.hi.wsdl.HiWsdlArtifactRoot`**. Resolution order (first match wins):

1. **`HiWsdlArtifactRoot.setRoot(Path)`**
2. Environment variable **`HI_WSDL_ARTIFACT_ROOT`**
3. Key **`HI_WSDL_ARTIFACT_ROOT`** in **`local.properties`** in the JVM **working directory** (`user.dir`)
4. JVM system property **`-DHI_WSDL_ARTIFACT_ROOT=...`**

Optional: place WSDL on the application classpath under your licence (fallback when no root is configured).

**`hi.wsdl.tree.root`** and **`HI_WSDL_TREE_ROOT`** are used only when **building this project from source**; they are not read at runtime. See **`CONTRIBUTING.md`** and **`wsdls/readme.txt`**.

---

## What you configure in your application

Construct a facade client (for example **`ConsumerSearchIHIClient`**) with:

| Item | Purpose |
| ---- | ------- |
| **`SSLSocketFactory`** | Mutual TLS to the HI endpoint. |
| Private key + certificate | TLS and SOAP signing (often from a PKCS#12 keystore). |
| Endpoint URL | SOAP service URL from your registration (cert vs production). |
| Product / vendor / user qualified IDs | Values issued for your software product. |
| WSDL root | As above (`HiWsdlArtifactRoot` or equivalent). |

Load keystores, truststores, and identifiers from your platform (secrets manager, environment, or config files). Do not commit credentials to source control (**`SECURITY.md`**).

Keystore paths in **`.properties`** files: prefer forward slashes (`./config/keystore.jks`).

### Optional `local.properties` keys

If **`local.properties`** exists in **`user.dir`**, the library reads **`HI_WSDL_ARTIFACT_ROOT`** from it (see **`local.properties.example`**). The same property names are used by **this repository's** integration tests when you build from a checkout (**`CONTRIBUTING.md`**); your production app may use different configuration.

| Key | Purpose |
| --- | --- |
| **`HI_WSDL_ARTIFACT_ROOT`** | Runtime WSDL/XSD root (`wsdl/` + `schema/`). |
| **`HI_KEYSTORE_*`**, **`HI_KEY_PASSWORD`** | Client keystore for TLS/signing. |
| **`HI_KEY_ALIAS_MEDICARE_IHI`** | Private-key alias (IHI operations). |
| **`HI_KEY_ALIAS_MEDICARE_HPIO`** | Private-key alias (HPIO/HPI-I operations). |
| **`HI_KEY_ALIAS_MEDICARE`** | Fallback alias when the specific alias is unset. |
| **`HI_TRUSTSTORE_*`** | Truststore for the HI HTTPS server. |
| **`HI_MEDICARE_ENDPOINT_BASE`** | SOAP base URL (cert environment example in **`local.properties.example`**). |
| **`HI_USER_*`**, **`HI_VENDOR_*`**, **`HI_HPIO_*`**, **`HI_PRODUCT_*`** | Registration metadata. |

Copy **`local.properties.example`** to **`local.properties`**, fill in values, and keep **`local.properties`** out of Git.

---

## Client classes

Package base: **`au.gov.nehta.vendorlibrary.hi`**.

| Area | Classes |
| ---- | ------- |
| **IHI** (`ihi`) | `ConsumerSearchIHIClient`, `ConsumerSearchIHIBatchSyncClient`, `ConsumerSearchIHIBatchAsyncClient`, `ConsumerCreateProvisionalIHIClient`, `ConsumerMergeProvisionalIHIClient`, `ConsumerUpdateProvisionalIHIClient`, `ConsumerCreateUnverifiedIHIClient`, `ConsumerResolveProvisionalIHIClient`, `ConsumerNotifyDuplicateIHIClient`, `ConsumerNotifyReplicaIHIClient`, `ConsumerUpdateIHIClient`, `ConsumerCreateVerifiedIHIClient` |
| **HPI-I** (`hpii`) | `ProviderSearchForProviderIndividualClient`, `ProviderSearchHIProviderDirectoryForIndividualClient`, `SearchForProviderIndividualBatchAsyncClient`, `ProviderManageTdsProviderIndividualClient`, `ProviderSearchTdsProviderIndividualClient` |
| **HPI-O** (`hpio`) | `ProviderSearchForProviderOrganisationClient`, `ProviderSearchHIProviderDirectoryForOrganisationClient`, `SearchForProviderOrganisationBatchAsyncClient`, `ProviderReadProviderOrganisationClient`, `ProviderReadAdministrativeIndividualClient`, `ProviderManageProviderOrganisationClient`, `ProviderManageProviderDirectoryEntryClient`, `ProviderManageProviderAdministrativeIndividualClient` |
| **Reference data** | `ReadReferenceDataClient` |

Samples under **`src/sample/java`** (`...hi.sample`) are not on the default classpath; see **`CONTRIBUTING.md`** to compile them from a source checkout.

---

## Consumer Search IHI: methods and `SearchIHI` fields

`ConsumerSearchIHIClient` validates the payload before each SOAP call. **`checkCommonMandatoryParameters`** requires **family name**, **date of birth**, and **sex**; **given name** is optional.

| Client method | Required identity | Must not set (among others) |
| --- | --- | --- |
| **`basicSearch`** | **IHI number** | Medicare card, DVA file, address blocks |
| **`detailedSearch`** | None (demographics only) | **IHI number**, Medicare, DVA, address blocks |
| **`basicMedicareSearch`** | Medicare card number | **IHI number**, DVA, address blocks |
| **`basicDvaSearch`** | DVA file number | **IHI number**, Medicare, address blocks |

Address searches (`australianPostalAddressSearch`, `australianStreetAddressSearch`, `internationalAddressSearch`) also require **`ihiNumber`** to be unset. Use **`detailedSearch`** for demographics-only lookup, not **`basicSearch`**.

Batch sync/async clients apply the same rules in **`SearchBatch.ArgumentValidator`**.

---

## Further reading

| Document | Content |
| -------- | ------- |
| **`CONTRIBUTING.md`** | Building from source, tests, CI, line endings. |
| **`MAINTAINERS.md`** | `wsimport`, POM, codegen. |
| **`SECURITY.md`** | Secrets and reporting. |
| **`wsdls/readme.txt`** | WSDL download, layout, build vs runtime property names. |
| [hi-wsdl-java](https://github.com/AuDigitalHealth/hi-wsdl-java) | Optional types-only JAR (**`-Phi-wsdl-artifact`**). |
| **`CHANGELOG.md`** | Release history. |
| Javadoc | **`mvn javadoc:javadoc`** from a source checkout. |

---

## Licensing

Copyright 2011 NEHTA  
Copyright 2021-2026 ADHA (Australian Digital Health Agency)

Licensed under the Apache License, Version 2.0. See **`LICENSE.txt`**.
