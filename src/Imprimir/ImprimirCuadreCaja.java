/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Imprimir;

import ModeloChance.ItemDto;
import ModeloChance.LotxTicketDto;
import ModeloChance.PersonaDto;
import ModeloChance.RecaudoDto;
import ModeloChance.TicketDto;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author carlos
 */
public class ImprimirCuadreCaja {

    private ArrayList<RecaudoDto> lista;
    private PersonaDto p;
    private String fecha;
//Variables de  acceso   al dispositivo
    private FileWriter fw;
    private BufferedWriter bw;
    private PrintWriter pw;
    private String dispositivo = "";

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public ArrayList<RecaudoDto> getLista() {
        return lista;
    }

    public void setLista(ArrayList<RecaudoDto> lista) {
        this.lista = lista;
    }

    public PersonaDto getP() {
        return p;
    }

    public void setP(PersonaDto p) {
        this.p = p;
    }

    /**
     * Esta funcion inicia el dispositivo donde se va a imprimir
     */
    public void setDispositivo(String texto) {
        dispositivo = texto;
        try {
            fw = new FileWriter(dispositivo);
            bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);
        } catch (Exception e) {
            System.out.print(e);
        }

    }

    public void escribir(String texto) {
        try {
            pw.println(texto);
        } catch (Exception e) {
            System.out.print(e);
        }

    }

    public void cortar() {
        try {

            char[] ESC_CUT_PAPER = new char[]{0x1B, 'm'};
            if (!this.dispositivo.trim().equals("pantalla.txt")) {
                pw.write(ESC_CUT_PAPER);
            }

        } catch (Exception e) {
            System.out.print(e);
        }

    }

    public void avanza_pagina() {
        try {
            if (!this.dispositivo.trim().equals("pantalla.txt")) {
                pw.write(0x0C);
            }

        } catch (Exception e) {
            System.out.print(e);
        }

    }

    public void setRojo() {
        try {
            char[] ESC_CUT_PAPER = new char[]{0x1B, 'r', 1};
            if (!this.dispositivo.trim().equals("pantalla.txt")) {
                pw.write(ESC_CUT_PAPER);
            }

        } catch (Exception e) {
            System.out.print(e);
        }

    }

    public void setNegro() {
        try {
            char[] ESC_CUT_PAPER = new char[]{0x1B, 'r', 0};
            if (!this.dispositivo.trim().equals("pantalla.txt")) {
                pw.write(ESC_CUT_PAPER);
            }

        } catch (Exception e) {
            System.out.print(e);
        }
    }

    public void setTipoCaracterLatino() {
        try {
            char[] ESC_CUT_PAPER = new char[]{0x1B, 'R', 18};
            if (!this.dispositivo.trim().equals("pantalla.txt")) {
                pw.write(ESC_CUT_PAPER);
            }

        } catch (Exception e) {
            System.out.print(e);
        }
    }

    public void setFormato(int formato) {
        try {
            char[] ESC_CUT_PAPER = new char[]{0x1B, '!', (char) formato};
            if (!this.dispositivo.trim().equals("pantalla.txt")) {
                pw.write(ESC_CUT_PAPER);
            }

        } catch (Exception e) {
            System.out.print(e);
        }
    }

    public void correr(int fin) {
        try {
            int i = 0;
            for (i = 1; i <= fin; i++) {
                this.salto();
            }

        } catch (Exception e) {
            System.out.print(e);
        }
    }

    public void salto() {
        try {

            pw.println("");

        } catch (Exception e) {
            System.out.print(e);

        }

    }

    public void dividir() {
        escribir("________________________");

    }

    public void cerrarDispositivo() {
        try {

            pw.close();
            if (this.dispositivo.trim().equals("pantalla.txt")) {

                java.io.File archivo = new java.io.File("pantalla.txt");
                java.awt.Desktop.getDesktop().open(archivo);
            }

        } catch (Exception e) {
            System.out.print(e);
        }

    }

    /**
     * acomoda el string para que contenga 8 caracteres. el con indica si se
     * agregan al inicio o al final 1 al inicio. 2 al final.
     *
     * @param valor
     * @return
     */
    private String acomodarStringImp(String valor) {
        int v = valor.length();
        switch (v) {
            case 1:return valor+"          " ;
            case 2:return valor+"         " ;
            case 3:return valor+"        "  ;
            case 4:return valor+"       " ;
            case 5:return valor+"      " ;
            case 6:return valor+"     " ;
            case 7:return valor+"    " ;
            case 8:return valor+"   " ;
            case 9:return valor+"  " ;
            case 10:return valor+" " ;
            case 11:return valor;

        }
       return valor;
    }

    public void imprimirTirilla(String moneda) {

        setDispositivo("com7");
        escribir((char) 27 + "m");
        setTipoCaracterLatino();
        escribir("AGENCIA DE LOTERIA.");
        escribir("CONEXION SIGLO XXI.");
        escribir("San Antonio.");
        escribir("Fecha : " + this.fecha);
        escribir("CUADRE DE CAJA POR LOTERIA");
        escribir("Vendedor : " + this.p.getCodigoNombre());
        escribir("Moneda : " + moneda);
        correr(1);
        escribir("Loteria           Ventas");
        int suma=0;
        for (RecaudoDto d : lista) {
            escribir(this.acomodarStringImp(d.getNombreLoteria()) +"      "+ d.getValor());
            suma = suma+d.getValor();
            
                    }
        correr(10);
        cortar();
        cerrarDispositivo();
    }

}
