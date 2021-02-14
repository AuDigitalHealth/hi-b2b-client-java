package au.gov.nehta.vendorlibrary.hi.client.wrapped;

public class QualifiedId {

    private final String qualifier;
    private final String id;

    public QualifiedId(String qualifier, String id) {
        this.id = id;
        this.qualifier = qualifier;
    }

    public au.net.electronichealth.ns.hi.xsd.common.qualifiedidentifier._3.QualifiedId as3Type() {
        au.net.electronichealth.ns.hi.xsd.common.qualifiedidentifier._3.QualifiedId
                qi = new au.net.electronichealth.ns.hi.xsd.common.qualifiedidentifier._3.QualifiedId();

        qi.setId(this.id);
        qi.setQualifier(this.qualifier);
        return qi;
    }
}
