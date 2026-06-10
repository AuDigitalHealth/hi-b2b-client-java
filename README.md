# HI B2B Client

Maven library for Australia's **Healthcare Identifiers (HI) Service** over **JAX-WS (SOAP)**. It provides Java clients for **IHI** (individual), **HPI-I** (provider person), and **HPI-O** (provider organisation) operations.

**Audience:** applications that add **`au.gov.nehta:hi-b2b-client`** as a dependency, supply mutual-TLS credentials and ADHA-registered product metadata, and resolve the licensed WSDL/XSD tree at runtime.

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

Add the artifact from [Maven Central](https://central.sonatype.com/). Use a **`<version>`** that matches your JDK and API stack (see **Release lines**).

```xml
<dependency>
  <groupId>au.gov.nehta</groupId>
  <artifactId>hi-b2b-client</artifactId>
  <version>1.6.5</version>
</dependency>
```

**This line (`1.6.5`):** Java **11**, **Jakarta** XML Web Services / JAXB, **14** standard HI B2B facade clients. Transitive runtime includes **`com.sun.xml.ws:jaxws-rt`** **4.0.4**.

---

## Release lines

| Version | Java | APIs | Facade clients |
| ------- | ---- | ---- | -------------- |
| **1.6.3** | 8 | **`javax.xml.ws`**, **`javax.xml.bind`** | **14** (standard HI B2B) |
| **1.6.5** | 11 | **Jakarta** XML WS / Bind | **14** (standard HI B2B) |
| **1.7.0** | 11 | **Jakarta** XML WS / Bind | **26** (full MCA) |

All published versions are on **[Maven Central](https://central.sonatype.com/)**.

SOAP application code on **`1.6.5`** uses **`jakarta.xml.ws`**, **`jakarta.xml.bind`**, and related Jakarta APIs. Java SE types such as **`javax.net.ssl`** and **`javax.xml.datatype.XMLGregorianCalendar`** are unchanged. SOAP types come from **`au.gov.nehta:hi-wsdl`** at the same version when both artifacts are on the classpath.

---

## Licensed WSDL and XSD (runtime)

The published JAR does **not** contain HI WSDL or XSD files. You must obtain the ADHA/Services Australia bundle under your licence and make it available at runtime.

1. Download from https://healthsoftware.humanservices.gov.au/claiming/ext-vnd/ (see **`wsdls/README.md`**).
2. Install so one directory has **immediate** children **`wsdl/`** and **`schema/`** (lowercase).

Point the library at that directory using **`au.gov.nehta.vendorlibrary.hi.wsdl.HiWsdlArtifactRoot`**. Resolution order (first match wins):

1. **`HiWsdlArtifactRoot.setRoot(Path)`**
2. Environment variable **`HI_WSDL_ARTIFACT_ROOT`**
3. Key **`HI_WSDL_ARTIFACT_ROOT`** in **`local.properties`** in the JVM **working directory** (`user.dir`)
4. JVM system property **`-DHI_WSDL_ARTIFACT_ROOT=...`**

Optional: place WSDL on the application classpath under your licence (fallback when no root is configured).

The default Maven lifecycle compiles against **`au.gov.nehta:hi-wsdl`** and does **not** run **`wsimport`**. Optional Ant helpers under **`wsdls/`** may use **`hi.wsdl.tree.root`** locally; see **`wsdls/README.md`**.

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

Package base: **`au.gov.nehta.vendorlibrary.hi`**. This artifact (**1.6.5**) exposes **14** standard HI B2B facade classes.

| Area | Classes |
| ---- | ------- |
| **IHI** (`ihi`) | `ConsumerSearchIHIClient`, `ConsumerSearchIHIBatchSyncClient` |
| **HPI-I** (`hpii`) | `ProviderSearchForProviderIndividualClient`, `ProviderSearchHIProviderDirectoryForIndividualClient`, `SearchForProviderIndividualBatchAsyncClient` |
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
| **`wsdls/README.md`** | WSDL download, layout, runtime property names |
| **`SECURITY.md`** | Secrets and reporting |
| **`CONTRIBUTING.md`** | Building or changing this repository from source |
| **`MAINTAINERS.md`** | Release and build internals |
| Javadoc | Attached to releases on Maven Central |

---

## Licensing

Copyright 2011 NEHTA  
Copyright 2021-2026 ADHA (Australian Digital Health Agency)

Licensed under the Apache License, Version 2.0. See **`LICENSE.md`**.
