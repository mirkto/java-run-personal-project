package src;

public class Pair {
    String key;
    String value;

    //------------- constructors -------------
    public Pair(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public Pair(String[] str) throws manyArgumentsException {
        if (str.length != 2) {
            throw new manyArgumentsException("wrong numbers of arguments!");
        }
        this.key = str[0];
        this.value = str[1];
    }

    //------------- getters and setters -------------
    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    //------------- methods -------------
    @Override
    public String toString() {
        return "\"" + key + ":\"" + value + "\"";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair that = (Pair) o;
        return getKey().equalsIgnoreCase(that.getKey()) &&
                getValue().equalsIgnoreCase(that.getValue());
    }

    class manyArgumentsException extends Exception {
        public manyArgumentsException(String msg) {
            super(msg);
        }
    }
}
