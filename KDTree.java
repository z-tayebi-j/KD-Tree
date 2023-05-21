public class KDTree {
    /*public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n =scanner.nextInt();
        int root_x = scanner.nextInt();
        int root_y = scanner.nextInt();
        KDTree root = new KDTree(root_x, root_y);
        for (int i = 1; i < n; ++i) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            root.addChild(x, y);
        }
        System.out.println(root.leftChild.x);
    }*/
    public Bank bank;
    public float x;
    public float y;
    public int numOfChildren = 0;
    public KDTree leftChild = null;
    public KDTree rightChild = null;
    boolean x_based = true;


    public KDTree(Bank bank) {
        this.bank = bank;
        x = bank.x;
        y = bank.y;
    }

    public void addChild(Bank child) {
        float child_x = child.x;
        float child_y = child.y;
        if (this.x_based) {
            if (child_x >= this.x) {
                if (rightChild == null) {
                    rightChild = new KDTree(child);
                    rightChild.x_based = !this.x_based;
                } else
                    rightChild.addChild(child);
            } else {
                if (leftChild == null) {
                    leftChild = new KDTree(child);
                    leftChild.x_based = !this.x_based;
                } else
                    leftChild.addChild(child);
            }
        } else {
            if (child_y >= this.y) {
                if (rightChild == null) {
                    rightChild = new KDTree(child);
                    rightChild.x_based = !this.x_based;
                } else
                    rightChild.addChild(child);
            } else {
                if (leftChild == null) {
                    leftChild = new KDTree(child);
                    leftChild.x_based = !this.x_based;
                } else
                    leftChild.addChild(child);
            }
        }
        ++this.numOfChildren;
    }

    public Bank get(float x, float y) {
        if (this.x == x && this.y == y)
            return this.bank;
        if (this.x_based) {
            if (x >= this.x) {
                if (this.rightChild == null)
                    return null;
                else
                    return this.rightChild.get(x, y);
            } else {
                if (this.leftChild == null)
                    return null;
                else
                    return this.leftChild.get(x, y);
            }
        } else {
            if (y >= this.y) {
                if (this.rightChild == null)
                    return null;
                else
                    return this.rightChild.get(x, y);
            } else {
                if (this.leftChild == null)
                    return null;
                else
                    return this.leftChild.get(x, y);
            }
        }
    }

    public boolean contains(float x, float y) {
        if (this.x == x && this.y == y)
            return true;
        if (this.x_based) {
            if (x >= this.x) {
                if (this.rightChild == null)
                    return false;
                else
                    return this.rightChild.contains(x, y);
            } else {
                if (this.leftChild == null)
                    return false;
                else
                    return this.leftChild.contains(x, y);
            }
        } else {
            if (y >= this.y) {
                if (this.rightChild == null)
                    return false;
                else
                    return this.rightChild.contains(x, y);
            } else {
                if (this.leftChild == null)
                    return false;
                else
                    return this.leftChild.contains(x, y);
            }
        }
    }

    public void isInNeighborhood(neighborhood n) {
        float x1 = n.x1;
        float x2 = n.x2;
        float y1 = n.y1;
        float y2 = n.y2;
        if (this.x_based) {
            if (this.x < x1) {
                if (this.rightChild != null)
                    this.rightChild.isInNeighborhood(n);
            } else if (this.x > x2) {
                if (this.leftChild != null)
                    this.leftChild.isInNeighborhood(n);
            } else {
                if (this.y >= y1 && this.y <= y2)
                    System.out.println(this.bank);
                if (this.rightChild != null)
                    this.rightChild.isInNeighborhood(n);
                if (this.leftChild != null)
                    this.leftChild.isInNeighborhood(n);
            }
        } else {
            if (this.y < y1) {
                if (this.rightChild != null)
                    this.rightChild.isInNeighborhood(n);
            } else if (this.y > y2) {
                if (this.leftChild != null)
                    this.leftChild.isInNeighborhood(n);
            } else {
                if (this.x >= x1 && this.x <= x2)
                    System.out.println(this.bank);
                if (this.rightChild != null)
                    this.rightChild.isInNeighborhood(n);
                if (this.leftChild != null)
                    this.leftChild.isInNeighborhood(n);
            }
        }
    }

    public Bank nearest(float x, float y) {
        if (this.leftChild == null) {
            if (this.rightChild == null)
                return this.bank;
            else {
                Bank temp = rightChild.nearest(x, y);
                float dist = (x - this.x) * (x - this.x) + (y - this.y) * (y - this.y);
                float right_dist = (x - temp.x) * (x - temp.x) + (y - temp.y) * (y - temp.y);
                if (right_dist < dist)
                    return temp;
                else
                    return this.bank;
            }
        } else {
            if (this.rightChild == null) {
                Bank temp = leftChild.nearest(x, y);
                float dist = (x - this.x) * (x - this.x) + (y - this.y) * (y - this.y);
                float left_dist = (x - temp.x) * (x - temp.x) + (y - temp.y) * (y - temp.y);
                if (left_dist < dist)
                    return temp;
                else
                    return this.bank;
            } else {
                KDTree next;
                KDTree other;
                if (this.x_based) {
                    if (x < this.x) {
                        next = this.leftChild;
                        other = this.rightChild;
                    } else {
                        other = this.leftChild;
                        next = this.rightChild;
                    }
                    Bank temp = next.nearest(x, y);
                    float dist = (x - this.x) * (x - this.x) + (y - this.y) * (y - this.y);
                    float temp_dist = (x - temp.x) * (x - temp.x) + (y - temp.y) * (y - temp.y);
                    Bank best;
                    if (temp_dist < dist)
                        best = temp;
                    else
                        best = this.bank;
                    float best_dist = (x - best.x) * (x - best.x) + (y - best.y) * (y - best.y);
                    if ((x - this.x) * (x - this.x) <= best_dist) {
                        temp = other.nearest(x, y);
                        temp_dist = (x - temp.x) * (x - temp.x) + (y - temp.y) * (y - temp.y);
                        if (temp_dist < dist)
                            best = temp;
                        else
                            best = this.bank;
                    }
                    return best;
                } else {
                    if (y < this.y) {
                        next = this.leftChild;
                        other = this.rightChild;
                    } else {
                        other = this.leftChild;
                        next = this.rightChild;
                    }
                    Bank temp = next.nearest(x, y);
                    float dist = (x - this.x) * (x - this.x) + (y - this.y) * (y - this.y);
                    float temp_dist = (x - temp.x) * (x - temp.x) + (y - temp.y) * (y - temp.y);
                    Bank best;
                    if (temp_dist < dist)
                        best = temp;
                    else
                        best = this.bank;
                    float best_dist = (x - best.x) * (x - best.x) + (y - best.y) * (y - best.y);
                    if ((y - this.y) * (y - this.y) <= best_dist) {
                        temp = other.nearest(x, y);
                        temp_dist = (x - temp.x) * (x - temp.x) + (y - temp.y) * (y - temp.y);
                        if (temp_dist < dist)
                            best = temp;
                        else
                            best = this.bank;
                    }
                    return best;
                }
            }
        }
    }

    public void isAvailable(float x, float y, float r) {
        float x1 = x - r;
        float x2 = x + r;
        float y1 = y - r;
        float y2 = y + r;
        if (this.x_based) {
            if (this.x < x1) {
                if (this.rightChild != null)
                    this.rightChild.isAvailable(x, y, r);
            } else if (this.x > x2) {
                if (this.leftChild != null)
                    this.leftChild.isAvailable(x, y, r);
            } else {
                if (this.y >= y1 && this.y <= y2)
                    if (r * r >= ((x - this.x) * (x - this.x) + (y - this.y) * (y - this.y)))
                        System.out.println(this.bank);
                if (this.rightChild != null)
                    this.rightChild.isAvailable(x, y, r);
                if (this.leftChild != null)
                    this.leftChild.isAvailable(x, y, r);
            }
        } else {
            if (this.y < y1) {
                if (this.rightChild != null)
                    this.rightChild.isAvailable(x, y, r);
            } else if (this.y > y2) {
                if (this.leftChild != null)
                    this.leftChild.isAvailable(x, y, r);
            } else {
                if (this.x >= x1 && this.x <= x2)
                    if (r * r >= ((x - this.x) * (x - this.x) + (y - this.y) * (y - this.y)))
                        System.out.println(this.bank);
                if (this.rightChild != null)
                    this.rightChild.isAvailable(x, y, r);
                if (this.leftChild != null)
                    this.leftChild.isAvailable(x, y, r);
            }
        }
    }
}