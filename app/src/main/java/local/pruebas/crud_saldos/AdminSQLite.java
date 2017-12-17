package local.pruebas.crud_saldos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nDCasT on 13/2/2016.
 */
public class AdminSQLite extends SQLiteOpenHelper {


    public AdminSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE tbl_tipoctas (" +
                "tipocta_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "tipocta_descrip varchar(45) DEFAULT NULL," +
                "tipocta_dateupdate datetime DEFAULT CURRENT_TIMESTAMP,"+
                "tipocta_acreedor INTEGER DEFAULT NULL)");

        db.execSQL("INSERT into tbl_tipoctas Values"+
                "(1,'Ahorros',DATETIME('now', 'localtime'),1),"+
                "(3,'Cuentas por Pagar',DATETIME('now', 'localtime'),2),"+
                "(4,'Prestamos',DATETIME('now', 'localtime'),2),"+
                "(2,'Tarjetas de Credito',DATETIME('now', 'localtime'),2)");

        db.execSQL("CREATE TABLE tbl_ctas (" +
                "cta_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "cta_cod varchar(15) DEFAULT NULL," +
                "cta_type int(11) DEFAULT NULL," +
                "cta_descrip varchar(45) DEFAULT NULL," +
                "cta_obser varchar(95) DEFAULT NULL,"+
                "cta_saldo double(16,2) DEFAULT NULL,"+
                "cta_dateupdate datetime DEFAULT CURRENT_TIMESTAMP)");

        db.execSQL("INSERT into tbl_ctas Values"+
                "(null,'EFEHNL',1,'Efectivo Lempiras','',250.00,DATETIME('now', 'localtime')),"+
                "(null,'BPHNL',1,'BP Lempiras','',1919.06,DATETIME('now', 'localtime')),"+
                "(null,'CB',2,'Cashback HNL Ficoa','',4578.00,DATETIME('now', 'localtime')),"+
                "(null,'CBUSD',2,'CashBack USD Fico','',8897.30,DATETIME('now', 'localtime'))," +
                "(null,'VC',2,'Credomatic Economia Lempiras','',-1500.00,DATETIME('now', 'localtime')),"+

                "(null,'CB Extra',3,'Extra CB Fico','',59397.85,DATETIME('now', 'localtime')),"+
                "(null,'Carro',4,'Prestamo Carro','',131605.00,DATETIME('now', 'localtime')),"+
                "(null,'AD',1,'Ahorro Diario','',360.00,DATETIME('now', 'localtime'))");


        db.execSQL("CREATE TABLE tbl_presdefine ("+
                "pres_id int(11) NOT NULL,"+
                "pres_saldo decimal(16,2) DEFAULT NULL,"+
                "pres_quincena int(11) DEFAULT NULL,"+
                "pres_month int(11) DEFAULT NULL,"+
                "pres_year int(11) DEFAULT NULL)")   ;

        db.execSQL("CREATE TABLE tbl_presupuesto ("+
                "pres_id int(11) NOT NULL,"+
                "pres_cod varchar(15) DEFAULT NULL,"+
                "pres_descrip varchar(45) DEFAULT NULL,"+
                "pres_obser varchar(45) DEFAULT NULL,"+
                "pres_type varchar(1) DEFAULT NULL)" );

        db.execSQL("CREATE TABLE tbl_regs ("+
                "reg_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
                "cod_pres int(11) DEFAULT NULL,"+
                "cod_cta int(11) DEFAULT NULL,"+
                "reg_date datetime DEFAULT CURRENT_TIMESTAMP,"+
                "reg_monto double(16,2) DEFAULT NULL,"+
                "reg_es varchar(1) DEFAULT NULL,"+
                "reg_concept varchar(45) DEFAULT NULL,"+
                "tbl_regscol varchar(45) DEFAULT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists tbl_tipoctas");
        db.execSQL("CREATE TABLE tbl_tipoctas (" +
                "tipocta_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "tipocta_descrip varchar(45) DEFAULT NULL," +
                "tipocta_dateupdate datetime DEFAULT CURRENT_TIMESTAMP,"+
                "tipocta_acreedor INTEGER DEFAULT NULL)");

