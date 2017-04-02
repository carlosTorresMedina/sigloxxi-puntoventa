/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloChance;

import java.text.NumberFormat;

/**
 *
 * @author carlos
 */
public class ItemDto {

    private int ordinal;
    private String numero;
    private int pleno;
    private int combinado;
    private int pata;
    private int uña;

    public ItemDto() {

    }

    @Override
    public String toString() {
        return "ItemDto{" + "ordinal=" + ordinal + ", numero=" + numero + ", pleno=" + this.getPleno() + ", combinado=" + this.getCombinado() + ", pata=" + this.getPata() + ", u\u00f1a=" + this.getUña() + '}';
    }

    public int digitosNumeros() {
        String numero = this.numero + "";
        return numero.length();
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getPleno() {
        int digitos = this.digitosNumeros();
        if (digitos == 4 || digitos == 3) {
            return pleno;
        }
        return 0;
    }

    public void setPleno(int pleno) {
        this.pleno = pleno;
    }

    public int getCombinado() {
        int digitos = this.digitosNumeros();
        if (digitos == 4 || digitos == 3) {
            return combinado;
        }
        return 0;
    }

    public void setCombinado(int combinado) {
        this.combinado = combinado;
    }

    public int getPata() {
        int digitos = this.digitosNumeros();
        if (digitos == 4 || digitos == 3 || digitos == 2) {
            return pata;
        }
        return 0;
    }

    public void setPata(int pata) {
        this.pata = pata;
    }

    public int getUña() {
        return uña;
    }

    public void setUña(int uña) {
        this.uña = uña;
    }

    public int getTotal() {
        return this.pleno + this.combinado + this.pata + this.uña;
    }

    public String getPlenoFormato() {
        NumberFormat formatoNumero = NumberFormat.getNumberInstance();
        String resultado = formatoNumero.format(Integer.parseInt(this.pleno + ""));
        return resultado;
    }

    public String getCombinadoFormato() {
        NumberFormat formatoNumero = NumberFormat.getNumberInstance();
        String resultado = formatoNumero.format(Integer.parseInt(this.combinado + ""));
        return resultado;
    }

    public String getPataFormato() {
        NumberFormat formatoNumero = NumberFormat.getNumberInstance();
        String resultado = formatoNumero.format(Integer.parseInt(this.pata + ""));
        return resultado;
    }

    public String getUñaFormato() {
        NumberFormat formatoNumero = NumberFormat.getNumberInstance();
        String resultado = formatoNumero.format(Integer.parseInt(this.uña + ""));
        return resultado;
    }

    
   
    
    
    
  

   

}
