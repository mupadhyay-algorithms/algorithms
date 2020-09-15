 The algorithm works this way
 
 interfaces:
 		NWayCache
 		LeastRecentlyUsed
 		MostRecentlyUsed
 
 the key is used to identify set and then the value from each set
 the set id is identified by using getting a hashcode from the key value.
 if key is a class then class mush implement hashcode method. 
 		the set id is identified by using mod operator with nways parameters
  set size is calculated using (cacheeCapacity/(blockSize*nways) -- passed as parameter to NWaysSetAssociativeCache
  here if n ways then part of n is included in blocks, for example if cache size is 64, block size is 8 and n is 2 ways then each set will be of 16 elements, and set size is 4, each set will contain 16 blocks (blocks * n)
  
  Optimization:
 		The important part is to access the keys within each block and identifying set
			LinkedList will be fast to add and insert at random location, but element access can be O(N)
			Arrays will be fast to access elements randomly but to add and remove -- used in updating cache will be O(N)
			Hence using java's LinkedHashMap, we could have written or used SkipLists for O(log n) access 
				or we could have used ArrayList in combination with LinkedList, getting best of both worlds thus increasing space complexity but this is being cache application and accessing elements is very important so space complexity is negligible.
		The research shows if block size is increased and total number of sets increased it will reduce the missess.

	Code Architecture

	NWaysSetAssociativeCache --> uses BlockSet to manage blocks using LinkedHashMap
	BlockSet ---> uses Block which uses LinkedHashMap for each block
  Test --> is the driver code to use NWaysSetAssociativeCache   			

  Problem Areas:
  	if key is other than integer we need a better hashing algorithm that doesn't collide hashing			


Test results:
	Put() Test -- WRITING 10 ITEMS TO AN LRU CACHE WITH 8 BLOCKS AND 8 WAY (LRU)
		lruSetAssociativeCache.put(0, 0): 
		lruSetAssociativeCache.put(1, 1): 
		lruSetAssociativeCache.put(2, 2): 
		lruSetAssociativeCache.put(3, 3): 
		lruSetAssociativeCache.put(4, 4): 
		lruSetAssociativeCache.put(5, 5): 
		lruSetAssociativeCache.put(6, 6): 
		lruSetAssociativeCache.put(7, 7): 
		lruSetAssociativeCache.put(8, 8): 
		lruSetAssociativeCache.put(9, 9): 
		lruSetAssociativeCache.put(10, 10): 
		lruSetAssociativeCache.put(11, 11): 
		lruSetAssociativeCache.put(12, 12): 
		lruSetAssociativeCache.put(13, 13): 
		lruSetAssociativeCache.put(14, 14): 
		lruSetAssociativeCache.put(15, 15): 
		lruSetAssociativeCache.put(16, 16): 
		lruSetAssociativeCache.put(17, 17): 
		lruSetAssociativeCache.put(18, 18): 
		lruSetAssociativeCache.put(19, 19): 
		lruSetAssociativeCache.put(20, 20): 
		lruSetAssociativeCache.put(21, 21): 
		lruSetAssociativeCache.put(22, 22): 
		lruSetAssociativeCache.put(23, 23): 
		lruSetAssociativeCache.put(24, 24): 
		lruSetAssociativeCache.put(25, 25): 
		lruSetAssociativeCache.put(26, 26): 
		lruSetAssociativeCache.put(27, 27): 
		lruSetAssociativeCache.put(28, 28): 
		lruSetAssociativeCache.put(29, 29): 
		lruSetAssociativeCache.put(30, 30): 
		lruSetAssociativeCache.put(31, 31): 
		lruSetAssociativeCache.put(32, 32): 
		lruSetAssociativeCache.put(33, 33): 
		lruSetAssociativeCache.put(34, 34): 
		lruSetAssociativeCache.put(35, 35): 
		lruSetAssociativeCache.put(36, 36): 
		lruSetAssociativeCache.put(37, 37): 
		lruSetAssociativeCache.put(38, 38): 
		lruSetAssociativeCache.put(39, 39): 
		lruSetAssociativeCache.put(40, 40): 
		lruSetAssociativeCache.put(41, 41): 
		lruSetAssociativeCache.put(42, 42): 
		lruSetAssociativeCache.put(43, 43): 
		lruSetAssociativeCache.put(44, 44): 
		lruSetAssociativeCache.put(45, 45): 
		lruSetAssociativeCache.put(46, 46): 
		lruSetAssociativeCache.put(47, 47): 
		lruSetAssociativeCache.put(48, 48): 
		lruSetAssociativeCache.put(49, 49): 
		lruSetAssociativeCache.put(50, 50): 
		lruSetAssociativeCache.put(51, 51): 
		lruSetAssociativeCache.put(52, 52): 
		lruSetAssociativeCache.put(53, 53): 
		lruSetAssociativeCache.put(54, 54): 
		lruSetAssociativeCache.put(55, 55): 
		lruSetAssociativeCache.put(56, 56): 
		lruSetAssociativeCache.put(57, 57): 
		lruSetAssociativeCache.put(58, 58): 
		lruSetAssociativeCache.put(59, 59): 
		lruSetAssociativeCache.put(60, 60): 
		lruSetAssociativeCache.put(61, 61): 
		lruSetAssociativeCache.put(62, 62): 
		lruSetAssociativeCache.put(63, 63): 
		
	Get() Test -- READING 100 ITEMS FROM AN LRU CACHE WITH 8 BLOCKS AND 8 WAY, first 64 (0-63) will succeed and remaining will miss will print null (LRU)
		lruSetAssociativeCache.get(0): Advertisement 0
		lruSetAssociativeCache.get(1): Advertisement 1
		lruSetAssociativeCache.get(2): Advertisement 2
		lruSetAssociativeCache.get(3): Advertisement 3
		lruSetAssociativeCache.get(4): Advertisement 4
		lruSetAssociativeCache.get(5): Advertisement 5
		lruSetAssociativeCache.get(6): Advertisement 6
		lruSetAssociativeCache.get(7): Advertisement 7
		lruSetAssociativeCache.get(8): Advertisement 8
		lruSetAssociativeCache.get(9): Advertisement 9
		lruSetAssociativeCache.get(10): Advertisement 10
		lruSetAssociativeCache.get(11): Advertisement 11
		lruSetAssociativeCache.get(12): Advertisement 12
		lruSetAssociativeCache.get(13): Advertisement 13
		lruSetAssociativeCache.get(14): Advertisement 14
		lruSetAssociativeCache.get(15): Advertisement 15
		lruSetAssociativeCache.get(16): Advertisement 16
		lruSetAssociativeCache.get(17): Advertisement 17
		lruSetAssociativeCache.get(18): Advertisement 18
		lruSetAssociativeCache.get(19): Advertisement 19
		lruSetAssociativeCache.get(20): Advertisement 20
		lruSetAssociativeCache.get(21): Advertisement 21
		lruSetAssociativeCache.get(22): Advertisement 22
		lruSetAssociativeCache.get(23): Advertisement 23
		lruSetAssociativeCache.get(24): Advertisement 24
		lruSetAssociativeCache.get(25): Advertisement 25
		lruSetAssociativeCache.get(26): Advertisement 26
		lruSetAssociativeCache.get(27): Advertisement 27
		lruSetAssociativeCache.get(28): Advertisement 28
		lruSetAssociativeCache.get(29): Advertisement 29
		lruSetAssociativeCache.get(30): Advertisement 30
		lruSetAssociativeCache.get(31): Advertisement 31
		lruSetAssociativeCache.get(32): Advertisement 32
		lruSetAssociativeCache.get(33): Advertisement 33
		lruSetAssociativeCache.get(34): Advertisement 34
		lruSetAssociativeCache.get(35): Advertisement 35
		lruSetAssociativeCache.get(36): Advertisement 36
		lruSetAssociativeCache.get(37): Advertisement 37
		lruSetAssociativeCache.get(38): Advertisement 38
		lruSetAssociativeCache.get(39): Advertisement 39
		lruSetAssociativeCache.get(40): Advertisement 40
		lruSetAssociativeCache.get(41): Advertisement 41
		lruSetAssociativeCache.get(42): Advertisement 42
		lruSetAssociativeCache.get(43): Advertisement 43
		lruSetAssociativeCache.get(44): Advertisement 44
		lruSetAssociativeCache.get(45): Advertisement 45
		lruSetAssociativeCache.get(46): Advertisement 46
		lruSetAssociativeCache.get(47): Advertisement 47
		lruSetAssociativeCache.get(48): Advertisement 48
		lruSetAssociativeCache.get(49): Advertisement 49
		lruSetAssociativeCache.get(50): Advertisement 50
		lruSetAssociativeCache.get(51): Advertisement 51
		lruSetAssociativeCache.get(52): Advertisement 52
		lruSetAssociativeCache.get(53): Advertisement 53
		lruSetAssociativeCache.get(54): Advertisement 54
		lruSetAssociativeCache.get(55): Advertisement 55
		lruSetAssociativeCache.get(56): Advertisement 56
		lruSetAssociativeCache.get(57): Advertisement 57
		lruSetAssociativeCache.get(58): Advertisement 58
		lruSetAssociativeCache.get(59): Advertisement 59
		lruSetAssociativeCache.get(60): Advertisement 60
		lruSetAssociativeCache.get(61): Advertisement 61
		lruSetAssociativeCache.get(62): Advertisement 62
		lruSetAssociativeCache.get(63): Advertisement 63
		lruSetAssociativeCache.get(64): null
		lruSetAssociativeCache.get(65): null
		lruSetAssociativeCache.get(66): null
		lruSetAssociativeCache.get(67): null
		lruSetAssociativeCache.get(68): null
		lruSetAssociativeCache.get(69): null
		lruSetAssociativeCache.get(70): null
		lruSetAssociativeCache.get(71): null
		lruSetAssociativeCache.get(72): null
		lruSetAssociativeCache.get(73): null
		lruSetAssociativeCache.get(74): null
		lruSetAssociativeCache.get(75): null
		lruSetAssociativeCache.get(76): null
		lruSetAssociativeCache.get(77): null
		lruSetAssociativeCache.get(78): null
		lruSetAssociativeCache.get(79): null
		lruSetAssociativeCache.get(80): null
		lruSetAssociativeCache.get(81): null
		lruSetAssociativeCache.get(82): null
		lruSetAssociativeCache.get(83): null
		lruSetAssociativeCache.get(84): null
		lruSetAssociativeCache.get(85): null
		lruSetAssociativeCache.get(86): null
		lruSetAssociativeCache.get(87): null
		lruSetAssociativeCache.get(88): null
		lruSetAssociativeCache.get(89): null
		lruSetAssociativeCache.get(90): null
		lruSetAssociativeCache.get(91): null
		lruSetAssociativeCache.get(92): null
		lruSetAssociativeCache.get(93): null
		lruSetAssociativeCache.get(94): null
		lruSetAssociativeCache.get(95): null
		lruSetAssociativeCache.get(96): null
		lruSetAssociativeCache.get(97): null
		lruSetAssociativeCache.get(98): null
		lruSetAssociativeCache.get(99): null
		
	After Clear() -- READING 100 ITEMS FROM AN LRU CACHE WITH 8 BLOCKS AND 8 WAY, first 64 (0-63) will succeed and remaining will miss will print null (LRU)
		lruSetAssociativeCache.get(0): null
		lruSetAssociativeCache.get(1): null
		lruSetAssociativeCache.get(2): null
		lruSetAssociativeCache.get(3): null
		lruSetAssociativeCache.get(4): null
		lruSetAssociativeCache.get(5): null
		lruSetAssociativeCache.get(6): null
		lruSetAssociativeCache.get(7): null
		lruSetAssociativeCache.get(8): null
		lruSetAssociativeCache.get(9): null
		lruSetAssociativeCache.get(10): null
		lruSetAssociativeCache.get(11): null
		lruSetAssociativeCache.get(12): null
		lruSetAssociativeCache.get(13): null
		lruSetAssociativeCache.get(14): null
		lruSetAssociativeCache.get(15): null
		lruSetAssociativeCache.get(16): null
		lruSetAssociativeCache.get(17): null
		lruSetAssociativeCache.get(18): null
		lruSetAssociativeCache.get(19): null
		lruSetAssociativeCache.get(20): null
		lruSetAssociativeCache.get(21): null
		lruSetAssociativeCache.get(22): null
		lruSetAssociativeCache.get(23): null
		lruSetAssociativeCache.get(24): null
		lruSetAssociativeCache.get(25): null
		lruSetAssociativeCache.get(26): null
		lruSetAssociativeCache.get(27): null
		lruSetAssociativeCache.get(28): null
		lruSetAssociativeCache.get(29): null
		lruSetAssociativeCache.get(30): null
		lruSetAssociativeCache.get(31): null
		lruSetAssociativeCache.get(32): null
		lruSetAssociativeCache.get(33): null
		lruSetAssociativeCache.get(34): null
		lruSetAssociativeCache.get(35): null
		lruSetAssociativeCache.get(36): null
		lruSetAssociativeCache.get(37): null
		lruSetAssociativeCache.get(38): null
		lruSetAssociativeCache.get(39): null
		lruSetAssociativeCache.get(40): null
		lruSetAssociativeCache.get(41): null
		lruSetAssociativeCache.get(42): null
		lruSetAssociativeCache.get(43): null
		lruSetAssociativeCache.get(44): null
		lruSetAssociativeCache.get(45): null
		lruSetAssociativeCache.get(46): null
		lruSetAssociativeCache.get(47): null
		lruSetAssociativeCache.get(48): null
		lruSetAssociativeCache.get(49): null
		lruSetAssociativeCache.get(50): null
		lruSetAssociativeCache.get(51): null
		lruSetAssociativeCache.get(52): null
		lruSetAssociativeCache.get(53): null
		lruSetAssociativeCache.get(54): null
		lruSetAssociativeCache.get(55): null
		lruSetAssociativeCache.get(56): null
		lruSetAssociativeCache.get(57): null
		lruSetAssociativeCache.get(58): null
		lruSetAssociativeCache.get(59): null
		lruSetAssociativeCache.get(60): null
		lruSetAssociativeCache.get(61): null
		lruSetAssociativeCache.get(62): null
		lruSetAssociativeCache.get(63): null
		lruSetAssociativeCache.get(64): null
		lruSetAssociativeCache.get(65): null
		lruSetAssociativeCache.get(66): null
		lruSetAssociativeCache.get(67): null
		lruSetAssociativeCache.get(68): null
		lruSetAssociativeCache.get(69): null
		lruSetAssociativeCache.get(70): null
		lruSetAssociativeCache.get(71): null
		lruSetAssociativeCache.get(72): null
		lruSetAssociativeCache.get(73): null
		lruSetAssociativeCache.get(74): null
		lruSetAssociativeCache.get(75): null
		lruSetAssociativeCache.get(76): null
		lruSetAssociativeCache.get(77): null
		lruSetAssociativeCache.get(78): null
		lruSetAssociativeCache.get(79): null
		lruSetAssociativeCache.get(80): null
		lruSetAssociativeCache.get(81): null
		lruSetAssociativeCache.get(82): null
		lruSetAssociativeCache.get(83): null
		lruSetAssociativeCache.get(84): null
		lruSetAssociativeCache.get(85): null
		lruSetAssociativeCache.get(86): null
		lruSetAssociativeCache.get(87): null
		lruSetAssociativeCache.get(88): null
		lruSetAssociativeCache.get(89): null
		lruSetAssociativeCache.get(90): null
		lruSetAssociativeCache.get(91): null
		lruSetAssociativeCache.get(92): null
		lruSetAssociativeCache.get(93): null
		lruSetAssociativeCache.get(94): null
		lruSetAssociativeCache.get(95): null
		lruSetAssociativeCache.get(96): null
		lruSetAssociativeCache.get(97): null
		lruSetAssociativeCache.get(98): null
		lruSetAssociativeCache.get(99): null

	Put() Test -- WRITING 64 ITEMS TO AN MRU CACHE WITH 8 BLOCKS AND 8 WAY (MRU)
		mruSetAssociativeCache.put(0, 0): 
		mruSetAssociativeCache.put(1, 1): 
		mruSetAssociativeCache.put(2, 2): 
		mruSetAssociativeCache.put(3, 3): 
		mruSetAssociativeCache.put(4, 4): 
		mruSetAssociativeCache.put(5, 5): 
		mruSetAssociativeCache.put(6, 6): 
		mruSetAssociativeCache.put(7, 7): 
		mruSetAssociativeCache.put(8, 8): 
		mruSetAssociativeCache.put(9, 9): 
		mruSetAssociativeCache.put(10, 10): 
		mruSetAssociativeCache.put(11, 11): 
		mruSetAssociativeCache.put(12, 12): 
		mruSetAssociativeCache.put(13, 13): 
		mruSetAssociativeCache.put(14, 14): 
		mruSetAssociativeCache.put(15, 15): 
		mruSetAssociativeCache.put(16, 16): 
		mruSetAssociativeCache.put(17, 17): 
		mruSetAssociativeCache.put(18, 18): 
		mruSetAssociativeCache.put(19, 19): 
		mruSetAssociativeCache.put(20, 20): 
		mruSetAssociativeCache.put(21, 21): 
		mruSetAssociativeCache.put(22, 22): 
		mruSetAssociativeCache.put(23, 23): 
		mruSetAssociativeCache.put(24, 24): 
		mruSetAssociativeCache.put(25, 25): 
		mruSetAssociativeCache.put(26, 26): 
		mruSetAssociativeCache.put(27, 27): 
		mruSetAssociativeCache.put(28, 28): 
		mruSetAssociativeCache.put(29, 29): 
		mruSetAssociativeCache.put(30, 30): 
		mruSetAssociativeCache.put(31, 31): 
		mruSetAssociativeCache.put(32, 32): 
		mruSetAssociativeCache.put(33, 33): 
		mruSetAssociativeCache.put(34, 34): 
		mruSetAssociativeCache.put(35, 35): 
		mruSetAssociativeCache.put(36, 36): 
		mruSetAssociativeCache.put(37, 37): 
		mruSetAssociativeCache.put(38, 38): 
		mruSetAssociativeCache.put(39, 39): 
		mruSetAssociativeCache.put(40, 40): 
		mruSetAssociativeCache.put(41, 41): 
		mruSetAssociativeCache.put(42, 42): 
		mruSetAssociativeCache.put(43, 43): 
		mruSetAssociativeCache.put(44, 44): 
		mruSetAssociativeCache.put(45, 45): 
		mruSetAssociativeCache.put(46, 46): 
		mruSetAssociativeCache.put(47, 47): 
		mruSetAssociativeCache.put(48, 48): 
		mruSetAssociativeCache.put(49, 49): 
		mruSetAssociativeCache.put(50, 50): 
		mruSetAssociativeCache.put(51, 51): 
		mruSetAssociativeCache.put(52, 52): 
		mruSetAssociativeCache.put(53, 53): 
		mruSetAssociativeCache.put(54, 54): 
		mruSetAssociativeCache.put(55, 55): 
		mruSetAssociativeCache.put(56, 56): 
		mruSetAssociativeCache.put(57, 57): 
		mruSetAssociativeCache.put(58, 58): 
		mruSetAssociativeCache.put(59, 59): 
		mruSetAssociativeCache.put(60, 60): 
		mruSetAssociativeCache.put(61, 61): 
		mruSetAssociativeCache.put(62, 62): 
		mruSetAssociativeCache.put(63, 63): 

	Get() Test -- READING 100 ITEMS FROM AN MRU CACHE WITH 8 BLOCKS AND 8 WAY, first 64 (0-63) will succeed and remaining will miss will print null (MRU)
		mruSetAssociativeCache.get(0): Advertisement 0
		mruSetAssociativeCache.get(1): Advertisement 1
		mruSetAssociativeCache.get(2): Advertisement 2
		mruSetAssociativeCache.get(3): Advertisement 3
		mruSetAssociativeCache.get(4): Advertisement 4
		mruSetAssociativeCache.get(5): Advertisement 5
		mruSetAssociativeCache.get(6): Advertisement 6
		mruSetAssociativeCache.get(7): Advertisement 7
		mruSetAssociativeCache.get(8): Advertisement 8
		mruSetAssociativeCache.get(9): Advertisement 9
		mruSetAssociativeCache.get(10): Advertisement 10
		mruSetAssociativeCache.get(11): Advertisement 11
		mruSetAssociativeCache.get(12): Advertisement 12
		mruSetAssociativeCache.get(13): Advertisement 13
		mruSetAssociativeCache.get(14): Advertisement 14
		mruSetAssociativeCache.get(15): Advertisement 15
		mruSetAssociativeCache.get(16): Advertisement 16
		mruSetAssociativeCache.get(17): Advertisement 17
		mruSetAssociativeCache.get(18): Advertisement 18
		mruSetAssociativeCache.get(19): Advertisement 19
		mruSetAssociativeCache.get(20): Advertisement 20
		mruSetAssociativeCache.get(21): Advertisement 21
		mruSetAssociativeCache.get(22): Advertisement 22
		mruSetAssociativeCache.get(23): Advertisement 23
		mruSetAssociativeCache.get(24): Advertisement 24
		mruSetAssociativeCache.get(25): Advertisement 25
		mruSetAssociativeCache.get(26): Advertisement 26
		mruSetAssociativeCache.get(27): Advertisement 27
		mruSetAssociativeCache.get(28): Advertisement 28
		mruSetAssociativeCache.get(29): Advertisement 29
		mruSetAssociativeCache.get(30): Advertisement 30
		mruSetAssociativeCache.get(31): Advertisement 31
		mruSetAssociativeCache.get(32): Advertisement 32
		mruSetAssociativeCache.get(33): Advertisement 33
		mruSetAssociativeCache.get(34): Advertisement 34
		mruSetAssociativeCache.get(35): Advertisement 35
		mruSetAssociativeCache.get(36): Advertisement 36
		mruSetAssociativeCache.get(37): Advertisement 37
		mruSetAssociativeCache.get(38): Advertisement 38
		mruSetAssociativeCache.get(39): Advertisement 39
		mruSetAssociativeCache.get(40): Advertisement 40
		mruSetAssociativeCache.get(41): Advertisement 41
		mruSetAssociativeCache.get(42): Advertisement 42
		mruSetAssociativeCache.get(43): Advertisement 43
		mruSetAssociativeCache.get(44): Advertisement 44
		mruSetAssociativeCache.get(45): Advertisement 45
		mruSetAssociativeCache.get(46): Advertisement 46
		mruSetAssociativeCache.get(47): Advertisement 47
		mruSetAssociativeCache.get(48): Advertisement 48
		mruSetAssociativeCache.get(49): Advertisement 49
		mruSetAssociativeCache.get(50): Advertisement 50
		mruSetAssociativeCache.get(51): Advertisement 51
		mruSetAssociativeCache.get(52): Advertisement 52
		mruSetAssociativeCache.get(53): Advertisement 53
		mruSetAssociativeCache.get(54): Advertisement 54
		mruSetAssociativeCache.get(55): Advertisement 55
		mruSetAssociativeCache.get(56): Advertisement 56
		mruSetAssociativeCache.get(57): Advertisement 57
		mruSetAssociativeCache.get(58): Advertisement 58
		mruSetAssociativeCache.get(59): Advertisement 59
		mruSetAssociativeCache.get(60): Advertisement 60
		mruSetAssociativeCache.get(61): Advertisement 61
		mruSetAssociativeCache.get(62): Advertisement 62
		mruSetAssociativeCache.get(63): Advertisement 63
		mruSetAssociativeCache.get(64): null
		mruSetAssociativeCache.get(65): null
		mruSetAssociativeCache.get(66): null
		mruSetAssociativeCache.get(67): null
		mruSetAssociativeCache.get(68): null
		mruSetAssociativeCache.get(69): null
		mruSetAssociativeCache.get(70): null
		mruSetAssociativeCache.get(71): null
		mruSetAssociativeCache.get(72): null
		mruSetAssociativeCache.get(73): null
		mruSetAssociativeCache.get(74): null
		mruSetAssociativeCache.get(75): null
		mruSetAssociativeCache.get(76): null
		mruSetAssociativeCache.get(77): null
		mruSetAssociativeCache.get(78): null
		mruSetAssociativeCache.get(79): null
		mruSetAssociativeCache.get(80): null
		mruSetAssociativeCache.get(81): null
		mruSetAssociativeCache.get(82): null
		mruSetAssociativeCache.get(83): null
		mruSetAssociativeCache.get(84): null
		mruSetAssociativeCache.get(85): null
		mruSetAssociativeCache.get(86): null
		mruSetAssociativeCache.get(87): null
		mruSetAssociativeCache.get(88): null
		mruSetAssociativeCache.get(89): null
		mruSetAssociativeCache.get(90): null
		mruSetAssociativeCache.get(91): null
		mruSetAssociativeCache.get(92): null
		mruSetAssociativeCache.get(93): null
		mruSetAssociativeCache.get(94): null
		mruSetAssociativeCache.get(95): null
		mruSetAssociativeCache.get(96): null
		mruSetAssociativeCache.get(97): null
		mruSetAssociativeCache.get(98): null
		mruSetAssociativeCache.get(99): null

	After Clear() -- READING 100 ITEMS FROM AN LRU CACHE WITH 8 BLOCKS AND 8 WAY, first 64 (0-63) will succeed and remaining will miss will print null (MRU)
		mruSetAssociativeCache.get(0): null
		mruSetAssociativeCache.get(1): null
		mruSetAssociativeCache.get(2): null
		mruSetAssociativeCache.get(3): null
		mruSetAssociativeCache.get(4): null
		mruSetAssociativeCache.get(5): null
		mruSetAssociativeCache.get(6): null
		mruSetAssociativeCache.get(7): null
		mruSetAssociativeCache.get(8): null
		mruSetAssociativeCache.get(9): null
		mruSetAssociativeCache.get(10): null
		mruSetAssociativeCache.get(11): null
		mruSetAssociativeCache.get(12): null
		mruSetAssociativeCache.get(13): null
		mruSetAssociativeCache.get(14): null
		mruSetAssociativeCache.get(15): null
		mruSetAssociativeCache.get(16): null
		mruSetAssociativeCache.get(17): null
		mruSetAssociativeCache.get(18): null
		mruSetAssociativeCache.get(19): null
		mruSetAssociativeCache.get(20): null
		mruSetAssociativeCache.get(21): null
		mruSetAssociativeCache.get(22): null
		mruSetAssociativeCache.get(23): null
		mruSetAssociativeCache.get(24): null
		mruSetAssociativeCache.get(25): null
		mruSetAssociativeCache.get(26): null
		mruSetAssociativeCache.get(27): null
		mruSetAssociativeCache.get(28): null
		mruSetAssociativeCache.get(29): null
		mruSetAssociativeCache.get(30): null
		mruSetAssociativeCache.get(31): null
		mruSetAssociativeCache.get(32): null
		mruSetAssociativeCache.get(33): null
		mruSetAssociativeCache.get(34): null
		mruSetAssociativeCache.get(35): null
		mruSetAssociativeCache.get(36): null
		mruSetAssociativeCache.get(37): null
		mruSetAssociativeCache.get(38): null
		mruSetAssociativeCache.get(39): null
		mruSetAssociativeCache.get(40): null
		mruSetAssociativeCache.get(41): null
		mruSetAssociativeCache.get(42): null
		mruSetAssociativeCache.get(43): null
		mruSetAssociativeCache.get(44): null
		mruSetAssociativeCache.get(45): null
		mruSetAssociativeCache.get(46): null
		mruSetAssociativeCache.get(47): null
		mruSetAssociativeCache.get(48): null
		mruSetAssociativeCache.get(49): null
		mruSetAssociativeCache.get(50): null
		mruSetAssociativeCache.get(51): null
		mruSetAssociativeCache.get(52): null
		mruSetAssociativeCache.get(53): null
		mruSetAssociativeCache.get(54): null
		mruSetAssociativeCache.get(55): null
		mruSetAssociativeCache.get(56): null
		mruSetAssociativeCache.get(57): null
		mruSetAssociativeCache.get(58): null
		mruSetAssociativeCache.get(59): null
		mruSetAssociativeCache.get(60): null
		mruSetAssociativeCache.get(61): null
		mruSetAssociativeCache.get(62): null
		mruSetAssociativeCache.get(63): null
		mruSetAssociativeCache.get(64): null
		mruSetAssociativeCache.get(65): null
		mruSetAssociativeCache.get(66): null
		mruSetAssociativeCache.get(67): null
		mruSetAssociativeCache.get(68): null
		mruSetAssociativeCache.get(69): null
		mruSetAssociativeCache.get(70): null
		mruSetAssociativeCache.get(71): null
		mruSetAssociativeCache.get(72): null
		mruSetAssociativeCache.get(73): null
		mruSetAssociativeCache.get(74): null
		mruSetAssociativeCache.get(75): null
		mruSetAssociativeCache.get(76): null
		mruSetAssociativeCache.get(77): null
		mruSetAssociativeCache.get(78): null
		mruSetAssociativeCache.get(79): null
		mruSetAssociativeCache.get(80): null
		mruSetAssociativeCache.get(81): null
		mruSetAssociativeCache.get(82): null
		mruSetAssociativeCache.get(83): null
		mruSetAssociativeCache.get(84): null
		mruSetAssociativeCache.get(85): null
		mruSetAssociativeCache.get(86): null
		mruSetAssociativeCache.get(87): null
		mruSetAssociativeCache.get(88): null
		mruSetAssociativeCache.get(89): null
		mruSetAssociativeCache.get(90): null
		mruSetAssociativeCache.get(91): null
		mruSetAssociativeCache.get(92): null
		mruSetAssociativeCache.get(93): null
		mruSetAssociativeCache.get(94): null
		mruSetAssociativeCache.get(95): null
		mruSetAssociativeCache.get(96): null
		mruSetAssociativeCache.get(97): null
		mruSetAssociativeCache.get(98): null
		mruSetAssociativeCache.get(99): null
