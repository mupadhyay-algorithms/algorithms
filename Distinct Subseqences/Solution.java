/*
Leetcode -- 115 Distinct Subsequences
Given a string S and a string T, count the number of distinct subsequences of S which equals T.

A subsequence of a string is a new string which is formed from the original string by deleting some (can be none) of the characters without disturbing the relative positions of the remaining characters. (ie, "ACE" is a subsequence of "ABCDE" while "AEC" is not).

It's guaranteed the answer fits on a 32-bit signed integer.

Example 1:

Input: S = "rabbbit", T = "rabbit"
Output: 3
Explanation:
As shown below, there are 3 ways you can generate "rabbit" from S.
(The caret symbol ^ means the chosen letters)

rabbbit
^^^^ ^^
rabbbit
^^ ^^^^
rabbbit
^^^ ^^^
Example 2:

Input: S = "babgbag", T = "bag"
Output: 5
Explanation:
As shown below, there are 5 ways you can generate "bag" from S.
(The caret symbol ^ means the chosen letters)

babgbag
^^ ^
babgbag
^^    ^
babgbag
^    ^^
babgbag
  ^  ^^
babgbag
    ^^^
*/

class Solution {
    public int numDistinct(String s, String t) {
        if (t==null || s == null)
            return 1;
        int m = t.length(); // rows
        int n = s.length(); // columns
        
        
        int dp[][] = new int[m+1][n+1];
        for (int i=0; i <=n; i++)
            dp[0][i] = 1;
        
        for (int i=1; i <=m; i++)
        {
            for (int j=1; j<=n; j++)
            {
                if (t.charAt(i-1)==s.charAt(j-1))
                    dp[i][j] = dp[i-1][j-1] + dp[i][j-1];
                else
                    dp[i][j] = dp[i][j-1];
            }
        }
        return dp[m][n];
    }
}
