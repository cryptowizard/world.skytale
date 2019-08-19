package world.skytale.MessageTypes;

/**
 * Exception thrown when downloaded mail does not have nessesary arguments to be converted
 * to xFreeMessage
 */
public class NotEnaughArgumentsException extends  Exception{

    public NotEnaughArgumentsException(String message)
    {
        super(message);
    }
}
