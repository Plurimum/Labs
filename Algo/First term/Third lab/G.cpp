#include <iostream>

using namespace std;

int dp[100][100];
string s;
int ep[100][100];

void rec(int l, int r){
    if (dp[l][r] == r - l + 1)
        return;
    if (dp[l][r] == 0){
        cout << s.substr(l, r - l + 1);
        return;
        }
    if (ep[l][r] == -1) {
            cout << s[l];
            rec(l + 1, r - 1);
        cout << s[r];
        return;
    }
    rec(l, ep[l][r]);
    rec(ep[l][r] + 1, r);
}

int main()
{
    cin >> s;
    dp[1][1] = 1;
    int n = s.size();
    for (int r = 0; r < n; ++r){
        for (int l = r; l >= 0; --l){
            if (l == r)    dp[l][r] = 1;
                else{
                    int best = 1000000;
                    int mk = -1;
                    if (s[l] == '(' && s[r] == ')' || s[l] == '[' && s[r] == ']' || s[l] == '{' && s[r] == '}')
                        best = dp[l + 1][r - 1];
                        for (int k = l; k < r; ++k)
                            if (dp[l][k] + dp[k + 1][r] < best){
                                best = dp[l][k] + dp[k + 1][r];
                                mk = k;
                                }
                        dp[l][r] = best; ep[l][r] = mk;
                        }
        }
    }
    rec(0, n - 1);
    return 0;
}
