package com.wizeline.DAO;

import java.io.*;
import java.util.logging.Logger;

public class UserDAOImpl implements UserDAO{
    private static final Logger log = Logger.getLogger(UserDAOImpl.class.getName());

    @Override
    public String createUser(String user, String pass) {
        createFile();
        log.info("Inicia procesamiento en capa de acceso a datos");
        log.info("Inicia proceso de alta de usuario en BD");

        writeFile(user, pass);

        log.info("Alta exitosa");
        return "success";
    }

    @Override
    public String login(String user, String pass) {
        createFile();
        log.info("Inicia procesamiento en capa de acceso a datos");
        log.info("Inicia proceso de login");

        if("success".equals(readFile(user, pass))){
            return "success";
        }else {
            return "Usuario o pass incorrecto";
        }
    }

    private String readFile(String user, String pass)  {
        String result = "fail";

        try {
            File myObj = new File("file.txt");
            BufferedReader br = new BufferedReader(new FileReader(myObj));

            String line = "";

            while((line = br.readLine()) != null){
                if(line.contains(user) && line.contains(pass)){
                    result = "success";
                }
            }
            br.close();
        } catch (IOException e) {
            log.info("Ocurrio una excepcion");
            e.printStackTrace();
        }
        return result;
    }

    private void createFile() {
        try {
            File myObj = new File("file.txt");
            if(myObj.createNewFile()){
                log.info("archivo creado con exito: "+myObj.getName());
            }else{
                log.info("Un error ha ocurrido");
            }
        } catch (IOException e) {
            log.info("Ocurrio una excepcion");
            e.printStackTrace();
        }
    }

    private void writeFile(String user, String pass) {
        try {
            File myObj = new File("file.txt");
            if(myObj.createNewFile()){
                log.info("archivo creado con exito: "+myObj.getName());
            }else{
                log.info("Un error ha ocurrido en writeFile()");
            }

            FileWriter fileWriter = new FileWriter(myObj.getName(), true);
            BufferedWriter bw = new BufferedWriter(fileWriter);
            bw.write(user+ ", "+ pass);
            bw.newLine();
            bw.close();
            log.info("Escritura completada");
        } catch (IOException e) {
            log.info("Ocurrio una excepcion");
            e.printStackTrace();
        }
    }
}
