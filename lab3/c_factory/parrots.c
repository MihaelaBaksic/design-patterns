#include "parrots.h"

Parrot* create(const char* name){
    Parrot* p = (Parrot*) malloc(sizeof(Parrot));
    construct(p, name);
    return p;
}

void construct(Parrot* p, const char* name){
    p->vtable = parrot_vtable;
    p->name = name;
}


const char* greet(){
    return "sqwak";
}

const char* menu(){
    return "voće";
}