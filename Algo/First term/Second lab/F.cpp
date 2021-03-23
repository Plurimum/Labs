#include <iostream>
#include <stack>
#include <vector>

using namespace std;

int main()
{
    ios_base::sync_with_stdio(0);
    stack<int> helperStack;
    int n, i, curMin = 1, a;
    cin >> n;
    stack<int> starterStack;
    vector <string> answ;
    int mas[n];
    for (i = 0; i < n; i++){
        cin >> a;
        mas[i] = a;
    }
    i = 1;
    helperStack.push(mas[0]);
    answ.push_back("push");
    while (!helperStack.empty()){
        if (!helperStack.empty()){
            if (helperStack.top() > curMin){
            helperStack.push(mas[i]);
            i++;
            answ.push_back("push");
            //cout << "1" << endl;
            } else {
            helperStack.pop();
            curMin++;
            answ.push_back("pop");
            if (helperStack.empty() && i < n){
                helperStack.push(mas[i]);
                i++;
                answ.push_back("push");
            }
            //cout << "2" << endl;
            }
        } else {
            helperStack.push(mas[i]);
            i++;
            //cout << "3" << endl;
        }
        if (!helperStack.empty()){
            if (helperStack.top() > curMin && i == n){
                i = 0;
                break;
            }
        }
    }
    if (i < n) {
        cout << "impossible";
    } else {
    for (i = 0; i < answ.size(); i++){
        cout << answ[i] << endl;
    }
    }
    return 0;
}
