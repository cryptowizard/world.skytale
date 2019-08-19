package world.skytale.Utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

import java.io.FileOutputStream;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;
//import jdk.testlibrary.RandomFactory;

public class Cyphers {

    public static String padding = "xxxxxxxxxxxxxxxxxxxxxxxxxxxx";
    public static int lp = padding.length();

    public static String encoding = "ISO-8859-15";





    public static String publicToString(PublicKey pub)
    {
        try {
            return new String(pub.getEncoded(),encoding);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            return null;
        }

    }
    public static PublicKey publicFromString(String pub)
    {
        try {
            byte [] pb = pub.getBytes("ISO-8859-15");
            return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(pb));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    public static PrivateKey privateFromString(String pk)
    {
        try {
            byte [] pb = pk.getBytes("ISO-8859-15");
            return KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(pb));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static String privateToString(PrivateKey pk)
    {
        try {
            return new String(pk.getEncoded(),encoding);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            return null;
        }

    }


    public static SecretKey makeAesKey(String Password) throws UnsupportedEncodingException
    {

        String encodedKey= "jyzXyL4Su42okBnjW9CMEg==";
        int el= encodedKey.length();
        int pl=Password.length();
        int n1= (el-pl)/2;

        encodedKey = encodedKey.substring(0,n1)+Password+encodedKey.substring(n1+pl,el);
        // byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        byte [] decodedKey = encodedKey.getBytes(encoding);
        // rebuild key using SecretKeySpec

        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, 16, "AES");
        return originalKey;

    }
    public static SecretKey makeAes(String Password) throws UnsupportedEncodingException
    {



        // byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        byte [] decodedKey = Password.getBytes(encoding);
        // rebuild key using SecretKeySpec

        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, 16, "AES");
        return originalKey;
    }

    public static SecretKey makeAesFromByte(byte decodedKey[])
    {
        SecretKey originalKey = new SecretKeySpec(decodedKey,0, 16, "AES");
        return originalKey;
    }

    public static SecretKey makeAesFromByte(byte decodedKey[], int offset)
    {
        SecretKey originalKey = new SecretKeySpec(decodedKey,offset, 16, "AES");
        return originalKey;
    }


