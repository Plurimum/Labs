#include <cmath>
#include <iostream>

using namespace std;
double myFunc(double x, double a, double Vp, double Vq) {
    return (sqrt((1 - a) * (1 - a) + x * x)) / Vp + (sqrt(a * a + (1 - x) * (1 - x))) / Vq;
}
double ternSearch(double a, double Vp, double Vq, double e, double left, double right) {
    if (right - left < e) return (left + right) / 2;
    double l_1 = (left * 2 + right) / 3;
    double r_1 = (left + 2 * right) / 3;
    if (myFunc(l_1, a, Vp, Vq) < myFunc(r_1, a, Vp, Vq)) return ternSearch(a, Vp, Vq, e, left, r_1);
    else return ternSearch(a, Vp, Vq, e, l_1, right);
}

int main() {
    double e = 0.0000001;
    double a, Vp, Vq;
    double l = 0, r = 1;
    cin >> Vp >> Vq >> a;
    cout.precision(7);
    cout << ternSearch(a, Vp, Vq, e, l, r) << endl;
    return 0;
}
