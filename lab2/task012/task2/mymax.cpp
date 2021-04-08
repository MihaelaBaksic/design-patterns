#include <iostream>
#include <vector>
#include <set>
#include <assert.h>

using namespace std;

template <typename Iterator, typename Predicate>
Iterator mymax(Iterator first, Iterator last, Predicate pred){

    Iterator max = last;

    for(Iterator i=first; i!=last; i++){
        if( max==last || pred(*i, *max))
            max = i;
    }
    return max;
}


int arr_int[] = { 1, 3, 5, 7, 4, 6, 9, 2, 0 };
vector<int> vector_int = { 1, 3, 5, 7, 4, 6, 9, 2, 0 };
set<int> set_int = { 1, 3, 5, 7, 4, 6, 9, 2, 0 };
string arr_str[] = {"mali", "veliki", "nijenajveci"};
vector<int> vector_empty;

bool gt_int(int t, int o){
    return t > o;
}

bool gt_string(string t, string o){
    return t > o;
}

int main(void){
    const int* maxint = mymax(&arr_int[0], &arr_int[sizeof(arr_int)/sizeof(*arr_int)], gt_int);
    cout << *maxint <<"\n";

    const string* maxstr = mymax(&arr_str[0], &arr_str[sizeof(arr_str)/sizeof(*arr_str)], gt_string);
    cout << *maxstr <<"\n";

    auto empty_max = mymax(vector_empty.begin(), vector_empty.end(), gt_int);
    assert(empty_max == vector_empty.end());

    auto vector_max = mymax(vector_int.begin(), vector_int.end(), gt_int);
    cout << *vector_max <<"\n";

    auto set_max = mymax(set_int.begin(), set_int.end(), gt_int);
    cout << *set_max <<"\n";
}