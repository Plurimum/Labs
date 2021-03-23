#include <iostream>
#include <vector>
using namespace std;
int main()
{
vector <int> a;
vector <int> b;
int n, k1, k;
cin >> n;
for (int i = 0; i < n; i++) {
cin >> k1;
if (k1 == 0) {
cin >> k;
a.push_back(k);
}
if(k1 == 1) {
int maxi = INT_MIN, maxik = -1;
for (int i = 0; i < a.size(); i++) {
if (a[i] >= maxi) {
maxi = a[i];
maxik = i;
}
}
b.push_back(a[maxik]);
a.erase(a.begin() + maxik);
}
}
for (int i = 0; i < b.size(); i++) {
cout << b[i] << endl;
}
return 0;
}