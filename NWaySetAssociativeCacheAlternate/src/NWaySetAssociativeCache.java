import java.io.*;
import java.util.*;

public class NWaySetAssociativeCache {
    public static void main(String[] args) throws IOException {
        SetAssociativeCacheRunner.parseInput(System.in);
    }

    /**
     * Parses Test Case input to instantiate and invoke a SetAssociativeCache
     *
     * NOTE: You can typically ignore anything in here. Feel free to collapse...
     */
    static class SetAssociativeCacheRunner {
        public static void parseInput(InputStream inputStream) throws IOException {
            InputStreamReader inputReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputReader);

            String line;
            int lineCount = 0;
            SetAssociativeCache<String, String> cache = null;

            while (!isNullOrEmpty(line = reader.readLine())) {
                lineCount++;
                OutParam<String> replacementAlgoName = new OutParam<>();

                if (lineCount == 1) {

                    cache = createCache(line, replacementAlgoName);

                } else {

                    // All remaining lines invoke instance methods on the SetAssociativeCache
                    Object retValue = SetAssociativeCacheFactory.InvokeCacheMethod(line, cache);

                    // Write the method's return value (if any) to stdout
                    if (retValue != null) {
                        System.out.println(retValue);
                    }
                }
            }
        }
    }

    private static SetAssociativeCache<String, String> createCache(String inputLine, OutParam<String> replacementAlgoName) {
        String[] cacheParams = Arrays.stream(inputLine.split(",")).map(s -> s.trim()).toArray(n -> new String[n]);
        int setCount = Integer.parseInt(cacheParams[0]);
        int setSize = Integer.parseInt(cacheParams[1]);
        replacementAlgoName.value = cacheParams[2];
        return SetAssociativeCacheFactory.CreateStringCache(setCount, setSize, replacementAlgoName.value);
    }


    // ############################ BEGIN Solution Classes ############################

    /**
     * NOTE: You are free to modify anything below, except for class names and generic interface.
     * Other public interface changes may require updating one or more of the helper classes above
     * for test cases to run and pass.
     * <p>
     * A Set-Associative Cache data structure with fixed capacity.
     * <p>
     * - Data is structured into setCount # of setSize-sized sets.
     * - Every possible key is associated with exactly one set via a hashing algorithm
     * - If more items are added to a set than it has capacity for (i.e. > setSize items),
     *      a replacement victim is chosen from that set using an LRU algorithm.
     * <p>
     * NOTE: Part of the exercise is to allow for different kinds of replacement algorithms...
     */
    public static class SetAssociativeCache<TKey, TValue> {
        int Capacity;
        int SetSize;
        int SetCount;
        IReplacementAlgo<String, String> algo;
        Map<Integer, CacheSet<TKey, TValue>> Sets;

        public SetAssociativeCache(int setCount, int setSize, IReplacementAlgo<String, String> algorithm) {
            this.SetCount = setCount;
            this.SetSize = setSize;
            this.Capacity = this.SetCount * this.SetSize;
            this.algo = algorithm;
            // Initialize the sets
            this.Sets = Collections.synchronizedMap(new LinkedHashMap<Integer, CacheSet<TKey, TValue>>(setCount));
            for (int i =0; i < SetCount; i++)
            {
            	CacheSet<TKey, TValue> itemset = new CacheSet<TKey, TValue>(this.SetSize, this.algo);
                Sets.put(i, itemset);
            }
        }

        /** Gets the value associated with `key`. Throws if key not found. */
        public TValue get(TKey key) {
            int setIndex = this.getSetIndex(key);            
            CacheSet<TKey, TValue> set = this.Sets.get(setIndex);
            if (set != null ) {
                return set.get(key);
            }
            return null;
        }

        /**
         * Adds the `key` to the cache with the associated value, or overwrites the existing key.
         * If adding would exceed capacity, an existing key is chosen to replace using an LRU algorithm
         * (NOTE: It is part of this exercise to allow for more replacement algos)
         */
        public void set(TKey key, TValue value) {
            int setIndex = this.getSetIndex(key);
            CacheSet<TKey, TValue> itemset = this.Sets.get(setIndex);
            if (itemset != null) {
                itemset.set(key, value);
            }
            else {
            	if (this.Sets.size() > this.SetCount )
            	{
            		System.err.println("ERROR : index =>"+ setIndex);
            	}
            }
        }

        /** Returns the count of items in the cache */
        public int getCount() {
            int count = 0;
            for ( CacheSet<TKey, TValue> set : this.Sets.values() )
            {
                count += set.Store.size();
            }
            return count;
        }

        /** Returns `true` if the given `key` is present in the set; otherwise, `false`. */
        public boolean containsKey(TKey key) {
            int setIndex = this.getSetIndex(key);
            CacheSet<TKey, TValue> set = this.Sets.get(setIndex);
            if (set != null)            
                return set.containsKey(key);
            else
                return false;
        }

        /** Maps a key to a set */
        private int getSetIndex(TKey key) {
            int s = key.hashCode() % this.SetCount;
            return s;
        }
    }

    /**
     * An internal data structure representing one set in a N-Way Set-Associative Cache
     */
    static class CacheSet<TKey, TValue> {
        int Capacity;
        Map<TKey, CacheItem<TKey, TValue>> Store;
        IReplacementAlgo<String, String> lruAlgorithm;
        public int Count;


        public CacheSet(int capacity, IReplacementAlgo<String, String> algorithm) {
            this.Capacity = capacity;
            this.lruAlgorithm = algorithm;
            this.Store = Collections.synchronizedMap(new LinkedHashMap<TKey, CacheItem<TKey, TValue>>(Capacity));
        }

        /** Gets the value associated with `key`. Throws if key not found. */
        public TValue get(TKey key) {
            // If the key is present, update the usage tracker
            CacheItem<TKey, TValue> item = Store.get(key);
            if (item != null) {
                this.recordUsage(key, item);
            } else {
                throw new RuntimeException(String.format("The key '%s' was not found", key));
            }

            return item.value;
        }

        public List<CacheItem<TKey, TValue>> getBlocks() {
            return Collections.synchronizedList(new ArrayList<CacheItem<TKey, TValue>>(Store.values()));
        }

        /**
         * Adds the `key` to the cache with the associated value, or overwrites the existing key.
         * If adding would exceed capacity, an existing key is chosen to replace using an LRU algorithm
         * (NOTE: It is part of this exercise to allow for more replacement algos)
         */
        public void set(TKey key, TValue value) {
            CacheItem<TKey, TValue> item = Store.get(key);
            if (item != null) {
                item.value = value;
                this.recordUsage(key,item);   
                return;             
            } else {
                // If the set is at it's capacity
                if (this.Store.size() == this.Capacity) {
                    lruAlgorithm.replacementAlgotithm((CacheSet<String, String>) this);
                } 
                Store.put(key, new CacheItem(key, value));
                this.Count++;

            }
        }

        /** Returns `true` if the given `key` is present in the set; otherwise, `false`. */
        public boolean containsKey(TKey key) {
            return Store.get(key)!=null;
        }

        private void removeKey(TKey key) {
            Store.remove(key);
            this.Count--;
        }


        private void recordUsage(TKey key, CacheItem<TKey, TValue> value) {
            this.Store.remove(key);
            this.Store.put(key, value);
        }
    }

    /**
     * An internal data structure representing a single item in an N-Way Set-Associative Cache
     */
    static class CacheItem<TKey, TValue> {
        public TKey key;
        public TValue value;

        public CacheItem(TKey key, TValue value) {
            this.key = key;
            this.value = value;
        }
    }

    public final static String LruAlgorithm = "LRUReplacementAlgo";
    public final static String MruAlgorithm = "MRUReplacementAlgo";

    /**
     * A common interface for replacement algos, which decide which item in a CacheSet to evict
     */
    interface IReplacementAlgo<TKey, TValue> {
        void replacementAlgotithm(CacheSet<TKey, TValue> cacheSet);
     }

    static class LRUReplacementAlgo<TKey, TValue> implements IReplacementAlgo<TKey, TValue> {

        @Override
        public void replacementAlgotithm(CacheSet<TKey, TValue> cacheSet) {
            List<CacheItem<TKey, TValue>> blocks = cacheSet.getBlocks();
            CacheItem<TKey, TValue> itemToRemove = blocks.get(0);
            cacheSet.Store.remove(itemToRemove.key);
            cacheSet.Count--;
        }
    }

    static class MRUReplacementAlgo<TKey, TValue> implements IReplacementAlgo<TKey, TValue> {
        @Override
        public void replacementAlgotithm(CacheSet<TKey, TValue> cacheSet) {
            List<CacheItem<TKey, TValue>> blocks = cacheSet.getBlocks();
            CacheItem<TKey, TValue> itemToRemove = blocks.get(blocks.size()-1);
            cacheSet.Store.remove(itemToRemove.key);
            cacheSet.Count--;
        }
    }

    // ############################ BEGIN Helper Classes ############################
    // NOTE: Your code in the classes below will not be evaluated as part of the exericse.
    // They are just used by the stub code in the header to help run HackerRank test cases.
    // You may need to make small modifications to these classes, depending on your interface design,
    // for tests to run and pass, but it is not a core part of the exercise
    //
    static class OutParam<T> {
        public T value;
    }

    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static class SetAssociativeCacheFactory {
        /// NOTE: replacementAlgoName is provided in case you need it here. Whether you do will depend on your interface design.
        public static SetAssociativeCache<String, String> CreateStringCache(int setCount, int setSize, String replacementAlgoName) {
            ReplacementAlgoFactory<String, String> algoFactory = new ReplacementAlgoFactory<String, String>();
            IReplacementAlgo<String, String> algorithm = algoFactory.createReplacementAlgo(replacementAlgoName);
            return new SetAssociativeCache<>(setCount, setSize, algorithm);
        }

        /// NOTE: Modify only if you change the main interface of SetAssociativeCache
        public static Object InvokeCacheMethod(String inputLine, SetAssociativeCache<String, String> cacheInstance) {
            String[] callArgs = Arrays.stream(inputLine.split(",", -1)).map(a -> a.trim()).toArray(n -> new String[n]);

            String methodName = callArgs[0].toLowerCase();
            //String[] callParams = Arrays.copyOfRange(callArgs, 1, callArgs.length - 1); // TODO: This is unused

            switch (methodName) {
                case "get":
                    return cacheInstance.get(callArgs[1]);
                case "set":
                    cacheInstance.set(callArgs[1], callArgs[2]);
                    return null;
                case "containskey":
                    return cacheInstance.containsKey(callArgs[1]);
                case "getcount":
                    return cacheInstance.getCount();

                // TODO: If you want to add and test other public methods to SetAssociativeCache,
                //  add them to the switch statement here... (this is not common)

                default:
                    throw new RuntimeException(String.format("Unknown method name '{%s}'", methodName));
            }
        }
    }

    // TODO: Consider making use of this in the `SetAssociativeCacheFactory` above to map replacement algo name 
    // to a IReplacementAlgo instance for the interface you design
    public static class ReplacementAlgoFactory<TKey, TValue> {
        public IReplacementAlgo<TKey, TValue> createReplacementAlgo(String replacementAlgoName) {
            switch (replacementAlgoName) {
                case LruAlgorithm:
                    return new LRUReplacementAlgo<TKey, TValue>();
                case MruAlgorithm:
                    return new MRUReplacementAlgo<TKey, TValue>();
                default:
                    throw new RuntimeException(String.format("Unknown replacement algo '%s'", replacementAlgoName));
            }
        }
    }

    // ^^ ######################### END Helper Classes ######################### ^^

}