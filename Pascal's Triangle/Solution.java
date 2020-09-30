/*
Pascal's Triangle II
Given an integer rowIndex, return the rowIndexth row of the Pascal's triangle.

Notice that the row index starts from 0.


In Pascal's triangle, each number is the sum of the two numbers directly above it.

Example 1:

Input: rowIndex = 3
Output: [1,3,3,1]
Example 2:

Input: rowIndex = 0
Output: [1]
Example 3:

Input: rowIndex = 1
Output: [1,1]
 
*/
class Solution {
    public List<Integer> getRow(int rowIndex) {
        int numRows = rowIndex+1;
        List<List<Integer>> ret = new ArrayList<List<Integer>>();
        if (numRows == 0)
            return ret.get(0);
        
        ret.add(new ArrayList<Integer>());
        ret.get(0).add(1);
        if (numRows == 1)
            return ret.get(0);
        
        ret.add(new ArrayList<Integer>());
        ret.get(1).add(1);
        ret.get(1).add(1);
        
        if (numRows == 2)
            return ret.get(1);

        for (int i = 2; i < numRows; i++) {
            ArrayList<Integer> tmp = new ArrayList<Integer>();
            tmp.add(1);
            for (int j=1; j<i; j++) {
                List<Integer> prev = ret.get(i-1);
                tmp.add(prev.get(j-1) + prev.get(j));
            }
            tmp.add(1);
            ret.add(tmp);
        }
        return ret.get(rowIndex);
    }
}        
