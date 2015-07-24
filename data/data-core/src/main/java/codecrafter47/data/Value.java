package codecrafter47.data;

public class Value<T> {
    private final String id;

    public static <T> Value<T> create(String id){
        return new Value<>(id);
    }

    private Value(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Value && getId().equals(((Value<?>)obj).getId());
    }

    @Override
    public String toString() {
        return getId();
    }
}
