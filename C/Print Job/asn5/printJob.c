/* CS2211a 2021 */
/* Assignment 05 */
/* Hala Elewa */
/* 251170694 */
/* HELEWA */
/* 12/1/2021 */
#include "headers.h"

void printJob(LIST_NODE *job) {
    // print the job (decrease the pages by PAGESPERMINUTE
    ((DOCUMENT*) job->dataPtr)->pages -= PAGESPERMINUTE;
}

