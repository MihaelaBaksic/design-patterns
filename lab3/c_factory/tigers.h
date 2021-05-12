#include <stdlib.h>

typedef char const* (*PTRFUN)();

typedef struct Tiger{
    PTRFUN* vtable;
    const char* name;
} Tiger;

Tiger* create(const char* name);

void construct(Tiger* p, const char* name);

PTRFUN tiger_vtable[2] = {&greet, &menu};

const char* greet();

const char* menu();