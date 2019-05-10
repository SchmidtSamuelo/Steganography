/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steganography;

/**
 *
 * @author Randy J Villafuerte
 */
public class LFSR {
    //val should be same length as mask in hex.
    //val is random with above criteria met.
    int val = 0xA4F;
    
    //mask: ignore the one at the end of the feedback polynomial
    int mask = 0x170;
    int LFSR = val;
    int bit;
    long reset = 0;
 
    public LFSR(){}
    
    public int getNextInt(int width, int height)
    {
//        System.out.println(LFSR);
        int size = (width * height) - 1;
        width = width-1;
        height = height-1;
//        System.out.println(size);
        bit = LFSR & 1;
        LFSR >>= 1;
        if (bit == 1)
            LFSR = LFSR ^ mask;
//        System.out.println(LFSR);
//        System.out.println(bit);
//        LFSR = (LFSR >> 1) | (bit << 15); 
        if((LFSR > size) || (LFSR < 0))
        {
            return getNextInt(width, height);
        }
        if(LFSR == val)
        {
//            System.out.println(0);
            ResetLFSR();
            return 0;
        }
           
        //System.out.println(LFSR);
        return LFSR;
    }
    
    public void ResetLFSR()
    {
        LFSR = val;
        reset = 0;
    }
    }
