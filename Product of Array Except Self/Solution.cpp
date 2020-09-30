/*
Product of Array Except Self
Given an array nums of n integers where n > 1,  return an array output such that output[i] is equal to the product of all the elements of nums except nums[i].

Example:

Input:  [1,2,3,4]
Output: [24,12,8,6]
Constraint: It's guaranteed that the product of the elements of any prefix or suffix of the array (including the whole array) fits in a 32 bit integer.

Note: Please solve it without division and in O(n).

Follow up:
Could you solve it with constant space complexity? (The output array does not count as extra space for the purpose of space complexity analysis.)
this is O(1) space solution
*/
class Solution {
public:
    vector<int> productExceptSelf(vector<int>& nums) {
        int length = nums.size();

        vector<int> ret(length, 1);
        //left processing
        for (int i = 1; i < length; i++) {
            ret[i] = nums[i - 1] * ret[i - 1];
        }
        // right processing
        int rightproduct = 1;
        for (int i = length - 1; i >= 0; i--) {

            ret[i] = ret[i] * rightproduct;
            rightproduct *= nums[i];
        }

        return ret;
         
    }
};
