// Function pointer type definition
typedef double (*PTRFUN)();

typedef struct unary_function {
    int lower_bound;
    int upper_bound;
    PTRFUN* vptr;
}Unary_Function;

typedef struct square{
    int lower_bound;
    int upper_bound;
    PTRFUN* vptr;
} Square;

//the data members of the base class subobject 
//are directly stored within the derived class object
typedef struct linear{
    int lower_bound;
    int upper_bound;
    PTRFUN* vptr;
    double a;
    double b;
} Linear;


//function declarations
double negative_value_at_unary( Unary_Function * , double);
void tabulate(Unary_Function *);
static int same_functions_for_ints(Unary_Function *, Unary_Function *, double);
Unary_Function* Unary_Function_Create(int, int);
void Unary_Function_Construct( Unary_Function* , int, int);
double pure_virtual_called(double);
void Square_Construct(Square*, int, int);
Square* Square_Create(int , int);
double value_at_square(Square*, double);
void Linear_Construct(Linear*, int, int, double, double);
Linear* Linear_Create(int, int, double, double);
double value_at_linear(Linear*, double);


//Unary_Function virtual table
PTRFUN unary_function_vptr[2] = {NULL, &negative_value_at_unary};

PTRFUN square_vptr[2] = {&value_at_square, &negative_value_at_unary};
PTRFUN linear_vptr[2] = {&value_at_linear, &negative_value_at_unary};