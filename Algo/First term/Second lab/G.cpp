#include <iostream>
#include <vector>

using namespace std;

vector<vector <int> > lots;
vector<int> r;

vector<int> myGet(int k){
    if (lots[k][0] != k){
        int thisElement;
        thisElement = lots[k][0];
        lots[k][0] = myGet(thisElement)[0];
        lots[k][1] = myGet(thisElement)[1];
        lots[k][2] = myGet(thisElement)[2];
        lots[k][3] = myGet(thisElement)[3];
    }
    return lots[k];
}
void myUnion(int x, int y){
    int newX = myGet(x)[0];
    int newY = myGet(y)[0];
    if (newX == newY){
        return;
    }
    if (r[newX] == r[newY]){
        r[newX]++;
    }
    if (r[newX] < r[newY]){
        lots[newX][0] = newY;
        lots[newX][1] = min(lots[newX][1], lots[newY][1]);
        lots[newY][1] = lots[newX][1];
        lots[newX][2] = max(lots[newX][2], lots[newY][2]);
        lots[newY][2] = lots[newX][2];
        lots[newX][3] += lots[newY][3];
        lots[newY][3] = lots[newX][3];
    } else {
        lots[newY][0] = newX;
        lots[newY][1] = min(lots[newY][1], lots[newX][1]);
        lots[newX][1] = lots[newY][1];
        lots[newY][2] = max(lots[newY][2], lots[newX][2]);
        lots[newX][2] = lots[newY][2];
        lots[newY][3] += lots[newX][3];
        lots[newX][3] = lots[newY][3];
        }
        /*int t = s[y];
        for (int i = 1; i < s.size(); i++){
            if (s[i] == t){
                s[i] = s[x];
                int myMin = lots[i][0];
                int myMax = lots[i][1];
                lots[i][0] = min(lots[x][0], myMin);
                lots[i][1] = max(lots[x][1], myMax);
                lots[x][0] = min(lots[x][0], myMin);
                lots[x][1] = max(lots[x][1], myMax);
                int myCount = lots[i][2];
                lots[i][2] += lots[x][2];
                lots[x][2] += myCount;
            }
        }
    }*/
}

int main()
{
    int n, i, a, x, y, k;
    cin >> n;
    for (i = 0; i <= n; i++){
        vector <int> trio(4);
        trio[0] = i;
        trio[1] = i;
        trio[2] = i;
        trio[3] = 1;
        r.push_back(1);
        lots.push_back(trio);
    }
    string operation;
    while (cin >> operation){
        if (operation == "union"){
            cin >> x >> y;
            myUnion(x, y);
        }
        if (operation == "get"){
            cin >> k;
            vector<int> masForGet = myGet(k);
            for (int j = 1; j < 4; j++){
                cout << masForGet[j] << " ";
            }
            cout << endl;
        }
    }
    return 0;
}
