/*
 * Cube.h
 *
 * Created: 08.02.2019 16:37:08
 *  Author: Barthap
 */ 


#ifndef CUBE_H_
#define CUBE_H_

#include "globals.h"

// Some of the functions are created to be portable
// These functions will work on cubes of different sizes by
// changing this constant
#define CUBE_SIZE 8
#define CUBE_BYTES 64

// If you change this to anything greater than 8, you also have
// change how the cube buffer works and probably all the functions
// in draw.c

// Cube buffer
// Data from this array is loaded onto the cube for each duty cycle
extern volatile uint8_t cube[CUBE_SIZE][CUBE_SIZE];

// Frame buffer
// Animations that take a lot of time to compute are temporarily
// stored to this array, then loaded into cube[8][8] when the image
// is ready to be displayed
extern volatile uint8_t fb[CUBE_SIZE][CUBE_SIZE];

// Some effects can render on different axis
// for example send pixels along an axis
// for better readability, we use the following predefined constants
#define AXIS_X 0x78
#define AXIS_Y 0x79
#define AXIS_Z 0x7a


extern volatile uint8_t current_layer;


#endif /* CUBE_H_ */