        db.execSQL("INSERT into tbl_tipoctas Values"+
                "(1,'Ahorros',DATETIME('now', 'localtime'),1),"+
                "(3,'Cuentas por Pagar',DATETIME('now', 'localtime'),2),"+
                "(4,'Prestamos',DATETIME('now', 'localtime'),2),"+
                "(2,'Tarjetas de Credito',DATETIME('now', 'localtime'),2)");

        db.execSQL("drop table if exists tbl_ctas");
        db.execSQL("CREATE TABLE tbl_ctas (" +
                "cta_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "cta_cod varchar(15) DEFAULT NULL," +
                "cta_type int(11) DEFAULT NULL," +
                "cta_descrip varchar(45) DEFAULT NULL," +
                "cta_obser varchar(95) DEFAULT NULL,"+
                "cta_saldo double(16,2) DEFAULT NULL,"+
                "cta_dateupdate datetime DEFAULT CURRENT_TIMESTAMP)");

        db.execSQL("INSERT into tbl_ctas Values"+
                "(null,'EFEHNL',1,'Efectivo Lempiras','',0.00,DATETIME('now', 'localtime')),"+
                "(null,'BPHNL',1,'BP Lempiras','',0.00,DATETIME('now', 'localtime')),"+
                "(null,'DM',2,'Disfruta Mas Ficohsa','',0.00,DATETIME('now', 'localtime')),"+
                "(null,'CB',2,'CashBack Fico','',0.00,DATETIME('now', 'localtime'))," +
                "(null,'CE',2,'Credomatic Economia Lempiras','',0.00,DATETIME('now', 'localtime')),"+
                "(null,'DM Extra',3,'Extra DM Fico','',0.00,DATETIME('now', 'localtime')),"+
                "(null,'CB Intra',3,'Intra CB Fico','',0.00,DATETIME('now', 'localtime')),"+
                "(null,'Carro',4,'Prestamo Carro','',0.00,DATETIME('now', 'localtime')),"+
                "(null,'AD',1,'Ahorro Diario','',0.00,DATETIME('now', 'localtime'))");

        db.execSQL("drop table if exists tbl_presdefine");
        db.execSQL("CREATE TABLE tbl_presdefine ("+
                "pres_id int(11) NOT NULL,"+
                "pres_saldo decimal(16,2) DEFAULT NULL,"+
                "pres_quincena int(11) DEFAULT NULL,"+
                "pres_month int(11) DEFAULT NULL,"+
                "pres_year int(11) DEFAULT NULL)")   ;

        db.execSQL("drop table if exists tbl_presupuesto");
        db.execSQL("CREATE TABLE tbl_presupuesto ("+
                "pres_id int(11) NOT NULL,"+
                "pres_cod varchar(15) DEFAULT NULL,"+
                "pres_descrip varchar(45) DEFAULT NULL,"+
                "pres_obser varchar(45) DEFAULT NULL,"+
                "pres_type varchar(1) DEFAULT NULL)" );

        db.execSQL("drop table if exists tbl_regs");
        db.execSQL("CREATE TABLE tbl_regs ("+
                "reg_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
                "cod_pres int(11) DEFAULT NULL,"+
                "cod_cta int(11) DEFAULT NULL,"+
                "reg_date datetime DEFAULT CURRENT_TIMESTAMP,"+
                "reg_monto double(16,2) DEFAULT NULL,"+
                "reg_es varchar(1) DEFAULT NULL,"+
                "reg_concept varchar(45) DEFAULT NULL,"+
                "tbl_regscol varchar(45) DEFAULT NULL)");
    }
}
