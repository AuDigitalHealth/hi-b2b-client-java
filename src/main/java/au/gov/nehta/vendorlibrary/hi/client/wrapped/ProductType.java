package au.gov.nehta.vendorlibrary.hi.client.wrapped;

/**
 * ProductType represents either a
 * au.net.electronichealth.ns.hi.common.commoncoreelements._3_0.ProductType
 * or a
 * au.net.electronichealth.ns.hi.xsd.common.commoncoreelements._3.ProductType
 * <p>
 * based on which service it is being used for
 */
public class ProductType {

    private final String platform;
    private final String productName;
    private final String productVersion;
    private final QualifiedId vendor;

    public ProductType(String platform, String productName, String productVersion, QualifiedId vendor) {
        this.platform = platform;
        this.productName = productName;
        this.productVersion = productVersion;
        this.vendor = vendor;
    }

    public au.net.electronichealth.ns.hi.xsd.common.commoncoreelements._3.ProductType as3Type() {
        au.net.electronichealth.ns.hi.xsd.common.commoncoreelements._3.ProductType
                pt = new au.net.electronichealth.ns.hi.xsd.common.commoncoreelements._3.ProductType();

        pt.setPlatform(this.platform);
        pt.setProductName(this.productName);
        pt.setProductVersion(this.productVersion);
        pt.setVendor(this.vendor.as3Type());
        return pt;
    }
}
