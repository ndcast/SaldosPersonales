package local.pruebas.crud_saldos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class configurarcuentas  extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    public String CER, ccCta,ccTipoCta,ccAcree, ccObser, ccDescrip,ccCodDescrip;
    public Double ccsaldo;
    public Spinner spcta, sptipocta;
    private TextView tvcta;
    private List<String> listacc;
    private ArrayAdapter<String> dataAdaptercc;
    private AdminSQLite admincc;
    private SQLiteDatabase dbconcc;
    private EditText etcd, etccsaldo, etccobser, etccdescripm;
    private List<String> listacctc,listacctc2,listacctc3;
    private ArrayAdapter<String> dataAdapterspcctc;
    private Button ccbtnok;
    private Context context;
    private String algo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.activity_configurarcuentas);
        admincc = new AdminSQLite(this, "administracion", null, 1);
        dbconcc = admincc.getReadableDatabase();


        etcd=(EditText) findViewById(R.id.ccetdescrip);
        etccsaldo=(EditText) findViewById(R.id.ccetsaldo);
        etccobser=(EditText) findViewById(R.id.ccetobser);
        etccdescripm=(EditText) findViewById(R.id.etccdescripm);
        etcd.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        final Button ccbtnacerca2=(Button) findViewById(R.id.ccbtnAcerca2);
        Button ccbtnsalir2=(Button) findViewById(R.id.ccbtnSalir);
        ccbtnok =(Button) findViewById(R.id.ccbtnIngresarDato);
        spcta=(Spinner) findViewById(R.id.spctasexistentes);
        sptipocta=(Spinner) findViewById(R.id.spcctipocta);
        tvcta=(TextView) findViewById(R.id.cctv3);





        /*

        etcd.setText(""); ok
        etccsaldo.setText(0); ok
        etccdescripm.setText("");
        etccobser.setText("");
        ccbtnok.setText("-");
        etcd.requestFocus();
        rd.setChecked(Boolean.TRUE);
        CER="Crear";
        tvcta.setVisibility(View.INVISIBLE);
        spcta.setVisibility(View.INVISIBLE);

         */
        algo="" ;

        CER="C";
        ccCta="";
        ccAcree="";
        ccTipoCta="";
        ccObser="";
        ccDescrip="";
        ccCodDescrip="";
        ccsaldo=0.00;
        etccsaldo.setText(ccsaldo.toString());

        listacctc2=new ArrayList<String>();
        listacctc3=new ArrayList<String>();
        listacctc = new ArrayList<String>();
        dataAdapterspcctc = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listacctc);
        dataAdapterspcctc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spcta.setOnItemSelectedListener(this);
        sptipocta.setOnItemSelectedListener(this);

        FTipoCta(Boolean.FALSE);


            ccbtnacerca2.setOnClickListener(new View.OnClickListener() {
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

        ccbtnok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ccCodDescrip=etcd.getText().toString();
                String codnospace= ccCodDescrip.replaceAll(" ","");
                String result="";

                ccTipoCta=sptipocta.getSelectedItem().toString();
                ccObser = etccobser.getText().toString();
                ccDescrip= etccdescripm.getText().toString();
                ccsaldo= Double.parseDouble(etccsaldo.getText().toString());
                int idccTipoCta=sptipocta.getSelectedItemPosition()+1;

                switch(CER)
                {
                    case "Crear":
                        result = "Insertando\r\n Codigo Descriptivo: " + codnospace.toString() +"\r\n" +
                                " Tipo Cta: " + ccTipoCta.toString()+"\r\n"+
                                " Descripcion: " + ccDescrip.toString()+"\r\n"+
                                " Observacion: " + ccObser.toString()+"\r\n"+
                                " Saldo: " + ccsaldo.toString()+"\r\n" ;

                        Procesar(v,codnospace.toString(), idccTipoCta,ccDescrip.toString(),ccObser.toString(),ccsaldo,result);
                        break;
                    case "Editar":
                        result = "Editando\r\n Codigo Descriptivo: " + codnospace.toString() +"\r\n" +
                                " Cta: " + ccCta+"\r\n"+
                                " Tipo Cta: " + ccTipoCta.toString()+"\r\n"+
                                " Descripcion: " + ccDescrip.toString()+"\r\n"+
                                " Observacion: " + ccObser.toString()+"\r\n"+
                                " Saldo: " + ccsaldo.toString()+"\r\n" ;
                        Procesar(v,codnospace.toString(), idccTipoCta,ccDescrip.toString(),ccObser.toString(),ccsaldo,result);

                        break;

                    case "Remover":
                        result = "Eliminando\r\n Codigo Descriptivo: " + codnospace.toString() +"\r\n" +
                                " Cta: " + ccCta+"\r\n"+
                                " Tipo Cta: " + ccTipoCta.toString()+"\r\n"+
                                " Descripcion: " + ccDescrip.toString()+"\r\n"+
                                " Observacion: " + ccObser.toString()+"\r\n"+
                                " Saldo: " + ccsaldo.toString()+"\r\n" ;
                        Procesar(v,codnospace.toString(), idccTipoCta,ccDescrip.toString(),ccObser.toString(),ccsaldo,result);

                        break;

                }


            }
        });


        ccbtnsalir2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Salida();

            }
        });


    }

    public void Procesar(View v, String cta_cod, int cta_type, String cta_descrip, String cta_obser, Double cta_saldo, String result)
    {

        AdminSQLite admin = new AdminSQLite(this,"administracion",null,1);
        SQLiteDatabase bd = admin.getReadableDatabase();


        String signo="";
        signo="Nada";

        Double monto =0.00;
        String sql="";
        Integer tca=0;

        /*      tbl_regs ("+
                "reg_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
                "cod_pres int(11) DEFAULT NULL,"+
                "cod_cta int(11) DEFAULT NULL,"+
                "reg_date datetime DEFAULT CURRENT_TIMESTAMP,"+
                "reg_monto double(16,2) DEFAULT NULL,"+
                "reg_es varchar(1) DEFAULT NULL,"+
                "reg_concept varchar(45) DEFAULT NULL,"+
                "tbl_regscol varchar(45) DEFAULT NULL)");
         */

        long regcount = DatabaseUtils.queryNumEntries(bd, "tbl_ctas");
        regcount+=1;
        int ctaspin= spcta.getSelectedItemPosition()+1;
        Toast.makeText(this,CER, Toast.LENGTH_SHORT).show();
        if(CER=="Crear")
        {
            signo="+";
            monto=monto*-1;
            signo="na";
            sql="Insert into tbl_ctas" +
                    " values(null,'"+cta_cod+
                    "',"+String.valueOf(cta_type)+
                    ",'"+cta_descrip+
                    "','"+cta_obser+
                    "',"+cta_saldo.toString()+
                    ",DATETIME('now', 'localtime'))"
            ;
        }
        if(CER=="Remover")
        {


            sql="DELETE FROM tbl_ctas WHERE cta_cod='"+spcta.getSelectedItem().toString()+"'";
        }
        if(CER=="Editar")
        {
            sql="UPDATE tbl_ctas SET cta_cod='"+cta_cod + "', cta_type="+String.valueOf(cta_type)+
                    ", cta_descrip='"+cta_descrip+"', cta_obser='"+cta_obser
                +"', cta_saldo="+cta_saldo.toString() +" WHERE cta_cod='"+spcta.getSelectedItem().toString()+"'";
        }

        /*AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
        dlgAlert.setMessage(CER+" : \r\n"+sql);
        dlgAlert.setTitle("Accion con registro");
        dlgAlert.setPositiveButton("Entiendo;)", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
            */


        insertarcta(sql,result);



    }

    public void Limpiar(){

        RadioButton rd= (RadioButton) findViewById(R.id.ccrdcrear);
        etcd.setText("");
        etccsaldo.setText("0");
        etccdescripm.setText("");

        etccobser.setText("");
        ccbtnok.setText("-");
        tvcta.setVisibility(View.INVISIBLE);
        spcta.setVisibility(View.INVISIBLE);

        CER="Crear";
        rd.setChecked(Boolean.TRUE);
        etcd.requestFocus();
        todasctas(1);
    }

    public  void Salida()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // Definir Titulo
        alertDialogBuilder.setTitle("Ok, Pregunta");

        // Definir mensaje del dialogo
        alertDialogBuilder
                .setMessage("Deseas Salir de esta pantalla?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Cierra Actividad si da click en si

                        configurarcuentas.this.finish();
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
        dbconcc.close();

    }

    public void insertarcta(String sql, String result)
    {

        AdminSQLite admin = new AdminSQLite(this,"administracion",null,1);
        dbconcc = admin.getReadableDatabase();
        algo=sql;
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            // Definir Titulo
            alertDialogBuilder.setTitle("Desea Realizar? ");

            // Definir mensaje del dialogo
            alertDialogBuilder
                    .setMessage("Deseas Proceder?: \r\n"+sql)
                    .setCancelable(false)
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Si
                                dbconcc.execSQL(algo);
                                dbconcc.close();
                                try{
                                    Limpiar();
                                }catch (Exception e) {
                                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
                                    dlgAlert.setMessage(e.toString());
                                    dlgAlert.setTitle("ERROR L:impiar");
                                    dlgAlert.setPositiveButton("Entiendo;)", null);
                                    dlgAlert.setCancelable(true);
                                    dlgAlert.create().show();
                                }


                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // No

                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();

            alertDialog.show();


    }

    public void FTipoCta(boolean yncta) {

        dbconcc = admincc.getReadableDatabase();

        String valor = "", query = "";

        query = "SELECT tipocta_id,tipocta_descrip,tipocta_acreedor   FROM tbl_tipoctas";

        Cursor crcctc = dbconcc.rawQuery(query, null);


        if (yncta == Boolean.FALSE) {


            if (crcctc != null) {
                if (crcctc.moveToFirst()) {

                    do {
                        String vtipocta_cod = crcctc.getString(0);
                        String vtipocta_descrip = crcctc.getString(1);
                        String vtipocta_acreedor = crcctc.getString(2);

                            listacctc.add(vtipocta_descrip);
                            listacctc2.add(vtipocta_cod);
                            listacctc3.add(vtipocta_acreedor);
                            sptipocta.setAdapter(dataAdapterspcctc);



                    } while (crcctc.moveToNext());
                }



                valor  ="";
            } else {
                Toast.makeText(this, "Pues no consulto nada: ", Toast.LENGTH_LONG).show();
            }
            crcctc.close();
            dbconcc.close();

        }
        else if(yncta==Boolean.TRUE)
        {
            valor =listacctc3.get(sptipocta.getSelectedItemPosition()).toString();
            int valorin= Integer.parseInt(valor);
            // Toast.makeText(this," Pues este es = "+valor, Toast.LENGTH_SHORT).show();
            TextView txt = (TextView) findViewById(R.id.tvessali);
            if(valorin<2) {
                txt.setText(" Esta cuenta es de Fondo");
            }else{
                txt.setText(" Esta cuenta es de Deuda");
            }

        }


    }



    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        String item1,item2;
        String botontexto=getString(R.string.boton_proceder_configurarcuentasc);




        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.ccrdcrear:
                if (checked)
                    CER="Crear";
                    tvcta.setVisibility(View.INVISIBLE);
                    spcta.setVisibility(View.INVISIBLE);
                botontexto=getString(R.string.boton_proceder_configurarcuentasc);
                    todasctas(1);
                break;
            case R.id.ccrdeditar:
                if (checked)
                    CER="Editar";


                    tvcta.setVisibility(View.VISIBLE);
                    spcta.setVisibility(View.VISIBLE);
                botontexto=getString(R.string.boton_proceder_configurarcuentase);
                    todasctas(2);

                break;
            case R.id.ccrdeliminar:
                if (checked)
                    CER="Remover";
                    tvcta.setVisibility(View.VISIBLE);
                    spcta.setVisibility(View.VISIBLE);
                botontexto=getString(R.string.boton_proceder_configurarcuentasr);
                todasctas(2);
                break;




        }
        ccbtnok.setText(botontexto);
        // Toast.makeText(this, "Seleccionado: " + CER+" y "+ccAcree, Toast.LENGTH_LONG).show();
    }



    public void onItemSelected(AdapterView<?> parent, View view1, int position, long id) {
        String item="";
        Spinner spinner = (Spinner) parent;



        if(spinner.getId() ==R.id.spctasexistentes)
        {
            ccCta=spcta.getSelectedItem().toString();
             camposeditar();
        }

        if(spinner.getId() == R.id.spcctipocta)
        {
            FTipoCta(Boolean.TRUE);
        }


    }

    public  void todasctas(int var){
       if(var>1){

            listacc = new ArrayList<String>();
            dataAdaptercc = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listacc);

            admincc = new AdminSQLite(this, "administracion", null, 1);
            dbconcc = admincc.getReadableDatabase();

            Cursor crcc = dbconcc.rawQuery("SELECT * FROM tbl_ctas", null);
            if (crcc != null) {
                if (crcc.moveToFirst()) {

                    do {
                        String vtipocta_cod = crcc.getString(1);
                        listacc.add(vtipocta_cod);
                        spcta.setAdapter(dataAdaptercc);

                    } while (crcc.moveToNext());

                } else {
                    //Toast.makeText(this, "Seleccione: ", Toast.LENGTH_LONG).show();
                }
            }
            crcc.close();
            dbconcc.close();
            }
    }


    public void camposeditar()
    {
        String vtipocta_cod ="";
        String vcta_descrip ="";
        String vcta_obser = "";
        String vcta_saldo = "";
        String vcta_dateupdate="";
        String vtipocta_descrip ="";
        String vtipocta_acreedor="";

            admincc = new AdminSQLite(this, "administracion", null, 1);
        dbconcc = admincc.getReadableDatabase();
        String sqlq ="SELECT cta_type,cta_descrip,cta_obser,cta_saldo,cta_dateupdate FROM tbl_ctas WHERE cta_cod='" +ccCta +"'";

        Cursor crcc = dbconcc.rawQuery(sqlq, null);
        if (crcc != null) {
            if (crcc.moveToFirst()) {

                do {
                    vtipocta_cod = crcc.getString(0);
                    vcta_descrip = crcc.getString(1);
                    vcta_obser = crcc.getString(2);
                    vcta_saldo = crcc.getString(3);
                    vcta_dateupdate = crcc.getString(0);
                } while (crcc.moveToNext());

            } else {
                //Toast.makeText(this, "Seleccione: ", Toast.LENGTH_LONG).show();
            }
        }
        crcc.close();


        etcd.setText(ccCta);
        etccsaldo.setText(vcta_saldo);
        etccobser.setText(vcta_obser);
        etccdescripm.setText(vcta_descrip);


        sqlq = "SELECT tipocta_id,tipocta_descrip,tipocta_acreedor   FROM tbl_tipoctas WHERE tipocta_id='" +vtipocta_cod +"'";

        Cursor crcctc = dbconcc.rawQuery(sqlq, null);

         if (crcctc != null) {
             if (crcctc.moveToFirst()) {

                 do {
                     vtipocta_cod = crcctc.getString(0);
                     vtipocta_descrip = crcctc.getString(1);
                     vtipocta_acreedor = crcctc.getString(2);

                 } while (crcctc.moveToNext());
             }
         }

        crcctc.close();
        dbconcc.close();
        sptipocta.setSelection(dataAdapterspcctc.getPosition(vtipocta_descrip));

        //tvcta.setVisibility(View.INVISIBLE);

        /*
        etcd=(EditText) findViewById(R.id.ccetdescrip);
        etccsaldo=(EditText) findViewById(R.id.ccetsaldo);
        etccobser=(EditText) findViewById(R.id.ccetobser);
        etccdescripm=(EditText) findViewById(R.id.etccdescripm);
        etcd.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        final Button ccbtnacerca2=(Button) findViewById(R.id.ccbtnAcerca2);
        Button ccbtnsalir2=(Button) findViewById(R.id.ccbtnSalir);
        ccbtnok =(Button) findViewById(R.id.ccbtnIngresarDato);
        spcta=(Spinner) findViewById(R.id.spctasexistentes);
        sptipocta=(Spinner) findViewById(R.id.spcctipocta);
        tvcta=(TextView) findViewById(R.id.cctv3); */


    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
