/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VistaPrincipal;

import Fachada.Fachada;
import ModeloChance.PersonaDto;
import VistaChance.ConsultarVentasChanceInternalFrame;
import VistaChance.VistaChanceInternalFrame;

/**
 *
 * @author carlos
 */
public class Principal extends javax.swing.JFrame {

    private PersonaDto persona;
    private Fachada fachada;

    //pantalla chance
    private VistaChanceInternalFrame registrarchanceInternalFrame;
    //pantalla recaudo
    private ConsultarVentasChanceInternalFrame consultarventaschanceInternalFrame;

    /**
     * Creates new form Principal
     */
    public Principal(PersonaDto p, Fachada f) {
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setContentPane(this.panel);
        this.persona = p;
        this.fachada = f;
        //creo la pantalla chance
        this.registrarchanceInternalFrame = new VistaChanceInternalFrame(this.persona, this.fachada);
        this.consultarventaschanceInternalFrame=new ConsultarVentasChanceInternalFrame(this,this.persona);
        this.add(this.registrarchanceInternalFrame);
        this.add(this.consultarventaschanceInternalFrame);
        this.mostrarPantallaChance();
    }

    public Fachada getFachada() {
        return fachada;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Pantalla chance");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        panel.setBackground(new java.awt.Color(204, 204, 225));

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 635, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 311, Short.MAX_VALUE)
        );

        jMenu1.setText("Principal");

        jMenuItem1.setText("Chance");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Ver recaudo dia");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
       
        System.exit(0);
    }//GEN-LAST:event_formWindowClosing

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        this.mostrarPantallaChance();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        this.mostrarPantallaRecaudo();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void mostrarPantallaChance(){
    this.registrarchanceInternalFrame.setVisible(true);
        this.registrarchanceInternalFrame.iniciarPantalla();
    }
    
    private void mostrarPantallaRecaudo(){
    this.consultarventaschanceInternalFrame.setVisible(true);
     
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JDesktopPane panel;
    // End of variables declaration//GEN-END:variables
}
