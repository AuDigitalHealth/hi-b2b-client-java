# HI B2B Client — feature overview

Broad list of what you can do with **`au.gov.nehta:hi-b2b-client`**: typed Java facades over Australia’s **Healthcare Identifiers (HI) Service** (SOAP/JAX-WS). Operation details and field rules are in ADHA specs and Javadoc; this document maps **library surface → capability**.

**Audience:** integrators choosing APIs for **`1.7.0`** (**26** facade classes). Setup (Maven Central dependency, keystores, endpoints) is in **`README.md`**.

Smaller release lines (**`1.6.3`**, **`1.6.5`**) expose **14** standard HI B2B facades only — see **`README.md`** release table before assuming a class exists in your version.

---

## What the library gives you

| Capability | How |
| ---------- | --- |
| Call HI SOAP operations from Java | One facade class per WSDL service (see tables below) |
| Mutual TLS to HI endpoints | Pass `SSLSocketFactory` (often from PKCS#12 via `KeystoreUtil`) |
| Sign outbound SOAP; verify inbound | `HISecurityHandler` on every facade (`ClientBase`) |
| HI SOAP headers (product, user, org, timestamps) | `HIHeaderHandler` + constructors on `BaseClient_3` |
| Resolve licensed WSDL/XSD at runtime | `HiWsdlArtifactRoot` (env, system property, `local.properties`, or `setRoot`) |
| Optional SOAP message logging | `LoggingHandler` |
| Request payload guardrails | Per-operation `ArgumentValidator` / `SearchBatch` before SOAP |
| Per-call user identity override | Many clients expose overloads with `QualifiedId` per request |
| Example wiring | `src/sample/java/.../hi/sample/*` (not on default classpath) |

You still need ADHA/Services Australia **registration**, **certificates**, **product metadata (PCIN)**, and **endpoint URLs** for live calls.

---

## Individual Healthcare Identifier (IHI)

Package: `au.gov.nehta.vendorlibrary.hi.ihi`.

### Search (single request)

**`ConsumerSearchIHIClient`** — find/validate an individual’s IHI. Client methods enforce which `SearchIHI` fields may be set.

| Method | Typical use |
| ------ | ----------- |
| `basicSearch` | Lookup by **IHI number** (+ demographics) |
| `basicMedicareSearch` | Lookup by **Medicare card number** (+ demographics) |
| `basicDvaSearch` | Lookup by **DVA file number** (+ demographics) |
| `detailedSearch` | **Demographics only** (no IHI, Medicare, DVA, or address blocks) |
| `australianPostalAddressSearch` | Demographics + Australian postal address |
| `australianStreetAddressSearch` | Demographics + Australian street address |
| `internationalAddressSearch` | Demographics + international address |

Common mandatory demographics: family name, date of birth, sex (given name optional). **`basicSearch` requires an IHI number**; demographics-only lookup must use **`detailedSearch`**, not `basicSearch`.

### Search (batch)

| Class | What you can do |
| ----- | ---------------- |
| **`ConsumerSearchIHIBatchSyncClient`** | Submit many IHI searches in **one synchronous** batch (`batchSearch` + `SearchBatch` helper) |
| **`ConsumerSearchIHIBatchAsyncClient`** | **Submit** batch (`submitSearchIHIBatch`), **poll status** (`getSearchIHIBatchStatus`), **retrieve results** (`retrieveSearchIHIBatch`), **delete** batch (`deleteSearchIHIBatch`) |

`SearchBatch` supports the same search shapes as sync batch (basic, Medicare, DVA, detailed, address variants) with the same field rules as single search.

### Create, update, merge, resolve (lifecycle)

| Class | Operation (SOAP) | Purpose (high level) |
| ----- | ------------------ | --------------------- |
| **`ConsumerCreateProvisionalIHIClient`** | Create provisional IHI | Allocate provisional identifier |
| **`ConsumerUpdateProvisionalIHIClient`** | Update provisional IHI | Change provisional record |
| **`ConsumerMergeProvisionalIHIClient`** | Merge provisional IHI | Combine provisional with existing IHI |
| **`ConsumerResolveProvisionalIHIClient`** | Resolve provisional IHI | Finalise provisional → active IHI |
| **`ConsumerCreateUnverifiedIHIClient`** | Create unverified IHI | Create unverified identifier |
| **`ConsumerCreateVerifiedIHIClient`** | Create verified IHI | Create verified identifier |
| **`ConsumerUpdateIHIClient`** | Update IHI | Update active IHI demographics/details |

### Notifications to HI Service

| Class | Operation | Purpose (high level) |
| ----- | --------- | --------------------- |
| **`ConsumerNotifyDuplicateIHIClient`** | Notify duplicate IHI | Report duplicate IHI situation |
| **`ConsumerNotifyReplicaIHIClient`** | Notify replica IHI | Report replica IHI situation |

Exact payloads and business rules are defined by HI Service specifications, not this library.

---

## Healthcare Provider Identifier — Individual (HPI-I)

Package: `au.gov.nehta.vendorlibrary.hi.hpii`.

| Class | Methods / capability |
| ----- | -------------------- |
| **`ProviderSearchForProviderIndividualClient`** | `identifierSearch`, `demographicSearch` — find provider individuals in HI |
| **`ProviderSearchHIProviderDirectoryForIndividualClient`** | `identifierSearch`, `demographicSearch` — search **HI Provider Directory** for individuals |
| **`SearchForProviderIndividualBatchAsyncClient`** | `submitSearch`, `retrieveSearch` — **async batch** provider-individual search |
| **`ProviderSearchTdsProviderIndividualClient`** | `searchTdsProviderIndividual` — search **TDS** (terminated/deactivated) provider individuals |
| **`ProviderManageTdsProviderIndividualClient`** | `manageTdsProviderIndividual` — manage TDS provider individual records |

---

## Healthcare Provider Identifier — Organisation (HPI-O)

Package: `au.gov.nehta.vendorlibrary.hi.hpio`.

### Search and directory

| Class | Methods / capability |
| ----- | -------------------- |
| **`ProviderSearchForProviderOrganisationClient`** | `identifierSearch` — find organisations by identifier |
| **`ProviderSearchHIProviderDirectoryForOrganisationClient`** | `identifierSearch`, `demographicSearch` — HI Provider Directory for organisations |
| **`SearchForProviderOrganisationBatchAsyncClient`** | `submitSearch`, `retrieveSearch` — async batch organisation search |

### Read

| Class | Operation | Purpose (high level) |
| ----- | --------- | --------------------- |
| **`ProviderReadProviderOrganisationClient`** | `readProvider` | Read organisation (HPI-O) details |
| **`ProviderReadAdministrativeIndividualClient`** | `readProviderAdministrativeIndividual` | Read administrative individual linked to provider context |

### Manage (mutating operations)

Payloads use HI “manage” request types (create/update/deactivate etc. per spec). Facades pass them through after optional validation.

| Class | Operation |
| ----- | --------- |
| **`ProviderManageProviderOrganisationClient`** | `manageProviderOrganisation` |
| **`ProviderManageProviderDirectoryEntryClient`** | `manageProviderDirectoryEntry` |
| **`ProviderManageProviderAdministrativeIndividualClient`** | `manageProviderAdministrativeIndividual` |

---

## Reference data

Package: `au.gov.nehta.vendorlibrary.hi.readreferencedata`.

| Class | Capability |
| ----- | ---------- |
| **`ReadReferenceDataClient`** | `readReferenceData` — fetch current allowed code lists from HI (e.g. provider type, speciality, organisation type, organisation service, operating system). Request lists **element names**; response returns acceptable values. |

---

## Cross-cutting integration patterns

### Constructing any facade

Typical constructor inputs:

- HI **endpoint URL** (cert vs production from registration)
- **`SSLSocketFactory`** for mutual TLS
- **Signing** private key + X.509 certificate (SOAP XML signature)
- **Product** header (`ProductType` / wrapped variant) — PCIN
- **Qualified user ID** (and sometimes **organisation qualified ID** for contracted service providers)
- Optional **`CertificateValidator`** for inbound signature trust

### Security and transport stack

- TLS client authentication via your socket factory
- Outbound signing of SOAP body and required header elements; inbound signature verification
- Stripping of `wsa:FaultTo` on outbound messages (Medicare/HI interoperability)
- Optional omission of HPI-O CSP header when no organisation qualified ID is configured

### WSDL and generated types

- Published JAR does **not** include licensed WSDL/XSD; you supply the ADHA bundle at runtime (`wsdl/` + `schema/` under one root). See **`README.md`** and **`wsdls/README.md`**.
- JAXB/JAX-WS types for requests/responses are generated at library build time from that bundle; your app uses those types in method arguments.

### Helpers in other packages

| Area | Examples |
| ---- | -------- |
| `au.gov.nehta.vendorlibrary.common.security` | `KeystoreUtil` — load keys/certs for TLS and signing |
| `au.gov.nehta.vendorlibrary.ws` | `WebServiceClientUtil`, `TimeUtility` |
| `au.gov.nehta.vendorlibrary.hi.wsdl` | `HiWsdlArtifactRoot` |
| `au.gov.nehta.vendorlibrary.hi.client.wrapped` | Wrapped `QualifiedId`, `ProductType` for simpler construction |

### Errors

Service faults surface as generated **`StandardErrorMsg`** (and related) types from the HI WSDL; handle in application code like any SOAP fault.

---

## Learning and verification

| Resource | Content |
| -------- | ------- |
| **`README.md`** | Dependency, WSDL root, config keys, search IHI rules summary |
| **`src/sample/java`** | Runnable-style examples for search IHI, batch sync, reference data, directory search, read/manage org |
| **Javadoc** | Attached to releases on Maven Central |
| **`CONTRIBUTING.md`** | Build, offline vs `-Pintegration` tests |
| **ADHA implementer portal** | Registration, certificates, authoritative operation specs |

---

## Quick index — all facade classes (`1.7.0`)

| # | Class |
| - | ----- |
| 1 | `ConsumerSearchIHIClient` |
| 2 | `ConsumerSearchIHIBatchSyncClient` |
| 3 | `ConsumerSearchIHIBatchAsyncClient` |
| 4 | `ConsumerCreateProvisionalIHIClient` |
| 5 | `ConsumerUpdateProvisionalIHIClient` |
| 6 | `ConsumerMergeProvisionalIHIClient` |
| 7 | `ConsumerResolveProvisionalIHIClient` |
| 8 | `ConsumerCreateUnverifiedIHIClient` |
| 9 | `ConsumerCreateVerifiedIHIClient` |
| 10 | `ConsumerUpdateIHIClient` |
| 11 | `ConsumerNotifyDuplicateIHIClient` |
| 12 | `ConsumerNotifyReplicaIHIClient` |
| 13 | `ProviderSearchForProviderIndividualClient` |
| 14 | `ProviderSearchHIProviderDirectoryForIndividualClient` |
| 15 | `SearchForProviderIndividualBatchAsyncClient` |
| 16 | `ProviderSearchTdsProviderIndividualClient` |
| 17 | `ProviderManageTdsProviderIndividualClient` |
| 18 | `ProviderSearchForProviderOrganisationClient` |
| 19 | `ProviderSearchHIProviderDirectoryForOrganisationClient` |
| 20 | `SearchForProviderOrganisationBatchAsyncClient` |
| 21 | `ProviderReadProviderOrganisationClient` |
| 22 | `ProviderReadAdministrativeIndividualClient` |
| 23 | `ProviderManageProviderOrganisationClient` |
| 24 | `ProviderManageProviderDirectoryEntryClient` |
| 25 | `ProviderManageProviderAdministrativeIndividualClient` |
| 26 | `ReadReferenceDataClient` |
