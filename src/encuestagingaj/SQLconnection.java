package encuestagingaj;
import java.sql.*;
import java.util.Hashtable;

/*
 * 
 */
public class SQLconnection {

    private String user;
    private String password;
    private String db;
    private String host;
    private String url;
    private Connection conn = null;
    private Statement stm;
    private Statement stm_count;
    private ResultSet rs;
    private ResultSet rs_count;

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

    public boolean connectar()
    {
        try {
            Class.forName("org.gjt.mm.mysql.Driver");
            conn = DriverManager.getConnection(url, user, password);
            if (conn != null){
                //System.out.println("Conexión a base de datos "+url+" … Ok");
                stm = conn.createStatement();
                stm_count = conn.createStatement();
                return true;
            }
        }
        catch(SQLException ex) {
            System.out.println("Hubo un problema al intentar conectarse con la base de datos "+url);
        }
        catch(ClassNotFoundException ex) {
            System.out.println(ex);
        }
        return false;
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


    public ResultSet consultar(String tabla) throws SQLException
    {
        rs = stm.executeQuery("SELECT * FROM " + tabla);
        return rs;
    }
    
    public int idLastPregunta(String tabla){
        
        int id = 0;
        try{
            rs = stm.executeQuery("SELECT * FROM " + tabla + " ORDER BY id_votacion DESC LIMIT 1");
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
    
    public String[][] getRespuestas(int id){
        
        String respuestas[][] = null;
        try{
            String 
                sql_count      = "SELECT count(opcion) FROM opciones WHERE id_votacion = '"+id+"'",
                sql_respuestas = "SELECT opcion, id_opcion FROM opciones WHERE id_votacion = '"+id+"'";
            
            
            rs = stm.executeQuery(sql_count);
            rs.next();
            int n = rs.getInt(1);
            respuestas = new String[n][2];
            
            
            rs = stm.executeQuery(sql_respuestas);
            rs.next();
            for(int i = 0; i < n; i++){
                respuestas[i][0] = rs.getString(1);
                respuestas[i][1] = rs.getString(2);
                rs.next();
            }
            
        }catch(SQLException ex){System.out.println(ex);}
        return respuestas;
        
    }
    
    public void insertar(int id_opcion,int id_votacion,String MAC) 
    {
        try {
            stm.execute("INSERT INTO voto (mac_tv, id_votacion,id_opcion) VALUES ('" + MAC + "','" +id_votacion + "', '"+id_opcion+"')");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public String[][] getVotos(int id_encuesta){
        
        String votos[][] = null;
        
        try{
            
            //Aquí traigo el número de respuestas que xiste
            String sql_count      = "SELECT count(opcion) FROM opciones WHERE id_votacion = '"+id_encuesta+"'",
                   sql_respuestas = "SELECT opcion, id_opcion FROM opciones WHERE id_votacion = '"+id_encuesta+"'";
            
            rs = stm.executeQuery(sql_count);
            rs.next();
            int n = rs.getInt(1);
            votos = new String[n][3];
            
            String no_votos = "";
            rs = stm.executeQuery(sql_respuestas);
            rs.next();
            for(int i = 0; i < n; i++){
                
                votos[i][1] = rs.getString(2);
                votos[i][2] = rs.getString(1);
                rs.next();
                
                no_votos =  "SELECT COUNT( v.id_opcion ) "+
                            "FROM voto v, opciones o "+
                            "WHERE v.id_votacion =  '"+id_encuesta+"' "+
                            "AND v.id_opcion = o.id_opcion "+
                            "AND v.id_opcion = '"+votos[i][1]+"'";
                
                rs_count = stm_count.executeQuery(no_votos);
                rs_count.next();
                votos[i][0] = rs_count.getString(1);
                rs_count.close();
                System.out.println("VOTO "+votos[i][1]+" : "+ votos[i][0]);
            }            
            
        }catch(SQLException ex){System.out.println(ex);}
        return votos;
        
    }
    /*
     * Retorna verdadero si ya voto y false si aun no vota
     */
    public boolean yaVoto(String mac, int encuesta){
        /**/
        int n = 0;
        String sql_count   = "SELECT count(*) FROM voto WHERE mac_tv = '"+mac+"' AND id_votacion = '"+encuesta+"'";
        try{       
            rs = stm.executeQuery(sql_count);
            rs.next();
            n =  rs.getInt(1);
        }catch(SQLException ex){System.out.println(ex);}

        if(n>0) return true;
        /**/
        return false;
    }
    /*
     * 
     */
    public int noRespuestas(int id_encuesta){
        /**/
        int n = 0;
        String sql_count      = "SELECT count(opcion) FROM opciones WHERE id_votacion = '"+id_encuesta+"'";
        try{       
            rs = stm.executeQuery(sql_count);
            rs.next();
            n =  rs.getInt(1);
        }catch(SQLException ex){System.out.println(ex);}
        
        return n;
    }

}
