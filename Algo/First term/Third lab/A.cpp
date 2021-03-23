#include <iostream>
#include <fstream>
#include <vector>

using namespace std;
int main() {
    ifstream in("input.txt");
    ofstream out("output.txt");
    int n, k, i, j;
    in >> n >> k;
    int a[n - 2];
    for (i = 0; i < n - 2; i++) {
        in >> a[i];
    }
    int b[n - 1];
    b[0] = 0;
    for (i = 1; i < n - 1; i++) {
        int max_ = -10001;
        for (j = i - 1; j >= i - k; j--) {
            if (b[j] > max_) {
                max_ = b[j];
            }
            if (j == 0) {
                break;
            }
        }
        b[i] = max_ + a[i - 1];
    }
    int max_ = INT_MIN;
    for (i = 0; i < k; i++) {
        if (b[n - 2 - i] > max_ && n - 2 - i >= 0) {
            max_ = b[n - 2 - i];
        }
    }
    out << max_ << endl;
    int currentIndex = n - 1;
    vector <int> vec;
    vec.push_back(n);
    while (currentIndex != 0) {
        max_ = INT_MIN;
        int index;
        for (i = currentIndex - 1; i >= currentIndex - k; i--) {
            if (i >= 0 && b[i] > max_) {
                index = i;
                max_ = b[i];
            }
        }
        currentIndex = index;
        vec.push_back(currentIndex + 1);
    }
    out << vec.size() - 1 << endl;
    int left = 0, right = vec.size() - 1;
    while (left < right) {
        swap (vec[left], vec[right]);
        left++;
        right--;
    }
    for (int i = 0; i < vec.size(); i++) {
        out << vec[i] << " ";
    }
    return 0;
}
