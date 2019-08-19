package world.skytale.models;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.PublicKey;

public class Contact {


    public static final int TYPE_DEFAULT=10;
    public static final int TYPE_ME=3;
    public static final int TYPE_FRIEND =2;
    public static final int TYPE_CLOSE_FRIEND=1;

    public static final int TYPE_OBSERVED=4;

    public static final int TYPE_SHARED=7;
    public static final int TYPE_REQUEST=6;
    public static final int TYPE_CHAT = 5;



    PublicKey publicKey;
    String firstName;
    String lastName;
    String adress;
    String type;



    public String getID()
    {
        return makeID(this.publicKey.getEncoded());
    }


    public static String makeID(byte []  encodedPublicKey)
    {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(encodedPublicKey);

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            String n = no.toString(32);
            return n.substring(5,20);
        }
        catch(Exception e)
        {
            return null;
        }
    }

}
