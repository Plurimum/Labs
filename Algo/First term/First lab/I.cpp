#include <iostream>
#include <cmath>
#include <iomanip>
using namespace std;

double numInSquare(double x){
double k = x*x;
return k;
}

int main(){
    double n;
    cin >> n;
    double l = 0.0, r = n+1, m;
    while (abs(r - l) > 0.00000001){
        m = (r + l) / 2;
        if (numInSquare(m) + sqrt(m) <= n)
            l = m;
        else
        r = m;
    }
    cout <<setprecision(7) << r;
    return 0;
}
