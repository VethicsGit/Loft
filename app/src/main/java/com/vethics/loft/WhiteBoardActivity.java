package com.vethics.loft;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.joaquimley.faboptions.FabOptions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import customview.CanvasView;

public class WhiteBoardActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int STORAGE_REQUEST_CODE = 1;
    private CanvasView canvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_white_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i = new Intent(StripePaymentActivity.this, ConfirmEnrollActivity.class);
                startActivity(i);*/
                finish();
                //overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        canvas = (CanvasView) this.findViewById(R.id.canvas);
        FabOptions fabOptions = (FabOptions) findViewById(R.id.fab);
        fabOptions.setButtonsMenu(R.menu.menu_whiteboard_options);
        fabOptions.setOnClickListener(this);
    }

    public void sendFile() {
        try {
            canvas.setDrawingCacheEnabled(true);
            Bitmap bitmap = canvas.getDrawingCache();

            Document document = new Document();
            File file = new File(Environment.getExternalStorageDirectory(), "Loft_cache");

            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                if (!file.exists()) {
                    file.mkdirs();
                }
            }
            File imgFileName = new File(file.getAbsolutePath() + File.separator + "canvas1" + ".png");
            File pdfFileName = new File(file.getAbsolutePath() + File.separator + "canvas1" + ".pdf");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            FileOutputStream fos = new FileOutputStream(imgFileName);

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            byte[] imageByte = baos.toByteArray();
            PdfWriter.getInstance(document, new FileOutputStream(pdfFileName)); //  Change pdf's name.

            document.open();
            Image img = Image.getInstance(imageByte);  // Change image's name and extension.

            float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                    - document.rightMargin() - 0) / img.getWidth()) * 100; // 0 means you have no indentation. If you have any, change it.
            PdfPTable table = new PdfPTable(1);
            table.setWidthPercentage(100);

            img.scalePercent(scaler);
            img.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);
            img.setWidthPercentage(80);
            Paragraph paragraph = new Paragraph();
            paragraph.setAlignment(Element.ALIGN_CENTER);
            //paragraph.add("Loft");
            paragraph.add(new Chunk(img, 0, 0, true));
            table.addCell(paragraph);
            //table.addCell(new Paragraph("Kindly Solve This Problem!"));

            document.add(table);
            document.close();
            baos.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //img.setAlignment(Image.LEFT| Image.TEXTWRAP);

                 /* float width = document.getPageSize().width() - document.leftMargin() - document.rightMargin();
                 float height = document.getPageSize().height() - document.topMargin() - document.bottomMargin();
                 img.scaleToFit(width, height) */
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.faboptions_undo:
                if (canvas.undo()) {
                    canvas.undo();
                }
                break;

            case R.id.faboptions_redo:
                if (canvas.redo()) {
                    canvas.redo();
                }
                break;

            case R.id.faboptions_draw:
                canvas.setPaintStrokeWidth(6F);
                canvas.setPaintFillColor(Color.BLACK);
                canvas.setMode(CanvasView.Mode.DRAW);
                break;

            case R.id.faboptions_colors:
                canvas.setPaintStrokeWidth(6F);
                canvas.setMode(CanvasView.Mode.DRAW);

                ColorPickerDialogBuilder
                        .with(WhiteBoardActivity.this)
                        .setTitle("Choose Color")
                        .initialColor(Color.WHITE)
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setOnColorSelectedListener(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(int selectedColor) {
                                //canvas.setPaintFillColor(selectedColor);
                            }
                        })
                        .setPositiveButton("OK", new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                canvas.setPaintFillColor(selectedColor);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .build()
                        .show();

                break;

            case R.id.faboptions_erase:
                canvas.setPaintStrokeWidth(10F);
                canvas.setPaintFillColor(canvas.getBaseColor());
                //canvas.setMode(CanvasView.Mode.ERASER);
                break;

            case R.id.faboptions_send:
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                    if (ContextCompat.checkSelfPermission(WhiteBoardActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        sendFile();
                    } else {
                        ActivityCompat.requestPermissions(WhiteBoardActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_REQUEST_CODE);
                    }
                } else {
                    sendFile();
                }
                break;

            default:
                // no-op
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case STORAGE_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendFile();
                } else {
                    Toast.makeText(WhiteBoardActivity.this, "To save the image/pdf you must grant the permission to this app.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

}