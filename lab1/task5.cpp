#include<iostream>

class B{
public:
  virtual int prva()=0;
  virtual int druga(int)=0;
};

class D: public B{
public:
  virtual int prva(){return 42;}
  virtual int druga(int x){return prva()+x;}
};

typedef int (*funptr_p)(B*);
typedef int (*funptr_d)(B*, int); 

void calculate(B* pb){

  //pointer to virtual table
  unsigned long vptr = *(unsigned long*)pb;

  //calling prva
  std::cout << ((funptr_p)(*(unsigned long*)(vptr)))(pb) << std::endl;

  //now pointing to druga
  vptr+=8;

  //calling druga()
  int x = 8;
  std::cout << ((funptr_d)((*(unsigned long*)(vptr))))(pb,x) << std::endl;

}


int main(void){
  
  B* pb = new D();
  calculate(pb);

}