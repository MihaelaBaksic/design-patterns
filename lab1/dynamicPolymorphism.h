#define N 5

// Function declarations
char const* dogGreet(void);
char const* dogMenu(void);
char const* catGreet(void);
char const* catMenu(void);
struct Animal* createDog(char const* name);
struct Animal* createCat(char const* name);
void constructDog(struct Animal* dog, char const* name);
void constructCat(struct Animal* cat, char const* name);
void testAnimals(void);
struct Animal* createNDogs(int n);


// Function pointer type definition
typedef char const* (*PTRFUN)();

//Function pointers table init
PTRFUN dogTable[2] = {&dogGreet, &dogMenu};
PTRFUN catTable[2] = {&catGreet, &catMenu};

/*
* Structure Animal
* contains ptr to function table and name
*/
struct Animal{
    char const* name;
    PTRFUN* functionTable;
};