#include <iostream>
#include <fstream>
#include <vector>
#include <map>

using namespace std;

map<string, int> operations;

int hashFunction(int value)
{
    return abs(value) * 1488 / 228 % 133722;
}

bool exists(int value, vector<vector<int> > set)
{
    bool exists = false;
    int hashValue = hashFunction(value);
    for (int i : set[hashValue])
    {
        if (value == i)
        {
            exists = true;
            break;
        }
    }
    return exists;
}

int main()
{
    string insert = "insert";
    operations["insert"] = 0;
    operations["delete"] = 1;
    operations["exists"] = 2;
    std::ifstream::sync_with_stdio(false);
    ifstream fin;
    ofstream fout;
    fin.open("set.in");
    fout.open("set.out");
    vector<vector<int>> set;
    set.assign(static_cast<const unsigned int>(133722), vector<int>(0));
    string command, operation;
    int value;
    while (fin >> command >> value)
    {
        int valueOfOperation = operations[command];
        int hashValue = hashFunction(value);
        switch (valueOfOperation)
        {
            case 0:
            {
                bool exists = false;
                for (int i : set[hashValue])
                {
                    if (value == i)
                    {
                        exists = true;
                        break;
                    }
                }
                if (!exists)
                {
                    set[hashValue].push_back(value);
                }
                break;
            }
            case 1:
            {
                for (int i = 0; i < set[hashValue].size(); ++i)
                {
                    if (value == set[hashValue][i])
                    {
                        for (int j = i; j < set[hashValue].size() - 1; j++)
                        {
                            set[hashValue][j] = set[hashValue][j + 1];
                        }
                        set[hashValue].pop_back();
                        break;
                    }
                }
                break;
            }
            case 2:
            {
                bool exists = false;
                for (int i : set[hashValue])
                {
                    if (value == i)
                    {
                        exists = true;
                        break;
                    }
                }
                if (exists)
                {
                    fout << "true" << '\n';
                } else
                {
                    fout << "false" << '\n';
                }
                break;
            }
            default:
                break;
        }
    }
    fout.close();
    fin.close();
    return 0;
}