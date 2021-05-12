#include "tiger.h"

Tiger* create(const char* name){
    Tiger* p = (Tiger*) malloc(sizeof(Tiger));
    construct(p, name);
    return p;
}

void construct(Tiger* p, const char* name){
    p->vtable = tiger_vtable;
    p->name = name;
}

const char* name(Tiger* t){
    return t->name;
}

const char* greet(){
    return "rawr";
}

const char* menu(){
    return "meso";
}