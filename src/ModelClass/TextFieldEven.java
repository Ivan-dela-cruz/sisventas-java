/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelClass;

import java.awt.event.KeyEvent;
import javax.swing.JTextField;

/**
 *
 * @author ivan
 */
public class TextFieldEven {

    public void textkeyPress(KeyEvent evt) {
        // declaramos una variable y le asignamos un evento

        char car = evt.getKeyChar();

        //condicion que nos permite ingresar datos de tipo texto
        if ((car < 'a' || car > 'z') && (car < 'A' || car > 'Z')
                && (car != (char) KeyEvent.VK_BACK_SPACE) && (car != (char) KeyEvent.VK_SPACE)) {
            evt.consume();

        }
    }

    public void numberkeyPress(KeyEvent evt) {
        // declaramos una variable y le asignamos un evento

        char car = evt.getKeyChar();

        //condicion que nos permite ingresar datos de tipo texto
        if ((car < '0' || car > '9') && (car != (char) KeyEvent.VK_BACK_SPACE)) {
            evt.consume();

        }
    }

    public void numberDecimalkeyPress(KeyEvent evt, JTextField TextField) {
        // declaramos una variable y le asignamos un evento

        char car = evt.getKeyChar();

        //condicion que nos permite ingresar datos de tipo numerico con un punto decimal
        if ((car < '0' || car > '9') && TextField.getText().contains(".")
                && (car != (char) KeyEvent.VK_BACK_SPACE)) {
            evt.consume();

        } else if ((car < '0' || car > '9') && (car != '.') && (car != (char) KeyEvent.VK_BACK_SPACE)) {
            evt.consume();
        }
    }

}
