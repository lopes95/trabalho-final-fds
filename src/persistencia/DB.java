package persistencia;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DB{
	public static String url = "jdbc:sqlite:leilao.db";
	
	public static Connection Conectar() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } 
		return conn;
    }
	
	public static void Desconectar(Connection conn){
        try {
        	if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
	
	private static void criarTabelaUsuario() {
        String sql = "CREATE TABLE IF NOT EXISTS usuarios (\n"
        			+ "id integer PRIMARY KEY,\n"
        			+ "nome text NOT NULL,\n"
        			+ "cpf text NOT NULL,\n"
        			+ "email text NOT NULL,\n"
        			+ "tipo text NOT NULL\n"
        			+ ");";
        
        try (Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
	
	private static void criarTabelaLotes() {
        String sql = "CREATE TABLE IF NOT EXISTS lotes (\n"
        			+ "id integer PRIMARY KEY,\n"
        			+ "idVendedor integer NOT NULL,\n"
        			+ "precoMinimo real NOT NULL,\n"
        			+ "FOREIGN KEY (idVendedor) REFERENCES usuarios(id)\n"
        			+ ");";
        
        try (Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
	
	private static void criarTabelaItens() {
        String sql = "CREATE TABLE IF NOT EXISTS itens (\n"
	        		+ "id integer PRIMARY KEY,\n"
	        		+ "descricao text NOT NULL,\n"
	        		+ "descricaoCompleta text NOT NULL,\n"
	        		+ "categoria text NOT NULL,\n"
	        		+ "idLote integer NOT NULL,\n"
	        		+ "FOREIGN KEY (idLote) REFERENCES lotes(id)\n"
	        		+ ");";
        
        try (Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
	
	private static void criarTabelaLances() {
        String sql = "CREATE TABLE IF NOT EXISTS lances (\n"
        		+ "id integer PRIMARY KEY,\n"
        		+ "idLote integer NOT NULL,\n"
        		+ "idUsuario integer NOT NULL,\n"
        		+ "valor real NOT NULL,\n"
        		+ "FOREIGN KEY (idUsuario) REFERENCES usuarios(id),\n"
        		+ "FOREIGN KEY (idLote) REFERENCES lotes(id)\n"
        		+ ");";
        
        try (Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

	public static void Seed() {
		Path path = FileSystems.getDefault().getPath("leilao.db");
		try {
			Files.delete(path);
		} catch (IOException e) {
			e.getMessage();
		}
		
		Connection conn = DB.Conectar();
		criarTabelaUsuario();
		criarTabelaLotes();
		criarTabelaItens();
		criarTabelaLances();
		Inserir inserir = new Inserir();
		inserir.Usuario("Joao", "03622115661", "joao@hotmail.com", "vendedor");
		inserir.Usuario("Maria", "84420383534", "maria@homail.com", "vendedor");
		inserir.Usuario("Gustavo", "02614122645", "gustavo@hotmail.com", "vendedor");
		inserir.Usuario("John", "06303121683", "john@hotmail.com", "vendedor");
		inserir.Usuario("Carla", "67882731544", "carla@hotmail.com", "comprador");
		inserir.Usuario("Gabriel", "71639663185", "gabriel@hotmail.com", "comprador");
		inserir.Usuario("Felipe", "95411420520", "felipe@hotmail.com", "comprador");
		inserir.Lotes(1, 500.50);
		inserir.Lotes(2, 230.00);
		inserir.Lotes(3, 100.20);
		inserir.Lotes(4, 80.70);
		inserir.Itens("Sapato Social", "Sapato Masculino Social Com Cinto Carteira Couro Salazari", "Roupas", 4);
		inserir.Itens("Playstation 1", "Console Playstation 1", "Video Game", 2);
		inserir.Itens("Controle", "Controle de Playstation 1", "Video Game", 2);
		inserir.Itens("Jogos", "Jogos para Playstation 1", "Video Game", 2);
		inserir.Itens("Radio Mp3 Player", "Radio Mp3 Player Som Automotivo Usb", "Acessorios", 3);
		inserir.Itens("Pendrive", "Pendrive Bluetooth", "Acessorios", 3);
		inserir.Itens("Pneus 195/55r15", "4 Pneus 195/55r15 Phantom Pirelli 85w - Pneustore", "Carros", 1);
		inserir.Lances(1, 5, 500.51);
		inserir.Lances(2, 6, 240.00);
		inserir.Lances(2, 5, 250.00);
		inserir.Lances(3, 7, 110.00);
		DB.Desconectar(conn);
	}
}
