#include <iostream>
#include <queue>
#include <stack>

using namespace std;

int main()
{
    ios_base::sync_with_stdio(0);
    long n, i, operation, last;
    cin >> n;
    stack<long> mins;
    stack<long> mas;
    for (i = 0; i < n; i++){
        cin >> operation;
        if (operation == 1){
            cin >> last;
            mas.push(last);
            if (mins.empty()){
                mins.push(last);
            } else {
                if (mas.top() <= mins.top()){
                    mins.push(mas.top());
                    }
            }
            continue;
        }
        if (operation == 2){
            if (mas.top() == mins.top()){
                    mas.pop();
                    mins.pop();
                continue;
            } else {
                    mas.pop();
                continue;
            }
        }
        if (operation == 3) {
            cout << " " <<mins.top();
        }
    }
    return 0;
}
