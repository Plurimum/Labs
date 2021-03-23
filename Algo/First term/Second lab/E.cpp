#include <iostream>
#include <stack>
#include <string>
#include <vector>
#include <map>

using namespace std;

int main()
{
    stack<int> myStack;
    int answ = 0, i, lastNumber, number, j;
    string str;
    map<char, int> mapNumber;
    vector <string> data;
    char cifra = '0';
    for (i = 0; i < 10; i++){
        mapNumber[cifra] = i;
        cifra++;
        //cout << cifra << endl;
    }
    while (cin >> str){
        data.push_back(str);
    }
    for (i = 0; i < data.size(); i++){
        if (data[i] == "+"){
            lastNumber = myStack.top();
            myStack.pop();
            answ = lastNumber + myStack.top();
            myStack.pop();
            myStack.push(answ);
            //cout << "+" << endl;
            continue;
        }
        if (data[i] == "*"){
            lastNumber = myStack.top();
            myStack.pop();
            answ = lastNumber * myStack.top();
            myStack.pop();
            myStack.push(answ);
            //cout << "*" << endl;
            continue;
        }
        if (data[i] == "-"){
            lastNumber = myStack.top();
            myStack.pop();
            answ = myStack.top() - lastNumber;
            myStack.pop();
            myStack.push(answ);
            //cout << "-" << endl;
            continue;
        }
            number = 0;
            j = 0;
            while (j < data[i].size()) {
                number = number * 10 + mapNumber[data[i][j]];
                //cout << number << " str[i] " << data[i][j] << endl;
                j++;
            }
            myStack.push(number);
        //cout << i << " " << data[i] << endl;
    }
    cout << answ;
    return 0;
}
