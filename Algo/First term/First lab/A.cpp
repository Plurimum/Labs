#include <iostream>
#include <vector>

using namespace std;

int N, i, first = 0, last, mid, p, ch;
long num;

void qs(int a[], int l, int r){
if (l >= r) return;
int x = a[(l+r)/2];
int tmpForSwapElements;
int i = l, j = r;
while (i <= j){
    while (a[i] < x){
        i++;
    }
    while (a[j] > x){
        j--;
    }
    if (i <= j) {
    tmpForSwapElements = a[i];
    a[i] = a[j];
    a[j] = tmpForSwapElements;
    i++;
    j--;
    }
}
    qs (a,l, j);
    qs (a,i, r);
}

int main()
{
    cin>>N;
    int myMas[N];
    for (i=0; i < N; i++){
        cin>>num;
        myMas[i] = num;
    }
    last = N - 1;
    qs (myMas,first, last);
    for (i = 0; i < N; i++){
        cout<< myMas[i]<< " ";
    }
    return 0;
}