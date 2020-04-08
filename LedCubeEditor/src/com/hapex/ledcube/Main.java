package com.hapex.ledcube;

import com.hapex.ledcube.logging.Log;
import com.hapex.ledcube.serial.SerialManager;
import java.lang.Math;
import java.util.Arrays;
import java.util.Random;

public class Main {
    private static byte[][] cube, tmpBuf;
    private volatile static byte[][] rs232_cube;

    public static void main(String[] args) {
        SerialManager serialManager = new SerialManager("COM4");

        serialManager.start();

        cube = new byte[8][8];
        rs232_cube = new byte[8][8];

        Thread cubeUpdater = new Thread(() -> {
            while (true) {
                serialManager.sendCube(rs232_cube);
            }
        }, "cubeUpdater");

        cubeUpdater.start();

        /*while (true) {
            //ripples(2000,10);
            //effect_loadbar(100);
            fireworks(7,50,48);
        }*/
        //fireworks(7,50,48);
        //ripples(2000,10);
        //effect_loadbar(100);

        for(int i = 0; i < 20; i++) {
            Log.debug("Game of life iteration ", Integer.toString(i));
            gol_play(20, 500);
        }



        cubeUpdater.interrupt();
        cubeUpdater.stop();
        try {
            cubeUpdater.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Log.debug("Done");


        final byte[] msg = {(byte)0xFF,(byte)0x00, (byte)0xDE, (byte)0xAD, (byte)0xBE, (byte)0xEF};
        serialManager.sendRaw(msg);

        Log.debug(cubeUpdater.getName(), String.valueOf(cubeUpdater.isAlive()));

        serialManager.finish();
    }

    private static void delayMs(int ms) {

        synchronized (rs232_cube) {
            rs232_cube = Arrays.copyOf(cube, cube.length);
        }

        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    static void fireworks (int iterations, int n, int delay)
    {
        Random gen = new Random();

        fill(0x00);

        int i,f,e;

        double origin_x = 3;
        double origin_y = 3;
        double origin_z = 3;

        int rand_y, rand_x, rand_z;

        double slowrate, gravity;

        // Particles and their position, x,y,z and their movement, dx, dy, dz
        double[][] particles = new double[n][6];

        for (i=0; i<iterations; i++)
        {

            origin_x = gen.nextInt(3);
            origin_y = gen.nextInt(3);
            origin_z = gen.nextInt(1);
            origin_z +=6;
            origin_x +=2;
            origin_y +=2;

            // shoot a particle up in the air
            for (e=0;e<origin_z;e++)
            {
                setVoxel((int)origin_x,(int)origin_y,e);
                delayMs(30+25*e);
                fill(0x00);
            }

            // Fill particle array
            for (f=0; f<n; f++)
            {
                // Position
                particles[f][0] = origin_x;
                particles[f][1] = origin_y;
                particles[f][2] = origin_z;

                rand_x = gen.nextInt(199);
                rand_y = gen.nextInt(199);
                rand_z = gen.nextInt(199);

                // Movement
                particles[f][3] = 1-(double)rand_x/100; // dx
                particles[f][4] = 1-(double)rand_y/100; // dy
                particles[f][5] = 1-(double)rand_z/100; // dz
            }

            // explode
            for (e=0; e<25; e++)
            {
                slowrate = 1+Math.tan((e+0.1)/20)*10;

                gravity = Math.tan((e+0.1)/20)/2;

                for (f=0; f<n; f++)
                {
                    particles[f][0] += particles[f][3]/slowrate;
                    particles[f][1] += particles[f][4]/slowrate;
                    particles[f][2] += particles[f][5]/slowrate;
                    particles[f][2] -= gravity;

                    setVoxel((int)particles[f][0],(int)particles[f][1],(int)particles[f][2]);


                }

                delayMs(delay);
                fill(0x00);
            }

        }

    }

    // Display a sine wave running out from the center of the cube.
    private static void ripples (int iterations, int delay)
    {
        double origin_x, origin_y, distance, height, ripple_interval;
        int x,y,i;

            fill(0x00);

            for (i = 0; i < iterations; i++) {

                for (x = 0; x < 8; x++) {
                    for (y = 0; y < 8; y++) {
                        distance = distance2d(3.5, 3.5, x, y) / 9.899495 * 8;
                        //distance = distance2d(3.5,3.5,x,y);
                        ripple_interval = 1.3;
                        height = 4 + Math.sin(distance / ripple_interval + (double) i / 50) * 4;

                        setVoxel(x, y, (int) height);
                    }
                }
                delayMs(delay);
                fill(0x00);
            }

    }

    private static void effect_loadbar(int delay)
    {
        //fill(0x00);

        int z,y;

        for (z=0;z<8;z++)
        {
            for (y=0;y<8;y++)
                cube[z][y] = (byte)0xff;

            delayMs(delay);
        }

        delayMs(delay*3);

        for (z=0;z<8;z++)
        {
            for (y=0;y<8;y++)
                cube[z][y] = 0x00;

            delayMs(delay);
        }
    }

    private static double distance2d (double x1, double y1, double x2, double y2)
    {
        return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
    }

    private static boolean inrange(int x, int y, int z)
    {
        // One of the coordinates was outside the cube.
        return x >= 0 && x < 8 && y >= 0 && y < 8 && z >= 0 && z < 8;
    }

    private static void setVoxel(int x, int y, int z) {
        if(inrange(x,y,z))
            cube[z][y] |= (1 << x);
    }

    private static void clrVoxel(int x, int y, int z) {
        if(inrange(x,y,z))
            cube[z][y] &= ~(1 << x);
    }
    private static boolean getVoxel(int x, int y, int z) {
        if(inrange(x,y,z))
            return (cube[z][y] & (1<<x)) > 0;
        else return false;
    }

    private static boolean isAnyLit() {
        for(int y = 0; y < 8; y++)
            for(int z = 0; z < 8; z++)
                if(cube[z][y] > 0) return true;
        return false;
    }

    private static void fill(byte pattern) {
        for(int z = 0; z < 8; z++)
            for(int y = 0; y < 8; y++)
                cube[z][y] = pattern;
    }
    private static void fill(int pattern) {
        fill((byte)pattern);
    }

    private static final int GOL_CREATE_MIN = 4;
    private static final int GOL_CREATE_MAX = 4;

    // Underpopulation
    private static final int GOL_TERMINATE_LONELY = 3;
    // Overpopulation
    private static final int GOL_TERMINATE_CROWDED = 6;

    private static final int GOL_X = 8;
    private static final int GOL_Y = 8;
    private static final int GOL_Z = 8;

    private static final int GOL_WRAP = 0;

    static void gol_play (int iterations, int delay)
    {
        int i = 0;
        Random gen = new Random();

        fill(0x00);
        /*for (i = 0; i < 20;i++)
        {
            setVoxel(gen.nextInt(4),gen.nextInt(4),gen.nextInt(4));
        }*/

        //Blinker 1
        /*setVoxel(1,1,1);
        setVoxel(2,1,1);
        setVoxel(1,2,1);
        setVoxel(2,2,2);*/

        setVoxel(4,4,4);

        setVoxel(3,4,4);
        setVoxel(5,4,4);
        setVoxel(4,3,4);
        setVoxel(4,4,4);
        setVoxel(4,4,4);
        setVoxel(4,4,4);


        delayMs(400);

        boolean isAlive = true;
        boolean hasChanges = true;
        //for (i = 0; i < iterations; i++)

        i = 1;
        while(isAlive && hasChanges)
        {
            isAlive = gol_nextgen();
            hasChanges = gol_has_changes();
            tmpBuf = Arrays.stream(cube).map(byte[]::clone).toArray(byte[][]::new);
            delayMs(delay);
            i++;
        }

        Log.info("Life died after ", Integer.toString(i), " generations");
        fill(0x00);
        delayMs(1000);
    }

    static boolean gol_nextgen ()
    {
        int x,y,z;

        //fill(0x00);
        int[][][] neigh = new int[GOL_X][GOL_Y][GOL_Z];

        for (x = 0; x < GOL_X; x++)
            for (y = 0; y < GOL_Y; y++)
                for (z = 0; z < GOL_Z; z++)
                    neigh[x][y][z] = gol_count_neighbors(x, y, z);

        for (x = 0; x < GOL_X; x++)
        {
            for (y = 0; y < GOL_Y; y++)
            {
                for (z = 0; z < GOL_Z; z++)
                {
                    // Current voxel is alive.
                    if (getVoxel(x,y,z))
                    {
                        if (neigh[x][y][z] <= GOL_TERMINATE_LONELY)
                        {
                            clrVoxel(x,y,z);
                        } else if (neigh[x][y][z] >= GOL_TERMINATE_CROWDED)
                        {
                            clrVoxel(x,y,z);
                        } else
                        {
                            setVoxel(x,y,z);
                        }
                        // Current voxel is dead.
                    } else
                    {
                        if (neigh[x][y][z] >= GOL_CREATE_MIN && neigh[x][y][z] <= GOL_CREATE_MAX)
                            setVoxel(x,y,z);
                    }
                }
            }
        }

        return isAnyLit();
    }

    static int gol_count_neighbors (int x, int y, int z)
    {
        int ix, iy, iz; // offset 1 in each direction in each dimension
        int nx, ny, nz; // neighbours address.

        int neigh = 0; // number of alive neighbours.

        for (ix = -1; ix < 2; ix++)
        {
            for (iy = -1; iy < 2; iy++)
            {
                for (iz = -1; iz < 2; iz++)
                {
                    // Your not your own neighbour, exclude 0,0,0, offset.
                    if ( !(ix == 0 && iy == 0 && iz == 0) )
                    {
                        if (GOL_WRAP == 0x01)
                        {
                            nx = (x+ix)%GOL_X;
                            ny = (y+iy)%GOL_Y;
                            nz = (z+iz)%GOL_Z;
                        } else
                        {
                            nx = x+ix;
                            ny = y+iy;
                            nz = z+iz;
                        }

                        if ( getVoxel(nx, ny, nz) )
                            neigh++;
                    }
                }
            }
        }
        return neigh;
    }

    static boolean gol_has_changes()
    {
        boolean result = Arrays.deepEquals(tmpBuf, cube);
       return !result;
       /* int x, y, z;
        for (x = 0; x < GOL_X; x++)
        {
            for (y = 0; y < GOL_Y; y++) {
                for (z = 0; z < GOL_Z; z++) {
                    if (getVoxel(x, y, z) != (tmpBuf[z][y] & (1 << x)) > 0)
                        return true;
                }
            }
        }
        return false;*/
    }
}
