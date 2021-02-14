***************************
	IMPORTANT
***************************

The latest files can be downloaded here:
The "wsdl-artefacts-<date>.zip" file containing these files can be downloaded from the following web site:

http://www.medicareaustralia.gov.au/provider/vendors/healthcare-identifiers-developers/licensed-material/current-versions.jsp



WSDL files to be supplied:
consumer\20100731\HI_ConsumerCreateProvisionalIHI-3.0.wsdl
consumer\20100731\HI_ConsumerCreateProvisionalIHIInterface-3.0.wsdl
consumer\20100731\HI_ConsumerMergeProvisionalIHI-3.0.wsdl
consumer\20100731\HI_ConsumerMergeProvisionalIHIInterface-3.0.wsdl
consumer\20100731\HI_ConsumerSearchIHI-3.0.wsdl
consumer\20100731\HI_ConsumerSearchIHIBatchAsync-3.0.wsdl
consumer\20100731\HI_ConsumerSearchIHIBatchAsyncInterface-3.0.wsdl
consumer\20100731\HI_ConsumerSearchIHIBatchSync-3.0.wsdl
consumer\20100731\HI_ConsumerSearchIHIBatchSyncInterface-3.0.wsdl
consumer\20100731\HI_ConsumerSearchIHIInterface-3.0.wsdl
consumer\20100731\HI_ConsumerUpdateProvisionalIHI-3.0.wsdl
consumer\20100731\HI_ConsumerUpdateProvisionalIHIInterface-3.0.wsdl
consumer\20100821\HI_ConsumerCreateUnverifiedIHI-3.0.2.wsdl
consumer\20100821\HI_ConsumerCreateUnverifiedIHIInterface-3.0.2.wsdl
consumer\20100821\HI_ConsumerResolveProvisionalIHI-3.0.2.wsdl
consumer\20100821\HI_ConsumerResolveProvisionalIHIInterface-3.0.2.wsdl
consumer\20100821\HI_ConsumerUpdateIHI-3.0.2.wsdl
consumer\20100821\HI_ConsumerUpdateIHIInterface-3.0.2.wsdl
consumer\20100930\HI_ConsumerNotifyDuplicateIHI-3.2.0.wsdl
consumer\20100930\HI_ConsumerNotifyDuplicateIHIInterface-3.2.0.wsdl
consumer\20100930\HI_ConsumerNotifyReplicaIHI-3.2.0.wsdl
consumer\20100930\HI_ConsumerNotifyReplicaIHIInterface-3.2.0.wsdl
consumer\20100930\HI_ConsumerUpdateIHI-3.2.0.wsdl
consumer\20100930\HI_ConsumerUpdateIHIInterface-3.2.0.wsdl
provider\20100930\HI_ProviderReadReferenceData-3.2.0.wsdl
provider\20100930\HI_ProviderReadReferenceDataInterface-3.2.0.wsdl
provider\20100930\HI_ProviderSearchHIProviderDirectoryForIndividual-3.2.0.wsdl
provider\20100930\HI_ProviderSearchHIProviderDirectoryForIndividualInterface-3.2.0.wsdl
provider\20100930\HI_ProviderSearchHIProviderDirectoryForOrganisation-3.2.0.wsdl
provider\20100930\HI_ProviderSearchHIProviderDirectoryForOrganisationInterface-3.2.0.wsdl

