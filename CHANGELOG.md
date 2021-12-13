# Change Log/Revision History

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
- Metro updated to 2.3 / JAX-WS 2.2.8

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
