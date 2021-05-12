#include "myfactory.h"

#include <stdio.h>
#include <stdlib.h>

typedef char const* (*PTRFUN)();

typedef struct Animal{
  PTRFUN* vtable;
  // vtable entries:
  // 0: char const* name(void* this);
  // 1: char const* greet();
  // 2: char const* menu();
} Animal;

// animalPrintGreeting and animalPrintMenu similar as in lab 1
void animalPrintGreeting( Animal* a){
    printf("%s pozdravlja: %s\n", a->vtable[0](a), a->vtable[1]());
}

void animalPrintMenu( Animal* a){
    printf("%s voli %s\n", a->vtable[0](a), a->vtable[2]());
}


int main(int argc, char *argv[]){
  for (int i=1; i<argc; ++i){
    printf("%s\n", argv[i]);
    Animal* p=( Animal*)myfactory(argv[i], "Modrobradi");

    if (!p){
      printf("Creation of plug-in object %s failed.\n", argv[i]);
      continue;
    }

    animalPrintGreeting(p);
    animalPrintMenu(p);
    free(p); 
  }
}
