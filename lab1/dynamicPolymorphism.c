#include <stdlib.h>
#include <stdio.h>
#include "dynamicPolymorphism.h"


void animalPrintGreeting(struct Animal* animal){
    printf("%s pozdravlja: %s\n", animal->name, animal->functionTable[0]());
};

void animalPrintMenu(struct Animal* animal){
    printf("%s voli %s\n", animal->name, animal->functionTable[1]());
};

struct Animal* createDog(char const* name){
    struct Animal* newDog = malloc(sizeof(struct Animal));
    constructDog(newDog, name);
    return newDog;
}

struct Animal* createCat(char const* name){
    struct Animal* newCat = malloc(sizeof(struct Animal));
    constructCat(newCat, name);
    return newCat;
}

void constructDog(struct Animal* dog, char const* name){
  dog->name = name;
  dog->functionTable = dogTable;
}

void constructCat(struct Animal* cat, char const* name){
  cat->name = name;
  cat->functionTable = catTable;
}


char const* dogGreet(void){
  return "vau!";
}
char const* dogMenu(void){
  return "kuhanu govedinu";
}
char const* catGreet(void){
  return "mijau!";
}
char const* catMenu(void){
  return "konzerviranu tunjevinu";
}


struct Animal* createNDogs(int n){
  struct Animal* nDogs = (struct Animal*) malloc(n* sizeof(struct Animal));
  for(int i=0; i<n; i++){
    constructDog((nDogs+i), "GenericDoggo");
  }
  return nDogs;
}


void testAnimals(void){
  struct Animal* p1=createDog("Hamlet");
  struct Animal* p2=createCat("Ofelija");
  struct Animal* p3=createDog("Polonije");

  animalPrintGreeting(p1);
  animalPrintGreeting(p2);
  animalPrintGreeting(p3);

  animalPrintMenu(p1);
  animalPrintMenu(p2);
  animalPrintMenu(p3);

  free(p1); free(p2); free(p3);

}

int main(void){
    testAnimals();

    //Stack dog creation
    struct Animal stackDog;
    constructDog(&stackDog, "StackDoggo");
    animalPrintGreeting(&stackDog);
    

    struct Animal* nDogs = createNDogs(N);
    for(int i=0; i<N; i++){
      animalPrintGreeting(nDogs+i);
    }

    return 0;
}