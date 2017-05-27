package com.metro.bhargavisriram.metroapplication;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import org.apache.commons.codec.binary.Base64;

import java.io.WriteAbortedException;
import java.security.SecureRandom;
import java.util.Hashtable;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Bhargavi on 01-04-2017.
 */

public class showTicket extends Activity {
    byte[] skey = new byte[1000];
    String skeyString,rawkey;
    static byte[] raw;
    String inputMessage,encryptedData,encryptedData2,decryptedMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_activity);
        try {
           // generateSymmetricKey();
            SharedPreferences sharedPreferences;
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            inputMessage=sharedPreferences.getString("phone", "-100");
            byte[] ibyte = inputMessage.getBytes();
            String keyis="[B@129fa2aa";
            raw=keyis.getBytes();
            byte[] ebyte=encrypt(raw, ibyte);
            //Toast.makeText(getApplicationContext(),raw.toString(),Toast.LENGTH_LONG).show();

        }
        catch(Exception e) {
            System.out.println(e);
        }
        ImageView tv1;
        tv1= (ImageView) findViewById(R.id.imageView);
        Bitmap bitmap1 = null,bitmap2=null;
        try {

            long m=Long.parseLong(inputMessage)+Long.parseLong("8");
         //   Toast.makeText(getApplicationContext(),inputMessage+"::"+m+"::"+String.valueOf(m),Toast.LENGTH_LONG).show();
            encryptedData=String.valueOf(m);

            byte[]   bytesEncoded = Base64.encodeBase64(encryptedData .getBytes());
       //   Toast.makeText(getApplicationContext(),encryptedData,Toast.LENGTH_LONG).show();

            //bitmap1 = generateQrCode(encryptedData);
            bitmap1 = generateQrCode(new String(bytesEncoded ));
        } catch (WriteAbortedException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        }
        tv1.setImageBitmap(bitmap1);

    }
    public void generateSymmetricKey() {
        try {
            Random r = new Random();
            // int num = r.nextInt(10000);
            int num = 234567;
            String knum = String.valueOf(num);
            byte[] knumb = knum.getBytes();
            skey=getRawKey(knumb);
            skeyString = new String(skey);
            System.out.println("Blowfish Symmetric key = "+skeyString);
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    private static byte[] getRawKey(byte[] seed) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("Blowfish");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        sr.setSeed(seed);
        kgen.init(128, sr); // 128, 256 and 448 bits may not be available
        SecretKey skey = kgen.generateKey();
        raw = skey.getEncoded();
        return raw;
    }
    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "Blowfish");
        Cipher cipher = Cipher.getInstance("Blowfish");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    public static Bitmap generateQrCode(String myCodeText) throws WriteAbortedException, WriterException {
        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); // H = 30% damage

        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        int size = 256;

        BitMatrix bitMatrix = qrCodeWriter.encode(myCodeText, BarcodeFormat.QR_CODE, size, size, hintMap);

        int width = size;
        Bitmap bmp = Bitmap.createBitmap(width, width, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < width; y++) {
                bmp.setPixel(y, x, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
            }
        }
        return bmp;
    }


}
