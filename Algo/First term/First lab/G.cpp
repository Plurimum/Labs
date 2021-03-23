#include <iostream>
using namespace std;

int main(){
    long long n, x, y;
    cin >> n >> x >> y;
    n--;
    long long l = 0, r = n*max(x, y), m;
    while (l < r){
        m = (r + l) / 2;
        if ((m / x + m / y) < n)l = m+1;
        else r = m;
    }
    cout << r + min(x,y);
    return 0;
}
