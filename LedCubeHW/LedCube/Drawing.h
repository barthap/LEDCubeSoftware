/*
 * Drawing.h
 *
 * Created: 08.02.2019 16:35:45
 *  Author: Barthap
 */ 


#ifndef DRAWING_H_
#define DRAWING_H_

void setvoxel(int x, int y, int z);
void clrvoxel(int x, int y, int z);
void tmpsetvoxel(int x, int y, int z);
void tmpclrvoxel(int x, int y, int z);

bool inrange(int x, int y, int z);
bool getvoxel(int x, int y, int z);
void fill(uint8_t pattern);


//...
void flpvoxel(int x, int y, int z);

void altervoxel(int x, int y, int z, int state);
void setplane_z(int z);
void clrplane_z(int z);
void setplane_x(int x);
void clrplane_x(int x);
void setplane_y(int y);
void clrplane_y(int y);

void setplane (char axis, unsigned char i);
void clrplane (char axis, unsigned char i);

void setline_z(int x, int y, int z1, int z2);
void setline_x(int z, int y, int x1, int x2);
void setline_y(int z, int x, int y1, int y2);
void clrline_z(int x, int y, int z1, int z2);
void clrline_x(int z, int y, int x1, int x2);
void clrline_y(int z, int x, int y1, int y2);
void tmpfill(unsigned char pattern);
void line(int x1, int y1, int z1, int x2, int y2, int z2);
void drawchar(char chr, int offset, int layer);
char flipbyte(char byte);
void charfly (char chr, int direction, char axis, int mode, uint16_t delay);
void strfly (char * str, int direction, char axis, int mode, uint16_t delay, uint16_t pause);
void box_filled(int x1, int y1, int z1, int x2, int y2, int z2);
void box_walls(int x1, int y1, int z1, int x2, int y2, int z2);
void box_wireframe(int x1, int y1, int z1, int x2, int y2, int z2);
char byteline (int start, int end);

void shift (char axis, int direction);

void mirror_x(void);
void mirror_y(void);
void mirror_z(void);


//utility

// Delay loop.
// This is not calibrated to milliseconds,
// but we had already made to many effects using this
// calibration when we figured it might be a good idea
// to calibrate it.
void delay_ms(uint16_t x);

// Copies the contents of fb (temp cube buffer) into the rendering buffer
void flipBuffer ();

void shift (char axis, int direction);


#endif /* DRAWING_H_ */