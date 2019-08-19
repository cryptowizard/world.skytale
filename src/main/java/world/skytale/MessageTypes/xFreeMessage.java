package world.skytale.MessageTypes;

public interface xFreeMessage {


    public static final String TAG = "xFreeMedia";

    public xFreeMessage fromDownloadedMail(DownloadedMail downloadedMail) throws IllegalArgumentException;

    public boolean proceessInvomingMessage();

    public DownloadedMail toDownloadedMail();


}
