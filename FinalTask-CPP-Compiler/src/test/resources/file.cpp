/*
int add(int a, int b) {
    return a + b;
}

int add(int a, int b, int c) {
    return a + b + c;
}

class Point {
public:
    int x;
    int y;

    Point() {
        x = 0;
        y = 0;
    }

    Point(int px, int py) {
        x = px;
        y = py;
    }

    int getX() {
        return x;
    }

    virtual int distance() {
        return x + y;
    }
};

class Point3D : public Point {
public:
    int z;

    Point3D() {
        z = 0;
    }

    int distance() {
        return x + y + z;
    }
};
*/
int get(int i) {
return i;
}

int main() {
/*
    int result = add(5, 10);
    int result2 = add(1, 2, 3);

    Point p;
    int px = p.getX();

    if (result > 10) {
        result = result - 5;
    }

    while (result > 0) {
        result = result - 1;
    }*/

    int h = get(3);
    int w = 2;

    if (3 > 2) {
        print_string("3 > 2");
    }

    print_int(h * w);


    //return 0;
}
