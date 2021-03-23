#include <iostream>

int dp[100002][20];

int main()
{
    std::ios_base::sync_with_stdio(0);
    int n;
    std::cin >> n;
    for (int i = 1; i <= n; i++){
        std::cin >> dp[i][0];
    }
    for (int i = 1; i <= 19; ++i){
        for (int j = 1; j <= n; ++j){
            dp[j][i] = dp[dp[j][i - 1]][i - 1];
        }
    }

    for (int i = 1; i <= n; i++){
        std::cout << i << ": ";
        for (int j = 0; j <= 19; j++){
            if (dp[i][j] != 0)
            {
                std::cout << dp[i][j] << " ";
            } else
            {
                break;
            }
        }
        std::cout << "\n";
    }
    return 0;
}