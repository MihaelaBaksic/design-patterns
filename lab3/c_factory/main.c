#include "myfactory.h"

#include <stdio.h>
#include <stdlib.h>

typedef char const* (*PTRFUN)();

struct Animal{
  PTRFUN* vtable;
  const char* name;
  // vtable entries:
  // 0: char const* name(void* this);
  // 1: char const* greet();
  // 2: char const* menu();
};

// parrots and tigers defined in respective dynamic libraries

// animalPrintGreeting and animalPrintMenu similar as in lab 1
void animalPrintGreeting(struct Animal* a){
    printf("%s pozdravlja: %s\n", a->name, a->vtable[0]());
}

void animalPrintMenu(struct Animal* a){
    printf("%s voli %s\n", a->name, a->vtable[1]());
}


int main(int argc, char *argv[]){
  for (int i=0; i<argc; ++i){
    struct Animal* p=(struct Animal*)myfactory(argv[i], "Modrobradi");
    if (!p){
      printf("Creation of plug-in object %s failed.\n", argv[i]);
      continue;
    }

    animalPrintGreeting(p);
    animalPrintMenu(p);
    free(p); 
  }
}
