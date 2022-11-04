/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketudpchat;

/**
 *
 * @author emili
 */
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
 
public class Cliente implements Runnable{
    
    private int puerto;
    private String mensaje;
    
    public Cliente(int puerto , String mensaje){
        
        this.puerto = puerto;
        this.mensaje = mensaje;
        
    }

    @Override
    public void run() {
        
        try {               
            //Obtengo la localizacion de localhost
            InetAddress direccionServidor = InetAddress.getByName("localhost");
 
            //Creo el socket de UDP
            DatagramSocket socketUDP = new DatagramSocket();
 
            
                
            byte[] buffer = new byte[1024];
            ByteArrayInputStream exam= new ByteArrayInputStream(buffer);

                //Convierto el mensaje a bytes
            String mensajeString = mensaje;
            buffer = mensajeString.getBytes();

                //Creo un datagrama
            DatagramPacket pregunta = new DatagramPacket(buffer, buffer.length, direccionServidor, puerto);

                //Lo envio con send
            socketUDP.send(pregunta);

                //Preparo la respuesta
            DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);

                //Recibo la respuesta
            socketUDP.receive(peticion);

                //Cojo los datos y lo muestro
            mensajeString = new String(peticion.getData());
            System.out.println(mensajeString);

                //cierro el socket
            exam.reset();
                
            if(mensajeString == "salir"){
                    
            socketUDP.close();
                    
        }
              
 
        } catch (SocketException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        //puerto del servidor
}