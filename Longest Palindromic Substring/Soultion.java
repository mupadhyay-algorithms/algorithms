/*
Longest Palindromic Substring
Medium

8016

573

Add to List

Share
Given a string s, find the longest palindromic substring in s. You may assume that the maximum length of s is 1000.

Example 1:

Input: "babad"
Output: "bab"
Note: "aba" is also a valid answer.
Example 2:

Input: "cbbd"
Output: "bb"
*/

class Solution {
    public String longestPalindrome(String s) {
      if (s.isEmpty())
          return "";
      int n = s.length();
      String res = null;

      boolean[][] dp = new boolean[n][n];
      int maxlen = 0;
      for (int i = n - 1; i >= 0; i--) {
        for (int j = i; j < n; j++) {
            int curlen = (j-i+1);
            dp[i][j] = s.charAt(i) == s.charAt(j) && 
             (curlen <= 3 || dp[i + 1][j - 1]);
            if (dp[i][j] && (res == null || curlen > maxlen)) 
            {
                maxlen = Math.max(maxlen, curlen);                
                res = s.substring(i, j + 1);
            }
         }
      }             
      return res;
    }
}
