import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class Block {

    private String previousHash;
    private String myData;
    private String myHash;
    private long timeStamp;
    private int nonce;
    public Block(String data, String pH) throws NoSuchAlgorithmException{
        this.myData = data;
        this.previousHash = pH;
        this.timeStamp = new Date().getTime();
        this.myHash = sha256();
    }
    public String sha256() throws NoSuchAlgorithmException{
        String originalString = this.myData + this.previousHash + this.timeStamp + this.nonce;
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return createHash(digest.digest(originalString.getBytes(StandardCharsets.UTF_8)));
    }
    private String createHash(byte[] hash){
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length ; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1)   hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
    public String getMyHash(){
        return this.myHash;
    }
    public String getPreviousHash(){
        return this.previousHash;
    }
    public void mineBlock(int diff) throws NoSuchAlgorithmException{
        String target = new String(new char[diff]).replace('\0', '0');
        while(!this.myHash.substring(0, diff).equals(target)){
            nonce++;
            this.myHash = sha256();
        }
        System.out.println("Block is mined !!!" + this.myHash);
    }
    public void printBlock(){
        System.out.println("hash : " + this.myHash);
        System.out.println("previoushash : " + this.previousHash);
        System.out.println("data : " + this.myData);
        System.out.println("timeStamp : " + this.timeStamp);
        System.out.println("nonce : " + this.nonce);
    }
}
