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

struct HashSet
{
    vector<vector<string>> set;
    int size;

    HashSet()
    {
        set.resize(228);
        size = 0;
    }

    int getIndexOf(const string& key)
    {
        int hashValue = hashFunction(key);
        hashValue %= 228;
        int index = -1;
        for (int i = 0; i < set[hashValue].size(); ++i)
        {
            if (key == set[hashValue][i])
            {
                index = i;
                break;
            }
        }
        return index;
    }

    void put(string key)
    {
        int hashValue = hashFunction(key);
        hashValue %= 228;
        int index = getIndexOf(key);
        if (index >= 0)
        {
        } else
        {
            set[hashValue].emplace_back(key);
            size++;
        }
    }

    void remove(const string& key)
    {
        int hashValue = hashFunction(key);
        hashValue %= 228;
        for (int i = 0; i < set[hashValue].size(); ++i)
        {
            if (key == set[hashValue][i])
            {
                swap(set[hashValue][i], set[hashValue][set[hashValue].size() - 1]);
                set[hashValue].pop_back();
                size--;
                break;
            }
        }
    }

    string get(const string& key)
    {
        int hashValue = hashFunction(key);
        hashValue %= 777;
        bool flag = false;
        int index = getIndexOf(key);
        if (index >= 0)
        {
            return set[hashValue][index];
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
    ifstream fin("multimap.in");
    ofstream fout("multimap.out");
    fin.tie();
    fout.tie();
    operations["put"] = 0;
    operations["delete"] = 1;
    operations["deleteall"] = 2;
    operations["get"] = 3;
    vector<vector<pair<string, HashSet>>> multiMap(1488);
    string command, key;
    while (fin >> command >> key)
    {
        int commandValue = operations[command];
        int hashValue = hashFunction(key);
        switch (commandValue)
        {
            case 0:
            {
                string value;
                fin >> value;
                bool keyExists = false;
                int index = 0;
                for (int i = 0; i < multiMap[hashValue].size(); ++i)
                {
                    if (key == multiMap[hashValue][i].first)
                    {
                        keyExists = true;
                        index = i;
                        break;
                    }
                }
                if (!keyExists)
                {
                    HashSet tmp;
                    tmp.put(value);
                    multiMap[hashValue].emplace_back(key, tmp);
                } else
                {
                    multiMap[hashValue][index].second.put(value);
                }
                break;
            }
            case 1:
            {
                string value;
                fin >> value;
                for (auto& i : multiMap[hashValue])
                {
                    if (key == i.first)
                    {
                        i.second.remove(value);
                        if (i.second.size == 0)
                        {
                            swap(i, multiMap[hashValue][multiMap[hashValue].size() - 1]);
                            multiMap[hashValue].pop_back();
                            break;
                        }
                        break;
                    }
                }
                break;
            }
            case 2:
            {
                for (auto& i : multiMap[hashValue])
                {
                    if (key == i.first)
                    {
                        swap(i, multiMap[hashValue][multiMap[hashValue].size() - 1]);
                        multiMap[hashValue].pop_back();
                        break;
                    }
                }
                break;
            }
            case 3:
            {
                HashSet answer;
                bool flag = false;
                for (auto& i : multiMap[hashValue])
                {
                    if (key == i.first)
                    {
                        answer = i.second;
                        flag = true;
                        break;
                    }
                }
                if (flag)
                {
                    fout << answer.size << " ";
                    for (auto& i : answer.set)
                    {
                        for (auto& j : i)
                        {
                            fout << j << " ";
                        }
                    }
                    fout << endl;
                } else
                {
                    fout << "0" << endl;
                }
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