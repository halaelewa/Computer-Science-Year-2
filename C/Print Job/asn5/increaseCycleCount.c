/* CS2211a 2021 */
/* Assignment 05 */
/* Hala Elewa */
/* 251170694 */
/* HELEWA */
/* 12/1/2021 */
#include "headers.h"

void increaseCycle(LIST *list) {
    LIST_NODE *head = list->head;

    while (head != NULL) {
        // get document structure for the current job
        ((DOCUMENT *) head->dataPtr)->cycles++;

#if DEBUG_SHOW_CYCLES
        DOCUMENT *doc = (DOCUMENT *) head->dataPtr;
        printf("Increment Cycle - Document: %d Pages: %d Priority: %d Cycle Count: %d\n",
               doc->documentNumber, doc->pages, doc->priority, doc->cycles);
#endif

        head = head->next;
    }
}
