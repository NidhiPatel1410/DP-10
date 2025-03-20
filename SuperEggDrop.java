// In this approach, we are using dp table, where storing the eggs on row side and floors on column side. Then computing how many 
// attempts required if given x eggs and y floors. So, to compute this at each point we have different choices. For eg. If 2 eggs and 
// 3 floors, we have options like either to start from floor 1 or start from floor 2 or start from floor 3. We will pick whatever will
// give minimum. So for this we have third inner loop. And the formula consist of 1 + Max(breakCase, NoBreakCase) i.e if we drop an
// egg from any floor it will either break or not break. If break we have to look at the floors below it, so f-1 and if it does not
// break than we have to look at the floors above it so j-f, because j gives the total floors and f is just the floor that we are 
// starting from.
// Since this is a O(kn^2) solution it is giving time limit exceeded. 

// Time Complexity : O(kn^2)
// Space Complexity : O(kn)
// Did this code successfully run on Leetcode : time limit exceeded
// Any problem you faced while coding this : no
class Solution {
    public int superEggDrop(int k, int n) {
        // Base case
        if (n == 0 || k == 0) {
            return 0;
        }
        // Dp with eggs on row and floors on column side
        int[][] dp = new int[k + 1][n + 1];
        // We know that our first row will have value 1,2,3,.. i.e. j (Eg. if 1 egg and
        // 1 floor, so attempt required 1, if 1 egg and 2 floors attempts in worst case
        // - 2)
        for (int j = 1; j < n + 1; j++) {
            dp[1][j] = j;
        }
        // Now start from second row
        for (int i = 2; i < k + 1; i++) {
            // First column
            for (int j = 1; j < n + 1; j++) {
                // Declare this as large number because initially it is 0, so in min it will
                // always be 0 if we dont take large number
                dp[i][j] = Integer.MAX_VALUE;
                // Now run a loop for starting from different floors
                for (int f = 1; f <= j; f++) {
                    // Take min of exploring start from different floors
                    dp[i][j] = Math.min(dp[i][j], 1 + Math.max(dp[i - 1][f - 1], dp[i][j - f]));
                }
            }
        }
        // Last cell will have the ans
        return dp[k][n];
    }
}

// In this optimized solution, we know that number of attempts required in worst
// case will be n, i.e we go on each floor and check.
// So lets take attempts on row side and eggs on column side, and in the dp
// table we will store how many floors we can explore if given
// these many attempts and these many eggs. So whenever on any row last column
// our cell value becomes greater than the given floors,
// we stop and return that attempts number.

// Time Complexity : O(kn)
// Space Complexity : O(kn)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no
class Solution {
    public int superEggDrop(int k, int n) {
        // Base case
        if (n == 0 || k == 0) {
            return 0;
        }
        // Take attempts on row and eggs on column side
        int[][] dp = new int[n + 1][k + 1];
        // Initially attempts 0
        int attempts = 0;
        // Till our last column has value less than number of floors given we will run
        // this loop
        while (dp[attempts][k] < n) {
            // Do attempts++ to start from 1st row
            attempts++;
            // Inner loop will be for eggs
            for (int j = 1; j < k + 1; j++) {
                // Simply add the floors we get from break case and no break case to 1
                dp[attempts][j] = 1 + dp[attempts - 1][j - 1] + dp[attempts - 1][j];
            }
        }
        // As soon this loop ends means we have a value which is greater than given
        // number of floors
        // Return that row number where it ended
        return attempts;
    }
}