    public static byte[] encryptRSA(PublicKey publicKey,byte [] m) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");

        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(m);
    }

    public static byte[] decryptRSA(PrivateKey privateKey, byte [] encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(encrypted);
    }

    public static KeyPair buildKeyPair(int keySize) {

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(keySize);
            return keyPairGenerator.genKeyPair();
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static byte [] encryptAES(SecretKey aesKey, byte[] m) throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException
    {

        String message = new String (m,"ISO-8859-1" );
        message = Cyphers.padding+message;

        // Encrypt cipher
        Cipher encryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        encryptCipher.init(Cipher.ENCRYPT_MODE, aesKey);

        return encryptCipher.doFinal(message.getBytes("ISO-8859-1"));
    }
    public static byte [] decryptAES(SecretKey aesKey, byte [] c) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException
    {

        Cipher decryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(aesKey.getEncoded());
        decryptCipher.init(Cipher.DECRYPT_MODE, aesKey, ivParameterSpec);


        byte [] m = decryptCipher.doFinal(c);
        String e = new String(m,"ISO-8859-1").substring(Cyphers.lp);
        m = e.getBytes("ISO-8859-1");

        return m;
    }
    public static byte [] encryptLong(PublicKey p2, byte [] m) throws Exception
    {
        byte [] c=null;

        while(m.length>245)
        {
            if(c==null)
            {
                c = encryptRSA(p2, Arrays.copyOfRange(m,0,245));

            }
            else {
                byte [] e = encryptRSA(p2, Arrays.copyOfRange(m,0,245));
                ////System.out.println(c.length);
                c= combine(c,e);
            }
            m=Arrays.copyOfRange(m,245,m.length);
        }
        if(c==null)
        {
            c = encryptRSA(p2,m);

        }
        else {
            byte [] e = encryptRSA(p2,m);
            ////System.out.println(c.length);
            c = combine(c,e);
        }

        return c;
    }
    public static byte [] decryptLong(PrivateKey privateKey, byte [] m) throws Exception
    {
        byte [] c=null;

        while(m.length>256)
        {
            if(c==null)
            {
                c = decryptRSA(privateKey, Arrays.copyOfRange(m,0,256));

            }
            else {
                byte [] e = decryptRSA(privateKey, Arrays.copyOfRange(m,0,256));
                ////System.out.println(e.length);
                c= combine(c,e);
            }
            m=Arrays.copyOfRange(m,256,m.length);
        }
        if(c==null)
        {
            c = decryptRSA(privateKey,m);

        }
        else {
            byte [] e = decryptRSA(privateKey,m);
            ////System.out.println(e.length);
            c = combine(c,e);
        }

        return c;
    }

    public static byte[] combine(byte[] a, byte[] b){
        int length = a.length + b.length;
        byte[] result = new byte[length];
        for(int i=0;i<a.length;i++)
        {
            result[i]=a[i];
        }
        for(int i=a.length;i<length;i++)
        {
            result[i]=b[i-a.length];
        }

        return result;
    }
    public static SecretKey randomAes() throws NoSuchAlgorithmException
    {
        return  KeyGenerator.getInstance("AES").generateKey();
    }
    public static Byte[] toObjects(byte[] bytesPrim) {
        Byte[] bytes = new Byte[bytesPrim.length];
        //Arrays.setAll(bytes, n -> bytesPrim[n]);
        return bytes;
    }
    public static byte[] toByte(Byte []  B)
    {
        byte[] b2 = new byte[B.length];
        for (int i = 0; i < B.length; i++)
        {
            b2[i] = B[i];
        }
        return b2;
    }


    public static List generateList(PrivateKey pk, String password, int length) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException
    {

        List<Byte []> list = new ArrayList<Byte []>();

//		SecretKey a = passwordCypher.makeAesKey("aaaaaaaaaa");
//		byte [] b = a.getEncoded();
//		SecretKey c = passwordCypher.makeAesFromByte(b);
//		////System.out.println(c.equals(a));
        byte[] privateKeyBytes = pk.getEncoded();

        byte[] A1 = Cyphers.makeAesKey(password).getEncoded();
        list.add(Cyphers.toObjects(A1));


        for(int i=0;i<20000;i++)
        {
            byte [] A2 = KeyGenerator.getInstance("AES").generateKey().getEncoded();
            list.add(Cyphers.toObjects(A2));

            //System.out.println(i);
        }
        byte [] P4 = privateKeyBytes;
        list.add(Cyphers.toObjects(P4));

        for(int i=list.size()-1;i>0;i--)
        {
            SecretKey k = Cyphers.makeAesFromByte(Cyphers.toByte(list.get(i-1)));
            byte[] m = Cyphers.toByte(list.get(i));
            byte []c = Cyphers.encryptAES(k,m);
            list.set(i,Cyphers.toObjects(c));

        }
        list.set(0, null);
        return list;

    }
    public static PrivateKey decryptList(List list , String password) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidKeySpecException
    {
        byte[] A1 = Cyphers.makeAesKey(password).getEncoded();
        list.set(0,Cyphers.toObjects(A1));


        for(int i=0;i<list.size()-1;i++)
        {
            SecretKey k = Cyphers.makeAesFromByte(Cyphers.toByte((Byte [])list.get(i)));
            byte[] m = Cyphers.toByte((Byte [])list.get(i+1));
            byte []c = Cyphers.decryptAES(k,m);
            list.set(i+1,Cyphers.toObjects(c));

            //System.out.println(i);
        }
        byte [] privateKeyBytes =Cyphers.toByte((Byte [])list.get(list.size()-1));
        ////System.out.println(new String(privateKeyBytes));
        ////System.out.println(new String(privateKey0.getEncoded()));

        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey pkN = kf.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));

        return pkN;

    }
    public static String SHA512(String input)
    {
        try {
            // getInstance() method is called with algorithm SHA-512
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);



            // return the HashText
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public static String SHA256(String input)
    {
        try {
            // getInstance() method is called with algorithm SHA-512
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);



            // return the HashText
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public  static byte[] getSalt(int l) throws NoSuchAlgorithmException, NoSuchProviderException
    {
        //Always use a SecureRandom generator
        //SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
        SecureRandom random = new SecureRandom();
        byte[] bytes =  random.generateSeed(l);
        //SecureRandom.getInstance().nextBytes(bytes);


        return bytes;
    }
    private static String generateStorngPasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        int iterations = 1000;
        char[] chars = password.toCharArray();
        byte[] salt = getSalt();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }

    private static String generateStorngPasswordHash(String password, byte [] salt, int iterations) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        //int iterations = 7700000;
        char[] chars = password.toCharArray();


        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return ( toHex(hash));
    }
    private static String generateStorngPasswordHash2(String password, byte [] salt, int iterations) throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException
    {
        //int iterations = 7700000;
        String salt2= new String(salt, "ISO-8859-1");
        String p2 = salt2+password;

        for(int i=0;i<=iterations;i++)
        {
            //System.out.println(p2);
            p2=SHA512(p2);
        }
        return p2;
    }


    private static byte[] getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[20];
        sr.nextBytes(salt);
        return salt;
    }

    private static String toHex(byte[] array) throws NoSuchAlgorithmException
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }
    public static void writeBytes(String path, byte [] file ) throws IOException
    {
        FileOutputStream stream = new FileOutputStream(path);
        try {
            stream.write(file);
        } finally {
            stream.close();
        }
    }

    //    public static byte [] loadFile(String path) throws IOException
