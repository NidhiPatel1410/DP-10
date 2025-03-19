// This is a different type of DP problem - DP with permutations - Here once we decide to leave the element, we can come back and pick
// that. Here order of choice (that is order of picking the elements) matters.

// In this problem, we are keeping one burstable array, and balloons present inside this can only be bursted. So we are exploring the
// burstable array of different length (1,2,3,..., n) and keeping a 2D dp matrix(n^2), that will store the computed earnings.

// Time Complexity : O(n^3)
// Space Complexity : O(n^2) 2d dp array
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no
class Solution {
    public int maxCoins(int[] nums) {
        // Base case
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int n = nums.length;
        // Declare a dp matrix of n^2
        int[][] dp = new int[n][n];
        // Now this outer loop will give us the length of different burstable arrays,
        // length can be from 1 to n
        for (int len = 1; len <= n; len++) {
            // This i will give us the start index of that burstable array, and it will be
            // bounded by n-len, because let's say we have n=4, then there can be 4
            // burstable array of len 1, so start index can max go till n-len i.e. 4-1 =
            // 3(0, 1, 2, 3).
            // Similarly there can be 3 burstable arrays of length 2, so start index of this
            // length 2 array can go only until n-len i.e 4-2=2 (0-1, 1-2, 2-3)
            // Similarly there can be 2 burstable arrays of length 3, so start index of this
            // length 3 array can go only until n-len i.e 4-3=1 (0-1-2, 1-2-3)
            // Similarly there can be 1 burstable array of length 4, so start index of this
            // length 4 array can go only until n-len i.e 4-4=0 (0-1-2-3)
            for (int i = 0; i <= n - len; i++) {
                // j is for the end index of burstable array, how can we get the end, suppose
                // our n=4(2,3,4,5) values. And burstable array we are looking at is (3,4) so we
                // want end index as 2, so whatever the length of this burstable array, if we
                // add i
                // to it(that is number of elements in normal array(2) before this burstable
                // array(before 3 there is 2 left in normal array)) and then we get(2,3,4) that
                // is 3,
                // so if we subtract 1 from it, we will get end index j=2
                int j = len + i - 1;
                // This k is for iterating over each balloons in burstable array
                for (int k = i; k <= j; k++) {
                    // Now to compute the earnings, the formula is earnings earned by bursting
                    // whatever before the current balloon + bursting the current balloon + bursting
                    // balloons after the current balloon

                    // And for bursting the current balloon at end, left * current * right
                    int left = 1;
                    if (i != 0) {
                        // If we are not at the first balloon in normal array, we get a left balloon
                        // from i-1
                        left = nums[i - 1];
                    }
                    int right = 1;
                    if (j != n - 1) {
                        // If we are not at the last balloon in normal array, we get a right balloon
                        // from j+1
                        right = nums[j + 1];
                    }
                    int before = 0;
                    if (k != i) {
                        // If we are not at the first burstable balloon, than we have some balloons that
                        // we can burst on left
                        // It's a subproblem which we already computed earlier, so get it's value from
                        // dp matrix
                        before = dp[i][k - 1];
                    }
                    int after = 0;
                    if (k != j) {
                        // If we are not at the last burstable balloon, than we have some balloons that
                        // we can burst on right
                        // It's a subproblem which we already computed earlier, so get it's value from
                        // dp matrix
                        after = dp[k + 1][j];
                    }
                    // Compute earnings
                    dp[i][j] = Math.max(dp[i][j], before + left * nums[k] * right + after);
                }
            }
        }
        // Our ans will be
        return dp[0][n - 1];

    }
}