import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static ArrayList<Block> blockList;
    public static void main(String[] argc) throws NoSuchAlgorithmException {
        int diff = 5;
        int blockSize = 10;
        blockList = new ArrayList<>();

        for (int i = 0; i < blockSize ; i++) {
            StringBuffer temp = new StringBuffer();
            Random rnd = new Random();
            for (int j = 0; j < 20; j++) {
                int rIndex = rnd.nextInt(3);
                switch (rIndex) {
                    case 0:
                        // a-z
                        temp.append((char) ((int) (rnd.nextInt(26)) + 97));
                        break;
                    case 1:
                        // A-Z
                        temp.append((char) ((int) (rnd.nextInt(26)) + 65));
                        break;
                    case 2:
                        // 0-9
                        temp.append((rnd.nextInt(10)));
                        break;
                }
            }
            System.out.println("Block " + i);
            if(i == 0){
                blockList.add(new Block(temp.toString(), "0"));
                System.out.println("Mining Block " + i + "...");
                blockList.get(i).mineBlock(diff);
            }
            else{
                blockList.add(new Block(temp.toString(), blockList.get(i - 1).getMyHash()));
                System.out.println("Mining Block " + i + "...");
                blockList.get(i).mineBlock(diff);
            }
        }
        System.out.println("================Block Created===================");
        System.out.println("BlockChain is Valid? " + isValid());
        System.out.println("================Block Infomation===================");

        for (Block temp : blockList) {
            temp.printBlock();
        }
    }
    public static boolean isValid() throws NoSuchAlgorithmException{
        Block currBlock;
        Block prevBlock;

        for (int i = 1; i < blockList.size() ; i++) {
            currBlock = blockList.get(i);
            prevBlock = blockList.get(i - 1);

            if(!prevBlock.getMyHash().equals(currBlock.getPreviousHash())){
                return false;
            }
            if(!currBlock.getMyHash().equals(currBlock.sha256())){
                return false;
            }
        }
        return true;
    }
}
