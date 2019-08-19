package world.skytale.Database.DataAceessObjects;

/**
 * I recomend storing profile picture together with Contact it is much faster than having a join
 * with ProfilePage
 * For the contats for whom we dont store picture it can have empty string it is more efficient than join or having separate tables
 */
public class ContactWithPictureDAO  extends ContactDAO{

    String picturePah;
}