//    {
//        File file = new File(path);
////	    	// ...(file is initialised)...
//        byte[] fileContent = Files.readAllBytes(file.toPath());
//        return fileContent;
//    }
    public static byte [] encryptPK (PrivateKey privateKey, String password, byte [] salt, int iterations) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, UnsupportedEncodingException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException
    {
        byte [] pk = privateKey.getEncoded();
        String generatedSecuredPasswordHash2 = generateStorngPasswordHash2(password,salt,iterations);

        byte [] getKey =generatedSecuredPasswordHash2.getBytes();

        int n = getKey.length/16;
        byte [][] keyList = new  byte [n][16];
        //System.out.println(n);
        for(int i=0;i<n;i++)
        {
            keyList[i]=Arrays.copyOfRange(getKey, i*16, (i+1)*16);
            // //System.out.println(new String(keyList[i]));
            SecretKey o = Cyphers.makeAesFromByte(keyList[i]);
            pk = Cyphers.encryptAES(o, pk);


        }
        return pk;

    }
    public static PrivateKey decryptPK(byte [] pk, String password, byte []salt , int iterations) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException
    {
        String generatedSecuredPasswordHash2 = generateStorngPasswordHash2(password,salt,iterations);
        byte [] getKey =generatedSecuredPasswordHash2.getBytes();

        int n = getKey.length/16;
        byte [][] keyList = new  byte [n][16];

        for(int i=0;i<n;i++)
        {
            keyList[i]=Arrays.copyOfRange(getKey, i*16, (i+1)*16);
            ////System.out.println(new String(keyList[i]));
            //SecretKey o = passwordCypher.makeAesFromByte(keyList[i]);
            //pk = passwordCypher.encryptAES(o, pk);


        }
        byte [] kc = pk;
        try {
            for(int i=n-1;i>=0;i--)
            {
                SecretKey o = Cyphers.makeAesFromByte(keyList[i]);

                kc= Cyphers.decryptAES(o, kc);
                ////System.out.println(i);


            }
        }catch(Exception e)
        {

            //System.out.println("Wrong key");

            return null;
        }
        byte [] mm = kc;

        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey pkN = kf.generatePrivate(new PKCS8EncodedKeySpec(mm));
        return pkN;

    }

    public void sddg( byte [] privateKeyBytes)
    {
        try {
            PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
// public static void main(String [] args) throws UnsupportedEncodingException, Exception
// {
//
//
//	 KeyPair keyPair = buildKeyPair(2048);
//
//
//	PublicKey pubKey0 = keyPair.getPublic();
//	byte [] pubB = pubKey0.getEncoded();
//   	PrivateKey privateKey0 = keyPair.getPrivate();
//   	byte [] pk = privateKey0.getEncoded();
//
//	 String  password = "EldoRado77";
//
//	 byte [] salt = "`ÃšÃ¿Å’Â«ajifi\fÃ£,)Âº".getBytes();
//
//	 int iterations =100;
//	 byte Encrypted [] = passwordCypher.encryptPK(privateKey0, password, salt, iterations);
//
//	 PrivateKey back = passwordCypher.decryptPK(Encrypted, password, salt, iterations);
//	 if(back!=null)
//	 {
//		 	RSASignature check = new RSASignature(pubKey0);
//		 	byte [] r = check.request();
//		 	//System.out.println(check.requestValid(r,passwordCypher.decryptLong(back, r)));
//	 }
//	 else
//	 {
//
//	 }
//	 byte [] bytes = null;
//	 String path = "C:\\Users\\Maciej.SOLECKI\\Pictures\\";
//	 writeBytes(path+"salt.xd",salt);
//	 writeBytes(path+"Encrypted.xd",salt);
//	 writeBytes(path+"Public.xd",pubB);
//	 //System.out.println(SHA512("ys0so").getBytes().length);
//	 //System.out.println(generateStorngPasswordHash("auaf").getBytes().length);
//	// generateStorngPasswordHash2("OKI",getSalt(),10);
//
////    // String generatedSecuredPasswordHash = generateStorngPasswordHash(originalPassword,salt);
////  //   //System.out.println(generatedSecuredPasswordHash);
////     String generatedSecuredPasswordHash2 = generateStorngPasswordHash(originalPassword,salt,1000);
////
////     //System.out.println(generatedSecuredPasswordHash2.getBytes().length);
////     byte [] getKey =generatedSecuredPasswordHash2.getBytes();
////
////     int n = getKey.length/16;
////     byte [][] keyList = new  byte [n][16];
////     //System.out.println(n);
////     for(int i=0;i<n;i++)
////     {
////    	 keyList[i]=Arrays.copyOfRange(getKey, i*16, (i+1)*16);
////    	 //System.out.println(new String(keyList[i]));
////    	 SecretKey o = passwordCypher.makeAesFromByte(keyList[i]);
////    	 pk = passwordCypher.encryptAES(o, pk);
////
////
////     }
////
////
////     byte [] kc = pk;
////
////     ////System.out.println(new String(kc));
////
////
////
////     for(int i=n-1;i>=0;i--)
////     {
////    	 SecretKey o = passwordCypher.makeAesFromByte(keyList[i]);
////
////    	 kc= passwordCypher.decryptAES(o, kc);
////    	 ////System.out.println(i);
////
////
////     }
////
////
////
////
////
////
////     byte [] mm = kc;
////
////     KeyFactory kf = KeyFactory.getInstance("RSA");
//// 	PrivateKey pkN = kf.generatePrivate(new PKCS8EncodedKeySpec(mm));
//
//
//
//
//
//
////  	byte[] ccc = passwordCypher.encryptLong(pubKey0, mo.getBytes("ISO-8859-1"));
//
//
//
//
//
//
//
//	//RSASignature check = new RSASignature(pubKey0);
//
//
//	String a12 = "Hello China Frreedom";
//
//
//
//
//
//
////
////	// create new key
////	 SecretKey secretKey = KeyGenerator.getInstance("AES").generateKey();
////
////	 // get base64 encoded version of the key
////	 String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
////	 //System.out.println(encodedKey);
////	 String Password = "Amaretsu23";
////	 int el= encodedKey.length();
////	 int pl=Password.length();
////	 int n1= (el-pl)/2;
////	 encodedKey = encodedKey.substring(0,n1)+Password+encodedKey.substring(n1+pl,el);
////
////	// decode the base64 encoded string
////	 byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
////	 // rebuild key using SecretKeySpec
////	 SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
////	SecretKey aesKey=originalKey;
////
////	//System.out.println(encodedKey);
////	//System.out.println(encodedKey.length());
////
////    // Encrypt cipher
////    Cipher encryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
////    encryptCipher.init(Cipher.ENCRYPT_MODE, aesKey);
////
////
////    String message = new String (privateKeyBytes,"ISO-8859-1" );
////
////
////    String not = "xxxxxxxxxxxxxxxxxxxxx";
////    int l = not.length();
////    message = not+message;
////
////    byte [] c = encryptCipher.doFinal(message.getBytes("ISO-8859-1"));
////
////    // Decrypt cipher
////    Cipher decryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
////    IvParameterSpec ivParameterSpec = new IvParameterSpec(aesKey.getEncoded());
////    decryptCipher.init(Cipher.DECRYPT_MODE, aesKey, ivParameterSpec);
////    Cipher dec=decryptCipher;
////
////
////    byte [] m = dec.doFinal(c);
////    String e = new String(m,"ISO-8859-1").substring(l);
////    m = e.getBytes("ISO-8859-1");
////
//// 	PrivateKey privateKeyF = kf.generatePrivate(new PKCS8EncodedKeySpec(m));
////
////   	byte [] ddd = passwordCypher.decryptRSA(privateKeyF, enc);
////   	//System.out.println(new String(ddd,"ISO-8859-1")+"DDDD");
//
//
//
//
//
//
//
//
//
// }

}
