public class neighborhood {
    public String name;
    public float x1, x2, y1, y2;

    public neighborhood(String name, float x1, float x2, float y1, float y2) {
        this.name = name;
        if (x1 <= x2) {
            this.x1 = x1;
            this.x2 = x2;
        } else {
            this.x1 = x2;
            this.x2 = x1;
        }
        if (y1 <= y2) {
            this.y1 = y1;
            this.y2 = y2;
        } else {
            this.y1 = y2;
            this.y2 = y1;
        }
    }
}

class Map {
    public int size = 0;
    public int biggestSize = 100;
    public String[] keys;
    public neighborhood[] values;

    public Map() {
        keys = new String[biggestSize];
        values = new neighborhood[biggestSize];
    }

    public void put(String name, neighborhood neighborhood) {
        keys[size] = name;
        values[size] = neighborhood;
        ++size;

        //check if the array is full:
        if (size == biggestSize) {
            biggestSize = biggestSize * 2;

            String[] temp_keys = new String[size];
            neighborhood[] temp_values = new neighborhood[size];
            for (int i = 0; i < size; ++i) {
                temp_keys[i] = keys[i];
                temp_values[i] = values[i];
            }

            keys = new String[biggestSize];
            values = new neighborhood[biggestSize];
            for (int i = 0; i < size; ++i) {
                keys[i] = temp_keys[i];
                values[i] = temp_values[i];
            }
        }
    }

    public neighborhood get(String name) {
        for (int i = 0; i < size; ++i) {
            if (keys[i].equals(name))
                return values[i];
        }
        return null;
    }
}