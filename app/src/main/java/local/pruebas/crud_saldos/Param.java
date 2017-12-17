package local.pruebas.crud_saldos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class Param extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_param);





        final Context context = this;
        Button btnctas = (Button) findViewById(R.id.btnctas);
        Button btnsalir= (Button) findViewById(R.id.btnsalirparam);
        Button btnconceptos= (Button) findViewById(R.id.btnmanconceptos);

        btnctas.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                CharSequence[] items = {"Agregar/Editar Tipos de Cuentas", "Agregar/Editar Cuentas", "Atras"};


                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Que desea hacer con Cuentas?")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                if (which == 0)
                                {
                                    Toast.makeText(context,"0",Toast.LENGTH_SHORT).show();
                                } else if(which == 1) {
                                    startActivity(new Intent(Param.this, configurarcuentas.class));
                                }
                            }
                        });

                AlertDialog alert =builder.create();
                alert.show();

                }
            });






        btnconceptos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(Param.this, "Aqui Formulario para agregar/editar/eliminar Conceptos", Toast.LENGTH_LONG).show();

                //creating a new folder for the database to be backuped to
                File direct = new File(Environment.getExternalStorageDirectory() + "/BackupFolder/CRUD");

                if(!direct.exists())
                {
                    if(direct.mkdir())
                    {
                        //directory is created;
                    }

                }


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
                                // Cierra Actividad si da click en si

                                Param.this.finish();
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

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menui = getMenuInflater();
        menui.inflate(R.menu.menu_context,menu);
    }











  /*
    private void exportDB() {
        // TODO Auto-generated method stub

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            Toast.makeText(getBaseContext(), data.toString(),
                    Toast.LENGTH_LONG).show();
            if (sd.canWrite()) {
                String  currentDBPath= "//data//" + "local.pruebas.crud_saldos"
                        + "//databases//" + "administracion";
                String backupDBPath  = "/BackupFolder/CRUD";
                Toast.makeText(getBaseContext(), currentDBPath.toString(),
                        Toast.LENGTH_LONG).show();
                File currentDB = new File(data, currentDBPath);
                Toast.makeText(getBaseContext(), backupDBPath.toString(),
                        Toast.LENGTH_LONG).show();
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();


            }
        } catch (Exception e) {

            Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG)
                    .show();

        }
    }

        //importing database
     private void importDB() {
            // TODO Auto-generated method stub

            try {
                File sd = Environment.getExternalStorageDirectory();
                File data  = Environment.getDataDirectory();

                if (sd.canWrite()) {
                    String  currentDBPath= "//data//" + "PackageName"
                            + "//databases//" + "DatabaseName";
                    String backupDBPath  = "/BackupFolder/DatabaseName";
                    File  backupDB= new File(data, currentDBPath);
                    File currentDB  = new File(sd, backupDBPath);

                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                    Toast.makeText(getBaseContext(), backupDB.toString(),
                            Toast.LENGTH_LONG).show();

                }
            } catch (Exception e) {

                Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG)
                        .show();

            }
        }*/
        //exporting database




}
