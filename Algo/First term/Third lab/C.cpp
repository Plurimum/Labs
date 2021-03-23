#include <iostream>

using namespace std;

int main()
{
    int n, i, j, k;
    cin >> n;
    int a[n];
    for (i = 0; i < n; i++) {
        cin >> a[i];
    }
    int d[n];
    for (i = 0; i < n; i++) {
        d[i] = 1;
        for (j = 0; j < i; j++) {
            if (a[j] < a[i] && d[j] + 1 > d[i]) {
                d[i] = max(1, d[j] + 1);
            }
        }
    }
    int max_ = 0;
    for (i = 0; i < n; i++) {
        if (d[i] > max_) {
            max_ = d[i];
        }
    }
    cout << max_ << endl;
    int b[max_];
    int currentIndex = n - 1, currentLength = max_;
    for (k = 0; k < max_; k++) {
        for (i = currentIndex; i >= 0; i--) {
            if (d[i] == currentLength) {
                currentLength--;
                b[k] = a[i];
                currentIndex = i;
                break;
            }
        }
    }
    for (i = max_ - 1; i >= 0; i--) {
        cout << b[i] << " ";
    }
    return 0;
}
