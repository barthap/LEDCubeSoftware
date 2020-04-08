/*
 * Cube.cpp
 *
 * Created: 08.02.2019 16:58:22
 *  Author: Barthap
 */ 

#include <avr/io.h>
#include "Cube.h"

volatile uint8_t cube[CUBE_SIZE][CUBE_SIZE];

// Frame buffer
// Animations that take a lot of time to compute are temporarily
// stored to this array, then loaded into cube[8][8] when the image
// is ready to be displayed
volatile uint8_t fb[CUBE_SIZE][CUBE_SIZE];

volatile uint8_t current_layer;