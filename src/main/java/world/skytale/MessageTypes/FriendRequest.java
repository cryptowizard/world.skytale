package world.skytale.MessageTypes;

import world.skytale.JSONmodels.PublicProfileJSON;

public class FriendRequest {


    public static final String TITLE_TAG = "FRIEND_REQUEST";

    String receiver;
    private String senderID;
    private long time;

    private byte [] signature;
    private PublicProfileJSON publicProfile;

    public static FriendRequest makeFriendRequest(String receiver)
    {
        return new FriendRequest();
    }


    public DownloadedMail toDownloadedMail()
    {
            return new DownloadedMail();
    }

    public static FriendRequest fromDownloadedMail(DownloadedMail downloadedMail)
    {
        return new FriendRequest();
    }






}
