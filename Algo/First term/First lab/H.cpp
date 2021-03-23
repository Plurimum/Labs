#include <iostream>

using namespace std;

int main()
{
    long long int h,w,z,c;
    cin>>h>>w>>z;
    long long int l=0;
    long long int r=(h + w)*z+1;
    while (r - l > 1){
        c = (l+r)/2;
        if ((c / w) * (c / h) >= z){
            r = c;
        } else {
            l = c;
        }
    }
    cout<<r;
    return 0;
}
