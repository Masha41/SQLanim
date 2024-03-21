package tables;

import db.MySQLConnector;
import objects.Animal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class AnimalTable extends AbsTable {

    public AnimalTable() {

        super("animals");
        columns = new HashMap<>();
        columns.put("id","bigint PRIMARY KEY AUTO_INCREMENT");
        columns.put("type","varchar(15)");
        columns.put("name","varchar(15)");
        columns.put("color", "varchar(15)");
        columns.put("weight","int");
        columns.put("age","int");
        create();

    }
    public ArrayList<Animal> selectAll(){
        String sqlQuery = String.format("SELECT * FROM %s", tableName);
        return selectByQuery(sqlQuery);
    }

    private ArrayList<Animal> selectByQuery(String sqlQuery) {
        ArrayList<Animal> animals = new ArrayList<>();
        db = new MySQLConnector();
        ResultSet rs = db.executeRequestWithAnswer(sqlQuery);
        try {
            //Перебор строк с данными
            while (rs.next()) {
                //Создать объект
                animals.add(new Animal(
                        rs.getLong("id"),
                        rs.getString("type"),
                        rs.getString("name"),
                        rs.getString("color"),
                        rs.getInt("weight"),
                        rs.getInt("age")

                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            db.close();

        }
        return animals;
    }


    public ArrayList<Animal> selectByType(String type){
        String sqlQuery = String.format("SELECT * FROM %s WHERE type = %s", tableName, type);
        return selectByQuery(sqlQuery);
    }

    public void insert(Animal animal){
        db = new MySQLConnector();
        String sqlQuery =String.format("INSERT INTO %s (type, name, color, weight, age)" +
                        "VALUES('%s', '%s', '%s', '%d', '%s')",
                tableName,
                animal.getType(),
                animal.getName(),
                animal.getColor(),
                animal.getWeight(),
                animal.getAge());
        db.executeRequest(sqlQuery);
        db.close();
    }


    public void update(Animal animal) {
        db = new MySQLConnector();
        String sqlQuery =String.format("UPDATE %s SET"  + "type ='%s', name ='%s', color='%s', weight='%d', age='%d'",
                tableName,
                animal.getType(),
                animal.getName(),
                animal.getColor(),
                animal.getWeight(),
                animal.getAge());
        db.executeRequest(sqlQuery);
        db.close();

    }
    public void delete(long id){
        db = new MySQLConnector();
        String sqlQuery =String.format("DELETE FROM %s WHERE id='%d'",
                tableName,
                id
        );
        db.executeRequest(sqlQuery);
        db.close();

    }
}
