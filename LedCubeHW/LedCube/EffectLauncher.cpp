/*
 * effect_launcher.cpp
 *
 * Created: 08.02.2019 16:35:11
 *  Author: Barthap
 */ 
#include "globals.h"
#include "Cube.h"
#include "EffectLauncher.h"
#include "Drawing.h"
#include "Effects.h"
#include "GameOfLife.h"

void EffectLauncher::launch (const EffectId effect_id)
{

	unsigned char ii;

	fill(0x00);

	switch (effect_id)
	{
		case EffectId::RAIN:
		effect_rain(100);
		break;
		
		
		case EffectId::RANDOM_Z:
		sendvoxels_rand_z(20,220,2000);
		break;
		
		case EffectId::ARROW_SPIN:
		effect_smileyspin(2,1000,2);	//arrow spin
		break;
				
		case EffectId::RANDOM_FILLER:
		effect_random_filler(5,1);
		effect_random_filler(5,0);
		effect_random_filler(5,1);
		effect_random_filler(5,0);
		break;
		
		case EffectId::Z_UPDOWN:
		effect_z_updown(15,1000);
		break;
		
		case EffectId::WORMSQUEEZE:
		effect_wormsqueeze (2, AXIS_Z, -1, 100, 1000);
		break;
		
		case EffectId::BLINKY2:
		effect_blinky2();
		break;
		
		case EffectId::BOX_WIREFRAME:
		for (ii=0;ii<8;ii++)
		{
			effect_box_shrink_grow (1, ii%4, ii & 0x04, 450);
		}

		case EffectId::PATH_BITMAP_HEART:
		effect_path_bitmap(700,6,3);
		break;

		effect_box_woopwoop(800,0);
		effect_box_woopwoop(800,1);
		effect_box_woopwoop(800,0);
		effect_box_woopwoop(800,1);
		break;
		
		case EffectId::PLANBOING:
		effect_planboing (AXIS_Z, 400);
		effect_planboing (AXIS_X, 400);
		effect_planboing (AXIS_Y, 400);
		effect_planboing (AXIS_Z, 400);
		effect_planboing (AXIS_X, 400);
		effect_planboing (AXIS_Y, 400);
		fill(0x00);
		break;
		
		case EffectId::TELCSTAIRS:
		fill(0x00);
		effect_telcstairs(0,800,0xff);
		effect_telcstairs(0,800,0x00);
		effect_telcstairs(1,800,0xff);
		effect_telcstairs(1,800,0xff);
		break;
		
		case EffectId::AXIS_RANDSUSPEND:
		effect_axis_updown_randsuspend(AXIS_Z, 550,5000,0);
		effect_axis_updown_randsuspend(AXIS_Z, 550,5000,1);
		effect_axis_updown_randsuspend(AXIS_Z, 550,5000,0);
		effect_axis_updown_randsuspend(AXIS_Z, 550,5000,1);
		effect_axis_updown_randsuspend(AXIS_X, 550,5000,0);
		effect_axis_updown_randsuspend(AXIS_X, 550,5000,1);
		effect_axis_updown_randsuspend(AXIS_Y, 550,5000,0);
		effect_axis_updown_randsuspend(AXIS_Y, 550,5000,1);
		break;
		
		case EffectId::LOADBAR:
		effect_loadbar(700);
		break;
		
		case EffectId::SMILEY_SPIN:
		effect_smileyspin(2,1000,0);	//smiley spin
		break;
		
		case EffectId::WORMSQUEEZE2:
		effect_wormsqueeze (1, AXIS_Z, 1, 100, 1000);
		break;
		
		case EffectId::BOXSIDE_RANDSEND_PARALEEL:
		effect_boxside_randsend_parallel (AXIS_Z, 0 , 200,1);
		delay_ms(1500);
		effect_boxside_randsend_parallel (AXIS_Z, 1 , 200,1);
		delay_ms(1500);
		
		effect_boxside_randsend_parallel (AXIS_Z, 0 , 200,2);
		delay_ms(1500);
		effect_boxside_randsend_parallel (AXIS_Z, 1 , 200,2);
		delay_ms(1500);
		
		effect_boxside_randsend_parallel (AXIS_Y, 0 , 200,1);
		delay_ms(1500);
		effect_boxside_randsend_parallel (AXIS_Y, 1 , 200,1);
		delay_ms(1500);
		break;
		
		case EffectId::PATH_BITMAP:
		effect_path_bitmap(700,2,3);
		break;
		
		case EffectId::BOINGBOING:
		boingboing(250, 600, 0x01, 0x02);
		break;
		
		case EffectId::WORMSQUEEZE3:
		effect_wormsqueeze (1, AXIS_Z, -1, 100, 1000);
		break;
		
		case EffectId::RANDOM_SPARKLE:
		effect_random_sparkle();
		break;
		
		case EffectId::WORMSQUEEZE4:
		effect_wormsqueeze (1, AXIS_Z, -1, 100, 1000);
		break;
		
		case EffectId::BOINGBOING2:
		boingboing(250, 600, 0x01, 0x03);
		break;
		
		case EffectId::STRINGFLY2:
		effect_stringfly2(const_cast<char*>("REGINA"));
		break;
		
		case EffectId::HEART_SPIN:
		effect_smileyspin(2,1000,6);	//heart spin
		break;
		
		case EffectId::PATH_AROUND:
		effect_rand_patharound(200,500);
		break;
		
		case EffectId::PATH_SPIRAL:
		effect_pathspiral(100,500);
		break;
		
		case EffectId::GAME_OF_LIFE:
		gol_play(20, 400);
		break;
		
		// In case the effect number is out of range:
		default:
		break;
		
		

	}
}

