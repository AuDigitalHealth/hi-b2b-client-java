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

Add the artifact from [Maven Central](https://central.sonatype.com/). Use a **`<version>`** that matches your JDK (see **Versioning**).

```xml
<dependency>
  <groupId>au.gov.nehta</groupId>
  <artifactId>hi-b2b-client</artifactId>
  <version>8.0.0</version>
</dependency>
```

**This line (`8.0.0`):** Java **8**, **`javax.xml.ws`** / **`javax.xml.bind`**, **14** standard HI B2B facade clients. Add **`com.sun.xml.ws:jaxws-rt`** **2.3.7** at runtime in your application. Pair with **`au.gov.nehta:hi-wsdl`** **`8.0.0`**.

---

## Versioning

The **first number** of the Maven version is the **Java SE** version that line targets. **`hi-b2b-client`** and **`hi-wsdl`** always use the **same** version on a given line (same SNAPSHOT or GA).

| Maven version | Java SE | APIs | Facade clients |
| ------------- | ------- | ---- | -------------- |
| **8.0.0** | **8** | **`javax.xml.ws`**, **`javax.xml.bind`** | **14** (standard HI B2B) |
| **11.0.0.1** | **11** | **Jakarta** XML WS / Bind | **26** (full MCA) |
| **17.0.0.1** | **17** | **Jakarta** XML WS / Bind | **26** (full MCA) |
| **21.0.0.1** | **21** | **Jakarta** XML WS / Bind | **26** (full MCA) |
| **24.0.0.1** | **24** | **Jakarta** XML WS / Bind | **26** (full MCA) |

Pick the coordinate that matches your JDK. Do not mix **`hi-b2b-client`** and **`hi-wsdl`** versions. All published versions are on **[Maven Central](https://central.sonatype.com/)**.

---

## Note

The **8.0.0** release does not support the full WSDL specification (**14** facades, **`javax`**). **11.0.0.1** and later lines use **Jakarta** and expose all **26** facades.

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

Package base: **`au.gov.nehta.vendorlibrary.hi`**. This artifact line (**8.0.0**) exposes **14** standard HI B2B facade classes. Full MCA coverage (**26** facades, **Jakarta** XML WS / Bind) is available from **11.0.0.1** onward - see **`CLIENT-FEATURES.md`** and **`WSDL-CLIENT-PURPOSES.md`**.

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

IHI **sync** batch (`ConsumerSearchIHIBatchSyncClient` / `SearchBatch`) applies the same field rules in **`SearchBatch.ArgumentValidator`**.

---

## Further reading

| Document | Content |
| -------- | ------- |
| **`CLIENT-FEATURES.md`** | Capability overview by facade class |
| **`WSDL-CLIENT-PURPOSES.md`** | WSDL service to facade mapping (**14** vs **26** lines) |
| **`wsdls/readme.txt`** | WSDL download, layout, runtime property names |
| **`SECURITY.md`** | Secrets and reporting |
| **`CONTRIBUTING.md`** | Building or changing this repository from source |
| **`MAINTAINERS.md`** | Release and build internals |
| Javadoc | Attached to releases on Maven Central |

---

## Licensing

Copyright 2011 NEHTA  
Copyright 2021-2026 ADHA (Australian Digital Health Agency)

Licensed under the Apache License, Version 2.0. See **`LICENSE.md`**.
