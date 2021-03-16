#include <stdlib.h>
#include <stdio.h>

typedef int (*pfun)(); 

typedef struct B{
  pfun* vptr;
} B;

typedef struct D{
  pfun* vptr;
} D;



int calculate( B* this){
  int prva_rez = this->vptr[0](this);
  int druga_rez = this->vptr[1](this, 5);
  printf("Prva: %d\nDruga: %d\n", prva_rez, druga_rez);
}

int prva(B* this){
  return 42;
}

int druga(B*this, int x){
  return this->vptr[0](this) + x;
}


pfun b_vptr[2] = {NULL, NULL};
pfun d_vptr[2] = {&prva, &druga};

int main(void){

  D* d = (D*) malloc(sizeof(D));
  d->vptr = d_vptr;
  calculate((B*)d);

}