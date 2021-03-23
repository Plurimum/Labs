#include <iostream>
#include <vector>
#include <fstream>
#include <string>
#include <iterator>
#include <map>

using namespace std;

map<string, int> operations;
int hashFunction(const string& key)
{
    int hash = 0;
    for (int i = 0; i < key.size(); i++){
        hash += ((key[i] * (i + 1)) % 228) * (key[i] - 'a' + 1);
    }
    return abs(hash) % 1488;
}

int main()
{
    std::ifstream::sync_with_stdio(false);
    ifstream fin("map.in");
    ofstream fout("map.out");
    fin.tie();
    fout.tie();
    operations["put"] = 0;
    operations["delete"] = 1;
    operations["get"] = 2;
    vector<vector<pair<string, string>>> map(1488);
    string command, key;
    while (fin >> command)
    {
        fin >> key;
        int hashValue = hashFunction(key);
        int size = map[hashValue].size();
        int valueOfOperation = operations[command];
        /*if (command == "put"){
            string value;
            fin >> value;
            bool exists = false;
            for (int i = 0; i < size; i++)
            {
                if (key == map[hashValue][i].first)
                {
                    map[hashValue][i].second = value;
                    exists = true;
                    break;
                }
            }
            if (!exists)
            {
                pair<string, string> tmp(key, value);
                map[hashValue].emplace_back(tmp);
            }
        } else if (command == "delete"){
            for (int i = 0; i < size; ++i)
            {
                if (key == map[hashValue][i].first)
                {
                    swap(map[hashValue][i], map[hashValue][size - 1]);
                    map[hashValue].pop_back();
                    break;
                }
            }
        } else if (command == "get"){
            string answer;
            bool contains = false;
            for (int i = 0; i < size; i++)
            {
                if (key == map[hashValue][i].first)
                {
                    answer = map[hashValue][i].second;
                    contains = true;
                    break;
                }
            }
            if (contains)
            {
                fout << answer << '\n';
            } else
            {
                fout << "none" << '\n';
            }
        }*/
        switch (valueOfOperation)
        {
            case 0:
            {
                string value;
                fin >> value;
                bool exists = false;
                for (auto& i : map[hashValue])
                {
                    if (key == i.first)
                    {
                        i.second = value;
                        exists = true;
                        break;
                    }
                }
                if (!exists)
                {
                    pair<string, string> tmp(key, value);
                    map[hashValue].emplace_back(tmp);
                }
                break;
            }
            case 1:
            {
                for (int i = 0; i < size; ++i)
                {
                    if (key == map[hashValue][i].first)
                    {
                        swap(map[hashValue][i], map[hashValue][size - 1]);
                        map[hashValue].pop_back();
                        break;
                    }
                }
                break;
            }
            case 2:
            {
                string answer;
                bool contains = false;
                for (auto& i : map[hashValue])
                {
                    if (key == i.first)
                    {
                        answer = i.second;
                        contains = true;
                        break;
                    }
                }
                if (contains)
                {
                    fout << answer << '\n';
                } else
                {
                    fout << "none" << '\n';
                }
                break;
            }
            default:
            {
                break;
            }
        }
    }
    fin.close();
    fout.close();
    return 0;
}