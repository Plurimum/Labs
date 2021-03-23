#include <iostream>
#include <vector>
#include <fstream>
#include <string>
#include <iterator>
#include <map>

using namespace std;

int hashFunction(const string& key)
{
    int hash = 0;
    for (int i = 0; i < key.size(); i++)
    {
        hash += ((key[i] * (i + 1)) % 228) * (key[i] - 'a' + 1);
    }
    return abs(hash) % 1488;
}


struct LinkedMap
{
    vector<vector<pair<string, string>>> map;

    LinkedMap()
    {
        map.resize(1488);
    }

    int getIndexOf(const string& key)
    {
        int hashValue = hashFunction(key);
        int index = -1;
        for (int i = 0; i < map[hashValue].size(); ++i)
        {
            if (key == map[hashValue][i].first)
            {
                index = i;
                break;
            }
        }
        return index;
    }

    void put(string key, string value)
    {
        int hashValue = hashFunction(key);
        int index = getIndexOf(key);
        if (index >= 0)
        {
            map[hashValue][index].second = value;
        } else
        {
            map[hashValue].emplace_back(key, value);
        }
    }

    void remove(const string& key)
    {
        int hashValue = hashFunction(key);
        for (int i = 0; i < map[hashValue].size(); ++i)
        {
            if (key == map[hashValue][i].first)
            {
                swap(map[hashValue][i], map[hashValue][map[hashValue].size() - 1]);
                map[hashValue].pop_back();
                break;
            }
        }
    }

    string get(const string& key)
    {
        int hashValue = hashFunction(key);
        bool flag = false;
        int index = getIndexOf(key);
        if (index >= 0)
        {
            return map[hashValue][index].second;
        } else
        {
            return "none";
        }
    }
};

map<string, int> operations;

int main()
{
    std::ifstream::sync_with_stdio(false);
    ifstream fin("linkedmap.in");
    ofstream fout("linkedmap.out");
    fin.tie();
    fout.tie();
    operations["put"] = 0;
    operations["delete"] = 1;
    operations["get"] = 2;
    operations["prev"] = 3;
    operations["next"] = 4;
    LinkedMap map;
    LinkedMap mapOfPrevs;
    LinkedMap mapOfNexts;
    string command, key;
    string lastAdded;
    while (fin >> command >> key)
    {
        int commandValue = operations[command];
        switch (commandValue)
        {
            case 0:
            {
                string value;
                fin >> value;
                if (map.getIndexOf(key) < 0)
                {
                    mapOfPrevs.put(key, lastAdded);
                    mapOfNexts.put(lastAdded, key);
                    lastAdded = key;

                }
                map.put(key, value);
                break;
            }
            case 1:
            {
                if (key == lastAdded)
                {
                    lastAdded = mapOfPrevs.get(key);
                }
                map.remove(key);
                string next = mapOfNexts.get(key);
                string prev = mapOfPrevs.get(key);
                mapOfNexts.put(prev, next);
                mapOfPrevs.put(next, prev);
                mapOfNexts.remove(key);
                mapOfPrevs.remove(key);
                break;
            }
            case 2:
            {
                fout << map.get(key) << '\n';
                break;
            }
            case 3:
            {
                fout << map.get(mapOfPrevs.get(key)) << '\n';
                break;
            }
            case 4:
            {
                fout << map.get(mapOfNexts.get(key)) << '\n';
                break;
            }
            default:
                break;
        }
    }
    fin.close();
    fout.close();
    return 0;
}