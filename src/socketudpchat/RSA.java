/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketudpchat;

import java.math.BigInteger;
import java.util.*;
import java.io.*;

/**
 *
 * @author emili
 */
public class RSA {

    
    //variables
    int tamPrimo;
    BigInteger n, q, p;
    BigInteger totient;
    BigInteger e, d;

    //constructor
    public RSA(int tamPrimo){
        this.tamPrimo = tamPrimo;
        generarPrimos();
        generarClaves();
        
    }

    //metodo para generar numeros primos

    public void generarPrimos(){
        //para los primos son p y q
        p = new BigInteger(tamPrimo, 10, new Random());
        do q = new BigInteger(tamPrimo, 10, new Random());
            while(q.compareTo(p)==0);
    }

    //generar las claves

    public void generarClaves(){
        // n = p*q
        n = p.multiply(q); //p*q
        //p(hi) = (p-1)*(q-1)
        totient = p.subtract(BigInteger.valueOf(1));
        totient = totient.multiply(q.subtract(BigInteger.valueOf(1)));

        //elegir el numero coprimo o primo relativo menor que n

        do e = new BigInteger(2*tamPrimo, new Random());
            while ((e.compareTo(totient)!=-1) || 
            (e.gcd(totient).compareTo(BigInteger.valueOf(1))!=0));
        //ahora debemos hacer la operacion modulo
        // d = e^ 1 mod totient

        d = e.modInverse(totient);

    }

    /*
    Cifrar con el numero e ya que "e" es la clave publica
    */ 

    public String encriptar(String mensaje){
        //variables
        int i;
        byte[] temp = new byte[1];
        byte[] digitos = mensaje.getBytes();
        String mensajeBytesString = "";
        
        BigInteger[] bigdigitos = new BigInteger[digitos.length];

        //lo primero que debemos hacer es correr el tamaño de bigdigitos
        for(i = 0; i < bigdigitos.length; i++){
            
            int mensajeBytes = digitos[i];
            mensajeBytesString += String.valueOf(mensajeBytes);
            temp[0] = digitos[i];
            bigdigitos[i] = new BigInteger(temp);
        
        }

        //vamos a cifrar
        String encriptado = "";
        
        for(i = 0; i < bigdigitos.length; i++){
            
            if(i + 1 == bigdigitos.length){
             
                encriptado += (bigdigitos[i].modPow(e , n)).toString();                
                
            }
            else{
            
                encriptado += (bigdigitos[i].modPow(e , n)).toString() + " ";
            
            }
            
        }
               
        return encriptado + "," + d.toString() + "," + n.toString();
    }

    /*
    descifrar array de biginteger
    */ 

    public String desencriptar(String mensaje , String d , String n){
        
        String mensajeDecifrado = "";
        BigInteger[] bytesMensaje = new BigInteger[mensaje.split(" ").length];
        
        for(int i = 0 ; i < bytesMensaje.length ; i ++){
            
            bytesMensaje[i] = new BigInteger(mensaje.split(" ")[i]).modPow(new BigInteger(d) , new BigInteger(n));
            
        }
        
        for(int j = 0 ; j < bytesMensaje.length ; j ++){
        
            char c;
            c = (char)Integer.parseInt(bytesMensaje[j].toString());
            mensajeDecifrado += c;
        
        }
        
        return mensajeDecifrado;
        
    }


    public BigInteger damep(){
        return (p);
    }

    public BigInteger dameq(){
        return (q);
    }

    public BigInteger dametotient(){
        return (totient);
    }

    public BigInteger damen(){
        return (n);
    }

    public BigInteger damee(){
        return (e);
    }

    public BigInteger damed(){
        return (d);
    }

    BigInteger[] desencriptar(String texto_A_Decifrar) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
