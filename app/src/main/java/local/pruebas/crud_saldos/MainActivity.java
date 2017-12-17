package local.pruebas.crud_saldos;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.app.AlertDialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {//implements View.OnClickListener {



    final Context context = this;
    private Button btnacerca,btningresar,btnsalir,btnconsultar, btnparams;
    private boolean saki;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        btnacerca=(Button) findViewById(R.id.btnAcerca);
        btnsalir=(Button) findViewById(R.id.btnSalir);
        btningresar=(Button) findViewById(R.id.btnIngresar);
        btnconsultar=(Button) findViewById(R.id.btnConsultar);
        btnparams=(Button) findViewById(R.id.buttonparams);


        btnacerca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);
                dlgAlert.setMessage("Esta aplicación está en proceso de construccion, atte. nDCasT ");
                dlgAlert.setTitle("App control de Saldos Personales v1.3");
                dlgAlert.setPositiveButton("Tu muy bien", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();

            }
        });

        btnsalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // Definir Titulo
                alertDialogBuilder.setTitle("Ok, Pregunta");

                // Definir mensaje del dialogo
                alertDialogBuilder
                        .setMessage("Deseas Salir de esta pantalla?")
                        .setCancelable(false)
                        .setPositiveButton("Si",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // Cierra toda la applicacion pues
                                finish();
                                System.exit(0);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // No hace nada
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.show();

            }
        });

        btningresar.setOnClickListener(new View.OnClickListener() {
            @Override

             public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, IngresarRegistros.class));

            }
        });

            btnconsultar.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {

                    startActivity(new Intent(MainActivity.this, ConsultarRegistros.class));

                }
            });

            btnparams.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {

                    startActivity(new Intent(MainActivity.this, Param.class));

                }
            });

    }



}
