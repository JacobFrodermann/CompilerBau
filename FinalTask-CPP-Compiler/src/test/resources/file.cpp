// ========================================
// FIBONACCI
// ========================================
int fibonacci(int n) {
    if (n <= 1) {
        return n;
    }
    return fibonacci(n - 1) + fibonacci(n - 2);
}

// ========================================
// FACTORIAL
// ========================================
int factorial(int n) {
    if (n <= 1) {
        return 1;
    }
    return n * factorial(n - 1);
}

// ========================================
// FUNCTION OVERLOADING
// ========================================
int add(int a, int b) {
    return a + b;
}

int add(int a, int b, int c) {
    return a + b + c;
}

int multiply(int a, int b) {
    return a * b;
}

// ========================================
// CLASSES
// ========================================
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

    int getY() {
        return y;
    }

    void setX(int newX) {
        x = newX;
    }

    void setY(int newY) {
        y = newY;
    }

    virtual int distance() {
        return x + y;
    }

    int area() {
        return x * y;
    }
};

class Point3D : public Point {
public:
    int z;

    Point3D() {
        z = 0;
    }

    Point3D(int px, int py, int pz) {
        x = px;
        y = py;
        z = pz;
    }

    int getZ() {
        return z;
    }

    void setZ(int newZ) {
        z = newZ;
    }

    int distance() {
        return x + y + z;
    }

    int volume() {
        return x * y * z;
    }
};

// ========================================
// HELPER FUNCTIONS
// ========================================
void printSeparator() {
    print_string("====================");
}

void testArithmetic() {
    printSeparator();
    print_string("ARITHMETIC TESTS");
    printSeparator();

    int a = 10;
    int b = 5;

    print_string("Addition: 10 + 5 =");
    print_int(a + b);

    print_string("Subtraction: 10 - 5 =");
    print_int(a - b);

    print_string("Multiplication: 10 * 5 =");
    print_int(a * b);

    print_string("Division: 10 / 5 =");
    print_int(a / b);

    print_string("Modulo: 10 % 3 =");
    print_int(a % 3);
}

void testComparison() {
    printSeparator();
    print_string("COMPARISON TESTS");
    printSeparator();

    int x = 10;
    int y = 5;

    if (x > y) {
        print_string("10 > 5: true");
    }

    if (x >= y) {
        print_string("10 >= 5: true");
    }

    if (y < x) {
        print_string("5 < 10: true");
    }

    if (y <= x) {
        print_string("5 <= 10: true");
    }

    if (x == 10) {
        print_string("10 == 10: true");
    }

    if (x != y) {
        print_string("10 != 5: true");
    }
}

void testLogical() {
    printSeparator();
    print_string("LOGICAL TESTS");
    printSeparator();

    bool t = true;
    bool f = false;

    if (t && t) {
        print_string("true && true: true");
    }

    if (t || f) {
        print_string("true || false: true");
    }

    if (!f) {
        print_string("!false: true");
    }
}

void testLoops() {
    printSeparator();
    print_string("LOOP TESTS");
    printSeparator();

    print_string("Counting up to 5:");
    int i = 1;
    while (i <= 5) {
        print_int(i);
        i = i + 1;
    }

    print_string("Counting down from 5:");
    int j = 5;
    while (j > 0) {
        print_int(j);
        j = j - 1;
    }
}

void testFibonacci() {
    printSeparator();
    print_string("FIBONACCI SEQUENCE");
    printSeparator();

    print_string("First 15 Fibonacci numbers:");
    int n = 0;
    while (n < 15) {
        int fib = fibonacci(n);
        print_int(fib);
        n = n + 1;
    }
}

void testFactorial() {
    printSeparator();
    print_string("FACTORIAL TEST");
    printSeparator();

    print_string("5! =");
    print_int(factorial(5));

    print_string("7! =");
    print_int(factorial(7));

    print_string("10! =");
    print_int(factorial(10));
}

void testOverloading() {
    printSeparator();
    print_string("FUNCTION OVERLOADING");
    printSeparator();

    int result1 = add(5, 10);
    int result2 = add(1, 2, 3);

    print_string("add(5, 10) =");
    print_int(result1);

    print_string("add(1, 2, 3) =");
    print_int(result2);
}

void testClasses() {
    printSeparator();
    print_string("CLASS TESTS");
    printSeparator();

    print_string("Creating Point with default constructor:");
    Point p1;
    print_string("p1.x =");
    print_int(p1.getX());
    print_string("p1.y =");
    print_int(p1.getY());

    print_string("Setting p1.x = 3, p1.y = 4");
    p1.setX(3);
    p1.setY(4);
    print_string("p1.distance() =");
    print_int(p1.distance());
    print_string("p1.area() =");
    print_int(p1.area());

    print_string("Creating Point with constructor:");
    Point p2 = Point(5, 12);
    print_string("p2.x =");
    print_int(p2.getX());
    print_string("p2.y =");
    print_int(p2.getY());
    print_string("p2.distance() =");
    print_int(p2.distance());
}

void testInheritance() {
    printSeparator();
    print_string("INHERITANCE TESTS");
    printSeparator();

    print_string("Creating Point3D:");
    Point3D p3d;
    p3d.setX(1);
    p3d.setY(2);
    p3d.setZ(3);

    print_string("p3d.x =");
    print_int(p3d.getX());
    print_string("p3d.y =");
    print_int(p3d.getY());
    print_string("p3d.z =");
    print_int(p3d.getZ());

    print_string("p3d.distance() =");
    print_int(p3d.distance());

    print_string("p3d.volume() =");
    print_int(p3d.volume());

    print_string("Creating Point3D with constructor:");
    Point3D p3d2 = Point3D(2, 3, 4);
    print_string("p3d2.distance() =");
    print_int(p3d2.distance());
    print_string("p3d2.volume() =");
    print_int(p3d2.volume());
}

void testNestedControlFlow() {
    printSeparator();
    print_string("NESTED CONTROL FLOW");
    printSeparator();

    int i = 1;
    while (i <= 3) {
        print_string("Outer loop:");
        print_int(i);

        int j = 1;
        while (j <= 2) {
            print_string("  Inner loop:");
            print_int(j);
            j = j + 1;
        }

        i = i + 1;
    }
}

void testComplexExpression() {
    printSeparator();
    print_string("COMPLEX EXPRESSIONS");
    printSeparator();

    int a = 2;
    int b = 3;
    int c = 4;

    int result = (a + b) * c - a;
    print_string("(2 + 3) * 4 - 2 =");
    print_int(result);

    int result2 = a * b + c / a;
    print_string("2 * 3 + 4 / 2 =");
    print_int(result2);
}

// ========================================
// MAIN
// ========================================
int main() {
    print_string("##############################");
    print_string("# C++ INTERPRETER TEST SUITE #");
    print_string("##############################");

    testArithmetic();
    testComparison();
    testLogical();
    testLoops();
    testFibonacci();
    testFactorial();
    testOverloading();
    testClasses();
    testInheritance();
    testNestedControlFlow();
    testComplexExpression();

    printSeparator();
    print_string("ALL TESTS COMPLETED!");
    printSeparator();

    return 0;
}
