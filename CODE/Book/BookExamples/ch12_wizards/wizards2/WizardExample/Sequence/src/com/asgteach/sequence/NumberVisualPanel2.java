/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.sequence;

import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.openide.util.NbBundle;

public final class NumberVisualPanel2 extends JPanel {

    public static final String PROP_SECOND_NUMBER = "secondNumber";

    /**
     * Creates new form NumberVisualPanel2
     */
    public NumberVisualPanel2() {
        initComponents();
        secondNumber.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent de) {
                fireChange(de);
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                fireChange(de);
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                fireChange(de);
            }

            private void fireChange(DocumentEvent de) {
                if (secondNumber.getDocument() == de.getDocument()) {
                    firePropertyChange(PROP_SECOND_NUMBER, 0, 1);
                }
            }
        });
    }

    @NbBundle.Messages({"CTL_Panel2Name=Provide Sequence Second Value"})
    @Override
    public String getName() {
        return Bundle.CTL_Panel2Name();
    }

    public String getSecondNumber() {
        return secondNumber.getText();
    }

    public void setCurrentSequence(String text) {
        currentSequence.setText(text);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        secondNumber = new javax.swing.JTextField();
        currentSequence = new javax.swing.JLabel();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(NumberVisualPanel2.class, "NumberVisualPanel2.jLabel1.text")); // NOI18N

        secondNumber.setText(org.openide.util.NbBundle.getMessage(NumberVisualPanel2.class, "NumberVisualPanel2.secondNumber.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(currentSequence, org.openide.util.NbBundle.getMessage(NumberVisualPanel2.class, "NumberVisualPanel2.currentSequence.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(secondNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(currentSequence, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(131, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(secondNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(currentSequence)
                .addContainerGap(60, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel currentSequence;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField secondNumber;
    // End of variables declaration//GEN-END:variables
}
