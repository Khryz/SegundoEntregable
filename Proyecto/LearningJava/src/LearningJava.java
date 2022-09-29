import com.sun.net.httpserver.HttpServer;
import com.wizeline.BO.BankAccountBO;
import com.wizeline.BO.BankAccountBOImpl;
import com.wizeline.BO.UserBO;
import com.wizeline.BO.UserBOImpl;
import com.wizeline.DTO.*;
import com.wizeline.exceptions.ExcepcionGenerica;
import com.wizeline.notificacion.NotificacionFactory;
import com.wizeline.notificacion.Notifications;
import com.wizeline.utils.Utils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* Herencia en alguna clase: LearningJava */
public class LearningJava extends Thread{
    private static final Logger log = Logger.getLogger(LearningJava.class.getName());

    /* Uso de al menos tres anotaciones: SuppressWarnings */
    @SuppressWarnings("unchecked")
    private static final String SUCCESS_CODE = "OK0000";

    private static String responseTextThread = "";
    private ResponseDTO response;
    private static String textThread = "";

    public static void main(String[] args) throws IOException {
        log.info("LearningJava - Iniciado servicio REST ...");

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/api/login", (exchange -> {
            log.info("LearningJava - Inicia procesamiento de peticion ...");
            MiResponseDTO response = null;
            String responseText = "";

            if ("GET".equals(exchange.getRequestMethod())) {
                log.info("LearningJava - Procesando peticion HTTP de tipo GET");
                UserDTO user =  new UserDTO();
                user = user.getParameters(splitQuery(exchange.getRequestURI()));
                response = login(user.getUser(), user.getPass());
                JSONObject json = new JSONObject(response);
                responseText = json.toString();

                NotificacionFactory notificacionFactory = new NotificacionFactory();
                Notifications notification = notificacionFactory.usarNotificacion("LOGIN");
                notification.notificacion();

                exchange.getResponseHeaders().set("contentType", "application/json; charset=UTF-8");
                exchange.sendResponseHeaders(200, responseText.getBytes().length);
            } else {

                exchange.sendResponseHeaders(405, -1);
            }
            OutputStream output = exchange.getResponseBody();
            /**
             * Always remember to close the resources you open.
             * Avoid memory leaks
             */
            log.info("LearningJava - Cerrando recursos ...");
            output.write(responseText.getBytes());
            output.flush();
            output.close();
            exchange.close();
        }));

        server.createContext("/api/createUser", (exchange -> {
            log.info("LearningJava - Inicia procesamiento de peticion ...");
            ResponseDTO response = new ResponseDTO();
            String responseText = "";
            /** Validates the type of http request  */
            exchange.getRequestBody();
            if ("POST".equals(exchange.getRequestMethod())) {
                log.info("LearningJava - Procesando peticion HTTP de tipo POST");
                UserDTO user =  new UserDTO();
                user = user.getParameters(splitQuery(exchange.getRequestURI()));
                response = createUser(user.getUser(), user.getPass());
                JSONObject json = new JSONObject(response);
                responseText = json.toString();

                /* Patron de diseño creacional: Factory method */
                NotificacionFactory notificacionFactory = new NotificacionFactory();
                Notifications notification = notificacionFactory.usarNotificacion("CREAR");
                notification.notificacion();

                exchange.getResponseHeaders().set("contentType", "application/json; charset=UTF-8");
                exchange.sendResponseHeaders(200, responseText.getBytes().length);
            } else {
                /** 405 Method Not Allowed */
                exchange.sendResponseHeaders(405, -1);
            }
            OutputStream output = exchange.getResponseBody();
            /**
             * Always remember to close the resources you open.
             * Avoid memory leaks
             */
            log.info("LearningJava - Cerrando recursos ...");
            output.write(responseText.getBytes());
            output.flush();
            output.close();
            exchange.close();
        }));

        server.createContext("/api/getCredentials", (exchange -> {
            log.info("LearningJava - Inicia procesamiento de peticion ...");
            Instant inicioDeEjecucion = Instant.now();
            MiResponseDTO response = null;

            String responseText = "";
            if ("GET".equals(exchange.getRequestMethod())) {
                log.info("LearningJava - Procesando peticion HTTP de tipo GET");

                UserDTO user =  new UserDTO();

                Map<String, String> params = splitQuery(exchange.getRequestURI());

                user = user.getParameters(params);

                String lastUsage = params.get("fecha");
                // Mi validacion de fecha usando Localdate
                if(Utils.validaFormatoFechaLocalDate(lastUsage)){
                    if(Utils.isPasswordValid(user.getPass())){
                        response = login(user.getUser(), user.getPass());

                        BankAccountBOImpl bankAccountBO = new BankAccountBOImpl();
                        BankAccountDTO bankAccountDTO = bankAccountBO.getAccountDetails(user.getUser(), lastUsage);

                        JSONObject json = new JSONObject(bankAccountDTO);
                        responseText = json.toString();
                        exchange.getResponseHeaders().set("contentType", "application/json; charset=UTF-8");
                        exchange.sendResponseHeaders(200, responseText.getBytes().length);
                    } else {
                        responseText = "Password Incorrecto";
                        exchange.getResponseHeaders().add("Content-type", "application/json");
                        exchange.sendResponseHeaders(401, responseText.getBytes().length);
                    }
                } else {
                    responseText = "Formato de Fecha Incorrecto";
                    exchange.getResponseHeaders().add("Content-type", "application/json");
                    exchange.sendResponseHeaders(400, responseText.getBytes().length);
                }
            } else {

                exchange.sendResponseHeaders(405, -1);
            }
            OutputStream output = exchange.getResponseBody();
            Instant finalDeEjecucion = Instant.now();

            /**
             * Always remember to close the resources you open.
             * Avoid memory leaks
             */
            log.info("LearningJava - Cerrando recursos ...");

            System.out.println("Diferencia en milisegundos: "+ChronoUnit.MILLIS.between(inicioDeEjecucion,finalDeEjecucion));

            String total2 = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" milisegundos."));

            String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" milisegundos."));
            System.out.println("Inicio: "+inicioDeEjecucion);
            System.out.println("Fin: "+finalDeEjecucion);

            log.info("Tiempo de respuesta: "+ total);
            System.out.println("Tiempo de respuesta: "+total2);
            output.write(responseText.getBytes());
            output.flush();
            output.close();
            exchange.close();
        }));

        server.createContext("/api/getAccounts", (exchange -> {
            log.info("LearningJava - Inicia procesamiento de peticion ...");

            BankAccountBO bankAccountBO = new BankAccountBOImpl();
            String responseText = "";

            if ("GET".equals(exchange.getRequestMethod())) {
                log.info("LearningJava - Procesando peticion HTTP de tipo GET");

                /* Uso de por lo menos una lista: Lista de cuentas obtenidas */
                List<BankAccountDTO> accounts = bankAccountBO.getAccounts();

                /* Uso de por lo menos un arreglo: JSONArray */
                JSONArray json = new JSONArray(accounts);

                responseText = json.toString();
                exchange.getResponseHeaders().set("contentType", "application/json; charset=UTF-8");
                exchange.sendResponseHeaders(200, responseText.getBytes().length);
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
            OutputStream output = exchange.getResponseBody();
            /**
             * Always remember to close the resources you open.
             * Avoid memory leaks
             */
            log.info("LearningJava - Cerrando recursos ...");
            output.write(responseText.getBytes());
            output.flush();
            output.close();
            exchange.close();
        }));

        server.createContext("/api/createUsers", (exchange -> {
            log.info("LearningJava - Inicia procesamiento de peticion ...");
            ResponseDTO response = new ResponseDTO();
            /** Validates the type of http request  */
            if ("POST".equals(exchange.getRequestMethod())) {
                log.info("LearningJava - Procesando peticion HTTP de tipo POST");

                // Obtenemos el request del body que mandamos
                StringBuilder text = new StringBuilder();
                try (Scanner scanner = new Scanner(exchange.getRequestBody())) {

                    while(scanner.hasNext()) {
                        text.append(scanner.next());
                    }
                }catch (Exception e) {
                    log.severe(e.getMessage());
                    throw new ExcepcionGenerica("Fallo al obtener el request del body");
                }
                textThread = text.toString();

                log.info(textThread);
                // Iniciamos thread

                /* Concurrencia usando 3 hilos y su ejecución: triplica la creacion de los mismos usuarios */
                LearningJava thread = new LearningJava();
                LearningJava hilo2 = new LearningJava();
                LearningJava hilo3 = new LearningJava();

                thread.start();
                hilo2.start();
                hilo3.start();

                // Esperamos a que termine el thread
                while(thread.isAlive());
                while(hilo2.isAlive());
                while(hilo3.isAlive());

                //Runnable usuarios1 = crearUsuarios();

                exchange.getResponseHeaders().set("contentType", "application/json; charset=UTF-8");
                exchange.sendResponseHeaders(200, responseTextThread.getBytes().length);
            } else {
                /** 405 Method Not Allowed */
                exchange.sendResponseHeaders(405, -1);
            }
            OutputStream output = exchange.getResponseBody();
            /**
             * Always remember to close the resources you open.
             * Avoid memory leaks
             */
            log.info("LearningJava - Cerrando recursos ...");
            output.write(responseTextThread.getBytes());
            output.flush();
            output.close();
            exchange.close();
        }));

        server.createContext("/api/getAccountsGroupByType", (exchange -> {
            //log.info(msgProcPeticion);
            Instant inicioDeEjecucion = Instant.now();
            BankAccountBO bankAccountBO = new BankAccountBOImpl();
            String responseText = "";
            /** Validates the type of http request  */
            if ("GET".equals(exchange.getRequestMethod())) {
                log.info("LearningJava - Procesando peticion HTTP de tipo GET");
                List<BankAccountDTO> accounts = bankAccountBO.getAccounts();
                /* Uso de por lo menos un mapa: Mapa para agrupar cuentas */
                Map<String, List<BankAccountDTO>> groupedAccounts;

                /* Uso de por lo menos 1 Interfaz Funcional: Function */
                /* Uso de por lo menos 1 función Lambda y asignada a una Interfaz Funcional */
                Function<BankAccountDTO, String> groupFunction = (account) -> account.getAccountType().toString();

                /* Uso de por lo menos 1 Stream de datos: collect */
                /* Uso de por lo menos 2 tipos de colectores: 1 - Collectors.groupingBy */
                /* Uso de por lo menos 2 operaciones intermedias:1 - groupedAccounts */
                groupedAccounts = accounts.stream().collect(Collectors.groupingBy(groupFunction));


                JSONObject json = new JSONObject(groupedAccounts);
                responseText = json.toString();

                exchange.getResponseHeaders().set("contentType", "application/json; charset=UTF-8");
                exchange.sendResponseHeaders(200, responseText.getBytes().length);
            } else {
                /** 405 Method Not Allowed */
                exchange.sendResponseHeaders(405, -1);
            }
            OutputStream output = exchange.getResponseBody();
            Instant finalDeEjecucion = Instant.now();
            /**
             * Always remember to close the resources you open.
             * Avoid memory leaks
             */
            log.info("LearningJava - Cerrando recursos ...");
            String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
            log.info("Tiempo de respuesta: ".concat(total));
            output.write(responseText.getBytes());
            output.flush();
            output.close();
            exchange.close();
        }));

        server.createContext("/api/suma/cantidadArray", (exchange -> {
            Instant inicioDeEjecucion = Instant.now();
            String responseText = "";
            /** Validates the type of http request  */
            if ("GET".equals(exchange.getRequestMethod())) {
                log.info("LearningJava - Procesando peticion HTTP de tipo GET");
                SumaResponseDTO sumaResponseDTO = new SumaResponseDTO();
                SumaRequestDTO sumaRequestDTO = new SumaRequestDTO();

                Map<String, String> params = splitQuery(exchange.getRequestURI());

                sumaRequestDTO = sumaRequestDTO.getRequest(params);

                Integer[] intArray = {6,3,1,2,4,5,0};
                /* Uso de por lo menos 2 tipos de colectores: 2 - Collectors.counting */
                long count = Arrays.stream(intArray).collect(Collectors.counting());

                /* Uso de por lo menos 2 operaciones intermedias:2 - streamOrdenado */
                List<Integer> streamOrdenado = new ArrayList<>();
                Arrays.stream(intArray).sorted().forEach(element -> streamOrdenado.add(element));

                BiFunction<Double, Double, Double> sumaLambda = (x , y) -> x+y;
                Double sumaTotal = sumaLambda.apply(sumaRequestDTO.getNumero1(), sumaRequestDTO.getNumero2());

                System.out.println(sumaTotal);

                sumaResponseDTO.setSumaTotal(sumaTotal);
                sumaResponseDTO.setCantidadArray(count);
                sumaResponseDTO.setListaOrdenada(streamOrdenado);

                JSONObject json = new JSONObject(sumaResponseDTO);
                responseText = json.toString();

                exchange.getResponseHeaders().set("contentType", "application/json; charset=UTF-8");
                exchange.sendResponseHeaders(200, responseText.getBytes().length);
            } else {
                /** 405 Method Not Allowed */
                exchange.sendResponseHeaders(405, -1);
            }
            OutputStream output = exchange.getResponseBody();
            Instant finalDeEjecucion = Instant.now();
            /**
             * Always remember to close the resources you open.
             * Avoid memory leaks
             */
            log.info("LearningJava - Cerrando recursos ...");
            String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
            log.info("Tiempo de respuesta: ".concat(total));
            output.write(responseText.getBytes());
            output.flush();
            output.close();
            exchange.close();
        }));

        /** creates a default executor */
        server.setExecutor(null);
        server.start();
        log.info("LearningJava - Server started on port 8080");
    }


    /* Uso de al menos tres anotaciones: Override */
    @Override
    public void run(){
        crearUsuarios();
    }

    private static Runnable crearUsuarios() {
        // Comentarios
        try {
            String user = "user";
            String pass = "password";
            JSONArray jsonArray = new JSONArray(textThread);
            JSONObject userJson;

            ResponseDTO response = null;

            log.info("jsonArray.length(): " + jsonArray.length());
            for(int i = 0; i < jsonArray.length(); i++) {
                userJson = new JSONObject(jsonArray.get(i).toString());
                response = createUser(userJson.getString(user), userJson.getString(pass));
                responseTextThread = new JSONObject(response).toString();
                log.info("Usuario " + (i+1) + ": " + responseTextThread);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    /* Uso de al menos tres anotaciones: Deprecated(sice) //java 11 */
    @Deprecated(since = "Anotaciones update")
    private void crearUsuariosThread(){
        try {
            String user = "user";
            String pass = "password";
            JSONArray jsonArray = new JSONArray(textThread);
            JSONObject user1 = new JSONObject(jsonArray.get(0).toString());
            JSONObject user2 = new JSONObject(jsonArray.get(1).toString());
            JSONObject user3 = new JSONObject(jsonArray.get(2).toString());

            // Creamos usuario 1
            response = createUser(user1.getString(user), user1.getString(pass));
            responseTextThread = new JSONObject(response).toString();
            log.info("Usuario 1: " + responseTextThread);
            Thread.sleep(1000);

            // Creamos usuario 2
            response = createUser(user2.getString(user), user2.getString(pass));
            responseTextThread = new JSONObject(response).toString();
            log.info("Usuario 2: " + responseTextThread);
            Thread.sleep(1000);

            // Creamos usuario 3
            response = createUser(user3.getString(user), user3.getString(pass));
            responseTextThread = new JSONObject(response).toString();
            log.info("Usuario 3: " + responseTextThread);
        } catch (Exception e) {
            log.severe("Error: "+e.getMessage());

            /* Uso de por lo menos una excepción de creación propia: ExcepcionGenerica */
            throw new ExcepcionGenerica(e.getMessage());
        }
    }

    /*private static ResponseDTO login(String User, String password) {
        UserBO userBo = new UserBOImpl();
        return userBo.loginUser(User, password);
    }*/

    /* Sobrecarga de almenos un metodo: login */
    private static MiResponseDTO login(String User, String password) {
        UserBO userBo = new UserBOImpl();
        return login(User, password, userBo);
    }

    private static MiResponseDTO login(String User, String password, UserBO userBo) {
        return userBo.loginUser(User, password);
    }

    private static ResponseDTO createUser(String User, String password) {
        UserBO userBo = new UserBOImpl();
        return userBo.createUser(User, password);
    }

    public static Map<String, String> splitQuery(URI uri) throws UnsupportedEncodingException {
        Map<String, String> query_pairs = new LinkedHashMap<>();
        String query = uri.getQuery();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }
        return query_pairs;
    }
}