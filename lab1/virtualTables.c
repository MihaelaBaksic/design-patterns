#include<stdlib.h>
#include<stdio.h>
#include "virtualTables.h"

double negative_value_at_unary( Unary_Function *this, double x){
    return -(this->vptr[0](this, x));
    
}

void tabulate(Unary_Function *this){
    for(int x = this->lower_bound; x <= this->upper_bound; x++) {
        printf("f(%d)=%lf\n", x, this->vptr[0](this, (double)x));
    }
}

static int same_functions_for_ints(Unary_Function *f1, Unary_Function *f2, double tolerance) {
    if(f1->lower_bound != f2->lower_bound) return 0;
    if(f1->upper_bound != f2->upper_bound) return 0;
    for(int x = f1->lower_bound; x <= f1->upper_bound; x++) {
    double delta = f1->vptr[0](f1, (double) x) - f2->vptr[0](f2, (double) x);
    if(delta < 0) delta = -delta;
    if(delta > tolerance) return 0;
    }
    return 1;
};

Unary_Function* Unary_Function_Create(int lb, int ub){
    Unary_Function *this = (Unary_Function*)malloc(sizeof(Unary_Function));
    Unary_Function_Construct(this, lb, ub);
    return this;
}

//Handles setting of vptr and given arguments
void Unary_Function_Construct( Unary_Function* this, int lb, int ub){
    this->lower_bound = lb;
    this->upper_bound = ub;
    this->vptr = unary_function_vptr;
}

double pure_virtual_called(double x){
    exit(1);
}

void Square_Construct(Square* this, int lb, int ub){
    Unary_Function_Construct((Unary_Function*) this, lb, ub);
    this->vptr = square_vptr;
}

Square* Square_Create(int lb, int ub){
    Square* this = (Square*) malloc(sizeof(Square));
    Square_Construct(this, lb, ub);
    return this;
}

double value_at_square(Square* this, double x){
    return x*x;
}

void Linear_Construct(Linear* this, int lb, int ub, double a_coef, double b_coef){
    Unary_Function_Construct((Unary_Function*) this, lb, ub);
    this->a = a_coef;
    this->b = b_coef;
    this->vptr = linear_vptr;
}

Linear* Linear_Create(int lb, int ub, double a_coef, double b_coef){
    Linear* this = (Linear*) malloc(sizeof(Linear));
    Linear_Construct(this, lb, ub, a_coef, b_coef);
    return this;
}

double value_at_linear(Linear* this, double x){
    return this->a*x + this->b;
}

int main(){

    Unary_Function* f1 = (Unary_Function*) Square_Create(-2, 2);
    tabulate(f1);

    Unary_Function* f2 = (Unary_Function*) Linear_Create(-2, 2, 5, -2);
    tabulate(f2);

    printf("f1==f2: %s\n", same_functions_for_ints(f1, f2, 1E-6) ? "DA" : "NE");
    printf("neg_val f2(1) = %lf\n", negative_value_at_unary(f2, 1.0));

    free(f1);
    free(f2);
}



