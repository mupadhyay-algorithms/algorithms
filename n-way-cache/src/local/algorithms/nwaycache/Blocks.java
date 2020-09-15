package local.algorithms.nwaycache;

import java.util.*;


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

class Blocks<K, V> implements 	LeastRecentlyUsed<K, V>,
								MostRecentlyUsed<K, V>
{

    private final Map<K, Block<K, V>> blocks;
    private final int blockSetCapacity;
    private final boolean isLRU;

    public Blocks(int blockSetCapacity, boolean isLRU) {
        if (blockSetCapacity < 1) {
            throw new RuntimeException("BlockSet capacity cannot be less than one (1)");
        }
        this.blockSetCapacity = blockSetCapacity;
        this.isLRU = isLRU;
        this.blocks = Collections.synchronizedMap(new LinkedHashMap<K, Block<K, V>>(blockSetCapacity));
        
    }

    public Block<K, V> getBlock(K key) {
        Block<K, V> block = blocks.get(key);
        if (block != null) {
            updateLastUsed(key, block);
        }
        return block;
    }

    public void updateBlock(K key, V value) {
        Block<K, V> block = blocks.get(key);
        if (block != null || blocks.size() >=blockSetCapacity ) {
        	if (block != null) {
	            updateLastUsed(key, block);
	            return;
        	}
        	if (blocks.size() >=blockSetCapacity) {
        		if (isLRU) {
        			LRU();
        		}
        		else {
        			MRU();
        		}
        	}
        }
        blocks.put(key, new Block<K, V>(key, value));
    }

    private void updateLastUsed(K key, Block<K, V> block) {
        blocks.remove(key);
        blocks.put(key, block);
    }

    public List<Block<K, V>> getBlocks() {
        return Collections.synchronizedList(new ArrayList<Block<K, V>>(blocks.values()));
    }

    public boolean isFull() {
        return blocks.size() == blockSetCapacity;
    }

    public void remove(Block<K, V> block) {
        blocks.remove(block.getKey());
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Blocks) {
            Blocks<K, V> that = (Blocks<K, V>) object;
            return this.blocks.equals(that.blocks) &&
                   this.blockSetCapacity == that.blockSetCapacity;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = blocks.hashCode();
        return hash;
    }

    @Override
    public String toString() {
        return "BlockSet(" + blocks + ", " + blockSetCapacity + ")";
    }
    
    @Override
    public Block<K, V> LRU() {
        Block<K, V> leastRecentlyUsedBlock = getBlocks().get(0);
        blocks.remove(leastRecentlyUsedBlock.getKey());
        return leastRecentlyUsedBlock;    	
    }
    
    @Override
    public Block<K, V> MRU() {
    	int sizeOfMap = getBlocks().size();    	
        Block<K, V> mostRecentlyUsedBlock = getBlocks().get(sizeOfMap-1);
        blocks.remove(mostRecentlyUsedBlock.getKey());
        return mostRecentlyUsedBlock;    	
    }
    

}
