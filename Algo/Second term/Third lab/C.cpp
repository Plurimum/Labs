#include <iostream>
#include <vector>
#include <math.h>
#include <fstream>

using namespace std;

int mSize;
vector<int> depth;
vector<int> degreeTwo;
vector<vector<int> > dp;
vector<vector<int> > weight;
vector<vector<int> > vertexes;

void dfs(int v, int d){
    depth[v] = d;
    d++;
    for (auto u : vertexes[v]){
        dfs(u, d);
    }
}

int minOnPath(int v, int u){
    int min = INT32_MAX;
    if (depth[v] > depth[u]){
        int tmp = v;
        v = u;
        u = tmp;
    }
    for (int i = mSize - 1; i >= 0; i--){
        if (depth[u] - depth[v] >= degreeTwo[i]){
            min = std::min(weight[u][i], min);
            u = dp[u][i];
        }
    }
    if (v == u) return min;
    for (int i = mSize - 1; i >= 0; i--){
        if (dp[v][i] == dp[u][i]){
            continue;
        }
        min = std::min(weight[v][i], std::min(weight[u][i], min));
        v = dp[v][i];
        u = dp[u][i];
    }
    return std::min(std::min(weight[v][0], min), std::min(weight[u][0], min));
}

void initialize(int n){
    vertexes.resize((size_t) n);
    depth.resize((size_t) n);
    mSize = (int) (log(n) / log(2) + 1);
    dp.resize((size_t) n);
    weight.resize((size_t) n);
    for (int i = 0; i < n; i++){
        dp[i].resize((size_t) mSize + 1);
        weight[i].resize((size_t) mSize + 1);
    }
    degreeTwo.resize((size_t) mSize + 1);
}

void preprocess(int n){
    for (int j = 1; j < mSize; j++){
        for (int i = 0; i < n; i++){
            dp[i][j] = dp[dp[i][j - 1]][j - 1];
            weight[i][j] = std::min(weight[i][j - 1], weight[dp[i][j - 1]][j - 1]);
        }
    }
    degreeTwo[0] = 1;
    for (int i = 1; i <= mSize; i++){
        degreeTwo[i] = degreeTwo[i - 1] << 1;
    }
}

int main()
{
    std::ifstream::sync_with_stdio(false);
    ifstream fin;
    ofstream fout;
    fin.open("minonpath.in");
    fout.open("minonpath.out");
    int n;
    fin >> n;
    initialize(n);
    for (int i = 1; i < n; i++){
        int v, w;
        fin >> v >> w;
        dp[i][0] = --v;
        weight[i][0] = w;
        vertexes[v].emplace_back(i);
    }
    dfs(0, 0);
    preprocess(n);
    int m;
    fin >> m;
    for (int i = 0; i < m; i++){
        int u, v;
        fin >> v >> u;
        fout << minOnPath(--v, --u) << endl;
    }
    fin.close();
    fout.close();
    return 0;
}