package net.gat3way.achilles.crypt.entity;

import java.io.Serializable;

public class XS2AAccount implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 4771428296938250128L;
    private String iban;

    /**
     * @return the iban
     */
    public String getIban() {
        return iban;
    }

    /**
     * @param iban the iban to set
     */
    public void setIban(String iban) {
        this.iban = iban;
    }
    
}
