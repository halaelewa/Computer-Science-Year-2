/* CS2211a 2021 */
/* Assignment 05 */
/* Hala Elewa */
/* 251170694 */
/* HELEWA */
/* 12/1/2021 */
#include "headers.h"

int main() {

    srand(time(NULL));

    LIST *list = createList();

    int iterations = ITERATIONS;

    LIST_NODE *active = NULL;

    while (iterations--) {

        int chance = 1 + rand() % 10; // generate the chance to add a nwe job

        // check if the chance 10% is hit
        // if so, insert the job into the queue jobs (LinkedList)
        if (chance == 1) {

            DOCUMENT *doc = createDocument();

            addJob(list, doc);

#if DEBUG_ADDING
            printf("\nAdding to Queue - Doc: %d NoPages: %d Priority: %d\n",
                   doc->documentNumber, doc->pages, doc->priority);
#endif
        }

        if (active == NULL) {
            if (list->counter) {
                active = list->head;
                removeJob(list);
            } else {
#if DEBUG_LIST
                printf("EMPTY QUEUE - no print jobs currently in queue\n");
#endif
            }
        } else {
            // print the job
            printJob(active);

            DOCUMENT *activeDoc = (DOCUMENT *) active->dataPtr;
            if (activeDoc->pages >= 0) {
#if DEBUG_PRINT_PROCESS
                printf("PRINTING - DOCUMENT: %d   PAGE: %d   Priority: %d\n",
                       activeDoc->documentNumber, activeDoc->pages, activeDoc->priority);
#endif
            }

            // all pages have been printed
            if (activeDoc->pages == 0) {
                printf("Print Job Completed - Document Number:  %d - Cycle Count:  %d\n",
                       activeDoc->documentNumber, activeDoc->cycles);

                active = NULL; // remove the job from printer
            }
        }

        if (list->counter == 0) {
#if DEBUG_LIST
            printf("EMPTY QUEUE - no print jobs currently in queue\n");
#endif
        } else { // there is jobs in the queue, print out the list
#if DEBUG_LIST
            printf("\nCurrent Printer Queue Size: %d\n", list->counter);
            LIST_NODE *tmp = list->head;

            while (tmp != NULL) {
                DOCUMENT *doc = (DOCUMENT *) tmp->dataPtr;
                printf("Current Printer Queue : DocNum %d  NumOfPages %d  PriorityLevel %d  NumOfCycles %d\n",
                       doc->documentNumber, doc->pages, doc->priority, doc->cycles);
                tmp = tmp->next;
            }

            if (list->counter)
                printf("END OF LIST\n\n");
#endif
        }

        // increase the cycle count for each job in the list
        increaseCycle(list);

        // check if any job cycle count has exceeded the MAXCYCLE
        checkCycleCount(list);
    }

    printf("\nEnd of Program - * HALA ELEWA *\n");
    printf("Number of printer jobs left in queue: %d\n", list->counter);

    // free the memory
    freeMemory(list);

    return 0;
}

