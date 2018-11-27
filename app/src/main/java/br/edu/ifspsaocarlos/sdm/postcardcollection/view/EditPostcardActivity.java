package br.edu.ifspsaocarlos.sdm.postcardcollection.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
//import android.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.edu.ifspsaocarlos.sdm.postcardcollection.R;
import br.edu.ifspsaocarlos.sdm.postcardcollection.utils.SessionUtils;
import br.edu.ifspsaocarlos.sdm.postcardcollection.view.login.LoginActivity;

import static br.edu.ifspsaocarlos.sdm.postcardcollection.utils.SessionUtils.POSTCARD_ID;
import static br.edu.ifspsaocarlos.sdm.postcardcollection.utils.SessionUtils.USER_ID;

public class EditPostcardActivity extends AppCompatActivity {

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    private String mCurrentPhotoPath;

    private String userId;
    ImageView postcardPhotoView;
    Button btnSaveItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_postcard);

        postcardPhotoView = (ImageView) findViewById(R.id.imgPostcard);
        postcardPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        if (getIntent().hasExtra(USER_ID)) {
            this.userId = getIntent().getStringExtra(USER_ID);

            // Configurar o menu da ActionBar
//            btnSaveItem = (Button) findViewById("btnSavePostcard");
//            btnSaveItem.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
        }
        else {
            Toast.makeText(this, "Não há usuário logado! Redirecionando para o Login...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(EditPostcardActivity.this, LoginActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_edit_postcard, menu);
        // se não veio nenhum Id de Cartão Postal, está inserindo um novo
        if (!getIntent().hasExtra(POSTCARD_ID))
        {
            // então não vamos mostrar o ícone de lixeira (exclusão)
            MenuItem item = menu.findItem(R.id.delete_postcard);
            item.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // opções do menu
        switch (item.getItemId()) {
            case R.id.save_postcard:
                savePostcard();
                return true;
            case R.id.delete_postcard:
                deletePostcard();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "card_" + timeStamp + "_";

        // Diretório privado do meu app (https://developer.android.com/training/camera/photobasics#java)
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        Log.d(SessionUtils.TAG, "ExternalFilesDir = " + storageDir.getPath());

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",   /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.d(SessionUtils.TAG, "FILE_NAME " + imageFileName);
        Log.d(SessionUtils.TAG, "FILE_PATH " + mCurrentPhotoPath);
        return image;
    }

    private void dispatchTakePictureIntent() {
        // Returns the first activity component that can handle the intent: usually the Camera app
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent, otherwise my app would crash
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(this, "Error: the photo file could not be created.", Toast.LENGTH_SHORT).show();
            }

            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "br.edu.ifspsaocarlos.fileprovider", photoFile);
                //        "com.example.android.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                // Starts camera activity
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap imageBitmap = null;
            try {
                imageBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
                postcardPhotoView.setImageBitmap(imageBitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
//          Parte do Tutorial do Android Developers: não funcionou
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            postcardPhotoView.setImageURI((Uri) extras.get(MediaStore.EXTRA_OUTPUT));

            //With the help from https://stackoverflow.com/a/51740118/2307882
        }
    }

    public void deletePostcard()
    {
//        cDAO.apagaContato(c);
//        Intent resultIntent = new Intent();
//        setResult(3,resultIntent);
//        finish();
    }

    public void savePostcard()
    {
//        String name = ((EditText) findViewById(R.id.et_nome)).getText().toString();
//        String fone = ((EditText) findViewById(R.id.et_fone1)).getText().toString();
//        String fone2 = ((EditText) findViewById(R.id.et_fone2)).getText().toString();
//        String email = ((EditText) findViewById(R.id.et_email)).getText().toString();
//        String aniver = ((EditText) findViewById(R.id.et_aniver)).getText().toString();
//        // tratar os casos de quando a opção 'salvar' for acionada:
//        // se c é vazio = novo contato
//        if (c==null)
//        {
//            c = new Contato();
//            c.setNome(name);
//            c.setFone(fone);
//            c.setFone2(fone2);
//            c.setEmail(email);
//            c.setAniversario(aniver);
//            cDAO.insereContato(c);
//        }
//        else // se c não é vazio = edição do contato
//        {
//            c.setNome(name);
//            c.setFone(fone);
//            c.setFone2(fone2);
//            c.setEmail(email);
//            c.setAniversario(aniver);
//            cDAO.atualizaContato(c);
//
//        }
//        Intent resultIntent = new Intent();
//        setResult(RESULT_OK,resultIntent);
//        finish();
    }


}
