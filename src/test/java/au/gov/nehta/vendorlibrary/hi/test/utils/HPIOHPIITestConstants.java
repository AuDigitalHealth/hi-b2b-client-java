/*
 * Copyright 2011 NEHTA
 *
 * Licensed under the NEHTA Open Source (Apache) License; you may not use this
 * file except in compliance with the License. A copy of the License is in the
 * 'license.txt' file, which should be provided with this work.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package au.gov.nehta.vendorlibrary.hi.test.utils;

import au.net.electronichealth.ns.hi.xsd.common.addresscore._3.PostalDeliveryGroupType;
import au.net.electronichealth.ns.hi.xsd.common.commoncoredatatypes._3.*;
import au.net.electronichealth.ns.hi.xsd.providercore.address._3_2.AustralianAddressCriteriaType;
import au.net.electronichealth.ns.hi.xsd.providercore.address._3_2.InternationalAddressCriteriaType;

public final class HPIOHPIITestConstants {
    // Medicare HPIO test constants
    public static final String MCA_HPIO = "http://ns.electronichealth.net.au/id/hi/hpio/1.0/8003624166667003";
    public static final String MCA_ORG_TYPE = "8591";
    public static final String MCA_SERVICE_TYPE = "8591-1";
    public static final String MCA_ORG_NAME = "HI Common Services";
    public static final String MCA_HPIO_NO_QUALIFIER = "8003624166667003";
    public static final String MCA_ORG_DETAILS_ABN = "12312312345";
    public static final String MCA_ORG_DETAILS_ACN = "123456789";

    public static final String MCA_AUSADDR_UNSTRUCTUREDADDRESSLINE = "1 main street";// "john curtin memorial building\nunit 8\nlevel 5\ngarran pl";
    public static final String MCA_AUSADDR_SUBURB = "GARRAN";
    public static final String MCA_AUSADDR_POSTCODE = "2605";

    // Medicare HPII test constants
    public static final String MCA_HPII = "http://ns.electronichealth.net.au/id/hi/hpii/1.0/8003619166667284";
    public static final String MCA_FAMILY_NAME = "HPIDS";
    public static final String MCA_GIVEN_NAME = "Frank";
    public static final String MCA_PROVIDER_TYPECODE = "4231";
    public static final String MCA_PROVIDER_SPECIALITY = "423111";

    // DRP HIPO test constants
    public static final String DRP_HPIO = "http://ns.electronichealth.net.au/id/hi/hpio/1.0/8003620000030333";
    public static final String DRP_ORG_NAME = "Croydon Family Practice";
    public static final String DRP_ORG_TYPE = "GP";
    public static final String DRP_SERVICE_TYPE = "GP";
    public static final String HPIO_NO_QUALIFIER = "8003620000030333";

    public static final String DRP_AUSADDR_STREETNAME = "287 Mt Dandenong Road";
    public static final String DRP_AUSADDR_SUBURB = "Croydon";
    public static final String DRP_AUSADDR_POSTCODE = "3136";
    public static final String DRP_AUSADDR_POSTCODE_INVALID = "313";

    // DRP HPII test constants
    public static final String DRP_HPII = "http://ns.electronichealth.net.au/id/hi/hpii/1.0/8003610000010401";

    public static AustralianAddressCriteriaType getAustralianAddressCriteriaTypeForMedicare() {
        AustralianAddressCriteriaType address = new AustralianAddressCriteriaType();
        address.setUnstructuredAddressLine(MCA_AUSADDR_UNSTRUCTUREDADDRESSLINE);
        address.setSuburb(MCA_AUSADDR_SUBURB);
        address.setPostcode(MCA_AUSADDR_POSTCODE);
        address.setState(StateType.ACT);
        return address;
    }

    public static InternationalAddressCriteriaType getInternationalAddressCriteriaTypeForMedicare() {
        InternationalAddressCriteriaType address = new InternationalAddressCriteriaType();
        address.setInternationalAddressLine(MCA_AUSADDR_UNSTRUCTUREDADDRESSLINE);
        address.setCountry(CountryType.VALUE_13);
        address.setInternationalPostcode(MCA_AUSADDR_POSTCODE);
        address.setInternationalStateProvince("ACT");
        return address;
    }

    public static AustralianAddressCriteriaType getAustralianAddressCriteriaTypeForDRP() {
        AustralianAddressCriteriaType address = new AustralianAddressCriteriaType();

        address.setStreetSuffix(StreetSuffixType.CN);
        address.setStreetType(StreetType.ACCS);
        address.setStreetName(DRP_AUSADDR_STREETNAME);
        address.setSuburb(DRP_AUSADDR_SUBURB);
        address.setPostcode(DRP_AUSADDR_POSTCODE);
        address.setState(StateType.VIC);
        PostalDeliveryGroupType deliveryGroupType = new PostalDeliveryGroupType();
        deliveryGroupType.setPostalDeliveryType(PostalDeliveryType.CARE_PO);
        address.setPostalDeliveryGroup(deliveryGroupType);
        return address;
    }

    public static InternationalAddressCriteriaType getInternationalAddressCriteriaTypeForDRP() {
        InternationalAddressCriteriaType address = new InternationalAddressCriteriaType();
        address.setInternationalAddressLine(DRP_AUSADDR_STREETNAME);
        address.setCountry(CountryType.VALUE_13);
        address.setInternationalPostcode(DRP_AUSADDR_POSTCODE);
        address.setInternationalStateProvince(StateType.VIC.toString());
        return address;
    }
}
