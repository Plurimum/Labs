#include <iostream>
#include <fstream>

using namespace std;

int main()
{
    ifstream in("input.txt");
    ofstream out("output.txt");
    int n, m, i, j;
    in >> n >> m;
    int a[n][m];
    for (i = 0; i < n; i++) {
        for (j = 0; j < m; j++) {
            in >> a[i][j];
        }
    }
    int b[n][m];
    for (i = 0; i < n; i++) {
        for (j = 0; j < m; j++) {
            if (i == 0) {
                if (j == 0) {
                    b[i][j] = a[0][0];
                } else {
                    b[i][j] = b[i][j - 1] + a[i][j];
                }
            } else if (j == 0) {
                b[i][j] = b[i - 1][j] + a[i][j];
            } else {
                b[i][j] = a[i][j] + max(b[i - 1][j], b[i][j - 1]);
            }
        }
    }
    out << b[n - 1][m - 1] << endl;
    int currentLine = n - 1, currentColumn = m - 1;
    string answer = "";
    while (currentColumn != 0 || currentLine != 0) {
        if (currentLine == 0) {
            answer += "R";
            currentColumn--;
        } else if (currentColumn == 0) {
            answer += "D";
            currentLine--;
        } else if (b[currentLine - 1][currentColumn] > b[currentLine][currentColumn - 1]) {
            answer += "D";
            currentLine--;
        } else {
            answer += "R";
            currentColumn--;
        }
    }
    int left = 0, right = answer.size() - 1;
    while (left < right) {
        swap(answer[left], answer[right]);
        left++;
        right--;
    }
    out << answer;
    return 0;
}
