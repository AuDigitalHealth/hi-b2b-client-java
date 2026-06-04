/*
 * Copyright 2011 NEHTA
 * Copyright 2021-2026 ADHA (Australian Digital Health Agency)
 */
package au.gov.nehta.vendorlibrary.hi.ihi;

import au.net.electronichealth.ns.hi.svc.consumersearchihi._3.SearchIHI;
import au.net.electronichealth.ns.hi.xsd.common.addresscore._3.PostalDeliveryGroupType;
import au.net.electronichealth.ns.hi.xsd.common.commoncoredatatypes._3.PostalDeliveryType;
import au.net.electronichealth.ns.hi.xsd.common.commoncoredatatypes._3.SexType;
import au.net.electronichealth.ns.hi.xsd.consumercore.address._3.AustralianPostalAddressType;
import au.net.electronichealth.ns.hi.xsd.consumercore.address._3.AustralianStreetAddressType;
import au.net.electronichealth.ns.hi.xsd.consumercore.address._3.InternationalAddressType;
import org.junit.Test;

import static au.gov.nehta.vendorlibrary.hi.test.utils.IHITestConstants.*;
import static org.junit.Assert.fail;

public class ConsumerSearchIHIClientArgumentValidatorTest {

    private final ConsumerSearchIHIClient.ArgumentValidator validator =
            new ConsumerSearchIHIClient.ArgumentValidator();

    @Test
    public void basicSearchCheck_acceptsMedicareTestPayload() {
        validator.basicSearchCheck(basicSearchRequest());
    }

    @Test
    public void basicSearchCheck_acceptsPayloadWithoutGivenName() {
        SearchIHI request = basicSearchRequest();
        request.setGivenName(null);
        validator.basicSearchCheck(request);
    }

    @Test(expected = IllegalArgumentException.class)
    public void basicSearchCheck_rejectsIhiNumberOnly() {
        SearchIHI request = new SearchIHI();
        request.setIhiNumber(MEDICARE_GENERIC_TEST_INDIVIDUAL_IHI_NUMBER);
        validator.basicSearchCheck(request);
    }

    @Test(expected = IllegalArgumentException.class)
    public void basicSearchCheck_rejectsMissingIhiNumber() {
        SearchIHI request = demographicsOnlyRequest();
        validator.basicSearchCheck(request);
    }

    @Test(expected = IllegalArgumentException.class)
    public void basicSearchCheck_rejectsMedicareCardNumber() {
        SearchIHI request = basicSearchRequest();
        request.setMedicareCardNumber(MEDICARE_GENERIC_TEST_INDIVIDUAL_MEDICARE_CARD_NUMBER);
        validator.basicSearchCheck(request);
    }

    @Test
    public void detailedSearchCheck_acceptsMedicareTestPayload() {
        validator.detailedSearchCheck(detailedSearchRequest());
    }

    @Test
    public void detailedSearchCheck_acceptsPayloadWithoutGivenName() {
        SearchIHI request = detailedSearchRequest();
        request.setGivenName(null);
        validator.detailedSearchCheck(request);
    }

    @Test(expected = IllegalArgumentException.class)
    public void detailedSearchCheck_rejectsIhiNumber() {
        SearchIHI request = detailedSearchRequest();
        request.setIhiNumber(MEDICARE_GENERIC_TEST_INDIVIDUAL_IHI_NUMBER);
        validator.detailedSearchCheck(request);
    }

    @Test(expected = IllegalArgumentException.class)
    public void detailedSearchCheck_rejectsMedicareCardNumber() {
        SearchIHI request = detailedSearchRequest();
        request.setMedicareCardNumber(MEDICARE_GENERIC_TEST_INDIVIDUAL_MEDICARE_CARD_NUMBER);
        validator.detailedSearchCheck(request);
    }

    @Test
    public void basicMedicareSearchCheck_acceptsMedicareTestPayload() {
        validator.basicMedicareSearchCheck(basicMedicareSearchRequest());
    }

    @Test(expected = IllegalArgumentException.class)
    public void basicMedicareSearchCheck_rejectsIhiNumber() {
        SearchIHI request = basicMedicareSearchRequest();
        request.setIhiNumber(MEDICARE_GENERIC_TEST_INDIVIDUAL_IHI_NUMBER);
        validator.basicMedicareSearchCheck(request);
    }

