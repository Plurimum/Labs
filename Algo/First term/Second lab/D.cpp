#include <iostream>
#include <vector>
#include <queue>
#include <deque>

using namespace std;

int main()
{
    int i, n, id, j;
    cin >> n;
    string str;
    vector<int> answ;
    deque<int> firstQueue;
    deque<int> secondQueue;
    for (i = 0; i < n; i++){
        cin >> str;
        if (str == "+"){
            cin >> id;
            secondQueue.push_back(id);
            if (secondQueue.size() == firstQueue.size() + 1){
                firstQueue.push_back(secondQueue.front());
                secondQueue.pop_front();
            }
        }
        if (str == "*"){
            //if (!secondQueue.empty()){
                cin >> id;
                if (firstQueue.size() == secondQueue.size()){
                firstQueue.push_back(id);
                continue;
                }
                if (firstQueue.size() == secondQueue.size() + 1){
                secondQueue.push_front(id);
                }
            //} else {
               // if (firstQueue.empty()){
                   // cin >> id;
                   // firstQueue.push_back(id);
                //} else {
                 //   cin >> id;
                   // secondQueue.push_back(id);
                  //  }
             //   }
        }
        if (str == "-"){
            answ.push_back(firstQueue.front());
            firstQueue.pop_front();
            if (!secondQueue.empty() && firstQueue.size() == secondQueue.size() - 1){
                firstQueue.push_back(secondQueue.front());
                secondQueue.pop_front();
            }
        }
    }
    for (i = 0; i < answ.size(); i++){
        cout << answ[i] << endl;
    }
    return 0;
}
