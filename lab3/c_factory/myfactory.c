#include "myfactory.h"
#include <dlfcn.h>
#include <stdio.h>
#include <stdlib.h>

void* myfactory(char const* libname, char const* ctorarg){

    void* handle;

    void* (*create)(const char*);

    handle = dlopen(libname, RTLD_NOW);

    if(!handle){
        printf("Error opening lib\n");
        exit(1);
    }

    create = dlsym(handle, "create");

    void* obj = (*create)(ctorarg);

    return obj;
}