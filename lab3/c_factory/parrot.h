#include <stdlib.h>

typedef char const* (*PTRFUN)();

typedef struct Parrot{
    PTRFUN* vtable;
    const char* name;
} Parrot;

Parrot* create(const char* name);

void construct(Parrot* p, const char* name);

const char* name();
const char* greet();
const char* menu();

PTRFUN parrot_vtable[3] = {&name, &greet, &menu};

int size();
