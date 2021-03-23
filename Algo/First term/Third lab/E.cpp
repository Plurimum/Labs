#include <iostream>
#include <string>

using namespace std;

int main()
{
    string firstString, secondString;
    cin >> firstString >> secondString;
    unsigned int i, j;
    int dp[firstString.size() + 1][secondString.size() + 1];
    for (i = 0; i <= firstString.size(); i++) {
        for (j = 0; j <= secondString.size(); j++) {
            if (i == 0 && j == 0) {
                dp[i][j] = 0;
            } else
                if (i == 0) {
                dp[i][j] = j;
                } else
                    if (j == 0) {
                    dp[i][j] = i;
                    } else {
                        if (firstString[i - 1] == secondString[j - 1]) {
                        dp[i][j] = min (dp[i][j - 1] + 1, min (dp[i - 1][j] + 1, dp[i - 1][j - 1]));
                        } else {
                            dp[i][j] = min (dp[i][j - 1] + 1, min (dp[i - 1][j] + 1, dp[i - 1][j - 1] + 1));
                        }
                    }
        }
    }
    cout << dp[firstString.size()][secondString.size()];
    return 0;
}
