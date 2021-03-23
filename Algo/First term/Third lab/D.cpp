#include <iostream>

using namespace std;
int main() {
    int n, limit = 1000000000, i, j;
    cin >> n;
    if (n == 1) {
        cout << 8;
    } else {
        long long int a[n][10];
        for (i = 0; i < n; i++) {
            for (j = 0; j < 10; j++) {
                if (i == 0) {
                    if (j != 0 && j != 8) {
                        a[i][j] = 1;
                    } else {
                        a[i][j] = 0;
                    }
                } else {
                    switch (j){
                    case 0:
                        a[i][j] = ((a[i - 1][4]) % limit + (a[i - 1][6]) % limit) % limit;
                        break;
                    case 1:
                        a[i][j] = ((a[i - 1][6]) % limit + (a[i - 1][8]) % limit) % limit;
                        break;
                    case 2:
                        a[i][j] = ((a[i - 1][7]) % limit + (a[i - 1][9]) % limit) % limit;
                        break;
                    case 3:
                        a[i][j] = ((a[i - 1][4]) % limit + (a[i - 1][8]) % limit) % limit;
                        break;
                    case 4:
                        a[i][j] = ((a[i - 1][0]) % limit + (a[i - 1][3]) % limit + (a[i - 1][9]) % limit) % limit;
                        break;
                    case 5:
                        a[i][j] = 0;
                        break;
                    case 6:
                        a[i][j] = ((a[i - 1][0]) % limit + (a[i - 1][1]) % limit + (a[i - 1][7]) % limit) % limit;
                        break;
                    case 7:
                        a[i][j] = ((a[i - 1][2]) % limit + (a[i - 1][6]) % limit) % limit;
                        break;
                    case 8:
                        a[i][j] = ((a[i - 1][1]) % limit + (a[i - 1][3]) % limit) % limit;
                        break;
                    case 9:
                        a[i][j] = ((a[i - 1][4]) % limit + (a[i - 1][2]) % limit) % limit;
                        break;
                    }
                }
            }
        }
        long long int sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += a[n - 1][i] % limit;
            sum = sum % limit;
        }
        cout << sum;
    }
    return 0;
}
