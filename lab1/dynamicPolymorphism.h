// Function declarations
char const* dogGreet(void);
char const* dogMenu(void);
char const* catGreet(void);
char const* catMenu(void);
struct Animal* createDog(char const* name);
struct Animal* createCat(char const* name);
void testAnimals(void);


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