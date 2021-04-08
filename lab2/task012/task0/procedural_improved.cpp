#include <iostream>
using namespace std;

typedef struct Point{
    int x;
    int y;
} Point;

class Shape{
public:
    virtual void draw() = 0;
    virtual void move(int x_shift, int y_shift) = 0;
};

class Circle : public Shape{

public:
    double radius_;
    Point center_;
    
    virtual void draw(){
        cerr << "Drawing Circle" << endl;
    }

    virtual void move(int x_shift, int y_shift){
        this->center_.x += x_shift;
        this->center_.y += y_shift;
        cerr << "Moving Circle" << endl;
    }
};

class Square : public Shape{

public:
    double side_;
    Point center_;

    virtual void draw(){
        cerr << "Drawing Square" << endl;
    }

    virtual void move(int x_shift, int y_shift){
        this->center_.x += x_shift;
        this->center_.y += y_shift;
        cerr << "Moving Square" << endl;
    }
};

class Rhomb : public Shape{

    double side_;
    double diagonal_e_;
    double diagonal_f_;
    double angle_;
    Point point_;

    virtual void draw(){
        cerr << "Drawing Rhomb" << endl;
    }

    virtual void move(int x_shift, int y_shift){
        this->point_.x += x_shift;
        this->point_.y += y_shift;
        cerr << "Moving Rhomb" << endl;
    }
};

void drawShapes(Shape** shapes, int n){
    for(int i=0; i<n; i++){
        shapes[i]->draw();
    }
}

void moveShapes(Shape** shapes, int n, int x_shift, int y_shift){
    for(int i=0; i<n; i++){
        shapes[i]->move(x_shift, y_shift);
    }
}

int main(){
        Shape* shapes[5];
    shapes[0]=(Shape*)new Circle;
    shapes[1]=(Shape*)new Square;
    shapes[2]=(Shape*)new Square;
    shapes[3]=(Shape*)new Circle;
    shapes[4]=(Shape*)new Rhomb;


    drawShapes(shapes, 5);
    moveShapes(shapes, 5, 2, -3);
}