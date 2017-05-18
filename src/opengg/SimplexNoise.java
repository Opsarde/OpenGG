/*************************************************************
 *     file: SimplexNose.java
 *     authors: OpenGG (Shun Lu, Roenyl Tisoy, Tuan Pham, Evan Gunell)
 *     class: CS 445 - Computer Graphics
 * 
 *     assignment: program 3
 *     last modified: 5/9/2017
 * 
 *     purpose: A variation of Perlin noise, Simplex noise.
*      Simplex noise has the same purpose, but uses a simpler space-filling grid.
*      Simplex noise alleviates some of the problems with Perlin's "classic
*      noise", among them computational complexity and visually significant
*      directional artifacts.
 * 
 * 
 *************************************************************/

package opengg;

import java.util.Random;

public class SimplexNoise {

    SimplexNoise_octave[] octaves;
    double[] frequencys;
    double[] amplitudes;

    int largestFeature;
    double persistence;
    int seed;

    /**
    * METHOD: constructor
    * PURPOSE: initializes the SimplexNoise and generates a random seed
    */
    public SimplexNoise(int largestFeature,double persistence, int seed){
        this.largestFeature=largestFeature;
        this.persistence=persistence;
        this.seed=seed;

        //recieves a number (eg 128) and calculates what power of 2 it is (eg 2^7)
        int numberOfOctaves=(int)Math.ceil(Math.log10(largestFeature)/Math.log10(2));
        octaves=new SimplexNoise_octave[numberOfOctaves];
        frequencys=new double[numberOfOctaves];
        amplitudes=new double[numberOfOctaves];
        Random rnd=new Random(seed);
        for(int i=0;i<numberOfOctaves;i++){
            octaves[i]=new SimplexNoise_octave(rnd.nextInt());
            frequencys[i] = Math.pow(2,i);
            amplitudes[i] = Math.pow(persistence,octaves.length-i);
        }
    }
    
    /**
     * METHOD: getNoise
     * PURPOSE: Returns noise octave with 2 parameters
     */
    public double getNoise(int x, int y){
        double result=0;
        for(int i=0;i<octaves.length;i++){
          //double frequency = Math.pow(2,i);
          //double amplitude = Math.pow(persistence,octaves.length-i);
          result=result+octaves[i].noise(x/frequencys[i], y/frequencys[i])* amplitudes[i];
        }
        return result;

    }

    /**
     * METHOD: getNoise
     * PURPOSE: returns noise octaves with 3 parameters
     */
    public double getNoise(int x,int y, int z){
        double result=0;
        for(int i=0;i<octaves.length;i++){
          double frequency = Math.pow(2,i);
          double amplitude = Math.pow(persistence,octaves.length-i);

          result=result+octaves[i].noise(x/frequency, y/frequency,z/frequency)* amplitude;
        }
        return result;
    }
} 