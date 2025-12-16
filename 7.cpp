#include <cstring>
#include <sstream>
#include <vector>

using namespace std;

class Token {
public:
    Token(const char* l, int r, int c);
    Token(const Token& other);
    Token& operator=(const Token& other);
    ~Token();
    char* lexem;
    int row;
    int col;
};

// Token.cpp
Token::Token(const char* l, int r, int c) : row(r), col(c) {
    lexem = new char[strlen(l) + 1];
    strcpy(lexem, l);
}

Token::Token(const Token& other) : row(other.row), col(other.col) {
    lexem = new char[strlen(other.lexem) + 1];
    strcpy(lexem, other.lexem);
}

Token& Token::operator=(const Token& other) {
    if (this != &other) {
        delete[] lexem;
        lexem = new char[strlen(other.lexem) + 1];
        strcpy(lexem, other.lexem);
        row = other.row;
        col = other.col;
    }
    return *this;
}

Token::~Token() { delete[] lexem; }

void tokenize(const string& input, vector<Token>& tokens) {
    istringstream iss(input);
    string line;
    int row = 0;
    
    while (getline(iss, line)) {
        istringstream lineStream(line);
        string word;
        int col = 0;
        
        while (lineStream >> word) {
            tokens.push_back(Token(word.c_str(), row, col));
            col += word.length() + 1;
        }
        row++;
    }
}

class RefCounter {
public:
    RefCounter() : n(1) {}
    void inc() { n++; }
    void dec() { n--; }
    bool isZero() const { return n == 0; }
private:
    unsigned int n;
};

class SmartToken {
public:
    SmartToken(Token* p = nullptr) : pObj(p), rc(p ? new RefCounter() : nullptr) {}
    
    SmartToken(const SmartToken& sp) : pObj(sp.pObj), rc(sp.rc) {
        if (rc) rc->inc();
    }
    
    ~SmartToken() {
        if (rc) {
            rc->dec();
            if (rc->isZero()) {
                delete pObj;
                delete rc;
            }
        }
    }
    
    SmartToken& operator=(const SmartToken& sp) {
        if (this != &sp) {
            if (rc) {
                rc->dec();
                if (rc->isZero()) {
                    delete pObj;
                    delete rc;
                }
            }
            pObj = sp.pObj;
            rc = sp.rc;
            if (rc) rc->inc();
        }
        return *this;
    }
    
    Token& operator*() { return *pObj; }
    Token* operator->() { return pObj; }
    bool operator==(const SmartToken& sp) const { return pObj == sp.pObj; }
    
private:
    Token* pObj;
    RefCounter* rc;
};

class RingBuffer {
public:
    RingBuffer(unsigned int size) : count(0), head(0), size(size) {
        elems = new SmartToken[size];
    }
    
    ~RingBuffer() { delete[] elems; }
    
    SmartToken readBuffer() {
        if (count == 0) return SmartToken();
        SmartToken result = elems[head];
        head = (head + 1) % size;
        count--;
        return result;
    }
    
    void writeBuffer(const SmartToken& data) {
        unsigned int pos = (head + count) % size;
        elems[pos] = data;
        if (count < size) {
            count++;
        } else {
            head = (head + 1) % size;
        }
    }
    
private:
    unsigned int count;
    unsigned int head;
    unsigned int size;
    SmartToken* elems;
};
