package codecrafter47.data;

import javax.inject.Inject;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class DataAggregator<B> {
    protected final Logger logger;
    private final Map<Value<?>, Function<B, ?>> valueProviderMap = new HashMap<>();

    @Inject
    public DataAggregator(Logger logger) {
        this.logger = logger;
    }

    protected <V> void bind(Value<V> valueClass, Function<B, V> provider){
        valueProviderMap.put(valueClass, provider);
    }

    private final class ExceptionSafeProvider<T, R> implements Function<T, R>{
        private final Function<T, R> function;

        private ExceptionSafeProvider(Function<T, R> function) {
            this.function = function;
        }

        @Override
        public R apply(T t) {
            try {
                return function.apply(t);
            } catch(Throwable th){
                logger.log(Level.SEVERE, "Failed to invoke " + function.getClass().getName() + ".apply()", th);
            }
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public <V> Optional<V> getValue(Value<V> type, B context){
        try {
            return Optional.ofNullable(valueProviderMap.get(type)).map(provider -> (V) provider.apply(context));
        } catch(Throwable th){
            logger.log(Level.SEVERE, "Unexpected exception", th);
        }
        return Optional.empty();
    }

    public boolean provides(Value<?> type){
        return valueProviderMap.containsKey(type);
    }

    public Set<? extends Value<?>> getAvailableValueTypes(){
        return Collections.unmodifiableSet(valueProviderMap.keySet());
    }
}
