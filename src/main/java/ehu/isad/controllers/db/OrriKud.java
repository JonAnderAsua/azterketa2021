package ehu.isad.controllers.db;

import ehu.isad.model.Orrialde;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrriKud {

    private static final OrriKud instance = new OrriKud();
    private static final DBController dbcontroller = DBController.getController();

    private OrriKud() {}

    public static OrriKud getInstance() {
        return instance;
    }


    public String getBertsioa(String kodetuta) throws SQLException {
        String eskaera = "SELECT version FROM checksums WHERE md5 LIKE '"+kodetuta+"'";
        ResultSet rs = dbcontroller.execSQL(eskaera);
        String emaitza = "";
        if(rs.next()){
            emaitza = rs.getString("version");
        }
        return emaitza;
    }

    public void sartuDatuBasean(Orrialde o) {
        String eskaera = "INSERT INTO checksums VALUES(1,'"+o.getVersion()+"','"+o.getMd5()+"',0)";
        dbcontroller.execSQL(eskaera);
    }
}
