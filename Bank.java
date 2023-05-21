public class Bank {
    public String name;
    public float x;
    public float y;
    public List branches;
    public String branchName;
    public boolean isBranch = false;

    public Bank(float x, float y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
        isBranch = false;
        branches = new List();
    }

    public Bank(float x, float y, String name, String branchName) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.branchName = branchName;
        isBranch = true;
    }

    public String toString() {
        if (isBranch)
            return "{type : branch, name : " + this.name + ", branch name : " + this.branchName + ", coordinates : (" + this.x + "," + this.y + ")}";
        else
            return "{type : central, name : " + this.name + ", coordinates : (" + this.x + "," + this.y + ")}";
    }
}

class List {
    public int size = 0;
    public int biggestSize = 100;
    public Bank[] data;

    public List() {
        data = new Bank[biggestSize];
    }

    public void add(Bank bank) {
        data[size] = bank;
        ++size;
        //check if the array is full:
        if (size == biggestSize) {
            biggestSize = biggestSize * 2;
            Bank[] temp_data = new Bank[size];
            for (int i = 0; i < size; ++i)
                temp_data[i] = data[i];
            data = new Bank[biggestSize];
            for (int i = 0; i < size; ++i)
                data[i] = temp_data[i];
        }
    }
}
