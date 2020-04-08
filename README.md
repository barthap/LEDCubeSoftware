# LED Cube Software

This is a backup repository where I store my modified software for my **8x8x8 LED Cube**
Original comes from this tutorial:

**https://www.instructables.com/id/Led-Cube-8x8x8/**

Full credits belong to author of the tutorial. For details on how to use it also see the tutorial.

I mave made some modifications for my needs:

 - Ported microcontroller code to Atmel Studio 7 project
 - Modified some Atmega32 code to C++11 features
 - Partially ported PC software from C to Java - fully supports COM Serial Port communication,
 and a few effects including game of life. _But code is a mess and needs strong refactor though_
 - Other minor fixes which I don't remember
 
**Repository structure**
 - `LedCubeEditor` - Java PC software to serial communication with cube
 - `LedCubeHW` - Atmel Studio 7 project for Atmega32 target
 - `LedCubeHW/LedCube/Debug/*.hex` - all hex files ready to flash
   - `main_flash.hex` and `main_eeprom.eep` - original hex/eeprom files from the tutorial
   - `LedCube.hex` and `LedCube.eep` - built by Atmel Studio from my code
 