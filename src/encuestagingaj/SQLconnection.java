package encuestagingaj;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Albert
 */
import java.sql.*;
import java.util.Hashtable;

public class SQLconnection {

    private String user;
    private String password;
    private String db;
    private String host;
    private String url;
    private Connection conn = null;
    private Statement stm;
    private ResultSet rs;

    public SQLconnection()
    {
        this.url = "jdbc:mysql://" + this.host + "/" + this.db;
    }

    public SQLconnection (String server, String usuario, String contraseña, String bd)
    {
        this.user = usuario;
        this.password = contraseña;
        this.host = server;
        this.db = bd;
        this.url = "jdbc:mysql://" + this.host + "/" + this.db;
    }

    public void connectar()
    {
        try {
            Class.forName("org.gjt.mm.mysql.Driver");
            conn = DriverManager.getConnection(url, user, password);
            if (conn != null){
                //System.out.println("Conexión a base de datos "+url+" … Ok");
                stm = conn.createStatement();
            }
        }
        catch(SQLException ex) {
            System.out.println("Hubo un problema al intentar conectarse con la base de datos "+url);
        }
        catch(ClassNotFoundException ex) {
            System.out.println(ex);
        }
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public ResultSet consultar(String tabla) throws SQLException
    {
        rs = stm.executeQuery("SELECT * FROM " + tabla);
        return rs;
    }
    
    public int idLastPregunta(String tabla){
        
        int id = 0;
        try{
            rs = stm.executeQuery("SELECT * FROM " + tabla + " LIMIT 1");
            rs.next();
            id = rs.getInt(1);
        }catch(SQLException ex){System.out.println(ex);}
        
        return id;
    }
    
    public String getPreguntaByID(String tabla, int id)
    {
        String name = null;
        try{
            rs = stm.executeQuery("SELECT pregunta FROM " + tabla + " WHERE id_votacion = '"+id+"' LIMIT 1");
            rs.next();
            name = rs.getString(1);
        }catch(SQLException ex){System.out.println(ex);}
        return name;
    }
    
    public String[] getRespuestas(int id){
        
        String respuestas[] = null;
        try{
            String 
                sql_count      = "SELECT count(opcion) FROM opciones WHERE id_votacion = '"+id+"'",
                sql_respuestas = "SELECT opcion FROM opciones WHERE id_votacion = '"+id+"'";
            
            
            rs = stm.executeQuery(sql_count);
            rs.next();
            int n = rs.getInt(1);
            respuestas = new String[n];
            
            
            rs = stm.executeQuery(sql_respuestas);
            rs.next();
            for(int i = 0; i < n; i++){
                respuestas[i] = rs.getString(1);
                rs.next();
            }
            
        }catch(SQLException ex){System.out.println(ex);}
        return respuestas;
        
    }
    
    public void insertar(Hashtable usuario) 
    {
        try {
            stm.execute("INSERT INTO usuarios (nombre, contraseña) VALUES ('" + usuario.get("nombre") + "','" + usuario.get("contraseña") + "')");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

}
