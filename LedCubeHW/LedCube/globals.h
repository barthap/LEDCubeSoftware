/*
 * globals.h
 *
 * Created: 08.02.2019 16:25:34
 *  Author: Barthap
 */ 


#ifndef GLOBALS_H_
#define GLOBALS_H_

#include <avr/io.h>

#ifndef F_CPU
#define F_CPU 14745600
#endif

#define BAUD 115200
#define MYUBRR (((((F_CPU * 10) / (16L * BAUD)) + 5) / 10) - 1)


#define DATA_BUS PORTA
#define LAYER_SELECT PORTC
#define LATCH_ADDR PORTB
#define LATCH_MASK 0x07
#define LATCH_MASK_INV 0xf8
#define OE_PORT PORTB
#define OE_MASK 0x08

#define LED_PIN PB3

//using byte = uint8_t;
//typedef uint8_t byte;


#endif /* GLOBALS_H_ */