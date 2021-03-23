#include <iostream>
#include <fstream>

using namespace std;

int n;

bool checkProfile(int x, int y, int m){
    int a[m + 1];
    int b[m + 1];
    for (int i = 1; i <= m; i++){
        a[i] = x & 1;
        b[i] = y & 1;
        x >>= 1;
        y >>= 1;
        if ((i > 1) && (a[i] == a[i - 1]) && (a[i] == b[i]) && (a[i] == b[i - 1])){
            return false;
        }
    }
    return true;
}

int main()
{
    ifstream in("nice.in");
    ofstream out("nice.out");
    int x1, x2;
    in >> x1 >> x2;
    int i, j, m;
    m = min(x1, x2);
    n = max(x1, x2);
    int x = 1;
    for (i = 1; i <= m; i++){
        x <<= 1;
    }
    int a[n + 1][x + 1];
    for (i = 0; i <= n; i++){
        for (j = 0; j <= x; j++){
            a[i][j] = 0;
        }
    }
    for (i = 0; i < x; i++){
        a[1][i] = 1;
    }
    for (i = 2; i <= n; i++){
        for (j = 0; j < x; j++){
            for (int k = 0; k < x; k++){
                if (checkProfile(j, k, m)){
                    a[i][k] = a[i - 1][j] + a[i][k];
                }
            }
        }
    }
    int answer = 0;
    for (i = 0; i < x; i++){
        answer += a[n][i];
    }
    out << answer;
    return 0;
}
