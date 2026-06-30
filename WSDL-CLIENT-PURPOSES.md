# WSDL functionality by client purpose (HI vs myHR)

Cross-repo reference for **Healthcare Identifiers (HI)** and **My Health Record (PCEHR / myHR)** B2B SOAP contracts. These are separate national services: different WSDL trees, different Maven artifacts, and no shared SOAP operations.

| | **hi-b2b-client-java** | **mhr-b2b-client-java** |
|---|---|---|
| **National service** | Healthcare Identifiers (HI) Service | My Health Record (PCEHR / myHR) B2B |
| **WSDL prefix** | `HI_*` (MCA licensed bundle) | `B2B_*` under `wsdls/src/main/resources/wsdl/External/` |
| **Identifiers handled** | IHI, HPI-I, HPI-O | Individual myHR record (IHI as context in headers, not managed here) |
| **Typical use** | Look up / validate / manage healthcare identifiers | Access record, register consumer, upload/retrieve documents, read views |
| **Service WSDLs codegen’d** | **26** (+ paired `*Interface` WSDLs in licensed bundle) | **12** `B2B_*` services |
| **Facade classes** | **26** in **`1.7.0`**; **14** in **`1.6.3`** / **`1.6.5`** |
| **Maven artifact** | `au.gov.nehta:hi-b2b-client` | `au.gov.nehta:mhr-b2b-client` |

Apps needing both depend on **two libraries**. HI-only detail: **`CLIENT-FEATURES.md`** in this repo. MHR third-party scope: **`ADHA-THIRD-PARTY-SCOPE.md`** in `mhr-b2b-client-java`. Three-client matrix: **`WSDL-CLIENT-PURPOSES.md`** in `myhr-b2b-client-java`.

---

## HI (`hi-b2b-client-java`) — MCA `HI_*` WSDL

Grouped by **client purpose**. WSDL filenames in the licensed bundle include version suffixes (e.g. `HI_ConsumerSearchIHI-3.0.wsdl`); logical names below omit versions.

| Purpose | WSDL service | What it does | Java facade | In **`1.6.x`** (14 facades) |
|---|---|---|---|:---:|
| **IHI — search** | `HI_ConsumerSearchIHI` | Find/validate an individual’s IHI (by IHI, Medicare, DVA, or demographics) | `ConsumerSearchIHIClient` | Yes |
| **IHI — batch search** | `HI_ConsumerSearchIHIBatchSync` | Synchronous batch IHI search | `ConsumerSearchIHIBatchSyncClient` | Yes |
| **IHI — batch search** | `HI_ConsumerSearchIHIBatchAsync` | Submit/retrieve async batch IHI search | `ConsumerSearchIHIBatchAsyncClient` | **`1.7.0` only** |
| **IHI — create** | `HI_ConsumerCreateProvisionalIHI` | Create provisional IHI | `ConsumerCreateProvisionalIHIClient` | **`1.7.0` only** |
| **IHI — update** | `HI_ConsumerUpdateProvisionalIHI` | Update provisional IHI | `ConsumerUpdateProvisionalIHIClient` | **`1.7.0` only** |
| **IHI — merge** | `HI_ConsumerMergeProvisionalIHI` | Merge provisional IHIs | `ConsumerMergeProvisionalIHIClient` | **`1.7.0` only** |
| **IHI — resolve** | `HI_ConsumerResolveProvisionalIHI` | Resolve provisional IHI | `ConsumerResolveProvisionalIHIClient` | **`1.7.0` only** |
| **IHI — create** | `HI_ConsumerCreateUnverifiedIHI` | Create unverified IHI | `ConsumerCreateUnverifiedIHIClient` | **`1.7.0` only** |
| **IHI — create** | `HI_ConsumerCreateVerifiedIHI` | Create verified IHI | `ConsumerCreateVerifiedIHIClient` | **`1.7.0` only** |
| **IHI — update** | `HI_ConsumerUpdateIHI` | Update IHI details | `ConsumerUpdateIHIClient` | **`1.7.0` only** |
| **IHI — notify** | `HI_ConsumerNotifyDuplicateIHI` | Notify duplicate IHI | `ConsumerNotifyDuplicateIHIClient` | **`1.7.0` only** |
| **IHI — notify** | `HI_ConsumerNotifyReplicaIHI` | Notify replica IHI | `ConsumerNotifyReplicaIHIClient` | **`1.7.0` only** |
| **HPI-I — search** | `HI_ProviderSearchForProviderIndividual` | Search provider individual (HPI-I) | `ProviderSearchForProviderIndividualClient` | Yes |
| **HPI-I — directory search** | `HI_ProviderSearchHIProviderDirectoryForIndividual` | Search HI provider directory for individual | `ProviderSearchHIProviderDirectoryForIndividualClient` | Yes |
| **HPI-I — batch search** | `HI_ProviderBatchAsyncSearchForProviderIndividual` | Async batch search for provider individual | `SearchForProviderIndividualBatchAsyncClient` | Yes |
| **HPI-I — TDS search** | `HI_ProviderSearchTdsProviderIndividual` | Search TDS provider individual | `ProviderSearchTdsProviderIndividualClient` | **`1.7.0` only** |
| **HPI-I — TDS manage** | `HI_ProviderManageTdsProviderIndividual` | Manage TDS provider individual | `ProviderManageTdsProviderIndividualClient` | **`1.7.0` only** |
| **HPI-O — search** | `HI_ProviderSearchForProviderOrganisation` | Search provider organisation (HPI-O) | `ProviderSearchForProviderOrganisationClient` | Yes |
| **HPI-O — directory search** | `HI_ProviderSearchHIProviderDirectoryForOrganisation` | Search HI provider directory for organisation | `ProviderSearchHIProviderDirectoryForOrganisationClient` | Yes |
| **HPI-O — batch search** | `HI_ProviderBatchAsyncSearchForProviderOrganisation` | Async batch search for organisation | `SearchForProviderOrganisationBatchAsyncClient` | Yes |
| **HPI-O — read** | `HI_ProviderReadProviderOrganisation` | Read provider organisation details | `ProviderReadProviderOrganisationClient` | Yes |
| **HPI-O — read** | `HI_ProviderReadProviderAdministrativeIndividual` | Read administrative individual | `ProviderReadAdministrativeIndividualClient` | Yes |
| **HPI-O — manage** | `HI_ProviderManageProviderOrganisation` | Manage provider organisation | `ProviderManageProviderOrganisationClient` | Yes |
| **HPI-O — manage** | `HI_ProviderManageProviderDirectoryEntry` | Manage provider directory entry | `ProviderManageProviderDirectoryEntryClient` | Yes |
| **HPI-O — manage** | `HI_ProviderManageProviderAdministrativeIndividual` | Manage administrative individual | `ProviderManageProviderAdministrativeIndividualClient` | Yes |
| **Reference data** | `HI_ProviderReadReferenceData` | Read HI reference data (codes, lists) | `ReadReferenceDataClient` | Yes |

