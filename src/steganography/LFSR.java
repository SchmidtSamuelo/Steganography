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
        int size = (width * height) - 1;
        width = width-1;
        height = height-1;
        bit = LFSR & 1;
        LFSR >>= 1;
        if (bit == 1)
            LFSR = LFSR ^ mask;
        if((LFSR > size) || (LFSR < 0))
        {
            return getNextInt(width, height);
        }
        if(LFSR == val)
        {
            ResetLFSR();
            return 0;
        }
           
        return LFSR;
    }
    
    public void ResetLFSR()
    {
        LFSR = val;
        reset = 0;
    }
    }
