package ukash.arm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {

    TextView textTargetUri;
    ImageView targetImage;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonLoadImage = (Button)findViewById(R.id.loadimage);
        textTargetUri = (TextView)findViewById(R.id.targeturi);
        targetImage = (ImageView)findViewById(R.id.targetimage);

        buttonLoadImage.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }});

        editText = (EditText) findViewById(R.id.editText);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnViewData = (Button) findViewById(R.id.btnView);
        mDatabaseHelper = new DatabaseHelper(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = editText.getText().toString();
                if (editText.length() !=0){
                    AddData(newEntry);
                    editText.setText("");
                }else {
                    toastMessage("Musisz co wpisać");
                }
            }
        });

        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListDataActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            Uri targetUri = data.getData();
            textTargetUri.setText(targetUri.toString());
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                targetImage.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

// -------------------------DODAWANIE ZDJ (up)--------------------------------------------------------
    private static final String TAG = "MainActivity";

    DatabaseHelper mDatabaseHelper;
    private Button btnAdd, btnViewData;
    private EditText editText;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        editText = (EditText) findViewById(R.id.editText);
//        btnAdd = (Button) findViewById(R.id.btnAdd);
//        btnViewData = (Button) findViewById(R.id.btnView);
//        mDatabaseHelper = new DatabaseHelper(this);
//
//        btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String newEntry = editText.getText().toString();
//                if (editText.length() !=0){
//                    AddData(newEntry);
//                    editText.setText("");
//                }else {
//                    toastMessage("Musisz co wpisać");
//                }
//            }
//        });
//
//        btnViewData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, ListDataActivity.class);
//                startActivity(intent);
//            }
//        });
//    }


    public void AddData(String newEntry){
        boolean insertData = mDatabaseHelper.addData(newEntry);

        if (insertData){
            toastMessage("Zapisano dane");
        }else {
            toastMessage("UPS! Coś poszło nie tak");
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}