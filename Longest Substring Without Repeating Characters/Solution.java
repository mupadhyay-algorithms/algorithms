/*
Given a string s, find the length of the longest substring without repeating characters.

 

Example 1:

Input: s = "abcabcbb"
Output: 3
Explanation: The answer is "abc", with the length of 3.
Example 2:

Input: s = "bbbbb"
Output: 1
Explanation: The answer is "b", with the length of 1.
Example 3:

Input: s = "pwwkew"
Output: 3
Explanation: The answer is "wke", with the length of 3.
Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.
Example 4:

Input: s = ""
Output: 0
 
Constraints:

0 <= s.length <= 5 * 104
s consists of English letters, digits, symbols and spaces.
*/

import java.util.HashMap;
import java.util.Map;

public class Solution {
    public static int lengthOfLongestSubstring(String s) {
        int n = s.length(), currentmax = 0;
        Map<Character, Integer> map = new HashMap<>(); // current index of character
        // try to extend the range [i, j]
        for (int j = 0, i = 0; j < n; j++) {
            // Find the current key from hashmap
            if (map.containsKey(s.charAt(j))) {
                // if exists gets the previous index of character
                // check if the previous index of the character from map with
                //  already last index of previous character, 
                //     find the maximum to remove character
                //  for example:
                //      abcabcbb
                //      here i=0 initially, when we add characters a=1 (due to +1) 
                //             b=2, c=3 now we encounter a again
                //             the j index is 3 but previous a index is 1
                //             and last index is a hence our last index is 1
                //              then we hit b again, i will be advanced again
                //     but if string is abccbabb
                //              then we we hit c agaain
                //              c's index is 3 and the previously saved i = 0
                //              hence now i = 3; removing all characters from abc
                i = Math.max(map.get(s.charAt(j)), i);
            }
            currentmax = Math.max(currentmax, j - i +1);
            map.put(s.charAt(j), j+1 );
        }
        return currentmax;    
    }
}
