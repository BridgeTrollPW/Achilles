package net.gat3way.achilles.crypt.entity;

import java.io.Serializable;
import java.util.List;

public class Vault implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -4862686976834705543L;
    /**
     *
     */
    private String profileName;
    private List<XS2AAccount> xs2aAccounts;

    /**
     * @return the profileName
     */
    public String getProfileName() {
        return profileName;
    }

    /**
     * @return the xS2AAccounts
     */
    public List<XS2AAccount> getXS2AAccounts() {
        return xs2aAccounts;
    }

    /**
     * @param xS2AAccounts the xS2AAccounts to set
     */
    public void setXS2AAccounts(List<XS2AAccount> xS2AAccounts) {
        this.xs2aAccounts = xS2AAccounts;
    }

    /**
     * @param profileName the profileName to set
     */
    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }
    
}
