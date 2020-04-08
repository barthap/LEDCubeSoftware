/* 
* VirtualDelay.h
*
* Created: 11.02.2019 09:46:29
* Author: Barthap
*/


#ifndef __VIRTUALDELAY_H__
#define __VIRTUALDELAY_H__


class VirtualDelay {
	public:
	VirtualDelay(unsigned long (*timerFunctionPtr)() = nullptr);
	void start(signed long delay);
	bool elapsed();

	bool running = false;
	unsigned long timeOut, (*timerFunctionPtr)();
};

#define DO_ONCE(x)  \
{                   \
	static bool _b; \
	if (!_b) {      \
		_b = 1;     \
		x;          \
	}               \
}


#endif //__VIRTUALDELAY_H__
