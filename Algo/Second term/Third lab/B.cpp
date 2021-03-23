#include <iostream>
#include <vector>

using namespace std;

vector<vector<int> > up;
vector<int> entry;
vector<int> ex;
int myTime, mySize;
vector<vector<int> > tree;

bool isParent(int parent, int child){
    bool correctEntry = entry[parent] <= entry[child];
    bool correctExit = ex[child] <= ex[parent];
    return correctExit && correctEntry;
}

void dfs(int u, int p){
    entry[u] = myTime;
    myTime++;
    up[0][u] = p;
    for (int i = 1; i < mySize; i++){
        up[i][u] = up[i - 1][up[i - 1][u]];
    }
    for (int i = 0; i < tree[u].size(); i++){
        int vertex = tree[u][i];
        if (vertex != p){
            dfs(vertex, u);
        }
    }
    ex[u] = myTime;
    myTime++;
}

void initialize(int n){
    mySize = 1;
    while ((1 << mySize) <= n){
        mySize++;
    }
    up.resize((size_t) mySize);
    entry.resize((size_t) n);
    ex.resize((size_t) n);
}

int lca(int a, int b){
    if (!isParent(a, b)){
        if (!isParent(b, a)){
            for (int i = mySize - 1; i >= 0; i--){
                if (isParent(up[i][a], b)){
                    continue;
                }
                a = up[i][a];
            }
            return up[0][a];
        } else {
            return b;
        }
    } else {
        return a;
    }
}

int main()
{
    std::ios_base::sync_with_stdio(false);
    int n;
    cin >> n;
    int log = 1;
    while ((1 << log) <= n){
        log++;
    }
    tree.resize((size_t) n + 1);
    initialize(n);
    for (int i = 0; i < log; i++){
        up[i].resize((size_t) n);
    }
    for (int i = 1; i < n; i++){
        int x;
        cin >> x;
        tree[--x].emplace_back(i);
        tree[i].emplace_back(x);
    }
    myTime = 0;
    dfs(0, 0);
    int m;
    cin >> m;
    for (int i = 0; i < m; i++){
        int u, v;
        cin >> u >> v;
        cout << lca(--u, --v) + 1 << endl;
    }
    return 0;
}