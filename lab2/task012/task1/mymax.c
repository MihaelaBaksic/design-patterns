#include <stdio.h>
#include <string.h>

int gt_int(const void*, const void*);
int gt_str(const void*, const void*);
int gt_char(const void*, const void*);

const void* mymax( const void* base, size_t nmemb, size_t size, int (*compar)(const void *, const void*)){

    void* max = NULL;

    for(int i=0; i<nmemb; i++){
        if( max==NULL || compar(base, max) > 0){
            max = (void*) base;
        }
        
        base = (const void*) ( ((unsigned char*)base) + size);
    }

    return max;
}


int gt_int(const void* i1, const void* i2){
    return *((const int*)i1) > *((const int*)i2);
}

int gt_char(const void* c1, const void* c2){
    return *((const char*)c1) > *((const char*)c2);
}

int gt_str(const void* s1, const void* s2){
    return strcmp(*(const char**)s1, *(const char**)s2);
}

int main(void){

    int arr_int[] = { 1, 3, 5, 7, 4, 6, 9, 2, 0 };
    char arr_char[]="Suncana strana ulice";
    const char* arr_str[] = {
        "Gle", "malu", "vocku", "poslije", "kise",
        "Puna", "je", "kapi", "pa", "ih", "njise"};


    int max_int = *((const int*)mymax(arr_int, 9, sizeof(int), &gt_int));
    printf("%d\n", max_int);

    int max_char = *((const int*)mymax(arr_char, 21, sizeof(char), &gt_char));
    printf("%c\n", max_char);

    const char* max_str = *(const char**)mymax(arr_str, 11, sizeof(const char*), &gt_str);
    printf("%s\n", max_str);
    
}