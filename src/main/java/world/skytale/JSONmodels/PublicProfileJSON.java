package world.skytale.JSONmodels;

import java.io.Serializable;

/**
 *  Public profile is contains all the public information that will travel unencrypted thou the network
 *  profile is optimized to take minimum size on the disk
 */

public class PublicProfileJSON implements Serializable {

    private static final long serialVersionUID = 571321L;
    public static final String NAME_TAG = "publicprofile";

    String description;
    String [] links;


    ContactJSON contact;
    AttachedFileJSON profilePicture;


}
