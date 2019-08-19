package xFreeMedia;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class One {



    public static void main (String... args) throws UnsupportedEncodingException {

//   for(int i=0;i<10;i++) {
//       KeyPair kp = Cyphers.buildKeyPair(2048);
//       byte[] pb = kp.getPublic().getEncoded();
//
//
//       String string = new String(pb, Cyphers.encoding);
//       String string2 = Base64.getEncoder().encodeToString(pb);
//        String string3 = Contact.makeID(pb);
//        System.out.println(string3);
//       System.out.println(string2);
//   }
   System.out.println(new String(String.valueOf(new Date().getTime())).length());


}

}
