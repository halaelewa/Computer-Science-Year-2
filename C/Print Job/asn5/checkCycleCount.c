/* CS2211a 2021 */
/* Assignment 05 */
/* Hala Elewa */
/* 251170694 */
/* HELEWA */
/* 12/1/2021 */
#include "headers.h"

void checkCycleCount(LIST *list) {
    LIST_NODE *head = list->head, *prevHead = NULL;
    int q = 0;

    while (head != NULL) {
        // get document structure for the current job
        DOCUMENT *doc = (DOCUMENT *) head->dataPtr;

        prevHead = head;

        // cycle count for current job is exceeded the MAXCYCLES
        if (doc->cycles >= MAXCYCLES) {
#if DEBUG_SHOW_EXCEEDED
            if (q == 0) {
                printf("EXCEEDED CYCLE COUNT - Document: %d   Pages: %d   Priority: %d   Cycle Count: %d\n",
                       doc->documentNumber, doc->pages, doc->priority, doc->cycles);
                q = 1;
            }
#endif
            // DO BONUS
          //  ((DOCUMENT *) head->dataPtr)->priority = 0;

         //   LIST_NODE *curr = list->head, *prev = NULL;

         //   while (curr != NULL) {
         //       DOCUMENT *currDoc = (DOCUMENT *) curr->dataPtr;

           //     if (currDoc->priority == 0)
           //         break;

          //      curr = curr->next;
          //  }

//            printf("HERE\n");
//            return;

//            if (prevHead != NULL) {
//                printf("prevHead = %d\n", ((DOCUMENT *) prevHead->dataPtr)->priority);
//                return;
//            }
           // prevHead->next = head->next;
          //  head->next = curr;
        }

        head = head->next;
    }
}