    @Test
    public void basicDvaSearchCheck_acceptsMedicareTestPayload() {
        validator.basicDvaSearchCheck(basicDvaSearchRequest());
    }

    @Test(expected = IllegalArgumentException.class)
    public void basicDvaSearchCheck_rejectsIhiNumber() {
        SearchIHI request = basicDvaSearchRequest();
        request.setIhiNumber(MEDICARE_DVA_TEST_INDIVIDUAL_IHI_NUMBER);
        validator.basicDvaSearchCheck(request);
    }

    @Test
    public void australianPostalAddressSearchCheck_acceptsMedicareTestPayload() {
        validator.australianPostalAddressSearchCheck(australianPostalAddressSearchRequest());
    }

    @Test(expected = IllegalArgumentException.class)
    public void australianPostalAddressSearchCheck_rejectsIhiNumber() {
        SearchIHI request = australianPostalAddressSearchRequest();
        request.setIhiNumber(MEDICARE_GENERIC_TEST_INDIVIDUAL_IHI_NUMBER);
        validator.australianPostalAddressSearchCheck(request);
    }

    @Test
    public void australianStreetAddressSearchCheck_acceptsMedicareTestPayload() {
        validator.australianStreetAddressSearchCheck(australianStreetAddressSearchRequest());
    }

    @Test(expected = IllegalArgumentException.class)
    public void australianStreetAddressSearchCheck_rejectsIhiNumber() {
        SearchIHI request = australianStreetAddressSearchRequest();
        request.setIhiNumber(MEDICARE_GENERIC_TEST_INDIVIDUAL_IHI_NUMBER);
        validator.australianStreetAddressSearchCheck(request);
    }

    @Test
    public void internationalAddressSearchCheck_acceptsMedicareTestPayload() {
        validator.internationalAddressSearchCheck(internationalAddressSearchRequest());
    }

    @Test(expected = IllegalArgumentException.class)
    public void internationalAddressSearchCheck_rejectsIhiNumber() {
        SearchIHI request = internationalAddressSearchRequest();
        request.setIhiNumber(MEDICARE_GENERIC_TEST_INDIVIDUAL_IHI_NUMBER);
        validator.internationalAddressSearchCheck(request);
    }

