/* CS2211a 2021 */
/* Assignment 05 */
/* Hala Elewa */
/* 251170694 */
/* HELEWA */
/* 12/1/2021 */
#include "headers.h"

void freeMemory(LIST *list) {
    LIST_NODE *head = list->head;
    while (head != NULL) {
        free(head->dataPtr);
        LIST_NODE *tmp = head;
        head = head->next;
        free(tmp);
    }
}

