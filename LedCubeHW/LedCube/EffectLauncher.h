/*
 * effect_launcher.h
 *
 * Created: 08.02.2019 16:33:19
 *  Author: Barthap
 */ 


#ifndef EFFECT_LAUNCHER_H_
#define EFFECT_LAUNCHER_H_


// Total number of effects
// Used in the main loop to loop through all the effects one by bone.
// Set this number one higher than the highest number inside switch()
// in launch_effect() in launch_effect.c
constexpr uint8_t EFFECTS_TOTAL = 26;	//27 but dont use game of life

enum class EffectId : uint8_t
{
	RAIN = 0,
	RANDOM_Z,
	ARROW_SPIN,
	RANDOM_FILLER,
	Z_UPDOWN,
	WORMSQUEEZE,
	BLINKY2,
	BOX_WIREFRAME,
	PATH_BITMAP_HEART,
	PLANBOING,
	TELCSTAIRS,
	AXIS_RANDSUSPEND,
	PATH_SPIRAL,
	LOADBAR,
	SMILEY_SPIN,
	WORMSQUEEZE2,
	BOXSIDE_RANDSEND_PARALEEL,
	PATH_BITMAP,
	BOINGBOING,
	WORMSQUEEZE3,
	RANDOM_SPARKLE,
	BOINGBOING2,
	STRINGFLY2,
	HEART_SPIN,
	PATH_AROUND,
	WORMSQUEEZE4,
	GAME_OF_LIFE = 26
};

class EffectLauncher
{
public:
	void launch(const EffectId effect_id);
};



#endif /* EFFECT_LAUNCHER_H_ */