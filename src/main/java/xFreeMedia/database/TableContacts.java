package xFreeMedia.database;

import world.skytale.models.Contact;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

public class TableContacts {





        public static final String TABLE_NAME = "Contacts";

        public static final String ID = "ID";
        public static final String firstName = "firstName";
        public static final String lastName = "lastName";
        public static final String email = "email";
        public static final String picturePath = "picturePath";
        public static final String type = "type";
        public static final String publickKey = "publicKey";
        //public static final String chatID = "chatID";

        public static final String lastUpdateDate = "lastUpadateDate";




        public  static String CreateTable()
        {
            String createTableContacts = "CREATE TABLE "+TableContacts.TABLE_NAME+" (\r\n" +
                    "	"+TableContacts.ID+"	TEXT NOT NULL,\r\n" +
                    "	"+TableContacts.firstName+"	TEXT,\r\n" +
                    "	"+TableContacts.lastName+"	TEXT,\r\n" +
                    "	"+TableContacts.email+"	TEXT NOT NULL,\r\n" +
                    "	"+TableContacts.picturePath+"	TEXT,\r\n" +
                    "	"+TableContacts.type+"	INTEGER DEFAULT 1,\r\n" +
                    "	"+TableContacts.lastUpdateDate+"	INTEGER DEFAULT 0,\r\n" +
                    "	"+TableContacts.publickKey+"	TEXT NOT NULL,\r\n" +
                    "	PRIMARY KEY("+TableContacts.ID+")\r\n" +
                    ");";
            return createTableContacts;
        }






}
