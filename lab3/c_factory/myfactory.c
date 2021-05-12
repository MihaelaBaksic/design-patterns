#include "myfactory.h"
#include <dlfcn.h>
#include <stdio.h>
#include <stdlib.h>

void myfactory(char const* libname, char const* ctorarg, void* obj_adr){

    void* handle;

   void (*construct)(void*, const char*);

    handle = dlopen(libname, RTLD_NOW);

    if(!handle){
        printf("Error opening lib\n");
        exit(1);
    }

    construct = dlsym(handle, "construct");

    (*construct)(obj_adr, ctorarg);

}

int getSize(char const* libname){

    void* handle;

    int (*size)();

    handle = dlopen(libname, RTLD_NOW);


    if(!handle){
        printf("Error opening lib\n");
        exit(1);
    }

    size = dlsym(handle, "size");

    int struct_size = (*size)();
    return struct_size;
}