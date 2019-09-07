package world.skytale.messages;

import java.io.Serializable;

public class PostJSON implements Serializable {

    private static final long serialVersionUID = 57134L;

    String text;
    AttachedFileJSON[] attachments;
    long time;
    int viewType;

    String link;
    String orginalSenderID;


}


