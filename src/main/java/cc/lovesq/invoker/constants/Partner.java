package cc.lovesq.invoker.constants;

import java.util.HashMap;
import java.util.Map;

public enum Partner {
    SLS("sls", "webtemplate", "Simple Log Service API");

    private String partner;
    private String identity;
    private String desc;

    private Partner(String partner, String identity, String desc) {
        this.partner = partner;
        this.identity = identity;
        this.desc = desc;
    }

    public String getPartner() {
        return partner;
    }

    public String getIdentity() {
        return identity;
    }

    public String getDesc() {
        return desc;
    }

    private static final Map<String, Partner> enumMap = new HashMap<String, Partner>();
    static {
        for (Partner oneEnum : Partner.values()) {
            enumMap.put(oneEnum.getPartner(), oneEnum);
        }
    }

    public static boolean isDefined(String partner) {
        return enumMap.containsKey(partner);
    }
}
