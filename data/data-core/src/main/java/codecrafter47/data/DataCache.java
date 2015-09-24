package codecrafter47.data;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class DataCache {
    private final Map<Value<?>, Object> cache = new ConcurrentHashMap<>();

    public <T> void updateValue(Value<T> value, T object) {
        if (object == null) {
            cache.remove(value);
        } else {
            cache.put(value, object);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<T> getValue(Value<T> value) {
        return Optional.ofNullable((T) cache.get(value));
    }

    public Map<Value<?>, Object> getMap() {
        return cache;
    }
}
