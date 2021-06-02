package net.jesusramirez.redes_sociales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MainActivity extends AppCompatActivity {
    /*
    BOTONES PARA EL MANEJO DE LA SESION EN FACEBOOK Y PARA COMPARTIR EN LA APLICACION
     */
LoginButton btnInicio;
CallbackManager callbackManager;
ShareButton compartirLink, compartirFoto, compartirTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnInicio=findViewById(R.id.btnInicio);
        compartirLink = findViewById(R.id.btnLink);
        compartirFoto = findViewById(R.id.btnFoto);
        compartirTexto = findViewById(R.id.btnTexto);

        /* CODIGO USADO PARA OBTENER LA CLAVE QUE SE REGISTRA EN FACEBOOK DEVELOPER
        try {
            PackageInfo info = getPackageManager().getPackageInfo("net.jesusramirez.redes_sociales", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String sign = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.d("FIRMA", sign);
                Toast.makeText(getApplicationContext(), sign, Toast.LENGTH_LONG).show();
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
         */

        // ADMINISTRADOR DE LA CONEXION CON FACEBOOK, ES UNA DEVOLUCION DE LLAMADA
        callbackManager = CallbackManager.Factory.create();

        // REGISTRO EN LA CONEXION CON EL ADMINISTRADOR
        btnInicio.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // CODIGO QUE OCURRE CUANDO SE INICIA SESION DE MANERA CORRECTA
                //COMPARTIR UNA PUBLICACION DE SOLO TEXTO
                ShareLinkContent post = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse(""))
                        .build();
                compartirTexto.setShareContent(post);

                //COMPARTIR UNA PUBLICACION CON UN LINK
                ShareLinkContent content = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse("https://youtu.be/V-sxggm1XDE"))
                        .setQuote("LA COSA NOSTRA")
                        .setShareHashtag(new ShareHashtag.Builder()
                                .setHashtag("#RAP")
                                .build()).build();
                compartirLink.setShareContent(content);

                //COMPARTIR PUBLICACION CON IMAGEN
                Bitmap image = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.imagen);

                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(image)
                        .build();

                SharePhotoContent contentPhoto = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();
                compartirFoto.setShareContent(contentPhoto);
            }

            @Override
            public void onCancel() {
                // CUANDO SE CANCELA EL INICIO DE SESION OCURRE EL SIGUIENTE CODIGO
                Toast.makeText(MainActivity.this, "SE CANCELO LA AUTENTICACION", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // CODIGO QUE OCURRE CUANDO HAY UN ERROR
                Toast.makeText(MainActivity.this, "OCURRIO UN ERROR", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //DE ESTA MANERA SE OBTIENE EL RESULTADO DEL INICIO DE SESION Y DE LAS PUBLICACIONES
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}