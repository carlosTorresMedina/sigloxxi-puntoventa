/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloChance;

/**
 *
 * @author carlos
 */
public class LotxTicketDto {

    private int codigoLoteria;
    private String nombreLoteria;
    
    
    public LotxTicketDto(String codigoLoteria) {
        String v[] = codigoLoteria.split("-");
        if(v.length==2){
        this.codigoLoteria = Integer.parseInt(v[0]);
        this.nombreLoteria=v[1];
        }
        if(v.length==3){
         this.codigoLoteria = Integer.parseInt(v[0]);
        this.nombreLoteria=v[1]+"-"+v[2];
        }
    }

    public int getCodigoLoteria() {
        return codigoLoteria;
    }
    
    public String getNombreLoteria(){
    return this.nombreLoteria;
    }

}
