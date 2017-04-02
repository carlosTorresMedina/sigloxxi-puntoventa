/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VistaChance;

import java.text.NumberFormat;
import javax.swing.JTextField;

/**
 *
 * @author carlos
 */
public class Total {

    public JTextField total;
    public int pleno0;
    public int combinado0;
    public int pata0;
    public int uña0;
    public int pleno1;
    public int combinado1;
    public int pata1;
    public int uña1;
    public int pleno2;
    public int combinado2;
    public int pata2;
    public int uña2;
    public int pleno3;
    public int combinado3;
    public int pata3;
    public int uña3;
    public int pleno4;
    public int combinado4;
    public int pata4;
    public int uña4;
    public int pleno5;
    public int combinado5;
    public int pata5;
    public int uña5;
    public int pleno6;
    public int combinado6;
    public int pata6;
    public int uña6;
    public int pleno7;
    public int combinado7;
    public int pata7;
    public int uña7;
    public int pleno8;
    public int combinado8;
    public int pata8;
    public int uña8;
    public int pleno9;
    public int combinado9;
    public int pata9;
    public int uña9;
    
    
    public int loterias;

    public void total() {
        if (loterias == 0) {
            this.total.setText("0");
        } else {
            int total = (this.pleno0 + this.pleno1 + this.pleno2 + this.pleno3 + this.pleno4 + this.pleno5+ this.pleno6 + this.pleno7 + this.pleno8 + this.pleno9
                    + this.combinado0 + this.combinado1 + this.combinado2 + this.combinado3 + this.combinado4 + this.combinado5 + this.combinado6 + this.combinado7 + this.combinado8 + this.combinado9
                    +this.pata0 + this.pata1 + this.pata2 + this.pata3 + this.pata4 + this.pata5 + this.pata6 + this.pata7 + this.pata8 + this.pata9
                    + this.uña0+ this.uña1 + this.uña2 + this.uña3 + this.uña4 + this.uña5 + this.uña6 + this.uña7 + this.uña8 + this.uña9
                    ) * this.loterias;
            NumberFormat formatoNumero = NumberFormat.getNumberInstance();
            String resultado = formatoNumero.format(Integer.parseInt(total + ""));

            this.total.setText(resultado);
        }
    }
}