XSD files to be supplied:
schema\mca\common\20100731\AddressCore.xsd
schema\mca\common\20100731\CommonCoreDatatypes.xsd
schema\mca\common\20100731\CommonCoreElements.xsd
schema\mca\common\20100731\CommonQualifiedIdentifier.xsd
schema\mca\common\20100731\ElectronicCommunicationCore.xsd
schema\mca\common\20100731\IndividualNameCore.xsd
schema\mca\consumer\core\20100731\Address.xsd
schema\mca\consumer\core\20100731\ConsumerCoreDatatypes.xsd
schema\mca\consumer\core\20100731\ConsumerCoreElements.xsd
schema\mca\consumer\core\20100731\ElectronicCommunication.xsd
schema\mca\consumer\core\20100731\IndividualHealthcareIdentification.xsd
schema\mca\consumer\core\20100731\IndividualName.xsd
schema\mca\consumer\core\20100930\ConsumerCoreDatatypes.xsd
schema\mca\consumer\core\20100930\ConsumerCoreElements.xsd
schema\mca\consumer\core\20100930\IndividualName.xsd
schema\mca\consumer\messages\20100731\CreateProvisionalIHIMessages.xsd
schema\mca\consumer\messages\20100731\CreateUnverifiedIHIMessages.xsd
schema\mca\consumer\messages\20100731\MergeProvisionalIHIMessages.xsd
schema\mca\consumer\messages\20100731\ResolveProvisionalIHIMessages.xsd
schema\mca\consumer\messages\20100731\SearchIHIBatchMessages.xsd
schema\mca\consumer\messages\20100731\SearchIHIMessages.xsd
schema\mca\consumer\messages\20100731\UpdateIHIMessages.xsd
schema\mca\consumer\messages\20100731\UpdateProvisionalIHIMessages.xsd
schema\mca\consumer\messages\20100930\NotifyDuplicateIHIMessages.xsd
schema\mca\consumer\messages\20100930\NotifyReplicaIHIMessages.xsd
schema\mca\consumer\messages\20100930\UpdateIHIMessages.xsd
schema\mca\provider\core\20100930\Address.xsd
schema\mca\provider\core\20100930\ElectronicCommunication.xsd
schema\mca\provider\core\20100930\Endpoint.xsd
schema\mca\provider\core\20100930\IndividualName.xsd
schema\mca\provider\core\20100930\LinkedType.xsd
schema\mca\provider\core\20100930\OrganisationDetails.xsd
schema\mca\provider\core\20100930\OrganisationName.xsd
schema\mca\provider\core\20100930\OrganisationService.xsd
schema\mca\provider\core\20100930\PersonalDetails.xsd
schema\mca\provider\core\20100930\ProviderCoreElements.xsd
schema\mca\provider\core\20100930\ProviderType.xsd
schema\mca\provider\messages\20100930\ReadReferenceDataMessages.xsd
schema\mca\provider\messages\20100930\SearchHIProviderDirectoryForIndividualMessages.xsd
schema\mca\provider\messages\20100930\SearchHIProviderDirectoryForOrganisationMessages.xsd
schema\w3c\xmldsig-core-schema.xsd	


***************************
Run command
 ant -f build.xml dist
 
You can regenerate the JAR files with the following steps 
 Run the ant file located in nehta-vendorlibrary-java-hiclient-wsdl-1.2.0 folder. [ "ant clean" followed by "ant dist" ]
	(or)
 Optionally you can customize/redirect the generated Java artefacts to custom package/s names using the following steps.
    i)  Update/Add JaxB custom binding  file/s to customise the generated schema class package names. 
	    [Refer: ihiSearchJaxbBindings.xml, searchHpiiJaxbBindings.xml, searchHpiiJaxbBindings.xml,
	    createUnverifiedIHIJaxbBindings.xml orreadReferenceDataJaxbBindings  under
	    nehta-vendorlibrary-java-hiclient-wsdl-1.2.0/binding folder]
    ii)  Redirect class files to custom package/s using nehta-vendorlibrary-java-hiclient-wsdl-1.2.0/build.properties
    settings. [Example: ihisearch.consumer.tls.packageName='au.net.electronichealth.ns.hi.svc.consumersearchihi._3_0'].
    iii) Clean the existing artefacts by running ant clean [build.xml from  MCAWsdlWsimpot ]
    iv)  Run ant followed by default 'dist' target using this command "ant -f build.xml dist"