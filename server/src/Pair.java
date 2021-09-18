package src;

public class Pair {
    String key;
    String value;

    public Pair(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() { return key; }
    public String getValue() { return value; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair that = (Pair) o;
        return getKey().equalsIgnoreCase(that.getKey())
                && getValue().equalsIgnoreCase(that.getValue());
    }
}

//        for (Pair p: query) {
//            System.out.println(" - " + p.getKey() + ":" + p.getValue() );
//        }
//        LOGGER.info( "--- response done --- ");
//        return "[" + response +"]" ;