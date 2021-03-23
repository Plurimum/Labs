#include <iostream>
#include <math.h>
using namespace std;
int main() {
int n, k;
cin>> n>>k;
int a[n], b[k];
for (int i=0; i < n; i++){
    cin>> a[i];
}
for (int i = 0; i < k; i++){
    cin >> b[i];
}
int c[k];
int lb, ub;
for (int i=0; i < k; i++){
        int l = 0, r = n;
        while (l < r-1){
            int x = (l+r)/2;
            if (a[x] <= b[i]){
                l = x;
            } else r = x;
        }
        lb = l;
        ub = lb + 1;
        if (lb != n-1){
            if (b[i] - a[lb] > a[ub] - b[i]){
                c[i] = a[ub];
            } else c[i] = a[lb];
        } else c[i] = a[lb];
}
for (int i = 0; i < k; i++){
    cout<<c[i]<<endl;
}
return 0;
}
