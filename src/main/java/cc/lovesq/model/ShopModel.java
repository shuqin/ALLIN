package cc.lovesq.model;

public class ShopModel {

    /** 是否有线下门店 */
    private boolean hasRetailStore;

    /** 是否加入了担保 */
    private boolean isSecuredShop;

    public boolean hasRetailStore() {
        return hasRetailStore;
    }

    public void setHasRetailStore(boolean hasRetailStore) {
        this.hasRetailStore = hasRetailStore;
    }

    public boolean isSecuredShop() {
        return isSecuredShop;
    }

    public void setSecuredShop(boolean securedShop) {
        isSecuredShop = securedShop;
    }
}
