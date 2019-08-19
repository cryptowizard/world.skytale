package world.skytale.JSONmodels;

import java.io.Serializable;
import java.security.PublicKey;

/**
 *  Minimum contact information
 */
public class ContactJSON implements Serializable {

    private static final long serialVersionUID = 57131L;

    byte [] publicKey;
    String firstName;
    String lastName;
    String adress;

    public PublicKey getPublicKey()
    {
        PublicKey pb;
        return null;
    }

}
