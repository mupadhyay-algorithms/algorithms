/*

Container With Most Water
Given n non-negative integers a1, a2, ..., an , where each represents a point at coordinate (i, ai). n vertical lines are drawn such that the two endpoints of line i is at (i, ai) and (i, 0). Find two lines, which together with x-axis forms a container, such that the container contains the most water.
 

Example:

Input: [1,8,6,2,5,4,8,3,7]
Output: 49

*/

public class Solution {
    public int maxArea(int[] height) {
        int maxarea = 0, currentarea=0;
        for (int i = 0; i < height.length; i++)
            for (int j = i + 1; j < height.length; j++) {
                currentarea = Math.min(height[i], height[j]) * (j - i);
                maxarea = Math.max(maxarea, currentarea);
            }
        return maxarea;
    }
}
