package tse.fi2.info4.tbek;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;


public class MyTableModel extends AbstractTableModel
{
	static Connection myConn = null;
	static Statement stmt = null;
	static ResultSet myRs = null;	
	private String[] columnNames;
    private Class[] columnClasses;
    private List<List<Object>> cells = new ArrayList<List<Object>>();
 
    public MyTableModel() {
        columnNames = new String[1];
        columnNames[0] = "Result Set";
 
        List<Object> row = new ArrayList<Object>();
        row.add("No data");
        cells.add(row);
    }
   public static ResultSet lecturebd() {
	    
    	myConn=GestionBD.connexion_bd();
    	
    	try {
    		//con.setHoldability(ResultSet.CLOSE_CURSORS_AT_COMMIT);
    		stmt=myConn.createStatement();
    		myRs=stmt.executeQuery("SELECT *  FROM Utilisateur;");
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		/*finally {
    			connexionbd.fermeture_connexion(con,s , resultat);
    		}*/
    	
    		return myRs;	    		
    	}
    public MyTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
 
        // SOMETHING TO KEEP AN EYE ON! It looks like the ResultSetMetaData
        // object screws up once the ResultSet itself has been read (ie by
        // rs.next() ). Putting any rsmd.XXXX commands after the "while" loop at
        // the bottom throws a nasty exception. A bug on the SQLite side I think.
 
        columnNames = new String[rsmd.getColumnCount()];
        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
            columnNames[i-1] = rsmd.getColumnName(i);
        }
 
        columnClasses = new Class[rsmd.getColumnCount()];
        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
            int columnType = rsmd.getColumnType(i);
 
            switch (columnType) {
                case java.sql.Types.INTEGER:
                case java.sql.Types.NUMERIC:
                    columnClasses[i-1] = Integer.class;
                    break;
                case java.sql.Types.VARCHAR:
                case java.sql.Types.CHAR:
                    columnClasses[i-1] = String.class;
                    break;
                case java.sql.Types.DECIMAL:
                case java.sql.Types.FLOAT:
                    columnClasses[i-1] = Float.class;
                default:
                    columnClasses[i-1] = Object.class;
                    break;
            }
        }
 
        while (rs.next()) {
            List<Object> row = new ArrayList<Object>();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                row.add(rs.getString(i));
            }
            cells.add(row);
        }
    }
 
    public static DefaultTableModel buildTableModel(ResultSet rs)
	        throws SQLException {

	    ResultSetMetaData metaData = rs.getMetaData();
	    
	    // names of columns
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = metaData.getColumnCount();
	    for (int column = 1; column <= columnCount; column++) {
	        columnNames.add(metaData.getColumnName(column));
	    }

	    // data of the table
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    while (rs.next()) {
	        Vector<Object> vector = new Vector<Object>();
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
	            vector.add(rs.getObject(columnIndex));
	        }
	        data.add(vector);
	    }

	    return new DefaultTableModel(data, columnNames);

	}
	
	public static JTable table() throws SQLException
	{
	Connection myConn= GestionBD.connexion_bd();
	stmt = myConn.createStatement();
	 ResultSet rs = stmt.executeQuery("select * from Utilisateur");

	    // It creates and displays the table
	    JTable table = new JTable(buildTableModel(rs));
	    rs.close();
	    stmt.close();
	    myConn.close();
	    

	return table;
	}
	public static JTable actualisation() throws SQLException {
   	    //try{
//    	ResultSet new_rs =Connexionbd.lecturebd();
//	    final MyTableModel new_rtm = new MyTableModel( new_rs );	    
//	    tableau.setModel(new_rtm);
	    //}
        
        	JTable tableau= table();
        	
		
		return tableau;

}
	
    public int getRowCount() {
        return cells.size();
    }
 
    public int getColumnCount() {
        return columnNames.length;
    }
 
    @Override
    public Class getColumnClass(int columnIndex) {
        return columnClasses[columnIndex];
    }
 
    public Object getValueAt(int rowIndex, int columnIndex) {
        return cells.get(rowIndex).get(columnIndex);
    }
 
    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }
	

	}