package com.example.springbootwiththymleaf.connect;

import com.example.springbootwiththymleaf.model.Owner;
import org.apache.log4j.Logger;
import org.springframework.dao.DataRetrievalFailureException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCConnect {
    public static final org.apache.log4j.Logger logger = Logger.getLogger(JDBCConnect.class);
    Connection conn=null;
    Statement stat=null;
    public void connect() {
        try {
            conn = DriverManager.getConnection("jdbc:h2:mem://localhost/~/testdb", "sa", "");//application.properties ayarlarında oluşturduğumuz şifre
            conn.setAutoCommit(false);
            System.out.println("Connection Success");
        } catch (SQLException throwables) {
            logger.error("Connection Failed!!!");
        }
    }
           public void show(){
            try {
                stat=conn.createStatement();
                ResultSet rs=stat.executeQuery("SELECT " +
                        " ID," +
                        "FIRST_NAME," +
                        "LAST_NAME" +
                        " FROM T_OWNER");
                List<Owner>result=new ArrayList<Owner>();
                while (rs.next()){
                    Owner owner=new Owner();
                    owner.setId(rs.getLong("ID"));
                    owner.setFirstName(rs.getString("FIRST_NAME"));
                    owner.setLastName(rs.getString("LAST_NAME"));
                    result.add(owner);
                    conn.commit();
                    logger.info("ID:"+owner.getId()+" "
                            +"Name:"+owner.getFirstName()+" "+
                            "SurName:"+owner.getLastName()+" \n");
                }
            } catch (SQLException throwables) {
                logger.error(throwables.getMessage());
                try {
                    conn.rollback();
                } catch (SQLException e) {
                    throw new DataRetrievalFailureException("Cannot execute query",e);
                }


            }
        }
        public void close(){
            try {
                stat.close();

            }catch (Exception e){}
            try {
                conn.close();
                System.out.println("Connection Closed!!!");
            }catch(Exception e){}
        }
    }
