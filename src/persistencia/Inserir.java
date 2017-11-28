package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Inserir {
	public static String url = "jdbc:sqlite:leilao.db";
	
	public void Usuario(String nome, String cpf, String email, String tipo)
	{
		String sql = "INSERT INTO usuarios(nome,cpf,email,tipo) VALUES(?,?,?,?)";
		 
        try (Connection conn = DB.Conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setString(2, cpf);
            pstmt.setString(3, email);
            pstmt.setString(4, tipo);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	
	public void Lotes(int idVendedor, double precoMinimo)
	{
		String sql = "INSERT INTO lotes(idVendedor,precoMinimo) VALUES(?,?)";
		 
        try (Connection conn = DB.Conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idVendedor);
            pstmt.setDouble(2, precoMinimo);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}

	public void Itens(String descricao, String descricaoCompleta, String categoria, int idLote)
	{
		String sql = "INSERT INTO itens(descricao,descricaoCompleta,categoria,idLote) VALUES(?,?,?,?)";
		 
        try (Connection conn = DB.Conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, descricao);
            pstmt.setString(2, descricaoCompleta);
            pstmt.setString(3, categoria);
            pstmt.setInt(4, idLote);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}

	public void Lances(int idLote, int idUsuario, double valor)
	{
		String sql = "INSERT INTO lances(idLote,idUsuario,valor) VALUES(?,?,?)";
		
        try(Connection conn = DB.Conectar();
        	PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, idLote);
            pstmt.setInt(2, idUsuario);
            pstmt.setDouble(3, valor);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
}