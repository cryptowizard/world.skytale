package world.skytale.JSONmodels;

import java.io.Serializable;

public class PostJSON implements Serializable {

    private static final long serialVersionUID = 57134L;

    String text;
    AttachedFileJSON[] attachments;
    long time;
    int viewType;
    int displayPrioritY;
    String link;
    String orginalSenderID;
}
