/* CS2211a 2021 */
/* Assignment 05 */
/* Hala Elewa */
/* 251170694 */
/* HELEWA */
/* 12/1/2021 */
#include "headers.h"

static int counter = 0;

DOCUMENT *createDocument() {
    int priority = 1 + rand() % 10; // generate a random priority

    /* assign the priority as follows:
     * 10% - high priority, represented by 1
     * 70% - average priority, represented by 2
     * 20% - low priority, represented by 3
     */
    if (priority >= 1 && priority <= 7) // average priority
        priority = 2;
    else if (priority == 8) // high priority
        priority = 1;
    else // low priority
        priority = 3;

    DOCUMENT *doc = (DOCUMENT *) malloc(sizeof(DOCUMENT));

    doc->priority = priority;
    doc->documentNumber = ++counter;
    doc->cycles = 1;
    doc->pages = 1 + rand() % MAXPAGES;

    return doc;
}