    @Test(expected = IllegalArgumentException.class)
    public void basicSearchCheck_rejectsNullRequest() {
        validator.basicSearchCheck(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void detailedSearchCheck_rejectsNullRequest() {
        validator.detailedSearchCheck(null);
    }

    private static SearchIHI basicSearchRequest() {
        SearchIHI searchIHI = demographicsOnlyRequest();
        searchIHI.setIhiNumber(MEDICARE_GENERIC_TEST_INDIVIDUAL_IHI_NUMBER);
        return searchIHI;
    }

    private static SearchIHI detailedSearchRequest() {
        return demographicsOnlyRequest();
    }

    private static SearchIHI demographicsOnlyRequest() {
        SearchIHI searchIHI = new SearchIHI();
        searchIHI.setFamilyName(MEDICARE_GENERIC_TEST_INDIVIDUAL_FAMILY_NAME);
        searchIHI.setGivenName(MEDICARE_GENERIC_TEST_INDIVIDUAL_GIVEN_NAME);
        searchIHI.setDateOfBirth(MEDICARE_GENERIC_TEST_INDIVIDUAL_DATE_OF_BIRTH);
        searchIHI.setSex(MEDICARE_GENERIC_TEST_INDIVIDUAL_SEX);
        return searchIHI;
    }

    private static SearchIHI basicMedicareSearchRequest() {
        SearchIHI searchIHI = demographicsOnlyRequest();
        searchIHI.setMedicareCardNumber(MEDICARE_GENERIC_TEST_INDIVIDUAL_MEDICARE_CARD_NUMBER);
        searchIHI.setMedicareIRN(MEDICARE_GENERIC_TEST_INDIVIDUAL_MEDICARE_IRN);
        return searchIHI;
    }

    private static SearchIHI basicDvaSearchRequest() {
        SearchIHI searchIHI = demographicsOnlyRequest();
        searchIHI.setDvaFileNumber(MEDICARE_DVA_TEST_INDIVIDUAL_DVA_FILE_NUMBER);
        searchIHI.setFamilyName(MEDICARE_DVA_TEST_INDIVIDUAL_FAMILY_NAME);
        searchIHI.setGivenName(MEDICARE_DVA_TEST_INDIVIDUAL_GIVEN_NAME);
        searchIHI.setDateOfBirth(MEDICARE_DVA_TEST_INDIVIDUAL_DATE_OF_BIRTH);
        searchIHI.setSex(MEDICARE_DVA_TEST_INDIVIDUAL_SEX);
        return searchIHI;
    }

    private static SearchIHI australianPostalAddressSearchRequest() {
        SearchIHI searchIHI = demographicsOnlyRequest();
        PostalDeliveryGroupType postalDeliveryGroup = new PostalDeliveryGroupType();
        postalDeliveryGroup.setPostalDeliveryNumber(
                MEDICARE_GENERIC_TEST_INDIVIDUAL_AUSTRALIAN_POSTAL_ADDRESS_POSTAL_DELIVERY_NUMBER);
        postalDeliveryGroup.setPostalDeliveryType(
                MEDICARE_GENERIC_TEST_INDIVIDUAL_AUSTRALIAN_POSTAL_ADDRESS_POSTAL_DELIVERY_TYPE_CODE);
        AustralianPostalAddressType address = new AustralianPostalAddressType();
        address.setPostalDeliveryGroup(postalDeliveryGroup);
        address.setSuburb(MEDICARE_GENERIC_TEST_INDIVIDUAL_AUSTRALIAN_POSTAL_ADDRESS_SUBURB);
        address.setPostcode(MEDICARE_GENERIC_TEST_INDIVIDUAL_AUSTRALIAN_POSTAL_ADDRESS_POSTCODE);
        address.setState(MEDICARE_GENERIC_TEST_INDIVIDUAL_AUSTRALIAN_POSTAL_ADDRESS_STATE);
        searchIHI.setAustralianPostalAddress(address);
        return searchIHI;
    }

    private static SearchIHI australianStreetAddressSearchRequest() {
        SearchIHI searchIHI = demographicsOnlyRequest();
        AustralianStreetAddressType address = new AustralianStreetAddressType();
        address.setStreetNumber(MEDICARE_GENERIC_TEST_INDIVIDUAL_AUSTRALIAN_STREET_ADDRESS_STREET_NUMBER);
        address.setStreetName(MEDICARE_GENERIC_TEST_INDIVIDUAL_AUSTRALIAN_STREET_ADDRESS_STREET_NAME);
        address.setStreetType(MEDICARE_GENERIC_TEST_INDIVIDUAL_AUSTRALIAN_STREET_ADDRESS_STREET_TYPE);
        address.setSuburb(MEDICARE_GENERIC_TEST_INDIVIDUAL_AUSTRALIAN_STREET_ADDRESS_SUBURB);
        address.setState(MEDICARE_GENERIC_TEST_INDIVIDUAL_AUSTRALIAN_STREET_ADDRESS_STATE);
        address.setPostcode(MEDICARE_GENERIC_TEST_INDIVIDUAL_AUSTRALIAN_STREET_ADDRESS_POST_CODE);
        searchIHI.setAustralianStreetAddress(address);
        return searchIHI;
    }

    private static SearchIHI internationalAddressSearchRequest() {
        SearchIHI searchIHI = demographicsOnlyRequest();
        InternationalAddressType address = new InternationalAddressType();
        address.setInternationalAddressLine(
                MEDICARE_GENERIC_TEST_INDIVIDUAL_INTERNATIONAL_ADDRESS_INTERNATIONAL_ADDRESS_LINE);
        address.setInternationalPostcode(
                MEDICARE_GENERIC_TEST_INDIVIDUAL_INTERNATIONAL_ADDRESS_INTERNATIONAL_ADDRESS_POSTCODE);
        address.setInternationalStateProvince(
                MEDICARE_GENERIC_TEST_INDIVIDUAL_INTERNATIONAL_ADDRESS_INTERNATIONAL_ADDRESS_STATE_PROVINCE);
        address.setCountry(MEDICARE_GENERIC_TEST_INDIVIDUAL_INTERNATIONAL_ADDRESS_INTERNATIONAL_ADDRESS_COUNTRY);
        searchIHI.setInternationalAddress(address);
        return searchIHI;
    }
}
