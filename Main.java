import java.util.Scanner;

public class Main {
    public static void delete(KDTree root, float x, float y) {
        if (root.x == x && root.y == y)
            if (root.leftChild == null && root.rightChild == null) {
                root.bank = null;
                root = null;
            }

        if (root.x_based) {
            if (x >= root.x)
                delete(root.rightChild, x, y);
            else
                delete(root.leftChild, x, y);
        } else {
            if (y >= root.y)
                delete(root.rightChild, x, y);
            else
                delete(root.leftChild, x, y);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map neighborhoods = new Map();
        List centralBanks = new List();
        KDTree root = null;
        boolean isFirstBank = true;
        while (scanner.hasNext()) {
            String s = scanner.next();
            switch (s) {
                case "addN":
                    float x1 = scanner.nextFloat();
                    float x2 = scanner.nextFloat();
                    float y1 = scanner.nextFloat();
                    float y2 = scanner.nextFloat();
                    String name = scanner.next();
                    neighborhoods.put(name, new neighborhood(name, x1, x2, y1, y2));
                    break;
                case "addB":
                    float bX = scanner.nextFloat();
                    float bY = scanner.nextFloat();
                    String CBname = scanner.next();
                    Bank newB;
                    if (isFirstBank) {
                        newB = new Bank(bX, bY, CBname);
                        root = new KDTree(newB);
                        isFirstBank = false;
                    } else {
                        while (root.contains(bX, bY)) {
                            System.out.println("There is already a bank here! please enter new coordinate:");
                            bX = scanner.nextFloat();
                            bY = scanner.nextFloat();
                        }
                        newB = new Bank(bX, bY, CBname);
                        root.addChild(newB);
                    }
                    centralBanks.add(newB);
                    break;
                case "addBr":
                    float brX = scanner.nextFloat();
                    float brY = scanner.nextFloat();
                    while (root.contains(brX, brY)) {
                        System.out.println("There is already a bank here! please enter new coordinate:");
                        brX = scanner.nextFloat();
                        brY = scanner.nextFloat();
                    }
                    String bName = scanner.next();
                    String brName = scanner.next();
                    Bank newBr;
                    newBr = new Bank(brX, brY, bName, brName);
                    root.addChild(newBr);
                    for (int i = 0; i < centralBanks.size; ++i)
                        if (centralBanks.data[i].name.equals(bName)) {
                            centralBanks.data[i].branches.add(newBr);
                            break;
                        }
                    break;
                case "delBr":
                    float x = scanner.nextFloat();
                    float y = scanner.nextFloat();
                    if (!root.contains(x, y))
                        System.out.println("There is no bank here!");
                    else {
                        Bank delB = root.get(x, y);
                        if (!delB.isBranch)
                            System.out.println("You can't delete a central branch!");
                        else
                            delete(root, x, y);
                    }
                    break;
                case "listB":
                    String neighborhoodName = scanner.next();
                    root.isInNeighborhood(neighborhoods.get(neighborhoodName));
                    break;
                case "listBrs":
                    String bankName = scanner.next();
                    for (int i = 0; i < centralBanks.size; ++i) {
                        Bank bank = centralBanks.data[i];
                        if (bank.name.equals(bankName))
                            for (int j = 0; j < bank.branches.size; ++j) {
                                Bank branch = bank.branches.data[j];
                                System.out.println("(" + branch.x + "," + branch.y + ")");
                            }
                    }

                    break;
                case "nearB":
                    x = scanner.nextFloat();
                    y = scanner.nextFloat();
                    System.out.println(root.nearest(x, y).toString());
                    break;
                case "nearBr":
                    x = scanner.nextFloat();
                    y = scanner.nextFloat();
                    bankName = scanner.next();
                    Bank bank = null;
                    for (int i = 0; i < centralBanks.size; ++i) {
                        if (centralBanks.data[i].name.equals(bankName)) {
                            bank = centralBanks.data[i];
                        }
                    }
                    KDTree tempTree = new KDTree(bank.branches.data[0]);
                    for (int i = 1; i < bank.branches.size; ++i) {
                        tempTree.addChild(bank.branches.data[i]);
                    }

                    System.out.println(tempTree.nearest(x, y).toString());
                    break;
                case "availB":
                    float r = scanner.nextFloat();
                    x = scanner.nextFloat();
                    y = scanner.nextFloat();
                    root.isAvailable(x, y, r);
                    break;
                case "mostBrs":
                    Bank mostBr = centralBanks.data[0];
                    for (int i = 1; i < centralBanks.size; ++i)
                        if (centralBanks.data[i].branches.size > mostBr.branches.size)
                            mostBr = centralBanks.data[i];
                    System.out.println(mostBr.name);
                    break;
                default:
                    System.out.println("Invalid command format!");
                    break;
            }
        }
    }
}
