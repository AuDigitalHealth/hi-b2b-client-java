# HI B2B Client

Maven library for Australia's **Healthcare Identifiers (HI) Service** over **JAX-WS (SOAP)**. It provides Java clients for **IHI** (individual), **HPI-I** (provider person), and **HPI-O** (provider organisation) operations.

**Audience:** applications that add **`au.gov.nehta:hi-b2b-client`** as a dependency, supply mutual-TLS credentials and ADHA-registered product metadata, and resolve the licensed WSDL/XSD tree at runtime.

Traffic uses **HTTPS with mutual TLS** and **signed** SOAP. You need ADHA / Services Australia **registration**, test or production **certificates**, and **endpoint URLs** before live calls succeed.

Registration: https://implementer.digitalhealth.gov.au/resources/hi-service-registration-and-certificates

---

## Terms

| Term | Meaning |
| ---- | ------- |
| ADHA | Australian Digital Health Agency (specs, registration). |
| HI Service | National SOAP services for healthcare identifiers. |
| IHI | Individual Healthcare Identifier. |
| HPI-I | Healthcare Provider Identifier - Individual. |
| HPI-O | Healthcare Provider Identifier - Organisation. |
| NEHTA | Name used in Java package namespaces (`nehta`). |

---

## Dependency

Add the artifact from [Maven Central](https://central.sonatype.com/). Use a **`<version>`** that matches your JDK and API stack (see **Release lines**).

```xml
<dependency>
  <groupId>au.gov.nehta</groupId>
  <artifactId>hi-b2b-client</artifactId>
  <version>1.7.0</version>
</dependency>
```

**This line (`1.7.0`):** Java **11**, Jakarta XML Web Services / JAXB, **26** full MCA facade clients. Transitive runtime includes **`com.sun.xml.ws:jaxws-rt`** (Jakarta). No separate JDK SOAP install is required.

SOAP application code uses **`jakarta.xml.ws`**, **`jakarta.xml.bind`**, and related Jakarta APIs. Java SE types such as **`javax.net.ssl`** and **`javax.xml.datatype.XMLGregorianCalendar`** are unchanged.

---

## Release lines

| Version | Java | APIs | Facade clients |
| ------- | ---- | ---- | -------------- |
| **1.6.3** | 8 | **`javax.xml.ws`**, **`javax.xml.bind`** | **14** (standard HI B2B) |
| **1.6.5** | 11 | **Jakarta** XML WS / Bind | **14** (standard HI B2B) |
| **1.7.0** | 11 | **Jakarta** XML WS / Bind | **26** (full MCA) |

All published versions are on **[Maven Central](https://central.sonatype.com/)**.

---

## Licensed WSDL and XSD (runtime)

The published JAR does **not** contain HI WSDL or XSD files. Obtain the ADHA/Services Australia bundle under your licence and make it available at runtime.

1. Download from https://healthsoftware.humanservices.gov.au/claiming/ext-vnd/ (layout details: **`wsdls/README.md`**).
2. Install so one directory has **immediate** children **`wsdl/`** and **`schema/`** (lowercase; case-sensitive on some hosts).

Point the library at that directory with **`au.gov.nehta.vendorlibrary.hi.wsdl.HiWsdlArtifactRoot`**. Resolution order (first match wins):

1. **`HiWsdlArtifactRoot.setRoot(Path)`**
2. Environment variable **`HI_WSDL_ARTIFACT_ROOT`**
3. JVM system property **`-DHI_WSDL_ARTIFACT_ROOT=...`**
4. Key **`HI_WSDL_ARTIFACT_ROOT`** in a **`local.properties`** file in the JVM working directory (**`user.dir`**), if present

Optional: place WSDL on the application classpath under your licence (fallback when no root is configured).

See **`wsdls/README.md`** for property names and directory layout.

---

## Application configuration

Construct a facade client (for example **`ConsumerSearchIHIClient`**) with:

| Item | Purpose |
| ---- | ------- |
| **`SSLSocketFactory`** | Mutual TLS to the HI endpoint. |
| Private key + certificate | TLS and SOAP signing (often from a PKCS#12 keystore). |
| Endpoint URL | SOAP service URL from your registration (cert vs production). |
| Product / vendor / user qualified IDs | Values issued for your software product. |
| WSDL root | As above (`HiWsdlArtifactRoot` or equivalent). |

Load keystores, truststores, and identifiers from your platform (secrets manager, environment, or configuration files). Do not commit credentials to source control (**`SECURITY.md`**).

Use **forward slashes** in filesystem paths inside property files (for example `./config/keystore.jks`) so the same values work on Windows, macOS, and Linux.

### Configuration keys (optional)

If you use a **`local.properties`** file in **`user.dir`**, the library reads **`HI_WSDL_ARTIFACT_ROOT`** and related keys documented in **`local.properties.example`** (template for integrators and for this repository's tests).

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

Your production application may use environment variables or a secrets store instead of **`local.properties`**.

---

## Client classes

Package base: **`au.gov.nehta.vendorlibrary.hi`**. Version **1.7.0** exposes **26** facade classes:

| Area | Classes |
| ---- | ------- |
| **IHI** (`ihi`) | `ConsumerSearchIHIClient`, `ConsumerSearchIHIBatchSyncClient`, `ConsumerSearchIHIBatchAsyncClient`, `ConsumerCreateProvisionalIHIClient`, `ConsumerMergeProvisionalIHIClient`, `ConsumerUpdateProvisionalIHIClient`, `ConsumerCreateUnverifiedIHIClient`, `ConsumerResolveProvisionalIHIClient`, `ConsumerNotifyDuplicateIHIClient`, `ConsumerNotifyReplicaIHIClient`, `ConsumerUpdateIHIClient`, `ConsumerCreateVerifiedIHIClient` |
| **HPI-I** (`hpii`) | `ProviderSearchForProviderIndividualClient`, `ProviderSearchHIProviderDirectoryForIndividualClient`, `SearchForProviderIndividualBatchAsyncClient`, `ProviderManageTdsProviderIndividualClient`, `ProviderSearchTdsProviderIndividualClient` |
| **HPI-O** (`hpio`) | `ProviderSearchForProviderOrganisationClient`, `ProviderSearchHIProviderDirectoryForOrganisationClient`, `SearchForProviderOrganisationBatchAsyncClient`, `ProviderReadProviderOrganisationClient`, `ProviderReadAdministrativeIndividualClient`, `ProviderManageProviderOrganisationClient`, `ProviderManageProviderDirectoryEntryClient`, `ProviderManageProviderAdministrativeIndividualClient` |
| **Reference data** | `ReadReferenceDataClient` |

Published Javadoc is attached to releases on Maven Central.

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

| Document | Audience |
| -------- | -------- |
| **`wsdls/README.md`** | Licensed WSDL/XSD layout and runtime properties |
| **`SECURITY.md`** | Secrets and reporting |
| **`CONTRIBUTING.md`** | Building or changing this repository from source |
| **`MAINTAINERS.md`** | Release and build internals |

---

## Licensing

Copyright 2011 NEHTA  
Copyright 2021-2026 ADHA (Australian Digital Health Agency)

Licensed under the Apache License, Version 2.0. See **`LICENSE.md`**.
