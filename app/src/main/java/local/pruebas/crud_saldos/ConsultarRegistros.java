package local.pruebas.crud_saldos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import android.database.sqlite.*;


import java.util.ArrayList;
import java.util.List;

public class ConsultarRegistros extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    final Context context = this;
    private Spinner sprevisar;
    private Button btnsalir3;
    public String tipocons;
    private List<String> lista0;
    private ArrayAdapter<String> dataAdaptersprv;
    private AdminSQLite admin2;
    private SQLiteDatabase dbcon2;
    public ScrollView sv;
    public  TextView tvsaldosum;
    private TableLayout table_layout,table_layout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_registros);
        sv = (ScrollView) findViewById(R.id.scrollView);
        tvsaldosum=(TextView) findViewById(R.id.tvsaldosum);
        table_layout = (TableLayout) findViewById(R.id.tableLayout1);
        table_layout2 = (TableLayout) findViewById(R.id.tableLayout2);

        btnsalir3 = (Button) findViewById(R.id.btnSalir3);
        sprevisar = (Spinner) findViewById(R.id.sprevisar);

        lista0 = new ArrayList<String>();

        dataAdaptersprv = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lista0);
        dataAdaptersprv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        admin2 = new AdminSQLite(this, "administracion", null, 1);
        dbcon2 = admin2.getReadableDatabase();

        //Llenado de Valores Spinner Ctas a revisar

        Cursor cr0 = dbcon2.rawQuery("SELECT * FROM tbl_tipoctas", null);
        if (cr0 != null) {
            if (cr0.moveToFirst()) {

                do {
                    String vtipocta_cod = cr0.getString(1);
                    lista0.add(vtipocta_cod);
                    sprevisar.setAdapter(dataAdaptersprv);

                } while (cr0.moveToNext());

            } else {
                //Toast.makeText(this, "Seleccione: ", Toast.LENGTH_LONG).show();
            }
        }


            //********** LLENADO DE MOVIMIENTOS RECIENTES
            Cursor cr2 = dbcon2.rawQuery("SELECT B.cta_cod, A.reg_date, A.reg_monto, A.reg_es,A.reg_concept, A.tbl_regscol "+
                    " from tbl_regs A, tbl_ctas B WHERE A.cod_cta=B.cta_id "+
                    "order by A.reg_date DESC LIMIT 20", null);


                try
            {
                BuildTableMovs(cr2);
            }
            catch(Exception e)
            {
                Toast.makeText(this,e.getMessage()+"-CR2" , Toast.LENGTH_LONG).show();
            }
        // ****

            sprevisar.setOnItemSelectedListener(this);

            btnsalir3.setOnClickListener(new View.OnClickListener() {
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
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Cierra Actividad si da click en si

                                    ConsultarRegistros.this.finish();
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
        sv.setFocusableInTouchMode(true);
        sv.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);

        }


