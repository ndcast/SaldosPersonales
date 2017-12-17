package local.pruebas.crud_saldos;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;







public class IngresarRegistros extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    //Código de envío


    //Iniciando la actividad Form

    private List<String> listac;
    private ArrayAdapter<String> dataAdapterc;

    final Context context = this;
    private Button btnacerca2,btningresarreg,btnsalir2;
    private EditText etmonto,etobser,etconcepto;
    private Spinner etcta,spconcepto;
    public String ES,Cta,Concepto;
    public int inreg,incta, inpres;

    private boolean saki;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_registros);

        btnacerca2=(Button) findViewById(R.id.btnAcerca2);
        btningresarreg=(Button) findViewById(R.id.btnIngresarDato);
        btnsalir2=(Button) findViewById(R.id.btnSalir2);
        etmonto=(EditText) findViewById(R.id.etmonto);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spconcepto=(Spinner) findViewById(R.id.spconcepto);
        etcta=(Spinner) findViewById(R.id.spinner);
        //etconcepto=(EditText) findViewById(R.id.etotros);

        etobser=(EditText) findViewById(R.id.etobser);


        ES=Cta=Concepto="";
        incta=inreg=inpres=0;

        //SPINNER CODE INICIA  sacado de http://www.tutorialspoint.com/android/android_spinner_control.htm

        // Spinner Drop down elements
        todasctas(1);



        List<String> concepts = new ArrayList<String>();
        concepts.add("TRP01 - Bus");
        concepts.add("TRP02 - Taxi");
        concepts.add("TRP03 - Combustible");
        concepts.add("VIV01 - Apto");
        concepts.add("VIV02 - Luz");
        concepts.add("VIV03 - Agua");
        concepts.add("COM01 - Desayunos");
        concepts.add("COM02 - Almuerzos");
        concepts.add("COM03 - Cenas");
        concepts.add("COM04 - Varios");
        concepts.add("SER01 - Telefonía");
        concepts.add("SER02 - Internet");
        concepts.add("OTR01 - Personales");
        concepts.add("OTR02 - Sociales");
        concepts.add("POG01 - Ingresos");
        concepts.add("POA01 - Pagos");
        concepts.add("POA02 - Ajuste");
        concepts.add("CXP01 - BAC Tarjeta");
        concepts.add("CXP02 - FICO Cuota I");
        concepts.add("CXP03 - NAM Cuota ");
        concepts.add("CXP04 - FICO Cuota II");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, concepts);
        // Drop down layout style - list view with radio button
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spconcepto.setAdapter(dataAdapter1);

        // Spinner click listener

        spinner.setOnItemSelectedListener(this);
        spconcepto.setOnItemSelectedListener(this);
        //SPINNER CODE TERMINA



        btnacerca2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
                dlgAlert.setMessage("Esta aplicación está en proceso de construccion, atte. nDCasT ");
                dlgAlert.setTitle("App control de Saldos Personales");
                dlgAlert.setPositiveButton("Tu muy bien ;)", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();

            }
        });


        btnsalir2.setOnClickListener(new View.OnClickListener() {
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
                                
                                IngresarRegistros.this.finish();
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

        btningresarreg.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);
                String p =etmonto.getText().toString();
                String otro=etobser.getText().toString();
                double dmonto=0;

                try {
                    dmonto=Double.parseDouble(p);

                }catch (Exception e){
                    p="0.00";
                    dmonto=Double.parseDouble(p);
                }


                inreg++;

                if(verificarnulos(ES,Cta,Concepto,dmonto,etobser.getText().toString())==true)
                {


                dlgAlert.setMessage("Ingresando registro a base de datos...\n\rMonto: " + etmonto.getText().toString() +
                        "\r\n Tipo: " + ES + "\r\n Cta. Destino: " + Cta + "\r\n Concepto: " + Concepto +
                        "\r\n Observacion: " + etobser.getText().toString());

                Insertar(v, ES, Concepto, etobser.getText().toString(), dmonto, otro);




                etmonto.requestFocus();
                }else {
                    dlgAlert.setMessage("Ingrese todos los valores...\r\n ES:"+ES+"-CTA:"+Cta+"-Concp:"+Concepto+"-monto:"+etmonto.getText()+"-obser:"+etobser.getText().toString());
                }

                dlgAlert.setTitle("Ingreso");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();


            }
        });

    }

    public void Insertar(View v, String reg_es, String reg_concept, String tbl_regscol, Double monto, String otro)
    {

        AdminSQLite admin = new AdminSQLite(this,"administracion",null,1);
        SQLiteDatabase bd = admin.getReadableDatabase();
        String vtipocta_acreedor="";
        String signo="";
        signo="Nada";
        double resultado=99;
        String vsav_id = "";
        String vsav_cod = "";
        String vsav_update = "";
        String vsav_descrip = "";
        String vsav_saldo = "";
        String vcta_type ="";



        long regcount = DatabaseUtils.queryNumEntries(bd, "tbl_regs");
        regcount+=1;
        int ctaspin= etcta.getSelectedItemPosition()+1;


        /*

         3-Hacer Actividades de mantenimientos para Ctas
           3.2 - Insertar Cta con tipo de cuenta .
         4-Hacer AutoFill desde tablas, para:

           4.2 - Concepto
           4.3 - Deseo Revisar
         5-Pago de tarjetas, insercion automatica de descarga cta destino y origen.
         */

        //Toast.makeText(this, "Es "+Cta, Toast.LENGTH_SHORT).show();
        Cursor c = bd.rawQuery("SELECT A.cta_id,A.cta_cod,A.cta_dateupdate,A.cta_descrip,A.cta_saldo,A.cta_type,B.tipocta_acreedor " +
                "FROM tbl_ctas as A inner join tbl_tipoctas as B ON A.cta_type=B.tipocta_id" +
                " where cta_cod='"+Cta+"' ", null);
        regcount=DatabaseUtils.queryNumEntries(bd, "tbl_ctas");
            c.moveToFirst() ;

                vsav_id = c.getString(0);
                vsav_cod = c.getString(1);
                vsav_update = c.getString(2);
                vsav_descrip = c.getString(3);
                vsav_saldo = c.getString(c.getColumnIndex("cta_saldo"));
                vcta_type = c.getString(5);
                vtipocta_acreedor=c.getString(c.getColumnIndex("tipocta_acreedor"));
                int tca=Integer.parseInt(vtipocta_acreedor);
                double viene=Double.parseDouble(vsav_saldo);


                bd.execSQL("INSERT INTO tbl_regs VALUES(null,2," + vsav_id + ",DATETIME('now', 'localtime')," + monto + ",'" + reg_es.substring(0, 1).toString() + "','" + reg_concept + "','" + tbl_regscol + "')"); //+ otro + "')");

                if(ES=="Entrada")
                {
                    switch(tca) {
                        case 1:
                            signo="+";
                            break;
                        case 2:
                            monto=monto*-1;
                            signo="-";
                            break;
                        default:
                            signo="na";

                    }
                    resultado=viene+monto;
                }

                if(ES=="Salida")
                {
                    switch(tca) {
                        case 1:
                            signo="-";
                            monto=monto*-1;
                            break;
                        case 2:
                            signo="+";
                            break;
                        default:
                            signo="na";
                    }
                    resultado=viene+monto;
                }


            Toast.makeText(this,"TipoA:"+tca +" ES:"+reg_es.substring(0, 1).toString() +" Saldo:"+ vsav_saldo+" "+signo+" "+monto+" = "+resultado, Toast.LENGTH_SHORT).show();

            bd.execSQL("UPDATE tbl_ctas set cta_saldo=" + resultado + ", cta_dateupdate=DATETIME('now', 'localtime') WHERE cta_cod='" + Cta + "'");

        //}
        c.close();
        bd.close();


        //---------------

        //Toast.makeText(this, "Este mensaje es nuevo"+ regcount, Toast.LENGTH_SHORT).show();
        etmonto.setText(null);
        etobser.setText("");
        etmonto.requestFocus();

        }


    public  void todasctas(int var){
        if(var==1){

            listac = new ArrayList<String>();

            dataAdapterc = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listac);
            dataAdapterc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            AdminSQLite adminc = new AdminSQLite(this, "administracion", null, 1);
            SQLiteDatabase dbconc = adminc.getReadableDatabase();

            Cursor crc = dbconc.rawQuery("SELECT * FROM tbl_ctas", null);
            if (crc != null) {
                if (crc.moveToFirst()) {

                    do {
                        String vtipocta_cod = crc.getString(1);
                        listac.add(vtipocta_cod);
                        etcta.setAdapter(dataAdapterc);

                    } while (crc.moveToNext());

                } else {
                    //Toast.makeText(this, "Seleccione: ", Toast.LENGTH_LONG).show();
                }
            }
            crc.close();
            dbconc.close();
        }
    }

    public void onRadioButtonClicked(View view)
    {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rdentrada:
                if (checked)
                    ES="Entrada";
                    break;
            case R.id.rdsalida:
                if (checked)
                    ES="Salida";
                    break;
        }
    }


    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            // On selecting a spinner item
        String item="";
        Spinner spinner = (Spinner) parent;
        String valor = "", query = "";
        if(spinner.getId() == R.id.spinner)
        {
            Cta = parent.getItemAtPosition(position).toString();
            item = Cta;

            //Sacar cuanto tiene de fondos
            AdminSQLite adminc = new AdminSQLite(this, "administracion", null, 1);
            SQLiteDatabase dbconc = adminc.getReadableDatabase();



            query = "SELECT cta_saldo FROM tbl_ctas WHERE cta_cod='" + Cta + "'";

            Cursor crcctc = dbconc.rawQuery(query, null);

            if (crcctc != null)
            {
                if (crcctc.moveToFirst())
                {

                    do
                    {
                        valor = crcctc.getString(0);
                    } while (crcctc.moveToNext());
                }
            }

        }
        else if (spinner.getId() == R.id.spconcepto)
        {
            Concepto = parent.getItemAtPosition(position).toString();
            item = Concepto;
        }


        // Showing selected spinner item
            Toast.makeText(parent.getContext(), "Seleccionado: " + item +" , Saldo que tiene = " + valor, Toast.LENGTH_LONG).show();
    }



    public void onNothingSelected(AdapterView<?> arg0) {

    }

    private boolean verificarnulos(String vES, String vCta, String vConcepto, double vMonto, String vObser)
    {
        boolean tf=false;

            if(vES=="" || vCta=="" || vConcepto=="" || vObser=="" || vMonto <=0)
            {
                tf=false;
            }else{tf=true;}

        return tf;

    }

}
