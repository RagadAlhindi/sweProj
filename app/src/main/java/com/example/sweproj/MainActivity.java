package com.example.sweproj;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Spinner sp ;
    String Colector = "";
    TextView txtalertName;
    EditText plate, model, Year, UserComment, Amount;
    Button SubmitSave, view;
    RadioButton car, boat, motro;
    //
    Button BSelectImage;

    // One Preview Image
    ImageView IVPreviewImage;

    // constant to compare
    // the activity result code
      int SELECT_PICTURE = 10;
      String selectedType ="";
      String selectedCity ="";


    String imagePath;

    DBhelper dataBaseHelper;

   private Bitmap bitmap;
    private static final int REQUEST_CODE_IMAGE_CHOOSER = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = findViewById(R.id.SpCountry);
        plate = findViewById(R.id.plate);
        model = findViewById(R.id.model);
        Year = findViewById(R.id.year);
        Amount = findViewById(R.id.Amount);
        UserComment = findViewById(R.id.userDescription);
        txtalertName = findViewById(R.id.userAlert);
        car = findViewById(R.id.car);
        boat = findViewById(R.id.boat);
        motro = findViewById(R.id.motorcycle);
        SubmitSave = findViewById(R.id.btnSubmit);



        dataBaseHelper = new DBhelper(MainActivity.this);






        SubmitSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((RadioButton)car).isChecked()) {
                    selectedType = "car";
                } else if (((RadioButton)boat).isChecked()) {
                    selectedType = "boat";
                } else if (((RadioButton)motro).isChecked()) {
                    selectedType = "motrocycle";
                }
            String Plate = plate.getText().toString();
            String Model = model.getText().toString();
            String year = Year.getText().toString();
            String comment = UserComment.getText().toString();
            String amount = Amount.getText().toString();

            if (Plate.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please fill the plate number field", Toast.LENGTH_SHORT).show();
            } else if (Model.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please fill the model field", Toast.LENGTH_SHORT).show();
            } else if (year.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please fill the year of manufacture field", Toast.LENGTH_SHORT).show();
            } else if (comment.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please fill the description field", Toast.LENGTH_SHORT).show();
            }
            else if (amount.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill the rent amount field", Toast.LENGTH_SHORT).show();
            }
            else if (selectedCity.equals("")) {
                Toast.makeText(MainActivity.this, "Please choose the location in the city field", Toast.LENGTH_SHORT).show();
            }
            else if (selectedType.equals("")) {
                Toast.makeText(MainActivity.this, "Please choose the vehicle type", Toast.LENGTH_SHORT).show();
            }

            else {

                Colector += "Plate: " + Plate + "\n";
                Colector += "Model: " + Model + "\n";
                Colector += "Year of manufacture:  "+ year + "\n";
                Colector += "Description: " +comment + "\n";

                Toast.makeText(MainActivity.this, "Vehicle Info \n:" + Colector, Toast.LENGTH_LONG).show();

                    // create model
                    VehicleModel vehicleMod;

                byte[] img;
                if (bitmap!=null){
                 bitmap = BitmapFactory.decodeFile(imagePath);
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
                 img = byteArray.toByteArray();
                    //try {

                        vehicleMod = new VehicleModel(-1, Plate, Model, Integer.parseInt(year),selectedType,selectedCity, comment, Integer.parseInt(amount), img);
                        Toast.makeText(MainActivity.this, vehicleMod.toString(), Toast.LENGTH_SHORT).show();
                 //   } catch (Exception e) {
                    //    Toast.makeText(MainActivity.this, "Enter Valid input", Toast.LENGTH_SHORT).show();
                    //     vehicleMod = new VehicleModel(-1, "ERROR", "", 0, "", "","",0,null);
                   // }

                    DBhelper dataBaseHelper = new DBhelper(MainActivity.this);
                    boolean b = dataBaseHelper.addOne(vehicleMod);
                    Toast.makeText(MainActivity.this, "SUCCESS= "+ b, Toast.LENGTH_SHORT).show(); }




                }
            }


        });

        List<String> categoryCountry = new ArrayList<>();
        categoryCountry.add("Select City");
        categoryCountry.add("Abha");
        categoryCountry.add("Dammam");
        categoryCountry.add("Hail");
        categoryCountry.add("Jazan");
        categoryCountry.add("Jeddah");
        categoryCountry.add("Madina");
        categoryCountry.add("Mekkah");
        categoryCountry.add("Riyadh");
        categoryCountry.add("Taif");


        ArrayAdapter<String> arrayAdapter;
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryCountry);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(arrayAdapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                if (parent.getItemAtPosition(position).equals("Select City")) {
                    //Do Nothing

                } else {
                    String item = parent.getItemAtPosition(position).toString();
                    Colector +="City: "+ item + "\n" ;
                    selectedCity = item;
                    Toast.makeText(MainActivity.this, "Selected city: " + item, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        // register the UI widgets with their appropriate IDs
        BSelectImage = findViewById(R.id.BSelectImage);
        IVPreviewImage = findViewById(R.id.IVPreviewImage);

        // handle the Choose Image button to trigger
        // the image chooser function
        BSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });










    }

    // this function is triggered when
    // the Select Image Button is clicked
    // define the imageBitmap object as a class variable


    private void imageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_IMAGE_CHOOSER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMAGE_CHOOSER && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            handleImageSelection(selectedImageUri);
            IVPreviewImage.setImageURI(selectedImageUri);
        }
    }

    ActivityResultLauncher<Intent> launchSomeActivity
            = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null && data.getData() != null) {
                        Uri selectedImageUri = data.getData();
                        String imagePath = getRealPathFromUri(selectedImageUri);
                        bitmap = BitmapFactory.decodeFile(imagePath);
                        IVPreviewImage.setImageBitmap(bitmap);
                    }
                }
            });

    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column_index);
        cursor.close();
        return path;
    }
    private void handleImageSelection(Uri selectedImageUri) {
        String imagePath = getRealPathFromUri(selectedImageUri);
        if (TextUtils.isEmpty(imagePath)) {
            // The path is invalid, display an error message to the user
            Toast.makeText(this, "Invalid image path", Toast.LENGTH_SHORT).show();
            return;
        }
        File imageFile = new File(imagePath);
        if (!imageFile.exists()) {
            // The file does not exist, display an error message to the user
            Toast.makeText(this, "Image file does not exist", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isImageFile(imageFile)) {
            // The file is not an image, display an error message to the user
            Toast.makeText(this, "File is not an image", Toast.LENGTH_SHORT).show();
            return;
        }
        Bitmap imageBitmap = BitmapFactory.decodeFile(imagePath);
        if (imageBitmap != null) {
            // The image was loaded successfully, do something with the Bitmap object
        } else {
            // The image could not be loaded, display an error message to the user
            Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isImageFile(File file) {
        String fileName = file.getName();
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        return extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg") || extension.equalsIgnoreCase("png");
    }
}