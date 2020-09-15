import local.algorithms.nwaycache.*;

/* The algorithm works this way
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

public class Test {

    public static void main(String[] args) {

    	// Parameters nways, cacheCapacity, blockSize, true for LRU false for MRU
    	// we are creating 2 ways set associative cache with cache capacity of 64 elements and a block capacity of 8 elements, using LRU
        NWayCache<Integer, String> lruSetAssociativeCache = new NWaysSetAssociativeCache<>(2, 64, 8, true);

        System.out.println("Put() Test -- WRITING 10 ITEMS TO AN LRU CACHE WITH 8 BLOCKS AND 8 WAY (LRU)");

        // LRU TEST 
        for (int i = 0; i < 64; i++) {
            System.out.println("lruSetAssociativeCache.put(" + i + ", " + i + "): ");
            lruSetAssociativeCache.put(i, "Advertisement "+ i);
        }

        System.out.println();
        System.out.println("Get() Test -- READING 100 ITEMS FROM AN LRU CACHE WITH 8 BLOCKS AND 8 WAY, first 64 (0-63) will succeed and remaining will miss will print null (LRU)");

        for (int i = 0; i < 100; i++) {
            System.out.println("lruSetAssociativeCache.get(" + i + "): " + lruSetAssociativeCache.get(i));
        }
        
        lruSetAssociativeCache.clear();
        System.out.println();
        System.out.println("After Clear() -- READING 100 ITEMS FROM AN LRU CACHE WITH 8 BLOCKS AND 8 WAY, first 64 (0-63) will succeed and remaining will miss will print null (LRU)");

        for (int i = 0; i < 100; i++) {
            System.out.println("lruSetAssociativeCache.get(" + i + "): " + lruSetAssociativeCache.get(i));
        }
        
        
    	// Parameters nways, cacheCapacity, blockSize, true for LRU false for MRU
        NWayCache<Integer, String> mruSetAssociativeCache = new NWaysSetAssociativeCache<>(2, 64, 8, false);

        System.out.println("Put() Test -- WRITING 64 ITEMS TO AN MRU CACHE WITH 8 BLOCKS AND 8 WAY (MRU)");

        // MRU TEST 
        
        for (int i = 0; i < 64; i++) {
            System.out.println("mruSetAssociativeCache.put(" + i + ", " + i + "): ");
            mruSetAssociativeCache.put(i, "Advertisement "+ i);
        }

        System.out.println("Get() Test -- READING 100 ITEMS FROM AN MRU CACHE WITH 8 BLOCKS AND 8 WAY, first 64 (0-63) will succeed and remaining will miss will print null (MRU)");

        for (int i = 0; i < 100; i++) {
            System.out.println("mruSetAssociativeCache.get(" + i + "): " + mruSetAssociativeCache.get(i));
        }       

        mruSetAssociativeCache.clear();
        System.out.println();
        System.out.println("After Clear() -- READING 100 ITEMS FROM AN LRU CACHE WITH 8 BLOCKS AND 8 WAY, first 64 (0-63) will succeed and remaining will miss will print null (MRU)");

        for (int i = 0; i < 100; i++) {
            System.out.println("mruSetAssociativeCache.get(" + i + "): " + mruSetAssociativeCache.get(i));
        }
        
    }
}
