#include <stdlib.h>

typedef char const* (*PTRFUN)();

typedef struct Tiger{
    PTRFUN* vtable;
    const char* name;
} Tiger;

Tiger* create(const char* name);

void construct(Tiger* p, const char* name);

const char* name();
const char* greet();
const char* menu();

PTRFUN tiger_vtable[3] = {&name, &greet, &menu};

int size();