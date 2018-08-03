package com.example.bjimba.digitalidentitycard;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Main3Activity extends AppCompatActivity {

    TextView tx,tx1;
    ImageView imgV;
    Bitmap bitmap;
    String message;
    List<String> list ;

    public final static int QRcodeWidth = 500 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        tx= (TextView) findViewById(R.id.textView);
        tx1= (TextView) findViewById(R.id.textView2);
        imgV=(ImageView)findViewById(R.id.iv);

        Bundle bundle = getIntent().getExtras();
        message = bundle.getString("hash_Value");

        //message = message + "/"+ message;
        //tx.setText(message);
        //findEitherAddressHash(message,"/");

        if(message.length() == 0){
            Toast.makeText(Main3Activity.this, "Enter String!", Toast.LENGTH_SHORT).show();
        }else {
            try {
                bitmap = TextToImageEncode(message);
                imgV.setImageBitmap(bitmap);
                  //give read write permission
                Toast.makeText(Main3Activity.this, "QRCode ", Toast.LENGTH_SHORT).show();
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }



    }

    private void findEitherAddressHash(String message,String separator) {
        for (String retval: message.split(separator)) {
            list.add(retval);
        }
        Toast.makeText(Main3Activity.this,"listize "+list.size(),Toast.LENGTH_SHORT).show();
        tx.setText(list.get(0));
        //tx1.setText(list.get(1));

    }

    private Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.black):getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }
}
