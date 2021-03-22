#include <iostream>

//#pragma pack(push)
//#pragma pack(4) // default is 8

class CoolClass{
public:
  virtual void set(int x){x_=x;};
  virtual int get(){return x_;};
private:
  int x_;
};

class PlainOldClass{
public:
  void set(int x){x_=x;};
  int get(){return x_;};
private:
  int x_;
};

int main(void){
    std::cout << "Size of CoolClass : " << sizeof(CoolClass) << std::endl;
    std::cout << "Size of PlainOldClass : " << sizeof(PlainOldClass) << std::endl;
    std::cout << "Size of pointer "<< sizeof(int*) << std::endl;
    
}

//#pragma pack(pop)