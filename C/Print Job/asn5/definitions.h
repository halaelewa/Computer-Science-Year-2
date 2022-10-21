#ifndef definitions

#define PAGESPERMINUTE 1
#define MAXCYCLES 200
#define MAXPAGES 30
#define ITERATIONS 1000

// Debug series
#define DEBUG_LIST 0
#define DEBUG_ADDING 0
#define DEBUG_PRINT_PROCESS 0
#define DEBUG_SHOW_CYCLES 0
#define DEBUG_SHOW_EXCEEDED 0

typedef struct document {
    int priority;
    int pages;
    int cycles;
    int documentNumber;
} DOCUMENT;

typedef struct node {
    void *dataPtr;
    struct node *next;
} LIST_NODE;

typedef struct list {
    LIST_NODE *head;
    int counter;
} LIST;

// functions declarations/prototypes
LIST *createList(void);

DOCUMENT *createDocument();

void removeJob(LIST *list);

void printJob(LIST_NODE *job);

void increaseCycle(LIST *list);

void checkCycleCount(LIST *list);

void addJob(LIST *list, void *itemPtr);

void freeMemory(LIST *list);

#endif


