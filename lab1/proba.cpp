#include<iostream>

class Base{
    public:
        virtual void printaj(){
            std::cout << "I am base class" << std::endl;
        }
};

class Derived : public Base{
    public:
        virtual void printaj(){
            std::cout << "I am derived class" << std::endl;
        }
};

int main(){
    Base baseStatic = *(new Derived());

    Base* basePtr = new Derived();

    baseStatic.printaj();
    basePtr->printaj();
}