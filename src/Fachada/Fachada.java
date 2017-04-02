/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Fachada;


import Imprimir.Impresora;
import Imprimir.ImprimirCuadreCaja;
import ModeloChance.GestorChance;
import ModeloChance.GestorPremio;
import ModeloChance.LoteriaDto;
import ModeloChance.PersonaDto;
import ModeloChance.RecaudoDto;
import ModeloChance.TicketDto;
import ModeloChance.TicketDto_1;
import java.util.ArrayList;

/**
 *
 * @author carlos puerta principal al sistema
 */
public class Fachada {

    
    public void imprimirRecaudo(String moneda,PersonaDto p){
         GestorChance modulo = new GestorChance();
    ArrayList<RecaudoDto> lista=modulo.listarRecaudo(moneda, p.getCodigo());
    ImprimirCuadreCaja imp=new ImprimirCuadreCaja();
    imp.setLista(lista);
    imp.setP(p);
    imp.setFecha(modulo.fechaSistema());
    imp.imprimirTirilla(moneda);
    
    
    }
    
    /**
     * Registra el ticket en la base de datos
     * @param ticket
     * @return 
     */
    public String Imprimir(TicketDto ticket) {
        GestorChance modulo = new GestorChance();
        return modulo.Imprimir(ticket);
    }

    /**
     * imprime.
     */
    public void impresion(TicketDto dot) {
        
        //To change body of generated methods, choose Tools | Templates.
Impresora imp=new Impresora();
imp.setTicket(dot);

imp.imprimirTicket();
   
    }

   
    /**
     * metodo que lista todos los planes loterias por fecha
     *
     * @param fecha
     * @return
     */
    public ArrayList<LoteriaDto> listarPlanLoteriaXFecha() {
        GestorChance modulo = new GestorChance();
        return modulo.listarPlanLoteriaXFecha();
    }

    /**
     * lista las personas por la cedula
     *
     * @param cedula
     * @return
     */
    public ArrayList<PersonaDto> listarPersonaXCedula(long codigo) {
        GestorChance modulo = new GestorChance();
        return modulo.listarPersonaXCedula(codigo);
    }

    public ArrayList<TicketDto_1> consultarVentasChance(int codigo, String moneda) {
       return GestorPremio.getInstance().consultarVentasChance(codigo, moneda);
    }

    
    
}
