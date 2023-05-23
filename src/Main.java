import java.sql.*;

public class Main {


    private static final String sqlCreateTable = "DROP TABLE IF EXISTS Dentista; CREATE TABLE Dentista"
            + "("
            + " nome VARCHAR(100) NOT NULL,"
            + " sobrenome VARCHAR(100) NOT NULL,"
            + " matricula VARCHAR(100) NOT NULL"
            + ")";

    private static final String sqlInsert = "INSERT INTO Dentista (nome,sobrenome , matricula) VALUES (?, ?, ?)";

    private static final String sqlUpdate = "UPDATE Dentista SET matricula = ? WHERE matricula = ?";

    public static void main(String[] args) throws Exception {

        Dentista dentista = new Dentista("Lucas", "Santos", "123");

        Connection connection = null;

        try{

            connection = getConnection();


            Statement statement = connection.createStatement();
            statement.execute(sqlCreateTable);


            PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert);



            preparedStatement.setString(1, dentista.getNome());

            preparedStatement.setString(2, dentista.getSobrenome());

            preparedStatement.setString(3, dentista.getMatricula());

            //Executando nossa query preparada
            preparedStatement.execute();

            String sql = "SELECT * FROM Dentista";

            Statement statement1 = connection.createStatement();
            ResultSet rs = statement1.executeQuery(sql);

            while (rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getString(2)
                        + " " + rs.getString(3));
            }


            connection.setAutoCommit(false);

            PreparedStatement psUpdate = connection.prepareStatement(sqlUpdate);
            psUpdate.setString(1, "321");
            psUpdate.setString(2, dentista.getMatricula());
            psUpdate.execute();


            connection.commit();


            connection.setAutoCommit(true);

            statement1 = connection.createStatement();
            rs = statement1.executeQuery(sql);

            while (rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getString(2)
                        + " " + rs.getString(3) );
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {

            //encerrando a conexao com o banco de dados
            connection.close();
        }

    }
    private static Connection getConnection() throws Exception {
        Class.forName("org.h2.Driver").newInstance();
        return DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
    }
}