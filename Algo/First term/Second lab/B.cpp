#include <iostream>
#include <vector>
#include <stack>

using namespace std;

int main()
{
    int n, i, a, lastColor, countOfRemoves = 0;
    cin >> n;
    stack<int> balls;
    int mas[n];
    int colors[10];
    for (i = 0; i < 10; i++){
        colors[i] = 0;
    }
    for (i = 0; i < n; i++){
        cin >> a;
        mas[i] = a;
    }
    for (i = 0; i < n - 1; i++){
        if (balls.size() > 1){
            lastColor = balls.top();
            balls.pop();
            if (!balls.empty()){
                if (mas[i] == balls.top() && lastColor == mas[i]){
                    while (mas[i] == lastColor && i < n){
                        balls.push(mas[i]);
                        balls.pop();
                        countOfRemoves++;
                       // cout << countOfRemoves << " " << i << endl;
                        i++;
                    }
                    i--;
                    balls.push(lastColor);
                    balls.pop();
                    balls.pop();
                    countOfRemoves += 2;
                   // cout << countOfRemoves << " " << i << endl;
                } else {
                    balls.push(lastColor);
                    balls.push(mas[i]);
                   // cout << "1st else" << endl;
                }
            }
        } else {
            balls.push(mas[i]);
            i++;
            balls.push(mas[i]);
           // cout << "2nd else" << endl;
        }
    }
    if (balls.size() > 1){
        lastColor = balls.top();
       // cout << lastColor << endl;
        balls.pop();
       // cout << balls.top() << endl;
        //cout << mas[i] << endl;
        if (mas[i] == balls.top() && lastColor == mas[i]){
            countOfRemoves += 3;
           // cout << countOfRemoves << " " << i << endl;
        }
    }
    cout << countOfRemoves;
    return 0;
}