public void consultar(String tcons){

    int ctaidsprevisar=sprevisar.getSelectedItemPosition()+1;

        Cursor cr = dbcon2.rawQuery("SELECT cta_cod,cta_dateupdate,cta_saldo AS SALDO FROM tbl_ctas where cta_type=" + ctaidsprevisar,null);
        Cursor crsum = dbcon2.rawQuery("SELECT sum(cta_saldo) AS SALDO FROM tbl_ctas where cta_type=" + ctaidsprevisar,null);

                //"SELECT A.cta_cod,A.cta_dateupdate,sum(B.reg_monto) || '/' || A.cta_saldo AS SALDO FROM tbl_ctas as A, "+
                //"tbl_regs as B WHERE B.cod_cta=A.cta_id and B.reg_date between date('now','-15 day') and date('now','+1 day') and B.reg_es='S' and A.cta_type=" + ctaidsprevisar+" GROUP by A.cta_cod, A.cta_dateupdate"


    try
    {
        BuildTableCtas(cr,crsum);
     }
    catch(Exception e)
    {
        Toast.makeText(this,e.getMessage()+"-CR" , Toast.LENGTH_LONG).show();
    }

   //Hacer SCROLL hasta arriba
    sv.setFocusableInTouchMode(true);
    sv.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);



}
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item="";
        Spinner spinner = (Spinner) parent;

        if(spinner.getId() == R.id.sprevisar)
        {
            tipocons = parent.getItemAtPosition(position).toString();
            consultar(tipocons);

        }

        //Toast.makeText(parent.getContext(), "SCROLL: " + tipocons, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {

    }

    private void BuildTableCtas(Cursor c, Cursor cs) {
        table_layout.removeAllViews();
        int ctaidsprevisar=sprevisar.getSelectedItemPosition()+1;
        // limpiar Cursor c = dbcon2.rawQuery("SELECT cta_cod,cta_dateupdate,cta_saldo  FROM tbl_ctas where cta_type="+ctaidsprevisar, null);


        // Agregar filas headers
        TableRow rowHeader = new TableRow(context);
        rowHeader.setBackgroundColor(Color.parseColor("#c0c0c0"));
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        String[] headerText={"COD","ACTUALIZADO","SALDO"};
        for(String ce:headerText)
        {
            TextView tv = new TextView(this);
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(15);
            tv.setPadding(5, 5, 5, 5);
            tv.setText(ce);
            rowHeader.addView(tv);
        }
        table_layout.addView(rowHeader);

        //************ Fin de Columnas


        int rows = c.getCount();
        int cols = c.getColumnCount();

       // Toast.makeText(ConsultarRegistros.this, "Hmm "+ctaidsprevisar+"-filas"+rows+"-cols-"+cols, Toast.LENGTH_SHORT).show();
        c.moveToFirst();

        // Ciclo para llenar las filas

        if(c.getCount() >0) {
            if(c.moveToFirst())
            {
                do
                {

                    // Leer datos de columnas
                    String cta_cod = c.getString(c.getColumnIndex("cta_cod"));
                    String cta_dateupdate = c.getString(c.getColumnIndex("cta_dateupdate"));
                    String cta_saldo = c.getString(c.getColumnIndex("SALDO"));

                    // Llenar filas con data
                    TableRow row = new TableRow(context);
                    row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
                    String[] colText = {cta_cod + "", cta_dateupdate, cta_saldo};
                    for (String text : colText) {
                        TextView tv = new TextView(this);
                        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT));
                        tv.setGravity(Gravity.CENTER);
                        tv.setTextSize(16);
                        tv.setPadding(5, 5, 5, 5);
                        tv.setText(text);
                        row.addView(tv);
                    }
                    table_layout.addView(row);
                }while (c.moveToNext());

            }
            if(cs.getCount() >0)
            {
                cs.moveToFirst();
                String cta_saldo = cs.getString(cs.getColumnIndex("SALDO"));
                tvsaldosum.setText("Lps. "+cta_saldo.toString());
            }

        }
    }

    private void BuildTableMovs(Cursor c) {
        table_layout2.removeAllViews();
        int ctaidsprevisar=sprevisar.getSelectedItemPosition()+1;

        // Agregar filas headers
        TableRow rowHeader = new TableRow(context);
        rowHeader.setBackgroundColor(Color.parseColor("#c0c0c0"));
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        String[] headerText={"Codigo","Actualizado","Monto","Ent/Sal","Concepto","Observacion"};
        for(String ce:headerText)
        {
            TextView tv = new TextView(this);
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(14);
            tv.setPadding(1, 1, 1, 1);
            tv.setText(ce);
            rowHeader.addView(tv);
        }
        table_layout2.addView(rowHeader);

        //************ Fin de Columnas
        int rows = c.getCount();
        int cols = c.getColumnCount();

        c.moveToFirst();

        // Ciclo para llenar las filas

        if(c.getCount() >0) {
            if(c.moveToFirst())
            {
                do
                {
                    // Leer datos de columnas
                    String vcod_cta = c.getString(0);
                    String vreg_date = c.getString(1);
                    String vreg_monto = c.getString(2);
                    String vreg_es = c.getString(3);
                    String vreg_concept = c.getString(4);
                    String vtbl_regscol = c.getString(5);


                    // crear filas con la data
                    TableRow row = new TableRow(context);
                    row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
                    String[] colText = {vcod_cta, vreg_date, vreg_monto,vreg_es,vreg_concept,vtbl_regscol};
                    for (String text : colText) {
                        TextView tv = new TextView(this);
                        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                                TableRow.LayoutParams.WRAP_CONTENT));
                        tv.setGravity(Gravity.CENTER);
                        tv.setTextSize(11);
                        tv.setPadding(1,1,1,1);
                        tv.setText(text);
                        row.addView(tv);
                    }
                    table_layout2.addView(row);
                }while (c.moveToNext());


            }
        }
    }


}
