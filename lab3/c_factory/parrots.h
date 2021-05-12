#include <stdlib.h>

typedef char const* (*PTRFUN)();

typedef struct Parrot{
    PTRFUN* vtable;
    const char* name;
} Parrot;

Parrot* create(const char* name);

void construct(Parrot* p, const char* name);

PTRFUN parrot_vtable[2] = {&greet, &menu};

const char* greet();

const char* menu();