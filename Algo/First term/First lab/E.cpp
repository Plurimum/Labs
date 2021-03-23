#include <iostream>
using namespace std;
void qsort(int a[], int nSt, int nEnd) {
int l, r, c, x;
if (nSt >= nEnd) return;
l = nSt;
r = nEnd;
x=a[(l+r)/2];
while (l <= r) {
while (a[l] < x) l++;
while (a[r] > x) r--;
if (l <= r) {
c = a[l];
a[l] = a[r];
a[r] = c;
l++;
r--;
}
}
qsort(a,nSt,r);
qsort(a,l,nEnd);
}
int main() {
int n;
cin >> n;
int a[n];
for (int i = 0; i < n; i++) {
cin >> a[i];
}
qsort(a, 0, n-1);
int k;
cin >> k;
int b[k];
int lb, ub;
for (int i = 0; i < k; i++)
{
cin >> lb >> ub;
int l = -1;
int r = n;
while (l < r-1) {
int m = (l+r)/2;
if (a[m] < lb) {
l = m;
} else r = m;
}
lb = r;
l = -1;
r = n;
while (l < r-1) {
int m = (l+r)/2;
if (a[m] <= ub) {
l = m;
} else r = m;
}
ub = r;
b[i] = ub - lb;
}
for (int i = 0; i < k; i++) {
cout << b[i] << " ";
}
return 0;
}