**Notes**

- Each service WSDL has a paired `*Interface` WSDL in the licensed bundle; facades bind to the service WSDL.
- **`1.7.0`** exposes all **26** rows above. **`1.6.3`** and **`1.6.5`** expose the rows marked **Yes** in the last column only.

---

## myHR / PCEHR (`mhr-b2b-client-java`) — `B2B_*` WSDL

Grouped by **client purpose**. All **12** WSDLs are codegen’d from `wsdls/src/main/resources/wsdl/External/`; **15** facade classes wrap them.

| Purpose | WSDL | SOAP / functional capability | Java facade(s) |
|---|---|---|---|
| **Record access** | `B2B_PCEHRProfile` | Check whether a myHR exists for an individual | `DoesPCEHRExistClient` |
| **Record access** | `B2B_PCEHRProfile` | Gain access to a consumer’s myHR (incl. access code flow) | `GainPCEHRAccessClient` |
| **Registration** | `B2B_RegisterPCEHR` | Register a consumer for myHR | `RegisterPCEHRClient` |
| **Document exchange — registry** | `B2B_DocumentRegistry` | Register document metadata (XDS.b); also used to **list** documents in the record | `UploadDocumentMetadataClient`, `GetDocumentListClient` |
| **Document exchange — repository** | `B2B_DocumentRepository` | Upload document content (Provide & Register, **MTOM**) | `UploadDocumentClient` |
| **Document exchange — repository** | `B2B_DocumentRepository` | Retrieve document content (Retrieve Document Set) | `GetDocumentClient` |
| **Document exchange — removal** | `B2B_RemoveDocument` | Remove a document from the record | `RemoveDocumentClient` |
| **Clinical / record views** | `B2B_GetView` | Third-party view types (7): health record overview, Medicare overview, pathology, diagnostic imaging, observations, prescriptions/dispenses, health check schedule — see **`ADHA-THIRD-PARTY-SCOPE.md`** in `mhr-b2b-client-java` | `GetViewClient` |
| **Record views — individual** | `B2B_GetIndividualDetailsView` | Individual details held in myHR | `GetIndividualDetailsViewClient` |
| **Record views — representatives** | `B2B_GetRepresentativeList` | List authorised representatives | `GetRepresentativeListClient` |
| **Record views — audit** | `B2B_GetAuditView` | Audit trail view | `GetAuditViewClient` |
| **Record views — change history** | `B2B_GetChangeHistoryView` | Change history view | `GetChangeHistoryViewClient` |
| **Templates — read** | `B2B_GetTemplate` | Fetch a document template | `GetTemplateClient` |
| **Templates — search** | `B2B_SearchTemplate` | Search available templates | `SearchTemplateClient` |

**Notes**

- Document flows use **IHE XDS.b** (`DocumentRegistry` / `DocumentRepository`) plus PCEHR-specific remove/register/view WSDLs.
- `GetViewClient` is one facade over **`B2B_GetView`** with typed overloads for each supported view type (see **`ADHA-THIRD-PARTY-SCOPE.md`** in `mhr-b2b-client-java`).

---

## Side-by-side: what each library is for

| Client purpose | HI (`hi-b2b-client`) | myHR (`mhr-b2b-client`) |
|---|---|---|
| **Person identifier lookup / validation** | Yes — IHI search & lifecycle | No (uses IHI as record key only) |
| **Provider person (HPI-I)** | Search, directory, batch, TDS | No |
| **Provider organisation (HPI-O)** | Search, read, manage org/directory/admin | No |
| **Reference / code lists** | `ReadReferenceData` | No |
| **Consumer myHR registration** | No | `RegisterPCEHRClient` |
| **Check / open myHR access** | No | `DoesPCEHRExist`, `GainPCEHRAccess` |
| **Upload / download clinical documents** | No | XDS registry + repository (+ MTOM upload) |
| **Remove documents from myHR** | No | `RemoveDocumentClient` |
| **Clinical summaries & record views** | No | Multiple `Get*View` clients |
| **Document templates** | No | `GetTemplate`, `SearchTemplate` |

---

## Typical integration flow

Many myHR B2B calls require an **IHI** (and often HPI-I / HPI-O) in SOAP headers. That identifier is usually obtained via **`hi-b2b-client`**, then passed into **`mhr-b2b-client`** facades — two artifacts, one application workflow.
