/* CS2211a 2021 */
/* Assignment 05 */
/* Hala Elewa */
/* 251170694 */
/* HELEWA */
/* 12/1/2021 */
#include "headers.h"

void addJob(LIST *list, void *itemPtr) {
    // allocate node
    LIST_NODE *newNode;
    newNode = (LIST_NODE *) malloc(sizeof(LIST_NODE));

    // put in the data
    newNode->dataPtr = itemPtr;

    list->counter++; // increase the counter

    // change old prev of head node to new node, nothing if it is the first entry
    if (list->head == NULL) {
        newNode->next = NULL;
        list->head = newNode;
    }

    else { // find the right place to place the new node

        /*
         * curr node: keep track of the current node
         * prev node: keep track of the previous node
         * */
        LIST_NODE *curr = list->head, *prev = NULL;

        int q = 0; // flag, the node to be inserted has a duplicated priority

        while (curr != NULL) {

            DOCUMENT *currDoc = (DOCUMENT *) curr->dataPtr; // document structure of the current node
            DOCUMENT *newNodeDoc = (DOCUMENT *) newNode->dataPtr; // document structure of the new node

            // priority of the current node is greater than the new node priority, so add it here
            if (currDoc->priority > newNodeDoc->priority)
                break;

            // priority is the same for the current node and the new node, so look for the pages
            else if (currDoc->priority == newNodeDoc->priority) {
                LIST_NODE *tmp = curr;

                // as long as the priority still the same
                while (tmp != NULL && currDoc->priority == ((DOCUMENT*) tmp->dataPtr)->priority) {
                    // number of pages of the current node is smaller the new node page numbers
                    // add the new node here
                    if (((DOCUMENT*) tmp->dataPtr)->pages > newNodeDoc->pages)
                        break;

                    prev = tmp;
                    tmp = tmp->next;
                }

                // node is the last element
                if (tmp == NULL) {
                    newNode->next = NULL;
                    if (prev != NULL)
                        prev->next = newNode;
//                    else {
//                        newNode->next = list->head;
//                        list->head = newNode;
//                    }
                }

                else {
                    newNode->next = tmp;
                    if (prev != NULL)
                        prev->next = newNode;
                    else {
                        newNode->next = list->head;
                        list->head = newNode;
                    }
                }

                q = 1;
                break;
            }

            prev = curr;

            // priority of the current node is less than the new node priority, so go to the next node
            curr = curr->next;
        }

        if (q == 0) {
            // the node is the last node
            if (curr == NULL) {
                newNode->next = NULL;
                prev->next = newNode;
            } else {
                if (prev == NULL) {
                    newNode->next = list->head;
                    list->head = newNode;
                } else {
                    newNode->next = curr;
                    prev->next = newNode;
                }
            }
        }
    }
}
