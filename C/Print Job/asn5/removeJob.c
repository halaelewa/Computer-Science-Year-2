/* CS2211a 2021 */
/* Assignment 05 */
/* Hala Elewa */
/* 251170694 */
/* HELEWA */
/* 12/1/2021 */
#include "headers.h"

void removeJob(LIST *list) {
    // remove the head (front) node from the linked list
    // by making the head equal to the node that's next to it
    list->head = list->head->next;
    list->counter--; // decrease the counter of the list jobs
}
