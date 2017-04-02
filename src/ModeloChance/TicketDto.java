/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloChance;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author carlos
 */
public class TicketDto {

    private ArrayList<LotxTicketDto> loterias;
    private ArrayList<ItemDto> items;
    private String fecha;
    private PersonaDto persona;
    private int Vlr_apuesta;
    private String moneda;
    private String serial;
    private String hora;

    public TicketDto() {
        this.loterias = new ArrayList();
        this.items = new ArrayList();
    }

    /**
     * genera una fecha actual del ticket
     * @return 
     */
    public String generarHora(){
        Date date=new Date();
         SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      String fecha = formatoDelTexto.format(date);
      String v[]=fecha.split(" ");
      return v[1];
    }
    
    
    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public ArrayList<LotxTicketDto> getLoterias() {
        return loterias;
    }

    public void setLoterias(ArrayList<LotxTicketDto> loterias) {
        this.loterias = loterias;
    }

    public ArrayList<ItemDto> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemDto> items) {
        this.items = items;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public PersonaDto getPersona() {
        return persona;
    }

    public void setPersona(PersonaDto persona) {
        this.persona = persona;
    }

    public int getVlr_apuesta() {
        this.calcularApuesta();
        return Vlr_apuesta;
    }

    public int getVrl_apuesta_sinCalcular() {
        return Vlr_apuesta;
    }

    public String getVrl_apuesta_sinCalcularFormato() {
        NumberFormat formatoNumero = NumberFormat.getNumberInstance();
        String resultado = formatoNumero.format(Integer.parseInt(this.Vlr_apuesta + ""));
        return resultado;
    }

    private void calcularApuesta() {

        for (ItemDto dto : this.items) {
            this.Vlr_apuesta += dto.getTotal();
        }
        System.out.println(this.loterias.size());
        this.Vlr_apuesta *= this.loterias.size();
    }

    public void setVlr_apuesta(int Vlr_apuesta) {
        this.Vlr_apuesta = Vlr_apuesta;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

}
