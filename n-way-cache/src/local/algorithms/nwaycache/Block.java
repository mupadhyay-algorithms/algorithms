package local.algorithms.nwaycache;

/* The algorithm works this way
 * interfaces:
 * 		NWayCache
 * 		LeastRecentlyUsed
 * 		MostRecentlyUsed
 * 
 * the key is used to identify set and then the value from each set
 * the set id is identified by using getting a hashcode from the key value.
 * if key is a class then class mush implement hashcode method. 
 * 		the set id is identified by using mod operator with nways parameters
 *  set size is calculated using (cacheeCapacity/(blockSize*nways) -- passed as parameter to NWaysSetAssociativeCache
 *  here if n ways then part of n is included in blocks, for example if cache size is 64, block size is 8 and n is 2 ways then each set will be of 16 elements, and set size is 4, each set will contain 16 blocks (blocks * n)
 *  
 *  Optimization:
 * 		The important part is to access the keys within each block and identifying set
 *			LinkedList will be fast to add and insert at random location, but element access can be O(N)
 *			Arrays will be fast to access elements randomly but to add and remove -- used in updating cache will be O(N)
 *			Hence using java's LinkedHashMap, we could have written or used SkipLists for O(log n) access 
 *				or we could have used ArrayList in combination with LinkedList, getting best of both worlds thus increasing space complexity but this is being cache application and accessing elements is very important so space complexity is negligible.
 *		The research shows if block size is increased and total number of sets increased it will reduce the missess.
 *
 *	Code Architecture
 *
 *	NWaysSetAssociativeCache --> uses BlockSet to manage blocks using LinkedHashMap
 *	BlockSet ---> uses Block which uses LinkedHashMap for each block
 *  Test --> is the driver code to use NWaysSetAssociativeCache
 *  
 * Problem Areas:
 * 	if key is other than integer we need a better hashing algorithm that doesn't collide hashing			
 */

class Block<K, V> {

    private final K key;
    private final V value;

    public Block(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public V getValue() {
        return value;
    }

    public K getKey() {
        return key;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Block) {
            Block<K, V> that = (Block<K, V>) object;
            return this.key.equals(that.key) &&
                   this.value.equals(that.value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 31 + key.hashCode(); //31
        hash = hash * 13 + value.hashCode(); //13
        return hash;
    }

    @Override
    public String toString() {
        return "Block(" + key + ", " + value + ")";
    }

}