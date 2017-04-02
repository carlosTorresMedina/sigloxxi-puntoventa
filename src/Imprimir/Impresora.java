package Imprimir;

import ModeloChance.ItemDto;
import ModeloChance.LotxTicketDto;
import ModeloChance.TicketDto;
import java.io.*;

/**
 * Class declaration
 * 
*
 * @author
 * @version 1.10, 08/04/00
 */
public class Impresora {

    TicketDto ticket;

//Variables de  acceso   al dispositivo
    private FileWriter fw;
    private BufferedWriter bw;
    private PrintWriter pw;
    private String dispositivo = "";

    public TicketDto getTicket() {
        return ticket;
    }

    public void setTicket(TicketDto ticket) {
        this.ticket = ticket;
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
    private String acomodarStringImp(String valor, int con) {
        int v = valor.length();
        if (con == 1) {

            if (v == 1) {
                return "      " + valor;
            }
            if (v == 2) {
                return "     " + valor;
            }
            if (v == 3) {
                return "    " + valor;
            }
            if (v == 4) {
                return "   " + valor;
            }
            if (v == 5) {
                return "  " + valor;
            }
            if (v == 6) {
                return " " + valor;
            }

            return valor;
        } else {

            if (v == 1) {
                return valor + "      ";
            }
            if (v == 2) {
                return valor + "     ";
            }
            if (v == 3) {
                return valor + "    ";
            }
            if (v == 4) {
                return valor + "   ";
            }
            if (v == 5) {
                return valor + "  ";
            }
            if (v == 6) {
                return valor + " ";
            }

            return valor;
        }
        
    }

    public void imprimirTicket() {

        setDispositivo("com7");
        escribir((char) 27 + "m");
        setTipoCaracterLatino();
        escribir("AGENCIA DE LOTERIA."); 
        escribir("CONEXION SIGLO XXI.");
        escribir("San Antonio.");
        escribir("Fecha : " + this.ticket.getFecha());
        escribir("Serial : " + this.ticket.getSerial());
        escribir("Hora : " + this.ticket.generarHora());
        escribir("Vendedor : " + this.ticket.getPersona().getCodigoNombre());
        escribir("Moneda : " + ticket.getMoneda());
        correr(1);
        String loterias = "";
        for (LotxTicketDto d : this.ticket.getLoterias()) {
            loterias += d.getNombreLoteria() + " ";
        }
        escribir("Loterias : " + loterias);
        escribir("Numero Pleno  Combinado Pata   Una");
        for (ItemDto i : this.ticket.getItems()) {
            escribir(this.acomodarStringImp(i.getNumero(),2) + this.acomodarStringImp(i.getPlenoFormato(),1) + "   " + this.acomodarStringImp(i.getCombinadoFormato(),1) + this.acomodarStringImp(i.getPataFormato(),1) + this.acomodarStringImp(i.getUñaFormato(),1));
        }
        this.dividir();
        escribir("Total : " + this.ticket.getVrl_apuesta_sinCalcularFormato());
        this.dividir();
        escribir("Caduca a los 8 dias.");
        escribir("******SUERTE TE DESEA SIGLO XXI******");
        correr(10);
        cortar();
        cerrarDispositivo();
    }

}
