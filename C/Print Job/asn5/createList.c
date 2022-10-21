/* CS2211a 2021 */
/* Assignment 05 */
/* Hala Elewa */
/* 251170694 */
/* HELEWA */
/* 12/1/2021 */
#include "headers.h"

LIST *createList(void) {
    LIST *list;
    list = (LIST *) malloc(sizeof(LIST));

    if (list) { // if there is memory
        list->head = NULL; // make the head equal to NULL
        list->counter = 0; // the counter for the list is 0

        return list; // return the list pointer
    }

    // no memory left, return NULL
    return NULL;
}
